package mx.gob.renapo.dto;

import java.util.Date;

public class DTOHistoricoAlerta {
	
	private Long id;
	
	private String fechaEnvio;
	
	private DTOAlertaContacto alertaContacto;
	
	private DTOCodigoErrorAlerta codigoError;
	
	private StringBuilder texto;
	
	private String titulo;
	
	private String estatus;
	
	private Long tipoAlerta;	
	
	private Date fechaCreacionAlerta;
	
	private DTOContacto contacto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(String fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public DTOAlertaContacto getAlertaContacto() {
		return alertaContacto;
	}

	public void setAlertaContacto(DTOAlertaContacto alertaContacto) {
		this.alertaContacto = alertaContacto;
	}

	public DTOCodigoErrorAlerta getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(DTOCodigoErrorAlerta codigoError) {
		this.codigoError = codigoError;
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

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Long getTipoAlerta() {
		return tipoAlerta;
	}

	public void setTipoAlerta(Long tipoAlerta) {
		this.tipoAlerta = tipoAlerta;
	}

	public Date getFechaCreacionAlerta() {
		return fechaCreacionAlerta;
	}

	public void setFechaCreacionAlerta(Date fechaCreacionAlerta) {
		this.fechaCreacionAlerta = fechaCreacionAlerta;
	}

	public DTOContacto getContacto() {
		return contacto;
	}

	public void setContacto(DTOContacto contacto) {
		this.contacto = contacto;
	}
	
	

}
