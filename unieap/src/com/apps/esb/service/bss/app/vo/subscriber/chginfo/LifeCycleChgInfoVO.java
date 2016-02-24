package com.apps.esb.service.bss.app.vo.subscriber.chginfo;

import java.util.List;

import com.apps.esb.service.bss.app.vo.subscriber.lifecycle.StatusVO;
import com.unieap.base.vo.BaseVO;

public class LifeCycleChgInfoVO extends BaseVO{
	private String addValidity;
	private List<StatusVO> oldLifeCycleStatusList;
	private List<StatusVO> newLifeCycleStatusList;
	public String getAddValidity() {
		return addValidity;
	}
	public void setAddValidity(String addValidity) {
		this.addValidity = addValidity;
	}
	public List<StatusVO> getOldLifeCycleStatusList() {
		return oldLifeCycleStatusList;
	}
	public void setOldLifeCycleStatusList(List<StatusVO> oldLifeCycleStatusList) {
		this.oldLifeCycleStatusList = oldLifeCycleStatusList;
	}
	public List<StatusVO> getNewLifeCycleStatusList() {
		return newLifeCycleStatusList;
	}
	public void setNewLifeCycleStatusList(List<StatusVO> newLifeCycleStatusList) {
		this.newLifeCycleStatusList = newLifeCycleStatusList;
	}
	
}
