package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletControll
 */
@WebServlet("/user/*")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] tokens = request.getRequestURI().split("/");
		String cmd = tokens[tokens.length - 1];

		if (cmd.equals("login")) {
			request.setAttribute("title", "로그인 페이지");
			request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, response);
		} else if (cmd.equals("signUp")) {
			request.getRequestDispatcher("/WEB-INF/views/user/signUp.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("/WEB-INF/views/error/error404.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
