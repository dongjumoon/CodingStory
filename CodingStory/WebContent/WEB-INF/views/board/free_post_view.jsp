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
<style>
	.comment-list .comment-write > * {vertical-align:top;}
	.comment-list .comment-write {font-size:0;}
	.comment-list .comment-write textarea {width:calc(100% - 50px); height:47px;}
	.comment-list .comment-write button {width:50px; height:47px; background-color:#da6;}
	.comment-list li {padding:20px 0;}
	.comment-list li p:not(:last-child) {display:inline-block;}
	.comment-list li p:nth-child(2) {font-size:12px; color:#aaa}
	.comment-list li p:last-child {padding-top:10px;}
</style>
<section class="comment-list">
	<h4 class="screen-out">댓글란</h4>
	<ul>
		<li>
			<p>mdj44518</p>
			<p>15:20</p>
			<p>멋진 생각이네요 !</p>
		</li>
		<li>
			<p>mdj44518</p>
			<p>15:20</p>
			<p>멋진 생각이네요 !</p>
		</li>
		<li>
			<p>mdj44518</p>
			<p>15:20</p>
			<p>멋진 생각이네요 !</p>
		</li>
	</ul>
	<div class="comment-write">
		<textarea></textarea>
		<button>댓글<br>작성</button>
	</div>
</section>
<script>
// 	var boardId = ${post.boardId};
// 	$.ajax({
// 		url: "${contextPath}" + "/comment",
// 		type:"post",
// 		data:boardId,
// 		dataType:"json",
// 		success:function(data){
			
// 		}
// 	});


	$(".comment-write button").click(function(){
		var cmtContent = $(".comment-write textarea");
		if (cmtContent.val().length > 0) {
			//ajax insert
		} else {
			alert("댓글 내용을 입력해주세요.");
			cmtContent.focus();
		}
		cmtContent.val("");
	});
</script>
