package mx.gob.renapo.util;

import java.text.SimpleDateFormat;

public class Utileria {
	
	/**
	 * Formato para fecha corta
	 */
	public static final SimpleDateFormat FECHA_CORTA = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * Constante para estatus de envio de alarma a Twitter
	 */
	public static final String CAMPO_ESTATUS_TWITTER = "estatus_envio_twitter";
	
	/**
	 * Constante para estatus de envio de alarma a correo
	 */
	public static final String CAMPO_ESTATUS_CORREO = "estatus_envio_correo";
	
	/**
	 * Constante para numero de intentos de envio de alarma a twitter
	 */
	public static final String CAMPO_NUMERO_INTENTOS_ENVIO_TWITTER = "numero_intentos_twitter";
	
	/**
	 * Constant para numero de intentos de envio de alarma a correo
	 */
	public static final String CAMPO_NUMERO_INTENTOS_ENVIO_CORREO = "numero_intentos_correo";
	
	/**
	 * Constante para numero maximo de intentos de envio de alertas
	 */
	public static final Integer NUMERO_MAXIMO_INTENTO_ENVIO = 5;
	
	public static Object recuperaDato(Object objeto) {
		return objeto == null ? " " : objeto; 
	}
	
}
