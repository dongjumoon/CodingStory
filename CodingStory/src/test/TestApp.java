package test;

import java.sql.Date;

import security.HashPassword;

public class TestApp {

	public static void main(String[] args) {
		System.out.println(5 / 5);
		System.out.println((11 - 1) / 5 * 5);
		
		String pw = HashPassword.getHashPw("나만의비밀번호");
		String pw2 = HashPassword.getHashPw("나만의비밀번호1");
		System.out.println(pw.equals(pw2));
		
	}

}
