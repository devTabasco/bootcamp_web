package services.goods;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;

import beans.ActionBean;
import beans.CategoriesBean;
import beans.GoodsBean;
import beans.GroupBean;
import beans.StoreBean;
import beans.StoreImages;
import services.auth.Auth;
import utils.CommonImageFileManager;

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
			case 4:
				action = this.getGoodsListCtl();
				break;
			case 5:

				action = this.insGoodsCtl();
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

	private ActionBean insGoodsCtl() {
		ActionBean action = new ActionBean();
		String page = "mgr.jsp", message = null; 
		boolean isForward = true;

		/* Bean Data */
		GroupBean group = ((GroupBean)this.session.getAttribute("AccessInfo"));
		StoreBean store = null;
		GoodsBean goods = null;
		ArrayList<GoodsBean> goodsList = null;
		CommonImageFileManager fileManager = ServletFileUpload.isMultipartContent(this.req)? new CommonImageFileManager(this.req) : null;

		int storeIdx = -1;
		for(StoreBean sb : group.getStoreInfoList()) {
			storeIdx++;
			if(sb.getStoreCode().equals(
					ServletFileUpload.isMultipartContent(this.req)?
							fileManager.searchStoreIdx() : this.req.getParameter("storeCode"))) break;
		}

		store = group.getStoreInfoList().get(storeIdx);

		if(store.getGoodsList() == null) {
			goodsList = new ArrayList<GoodsBean>();
			store.setGoodsList(goodsList);
		}

		goods = new GoodsBean();
		goodsList.add(goods);

		ArrayList<StoreImages> storeImageList = null;
		/* MultipartContent의 유무 */
		if(ServletFileUpload.isMultipartContent(this.req)) {		
			storeImageList = fileManager.saveGoodsImageFile(store, group.getGroupCode(), true);
		}else {	
			store.getGoodsList().get(store.getGoodsList().size()-1).setGoodsCode(this.req.getParameter("goodsCode"));
			store.getGoodsList().get(store.getGoodsList().size()-1).setGoodsName(this.req.getParameter("goodsName"));
			store.getGoodsList().get(store.getGoodsList().size()-1).setGoodsCategoryCode(this.req.getParameter("goodsCategoryCode"));
			store.getGoodsList().get(store.getGoodsList().size()-1).setGoodsStateCode(this.req.getParameter("goodsStateCode"));
			store.getGoodsList().get(store.getGoodsList().size()-1).setGoodsColorCode(this.req.getParameter("goodsColorCode"));
		}
		
		this.req.setAttribute("options", (new Auth(this.session).backController(998, this.req)).toString());

		action.setPage(page);
		action.setRedirect(isForward);
		return action;
	}
	private ActionBean getGoodsListCtl() {
		ActionBean action = new ActionBean();
		String page = "mgr.jsp", message = null; 
		boolean isForward = true;

		/* ClientData >> Bean : storeCode */
		StoreBean store= null;

		if(this.req.getParameter("storeCode") != null ){
			store = new StoreBean();
			store.setStoreCode(this.req.getParameter("storeCode"));

			this.dao = new GoodsDataAccessObject();
			this.connect = this.dao.openConnection();

			this.dao.getGoodsList(connect, store);
			this.dao.getGoodsCategoryList(connect, store);

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
			categoryList = this.dao.getCategoryList(connect, clientData);
			this.dao.closeConnection(connect);
			this.dao = null;

			//if(categoryList != null) {
			this.req.setAttribute("serverData", new Gson().toJson(categoryList));
			this.req.setAttribute("options", (new Auth(this.session).backController(998, this.req)).toString());
			//}
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
		String page = "mgr.jsp", message = null; 
		boolean isForward = true, tran = false;

		/* ClientData --> StoreBean */
		StoreBean store = null;
		ArrayList<CategoriesBean> categoryList = null;
		CategoriesBean category = null;

		if(this.req.getParameter("storeCode") != null 
				&& this.req.getParameter("levCode") != null 
				&& this.req.getParameter("levName") != null) {
			store = new StoreBean();
			categoryList = new ArrayList<CategoriesBean>();
			category = new CategoriesBean();
			category.setLevCode(this.req.getParameter("levCode"));
			category.setLevName(this.req.getParameter("levName"));
			categoryList.add(category);
			store.setStoreCode(this.req.getParameter("storeCode"));
			store.setCateList(categoryList);

			/* DAO : open modify set modify close */
			this.dao = new GoodsDataAccessObject();
			this.connect = this.dao.openConnection();
			this.dao.modifyTranStatus(connect, false);

			/* 추가할 maxCode 가져오기 */
			store.getCateList().get(0).setLevCode(this.dao.getMaxCode(connect, store)) ;
			/* 분류코드 추가 >> transaction  */
			tran = this.convertToBoolean(this.dao.insCategoryItem(connect, store));
			/* 분류리스트 가져오기 */
			store.getCateList().get(0).setLevCode(store.getCateList().get(0).getLevCode().substring(0, 1));
			/* Json Data 생성 */
			this.req.setAttribute("serverData", new Gson().toJson(this.dao.getCategoryList(connect, store)));

			this.dao.setTransaction(tran, connect);
			this.dao.modifyTranStatus(connect, true);
			this.dao.closeConnection(this.connect);
			this.dao = null;
		}		

		action.setPage(page);
		action.setRedirect(isForward);
		return action;
	}

	private ActionBean updCategoryCtl() {
		ActionBean action = new ActionBean();
		String page = "mgr.jsp", message = null; 
		boolean isForward = true, tran = false;

		/* ClientData --> StoreBean */
		StoreBean store = null;
		ArrayList<CategoriesBean> categoryList = null;
		CategoriesBean category = null;

		if(this.req.getParameter("storeCode") != null 
				&& this.req.getParameter("levCode") != null 
				&& this.req.getParameter("levName") != null) {
			store = new StoreBean();
			categoryList = new ArrayList<CategoriesBean>();
			category = new CategoriesBean();
			category.setLevCode(this.req.getParameter("levCode"));
			category.setLevName(this.req.getParameter("levName"));
			categoryList.add(category);
			store.setStoreCode(this.req.getParameter("storeCode"));
			store.setCateList(categoryList);

			/* DAO : open modify set modify close */
			this.dao = new GoodsDataAccessObject();
			this.connect = this.dao.openConnection();
			this.dao.modifyTranStatus(connect, false);

			/* 분류코드 추가 >> transaction  */
			tran = this.convertToBoolean(this.dao.updCategoryItem(connect, store));
			/* 분류리스트 가져오기 */
			store.getCateList().get(0).setLevCode(store.getCateList().get(0).getLevCode().substring(0, 1));
			/* Json Data 생성 */
			this.req.setAttribute("serverData", new Gson().toJson(this.dao.getCategoryList(connect, store)));

			this.dao.setTransaction(tran, connect);
			this.dao.modifyTranStatus(connect, true);
			this.dao.closeConnection(this.connect);
			this.dao = null;
		}		

		action.setPage(page);
		action.setRedirect(isForward);
		return action;
	}

	private boolean convertToBoolean(int value) {
		return value>0? true: false;
	}
}
