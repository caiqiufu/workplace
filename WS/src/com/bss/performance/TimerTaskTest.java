package com.bss.performance;

import com.bss.TestQueryInfo;

public class TimerTaskTest extends java.util.TimerTask{ 
    
    @Override 
    public void run() { 
    	try {
    		PerformanceTest.count = PerformanceTest.count+1;
			TestQueryInfo.performance();
			if(PerformanceTest.count>50000){
				this.cancel();
			}
			System.out.println("record no.="+PerformanceTest.count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("error="+PerformanceTest.count);
		}
    } 
}
