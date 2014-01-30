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
import mx.gob.renapo.dto.DTOAlertaContacto;
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
		List<DTOAlertaContacto> listaAlertas = new ArrayList<DTOAlertaContacto>();
		Integer alertasPendientes = 0;
		try {
			listaAlertas = alertaDAO.consultaAlerta(alerta);
			historicoAlertaDAO = (DAOHistoricoAlerta) context.getBean("historialAlertaDAO");
			for(DTOAlertaContacto alertaContactoDTO: listaAlertas) {
				historicoHistoricoAlertaDTO = envioAlertaCorreo(alertaContactoDTO);
				if(historicoHistoricoAlertaDTO.getCodigoError().getClaveCodigo()!=1) {
					if(!alertaContactoDTO.getContactoTwitter().equals("")) {
						if(alertaContactoDTO.getNumeroIntentosCorreo()
								<Utileria.NUMERO_MAXIMO_INTENTO_ENVIO) {
							historicoHistoricoAlertaDTO = envioAlertaTwitter(alertaContactoDTO);
							historicoAlertaDAO.guardaHistoricoAlerta(historicoHistoricoAlertaDTO);
						}
						else {
							this.borrarAlertaGuardarHistorico(alertaContactoDTO.getAlerta(), historicoHistoricoAlertaDTO);
						}
					}	
					else {
						this.borrarAlertaGuardarHistorico(alertaContactoDTO.getAlerta(), historicoHistoricoAlertaDTO);
					}
				}
				else if(historicoHistoricoAlertaDTO.getCodigoError().getClaveCodigo()==1) {
					this.borrarAlertaGuardarHistorico(alertaContactoDTO.getAlerta(), historicoHistoricoAlertaDTO);
				}
			}
			
			alertasPendientes = alertaDAO.consultaNumeroAlertasPendientesPorEnviar
					(listaAlertas.get(0).getAlerta().getId());
			if(alertasPendientes==0){
				alertaDAO.borrarAlerta(listaAlertas.get(0).getAlerta());
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
	public DTOHistoricoAlerta envioAlertaCorreo(DTOAlertaContacto alertaContactoDTO)  {
		SimpleMailMessage mensaje = new SimpleMailMessage();
		mensaje.setTo(alertaContactoDTO.getContactoCorreo());
		mensaje.setText(alertaContactoDTO.getAlerta().getTexto().toString());
		mensaje.setSubject(alertaContactoDTO.getAlerta().getTitulo());
		DTOCodigoErrorAlerta codigoErrorAlerta = new DTOCodigoErrorAlerta();
		DTOHistoricoAlerta historicoAlertaDTO = new DTOHistoricoAlerta();
		historicoAlertaDTO.setAlertaContacto(alertaContactoDTO);
		historicoAlertaDTO.setFechaEnvio("2014-01-14");
		try {
			mailSender.send(mensaje);
			codigoErrorAlerta.setClaveCodigo(1);
			historicoAlertaDTO.setCodigoError(codigoErrorAlerta);
			historicoAlertaDTO.setEstatus("Enviado");
			
		}catch(MailException e) {
			alertaDAO = (DAOAlerta) context.getBean("alertaDAO");
			alertaContactoDTO.setNumeroIntentosCorreo(alertaContactoDTO.getNumeroIntentosCorreo()+1);
			alertaContactoDTO.setEstatusEnvioCorreo("Error al enviar correo");
			codigoErrorAlerta.setClaveCodigo(2);
			historicoAlertaDTO.setCodigoError(codigoErrorAlerta);
			historicoAlertaDTO.setAlertaContacto(alertaContactoDTO);
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
		alertaDAO.borrarAlertaContacto(alerta.getId(), historicoAlerta.getAlertaContacto().getIdContacto());
		historicoAlertaDAO.guardaHistoricoAlerta(historicoAlerta);	
	}
	
	/**
	 * Metodo para enviar medianto Twitter la alerta recuperada
	 * @param alertaDTO argumento con la informacion de la alerta
	 * @return DTOHistoricoAlerta con los resultados del envio
	 * @throws Exception
	 */
		
	public DTOHistoricoAlerta envioAlertaTwitter(DTOAlertaContacto alertaContactoDTO) {
		 String mensaje = alertaContactoDTO.getContactoTwitter() + " "  + alertaContactoDTO.getAlerta().getTexto().toString();
		 DTOCodigoErrorAlerta codigoErrorAlerta = new DTOCodigoErrorAlerta();
			DTOHistoricoAlerta historicoAlertaDTO = new DTOHistoricoAlerta();
			historicoAlertaDTO.setAlertaContacto(alertaContactoDTO);
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
