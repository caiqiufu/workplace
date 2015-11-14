package com.unieap.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.unieap.UnieapConstants;

@Component
public class TaskDBConnectionTest {
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void dBConnectionTest() {
		UnieapConstants.getCurrentTime(null, null);
	}
}
