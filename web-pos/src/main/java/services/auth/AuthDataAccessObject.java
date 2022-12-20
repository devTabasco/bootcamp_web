package services.auth;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import beans.AccessLogBean;
import beans.CategoriesBean;
import beans.EmployeeBean;
import beans.GroupBean;
import beans.StoreBean;
import services.DataAccessObject;

public class AuthDataAccessObject extends DataAccessObject {
	AuthDataAccessObject() {
	}

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

	/* [INS] AccessLog */
	/* 상점코드, 직원코드, 접속일시, 접속주소, 접속목적(1,-1) */
	final int insAccessLogin(Connection connection, StoreBean store) {
		int result = 0;
		String dml = "INSERT INTO ACCESSLOG(AC_SESTCODE, AC_SECODE, AC_DATE, AC_IP, AC_TYPE, AC_BROWSER, AC_PUBLICIP, AC_STATE) "
				+ "VALUES(?, ?, DEFAULT, ?, ?, ?, ?, ?)";
		try {
			this.ps = connection.prepareStatement(dml);
			this.ps.setNString(1, store.getStoreCode());
			this.ps.setNString(2, store.getEmpList().get(0).getEmpCode());
			this.ps.setNString(3, store.getEmpList().get(0).getAccessList().get(0).getAccessLocate());
			this.ps.setInt(4, store.getEmpList().get(0).getAccessList().get(0).getAccessType());
			this.ps.setNString(5, store.getEmpList().get(0).getAccessList().get(0).getAccessBrowser());
			this.ps.setNString(6, store.getEmpList().get(0).getAccessList().get(0).getAccessPublicIp());
			this.ps.setNString(7, store.getEmpList().get(0).getAccessList().get(0).getAccessState());
			
			result = this.ps.executeUpdate();
		}catch(SQLException e) {e.printStackTrace();}
		
		return result;
	}

	final GroupBean getAccessInfo(Connection connection, StoreBean store) {
		StoreBean storeBaen = null;
		GroupBean group = new GroupBean();
		EmployeeBean empBaen = new EmployeeBean();
		AccessLogBean accessLogBean = new AccessLogBean();
		CategoriesBean categoryBean = new CategoriesBean();
		ArrayList<CategoriesBean> categoryList = new ArrayList<CategoriesBean>();
		ArrayList<AccessLogBean> accessList = new ArrayList<AccessLogBean>();
		ArrayList<StoreBean> storeList = new ArrayList<StoreBean>();
		ArrayList<EmployeeBean> empList = new ArrayList<EmployeeBean>();
		String query = "SELECT * FROM ACCESSINFO "
				+ "WHERE STORECODE = ? AND EMPCODE = ?";
		try {
			this.ps = connection.prepareStatement(query);
			this.ps.setNString(1, store.getStoreCode());
			this.ps.setNString(2, store.getEmpList().get(0).getEmpCode());
			
			this.rs = this.ps.executeQuery();
			while(this.rs.next()) {
				accessLogBean.setAccessDateTime(this.rs.getNString("ACCESSDATE"));
				accessLogBean.setAccessBrowser(this.rs.getNString("BROWSER"));
				accessLogBean.setAccessPublicIp(this.rs.getNString("PUBLICIP"));
				accessLogBean.setAccessLocate(this.rs.getNString("PRIVATEIP"));
				accessLogBean.setAccessBrowser(this.rs.getNString("BROWSER"));
				accessLogBean.setAccessState(this.rs.getNString("ISFORCE"));
				accessList.add(accessLogBean);
				
				empBaen.setAccessList(accessList);
				empBaen.setEmpCode(this.rs.getNString("EMPCODE"));
				empBaen.setEmpName(this.rs.getNString("EMPNAME"));
				empBaen.setEmpLevCode(this.rs.getNString("EMPLEVCODE"));
				empList.add(empBaen);
				
				categoryBean.setLevName(this.rs.getNString("LEVNAME"));
				categoryList.add(categoryBean);
				
				store.setEmpList(empList);
				store.setLevInfo(categoryList);
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

	final int isStoreCode(Connection connection, StoreBean store) {
		int result = 0;
		String query = "SELECT COUNT(*) AS ISSTORE FROM ST WHERE ST_CODE = ?";

		try {
			this.ps = connection.prepareStatement(query);
			ps.setNString(1, store.getStoreCode());

			this.rs = this.ps.executeQuery();

			if (this.rs.isBeforeFirst()) {
				while (this.rs.next()) {
					result = this.rs.getInt("ISSTORE");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

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
				store.setStoreCode(this.rs.getNString("STORECODE"));
				store.setStoreName(this.rs.getNString("STORENAME"));
				
				group.getStoreInfoList().add(store);
			}
			
		}catch(SQLException e) {e.printStackTrace();}
		
	}
	
//	int isAccess(Connection connect, StoreBean store) {
//		int result = 0;
//		String query = ""
//				+ "SELECT ISACCESS "
//				+ "FROM ISACCESS "
//				+ "WHERE STORECODE = ? AND EMPCODE = ? ";
//		try {
//			this.ps = connect.prepareStatement(query);
//			this.ps.setNString(1, store.getStoreCode());
//			this.ps.setNString(2, store.getEmpList().get(0).getEmpCode());
//			
//			this.rs = this.ps.executeQuery();
//			while(this.rs.next()) {
//				result = this.rs.getInt("ISACCESS");
//			}
//		}catch(SQLException e) { e.printStackTrace();}
//		
//		return result;
//	}
	
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
		ArrayList<EmployeeBean> empList = null;
		EmployeeBean emp = null;
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
			this.ps.setNString(3, store.getEmpList().get(0).getAccessList().get(0).getAccessLocate());
						
			this.rs = this.ps.executeQuery();
			
			if(this.rs.isBeforeFirst()) {
				System.out.println("before");
				/* 로그인 전 이미 로그인 된 기록을 저장할 Object 할당 */
				beforeAccess = new StoreBean();
				empList = new ArrayList<EmployeeBean>();
				emp = new EmployeeBean();
				accessList = new ArrayList<AccessLogBean>();
				access = new AccessLogBean();
				
				while(this.rs.next()) {
					System.out.println("record");
					/* publicIp, private Ip, browser 정보 저장 */
					access.setAccessPublicIp(this.rs.getNString("PUBLICIP"));
					access.setAccessLocate(this.rs.getNString("PRIVATEIP"));
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
