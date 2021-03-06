<%@page import="model.WealthPostDTO"%>
<%@page import="dao.WealthBoardDAO"%>
<%@page import="model.VideoPostDTO"%>
<%@page import="dao.VideoBoardDAO"%>
<%@page import="model.FreePostDTO"%>
<%@page import="java.util.List"%>
<%@page import="dao.FreeBoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/begin.jspf" %>
<script>
	if (location.protocol == "http:") {
 		location.href = location.href.replace("http:", "https:");
 	}
</script>
<%
	List<FreePostDTO> freePostList = new FreeBoardDAO().getBoardList(1);
	List<WealthPostDTO> wealthPostList = new WealthBoardDAO().getBoardList(1);
	List<VideoPostDTO> videoPostList = new VideoBoardDAO().getBoardList(1);
	pageContext.setAttribute("freePostList", freePostList);
	pageContext.setAttribute("wealthPostList", wealthPostList);
	pageContext.setAttribute("videoPostList", videoPostList);
%>
<link href="${contextPath}/css/index_page.css?version=1.1" rel="stylesheet" type="text/css">
<h2 class="screen-out">게시판 종합</h2>
	<article id="main-view">
		<h3 id="main-title">부화한 개미들</h3>
	</article>
	<section class="video-board">
		<h3><a href="${contextPath}/board/video">영상 게시판 <i class="fa fa-hand-o-left" aria-hidden="true"></i></a></h3>
		<ul>
			<c:forEach var="post" items="${videoPostList}" end="3">
				<li>
					<h4 class="screen-out">${post.boardTitle}</h4>
					<div class="video-box">
						<a href="${contextPath}/board/video/view?boardId=${post.boardId}">
							<img alt="${post.boardTitle}" src="https://img.youtube.com/vi/${post.videoURL}/0.jpg">
						</a>
					</div>
				</li>
			</c:forEach>
		</ul>
	</section>
	<section class="board">
		<h3><a href="${contextPath}/board/free">자유 게시판 <i class="fa fa-hand-o-left" aria-hidden="true"></i></a></h3>
		<table>
			<caption class="screen-out">게시글 목록</caption>
			<thead>
				<tr>
					<td>번호</td>
					<td>제 목</td>
					<td>작성자</td>
					<td>작성일</td>
					<td>조회수</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="post" items="${freePostList}" begin="0" end="4" step="1" >
					<tr>
						<td>${post.boardId}</td>
						<td><a href="${contextPath}/board/free/view?boardId=${post.boardId}" title="내용보기">${post.boardTitle}</a></td>
						<td><span>${post.userId}</span></td>
						<td>${post.boardDate}</td>
						<td>${post.boardViews}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="board-btn-box">
			<a href="${contextPath}/board/free">게시글 더보기</a>
		</div>
	</section>
	
	<section class="board">
		<h3><a href="${contextPath}/board/wealth">재테크 정보 <i class="fa fa-hand-o-left" aria-hidden="true"></i></a></h3>
		<table>
			<caption class="screen-out">게시글 목록</caption>
			<thead>
				<tr>
					<td>번호</td>
					<td>제 목</td>
					<td>작성자</td>
					<td>작성일</td>
					<td>조회수</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="post" items="${wealthPostList}" begin="0" end="4" step="1">
					<tr>
						<td>${post.boardId}</td>
						<td><a href="${contextPath}/board/wealth/view?boardId=${post.boardId}" title="내용보기">${post.boardTitle}</a></td>
						<td><span>${post.userId}</span></td>
						<td>${post.boardDate}</td>
						<td>${post.boardViews}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="board-btn-box">
			<a href="${contextPath}/board/wealth">게시글 더보기</a>
		</div>
	</section>
	
<%@ include file="/WEB-INF/views/common/end.jspf" %>