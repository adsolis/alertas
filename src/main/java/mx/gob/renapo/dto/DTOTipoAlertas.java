package mx.gob.renapo.dto;

public class DTOTipoAlertas {
	
	/**
	 * Atributo con el id del tipo de alerta
	 */
	private Long id;
	
	/**
	 * Atributo con el codigo de tipo de alerta
	 */
	private String codigo;
	
	/**
	 * Atributo con el tipo de alerta
	 */
	private String tipo;
	
	/**
	 * Atributo con el nivel de la alerta
	 */
	private String nivel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	
	

}
