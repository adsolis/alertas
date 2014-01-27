package mx.gob.renapo.dao;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;

import mx.gob.renapo.dto.DTOTipoAlertas;

public interface DAOTipoAlerta {
	
	/**
	 * Metodo que consulta el tipo de alerta existente
	 * @return objeto con el tipo de alerta recuperado
	 * @throws DataAccessException
	 * @throws SQLException
	 */
	
	DTOTipoAlertas consultaTipoAlerta() throws DataAccessException, SQLException;

}
