<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="${contextPath}/css/sign_up_page.css" rel="stylesheet" type="text/css">
<h2 class="screen-out">회원가입</h2>
<div>
	<form action="${contextPath}/user/signUp" method="post" class="form sign-up-form">
		<h3>회원 가입</h3>
		<fieldset>
			<legend>필수 입력</legend>
			<p>
				<span class="screen-out">아이디 :</span>
				<input type="text" name="id" placeholder="*아이디" required>
				<button>중복체크</button>
			</p>
			<p><span class="screen-out">비밀번호 :</span><input type="password" name="pw" id="pw" placeholder="*비밀번호" required></p>
			<p><span class="screen-out">비밀번호 확인 :</span><input type="password" name="pw2" placeholder="*비밀번호 확인" required></p>
			<p><span class="screen-out">이름 :</span><input type="text" name="name" placeholder="*이름" required></p>
			<p>
				<span class="screen-out">성별 :</span>
				<input type="radio" name="gender" id="gender-male" value="male" checked><label for="gender-male">남</label>
				<input type="radio" name="gender" id="gender-female" value="female"><label for="gender-female">여</label>
			</p>
		</fieldset>
		<fieldset>
			<legend>선택 입력 사항</legend>
			<p><span class="screen-out">연락처 :</span><input type="text" name="phone" placeholder="연락처"></p>
			<p><span class="screen-out">이메일 :</span><input type="email" name="email" placeholder="이메일"></p>
			<p><span class="screen-out">주소 :</span><input type="text" name="address" placeholder="주소"></p>
		</fieldset>
		<p><input type="submit" value="회원가입"></p>
	</form>
</div>
<script>
	
</script>