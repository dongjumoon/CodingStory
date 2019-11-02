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

import dao.CommentDAO;
import model.BoardType;
import model.CommentDTO;

/**
 * Servlet implementation class CommentServlert
 */
@WebServlet("/comment")
public class CommentServlert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		int pageNum = 1;
		try {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		} catch(Exception e) {};
		BoardType boardType = BoardType.valueOf(request.getParameter("boardType"));
		
		List<CommentDTO> cmtList = new CommentDAO(boardType).getCommentList(boardId, pageNum);
		
		PrintWriter out = response.getWriter();
		out.append("{\"cmts\": [");
		String cmt = "\"cmtId\" : \"{0}\","
		           + "\"cmtUser\" : \"{1}\","
		           + "\"cmtDate\" : \"{2}\","
		           + "\"cmtContent\" : \"{3}\"}";
		
		for (int i = 0; i < cmtList.size(); i++) {
			out.append("{");
			out.append(MessageFormat.format(cmt, cmtList.get(i).getCmtId(),
			                                     cmtList.get(i).getCmtUser(),
			                                     cmtList.get(i).getCmtDate(),
			                                     cmtList.get(i).getCmtContent().replace("\\", "\\\\").replace("\"", "\\\"")));
			if (cmtList.size() != i+1) {
				out.append(",");
			}
		}
		out.append("],");
		// 페이징에 필요한 정보심기
		String format = "\"pageCount\" : \"{0}\","
		              + "\"pageAreaNum\" : \"{1}\","
		              + "\"nextPageAreaNum\" : \"{2}\"}";
		
		int maxPageCount = CommentDAO.MAX_PAGE_COUNT;
		
		int pageCount = new CommentDAO(boardType).getPageCount(boardId, pageNum);
		int pageAreaNum = (pageNum - 1) / maxPageCount * CommentDAO.MAX_PAGE_COUNT;
		int nextPageAreaNum;
		if (pageCount > maxPageCount) {
			nextPageAreaNum = pageAreaNum + pageCount;
			pageCount--;
		} else {
			nextPageAreaNum = -1;
		}
		
		out.append(MessageFormat.format(format, pageCount,
		                                        pageAreaNum,
		                                        nextPageAreaNum));
		
	}

}
