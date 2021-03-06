package com.apps.easymobile.pojo;
// Generated 2015-11-9 9:37:41 by Hibernate Tools 4.3.1

import java.util.Date;

import org.springframework.util.StringUtils;

import com.unieap.CacheMgt;
import com.unieap.mdm.vo.DicDataVO;

/**
 * AppUpgrade generated by hbm2java
 */
public class AppUpgrade implements java.io.Serializable {

	private Integer id;
	private String activeFlag;
	private String activeFlagDesc;
	private String version;
	private String changeDesc;
	private Date createDate;
	private String remark;
	private String tenantId;
	private String fileId;
	private Date effectiveDate;
	private String url;

	public AppUpgrade() {
	}

	public AppUpgrade(Integer id, String activeFlag) {
		this.id = id;
		this.activeFlag = activeFlag;
	}

	public AppUpgrade(Integer id, String activeFlag, String version, String changeDesc, Date createDate, String remark,Date effectiveDate,String url) {
		this.id = id;
		this.activeFlag = activeFlag;
		this.version = version;
		this.changeDesc = changeDesc;
		this.createDate = createDate;
		this.remark = remark;
		this.effectiveDate = effectiveDate;
		this.url = url;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getChangeDesc() {
		return this.changeDesc;
	}

	public void setChangeDesc(String changeDesc) {
		this.changeDesc = changeDesc;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getActiveFlagDesc() {
		return activeFlagDesc;
	}

	public void setActiveFlagDesc(String activeFlagDesc) {
		this.activeFlagDesc = activeFlagDesc;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
}
