package ajaxServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ChatDAO;
import model.ChatDTO;

/**
 * Servlet implementation class AddChatServlet
 */
@WebServlet("/addChat")
public class AddChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String fromUserId = (String)request.getSession().getAttribute("user");
		String chatContent = request.getParameter("chatContent");
		
		if (fromUserId == null || fromUserId.equals("") || chatContent == null || chatContent.equals("")) {
			return;			
		}
		
		ChatDTO chat = new ChatDTO();
		chat.setFromUserId(fromUserId);
		chat.setChatContent(chatContent);

		response.getWriter().append("" + new ChatDAO().insert(chat));
	}

}
