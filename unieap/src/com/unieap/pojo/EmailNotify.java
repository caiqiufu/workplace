package com.unieap.pojo;

import java.util.Date;

/**
 * EmailSend entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EmailNotify implements java.io.Serializable {

	// Fields

	private Integer id;
	private String type;
	private String fromBy;
	private String sendTo;
	private String subject;
	private byte[] content;
	private Date createDate;
	private Date sendDate;
	private String status;
	private String description;
	private Integer times;
	private String resultDesc;

	// Constructors

	/** default constructor */
	public EmailNotify() {
	}

	/** minimal constructor */
	public EmailNotify(Integer id, String type,String fromBy,String sendTo, Date createDate,
			String status) {
		this.id = id;
		this.type = type;
		this.fromBy = fromBy;
		this.sendTo = sendTo;
		this.createDate = createDate;
		this.status = status;
	}
	
	public EmailNotify(Integer id, String type, String fromBy,String sendTo, String subject, byte[] content, Date createDate,
			Date sendDate, String status, String description, Integer times,String resultDesc) {
		super();
		this.id = id;
		this.type = type;
		this.fromBy = fromBy;
		this.sendTo = sendTo;
		this.subject = subject;
		this.content = content;
		this.createDate = createDate;
		this.sendDate = sendDate;
		this.status = status;
		this.description = description;
		this.times = times;
		this.resultDesc = resultDesc;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFromBy() {
		return fromBy;
	}

	public void setFromBy(String fromBy) {
		this.fromBy = fromBy;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	
}