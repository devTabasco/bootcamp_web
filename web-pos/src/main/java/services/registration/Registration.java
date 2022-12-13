package services.registration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.apache.jasper.compiler.NewlineReductionServletWriter;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;

import beans.ActionBean;
import beans.CategoriesBean;
import beans.GroupBean;
import beans.StoreBean;

/*
 * - 대표자 등록
 * - 상점 등록
 * - 직원 등록
 * - 분류(카테고리) 등록
 * 
 * */

public class Registration {

	private HttpServletRequest request;

	public Registration() {

	}

	public ActionBean backController(int serviceCode, HttpServletRequest request) {
		this.request = request;
		ActionBean action = new ActionBean();

		switch (serviceCode) {
		case -2:
			action = this.deleteStore();
			break;
		case -1:
			action = this.deleteTemp();
			break;
		case 0:
			action = this.groupNameDuplicateCheck();
			break;
		case 1:
			action = this.regGroupCtl();
			break;
		case 3:
			this.RegEmpCtl();
			break;
		case 4:
			this.RegEmpCtl();
			break;
		case 2: case 5:
			action = this.RegStoreCtl();
			break;
		case 6:
			this.RegEmpCtl();
			break;
		}

		return action;
	}
	
	private ActionBean moveGroup() {
		ActionBean action = new ActionBean();
		String page = "grStep1.jsp", message = null;
		boolean redirect = true;
		
		action.setPage(page);
		action.setRedirect(redirect);
		
		return action;
	}
	
