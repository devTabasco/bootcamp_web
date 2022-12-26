package services.goods;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import beans.ActionBean;
import beans.CategoriesBean;
import beans.StoreBean;
import services.auth.Auth;

public class GoodsManagement {
	private HttpServletRequest req;
	private HttpSession session;
	private GoodsDataAccessObject dao;
	private Connection connect;
	
	public GoodsManagement() {}
	public GoodsManagement(HttpServletRequest req) {
		this.req = req;
		this.session = this.req.getSession();
	}
	
	public ActionBean backController(int serviceCode) {
		ActionBean action = null;
		if(this.session.getAttribute("AccessInfo") != null) {
			switch(serviceCode) {
			case 1:
				action = this.getCategoryListCtl();
				break;
			case 2:
				action = this.insCategoryCtl();
				break;
			case 3:
				action = this.updCategoryCtl();
				break;
			}
		} else {
			action = new ActionBean();
			try {
				action.setPage("index.jsp?message="+ URLEncoder.encode("에러메세지:로그인 후 사용하실 수 있습니다.:1", "UTF-8"));
			} catch (UnsupportedEncodingException e) {e.printStackTrace();}
			action.setRedirect(false);
		}
		return action;
	}
	
	private ActionBean getCategoryListCtl() {
		ActionBean action = new ActionBean();
		String page = "mgr.jsp", message = null; 
		boolean isForward = true;
		
		/* ClientData >> Bean : storeCode, levCode */
		StoreBean clientData = null;
		ArrayList<CategoriesBean> categoryList = null;
		CategoriesBean category = null;
		
		if(this.req.getParameter("storeCode") != null 
				&& this.req.getParameter("levCode") != null) {
			clientData = new StoreBean();
			clientData.setStoreCode(this.req.getParameter("storeCode"));
			categoryList = new ArrayList<CategoriesBean>();
			category = new CategoriesBean();
			category.setLevCode(this.req.getParameter("levCode"));
			categoryList.add(category);
			clientData.setCateList(categoryList);
		
			this.dao = new GoodsDataAccessObject();
			this.connect = this.dao.openConnection();
			categoryList = this.dao.getCatagoryList(connect, clientData);
			this.dao.closeConnection(connect);

			if(categoryList != null) {
				this.req.setAttribute("serverData", new Gson().toJson(categoryList));
				this.req.setAttribute("options", (new Auth(this.session).backController(998, this.req)).toString());
			}
		}else {
			message = "에러메세지:상점코드와 분류코드 일부분이 전송되어져야합니다.:1";
			try {
				page += "?message=" + URLEncoder.encode(message, "UTF-8");
			} catch (UnsupportedEncodingException e) {e.printStackTrace();}
		}
		
		action.setPage(page);
		action.setRedirect(isForward);
		return action;
	}
	
	private ActionBean insCategoryCtl() {
		ActionBean action = new ActionBean();
		String page = "index.jsp", message = null; 
		boolean isForward = false, tran = false;
		
		/* ClientData --> StoreBean */
		
		/* DAO : open modify set modify close */
		
		/* forward */
		
		action.setPage(page);
		action.setRedirect(isForward);
		return action;
	}
	
	private ActionBean updCategoryCtl() {
		ActionBean action = new ActionBean();
		String page = "index.jsp", message = null; 
		boolean isForward = false, tran = false;
		
		
		
		action.setPage(page);
		action.setRedirect(isForward);
		return action;
	}
	
	private int getMaxIdx() {
		int maxIdx = -1;
		
		return maxIdx;
	}
}
