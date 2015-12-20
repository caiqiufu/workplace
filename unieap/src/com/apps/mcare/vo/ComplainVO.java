package com.apps.mcare.vo;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.unieap.CacheMgt;
import com.unieap.mdm.vo.DicDataVO;

public class ComplainVO {
	private String id;
	private String evalution;
	private String text;
	private String url;
	private String fileName;
	private String submitDate;
	private String submitDateStart;
	private String submitDateEnd;
	private String submitBy;
	private String feedback;
	private Date modifyDate;
	private String modifyBy;
	private String status;
	private String statusDesc;
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}
	public String getSubmitBy() {
		return submitBy;
	}
	public void setSubmitBy(String submitBy) {
		this.submitBy = submitBy;
	}
	public String getSubmitDateStart() {
		return submitDateStart;
	}
	public void setSubmitDateStart(String submitDateStart) {
		this.submitDateStart = submitDateStart;
	}
	public String getSubmitDateEnd() {
		return submitDateEnd;
	}
	public void setSubmitDateEnd(String submitDateEnd) {
		this.submitDateEnd = submitDateEnd;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
		if(!StringUtils.isEmpty(this.status)){
			DicDataVO dic =  CacheMgt.getDicData("complainStatus",status);
			if(dic!=null){
				this.statusDesc = dic.getDicName();
			}
		}
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
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
	
	
}
