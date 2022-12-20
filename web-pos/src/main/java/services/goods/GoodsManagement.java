package services.goods;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.ActionBean;
import services.auth.AuthDataAccessObject;

public class GoodsManagement {

	private HttpServletRequest request;
	HttpSession session;
	GoodsDataAccessObject dao;
	
	public ActionBean backController(int serviceCode, HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
		ActionBean action = null;
		session = this.request.getSession();
		dao = new GoodsDataAccessObject();
		
		
		return null;
	}

}
