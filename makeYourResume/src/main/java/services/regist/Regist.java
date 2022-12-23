package services.regist;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;

import beans.ActionBean;
import beans.MemberBean;

public class Regist {
	HttpServletRequest request;
	HttpSession session;

	public ActionBean backController(int serviceCode, HttpServletRequest request) {
		ActionBean action = new ActionBean();
		this.request = request;
		session = this.request.getSession();

		switch (serviceCode) {
		case 1:
			action = this.registCtl();
			break;
		case 2:
			action = this.addInfo();
			break;

		default:
			break;
		}

		return action;

	}
	
	private ActionBean addInfo() {
		ActionBean action = new ActionBean();
		MemberBean memberBean = null;
		String page = "index.jsp", message = null;
		boolean redirect = true;
		
		RegistDataAccessObject dao = new RegistDataAccessObject();
		memberBean = ((MemberBean)this.session.getAttribute("memberInfo"));
		memberBean.setMemberBirth(this.request.getParameter("memberBirth").replaceAll("-", ""));
		memberBean.setMemberHomePhone(this.request.getParameter("memberHomePhone"));
		memberBean.setMemberSex(this.request.getParameter("memberSex"));
		memberBean.setMemberAddr(this.request.getParameter("memberAddr"));
		
		System.out.println(this.request.getParameter("memberAddr"));
		
		/* 2. DAO Allocation & DAO Open */
		Connection connection = dao.openConnection();
		/* 2-1. Transaction Start */
		dao.setAutoCommitController(connection, false);
		
		if(convertToBoolean(dao.insertAddInfo(connection, memberBean))) {
			page = "makeResume.jsp";
			redirect = false;
			
			this.session.setAttribute("memberInfo", memberBean);
		}
		
		/* 2-4. Transaction End */
		dao.setTransaction(true, connection);
		dao.setAutoCommitController(connection, true);
		
		/* 2-5. DAO Close */
		dao.closeConnection(connection);
		
		action.setPage(page);
		action.setRedirect(redirect);
		
		return action;
	}

	private ActionBean registCtl() {
		/*
		 * job : 회원가입 1. DB에서 ID와 PHONE이 Unique한지 확인 2. 없다면 insert
		 * 
		 */
		System.out.println(this.request.getParameter("memberId"));
		System.out.println(this.request.getParameter("memberPhone"));
		System.out.println(this.request.getParameter("memberEmail"));
		System.out.println(this.request.getParameter("memberPassword"));

		ActionBean action = new ActionBean();
		String page = "index.jsp", message = null;
		boolean redirect = true;
		RegistDataAccessObject dao = new RegistDataAccessObject();

		/* 1. req --> GroupBean */
		MemberBean memberBean = new MemberBean();
		memberBean.setMemberName(this.request.getParameter("memberName"));
		memberBean.setMemberPhone(this.request.getParameter("memberPhone").replace("-", ""));
		memberBean.setMemberEmail(this.request.getParameter("memberEmail"));
		memberBean.setMemberPassword(this.request.getParameter("memberPassword"));

		/* 2. DAO Allocation & DAO Open */
		Connection connection = dao.openConnection();
		/* 2-1. Transaction Start */
		dao.setAutoCommitController(connection, false);
		/* 2-2. [INS] STOREGROUP */

		// 1. DB에서 ID와 PHONE이 Unique한지 확인
		if (!this.convertToBoolean(dao.selectId(connection, memberBean))) {
			// 2. 없다면 insert
			if (!convertToBoolean(dao.insertMember(connection, memberBean))) message = "회원가입에 실패하였습니다.";
		} else
			message = "이미 존재하는 회원 정보입니다.";

		/* 2-4. Transaction End */
		dao.setTransaction(true, connection);
		dao.setAutoCommitController(connection, true);
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
		action.setRedirect(redirect);

		return action;

	}

	/* Convert to Boolean */
	private boolean convertToBoolean(int value) {
		return value > 0 ? true : false;
	}

}
