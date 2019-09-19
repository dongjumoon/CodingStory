<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h2 class="screen-out">자유 게시판</h2>
<section class="board">
	<h3><a href="${contextPath}/board/free">자유 게시판</a></h3>
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
			<c:forEach begin="0" end="3">
			<tr>
				<td>91</td>
				<td><a href="#" title="내용보기">백엔드 미구현...</a></td>
				<td><span>문동주</span></td>
				<td>17:20</td>
				<td>8</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<style>
		#dv {overflow:hidden; margin-top:10px; text-align:center;}
    	#dv>a {display:bloak; float:right; padding:7px; border:1px solid black; border-radius:10px;}
    	#dv+nav {text-align:center; margin-top:10px;}
    	#dv+nav a {padding:5px;}
    </style>
    
	<div id="dv">
		<a href="${contextPath}/board/free/write.user">글쓰기</a>
	</div>
	<nav>
		<h4 class="screen-out">페이지 네비게이션</h4>
		<a href="?page=1"><<</a>
		<a href="?page=1">1</a>
		<a href="?page=2">2</a>
		<a href="?page=3">3</a>
		<a href="?page=4">4</a>
		<a href="?page=5">5</a>
		<a href="?page=6">>></a>
	</nav>
</section>

<!-- 페이징 , 글쓰기=로그인여부. -->