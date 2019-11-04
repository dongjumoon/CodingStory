package ajaxServlet;

import java.io.IOException;
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
		String userId = (String)request.getSession().getAttribute("user");
		String lastChatId = request.getParameter("lastChatId");
		
		if (lastChatId == null) {
			// 없다면 첫 요청
			int newChatCount = new ChatDAO().getNewChatCount(userId);
			
			List<ChatDTO> chatList = new ChatDAO().getChatList();
			if (chatList == null || chatList.size() == 0) {
				return;
			}
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append("{\"chatList\" : [");
			for (int i = 0; i < chatList.size(); i++) {
				strBuffer.append("{\"fromUserId\":\"" + chatList.get(i).getFromUserId() + "\"");
				strBuffer.append(",\"chatDate\":\""   + chatList.get(i).getChatDate() +"\"");
				strBuffer.append(",\"chatContent\":\""+ chatList.get(i).getChatContent().replace("\\", "\\\\").replace("\"", "\\\"") +"\"");
				strBuffer.append(",\"chatId\":\""     + chatList.get(i).getChatId() + "\"}");

				if (chatList.size() -1 != i) {
					strBuffer.append(",");				
				}
			}
			strBuffer.append("], \"newChatCount\" : \""+newChatCount+"\"}");
			response.getWriter().write(strBuffer.toString());
			
		} else {
			// 요청유저가 읽지 않은 챗이 있는지확인. 있으면 넘기고 없으면 noMessage
			int newChatCount = new ChatDAO().getNewChatCount(userId);
			// 사용자가 다른 기기로 메세지를 확인 했는지 파악 
			int lastChatIdNum = Integer.parseInt(lastChatId);
			int nowUnitnewChatCount = new ChatDAO().getNewChatCount(lastChatIdNum);
			
			if (newChatCount == 0 && nowUnitnewChatCount == 0) {
				response.getWriter().append("{\"noMessage\" : \"0\"}");
			} else {
				List<ChatDTO> chatList = new ChatDAO().getChatList();
				if (chatList == null || chatList.size() == 0) {
					return;
				}
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append("{\"chatList\" : [");
				for (int i = 0; i < chatList.size(); i++) {
					strBuffer.append("{\"fromUserId\":\"" + chatList.get(i).getFromUserId() + "\"");
					strBuffer.append(",\"chatDate\":\""   + chatList.get(i).getChatDate() +"\"");
					strBuffer.append(",\"chatContent\":\""+ chatList.get(i).getChatContent().replace("\\", "\\\\").replace("\"", "\\\"") +"\"");
					strBuffer.append(",\"chatId\":\""     + chatList.get(i).getChatId() + "\"}");

					if (chatList.size() -1 != i) {
						strBuffer.append(",");				
					}
				}
				strBuffer.append("], \"newChatCount\" : \""+newChatCount+"\"}");
				response.getWriter().write(strBuffer.toString());
			}
		}
	}

}
