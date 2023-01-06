package services.registration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.CategoriesBean;
import beans.GroupBean;
import beans.StoreBean;

public class RegDataAccessObject extends services.DataAccessObject {
	
	public RegDataAccessObject() {}
	Connection openConnection() {
		return this.openConnect();
	}
	Connection openConnection(String userName, String password ) {
		return this.openConnect();
	}
	void closeConnection(Connection connect) {
		this.closeConnect(connect);
	}
	void modifyTranStatus(Connection connect, boolean tran) {
		this.setTranStatus(connect, tran);
	}
	void setTransaction(boolean tran, Connection connect) {
		this.setTransactionEnd(tran, connect);
	}
	
	/* [SEL] IS GROUPNAME */
	final ArrayList<GroupBean> getGroupName(Connection connection, GroupBean group){
		ArrayList<GroupBean> groupList = null;
		GroupBean groupBean = null;
		
		String query = "SELECT GROUPNAME "
				+ "FROM DUPGROUPNAME WHERE GROUPNAME = ?";		
		try {
			this.ps = connection.prepareStatement(query);
			this.ps.setNString(1, group.getGroupName());
			
			this.rs = this.ps.executeQuery();
			
			if(this.rs.isBeforeFirst()) {
				groupList = new ArrayList<GroupBean>();
				while(this.rs.next()) {
					groupBean = new GroupBean();
					groupBean.setGroupName(this.rs.getNString("GROUPNAME"));
					groupList.add(groupBean);
				}
			}
			
		} catch (SQLException e) {e.printStackTrace();}
		
		return groupList;
	}
	
	/* [INS] STOREGROUP */
	final int insStoreGroup(Connection connection, GroupBean group) {
		int result = 0;
		String dml = "INSERT INTO SG(SG_CODE, SG_NAME, SG_CEO, SG_PIN) "
				+ "VALUES('S'||GROUPCODE.NEXTVAL, ?, ?, ?)";
		
		try {
			this.ps = connection.prepareStatement(dml);
			this.ps.setNString(1, group.getGroupName());
			this.ps.setNString(2, group.getGroupCeo());
			this.ps.setNString(3, group.getGroupPin());
			
			result = this.ps.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}
		
		return result;
	}
	
	/* [DEL] STOREGROUP */
	final int delStoreGroup(Connection connection, GroupBean group) {
		int result = 0;
		String dml = "DELETE FROM SG WHERE SG_NAME = ?";
		
		try {
			this.ps = connection.prepareStatement(dml);
			this.ps.setNString(1, group.getGroupName());
			
			result = this.ps.executeUpdate();
		}catch(SQLException e) {e.printStackTrace();}
		
		return result;
	}
	
	/* [SEL] GROUPCODE */
	final ArrayList<GroupBean> getGroupCode(Connection connection, GroupBean group) {
		GroupBean groupBean = null;
		ArrayList<GroupBean> groupList = null;
		String query = "SELECT SG_CODE AS GROUPCODE, SG_NAME AS GROUPNAME "
				+ "FROM SG "
				+ "WHERE SG_NAME = ?";
		
		try {
			this.ps = connection.prepareStatement(query);
			this.ps.setNString(1, group.getGroupName());
						
			this.rs = this.ps.executeQuery();
			if(this.rs != null) {
				groupList = new ArrayList<GroupBean>();
				while(this.rs.next()) {
					groupBean = new GroupBean();
					groupBean.setGroupCode(this.rs.getNString("GROUPCODE"));
					groupBean.setGroupName(this.rs.getNString("GROUPNAME"));
					groupList.add(groupBean);
				}
			}
			
		} catch (SQLException e) {e.printStackTrace();}
		
		return groupList;
	}
	
	/* [INS] TEMP */
	final int insTemp(Connection connection, GroupBean group) {
		int result = 0;
		String dml = "INSERT INTO TEMP(TEMP_GROUPNAME) VALUES(?)";
		
		try {
			this.ps = connection.prepareStatement(dml);
			this.ps.setNString(1, group.getGroupName());
			
			result = this.ps.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}
		return result;
	}
	
	/* [DEL] TEMP */
	final int delTemp(Connection connection, GroupBean group) {
		int result = 0;
		String dml = "DELETE FROM TEMP WHERE TEMP_GROUPNAME = ?";
		
		try {
			this.ps = connection.prepareStatement(dml);
			this.ps.setNString(1, group.getGroupName());
			
			result = this.ps.executeUpdate();
		}catch(SQLException e) {e.printStackTrace();}
		
		return result;
	}
	
	/* [INS] STORES */
	final int insStore(Connection connection, GroupBean group) {
		int result = 0;
		String dml = "INSERT INTO ST("
				   + "ST_CODE, ST_NAME, ST_ZIPCODE, ST_ADDR, "
				   + "ST_ADDRDETAIL, ST_PHONE, ST_SGCODE) "
				   + "VALUES(?, ? ,?, ?, ?, ?, ?)";
		try {
			this.ps = connection.prepareStatement(dml);
			this.ps.setNString(1, group.getStoreInfoList().get(0).getStoreCode());
			this.ps.setNString(2, group.getStoreInfoList().get(0).getStoreName());
			this.ps.setNString(3, group.getStoreInfoList().get(0).getStoreZip());
			this.ps.setNString(4, group.getStoreInfoList().get(0).getStoreAddr());
			this.ps.setNString(5, group.getStoreInfoList().get(0).getStoreAddrDetail());
			this.ps.setNString(6, group.getStoreInfoList().get(0).getStorePhone());
			this.ps.setNString(7, group.getGroupCode());
			
			result = this.ps.executeUpdate();
		}catch(SQLException e) {e.printStackTrace();}
		
		return result;
	}
	/* [INS] STORESCATEGORIES */
	final int insStoreLevel(Connection connection, GroupBean group) {
		int result = 0;
		String dml = "INSERT INTO SC(SC_STCODE, SC_CODE, SC_NAME) "
				   + "VALUES(?, ?, ?)";
		try {
			this.ps = connection.prepareStatement(dml);
			this.ps.setNString(1, group.getStoreInfoList().get(0).getStoreCode());
			for(CategoriesBean c : group.getStoreInfoList().get(0).getCateList()) {
				this.ps.setNString(2, c.getLevCode());
				this.ps.setNString(3, c.getLevName());
				result = this.ps.executeUpdate();
				if(result == 0) break;
			}
		}catch(SQLException e) {e.printStackTrace();}
		return result;
	}
	
	/*[INS] Employees*/
	final int insEmployees(Connection connection, StoreBean store)  { //직원 인서트
		int result = 0;
		String dml = "INSERT INTO SE(SE_STCODE, SE_CODE, SE_SCLEV, SE_NAME, SE_PIN, SE_SICODE) "
				+ "VALUES(?, ?, ?, ?, ?, DEFAULT)";
		try {
			this.ps = connection.prepareStatement(dml);
			this.ps.setNString(1, store.getStoreCode());
			this.ps.setNString(2, store.getEmpList().get(0).getEmpCode());
			this.ps.setNString(3, store.getEmpList().get(0).getEmpLevCode());
			this.ps.setNString(4, store.getEmpList().get(0).getEmpName());
			this.ps.setNString(5, store.getEmpList().get(0).getEmpPin());
			
			result = this.ps.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}
		
		return result;
	}
	
	/* [SEL] ST+SC STORECODE LEVCODE LEVNAME */
	final ArrayList<StoreBean> getStoreInfo(Connection connection) {
		
		
		return null;
	}
}
