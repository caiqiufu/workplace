package com;

import java.util.Timer;
import java.util.TimerTask;

public class TaskTest {

	public static void main(String[] args) {
		// TODO todo.generated by zoer
		Timer timer = new Timer();
		timer.schedule(new MyTask(), 1000, 2000);
		Integer in = new Integer("2");
		System.out.println(in.toString());
	}
}

class MyTask extends TimerTask {

	@Override
	public void run() {
		System.out.println("dddd");

	}

}
