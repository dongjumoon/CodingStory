package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.FreePostDTO;

public class FreeBoardDAO {
	
	private Connection conn;
	
	public FreeBoardDAO() {
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
	
	public int insert(FreePostDTO board) {
		String sql = "insert into FREE_BOARD_TB (userId, boardTitle, boardContent) values(?, ?, ?)";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getUserId());
			pstmt.setString(2, board.getBoardTitle());
			pstmt.setString(3, board.getBoardContent());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, conn);
		}
		return -1;
	}

	public List<FreePostDTO> getBoardList(int pageNum) {
		String sql = "select boardId, boardTitle, userId, boardDate, boardViews "
				   + "from FREE_BOARD_TB "
				   + "order by boardId desc "
				   + "limit ?, ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (pageNum - 1) * 10);
			pstmt.setInt(2, 10);
			rs = pstmt.executeQuery();
			List<FreePostDTO> freePostList = new ArrayList<>();
			while (rs.next()) {
				FreePostDTO row = new FreePostDTO();
				row.setBoardId(rs.getInt("boardId"));
				row.setBoardTitle(rs.getString("boardTitle"));
				row.setUserId(rs.getString("userId"));
				row.setBoardDate(rs.getDate("boardDate"));
				row.setBoardViews(rs.getInt("boardViews"));
				freePostList.add(row);
			}
			return freePostList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, pstmt, conn);
		}
		return null;
		
	}
	
	// 해당 페이지에서 보여줄 페이징 표시 갯수 구하기
	public int getPageCount(int pageNum) {
		String sql = "select ceil(COUNT(boardId) / 10) boardCount " + 
					 "from (select boardId from FREE_BOARD_TB limit ?, 9999999) t1";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			int pageAreaNum = (pageNum - 1) / 5 * 5;
			pstmt.setInt(1, pageAreaNum * 10);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int pageCount = rs.getInt(1);
				return pageCount > 5 ? 5 : pageCount;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, pstmt, conn);
		}
		return -1;
	}
	
	public FreePostDTO getPost(int boardId) {
		String sql = "select boardId, userId, boardTitle, boardContent, boardDate, boardViews "
				   + "from FREE_BOARD_TB "
				   + "where boardId=" + boardId;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				FreePostDTO post = new FreePostDTO();
				post.setBoardId(rs.getInt("boardId"));
				post.setUserId(rs.getString("userId"));
				post.setBoardTitle(rs.getString("boardTitle"));
				post.setBoardContent(rs.getString("boardContent"));
				post.setBoardDate(rs.getDate("boardDate"));
				post.setBoardViews(rs.getInt("boardViews"));
				IncreseViews(boardId, conn);//조회수 증가
				return post;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, stmt, conn);
		}
		return null;
	}
	
	private static void IncreseViews(int boardId, Connection conn) {
		String sql = "update FREE_BOARD_TB set boardViews = boardViews + 1 WHERE boardId =" + boardId;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(stmt);
		}
	}
}
