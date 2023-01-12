package icia.js.hoonzzang.services.auth;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import icia.js.hoonzzang.beans.EmployeesBean;
import icia.js.hoonzzang.beans.CategoriesBean;
import icia.js.hoonzzang.beans.GroupBean;
import icia.js.hoonzzang.beans.StoreBean;
import icia.js.hoonzzang.utils.Encryption;
import lombok.extern.slf4j.Slf4j;

/* Authentication + Memeber Join*/
@Service
@Slf4j
public class Authentication_json {
	@Autowired
	private Encryption enc;
	@Autowired
	private SqlSessionTemplate session;

	public Authentication_json() {

	}

	public void backController(int serviceCode, Model model) {
		switch (serviceCode) {
		case 0:
			this.groupRegistration(model);
			break;
		case 1:
			this.groupNameDuplicateCheckCtl(model);
			break;
		case 2:
			this.deleteTempGroupName(model);
			break;
		case 3:
			this.isStoreCode(model);
			break;
		}
	}

	public void backController(int serviceCode, ModelAndView mav) {
		switch (serviceCode) {
		case 1:

			break;
		}
	}

	/* Registration */
	@Transactional
	private void groupRegistration(Model model) {
		GroupBean group = (GroupBean) model.getAttribute("group");

		/* AES 암호화 처리 */
		try {
			String aesString = enc.aesEncode(group.getGroupCeo(),
					group.getStoreInfoList().get(0).getStoreCode() + group.getGroupName());
			group.setGroupCeo(aesString);
			group.getStoreInfoList().get(0).getEmpList().get(0).setEmpName(aesString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/* SHA 암호화 처리 */
		String encPassword = enc.encode(group.getGroupPin());
		group.setGroupPin(encPassword);
		group.getStoreInfoList().get(0).getEmpList().get(0).setEmpPin(encPassword);

		this.session.insert("insGroup", group);
		this.session.insert("insStore", group);
		this.session.update("insCate", group);
		this.session.insert("insEmp", group);
		
		/* AES 복호화 */
		String decString = null;
		try {
			decString = enc.aesDecode(group.getGroupCeo(), group.getStoreInfoList().get(0).getStoreCode() + group.getGroupName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		group.setGroupCeo(decString);
		this.deleteTempGroupName(model, true);
		
		
	}

	/* GroupName 중복검사 후 임시 테이블에 저장 */
	private void groupNameDuplicateCheckCtl(Model model) {
		ArrayList<StoreBean> storeList = null;
		StoreBean store = null;
		ArrayList<EmployeesBean> empList = null;
		EmployeesBean emp = null;
		ArrayList<CategoriesBean> cateList = null;
		CategoriesBean cate = null;

		GroupBean group = (GroupBean) model.getAttribute("group");
		// 중복검사 (SG + TEMP)
		if (!this.convertToBoolean(this.session.selectOne("isGroupName", group))) {
			empList = new ArrayList<EmployeesBean>();
			emp = new EmployeesBean();
			empList.add(emp);
			cateList = new ArrayList<CategoriesBean>();
			cate = new CategoriesBean();
			cateList.add(cate);
			storeList = new ArrayList<StoreBean>();
			store = new StoreBean();
			store.setCateList(cateList);
			store.setEmpList(empList);
			storeList.add(store);
			group.setStoreInfoList(storeList);

			/* Temp 테이블에 ins << 동시성 이슈 */
			if (!this.convertToBoolean(this.session.insert("insTemp", group))) {
				group.setMessage("네트워크 오류:네트워크가 불안정합니다. 잠시 후 다시 시도해주세요");
			}
		} else {
			group.setMessage("그룹네임 중복 오류:이미 사용중인 그룹명입니다.");
		}

	}

	/* 임시 저장된 GroupName 삭제 */
	private void deleteTempGroupName(Model model, boolean... isReg) {
		GroupBean group = (GroupBean) model.getAttribute("group");
		String message = null;
		if (group.getGroupName() != "") {
			boolean delResult = this.convertToBoolean(this.session.delete("delTemp", group));
			if (isReg[0] == true)
				message = (delResult)
						? "회원가입성공:" + group.getGroupCeo() + "님 회원가입을 감사드립니다.\n아래의 정보로 로그인 해주세요.\n" + "[GroupCode >>>"
								+ group.getGroupCode() + "]\n" + "[StoreCode >>>"
								+ group.getStoreInfoList().get(0).getStoreCode() + "]\n" + "[EmployeeCode >>> "
								+ group.getStoreInfoList().get(0).getEmpList().get(0).getEmpCode() + "]"
						: "회원가입 오류:일시적인 장애로 회원가입이 되지 않았습니다. 잠시후 다시 시도해 주세요.";
			else
				message = (delResult) ? "정상처리:이전에 입력된 그룹네임이 삭제되었습니다."
						: "그룹네임 삭제 오류:이전에 입력된 그룹네임이 삭제되지 않아 이전 그룹네임을 사용할 수 없습니다.";

			/* 그룹네임 삭제 후 기존 입력 정보 지우기 */
			group.setMessage(message);
			group.setGroupName(null);
			group.setGroupPin(null);
			group.setGroupCeo(null);
			group.setStoreInfoList(null);

		}
	}
	
	/* Store Code 확인 */
	private void isStoreCode(Model model) {
		GroupBean group = (GroupBean) model.getAttribute("group");
		
		//storeCode조회 false인경우에만 message날리기
		if(this.convertToBoolean(this.session.selectOne("isStoreCode",group))) {
			group.setMessage("storeCode");
		}else {
			group.setMessage(null);
		}
		
	}

	private boolean convertToBoolean(int value) {
		return (value >= 1) ? true : false;
	}
}
