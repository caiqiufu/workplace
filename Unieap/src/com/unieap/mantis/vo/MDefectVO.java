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

public class MDefectVO {
	private Integer defectId;
    private String prodVersion;
    private String prodVersionDesc;
    private String testStream;
    private String testStreamDesc;
    private String severity;
    private String severityDesc;
    private String priority;
    private String priorityDesc;
    private String subStream;
    private String subStreamDesc;
    private String testPlan;
    private String testPlanDesc;
    private String tcId;
    private String title;
    private String descpt;
    private String remark;
    private String steps;
    private Date createDate;
    private Date modifyDate;
    private String modifyBy;
    private String modifyByName;
    private String createBy;
    private String createByName;
    private String status;
    private String statusDesc;
    private String assignto;
    private String assigntoName;
    private Date targetDate;
    private String project;
    private String projectDesc;
    private String dts;
    
	public Integer getDefectId() {
		return defectId;
	}
	public void setDefectId(Integer defectId) {
		this.defectId = defectId;
	}
	public String getProdVersion() {
		return prodVersion;
	}
	public void setProdVersion(String prodVersion) {
		this.prodVersion = prodVersion;
		if(!StringUtils.isEmpty(this.prodVersion)){
			DicDataVO dic =  CacheMgt.getDicData("1005",prodVersion);
			if(dic!=null){
				this.prodVersionDesc = dic.getDicName();
			}
		}
	}
	public String getProdVersionDesc() {
		return prodVersionDesc;
	}
	public void setProdVersionDesc(String prodVersionDesc) {
		this.prodVersionDesc = prodVersionDesc;
	}
	public String getTestStream() {
		return testStream;
	}
	public void setTestStream(String testStream) {
		this.testStream = testStream;
		if(!StringUtils.isEmpty(this.testStream)){
			DicDataVO dic =  CacheMgt.getDicData("1006",testStream);
			if(dic!=null){
				this.testStreamDesc = dic.getDicName();
			}
		}
	}
	public String getTestStreamDesc() {
		return testStreamDesc;
	}
	public void setTestStreamDesc(String testStreamDesc) {
		this.testStreamDesc = testStreamDesc;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
		if(!StringUtils.isEmpty(this.severity)){
			DicDataVO dic =  CacheMgt.getDicData("1002",severity);
			if(dic!=null){
				this.severityDesc = dic.getDicName();
			}
		}
	}
	public String getSeverityDesc() {
		return severityDesc;
	}
	public void setSeverityDesc(String severityDesc) {
		this.severityDesc = severityDesc;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
		if(!StringUtils.isEmpty(this.priority)){
			DicDataVO dic =  CacheMgt.getDicData("1004",priority);
			if(dic!=null){
				this.priorityDesc = dic.getDicName();
			}
		}
	}
	public String getPriorityDesc() {
		return priorityDesc;
	}
	public void setPriorityDesc(String priorityDesc) {
		this.priorityDesc = priorityDesc;
	}
	public String getSubStream() {
		return subStream;
	}
	public void setSubStream(String subStream) {
		this.subStream = subStream;
		if(!StringUtils.isEmpty(this.subStream)){
			DicDataVO dic =  CacheMgt.getDicData("1007",subStream);
			if(dic!=null){
				this.subStreamDesc = dic.getDicName();
			}
		}
	}
	public String getSubStreamDesc() {
		return subStreamDesc;
	}
	public void setSubStreamDesc(String subStreamDesc) {
		this.subStreamDesc = subStreamDesc;
	}
	public String getTestPlan() {
		return testPlan;
	}
	public void setTestPlan(String testPlan) {
		this.testPlan = testPlan;
		if(!StringUtils.isEmpty(this.testPlan)){
			DicDataVO dic =  CacheMgt.getDicData("1008",testPlan);
			if(dic!=null){
				this.testPlanDesc = dic.getDicName();
			}
		}
	}
	public String getTestPlanDesc() {
		return testPlanDesc;
	}
	public void setTestPlanDesc(String testPlanDesc) {
		this.testPlanDesc = testPlanDesc;
	}
	public String getTcId() {
		return tcId;
	}
	public void setTcId(String tcId) {
		this.tcId = tcId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescpt() {
		return descpt;
	}
	public void setDescpt(String descpt) {
		this.descpt = descpt;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSteps() {
		return steps;
	}
	public void setSteps(String steps) {
		this.steps = steps;
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
			DicDataVO dic =  CacheMgt.getDicData("1003",status);
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
	public String getAssignto() {
		return assignto;
	}
	public void setAssignto(String assignto) {
		this.assignto = assignto;
		Property userCode = Property.forName("userCode");
		DetachedCriteria criteria=DetachedCriteria.forClass(com.unieap.pojo.User.class).add(userCode.eq(assignto));
		List<com.unieap.pojo.User> users = DBManager.getHT(null).findByCriteria(criteria);
		if(users!=null && users.size()>0){
			User user = users.get(0);
			assigntoName = user.getUserName();
		}
	}
	public String getAssigntoName() {
		return assigntoName;
	}
	public void setAssigntoName(String assigntoName) {
		this.assigntoName = assigntoName;
	}
	public Date getTargetDate() {
		return targetDate;
	}
	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
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
		if(!StringUtils.isEmpty(this.project)){
			DicDataVO dic =  CacheMgt.getDicData("1014",project);
			if(dic!=null){
				this.projectDesc = dic.getDicName();
			}
		}
	}
	public String getProjectDesc() {
		return projectDesc;
	}
	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}
	public String getDts() {
		return dts;
	}
	public void setDts(String dts) {
		this.dts = dts;
	}
	
    
}
