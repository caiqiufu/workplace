package com.unieap.task;

public class TaskTest1 implements TaskService{

	//@Scheduled(cron="15 * * * * ?")
	public void myTest(){
		System.out.println("test job 1");
	}

	@Override
	public void execute() {
		System.out.println("execute 1");
	}
}
