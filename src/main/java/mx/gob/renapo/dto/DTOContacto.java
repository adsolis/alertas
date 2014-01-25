package mx.gob.renapo.dto;

public class DTOContacto {
	
	private Long id;
	
	private String nombre;
	
	private String apellidoPaterno;
	
	private String apellidoMaterno;
	
	private String correo;
	
	private String twitter;
	
	private Integer estadoOrigen;
	
	/**
	 * atributo para definir el tipo de alerta 
	 */
	private String tipo;
	
	/**
	 *nivel de la alerta
	 */
	private Integer nivel;
	
	/**
	 * codigo de la alerta
	 */
	private String codigoAlerta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public Integer getEstadoOrigen() {
		return estadoOrigen;
	}

	public void setEstadoOrigen(Integer estadoOrigen) {
		this.estadoOrigen = estadoOrigen;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public String getCodigoAlerta() {
		return codigoAlerta;
	}

	public void setCodigoAlerta(String codigoAlerta) {
		this.codigoAlerta = codigoAlerta;
	}
	
	

}
