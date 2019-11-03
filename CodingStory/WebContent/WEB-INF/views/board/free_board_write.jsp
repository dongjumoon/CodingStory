<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h2 class="screen-out">자유게시판 글쓰기</h2>
<div>
	<form action="${contextPath}/board/free" method="post" enctype="multipart/form-data" class="board-write-form">
		<fieldset>
			<c:if test="${post == null}">
				<legend>게시물 작성</legend>
				<p><span class="screen-out">제목:</span><input type="text" name="boardTitle" maxlangth="30" placeholder="제목" required></p>
				<p>
					<input type="file" name="imgFileName" id="img-file-name" class="screen-out">
					<input type="text" name="fileNameViewer" id="file-name-viewer" readonly placeholder="이미지 추가 ">
				</p>
				<p><span class="screen-out">내용:</span><textarea rows="20" cols="50" name="boardContent" placeholder="글 내용" required></textarea></p>
				<p><input type="submit" value="제출"></p>
			</c:if>
			<c:if test="${post != null}">
				<legend>게시물 수정</legend>
				<p><span class="screen-out">제목:</span><input type="text" name="boardTitle" maxlangth="30" placeholder="제목" required value="${post.boardTitle}"></p>
				<p><span class="screen-out">내용:</span><textarea rows="20" cols="50" name="boardContent" placeholder="글 내용" required>${post.boardContent}</textarea></p>
				<p><input type="hidden" name="boardId" value="${post.boardId}"><input type="submit" value="수정"></p>
			</c:if>
		</fieldset>
	</form>
</div>

<script>
	$("#file-name-viewer").click(function(){
		$("#img-file-name").click();
	});
	$("#img-file-name").change(function(){
		var tokens = $(this).val().split("\\");
		var fileName = tokens[tokens.length - 1];
		var fileNameViewer = $("#file-name-viewer");
		fileNameViewer.val(fileName);
		
		if (fileName.length > 0) {
			//jpg png git 체크
			var ext = fileName.substr(fileName.lastIndexOf(".") + 1).toLowerCase();
			if (!(ext === "jsp" || ext === "png" || ext === "gif")) {
				fileNameViewer.css({"border" : "1px solid red", "outline" : "red solid 1px"});
				alert("jpg / png / gif 파일만 추가할 수 있습니다.");
				return;
			}
		}
		fileNameViewer.css({"border" : "", "outline" : ""});
	});
</script>