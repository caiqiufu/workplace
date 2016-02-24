package com.apps.esb.service.bss.app.vo.account.transfer;

import java.util.List;

import com.unieap.base.vo.BaseVO;

public class QueryTransferLogsVO extends BaseVO{
	private List<TransferLogVO> transferLogList;
	private String totalNum;
	private String beginNum;
	private String fetchNum;
	public List<TransferLogVO> getTransferLogList() {
		return transferLogList;
	}
	public void setTransferLogList(List<TransferLogVO> transferLogList) {
		this.transferLogList = transferLogList;
	}
	public String getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}
	public String getBeginNum() {
		return beginNum;
	}
	public void setBeginNum(String beginNum) {
		this.beginNum = beginNum;
	}
	public String getFetchNum() {
		return fetchNum;
	}
	public void setFetchNum(String fetchNum) {
		this.fetchNum = fetchNum;
	}
	
}
