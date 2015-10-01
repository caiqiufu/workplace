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
import com.apps.esb.service.bss.handler.ProcessResult;
import com.unieap.UnieapConstants;

public class BssServiceUtils {
	public static RequestInfo getRequestInfo(String requestInfoString) throws Exception{
		RequestInfo requestInfo = new RequestInfo();
		RequestHeader header = new RequestHeader();
		RequestBody body = new RequestBody();
		JSONObject jsonResult = new JSONObject(requestInfoString);
		if(jsonResult.has("requestHeader")){
			JSONObject requestHeader = (JSONObject)jsonResult.get("requestHeader");
			if(requestHeader.has("accessPwd")){
				String accessPwd = requestHeader.getString("accessPwd");
				header.setAccessPwd(accessPwd);
			}else{
				header.setAccessPwd(UnieapConstants.UNIEAP);
			}
			if(requestHeader.has("accessUser")){
				String accessUser = requestHeader.getString("accessUser");
				header.setAccessUser(accessUser);
			}else{
				header.setAccessUser(UnieapConstants.UNIEAP);
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
				header.setChannelCode("eCare");
			}
			if(requestHeader.has("extTransactionId")){
				String extTransactionId = requestHeader.getString("extTransactionId");
				header.setExtTransactionId(extTransactionId);
			}
			if(requestHeader.has("requestTime")){
				String requestTime = requestHeader.getString("requestTime");
				header.setRequestTime(requestTime);
			}else{
				String requestTime = UnieapConstants.getCurrentTime(null, null);
				header.setRequestTime(requestTime);
			}
			if(requestHeader.has("systemCode")){
				String systemCode = requestHeader.getString("systemCode");
				header.setSystemCode(systemCode);
			}else{
				header.setSystemCode("APP");
			}
			requestInfo.setRequestHeader(header);
		}else{
			throw new Exception("missed element:requestHeader");
		}
		if(jsonResult.has("requestBody")){
			JSONObject requestBody = (JSONObject)jsonResult.get("requestBody");
			if(requestBody.has("serviceNumber")){
				String serviceNumber = requestBody.getString("serviceNumber");
				body.setServiceNumber(serviceNumber);
			}
			if(requestBody.has("extParameters")){
				String extParameters = requestBody.getString("extParameters");
				body.setExtParameters(extParameters);
			}
		}
		requestInfo.setRequestBody(body);
		return requestInfo;
	}
	public static String getResposeInfoString(ResponsetInfo responseInfo) throws Exception{
		JSONObject jsonResult = new JSONObject();
		ResponsetHeader header = responseInfo.getResponsetHeader();
		ResponseBody body = responseInfo.getResponseBody();
		JSONObject headerJsonResult = new JSONObject();
		headerJsonResult.put("bizCode",header.getBizCode());
		headerJsonResult.put("channelCode",header.getChannelCode());
		headerJsonResult.put("extTransactionId",header.getExtTransactionId());
		headerJsonResult.put("transactionId",header.getTransactionId());
		headerJsonResult.put("requestTime",header.getRequestTime());
		headerJsonResult.put("responseTime",header.getResponseTime());
		headerJsonResult.put("resultCode", header.getResultCode());
		headerJsonResult.put("resultDesc", header.getResultDesc());
		jsonResult.put("responseHeader", headerJsonResult);
		JSONObject bodyJsonResult = new JSONObject();
		bodyJsonResult.put("serviceNumber", body.getServiceNumber());
		if(!StringUtils.isEmpty(body.getExtParameters())){
			JSONObject extParametersJson = new JSONObject(body.getExtParameters());
			bodyJsonResult.put("extParameters",extParametersJson);
		}
		jsonResult.put("responseBody", bodyJsonResult);
		return jsonResult.toString();
	}
	public static Esblog getEsbLog(RequestHeader requestHeader,ProcessResult processResult,String requestInfoString, 
			String responsetInfoString,String during,String destSystem) {
		Esblog esblog = new Esblog();
		esblog.setLogId(UnieapConstants.getSequence(null, UnieapConstants.ESB));
		esblog.setChannelCode(requestHeader.getChannelCode());
		esblog.setBizCode(requestHeader.getBizCode());
		esblog.setServiceNumber(processResult.getServiceNumber());
		esblog.setTransactionId(requestHeader.getTransactionId());
		esblog.setRequestTime(requestHeader.getRequestTime());
		esblog.setResponseTime(requestHeader.getResponseTime());
		esblog.setSystemCode(requestHeader.getSystemCode());
		esblog.setExtTransactionId(requestHeader.getExtTransactionId());
		esblog.setResultCode(processResult.getResultCode());
		esblog.setResultDesc(StringUtils.substring(processResult.getResultDesc(),0, 1020));
		esblog.setRequestInfo(requestInfoString.getBytes());
		esblog.setResponseInfo(responsetInfoString.getBytes());
		esblog.setCreateDate(UnieapConstants.getDateTime(null));
		esblog.setExecuteTime(during);
		esblog.setSourceSystem(requestHeader.getSystemCode());
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
		double code = Math.random();
		String strCode = Double.toString(code);
		String rundNumber = StringUtils.substring(strCode,strCode.length()-6);
		transactionId.append(time).append(rundNumber);
		return transactionId.toString();
	}
	
