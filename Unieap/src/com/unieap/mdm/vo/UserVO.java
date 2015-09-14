package com.unieap.mdm.vo;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.util.StringUtils;

import com.unieap.CacheMgt;
import com.unieap.db.DBManager;
import com.unieap.pojo.User;

public class UserVO {
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
	private String modifyByName;
	private String createByName;
	private String remark;
	private String email;
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
			DicDataVO dic =  CacheMgt.getDicData("1001",enable);
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
			DicDataVO dic =  CacheMgt.getDicData("1001",expired);
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
			DicDataVO dic =  CacheMgt.getDicData("1001",locked);
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
		Property userCode = Property.forName("userCode");
		DetachedCriteria criteria=DetachedCriteria.forClass(com.unieap.pojo.User.class).add(userCode.eq(modifyBy));
		List<com.unieap.pojo.User> users = DBManager.getHT(null).findByCriteria(criteria);
		if(users!=null && users.size()>0){
			User user = users.get(0);
			this.modifyByName = user.getUserName();
		}
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
		Property userCode = Property.forName("userCode");
		DetachedCriteria criteria=DetachedCriteria.forClass(com.unieap.pojo.User.class).add(userCode.eq(createBy));
		List<com.unieap.pojo.User> users = DBManager.getHT(null).findByCriteria(criteria);
		if(users!=null && users.size()>0){
			User user = users.get(0);
			this.createByName = user.getUserName();
		}
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
	public String getModifyByName() {
		return modifyByName;
	}
	public void setModifyByName(String modifyByName) {
		this.modifyByName = modifyByName;
	}
	public String getCreateByName() {
		return createByName;
	}
	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}
	
	
}
