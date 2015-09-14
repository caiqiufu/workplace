package com.unieap.pojo;

import java.util.Date;

/**
 * VisitLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class VisitLog implements java.io.Serializable {

	// Fields

	private Integer logId;
	private String userCode;
	private String remoteIp;
	private Date loginDate;
	private Date logoutDate;
	private String remark;

	// Constructors

	/** default constructor */
	public VisitLog() {
	}

	/** minimal constructor */
	public VisitLog(Integer logId, String userCode, String remoteIp) {
		this.logId = logId;
		this.userCode = userCode;
		this.remoteIp = remoteIp;
	}

	/** full constructor */
	public VisitLog(Integer logId, String userCode, String remoteIp,
			Date loginDate, Date logoutDate, String remark) {
		this.logId = logId;
		this.userCode = userCode;
		this.remoteIp = remoteIp;
		this.loginDate = loginDate;
		this.logoutDate = logoutDate;
		this.remark = remark;
	}

	// Property accessors

	public Integer getLogId() {
		return this.logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getRemoteIp() {
		return this.remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public Date getLoginDate() {
		return this.loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public Date getLogoutDate() {
		return this.logoutDate;
	}

	public void setLogoutDate(Date logoutDate) {
		this.logoutDate = logoutDate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}