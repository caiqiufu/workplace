package tools.performance;

import tools.biz.TestQueryInfo;

public class TimerTaskTest extends java.util.TimerTask{ 
    
    @Override 
    public void run() { 
    	try {
    		PerformanceTest.count = PerformanceTest.count+1;
			TestQueryInfo.performance();
			if(PerformanceTest.count>10){
				this.cancel();
			}
			System.out.println(PerformanceTest.count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("error="+PerformanceTest.count);
		}
    } 
}
