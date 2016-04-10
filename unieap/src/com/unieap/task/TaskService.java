package com.unieap.task;

public interface TaskService {
	public String getTaskCode();
	public int getTaskTimeDuration();
	public void taskExecute()throws Exception;
	public boolean lockTask(String taskCode, int duration)throws Exception;
	public boolean unLockTask(String taskCode)throws Exception;
}

