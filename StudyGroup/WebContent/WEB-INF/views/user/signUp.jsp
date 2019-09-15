<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h2 class="screen-out">회원가입</h2>
<div>
	<form action="${pageContext.request.contextPath}/user/signUp" method="post">
		<fieldset>
			<legend>회원 가입</legend>
				<p>
					<span class="screen-out">아이디 :</span>
					<input type="text" name="id" placeholder="*아이디" required>
					<button>중복체크</button>
				</p>
				<p><span class="screen-out">비밀번호 :</span><input type="password" name="pw" placeholder="*비밀번호" required></p>
				<p><span class="screen-out">비밀번호 확인 :</span><input type="password" name="pw" placeholder="*비밀번호 확인" required></p>
				<p><span class="screen-out">이름 :</span><input type="text" name="name" placeholder="*이름" required></p>
				<p>
					<span class="screen-out">성별 :</span>
					<input type="radio" name="gender" id="gender-male" value="male" checked><label for="gender-male">남</label>
					<input type="radio" name="gender" id="gender-female" value="female"><label for="gender-female">여</label>
				</p>
				<p><span class="screen-out">연락처 :</span><input type="text" name="name" placeholder="연락처"></p>
				<p><span class="screen-out">이메일 :</span><input type="email" name="name" placeholder="이메일"></p>
				<p><span class="screen-out">주소 :</span><input type="text" name="name" placeholder="주소"></p>
				<p><input type="submit" value="회원가입"></p>
		</fieldset>
	</form>
</div>
<script>
	
</script>