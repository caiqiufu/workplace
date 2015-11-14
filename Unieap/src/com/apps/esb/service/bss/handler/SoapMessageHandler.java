package com.apps.esb.service.bss.handler;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;

import org.springframework.util.StringUtils;

import com.apps.esb.pojo.Esblog;
import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.element.RequestBody;
import com.apps.esb.service.bss.element.RequestHeader;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponseBody;
import com.apps.esb.service.bss.element.ResponsetHeader;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.pojo.ErrorCode;

public class SoapMessageHandler {
	public MessageFactory messageFactory;
	public SOAPConnectionFactory connectionFactory;

	public SoapMessageHandler() throws Exception {
		messageFactory = MessageFactory.newInstance();
		connectionFactory = SOAPConnectionFactory.newInstance();
	}

	/**
	 * CRM query header
	 * @param method
	 * @param message
	 * @throws Exception
	 */
	public void getCRMQuerHeader(String method, SOAPMessage message) throws Exception {
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		envelope.addNamespaceDeclaration("quer", "http://crm.huawei.com/query/");
		envelope.addNamespaceDeclaration("bas", "http://crm.huawei.com/basetype/");
		SOAPBody body = envelope.getBody();
		SOAPElement bodyElement = body.addChildElement(method, "quer");
		SOAPElement requestHeaderElement = bodyElement.addChildElement("RequestHeader","quer");
		requestHeaderElement.addChildElement("TransactionId","bas").addTextNode(BssServiceUtils.generateTransactionId());
		requestHeaderElement.addChildElement("ChannelId","bas").addTextNode(SYSConfig.getConfig().get("crm.inf.channelCode"));
		requestHeaderElement.addChildElement("TechnicalChannelId","bas").addTextNode(SYSConfig.getConfig().get("crm.inf.systemCode"));
		requestHeaderElement.addChildElement("AccessUser","bas").addTextNode(SYSConfig.getConfig().get("crm.inf.accessUser"));
		requestHeaderElement.addChildElement("AccessPwd","bas").addTextNode(SYSConfig.getConfig().get("crm.inf.accessPwd"));
	}
	public void getCRMUpdHeader(String method, SOAPMessage message) throws Exception {
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		envelope.addNamespaceDeclaration("upd", "http://crm.huawei.com/update/");
		envelope.addNamespaceDeclaration("bas", "http://crm.huawei.com/basetype/");
		SOAPBody body = envelope.getBody();
		SOAPElement bodyElement = body.addChildElement(method, "upd");
		SOAPElement requestHeaderElement = bodyElement.addChildElement("RequestHeader","upd");
		requestHeaderElement.addChildElement("TransactionId","bas").addTextNode(BssServiceUtils.generateTransactionId());
		requestHeaderElement.addChildElement("ChannelId","bas").addTextNode(SYSConfig.getConfig().get("crm.inf.channelCode"));
		requestHeaderElement.addChildElement("TechnicalChannelId","bas").addTextNode(SYSConfig.getConfig().get("crm.inf.systemCode"));
		requestHeaderElement.addChildElement("AccessUser","bas").addTextNode(SYSConfig.getConfig().get("crm.inf.accessUser"));
		requestHeaderElement.addChildElement("AccessPwd","bas").addTextNode(SYSConfig.getConfig().get("crm.inf.accessPwd"));
	}
	public void getCRMSerHeader(String method, SOAPMessage message) throws Exception {
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		envelope.addNamespaceDeclaration("ser", "http://crm.huawei.com/service/");
		envelope.addNamespaceDeclaration("bas", "http://crm.huawei.com/basetype/");
		SOAPBody body = envelope.getBody();
		SOAPElement bodyElement = body.addChildElement(method, "ser");
		SOAPElement requestHeaderElement = bodyElement.addChildElement("RequestHeader","ser");
		requestHeaderElement.addChildElement("TransactionId","bas").addTextNode(BssServiceUtils.generateTransactionId());
		requestHeaderElement.addChildElement("ChannelId","bas").addTextNode(SYSConfig.getConfig().get("crm.inf.channelCode"));
		requestHeaderElement.addChildElement("TechnicalChannelId","bas").addTextNode(SYSConfig.getConfig().get("crm.inf.systemCode"));
		requestHeaderElement.addChildElement("AccessUser","bas").addTextNode(SYSConfig.getConfig().get("crm.inf.accessUser"));
		requestHeaderElement.addChildElement("AccessPwd","bas").addTextNode(SYSConfig.getConfig().get("crm.inf.accessPwd"));
	}
	public void getCBSArsHeader(String method, SOAPMessage message) throws Exception {
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		envelope.addNamespaceDeclaration("ars", "http://www.huawei.com/bme/cbsinterface/arservices");
		envelope.addNamespaceDeclaration("cbs", "http://www.huawei.com/bme/cbsinterface/cbscommon");
		envelope.addNamespaceDeclaration("arc", "http://cbs.huawei.com/ar/wsservice/arcommon");
		SOAPBody body = envelope.getBody();
		SOAPElement bodyElement = body.addChildElement(method,"ars");
		SOAPElement requestHeaderElement = bodyElement.addChildElement("RequestHeader");
		requestHeaderElement.addChildElement("Version","cbs").addTextNode("1.0");
		requestHeaderElement.addChildElement("MessageSeq","cbs").addTextNode(BssServiceUtils.generateTransactionId());
		
		SOAPElement accessSecurityElement = requestHeaderElement.addChildElement("AccessSecurity","cbs");
		accessSecurityElement.addChildElement("LoginSystemCode","cbs").addTextNode(SYSConfig.getConfig().get("cbs.inf.accessUser"));
		accessSecurityElement.addChildElement("Password","cbs").addTextNode(SYSConfig.getConfig().get("cbs.inf.accessPwd"));
	}
	public void getCBSBcHeader(String method, SOAPMessage message) throws Exception {
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		envelope.addNamespaceDeclaration("bcs", "http://www.huawei.com/bme/cbsinterface/bcservices");
		envelope.addNamespaceDeclaration("cbs", "http://www.huawei.com/bme/cbsinterface/cbscommon");
		envelope.addNamespaceDeclaration("bcc", "http://www.huawei.com/bme/cbsinterface/bccommon");
		SOAPBody body = envelope.getBody();
		SOAPElement bodyElement = body.addChildElement(method,"bcs");
		SOAPElement requestHeaderElement = bodyElement.addChildElement("RequestHeader");
		requestHeaderElement.addChildElement("Version","cbs").addTextNode("1.0");
		requestHeaderElement.addChildElement("MessageSeq","cbs").addTextNode(BssServiceUtils.generateTransactionId());
		
		SOAPElement accessSecurityElement = requestHeaderElement.addChildElement("AccessSecurity","cbs");
		accessSecurityElement.addChildElement("LoginSystemCode","cbs").addTextNode(SYSConfig.getConfig().get("cbs.inf.accessUser"));
		accessSecurityElement.addChildElement("Password","cbs").addTextNode(SYSConfig.getConfig().get("cbs.inf.accessPwd"));
	}
	public void getCBSBbHeader(String method, SOAPMessage message) throws Exception {
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		envelope.addNamespaceDeclaration("bbs", "http://www.huawei.com/bme/cbsinterface/bbservices");
		envelope.addNamespaceDeclaration("cbs", "http://www.huawei.com/bme/cbsinterface/cbscommon");
		envelope.addNamespaceDeclaration("bbc", "http://www.huawei.com/bme/cbsinterface/bbcommon");
		SOAPBody body = envelope.getBody();
		SOAPElement bodyElement = body.addChildElement(method,"bbs");
		SOAPElement requestHeaderElement = bodyElement.addChildElement("RequestHeader");
		requestHeaderElement.addChildElement("Version","cbs").addTextNode("1.0");
		requestHeaderElement.addChildElement("MessageSeq","cbs").addTextNode(BssServiceUtils.generateTransactionId());
		
		SOAPElement accessSecurityElement = requestHeaderElement.addChildElement("AccessSecurity","cbs");
		accessSecurityElement.addChildElement("LoginSystemCode","cbs").addTextNode(SYSConfig.getConfig().get("cbs.inf.accessUser"));
		accessSecurityElement.addChildElement("Password","cbs").addTextNode(SYSConfig.getConfig().get("cbs.inf.accessPwd"));
	}
	public ProcessResult process(Object obj, RequestInfo requestInfo, String serviceNumber, String extParameters,
			String parameters,String timeoutName) throws Exception {
		long beginTime = System.currentTimeMillis();
		BizHandler bizHandler = (BizHandler) obj;
		SOAPMessage request = bizHandler.getRequestSOAPMessage(serviceNumber, requestInfo);
		SOAPMessage response = callService(serviceNumber, requestInfo, request, beginTime,timeoutName);
		ProcessResult processResult = bizHandler.getResposeResult(response);
		processResult.setServiceNumber(serviceNumber);
		exchangeBillingErrorCodeMapping(processResult);
		String responseTime = UnieapConstants.getCurrentTime(null, null);
		requestInfo.getRequestHeader().setResponseTime(responseTime);
		String bizCode = requestInfo.getRequestHeader().getBizCode();
		String systemName = getSystemName(bizCode);
		long endTime = System.currentTimeMillis();
		String during = "" + (endTime - beginTime);
		this.esbLog(request, response, requestInfo.getRequestHeader(),processResult, during, systemName);
		return processResult;
	}
    
