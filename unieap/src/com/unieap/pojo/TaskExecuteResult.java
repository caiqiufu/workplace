package com.unieap.pojo;
// Generated 2016-4-8 0:19:40 by Hibernate Tools 4.3.1.Final

import java.util.Date;

/**
 * TaskExecuteResult generated by hbm2java
 */
public class TaskExecuteResult implements java.io.Serializable {

	private int id;
	private String taskCode;
	private String taskName;
	private String taskClass;
	private String cron;
	private Date startTime;
	private Date endTime;
	private Integer processRecord;
	private Date createDate;

	public TaskExecuteResult() {
	}

	public TaskExecuteResult(int id, String taskCode, String taskName, String taskClass, String cron) {
		this.id = id;
		this.taskCode = taskCode;
		this.taskName = taskName;
		this.taskClass = taskClass;
		this.cron = cron;
	}

	public TaskExecuteResult(int id, String taskCode, String taskName, String taskClass, String cron, Date startTime,
			Date endTime, Integer processRecord, Date createDate) {
		this.id = id;
		this.taskCode = taskCode;
		this.taskName = taskName;
		this.taskClass = taskClass;
		this.cron = cron;
		this.startTime = startTime;
		this.endTime = endTime;
		this.processRecord = processRecord;
		this.createDate = createDate;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTaskCode() {
		return this.taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskClass() {
		return this.taskClass;
	}

	public void setTaskClass(String taskClass) {
		this.taskClass = taskClass;
	}

	public String getCron() {
		return this.cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getProcessRecord() {
		return this.processRecord;
	}

	public void setProcessRecord(Integer processRecord) {
		this.processRecord = processRecord;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
