package com.apps.esb.service.bss.app.cbs.vo.recharge;

import java.util.List;

public class LifeCycleChgInfoVO {
	private String addValidity;
	private List<LifeCycleStatusVO> oldLifeCycleStatus;
	private List<LifeCycleStatusVO> newLifeCycleStatus;
	public List<LifeCycleStatusVO> getOldLifeCycleStatus() {
		return oldLifeCycleStatus;
	}
	public void setOldLifeCycleStatus(List<LifeCycleStatusVO> oldLifeCycleStatus) {
		this.oldLifeCycleStatus = oldLifeCycleStatus;
	}
	public List<LifeCycleStatusVO> getNewLifeCycleStatus() {
		return newLifeCycleStatus;
	}
	public void setNewLifeCycleStatus(List<LifeCycleStatusVO> newLifeCycleStatus) {
		this.newLifeCycleStatus = newLifeCycleStatus;
	}
	public String getAddValidity() {
		return addValidity;
	}
	public void setAddValidity(String addValidity) {
		this.addValidity = addValidity;
	}
	
	
}
