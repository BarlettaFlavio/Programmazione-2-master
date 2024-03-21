package datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Questa classe funge da connessione per il nostro database, ognuna di queste costanti stringhe dichiarate contiene un campo
 * di login per il nostro db
 * @author Camillo
 *
 */
public class DataSource {
	//COSTANTI
	private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private final static String USERNAME = "user=sql7390732&";
	private final static String PWD = "password=8i3ANwkvAh";
	private final static String DB_URL = "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7390732?";
	
	/**
	 * Questo metodo effettua la connesione al database
	 * @return
	 */
	public static Connection connection() {
		try {
			Class.forName(JDBC_DRIVER);
		}catch(ClassNotFoundException e){
			System.out.println("ClassNotFoundException :");
			System.out.println(e.getMessage());
		}
		//creo la connessione al db
		try {
			return DriverManager.getConnection(DB_URL + USERNAME + PWD);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
