<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h2 class="screen-out">로그인</h2>
<div>
	<form action="${contextPath}/user/login" method="post">
		<fieldset>
			<legend>Coding Story</legend>
				<p><span class="screen-out">아이디 :</span><input type="text" name="id" placeholder="아이디" required></p>
				<p><span class="screen-out">비밀번호 :</span><input type="password" name="pw" placeholder="비밀번호" required></p>
				<p><input type="submit" value="로그인"></p>
		</fieldset>
	</form>
	<section>
		<h3 class="screen-out">회원가입</h3>
		<p><a href="${contextPath}/user/signUp">회원가입</a></p>
	</section>
</div>