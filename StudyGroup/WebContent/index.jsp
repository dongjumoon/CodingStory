<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>코딩이야기</title>
<link >
<style>
	/* reset */
  	* {margin:0;padding:0;box-sizing:border-box;list-style:none;text-decoration:none;}
  	fieldset {border:none;}
  	a {color:black;}
	/* common */
 	.screen-out {display:none;}
 	.ir-box {font-size:0;}
	body {background-color:#eee; font-family:'굴림', sans-serif;}
	
	/* main */
	
	/* header */
	header {position:relative; height:50px; background-color:white;}
	h1 {position:absolute; font-size:25px; left:55px; line-height:50px;}
 	#search-form {
 		width:30%; min-width:400px; height:34px; padding:5px 0 0 10px; margin-top:8px; 
 		border:1px solid black; border-radius:20px;
 		position:absolute; left:50%;
 		transform:translateX(-50%);
 	}
 	#search-form select {border:none; display:inline-block; background-image: url("");}
 	#search-form select option {}
 	#search-form button[type=submit] {display:inline-block;}
 	#search::before {content:"asx";}
 	#search {border:none; width:calc(100% - 150px); display:inline-block; font-size:17px;}
 	#gnb-switch-label {position:absolute; left:16px; top:12px; width:25px; height:25px; background-color:pink;}
 	#gnb {
 		position:fixed; top:50px; width:200px; margin-left:-200px;
 		min-height:calc(100vh - 50px); background-color:rgba(132,423,212,.3);
 		text-align:center;
 	}
 	#gnb-switch:checked + #gnb {margin-left:0;}
 	#gnb a {padding:20px 0; display:block;}
 	#user-nav-bar {position:absolute; right:0;}
 	#user-nav-bar li {float:left;}
 	#user-nav-bar a {padding:calc((50px - 1em) / 2) 10px; line-height:50px;}
 	
 	#wrap {background-color:rgba(22,22,222,.3); width:calc(100% - 200px); margin-left:200px;}
 	/* main contents */
 	#contents #main-img {background-color:rgba(255,0,0,.3); height:250px;}
 	#contents section[id*='board'] {padding-left:50px;}
 	#contents section[id*='board'] h3 {padding:20px 0;}
 	
 	/* footer */
 	footer {min-height: 100px; background-color:rgba(0,0,255,.3);}
 	footer address {text-align:center;}
</style>
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
				<button type="submit">전송</button>
			</fieldset>
		</form>
	</div>
	<label id="gnb-switch-label" for="gnb-switch"></label><input type="checkbox" class="screen-out" id="gnb-switch" checked>
	<nav id="gnb">
		<ul>
			<li><a href="#">영상게시판</a></li>
			<li><a href="#">자유게시판</a></li>
			<li><a href="#">제작자소개</a></li>
		</ul>
	</nav>
	<nav id="user-nav-bar">
		<ul>
			<li><a href="#">메세지함</a></li>
			<li><a href="#">회원가입</a></li>
			<li><a href="#">로그인</a></li>
		</ul>
	</nav>
</header>
<div id="wrap">
<section id="contents">
	<h2 class="screen-out">게시판 종합</h2>
	<article>
		<h3 class="screen-out">공지</h3>
		<div id="main-img"><img src="" alt="공지내용 !@@!$@"></div>
	</article>
	<section id="video-board">
		<h3>영상 게시판</h3>
		<ul>
			<li>영상1</li>
			<li>영상2</li>
			<li>영상3</li>
		</ul>
	</section>
	<section id="free-board">
		<h3>자유 게시판</h3>
		<ul>
			<li>게시글1</li>
			<li>게시글2</li>
			<li>게시글3</li>
		</ul>
	</section>
</section>
<footer>
	<address>address....?</address>
</footer>
</div>
</body>
</html>