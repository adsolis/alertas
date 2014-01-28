package mx.gob.renapo.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import mx.gob.renapo.dao.DAOAlerta;
import mx.gob.renapo.dao.DAOContacto;
import mx.gob.renapo.dao.DAOHistoricoAlerta;
import mx.gob.renapo.dto.DTOAlerta;
import mx.gob.renapo.dto.DTOCodigoErrorAlerta;
import mx.gob.renapo.dto.DTOHistoricoAlerta;

@Service("mailService")
public class EnvioAlertaService {
	
	@Autowired
    private JavaMailSender mailSender;
	private DAOAlerta alertaDAO;
	private DAOContacto contactoDAO;
	private DAOHistoricoAlerta historicoAlertaDAO;
	private static String OAUTH_CONSUMER_KEY = "rj2oX4NmL6Hezm9y7M3N7Q";
	private static String OAUTH_CONSUMER_SECRET = "QRLErDFD4CWF4IzLigBQpohyYzLqiyor0qGCy8QniY";
	ConfigurableApplicationContext context = null;
	
	/**
	 * Metodo que consulta las alertas disponibles
	 * @return DTOHistoricoAlerta
	 */
	public DTOHistoricoAlerta consultaAlertas() {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		List<Object> criteriosContactos = new ArrayList<Object>();
		DTOAlerta alertaDTO = new DTOAlerta();
		DTOHistoricoAlerta historicoHistoricoAlertaDTO = null;
		alertaDTO.setTipo("1");
		criteriosContactos.add(alertaDTO.getTipo());
		alertaDAO = (DAOAlerta) context.getBean("alertaDAO");

		try {
			alertaDTO = alertaDAO.consultaAlerta(alertaDTO);
			historicoAlertaDAO = (DAOHistoricoAlerta) context.getBean("historialAlertaDAO");	
			historicoHistoricoAlertaDTO = envioAlertaCorreo(alertaDTO);
			historicoAlertaDAO.guardaHistoricoAlerta(historicoHistoricoAlertaDTO);
			if(historicoHistoricoAlertaDTO.getCodigoError().getClaveCodigo()!=1
					&&
				!alertaDTO.getContactoTwitter().equals("")) {
				historicoHistoricoAlertaDTO = envioAlertaTwitter(alertaDTO);
				historicoAlertaDAO.guardaHistoricoAlerta(historicoHistoricoAlertaDTO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public DTOHistoricoAlerta envioAlertaCorreo(DTOAlerta alertaDTO)  {
		SimpleMailMessage mensaje = new SimpleMailMessage();
		mensaje.setTo(alertaDTO.getContactoCorreo());
		mensaje.setText(alertaDTO.getTexto().toString());
		mensaje.setSubject(alertaDTO.getTitulo());
		DTOCodigoErrorAlerta codigoErrorAlerta = new DTOCodigoErrorAlerta();
		DTOHistoricoAlerta historicoAlertaDTO = new DTOHistoricoAlerta();
		historicoAlertaDTO.setAlerta(alertaDTO);
		historicoAlertaDTO.setFechaEnvio("2014-01-14");
		try {
			mailSender.send(mensaje);
			codigoErrorAlerta.setClaveCodigo(1);
			historicoAlertaDTO.setCodigoError(codigoErrorAlerta);
			historicoAlertaDTO.setEstatus("Enviado");
			
		}catch(MailException e) {
			alertaDAO = (DAOAlerta) context.getBean("alertaDAO");
			alertaDTO.setNumeroIntentosCorreo(alertaDTO.getNumeroIntentosCorreo()+1);
			alertaDTO.setEstatusEnvioCorreo("Error al enviar correo");
			codigoErrorAlerta.setClaveCodigo(2);
			historicoAlertaDTO.setCodigoError(codigoErrorAlerta);
			historicoAlertaDTO.setAlerta(alertaDTO);
			historicoAlertaDTO.setEstatus("Error al enviar correo");
		}
		
		return historicoAlertaDTO;
	}
	
	/**
	 * Metodo para enviar medianto Twitter la alerta recuperada
	 * @param alertaDTO argumento con la informacion de la alerta
	 * @return DTOHistoricoAlerta con los resultados del envio
	 * @throws Exception
	 */
		
	public DTOHistoricoAlerta envioAlertaTwitter(DTOAlerta alertaDTO) {
		String accessToken = "170577565-BSzEOvxiFpFOd1GUacN9f4z6qPft6BGbg62yC7bw";
		String accessTokenSecret = "Pjrdev033veEVCGVluAiiOgj5gAwtOsNCSmsOpeVlmisO";
		ConfigurationBuilder conf = new ConfigurationBuilder()
		.setDebugEnabled(true)
		.setOAuthConsumerKey(OAUTH_CONSUMER_KEY)
		.setOAuthConsumerSecret(OAUTH_CONSUMER_SECRET)
		.setOAuthAccessToken(accessToken)
		.setOAuthAccessTokenSecret(accessTokenSecret);
		 String mensaje = alertaDTO.getContactoTwitter() + " "  + alertaDTO.getTexto().toString();
		 DTOCodigoErrorAlerta codigoErrorAlerta = new DTOCodigoErrorAlerta();
			DTOHistoricoAlerta historicoAlertaDTO = new DTOHistoricoAlerta();
			historicoAlertaDTO.setAlerta(alertaDTO);
			historicoAlertaDTO.setFechaEnvio("2014-01-14");
	        Twitter twitter = new TwitterFactory(conf.build()).getInstance();
	        
	        try {
	            Status status = twitter.updateStatus(mensaje);
	            codigoErrorAlerta.setClaveCodigo(1);
				historicoAlertaDTO.setCodigoError(codigoErrorAlerta);
				historicoAlertaDTO.setEstatus("Enviado");
	        } catch (TwitterException e) {
	            e.printStackTrace();
	            codigoErrorAlerta.setClaveCodigo(2);
				historicoAlertaDTO.setCodigoError(codigoErrorAlerta);
				historicoAlertaDTO.setEstatus("Fallo");
	        }
		
		return historicoAlertaDTO;
	}

	public DAOAlerta getAlertaDAO() {
		return alertaDAO;
	}

	public void setAlertaDAO(DAOAlerta alertaDAO) {
		this.alertaDAO = alertaDAO;
	}

	public DAOContacto getContactoDAO() {
		return contactoDAO;
	}

	public void setContactoDAO(DAOContacto contactoDAO) {
		this.contactoDAO = contactoDAO;
	}

	public DAOHistoricoAlerta getHistoricoAlertaDAO() {
		return historicoAlertaDAO;
	}

	public void setHistoricoAlertaDAO(DAOHistoricoAlerta historicoAlertaDAO) {
		this.historicoAlertaDAO = historicoAlertaDAO;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

}
