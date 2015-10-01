package com.apps.esb.service.bss.app.cbs.vo.querybalance;

public class OutstandingVO {
public String billCycleID;
public String billCycleBeginTime;
public String billCycleEndTime;
public String dueDate;
public String outstandingAmount;
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
public String getOutstandingAmount() {
	return outstandingAmount;
}
public void setOutstandingAmount(String outstandingAmount) {
	this.outstandingAmount = outstandingAmount;
}

}
