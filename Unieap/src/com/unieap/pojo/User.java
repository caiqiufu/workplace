package com.unieap.pojo;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.unieap.CacheMgt;
import com.unieap.mdm.vo.DicDataVO;

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
	private String enableDesc;
	private String expired;
	private String expiredDesc;
	private String locked;
	private String lockedDesc;
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
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public String getEnableDesc() {
		if(!StringUtils.isEmpty(this.enable)){
			DicDataVO dic =  CacheMgt.getDicData("activeFlag",enable);
			if(dic!=null){
				this.enableDesc = dic.getDicName();
			}
		}
		return enableDesc;
	}
	public void setEnableDesc(String enableDesc) {
		this.enableDesc = enableDesc;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
		
	}
	public String getExpiredDesc() {
		if(!StringUtils.isEmpty(this.expired)){
			DicDataVO dic =  CacheMgt.getDicData("activeFlag",expired);
			if(dic!=null){
				this.expiredDesc = dic.getDicName();
			}
		}
		return expiredDesc;
	}
	public void setExpiredDesc(String expiredDesc) {
		this.expiredDesc = expiredDesc;
	}
	public String getLocked() {
		return locked;
	}
	public void setLocked(String locked) {
		this.locked = locked;
	}
	public String getLockedDesc() {
		if(!StringUtils.isEmpty(this.locked)){
			DicDataVO dic =  CacheMgt.getDicData("activeFlag",locked);
			if(dic!=null){
				this.lockedDesc = dic.getDicName();
			}
		}
		return lockedDesc;
	}
	public void setLockedDesc(String lockedDesc) {
		this.lockedDesc = lockedDesc;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}