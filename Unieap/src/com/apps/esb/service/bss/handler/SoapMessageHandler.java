package com.apps.esb.service.bss.handler;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
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

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.element.RequestBody;
import com.apps.esb.service.bss.element.RequestHeader;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponseBody;
import com.apps.esb.service.bss.element.ResponsetHeader;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.unieap.CacheMgt;
import com.unieap.base.SYSConfig;

public class SoapMessageHandler {
	public MessageFactory messageFactory;
	public SOAPConnectionFactory connectionFactory;

	public SoapMessageHandler() throws Exception {
		messageFactory = MessageFactory.newInstance();
		connectionFactory = SOAPConnectionFactory.newInstance();
	}

	public void getCRMQueryHeader(String method, SOAPMessage message, RequestHeader requestHeader) throws Exception {
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		envelope.addNamespaceDeclaration("quer", "http://crm.huawei.com/query/");
		envelope.addNamespaceDeclaration("bas", "http://crm.huawei.com/basetype/");
		SOAPBody body = envelope.getBody();
		SOAPElement bodyElement = body.addChildElement(method, "quer");
		SOAPElement requestHeaderElement = bodyElement.addChildElement("RequestHeader", "quer");
		requestHeaderElement.addChildElement("TransactionId", "bas").addTextNode(requestHeader.getTransactionId());
		requestHeaderElement.addChildElement("ChannelId", "bas").addTextNode(requestHeader.getChannelCode());
		requestHeaderElement.addChildElement("TechnicalChannelId", "bas").addTextNode(requestHeader.getSystemCode());
		requestHeaderElement.addChildElement("AccessUser", "bas").addTextNode(requestHeader.getAccessUser());
		requestHeaderElement.addChildElement("AccessPwd", "bas").addTextNode(requestHeader.getAccessPwd());
	}

	public RequestHeader getCRMRequestHeader(RequestInfo requestInfo) throws Exception {
		RequestHeader header = new RequestHeader();
		header.setAccessUser("ipcc");
		header.setAccessPwd("r8q0a5WwGNboj9I35XzNcQ==");
		header.setChannelCode("18");
		header.setSystemCode("53");
		header.setTransactionId(BssServiceUtils.generateTransactionId());
		return header;
	}

	public SOAPMessage callWebService(String url,SOAPMessage request,String timeoutConfig) throws Exception {
		int timeout = 10000;
		if(!StringUtils.isEmpty(timeoutConfig)&&SYSConfig.getConfig().get(timeoutConfig)!=null){
			timeout = Integer.parseInt(SYSConfig.getConfig().get(timeoutConfig));
		}
		SOAPConnection connection = connectionFactory.createConnection();
		SOAPMessage response = connection.call(request, getURL(url,timeout));
		return response;
	}
	
	
	public URL getURL(String url,final int timeout) throws Exception{
		URL urlval = new URL(new URL(url), "",
                new URLStreamHandler(){
            @Override
            protected URLConnection openConnection(URL url) throws IOException {
              URL target = new URL(url.toString());
              URLConnection connection = target.openConnection();
              // Connection settings
              connection.setConnectTimeout(timeout);
              connection.setReadTimeout(timeout);
              return(connection);
            }
          });
		return urlval;
	}
	
	public String getUrl(String bizCode) throws Exception{
		Map<String,String> infInfo = SYSConfig.getInfInfo().get(bizCode);
		if(infInfo==null){
			throw new Exception("bizCode["+bizCode+"] not exist.");
		}
		return infInfo.get("url");
	}
	public String getSystemName(String bizCode) throws Exception{
		Map<String,String> infInfo = SYSConfig.getInfInfo().get(bizCode);
		if(infInfo==null){
			throw new Exception("bizCode["+bizCode+"] not exist.");
		}
		return infInfo.get("systemName");
	}
	public void esbLog(Object bizObj,SOAPMessage request,SOAPMessage response,RequestInfo requestInfo,String destSystem,String during){
		 RequestHeader requestHeader = requestInfo.getRequestHeader();
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("bizObj", bizObj);
		data.put("request", request);
		data.put("response", response);
		data.put("during", during);
		data.put("requestInfo", requestInfo);
		
		
		data.put("destSystem",destSystem);
		data.put("bizCode",requestHeader.getBizCode());
		data.put("requestTime", requestHeader.getRequestTime());
		data.put("responseTime",requestHeader.getResponseTime());
		CacheMgt.addEsbSOAPlog(data);
	}
	
	public RequestInfo getRequestInfoFromSOAPMessage(SOAPMessage request) throws Exception {
		if(request == null){
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
				String extTransactionId = document.getElementsByTagName("bas:ExtTransactionId").item(0).getTextContent();
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
		if (document.getElementsByTagName("quer:GetCustomerBody").getLength() > 0){
			RequestBody requestBody = new RequestBody();
			if (document.getElementsByTagName("quer:ServiceNumber").getLength() > 0) {
				String serviceNumber = document.getElementsByTagName("quer:ServiceNumber").item(0).getTextContent();
				requestBody.setServiceNumber(serviceNumber);
			}
			requestInfo.setRequestBody(requestBody);
		}
		return requestInfo;
	}

	public ResponsetInfo getResponseInfoFromSOAPMessage(SOAPMessage response) throws Exception {
		if(response == null){
			return new ResponsetInfo();
		}
		ResponsetInfo responsetInfo = new ResponsetInfo();
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("quer:ResponseHeader").getLength() > 0){
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
			if (document.getElementsByTagName("bas:TechnicalChannelId").getLength() > 0) {
				String systemCode = document.getElementsByTagName("bas:TechnicalChannelId").item(0).getTextContent();
				responsetHeader.setSystemCode(systemCode);
			}
			if (document.getElementsByTagName("bas:AccessUser").getLength() > 0) {
				String accessUser = document.getElementsByTagName("bas:AccessUser").item(0).getTextContent();
				responsetHeader.setAccessUser(accessUser);
			}
			responsetInfo.setResponsetHeader(responsetHeader);
		}
		if (document.getElementsByTagName("quer:GetCustomerBody").getLength() > 0){
			ResponseBody responseBody = new ResponseBody();
			if (document.getElementsByTagName("quer:ServiceNumber").getLength() > 0) {
				String serviceNumber = document.getElementsByTagName("quer:ServiceNumber").item(0).getTextContent();
				responseBody.setServiceNumber(serviceNumber);
			}
			responsetInfo.setResponseBody(responseBody);
		}
		return responsetInfo;
		
	}
}
