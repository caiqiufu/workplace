package com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo;

import java.util.List;

public class ActualCustomerVO {
	private IndividualInfoVO individualInfoVO;
	private CorpInfoVO corpInfoVO;
	private List<AddressInfoVO> AddressInfoList;
	public IndividualInfoVO getIndividualInfoVO() {
		return individualInfoVO;
	}
	public void setIndividualInfoVO(IndividualInfoVO individualInfoVO) {
		this.individualInfoVO = individualInfoVO;
	}
	public CorpInfoVO getCorpInfoVO() {
		return corpInfoVO;
	}
	public void setCorpInfoVO(CorpInfoVO corpInfoVO) {
		this.corpInfoVO = corpInfoVO;
	}
	public List<AddressInfoVO> getAddressInfoList() {
		return AddressInfoList;
	}
	public void setAddressInfoList(List<AddressInfoVO> addressInfoList) {
		AddressInfoList = addressInfoList;
	}
	
}
