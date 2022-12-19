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

public class Auth {
	private HttpServletRequest req;
	private AuthDataAccessObject dao;
	private Connection connect;
	
	public Auth(HttpServletRequest req) {
		this.req = req;
	}
	
	public ActionBean backController(int serviceCode ) {
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
	
	private ActionBean initCtl() {
		ActionBean action = new ActionBean();
		String page = "index.jsp", message = null;
		boolean isForward = false, tran = false;
		
		HttpSession session = this.req.getSession();
		
		if(session.getAttribute("AccessInfo") != null) {
			page = "mgr.jsp";
			isForward = true;
		}
		
		action.setPage(page);
		action.setRedirect(isForward);
		return action;
	}
	
	private ActionBean accessCtl() {
		/* Job Controller Pattern */
		HttpSession session = null;
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
		
		access.setAccessPublicIp(this.req.getParameter("accessPublicIp"));
		access.setAccessLocation(this.req.getRemoteAddr());
		access.setAccessType(1);
		access.setAccessState("R");
		access.setAccessBrowser(message);
		accessList.add(access);
		
		emp.setEmpCode(this.req.getParameter("empCode"));
		emp.setEmpPin(this.req.getParameter("empPin"));
		emp.setAccessList(accessList);
		empList.add(emp);
		
		store.setStoreCode(this.req.getParameter("storeCode"));		
		store.setEmpList(empList);
		
		/* 2. DAO */
		this.dao = new AuthDataAccessObject();
		this.connect = this.dao.openConnection();
		this.dao.modifyTranStatus(connect, false);
		
		if(this.convertToBoolean(this.dao.isStoreCode(connect, store))) {
			if(this.convertToBoolean(this.dao.isEmpCode(connect, store))) {
				if(this.convertToBoolean(this.dao.isEqualPinCode(connect, store))) {
					/* 현재 액세스 여부 */
					if(this.convertToBoolean(this.dao.isAccess(connect, store))) {
						// 로그아웃
						this.accessOutCtl();
					}
					
					if(this.convertToBoolean(this.dao.insAccessLog(connect, store))) {
						session  = this.req.getSession();
						tran = true;
						isForward = true;
						group = this.dao.getAccessInfo(connect, store);
						if(group.getStoreInfoList().get(0)
								.getEmpList().get(0)
								.getEmpLevCode().equals("L1")) {
							this.dao.getStoreInfo(connect, group);
						}
						group.getStoreInfoList().get(0).getEmpList().get(0).setEmpPin(null);
						session.setAttribute("AccessInfo", group);
						page = !group.getStoreInfoList().get(0)
								.getEmpList().get(0)
								.getEmpLevCode().equals("L3")? "mgr.jsp":"sales.jsp";
					}
				}
			}
		}
				
		this.dao.setTransaction(tran, connect);
		this.dao.modifyTranStatus(connect, true);
		this.dao.closeConnection(connect);
		this.dao = null;
		
		action.setPage(page);
		action.setRedirect(isForward);
		return action;
	}
	
	private ActionBean accessOutCtl() {
		/* Job Controller Pattern */
		HttpSession session = this.req.getSession();
		ActionBean action = new ActionBean();
		String page = "index.jsp", message = null;
		boolean isForward = false, tran = false;
		
		/* 현재 세션 확인 */
		if(session.getAttribute("AccessInfo") != null) {
			/* DAO */
			this.dao = new AuthDataAccessObject();
			this.connect = this.dao.openConnection();
			this.dao.modifyTranStatus(connect, false);
			
			((GroupBean)session.getAttribute("AccessInfo")).getStoreInfoList().get(0).getEmpList().get(0).getAccessList().get(0).setAccessType(-1);
			if(this.convertToBoolean(this.dao.insAccessLog(connect, 
					((GroupBean)session.getAttribute("AccessInfo")).getStoreInfoList().get(0)))){
				tran = true;
			}
			
			this.dao.setTransaction(tran, connect);
			this.dao.modifyTranStatus(connect, true);
			this.dao.closeConnection(connect);
			this.dao = null;
		}
		action.setPage(page);
		action.setRedirect(isForward);
		return action;
	}
	
	private String getBrowser() {
		String result = null;
		
		return result;
	}
	private boolean convertToBoolean(int value) {
		return (value>0)? true: false;
	}
}
