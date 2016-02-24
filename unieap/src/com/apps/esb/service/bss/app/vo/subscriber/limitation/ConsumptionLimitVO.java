package com.apps.esb.service.bss.app.vo.subscriber.limitation;

import com.unieap.base.vo.BaseVO;

public class ConsumptionLimitVO extends BaseVO{
	private String actionType;
	private String actionTypeDesc;
	private String limitType;
	private String limitTypeDesc;
	private String notifyType;
	private String notifyTypeDesc;
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
	public String getActionTypeDesc() {
		return actionTypeDesc;
	}
	public void setActionTypeDesc(String actionTypeDesc) {
		this.actionTypeDesc = actionTypeDesc;
	}
	public String getLimitTypeDesc() {
		return limitTypeDesc;
	}
	public void setLimitTypeDesc(String limitTypeDesc) {
		this.limitTypeDesc = limitTypeDesc;
	}
	public String getNotifyTypeDesc() {
		return notifyTypeDesc;
	}
	public void setNotifyTypeDesc(String notifyTypeDesc) {
		this.notifyTypeDesc = notifyTypeDesc;
	}
	
}
