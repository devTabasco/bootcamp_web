package services.registration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import beans.ActionBean;
import beans.GroupBean;
import services.RegDataAccessObject;

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
		case 0:
			action = this.groupNameDuplicateCheck();
			break;
		case 1:
			action = this.memberJoinCtl();
			break;
		case 2:
			this.RegStoreCtl();
			break;
		case 3:
			this.RegEmpCtl();
			break;
		}

		return action;
	}

	private ActionBean groupNameDuplicateCheck() {
		ActionBean action = new ActionBean();
		GroupBean groupBean = new GroupBean();
		String page, message=null;
		Boolean redirect;

		groupBean.setGroupName(this.request.getParameter("groupName"));

		// 2. dao allocation
		RegDataAccessObject dao = new RegDataAccessObject();
		Connection connection = dao.openConnection();
		ArrayList<GroupBean> groupList = new ArrayList<GroupBean>();
		
		// select groupname
		groupList = dao.getGroupName(connection, groupBean);

		// dao close;
		dao.closeConnection(connection);

		if(groupList == null) {
			this.request.setAttribute("groupName", groupBean.getGroupName());
			page = "group-step2.jsp?previous=" + this.getRefererPage();
			redirect = false;
		}else {
			page = "group-step1.jsp";
			message = "이미 사용중인 그룹명입니다.";
			redirect = true;
		}
		
		if(message != null) {
			try {
				page += "?message="+ URLEncoder.encode(message, "UTF-8");
			} catch (UnsupportedEncodingException e) {e.printStackTrace();}
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

//		System.out.println(groupBean.getGroupName());
//		System.out.println(groupBean.getGroupCeo());
//		System.out.println(groupBean.getGroupPin());

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

	private ActionBean RegStoreCtl() {

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
		return this.request.getHeader("referer").substring(this.request.getHeader("referer").lastIndexOf('/')+1);
	}
	
}
