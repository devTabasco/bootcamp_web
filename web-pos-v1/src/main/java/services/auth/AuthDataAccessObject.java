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
		String query = "SELECT COUNT(*) AS ISSTORE FROM ST WHERE ST_CODE = ?";
		
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
		String query = "SELECT COUNT(*) AS ISEMP FROM SE WHERE SE_STCODE = ? AND SE_CODE = ?";
		
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
		String query = "SELECT COUNT(*) AS ISACCESS FROM SE WHERE (SE_STCODE = ? AND SE_CODE = ?) AND SE_PIN = ?";
		
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
		String dml = "INSERT INTO ACCESSLOG(AC_SESTCODE, AC_SECODE, AC_DATE, AC_IP, AC_TYPE, AC_PUBLICIP, AC_BROWSER, AC_STATE) "
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
		
		String query = "SELECT * FROM ACCESSINFO "
				+ "WHERE STORECODE = ? AND EMPCODE = ? "
				+ "  AND BROWSER =? AND PUBLICIP = ? "
				+ "  AND PRIVATEIP = ?";
		try {
			this.ps = connect.prepareStatement(query);
			this.ps.setNString(1, store.getStoreCode());
			this.ps.setNString(2, store.getEmpList().get(0).getEmpCode());
			this.ps.setNString(3, store.getEmpList().get(0).getAccessList().get(0).getAccessBrowser());
			this.ps.setNString(4, store.getEmpList().get(0).getAccessList().get(0).getAccessPublicIp());
			this.ps.setNString(5, store.getEmpList().get(0).getAccessList().get(0).getAccessLocation());
			
			this.rs = this.ps.executeQuery();
			while(this.rs.next()) {
				al.setAccessDate(this.rs.getNString("ACCESSDATE"));
				al.setAccessPublicIp(this.rs.getNString("PUBLICIP"));
				al.setAccessLocation(this.rs.getNString("PRIVATEIP"));
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
				+ "FROM ST WHERE ST_SGCODE = ? AND ST_CODE != ?";
		
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
				+ "FROM ISACCESS "
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
	
	final StoreBean getBeforeAccess(Connection connect, StoreBean store) {
		/* 로그인 전 이미 로그인 된 기록이 있을 경우 저장할 Object 선언 */
		StoreBean beforeAccess = null;
		ArrayList<EmployeesBean> empList = null;
		EmployeesBean emp = null;
		ArrayList<AccessLogBean> accessList = null;
		AccessLogBean access = null;
		
		String query = ""
				+ "SELECT STORECODE, EMPCODE, "
				+ "		  PUBLICIP, PRIVATEIP, BROWSER "
				+ "FROM ISACCESS "
				+ "WHERE (BROWSER != ? "
				+ "    OR PUBLICIP != ? "
				+ "    OR PRIVATEIP != ?) "
				+ "  AND STORECODE = ? "
				+ "  AND EMPCODE = ? "
				+ "  AND ISACCESS = 1";
		
		try {
			this.ps = connect.prepareStatement(query);
			this.ps.setNString(4, store.getStoreCode());
			this.ps.setNString(5, store.getEmpList().get(0).getEmpCode());
			this.ps.setNString(1, store.getEmpList().get(0).getAccessList().get(0).getAccessBrowser());
			this.ps.setNString(2, store.getEmpList().get(0).getAccessList().get(0).getAccessPublicIp());
			this.ps.setNString(3, store.getEmpList().get(0).getAccessList().get(0).getAccessLocation());
						
			this.rs = this.ps.executeQuery();
			
			if(this.rs.isBeforeFirst()) {
				System.out.println("before");
				/* 로그인 전 이미 로그인 된 기록을 저장할 Object 할당 */
				beforeAccess = new StoreBean();
				empList = new ArrayList<EmployeesBean>();
				emp = new EmployeesBean();
				accessList = new ArrayList<AccessLogBean>();
				access = new AccessLogBean();
				
				while(this.rs.next()) {
					System.out.println("record");
					/* publicIp, private Ip, browser 정보 저장 */
					access.setAccessPublicIp(this.rs.getNString("PUBLICIP"));
					access.setAccessLocation(this.rs.getNString("PRIVATEIP"));
					access.setAccessBrowser(this.rs.getNString("BROWSER"));
					accessList.add(access);
					emp.setAccessList(accessList);
					/* empCode 정보 저장 */
					emp.setEmpCode(this.rs.getNString("EMPCODE"));
					empList.add(emp);
					beforeAccess.setEmpList(empList);
					/* storeCode 정보 저장 */
					beforeAccess.setStoreCode(this.rs.getNString("STORECODE"));
				}
			}
			
		}catch(SQLException e) { e.printStackTrace();}
		
		return beforeAccess;
	} 
}
