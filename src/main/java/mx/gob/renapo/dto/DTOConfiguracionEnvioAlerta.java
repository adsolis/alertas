package mx.gob.renapo.dto;

public class DTOConfiguracionEnvioAlerta {
	
	private Long id;
	
	private String tipo;
	
	private int rangoConsulta;
	
	private String horaInicio;
	
	private String horaFinal;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getRangoConsulta() {
		return rangoConsulta;
	}

	public void setRangoConsulta(int rangoConsulta) {
		this.rangoConsulta = rangoConsulta;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
	}
	
	

}
