var contextPath = location.href.match("localhost") == null ? "" : "/CodingStory";//로컬에서 테스트할때마다 바꿔주지 않기위해
function loadCommentList(pageNum){
	$.ajax({
		url: contextPath + "/comment",
		type: "post",
		data: {boardId: boardId, pageNum: pageNum},
		dataType: "json",
		success: function(data){
			var cmtList = $(".comment-list>ul");
			cmtList.empty();
			data.cmts.forEach(function(i){
				var li = $("<li/>");
				li.append(
					$("<p/>").html(i.cmtUser),
					$("<p/>").html(i.cmtDate),
					$("<p/>").html(i.cmtContent)
				);
				cmtList.append(li);
			});
			var pageNav =  $(".comment-list>nav>div");
			pageNav.empty();
			if (data.pageCount > 0) {
				pageNav.append($("<button/>").text(data.pageAreaNum == 0 ? 1 : data.pageAreaNum));
				for (var i = 1; i <= data.pageCount; i++) {
					var pNum = Number(data.pageAreaNum) + i;
					var btn = $("<button/>").text(pNum);
					if (pNum === Number(pageNum)) {
						btn.css({"font-size":"22px","font-weight":"bold"});
					}
					pageNav.append(btn);
				}
				pageNav.append($("<button/>").text(data.nextPageAreaNum));
			}
		}
	});
}
loadCommentList(1);
$(".comment-write button").click(function(){
	var cmtContent = $(".comment-write textarea");
	if (cmtContent.val().length > 0) {
		var content = cmtContent.val();
		$.ajax({
			url: contextPath + "/addComment",
			type: "post",
			data: {boardId: boardId, cmtContent: content},
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

$(".cmt-page-nav").on("click", "button", function(){
	loadCommentList($(this).text());
});
$(".cmt-page-nav").on("mouseover focus", "button", function(){
	$(this).css({"cursor": "pointer",
				 "color": "#3da"});
});
$(".cmt-page-nav").on("mouseout blur", "button", function(){
	$(this).css("color", "");
});