<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h2>자유게시판 글쓰기</h2>
<div>
	<form action="${contextPath}/board/free" method="post">
		<fieldset>
			<legend>게시물 작성</legend>
			<p><span class="screen-out">제목:</span><input type="text" name="boardTitle" maxlangth="30"></p>
			<p><span class="screen-out">내용:</span><textarea rows="20" cols="50" name="boardContent"></textarea></p>
			<p><input type="submit" value="제출"></p>
		</fieldset>
	</form>
</div>