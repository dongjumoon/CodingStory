package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import model.UserDTO;

public class UserDAO {
	private Connection conn;
	
	public UserDAO() {
		try {
			conn = ConnectionProvider.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int insertUser(UserDTO user) {
		String sql = "insert into user (userid, userpw, username, usergender, userphone, useremail, useraddress) "
				+ "values(?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserPw());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserPhone());
			pstmt.setString(6, user.getUserEmail());
			pstmt.setString(7, user.getUserAddress());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, conn);
		}
		return -1;
	}
	
	public int login(String id, String pw) {
		String sql = "select userpw from user where userid = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("userpw").equals(pw)) {
					return 1;
				}
			}
			return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
}
