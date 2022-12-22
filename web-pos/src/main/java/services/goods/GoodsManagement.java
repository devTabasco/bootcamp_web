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
import services.auth.AuthDataAccessObject;

public class GoodsManagement {

	private HttpServletRequest request;
	HttpSession session;
	GoodsDataAccessObject dao; //Heap은 Null;
	Connection connection;

	public ActionBean backController(int serviceCode, HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
		ActionBean action = null;
		session = this.request.getSession();
		dao = new GoodsDataAccessObject();

		if (this.session.getAttribute("AccessInfo") != null) {
			switch (serviceCode) {
			case 1:
				action = this.getCategory();
				break;
			case 2:
				action = this.insCategoryCtl();
				break;
			case 0:
				break;
			}
		} else {
			action = new ActionBean();
			
			try {
			action.setPage("index.jsp?message="+URLEncoder.encode("잘못된 접근 입니다.","UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			action.setRedirect(false);
		}

		return action;
	}

	private ActionBean getCategory() {
		ActionBean action = new ActionBean();
		String page = "manager.jsp", message = null;
		boolean redirect = true, tran = false;
		StoreBean store = null;
		CategoriesBean category;
		ArrayList<CategoriesBean> categoryList;

		if (this.request.getParameter("storeCode") != null && this.request.getParameter("levCode") != null) {
			store = new StoreBean();
			store.setStoreCode(this.request.getParameter("storeCode"));
			categoryList = new ArrayList<CategoriesBean>();
			category = new CategoriesBean();
			category.setLevCode(this.request.getParameter("levCode"));
			categoryList.add(category);
			store.setLevInfo(categoryList);

			this.connection = this.dao.openConnection();
			dao.modifyTranStatus(connection, false);
			categoryList = this.dao.getCategoryList(connection, store);
			this.dao.closeConnection(connection);

			if (categoryList != null) {
				this.request.setAttribute("serverData", new Gson().toJson(categoryList));
				this.request.setAttribute("options", (new Auth(this.session).backLoadController(998, this.request)).toString());
				System.out.println(this.request.getAttribute("options")+"options확인");
			}
			redirect = false;

		} else {
			message = "상점코드와 분류코드 일부분이 전송되어져야합니다.";
			try {
				page += "?message=" + URLEncoder.encode(message, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		action.setPage(page);
		action.setRedirect(redirect);
		return action;

	}

	private ActionBean insCategoryCtl() {
		ActionBean action = new ActionBean();
		String page = "index.jsp", message = null;
		boolean redirect = true, tran = false;
		StoreBean store = new StoreBean();
		CategoriesBean category = new CategoriesBean();
		ArrayList<CategoriesBean> categoryList = new ArrayList<CategoriesBean>();
		
		/* ClientData --> StoreBean */
		System.out.println(this.request.getParameter("storeCode"));
		System.out.println(this.request.getParameter("levCode"));
		System.out.println(this.request.getParameter("levName"));
		System.out.println(this.request.getParameter("currentIdx"));
		
		store.setStoreCode(this.request.getParameter("storeCode"));
		category.setLevCode(this.request.getParameter("levCode"));
		category.setLevName(this.request.getParameter("levName"));
		
		categoryList.add(category);
		store.setLevInfo(categoryList);
		
		/* DAO : open modify set modify close */
		this.connection = this.dao.openConnection();
		dao.modifyTranStatus(connection, false);

		//dao 전달
		int getMax = 0;
		if(convertToBoolean(getMax = dao.getMaxCode(connection, store))) {
			store.getLevInfo().get(0).setLevCode(category.getLevCode() + getMax);
		}
		
		if(convertToBoolean(this.dao.insCategoryItem(connection, store))){
			this.request.setAttribute("currentIdx", this.request.getParameter("currentIdx"));
			page = "GetGoodsCategory";
			redirect = false;
		};
		
		dao.setTransaction(true, connection);
		dao.modifyTranStatus(connection, true);
		this.dao.closeConnection(connection);
		this.dao = null;
		
		/* forward */
		
		action.setPage(page);
		action.setRedirect(redirect);
		return action;
	}

	private ActionBean updCategoryCtl() {
		ActionBean action = new ActionBean();
		String page = "index.jsp", message = null;
		boolean redirect = true, tran = false;

		action.setPage(page);
		action.setRedirect(redirect);
		return action;
	}

	private int getMaxIdx() {
		int maxIdx = -1;

		return maxIdx;
	}
	
	private boolean convertToBoolean(int value) {
		return value > 0 ? true : false;
	}

}
