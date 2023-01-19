package icia.js.hoonzzang;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import icia.js.hoonzzang.beans.GroupBean;
import icia.js.hoonzzang.beans.StoreBean;
import icia.js.hoonzzang.services.auth.Authentication;
import icia.js.hoonzzang.services.auth.Authentication_json;
import lombok.extern.slf4j.Slf4j;
import icia.js.hoonzzang.utils.ProjectUtils;

@RestController
@Slf4j
public class APIController {
	@Autowired
//	private Authentication auth;
	private Authentication_json auth_json;
//	@PostMapping("/GroupDupChk")
//	public GroupBean groupDuplicateCheck(Model model, @ModelAttribute GroupBean group) {
//		model.addAttribute("group", group);
//		auth.backController(1, model);
//		//group.setMessage("그룹명 중복:사용중인 그룹명입니다. 다른 그룹명을 입력해주세요");
//		log.info("< {} >", group);
//		return (GroupBean)model.getAttribute("group");
//	}
//	
//	@PostMapping("/MemberJoin")
//	public GroupBean groupMemberJoin(Model model, @ModelAttribute GroupBean group) {
//		model.addAttribute("group", group);
//		auth.backController(2, model);
//		
////		group.setMessage("그룹명 중복: 사용중인 그룹명 입니다. 다른 그룹명을 입력해주세요.");
//		log.info("< {} >",group);
//		return (GroupBean)model.getAttribute("group");
//	}
//	
//	@PostMapping("/DelTempGroupName")
//	public GroupBean deleteTemp(Model model, @ModelAttribute GroupBean group) {
//		model.addAttribute("group", group);
//		auth.backController(3, model);
//		
////		group.setMessage("그룹명 중복: 사용중인 그룹명 입니다. 다른 그룹명을 입력해주세요.");
//		log.info("< {} >",group);
//		return (GroupBean)model.getAttribute("group");
//	}
	
	//Json
	
	@PostMapping("/GroupDupChk")
	public GroupBean groupDuplicateCheck(Model model, @ModelAttribute GroupBean group) {
		model.addAttribute("group", group);
		auth_json.backController(1, model);

		return (GroupBean)model.getAttribute("group");
	}
	
	@PostMapping("/DelTempGroupName")
	public GroupBean delTempGroupName(Model model, @ModelAttribute GroupBean group) {
		model.addAttribute("group", group);
		auth_json.backController(2, model);
		
		return (GroupBean)model.getAttribute("group");
	}
	
	@PostMapping("/GroupRegistration")
	public GroupBean groupRegistration(Model model, @RequestBody GroupBean group) {
		model.addAttribute("group", group);
		auth_json.backController(0, model);
		log.info("{}", (GroupBean)model.getAttribute("group"));
		return (GroupBean)model.getAttribute("group");
	}
	
	@PostMapping("/StoreCodeChk")
	public GroupBean storeCodeChk(Model model, @ModelAttribute GroupBean group) {
		model.addAttribute("group",group);
		auth_json.backController(3, model);
		log.info("{}", (GroupBean)model.getAttribute("group"));
		return (GroupBean)model.getAttribute("group");
	}
	
	
}
