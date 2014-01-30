package mx.gob.renapo.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import mx.gob.renapo.dto.DTOAlerta;
import mx.gob.renapo.dto.DTOAlertaContacto;
import mx.gob.renapo.dto.DTOCodigoErrorAlerta;

public interface DAOAlerta {
	
	/**
	 * Metodo que consulta las alertas que estan pendientes para ser enviadas
	 * @return DTOAlerta 
	 * @throws Exception
	 */
	List<DTOAlertaContacto> consultaAlerta(DTOAlerta alertaDTO) 
			throws Exception;
	
	/**
	 * Metodo para actualizar el estatus de la alerta
	 * @param alerta objeto con la informacion a actualizar en la alerta
	 * @param campoNumeroIntentos argumento con el campo de numero de intentos
	 * a actualizar
	 * @param campoEstatusContacto argumento con el campo de estatus de contaacto
	 * a actualizar
	 * @throws DataAccessException en caso de error con la transaccion con la BBDD
	 * @throws SQLException en caso de error con la conexion con la BBDD
	 */
	void actualizaIntentosAlerta(DTOAlertaContacto alerta, String campoNumeroIntentos, 
			String campoEstatusContacto) 
			throws DataAccessException, SQLException;
	
	/**
	 * Metodo para borrar una alerta
	 * @param alerta argumento que contiene la alerta que se va a borrar
	 * @throws DataAccessException en caso de error con la transaccion en la BBDD
	 * @throws SQLException En caso de error en la conexxion a la BBDD
	 */
	void borrarAlerta(DTOAlerta alerta) throws DataAccessException, SQLException;
	
	/**
	 * Metodo para borrar un registro de alerta_contacto
	 * @param idAlerta argumento con el id de la alerta
	 * @param idContacto argumento con el id del contacto
	 * @throws DataAccessException en caso de que exista un error en la transaccion
	 * @throws SQLException en caso de error en la conexion con la BBDD
	 */
	void borrarAlertaContacto(Long idAlerta, Long idContacto) throws DataAccessException, SQLException;
	
	/**
	 * Metodo para consultar el numerod de alertas pendientes
	 * @param idAlerta id de la alerta a conaultar en tabla de alerta_contacto
	 * @return numero de alertas pendientes 
	 *@throws DataAccessException en caso de que exista un error en la transaccion
	 * @throws SQLException en caso de error en la conexion con la BBDD
	 */
	Integer consultaNumeroAlertasPendientesPorEnviar(Long idAlerta )throws DataAccessException, SQLException;

}
