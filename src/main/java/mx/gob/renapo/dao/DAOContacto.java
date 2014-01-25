package mx.gob.renapo.dao;

import java.util.List;

import mx.gob.renapo.dto.DTOContacto;

public interface DAOContacto {
	
	/**
	 * Metodo para guardar un nuevo contacto
	 * @param contacto
	 * @throws Exception
	 */
	void guardaContacto(DTOContacto contacto) throws Exception;
	
	/**
	 * Metodo para recuperar la informacion de un contacto
	 * @param idContacto
	 * @return DTOContacto 
	 * @throws Exception
	 */
	DTOContacto consultaContacto(Long idContacto) throws Exception;
	
	/**
	 * Metodo para recuperar una lista de contactos
	 * @param contactos lista con los id de los contactos a recuperar
	 * @return List<DTOContacto>
	 * @throws Exception
	 */
	List<DTOContacto> consultaContactos(List<Object> contactos) throws Exception;

}
