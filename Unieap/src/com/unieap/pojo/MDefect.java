package com.unieap.pojo;

import java.util.Date;

/**
 * MDefect entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MDefect implements java.io.Serializable {

	// Fields

	private Integer defectId;
	private String prodVersion;
	private String testStream;
	private String severity;
	private String priority;
	private String subStream;
	private String testPlan;
	private String tcId;
	private String title;
	private String descpt;
	private String remark;
	private String steps;
	private Date createDate;
	private Date modifyDate;
	private String modifyBy;
	private String createBy;
	private String status;
	private String assignto;
	private Date targetDate;
	private String project;
	private String dts;

	// Constructors

	/** default constructor */
	public MDefect() {
	}

	/** minimal constructor */
	public MDefect(Integer defectId, String testStream, String subStream,
			String testPlan, String tcId, String title, String descpt,
			Date createDate, String createBy, String project) {
		this.defectId = defectId;
		this.testStream = testStream;
		this.subStream = subStream;
		this.testPlan = testPlan;
		this.tcId = tcId;
		this.title = title;
		this.descpt = descpt;
		this.createDate = createDate;
		this.createBy = createBy;
		this.project = project;
	}

	/** full constructor */
	public MDefect(Integer defectId, String prodVersion, String testStream,
			String severity, String priority, String subStream,
			String testPlan, String tcId, String title, String descpt,
			String remark, String steps, Date createDate, Date modifyDate,
			String modifyBy, String createBy, String status, String assignto,
			Date targetDate, String project, String dts) {
		this.defectId = defectId;
		this.prodVersion = prodVersion;
		this.testStream = testStream;
		this.severity = severity;
		this.priority = priority;
		this.subStream = subStream;
		this.testPlan = testPlan;
		this.tcId = tcId;
		this.title = title;
		this.descpt = descpt;
		this.remark = remark;
		this.steps = steps;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.modifyBy = modifyBy;
		this.createBy = createBy;
		this.status = status;
		this.assignto = assignto;
		this.targetDate = targetDate;
		this.project = project;
		this.dts = dts;
	}

	// Property accessors

	public Integer getDefectId() {
		return this.defectId;
	}

	public void setDefectId(Integer defectId) {
		this.defectId = defectId;
	}

	public String getProdVersion() {
		return this.prodVersion;
	}

	public void setProdVersion(String prodVersion) {
		this.prodVersion = prodVersion;
	}

	public String getTestStream() {
		return this.testStream;
	}

	public void setTestStream(String testStream) {
		this.testStream = testStream;
	}

	public String getSeverity() {
		return this.severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getSubStream() {
		return this.subStream;
	}

	public void setSubStream(String subStream) {
		this.subStream = subStream;
	}

	public String getTestPlan() {
		return this.testPlan;
	}

	public void setTestPlan(String testPlan) {
		this.testPlan = testPlan;
	}

	public String getTcId() {
		return this.tcId;
	}

	public void setTcId(String tcId) {
		this.tcId = tcId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescpt() {
		return this.descpt;
	}

	public void setDescpt(String descpt) {
		this.descpt = descpt;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSteps() {
		return this.steps;
	}

	public void setSteps(String steps) {
		this.steps = steps;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyBy() {
		return this.modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAssignto() {
		return this.assignto;
	}

	public void setAssignto(String assignto) {
		this.assignto = assignto;
	}

	public Date getTargetDate() {
		return this.targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

	public String getProject() {
		return this.project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getDts() {
		return this.dts;
	}

	public void setDts(String dts) {
		this.dts = dts;
	}

}