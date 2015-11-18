package com.apps.esb.vo;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.unieap.CacheMgt;
import com.unieap.mdm.vo.DicDataVO;

public class EsblogVO {
	private Integer logId;
	private String channelCode;
	private String bizCode;
	private String bizCodeDesc;
	private String serviceNumber;
	private String transactionId;
	private String requestTime;
	private String requestTimeStart;
	private String requestTimeEnd;
	private String responseTime;
	private String systemCode;
	private String extTransactionId;
	private Date createDate;
	private String resultCode;
	private String resultDesc;
	private String executeTime;
	private String sourceSystem;
	private String destSystem;
	public Integer getLogId() {
		return logId;
	}
	public void setLogId(Integer logId) {
		this.logId = logId;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getBizCode() {
		return bizCode;
	}
	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
		if (!StringUtils.isEmpty(this.bizCode)) {
			DicDataVO dic = CacheMgt.getDicData("bizHandler", bizCode);
			if (dic != null) {
				this.bizCodeDesc = dic.getDicName();
			}else{
				this.bizCodeDesc = bizCode;
			}
		}
	}
	public String getBizCodeDesc() {
		return bizCodeDesc;
	}
	public void setBizCodeDesc(String bizCodeDesc) {
		this.bizCodeDesc = bizCodeDesc;
	}
	public String getServiceNumber() {
		return serviceNumber;
	}
	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	public String getExtTransactionId() {
		return extTransactionId;
	}
	public void setExtTransactionId(String extTransactionId) {
		this.extTransactionId = extTransactionId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	public String getExecuteTime() {
		return executeTime;
	}
	public void setExecuteTime(String executeTime) {
		this.executeTime = executeTime;
	}
	public String getSourceSystem() {
		return sourceSystem;
	}
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}
	public String getDestSystem() {
		return destSystem;
	}
	public void setDestSystem(String destSystem) {
		this.destSystem = destSystem;
	}
	public String getRequestTimeStart() {
		return requestTimeStart;
	}
	public void setRequestTimeStart(String requestTimeStart) {
		this.requestTimeStart = requestTimeStart;
	}
	public String getRequestTimeEnd() {
		return requestTimeEnd;
	}
	public void setRequestTimeEnd(String requestTimeEnd) {
		this.requestTimeEnd = requestTimeEnd;
	}



}