	public static ResponsetInfo getResponsetInfo(RequestInfo requestInfo, ProcessResult processResult) {
		ResponsetInfo responsetInfo = new ResponsetInfo();
		ResponsetHeader responsetHeader = new ResponsetHeader();
		responsetHeader.setBizCode(requestInfo.getRequestHeader().getBizCode());
		responsetHeader.setChannelCode(requestInfo.getRequestHeader().getChannelCode());
		responsetHeader.setExtTransactionId(requestInfo.getRequestHeader().getExtTransactionId());
		// store log id
		responsetHeader.setTransactionId(requestInfo.getRequestHeader().getTransactionId());
		responsetHeader.setRequestTime(requestInfo.getRequestHeader().getRequestTime());
		responsetHeader.setResponseTime(UnieapConstants.getCurrentTime(null, null));
		responsetHeader.setResultCode(processResult.getResultCode());
		responsetHeader.setResultDesc(processResult.getResultDesc());
		responsetInfo.setResponsetHeader(responsetHeader);
		ResponseBody responseBody = new ResponseBody();
		responseBody.setServiceNumber(processResult.getServiceNumber());
		responseBody.setExtParameters(processResult.getExtParameters());
		responsetInfo.setResponseBody(responseBody);
		return responsetInfo;
	}
	public static RequestInfo  copyRequestInfo(RequestInfo oldRequestInfo){
		RequestInfo requestInfo = new RequestInfo();
		RequestHeader requestHeader = new RequestHeader();
		RequestHeader oldRequestHeader = oldRequestInfo.getRequestHeader();
		requestHeader.setAccessPwd(oldRequestHeader.getAccessPwd());
		requestHeader.setAccessUser(oldRequestHeader.getAccessUser());
		requestHeader.setBizCode(oldRequestHeader.getBizCode());
		requestHeader.setChannelCode(oldRequestHeader.getChannelCode());
		requestHeader.setExtTransactionId(oldRequestHeader.getExtTransactionId());
		requestHeader.setRequestTime(oldRequestHeader.getRequestTime());
		requestHeader.setResponseTime(oldRequestHeader.getResponseTime());
		requestHeader.setSystemCode(oldRequestHeader.getSystemCode());
		requestHeader.setTransactionId(oldRequestHeader.getTransactionId());
		requestHeader.setVersion(oldRequestHeader.getVersion());
		RequestBody oldRequestBody = oldRequestInfo.getRequestBody();
		requestInfo.setRequestHeader(requestHeader);
		RequestBody requestBody = new RequestBody();
		requestBody.setServiceNumber(oldRequestBody.getServiceNumber());
		requestBody.setExtParameters(oldRequestBody.getExtParameters());
		requestInfo.setRequestBody(requestBody);
		return requestInfo;
	}
}
