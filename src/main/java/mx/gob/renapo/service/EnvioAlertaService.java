package mx.gob.renapo.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import mx.gob.renapo.dao.DAOAlerta;
import mx.gob.renapo.dao.DAOContacto;
import mx.gob.renapo.dao.DAOHistoricoAlerta;
import mx.gob.renapo.dto.DTOAlerta;
import mx.gob.renapo.dto.DTOCodigoErrorAlerta;
import mx.gob.renapo.dto.DTOHistoricoAlerta;
import mx.gob.renapo.util.Utileria;

@Service("mailService")
public class EnvioAlertaService {
	
	@Autowired
    private JavaMailSender mailSender;
	private DAOAlerta alertaDAO;
	private DAOContacto contactoDAO;
	private DAOHistoricoAlerta historicoAlertaDAO;
	ConfigurableApplicationContext context = null;
	
	/**
	 * Metodo que consulta las alertas disponibles
	 * @return DTOHistoricoAlerta
	 */
	public DTOHistoricoAlerta consultaAlertas() {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		List<Object> criteriosContactos = new ArrayList<Object>();
		DTOAlerta alerta = new DTOAlerta();
		DTOHistoricoAlerta historicoHistoricoAlertaDTO = null;
		alerta.setTipo("1");
		criteriosContactos.add(alerta.getTipo());
		alertaDAO = (DAOAlerta) context.getBean("alertaDAO");
		List<DTOAlerta> listaAlertas = new ArrayList<DTOAlerta>();
		try {
			listaAlertas = alertaDAO.consultaAlerta(alerta);
			historicoAlertaDAO = (DAOHistoricoAlerta) context.getBean("historialAlertaDAO");
			for(DTOAlerta alertaDTO: listaAlertas) {
				historicoHistoricoAlertaDTO = envioAlertaCorreo(alertaDTO);
				if(historicoHistoricoAlertaDTO.getCodigoError().getClaveCodigo()!=1) {
					if(!historicoHistoricoAlertaDTO.getAlerta().getContactoTwitter().equals("")) {
						if(historicoHistoricoAlertaDTO.getAlerta().getNumeroIntentosCorreo()
								<Utileria.NUMERO_MAXIMO_INTENTO_ENVIO) {
							historicoHistoricoAlertaDTO = envioAlertaTwitter(alertaDTO);
							historicoAlertaDAO.guardaHistoricoAlerta(historicoHistoricoAlertaDTO);
						}
						else {
							this.borrarAlertaGuardarHistorico(alertaDTO, historicoHistoricoAlertaDTO);
						}
					}	
					else {
						this.borrarAlertaGuardarHistorico(alertaDTO, historicoHistoricoAlertaDTO);
					}
				}
				else if(historicoHistoricoAlertaDTO.getCodigoError().getClaveCodigo()==1) {
					this.borrarAlertaGuardarHistorico(alertaDTO, historicoHistoricoAlertaDTO);
				}
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}	
		return null;
	}
	
	/**
	 * Metodo para realizar el envio de la alerta por correo
	 * @param alertaDTO
	 * @return
	 */
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
	 * metodo para borrar alerta
	 * @throws SQLException 
	 * @throws DataAccessException 
	 */
	public void borrarAlertaGuardarHistorico(DTOAlerta alerta, 
			DTOHistoricoAlerta historicoAlerta) throws DataAccessException, SQLException {
		alertaDAO.borrarAlerta(alerta);
		historicoAlertaDAO.guardaHistoricoAlerta(historicoAlerta);	
	}
	
	/**
	 * Metodo para enviar medianto Twitter la alerta recuperada
	 * @param alertaDTO argumento con la informacion de la alerta
	 * @return DTOHistoricoAlerta con los resultados del envio
	 * @throws Exception
	 */
		
	public DTOHistoricoAlerta envioAlertaTwitter(DTOAlerta alertaDTO) {
		 String mensaje = alertaDTO.getContactoTwitter() + " "  + alertaDTO.getTexto().toString();
		 DTOCodigoErrorAlerta codigoErrorAlerta = new DTOCodigoErrorAlerta();
			DTOHistoricoAlerta historicoAlertaDTO = new DTOHistoricoAlerta();
			historicoAlertaDTO.setAlerta(alertaDTO);
			historicoAlertaDTO.setFechaEnvio("2014-01-14");
	        Twitter twitter = TwitterFactory.getSingleton();
	        
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
