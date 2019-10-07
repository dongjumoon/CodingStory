package ajaxServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FreeCommentDAO;
import model.FreeCommentDTO;

/**
 * Servlet implementation class CommentServlert
 */
@WebServlet("/comment")
public class CommentServlert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		List<FreeCommentDTO> cmtList = new FreeCommentDAO().getCommentList(boardId);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.append("[");
		for (int i = 0; i < cmtList.size(); i++) {
			out.append("{");
			String cmt = "\"cmtUser\" : \"{0}\","
					   + "\"cmtDate\" : \"{1}\","
					   + "\"cmtContent\" : \"{2}\"";
			out.append(MessageFormat.format(cmt, cmtList.get(i).getCmtUser(),
												 cmtList.get(i).getCmtDate(),
												 cmtList.get(i).getCmtContent()));
			if (cmtList.size() != i+1) {
				out.append("},");
			} else {
				out.append("}");
			}
		}
		out.append("]");
	}

}
