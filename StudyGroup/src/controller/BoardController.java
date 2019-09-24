package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.FreeBoardDAO;
import model.FreePostDTO;

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
			goFreeBoardPage(request, response);
		} else if (action.equals("view")) { // 글 내용 요청
			String board = uriTokens[uriTokens.length - 2];
			int boardId = 0;
			try {
				boardId = Integer.parseInt(request.getParameter("boardId"));
			} catch (Exception e) {
				request.getSession().setAttribute("message", "잘못된 요청입니다.");
				request.setAttribute("title", "페이지 요청 오류");
				request.getRequestDispatcher("/error404").forward(request, response);
				return;
			}
			String nextPage = "";
			if (board.equals("free") && boardId > 0) {
				FreePostDTO freePost = new FreeBoardDAO().getPost(boardId);
				request.setAttribute("post", freePost);
				request.setAttribute("title", freePost.getBoardTitle());
				nextPage = "/WEB-INF/views/board/free_post_view.jsp";
			} else if (board.equals("video") && boardId > 0) {
				
			} else {
				
			}
			request.getRequestDispatcher(nextPage).forward(request, response);
		} else if (action.equals("video")) {
			goVideoBoardPage(request, response);
		} else if (action.equals("write.user")) {
			String boardType = uriTokens[uriTokens.length - 2];
			if (boardType.equals("free")) {
				request.setAttribute("title", "자유게시판");
				request.getRequestDispatcher("/WEB-INF/views/board/free_board_write.jsp").forward(request, response);
			} else if (boardType.equals("video")) {
				request.setAttribute("title", "영상게시판");
				request.getRequestDispatcher("/WEB-INF/views/board/video_board_write.jsp").forward(request, response);
			} else {
				request.setAttribute("title", "페이지 요청 오류");
				request.getRequestDispatcher("/error404").forward(request, response);
			}
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
		
		if (action.equals("free")) {
			String title = request.getParameter("boardTitle");
			String content = request.getParameter("boardContent");
			String user = (String)request.getSession().getAttribute("user");
			FreePostDTO freePost = new FreePostDTO();
			freePost.setBoardTitle(title);
			freePost.setBoardContent(content);
			freePost.setUserId(user);
			int result = new FreeBoardDAO().insert(freePost);
			HttpSession session = request.getSession();
			if (result > 0) {
				session.setAttribute("message", "글 작성이 완료되었습니다.");
			} else {
				session.setAttribute("message", "DB오류로 게시물 작성에 실패하였습니다.");
			}
			response.sendRedirect(request.getContextPath()+"/board/free");
		} else if (action.equals("video")) {
		} else if (action.equals("write.user")) {
		} else {
			request.setAttribute("title", "페이지 요청 오류");
			request.getRequestDispatcher("/error404").forward(request, response);
		}
	}
	


	private void goFreeBoardPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		if (page == null) page = "1";
		int pageInt = 1;
		try {
			pageInt = Integer.parseInt(page);
		} catch (Exception e) {
			request.getSession().setAttribute("message", "잘못된 페이지 번호입니다.");
			request.setAttribute("title", "페이지 요청 오류");
			request.getRequestDispatcher("/error404").forward(request, response);
			return;
		}
		List<FreePostDTO> freePostList = new FreeBoardDAO().getBoardList(pageInt);
		if (freePostList != null) {
			request.setAttribute("freePostList", freePostList);
		} else {
			request.getSession().setAttribute("message", "데이터 조회에 실패하였습니다.");
		}
		int pageCount = new FreeBoardDAO().getPageCount(pageInt);
		int pageAreaNum = (pageInt - 1) / 5 * 5;
		int nextPageAreaNum;
		if (pageCount == 5) {
			nextPageAreaNum = pageAreaNum + 1 + 5;
		} else {
			nextPageAreaNum = pageAreaNum + pageCount;
		}
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageAreaNum", pageAreaNum);
		request.setAttribute("nextPageAreaNum", nextPageAreaNum);
		request.setAttribute("title", "자유게시판");
		request.getRequestDispatcher("/WEB-INF/views/board/free_board.jsp").forward(request, response);
	}
	
	private void goVideoBoardPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("title", "영상게시판");
		request.getRequestDispatcher("/WEB-INF/views/board/video_board.jsp").forward(request, response);
	}

}
