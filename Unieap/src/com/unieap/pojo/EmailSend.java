package com.unieap.pojo;

import java.util.Date;

/**
 * EmailSend entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EmailSend implements java.io.Serializable {

	// Fields

	private Integer sendId;
	private String email;
	private String content;
	private Date createDate;
	private Date sendDate;
	private String status;
	private String description;
	private Integer times;
	private String subject;

	// Constructors

	/** default constructor */
	public EmailSend() {
	}

	/** minimal constructor */
	public EmailSend(Integer sendId, String email, Date createDate,
			String status) {
		this.sendId = sendId;
		this.email = email;
		this.createDate = createDate;
		this.status = status;
	}

	/** full constructor */
	public EmailSend(Integer sendId, String email, String content,
			Date createDate, Date sendDate, String status, String description,
			Integer times, String subject) {
		this.sendId = sendId;
		this.email = email;
		this.content = content;
		this.createDate = createDate;
		this.sendDate = sendDate;
		this.status = status;
		this.description = description;
		this.times = times;
		this.subject = subject;
	}

	// Property accessors

	public Integer getSendId() {
		return this.sendId;
	}

	public void setSendId(Integer sendId) {
		this.sendId = sendId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getTimes() {
		return this.times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}