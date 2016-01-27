package com.apps.esb.service.bss.interfacecall;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Map;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;

import org.springframework.util.StringUtils;

import com.apps.esb.pojo.Esblog;
import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.element.RequestHeader;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;

public class SoapCallUtils extends CallUtils {
	private static MessageFactory messageFactory;
	private static SOAPConnectionFactory connectionFactory;

	private static void factory() throws Exception {
		if (messageFactory == null) {
			messageFactory = MessageFactory.newInstance();
			connectionFactory = SOAPConnectionFactory.newInstance();
		}
	}
	public static MessageFactory getMessageFactory() throws Exception{
		factory();
		return messageFactory;
		
	}
	public static SOAPConnectionFactory getConnectionFactory() throws Exception{
		factory();
		return connectionFactory; 
	}
	
	public static ProcessResult process(Object obj, RequestInfo requestInfo, String serviceNumber, String extParameters,
			Map<String, String> handler, String timeoutName) throws Exception {
		long beginTime = System.currentTimeMillis();
		BizHandler bizHandler = (BizHandler) obj;
		SOAPMessage request = bizHandler.getRequestSOAPMessage(serviceNumber, requestInfo);
		SOAPMessage response = callService(serviceNumber, requestInfo, request, beginTime, timeoutName);
		ProcessResult processResult = bizHandler.getResposeResult(response);
		processResult.setServiceNumber(serviceNumber);
		String responseTime = UnieapConstants.getCurrentTime(null, null);
		requestInfo.getRequestHeader().setResponseTime(responseTime);
		String bizCode = requestInfo.getRequestHeader().getBizCode();
		String systemName = getSystemName(bizCode);
		long endTime = System.currentTimeMillis();
		String during = "" + (endTime - beginTime);
		esbLog(request, response, requestInfo.getRequestHeader(), processResult, during, systemName);
		return processResult;
	}

	public static SOAPMessage callWebService(String url, SOAPMessage request, String timeoutConfig) throws Exception {
		int timeout = 10000;
		if (!StringUtils.isEmpty(timeoutConfig) && SYSConfig.getConfig().get(timeoutConfig) != null) {
			timeout = Integer.parseInt(SYSConfig.getConfig().get(timeoutConfig));
		}
		String isdebug = SYSConfig.getConfig().get("mcare.app.extaction.debug");
		if (UnieapConstants.YES.equals(isdebug)) {
			System.setProperty("java.net.useSystemProxies", "true");
		}
		SOAPConnection connection = connectionFactory.createConnection();
		SOAPMessage response = connection.call(request, getURL(url, timeout));
		return response;
	}

	public static URL getURL(String url, final int timeout) throws Exception {
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

	public static SOAPMessage callService(String serviceNumber, RequestInfo requestInfo, SOAPMessage request,
			long beginTime, String timeoutName) throws Exception {
		String requestTime = UnieapConstants.getCurrentTime(null, null);
		RequestHeader requestHeader = requestInfo.getRequestHeader();
		requestHeader.setRequestTime(requestTime);
		requestHeader.setSystemCode("esb");
		String bizCode = requestHeader.getBizCode();
		String url = getInfUrl(bizCode);
		String systemName = getSystemName(bizCode);
		SOAPMessage response;
		try {
			response = callWebService(url, request, timeoutName);
		} catch (Exception e) {
			ProcessResult processResult = new ProcessResult();
			processResult.setResultCode(UnieapConstants.C99999);
			processResult.setResultDesc(e.getLocalizedMessage());
			String requestInfoString = BssServiceUtils.getSoapMessageString(request);
			long endTime = System.currentTimeMillis();
			String during = "" + (endTime - beginTime);
			Esblog esblog = BssServiceUtils.getEsbLog(requestHeader, processResult, requestInfoString, "", during,
					systemName);
			CacheMgt.addEsblog(esblog);
			throw e;
		}
		return response;

	}

	public static String getInfUrl(String bizCode) throws Exception {
		Map<String, String> infInfo = SYSConfig.getEsbInfo().get(bizCode);
		if (infInfo == null) {
			throw new Exception("bizCode[" + bizCode + "] not exist.");
		}
		return infInfo.get("url");
	}

	public static String getSystemName(String bizCode) throws Exception {
		Map<String, String> infInfo = SYSConfig.getEsbInfo().get(bizCode);
		if (infInfo == null) {
			throw new Exception("bizCode[" + bizCode + "] not exist.");
		}
		return infInfo.get("systemName");
	}

	public static String getInftName(String bizCode) throws Exception {
		Map<String, String> infInfo = SYSConfig.getEsbInfo().get(bizCode);
		if (infInfo == null) {
			throw new Exception("bizCode[" + bizCode + "] not exist.");
		}
		return infInfo.get("infName");
	}
}
