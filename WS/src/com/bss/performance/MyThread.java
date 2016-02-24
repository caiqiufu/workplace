package com.bss.performance;

import java.util.Timer;

public class MyThread extends Thread {
	public static int threadNo;
	public void setNo(int threadNo){
		this.threadNo = threadNo;
	}
	public void run() {
		Timer timer = new Timer();
		timer.schedule(new TimerTaskTest(), 10, 500);
		System.out.println("run thread="+threadNo);
	}

}
