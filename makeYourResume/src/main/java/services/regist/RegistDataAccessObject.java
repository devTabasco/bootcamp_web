package services.regist;

import java.sql.Connection;
import java.sql.SQLException;

import org.eclipse.jdt.internal.compiler.env.IUpdatableModule.UpdateKind;

import beans.MemberBean;
import services.DataAccessObject;

public class RegistDataAccessObject extends DataAccessObject {

	public int selectId(Connection connection, MemberBean memberBean) {
		int result = 0;
		String query = "SELECT count(*) AS ISMEMBER FROM MEMBERS WHERE (ME_EMAIL = ? or ME_PHONE = ?) and ME_PASSWORD";

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
		
		return result;
	}
	
	public int insertAddInfo(Connection connection, MemberBean memberBean) {
		int result = 0;
		String dml = "UPDATE MEMBERS "
				+ "SET ME_BIRTH = ?, ME_SEX = ?, ME_HOMEPHONE = ?, ME_ADDR = ? "
				+ "WHERE (ME_EMAIL = ? or ME_PHONE = ?)";

		try {
			this.ps = connection.prepareStatement(dml);
			System.out.println(memberBean.getMemberBirth());
			System.out.println(memberBean.getMemberHomePhone());
			System.out.println(memberBean.getMemberAddr());
			System.out.println(memberBean.getMemberSex());
			
			ps.setNString(1, memberBean.getMemberBirth());
			ps.setNString(2, memberBean.getMemberSex());
			ps.setNString(3, memberBean.getMemberHomePhone());
			ps.setNString(4, memberBean.getMemberAddr());
			ps.setNString(5, memberBean.getMemberEmail());
			ps.setNString(6, memberBean.getMemberPhone());

			result = this.ps.executeUpdate();
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
