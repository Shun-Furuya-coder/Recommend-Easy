package dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	
	public static Connection getConnection() throws SQLException{
		
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:faculty.sqlite");
			return conn;
		}
		catch (ClassNotFoundException e) {
			System.out.println(e);
			return null;
		}
	}

}
