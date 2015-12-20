package tools.performance;

public class PerformanceTest {

	public static int count;
	public static void main(String[] args) throws Exception {
		int threadSize = 10; 
		MyThread myThread;
		for(int i = 0 ; i< threadSize ; i++){
			myThread = new MyThread();
			myThread.setNo(i);
			myThread.start();
		} 
	}
}
