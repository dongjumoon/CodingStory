<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/begin.jspf" %>
<link href="${contextPath}/css/index_page.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/index_page_media.css" rel="stylesheet" type="text/css">
<h2 class="screen-out">게시판 종합</h2>
	<article id="main-view">
		<h3 id="main-title">Coding Story</h3>
	</article>
	<section class="video-board">
		<h3><a href="${contextPath}/board/video">영상 게시판</a></h3>
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

<%@ include file="/WEB-INF/views/common/end.jspf" %>