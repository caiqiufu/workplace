package com.apps.esb.service.bss.app.crm.vo.getavailablesuboffering;

import java.util.List;
import java.util.Map;

import com.apps.esb.service.bss.app.crm.vo.getavailableprimaryoffering.OfferingInfoVO;

public class AvailableSubOfferingVO {
	private List<OfferingInfoVO> offeringInfoList;
	private Map<String,OfferingInfoVO> offeringInfoMap;

	public List<OfferingInfoVO> getOfferingInfoList() {
		return offeringInfoList;
	}

	public void setOfferingInfoList(List<OfferingInfoVO> offeringInfoList) {
		this.offeringInfoList = offeringInfoList;
	}

	public Map<String, OfferingInfoVO> getOfferingInfoMap() {
		return offeringInfoMap;
	}

	public void setOfferingInfoMap(Map<String, OfferingInfoVO> offeringInfoMap) {
		this.offeringInfoMap = offeringInfoMap;
	}
	
}
