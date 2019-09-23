<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h2>${article.boardTitle}</h2>
<div>
	<p>${article.boardHit}</p>
	<p>${article.userId}</p>
	<p>${article.boardDate}</p>
	<p>${article.boardContent}</p>
</div>
<div>
	<c:forEach begin="0" end="4">
		<p>댓글댓글</p>
	</c:forEach>
	<!-- 댓글페이징? -->
	<textarea rows="3" cols="30"></textarea>
	<button>댓글작성</button>
</div>