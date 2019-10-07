package ajaxServlet;

import java.io.IOException;
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
		int parentId = Integer.parseInt(request.getParameter("parentId"));
		String cmtContent = request.getParameter("cmtContent");
		cmt.setCmtContent(cmtContent);
		cmt.setCmtUser((String)request.getSession().getAttribute("user"));
		cmt.setParentId(parentId);
		new FreeCommentDAO().insert(cmt);
		
		//응답 결정
	}

}
