package icia.js.changyong;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import icia.js.changyong.beans.GroupBean;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class APIController {
	@PostMapping("/GroupDupChk")
	//Spring의 Dispahtcher servlet이 사용하는 것이 Model(interface)
	//@modelAttribute - Model에 있는 데이터를 옮겨라
	public GroupBean groupDuplicateCheck(Model model, @ModelAttribute GroupBean group) {
		model.addAttribute("group", group);
//		group.setMessage("그룹명 중복: 사용중인 그룹명 입니다. 다른 그룹명을 입력해주세요.");
		log.info("< {} >",group);
		return (GroupBean)model.getAttribute("group");
	}
}
