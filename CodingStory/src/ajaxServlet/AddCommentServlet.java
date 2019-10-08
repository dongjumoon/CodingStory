package ajaxServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FreeCommentDAO;
import model.FreeCommentDTO;

/**
 * Servlet implementation class AddCommentServlet
 */
@WebServlet("/addComment")
public class AddCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FreeCommentDTO cmt = new FreeCommentDTO();
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		String cmtContent = request.getParameter("cmtContent");
		cmt.setCmtContent(cmtContent);
		cmt.setCmtUser((String)request.getSession().getAttribute("user"));
		cmt.setBoardId(boardId);
		int result = new FreeCommentDAO().insert(cmt);
		PrintWriter out = response.getWriter();
		if (result != 1) {
			out.append("" + result);
		} else {
			int lastPage = new FreeCommentDAO().getLastCommentPage(boardId);
			out.append("" + lastPage);
		}
	}

}
