package com.unieap.pojo;

import java.util.Date;

/**
 * RStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RStatus implements java.io.Serializable {

	// Fields

	private Integer id;
	private Date datetime;
	private String status;
	private Integer amount;
	private Integer project;

	// Constructors

	/** default constructor */
	public RStatus() {
	}

	/** full constructor */
	public RStatus(Integer id, Date datetime, String status, Integer amount,
			Integer project) {
		this.id = id;
		this.datetime = datetime;
		this.status = status;
		this.amount = amount;
		this.project = project;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDatetime() {
		return this.datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getProject() {
		return this.project;
	}

	public void setProject(Integer project) {
		this.project = project;
	}

}