<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h2 class="screen-out">자유 게시판</h2>
<section class="board">
	<h3><a href="${contextPath}/board/free">
			자유 게시판
			<c:if test="${param.search != null}"> '${param.search}' 검색결과</c:if>
		</a></h3>
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
			<c:if test="${freePostList.size() == 0}">
				<tr>
					<td colspan="5">검색 결과가 없습니다.</td>
				</tr>
			</c:if>
			<c:forEach var="post" items="${freePostList}">
			<tr>
				<td>${post.boardId}</td>
				<td><a href="free/view?boardId=${post.boardId}" title="내용보기">${post.boardTitle}</a></td>
				<td><span>${post.userId}</span></td>
				<td>${post.boardDate}</td>
				<td>${post.boardViews}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="board-btn-box">
		<a href="${contextPath}/board/free/write.user">글쓰기</a>
	</div>
	<c:if test="${param.search == null}">
		<nav>
			<h4 class="screen-out">페이지 네비게이션</h4>
			<a href="?page=${pageAreaNum == 0 ? 1 : pageAreaNum}"><<</a>
			<c:forEach var="i" begin="1" end="${pageCount}">
				<a href="?page=${pageAreaNum + i}">${pageAreaNum + i}</a>
			</c:forEach>
			<a href="?page=${nextPageAreaNum}">>></a>
		</nav>
		<script>
			var page = '${param.page}';
			if (page == '') page = '1';
			
			$('.board-btn-box+nav a').each(function (index, item) {
				if ($(this).text() === page) {
					$(this).css({"font-weight": "bold", "font-size": "20px"});
				}
			});
		</script>
	</c:if>
</section>