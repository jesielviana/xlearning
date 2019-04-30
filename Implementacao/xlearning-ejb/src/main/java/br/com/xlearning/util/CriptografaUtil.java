package br.com.xlearning.util;

import org.jboss.crypto.CryptoUtil;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

public class CriptografaUtil {
	private static final String senha = "12345";

	public static void main(String[] args) {
		System.out.println(CryptoUtil.createPasswordHash("MD5",
				CryptoUtil.BASE16_ENCODING, null, null, "12345"));
		PasswordEncoder encoder = new Md5PasswordEncoder();
		String novasenha = encoder.encodePassword(senha, null);
		System.out.println(novasenha);
	}

	public static String getSenhaCriptografada(String senha) {
		PasswordEncoder encoder = new Md5PasswordEncoder();
		return encoder.encodePassword(senha, null);
	}
}
