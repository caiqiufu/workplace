package com.apps.esb.service.bss.app.cbs.vo.queryaggregationamount;

import java.util.List;

public class AggregationAmountVO {
	private String billChargeID;
	private String billCycleID;
	private String custKey;
	private String acctKey;
	private String objType;
	private String objKey;
	private String primaryIdentify;
	private String category;
	private List<AggregationInfoVO> aggregationInfoList;
	
	public String getBillChargeID() {
		return billChargeID;
	}
	public void setBillChargeID(String billChargeID) {
		this.billChargeID = billChargeID;
	}
	public String getBillCycleID() {
		return billCycleID;
	}
	public void setBillCycleID(String billCycleID) {
		this.billCycleID = billCycleID;
	}
	public String getCustKey() {
		return custKey;
	}
	public void setCustKey(String custKey) {
		this.custKey = custKey;
	}
	public String getAcctKey() {
		return acctKey;
	}
	public void setAcctKey(String acctKey) {
		this.acctKey = acctKey;
	}
	public String getObjType() {
		return objType;
	}
	public void setObjType(String objType) {
		this.objType = objType;
	}
	public String getObjKey() {
		return objKey;
	}
	public void setObjKey(String objKey) {
		this.objKey = objKey;
	}
	public String getPrimaryIdentify() {
		return primaryIdentify;
	}
	public void setPrimaryIdentify(String primaryIdentify) {
		this.primaryIdentify = primaryIdentify;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public List<AggregationInfoVO> getAggregationInfoList() {
		return aggregationInfoList;
	}
	public void setAggregationInfoList(List<AggregationInfoVO> aggregationInfoList) {
		this.aggregationInfoList = aggregationInfoList;
	}
	
	
	
}
