<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	var boardId = ${post.boardId};
</script>
<h2 class="screen-out">글 내용</h2>
<section class="post-viewer">
	<h4>${post.boardTitle}</h4>
	<p>${post.boardDate}</p>
	<p>조회수 ${post.boardViews}</p>
	<p class="user-id">${post.userId}</p>
	<div class="video-box">
		<iframe src="https://www.youtube.com/embed/${post.videoURL}" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
	</div>
	<p class="board-content">${post.boardContent}</p>
<c:if test="${user == post.userId}">
	<div class="board-btn-box">
		<a href="delete?boardId=${post.boardId}" class="delete-btn">삭제</a>
		<a href="update?boardId=${post.boardId}">수정</a>
	</div>
</c:if>
</section>