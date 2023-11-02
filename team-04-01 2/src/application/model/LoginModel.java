package application.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dbUtil.DbConnection;

public class LoginModel {
	
	Connection connection;
	
	public LoginModel() {
		
		try {
			connection = DbConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (connection == null) {
			System.out.println("Connection was not successful.");
			System.exit(1);
		}
	}
		
	public boolean isDBConnected() {
		try {
			return !connection.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isLogin(String pass) throws Exception {
		
		PreparedStatement pr = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM user_info where password = ?";
		
		try {
			pr = this.connection.prepareStatement(sql);
			pr.setString(1, pass);
			
			rs = pr.executeQuery();
			
			if (rs.next()) {
				return true;
			}
			return false;
		}
		catch (SQLException ex) {
			return false;
		}
		
		finally {
			pr.close();
			rs.close();
		}
	}
	
	public void updatePassword(String newPassword){
		try {
			PreparedStatement statement = connection.prepareStatement("UPDATE user_info SET password = ? WHERE id = ?");
			statement.setString(1, newPassword);
			statement.setInt(2, 1);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
