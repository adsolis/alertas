package mx.gob.renapo.dao.implement;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import mx.gob.renapo.dao.DAOHistoricoAlerta;
import mx.gob.renapo.dto.DTOHistoricoAlerta;

public class DAOHistoricoAlertaImpl implements DAOHistoricoAlerta{
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private StringBuilder INSERT_HISTORICO_ALERTA = new StringBuilder()
	.append("INSERT INTO historico_alerta ")
	.append("(fechaEnvio, idCodigoError, texto, titulo, estatus, fechaCreacionAlerta, contacto, tipo_alerta) ")
	.append("VALUES (?,?,?,?,?,?,?,?");

	public DTOHistoricoAlerta consultaHistoricoAlerta() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Metodo para guardar la informacion de los resultados del envio de la alerta
	 * @param historicoAlerta argumento con la informacion de los resultados del envio
	 * @throws Exception
	 */
	public void guardaHistoricoAlerta(DTOHistoricoAlerta historicoAlerta)
			throws Exception {
		Connection con = null;
		Object [] argumentos = null;
		argumentos = new Object[] {
				historicoAlerta.getFechaEnvio(),
				historicoAlerta.getCodigoError().getClaveCodigo(),
				historicoAlerta.getAlerta().getTexto().toString(),
				historicoAlerta.getAlerta().getTitulo(),
				historicoAlerta.getEstatus(),
				historicoAlerta.getAlerta().getTipo(),
				historicoAlerta.getAlerta().getFechaCreacionAlerta(),
				historicoAlerta.getAlerta().getContactoCorreo()
		};
		
		
		con = dataSource.getConnection();
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(INSERT_HISTORICO_ALERTA.toString(), argumentos);
		con.close();
		con = null;

	}

	public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
