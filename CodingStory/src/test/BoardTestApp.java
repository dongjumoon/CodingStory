package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import dao.FreeBoardDAO;
import dao.JdbcUtil;

public final class BoardTestApp {
	
	private static Connection conn;
	
	private BoardTestApp() {
		try {
			conn = DriverManager.getConnection("jdbc:mariadb://moon911.com", "", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		BoardTestApp dbQuery = new BoardTestApp();
		String[] users = {"mdj44518", "esj44518", "moon", "mond", "admin"};
		String[] titles = {"기분좋은 하루", "가나다라마바사아자차카타파", "오늘은 맛있는 점심을먹자", "정처산기반 화이팅~"};
		Random r = new Random();
		for (int i = 0; i < 100; i++) {
			String randomUser = users[r.nextInt(users.length)];
			String randomTitle = titles[r.nextInt(titles.length)];
			dbQuery.insert(randomUser, randomTitle, "123");
		}
		JdbcUtil.close(conn);
	}
	
	private void insert(String userId, String boardTitle, String boardContent) {
		String sql = "insert into FREE_BOARD_TB (userId, boardTitle, boardContent) values(?, ?, ?)";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, boardTitle);
			pstmt.setString(3, boardContent);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
	}

}