	private ActionBean deleteStore() {
		ActionBean action = new ActionBean();
		GroupBean groupBean = new GroupBean();
		StoreBean storeBean = new StoreBean();
		RegDataAccessObject dao = new RegDataAccessObject();
		Connection connection = dao.openConnection();
		String message = null, page = "group-step2.jsp";
		Boolean redirect = false, tran = false;

		storeBean.setStoreCode(this.request.getParameter("storeCode"));

		// transaction control
		dao.modifyTranStatus(connection, false);

		tran = this.convertToBoolean(dao.deleteStore(connection, storeBean));
		
		dao.setTransaction(true, connection);
		
		dao.modifyTranStatus(connection, true);
		// dao close;
		dao.closeConnection(connection);
		
		if(tran) {
			page = this.request.getParameter("target");
			message = "새로 등록해주세요.";
			redirect = true;
		}else message = "네트워크오류:네트워크가 불안정합니다. 잠시 후 다시 시도해주세요";
		
		if (message != null) {
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
	
	private ActionBean deleteTemp() {
		ActionBean action = new ActionBean();
		GroupBean groupBean = new GroupBean();
		RegDataAccessObject dao = new RegDataAccessObject();
		Connection connection = dao.openConnection();
		String message = null, page = "group-step2.jsp";
		Boolean redirect = false, tran = false;

		groupBean.setGroupName(this.request.getParameter("groupName"));

		// transaction control
		dao.modifyTranStatus(connection, false);
		dao.setTransaction(true, connection);

		
		tran = this.convertToBoolean(dao.deleteTemp(connection, groupBean));
		
		dao.modifyTranStatus(connection, true);
		// dao close;
		dao.closeConnection(connection);
		
		if(tran) {
			page = this.request.getParameter("target");
			message = "그룹명을 새로 입력해주세요.";
			redirect = true;
		}else message = "네트워크오류:네트워크가 불안정합니다. 잠시 후 다시 시도해주세요";
		
		if (message != null) {
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
	
	private ActionBean groupNameDuplicateCheck() {
		ActionBean action = new ActionBean();
		GroupBean groupBean = new GroupBean();
		String page, message = null;
		Boolean redirect;

		groupBean.setGroupName(this.request.getParameter("groupName"));

		// 2. dao allocation
		RegDataAccessObject dao = new RegDataAccessObject();
		Connection connection = dao.openConnection();
		ArrayList<GroupBean> groupList = new ArrayList<GroupBean>();

		// transaction control
		dao.modifyTranStatus(connection, false);
		dao.setTransaction(true, connection);

		// select groupname
		groupList = dao.getGroupName(connection, groupBean);

		if (groupList == null) {
			// 임시테이블을 활용해 groupList에 값이 없을 시 temp에 임시로 적용
			if (this.convertToBoolean(dao.insTemp(connection, groupBean))) {
				this.request.setAttribute("groupName", groupBean.getGroupName());
				page = "group-step2.jsp?previous=" + this.getRefererPage();
				redirect = false;
			} else {
				page = "group-step1.jsp";
				message = "네트워크오류:네트워크가 불안정 합니다. 잠시 후 다시 시도해주세요.";
				redirect = true;
			}
		} else {
			page = "group-step1.jsp";
			message = "이미 사용중인 그룹명입니다.";
			redirect = true;
		}

		dao.modifyTranStatus(connection, true);

		// dao close;
		dao.closeConnection(connection);

		if (message != null) {
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

	private ActionBean memberJoinCtl() {
		ActionBean action = new ActionBean();
		// 1. request -> groupBean
		GroupBean groupBean = new GroupBean();
		ArrayList<GroupBean> groupList = new ArrayList<GroupBean>();
		groupBean.setGroupName(this.request.getParameter("groupName"));
		groupBean.setGroupCeo(this.request.getParameter("groupCeo"));
		groupBean.setGroupPin(this.request.getParameter("groupPin"));

		// 2. dao allocation
		boolean tran = false;
		RegDataAccessObject dao = new RegDataAccessObject();
		Connection connection = dao.openConnection();

		// 2-1. transaction start
		dao.modifyTranStatus(connection, false);

		// 2-2. insert storegroup
		tran = convertToBoolean(dao.insStoreGroup(connection, groupBean));

		// 2-3. select groupcode
		groupList = dao.getGroupCode(connection, groupBean);

		// 2-4. transaction end
		dao.setTransaction(tran, connection);
		dao.modifyTranStatus(connection, true);

		// 2-5. dao close;
		dao.closeConnection(connection);

		request.setAttribute("groupCode", groupList.get(0).getGroupCode());
		action.setPage(tran ? "step2.jsp" : "step1.html");
		action.setRedirect(!tran);

		return action;

	}
	
	/* 회원가입 :: 그룹코드 생성 */
	private ActionBean regGroupCtl() {
		ActionBean action = new ActionBean();
		String page = "group-step1.jsp", message = null;
		boolean redirect = false;
		RegDataAccessObject dao = new RegDataAccessObject();
				
		/* 1. req --> GroupBean */
		GroupBean group = new GroupBean();
		group.setGroupName(this.request.getParameter("groupName"));
		group.setGroupCeo(this.request.getParameter("groupCeo"));
		group.setGroupPin(this.request.getParameter("groupPin"));
		
		/* 2-0. Variable Declaration */
		boolean tran = false;
		ArrayList<GroupBean> groupList = null;
		/* 2. DAO Allocation & DAO Open */
		dao = new RegDataAccessObject();
		Connection connection = dao.openConnection();
		/* 2-1. Transaction Start */
		dao.modifyTranStatus(connection, false);
		/* 2-2. [INS] STOREGROUP */
		tran = this.convertToBoolean(dao.insStoreGroup(connection, group));
		if(tran) {
			/* 2-2. additional */
			dao.deleteTemp(connection, group);
			page = "store-step1.jsp";
			redirect = tran;
			
			/* 2-3. [SEL] GROUPCODE */
			groupList = dao.getGroupCode(connection, group);
			
			/* Dynamic Data(DB Data) >> Request */
			request.setAttribute("groupCode", groupList.get(0).getGroupCode());
			request.setAttribute("groupName", group.getGroupName());
		}else message = "네트워크오류:네트워크가 불안정합니다. 잠시 후 다시 시도해주세요";
		
		/* 2-4. Transaction End */
		dao.setTransaction(tran, connection);
		dao.modifyTranStatus(connection, true);
		/* 2-5. DAO Close */
		dao.closeConnection(connection);
				
		if (message != null) {
			try {
				page += "?message=" + URLEncoder.encode(message, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		action.setPage(page);
		action.setRedirect(!redirect);
				
		return action;
	}

	private ActionBean RegStoreCtl() {
		ActionBean action = new ActionBean();
		String page = "group-step1.jsp", message = null;
		boolean redirect = false;
		RegDataAccessObject dao = new RegDataAccessObject();
		String[][] levInfo = {{"L1", "그룹대표"},{"L2", "매니저"},{"L3", "직원"}};
		
		/* 1. req --> GroupBean */
		/* Store info */
		GroupBean group = new GroupBean();
		StoreBean store = new StoreBean();
		ArrayList<StoreBean> storeList = new ArrayList<StoreBean>();
		
		store.setStoreCode(this.request.getParameter("storeCode"));
		store.setStoreName(this.request.getParameter("storeName"));
		store.setStoreZip(this.request.getParameter("storeZipCode"));
		store.setStoreAddr(this.request.getParameter("storeAddr"));
		store.setStoreAddrDetail(this.request.getParameter("storeAddrDetails"));
		store.setStorePhone(this.request.getParameter("storePhone"));
		
		storeList.add(store);
		group.setGroupCode(this.request.getParameter("groupCode"));
		
		/* Default CategoriesInfo */
		CategoriesBean category = new CategoriesBean();
		ArrayList<CategoriesBean> categoryList = new ArrayList<CategoriesBean>();
		
//		category.setLevCode("L1");
//		category.setLevName("그룹대표");
//		categoryList.add(category);		
//		category.setLevCode("L2");
//		category.setLevName("매니저");
//		categoryList.add(category);
//		category.setLevCode("L3");
//		category.setLevName("직원");
//		categoryList.add(category);
		
		for(String[] lev : levInfo) {
			category = new CategoriesBean();
			category.setLevCode(lev[0]);
			category.setLevName(lev[1]);
			categoryList.add(category);
		}
		
		store.setLevInfo(categoryList);		
		group.setStoreInfoList(storeList);
		
		/* 2-0. Variable Declaration */
		boolean tran = false;
		/* 2. DAO Allocation & DAO Open */
		dao = new RegDataAccessObject();
		Connection connection = dao.openConnection();
		/* 2-1. Transaction Start */
		dao.modifyTranStatus(connection, false);
		/* 2-2. [INS] STOREGROUP */
		tran = this.convertToBoolean(dao.insStore(connection, group));
		if(tran) {
			/* 2-2. additional */
			//this.
			if(this.convertToBoolean(dao.insStoreLevel(connection, group))){
				message = this.makeMessage(group);
				//request에 담아 보낼때는 encoding 할필요 없음
				this.request.setAttribute("message", message);
				request.setAttribute("storeCode", this.request.getParameter("storeCode"));
				request.setAttribute("levInfo", this.makeHTMLSelectBox(group));
				page = "employee-step1.jsp";
				redirect = tran;
			}
			/* Dynamic Data(DB Data) >> Request */
//			request.setAttribute("groupName", group.getGroupName());
		}
		
		/* 2-4. Transaction End */
		dao.setTransaction(tran, connection);
		dao.modifyTranStatus(connection, true);
		/* 2-5. DAO Close */
		dao.closeConnection(connection);
		dao = null;
				
//		if (message != null) {
//			try {
//				page += "?message=" + URLEncoder.encode(message, "UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//		}
		
		action.setPage(page);
		action.setRedirect(true);
				
		return action;
	}
	
	//selectBox 코드 만들어주기
	private String makeHTMLSelectBox(Object obj) {
		StringBuffer select = new StringBuffer();
		select.append("<select name=\"levelInfo\">");
		for(CategoriesBean category : ((GroupBean)obj).getStoreInfoList().get(0).getLevInfo()) {
			select.append("<option value=\""+ category.getLevCode() + "\">" + category.getLevName() + "</option>");
		}
		select.append("</select>");			
		
		return select.toString();
	}
	
	//그룹, 스토어 빈을 받아서 메세지 만들어주기.
	private String makeMessage(Object object) {
		StringBuffer message = new StringBuffer();
		GroupBean group = (GroupBean)object;
		message.append("Store Infomation:");
		message.append("[그룹코드] - " + group.getGroupCode());
		message.append("\n[그룹명] - " + group.getGroupName());
		message.append("\n[상점코드] - " + group.getStoreInfoList().get(0).getStoreCode());
		message.append("\n[상점명] - " + group.getStoreInfoList().get(0).getStoreName());
		
		return null;
	}

	private ActionBean RegEmpCtl() {

		return null;
	}

	private ActionBean RegCategoriesCtl() {

		return null;
	}

	/* Convert to Boolean */
	private boolean convertToBoolean(int value) {
		return value > 0 ? true : false;
	}

	/* Referer Page 추출 */
	private String getRefererPage() {
		return this.request.getHeader("referer").substring(this.request.getHeader("referer").lastIndexOf('/') + 1);
	}

}
