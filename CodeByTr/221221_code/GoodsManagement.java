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
import services.auth.AuthDataAccessObject;

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
			action.setPage("index.jsp");
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
		
			this.connect = this.dao.openConnection();
			categoryList = this.dao.getCatagoryList(connect, clientData);
			this.dao.closeConnection(connect);
			
			if(categoryList != null) this.req.setAttribute("categoryList", new Gson().toJson(categoryList));
			
			
		}else {
			message = "상점코드와 분류코드 일부분이 전송되어져야합니다.";
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
