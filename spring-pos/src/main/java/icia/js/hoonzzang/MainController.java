package icia.js.hoonzzang;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import icia.js.hoonzzang.beans.GroupBean;
import icia.js.hoonzzang.beans.StoreBean;
import icia.js.hoonzzang.services.auth.Authentication_json;
import icia.js.hoonzzang.utils.ClientInfo;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {
	@Autowired
//	private Authentication auth;
	private Authentication_json auth_json;
	@Autowired
	private ClientInfo clientInfo;
	
	@RequestMapping(value = {"/", "/Index"}, method = RequestMethod.GET)
	public String index() {
		log.info("***** 사이트 진입 *****");
		return "index";
	}
	
	@RequestMapping(value = "/JoinStep", method = RequestMethod.GET)
	public String joinStep() {
		log.info("----- join Form 진입 -----");
//		return "joinForm";
		return "joinForm_json_230112";
	}
	
	@PostMapping("/Access")
	//RequestHeader : request의 Header를 가져오는 annotation.
//	public ModelAndView memberJoin(ModelAndView mav, @RequestHeader Map(String, String) headerInfo, @ModelAttribute GroupBean group) {
	public ModelAndView memberJoin(ModelAndView mav, @RequestHeader("User-Agent") String browser, @ModelAttribute GroupBean group) {
		mav.addObject("group",group);
		auth_json.backController(1, mav);
		log.info("-----로그인 처리-----");
		return mav;
	}
	
	@GetMapping("/goMgr")
	public String goMgr() {
		return "mgr";
	}
	
	@GetMapping("/goSales")
	public String goSales() {
		return "sales";
	}
	
}
