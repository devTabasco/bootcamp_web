package services.regist;

import java.sql.Connection;
import java.sql.SQLException;

import beans.MemberBean;
import services.DataAccessObject;

public class RegistDataAccessObject extends DataAccessObject {

	public int selectId(Connection connection, MemberBean memberBean) {
		int result = 0;
		String query = "SELECT count(*) AS ISMEMBER FROM MEMBERS WHERE (ME_EMAIL = ? or ME_PHONE = ?) and ME_PASSWORD";

		try {
			this.ps = connection.prepareStatement(query);
			ps.setNString(1, memberBean.getMemberEmail());
			ps.setNString(2, memberBean.getMemberPhone());

			this.rs = this.ps.executeQuery();
			
			while (this.rs.next()) {
				result = this.rs.getInt("ISMEMBER");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int insertMember(Connection connection, MemberBean memberBean) {
		int result = 0;
		String dml = "INSERT INTO MEMBERS(ME_NAME, ME_EMAIL, ME_PHONE, ME_PASSWORD) "
				+ "VALUES(?, ?, ?, ?)";

		try {
			this.ps = connection.prepareStatement(dml);
			System.out.println(memberBean.getMemberId());
			System.out.println(memberBean.getMemberEmail());
			System.out.println(memberBean.getMemberPhone());
			System.out.println(memberBean.getMemberPassword());
			
			ps.setNString(1, memberBean.getMemberId());
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
