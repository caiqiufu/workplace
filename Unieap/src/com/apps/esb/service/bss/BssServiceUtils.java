package com.apps.esb.service.bss;

import java.io.StringWriter;

import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import com.apps.esb.pojo.Esblog;
import com.apps.esb.service.bss.element.RequestBody;
import com.apps.esb.service.bss.element.RequestHeader;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponseBody;
import com.apps.esb.service.bss.element.ResponsetHeader;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.unieap.UnieapConstants;

public class BssServiceUtils {
	public static RequestInfo getRequestInfo(String requestInfoString) throws Exception{
		RequestInfo requestInfo = new RequestInfo();
		RequestHeader header = new RequestHeader();
		RequestBody body = new RequestBody();
		JSONObject jsonResult = new JSONObject(requestInfoString);
		if(jsonResult.has("RequestHeader")){
			JSONObject requestHeader = (JSONObject)jsonResult.get("RequestHeader");
			if(requestHeader.has("accessPwd")){
				String accessPwd = requestHeader.getString("accessPwd");
				header.setAccessPwd(accessPwd);
			}else{
				throw new Exception("missed element:RequestHeader->accessPwd");
			}
			if(requestHeader.has("accessUser")){
				String accessUser = requestHeader.getString("accessUser");
				header.setAccessUser(accessUser);
			}else{
				throw new Exception("missed element:RequestHeader->accessUser");
			}
			if(requestHeader.has("bizCode")){
				String bizCode = requestHeader.getString("bizCode");
				header.setBizCode(bizCode);
			}else{
				throw new Exception("missed element:RequestHeader->bizCode");
			}
			if(requestHeader.has("channelCode")){
				String channelCode = requestHeader.getString("channelCode");
				header.setChannelCode(channelCode);
			}else{
				throw new Exception("missed element:RequestHeader->channelCode");
			}
			if(requestHeader.has("extTransactionId")){
				String extTransactionId = requestHeader.getString("extTransactionId");
				header.setExtTransactionId(extTransactionId);
			}
			if(requestHeader.has("requestTime")){
				String requestTime = requestHeader.getString("requestTime");
				header.setRequestTime(requestTime);
			}else{
				throw new Exception("missed element:RequestHeader->requestTime");
			}
			if(requestHeader.has("systemCode")){
				String systemCode = requestHeader.getString("systemCode");
				header.setSystemCode(systemCode);
			}else{
				throw new Exception("missed element:RequestHeader->systemCode");
			}
			requestInfo.setRequestHeader(header);
		}else{
			throw new Exception("missed element:RequestHeader");
		}
		if(jsonResult.has("RequestBody")){
			JSONObject requestBody = (JSONObject)jsonResult.get("RequestBody");
			if(requestBody.has("serviceNumber")){
				String serviceNumber = requestBody.getString("serviceNumber");
				body.setServiceNumber(serviceNumber);
			}else{
				throw new Exception("missed element:RequestBody->serviceNumber");
			}
			if(requestBody.has("extParameters")){
				String extParameters = requestBody.getString("extParameters");
				body.setExtParameters(extParameters);
			}
		}else{
			throw new Exception("missed element:RequestBody");
		}
		requestInfo.setRequestBody(body);
		return requestInfo;
	}
	public static String getResposeInfoString(ResponsetInfo responseInfo) throws Exception{
		JSONObject jsonResult = new JSONObject();
		ResponsetHeader header = responseInfo.getResponsetHeader();
		ResponseBody body = responseInfo.getResponseBody();
		JSONObject headerJsonResult = new JSONObject();
		headerJsonResult.put("accessPwd",header.getAccessPwd());
		headerJsonResult.put("accessUser",header.getAccessUser());
		headerJsonResult.put("bizCode",header.getBizCode());
		headerJsonResult.put("channelCode",header.getChannelCode());
		headerJsonResult.put("extTransactionId",header.getExtTransactionId());
		headerJsonResult.put("transactionId",header.getTransactionId());
		headerJsonResult.put("requestTime",header.getRequestTime());
		headerJsonResult.put("responseTime",header.getResponseTime());
		headerJsonResult.put("systemCode",header.getSystemCode());
		headerJsonResult.put("version",header.getVersion());
		JSONObject resultJsonResult = new JSONObject();
		resultJsonResult.put("resultCode", header.getResultCode());
		resultJsonResult.put("resultDesc", header.getResultDesc());
		headerJsonResult.put("result", resultJsonResult);
		jsonResult.put("ResponseHeader", headerJsonResult);
		JSONObject bodyJsonResult = new JSONObject();
		bodyJsonResult.put("serviceNumber", body.getServiceNumber());
		bodyJsonResult.put("extParameters", body.getExtParameters());
		jsonResult.put("ResponseBody", bodyJsonResult);
		return jsonResult.toString();
	}
	public static Esblog getEsbLog(RequestInfo requestInfo, String requestInfoString, ResponsetInfo responsetInfo,
			String responsetInfoString,String during,String destSystem) {
		Esblog esblog = new Esblog();
		esblog.setLogId(UnieapConstants.getSequence(null, UnieapConstants.ESB));
		esblog.setChannelCode(requestInfo.getRequestHeader().getChannelCode());
		esblog.setBizCode(requestInfo.getRequestHeader().getBizCode());
		esblog.setServiceNumber(requestInfo.getRequestBody().getServiceNumber());
		esblog.setTransactionId(requestInfo.getRequestHeader().getTransactionId());
		esblog.setRequestTime(requestInfo.getRequestHeader().getRequestTime());
		esblog.setResponseTime(responsetInfo.getResponsetHeader().getResponseTime());
		esblog.setSystemCode(requestInfo.getRequestHeader().getSystemCode());
		esblog.setExtTransactionId(requestInfo.getRequestHeader().getExtTransactionId());
		esblog.setResultCode(responsetInfo.getResponsetHeader().getResultCode());
		esblog.setResultDesc(responsetInfo.getResponsetHeader().getResultDesc());
		esblog.setRequestInfo(requestInfoString.getBytes());
		esblog.setResponseInfo(responsetInfoString.getBytes());
		esblog.setCreateDate(UnieapConstants.getDateTime(null));
		esblog.setExecuteTime(during);
		esblog.setSourceSystem(requestInfo.getRequestHeader().getSystemCode());
		esblog.setDestSystem(destSystem);
		return esblog;
	}
	public static String getSoapMessageString(SOAPMessage message) throws Exception{
		if(message==null){
			return "";
		}
		Transformer trans = TransformerFactory.newInstance().newTransformer();
		StringWriter sw = new StringWriter();
		trans.transform(new DOMSource(message.getSOAPPart().getEnvelope()), new StreamResult(sw));
	    sw.flush();
	    sw.close();
	    return sw.toString();
	}
	public static String generateTransactionId() {
		StringBuffer transactionId = new StringBuffer();
		String time = UnieapConstants.getCurrentTime(null, UnieapConstants.TIMEFORMAT2);
		long code = Math.round(Math.random() * 10000000);
		String rundNumber = StringUtils.substring(Long.toString(code), 1);
		transactionId.append(time).append(rundNumber);
		return transactionId.toString();
	}
}
