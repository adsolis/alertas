package mx.gob.renapo.dao.implement;

import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

import mx.gob.renapo.dao.DAOHistoricoAlerta;
import mx.gob.renapo.dto.DTOHistoricoAlerta;
import mx.gobb.renapo.connection.ConexionBBDD;

public class DAOHistoricoAlertaImpl implements DAOHistoricoAlerta{
	
	private DataSource dataSource;

	public DTOHistoricoAlerta consultaHistoricoAlerta() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void guardaHistoricoAlerta(DTOHistoricoAlerta historicoAlerta)
			throws Exception {
		Connection con = null;
		
		StringBuilder queryInsertarHistorico = new StringBuilder()
		.append("INSERT INTO historico_alerta")
		.append("(fechaEnvio, idCodigoError, texto, titulo, estatus, fechaCreacionAlerta, contacto, tipo_alerta)")
		.append(" VALUES ('")
		.append(historicoAlerta.getFechaEnvio())
		.append("', ")
		.append(historicoAlerta.getCodigoError().getClaveCodigo())
		.append(", '")
		.append(historicoAlerta.getAlerta().getTexto().toString())
		.append("', '")
		.append(historicoAlerta.getAlerta().getTitulo())
		.append("', '")
		.append(historicoAlerta.getEstatus())
		.append("', '")
		.append(historicoAlerta.getAlerta().getTipo())
		.append("' , '")
		.append("', '")
		.append(historicoAlerta.getAlerta().getFechaCreacionAlerta())
		.append("', '")
		.append(historicoAlerta.getAlerta().getContactoCorreo())
		.append("')");
		
		con = dataSource.getConnection();
		Statement smt = con.createStatement();
		System.out.println(queryInsertarHistorico.toString());
		smt.executeUpdate(queryInsertarHistorico.toString());
		con.close();
		con = null;

	}

	public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
