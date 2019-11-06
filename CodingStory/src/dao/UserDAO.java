package dao;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.*;

import model.UserDTO;
import security.HashPassword;

public class UserDAO {
	private Connection conn;
	
	public UserDAO() {
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
	
	public int insertUser(UserDTO user) {
		String sql = "select max(chatId) from CHAT_TB";// 가입 시점까지의 채팅은 읽음처리
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			int lastSeenChatId = 0;
			if (rs.next()) {
				lastSeenChatId = rs.getInt(1);
			}
			
			sql = "insert into USER_TB (userId, userPw, userName, lastSeenChatId) " +
		             "values(?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, HashPassword.getHashPw(user.getUserPw()));
			pstmt.setString(3, user.getUserName());
			pstmt.setInt(4, lastSeenChatId);
			
			return pstmt.executeUpdate();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			JdbcUtil.close(rs, pstmt, conn);
		}
		
		return -1;
		
	}
	
	public int login(String id, String pw) {
		String sql = "select userPw from USER_TB where userId = ?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String inputPassword = HashPassword.getHashPw(pw);
				if (rs.getString("userPw").equals(inputPassword)) {
					return 1;
				}
			}
			return 0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, pstmt, conn);
		}
		
		return -1;
	}

	public int updateLastSeenChatId(String userId, int lastChatId) {
		String sql = "update USER_TB set lastSeenChatId = ? where userId = ?";
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, lastChatId);
			pstmt.setString(2, userId);
			
			return pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, conn);
		}
		
		return -1;
	}
}
