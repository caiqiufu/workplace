package com.unieap.security;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class SignProvider {
	public static boolean verify(String pubKeyText, String plainText, String signText) {
		try {
			// 解密由base64编码的公钥,并构造X509EncodedKeySpec对象
			X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(Base64.decode(pubKeyText));
			// RSA对称加密算法
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			// 取公钥匙对象
			PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);
			// 解密由base64编码的数字签名
			byte[] signed = Base64.decode(signText);
			Signature signatureChecker = Signature.getInstance("MD5withRSA");
			signatureChecker.initVerify(pubKey);
			signatureChecker.update(plainText.getBytes());
			// 验证签名是否正常
			if (signatureChecker.verify(signed))
				return true;
			else
				return false;
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}
	}
}
