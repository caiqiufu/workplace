package com.easymcare;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class KeyGenerater {
	private String priKey;
	private String pubKey;

	public void generater() throws Exception {
		KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
		SecureRandom secrand = new SecureRandom();
		secrand.setSeed("www.easymcare.com.cambodia.smart".getBytes());//初始化随机产生器
		keygen.initialize(1024, secrand);
		KeyPair keys = keygen.genKeyPair();
		PublicKey pubkey = keys.getPublic();
		PrivateKey prikey = keys.getPrivate();
		pubKey = Base64.encode(pubkey.getEncoded());
		priKey = Base64.encode(prikey.getEncoded());
		System.out.println("pubKey = " + new String(pubKey));
		System.out.println("priKey = " + new String(priKey));
	}

	public String getPriKey() {
		return priKey;
	}

	public String getPubKey() {
		return pubKey;
	}
}
