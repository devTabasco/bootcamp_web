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
import services.goods.GoodsManagement;
import services.registration.Registration;

@WebServlet({"/Landing","/MovePage", "/Join", "/GroupDupCheck", "/MemberJoin", "/RegStore", "/RegEmp", "/Access", "/AccessOut", "/GetGoodsCategoryList", "/InsGoodsCategory", "/UpdGoodsCategory", "/GetGoodsList", "/InsGoods"})
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FrontController() {
        super();
    }
    
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String jobCode = req.getRequestURI().substring(req.getContextPath().length()+1);
		ActionBean action = null;
		
		if(jobCode.equals("MemberJoin")) action = new Registration(req).backController(1);
		else if(jobCode.equals("RegStore")) action = new Registration(req).backController(2);
		else if(jobCode.equals("RegEmp")) action = new Registration(req).backController(3);
		else if(jobCode.equals("Access")) action = new Auth(req).backController(1);
		else if(jobCode.equals("AccessOut")) action = new Auth(req).backController(-1);
		else if(jobCode.equals("Landing")) action = new Auth(req).backController(0);
		else if(jobCode.equals("GetGoodsCategoryList")) action = new GoodsManagement(req).backController(1);
		else if(jobCode.equals("InsGoodsCategory")) action = new GoodsManagement(req).backController(2);
		else if(jobCode.equals("UpdGoodsCategory")) action = new GoodsManagement(req).backController(3);
		else if(jobCode.equals("GetGoodsList")) action = new GoodsManagement(req).backController(4);
		else if(jobCode.equals("InsGoods")) action = new GoodsManagement(req).backController(5);
		else {
			action = new ActionBean();
			action.setPage("index.jsp");
			action.setRedirect(false);
		}
		
		//isForward = false
		if(!action.isRedirect()) res.sendRedirect(action.getPage());
		else {//isForward = true
			RequestDispatcher dispatcher = req.getRequestDispatcher(action.getPage());
			dispatcher.forward(req, res);
		}
		
	}
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String jobCode = req.getRequestURI().substring(req.getContextPath().length()+1);
		ActionBean action = null;
		
		if(jobCode.equals("GroupDupCheck")) action = new Registration(req).backController(0);
		else if(jobCode.equals("MovePage")) action = new Registration(req).backController(-1);
		else if(jobCode.equals("Join")) action = new Registration(req).backController(4);
		else {
			action = new ActionBean();
			action.setPage("index.jsp");
			action.setRedirect(false);
		}
		
		if(!action.isRedirect()) res.sendRedirect(action.getPage());
		else {
			RequestDispatcher dispatcher = req.getRequestDispatcher(action.getPage());
			dispatcher.forward(req, res);
		}
	}
}
