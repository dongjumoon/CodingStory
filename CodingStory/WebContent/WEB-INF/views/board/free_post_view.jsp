<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h2 class="screen-out">글 내용</h2>
<section class="post-viewer">
	<h4>${post.boardTitle}</h4>
	<p>${post.boardDate}</p>
	<p>조회수 ${post.boardViews}</p>
	<p>${post.userId}</p>
	<p>${post.boardContent}</p>
<c:if test="${user == post.userId}">
	<div class="board-btn-box">
		<a href="delete?boardId=${post.boardId}" class="delete-btn">삭제</a>
		<a href="update?boardId=${post.boardId}">수정</a>
	</div>
</c:if>
</section>
