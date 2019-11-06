<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h2 class="screen-out">영상게시판 글쓰기</h2>
<div>
	<form action="${contextPath}/board/video" method="post" class="board-write-form">
		<fieldset>
			<c:if test="${post == null}">
				<legend>게시물 작성</legend>
				<p><span class="screen-out">제목:</span><input type="text" name="boardTitle" maxlength="30" placeholder="제목" required></p>
				<p class="video-url-input">
					<span class="screen-out">영상 URL:</span>
					<input type="text" name="videoURL" id="video-url" maxlength="100" placeholder="영상 링크" required><!-- 
				 --><a href="https://www.youtube.com/" target="_blank" title="유튜브 새창열기"><i class="fa fa-youtube" aria-hidden="true"></i></a>
				</p>
				<p><span class="screen-out">내용:</span><textarea rows="20" cols="50" name="boardContent" placeholder="글 내용" required></textarea></p>
				<p><input type="submit" value="제출"></p>
			</c:if>
			<c:if test="${post != null}">
				<legend>게시물 수정</legend>
				<p><span class="screen-out">제목:</span><input type="text" name="boardTitle" maxlength="30" placeholder="제목" required value="${post.boardTitle}"></p>
				<p class="video-url-input">
					<span class="screen-out">영상 URL:</span>
					<input type="text" name="videoURL" id="video-url" maxlength="100" placeholder="영상 링크" required value="${post.videoURL}"><!-- 
				 --><a href="https://www.youtube.com/" target="_blank" title="유튜브 새창열기"><i class="fa fa-youtube" aria-hidden="true"></i></a>
				</p>
				<p><span class="screen-out">내용:</span><textarea rows="20" cols="50" name="boardContent" required placeholder="글 내용" required>${post.boardContent}</textarea></p>
				<p><input type="hidden" name="boardId" value="${post.boardId}"><input type="submit" value="수정"></p>
			</c:if>
		</fieldset>
	</form>
</div>