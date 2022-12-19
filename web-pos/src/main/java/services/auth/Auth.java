package services.auth;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import beans.AccessLogBean;
import beans.ActionBean;
import beans.EmployeeBean;
import beans.GroupBean;
import beans.StoreBean;
import controller.ClientInfo;
import services.registration.RegDataAccessObject;

public class Auth {
	private HttpServletRequest request;

	public Auth() {

	}

	public ActionBean backController(int serviceCode, HttpServletRequest request) {
		this.request = request;
		ActionBean action = null;

		switch (serviceCode) {
		case -1:
			System.out.println(serviceCode);
			action = this.accessOut();
			break;
		case 1:
			action = this.accessCtl();
			break;
		case 2:
			action = this.accessOut();
			break;
		case 0:
			action = this.initCtl();
			break;
		case 4:
			break;

		}

		return action;
	}

	private ActionBean initCtl() {
		ActionBean action = new ActionBean();
		String page = "index.jsp", message = null;
		boolean redirect = true, tran = false;

		HttpSession session = this.request.getSession();

//		if (session.getAttribute("AccessInfo") != null) {
//			if (this.isAccess(((GroupBean) session.getAttribute("AccessInfo")).getStoreInfoList().get(0))) {
//				page = "manager.jsp";
//				redirect = false;
//			} else session.invalidate();
//		}
		
		if(session.getAttribute("AccessInfo") != null) {
			System.out.println("Landing : 세션이 살아있는 경우");
			System.out.println(((GroupBean)session.getAttribute("AccessInfo")).getStoreInfoList().get(0).getStoreName());
			System.out.println(((GroupBean)session.getAttribute("AccessInfo")).getStoreInfoList().get(0).getEmpList().get(0).getAccessList().get(0).getAccessBrowser());
			if(this.isAccess(
					((GroupBean)session.getAttribute("AccessInfo")).getStoreInfoList().get(0), true)){
				page = "manager.jsp";
				redirect = false;
			}
		}

		action.setPage(page);
		action.setRedirect(redirect);

		return action;

	}

	public Object backController(int serviceCode, Object obj) {
		Object result = null;
		switch (serviceCode) {
		case 999:
			result = this.isAccess((StoreBean)obj, true);
			break;
		}
		return result;
	}
	
