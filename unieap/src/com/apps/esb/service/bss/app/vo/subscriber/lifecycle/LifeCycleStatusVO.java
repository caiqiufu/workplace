package com.apps.esb.service.bss.app.vo.subscriber.lifecycle;

import java.util.List;

import com.unieap.base.vo.BaseVO;

public class LifeCycleStatusVO extends BaseVO{
	private List<StatusVO> statusList; 
	private StatusVO currentStatus;
	private String rBlacklistStatus;
	private String fraudTimes;
	public List<StatusVO> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<StatusVO> statusList) {
		this.statusList = statusList;
	}
	public StatusVO getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(StatusVO currentStatus) {
		this.currentStatus = currentStatus;
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
