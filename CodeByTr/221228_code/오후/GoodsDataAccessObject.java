package services.goods;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.CategoriesBean;
import beans.GoodsBean;
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
	
	void getGoodsList(Connection connect, StoreBean clientData){
		ArrayList<GoodsBean> goodsList = null;
		GoodsBean goods = null;
		
		String query = "SELECT SM_CODE AS GOODSCODE, "
				+ "			   SM_SCCAT AS GOODSCATEGORYCODE,"
				+ "			   SM_NAME AS GOODSNAME, "
				+ "		       SM_SCSTATE AS GOODSSTATECODE, "
				+ "			   SM_COLOR AS GOODSCOLORCODE, "
				+ "			   SM_SICODE AS GOODSIMAGECODE "
				+ "		FROM WEBDBA.SM "
				+ "		WHERE SM_STCODE = ? ";
		
		try {
			this.ps = connect.prepareStatement(query);
			this.ps.setNString(1, clientData.getStoreCode());
						
			this.rs = this.ps.executeQuery();
			if(this.rs.isBeforeFirst()) {
				goodsList = new ArrayList<GoodsBean>();
				while(this.rs.next()) {
					goods = new GoodsBean();
					goods.setGoodsCode(this.rs.getNString("GOODSCODE"));
					goods.setGoodsCategoryCode(this.rs.getNString("GOODSCATEGORYCODE"));
					goods.setGoodsName(this.rs.getNString("GOODSNAME"));
					goods.setGoodsStateCode(this.rs.getNString("GOODSSTATECODE"));
					goods.setGoodsColorCode(this.rs.getNString("GOODSCOLORCODE"));
					goods.setGoodsImageCode(this.rs.getNString("GOODSIMAGECODE"));
					goodsList.add(goods);
				}
				clientData.setGoodsList(goodsList);
			}
		}catch(SQLException e) {e.printStackTrace();}
	}
	
	void getGoodsCategoryList(Connection connect, StoreBean store) {
		ArrayList<CategoriesBean> categoryList = null;
		CategoriesBean category = null;
		
		String query = "SELECT SC_CODE AS LEVCODE, SC_NAME AS LEVNAME "
				+ "		FROM WEBDBA.SC SC "
				+ "		WHERE SC_STCODE = ? ";
		
		try {
			this.ps = connect.prepareStatement(query);
			this.ps.setNString(1, store.getStoreCode());
						
			this.rs = this.ps.executeQuery();
			if(this.rs.isBeforeFirst()) {
				categoryList = new ArrayList<CategoriesBean>();
				while(this.rs.next()) {
					category = new CategoriesBean();
					category.setLevCode(this.rs.getNString("LEVCODE"));
					category.setLevName(this.rs.getNString("LEVNAME"));
					categoryList.add(category);
				}
				store.setCateList(categoryList);
			}
		}catch(SQLException e) {e.printStackTrace();}
	}
				
	ArrayList<CategoriesBean> getCategoryList(Connection connect, StoreBean clientData){
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
				+ "   WHERE SC_STCODE = ? AND SC_CODE = ?";
		System.out.println(clientData.getStoreCode());
		System.out.println(clientData.getCateList().get(0).getLevCode());
		System.out.println(clientData.getCateList().get(0).getLevName());
		try {
			this.ps = connect.prepareStatement(dml);
			this.ps.setNString(2, clientData.getStoreCode());
			this.ps.setNString(3, clientData.getCateList().get(0).getLevCode());
			this.ps.setNString(1, clientData.getCateList().get(0).getLevName());
			
			result = this.ps.executeUpdate();
		}catch(SQLException e) {e.printStackTrace();}
		
		return result;
	}
}
