package services.auth;

import javax.servlet.http.HttpServletRequest;

import beans.ActionBean;
/*
 * - login
 * - logout
 * - 암호화
 * */

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
		
		System.out.println("로그인 성공?");
		
		return action;
	}
	
	private ActionBean accessOutCtl() {
		
		return action;
	}
	
	

}
