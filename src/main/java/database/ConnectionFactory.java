package database;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class ConnectionFactory{
	
	//bloco inicializador estático
	static {
		try{
			Class.forName ("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		}		
	}

	public static Connection obtemConexao () throws SQLException {
		Connection c = DriverManager.getConnection ("jdbc:mysql://localhost:3306/medico", "root", "123456");
		return c;		
	}
}