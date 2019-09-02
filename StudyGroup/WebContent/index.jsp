<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>코딩이야기</title>
<link >
<style>
	/* reset */
  	* {margin:0;padding:0;box-sizing:border-box;list-style:none;text-decoration:none;}
  	fieldset {border:none;}
	/* common */
 	.screen-out {display:none;}
 	.ir-box {font-size:0;}
	
	/* main */
	body {background-color:#eee;font-size:10px;}
 	header #search-form {width:400px; padding:7px; border:1px solid black; border-radius:20px;}
 	header #search-form select {border:none;}
 	header #search-form select option {}
 	header #search-box {display:inline-block; }
 	header #search {border:none;}
 	header #gnb {}
 	header #user-nav-bar {}
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
			<input type="submit" value="전송">
		</fieldset>
	</form>
	</div>
	<label id="gnb-switch"><input type="checkbox" class="screen-out" checked></label>
	<nav id="gnb">
		<ul>
			<li>영상게시판</li>
			<li>자유게시판</li>
			<li>제작자소개</li>
		</ul>
	</nav>
	<nav id="user-nav-bar">
		<ul>
			<li>메세지함</li>
			<li>회원가입</li>
			<li>로그인</li>
		</ul>
	</nav>
</header>
<section id="contents">
	<h2 class="screen-out">게시판 종합</h2>
	<article>
		<h3 class="screen-out">공지</h3>
		<div><img src="" alt="공지내용 !@@!$@"></div>
	</article>
	<section>
		<h3>영상 게시판</h3>
		<ul>
			<li>영상1</li>
			<li>영상2</li>
			<li>영상3</li>
		</ul>
	</section>
	<section>
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
</body>
</html>