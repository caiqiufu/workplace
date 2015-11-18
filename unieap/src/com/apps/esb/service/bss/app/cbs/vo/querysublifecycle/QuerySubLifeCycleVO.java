package com.apps.esb.service.bss.app.cbs.vo.querysublifecycle;

import java.util.List;

public class QuerySubLifeCycleVO {
	private List<LifeCycleStatusVO> lifeCycleStatusList; 
	private String currentStatusIndex;
	private String rBlacklistStatus;
	private String fraudTimes;
	public List<LifeCycleStatusVO> getLifeCycleStatusList() {
		return lifeCycleStatusList;
	}
	public void setLifeCycleStatusList(List<LifeCycleStatusVO> lifeCycleStatusList) {
		this.lifeCycleStatusList = lifeCycleStatusList;
	}
	public String getCurrentStatusIndex() {
		return currentStatusIndex;
	}
	public void setCurrentStatusIndex(String currentStatusIndex) {
		this.currentStatusIndex = currentStatusIndex;
	}
	public String getrBlacklistStatus() {
		return rBlacklistStatus;
	}
	public void setrBlacklistStatus(String rBlacklistStatus) {
		this.rBlacklistStatus = rBlacklistStatus;
	}
	public String getFraudTimes() {
		return fraudTimes;
	}
	public void setFraudTimes(String fraudTimes) {
		this.fraudTimes = fraudTimes;
	}
	
}
