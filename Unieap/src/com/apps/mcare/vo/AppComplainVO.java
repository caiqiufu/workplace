package com.apps.mcare.vo;

import java.util.Date;

public class AppComplainVO {
	private Integer id;
	private String evalution;
	private String text;
	private byte[] screenshot;
	private Date submitDate;
	private String submitBy;
	private String status;
	private Date modifyDate;
	private String modifyBy;
	private String remark;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEvalution() {
		return evalution;
	}
	public void setEvalution(String evalution) {
		this.evalution = evalution;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public byte[] getScreenshot() {
		return screenshot;
	}
	public void setScreenshot(byte[] screenshot) {
		this.screenshot = screenshot;
	}
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	public String getSubmitBy() {
		return submitBy;
	}
	public void setSubmitBy(String submitBy) {
		this.submitBy = submitBy;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
