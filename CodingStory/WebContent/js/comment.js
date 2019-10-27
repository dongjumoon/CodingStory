var contextPath = location.href.match("localhost") == null ? "" : "/CodingStory";//로컬에서 테스트할때마다 바꿔주지 않기위해

var tokens = location.pathname.split("/");
var boardType = tokens[tokens.length - 2].toUpperCase();
loadCommentList(1);
function loadCommentList(pageNum){
	$.ajax({
		url: contextPath + "/comment",
		type: "post",
		data: {boardId: boardId, pageNum: pageNum, boardType: boardType},
		dataType: "json",
		success: function(data){// 댓글과 pageNav 다시 그리기
			var cmtList = $(".comment-list>ul");
			cmtList.empty();
			data.cmts.forEach(function(i){
				var li = $("<li/>");
				li.append(
					$("<p/>").html(i.cmtId),
					$("<p/>").html(i.cmtUser),
					$("<p/>").html(i.cmtDate),
					$("<p/>").html(i.cmtContent)
				);
				// 보고있는 사람과 해당 댓글 작성자가 일치하면 수정,삭제버튼 추가
				if (userId === i.cmtUser) {
					var p = $("<p/>");
					p.append(
						$("<button/>").addClass("comment-update-btn").text("수정"),
						$("<button/>").addClass("comment-delete-btn").text("삭제")
					)
					li.append(p);
				}
				cmtList.append(li);
			});
			
			
			var pageNav =  $(".comment-list>nav>div");
			pageNav.empty();
			if (data.pageCount > 0) {
				// '<<' 버튼
				if (data.pageAreaNum !== '0') {
					pageNav.append($("<button/>").addClass("back-area-btn").text(data.pageAreaNum));
				}
				
				if (!(data.pageCount === '1' && data.pageAreaNum === '0')) {
					for (var i = 1; i <= data.pageCount; i++) {
						var pNum = Number(data.pageAreaNum) + i;
						var btn = $("<button/>").text(pNum);
						
						if (pNum === Number(pageNum)) {//현재 보고있는 페이지라면 알수있게 표시
							btn.css({"font-size":"22px","font-weight":"bold"});
							btn.addClass("current-page");
						}
						pageNav.append(btn);
					}					
				}
				
				// '>>' 버튼
				if (data.nextPageAreaNum !== '-1') {
					pageNav.append($("<button/>").addClass("next-area-btn").text(data.nextPageAreaNum));					
				}
			}
		}
	});
}

$(".comment-write button").click(function(){
	var cmtContent = $(".comment-write textarea");
	if (cmtContent.val().length > 0) {
		var content = cmtContent.val();
		$.ajax({
			url: contextPath + "/addComment",
			type: "post",
			data: {boardId: boardId, cmtContent: content, boardType: boardType},
			success: function(data){
				loadCommentList(data);
			}
		});
	} else {
		alert("댓글 내용을 입력해주세요.");
		cmtContent.focus();
	}
	cmtContent.val("");
});

// 페이지 버튼 이벤트 ' << ? ? ? ? ? >> '
var currentPage = '1';
$(".cmt-page-nav").on("click", "button:not(.current-page)", function(){
	currentPage = $(this).text();
	loadCommentList(currentPage);
});
$(".cmt-page-nav").on("mouseover focus", "button:not(.current-page)", function(){
	$(this).css({"cursor": "pointer",
				 "color": "#3da"});
});
$(".cmt-page-nav").on("mouseout blur", "button:not(.current-page)", function(){
	$(this).css("color", "");
});


// 수정,삭제관련
//<li>
//	<p>7</p>
//	<p>local</p>
//	<p>2019-10-09</p>
//	<p>여기는 댓글 내용</p>
//	<p>
//		<button class="comment-update-btn">수정</button>
//		<button class="comment-delete-btn">삭제</button>
//	</p>
//</li>
var beforeCommentContent;
var beforeComment;
$(".comment-list").on("click", ".comment-update-btn", function(){
	beforeCommentContent = $(this).parent().prev().text();
	beforeComment = $(this).parent().parent().clone();//수정취소 버튼 클릭시 되돌릴 객체
	// 수정모드로 교체
	$(this).parent().prev().replaceWith($("<textarea/>").val(beforeCommentContent).fadeIn());
	// 버튼 클래스 바꾸기.
	$(this).get(0).className = "update-mode-submit-btn";
	$(this).text("완료");
	$(this).next().get(0).className = "update-mode-rollback-btn";
	$(this).next().text("취소");
});

$(".comment-list").on("click", ".comment-delete-btn", function(){
	var isDelete = confirm("정말 삭제하시겠습니까?");
	if (isDelete) {
		var cmtId = $(this).parent().prevAll().last().text();
		$.ajax({
			url: contextPath + "/deleteComment",
			type:"post",
			data:{cmtId: cmtId, boardType: boardType},
			success:function(data){
				if (data === '1') {
					alert("삭제되었습니다.");
					loadCommentList(1);
				}
			}
		});
	}
});

$(".comment-list").on("click", ".update-mode-submit-btn", function(){
	var cmtId = $(this).parent().prevAll().last().text();
	var cmtContent = $(this).parent().prev().val();
	var cmtBox = $(this).parent().parent();
	$.ajax({
		url: contextPath + "/updateComment",
		type:"post" ,
		data:{cmtId: cmtId, cmtContent: cmtContent, boardType: boardType},
		success: function(data){
			if (data === '1') {
            	beforeComment.children("p:nth-child(4)").text(cmtContent);
            	cmtBox.replaceWith(beforeComment.fadeIn());
			}
		}
	});
});

$(".comment-list").on("click", ".update-mode-rollback-btn", function(){
	$(this).parent().parent().replaceWith(beforeComment.fadeIn());
});