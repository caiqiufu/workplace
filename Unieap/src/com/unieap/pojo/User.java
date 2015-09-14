package com.unieap.pojo;

import java.util.Date;

/**
 * User entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	private Integer userId;
	private String userCode;
	private String userName;
	private String password;
	private String enable;
	private String expired;
	private String locked;
	private Date createDate;
	private Date modifyDate;
	private String modifyBy;
	private String createBy;
	private String remark;
	private String email;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(Integer userId, String userCode, String userName,
			String password, String enable, String expired, String locked,
			Date createDate, String createBy) {
		this.userId = userId;
		this.userCode = userCode;
		this.userName = userName;
		this.password = password;
		this.enable = enable;
		this.expired = expired;
		this.locked = locked;
		this.createDate = createDate;
		this.createBy = createBy;
	}

	/** full constructor */
	public User(Integer userId, String userCode, String userName,
			String password, String enable, String expired, String locked,
			Date createDate, Date modifyDate, String modifyBy, String createBy,
			String remark, String email) {
		this.userId = userId;
		this.userCode = userCode;
		this.userName = userName;
		this.password = password;
		this.enable = enable;
		this.expired = expired;
		this.locked = locked;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.modifyBy = modifyBy;
		this.createBy = createBy;
		this.remark = remark;
		this.email = email;
	}

	// Property accessors

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEnable() {
		return this.enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getExpired() {
		return this.expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}

	public String getLocked() {
		return this.locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
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

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}