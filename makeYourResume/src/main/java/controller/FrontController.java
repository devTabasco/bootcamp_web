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
import services.regist.Regist;

/**
 * Servlet implementation class FrontController
 */
@WebServlet({"/FrontController", "/Access", "/regist", "/addInfo"})
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ActionBean action = null;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		request.setCharacterEncoding("UTF-8");
		String jobCode = request.getRequestURI().substring(request.getContextPath().length()+1);
		Auth auth;
		Regist regist;
		
		/* Regist */
		if(jobCode.equals("regist")) {
			regist = new Regist();
			action = regist.backController(1, request);
		}else if(jobCode.equals("addInfo")) {
			regist = new Regist();
			action = regist.backController(2, request);
		}
		
		/* Auth */
		if(jobCode.equals("Access")) {
			auth = new Auth();
			action = auth.backController(1, request);
		}
		
		if(action.isRedirect()) {
			//response를 보내주는 방식이 Redirect
			response.sendRedirect(action.getPage());
		}else {
			//request를 다시 return 해주는 방식이 forward
			RequestDispatcher dispatcher = request.getRequestDispatcher(action.getPage());
			dispatcher.forward(request, response);
		}
		
	}

}
