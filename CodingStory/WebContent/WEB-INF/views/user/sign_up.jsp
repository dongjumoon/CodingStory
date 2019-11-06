<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="${contextPath}/css/sign_up_page.css" rel="stylesheet" type="text/css">
<h2 class="screen-out">회원가입</h2>
<div>
	<form action="${contextPath}/user/signUp" method="post" class="form sign-up-form">
		<h3>회원 가입</h3>
		<fieldset>
			<legend class="screen-out">필수 입력</legend>
			<p>
				<span class="screen-out">아이디 :</span>
				<input type="text" name="id" placeholder="*아이디" maxlength="10" required>
			</p>
			<p><span class="screen-out">비밀번호 :</span><input type="password" name="pw" id="pw" placeholder="*비밀번호" maxlength="15" required></p>
			<p><span class="screen-out">비밀번호 확인 :</span><input type="password" name="pw2" placeholder="*비밀번호 확인" maxlength="15" required></p>
			<p><span class="screen-out">이름 :</span><input type="text" name="name" placeholder="*이름" maxlength="10" required></p>
		</fieldset>
		<p><input type="submit" value="회원가입"></p>
	</form>
</div>