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
	public static final int MAX_PAGE_COUNT = 5; // 페이지 이동 태그 갯수 5 = << ? ? ? ? ? >>
	public static final int PRINT_COUNT = 10; // 한 페이지에 나타낼 게시물의 수
	
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
	
	public int update(int boardId, String title, String content) {
		String sql = "update FREE_BOARD_TB set boardTitle = ?, boardContent = ? where boardId = ?";
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, boardId);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, conn);
		}
		return -1;
	}
	
	public int delete(int boardId) {
		String sql = "delete from FREE_BOARD_TB where boardId = " + boardId;
		
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			return stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(stmt, conn);
		}
		return -1;
	}

	public List<FreePostDTO> getBoardList(int pageNum) {
		String sql = "select boardId, boardTitle, userId, boardViews, " + 
					 "	     if(date(now()) = date(boardDate), " + 
					 "		 date_format(boardDate,'%H:%i'), " + 
					 "		 date(boardDate)) boardDate " + 
					 "from FREE_BOARD_TB " + 
					 "order by boardId desc " + 
					 "limit ?, ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (pageNum - 1) * PRINT_COUNT);
			pstmt.setInt(2, PRINT_COUNT);
			rs = pstmt.executeQuery();
			List<FreePostDTO> freePostList = new ArrayList<>();
			while (rs.next()) {
				FreePostDTO row = new FreePostDTO();
				row.setBoardId(rs.getInt("boardId"));
				row.setBoardTitle(rs.getString("boardTitle"));
				row.setUserId(rs.getString("userId"));
				row.setBoardDate(rs.getString("boardDate"));
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
	
	public List<FreePostDTO> getCommentViewerInBoardList(int pageNum) {
		String sql = "select t1.boardId, " + 
					 "		 if(cmtCount is null, boardTitle, concat(boardTitle,' <span class=\"cmt-count-viewer\">[',cmtCount,']</span>')) boardTitle, " + 
					 "		 userId, boardViews, " + 
					 "		 if(date(now()) = date(boardDate), " + 
					 "		 date_format(boardDate,'%H:%i'), " + 
					 "		 date(boardDate)) boardDate " + 
					 "from FREE_BOARD_TB t1 left join (select boardId, count(cmtId) cmtCount " + 
					 "	   		 					   FROM FREE_COMMENT_TB " + 
					 "				 				   group by boardId) t2		on t1.boardId = t2.boardId " + 
					 "order by t1.boardId desc " + 
					 "limit ?, ?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (pageNum - 1) * PRINT_COUNT);
			pstmt.setInt(2, PRINT_COUNT);
			rs = pstmt.executeQuery();
			List<FreePostDTO> freePostList = new ArrayList<>();
			while (rs.next()) {
				FreePostDTO row = new FreePostDTO();
				row.setBoardId(rs.getInt("boardId"));
				row.setBoardTitle(rs.getString("boardTitle"));
				row.setUserId(rs.getString("userId"));
				row.setBoardDate(rs.getString("boardDate"));
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
	
	public List<FreePostDTO> getBoardList(String search) {
		String sql = "select boardId, boardTitle, userId, boardViews, " + 
					 "	     if(date(now()) = date(boardDate), " + 
					 "		 date_format(boardDate,'%H:%i'), " + 
					 "		 date(boardDate)) boardDate " + 
					 "from FREE_BOARD_TB " + 
					 "where boardTitle like concat('%"+search+"%')" + 
					 "order by boardId desc " + 
					 "limit 0, 20";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			List<FreePostDTO> freePostList = new ArrayList<>();
			while (rs.next()) {
				FreePostDTO row = new FreePostDTO();
				row.setBoardId(rs.getInt("boardId"));
				row.setBoardTitle(rs.getString("boardTitle"));
				row.setUserId(rs.getString("userId"));
				row.setBoardDate(rs.getString("boardDate"));
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
		String sql = "select ceil(COUNT(boardId) / ?) boardCount " + 
					 "from (select boardId from FREE_BOARD_TB limit ?, ?) t1";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			int pageAreaNum = (pageNum - 1) / MAX_PAGE_COUNT * MAX_PAGE_COUNT;
			pstmt.setInt(1, PRINT_COUNT);
			pstmt.setInt(2, pageAreaNum * PRINT_COUNT);
			pstmt.setInt(3, MAX_PAGE_COUNT * PRINT_COUNT);
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
	
	public FreePostDTO getPost(int boardId) {
		String sql = "select boardId, userId, boardTitle, boardContent, boardViews, " +
  				     "	     if(date(now()) = date(boardDate), " +
  				     "		 date_format(boardDate,'%H:%i'), " +
  				     "		 date(boardDate)) boardDate " +
				     "from FREE_BOARD_TB " +
				     "where boardId=" + boardId;
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
				post.setBoardContent(rs.getString("boardContent").replaceAll("\n", "<br>"));
				post.setBoardDate(rs.getString("boardDate"));
				post.setBoardViews(rs.getInt("boardViews"));
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
	
	public void increseViews(int boardId) {
		String sql = "update FREE_BOARD_TB set boardViews = boardViews + 1 WHERE boardId =" + boardId;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(stmt, conn);
		}
	}
	
}
