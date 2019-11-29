package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.DAO;
import dao.FreeBoardDAO;
import dao.VideoBoardDAO;
import model.DTOInterface;
import model.FreePostDTO;
import model.PostDTO;
import model.VideoPostDTO;
import util.StringUtil;

/**
 * Servlet implementation class BoardController
 */
@WebServlet("/board/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] uriTokens = request.getRequestURI().split("/");
		String action = uriTokens[uriTokens.length - 1];

		new FreePostDTO();
		if (action.equals("free")) {
			goFreeBoardPage(request, response);
			
		} else if (action.equals("video")) {
			goVideoBoardPage(request, response);
			
		} else if (action.equals("view")) { // 글 내용 요청
			String board = uriTokens[uriTokens.length - 2];// '게시판구분'/view (어느게시판 관련 요청인지)
			int boardId;
			try {
				boardId = Integer.parseInt(request.getParameter("boardId"));
			} catch (Exception e) {
				request.getSession().setAttribute("message", "잘못된 요청입니다.");
				request.setAttribute("title", "페이지 요청 오류");
				request.getRequestDispatcher("/error404").forward(request, response);
				return;
			}
			PostDTO post = null;
			if (board.equals("free") && boardId > 0) {
				FreePostDTO freePost = new FreeBoardDAO().getPost(boardId);
				freePost.setBoardContent(StringUtil.parseHtml(freePost.getBoardContent()));
				String imgFile = freePost.getImgFileRealName();
				if (imgFile != null) {
					String[] tokens = imgFile.replace("\\", "/").split("/");
					String fileRealName = tokens[tokens.length - 1];
					freePost.setImgFileRealName(fileRealName);
				}
				new FreeBoardDAO().increseViews(boardId);
				post = freePost;
				
			} else if (board.equals("video") && boardId > 0) {
				VideoPostDTO videoPost = new VideoBoardDAO().getPost(boardId);
				videoPost.setVideoURL(VideoBoardDAO.getVideoId(videoPost.getVideoURL()));
				videoPost.setBoardContent(StringUtil.parseHtml(videoPost.getBoardContent()));
				new VideoBoardDAO().increseViews(boardId);
				post = videoPost;
				
			} else {
				request.setAttribute("title", "페이지 요청 오류");
				request.getRequestDispatcher("/error404").forward(request, response);
				return;
			}
			request.setAttribute("post", post);
			request.setAttribute("title", post.getBoardTitle());
			request.getRequestDispatcher("/WEB-INF/views/board/"+board+"_post_view.jsp").forward(request, response);
			
		} else if (action.equals("write.user")) {// .user 요청은 LoginCheckFilter를 통해 회원인지 판단(web.xml설정)
			String boardType = uriTokens[uriTokens.length - 2];// '게시판구분'/write.user (어느게시판 관련 요청인지)
			if (boardType.equals("free")) {
				request.setAttribute("title", "자유게시판");
				
			} else if (boardType.equals("video")) {
				request.setAttribute("title", "영상게시판");
				
			} else {
				request.setAttribute("title", "페이지 요청 오류");
				request.getRequestDispatcher("/error404").forward(request, response);
				return;
			}
			request.getRequestDispatcher("/WEB-INF/views/board/"+boardType+"_board_write.jsp").forward(request, response);
			
		} else if (action.equals("delete.user")) {
			String boardType = uriTokens[uriTokens.length - 2];// '게시판구분'/delete (어느게시판 관련 요청인지)
			HttpSession session = request.getSession();
			String userId = (String)session.getAttribute("user");
			int boardId = 0;
			try {
				boardId = Integer.parseInt(request.getParameter("boardId"));
			} catch (Exception e) {
				session.setAttribute("message", "잘못된 요청입니다.");
				response.sendRedirect(request.getContextPath() + "/board/" + boardType);
				return;
			}
			DAO dao = null;
			PostDTO post = new PostDTO();
			post.setBoardId(boardId);
			post.setUserId(userId);
			DTOInterface dto = post;
			if (boardType.equals("free")) {
				dao = new FreeBoardDAO();
				
			} else if (boardType.equals("video")) {
				dao = new VideoBoardDAO();
				
			} else {
				request.setAttribute("title", "페이지 요청 오류");
				request.getRequestDispatcher("/error404").forward(request, response);
				return;
			}
			
			int result = dao.delete(dto);
			if (result == 1) {
				session.setAttribute("message", "삭제 되었습니다");
			} else if (result == 0) {
				session.setAttribute("message", "이미 삭제되었거나 없는 게시물입니다.");
			} else {
				session.setAttribute("message", "DB오류 또는 삭제권한이 없습니다.");
			}
			response.sendRedirect(request.getContextPath() + "/board/" + boardType);
		} else if (action.equals("update")) {
			String boardType = uriTokens[uriTokens.length - 2];// '게시판구분'/update (어느게시판 관련 요청인지)
			HttpSession session = request.getSession();
			int boardId = 0;
			try {
				boardId = Integer.parseInt(request.getParameter("boardId"));
			} catch (Exception e) {
				session.setAttribute("message", "잘못된 요청입니다.");
				response.sendRedirect(request.getContextPath() + "/board/" + boardType);
				return;
			}
			
			PostDTO post = new PostDTO();
			
			if (boardType.equals("free")) {
				post = new FreeBoardDAO().getPost(boardId);
				
			} else if (boardType.equals("video")) {
				post = new VideoBoardDAO().getPost(boardId);
				
			} else {
				request.setAttribute("title", "페이지 요청 오류");
				request.getRequestDispatcher("/error404").forward(request, response);
				return;
			}
			
			if (post != null) {
				post.setBoardContent(post.getBoardContent().replace("&", "&#38;"));
				request.setAttribute("post", post);
				request.setAttribute("title", "수정 페이지");
				request.getRequestDispatcher("/WEB-INF/views/board/" + boardType + "_board_write.jsp").forward(request, response);
				
			} else {
				session.setAttribute("message", "DB오류로 접근에 실패했습니다.");
				response.sendRedirect(request.getContextPath() + "/board/" + boardType);
			}
			
		} else if (action.equals("search")) {
			String searchArea = request.getParameter("searchArea");
			String search = request.getParameter("search");
			if (searchArea == null || search == null) {
				request.getSession().setAttribute("message", "잘못된 접근입니다.");
				response.sendRedirect(request.getContextPath().equals("") ? "/" : request.getContextPath());
			}
			
			if (searchArea.equals("all")) {
				//영상게시판
				List<VideoPostDTO> videoPostList = new VideoBoardDAO().getBoardList(search);
				if (videoPostList != null) {
					request.setAttribute("videoPostList", videoPostList);
				}
				
				//자유게시판
				List<FreePostDTO> freePostList = new FreeBoardDAO().getBoardList(search);
				if (freePostList != null) {
					request.setAttribute("freePostList", freePostList);
				}
				
				request.setAttribute("title", "종합 검색 결과");
				request.getRequestDispatcher("/WEB-INF/views/board/search_result_page.jsp").forward(request, response);
				
			} else if (searchArea.equals("free")) {
				List<FreePostDTO> freePostList = new FreeBoardDAO().getBoardList(search);
				if (freePostList != null) {
					request.setAttribute("freePostList", freePostList);
				}
				request.setAttribute("title", "자유게시판 검색");
				request.getRequestDispatcher("/WEB-INF/views/board/free_board.jsp").forward(request, response);
				
			} else if (searchArea.equals("video")) {
				List<VideoPostDTO> videoPostList = new VideoBoardDAO().getBoardList(search);
				if (videoPostList != null) {
					request.setAttribute("videoPostList", videoPostList);
				}
				request.setAttribute("title", "영상게시판 검색");
				request.getRequestDispatcher("/WEB-INF/views/board/video_board.jsp").forward(request, response);
				
			} else {
				request.setAttribute("title", "페이지 요청 오류");
				request.getRequestDispatcher("/error404").forward(request, response);
			}
		} else {
			request.setAttribute("title", "페이지 요청 오류");
			request.getRequestDispatcher("/error404").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] uriTokens = request.getRequestURI().split("/");
		String action = uriTokens[uriTokens.length - 1];
		HttpSession session = request.getSession();
		//
		DAO dao = null;
		DTOInterface dto = null; 
		String boardId = request.getParameter("boardId");
		if (boardId == null) { //insert
			if (action.equals("free")) { //free insert
				String saveDirectory = session.getServletContext().getRealPath("images_upload/");
				int maxPostSize = 1024 * 1024 * 10;
				String encoding = "UTF-8";
				
				MultipartRequest multipartRequest = null;
				try {
					multipartRequest = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, new DefaultFileRenamePolicy());
				} catch (Exception e) {
					session.setAttribute("message", "업로드 실패. 파일 크기는 10MB를 넘을 수 없습니다.");
					response.sendRedirect(request.getContextPath() + "/board/free");
					return;
				}
				
				String title = multipartRequest.getParameter("boardTitle");
				String content = multipartRequest.getParameter("boardContent");
				String imgFileName = multipartRequest.getOriginalFileName("imgFileName");
				String imgFileRealName = multipartRequest.getFilesystemName("imgFileName");
				String user = (String)request.getSession().getAttribute("user");
				
				//파일이 넘어 왔는데 그것이 jpg,png,gif 파일이 아니라면 등록거부
				if (imgFileName != null) {
					String ext = imgFileName.substring(imgFileName.lastIndexOf(".") + 1).toLowerCase();
					if (!ext.equals("jpg") && !ext.equals("png") && !ext.equals("gif")) {
						File file = new File(saveDirectory + imgFileRealName);
						file.delete();
						session.setAttribute("message", "업로드실패. 파일추가는 사진파일(jpg,png,gif)만 가능합니다");
						response.sendRedirect(request.getContextPath() + "/board/free");
						return;
					}
				}
				
				FreePostDTO freePost = new FreePostDTO();
				freePost.setBoardTitle(title);
				freePost.setBoardContent(content);
				freePost.setUserId(user);
				freePost.setImgFileName(imgFileName);
				freePost.setImgFileRealName(saveDirectory + imgFileRealName);
				
				dao = new FreeBoardDAO();
				dto = freePost;
				
			} else if (action.equals("video")) { // video insert
				String title = request.getParameter("boardTitle");
				String content = request.getParameter("boardContent");
				String videoURL = request.getParameter("videoURL");
				String user = (String)request.getSession().getAttribute("user");
				
				VideoPostDTO videoPost = new VideoPostDTO();
				videoPost.setBoardTitle(title);
				videoPost.setBoardContent(content);
				videoPost.setUserId(user);
				videoPost.setVideoURL(videoURL);
				
				dao = new VideoBoardDAO();
				dto = videoPost;
			}  else {
				request.setAttribute("title", "페이지 요청 오류");
				request.getRequestDispatcher("/error404").forward(request, response);
				return;
			}
			
			int result = dao.insert(dto);
			if (result > 0) {
				session.setAttribute("message", "글 작성이 완료되었습니다.");
			} else {
				session.setAttribute("message", "DB오류로 게시물 작성에 실패하였습니다.");
			}
			
		} else { //update
			String updateId = (String)session.getAttribute("user");
			String insertId = null;
			int parseBoardId = Integer.parseInt(boardId);
			
			if (action.equals("free")) { //free update
				String saveDirectory = session.getServletContext().getRealPath("images_upload/");
				int maxPostSize = 1024 * 1024 * 10;
				String encoding = "UTF-8";
				
				MultipartRequest multipartRequest = null;
				try {
					multipartRequest = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, new DefaultFileRenamePolicy());
				} catch (Exception e) {
					session.setAttribute("message", "업로드 실패. 파일 크기는 10MB를 넘을 수 없습니다.");
					response.sendRedirect(request.getContextPath() + "/board/free");
					return;
				}
				
				FreePostDTO post = new FreeBoardDAO().getPost(parseBoardId);
				
				insertId = post.getUserId();
			
				String title = multipartRequest.getParameter("boardTitle");
				String content = multipartRequest.getParameter("boardContent");
				String imgFileName = multipartRequest.getOriginalFileName("imgFileName");
				String imgFileRealName = multipartRequest.getFilesystemName("imgFileName");
				String fileNameViewer = multipartRequest.getParameter("fileNameViewer");
				if (imgFileRealName != null) imgFileRealName = saveDirectory + imgFileRealName;
				
				if (fileNameViewer.equals("")) {// fileNameViewer 값이 없다면 결국 이미지는 삭제.
					if (imgFileName != null) {
						File file = new File(imgFileRealName);// 넘어온파일 지우고
						file.delete();
					}
					String beforeFile = post.getImgFileRealName();
					if (beforeFile != null) {
						File file = new File(beforeFile);// 기존파일도 지우고
						file.delete();
					}
					imgFileName = null;
					imgFileRealName = null;
				} else {
					if (imgFileName == null) { // fileNameViewer 값이 있고 넘어온 파일은 없다면 기존파일 유지
						imgFileName = post.getImgFileName();
						imgFileRealName = post.getImgFileRealName();
					} else {
						//파일이 넘어 왔는데 그것이 jpg,png,gif 파일이 아니라면 수정거부
						String ext = imgFileName.substring(imgFileName.lastIndexOf(".") + 1).toLowerCase();
						if (!ext.equals("jpg") && !ext.equals("png") && !ext.equals("gif")) {
							File file = new File(imgFileRealName);
							file.delete();
							session.setAttribute("message", "수정실패. 파일은 사진파일(jpg,png,gif)만 가능합니다");
							response.sendRedirect(request.getContextPath() + "/board/free");
							return;
						}
						String beforeFile = post.getImgFileRealName();
						if (beforeFile != null) {
							File file = new File(beforeFile);// 기존파일 지우기
							file.delete();
						}
					}
				}
				
				post.setBoardTitle(title);
				post.setBoardContent(content);
				post.setImgFileName(imgFileName);
				post.setImgFileRealName(imgFileRealName);
				
				dao = new FreeBoardDAO();
				dto = post; 
				
			} else if (action.equals("video")) { // video update
				VideoPostDTO post = new VideoBoardDAO().getPost(parseBoardId);
				
				insertId = post.getUserId();
				
				post.setBoardTitle(request.getParameter("boardTitle"));
				post.setBoardContent(request.getParameter("boardContent"));
				post.setVideoURL(request.getParameter("videoURL"));
				
				dao = new VideoBoardDAO();
				dto = post;
				
			} else {
				request.setAttribute("title", "페이지 요청 오류");
				request.getRequestDispatcher("/error404").forward(request, response);
				return;
			}
			
			
			if (insertId.equals(updateId)) {
				int result = dao.update(dto);
				if (result == 1) {
					session.setAttribute("message", "수정 되었습니다.");
				} else {
					session.setAttribute("message", "DB오류로 수정에 실패하였습니다.");
				}
			} else {
				session.setAttribute("message", "수정 권한이 없습니다.");
			}
		}
		response.sendRedirect(request.getContextPath()+"/board/" + action);
	}

	private void goFreeBoardPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		if (page == null) page = "1";
		int pageInt = 1;
		try {
			pageInt = Integer.parseInt(page);
		} catch (Exception e) {
			request.getSession().setAttribute("message", "잘못된 페이지 번호입니다.");
			request.setAttribute("title", "페이지 요청 오류");
			request.getRequestDispatcher("/error404").forward(request, response);
			return;
		}
		List<FreePostDTO> freePostList = new FreeBoardDAO().getBoardList(pageInt);
		int pageCount = new FreeBoardDAO().getPageCount(pageInt);
		
		if (freePostList != null && pageCount != -1) {
			//페이징에 필요한 정보 얻기 
			int maxPageCount = FreeBoardDAO.MAX_PAGE_COUNT;
			
			int pageAreaNum = (pageInt - 1) / maxPageCount * maxPageCount;
			int nextPageAreaNum;
			if (pageCount > maxPageCount) {
				nextPageAreaNum = pageAreaNum + pageCount;
				pageCount--;
			} else {
				nextPageAreaNum = -1;
			}
			request.setAttribute("freePostList", freePostList);
			request.setAttribute("pageCount", pageCount);
			request.setAttribute("pageAreaNum", pageAreaNum);
			request.setAttribute("nextPageAreaNum", nextPageAreaNum);
			request.setAttribute("title", "자유게시판");
			request.getRequestDispatcher("/WEB-INF/views/board/free_board.jsp").forward(request, response);
			
		} else {
			request.getSession().setAttribute("message", "데이터 조회에 실패하였습니다.");
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath.length() == 0 ? "/" : contextPath);
		}
	}
	
	private void goVideoBoardPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		if (page == null) page = "1";
		int pageInt = 1;
		try {
			pageInt = Integer.parseInt(page);
		} catch (Exception e) {
			request.getSession().setAttribute("message", "잘못된 페이지 번호입니다.");
			request.setAttribute("title", "페이지 요청 오류");
			request.getRequestDispatcher("/error404").forward(request, response);
			return;
		}
		List<VideoPostDTO> videoPostList = new VideoBoardDAO().getBoardList(pageInt);
		int pageCount = new VideoBoardDAO().getPageCount(pageInt);
		
		if (videoPostList != null && pageCount != -1) {
			//페이징에 필요한 정보 얻기 
			int maxPageCount = VideoBoardDAO.MAX_PAGE_COUNT;
			
			int pageAreaNum = (pageInt - 1) / maxPageCount * maxPageCount;
			int nextPageAreaNum;
			if (pageCount > maxPageCount) {
				nextPageAreaNum = pageAreaNum + pageCount;
				pageCount--;
			} else {
				nextPageAreaNum = -1;
			}
			request.setAttribute("videoPostList", videoPostList);
			request.setAttribute("pageCount", pageCount);
			request.setAttribute("pageAreaNum", pageAreaNum);
			request.setAttribute("nextPageAreaNum", nextPageAreaNum);
			request.setAttribute("title", "영상게시판");
			request.getRequestDispatcher("/WEB-INF/views/board/video_board.jsp").forward(request, response);
			
		} else {
			request.getSession().setAttribute("message", "데이터 조회에 실패하였습니다.");
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath.length() == 0 ? "/" : contextPath);
		}
	}

}
