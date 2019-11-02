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

import model.BoardType;
import model.CommentDTO;
import util.StringUtil;

public class CommentDAO {
	private Connection conn;
	private String boardType;
	public static final int MAX_PAGE_COUNT = 5; // 페이지 이동 태그 갯수 5 = << ? ? ? ? ? >>
	public static final int PRINT_COUNT = 5; // 한 페이지에 나타낼 댓글 수
	
	public CommentDAO(BoardType boardType) {
		this.boardType = boardType.name() + "_COMMENT_TB ";
		
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
	
	public int insert(CommentDTO comment) {
		String sql = "insert into " + boardType + " (boardId, cmtContent, cmtUser) values(?, ?, ?)";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comment.getBoardId());
			pstmt.setString(2, comment.getCmtContent());
			pstmt.setString(3, comment.getCmtUser());
			
			return pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, conn);
		}
		
		return -1;
	}
	
	public List<CommentDTO> getCommentList(int boardId, int pageNum) {
		String sql =  "select cmtId, cmtUser, cmtContent, " +
		              "       if(date(now()) = date(cmtDate), " +
		              "          date_format(cmtDate,'%H:%i'), " +
		              "          date(cmtDate)) cmtDate " +
		              "from " + boardType +
		              "where boardId = ? " +
		              "order by cmtId " +
		              "limit ?, ?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);
			pstmt.setInt(2, (pageNum - 1) * PRINT_COUNT);
			pstmt.setInt(3, PRINT_COUNT);
			
			rs = pstmt.executeQuery();
			List<CommentDTO> cmtList = new ArrayList<>();
			while (rs.next()) {
				CommentDTO cmt = new CommentDTO();
				cmt.setCmtId(rs.getInt("cmtId"));
				cmt.setCmtUser(rs.getString("cmtUser"));
				cmt.setCmtContent(StringUtil.parseHtml(rs.getString("cmtContent")));
				cmt.setCmtDate(rs.getString("cmtDate"));
				cmtList.add(cmt);
			}
			return cmtList;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, pstmt, conn);
		}
		
		return null;
	}
	
	public int getPageCount(int boardId, int pageNum) {		
		String sql = "select ceil(count(cmtId) / ?) " + 
		             "from (select cmtId " +
		             "      from " + boardType +
		             "      where boardId = ? " +
		             "      order by cmtId " +
		             "      limit ?, ?) t1";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			int pageAreaNum = (pageNum - 1) / MAX_PAGE_COUNT * MAX_PAGE_COUNT;
			pstmt.setInt(1, PRINT_COUNT);
			pstmt.setInt(2, boardId);
			pstmt.setInt(3, pageAreaNum * PRINT_COUNT);
			pstmt.setInt(4, MAX_PAGE_COUNT * PRINT_COUNT + 1);
			
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
	
	public int getLastCommentPage(int boardId) {
		String sql = "select ceil(count(cmtId) / ?) from "+ boardType +" where boardId = ?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, PRINT_COUNT);
			pstmt.setInt(2, boardId);
			
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
	
	public int update(int cmtId, String cmtContent, String requestorId) {
		String sql = "update "+ boardType +" set cmtContent = ? where cmtId = ? and cmtUser = ?";
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cmtContent);
			pstmt.setInt(2, cmtId);
			pstmt.setString(3, requestorId);
			
			return pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, conn);
		}
		
		return -1;
	}
	
	public int delete(int cmtId, String requestorId) {
		String sql = "delete from "+ boardType +" where cmtId = ? and cmtUser = ?";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cmtId);
			pstmt.setString(2, requestorId);
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
