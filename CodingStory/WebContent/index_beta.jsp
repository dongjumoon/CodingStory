<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>코딩이야기</title>
<style>
	/* reset */
  	* {margin:0;padding:0;box-sizing:border-box;list-style:none;text-decoration:none;}
  	fieldset {border:none;}
  	select::-ms-expand {display:none;}
  	a {color:black;}
	/* common */
 	.screen-out {display:none;}
 	.mousePointer {cursor: pointer;}
 	#blind {position:fixed; display:none; width:100%; top:0; height:100vh; background-color:rgba(0,0,0,.5); z-index:110;}
	body {font-family:'굴림', sans-serif;}
	button {border:none; outline:none; background-color: transparent;}
	
	/* -- main -- */
	/* header */
	header {
		position:fixed; height:50px; width:100%;
		background-color:white; top:0; z-index:100;
		box-shadow:0 -2px 10px -2px;}
 	h1 {display:none;}
 	#search-form {
 		width:30%; min-width:400px; height:34px; padding:6px 0 0 10px; margin-top:8px; 
 		box-shadow:0 0 3px -1px; border-radius:20px; position:relative; left:50%;
 		transform:translateX(-50%);}
 	#search-form select {
 		border:none; background-color:white; height:22px; padding:0 0 2px 10px; 
 		-webkit-appearance: none; -moz-appearance:none; -o-appearance:none; appearance:none;}
 	#search-form button[type=submit] {position:absolute; right:0; margin:-9px 9px 0 0;}
 	#search {border:none; outline:none; width:calc(100% - 135px); display:inline-block; font-size:16px;}
 	#search:focus {border:1px solid green;}
 	#search+button .fa {font-size:17px; padding:10px;}
 	#gnb-switch-label {position:absolute; width:50px; top:0; height:50px; line-height:54px; text-align:center; transition:all .3s ease;}
 	#gnb-switch-label:hover {border-radius:50%; background-color:#eee;}
 	#gnb-switch-label .fontAwesome,
 	#virtual-elements .fontAwesome {font-size:22px;}
 	#gnb {
 		background-color:white;
 		position:fixed; top:0; width:230px; margin-left:-240px;
 		height:100vh; overflow:auto; z-index:120;
		box-shadow:8px 32px 15px -20px;}
 	#gnb-switch:checked + #gnb {margin-left:0;}
 	#gnb #virtual-elements {
 		display:block; height:50px; width:50px; line-height:54px;
 		background-color:white; text-align:center; transition:all .3s ease;}
 	#gnb #virtual-elements:hover {border-radius:50%; background-color:#eee;}
 	#gnb #virtual-elements .fontAwesome {font-size:22px;}
 	#gnb #virtual-elements::after {content:"";position:absolute;left:50px; width:180px;height:50px;background-color:white;}
 	#gnb a {padding:28px 0; display:block; text-align:center; transition:all .3s ease;}
 	#gnb a:hover {background-color:#eee;}
 	#user-nav-bar {position:absolute; right:5px; top:15px;}
 	#user-nav-bar li {float:left;}
 	#user-nav-bar a {padding:10px; transition:all .3s ease;}
 	#user-nav-bar a:hover {border-radius:50%; background-color:#eee;}
 	#user-nav-bar #login-atag {border:2px solid #eee; border-radius:15px; margin-left:10px;}
 	#user-nav-bar .user-nav-bar-font {font-size:22px; vertical-align:middle;}
 	
 	/* main contents */
 	#wrap {width:calc(100% - 230px); min-width:320px; margin-left:230px; margin-top:50px;}
 	#contents {max-width:1500px; margin:auto;}
 	#contents #main-view {
 		width:80%; margin:80px auto 0; position:relative;
 		background-image:url("images/main_img.jpg"); background-size:cover; height:250px;}
 	#contents #main-title {
 		position:absolute; top:50%; left:50%; color:white; font-size:80px; white-space:nowrap;
 		text-shadow:1px 2px 5px #000; transform:translate(-50%,-50%);}
 	#contents>section {width:80%; margin:auto; padding:30px;}
 	#contents>section h3,
 	#contents>section h3 a {padding:15px 0;}
 	#contents>section:not(:last-child) {border-bottom:1px solid black;}
 	#contents .video-forum,
 	#contents .video-forum ul {overflow:hidden;}
 	#contents .video-forum li {width:23%; position:relative; float:left;}
 	#contents .video-forum li:not(:first-child) {margin-left:2.66%;}
 	#contents .video-forum li .video-box {width:100%; padding-bottom:56.26%}
 	#contents .video-forum li .video-box iframe {position: absolute; width: 100%; height: 100%; border:none;}
 	#contents .forum table {border-collapse:collapse; width:100%;text-align:center;}
 	#contents .forum table thead {border-top:2px solid black;}
 	#contents .forum table thead td {padding:13px 10px;} 
 	#contents .forum td:nth-child(1) {width:1px; white-space: nowrap;}
 	#contents .forum td:nth-child(2) {}
 	#contents .forum td:nth-child(3) {width:1px; white-space: nowrap;}
 	#contents .forum td:nth-child(4) {width:1px;}
 	#contents .forum td:nth-child(5) {width:1px; white-space: nowrap;}
 	#contents .forum table tbody td:nth-child(2) {text-align:left; padding:10px;}
 	#contents .forum table tbody td:nth-child(2) a:hover {text-decoration:underline;}
 	#contents .forum table td {border-bottom: 1px solid #bbb; padding:10px;}
 	
 	
 	/* footer */
 	footer {max-width:1500px; min-height:90px; margin:auto; background-color:#FBF8EF; box-shadow:2px 2px 5px -5px; }
 	footer address {text-align:center;}
 	
 	@media (max-width:1350px) {   /* 영상게시판전용 */
        #contents .video-forum ul {width:133.33%;}
        #contents .video-forum li:nth-child(2),
        #contents .video-forum li:nth-child(3) {margin-left:3%;}
        #contents .video-forum li:nth-child(4) {display:none;}
    }
 	
 	@media (max-width:1024px) {   /* 테블릿 */
 		#wrap {width:100%; margin-left:0;}
        #contents .video-forum ul {width:200%;}
        #contents .video-forum li:nth-child(1),
        #contents .video-forum li:nth-child(2) {width:23.5%;}
        #contents .video-forum li:nth-child(3) {display:none;}
        #gnb {transition:all .3s ease;}
        #gnb-switch:checked+#gnb+#blind {display:block;}
        #contents #main-title {font-size:70px;}
    	#contents #main-view {width:100%; margin-top:0;}
    	#search-form {box-shadow:0 0 0 0; width:100%; min-width:320px;}
	 	#search-form select,
	 	#search {display:none;}
	 	#search-form button[type=submit] {margin:-8px 0 0 0; transition:background-color,border-radius .3s ease;}
	 	#search-form button[type=submit]:hover {border-radius:50%; background-color:#eee;}
	 	#contents .forum table {box-shadow:0 0 10px #eee;}
	 	#contents .forum table tr {box-shadow:inset 0 5px 5px -3px #eee; border-left:2px solid green;text-align:left;}
	 	#contents .forum table td {display:inline; vertical-align:top; border:0; padding:0; color:#6E6E6E; font-size:12px;}
	 	#contents .forum table thead,
	 	#contents .forum table td:nth-child(1) {display:none;}
	 	#contents .forum table tbody td:nth-child(2) {display:block; width:100%; font-weight:bold; font-size:16px; padding:0;}
	 	#contents .forum table td:nth-child(3) {padding-left:15px;} 
	 	#contents .forum table td:nth-child(4) {padding-left:8px;}
	 	#contents .forum table td:nth-child(5) {padding-left:8px;}
	 	#contents .forum table td:nth-child(5)::before {
	 		content: "."; color:white; display:inline-block; margin-bottom:15px; padding-right:3px; width:20px; height:12px;
	 		background:url("images/views_img.jpg") no-repeat;}
	 	#contents .forum table td span {} /* 작성자 클릭시 */
	 	#contents .forum table td a {padding:15px 15px 8px; display:block;}
    }
    
    @media (max-width:750px) {   /* 영상게시판전용 */
    	#contents>section {width:100%; margin:0;}
    }
    
    @media (max-width:480px) {   /* 모바일 */
    	#contents .video-forum ul {width:100%;}
    	#contents .video-forum li:nth-child(1) {width:100%;}
        #contents .video-forum li:nth-child(2) {display:none;}
        #contents #main-title {font-size:50px;}
    }
