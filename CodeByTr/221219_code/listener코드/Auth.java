package services.auth;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.AccessLogBean;
import beans.ActionBean;
import beans.EmployeesBean;
import beans.GroupBean;
import beans.StoreBean;
import controller.ClientInfo;

public class Auth {
	private HttpServletRequest req;
	private HttpSession session;
	private AuthDataAccessObject dao;
	private Connection connect;

	public Auth() {	}
	
	public Auth(HttpServletRequest req) {
		this.req = req;
		this.session = this.req.getSession();
		this.session.setMaxInactiveInterval(60);
	}
	
	public ActionBean backController(int serviceCode) {
		ActionBean action = null;
		switch(serviceCode) {
		case 1:
			action = this.accessCtl();
			break;
		case -1:
			action = this.accessOutCtl();
			break;
		case 0:
			action = this.initCtl();
			break;
		}

		return action;
	}

	public Object backController(int serviceCode, Object obj) {
		Object result = null;
		switch(serviceCode) {
		case 999:
			result = this.isAccess((StoreBean)obj, true);
			break;
		}
		return result;
	}

	private ActionBean initCtl() {
		ActionBean action = new ActionBean();
		String page = "index.jsp";
		boolean isForward = false;

		if(session.getAttribute("AccessInfo") != null) {
			System.out.println("Landing : 세션이 살아있는 경우");
			System.out.println(((GroupBean)session.getAttribute("AccessInfo")).getStoreInfoList().get(0).getEmpList().get(0).getAccessList().get(0).getAccessBrowser());
			if(this.isAccess(
					((GroupBean)session.getAttribute("AccessInfo")).getStoreInfoList().get(0), true)){
				page = "mgr.jsp";
				isForward = true;
			}
		}

		action.setPage(page);
		action.setRedirect(isForward);
		return action;
	}

