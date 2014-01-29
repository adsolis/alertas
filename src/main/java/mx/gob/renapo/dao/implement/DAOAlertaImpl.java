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
import mx.gob.renapo.util.Utileria;

public class DAOAlertaImpl implements DAOAlerta{

	private DataSource dataSourceOracle;
	private JdbcTemplate jdbcTemplate;
	/**
	 * Constante con el query para recuperar las alertas y contactos con un tipo de alerta especifico
	 */
	private static final StringBuilder QUERY_CONSULTAR_ALERTAS_PENDIENTES = new StringBuilder()
	.append("select T1.id, T1.texto, T1.titulo, TO_CHAR(T1.fecha_creacion, 'dd/MM/YYYY') as FECHA_CREACION, ")
	.append("T1.numero_intentos_correo, T1.numero_intentos_twitter, T1.contacto, T2.correo, T2.twitter ")
	.append("from alerta T1, contacto T2 where T1.tipo_alerta = ? ")
	.append("and T1.contacto = T2.id");
	
	/**
	 * Constante con el query para eliminar una alerta
	 */
	private static final StringBuilder ELIMINAR_ALERTA = new StringBuilder()
	.append("DELETE FROM alerta WHERE id = ?");
	

	public List<DTOAlerta> consultaAlerta(DTOAlerta alertaDTO) throws Exception {
		Object[] argumentosConsulta = null;
		argumentosConsulta = new Object[] {
			alertaDTO.getTipo()	
		};
		List<DTOAlerta> listaAlertas = new ArrayList<DTOAlerta>();
		Connection con = null;
		List<Map<String, Object>> resultados = null;
		con = dataSourceOracle.getConnection();
		jdbcTemplate = new JdbcTemplate(dataSourceOracle);
		resultados = jdbcTemplate.queryForList(QUERY_CONSULTAR_ALERTAS_PENDIENTES.toString(), 
		argumentosConsulta);
		
		if(resultados.size()>0) {
			for(Map<String, Object> resultado: resultados) {
				listaAlertas.add(mapeaResultadoAlerta(resultado));
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
	public void actualizaIntentosAlerta(DTOAlerta alerta, 
			String campoNumeroIntentos, 
			String campoEstatusContacto) 
			throws DataAccessException, SQLException {
		
		StringBuilder actualizaIntentosAlerta = new StringBuilder()
		.append("UPDATE alertas_pendientes SET ").append(campoNumeroIntentos)
		.append(" = ?, ").append(campoEstatusContacto)
		.append(" = ? WHERE id = ?");
		Object[] argumentos = null;
		
		if(campoEstatusContacto.equals(Utileria.CAMPO_ESTATUS_CORREO)) {
			argumentos = new Object[] {
				alerta.getNumeroIntentosCorreo(),
				alerta.getEstatusEnvioCorreo(),
				alerta.getId()
			};
		}
		
		if(campoEstatusContacto.equals(Utileria.CAMPO_ESTATUS_TWITTER)) {
			argumentos = new Object[] {
				alerta.getNumeroIntentosTwitter(),
				alerta.getEstatusEnvioTwitter(),
				alerta.getId()
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
	 * Metodo para mapear la alerta recuperada
	 * @param linea
	 * @param alertaDTO
	 * @return
	 */
	private DTOAlerta mapeaResultadoAlerta(Map<String, Object> linea) {
		
		DTOAlerta alertaDTO = new DTOAlerta();
		alertaDTO.setId(Long.valueOf(linea.get("id").toString()));
		alertaDTO.setTexto(new StringBuilder().append(linea.get("texto").toString()));
		alertaDTO.setTitulo(linea.get("titulo").toString());
		alertaDTO.setIdContacto(Long.valueOf(linea.get("contacto").toString()));
		alertaDTO.setFechaCreacionAlerta(linea.get("fecha_creacion").toString());
		alertaDTO.setContactoCorreo(linea.get("correo").toString());
		alertaDTO.setContactoTwitter(Utileria.recuperaDato(linea.get("twitter")).toString());
		alertaDTO.setNumeroIntentosCorreo(Integer.valueOf(linea.get("numero_intentos_correo").toString()));
		alertaDTO.setNumeroIntentosTwitter(Integer.valueOf(linea.get("numero_intentos_twitter").toString()));
		
		return alertaDTO;
	}
	
	

	
	public void setDataSourceOracle(DataSource dataSourceOracle) {
		        this.dataSourceOracle = dataSourceOracle;
		    }


}
