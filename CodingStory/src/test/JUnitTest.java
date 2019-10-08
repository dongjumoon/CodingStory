package test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.JdbcUtil;

public class JUnitTest {
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	@Before
	public void setUp() throws Exception {
		conn = DriverManager.getConnection("jdbc:mariadb://moon911.com", "", "");
	}
	@After
	public void tearDown() throws Exception {
		JdbcUtil.close(rs, pstmt, conn);
	}

	@Test
	public void test() throws Exception {
		int boardId = 344;
		String sql = "select count(cmtId) from FREE_COMMENT_TB where boardId = " + boardId;
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		int cmtCount = 0;
		boolean isNext = rs.next(); 
		if (isNext) {
			cmtCount = rs.getInt(1);// 댓글 갯수..? 32개로 잡고
		}
		assertEquals(isNext, true);
		
		int clickNum = 5;// 페이지 입력값*********
		sql = "select * from FREE_COMMENT_TB where boardId = ? order by cmtId limit ?, ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, boardId);
		pstmt.setInt(2, (clickNum - 1) * 5);
		pstmt.setInt(3, 5);
		rs = pstmt.executeQuery();
		int i = 0;
		while (rs.next()) {// 최대 5개
			i++;
		}
		assertEquals(cmtCount > 5 ? i == 5 : i <= 5, true);
		
		
		// 페이지 영역값으로 몇페이지 나오나?
		sql = "SELECT ceil(COUNT(cmtId) / 5) " + 
			  "FROM (SELECT cmtId from FREE_COMMENT_TB where boardId = ? order by cmtId LIMIT ?, ?) t1";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, boardId);
		pstmt.setInt(2, (clickNum - 1) / 5 * 5 * 5);
		pstmt.setInt(3, 5 * 5);
		rs = pstmt.executeQuery();
		int result = 0;
		if (rs.next()) {
			result = rs.getInt(1);
		}
		assertEquals(result, 5);
	}
	
	@Test
	public void commentInsertTest() throws Exception {
		int boardId = 344; 
		String sql = "select ceil(count(cmtId) / 5) from FREE_COMMENT_TB where boardId = " + boardId;
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		int result = 0;		
		if (rs.next()) {	
			result = rs.getInt(1);
		}
		
		assertEquals(result, 4);
	}

}
