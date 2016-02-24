package com.easymcare;

public class Test {

	public static void main(String[] args) throws Exception {
		KeyGenerater keyGenerater = new KeyGenerater();
		keyGenerater.generater();
		String signed = Signaturer.sign(keyGenerater.getPriKey(), "2016-05-15 00:00:00");
		System.out.println("signed = " + signed);
		boolean verify = SignProvider.verify(keyGenerater.getPubKey(), "2016-05-15 00:00:00", signed);
		System.out.println("verify = " + verify);
	}

}
