package mx.gob.renapo.dao.implement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import mx.gob.renapo.dao.DAOContacto;
import mx.gob.renapo.dto.DTOContacto;
import mx.gobb.renapo.connection.ConexionBBDD;

/**
 * Clase para realizar las transacciones relacionadas con la informacion de Contacto
 * @author Alejandro Diaz
 *
 */
public class DAOContactoImpl implements DAOContacto{
	
	private DataSource dataSourceOracle;

	public void guardaContacto(DTOContacto contacto) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public DTOContacto consultaContacto(Long idContacto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DTOContacto> consultaContactos(List<Object> contactos)
			throws Exception {
		
		Connection con = null;
		List<DTOContacto> listaContactos = new ArrayList<DTOContacto>();
		DTOContacto contacto = null;
		StringBuilder consultaContactos = new StringBuilder();
		consultaContactos.append("SELECT * FROM contacto WHERE tipo='")
		.append(contactos.get(0))
		.append("' AND nivel=")
		.append(contactos.get(1))
		.append(" AND codigo='")
		.append(contactos.get(2))
		.append("'");
		
		con = dataSourceOracle.getConnection();
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(consultaContactos.toString());
		
		while(rs.next()) {
			contacto = new DTOContacto();
			contacto.setId(rs.getLong("id"));
			contacto.setNombre(rs.getString("nombre"));
			contacto.setApellidoPaterno(rs.getString("apellidoPaterno"));
			contacto.setApellidoMaterno(rs.getString("apellidoMaterno"));
			contacto.setCorreo(rs.getString("correo"));
			contacto.setTwitter(rs.getString("twitter"));
			listaContactos.add(contacto);
		}
		
		con.close();
		con = null;
		return listaContactos;
	}


	public void setDataSourceOracle(DataSource dataSourceOracle) {
        this.dataSourceOracle = dataSourceOracle;
    }

}
