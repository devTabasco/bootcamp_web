package icia.js.hoonzzang.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import icia.js.hoonzzang.beans.GroupBean;

public class JwtIntercepter implements HandlerInterceptor {
	@Autowired
	private JsonWebTokenService jwtService;
	@Autowired
	private ProjectUtils utils;
	
	//interceptor : httpServletRequest를 활용
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean result = false;
		String userKey = ((GroupBean)this.utils.getAttribute("AccessInfo")).getGroupCode();
		
		if(request.getMethod().equals("OPTIONS")) {
			result = true;
		}else {
			String jwtToken = request.getHeader("JWTForJSFramework");
			//null이면 CORS 문제 브라우저가 해더에 못담은 것.
			if(jwtToken != null && jwtToken.length() > 0) {
				result = this.jwtService.tokenExpiredDateCheck(jwtToken, userKey);
				if(!result) throw new Exception("Token does not exist");
			}else {
				result = false;
				throw new Exception("Token does not exist");
			}
		}
		return result;
	}
}
