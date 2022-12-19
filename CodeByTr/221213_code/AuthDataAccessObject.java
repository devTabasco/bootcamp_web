package services.auth;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.GroupBean;
import beans.StoreBean;
import services.DataAccessObject;

public class AuthDataAccessObject extends DataAccessObject {
	AuthDataAccessObject(){}
	
	final Connection openConnection() {
		return this.openConnect();
	}
	final Connection openConnection(String userName, String password ) {
		return this.openConnect();
	}
	final void closeConnection(Connection connect) {
		this.closeConnect(connect);
	}
	final void modifyTranStatus(Connection connect, boolean tran) {
		this.setTranStatus(connect, tran);
	}
	final void setTransaction(boolean tran, Connection connect) {
		this.setTransactionEnd(tran, connect);
	}
	
	final int isStoreCode(Connection connect, StoreBean store) {
		int result = 0;
		String query = "SELECT COUNT(*) FROM ST WHERE ST_CODE = ?";
		
		try {
			this.ps = connect.prepareStatement(query);
			
		}catch(SQLException e) {e.printStackTrace();}
		
		return result;
	}
	
	final int isEmpCode(Connection connect, StoreBean store) {
		int result = 0;
		String query = "SELECT COUNT(*) FROM SE WHERE SE_STCODE = ? AND SE_CODE = ?";
		
		try {
			this.ps = connect.prepareStatement(query);
			
		}catch(SQLException e) {e.printStackTrace();}

		return result;
	}
	
	final int isEqualPinCode(Connection connect, StoreBean store) {
		int result = 0;
		String query = "SELECT COUNT(*) FROM SE WHERE (SE_STCODE = ? AND SE_CODE = ?) AND SE_PIN = ?";
		
		try {
			this.ps = connect.prepareStatement(query);
			
		}catch(SQLException e) {e.printStackTrace();}
		
		return result;
	}
	
	final int insAccessLog(Connection connect, StoreBean store) {
		int result = 0;
		String dml = "";
		try {
			this.ps = connect.prepareStatement(dml);
			
		}catch(SQLException e) {e.printStackTrace();}
		
		return result;
	}
	
	final GroupBean getEmpInfo(Connection connect, StoreBean store) {
		GroupBean group = null;
		String query = "";
		try {
			this.ps = connect.prepareStatement(query);
			
		}catch(SQLException e) {e.printStackTrace();}
		
		return null;
	}
	
	final void getStoreInfo(Connection connect, GroupBean group) {
		String query = "";
		
		try {
			this.ps = connect.prepareStatement(query);
			
		}catch(SQLException e) {e.printStackTrace();}
		
	}
}
