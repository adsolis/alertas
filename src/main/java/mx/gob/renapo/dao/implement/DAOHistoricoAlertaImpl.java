package mx.gob.renapo.dao.implement;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import mx.gob.renapo.dao.DAOHistoricoAlerta;
import mx.gob.renapo.dto.DTOHistoricoAlerta;

public class DAOHistoricoAlertaImpl implements DAOHistoricoAlerta{
	
	private DataSource dataSourceOracle;
	private JdbcTemplate jdbcTemplate;
	private StringBuilder INSERT_HISTORICO_ALERTA = new StringBuilder()
	.append("INSERT INTO HISTORICO_ALERTA ")
	.append("(FECHA_ENVIO, CODIGO_ERROR, TEXTO, TITULO, ESTATUS, FECHA_CREACION_ALERTA, CONTACTO, TIPO_ALERTA) ")
	.append("VALUES (SYSDATE,?,?,?,?,TO_DATE(?, 'dd/MM/YYYY'),?,?)");

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
				historicoAlerta.getCodigoError().getClaveCodigo(),
				historicoAlerta.getAlerta().getTexto().toString(),
				historicoAlerta.getAlerta().getTitulo(),
				historicoAlerta.getEstatus(),
				historicoAlerta.getAlerta().getFechaCreacionAlerta(),
				historicoAlerta.getAlerta().getIdContacto(),
				historicoAlerta.getAlerta().getTipo()
		};
		
		
		con = dataSourceOracle.getConnection();
		jdbcTemplate = new JdbcTemplate(dataSourceOracle);
		jdbcTemplate.update(INSERT_HISTORICO_ALERTA.toString(), argumentos);
		con.close();
		con = null;

	}

	public void setDataSourceOracle(DataSource dataSourceOracle) {
        this.dataSourceOracle = dataSourceOracle;
    }

}
