package com.unieap.mantis.vo;
public class ViewIssueVO {
	private String project;
	private String repoter;
	private Integer defectId;
    private String prodVersion;
    private String testStream;
    private String severity;
    private String priority;
    private String subStream;
    private String testPlan;
    private String tcId;
    private String title;
    private String status;
    private String hideStatus;
    private String assignto;
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public Integer getDefectId() {
		return defectId;
	}
	public void setDefectId(Integer defectId) {
		this.defectId = defectId;
	}
	public String getProdVersion() {
		return prodVersion;
	}
	public void setProdVersion(String prodVersion) {
		this.prodVersion = prodVersion;
	}
	public String getTestStream() {
		return testStream;
	}
	public void setTestStream(String testStream) {
		this.testStream = testStream;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getSubStream() {
		return subStream;
	}
	public void setSubStream(String subStream) {
		this.subStream = subStream;
	}
	public String getTestPlan() {
		return testPlan;
	}
	public void setTestPlan(String testPlan) {
		this.testPlan = testPlan;
	}
	public String getTcId() {
		return tcId;
	}
	public void setTcId(String tcId) {
		this.tcId = tcId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getHideStatus() {
		return hideStatus;
	}
	public void setHideStatus(String hideStatus) {
		this.hideStatus = hideStatus;
	}
	public String getAssignto() {
		return assignto;
	}
	public void setAssignto(String assignto) {
		this.assignto = assignto;
	}
	public String getRepoter() {
		return repoter;
	}
	public void setRepoter(String repoter) {
		this.repoter = repoter;
	}
    
}
