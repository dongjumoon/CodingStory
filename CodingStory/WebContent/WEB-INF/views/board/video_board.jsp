<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h2 class="screen-out">영상 게시판</h2>
<section class="board">
	<h3><a href="${contextPath}/board/video">영상 게시판</a></h3>
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
				<td>x</td>
				<td><a href="#" title="내용보기">준비중입니다...</a></td>
				<td><span>문동주</span></td>
				<td>xxxx</td>
				<td>x</td>
			</tr>
		</tbody>
	</table>
	<div class="board-btn-box">
		<a href="${contextPath}/board/video/write.user">글쓰기</a>
	</div>
</section>