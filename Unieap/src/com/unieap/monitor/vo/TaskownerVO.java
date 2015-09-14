package com.unieap.monitor.vo;

import java.util.List;

import com.unieap.base.vo.BaseVO;

public class TaskownerVO extends BaseVO{
	private Long taskOwnerId;
	private String ownerName;
	private List<TaskVO> mainTasks; 
	private List<TaskVO> tasks;
	
	public List<TaskVO> getMainTasks() {
		return mainTasks;
	}
	public void setMainTasks(List<TaskVO> mainTasks) {
		this.mainTasks = mainTasks;
	}
	public List<TaskVO> getTasks() {
		return tasks;
	}
	public void setTasks(List<TaskVO> tasks) {
		this.tasks = tasks;
	}
	public Long getTaskOwnerId() {
		return taskOwnerId;
	}
	public void setTaskOwnerId(Long taskOwnerId) {
		this.taskOwnerId = taskOwnerId;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
}
