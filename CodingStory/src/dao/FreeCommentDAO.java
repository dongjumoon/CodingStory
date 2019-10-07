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

import model.FreeCommentDTO;

public class FreeCommentDAO {
	private Connection conn;
	public static final int MAX_PAGE_COUNT = 5; // 페이지 이동 태그 갯수 5 = << ? ? ? ? ? >>
	public static final int PRINT_COUNT = 10; // 한 페이지에 나타낼 댓글 수
	
	public FreeCommentDAO() {
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
	
	public int insert(FreeCommentDTO comment) {
		String sql = "insert into FREE_COMMENT_TB (parentId, cmtContent, cmtUser) values(?, ?, ?)";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comment.getParentId());
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
	
	public List<FreeCommentDTO> getCommentList(int boardId) {
		String sql = "select cmtUser, cmtContent, cmtDate from FREE_COMMENT_TB where parentId = ? order by cmtId";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);
			rs = pstmt.executeQuery();
			List<FreeCommentDTO> cmtList = new ArrayList<>();
			while (rs.next()) {
				FreeCommentDTO cmt = new FreeCommentDTO();
				cmt.setCmtUser(rs.getString("cmtUser"));
				cmt.setCmtContent(rs.getString("cmtContent"));
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
}