	private ActionBean accessCtl() {
		/* Job Controller Pattern */
		HttpSession session = this.request.getSession();
		ActionBean action = new ActionBean();
		String page = "index.jsp", message = null;
		boolean isForward = false, tran = false;

		/* 1. Client Data : storeCode, empCode, empPin >> Bean */
		GroupBean group = null;
		StoreBean store = new StoreBean();
		ArrayList<EmployeeBean> empList = new ArrayList<EmployeeBean>();
		EmployeeBean emp = new EmployeeBean();
		AccessLogBean access = new AccessLogBean();
		ArrayList<AccessLogBean> accessList = new ArrayList<AccessLogBean>();
		AuthDataAccessObject dao = new AuthDataAccessObject();

		if(session.getAttribute("AccessInfo") == null) {
			access.setAccessPublicIp(this.request.getParameter("accessPublicIp"));
			access.setAccessLocate(this.request.getRemoteAddr());
			access.setAccessType(1);
			access.setAccessState("R");
			access.setAccessBrowser(getBrowser());
			accessList.add(access);

			emp.setEmpCode(this.request.getParameter("empCode"));
			emp.setEmpPin(this.request.getParameter("empPin"));
			emp.setAccessList(accessList);
			empList.add(emp);

			store.setStoreCode(this.request.getParameter("storeCode"));		
			store.setEmpList(empList);		

			/* Browser 정보 */
			String browserInfo = (store.getEmpList().get(0).getAccessList().get(0).getAccessBrowser());
			System.out.println(browserInfo+" : browserInfo");
			/* 2. DAO */
			Connection connection = dao.openConnection();
			dao.modifyTranStatus(connection, false);

			if(this.convertToBoolean(dao.isStoreCode(connection, store))) {
				if(this.convertToBoolean(dao.isEmpCode(connection, store))) {
					if(this.convertToBoolean(dao.isEqualPinCode(connection, store))) {
						/* 현재 액세스 여부 */
						if(!this.isAccess(store, true)) {
							System.out.println("세션은 없고 자신의 로그인 기록이 없는 경우");
							System.out.println(browserInfo);

							/* 다른 로그인 정보 확인 */
							if(this.isAccess(store, false)) {
								System.out.println("세션은 없고 자신의 로그인 기록은 없지만 다른 브라우저의 로그인 기록은 있는 경우");
								System.out.println(browserInfo);

								// 다른 로그인 정보 수집 >> 로그아웃
								StoreBean beforeAccess = dao.getBeforeAccess(connection, store);
								beforeAccess.getEmpList().get(0).getAccessList().get(0).setAccessType(-1);
								beforeAccess.getEmpList().get(0).getAccessList().get(0).setAccessState("I");
								this.accessOutCtl(beforeAccess);
							}

							if(this.convertToBoolean(dao.insAccessLogin(connection, store))) {
//								session  = this.request.getSession();
//								session.setMaxInactiveInterval(30);
								tran = true;
								isForward = true;
								group = dao.getAccessInfo(connection, store);

								if(group.getStoreInfoList().get(0)
										.getEmpList().get(0)
										.getEmpLevCode().equals("L1")) {
									dao.getStoreInfo(connection, group);
								}
								/* pin 삭제 */
								group.getStoreInfoList().get(0).getEmpList().get(0).setEmpPin(null);

								System.out.println(group.getStoreInfoList().get(0).getEmpList().get(0).getAccessList().get(0).getAccessBrowser());
								session.setAttribute("AccessInfo", group);
								page = !group.getStoreInfoList().get(0)
										.getEmpList().get(0)
										.getEmpLevCode().equals("L3")? "manager.jsp":"pos.jsp";
							}
						}
					}
				}
			}

			dao.setTransaction(tran, connection);
			dao.modifyTranStatus(connection, true);
			dao.closeConnection(connection);
		}else {
			/* Browser 정보 */
			String browserInfo = ((GroupBean)session.getAttribute("AccessInfo")).getStoreInfoList().get(0).getEmpList().get(0).getAccessList().get(0).getAccessBrowser();

			/* 새로고침 영역 */
			if(this.isAccess(
					((GroupBean)session.getAttribute("AccessInfo"))
					.getStoreInfoList().get(0), true)) {
				System.out.println("세션은 있고 자신의 로그인 기록이 있는 경우");
				System.out.println(browserInfo);
				page = "manager.jsp";
				isForward = true;
			}else {
				System.out.println("세션은 있고 자신의 로그아웃 기록이 있는 경우");
				System.out.println(browserInfo);
				session.invalidate();
			}
		}
		action.setPage(page);
		action.setRedirect(isForward);
		return action;
	}

//	private ActionBean accessCtl() {
//		ActionBean action = new ActionBean();
//		GroupBean group = null;
//		StoreBean store = new StoreBean();
//		EmployeeBean emp = new EmployeeBean();
//		AccessLogBean accessLog = new AccessLogBean();
//		ArrayList<AccessLogBean> accessLogList = new ArrayList<AccessLogBean>();
//		ArrayList<EmployeeBean> empList = new ArrayList<EmployeeBean>();
//		String page = "index.jsp", message = null;
//		boolean redirect = true, tran = false;
//		AuthDataAccessObject dao = new AuthDataAccessObject();
//		HttpSession session = null;
//
//		store.setStoreCode(this.request.getParameter("storeCode"));
//		emp.setEmpCode(this.request.getParameter("empCode"));
//		emp.setEmpPin(this.request.getParameter("empPin"));
//
//		accessLog.setAccessPublicIp(this.request.getParameter("accessPublicIp"));
//		accessLog.setAccessState("R");
//		accessLog.setAccessBrowser(getBrowser());
//		accessLog.setAccessLocate(request.getRemoteAddr());
//		accessLog.setAccessType(1);
//		accessLogList.add(accessLog);
//		emp.setAccessList(accessLogList);
//
//		empList.add(emp);
//		store.setEmpList(empList);
//
//		// transaction control
//		Connection connection = dao.openConnection();
//
//		dao.modifyTranStatus(connection, false);
//		/*
//		 * 
//		 * 
//		 * */
//		// EMPINFO
//
//		// employCode로 레벨 정보 가져오기
//		// isStoreCode
//		session = this.request.getSession();
//		if (session.getAttribute("AccessInfo") != null) {
////			if (this.isAccess(((GroupBean) session.getAttribute("AccessInfo")).getStoreInfoList().get(0))) {
////				page = "manager.jsp";
////				redirect = false;
////			} else
//				session.invalidate();
//		} else {
//			if (this.convertToBoolean(dao.isStoreCode(connection, store))) {
//				if (this.convertToBoolean(dao.isEmpCode(connection, store))) {
//					if (this.convertToBoolean(dao.isEqualPinCode(connection, store))) {
//						/* 현재 액세스 여부 */
//						if (this.isAccess(store)) {
//							// 로그아웃
//							store.getEmpList().get(0).getAccessList().get(0).setAccessType(-1);
//							store.getEmpList().get(0).getAccessList().get(0).setAccessState("I");
//							this.accessOutCtl(store);
//							store.getEmpList().get(0).getAccessList().get(0).setAccessType(1);
//							store.getEmpList().get(0).getAccessList().get(0).setAccessState("R");
//						}
//
//						if (this.convertToBoolean(dao.insAccessLogin(connection, store))) {
//							session = this.request.getSession();
//							tran = true;
//							redirect = false;
//							group = dao.getAccessInfo(connection, store);
//							if (group.getStoreInfoList().get(0).getEmpList().get(0).getEmpLevCode().equals("L1")) {
//								dao.getStoreInfo(connection, group);
//							}
//							group.getStoreInfoList().get(0).getEmpList().get(0).setEmpPin(null);
//							session.setAttribute("AccessInfo", group);
//							page = !group.getStoreInfoList().get(0).getEmpList().get(0).getEmpLevCode().equals("L3")
//									? "manager.jsp"
//									: "pos.html";
//						}
//					}
//				}
//			}
//		}
//
//		dao.setTransaction(true, connection);
//		dao.modifyTranStatus(connection, true);
//
//		// dao close;
//		dao.closeConnection(connection);
//
//		action.setPage(page);
//		action.setRedirect(redirect);
//
//		return action;
//	}

