package mx.gob.renapo.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import mx.gob.renapo.dto.DTOAlerta;
import mx.gob.renapo.dto.DTOCodigoErrorAlerta;

public interface DAOAlerta {
	
	/**
	 * Metodo que consulta las alertas que estan pendientes para ser enviadas
	 * @return DTOAlerta 
	 * @throws Exception
	 */
	List<DTOAlerta> consultaAlerta(DTOAlerta alertaDTO) 
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
	void actualizaIntentosAlerta(DTOAlerta alerta, String campoNumeroIntentos, 
			String campoEstatusContacto) 
			throws DataAccessException, SQLException;
	
	/**
	 * Metodo para borrar una alerta
	 * @param alerta argumento que contiene la alerta que se va a borrar
	 * @throws DataAccessException en caso de error con la transaccion en la BBDD
	 * @throws SQLException En caso de error en la conexxion a la BBDD
	 */
	void borrarAlerta(DTOAlerta alerta) throws DataAccessException, SQLException;

}
