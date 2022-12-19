package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ActionBean;
import services.auth.Auth;
import services.registration.Registration;

@WebServlet({"/MovePage", "/Join", "/GroupDupCheck", "/MemberJoin", "/RegStore", "/RegEmp", "/Access", "/AccessOut"})
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FrontController() {
        super();
    }
    
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String jobCode = req.getRequestURI().substring(req.getContextPath().length()+1);
		ActionBean action = null;
		Registration reg = null;
		Auth auth = null;
		
		if(jobCode.equals("MemberJoin")) {
			reg = new Registration(req);
			action = reg.backController(1);
		}else if(jobCode.equals("RegStore")) {
			reg = new Registration(req);
			action = reg.backController(2);
		}else if(jobCode.equals("RegEmp")) {
			reg = new Registration(req);
			action = reg.backController(3);
		}else if(jobCode.equals("Access")) {
			auth = new Auth(req);
			action = auth.backController(1, req);
		}else if(jobCode.equals("AccessOut")) {
			auth = new Auth(req);
			action = auth.backController(-1, req);
		}
		
		if(!action.isRedirect()) {
			res.sendRedirect(action.getPage());
		}else {
			RequestDispatcher dispatcher = req.getRequestDispatcher(action.getPage());
			dispatcher.forward(req, res);
		}
		
	}
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String jobCode = req.getRequestURI().substring(req.getContextPath().length()+1);
		ActionBean action = null;
		Registration reg = null;
		Auth auth = null;
		
		if(jobCode.equals("GroupDupCheck")) {
			reg = new Registration(req);
			action = reg.backController(0);
		}else if(jobCode.equals("MovePage")) {
			reg = new Registration(req);
			action = reg.backController(-1);
		}else if(jobCode.equals("Join")) {
			reg = new Registration(req);
			action = reg.backController(4);
		}
		
		if(!action.isRedirect()) {
			res.sendRedirect(action.getPage());
		}else {
			RequestDispatcher dispatcher = req.getRequestDispatcher(action.getPage());
			dispatcher.forward(req, res);
		}
	}
}
