package services.auth;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;

import beans.AccessLogBean;
import beans.ActionBean;
import beans.EmployeeBean;
import beans.GroupBean;
import beans.StoreBean;
import services.registration.RegDataAccessObject;

public class Auth {
	private HttpServletRequest request;

	public Auth() {

	}

	ActionBean action = null;

	public ActionBean backController(int serviceCode, HttpServletRequest request) {
		this.request = request;

		switch (serviceCode) {
		case 1:
			action = this.accessCtl();
			break;
		case 2:
			action = this.accessOutCtl();
			break;
		case 3:
			break;
		case 4:
			break;
		}

		return action;

	}

	private ActionBean accessCtl() {
		ActionBean action = new ActionBean();
		GroupBean group = null;
		StoreBean store = new StoreBean();
		EmployeeBean emp = new EmployeeBean();
		AccessLogBean accessLog = new AccessLogBean();
		ArrayList<AccessLogBean> accessLogList = new ArrayList<AccessLogBean>();
		ArrayList<EmployeeBean> empList = new ArrayList<EmployeeBean>();
		String page = "index.jsp", message = null;
		boolean redirect = true, tran = false;
		AuthDataAccessObject dao = new AuthDataAccessObject();

		store.setStoreCode(this.request.getParameter("storeCode"));
		emp.setEmpCode(this.request.getParameter("empCode"));
		emp.setEmpPin(this.request.getParameter("empPin"));
		
		accessLog.setAccessLocate(request.getRemoteAddr());
		accessLog.setAccessType("1");
		accessLogList.add(accessLog);
		emp.setAccessList(accessLogList);
		
		empList.add(emp);
		store.setEmpList(empList);

		// transaction control
		Connection connection = dao.openConnection();

		dao.modifyTranStatus(connection, false);
		/*
		 * 
		 * 
		 * */
		// EMPINFO

		// employCode로 레벨 정보 가져오기
		// isStoreCode
		
		if(this.convertToBoolean(dao.isStoreCode(connection, store))) {
			if(this.convertToBoolean(dao.isEmpCode(connection, store))) {
				if(this.convertToBoolean(dao.isEqualPinCode(connection, store))) {
					if(this.convertToBoolean(dao.insAccessLogin(connection, store))) {
						tran = true;
						redirect = false;
						group = dao.getAccessInfo(connection, store);
						if(group.getStoreInfoList().get(0)
								.getEmpList().get(0)
								.getEmpLevCode().equals("L1")) {
							dao.getStoreInfo(connection, group);
						}
						request.setAttribute("AccessInfo", group);
						page = !group.getStoreInfoList().get(0).getEmpList().get(0).getEmpLevCode().equals("L3")?"manager.jsp":"pos.html";
					}
				}
			}
		}
		
		dao.setTransaction(true, connection);
		dao.modifyTranStatus(connection, true);

		// dao close;
		dao.closeConnection(connection);
		System.out.println(group.getGroupCode());
		
		action.setPage(page);
		action.setRedirect(redirect);

		return action;
	}

	private ActionBean accessOutCtl() {

		return action;
	}

	private boolean convertToBoolean(int value) {
		return value > 0 ? true : false;
	}

}