	public void exchangeBillingErrorCodeMapping(ProcessResult processResult){
		ErrorCode errorCode = CacheMgt.getErrorCode(processResult.getResultCode());
		if(errorCode!=null){
			processResult.setResultCode(errorCode.getErrorCode());
			processResult.setResultDesc(errorCode.getErrorDesc());
		}
	}
	
	public SOAPMessage callWebService(String url, SOAPMessage request, String timeoutConfig) throws Exception {
		int timeout = 10000;
		if (!StringUtils.isEmpty(timeoutConfig) && SYSConfig.getConfig().get(timeoutConfig) != null) {
			timeout = Integer.parseInt(SYSConfig.getConfig().get(timeoutConfig));
		}
		String isdebug =SYSConfig.getConfig().get("mcare.app.extaction.debug");
		if(UnieapConstants.YES.equals(isdebug)){
			System.setProperty("java.net.useSystemProxies", "true");
		}
		SOAPConnection connection = connectionFactory.createConnection();
		SOAPMessage response = connection.call(request, getURL(url, timeout));
		return response;
	}

	public URL getURL(String url, final int timeout) throws Exception {
		URL urlval = new URL(new URL(url), "", new URLStreamHandler() {
			@Override
			protected URLConnection openConnection(URL url) throws IOException {
				URL target = new URL(url.toString());
				URLConnection connection = target.openConnection();
				// Connection settings
				connection.setConnectTimeout(timeout);
				connection.setReadTimeout(timeout);
				return (connection);
			}
		});
		return urlval;
	}

