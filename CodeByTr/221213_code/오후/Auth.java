package services.auth;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import beans.AccessLogBean;
import beans.ActionBean;
import beans.EmployeesBean;
import beans.GroupBean;
import beans.StoreBean;
import services.registration.RegDataAccessObject;

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
		}
		
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
		
		access.setAccessLocation(this.req.getRemoteAddr());
		access.setAccessType(1);
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
					if(this.convertToBoolean(this.dao.insAccessLog(connect, store))) {
						tran = true;
						isForward = true;
						group = this.dao.getAccessInfo(connect, store);
						if(group.getStoreInfoList().get(0)
								.getEmpList().get(0)
								.getEmpLevCode().equals("L1")) {
							this.dao.getStoreInfo(connect, group);
						}
					}
				}
			}
		}
				
		this.dao.setTransaction(tran, connect);
		this.dao.modifyTranStatus(connect, true);
		this.dao.closeConnection(connect);
		this.dao = null;
		
		System.out.println(group.getGroupCode());
		
		return null;
	}
	private ActionBean accessOutCtl() {
		
		return null;
	}
	
	private boolean convertToBoolean(int value) {
		return (value>0)? true: false;
	}
}
