package com.apps.esb.service.bss.app.vo.subscriber.offering;

import java.util.List;
import java.util.Map;

import com.unieap.base.vo.BaseVO;

public class QueryAvailableOfferingVO extends BaseVO{
	private List<OfferingVO> primaryOfferingList;
	private List<OfferingVO> supplementaryOfferingList;
	private Map<String,OfferingVO> offeringMap;
	public List<OfferingVO> getPrimaryOfferingList() {
		return primaryOfferingList;
	}
	public void setPrimaryOfferingList(List<OfferingVO> primaryOfferingList) {
		this.primaryOfferingList = primaryOfferingList;
	}
	public List<OfferingVO> getSupplementaryOfferingList() {
		return supplementaryOfferingList;
	}
	public void setSupplementaryOfferingList(List<OfferingVO> supplementaryOfferingList) {
		this.supplementaryOfferingList = supplementaryOfferingList;
	}
	public Map<String, OfferingVO> getOfferingMap() {
		return offeringMap;
	}
	public void setOfferingMap(Map<String, OfferingVO> offeringMap) {
		this.offeringMap = offeringMap;
	}
	
}
