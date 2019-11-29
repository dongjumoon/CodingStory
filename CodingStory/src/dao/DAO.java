package dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.DTOInterface;

public abstract class DAO {
	
	protected Connection conn;
	public static final int MAX_PAGE_COUNT = 5; // 페이지 이동 태그 갯수 5 = << ? ? ? ? ? >>
	public static final int PRINT_COUNT = 10; // 한 페이지에 나타낼 게시물의 수
	
	public DAO() {
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
	
	public abstract int insert(DTOInterface dto);
	public abstract int update(DTOInterface dto);
	public abstract int delete(DTOInterface dto);
	
}
