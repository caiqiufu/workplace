package com.bss.performance;

public class PerformanceTest {

	public static int count = 0;
	public static void main(String[] args) throws Exception {
		int threadSize = 500; 
		MyThread myThread;
		for(int i = 0 ; i< threadSize ; i++){
			myThread = new MyThread();
			myThread.setNo(i);
			myThread.start();
		} 
	}
}
