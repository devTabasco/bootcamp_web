package services.registration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import beans.ActionBean;
import beans.GroupBean;

/* Registration Class
 * - 대표자등록
 * - 상점등록
 * - 직원등록
 * - 분류등록 
 * */
public class Registration {
	private HttpServletRequest req;
	private RegDataAccessObject dao;
	
	public Registration(HttpServletRequest req) {
		this.req = req;
	}
	
	
	public ActionBean backController(int serviceCode) {
		ActionBean action = null;
		switch(serviceCode) {
		case -1:
			action = this.delGroupNameCtl();
			break;
		case 0:
			action = this.groupNameDuplicateCheckCtl();
			break;
		case 1:
			action = this.regGroupCtl();
			break;
		case 2:
			action = this.regStoreCtl();
			break;
		case 3:
			action = this.regEmpCtl();
			break;
		case 4:
			action = this.moveGroup();
			break;
		}
		return action;
	}
	/* 회원가입 첫 페이지 이동 */
	private ActionBean moveGroup() {
		ActionBean action = new ActionBean();
		String page="grStep1.jsp", message=null;
		boolean forward = false;
		
		action.setPage(page);
		action.setRedirect(forward);
		return action;
	}
	/* 그룹네임 중복 체크 >> 임시 저장 */
	private ActionBean groupNameDuplicateCheckCtl() {
		ActionBean action = new ActionBean();
		String page, message=null;
		Boolean forward;
		ArrayList<GroupBean> groupList = null;
		GroupBean group = new GroupBean();
		group.setGroupName(this.req.getParameter("groupName"));
				
		this.dao = new RegDataAccessObject();
		Connection connection = dao.openConnection();
		dao.modifyTranStatus(connection, false);
		groupList = dao.getGroupName(connection, group);
		
		if(groupList == null) {
			if(this.convertToBoolean(dao.insTemp(connection, group))) {
				this.req.setAttribute("groupName", group.getGroupName());
				page = "grStep2.jsp?previous=" + this.getRefererPage();
				forward = true;
			}else {
				page = "grStep1.jsp";
				message = "네트워크 에러:네트워크가 불안정합니다. 잠시 후 다시 시도해주세요";
				forward = false;
			}
		}else {
			page = "grStep1.jsp";
			message = "그룹명 중복:이미 사용중인 그룹명입니다.";
			forward = false;
		}		
		
		dao.setTransaction(true, connection);
		dao.modifyTranStatus(connection, true);
		dao.closeConnection(connection);
		this.dao = null;
		
		if(message != null) {
			try {
				page += "?message="+ URLEncoder.encode(message, "UTF-8");
			} catch (UnsupportedEncodingException e) {e.printStackTrace();}
		}
		action.setPage(page);
		action.setRedirect(forward);
		
		return action;
	}
	
	/* 그룹네임 삭제 >> 임시 저장 */
	private ActionBean delGroupNameCtl() {
		ActionBean action = new ActionBean();
		boolean tran, forward = false;
		String message = null, page = this.req.getParameter("target"); 
		
		if(!page.equals("index.jsp")){
			/* 1. request data >> GroupBean */
			GroupBean group = new GroupBean();
			group.setGroupName(this.req.getParameter("groupName"));

			/* 2. DAO :: DML */
			this.dao = new RegDataAccessObject();
			Connection connection = this.dao.openConnection();
			this.dao.modifyTranStatus(connection, false);

			tran = this.convertToBoolean(dao.delTemp(connection, group));

			this.dao.modifyTranStatus(connection, true);
			this.dao.closeConnection(connection);
			this.dao = null;

			if(tran) {
				forward = false;
			}else {
				message = "네트워크 에러:네트워크가 불안정합니다. 잠시 후 다시 시도해주세요";
				try {
					this.req.setAttribute("message", URLEncoder.encode(message, "UTF-8"));
				} catch (UnsupportedEncodingException e) {e.printStackTrace();}
			}
		}
		
		action.setPage(page);
		action.setRedirect(forward);
		
		return action;
	}
	
	/* 회원가입 :: 그룹코드 생성 */
	private ActionBean regGroupCtl() {
		ActionBean action = new ActionBean();
		String page = "grStep1.jsp", message = null;
		boolean forward = false;
				
		/* 1. req --> GroupBean */
		GroupBean group = new GroupBean();
		group.setGroupName(this.req.getParameter("groupName"));
		group.setGroupCeo(this.req.getParameter("groupCeo"));
		group.setGroupPin(this.req.getParameter("groupPin"));
		
		/* 2-0. Variable Declaration */
		boolean tran = false;
		ArrayList<GroupBean> groupList = null;
		/* 2. DAO Allocation & DAO Open */
		this.dao = new RegDataAccessObject();
		Connection connection = this.dao.openConnection();
		/* 2-1. Transaction Start */
		this.dao.modifyTranStatus(connection, false);
		/* 2-2. [INS] STOREGROUP */
		tran = this.convertToBoolean(this.dao.insStoreGroup(connection, group));
		if(tran) {
			/* 2-2. additional */
			this.dao.delTemp(connection, group);
			page = "step2.jsp";
			forward = tran;
			
			/* 2-3. [SEL] GROUPCODE */
			groupList = this.dao.getGroupCode(connection, group);
			
			/* Dynamic Data(DB Data) >> Request */
			req.setAttribute("groupCode", groupList.get(0).getGroupCode());
			req.setAttribute("groupName", groupList.get(0).getGroupName());
		}else {
			message = "네트워크 에러:네트워크가 불안정합니다. 잠시 후 다시 시도해주세요";
			try {
				this.req.setAttribute("message", URLEncoder.encode(message, "UTF-8"));
			} catch (UnsupportedEncodingException e) {e.printStackTrace();}
		}
		
		/* 2-4. Transaction End */
		this.dao.setTransaction(tran, connection);
		this.dao.modifyTranStatus(connection, true);
		/* 2-5. DAO Close */
		this.dao.closeConnection(connection);
		this.dao = null;
		
				
		action.setPage(page);
		action.setRedirect(forward);
				
		return action;
	}
	
	/* 회원가입2 :: 매장코드 생성 */
	private ActionBean regStoreCtl() {
		
		return null;
	}
	/* 회원가입3 :: 분류코드 생성 */
	private ActionBean regCategoriesCtl() {
		
		return null;
	}
	/* 회원가입4 :: 직원코드 생성 */
	private ActionBean regEmpCtl() {
		
		return null;
	}
	
	
	/* Convert to Boolean */
	private boolean convertToBoolean(int value) {
		return value>0? true:false;
	}
	
	/* Referer Page 추출 */
	private String getRefererPage() {
		String refer = null;
		try {
			refer = this.req.getHeader("referer").substring(this.req.getHeader("referer").lastIndexOf('/')+1).split("?")[0];
		}catch(Exception e) {
			refer = this.req.getHeader("referer").substring(this.req.getHeader("referer").lastIndexOf('/')+1);
		}
		
		return refer;
	}
	
}
