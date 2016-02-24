package com.bss;

public class MyThread extends Thread {
	public void run(){
		try {
			TestQueryInfo.performance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
