package com.unieap.mantis.vo;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.util.StringUtils;

import com.unieap.CacheMgt;
import com.unieap.db.DBManager;
import com.unieap.mdm.vo.DicDataVO;
import com.unieap.pojo.User;

public class MTestcaseVO {
	private Integer tcId;
	private String tcCode;
	private String tcName;
	private String tcDescription;
	private String testStream;
	private String testStreamDesc;
	private String subStream;
	private String subStreamDesc;
	private String tester;
	private String status;
	private String statusDesc;
	private String testResult;
	private String remark;
	private String createBy;
	private String createByName;
	private Date createDate;
	private String modifyBy;
	private String modifyByName;
	private Date modifyDate;
	private String project;
    
	
	public Integer getTcId() {
		return tcId;
	}
	public void setTcId(Integer tcId) {
		this.tcId = tcId;
	}
	public String getTcCode() {
		return tcCode;
	}
	public void setTcCode(String tcCode) {
		this.tcCode = tcCode;
	}
	public String getTcName() {
		return tcName;
	}
	public void setTcName(String tcName) {
		this.tcName = tcName;
	}
	public String getTcDescription() {
		return tcDescription;
	}
	public void setTcDescription(String tcDescription) {
		this.tcDescription = tcDescription;
	}
	public String getTester() {
		return tester;
	}
	public void setTester(String tester) {
		this.tester = tester;
	}
	public String getTestResult() {
		return testResult;
	}
	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}
	public String getTestStream() {
		return testStream;
	}
	public void setTestStream(String testStream) {
		this.testStream = testStream;
		/*if(!StringUtils.isEmpty(this.testStream)){
			DicDataVO dic =  CacheMgt.getDicData("1006",testStream);
			if(dic!=null){
				this.testStreamDesc = dic.getDicName();
			}
		}*/
	}
	public String getTestStreamDesc() {
		return testStreamDesc;
	}
	public void setTestStreamDesc(String testStreamDesc) {
		this.testStreamDesc = testStreamDesc;
	}
	public String getSubStream() {
		return subStream;
	}
	public void setSubStream(String subStream) {
		this.subStream = subStream;
		/*if(!StringUtils.isEmpty(this.subStream)){
			DicDataVO dic =  CacheMgt.getDicData("1007",subStream);
			if(dic!=null){
				this.subStreamDesc = dic.getDicName();
			}
		}*/
	}
	public String getSubStreamDesc() {
		return subStreamDesc;
	}
	public void setSubStreamDesc(String subStreamDesc) {
		this.subStreamDesc = subStreamDesc;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
		if(!StringUtils.isEmpty(this.status)){
			DicDataVO dic =  CacheMgt.getDicData("1015",status);
			if(dic!=null){
				this.statusDesc = dic.getDicName();
			}
		}
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
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
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	
}
