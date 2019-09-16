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
	<p><a href="${contextPath}/board/free/write.user">글쓰기</a></p>
</section>

<!-- 페이징 , 글쓰기=로그인여부. -->