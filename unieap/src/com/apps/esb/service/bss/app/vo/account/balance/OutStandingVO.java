package com.apps.esb.service.bss.app.vo.account.balance;

import java.util.List;

import com.unieap.base.vo.BaseVO;

public class OutStandingVO extends BaseVO{
	private String billCycleID;
	private String billCycleBeginTime;
	private String billCycleEndTime;
	private String dueDate;
	private String outStandingAmount;
	private List<OutStandingDetailVO> outStandingDetailList;
	public String getBillCycleID() {
		return billCycleID;
	}
	public void setBillCycleID(String billCycleID) {
		this.billCycleID = billCycleID;
	}
	public String getBillCycleBeginTime() {
		return billCycleBeginTime;
	}
	public void setBillCycleBeginTime(String billCycleBeginTime) {
		this.billCycleBeginTime = billCycleBeginTime;
	}
	public String getBillCycleEndTime() {
		return billCycleEndTime;
	}
	public void setBillCycleEndTime(String billCycleEndTime) {
		this.billCycleEndTime = billCycleEndTime;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getOutStandingAmount() {
		return outStandingAmount;
	}
	public void setOutStandingAmount(String outStandingAmount) {
		this.outStandingAmount = outStandingAmount;
	}
	public List<OutStandingDetailVO> getOutStandingDetailList() {
		return outStandingDetailList;
	}
	public void setOutStandingDetailList(List<OutStandingDetailVO> outStandingDetailList) {
		this.outStandingDetailList = outStandingDetailList;
	}
	
}
