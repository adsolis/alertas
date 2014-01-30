package mx.gob.renapo.dao.implement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import mx.gob.renapo.dao.DAOAlerta;
import mx.gob.renapo.dto.DTOAlerta;
import mx.gob.renapo.dto.DTOAlertaContacto;
import mx.gob.renapo.util.Utileria;

public class DAOAlertaImpl implements DAOAlerta{

	private DataSource dataSourceOracle;
	private JdbcTemplate jdbcTemplate;
	/**
	 * Constante con el query para recuperar las alertas y contactos con un tipo de alerta especifico
	 */
	private static final StringBuilder QUERY_CONSULTAR_ALERTAS_PENDIENTES = new StringBuilder()
	.append("select T1.ID, T1.TIPO_ALERTA, T1.TEXTO, T1.TITULO, TO_CHAR(T1.FECHA_CREACION, 'dd/MM/YYYY') AS FECHA_CREACION, T2.NUMERO_INTENTOS_CORREO,")
	.append("T2.ID_ALERTA, T2. ID_CONTACTO, ")
	.append("T2.NUMERO_INTENTOS_TWITTER, T3.CORREO, T3.TWITTER ")
	.append("from ALERTA T1, ALERTA_CONTACTO T2, CONTACTO T3 ")
	.append("where T1.TIPO_ALERTA = ? and T1.ID = T2.ID_ALERTA  and T2.ID_CONTACTO = T3.ID");
	
	private static final StringBuilder QUERY_CONSULTA_CANTIDAD_ALERTAS_PENDIENTES = 
			new StringBuilder().append("SELECT COUNT(*) FROM ALERTA_CONTACTO ")
			.append(" WHERE ID_ALERTA = ?");
	
	/**
	 * Constante para eliminar un registro de alerta contacto
	 */
	private static final StringBuilder ELIMINAR_ALERTA_CONTACTO = new StringBuilder()
	.append("DELETE FROM alerta_contacto WHERE id_alerta = ? AND id_contacto = ?");
	
	/**
	 * Constante con el query para eliminar una alerta
	 */
	private static final StringBuilder ELIMINAR_ALERTA = new StringBuilder()
	.append("DELETE FROM alerta WHERE id = ?");
	

	public List<DTOAlertaContacto> consultaAlerta(DTOAlerta alertaDTO) throws Exception {
		Object[] argumentosConsulta = null;
		argumentosConsulta = new Object[] {
			alertaDTO.getTipo()	
		};
		List<DTOAlertaContacto> listaAlertas = new ArrayList<DTOAlertaContacto>();
		Connection con = null;
		List<Map<String, Object>> resultados = null;
		con = dataSourceOracle.getConnection();
		jdbcTemplate = new JdbcTemplate(dataSourceOracle);
		resultados = jdbcTemplate.queryForList(QUERY_CONSULTAR_ALERTAS_PENDIENTES.toString(), 
		argumentosConsulta);
		
		if(resultados.size()>0) {
			for(Map<String, Object> resultado: resultados) {
				listaAlertas.add(mapeaResultadoAlertaContacto(resultado));
			}
		}
		
		con.close();
		return listaAlertas;
	}
	
	/**
	 * Metodo para actualizar el estatus y numero de intentos de la alerta
	 * @param alerta objeto con la informacion a actualizar en la alerta
	 * @param campoNumeroIntentos argumento con el campo de numero de intentos
	 * a actualizar
	 * @param campoEstatusContacto argumento con el campo de estatus de contaacto
	 * a actualizar
	 * @throws DataAccessException en caso de error con la transaccion con la BBDD
	 * @throws SQLException en caso de error con la conexion con la BBDD
	 */
	public void actualizaIntentosAlerta(DTOAlertaContacto alerta, 
			String campoNumeroIntentos, 
			String campoEstatusContacto) 
			throws DataAccessException, SQLException {
		
		StringBuilder actualizaIntentosAlerta = new StringBuilder()
		.append("UPDATE alerta_contacto SET ").append(campoNumeroIntentos)
		.append(" = ?, ").append(campoEstatusContacto)
		.append(" = ? WHERE id_contacto = ? and id_alerta = ? ");
		Object[] argumentos = null;
		
		if(campoEstatusContacto.equals(Utileria.CAMPO_ESTATUS_CORREO)) {
			argumentos = new Object[] {
				alerta.getNumeroIntentosCorreo(),
				alerta.getEstatusEnvioCorreo(),
				alerta.getIdContacto(),
				alerta.getAlerta().getId()
			};
		}
		
		if(campoEstatusContacto.equals(Utileria.CAMPO_ESTATUS_TWITTER)) {
			argumentos = new Object[] {
				alerta.getNumeroIntentosTwitter(),
				alerta.getEstatusEnvioTwitter(),
				alerta.getIdContacto(),
				alerta.getAlerta().getId()
			};
		}
		
		Connection con = null;
		con = dataSourceOracle.getConnection();
		jdbcTemplate = new JdbcTemplate(dataSourceOracle);
		jdbcTemplate.update(actualizaIntentosAlerta.toString(), argumentos);
		
	}
	
