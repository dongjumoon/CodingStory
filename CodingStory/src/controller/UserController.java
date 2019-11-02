package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import model.UserDTO;

/**
 * Servlet implementation class ServletControll
 */
@WebServlet("/user/*")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] uriTokens = request.getRequestURI().split("/");
		String action = uriTokens[uriTokens.length - 1];

		if (action.equals("login")) {
			request.getSession().removeAttribute("resultURI");// 로그인 화면에 강제로 온것이 아니기에.
			request.setAttribute("title", "로그인");
			request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, response);
			
		} else if (action.equals("signUp")) {
			request.setAttribute("title", "회원가입");
			request.getRequestDispatcher("/WEB-INF/views/user/sign_up.jsp").forward(request, response);
			
		} else if(action.equals("logout")) {
			request.getSession().invalidate();
			Cookie cookie = new Cookie("user", null);
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
			
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath.length() == 0 ? "/" : contextPath);
			
		} else {
			request.setAttribute("title", "페이지 요청 오류");
			request.getRequestDispatcher("/error404").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] uriTokens = request.getRequestURI().split("/");
		String action = uriTokens[uriTokens.length - 1];

		if (action.equals("signUp")) {
			UserDTO user = new UserDTO();
			user.setUserId(request.getParameter("id").trim());
			user.setUserPw(request.getParameter("pw"));
			user.setUserName(request.getParameter("name"));
			user.setUserGender(request.getParameter("gender"));
			user.setUserPhone(request.getParameter("phone"));
			user.setUserEmail(request.getParameter("email"));
			user.setUserAddress(request.getParameter("address"));
			int result = new UserDAO().insertUser(user);
			if (result == 1) {
				request.setAttribute("title", "로그인");
				request.getSession().setAttribute("message", "가입이 완료되었습니다.");
				response.sendRedirect(request.getContextPath()+"/user/login");
				return;
				
			} else if (result == 0) {
				request.getSession().setAttribute("message", "이미 존재하는 회원입니다.");
				
			} else {
				request.getSession().setAttribute("message", "데이터베이스 오류로 가입에 실패하였습니다.");
			}
			request.setAttribute("title", "회원가입");
			request.getRequestDispatcher("/WEB-INF/views/user/sign_up.jsp").forward(request, response);
			
		} else if (action.equals("login")) {
			String id = request.getParameter("id").trim();
			String pw = request.getParameter("pw");
			boolean isLongTimeLoginRequest = request.getParameter("longTimeLogin") != null;
			
			int result = new UserDAO().login(id, pw);
			if (result == 1) {
				HttpSession session = request.getSession();
				session.setAttribute("user", id);
				if (isLongTimeLoginRequest) {
					Cookie cookie = new Cookie("user", id);
					cookie.setPath("/");
					cookie.setMaxAge(60 * 60 * 24 * 30);//한달
					response.addCookie(cookie);
				}
				if (id.equals("tester")) {
					// 테스터는 무조건 홈으로
					session.removeAttribute("resultURI");
				}
				// 로그인화면에 강제로 온것이라면
				String resultURI = (String)session.getAttribute("resultURI");
				if (resultURI != null) {
					// 로그인화면 오기 전 페이지로
					session.removeAttribute("resultURI");
					response.sendRedirect(resultURI);
					return;
				}
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath.length() == 0 ? "/" : contextPath);
				return;
				
			} else if (result == 0) {
				request.getSession().setAttribute("message", "회원정보 불일치. 아이디와 비밀번호를 확인해주세요.");
				
			} else {
				request.getSession().setAttribute("message", "데이터베이스 오류로 로그인에 실패하였습니다.");
			}
			
			response.sendRedirect(request.getContextPath() + "/user/login");
			
		} else {
			request.setAttribute("title", "페이지 요청 오류");
			request.getRequestDispatcher("/error404").forward(request, response);
		}
	}

}
