package com.unieap.monitor.vo;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.unieap.CacheMgt;
import com.unieap.base.vo.BaseVO;
import com.unieap.mdm.vo.DicDataVO;

public class TaskVO extends BaseVO{
	private Long taskId;
	private List<TaskVO> subTasks;
	private List<TaskVO> depTasks;
	private String preTaskIds;
	private String taskGroup;
	private List<TaskresourceVO> taskResource;
	private String taskName;
	private String taskNameRemark;
	private String startDate;
	private Double duration;
	private String status;
	private String statusDesc;
	private String isMain;
	private String isMainDesc;
	private String isShow;
	private String isShowDesc;
	private Long squNum;
	private String completePer;
	private String endDate;
	private String startTime;
	private String endTime;
	private String path;
	private Long parentId;
	private String isLeaf;
	
	public String getPreTaskIds() {
		return preTaskIds;
	}
	public void setPreTaskIds(String preTaskIds) {
		this.preTaskIds = preTaskIds;
	}
	public List<TaskVO> getDepTasks() {
		return depTasks;
	}
	public void setDepTasks(List<TaskVO> depTasks) {
		this.depTasks = depTasks;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getIsMainDesc() {
		return isMainDesc;
	}
	public void setIsMainDesc(String isMainDesc) {
		this.isMainDesc = isMainDesc;
	}
	public String getIsShowDesc() {
		return isShowDesc;
	}
	public void setIsShowDesc(String isShowDesc) {
		this.isShowDesc = isShowDesc;
	}
	public String getIsMain() {
		return isMain;
	}
	public void setIsMain(String isMain) {
		this.isMain = isMain;
		if(StringUtils.isEmpty(this.isMainDesc)&&StringUtils.isNotEmpty(isMain)){
			DicDataVO dic =  CacheMgt.getDicData("ActiveFlag",isMain);
			if(dic!=null){
				this.isMainDesc = dic.getDicName();
			}
		}
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
		if(StringUtils.isEmpty(this.isShowDesc)&&StringUtils.isNotEmpty(isShowDesc)){
			DicDataVO dic =  CacheMgt.getDicData("ActiveFlag",isShow);
			if(dic!=null){
				this.isShowDesc = dic.getDicName();
			}
		}
	}
	
	public Long getSquNum() {
		return squNum;
	}
	public void setSquNum(Long squNum) {
		this.squNum = squNum;
	}
	public List<TaskVO> getSubTasks() {
		return subTasks;
	}
	public void setSubTasks(List<TaskVO> subTasks) {
		this.subTasks = subTasks;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public Double getDuration() {
		return duration;
	}
	public void setDuration(Double duration) {
		this.duration = duration;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
		if(StringUtils.isEmpty(this.statusDesc)&&StringUtils.isNotEmpty(status)){
			DicDataVO dic =  CacheMgt.getDicData("TaskStatus",status);
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
	public String getCompletePer() {
		return completePer;
	}
	public void setCompletePer(String completePer) {
		this.completePer = completePer;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getTaskGroup() {
		return taskGroup;
	}
	public void setTaskGroup(String taskGroup) {
		this.taskGroup = taskGroup;
	}
	public List<TaskresourceVO> getTaskResource() {
		return taskResource;
	}
	public void setTaskResource(List<TaskresourceVO> taskResource) {
		this.taskResource = taskResource;
	}
	public String getTaskNameRemark() {
		return taskNameRemark;
	}
	public void setTaskNameRemark(String taskNameRemark) {
		this.taskNameRemark = taskNameRemark;
	}
	
}
