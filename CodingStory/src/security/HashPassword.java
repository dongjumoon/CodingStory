package security;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashPassword {
	private static final byte[] SALT = "*****".getBytes(); // 소금 뭐칠지.
	private static final int ITERATIONS = 10; // 몇번 섞을지
	private static final int KEY_LENGTH = 128;// 키 길이를 결정함.
	
	public static String getHashPw(String password) {
		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), SALT, ITERATIONS, KEY_LENGTH);
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] hashPwByte = skf.generateSecret(spec).getEncoded();
			return Base64.getEncoder().encodeToString(hashPwByte);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			spec.clearPassword();
		}
		return null;
	}
}
