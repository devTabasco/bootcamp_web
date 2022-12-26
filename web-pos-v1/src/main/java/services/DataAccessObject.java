package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataAccessObject {
	protected PreparedStatement ps;
	protected ResultSet rs;
	
	protected DataAccessObject() {

	}
	
	/* CONNECTION CREATION */
	protected Connection openConnect() {
		Connection connection = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.89:1521:xe", "posdba2", "1234");
		} catch (ClassNotFoundException e) {
			System.out.println("Error : OracleDriver None");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error : Can not Connect");
			e.printStackTrace();
		}

		return connection;
	}
	
	protected Connection openConnect(String userName, String password) {
		Connection connection = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.89:1521:xe", userName, password);
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
	protected void closeConnect(Connection connection) {
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
	protected void setTranStatus(Connection connection, boolean status) {
		try {
			if(connection != null && !connection.isClosed()) {
				connection.setAutoCommit(status);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected boolean setTransactionEnd(boolean tran, Connection connection) {
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
