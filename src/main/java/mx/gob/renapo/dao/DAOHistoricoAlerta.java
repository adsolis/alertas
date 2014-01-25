package mx.gob.renapo.dao;

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
	 * @throws Exception
	 */
	void guardaHistoricoAlerta(DTOHistoricoAlerta historicoAlerta) throws Exception;

}
