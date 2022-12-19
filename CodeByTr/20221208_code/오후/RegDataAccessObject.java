package services.registration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.GroupBean;
import beans.StoreBean;

public class RegDataAccessObject extends services.DataAccessObject {
	
	public RegDataAccessObject() {}
	
	/* [SEL] IS GROUPNAME */
	public ArrayList<GroupBean> getGroupName(Connection connection, GroupBean group){
		ArrayList<GroupBean> groupList = null;
		GroupBean groupBean = null;
		
		String query = "SELECT GROUPNAME "
				+ "FROM WEBDBA.DUPGROUPNAME WHERE GROUPNAME = ?";		
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
	public int insStoreGroup(Connection connection, GroupBean group) {
		int result = 0;
		String dml = "INSERT INTO WEBDBA.SG(SG_CODE, SG_NAME, SG_CEO, SG_PIN) "
				+ "VALUES('S'||WEBDBA.GROUPCODE.NEXTVAL, ?, ?, ?)";
		
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
	public int delStoreGroup(Connection connection, GroupBean group) {
		int result = 0;
		String dml = "DELETE FROM WEBDBA.SG WHERE SG_NAME = ?";
		
		try {
			this.ps = connection.prepareStatement(dml);
			this.ps.setNString(1, group.getGroupName());
			
			result = this.ps.executeUpdate();
		}catch(SQLException e) {e.printStackTrace();}
		
		return result;
	}
	
	/* [SEL] GROUPCODE */
	public ArrayList<GroupBean> getGroupCode(Connection connection, GroupBean group) {
		GroupBean groupBean = null;
		ArrayList<GroupBean> groupList = null;
		String query = "SELECT SG_CODE AS GROUPCODE, SG_NAME AS GROUPNAME "
				+ "FROM WEBDBA.SG "
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
	public int insTemp(Connection connection, GroupBean group) {
		int result = 0;
		String dml = "INSERT INTO WEBDBA.TEMP(TEMP_GROUPNAME) VALUES(?)";
		
		try {
			this.ps = connection.prepareStatement(dml);
			this.ps.setNString(1, group.getGroupName());
			
			result = this.ps.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}
		return result;
	}
	
	/* [DEL] TEMP */
	public int delTemp(Connection connection, GroupBean group) {
		int result = 0;
		String dml = "DELETE FROM WEBDBA.TEMP WHERE TEMP_GROUPNAME = ?";
		
		try {
			this.ps = connection.prepareStatement(dml);
			this.ps.setNString(1, group.getGroupName());
			
			result = this.ps.executeUpdate();
		}catch(SQLException e) {e.printStackTrace();}
		
		return result;
	}
	
	/* [INS] STORES */
	public int insStore(Connection connection) {
		int result = 0;
		
		return result;
	}
	/* [INS] STORESCATEGORIES */
	public int insStoreLevel(Connection connection) {
		int result = 0;
		
		return result;
	}
	/* [SEL] ST+SC STORECODE LEVCODE LEVNAME */
	public ArrayList<StoreBean> getStoreInfo(Connection connection) {
		
		
		return null;
	}
}
