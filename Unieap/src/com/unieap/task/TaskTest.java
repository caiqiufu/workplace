package com.unieap.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
public class TaskTest {

	@Scheduled(cron="15 * * * * ?")
	public void myTest(){
		System.out.println("test job");
	}
}
