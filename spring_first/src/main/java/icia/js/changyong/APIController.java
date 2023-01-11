package icia.js.changyong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import icia.js.changyong.auth.Authentication;
import icia.js.changyong.beans.GroupBean;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class APIController {
	@Autowired
	private Authentication auth;
	@PostMapping("/GroupDupChk")
	//Spring의 Dispahtcher servlet이 사용하는 것이 Model(interface)
	//@modelAttribute - Model에 있는 데이터를 옮겨라
	public GroupBean groupDuplicateCheck(Model model, @ModelAttribute GroupBean group) {
		model.addAttribute("group", group);
		auth.backController(1, model);
		
//		group.setMessage("그룹명 중복: 사용중인 그룹명 입니다. 다른 그룹명을 입력해주세요.");
		log.info("< {} >",group);
		return (GroupBean)model.getAttribute("group");
	}
	
	@PostMapping("/MemberJoin")
	public GroupBean groupMemberJoin(Model model, @ModelAttribute GroupBean group) {
		model.addAttribute("group", group);
		auth.backController(2, model);
		
//		group.setMessage("그룹명 중복: 사용중인 그룹명 입니다. 다른 그룹명을 입력해주세요.");
		log.info("< {} >",group);
		return (GroupBean)model.getAttribute("group");
	}
	
	@PostMapping("/RegStore")
	public GroupBean regStore(Model model, @ModelAttribute GroupBean group) {
		model.addAttribute("group", group);
		auth.backController(3, model);
		
//		group.setMessage("그룹명 중복: 사용중인 그룹명 입니다. 다른 그룹명을 입력해주세요.");
		log.info("< {} >",group);
		return (GroupBean)model.getAttribute("group");
	}
	
}
