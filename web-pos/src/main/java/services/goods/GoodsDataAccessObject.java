package services.goods;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.CategoriesBean;
import beans.StoreBean;
import services.DataAccessObject;

public class GoodsDataAccessObject extends DataAccessObject {

	final Connection openConnection() {
		return this.openConnect();
	}

	final Connection openConnection(String userName, String password) {
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

	ArrayList<CategoriesBean> getCategoryList(Connection connection, StoreBean clientData){
		ArrayList<CategoriesBean> categoryList = null;
		CategoriesBean category = null;
		
		String query = "SELECT SC_CODE AS LEVCODE, SC_NAME AS LEVNAME FROM SC WHERE SC_STCODE = ? AND SUBSTR(SC_CODE,1,1) = ? ";
		
		try {
			this.ps = connection.prepareStatement(query);
			this.ps.setNString(1, clientData.getStoreCode());
			this.ps.setNString(2, clientData.getLevInfo().get(0).getLevCode());
			
			this.rs = this.ps.executeQuery();
			
			if(rs.isBeforeFirst()) {
				System.out.println("테스트");
					categoryList = new ArrayList<CategoriesBean>();
					while(rs.next()) {
						category = new CategoriesBean();
						category.setLevCode(rs.getNString("LEVCODE"));
						category.setLevName(rs.getNString("LEVNAME"));
						
						categoryList.add(category);
					}
			}
			
		} catch (SQLException e) {
			
		}
		
		
		return categoryList;
	}
	
	int getMaxCode(Connection connection, StoreBean clientData) {
		int maxIdx = 0;
		
		String query = "SELECT SUBSTR(MAX(SC_CODE),2,1) AS MAXIDX FROM SC WHERE SC_STCODE = ? AND SUBSTR(SC_CODE,1,1) = ? ";
		
		try {
			this.ps = connection.prepareStatement(query);
			this.ps.setNString(1,clientData.getStoreCode());
			this.ps.setNString(2,clientData.getLevInfo().get(0).getLevCode());
			
			this.rs = this.ps.executeQuery();
			
			if(this.rs.isBeforeFirst()) {
				while(this.rs.next()) {
					maxIdx = Integer.parseInt(this.rs.getNString("MAXIDX")) + 1;
				}
			}
			
			
		} catch (SQLException e) {
			// TODO: handle exception
		}
		
		return maxIdx;
	}
	
	int insCategoryItem(Connection connection, StoreBean clientData) {
		int result = -1;
		
		String dml = "INSERT INTO SC(SC_STCODE, SC_CODE, SC_NAME) VALUES(?, ?, ?)";
		
		try {
			this.ps = connection.prepareStatement(dml);
			this.ps.setNString(1, clientData.getStoreCode());
			this.ps.setNString(2, clientData.getLevInfo().get(0).getLevCode());
			this.ps.setNString(3, clientData.getLevInfo().get(0).getLevName());
			
			System.out.println(clientData.getStoreCode());
			System.out.println(clientData.getLevInfo().get(0).getLevCode());
			System.out.println(clientData.getLevInfo().get(0).getLevName());
			
			result = this.ps.executeUpdate();
			System.out.println(result+"insert 확인");
		} catch (SQLException e) {
			// TODO: handle exception
		}
		
		return result;
	}
	
	int updateCategoryItem(Connection connection, StoreBean clientData) {
		int result = -1;
		
		String dml = "UPDATE SC SET SC_NAME = ? WHERE SC_STCODE = ? AND SC_CODE = ? ";
		
		try {
			this.ps = connection.prepareStatement(dml);
			this.ps.setNString(1, clientData.getLevInfo().get(0).getLevName());
			this.ps.setNString(2, clientData.getStoreCode());
			this.ps.setNString(3, clientData.getLevInfo().get(0).getLevCode());
			
			result = this.ps.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
		}
		
		return result;
	}
}
