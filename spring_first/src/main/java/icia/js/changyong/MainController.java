package icia.js.changyong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {
		
	@RequestMapping(value = {"/","/Index"}, method = RequestMethod.GET)
	public String index() {
		log.info("*****사이트 진입*****");
		return "index";
	}
	
	@RequestMapping(value = "/JoinStep", method = RequestMethod.GET)
	public String joinStep() {
		log.info("*****join Form 진입*****");
		return "joinForm";
	}

//	@RequestMapping(value = "/GroupDupCheck", method = RequestMethod.POST)
//	public String Group() {
//		log.info("*****join Form 진입*****");
//		return "joinForm";
//	}

}
