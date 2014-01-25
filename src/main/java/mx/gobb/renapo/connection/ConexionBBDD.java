package mx.gobb.renapo.connection;

import java.sql.Connection;
import java.sql.DriverManager;



public class ConexionBBDD {
	
	private Connection conexion;
	static String bd="pruebasalertas";
	static String user="root";
	static String password="root";
	static String server="jdbc:mysql://localhost/"+bd;
	
	public void establecerConexion(){
		try{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conexion = DriverManager.getConnection(server,user,password);
		}
		catch(Exception e){
		System.out.println("Imposible realizar conexion con la BD");
		e.printStackTrace();
		}
		}
		 
		public Connection getConexion(){
		return conexion;
		}

}
