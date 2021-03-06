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
 * Servlet implementation class UpdateCommentServlet
 */
@WebServlet("/updateComment")
public class UpdateCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cmtId = Integer.parseInt(request.getParameter("cmtId"));
		String cmtContent = request.getParameter("cmtContent");
		String requestorId = (String)request.getSession().getAttribute("user");
		BoardType boardType = BoardType.valueOf(request.getParameter("boardType"));
		
		int result = new CommentDAO(boardType).update(cmtId, cmtContent, requestorId);
		
		response.getWriter().append("" + result);
	}
}
