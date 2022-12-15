package services.auth;

import javax.servlet.http.HttpServletRequest;


public class Auth {
	private HttpServletRequest request;

	public void backController(int serviceCode, HttpServletRequest request) {
		this.request = request;
		
		switch (serviceCode) {
		case 1:
			this.accessCtl();
			break;

		default:
			break;
		}
		
	}
	
	private void accessCtl() {
		System.out.println(this.request.getParameter("memberId"));
		System.out.println(this.request.getParameter("memberPassword"));
	}

}
