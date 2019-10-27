package ajaxServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommentDAO;
import model.BoardType;

/**
 * Servlet implementation class DeleteCommentServlet
 */
@WebServlet("/deleteComment")
public class DeleteCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cmtId = Integer.parseInt(request.getParameter("cmtId"));
		String requestorId = (String)request.getSession().getAttribute("user");
		BoardType boardType = BoardType.valueOf(request.getParameter("boardType"));
		
		int result = new CommentDAO(boardType).delete(cmtId,requestorId);
		
		response.getWriter().append("" + result);
	}
}