	/**
	 * Metodo para borrar una alerta
	 * @param alerta argumento que contiene la alerta que se va a borrar
	 * @throws DataAccessException en caso de error con la transaccion en la BBDD
	 * @throws SQLException En caso de error en la conexxion a la BBDD
	 */
	public void borrarAlerta(DTOAlerta alerta) throws DataAccessException,
			SQLException {
		
		Object[] argumentos = new Object[] {
			alerta.getId()	
		};	
		jdbcTemplate = new JdbcTemplate(dataSourceOracle);
		jdbcTemplate.update(ELIMINAR_ALERTA.toString(), argumentos);		
	}
	
	/**
	 * Metodo para borrar un registro de alerta_contacto
	 * @param idAlerta argumento con el id de la alerta
	 * @param idContacto argumento con el id del contacto
	 * @throws DataAccessException en caso de que exista un error en la transaccion
	 * @throws SQLException en caso de error en la conexion con la BBDD
	 */
	public void borrarAlertaContacto(Long idAlerta, Long idContacto)
			throws DataAccessException, SQLException {
		
		Object [] argumentos = new Object[] {
				idAlerta,
				idContacto
		};
		
		jdbcTemplate = new JdbcTemplate(dataSourceOracle);
		jdbcTemplate.update(ELIMINAR_ALERTA_CONTACTO.toString(), argumentos);
		
	}
	
	/**
	 * Metodo para consultar el numerod de alertas pendientes
	 * @param idAlerta id de la alerta a conaultar en tabla de alerta_contacto
	 * @return numero de alertas pendientes 
	 *@throws DataAccessException en caso de que exista un error en la transaccion
	 * @throws SQLException en caso de error en la conexion con la BBDD
	 */
	public Integer consultaNumeroAlertasPendientesPorEnviar(Long idAlerta)
			throws DataAccessException, SQLException {
		Integer alertasPendientes = 0;
		Object [] argumentos = new Object[] {
				idAlerta
		};
		
		jdbcTemplate = new JdbcTemplate(dataSourceOracle);
		alertasPendientes = jdbcTemplate.queryForInt(QUERY_CONSULTA_CANTIDAD_ALERTAS_PENDIENTES.toString(), argumentos);
		
		return alertasPendientes;
	}

	
	/**
	 * Metodo para mapear la alerta recuperada
	 * @param linea
	 * @param alertaDTO
	 * @return
	 */
	private DTOAlertaContacto mapeaResultadoAlertaContacto(Map<String, Object> linea) {
		
		DTOAlerta alertaDTO = new DTOAlerta();
		DTOAlertaContacto alertaContacto = new DTOAlertaContacto();
		alertaDTO.setId(Long.valueOf(linea.get("id").toString()));
		alertaDTO.setTexto(new StringBuilder().append(linea.get("texto").toString()));
		alertaDTO.setTitulo(linea.get("titulo").toString());
		alertaDTO.setFechaCreacionAlerta(linea.get("fecha_creacion").toString());
		alertaDTO.setTipo(linea.get("tipo_alerta").toString());
		alertaContacto.setAlerta(alertaDTO);
		alertaContacto.setIdContacto(Long.valueOf(linea.get("id_contacto").toString()));
		alertaContacto.setContactoCorreo(linea.get("correo").toString());
		alertaContacto.setContactoTwitter(Utileria.recuperaDato(linea.get("twitter")).toString());
		alertaContacto.setNumeroIntentosCorreo(Integer.valueOf(linea.get("numero_intentos_correo").toString()));
		alertaContacto.setNumeroIntentosTwitter(Integer.valueOf(linea.get("numero_intentos_twitter").toString()));
		
		return alertaContacto;
	}
	
	

	
	public void setDataSourceOracle(DataSource dataSourceOracle) {
		        this.dataSourceOracle = dataSourceOracle;
		    }

}
