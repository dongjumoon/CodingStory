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
	List<FreePostDTO> freePostList = new FreeBoardDAO().getCommentViewerInBoardList(1);
	pageContext.setAttribute("freePostList", freePostList);
%>
<link href="${contextPath}/css/index_page.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/index_page_media.css" rel="stylesheet" type="text/css">
<h2 class="screen-out">게시판 종합</h2>
	<article id="main-view">
		<h3 id="main-title">Coding Story</h3>
	</article>
	<section class="video-board">
		<h3><a href="${contextPath}/board/video">영상 게시판</a></h3>
		<ul>
			<li>
				<h4 class="screen-out">얄팍한 코딩사전 : 객체지향 프로그래밍이란?</h4>
				<div class="video-box">
				<iframe src="https://www.youtube.com/embed/vrhIxBWSJ04" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
				</div>
			</li>
			<li>
				<h4 class="screen-out">포프TV : 올바른 변수 이름 짓는법</h4>
				<div class="video-box">
				<iframe src="https://www.youtube.com/embed/ZtkIwGZZAq8" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe></iframe>
				</div>
			</li>
			<li>
				<h4 class="screen-out">알고리즘 투게더 거니 : 1차 브라우저 전쟁</h4>
				<div class="video-box">
				<iframe src="https://www.youtube.com/embed/aY1TCdRWGfU" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
				</div>
			</li>
			<li>
				<h4 class="screen-out">테크보이 워니 : 초보 개발자가 하는 실수들</h4>
				<div class="video-box">
				<iframe src="https://www.youtube.com/embed/6qcQd4HPpTU" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
				</div>
			</li>
		</ul>
	</section>
	<section class="board">
		<h3><a href="${contextPath}/board/free">자유 게시판</a></h3>
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
				<c:forEach var="post" items="${freePostList}">
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
	</section>

<%@ include file="/WEB-INF/views/common/end.jspf" %>