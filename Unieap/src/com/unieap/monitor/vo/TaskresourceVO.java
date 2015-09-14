package com.unieap.monitor.vo;

import org.apache.commons.lang.StringUtils;

import com.unieap.CacheMgt;
import com.unieap.base.vo.BaseVO;
import com.unieap.mdm.vo.DicDataVO;

public class TaskresourceVO extends BaseVO{
	private Long resourceId;
	private Long taskId;
	private String resourceName;
	private String resourceType;
	private String resourceTypeDesc;
	private String digiResource;
	private String thirdPartyResource;
	private String hwresource;
	
	
	public String getDigiResource() {
		return digiResource;
	}
	public void setDigiResource(String digiResource) {
		this.digiResource = digiResource;
	}
	public String getThirdPartyResource() {
		return thirdPartyResource;
	}
	public void setThirdPartyResource(String thirdPartyResource) {
		this.thirdPartyResource = thirdPartyResource;
	}
	public String getHwresource() {
		return hwresource;
	}
	public void setHwresource(String hwresource) {
		this.hwresource = hwresource;
	}
	public Long getResourceId() {
		return resourceId;
	}
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
		if(StringUtils.isEmpty(this.resourceTypeDesc)&&StringUtils.isNotEmpty(resourceType)){
			DicDataVO dic =  CacheMgt.getDicData("TaskResourceType",resourceType);
			if(dic!=null){
				this.resourceTypeDesc = dic.getDicName();
			}
		}
	}
	public String getResourceTypeDesc() {
		return resourceTypeDesc;
	}
	public void setResourceTypeDesc(String resourceTypeDesc) {
		this.resourceTypeDesc = resourceTypeDesc;
	}
	
}
