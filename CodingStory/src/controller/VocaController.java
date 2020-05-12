package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class VocaController
 */
@WebServlet("/voca/*")
public class VocaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] uriTokens = request.getRequestURI().split("/");
		String action = uriTokens[uriTokens.length - 1];

		if (action.equals("insert")) {
			String word = request.getParameter("word");
			String description = request.getParameter("description");
		} else if (action.equals("selectAll")) {
			
		} else if (action.equals("getPage")) {
			String pageNum = request.getParameter("pageNum");
			String onePageViewCount = request.getParameter("onePageViewCount");
		} else if (action.equals("quizList")) {
			String totalQuizNumber = request.getParameter("totalQuizNumber");
		} else if (action.equals("rightWordReader")) {
			String word = request.getParameter("word");
			String description = request.getParameter("description");
		} else if (action.equals("isHaveWord")) {
			String word = request.getParameter("word");
		} else if (action.equals("update")) {
			String word = request.getParameter("word");
			String description = request.getParameter("description");
		} else if (action.equals("maxPage")) {
			String onePageViewCount = request.getParameter("onePageViewCount");
		}
		request.setAttribute("title", "영단어장");
		request.getRequestDispatcher("/WEB-INF/views/voca/root.jsp").forward(request, response);
	}

}
