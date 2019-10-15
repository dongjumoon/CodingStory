function minimumDesktopViewOn() {
	$("#contents #main-title").css("font-size","70px");
	$("#contents #main-view").css({"width":"100%", "margin-top":"0"});
	$(".video-board ul").css("width", "200%");
	$(".video-board li:eq(2)").prevAll().css("width", "23.5%");
	$(".video-board li:eq(2)").css("display", "none");
}
function minimumDesktopViewOff() {
	$("#contents #main-title").css("font-size","");
	$("#contents #main-view").css({"width":"", "margin-top":""});
	$(".video-board ul").css("width", "");
	$(".video-board li").css({"width": "","display": ""});
}
function getHtmlWidth() {
	var htmlWidth = $("html").width();
	var filter = "win16|win32|win64|mac|macintel";
	if (navigator.platform) {
		if (filter.indexOf(navigator.platform.toLowerCase()) >= 0) {
			//#gnb 는 100vh 이고 이거보다 크다면 스크롤이 있는것으로 판단
			if ($("#gnb").height() < $("html").height()) {
				htmlWidth += 17;// 스크롤바 크기 더하기
			}
		} 
	}
	return htmlWidth;
}
//gnb 화면 요청시
$("#gnb-switch-label").click(function(){
	var htmlWidth = getHtmlWidth();
	var isDesktop = htmlWidth > 1024;
	if (isDesktop) {
		$("#wrap").css({
			"width":"calc(100% - 230px)",// 230px = #gnb넓이
			"margin-left":"230px"
		});
		if (htmlWidth <= 1254) {
			minimumDesktopViewOn();
		}
	}
});

function gnbSwitchOff() {
	var htmlWidth = getHtmlWidth();
	if (htmlWidth > 1024 && htmlWidth <= 1350) {
		minimumDesktopViewOff();
	}
	$("#gnb-switch").get(0).checked = false;
	$("#blind").css("display","");
	$("#wrap").css({
		"width":"100%",
		"margin-left":"0"
	});
}
//가상으로 복사한 gnbSwitch영역
$("#virtual-elements").click(function(event){
	var e = event ? event : window.event;
	if (e.clientX < 50) {
		//메뉴버튼
		gnbSwitchOff();
	} else {
		//홈버튼?
	}
});
$("#blind").click(function(){
	gnbSwitchOff();
});

//메세지창 스크롤 가장 아래로
function lastChatView(){
	var isChecked = $("#message-box-switch").is(":checked");
	if (isChecked) {
		var box = $("#chat-box");
		box.scrollTop(box[0].scrollHeight);
	}
}
// IE에서 <label for='?'> 로 input checkbox 작동이 안되어서 아래 방법으로 수정
$("#message-box-switch-label").click(function(){
	$("#message-box-switch").click();
	if ($("#message-box-switch").is(":checked")) {
		// message-box 열때 new-message-count 0 으로 돌리고 안보이게.
		$("#message-box-switch-label .new-message-count").css("display", "");
		$("#message-box-switch-label .new-message-count").text("0");
	} else {
		// message-box 닫을때 어디까지 봤는지 업데이트
		if (lastChatId !== undefined) {
			$.ajax({
				url: contextPath + "/updateLastSeenChatId",
				type: "post",
				data: {lastChatId: lastChatId}
			});
		}
	}
	$("#white-blind").css("display", "block");
	lastChatView();
});

$(window).resize(function(){
	lastChatView();//채팅창 열려있으면 가장 아래로 스크롤
	searchModeOff();
	
	//가로 사이즈 변경시 그에 맞는 반응(컨텐츠 크기조정)
	var isGnbSwitchOn = $("#gnb-switch").get(0).checked;
	var htmlWidth = getHtmlWidth();
	if (isGnbSwitchOn) {//gnb가 보이는 상태일떄
		if (htmlWidth > 1254) {
			minimumDesktopViewOff();
			
		} else if (htmlWidth < 1024){
			gnbSwitchOff();
			
		} else {
			minimumDesktopViewOn();
		}
	} else if (htmlWidth > 1024) {//pc일때
		$("#search-form button[type=submit]").css("right", "");
		minimumDesktopViewOff();
		if (isReadyOnMobile) {
			$("#gnb-switch-label").click();
			isReadyOnMobile = false;
		}
	}
});

var isReadyOnMobile;
$(window).ready(function(){
	var htmlWidth = getHtmlWidth();
	var isDesktop = htmlWidth > 1024;
	if (isDesktop) {
		$("#gnb-switch-label").click();
		$("#gnb-switch")[0].checked = true;
	} else {
		var userNavBarWidth = $("#user-nav-bar").width();
		$("#search-form button[type=submit]").css("right", userNavBarWidth + 10);
		$(window).load(function(){// 해결방법을 찾자
			var userNavBarWidth2 = $("#user-nav-bar").width();
			var isSearchModeOff = $("#user-nav-bar").css("display") !== "none";
			if (userNavBarWidth !== userNavBarWidth2 && isSearchModeOff) {
				$("#search-form button[type=submit]").css("right", userNavBarWidth2 + 10);
			}
		});
		isReadyOnMobile = true;
	}
});

