package com.unieap.pojo;

/**
 * HandlerConfig entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class HandlerConfig implements java.io.Serializable {

	// Fields

	private Integer handlerId;
	private String handlerName;
	private String className;
	private String remark;
	private String activeFlag;

	// Constructors

	/** default constructor */
	public HandlerConfig() {
	}

	/** minimal constructor */
	public HandlerConfig(Integer handlerId, String handlerName,
			String activeFlag) {
		this.handlerId = handlerId;
		this.handlerName = handlerName;
		this.activeFlag = activeFlag;
	}

	/** full constructor */
	public HandlerConfig(Integer handlerId, String handlerName,
			String className, String remark, String activeFlag) {
		this.handlerId = handlerId;
		this.handlerName = handlerName;
		this.className = className;
		this.remark = remark;
		this.activeFlag = activeFlag;
	}

	// Property accessors

	public Integer getHandlerId() {
		return this.handlerId;
	}

	public void setHandlerId(Integer handlerId) {
		this.handlerId = handlerId;
	}

	public String getHandlerName() {
		return this.handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getActiveFlag() {
		return this.activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

}