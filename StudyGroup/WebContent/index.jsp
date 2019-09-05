<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>코딩이야기</title>
<style>
	/* reset */
  	* {margin:0;padding:0;box-sizing:border-box;list-style:none;text-decoration:none;}
  	fieldset {border:none;}
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
		box-shadow:0 -2px 10px -2px;
	}
 	h1 {display:none;}
 	#search-form {
 		width:30%; min-width:400px; height:34px; padding:6px 0 0 10px; margin-top:8px; 
 		box-shadow:0 0 3px -1px; border-radius:20px;
 		position:absolute; left:50%;
 		transform:translateX(-50%);
 	}
 	#search-form select {border:none; background-color:white; height:22px; padding:0 0 2px 10px; -webkit-appearance: none;}
 	#search-form button[type=submit] {float:right; margin-right:8px;}
 	#search {border:none; width:calc(100% - 130px); display:inline-block; font-size:16px;}
 	#search+button .fa {font-size:17px; padding:0 8px;}
 	#gnb-switch-label {position:absolute; width:50px; height:50px; line-height:54px; text-align:center;}
 	#gnb-switch-label .fontAwesome,
 	#virtual-elements .fontAwesome {font-size:22px;}
 	#gnb {
 		background-color:white;
 		position:fixed; width:230px; margin-left:-240px;
 		height:100vh;
 		overflow:auto; z-index:120;
		box-shadow:8px 32px 15px -20px;
 	}
 	#gnb-switch:checked + #gnb {margin-left:0;}
 	#gnb #virtual-elements {display:block; height:50px; width:50px; line-height:54px; background-color:white; text-align:center;}
 	#gnb #virtual-elements .fontAwesome {font-size:22px;}
 	#gnb #virtual-elements::after {content:"";position:absolute;left:50px; width:180px;height:50px;background-color:white;}
 	#gnb a {padding:28px 0; display:block; text-align:center;}
