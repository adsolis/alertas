package mx.gob.renapo.main;

import mx.gob.renapo.service.EnvioAlertaService;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PruebaEnvioAlertas{

	/**
	 * @param args
	 */
	
	/*public static void main(String[] args){
		String mensaje = "@_benek twitter4j test";
        Twitter twitter = TwitterFactory.getSingleton();
        try {
            Status status = twitter.updateStatus(mensaje);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
	}
	*/
	/*public static void main(String[] args) {
		try {
			// Creacion de una instacia de Scheduler
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler(); 
			System.out.println("Iniciando Scheduler...");
			scheduler.start(); 
			// Creacion una instacia de JobDetail
			JobDetail jobDetail = new JobDetail( 
			"Preparando ", 
			Scheduler.DEFAULT_GROUP,
			PruebaEnvioAlertas.class);

			// Creacion de un Trigger donde indicamos 
			//que el Job se 
			// ejecutara de inmediato y a partir de ahi en lapsos
			// de 5 segundos por 10 veces mas. 
			Trigger trigger = new SimpleTrigger( 
			"HolaMundoTrigger", 
			Scheduler.DEFAULT_GROUP, 
			3, 30000);

			// Registro dentro del Scheduler
			scheduler.scheduleJob(jobDetail, trigger);

			// Damos tiempo a que el Trigger registrado 
			//termine su periodo 
			// de vida dentro del scheduler
			Thread.sleep(60000);

			// Detenemos la ejecución de la 
			// instancia de Scheduler 
			scheduler.shutdown();

			} catch(Exception e) {
			System.out.println("Ocurrió una excepción");
			}

	}*/

	public static void main(String[] args) {
		
		//Create the application context
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
         
        EnvioAlertaService mailer = (EnvioAlertaService) context.getBean("mailService");
        
        mailer.consultaAlertas();
		
	}

}
