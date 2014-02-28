package mx.gob.renapo.dto;

public class DTOAlertaContacto {
	
	/**
	 * id de la alerta
	 */
	private DTOAlerta alerta;
	
	/**
	 * id del contacto para la alerta
	 */
	private Long idContacto;
	
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
	 * Atributo con la informacion de correo electronico
	 */
	private String contactoCorreo;
	
	/**
	 * Atributo con la informacion de Twitter
	 */
	private String contactoTwitter;
	
	
	
	public DTOAlerta getAlerta() {
		return alerta;
	}

	public void setAlerta(DTOAlerta alerta) {
		this.alerta = alerta;
	}

	public Long getIdContacto() {
		return idContacto;
	}

	public void setIdContacto(Long idContacto) {
		this.idContacto = idContacto;
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
