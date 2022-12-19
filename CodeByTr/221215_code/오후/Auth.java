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
	private AuthDataAccessObject dao;
	private Connection connect;
	
	public Auth(HttpServletRequest req) {
		this.req = req;
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
			result = this.isAccess((StoreBean)obj);
			break;
		}
		return result;
	}
	
	private ActionBean initCtl() {
		ActionBean action = new ActionBean();
		String page = "index.jsp";
		boolean isForward = false;
		
		HttpSession session = this.req.getSession();
		
		if(session.getAttribute("AccessInfo") != null) {
			if(this.isAccess(
					((GroupBean)session.getAttribute("AccessInfo")).getStoreInfoList().get(0))){
				page = "mgr.jsp";
				isForward = true;
			}else session.invalidate();
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
		access.setAccessBrowser(ClientInfo.getBrowserInfo(this.req.getHeader("user-agent")));
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
		this.dao.modifyTranStatus(this.connect, false);
		
		if(this.convertToBoolean(this.dao.isStoreCode(this.connect, store))) {
			if(this.convertToBoolean(this.dao.isEmpCode(this.connect, store))) {
				if(this.convertToBoolean(this.dao.isEqualPinCode(this.connect, store))) {
					/* 현재 액세스 여부 */
					if(this.isAccess(store)) {
						// 로그아웃
						store.getEmpList().get(0).getAccessList().get(0).setAccessType(-1);
						store.getEmpList().get(0).getAccessList().get(0).setAccessState("I");
						this.accessOutCtl(store);
						store.getEmpList().get(0).getAccessList().get(0).setAccessType(1);
						store.getEmpList().get(0).getAccessList().get(0).setAccessState("R");
					}
					
					if(this.convertToBoolean(this.dao.insAccessLog(this.connect, store))) {
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
	
	private ActionBean accessOutCtl(StoreBean...store) {
		/* Job Controller Pattern */
		HttpSession session = this.req.getSession();
		ActionBean action = new ActionBean();
		String page = "index.jsp";
		boolean isForward = false, tran = false;
		
		/* DAO */
		if(this.dao == null) {
			((GroupBean)session.getAttribute("AccessInfo"))
			.getStoreInfoList().get(0).getEmpList().get(0)
			.getAccessList().get(0).setAccessType(-1);
			
			this.dao = new AuthDataAccessObject();
			this.connect = this.dao.openConnection();
			this.dao.modifyTranStatus(connect, false);
	
			if(this.convertToBoolean(this.dao.insAccessLog(connect, 
					((GroupBean)session.getAttribute("AccessInfo")).getStoreInfoList().get(0)))){
				tran = true;
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
	
	boolean isAccess(StoreBean store) {
		boolean isAccess = false, 
				isDao = this.dao != null? true:false;
		
		if(!isDao) {
			this.dao = new AuthDataAccessObject();
			this.connect = this.dao.openConnection();
			
			isAccess = this.convertToBoolean(
					this.dao.isAccess(connect, store));	
			
			this.dao.closeConnection(connect);
			this.dao = null;
		} else {
			isAccess = this.convertToBoolean(
					this.dao.isAccess(connect, store));
		}
			
		return isAccess;
	}
	
	private String getBrowser() {
		String result = null;
		
		return result;
	}
	private boolean convertToBoolean(int value) {
		return (value>0)? true: false;
	}
}
