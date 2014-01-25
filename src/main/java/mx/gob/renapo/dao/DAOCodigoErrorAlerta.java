package mx.gob.renapo.dao;

import mx.gob.renapo.dto.DTOCodigoErrorAlerta;

public interface DAOCodigoErrorAlerta {
	
	/**
	 * Metodo para recuperar la informacion de un codigo error
	 * @param idCodigoError
	 * @return
	 * @throws Exception
	 */
	DTOCodigoErrorAlerta consultaCodigoError(Integer idCodigoError) throws Exception;
	
	

}
