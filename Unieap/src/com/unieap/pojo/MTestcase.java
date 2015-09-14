package com.unieap.pojo;

import java.util.Date;

/**
 * MTestcase entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MTestcase implements java.io.Serializable {

	// Fields

	private Integer tcId;
	private String tcCode;
	private String tcName;
	private String tcDescription;
	private String testStream;
	private String subStream;
	private String tester;
	private String status;
	private String testResult;
	private String remark;
	private String createBy;
	private Date createDate;
	private String modifyBy;
	private Date modifyDate;
	private String project;

	// Constructors

	/** default constructor */
	public MTestcase() {
	}

	/** minimal constructor */
	public MTestcase(Integer tcId, String tcCode,String project) {
		this.tcId = tcId;
		this.tcCode = tcCode;
		this.project = project;
	}

	/** full constructor */
	public MTestcase(Integer tcId, String tcCode, String tcName,
			String tcDescription, String testStream, String subStream,
			String tester, String status, String testResult, String remark,
			String createBy, Date createDate, String modifyBy, Date modifyDate,String project) {
		this.tcId = tcId;
		this.tcCode = tcCode;
		this.tcName = tcName;
		this.tcDescription = tcDescription;
		this.testStream = testStream;
		this.subStream = subStream;
		this.tester = tester;
		this.status = status;
		this.testResult = testResult;
		this.remark = remark;
		this.createBy = createBy;
		this.createDate = createDate;
		this.modifyBy = modifyBy;
		this.modifyDate = modifyDate;
		this.project = project;
	}

	// Property accessors

	public Integer getTcId() {
		return this.tcId;
	}

	public void setTcId(Integer tcId) {
		this.tcId = tcId;
	}

	public String getTcCode() {
		return this.tcCode;
	}

	public void setTcCode(String tcCode) {
		this.tcCode = tcCode;
	}

	public String getTcName() {
		return this.tcName;
	}

	public void setTcName(String tcName) {
		this.tcName = tcName;
	}

	public String getTcDescription() {
		return this.tcDescription;
	}

	public void setTcDescription(String tcDescription) {
		this.tcDescription = tcDescription;
	}

	public String getTestStream() {
		return this.testStream;
	}

	public void setTestStream(String testStream) {
		this.testStream = testStream;
	}

	public String getSubStream() {
		return this.subStream;
	}

	public void setSubStream(String subStream) {
		this.subStream = subStream;
	}

	public String getTester() {
		return this.tester;
	}

	public void setTester(String tester) {
		this.tester = tester;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTestResult() {
		return this.testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getModifyBy() {
		return this.modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

}