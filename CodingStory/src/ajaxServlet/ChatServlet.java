package ajaxServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ChatDAO;
import model.ChatDTO;

/**
 * Servlet implementation class ChatServlet
 */
@WebServlet("/chat")
public class ChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		List<ChatDTO> chatList = new ChatDAO().getChatList();
		if (chatList == null || chatList.size() == 0) return;
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("[");
		for (int i = 0; i < chatList.size(); i++) {
			strBuffer.append("{\"fromUserId\":\"" + chatList.get(i).getFromUserId() + "\"");
			strBuffer.append(",\"chatDate\":\""+ chatList.get(i).getChatDate() +"\"");
			strBuffer.append(",\"chatContent\":\""+ chatList.get(i).getChatContent() +"\"");
			strBuffer.append(",\"chatId\":\""+ chatList.get(i).getChatId() + "\"}");
			if (chatList.size() -1 != i) strBuffer.append(",");
		}
		strBuffer.append("]");
		response.getWriter().write(strBuffer.toString());
	}

}