	private String getBrowser() {
		/*
		 * m-opera > Mozilla/5.0 (iPhone; CPU iPhone OS 16_1_1 like Mac OS X)
		 * AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.1 Mobile/15E148
		 * Safari/604.1 OPT/3.4.0
		 * 
		 * m-whale > Mozilla/5.0 (iPhone; CPU iPhone OS 16_1_1 like Mac OS X)
		 * AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.0 Whale/2.3.11.3797
		 * Mobile/15E148 Safari/604.1
		 * 
		 * m-samsung > Mozilla/5.0 (Linux; Android 10; SAMSUNG SM-N986N)
		 * AppleWebKit/537.36 (KHTML, like Gecko) SamsungBrowser/16.0
		 * Chrome/92.0.4515.166 Mobile Safari/537.36
		 * 
		 * PC - Chrome Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36
		 * (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36
		 * 
		 * PC - IE Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko
		 * 
		 * PC - edge Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36
		 * (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46
		 */
		StringBuffer buffer = new StringBuffer();
		String userAgent = request.getHeader("User-Agent");
		// mobile vs pc
		if (userAgent.indexOf("Mobile") > 0) {
			buffer.append("m-");
		} else {
			buffer.append("pc-");
		}

		// Browser
		if (userAgent.indexOf("Trident") > 0) {
			buffer.append("IE");
		} else if (userAgent.indexOf("OPT") > 0) {
			buffer.append("OPERA");
		} else if (userAgent.indexOf("Whale") > 0) {
			buffer.append("Whale");
		} else if (userAgent.indexOf("SamsungBrowser") > 0) {
			buffer.append("SamsungBrowser");
		} else if (userAgent.indexOf("Edg") > 0) {
			buffer.append("Edge");
		} else {
			buffer.append("Chrome");
		}

		return buffer.toString();
	}

