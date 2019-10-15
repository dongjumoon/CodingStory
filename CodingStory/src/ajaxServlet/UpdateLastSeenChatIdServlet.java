package ajaxServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;

/**
 * Servlet implementation class UpdateLastSeenChatIdServlet
 */
@WebServlet("/updateLastSeenChatId")
public class UpdateLastSeenChatIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userId = (String)request.getSession().getAttribute("user");
		int lastChatId = Integer.parseInt(request.getParameter("lastChatId"));
		if (userId != null) {
			new UserDAO().updateLastSeenChatId(userId, lastChatId);
		}
	}

}