/*  	#gnb li:first-child a {box-shadow:inset 0 8px 10px -12px; } */
 	#user-nav-bar {position:absolute; right:5px; top:15px;}
 	#user-nav-bar li {float:left;}
 	#user-nav-bar a {padding:10px;}
 	#user-nav-bar #login-atag {border:2px solid #eee; border-radius:15px; margin-left:10px;}
 	#user-nav-bar .user-nav-bar-font {font-size:22px; vertical-align:middle;}
 	
 	/* main contents */
 	#wrap {width:calc(100% - 230px); min-width:320px; margin-left:230px;}
 	#contents {max-width:1500px; margin:auto;}
 	#contents #main-view {width:80%; margin:80px auto 0; position:relative; background-image:url("images/main_img.jpg"); background-size:cover; height:250px;}
 	#contents #main-title {
 		position:absolute; top:50%; left:50%; color:white; font-size:80px; white-space:nowrap;
 		text-shadow:1px 2px 5px #000;
 		transform:translate(-50%,-50%);
 		
 	}
 	#contents section[id*='board'] {width:80%; margin:auto; padding:30px;}
 	#contents section[id*='board'] h3,
 	#contents section[id*='board'] a {padding:15px 0;}
 	#contents #video-board,
 	#contents #video-board ul {overflow:hidden;}
 	#contents #video-board li {width:23%;height:150px; float:left;}
 	#contents #video-board li:not(:first-child) {margin-left:2.66%;} 
 	#contents #video-board li .video-box {width:100%; height:100%;}
 	#contents #free-board {border-top:1px solid black;}
 	#contents #free-board table {border-collapse:collapse; width:100%;text-align:center;}
 	#contents #free-board table thead {background-color:#FBF5EF; color:#777;}
 	#contents #free-board table tbody td:nth-child(2) {text-align:left;}
 	#contents #free-board table td {border-bottom: 1px solid black; padding:10px;}
 	
 	
 	/* footer */
 	footer {max-width:1500px; min-height:90px; margin:auto; background-color:#FBF8EF; box-shadow:2px 2px 5px -5px; }
 	footer address {text-align:center;}
 	
 	@media (max-width:1260px) {   /* 영상게시판전용 */
        #contents #video-board ul {width:133.33%;}
        #contents #video-board li:nth-child(2),
        #contents #video-board li:nth-child(3) {margin-left:3%;}
        #contents #video-board li:nth-child(4) {display:none;}
    }
 	
 	@media (max-width:1024px) {   /* 테블릿 */
 		#wrap {width:100%; margin-left:0;}
        #contents #video-board ul {width:200%;}
        #contents #video-board li:nth-child(1),
        #contents #video-board li:nth-child(2) {width:23.5%;}
        #contents #video-board li:nth-child(3) {display:none;}
        #gnb {transition:all .3s ease;}
        #gnb-switch:checked+#gnb+#blind {display:block;}
        #gnb li:first-child a {box-shadow:0 0 0;}
    }
    
    @media (max-width:750px) {   /* 영상게시판전용 */
    	#contents section[id*='board'] {width:100%; margin:0;}
    	#contents #main-title {font-size:70px;}
    	#contents #main-view {width:100%; margin-top:50px;}
    }
    
    @media (max-width:480px) {   /* 모바일 */
    	#contents #video-board ul {width:100%;}
    	#contents #video-board li:nth-child(1) {width:100%;}
        #contents #video-board li:nth-child(2) {display:none;}
        #contents #main-title {font-size:50px;}
    }
</style>    
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
	<label id="gnb-switch-label" for="gnb-switch"><i class="fa fa-bars fontAwesome" aria-hidden="true"></i></label><input type="checkbox" class="screen-out" id="gnb-switch" checked>
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
			<li><a href="#"><i class="fa fa-question user-nav-bar-font" aria-hidden="true"></i><span class="screen-out">도움말</span></a></li>
<!-- 			<li><a href="#"><i class="fa fa-commenting user-nav-bar-font" aria-hidden="true"></i><span class="screen-out">메세지함</span></a></li> -->
			<li><a href="#" id="login-atag"><span><i class="fa fa-user-circle user-nav-bar-font" aria-hidden="true"></i> 로그인</span></a></li>
		</ul>
	</nav>
</header>
<div id="wrap">
<section id="contents">
	<h2 class="screen-out">게시판 종합</h2>
	<article id="main-view">
		<h3 id="main-title">Coding Story</h3>
	</article>
	<section id="video-board">
		<h3><a href="#">영상 게시판</a></h3>
		<ul>
			<li>
				<h4 class="screen-out">얄팍한 코딩사전 : 객체지향 프로그래밍이란?</h4>
				<div class="video-box">
				<iframe width="100%" height="100%" src="https://www.youtube.com/embed/vrhIxBWSJ04" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
				</div>
			</li>
			<li>
				<h4 class="screen-out">포프TV : 전지적 면접관 시점</h4>
				<div class="video-box"><iframe width="100%" height="100%" src="https://www.youtube.com/embed/QOqUrMzOTcw" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe></div>
			</li>
			<li>
				<h4 class="screen-out">알고리즘 투게더 거니 : 1차 브라우저 전쟁</h4>
				<div class="video-box"><iframe width="100%" height="100%" src="https://www.youtube.com/embed/aY1TCdRWGfU" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe></div>
			</li>
			<li>
				<h4 class="screen-out">테크보이 워니 : 초보 개발자가 하는 실수들</h4>
				<div class="video-box"><iframe width="100%" height="100%" src="https://www.youtube.com/embed/6qcQd4HPpTU" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe></div>
			</li>
		</ul>
	</section>
	<section id="free-board">
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
					<td>오늘 수업도 수고하셨습니다.</td>
					<td>문동주</td>
					<td>17:20</td>
					<td>8</td>
				</tr>
				<tr>
					<td>90</td>
					<td>벌써 가을이다!!!</td>
					<td>문동주</td>
					<td>2019.09.02</td>
					<td>23</td>
				</tr>
				<tr>
					<td>89</td>
					<td>면접후기...</td>
					<td>문동주</td>
					<td>2019.09.01</td>
					<td>375</td>
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
	//gnb 화면 요청시
	$("#gnb-switch-label").click(function(){//스크롤크기차이 구하기, 스크롤부모이벤막기
		var documentWidth = $(document).width();
		var isDesktop = documentWidth > 1024;
		if (isDesktop) {
			$("#wrap").css({
				"width":"calc(100% - 230px)",
				"margin-left":"230px"
			});
			if (documentWidth <= 1260) {
				$("#video-board ul").css("width", "200%");
				$("#video-board li:eq(2)").prevAll().css("width", "23.5%");
				$("#video-board li:eq(2)").css("display", "none");
			}
		}
	});
	function gnbSwitchOff() {
		var documentWidth = $(document).width();
		if (documentWidth > 1024 && documentWidth <= 1260) {
			$("#video-board ul").css("width", "");
			$("#video-board li").css({"width": "","display": ""});
		}
		$("#gnb-switch").get(0).checked = false;
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
		var documentWidth = $(document).width();
		if (documentWidth > 1260) {
			$("#video-board ul").css("width", "");
			$("#video-board li").css("width", "");
		} else if (documentWidth <= 1024) {
			gnbSwitchOff();
		}
	});
</script>
</body>
</html>