	public SOAPMessage callService(String serviceNumber, RequestInfo requestInfo, SOAPMessage request, long beginTime,String timeoutName)
			throws Exception {
		String requestTime = UnieapConstants.getCurrentTime(null, null);
		RequestHeader requestHeader = requestInfo.getRequestHeader();
		requestHeader.setRequestTime(requestTime);
		requestHeader.setSystemCode("esb");
		String bizCode = requestHeader.getBizCode();
		String url = getUrl(bizCode);
		String systemName = getSystemName(bizCode);
		SOAPMessage response;
		try {
			response = this.callWebService(url, request, timeoutName);
		} catch (Exception e) {
			ProcessResult processResult = new ProcessResult();
			processResult.setResultCode(UnieapConstants.C99999);
			processResult.setResultDesc(e.getLocalizedMessage());
			String requestInfoString = BssServiceUtils.getSoapMessageString(request);
			long endTime = System.currentTimeMillis();
			String during = "" + (endTime - beginTime);
			Esblog esblog = BssServiceUtils.getEsbLog(requestHeader,processResult, requestInfoString, 
					"", during, systemName);
			CacheMgt.addEsblog(esblog);
			throw e;
		}
		return response;

	}

	public String getUrl(String bizCode) throws Exception {
		Map<String, String> infInfo = SYSConfig.getEsbInfo().get(bizCode);
		if (infInfo == null) {
			throw new Exception("bizCode[" + bizCode + "] not exist.");
		}
		return infInfo.get("url");
	}

	public String getSystemName(String bizCode) throws Exception {
		Map<String, String> infInfo = SYSConfig.getEsbInfo().get(bizCode);
		if (infInfo == null) {
			throw new Exception("bizCode[" + bizCode + "] not exist.");
		}
		return infInfo.get("systemName");
	}
	public String getInftName(String bizCode) throws Exception {
		Map<String, String> infInfo = SYSConfig.getEsbInfo().get(bizCode);
		if (infInfo == null) {
			throw new Exception("bizCode[" + bizCode + "] not exist.");
		}
		return infInfo.get("infName");
	}

