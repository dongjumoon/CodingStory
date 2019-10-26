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

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import model.VideoPostDTO;

public class VideoBoardDAO {

	private Connection conn;
	public static final int MAX_PAGE_COUNT = 5; // 페이지 이동 태그 갯수 5 = << ? ? ? ? ? >>
	public static final int PRINT_COUNT = 10; // 한 페이지에 나타낼 게시물의 수
	
	public VideoBoardDAO() {
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
	
	public static String getVideoId(String url) {
		String[] tokens = url.split("/");
		String videoId = tokens[tokens.length - 1];
		if (videoId.indexOf('?') >= 0) {
			int startIdx = videoId.indexOf("v=") + 2;
			int endIndex = videoId.indexOf('&', startIdx) < 0 ? videoId.length() : videoId.indexOf('&', startIdx);
			videoId = videoId.substring(startIdx, endIndex);
		}
		return videoId;
	}

	public int insert(VideoPostDTO board) {
		String sql = "insert into VIDEO_BOARD_TB (userId, boardTitle, boardContent, videoURL) values(?, ?, ?, ?)";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getUserId());
			pstmt.setString(2, board.getBoardTitle());
			pstmt.setString(3, board.getBoardContent());
			pstmt.setString(4, board.getVideoURL());
			
			return pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, conn);
		}
		
		return -1;
	}
	
	public int update(int boardId, String title, String content, String videoURL) {
		String sql = "update VIDEO_BOARD_TB set boardTitle = ?, boardContent = ?, videoURL = ? where boardId = ?";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, videoURL);
			pstmt.setInt(4, boardId);
			
			return pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, conn);
		}
		
		return -1;
	}
	
	public VideoPostDTO getPost(int boardId) {
		String sql = "select boardId, userId, boardTitle, boardContent, boardViews, videoURL," +
  		             "       if(date(now()) = date(boardDate), " +
  		             "          date_format(boardDate,'%H:%i'), " +
  		             "          date(boardDate)) boardDate " +
		             "from VIDEO_BOARD_TB " +
		             "where boardId=" + boardId;
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				VideoPostDTO post = new VideoPostDTO();
				post.setBoardId(rs.getInt("boardId"));
				post.setUserId(rs.getString("userId"));
				post.setBoardTitle(rs.getString("boardTitle"));
				post.setBoardContent(rs.getString("boardContent"));
				post.setBoardDate(rs.getString("boardDate"));
				post.setBoardViews(rs.getInt("boardViews"));
				post.setVideoURL(rs.getString("videoURL"));
				
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
	
	public List<VideoPostDTO> getBoardList(int pageNum) {
		String sql = "select t1.boardId, " + 
		             "       if(cmtCount is null, " +
		             "          boardTitle, " +
		             "          concat(boardTitle,' <span class=\"cmt-count-viewer\">[',cmtCount,']</span>')) boardTitle, " + 
		             "       userId, boardViews, " + 
		             "       if(date(now()) = date(boardDate), " + 
		             "          date_format(boardDate,'%H:%i'), " + 
		             "          date(boardDate)) boardDate " + 
		             "from VIDEO_BOARD_TB t1 left join (select boardId, count(cmtId) cmtCount " + 
		             "                                 from VIDEO_COMMENT_TB " + 
		             "                                 group by boardId) t2            on t1.boardId = t2.boardId " + 
	                 "order by t1.boardId desc " + 
	                 "limit ?, ?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (pageNum - 1) * PRINT_COUNT);
			pstmt.setInt(2, PRINT_COUNT);
			
			rs = pstmt.executeQuery();
			List<VideoPostDTO> videoPostList = new ArrayList<>();
			while (rs.next()) {
				VideoPostDTO row = new VideoPostDTO();
				row.setBoardId(rs.getInt("boardId"));
				row.setBoardTitle(rs.getString("boardTitle"));
				row.setUserId(rs.getString("userId"));
				row.setBoardDate(rs.getString("boardDate"));
				row.setBoardViews(rs.getInt("boardViews"));
				videoPostList.add(row);
			}
			return videoPostList;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, pstmt, conn);
		}
		
		return null;
	}
	
	public int getPageCount(int pageNum) {
		String sql = "select ceil(count(boardId) / ?) boardCount " + 
		             "from (select boardId from VIDEO_BOARD_TB limit ?, ?) t1";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			int pageAreaNum = (pageNum - 1) / MAX_PAGE_COUNT * MAX_PAGE_COUNT;
			pstmt.setInt(1, PRINT_COUNT);
			pstmt.setInt(2, pageAreaNum * PRINT_COUNT);
			pstmt.setInt(3, MAX_PAGE_COUNT * PRINT_COUNT + 1);
			
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
	
	public void increseViews(int boardId) {
		String sql = "update VIDEO_BOARD_TB set boardViews = boardViews + 1 WHERE boardId =" + boardId;
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
