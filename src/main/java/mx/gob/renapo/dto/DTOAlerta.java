package mx.gob.renapo.dto;

public class DTOAlerta {
	
	/**
	 * id del registro de la alerta
	 */
	private Long id;
	
	/**
	 * lista de contactos para la alerta
	 */
	private Long idContacto;
	
	/**
	 * texto de la alerta
	 */
	private StringBuilder texto;
	
	/**
	 * titulo de la alerta
	 */
	private String titulo;
	
	/**
	 * Atributo para el numero de intentos de envio a correo
	 */
	private Integer numeroIntentosCorreo;
	
	/**
	 * Atributo para el numero de intentos de envio a twitter
	 */
	private Integer numeroIntentosTwitter;
	
	/**
	 * Atributo para estatus de envio a correo
	 */
	private String estatusEnvioCorreo;
	
	/**
	 * Atributo para estatus de envio a twitter
	 */
	private String estatusEnvioTwitter;
	
	/**
	 * atributo para definir el tipo de alerta 
	 */
	private String tipo;
	
	/**
	 * atributo que define la fecha en que se creo la alerta
	 */
	private String fechaCreacionAlerta;
	
	/**
	 * Atributo con la informacion de correo electronico
	 */
	private String contactoCorreo;
	
	/**
	 * Atributo con la informacion de Twitter
	 */
	private String contactoTwitter;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdContacto() {
		return idContacto;
	}

	public void setIdContacto(Long idContacto) {
		this.idContacto = idContacto;
	}

	public StringBuilder getTexto() {
		return texto;
	}

	public void setTexto(StringBuilder texto) {
		this.texto = texto;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFechaCreacionAlerta() {
		return fechaCreacionAlerta;
	}

	public void setFechaCreacionAlerta(String fechaCreacionAlerta) {
		this.fechaCreacionAlerta = fechaCreacionAlerta;
	}

	public String getContactoCorreo() {
		return contactoCorreo;
	}

	public void setContactoCorreo(String contactoCorreo) {
		this.contactoCorreo = contactoCorreo;
	}

	public String getContactoTwitter() {
		return contactoTwitter;
	}

	public void setContactoTwitter(String contactoTwitter) {
		this.contactoTwitter = contactoTwitter;
	}

	public Integer getNumeroIntentosCorreo() {
		return numeroIntentosCorreo;
	}

	public void setNumeroIntentosCorreo(Integer numeroIntentosCorreo) {
		this.numeroIntentosCorreo = numeroIntentosCorreo;
	}

	public Integer getNumeroIntentosTwitter() {
		return numeroIntentosTwitter;
	}

	public void setNumeroIntentosTwitter(Integer numeroIntentosTwitter) {
		this.numeroIntentosTwitter = numeroIntentosTwitter;
	}

	public String getEstatusEnvioCorreo() {
		return estatusEnvioCorreo;
	}

	public void setEstatusEnvioCorreo(String estatusEnvioCorreo) {
		this.estatusEnvioCorreo = estatusEnvioCorreo;
	}

	public String getEstatusEnvioTwitter() {
		return estatusEnvioTwitter;
	}

	public void setEstatusEnvioTwitter(String estatusEnvioTwitter) {
		this.estatusEnvioTwitter = estatusEnvioTwitter;
	}
	
	

}
