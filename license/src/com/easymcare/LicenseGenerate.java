package com.easymcare;

public class LicenseGenerate {

	public static void main(String[] args) throws Exception {
		KeyGenerater keyGenerater = new KeyGenerater();
		keyGenerater.generater();
		String expiredTime = Signaturer.sign(keyGenerater.getPriKey(), "2016-02-15 00:00:00");
		System.out.println("licence.expiretime = " + expiredTime);
		//boolean verify = SignProvider.verify(keyGenerater.getPubKey(), "2015-11-15 00:00:00", signed);
		//System.out.println("verify = " + verify);
		String query = Signaturer.sign(keyGenerater.getPriKey(), "2");
		System.out.println("licence.flow.controller.query = " + query);
		String bizhandle = Signaturer.sign(keyGenerater.getPriKey(), "1");
		System.out.println("licence.flow.controller.bizhandle = " + bizhandle);
		String type = Signaturer.sign(keyGenerater.getPriKey(), "T");
		System.out.println("licence.type = " + type);
		
	}

}
