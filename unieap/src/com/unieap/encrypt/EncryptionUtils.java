package com.unieap.encrypt;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;

public class EncryptionUtils {
	public static void main(String[] args) throws Exception {
		String sKey = "1234567890111110";
		String sSrc = "Abc1234%";
		String enString = encryptSunJCE(sSrc, sKey);
		System.out.println("enString=" + enString);
		String deString = decryptSunJCE(enString, sKey);
		System.out.println("deString=" + deString);
		String mKey = "1234567890543210";
		String menString = encryptSun(sSrc, mKey);
		System.out.println("enString=" + menString);
		String mdeString = decryptSun(menString, mKey);
		System.out.println("deString=" + mdeString);
	}

	public static String encryptSunJCE(String sSrc, String sKey) throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		if (StringUtils.isEmpty(sKey)) {
			return null;
		}
		if (sKey.length() != 16) {
			return null;
		}
		byte[] raw = sKey.getBytes("utf-8");
		SecretKeySpec sKeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
		return new Base64().encodeToString(encrypted);

	}

	public static String decryptSunJCE(String sSrc, String sKey) throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		if (sKey == null) {
			return null;
		}
		if (sKey.length() != 16) {
			return null;
		}
		byte[] raw = sKey.getBytes("utf-8");
		SecretKeySpec sKeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
		byte[] encrypted64 = new Base64().decode(sSrc);
		byte[] original = cipher.doFinal(encrypted64);
		String originalString = new String(original, "utf-8");
		return originalString;

	}
	
	public static String encryptSun(String sSrc, String sKey) throws Exception {
		Security.addProvider(new sun.security.provider.Sun()); 
		if (StringUtils.isEmpty(sKey)) {
			return null;
		}
		if (sKey.length() != 16) {
			return null;
		}
		byte[] raw = sKey.getBytes("utf-8");
		SecretKeySpec sKeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
		return new Base64().encodeToString(encrypted);

	}

	public static String decryptSun(String sSrc, String sKey) throws Exception {
		Security.addProvider(new sun.security.provider.Sun()); 
		if (sKey == null) {
			return null;
		}
		if (sKey.length() != 16) {
			return null;
		}
		byte[] raw = sKey.getBytes("utf-8");
		SecretKeySpec sKeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
		byte[] encrypted64 = new Base64().decode(sSrc);
		byte[] original = cipher.doFinal(encrypted64);
		String originalString = new String(original, "utf-8");
		return originalString;

	}
}
