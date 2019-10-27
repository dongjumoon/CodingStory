package ajaxServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommentDAO;
import model.BoardType;
import model.CommentDTO;

/**
 * Servlet implementation class AddCommentServlet
 */
@WebServlet("/addComment")
public class AddCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		String cmtContent = request.getParameter("cmtContent");
		BoardType boardType = BoardType.valueOf(request.getParameter("boardType"));
		
		CommentDTO cmt = new CommentDTO();
		cmt.setCmtContent(cmtContent);
		cmt.setCmtUser((String)request.getSession().getAttribute("user"));
		cmt.setBoardId(boardId);
		
		int result = new CommentDAO(boardType).insert(cmt);
		
		PrintWriter out = response.getWriter();
		if (result != 1) {
			out.append("" + result);
		} else {
			int lastPage = new CommentDAO(boardType).getLastCommentPage(boardId);
			out.append("" + lastPage);
		}
	}

}
