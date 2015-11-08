package com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo;

public class ConsumptionLimitVO {
	private String actionType;
	private String limitType;
	private String notifyType;
	private String notifyLimit;
	private String limitValue;
	private String usedLimitValue;
	private String remainLimitValue;
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getLimitType() {
		return limitType;
	}
	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}
	public String getNotifyType() {
		return notifyType;
	}
	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}
	public String getNotifyLimit() {
		return notifyLimit;
	}
	public void setNotifyLimit(String notifyLimit) {
		this.notifyLimit = notifyLimit;
	}
	public String getLimitValue() {
		return limitValue;
	}
	public void setLimitValue(String limitValue) {
		this.limitValue = limitValue;
	}
	public String getUsedLimitValue() {
		return usedLimitValue;
	}
	public void setUsedLimitValue(String usedLimitValue) {
		this.usedLimitValue = usedLimitValue;
	}
	public String getRemainLimitValue() {
		return remainLimitValue;
	}
	public void setRemainLimitValue(String remainLimitValue) {
		this.remainLimitValue = remainLimitValue;
	}
	
}
