package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataAccessObject {
	protected PreparedStatement ps;
	protected ResultSet rs;
	
	public DataAccessObject() {

	}
	
	/* CONNECTION CREATION */
	public Connection openConnection() {
		Connection connection = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.8:1521:xe", "WEBDEV", "8520");
		} catch (ClassNotFoundException e) {
			System.out.println("Error : OracleDriver None");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error : Can not Connect");
			e.printStackTrace();
		}

		return connection;
	}
	
	public Connection openConnection(String userName, String password) {
		Connection connection = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.8:1521:xe", userName, password);
		} catch (ClassNotFoundException e) {
			System.out.println("Error : OracleDriver None");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error : Can not Connect");
			e.printStackTrace();
		}

		return connection;
	}
	/* CONNECTION Close */
	public void closeConnection(Connection connection) {
		try {
			if(connection != null && !connection.isClosed()) {
				if(this.rs != null && !this.rs.isClosed()) this.rs.close();
				if(this.ps !=null && !this.ps.isClosed()) this.ps.close();
				connection.close();
			} 	
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* Transaction Mgr */
	public void modifyTranStatus(Connection connection, boolean status) {
		try {
			if(connection != null && !connection.isClosed()) {
				connection.setAutoCommit(status);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean setTransaction(boolean tran, Connection connection) {
		boolean result = false;
		try {
			if(tran) {
				connection.commit();
				result = true;
			}else connection.rollback();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
