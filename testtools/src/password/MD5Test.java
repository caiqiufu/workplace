package password;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class MD5Test {

	public static void main(String[] args) {
		md5();
	}

	public static void md5() {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		// false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true
		// 表示：生成24位的Base64版
		md5.setEncodeHashAsBase64(false);
		String pwd = md5.encodePassword("1", null);
		System.out.println("MD5: " + pwd + " len=" + pwd.length());
		ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder();
		String encodedPassword = passwordEncoder.encodePassword("1", null);
		System.out.println("encodedPassword: " + encodedPassword + " len=" + encodedPassword.length());
		if(encodedPassword.equals("356a192b7913b04c54574d18c28d46e6395428ab")){
			System.out.println("passed");
		}
	}
}
