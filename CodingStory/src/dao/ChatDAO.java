package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.ChatDTO;

public class ChatDAO {
	private static final int RETURN_CHAT_COUNT = 20;
	private Connection conn;
	
	public ChatDAO() {
		try {
			InitialContext initCtx = new InitialContext();
			Context envContext = (Context)initCtx.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/mdj44518");
			conn = ds.getConnection();
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int insert(ChatDTO chat) {
		String sql = "insert into CHAT_TB (fromUserId, chatContent) values(?, ?)";
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, chat.getFromUserId());
			pstmt.setString(2, chat.getChatContent());
			
			return pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, conn);
		}
		
		return -1;
	}
	
	public List<ChatDTO> getChatList() {
		String sql = "SELECT chatId, fromUserId, chatContent, chatTime " + 
					 "FROM (SELECT chatId, fromUserId, chatContent, chatDate, " + 
					 "		       if(DATE(NOW()) = DATE(chatDate), " + 
					 "		       DATE_FORMAT(chatDate,'%H:%i'), " + 
					 "		       DATE_FORMAT(chatDate,'%c월%e일')) chatTime " + 
					 "      FROM CHAT_TB " + 
					 "      ORDER BY chatDate desc " + 
					 "      LIMIT ?) t1 " + 
					 "ORDER BY chatDate";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt(1, RETURN_CHAT_COUNT);
			
			rs = pstmt.executeQuery();
			List<ChatDTO> chatList = new ArrayList<>();
			while (rs.next()) {
				ChatDTO row = new ChatDTO();
				row.setChatId(rs.getInt("chatId"));
				row.setFromUserId(rs.getString("fromUserId"));
				row.setChatDate(rs.getString("chatTime"));
				row.setChatContent(rs.getString("chatContent"));
				chatList.add(row);
			}
			return chatList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, pstmt, conn);
		}
		return null;
	}

}
