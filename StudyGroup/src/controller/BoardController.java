package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FreeBoardDAO;
import model.FreeBoardDTO;

/**
 * Servlet implementation class BoardController
 */
@WebServlet("/board/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] uriTokens = request.getRequestURI().split("/");
		String action = uriTokens[uriTokens.length - 1];
		
		if (action.equals("free")) {
			// 자유게시판 정보 , page
			String page = request.getParameter("page");
			if (page == null) page = "1";
			
			request.setAttribute("title", "자유게시판");
			request.getRequestDispatcher("/WEB-INF/views/board/free_board.jsp").forward(request, response);
		} else if (action.equals("video")) {
			request.setAttribute("title", "영상게시판");
			request.getRequestDispatcher("/WEB-INF/views/board/video_board.jsp").forward(request, response);
		} else if (action.equals("write.user")) {
			String boardType = uriTokens[uriTokens.length - 2];
			if (boardType.equals("free")) {
				request.setAttribute("title", "자유게시판");
				request.getRequestDispatcher("/WEB-INF/views/board/free_board_write.jsp").forward(request, response);
			} else if (boardType.equals("video")) {
				request.setAttribute("title", "영상게시판");
				request.getRequestDispatcher("/WEB-INF/views/board/video_board_write.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("/error404").forward(request, response);
			}
		} else {
			request.getRequestDispatcher("/error404").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] uriTokens = request.getRequestURI().split("/");
		String action = uriTokens[uriTokens.length - 1];
		
		if (action.equals("free")) {
			String title = request.getParameter("boardTitle");
			String content = request.getParameter("boardContent");
			String user = (String)request.getSession().getAttribute("user");
			FreeBoardDTO freeBoard = new FreeBoardDTO();
			freeBoard.setBoardTitle(title);
			freeBoard.setBoardContent(content);
			freeBoard.setUserId(user);
			int result = new FreeBoardDAO().insert(freeBoard);
			if (result == 1) {
				//성공
			} else if (result == 0) {
				//입력오류
			} else {
				//db오류
			}
		} else if (action.equals("video")) {
		} else if (action.equals("write.user")) {
		} else {
			request.getRequestDispatcher("/error404").forward(request, response);
		}
	}

}
