var contextPath = location.href.match("localhost") == null ? "" : "/CodingStory";//로컬에서 테스트할때마다 바꿔주지 않기위해
function loadCommentList(){
	$.ajax({
		url: contextPath + "/comment",
		type: "post",
		data: {boardId: boardId},
		dataType: "json",
		success: function(data){
			var cmtList = $(".comment-list>ul");
			cmtList.empty();
			data.forEach(function(i){
				var li = $("<li/>");
				li.append(
					$("<p/>").html(i.cmtUser),
					$("<p/>").html(i.cmtDate),
					$("<p/>").html(i.cmtContent)
				);
				cmtList.append(li);
			});
		}
	});
}
loadCommentList();
$(".comment-write button").click(function(){
	var cmtContent = $(".comment-write textarea");
	if (cmtContent.val().length > 0) {
		var content = cmtContent.val();
		$.ajax({
			url: contextPath + "/addComment",
			type: "post",
			data: {boardId: boardId, cmtContent: content},
			success: function(data){
				if (data == '1') {
					loadCommentList();						
				}
			}
		});
	} else {
		alert("댓글 내용을 입력해주세요.");
		cmtContent.focus();
	}
	cmtContent.val("");
});