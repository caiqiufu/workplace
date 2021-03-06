package com.apps.reuse.pojo;

// default package
// Generated 2015-8-8 2:32:02 by Hibernate Tools 4.3.1

import java.util.Date;

import org.springframework.util.StringUtils;

import com.unieap.CacheMgt;
import com.unieap.mdm.vo.DicDataVO;

/**
 * ReuseSmsNotify generated by hbm2java
 */
public class ReuseSmsNotify implements java.io.Serializable {

	private int id;
	private String smsType;
	private String smsTypeDesc;
	private String fromBy;
	private String sendTo;
	private String content;
	private String verifyCode;
	private Date sendDate;
	private String checked;
	private String checkedDesc;
	private Integer checkTimes;
	private String expired;
	private String expiredDesc;
	private String remark;

	public ReuseSmsNotify() {
	}

	public ReuseSmsNotify(int id, String smsType, String fromBy, String sendTo, String content, Date sendDate,
			String checked, String expired) {
		this.id = id;
		this.smsType = smsType;
		this.fromBy = fromBy;
		this.sendTo = sendTo;
		this.content = content;
		this.sendDate = sendDate;
		this.checked = checked;
		this.expired = expired;
	}

	public ReuseSmsNotify(int id, String smsType, String fromBy, String sendTo, String content, String verifyCode,
			Date sendDate, String checked, Integer checkTimes, String expired, String remark) {
		this.id = id;
		this.smsType = smsType;
		this.fromBy = fromBy;
		this.sendTo = sendTo;
		this.content = content;
		this.verifyCode = verifyCode;
		this.sendDate = sendDate;
		this.checked = checked;
		this.checkTimes = checkTimes;
		this.expired = expired;
		this.remark = remark;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSmsType() {
		return this.smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
		if(!StringUtils.isEmpty(this.smsType)){
			DicDataVO dic =  CacheMgt.getDicData("5746",smsType);
			if(dic!=null){
				this.smsTypeDesc = dic.getDicName();
			}
		}
	}

	public String getFromBy() {
		return this.fromBy;
	}

	public void setFromBy(String fromBy) {
		this.fromBy = fromBy;
	}

	public String getSendTo() {
		return this.sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getVerifyCode() {
		return this.verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getChecked() {
		return this.checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
		if(!StringUtils.isEmpty(this.checked)){
			DicDataVO dic =  CacheMgt.getDicData("activeFlag",checked);
			if(dic!=null){
				this.checkedDesc = dic.getDicName();
			}
		}
	}

	public Integer getCheckTimes() {
		return this.checkTimes;
	}

	public void setCheckTimes(Integer checkTimes) {
		this.checkTimes = checkTimes;
	}

	public String getExpired() {
		return this.expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
		if(!StringUtils.isEmpty(this.expired)){
			DicDataVO dic =  CacheMgt.getDicData("activeFlag",expired);
			if(dic!=null){
				this.expiredDesc = dic.getDicName();
			}
		}
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCheckedDesc() {
		return checkedDesc;
	}

	public void setCheckedDesc(String checkedDesc) {
		this.checkedDesc = checkedDesc;
	}

	public String getExpiredDesc() {
		return expiredDesc;
	}

	public void setExpiredDesc(String expiredDesc) {
		this.expiredDesc = expiredDesc;
	}

	public String getSmsTypeDesc() {
		return smsTypeDesc;
	}

	public void setSmsTypeDesc(String smsTypeDesc) {
		this.smsTypeDesc = smsTypeDesc;
	}

}
