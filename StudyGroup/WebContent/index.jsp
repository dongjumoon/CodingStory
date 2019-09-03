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
	body {font-family:'굴림', sans-serif;}
	
	/* -- main -- */
	/* header */
	header {position:fixed; height:50px; width:100%; background-color:white; top:0;}
 	h1 {display:none;}
 	#search-form {
 		width:30%; min-width:400px; height:34px; padding:4px 0 0 10px; margin-top:8px; 
 		border:1px solid black; border-radius:20px;
 		position:absolute; left:50%;
 		transform:translateX(-50%);
 	}
 	#search-form select {border:none; display:inline-block; height:22px; padding-bottom:4px;}
 	#search-form select option {}
 	#search-form button[type=submit] {display:inline-block;}
 	#search {border:none; width:calc(100% - 150px); display:inline-block; font-size:17px;}
 	#gnb-switch-label {position:absolute; left:16px; top:12px; width:25px; height:25px; background-color:pink;}
 	#gnb {
 		position:fixed; top:50px; width:230px; margin-left:-230px;
 		height:calc(100vh - 50px); background-color:rgba(132,423,212,.3);
 		text-align:center; overflow:auto;
 	}
 	#gnb-switch:checked + #gnb {margin-left:0;}
 	#gnb a {padding:20px 0; display:block;}
 	#user-nav-bar {position:absolute; right:0;}
 	#user-nav-bar li {float:left;}
 	#user-nav-bar a {padding:calc((50px - 1em) / 2) 10px; line-height:50px;}
 	
 	/* main contents */
 	#wrap {width:calc(100% - 0px); min-width:320px; margin-left:0px; margin-top:50px;}
 	#contents #main-img {background-color:rgba(255,0,0,.3); height:250px;}
 	#contents section[id*='board'] {background-color:rgba(22,222,222,.3); width:80%; margin:auto; padding:30px;}
 	#contents section[id*='board'] h3,
 	#contents section[id*='board'] a {padding:15px 0;}
 	#contents #video-board,
 	#contents #video-board ul {overflow:hidden;}
 	#contents #video-board li {width:23%;height:150px; float:left;}
 	#contents #video-board li:not(:first-child) {margin-left:2.66%;} 
 	#contents #video-board li .video-box {width:100%; height:100%; background-color:rgba(255,0,0,.2);}
 	#contents #free-board {border-top:1px solid black;}
 	#contents #free-board table {border-collapse:collapse; width:100%;text-align:center;}
 	#contents #free-board table thead {background-color:#000; color:#fff;}
 	#contents #free-board table tbody td:nth-child(2) {text-align:left;}
 	#contents #free-board table td {border-bottom: 1px solid black; padding:10px;}
 	
 	
 	/* footer */
 	footer {min-height: 80px; background-color:rgba(0,0,255,.3);}
 	footer address {text-align:center;}
 	
 	@media (max-width:1260px) {   /* 영상게시판전용 */
        #contents #video-board ul {width:133.33%;}
        #contents #video-board li:nth-child(2),
        #contents #video-board li:nth-child(3) {margin-left:3%;}
        #contents #video-board li:nth-child(4) {display:none;}
    }
 	
 	@media (max-width:1024px) {   /* 테블릿 */
 		#wrap {width:100%; margin-left:0;}
        #contents #main-img {background-color:rgba(0,255,0,.3);}
        #contents #video-board ul {width:200%;}
        #contents #video-board li:nth-child(1),
        #contents #video-board li:nth-child(2) {width:23.5%;}
        #contents #video-board li:nth-child(3) {display:none;}
    }
    
    @media (max-width:750px) {   /* 영상게시판전용 */
    	#contents section[id*='board'] {width:100%; margin:0;}
    }
    
    @media (max-width:480px) {   /* 모바일 */
    	#contents #main-img {background-color:rgba(0,0,255,.3);}
    	#contents #video-board ul {width:100%;}
    	#contents #video-board li:nth-child(1) {width:100%;}
        #contents #video-board li:nth-child(2) {display:none;}
    }
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
		<h3><a href="#">영상 게시판</a></h3>
		<ul>
			<li>
				<h4 class="screen-out">제목1</h4>
				<div class="video-box">영상이미지</div>
			</li>
			<li>
				<h4 class="screen-out">제목2</h4>
				<div class="video-box">영상이미지</div>
			</li>
			<li>
				<h4 class="screen-out">제목3</h4>
				<div class="video-box">영상이미지</div>
			</li>
			<li>
				<h4 class="screen-out">제목4</h4>
				<div class="video-box">영상이미지</div>
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
</body>
</html>