	private ActionBean accessOutCtl(StoreBean... store) {
		HttpSession session = this.request.getSession();
		AuthDataAccessObject dao = new AuthDataAccessObject();
		Connection connection = dao.openConnection();
		ActionBean action = new ActionBean();
		String page = "index.jsp", message = null;
		boolean redirect = true, tran = false;

		/* DAO */
		if (dao == null) {
			((GroupBean) session.getAttribute("AccessInfo")).getStoreInfoList().get(0).getEmpList().get(0)
					.getAccessList().get(0).setAccessType(-1);

			dao.modifyTranStatus(connection, false);

			if (this.convertToBoolean(dao.insAccessLogin(connection,
					((GroupBean) session.getAttribute("AccessInfo")).getStoreInfoList().get(0)))) {
				tran = true;
			}

			dao.setTransaction(tran, connection);
			dao.modifyTranStatus(connection, true);
			dao.closeConnection(connection);
			/* 세션종료 */
			session.invalidate();
		} else {
			dao.insAccessLogin(connection, store[0]);
		}

		action.setPage(page);
		action.setRedirect(redirect);

		return action;
	}

	private ActionBean accessOut() {
		HttpSession session = this.request.getSession();
		ActionBean action = new ActionBean();
		String page = "index.jsp", message = null;
		boolean redirect = true, tran = false;
		AuthDataAccessObject dao = new AuthDataAccessObject();
		Connection connection = dao.openConnection();
		dao.modifyTranStatus(connection, false);
		GroupBean group = ((GroupBean) session.getAttribute("AccessInfo"));
		group.getStoreInfoList().get(0).getEmpList().get(0).getAccessList().get(0).setAccessType(-1);
		group.getStoreInfoList().get(0).getEmpList().get(0).getAccessList().get(0).setAccessState("I");
		group.getStoreInfoList().get(0).getEmpList().get(0).getAccessList().get(0).setAccessBrowser(getBrowser());
		group.getStoreInfoList().get(0).getEmpList().get(0).getAccessList().get(0)
				.setAccessLocate(request.getRemoteAddr());
		group.getStoreInfoList().get(0).getEmpList().get(0).getAccessList().get(0)
				.setAccessPublicIp(this.request.getParameter("accessPublicIp"));

		if (this.convertToBoolean(dao.insAccessLogin(connection, group.getStoreInfoList().get(0)))) {
			tran = true;
//			session = null;
//			session.removeAttribute("AccessInfo");
			session.invalidate();
		}

		dao.setTransaction(true, connection);
		dao.modifyTranStatus(connection, true);

		dao.closeConnection(connection);

		action.setPage(page);
		action.setRedirect(redirect);

		return action;
	}
	
	boolean isAccess(StoreBean store, Boolean isSession) {
		AuthDataAccessObject dao = new AuthDataAccessObject();
		boolean isAccess = false, 
				isDao = dao != null? true:false;

		if(!isDao) {
			Connection connection = dao.openConnection();
			connection = dao.openConnection();

			isAccess = this.convertToBoolean(
					dao.isAccess(connection, store, isSession));	

			dao.closeConnection(connection);
			dao = null;
		} else {
			Connection connection = dao.openConnection();
			isAccess = this.convertToBoolean(
					dao.isAccess(connection, store, isSession));
		}

		return isAccess;
	}

//	boolean isAccess(StoreBean store) {
//		AuthDataAccessObject dao = new AuthDataAccessObject();
//		boolean isAccess = false, isDao = dao != null ? true : false;
//
//		if (!isDao) {
//			Connection connection = dao.openConnection();
//
//			isAccess = this.convertToBoolean(dao.isAccess(connection, store));
//
//			dao.closeConnection(connection);
//			dao = null;
//		} else {
//			Connection connection = dao.openConnection();
//			isAccess = this.convertToBoolean(dao.isAccess(connection, store));
//		}
//
//		return isAccess;
//	}

	private boolean convertToBoolean(int value) {
		return value > 0 ? true : false;
	}

}
