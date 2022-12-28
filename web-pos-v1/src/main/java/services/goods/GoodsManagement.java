package services.goods;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import beans.ActionBean;
import beans.CategoriesBean;
import beans.GoodsBean;
import beans.GroupBean;
import beans.StoreBean;
import services.auth.Auth;

public class GoodsManagement {
	private HttpServletRequest req;
	private HttpSession session;
	private GoodsDataAccessObject dao;
	private Connection connect;

	public GoodsManagement() {
	}

	public GoodsManagement(HttpServletRequest req) {
		this.req = req;
		this.session = this.req.getSession();
	}

	public ActionBean backController(int serviceCode) {
		ActionBean action = null;
		if (this.session.getAttribute("AccessInfo") != null) {
			switch (serviceCode) {
			case 1:
				action = this.getCategoryListCtl();
				break;
			case 2:
				action = this.insCategoryCtl();
				break;
			case 3:
				action = this.updCategoryCtl();
				break;
			case 4:
				action = this.getGoodsListCtl();
				break;
			}
		} else {
			action = new ActionBean();
			try {
				action.setPage("index.jsp?message=" + URLEncoder.encode("에러메세지:로그인 후 사용하실 수 있습니다.:1", "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			action.setRedirect(false);
		}
		return action;
	}
	
	private ActionBean getGoodsListCtl() {
		ActionBean action = new ActionBean();
		String page = "mgr.jsp", message = null; 
		boolean isForward = true;
		
		/* ClientData >> Bean : storeCode */
		StoreBean store = null;
		ArrayList<CategoriesBean> categoryList = null;
		ArrayList<GoodsBean> goodsList = null;
		
		if(this.req.getParameter("storeCode") != null ){
			store = new StoreBean();
			store.setStoreCode(this.req.getParameter("storeCode"));
							
			this.dao = new GoodsDataAccessObject();
			this.connect = this.dao.openConnection();
			
			goodsList = this.dao.getGoodsList(connect, store);
			categoryList = this.dao.getGoodsCategoryList(connect, store);
			store.setGoodsList(goodsList);
			store.setCateList(categoryList);
			this.req.setAttribute("menuCode", dao.getStoreMenuCode(connect, store));
			
			this.dao.closeConnection(connect);
			this.dao = null;
			
			this.req.setAttribute("serverData", new Gson().toJson(store));
			this.req.setAttribute("options", (new Auth(this.session).backController(998, this.req)).toString());
			
		}else {
			message = "에러메세지:상점코드가 전송되어져야합니다.:1";
			try {
				page += "?message=" + URLEncoder.encode(message, "UTF-8");
			} catch (UnsupportedEncodingException e) {e.printStackTrace();}
		}
			
		action.setPage(page);
		action.setRedirect(isForward);
		return action;
	}
	
//	private ActionBean getGoodsListCtl() {
//		ActionBean action = new ActionBean();
//		String page = "mgr.jsp", message = null;
//		boolean isForward = true, tran = false;
//		
//		/* ClientData --> StoreBean */
//		StoreBean storeBean = null;
//		ArrayList<CategoriesBean> categoryList = null;
//		CategoriesBean categoriesBean = null;
//
//		if (this.req.getParameter("storeCode") != null) {
//			storeBean = new StoreBean();
//			categoriesBean = new CategoriesBean();
//			categoryList = new ArrayList<CategoriesBean>();
//
//			storeBean.setStoreCode(this.req.getParameter("storeCode"));
//
//			dao = new GoodsDataAccessObject();
//			connect = dao.openConnection();
//			dao.modifyTranStatus(connect, false);
//			
//			
//			
//			// 1. 상품코드 보내주기
//			System.out.println(dao.getStoreMenuCode(connect, storeBean));
//			this.req.setAttribute("menuCode", dao.getStoreMenuCode(connect, storeBean));
//			
//			// 2. 상품분류코드 리스트
//			categoriesBean.setLevCode("C");
//			categoryList.add(categoriesBean);
//			storeBean.setCateList(categoryList);
//			System.out.println(dao.getCatagoryList(connect, storeBean).size());
//			this.req.setAttribute("categoryCode", makeHTMLSelectBox(1,dao.getCatagoryList(connect, storeBean)));
//
//			// 3. 상품상태코드 리스트
//			categoriesBean.setLevCode("S");
//			categoryList.remove(0);
//			categoryList.add(categoriesBean);
//			storeBean.setCateList(categoryList);
//			System.out.println(dao.getCatagoryList(connect, storeBean).size());
//			this.req.setAttribute("stateCode", makeHTMLSelectBox(2,dao.getCatagoryList(connect, storeBean)));
//			
//			//4. 상품리스트 가져오기
//			
//			
//			this.req.setAttribute("serverData", new Gson().toJson(categoryList));
//			this.req.setAttribute("options", (new Auth(this.session).backController(998, this.req)).toString());
//			
//			/* DAO : open modify set modify close */
//			dao.setTransaction(true, connect);
//			dao.modifyTranStatus(connect, true);
//			dao.closeConnection(connect);
//			dao = null;
//
//		}
//
//		/* forward */
//		action.setPage(page);
//		action.setRedirect(isForward);
//		
//		return action;
//	}

	private ActionBean getCategoryListCtl() {
		ActionBean action = new ActionBean();
		String page = "mgr.jsp", message = null;
		boolean isForward = true;

		/* ClientData >> Bean : storeCode, levCode */
		StoreBean clientData = null;
		ArrayList<CategoriesBean> categoryList = null;
		CategoriesBean category = null;

		if (this.req.getParameter("storeCode") != null && this.req.getParameter("levCode") != null) {
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

			this.req.setAttribute("serverData", new Gson().toJson(categoryList));
			this.req.setAttribute("options", (new Auth(this.session).backController(998, this.req)).toString());
		} else {
			message = "에러메세지:상점코드와 분류코드 일부분이 전송되어져야합니다.:1";
			try {
				page += "?message=" + URLEncoder.encode(message, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		action.setPage(page);
		action.setRedirect(isForward);
		return action;
	}

	private ActionBean insCategoryCtl() {
		ActionBean action = new ActionBean();
		String page = "mgr.jsp", message = null;
		boolean isForward = true, tran = false;

		/* ClientData --> StoreBean */
		StoreBean storeBean = null;
		ArrayList<CategoriesBean> categoryList = null;
		CategoriesBean categoriesBean = null;

		if (this.req.getParameter("storeCode") != null && this.req.getParameter("levCode") != null
				&& this.req.getParameter("levName") != null) {
			storeBean = new StoreBean();
			categoriesBean = new CategoriesBean();
			categoryList = new ArrayList<CategoriesBean>();
//			Map<String, String[]> clientData = this.req.getParameterMap();

			categoriesBean.setLevCode(this.req.getParameter("levCode"));
			categoriesBean.setLevName(this.req.getParameter("levName"));
			categoryList.add(categoriesBean);
			storeBean.setCateList(categoryList);
			storeBean.setStoreCode(this.req.getParameter("storeCode"));

			dao = new GoodsDataAccessObject();
			connect = dao.openConnection();
			dao.modifyTranStatus(connect, false);

			// 1.현재 할당된 분류코드의 숫자부분의 최대값 >> +1 >> levCode += ?
			categoriesBean.setLevCode(categoriesBean.getLevCode() + dao.getMaxCode(connect, storeBean));
			categoryList.add(categoriesBean);
			storeBean.setCateList(categoryList);

			// 2.Ins Category
			if (tran = this.convertToBoolean(dao.insCategoryItem(connect, storeBean))) {
				storeBean.getCateList().get(0).setLevCode(storeBean.getCateList().get(0).getLevCode().substring(0, 1));
				categoryList = this.dao.getCatagoryList(connect, storeBean);

				this.req.setAttribute("serverData", new Gson().toJson(categoryList));
				this.req.setAttribute("options", (new Auth(this.session).backController(998, this.req)).toString());
			} else {
				// 기존에 분류코드 데이터가 없을 때
			}

			dao.setTransaction(tran, connect);
			dao.modifyTranStatus(connect, true);
			dao.closeConnection(connect);
			dao = null;

		}

		/* DAO : open modify set modify close */

		/* forward */
		action.setPage(page);
		action.setRedirect(isForward);
		return action;
	}

	private ActionBean updCategoryCtl() {
		ActionBean action = new ActionBean();
		String page = "mgr.jsp", message = null;
		boolean isForward = true, tran = false;

		/* ClientData --> StoreBean */
		StoreBean storeBean = null;
		ArrayList<CategoriesBean> categoryList = null;
		CategoriesBean categoriesBean = null;

		if (this.req.getParameter("storeCode") != null && this.req.getParameter("levCode") != null
				&& this.req.getParameter("levName") != null) {
			storeBean = new StoreBean();
			categoriesBean = new CategoriesBean();
			categoryList = new ArrayList<CategoriesBean>();
//			Map<String, String[]> clientData = this.req.getParameterMap();

			categoriesBean.setLevCode(this.req.getParameter("levCode"));
			categoriesBean.setLevName(this.req.getParameter("levName"));
			categoryList.add(categoriesBean);
			storeBean.setCateList(categoryList);
			storeBean.setStoreCode(this.req.getParameter("storeCode"));

			dao = new GoodsDataAccessObject();
			connect = dao.openConnection();
			dao.modifyTranStatus(connect, false);

			// updCategoryItem
			if (tran = this.convertToBoolean(dao.updCategoryItem(connect, storeBean))) {
				categoryList = this.dao.getCatagoryList(connect, storeBean);
				this.req.setAttribute("serverData", new Gson().toJson(categoryList));
				this.req.setAttribute("options", (new Auth(this.session).backController(998, this.req)).toString());
			} else {
				// 기존에 분류코드 데이터가 없을 때
			}

			dao.setTransaction(tran, connect);
			dao.modifyTranStatus(connect, true);
			dao.closeConnection(connect);
			dao = null;

		}

		action.setPage(page);
		action.setRedirect(isForward);
		return action;
	}
	
	private String makeHTMLSelectBox(int index, Object obj) {
		StringBuffer select = new StringBuffer();
		select.append("<select name=\"levelInfo\" class=\"box big\">");
		if(index==1) select.append("<option value=\"\">상품 분류를 선택해주세요</option>");
		if(index==2) select.append("<option value=\"\">상품 상태를 선택해주세요</option>");
		for(CategoriesBean c : ((ArrayList<CategoriesBean>)obj)) {
			select.append("<option value=\"" + c.getLevCode() + "\">" + c.getLevName() + "</option>");
		}
		
		select.append("</select>");
		return select.toString();
	}

	private int getMaxIdx() {
		int maxIdx = -1;

		return maxIdx;
	}

	private boolean convertToBoolean(int value) {
		return value > 0 ? true : false;
	}
}
