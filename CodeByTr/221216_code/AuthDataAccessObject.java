package services.auth;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.AccessLogBean;
import beans.CategoriesBean;
import beans.EmployeesBean;
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
		String query = "SELECT COUNT(*) AS ISSTORE FROM WEBDBA.ST WHERE ST_CODE = ?";
		
		try {
			this.ps = connect.prepareStatement(query);
			this.ps.setNString(1, store.getStoreCode());
			
			this.rs = this.ps.executeQuery();
			while(this.rs.next()) {
				result = this.rs.getInt("ISSTORE");
			}
			
		}catch(SQLException e) {e.printStackTrace();}
		
		return result;
	}
	
	final int isEmpCode(Connection connect, StoreBean store) {
		int result = 0;
		String query = "SELECT COUNT(*) AS ISEMP FROM WEBDBA.SE WHERE SE_STCODE = ? AND SE_CODE = ?";
		
		try {
			this.ps = connect.prepareStatement(query);
			this.ps.setNString(1, store.getStoreCode());
			this.ps.setNString(2, store.getEmpList().get(0).getEmpCode());
			
			this.rs = this.ps.executeQuery();
			while(this.rs.next()) {
				result = this.rs.getInt("ISEMP");
			}
		}catch(SQLException e) {e.printStackTrace();}

		return result;
	}
	
	final int isEqualPinCode(Connection connect, StoreBean store) {
		int result = 0;
		String query = "SELECT COUNT(*) AS ISACCESS FROM WEBDBA.SE WHERE (SE_STCODE = ? AND SE_CODE = ?) AND SE_PIN = ?";
		
		try {
			this.ps = connect.prepareStatement(query);
			this.ps.setNString(1, store.getStoreCode());
			this.ps.setNString(2, store.getEmpList().get(0).getEmpCode());
			this.ps.setNString(3, store.getEmpList().get(0).getEmpPin());
			
			this.rs = this.ps.executeQuery();
			while(this.rs.next()) {
				result = this.rs.getInt("ISACCESS");
			}
			
		}catch(SQLException e) {e.printStackTrace();}
		
		return result;
	}
	
	final int insAccessLog(Connection connect, StoreBean store) {
		int result = 0;
		String dml = "INSERT INTO WEBDBA.AL(AL_SESTCODE, AL_SECODE, AL_DATE, "
				+ "AL_IP, AL_TYPE, AL_PUBLICIP, AL_BROWSER, AL_STATE) "
				+ "VALUES(?, ?, DEFAULT, ?, ?, ?, ?, ?)";
		try {
			this.ps = connect.prepareStatement(dml);
			this.ps.setNString(1, store.getStoreCode());
			this.ps.setNString(2, store.getEmpList().get(0).getEmpCode());
			this.ps.setNString(3, store.getEmpList().get(0).getAccessList().get(0).getAccessLocation());
			this.ps.setInt(4, store.getEmpList().get(0).getAccessList().get(0).getAccessType());
			this.ps.setNString(5, store.getEmpList().get(0).getAccessList().get(0).getAccessPublicIp());
			this.ps.setNString(6, store.getEmpList().get(0).getAccessList().get(0).getAccessBrowser());
			this.ps.setNString(7, store.getEmpList().get(0).getAccessList().get(0).getAccessState());
			
			result = this.ps.executeUpdate();
		}catch(SQLException e) {e.printStackTrace();}
		
		return result;
	}
	
	final GroupBean getAccessInfo(Connection connect, StoreBean store) {
		GroupBean group = new GroupBean();
		AccessLogBean al = new AccessLogBean();
		ArrayList<AccessLogBean> accessList = new ArrayList<AccessLogBean>();
		EmployeesBean emp = new EmployeesBean();
		ArrayList<EmployeesBean> empList = new ArrayList<EmployeesBean>();
		CategoriesBean cate = new CategoriesBean();
		ArrayList<CategoriesBean> cateList = new ArrayList<CategoriesBean>();
		ArrayList<StoreBean> storeList = new ArrayList<StoreBean>();  
		
		String query = "SELECT * FROM WEBDBA.ACCESSINFO "
				+ "WHERE STORECODE = ? AND EMPCODE = ?";
		try {
			this.ps = connect.prepareStatement(query);
			this.ps.setNString(1, store.getStoreCode());
			this.ps.setNString(2, store.getEmpList().get(0).getEmpCode());
			
			this.rs = this.ps.executeQuery();
			while(this.rs.next()) {
				al.setAccessDate(this.rs.getNString("ACCESSDATE"));
				al.setAccessPublicIp(this.rs.getNString("PUBLICIP"));
				al.setAccessLocation(this.rs.getNString("ACCESSLOCATION"));
				al.setAccessBrowser(this.rs.getNString("BROWSER"));
				al.setAccessState(this.rs.getNString("ISFORCE"));
				accessList.add(al);
				
				emp.setAccessList(accessList);
				emp.setEmpCode(this.rs.getNString("EMPCODE"));
				emp.setEmpName(this.rs.getNString("EMPNAME"));
				emp.setEmpLevCode(this.rs.getNString("EMPLEVCODE"));
				empList.add(emp);
				
				cate.setLevName(this.rs.getNString("LEVNAME"));
				cateList.add(cate);
				
				store.setEmpList(empList);
				store.setCateList(cateList);
				store.setStoreCode(this.rs.getNString("STORECODE"));
				store.setStoreName(this.rs.getNString("STORENAME"));
				store.setStoreZip(this.rs.getNString("STOREZIP"));
				store.setStoreAddr(this.rs.getNString("STOREADDR"));
				store.setStoreAddrDetail(this.rs.getNString("STOREADDRDETAIL"));
				store.setStorePhone(this.rs.getNString("STOREPHONE"));
				storeList.add(store);
				
				group.setStoreInfoList(storeList);
				group.setGroupCode(this.rs.getNString("GROUPCODE"));
				group.setGroupName(this.rs.getNString("GROUPNAME"));
			}
		}catch(SQLException e) {e.printStackTrace();}
		
		return group;
	}
	
	final void getStoreInfo(Connection connect, GroupBean group) {
		StoreBean store; 
		String query = ""
				+ "SELECT ST_CODE AS STORECODE, ST_NAME AS STORENAME "
				+ "FROM WEBDBA.ST WHERE ST_SGCODE = ? AND ST_CODE != ?";
		
		try {
			this.ps = connect.prepareStatement(query);
			this.ps.setNString(1, group.getGroupCode());
			this.ps.setNString(2, group.getStoreInfoList().get(0).getStoreCode());
			
			this.rs = this.ps.executeQuery();
			while(this.rs.next()) {
				store = new StoreBean();
				store.setStoreCode("STORECODE");
				store.setStoreName("STORENAME");
				
				group.getStoreInfoList().add(store);
			}
			
		}catch(SQLException e) {e.printStackTrace();}
		
	}
	
	int isAccess(Connection connect, StoreBean store, boolean isSession) {
		int result = 0;
		String query = ""
				+ "SELECT ISACCESS AS ISACCESS "
				+ "FROM WEBDBA.ISACCESS "
				+ "WHERE STORECODE = ? AND EMPCODE = ? AND BROWSER "+ (isSession?"=":"!=") + " ?";
		try {
			this.ps = connect.prepareStatement(query);
			this.ps.setNString(1, store.getStoreCode());
			this.ps.setNString(2, store.getEmpList().get(0).getEmpCode());
			this.ps.setNString(3, store.getEmpList().get(0).getAccessList().get(0).getAccessBrowser());
			
			this.rs = this.ps.executeQuery();
			while(this.rs.next()) {
				result = this.rs.getInt("ISACCESS");
			}
		}catch(SQLException e) { e.printStackTrace();}
		
		return result;
	}
	
	final void getBeforeBrowser(Connection connect, StoreBean store) {
		String query = ""
				+ "SELECT DISTINCT BROWSER FROM WEBDBA.ISACCESS "
				+ "WHERE STORECODE = ? AND EMPCODE = ? AND BROWSER != ?";
		try {
			this.ps = connect.prepareStatement(query);
			this.ps.setNString(1, store.getStoreCode());
			this.ps.setNString(2, store.getEmpList().get(0).getEmpCode());
			this.ps.setNString(3, store.getEmpList().get(0).getAccessList().get(0).getAccessBrowser());
			
			this.rs = this.ps.executeQuery();
			while(this.rs.next()) {
				store.getEmpList().get(0).getAccessList().get(0).setAccessBrowser(this.rs.getNString("BROWSER"));
			}
		}catch(SQLException e) { e.printStackTrace();}
	} 
	
}
