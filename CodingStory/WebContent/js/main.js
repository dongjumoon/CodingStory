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
				htmlWidth += 17;
			}
		} 
	}
	return htmlWidth;
}
//gnb 화면 요청시
$("#gnb-switch-label").click(function(){// 스크롤부모이벤막기
	var htmlWidth = getHtmlWidth();
	var isDesktop = htmlWidth > 1024;
	if (isDesktop) {
		$("#wrap").css({
			"width":"calc(100% - 230px)",
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
$(window).resize(function(){
	var isGnbSwitchOn = $("#gnb-switch").get(0).checked;
	var htmlWidth = getHtmlWidth();
	if (isGnbSwitchOn) {
		if (htmlWidth > 1254) {
			minimumDesktopViewOff();
		} else if (htmlWidth < 1024){
			gnbSwitchOff();
		} else {
			minimumDesktopViewOn();
		}
	} else if (htmlWidth > 1024) {
		$("#search-form button[type=submit]").css("right", "");
		searchModeOff();
		minimumDesktopViewOff();
		if (isReadyOnMobile) {
			$("#gnb-switch-label").click();
			isReadyOnMobile = false;
		}
	} else {
		if (beforeWidth !== htmlWidth) {
			searchModeOff();
		}
	}
});
var isReadyOnMobile;
$(window).ready(function(){
	var htmlWidth = getHtmlWidth();
	var isDesktop = htmlWidth > 1024;
	if (isDesktop) {
		$("#gnb-switch-label").click();
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
var beforeWidth = getHtmlWidth();
$("#search-form button[type=submit]").click(function(){
	var htmlWidth = getHtmlWidth();
	beforeWidth = htmlWidth;
	var isMobile = htmlWidth <= 1024;
	var isSearchModeOff = $("#user-nav-bar").css("display") !== "none";
	if (isMobile && isSearchModeOff) {
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

//<section id="message-box">
//<h3 class="screen-out">채팅창</h3>
//<ul id="chat-box">
//	<li>
//		<p>문동주<p>
//		<p>15:30</p>
//		<p>대화 내요입니다.22</p>
//	</li>
//</ul>
//</section>

//메세지함이 있을때만?
var contextPath = location.href.match("localhost") == null ? "" : "/CodingStory";
function updateChatList() {
	$.ajax({
		url: contextPath + "/chat",
		type: "post",
		dataType: "json",
		success : function(data){
			var chatBox = $("#chat-box");
			chatBox.empty();
			for (var i = 0; i < data.length; i++) {
				var li = $("<li/>");
				li.append(
					$("<p/>").text(data[i].fromUserId),
					$("<p/>").text(data[i].chatDate),
					$("<p/>").text(data[i].chatContent)
				);
				chatBox.append(li);
			}
			chatBox.scrollTop(chatBox[0].scrollHeight);
		}
	});
}
if($("#message-box")[0] !== undefined) {
	updateChatList();
	$('#chat-content').keydown(function(event){
		var e = event ? event : window.event;
		if (e.keyCode == 13) {
			$.ajax({
				url: contextPath + "/addChat",
				type: "post",
				data: {chatContent:$(this).val()},
				dataType: "json"
			});
			$(this).val("");
			updateChatList();
			return false;
		}
	});
	setInterval(updateChatList, 3000);
}

