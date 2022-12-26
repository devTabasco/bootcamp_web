package services.goods;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.CategoriesBean;
import beans.StoreBean;
import services.DataAccessObject;

public class GoodsDataAccessObject extends DataAccessObject{
	
	public GoodsDataAccessObject(){}
	
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
	
	ArrayList<CategoriesBean> getCatagoryList(Connection connect, StoreBean clientData){
		ArrayList<CategoriesBean> categoryList = null;
		CategoriesBean category = null;
		
		String query = "SELECT SC_CODE AS LEVCODE, SC_NAME AS LEVNAME "
				+ "		FROM WEBDBA.SC SC "
				+ "		WHERE SC_STCODE = ? "
				+ "		  AND SUBSTR(SC_CODE, 1, 1) = ?";
		
		try {
			this.ps = connect.prepareStatement(query);
			this.ps.setNString(1, clientData.getStoreCode());
			this.ps.setNString(2, clientData.getCateList().get(0).getLevCode());
			
			this.rs = this.ps.executeQuery();
			if(this.rs.isBeforeFirst()) {
				categoryList = new ArrayList<CategoriesBean>();
				while(this.rs.next()) {
					category = new CategoriesBean();
					category.setLevCode(this.rs.getNString("LEVCODE"));
					category.setLevName(this.rs.getNString("LEVNAME"));
					categoryList.add(category);
				}
			}
		}catch(SQLException e) {e.printStackTrace();}
		
		return categoryList;
	}
	
	String getMaxCode(Connection connect, StoreBean clientData) {
		String maxIdx = null;
		String query = "SELECT NVL(MAX(SUBSTR(SC_CODE, 2, 1)), '-1') AS MAXIDX "
				+ "		FROM WEBDBA.SC "
				+ "		WHERE SC_STCODE = ? AND SUBSTR(SC_CODE, 1,1) = ?";
		try{
			this.ps = connect.prepareStatement(query);
			this.ps.setNString(1, clientData.getStoreCode());
			this.ps.setNString(2, clientData.getCateList().get(0).getLevCode());
			
			this.rs = this.ps.executeQuery();
			if(this.rs.isBeforeFirst()) {
				while(this.rs.next()) {
					maxIdx = clientData.getCateList().get(0).getLevCode() + (Integer.parseInt(this.rs.getNString("MAXIDX")) + 1);
				}
			}
			
		}catch(SQLException e) {e.printStackTrace();}
		
		return maxIdx;
	}
	
	int insCategoryItem(Connection connect, StoreBean clientData) {
		int result = 0;
		String dml = "INSERT INTO WEBDBA.SC(SC_STCODE, SC_CODE, SC_NAME) "
				+ "VALUES ( ?, ?, ?)";
		
		try {
			this.ps = connect.prepareStatement(dml);
			this.ps.setNString(1, clientData.getStoreCode());
			this.ps.setNString(2, clientData.getCateList().get(0).getLevCode());
			this.ps.setNString(3, clientData.getCateList().get(0).getLevName());
			
			result = this.ps.executeUpdate();
		}catch(SQLException e) {e.printStackTrace();}
		
		return result;
	}
	
	int updCategoryItem(Connection connect, StoreBean clientData) {
		int result = 0;
		String dml = "UPDATE WEBDBA.SC "
				+ "      SET SC_NAME = ? "
				+ "   WHERE SC_STCODE = ? AMD SC_CODE = ?";
		
		try {
			this.ps = connect.prepareStatement(dml);
			this.ps = connect.prepareStatement(dml);
			this.ps.setNString(2, clientData.getStoreCode());
			this.ps.setNString(3, clientData.getCateList().get(0).getLevCode());
			this.ps.setNString(1, clientData.getCateList().get(0).getLevName());
			
			result = this.ps.executeUpdate();
		}catch(SQLException e) {e.printStackTrace();}
		
		return result;
	}
}
