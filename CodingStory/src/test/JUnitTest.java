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
		int boardId = 337;
		String sql = "select count(cmtId) from FREE_COMMENT_TB where boardId = " + boardId;
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		int cmtCount = 0;
		boolean isNext = rs.next(); 
		if (isNext) {
			cmtCount = rs.getInt(1);
		}
		assertEquals(isNext, true);
		
		int clickNum = 1;
		sql = "select * from FREE_COMMENT_TB where boardId = ? order by cmtId limit ?, ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, boardId);
		pstmt.setInt(2, (clickNum - 1) * 10);
		pstmt.setInt(3, 10);
		rs = pstmt.executeQuery();
		int i = 0;
		while (rs.next()) {
			i++;
		}
		assertEquals(cmtCount > 10 ? i == cmtCount : i <= 10, true);
		
		//페이지 번호주면, 그곳의 영역과 nav갯수
		
	}

}
