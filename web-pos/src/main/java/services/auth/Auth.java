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

		if (session.getAttribute("AccessInfo") != null) {
			page = "manager.jsp";
			redirect = false;
		}

		action.setPage(page);
		action.setRedirect(redirect);

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
		HttpSession session = null;

		store.setStoreCode(this.request.getParameter("storeCode"));
		emp.setEmpCode(this.request.getParameter("empCode"));
		emp.setEmpPin(this.request.getParameter("empPin"));

		accessLog.setAccessPublicIp(this.request.getParameter("accessPublicIp"));
		accessLog.setAccessState("R");
		accessLog.setAccessBrowser(getBrowser());
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

		if (this.convertToBoolean(dao.isStoreCode(connection, store))) {
			if (this.convertToBoolean(dao.isEmpCode(connection, store))) {
				if (this.convertToBoolean(dao.isEqualPinCode(connection, store))) {
					if (this.convertToBoolean(dao.insAccessLogin(connection, store))) {
						session = this.request.getSession();
						tran = true;
						redirect = false;
						group = dao.getAccessInfo(connection, store);
						if (group.getStoreInfoList().get(0).getEmpList().get(0).getEmpLevCode().equals("L1")) {
							dao.getStoreInfo(connection, group);
						}

						session.setAttribute("AccessInfo", group);
						page = !group.getStoreInfoList().get(0).getEmpList().get(0).getEmpLevCode().equals("L3")
								? "manager.jsp"
								: "pos.html";
					}
				}
			}
		}

		dao.setTransaction(true, connection);
		dao.modifyTranStatus(connection, true);

		// dao close;
		dao.closeConnection(connection);

		action.setPage(page);
		action.setRedirect(redirect);

		return action;
	}

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
		//mobile vs pc
		if(userAgent.indexOf("Mobile")>0) {
			buffer.append("m-");
		}else {
			buffer.append("pc-");			
		}
		
		//Browser
		if(userAgent.indexOf("Trident")>0) {
			buffer.append("IE");
		}else if(userAgent.indexOf("OPT")>0) {
			buffer.append("OPERA");			
		}else if(userAgent.indexOf("Whale")>0) {
			buffer.append("Whale");			
		}else if(userAgent.indexOf("SamsungBrowser")>0) {
			buffer.append("SamsungBrowser");			
		}else if(userAgent.indexOf("Edg")>0) {
			buffer.append("Edge");			
		}else {
			buffer.append("Chrome");			
		}
		
		return buffer.toString();
	}

	private ActionBean accessOutCtl() {

		return action;
	}

	private boolean convertToBoolean(int value) {
		return value > 0 ? true : false;
	}

}
