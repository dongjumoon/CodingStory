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
import util.StringUtil;

public class ChatDAO {
	private static final int SAVE_CHAT_COUNT = 20;
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
		ResultSet rs = null;
		
		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, chat.getFromUserId());
			pstmt.setString(2, chat.getChatContent());
			
			int result = pstmt.executeUpdate();
			if (result == 1) {//메세지 1개 추가한후 20개 넘는지확인
				sql = "select chatId from CHAT_TB order by chatDate desc, chatId desc limit ?, 99";//20개 를 초과하는것들
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, SAVE_CHAT_COUNT);
				
				rs = pstmt.executeQuery();
				while (rs.next()) {//다 지움
					sql = "delete from CHAT_TB where chatId = " + rs.getInt(1);
					pstmt = conn.prepareStatement(sql);
					result = pstmt.executeUpdate();
					if (result == 0) {
						JdbcUtil.rollback(conn);
						return -1;
					}
				}
				conn.commit();
				return 1;
			}
			JdbcUtil.rollback(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JdbcUtil.rollback(conn);
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, conn);
		}
		
		return -1;
	}
	
	public List<ChatDTO> getChatList() {
		String sql = "select chatId, fromUserId, chatContent, chatTime " + 
		             "from (select chatId, fromUserId, chatContent, chatDate, " + 
		             "             if(date(now()) = date(chatDate), " + 
		             "                date_format(chatDate,'%H:%i'), " + 
		             "                date_format(chatDate,'%c월%e일')) chatTime " + 
		             "      from CHAT_TB) t1 " + 
		             "order by chatDate";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt= conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			List<ChatDTO> chatList = new ArrayList<>();
			while (rs.next()) {
				ChatDTO row = new ChatDTO();
				row.setChatId(rs.getInt("chatId"));
				row.setFromUserId(rs.getString("fromUserId"));
				row.setChatDate(rs.getString("chatTime"));
				row.setChatContent(StringUtil.parseHtml(rs.getString("chatContent")));
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

	public int getNewChatCount(String userId) {
		String sql = "select (select max(chatId) from CHAT_TB) - lastSeenChatId "
                   + "from USER_TB "
                   + "where userId = ?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, pstmt, conn);
		}
		
		return -1;
	}
	
	public int getNewChatCount(int lastChatId) {
		String sql = "select max(chatId) - ? from CHAT_TB";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, lastChatId);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, pstmt, conn);
		}
		
		return -1;
	}

}
