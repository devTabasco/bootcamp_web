package services;

import java.sql.Connection;
import java.sql.PseudoColumnUsage;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.CategoriesBean;
import beans.GroupBean;
import beans.StoreBean;

public class RegDataAccessObject extends DataAccessObject{

	public RegDataAccessObject() {
		// TODO Auto-generated constructor stub
	}
	
	/* [INS] STORESCATEGORIES */
	public int insStoreLevel(Connection connection, GroupBean group) {
		int result = 0;
		String dml = "INSERT INTO SC(SC_STCODE, SC_CODE, SC_NAME) "
				   + "VALUES(?, ?, ?)";
		try {
			this.ps = connection.prepareStatement(dml);
			this.ps.setNString(1, group.getStoreInfoList().get(0).getStoreCode());
			for(CategoriesBean category : group.getStoreInfoList().get(0).getLevInfo()) {
				this.ps.setNString(2, category.getLevCode());
				this.ps.setNString(3, category.getLevName());
				result = this.ps.executeUpdate();
				if(result == 0) break;
			}
		}catch(SQLException e) {e.printStackTrace();}
		return result;
	}
	
	// insert Store
		public int insStore(Connection connection, GroupBean group) {
			int result = 0;
			String dml = "INSERT INTO ST(ST_CODE, ST_NAME, ST_ZIPCODE, ST_ADDR, ST_ADDRDETAIL, ST_PHONE, ST_SGCODE) "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?)";
			
			try {
				this.ps = connection.prepareStatement(dml);
				ps.setNString(1, group.getStoreInfoList().get(0).getStoreCode());
				ps.setNString(2, group.getStoreInfoList().get(0).getStoreName());
				ps.setNString(3, group.getStoreInfoList().get(0).getStoreZip());
				ps.setNString(4, group.getStoreInfoList().get(0).getStoreAddr());
				ps.setNString(5, group.getStoreInfoList().get(0).getStoreAddrDetail());
				ps.setNString(6, group.getStoreInfoList().get(0).getStorePhone());
				ps.setNString(7, group.getGroupCode());
				
				result = this.ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			System.out.println(result);
			return result;
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
	
	//select GroupName
	public ArrayList<GroupBean> getGroupName(Connection connection, GroupBean group) {
		GroupBean groupBaen = null;
		ArrayList<GroupBean> groupList = null;
		String query = "SELECT GROUPNAME FROM DUPGROUPNAME WHERE GROUPNAME = ?";
		
		try {
			this.ps = connection.prepareStatement(query);
			ps.setNString(1, group.getGroupName());
			
			this.rs = this.ps.executeQuery();
			
			if(this.rs.isBeforeFirst()) {
				groupList = new ArrayList<GroupBean>();
			
				while (this.rs.next()) {
					groupBaen = new GroupBean();
					groupBaen.setGroupName(this.rs.getNString("GROUPNAME"));
					groupList.add(groupBaen);
				}
			
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return groupList;
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
			
			if(this.rs.isBeforeFirst()) {
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
	
	/* insert구문 순서
	 * 
	 * 1. dml작성
	 * 2. connection.prepareStatement 준비
	 * 3. prepareStatement 에 set
	 * 4. prepareStatement.excuteUpdate > int형으로 return
	 * 
	 * */
	
	
	//insert temp
	public int insTemp(Connection connection, GroupBean group) {
		int result = 0;
		
		String dml = "INSERT INTO TEMP(TEMP_GROUPNAME) "
				+ "VALUES(?)";
		
		try {
			this.ps = connection.prepareStatement(dml);
			ps.setNString(1, group.getGroupName());
			
			result = this.ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int deleteStore(Connection connection, StoreBean store) {
		int result = 0;
		
		String dml = "DELETE FROM ST WHERE ST_CODE = ?";
		
		try {
			this.ps = connection.prepareStatement(dml);
			ps.setNString(1, store.getStoreCode());
			
			result = this.ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int deleteTemp(Connection connection, GroupBean group) {
		int result = 0;
		
		String dml = "DELETE FROM TEMP WHERE TEMP_GROUPNAME = ?";
		
		try {
			this.ps = connection.prepareStatement(dml);
			ps.setNString(1, group.getGroupName());
			
			result = this.ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
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
