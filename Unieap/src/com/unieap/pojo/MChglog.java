package com.unieap.pojo;

import java.util.Date;

/**
 * MChglog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MChglog implements java.io.Serializable {

	// Fields

	private Integer logId;
	private String userName;
	private Date modifyDate;
	private String modifyType;
	private String displayName;
	private String oldValue;
	private String newValue;
	private String fieldName;
	private Integer recordId;

	// Constructors

	/** default constructor */
	public MChglog() {
	}

	/** minimal constructor */
	public MChglog(Integer logId, String userName, Date modifyDate,
			String modifyType, String displayName, Integer recordId) {
		this.logId = logId;
		this.userName = userName;
		this.modifyDate = modifyDate;
		this.modifyType = modifyType;
		this.displayName = displayName;
		this.recordId = recordId;
	}

	/** full constructor */
	public MChglog(Integer logId, String userName, Date modifyDate,
			String modifyType, String displayName, String oldValue,
			String newValue, String fieldName, Integer recordId) {
		this.logId = logId;
		this.userName = userName;
		this.modifyDate = modifyDate;
		this.modifyType = modifyType;
		this.displayName = displayName;
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.fieldName = fieldName;
		this.recordId = recordId;
	}

	// Property accessors

	public Integer getLogId() {
		return this.logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyType() {
		return this.modifyType;
	}

	public void setModifyType(String modifyType) {
		this.modifyType = modifyType;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getOldValue() {
		return this.oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return this.newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Integer getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

}