package mx.gob.renapo.dao;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;

import mx.gob.renapo.dto.DTOHistoricoAlerta;

public interface DAOHistoricoAlerta {
	
	/**
	 * Metodo para consultar el historico de alertas
	 * @return DTOHistoricoAlerta
	 */
	DTOHistoricoAlerta consultaHistoricoAlerta() throws Exception;
	
	/**
	 * Metodo para guardar la informacion de los resultados del envio de la alerta
	 * @param historicoAlerta argumento con la informacion de los resultados del envio
	 * @throws DataAccessException en caso de error con la transaccion en la BBDD
	 * @throws SQLException En caso de error en la conexxion a la BBDD
	 */
	void guardaHistoricoAlerta(DTOHistoricoAlerta historicoAlerta) throws 
	DataAccessException,
	SQLException;

}
