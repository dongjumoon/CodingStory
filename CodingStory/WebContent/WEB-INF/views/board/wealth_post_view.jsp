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
	<p>${post.userId}</p>
	<c:if test="${post.imgFileName != null}">
		<img src="${contextPath}/images_upload/${post.imgFileRealName}" alt="${post.imgFileName}">
	</c:if>
	<p class="board-content">${post.boardContent}</p>
<c:if test="${user == post.userId}">
	<div class="board-btn-box">
		<a href="delete.user?boardId=${post.boardId}" class="delete-btn">삭제</a>
		<a href="update?boardId=${post.boardId}">수정</a>
	</div>
</c:if>
</section>
<section class="comment-list">
	<h4 class="screen-out">댓글란</h4>
	<ul>
		<!-- 댓글 목록 -->
	</ul>
	<nav>
		<h4 class="screen-out">페이지 네비게이션</h4>
		<div class="cmt-page-nav">
			<!-- 댓글 페이징 -->
		</div>
	</nav>
	<c:if test="${user != null}">
		<div class="comment-write">
			<textarea></textarea>
			<button>댓글<br>작성</button>
		</div>
	</c:if>
	<script src="${contextPath}/js/comment.js?version=1.0"></script>
</section>