package services.auth;

import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.SQLException;

import beans.MemberBean;
import services.DataAccessObject;

public class AuthDataAccessObject extends DataAccessObject {

	/* 
	 * job : 로그인
	 * 1. MEMBERS Table에 ID와 PHONE 중 일치하는게 있는지 확인
	 * 2. 있다면, Pin번호까지 확인하여 로그인
	 *  */
	
	public int selectAccountInfo(Connection connection, MemberBean memberBean) {
		int result = 0;
		String query = "SELECT count(*) AS ISMEMBER FROM MEMBERS WHERE ME_EMAIL = ? or ME_PHONE = ?";

		try {
			this.ps = connection.prepareStatement(query);
			ps.setNString(1, memberBean.getMemberEmail());
			ps.setNString(2, memberBean.getMemberEmail());

			this.rs = this.ps.executeQuery();
			
			while (this.rs.next()) {
				result = this.rs.getInt("ISMEMBER");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("이메일+전번 확인 : "+ result);
		return result;
	}
	
	public int checkPin(Connection connection, MemberBean memberBean) {
		int result = 0;
		String query = "SELECT COUNT(*) AS CHECKPIN FROM MEMBERS WHERE (ME_EMAIL = ? or ME_PHONE = ?) and ME_PASSWORD = ?";

		try {
			this.ps = connection.prepareStatement(query);
			ps.setNString(1, memberBean.getMemberEmail());
			ps.setNString(2, memberBean.getMemberEmail());
			ps.setNString(3, memberBean.getMemberPassword());

			this.rs = this.ps.executeQuery();
			
			while (this.rs.next()) {
				result = this.rs.getInt("CHECKPIN");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("checkPin");
		return result;
	}
	
	public MemberBean selectMemberInfo(Connection connection, MemberBean memberBean) {
		MemberBean member = null;
		String query = "SELECT * FROM MEMBERS WHERE (ME_EMAIL = ? or ME_PHONE = ?) and ME_PASSWORD = ?";

		try {
			this.ps = connection.prepareStatement(query);
			ps.setNString(1, memberBean.getMemberEmail());
			ps.setNString(2, memberBean.getMemberEmail());
			ps.setNString(3, memberBean.getMemberPassword());

			this.rs = this.ps.executeQuery();
			
			while (this.rs.next()) {
				member = new MemberBean();
				member.setMemberEmail(this.rs.getNString("ME_EMAIL"));
				member.setMemberPhone(this.rs.getNString("ME_PHONE"));
				member.setMemberHomePhone(this.rs.getNString("ME_HOMEPHONE"));
				member.setMemberSex(this.rs.getNString("ME_SEX"));
				member.setMemberAddr(this.rs.getNString("ME_ADDR"));
				member.setMemberBirth(this.rs.getNString("ME_BIRTH"));
				member.setMemberName(this.rs.getNString("ME_NAME"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return member;
	}
	
	public int insertMember(Connection connection, MemberBean memberBean) {
		int result = 0;
		String dml = "INSERT INTO MEMBERS(ME_NAME, ME_EMAIL, ME_PHONE, ME_PASSWORD) "
				+ "VALUES(?, ?, ?, ?)";

		try {
			this.ps = connection.prepareStatement(dml);
			System.out.println(memberBean.getMemberName());
			System.out.println(memberBean.getMemberEmail());
			System.out.println(memberBean.getMemberPhone());
			System.out.println(memberBean.getMemberPassword());
			
			ps.setNString(1, memberBean.getMemberName());
			ps.setNString(2, memberBean.getMemberEmail());
			ps.setNString(3, memberBean.getMemberPhone());
			ps.setNString(4, memberBean.getMemberPassword());

			result = this.ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