$("#search-form button[type=submit]").click(function(){
	if ($("#search").val().length == 0) {
		alert("검색어를 입력해주세요");
		$("#search").focus();
		return false;
	}
	var htmlWidth = getHtmlWidth();
	var isMobile = htmlWidth <= 1024;
	var isSearchModeOff = $("#user-nav-bar").css("display") !== "none";
	if (isMobile && isSearchModeOff) {//모바일인데 #user-nav-bar가 보인다면 검색모드가 아닌상태에서 클릭한것이므로 검색모드로 전환
	 	$("#search-form select").css("display", "inline-block"); 
	 	$("#search").css("display", "inline-block").focus();
	 	$("#search-form button[type=submit]").css("right","10px");
		$("#gnb-switch-label").css("display", "none");
		$("#user-nav-bar").css("display", "none");
		return false;
	}
});

$("#search-form select").change(function(){$("#search").focus();});

function searchModeOff() {
	var htmlWidth = getHtmlWidth();
	var isDesktop = htmlWidth > 1024;
	if (isDesktop) {
		$("#search-form button[type=submit]").css("right", "");
	} else {
		var userNavBarWidth = $("#user-nav-bar").width();
		$("#search-form button[type=submit]").css("right", userNavBarWidth + 10);
	}
	$("#search-form select").css("display", ""); 
	$("#search").css("display", "");
	$("#gnb-switch-label").css("display", "");
	$("#user-nav-bar").css("display", "");
};
$("#wrap").click(searchModeOff);

var contextPath = location.href.match("localhost") == null ? "" : "/CodingStory";//로컬에서 테스트할때마다 바꿔주지 않기위해
var lastChatId;
function updateChatList() {
	if (arguments[0] === "pageLoad") {
		// 페이지 로드되고 첫 요청시
		$.ajax({
			url: contextPath + "/chat",
			type: "post",
			dataType: "json",
			success : function(data){ // 채팅목록 다시 출력
				var chatBox = $("#chat-box");
				//지우기전 스크롤이 끝까지 내린 상태인지.
				var isMaxScroll = chatBox.scrollTop() === chatBox[0].scrollHeight - chatBox.height();
				
				chatBox.empty();
				
				var chatList = data.chatList;
				for (var i = 0; i < chatList.length; i++) {
					var li = $("<li/>");
					li.append(
						$("<p/>").text(chatList[i].fromUserId),
						$("<p/>").text(chatList[i].chatDate),
						$("<p/>").text(chatList[i].chatContent)
					);
					if (chatList[i].fromUserId === userId) {
						li.addClass("my-msg");
					}
					chatBox.append(li);
					if (i + 1 === chatList.length) {
						lastChatId = chatList[i].chatId;
					}
				}
				
				if (isMaxScroll) chatBox.scrollTop(chatBox[0].scrollHeight);
				// 해당 유저의 읽지 않은 메세지 수 적용
				var newMsgCountViewer = $("#message-box-switch-label .new-message-count");
				newMsgCountViewer.text(data.newChatCount);
				// 채팅창 닫혀있고, 읽지 않은 메세지가 있으면 .new-message-count 보이게
				var isCloseChatBox = !$("#message-box").is(":visible");
				var isNewMessage = newMsgCountViewer.text() !== '0'; 
				if (isCloseChatBox && isNewMessage) {
					newMsgCountViewer.css("display","block");
				}
			}
		});		
	} else if (lastChatId !== undefined) {
		$.ajax({
			url: contextPath + "/chat",
			type: "post",
			data: {lastChatId: lastChatId},
			dataType: "json",
			success : function(data){ // 채팅목록 다시 출력
				if (data.noMessage === undefined) {
					var chatBox = $("#chat-box");
					//지우기전 스크롤이 끝까지 내린 상태인지.
					var isMaxScroll = chatBox.scrollTop() === chatBox[0].scrollHeight - chatBox.height();
					
					chatBox.empty();
					
					var chatList = data.chatList;
					for (var i = 0; i < chatList.length; i++) {
						var li = $("<li/>");
						li.append(
							$("<p/>").text(chatList[i].fromUserId),
							$("<p/>").text(chatList[i].chatDate),
							$("<p/>").text(chatList[i].chatContent)
						);
						if (chatList[i].fromUserId === userId) {
							li.addClass("my-msg");
						}
						chatBox.append(li);
						if (i + 1 === chatList.length) {
							lastChatId = chatList[i].chatId;
						}
					}
					
					if (isMaxScroll) chatBox.scrollTop(chatBox[0].scrollHeight);
					
					var isCloseChatBox = !$("#message-box").is(":visible");
					if (isCloseChatBox) {
						var newMsgCountViewer = $("#message-box-switch-label .new-message-count");
						var beforeCount = Number(newMsgCountViewer.text());
						newMsgCountViewer.text(beforeCount + Number(data.newChatCount));
						newMsgCountViewer.css("display","block");
					}
				}
			}
		});
	}
}
//로그인 한 회원이라면 message-box 가 존재함.
if($("#message-box")[0] !== undefined) {
	updateChatList("pageLoad");
	$('#chat-content').keydown(function(event){
		var e = event ? event : window.event;
		if (e.keyCode == 13) {//엔터키라면.
			$.ajax({
				url: contextPath + "/addChat",
				type: "post",
				data: {chatContent:$(this).val()},
				success: function(){
					updateChatList();
				}
			});
			$(this).val("");
			return false;
		}
	});
	setInterval(updateChatList, 3000);
}


$("#help-switch").click(function(){
    $(".help-message").toggleClass("screen-out");
    $("#white-blind").css("display", "block");
    return false;
});

$(".delete-btn").click(function(){
	return confirm("정말 삭제하시겠습니까?");
});

$("#white-blind").click(function(){
	$("#message-box-switch-label").click();
	$("#help-switch").click();
	$(this).css("display", "");
});

$(".close-btn").click(function(){
	$(this).parent().css("display", "none");
});