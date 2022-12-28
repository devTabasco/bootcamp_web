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
	
	ArrayList<CategoriesBean> getGoodsCategoryList(Connection connect, StoreBean store) {
		ArrayList<CategoriesBean> categoryList = null;
		CategoriesBean category = null;
		
		String query = "SELECT SC_CODE AS LEVCODE, SC_NAME AS LEVNAME "
				+ "		FROM SC "
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
		
		return categoryList;
	}
	
	ArrayList<GoodsBean> getGoodsList(Connection connect, StoreBean clientData){
		ArrayList<GoodsBean> goodsList = null;
		GoodsBean goods = null;
		
		String query = "SELECT SM_CODE AS GOODSCODE, "
				+ "			   SM_SCC AS GOODSCATEGORYCODE,"
				+ "			   SM_NAME AS GOODSNAME, "
				+ "		       SM_SCS AS GOODSSTATECODE, "
				+ "			   SM_COLOR AS GOODSCOLORCODE, "
				+ "			   SM_SICODE AS GOODSIMAGECODE "
				+ "		FROM SM "
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
		
		return goodsList;
	}
	
	//상품코드 가져오기
	String getStoreMenuCode(Connection connect, StoreBean clientData){
		String query = "SELECT COUNT(SM_CODE) AS cntMenu"
				+ "		FROM SM "
				+ "		WHERE SM_STCODE = ? ";
		
		String menuCode = null;
		
		try {
			this.ps = connect.prepareStatement(query);
			this.ps.setNString(1, clientData.getStoreCode());
			
			this.rs = this.ps.executeQuery();
			if(this.rs.isBeforeFirst()) {
				while(this.rs.next()) {
					menuCode = "00" + this.rs.getInt("cntMenu");
				}
			}
		}catch(SQLException e) {e.printStackTrace();}
		
		return menuCode;
	}
	
	ArrayList<CategoriesBean> getCatagoryList(Connection connect, StoreBean clientData){
		ArrayList<CategoriesBean> categoryList = null;
		CategoriesBean category = null;
		
		String query = "SELECT SC_CODE AS LEVCODE, SC_NAME AS LEVNAME "
				+ "		FROM SC "
				+ "		WHERE SC_STCODE = ? "
				+ "		  AND SUBSTR(SC_CODE, 1, 1) = ?";
		
		String levCode = clientData.getCateList().get(0).getLevCode();
		
		try {
			this.ps = connect.prepareStatement(query);
			this.ps.setNString(1, clientData.getStoreCode());
			this.ps.setNString(2, (levCode.length()>1)?levCode.substring(0,1):levCode);
			
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
	
	int getMaxCode(Connection connect, StoreBean clientData) {
		int maxIdx = 0;
		String query = "SELECT NVL(SUBSTR(MAX(SC_CODE), 2, 1), '-1') AS MAXIDX "
				+ "		FROM SC "
				+ "		WHERE SC_STCODE = ? AND SUBSTR(SC_CODE, 1,1) = ?";
		try{
			this.ps = connect.prepareStatement(query);
			this.ps.setNString(1, clientData.getStoreCode());
			this.ps.setNString(2, clientData.getCateList().get(0).getLevCode());
			
			this.rs = this.ps.executeQuery();
			if(this.rs.isBeforeFirst()) {
				while(this.rs.next()) {
					maxIdx = Integer.parseInt(this.rs.getNString("MAXIDX")) + 1;
				}
			}
			
		}catch(SQLException e) {e.printStackTrace();}
		
		return maxIdx;
	}
	
	int insCategoryItem(Connection connect, StoreBean clientData) {
		int result = 0;
		String dml = "INSERT INTO SC(SC_STCODE, SC_CODE, SC_NAME) "
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
		String dml = "UPDATE SC SET SC_NAME = ? WHERE SC_STCODE = ? AND SC_CODE = ?";
		
		try {
			this.ps = connect.prepareStatement(dml);
			this.ps.setNString(1, clientData.getCateList().get(0).getLevName());
			this.ps.setNString(2, clientData.getStoreCode());
			this.ps.setNString(3, clientData.getCateList().get(0).getLevCode());
			
			result = this.ps.executeUpdate();
		}catch(SQLException e) {e.printStackTrace();}
		
		return result;
	}
}
