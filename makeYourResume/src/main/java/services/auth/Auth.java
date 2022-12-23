package services.auth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.ActionBean;
import beans.MemberBean;
import services.regist.RegistDataAccessObject;


public class Auth {
	private HttpServletRequest request;
	HttpSession session;

	public ActionBean backController(int serviceCode, HttpServletRequest request) {
		ActionBean action = null;
		this.request = request;
		session = this.request.getSession();
		
		switch (serviceCode) {
		case 1:
			action = this.accessCtl();
			break;
		case 2:
			break;

		default:
			break;
		}
		
		return action;
	}
	
	private ActionBean accessCtl() {
		ActionBean action = new ActionBean();
		String page = "login.jsp", message = null;
		boolean redirect = true;
		AuthDataAccessObject dao = new AuthDataAccessObject();
		
		MemberBean memberBean = new MemberBean();
		//memberId에 Phone과 email을 모두 담아 bean의 Email로 dao에 전달
		memberBean.setMemberEmail(this.request.getParameter("memberEmail").replace("-", ""));
		memberBean.setMemberPassword(this.request.getParameter("memberPassword"));
		
		/* 2. DAO Allocation & DAO Open */
		Connection connection = dao.openConnection();
		/* 2-1. Transaction Start */
		dao.setAutoCommitController(connection, false);
		/* 2-2. [INS] STOREGROUP */

		// 1. DB에서 ID와 PHONE이 존재하는지 확인
		if (this.convertToBoolean(dao.selectAccountInfo(connection, memberBean))) {
			if(this.convertToBoolean(dao.checkPin(connection, memberBean))) {
				memberBean = dao.selectMemberInfo(connection, memberBean);
				//고객 인적사항 전달
				this.session.setAttribute("memberInfo", memberBean);
				//추가인적사항 필요하면, addInfo.jsp로 모두 있으면 makeResume.jsp로
				if((memberBean.getMemberBirth() == null) || (memberBean.getMemberHomePhone() == null) || (memberBean.getMemberSex() == null) || (memberBean.getMemberAddr() == null)) {
					page = "addInfo.jsp";
					redirect = false;
				}else {
					page = "makeResume.jsp";
					redirect = false;
				}
			}
		} else message = "회원정보가 잘못되었습니다.";

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
