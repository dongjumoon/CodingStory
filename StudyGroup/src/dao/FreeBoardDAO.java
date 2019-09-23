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

import model.FreeArticleDTO;

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
	
	public int insert(FreeArticleDTO board) {
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

	public List<FreeArticleDTO> getBoardList(int pageNum) {
		String sql = "select boardId, boardTitle, userId, boardDate, boardHit "
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
			List<FreeArticleDTO> freeArticleList = new ArrayList<>();
			while (rs.next()) {
				FreeArticleDTO row = new FreeArticleDTO();
				row.setBoardId(rs.getInt("boardId"));
				row.setBoardTitle(rs.getString("boardTitle"));
				row.setUserId(rs.getString("userId"));
				row.setBoardDate(rs.getDate("boardDate"));
				row.setBoardHit(rs.getInt("boardHit"));
				freeArticleList.add(row);
			}
			return freeArticleList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, pstmt, conn);
		}
		return null;
		
	}
	
	public FreeArticleDTO getArticle(int boardId) {
		String sql = "select boardId, userId, boardTitle, boardContent, boardDate, boardHit "
				   + "from FREE_BOARD_TB "
				   + "where boardId=" + boardId;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				FreeArticleDTO article = new FreeArticleDTO();
				article.setBoardId(rs.getInt("boardId"));
				article.setUserId(rs.getString("userId"));
				article.setBoardTitle(rs.getString("boardTitle"));
				article.setBoardContent(rs.getString("boardContent"));
				article.setBoardDate(rs.getDate("boardDate"));
				article.setBoardHit(rs.getInt("boardHit"));
				return article;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, stmt, conn);
		}
		return null;
	}
}