	public void esbLog(SOAPMessage request, SOAPMessage response, RequestHeader requestHeader,ProcessResult processResult,
			String during,String destSystem) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("request", request);
		data.put("response", response);
		data.put("during", during);
		data.put("requestHeader", requestHeader);
		data.put("processResult", processResult);
		data.put("destSystem", destSystem);
		CacheMgt.addEsbSOAPlog(data);
	}

	public RequestInfo getCRMQueryRequestInfoFromSOAPMessage(SOAPMessage request) throws Exception {
		if (request == null) {
			return new RequestInfo();
		}
		RequestInfo requestInfo = new RequestInfo();
		org.w3c.dom.Document document = request.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("quer:RequestHeader").getLength() > 0) {
			RequestHeader requestHeader = new RequestHeader();
			if (document.getElementsByTagName("bas:TransactionId").getLength() > 0) {
				String transactionId = document.getElementsByTagName("bas:TransactionId").item(0).getTextContent();
				requestHeader.setTransactionId(transactionId);
			}
			if (document.getElementsByTagName("bas:ChannelId").getLength() > 0) {
				String channelCode = document.getElementsByTagName("bas:ChannelId").item(0).getTextContent();
				requestHeader.setChannelCode(channelCode);
			}
			if (document.getElementsByTagName("bas:TechnicalChannelId").getLength() > 0) {
				String systemCode = document.getElementsByTagName("bas:TechnicalChannelId").item(0).getTextContent();
				requestHeader.setSystemCode(systemCode);
			}
			if (document.getElementsByTagName("bas:AccessUser").getLength() > 0) {
				String accessUser = document.getElementsByTagName("bas:AccessUser").item(0).getTextContent();
				requestHeader.setAccessUser(accessUser);
			}
			if (document.getElementsByTagName("bas:ExtTransactionId").getLength() > 0) {
				String extTransactionId = document.getElementsByTagName("bas:ExtTransactionId").item(0)
						.getTextContent();
				requestHeader.setExtTransactionId(extTransactionId);
			}
			if (document.getElementsByTagName("bas:RequestTime").getLength() > 0) {
				String requestTime = document.getElementsByTagName("bas:RequestTime").item(0).getTextContent();
				requestHeader.setRequestTime(requestTime);
			}
			if (document.getElementsByTagName("bas:Version").getLength() > 0) {
				String version = document.getElementsByTagName("bas:Version").item(0).getTextContent();
				requestHeader.setVersion(version);
			}
			requestInfo.setRequestHeader(requestHeader);
		}
		if (document.getElementsByTagName("quer:GetCustomerBody").getLength() > 0) {
			RequestBody requestBody = new RequestBody();
			if (document.getElementsByTagName("quer:ServiceNumber").getLength() > 0) {
				String serviceNumber = document.getElementsByTagName("quer:ServiceNumber").item(0).getTextContent();
				requestBody.setServiceNumber(serviceNumber);
			}
			requestInfo.setRequestBody(requestBody);
		}
		return requestInfo;
	}

	public ResponsetInfo getCRMQueryResponseInfoFromSOAPMessage(SOAPMessage response) throws Exception {
		if (response == null) {
			return new ResponsetInfo();
		}
		ResponsetInfo responsetInfo = new ResponsetInfo();
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("quer:ResponseHeader").getLength() > 0) {
			ResponsetHeader responsetHeader = new ResponsetHeader();
			if (document.getElementsByTagName("bas:RetCode").getLength() > 0) {
				String resultCode = document.getElementsByTagName("bas:RetCode").item(0).getTextContent();
				responsetHeader.setResultCode(resultCode);
			}
			if (document.getElementsByTagName("bas:RetMsg").getLength() > 0) {
				String resultDesc = document.getElementsByTagName("bas:RetMsg").item(0).getTextContent();
				responsetHeader.setResultDesc(resultDesc);
			}
			if (document.getElementsByTagName("bas:TransactionId").getLength() > 0) {
				String transactionId = document.getElementsByTagName("bas:TransactionId").item(0).getTextContent();
				responsetHeader.setTransactionId(transactionId);
			}
			if (document.getElementsByTagName("bas:ChannelId").getLength() > 0) {
				String channelCode = document.getElementsByTagName("bas:ChannelId").item(0).getTextContent();
				responsetHeader.setChannelCode(channelCode);
			}
			responsetInfo.setResponsetHeader(responsetHeader);
		}
		if (document.getElementsByTagName("quer:GetCustomerBody").getLength() > 0) {
			ResponseBody responseBody = new ResponseBody();
			if (document.getElementsByTagName("quer:ServiceNumber").getLength() > 0) {
				String serviceNumber = document.getElementsByTagName("quer:ServiceNumber").item(0).getTextContent();
				responseBody.setServiceNumber(serviceNumber);
			}
			responsetInfo.setResponseBody(responseBody);
		}
		return responsetInfo;
	}
	public final static String converDateFormat(String dateTime, String format) {
		if (StringUtils.isEmpty(format)) {
			format = UnieapConstants.TIMEFORMAT;
		}
		Date data = new Date(dateTime);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String dt = sdf.format(data);
		return dt;
	}
}
