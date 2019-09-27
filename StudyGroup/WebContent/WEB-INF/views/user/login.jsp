<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="${contextPath}/css/login_page.css" rel="stylesheet" type="text/css">
<h2 class="screen-out">로그인</h2>
<div>
	<form action="${contextPath}/user/login" method="post" class="form login-form">
		<fieldset>
			<legend><a href="${contextPath == '' ? '/' : contextPath}">Coding Story</a></legend>
				<p><span class="screen-out">아이디 :</span><input type="text" name="id" placeholder="아이디" required></p>
				<p><span class="screen-out">비밀번호 :</span><input type="password" name="pw" placeholder="비밀번호" required></p>
				<p><input type="submit" value="로그인" class="mouse-cursor"></p>
		</fieldset>
	</form>
	<section id="mySection">
		<h3 class="screen-out">회원가입</h3>
		<p><a href="${contextPath}/user/signUp">회원가입</a></p>
	</section>
</div>