	private ActionBean accessCtl() {
		/* Job Controller Pattern */
		ActionBean action = new ActionBean();
		String page = "index.jsp", message = null;
		boolean isForward = false, tran = false;

		/* 1. Client Data : storeCode, empCode, empPin >> Bean */
		GroupBean group = null;
		StoreBean store = new StoreBean();
		ArrayList<EmployeesBean> empList = new ArrayList<EmployeesBean>();
		EmployeesBean emp = new EmployeesBean();
		AccessLogBean access = new AccessLogBean();
		ArrayList<AccessLogBean> accessList = new ArrayList<AccessLogBean>();

		if(session.getAttribute("AccessInfo") == null) {
			access.setAccessPublicIp(this.req.getParameter("accessPublicIp"));
			access.setAccessLocation(this.req.getRemoteAddr());
			access.setAccessType(1);
			access.setAccessState("R");
			access.setAccessBrowser(ClientInfo.getBrowserInfo(this.req.getHeader("user-agent")));
			accessList.add(access);

			emp.setEmpCode(this.req.getParameter("empCode"));
			emp.setEmpPin(this.req.getParameter("empPin"));
			emp.setAccessList(accessList);
			empList.add(emp);

			store.setStoreCode(this.req.getParameter("storeCode"));		
			store.setEmpList(empList);		

			/* Browser 정보 */
			String browserInfo = (store.getEmpList().get(0).getAccessList().get(0).getAccessBrowser());
			/* 2. DAO */
			this.dao = new AuthDataAccessObject();
			this.connect = this.dao.openConnection();
			this.dao.modifyTranStatus(this.connect, false);

			if(this.convertToBoolean(this.dao.isStoreCode(this.connect, store))) {
				if(this.convertToBoolean(this.dao.isEmpCode(this.connect, store))) {
					if(this.convertToBoolean(this.dao.isEqualPinCode(this.connect, store))) {
						/* 현재 액세스 여부 */
						if(!this.isAccess(store, true)) {
							System.out.println("세션은 없고 자신의 로그인 기록이 없는 경우");
							System.out.println(browserInfo);

							/* 다른 로그인 정보 확인 */
							if(this.isAccess(store, false)) {
								System.out.println("세션은 없고 자신의 로그인 기록은 없지만 다른 브라우저의 로그인 기록은 있는 경우");
								System.out.println(browserInfo);

								// 다른 로그인 정보 수집 >> 로그아웃
								StoreBean beforeAccess = this.dao.getBeforeAccess(connect, store);
								beforeAccess.getEmpList().get(0).getAccessList().get(0).setAccessType(-1);
								beforeAccess.getEmpList().get(0).getAccessList().get(0).setAccessState("I");
								this.accessOutCtl(beforeAccess);
							}

							if(this.convertToBoolean(this.dao.insAccessLog(this.connect, store))) {
								tran = true;
								isForward = true;
								group = this.dao.getAccessInfo(connect, store);

								if(group.getStoreInfoList().get(0)
										.getEmpList().get(0)
										.getEmpLevCode().equals("L1")) {
									this.dao.getStoreInfo(connect, group);
								}
								/* pin 삭제 */
								group.getStoreInfoList().get(0).getEmpList().get(0).setEmpPin(null);

								this.session.setAttribute("AccessInfo", group);
								page = !group.getStoreInfoList().get(0)
										.getEmpList().get(0)
										.getEmpLevCode().equals("L3")? "mgr.jsp":"sales.jsp";
							}
						}
					}
				}
			}

			this.dao.setTransaction(tran, connect);
			this.dao.modifyTranStatus(connect, true);
			this.dao.closeConnection(connect);
			this.dao = null;
		}else {
			/* Browser 정보 */
			String browserInfo = ((GroupBean)this.session.getAttribute("AccessInfo")).getStoreInfoList().get(0).getEmpList().get(0).getAccessList().get(0).getAccessBrowser();

			/* 새로고침 영역 */
			if(this.isAccess(
					((GroupBean)this.session.getAttribute("AccessInfo"))
					.getStoreInfoList().get(0), true)) {
				System.out.println("세션은 있고 자신의 로그인 기록이 있는 경우");
				System.out.println(browserInfo);
				page = "mgr.jsp";
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

	private ActionBean accessOutCtl(StoreBean...store) {
		/* Job Controller Pattern */
		ActionBean action = new ActionBean();
		String page = "index.jsp";
		boolean isForward = false, tran = false;

		/* DAO */
		if(this.dao == null) {
			this.dao = new AuthDataAccessObject();
			this.connect = this.dao.openConnection();
			this.dao.modifyTranStatus(connect, false);
			
			if((GroupBean)this.session.getAttribute("AccessInfo") != null) {
				StoreBean accessOut = ((GroupBean)this.session.getAttribute("AccessInfo"))
						.getStoreInfoList().get(0);
				accessOut.getEmpList().get(0).getAccessList().get(0).setAccessType(-1);
				accessOut.getEmpList().get(0).getAccessList().get(0).setAccessState("R");

				if(this.isAccess(accessOut, true)) {
					if(this.convertToBoolean(this.dao.insAccessLog(connect, 
							((GroupBean)this.session.getAttribute("AccessInfo")).getStoreInfoList().get(0)))){
						tran = true;
					}
				}
			}

			this.dao.setTransaction(tran, connect);
			this.dao.modifyTranStatus(connect, true);
			this.dao.closeConnection(connect);
			this.dao = null;
			/* 세션종료 */
			session.invalidate();
		}else{
			this.dao.insAccessLog(connect, store[0]);	
		}

		action.setPage(page);
		action.setRedirect(isForward);

		return action;
	}

	boolean isAccess(StoreBean store, Boolean isSession) {
		boolean isAccess = false, 
				isDao = this.dao != null? true:false;

		if(!isDao) {
			this.dao = new AuthDataAccessObject();
			this.connect = this.dao.openConnection();

			isAccess = this.convertToBoolean(
					this.dao.isAccess(connect, store, isSession));	

			this.dao.closeConnection(connect);
			this.dao = null;
		} else {
			isAccess = this.convertToBoolean(
					this.dao.isAccess(connect, store, isSession));
		}

		return isAccess;
	}

	private boolean convertToBoolean(int value) {
		return (value>0)? true: false;
	}
}
