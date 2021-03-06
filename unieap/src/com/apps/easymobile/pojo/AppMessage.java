package com.apps.easymobile.pojo;
// Generated 2015-11-9 9:37:41 by Hibernate Tools 4.3.1

import java.util.Date;

import org.springframework.util.StringUtils;

import com.unieap.CacheMgt;
import com.unieap.mdm.vo.DicDataVO;

/**
 * AppPushMessage generated by hbm2java
 */
public class AppMessage implements java.io.Serializable {

	private Integer id;
	private String type;
	private String subject;
	private String text;
	private String hyperlink;
	private String activeFlag;
	private String activeFlagDesc;
	private String createBy;
	private Date createDate;
	private String modifyBy;
	private Date modifyDate;
	private Date effectiveDate;
	private Date expiredDate;

	public AppMessage() {
	}

	public AppMessage(Integer id,String type, String subject, String activeFlag, String createBy, Date createDate,Date effectiveDate,Date expiredDate) {
		this.id = id;
		this.type = type;
		this.subject = subject;
		this.activeFlag = activeFlag;
		this.createBy = createBy;
		this.createDate = createDate;
		this.effectiveDate = effectiveDate;
		this.expiredDate = expiredDate;
	}

	public AppMessage(Integer id, String type,String subject, String text, String hyperlink, String activeFlag, String createBy,
			Date createDate, String modifyBy, Date modifyDate,Date effectiveDate,Date expiredDate) {
		this.id = id;
		this.type = type;
		this.subject = subject;
		this.text = text;
		this.hyperlink = hyperlink;
		this.activeFlag = activeFlag;
		this.createBy = createBy;
		this.createDate = createDate;
		this.modifyBy = modifyBy;
		this.modifyDate = modifyDate;
		this.effectiveDate = effectiveDate;
		this.expiredDate = expiredDate;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getHyperlink() {
		return this.hyperlink;
	}

	public void setHyperlink(String hyperlink) {
		this.hyperlink = hyperlink;
	}

	public String getActiveFlag() {
		return this.activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
		if(!StringUtils.isEmpty(this.activeFlag)){
			DicDataVO dic =  CacheMgt.getDicData("activeFlag",activeFlag);
			if(dic!=null){
				this.activeFlagDesc = dic.getDicName();
			}
		}
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

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public String getActiveFlagDesc() {
		return activeFlagDesc;
	}

	public void setActiveFlagDesc(String activeFlagDesc) {
		this.activeFlagDesc = activeFlagDesc;
	}
	
	
}
