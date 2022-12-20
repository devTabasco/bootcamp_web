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
				+ "		FROM WEBDBA.SC "
				+ "		WHERE SC_STCODE = ? "
				+ "		  AND SUBSTR(SC_CODE, 1,1) = ?";
		
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
}
