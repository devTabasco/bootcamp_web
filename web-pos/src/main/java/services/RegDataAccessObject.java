package services;

import java.sql.Connection;
import java.sql.PseudoColumnUsage;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.GroupBean;
import beans.StoreBean;

public class RegDataAccessObject extends DataAccessObject{

	public RegDataAccessObject() {
		// TODO Auto-generated constructor stub
	}
	
	// insert StoreGroup
	public int insStoreGroup(Connection connection, GroupBean group) {
		int result = 0;
		String dml = "INSERT INTO SG(SG_CODE, SG_NAME, SG_CEO, SG_PIN) "
				+ "VALUES('S'||SG_CODE_SEQUENCE.NEXTVAL, ?, ?, ?)";
		
		try {
			this.ps = connection.prepareStatement(dml);
			ps.setNString(1, group.getGroupName());
			ps.setNString(2, group.getGroupCeo());
			ps.setNString(3, group.getGroupPin());
			
			result = this.ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	//select GroupCode
	public ArrayList<GroupBean> getGroupCode(Connection connection, GroupBean group) {
		GroupBean groupBaen = new GroupBean();
		ArrayList<GroupBean> groupList = null;
		String dml = "SELECT SG_CODE AS GROUPCODE FROM SG WHERE SG_NAME = ?";
		
		try {
			this.ps = connection.prepareStatement(dml);
			ps.setNString(1, group.getGroupName());
			
			this.rs = this.ps.executeQuery();
			
			if(this.rs != null) {
				groupList = new ArrayList<GroupBean>();
			
				while (this.rs.next()) {
					groupBaen = new GroupBean();
					groupBaen.setGroupCode(this.rs.getNString("GROUPCODE"));
					groupList.add(groupBaen);
				}
			
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return groupList;
	}
	
	//insert Stores
	public int insStore(Connection connection) {
		int result = 0;
		
		return result;
	}
	//insert Categorise
	public int insStoreCategories(Connection connection) {
		int result = 0;
		
		return result;
	}
	
	//select(Store + Category) StoreCode, SCCode, SCName
	public ArrayList<StoreBean> getStoreInfo() {
		
		
		
		return null;
	}
	
}
