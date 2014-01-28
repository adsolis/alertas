package mx.gob.renapo.dao.implement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import mx.gob.renapo.dao.DAOAlerta;
import mx.gob.renapo.dto.DTOAlerta;
import mx.gob.renapo.dto.DTOCodigoErrorAlerta;
import mx.gob.renapo.util.Utileria;

public class DAOAlertaImpl implements DAOAlerta{

	private DataSource dataSourceOracle;
	private JdbcTemplate jdbcTemplate;
	private static final StringBuilder QUERY_CONSULTAR_ALERTAS_PENDIENTES = new StringBuilder()
	.append("select T1.id, T1.texto, T1.titulo, TO_CHAR(T1.fecha_creacion, 'dd/MM/YYYY') as FECHA_CREACION, ")
	.append("T1.contacto, T2.correo, T2.twitter ")
	.append("from alerta T1, contacto t2 where T1.tipo_alerta = ? ")
	.append("and T1.contacto = T2.id");
	

	public DTOAlerta consultaAlerta(DTOAlerta alertaDTO) throws Exception {
		Object[] argumentosConsulta = null;
		argumentosConsulta = new Object[] {
			alertaDTO.getTipo()	
		};
		Connection con = null;
		List<Map<String, Object>> resultados = null;
		con = dataSourceOracle.getConnection();
		jdbcTemplate = new JdbcTemplate(dataSourceOracle);
		resultados = jdbcTemplate.queryForList(QUERY_CONSULTAR_ALERTAS_PENDIENTES.toString(), 
		argumentosConsulta);
		
		if(resultados.size()>0) {
			alertaDTO = mapeaResultadoAlerta(resultados.get(0), alertaDTO);
		}
		
		con.close();
		return alertaDTO;
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
	 * Metodo para mapear la alerta recuperada
	 * @param linea
	 * @param alertaDTO
	 * @return
	 */
	private DTOAlerta mapeaResultadoAlerta(Map<String, Object> linea, DTOAlerta alertaDTO) {
		
		alertaDTO.setId(Long.valueOf(linea.get("id").toString()));
		alertaDTO.setTexto(new StringBuilder().append(linea.get("texto").toString()));
		alertaDTO.setTitulo(linea.get("titulo").toString());
		alertaDTO.setIdContacto(Long.valueOf(linea.get("contacto").toString()));
		alertaDTO.setFechaCreacionAlerta(linea.get("fecha_creacion").toString());
		alertaDTO.setContactoCorreo(linea.get("correo").toString());
		alertaDTO.setContactoTwitter(linea.get("twitter").toString());
		
		return alertaDTO;
	}
	
	public void setDataSourceOracle(DataSource dataSourceOracle) {
		        this.dataSourceOracle = dataSourceOracle;
		    }


}