</style>
<link href="https://cdn.pixabay.com/photo/2017/06/10/07/18/list-2389219_960_720.png" rel="shortcut icon" type="image/x-icon" ><!-- title 아이콘 -->
<link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>
<body>
<header>
	<h1>코딩 이야기</h1>
	<div id="search-form">
		<form action="#">
			<fieldset>
				<legend class="screen-out">메인 검색창</legend>
				<select name="searchSelet">
					<option value="">전체검색</option>
					<option value="">영상검색</option>
					<option value="">게시판검색</option>
				</select>
				<span class="screen-out">검색어 :</span><input type="search" name="search" id="search">
				<button type="submit"><i class="fa fa-search mousePointer" aria-hidden="true"></i><span class="screen-out">검색</span></button>
			</fieldset>
		</form>
	</div>
	<label id="gnb-switch-label" for="gnb-switch"><i class="fa fa-bars fontAwesome" aria-hidden="true"></i></label><input type="checkbox" class="screen-out" id="gnb-switch">
	<nav id="gnb">
		<span id="virtual-elements"><i class="fa fa-bars fontAwesome" aria-hidden="true"></i></span>
		<ul>
			<li><a href="#">영상게시판</a></li>
			<li><a href="#">자유게시판</a></li>
			<li><a href="#">제작자소개</a></li>
		</ul>
	</nav>
	<div id="blind"></div><!-- 위치 이동 금지(#gnb-switch+#gnb+#blind) 사용중 -->
	<nav id="user-nav-bar">
		<ul>
			<c:choose>
				<c:when test="${user != null}">
					<li><a href="#"><i class="fa fa-commenting user-nav-bar-font" aria-hidden="true"></i><span class="screen-out">메세지함</span></a></li>	
				</c:when>
				<c:otherwise>
					<li><a href="#"><i class="fa fa-question user-nav-bar-font" aria-hidden="true"></i><span class="screen-out">도움말</span></a></li>
					<li><a href="${pageContext.request.contextPath}/user/login" id="login-atag"><span><i class="fa fa-user-circle user-nav-bar-font" aria-hidden="true"></i> 로그인</span></a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</nav>
</header>
<div id="wrap">
<section id="contents">
	<h2 class="screen-out">게시판 종합</h2>
	<article id="main-view">
		<h3 id="main-title">Coding Story</h3>
	</article>
	<section class="video-forum">
		<h3><a href="#">영상 게시판</a></h3>
		<ul>
			<li>
				<h4 class="screen-out">얄팍한 코딩사전 : 객체지향 프로그래밍이란?</h4>
				<div class="video-box">
				<iframe src="https://www.youtube.com/embed/vrhIxBWSJ04" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
				</div>
			</li>
			<li>
				<h4 class="screen-out">포프TV : 전지적 면접관 시점</h4>
				<div class="video-box">
				<iframe src="https://www.youtube.com/embed/QOqUrMzOTcw" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
				</div>
			</li>
			<li>
				<h4 class="screen-out">알고리즘 투게더 거니 : 1차 브라우저 전쟁</h4>
				<div class="video-box">
				<iframe src="https://www.youtube.com/embed/aY1TCdRWGfU" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
				</div>
			</li>
			<li>
				<h4 class="screen-out">테크보이 워니 : 초보 개발자가 하는 실수들</h4>
				<div class="video-box">
				<iframe src="https://www.youtube.com/embed/6qcQd4HPpTU" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
				</div>
			</li>
		</ul>
	</section>
	<section class="forum">
		<h3><a href="#">자유 게시판</a></h3>
		<table>
			<caption class="screen-out">게시글 목록</caption>
			<thead>
				<tr>
					<td>번호</td>
					<td>제 목</td>
					<td>작성자</td>
					<td>작성일</td>
					<td>조회수</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>91</td>
					<td><a href="#" title="내용보기">백엔드 미구현...</a></td>
					<td><span>문동주</span></td>
					<td>17:20</td>
					<td>8</td>
				</tr>
				<tr>
					<td>90</td>
					<td><a href="#" title="내용보기">즐거운 추석 보내세요~</a></td>
					<td><span>문동주</span></td>
					<td>2019.09.11</td>
					<td>1</td>
				</tr>
				<tr>
					<td>89</td>
					<td><a href="#" title="내용보기">영어 공부...</a></td>
					<td><span>문동주</span></td>
					<td>2019.09.10</td>
					<td>3752</td>
				</tr>
				<tr>
					<td>91</td>
					<td><a href="#" title="내용보기">스파르타아</a></td>
					<td><span>문동주</span></td>
					<td>2019.09.10</td>
					<td>8</td>
				</tr>
				<tr>
					<td>90</td>
					<td><a href="#" title="내용보기">벌써 가을이다!!!</a></td>
					<td><span>문동주</span></td>
					<td>2019.09.10</td>
					<td>1</td>
				</tr>
				<tr>
					<td>89</td>
					<td><a href="#" title="내용보기">면접후기...</a></td>
					<td><span>문동주</span></td>
					<td>2019.09.10</td>
					<td>3752</td>
				</tr>
				<tr>
					<td>91</td>
					<td><a href="#" title="내용보기">즐거운 봄여름가을겨울.</a></td>
					<td><span>문동주</span></td>
					<td>2019.09.10</td>
					<td>8</td>
				</tr>
				<tr>
					<td>90</td>
					<td><a href="#" title="내용보기">벌써 가을이다!!!</a></td>
					<td><span>문동주</span></td>
					<td>2019.09.10</td>
					<td>1</td>
				</tr>
				<tr>
					<td>89</td>
					<td><a href="#" title="내용보기">건강을 잘 챙기자...</a></td>
					<td><span>문동주</span></td>
					<td>2019.09.10</td>
					<td>3752</td>
				</tr>
				<tr>
					<td>89</td>
					<td><a href="#" title="내용보기">벌써 끝나가는 국비수업...</a></td>
					<td><span>문동주</span></td>
					<td>2019.09.10</td>
					<td>3752</td>
				</tr>
			</tbody>
		</table>
	</section>
	<section class="forum">
		<h3><a href="#">자유 게시판</a></h3>
		<table>
			<caption class="screen-out">게시글 목록</caption>
			<thead>
				<tr>
					<td>번호</td>
					<td>제 목</td>
					<td>작성자</td>
					<td>작성일</td>
					<td>조회수</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>91</td>
					<td><a href="#" title="내용보기">백엔드 미구현...</a></td>
					<td><span>문동주</span></td>
					<td>17:20</td>
					<td>8</td>
				</tr>
				<tr>
					<td>90</td>
					<td><a href="#" title="내용보기">즐거운 추석 보내세요~</a></td>
					<td><span>문동주</span></td>
					<td>2019.09.11</td>
					<td>1</td>
				</tr>
				<tr>
					<td>89</td>
					<td><a href="#" title="내용보기">영어 공부...</a></td>
					<td><span>문동주</span></td>
					<td>2019.09.10</td>
					<td>3752</td>
				</tr>
				<tr>
					<td>91</td>
					<td><a href="#" title="내용보기">스파르타아</a></td>
					<td><span>문동주</span></td>
					<td>2019.09.10</td>
					<td>8</td>
				</tr>
				<tr>
					<td>90</td>
					<td><a href="#" title="내용보기">벌써 가을이다!!!</a></td>
					<td><span>문동주</span></td>
					<td>2019.09.10</td>
					<td>1</td>
				</tr>
				<tr>
					<td>89</td>
					<td><a href="#" title="내용보기">면접후기...</a></td>
					<td><span>문동주</span></td>
					<td>2019.09.10</td>
					<td>3752</td>
				</tr>
				<tr>
					<td>91</td>
					<td><a href="#" title="내용보기">즐거운 봄여름가을겨울.</a></td>
					<td><span>문동주</span></td>
					<td>2019.09.10</td>
					<td>8</td>
				</tr>
				<tr>
					<td>90</td>
					<td><a href="#" title="내용보기">벌써 가을이다!!!</a></td>
					<td><span>문동주</span></td>
					<td>2019.09.10</td>
					<td>1</td>
				</tr>
				<tr>
					<td>89</td>
					<td><a href="#" title="내용보기">건강을 잘 챙기자...</a></td>
					<td><span>문동주</span></td>
					<td>2019.09.10</td>
					<td>3752</td>
				</tr>
				<tr>
					<td>89</td>
					<td><a href="#" title="내용보기">벌써 끝나가는 국비수업...</a></td>
					<td><span>문동주</span></td>
					<td>2019.09.10</td>
					<td>3752</td>
				</tr>
			</tbody>
		</table>
	</section>
</section>
<footer>
	<address>address....?</address>
</footer>
</div>
<script>
	function minimumDesktopViewOn() {
		$("#contents #main-title").css("font-size","70px");
		$("#contents #main-view").css({"width":"100%", "margin-top":"0"});
		$(".video-forum ul").css("width", "200%");
		$(".video-forum li:eq(2)").prevAll().css("width", "23.5%");
		$(".video-forum li:eq(2)").css("display", "none");
	}
	function minimumDesktopViewOff() {
		$("#contents #main-title").css("font-size","");
		$("#contents #main-view").css({"width":"", "margin-top":""});
		$(".video-forum ul").css("width", "");
		$(".video-forum li").css({"width": "","display": ""});
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
	$("html").ready(function(){
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
</script>
</body>
</html>