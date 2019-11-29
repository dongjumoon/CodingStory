package dao;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.DTOInterface;
import model.FreePostDTO;
import model.PostDTO;

public class FreeBoardDAO extends DAO {
	
	@Override
	public int insert(DTOInterface dto) {
		FreePostDTO board = (FreePostDTO)dto;
		
		String sql = "insert into FREE_BOARD_TB (userId, boardTitle, boardContent, imgFileName, imgFileRealName) values(?, ?, ?, ?, ?)";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getUserId());
			pstmt.setString(2, board.getBoardTitle());
			pstmt.setString(3, board.getBoardContent());
			pstmt.setString(4, board.getImgFileName());
			pstmt.setString(5, board.getImgFileRealName());
			
			return pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, conn);
		}
		
		return -1;
	}
	
	@Override
	public int update(DTOInterface dto) {
		FreePostDTO post = (FreePostDTO)dto;
		int boardId = post.getBoardId();
		String title = post.getBoardTitle();
		String content = post.getBoardContent();
		String imgFileName = post.getImgFileName();
		String imgFileRealName = post.getImgFileRealName();
		
		String sql = "update FREE_BOARD_TB set boardTitle = ?, boardContent = ?, imgFileName = ?, imgFileRealName = ? where boardId = ?";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, imgFileName);
			pstmt.setString(4, imgFileRealName);
			pstmt.setInt(5, boardId);
			
			return pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, conn);
		}
		
		return -1;
	}
	
	@Override
	public int delete(DTOInterface dto) {
		PostDTO fPost = (PostDTO)dto;
		int boardId = fPost.getBoardId();
		String userId =  fPost.getUserId();
		
		String sql = "select userId, imgFileRealName from FREE_BOARD_TB where boardId = " + boardId;
		
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {// 글 작성자와 삭제요청하는자가 일치하는지 확인
				if (rs.getString("userId").equals(userId)) {
					// 댓글삭제
					sql = "delete from FREE_COMMENT_TB where boardId = " + boardId;
					stmt.executeUpdate(sql);
					// 이미지 있으면 삭제
					String imgFile = rs.getString("imgFileRealName");
					if (imgFile != null) {
						File file = new File(imgFile);
						file.delete();
					}
					//글삭제
					sql = "delete from FREE_BOARD_TB where boardId = " + boardId;
					return stmt.executeUpdate(sql);
					
				}
			} else {
				return 0; //존재하지 않는 글
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, stmt, conn);
		}
		
		return -1;
	}
	
	public List<FreePostDTO> getBoardList(int pageNum) {
		String sql = "select t1.boardId, " + 
		             "       if(cmtCount is null, " +
		             "          boardTitle, " +
		             "          concat(boardTitle,' <span class=\"cmt-count-viewer\">[',cmtCount,']</span>')) boardTitle, " + 
		             "       userId, boardViews, " + 
		             "       if(date(now()) = date(boardDate), " + 
		             "          date_format(boardDate,'%H:%i'), " + 
		             "          date(boardDate)) boardDate " + 
		             "from FREE_BOARD_TB t1 left join (select boardId, count(cmtId) cmtCount " + 
		             "                                 from FREE_COMMENT_TB " + 
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
		String sql = "select t1.boardId, " + 
                     "       if(cmtCount is null, " +
                     "          boardTitle, " +
                     "          concat(boardTitle,' <span class=\"cmt-count-viewer\">[',cmtCount,']</span>')) boardTitle, " + 
                     "       userId, boardViews, " + 
                     "       if(date(now()) = date(boardDate), " + 
                     "          date_format(boardDate,'%H:%i'), " + 
                     "          date(boardDate)) boardDate " + 
                     "from FREE_BOARD_TB t1 left join (select boardId, count(cmtId) cmtCount " + 
                     "                                 from FREE_COMMENT_TB " + 
                     "                                 group by boardId) t2            on t1.boardId = t2.boardId " + 
                     "where boardTitle like concat('%"+search+"%')" + 
                     "order by t1.boardId desc " + 
                     "limit 0, 20";
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
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
			JdbcUtil.close(rs, stmt, conn);
		}
		
		return null;
	}
	
	// 해당 페이지에서 보여줄 페이징 표시 갯수 구하기
	public int getPageCount(int pageNum) {
		String sql = "select ceil(count(boardId) / ?) boardCount " + 
		             "from (select boardId from FREE_BOARD_TB limit ?, ?) t1";
		
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
	
	public FreePostDTO getPost(int boardId) {
		String sql = "select boardId, userId, boardTitle, boardContent, boardViews, " +
  		             "       if(date(now()) = date(boardDate), " +
  		             "          date_format(boardDate,'%H:%i'), " +
  		             "          date(boardDate)) boardDate, " +
  		             "          imgFileName, imgFileRealName " +
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
				post.setBoardContent(rs.getString("boardContent"));
				post.setBoardDate(rs.getString("boardDate"));
				post.setBoardViews(rs.getInt("boardViews"));
				post.setImgFileName(rs.getString("imgFileName"));
				post.setImgFileRealName(rs.getString("imgFileRealName"));
				
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
