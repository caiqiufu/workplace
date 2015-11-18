package com.unieap.base.vo;

/**
 * @author caibo
 *
 */
public class ResourceVO extends BaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resourceId;
	private String resourceCode;
	private String resourceName;
	private String type;
	private String value;
	public ResourceVO(){
		
	}
	
	public ResourceVO(String resourceId, String resourceCode,
			String resourceName, String type, String value) {
		super();
		this.resourceId = resourceId;
		this.resourceCode = resourceCode;
		this.resourceName = resourceName;
		this.type = type;
		this.value = value;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getResourceCode() {
		return resourceCode;
	}
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
