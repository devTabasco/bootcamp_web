package icia.js.hoonzzang.services.auth;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import icia.js.hoonzzang.beans.*;
import lombok.extern.slf4j.Slf4j;

/* Authentication + Member Join */
@Service
@Slf4j
public class Authentication {
	@Autowired
	private SqlSessionTemplate session;

	public Authentication() {
	};

	public void backController(int serviceCode, Model model) {
		switch (serviceCode) {
		case 1:
			this.groupNameDuplicateCheckCtl(model);
			break;
		case 2:
			this.initInsertCtl(model);
			break;
		case 3:
			this.deleteTempCtl(model);
			break;
		default:
			break;
		}
	}

	// 페이지 까지 함께 return
//	public void backController(int serviceCode, ModelAndView mav) {
//		switch (serviceCode) {
//		case 1:
//			break;
//		case 2:
//			break;
//		case 3:
//			break;
//		default:
//			break;
//		}
//	}

	private void groupNameDuplicateCheckCtl(Model model) {
		GroupBean group = (GroupBean) model.getAttribute("group");
//		log.info("{}",((GroupBean)model.getAttribute("group")).getGroupName());
		// 중복검사 (SG + TEMP)
		log.info("{}",group);

		if (!this.convertToBoolean(this.session.selectOne("isGroupName", group))) {
			// temp 테이블에 ins << 동시성 이슈
			if (!this.convertToBoolean(this.session.insert("insTemp", group))) {
				group.setMessage("네트워크 오류: 네트워크를 확인해주세요.");
			}
		} else {
			group.setMessage("그룹명 중복: 사용중인 그룹명 입니다. 다른 그룹명을 입력해주세요.");
		}

		// factoryBean 이나 sessionTemplate로 mapper 연결
//		if(!this.convertToBoolean(this.session.select("isGroupName", ((GroupBean)model.getAttribute("group")),null) , ((GroupBean)model.getAttribute("group"))));
//		log.info("{}",(int)this.session.selectOne("isGroupName",((GroupBean)model.getAttribute("group"))));
	}

	private void initInsertCtl(Model model) {
		GroupBean group = (GroupBean) model.getAttribute("group");
		String[][] defaultLevInfo = { { "L1", "그룹대표" }, { "L2", "매니저" }, { "L3", "직원" } };
		CategoriesBean c = null;
		ArrayList<CategoriesBean> categoriesList = new ArrayList<CategoriesBean>();
		log.info("{}",group);

		// 1. [INS]SG
		if (this.convertToBoolean(this.session.insert("insStoreGroup", group))) {
			// 2. [DEL] TEMP
			if (this.convertToBoolean(this.session.delete("delTemp", group))) {
				// 3. [SEL] GROUPCODE
//				group.setGroupCode(this.session.selectOne("getGroupCode", group));
			}
			System.out.println(group.getGroupCode());
		} else {
			// SG insert 안됨.
			group.setMessage("네트워크 오류: 네트워크 오류");
		}

		// 4. [INS] ST & [INS] CATEGORY & [INS] EMP
		// store insert
		if (this.convertToBoolean(this.session.insert("insStore", group))) {
			System.out.println("스토어 등록 성공!");

			for (String[] lev : defaultLevInfo) {
				if (categoriesList.size() != 0) categoriesList.clear();
				c = new CategoriesBean();
				c.setLevCode(lev[0]);
				c.setLevName(lev[1]);
				categoriesList.add(c);
				group.getStoreInfoList().get(0).setCateList(categoriesList);
				//category insert
				this.session.insert("insInitCategory", group);
				System.out.println("카테고리 등록 성공!");
			}
			group.getStoreInfoList().get(0).getCateList().get(0).setLevCode("L1");
//			group.getStoreInfoList().get(0).getCateList().get(0).setLevName("그룹대표");
			if (this.convertToBoolean(this.session.insert("insInitEmp", group))) {
				System.out.println("직원 등록 성공!");
			}
		} else {
			// insert 안됨.
			group.setMessage("네트워크 오류: 네트워크 오류");
		}
	}
	
	private void deleteTempCtl(Model model) {
		GroupBean group = (GroupBean) model.getAttribute("group");
//		log.info("{}",((GroupBean)model.getAttribute("group")).getGroupName());
		// 중복검사 (SG + TEMP)
		log.info("{}",group);

		if (this.convertToBoolean(this.session.delete("delTemp", group))) {
			group.setMessage("그룹명 삭제: 입력했던 그룹명이 삭제되었습니다. 다시 입력해주세요.");
			System.out.println("TEMP 삭제 성공!");
		}
	}

	private boolean convertToBoolean(int value) {
		return value > 0 ? true : false;
	}

}
