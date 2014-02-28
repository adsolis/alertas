package mx.gob.renapo.dao.implement;

import java.sql.SQLException;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import mx.gob.renapo.dao.DAOConfiguracionEnvioAlerta;
import mx.gob.renapo.dto.DTOConfiguracionEnvioAlerta;

public class DAOConfiguracionEnvioAlertaImpl implements DAOConfiguracionEnvioAlerta{
	
	private static final String QUERY_CONSULTA_CONFIGURACION_ENVIO_ALERTA = 
			"SELECT * FROM CONFIGURACION_ENVIO_ALERTA";

	private DataSource dataSourceOracle;
	private JdbcTemplate jdbcTemplate;
	
	
	public DTOConfiguracionEnvioAlerta consultaConfiguracionEnvioAlerta()
			throws DataAccessException, SQLException {
		
		jdbcTemplate = new JdbcTemplate(dataSourceOracle);
		jdbcTemplate.queryForMap(QUERY_CONSULTA_CONFIGURACION_ENVIO_ALERTA.toString());
		
		return 
		this.mapeaConfiguracionAlerta(jdbcTemplate.queryForMap(QUERY_CONSULTA_CONFIGURACION_ENVIO_ALERTA.toString()));
	}
	
	private DTOConfiguracionEnvioAlerta mapeaConfiguracionAlerta(Map<String, Object> linea) {
		DTOConfiguracionEnvioAlerta configuracionAlerta = new DTOConfiguracionEnvioAlerta();
		
		configuracionAlerta.setTipo((linea.get("tipo_alerta").toString()));
		configuracionAlerta.setHoraFinal(linea.get("fin").toString());
		configuracionAlerta.setHoraInicio(linea.get("inicio").toString());
		configuracionAlerta.setRangoConsulta(Integer.valueOf(linea.get("rango_consulta").toString()));
		
		return configuracionAlerta;
	}
	
	public void setDataSourceOracle(DataSource dataSourceOracle) {
        this.dataSourceOracle = dataSourceOracle;
    }

}
