package controller;

import java.awt.image.RescaleOp;
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

/**
 * Servlet implementation class FrontController
 */
@WebServlet({"/GroupDupCheck","/MemberJoin", "/RegStore", "/RegEmp", "/Access", "/AccessOut"})
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		request.setCharacterEncoding("UTF-8");
		ActionBean action = null;
		String jobCode = request.getRequestURI().substring(request.getContextPath().length()+1);
		Registration registration;
		
		if(jobCode.equals("GroupDupCheck")) {
			registration = new Registration();
			action = registration.backController(0, request); 
		}
		
		if(action.isRedirect()) {
			response.sendRedirect(action.getPage());
		}else {
			RequestDispatcher dispatcher = request.getRequestDispatcher(action.getPage());
			dispatcher.forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8"); //post 전송 시에만 가능
//		System.out.println(request.getParameter("groupName"));
//		System.out.println(request.getParameter("groupCeo"));
//		System.out.println(request.getParameter("groupPin"));
//		doGet(request, response);
		ActionBean action = null;
		String jobCode = request.getRequestURI().substring(request.getContextPath().length()+1);
		Registration registration;
		Auth auth;
		
		if(jobCode.equals("MemberJoin")) {
			registration = new Registration();
			action = registration.backController(1, request);
		}else if(jobCode.equals("RegStore")) {
			registration = new Registration();
			action = registration.backController(2, request);			
		}else if(jobCode.equals("RegEmp")) {
			registration = new Registration();
			action = registration.backController(3, request);
		}else if(jobCode.equals("Access")) {
			auth = new Auth();
			action = auth.backController(4, request);
		}else if(jobCode.equals("AccessOut")) { 
			auth = new Auth();
			action = auth.backController(5, request);
		}else if(jobCode.equals("GroupDupCheck")) {
			registration = new Registration();
			action = registration.backController(0, request); 
		}
		
		if(action.isRedirect()) {
			response.sendRedirect(action.getPage());
		}else {
			RequestDispatcher dispatcher = request.getRequestDispatcher(action.getPage());
			dispatcher.forward(request, response);
		}
		
	}

}
