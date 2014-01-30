package mx.gob.renapo.dto;

public class DTOAlerta {
	
	/**
	 * id del registro de la alerta
	 */
	private Long id;
	
	/**
	 * texto de la alerta
	 */
	private StringBuilder texto;
	
	/**
	 * titulo de la alerta
	 */
	private String titulo;
	
	/**
	 * atributo para definir el tipo de alerta 
	 */
	private String tipo;
	
	/**
	 * atributo que define la fecha en que se creo la alerta
	 */
	private String fechaCreacionAlerta;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	

}
