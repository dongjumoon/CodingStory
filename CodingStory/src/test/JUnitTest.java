package test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
		System.out.println("시작");
	}
	@After
	public void tearDown() throws Exception {
		JdbcUtil.close(rs, pstmt, conn);
		System.out.println("끝");
	}

	@Test
	public void test() {
		assertEquals(123, 123);
	}

}
