package mx.gob.renapo.dao;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;

import mx.gob.renapo.dto.DTOConfiguracionEnvioAlerta;

public interface DAOConfiguracionEnvioAlerta {
	
	DTOConfiguracionEnvioAlerta consultaConfiguracionEnvioAlerta() throws DataAccessException,
	SQLException;

}
