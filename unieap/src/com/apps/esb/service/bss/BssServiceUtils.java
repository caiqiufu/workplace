package com.apps.esb.service.bss;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.w3c.dom.Document;

import com.apps.esb.pojo.Esblog;
import com.apps.esb.pojo.EsblogDevice;
import com.apps.esb.service.bss.element.DeviceInfo;
import com.apps.esb.service.bss.element.RequestBody;
import com.apps.esb.service.bss.element.RequestHeader;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponseBody;
import com.apps.esb.service.bss.element.ResponsetHeader;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;

public class BssServiceUtils {
	public static RequestInfo getRequestInfo(String requestInfoString) throws Exception {
		RequestInfo requestInfo = new RequestInfo();
		RequestHeader header = new RequestHeader();
		RequestBody body = new RequestBody();
		JSONObject jsonResult = new JSONObject(requestInfoString);
		if (jsonResult.has("requestHeader")) {
			JSONObject requestHeader = (JSONObject) jsonResult.get("requestHeader");
			if (requestHeader.has("accessPwd")) {
				String accessPwd = requestHeader.getString("accessPwd");
				if (StringUtils.isEmpty(accessPwd)) {
					throw new Exception("accessPwd is null");
				} else {
					header.setAccessPwd(accessPwd);
				}
			} else {
				// header.setAccessPwd(UnieapConstants.UNIEAP);
				throw new Exception("missed element:RequestHeader->accessPwd");
			}
			if (requestHeader.has("accessUser")) {
				String accessUser = requestHeader.getString("accessUser");
				if (StringUtils.isEmpty(accessUser)) {
					throw new Exception("accessUser is null");
				} else {
					header.setAccessUser(accessUser);
				}
			} else {
				// header.setAccessUser(UnieapConstants.UNIEAP);
				throw new Exception("missed element:RequestHeader->accessUser");
			}
			if (requestHeader.has("bizCode")) {
				String bizCode = requestHeader.getString("bizCode");
				header.setBizCode(bizCode);
			} else {
				throw new Exception("missed element:RequestHeader->bizCode");
			}
			if (requestHeader.has("channelCode")) {
				String channelCode = requestHeader.getString("channelCode");
				header.setChannelCode(channelCode);
			} else {
				header.setChannelCode("eCare");
			}
			if (requestHeader.has("extTransactionId")) {
				String extTransactionId = requestHeader.getString("extTransactionId");
				header.setExtTransactionId(extTransactionId);
			}
			if (requestHeader.has("requestTime")) {
				String requestTime = requestHeader.getString("requestTime");
				header.setRequestTime(requestTime);
			} else {
				String requestTime = UnieapConstants.getCurrentTime(null, null);
				header.setRequestTime(requestTime);
			}
			if (requestHeader.has("systemCode")) {
				String systemCode = requestHeader.getString("systemCode");
				header.setSystemCode(systemCode);
			} else {
				header.setSystemCode("APP");
			}
			if (requestHeader.has("deviceInfo")) {
				DeviceInfo deviceInfo = new DeviceInfo();
				header.setDeviceInfo(deviceInfo);
				JSONObject deviceInfoJson = (JSONObject) requestHeader.get("deviceInfo");
				if (deviceInfoJson.has("IMEI")) {
					deviceInfo.setIMEI(deviceInfoJson.getString("IMEI"));
				}
				if (deviceInfoJson.has("OSType")) {
					deviceInfo.setOSType(deviceInfoJson.getString("OSType"));
				}
				if (deviceInfoJson.has("OSVersion")) {
					deviceInfo.setOSVersion(deviceInfoJson.getString("OSVersion"));
				}
				if (deviceInfoJson.has("APKVersion")) {
					deviceInfo.setAPKVersion(deviceInfoJson.getString("APKVersion"));
				}
				if (deviceInfoJson.has("networkType")) {
					deviceInfo.setNetworkType(deviceInfoJson.getString("networkType"));
				}
				if (deviceInfoJson.has("resolution")) {
					deviceInfo.setResolution(deviceInfoJson.getString("resolution"));
				}
				if (deviceInfoJson.has("brand")) {
					deviceInfo.setBrand(deviceInfoJson.getString("brand"));
				}
				if (deviceInfoJson.has("model")) {
					deviceInfo.setModel(deviceInfoJson.getString("model"));
				}
				if (deviceInfoJson.has("extParameters")) {
					deviceInfo.setExtParameters(deviceInfoJson.getString("extParameters"));
				}
			}
			requestInfo.setRequestHeader(header);
		} else {
			throw new Exception("missed element:requestHeader");
		}
		if (jsonResult.has("requestBody")) {
			JSONObject requestBody = (JSONObject) jsonResult.get("requestBody");
			if (requestBody.has("serviceNumber")) {
				String serviceNumber = requestBody.getString("serviceNumber");
				body.setServiceNumber(serviceNumber);
			}
			if (requestBody.has("extParameters")) {
				String extParameters = requestBody.getString("extParameters");
				body.setExtParameters(extParameters);
			}
		}
		requestInfo.setRequestBody(body);
		return requestInfo;
	}

	public static String checkServiceNumber(String serviceNumber) {
		if (StringUtils.isNotEmpty(serviceNumber)) {
			String prefix = StringUtils.substring(serviceNumber, 0, 1);
			if ("0".equals(prefix)) {
				return StringUtils.substring(serviceNumber, 1);
			} else {
				return serviceNumber;
			}
		} else {
			return serviceNumber;
		}
	}

	public static String getResposeInfoString(ResponsetInfo responseInfo) throws Exception {
		JSONObject jsonResult = new JSONObject();
		ResponsetHeader header = responseInfo.getResponsetHeader();
		/*
		 * if(!UnieapConstants.C0.equals(header.getResultCode())){ String
		 * resultDesc = UnieapConstants.getMessage("99998");
		 * header.setResultDesc(resultDesc); }
		 */
		ResponseBody body = responseInfo.getResponseBody();
		JSONObject headerJsonResult = new JSONObject();
		headerJsonResult.put("bizCode", header.getBizCode());
		headerJsonResult.put("channelCode", header.getChannelCode());
		headerJsonResult.put("extTransactionId", header.getExtTransactionId());
		headerJsonResult.put("transactionId", header.getTransactionId());
		headerJsonResult.put("requestTime", header.getRequestTime());
		headerJsonResult.put("responseTime", header.getResponseTime());
		headerJsonResult.put("resultCode", header.getResultCode());
		headerJsonResult.put("resultDesc", header.getResultDesc());
		jsonResult.put("responseHeader", headerJsonResult);
		JSONObject bodyJsonResult = new JSONObject();
		bodyJsonResult.put("serviceNumber", body.getServiceNumber());
		if (!StringUtils.isEmpty(body.getExtParameters())) {
			JSONObject extParametersJson = new JSONObject(body.getExtParameters());
			bodyJsonResult.put("extParameters", extParametersJson);
		}
		jsonResult.put("responseBody", bodyJsonResult);
		return jsonResult.toString();
	}

	public static Esblog getEsbLog(RequestHeader requestHeader, ProcessResult processResult, String requestInfoString,
			String responsetInfoString, String during, String destSystem) {
		Esblog esblog = new Esblog();
		esblog.setLogId(UnieapConstants.getSequence(null, "esb"));
		esblog.setChannelCode(requestHeader.getChannelCode());
		esblog.setBizCode(requestHeader.getBizCode());
		esblog.setServiceNumber(processResult.getServiceNumber());
		esblog.setTransactionId(requestHeader.getTransactionId());
		esblog.setRequestTime(requestHeader.getRequestTime());
		esblog.setResponseTime(requestHeader.getResponseTime());
		esblog.setSystemCode(requestHeader.getSystemCode());
		esblog.setExtTransactionId(requestHeader.getExtTransactionId());
		esblog.setResultCode(processResult.getResultCode());
		esblog.setResultDesc(StringUtils.substring(processResult.getResultDesc(), 0, 1020));
		esblog.setRequestInfo(requestInfoString.getBytes());
		esblog.setResponseInfo(responsetInfoString.getBytes());
		esblog.setCreateDate(UnieapConstants.getDateTime(null));
		esblog.setExecuteTime(during);
		esblog.setSourceSystem(requestHeader.getSystemCode());
		esblog.setDestSystem(destSystem);
		return esblog;
	}

	public static EsblogDevice getEsbLogDevice(RequestHeader requestHeader, Esblog esblog) {
		EsblogDevice esblogDevice = new EsblogDevice();
		esblogDevice.setApkversion(requestHeader.getDeviceInfo().getAPKVersion());
		esblogDevice.setBrand(requestHeader.getDeviceInfo().getBrand());
		esblogDevice.setImei(requestHeader.getDeviceInfo().getIMEI());
		esblogDevice.setModel(requestHeader.getDeviceInfo().getModel());
		esblogDevice.setNetworkType(requestHeader.getDeviceInfo().getNetworkType());
		esblogDevice.setOstype(requestHeader.getDeviceInfo().getOSType());
		esblogDevice.setOsversion(requestHeader.getDeviceInfo().getOSVersion());
		esblogDevice.setResolution(requestHeader.getDeviceInfo().getResolution());
		esblogDevice.setId(UnieapConstants.getSequence(null, "esb"));
		esblogDevice.setLogId(esblog.getLogId());
		esblogDevice.setCreateDate(esblog.getCreateDate());
		esblogDevice.setServiceNumber(esblog.getServiceNumber());
		return esblogDevice;
	}

	public static String getSoapMessageString(SOAPMessage message) throws Exception {
		if (message == null) {
			return "";
		}
		/*
		 * Transformer trans =
		 * TransformerFactory.newInstance().newTransformer(); StringWriter sw =
		 * new StringWriter(); trans.transform(new
		 * DOMSource(message.getSOAPPart().getEnvelope()), new
		 * StreamResult(sw)); sw.flush(); sw.close(); return sw.toString();
		 */

		TransformerFactory tff = TransformerFactory.newInstance();
		Transformer tf = tff.newTransformer();
		Source source = message.getSOAPPart().getContent();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(100);
		StreamResult result = new StreamResult(bos);
		tf.transform(source, result);
		return new String(bos.toByteArray());
	}

	public static org.w3c.dom.Document getSoapMessageDocument(SOAPMessage message) throws Exception {
		if (message == null) {
			return null;
		}
		String soapString = getSoapMessageString(message);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
		Document document = documentBuilder.parse(new org.xml.sax.InputSource(new StringReader(soapString)));
		return document;

	}

	public static String generateTransactionId() {
		StringBuffer transactionId = new StringBuffer();
		String time = UnieapConstants.getCurrentTime(null, UnieapConstants.TIMEFORMAT2);
		double code = Math.random();
		String strCode = Double.toString(code);
		String rundNumber = StringUtils.substring(strCode, strCode.length() - 6);
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

	public static RequestInfo copyRequestInfo(RequestInfo oldRequestInfo) {
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
		if (oldRequestHeader.getDeviceInfo() != null) {
			DeviceInfo deviceInfo = new DeviceInfo();
			requestHeader.setDeviceInfo(deviceInfo);
			deviceInfo.setAPKVersion(oldRequestHeader.getDeviceInfo().getAPKVersion());
			deviceInfo.setBrand(oldRequestHeader.getDeviceInfo().getBrand());
			deviceInfo.setExtParameters(oldRequestHeader.getDeviceInfo().getExtParameters());
			deviceInfo.setIMEI(oldRequestHeader.getDeviceInfo().getIMEI());
			deviceInfo.setModel(oldRequestHeader.getDeviceInfo().getModel());
			deviceInfo.setNetworkType(oldRequestHeader.getDeviceInfo().getNetworkType());
			deviceInfo.setOSType(oldRequestHeader.getDeviceInfo().getOSType());
			deviceInfo.setOSVersion(oldRequestHeader.getDeviceInfo().getOSVersion());
			deviceInfo.setResolution(oldRequestHeader.getDeviceInfo().getResolution());
		}
		RequestBody oldRequestBody = oldRequestInfo.getRequestBody();
		requestInfo.setRequestHeader(requestHeader);
		RequestBody requestBody = new RequestBody();
		requestBody.setServiceNumber(oldRequestBody.getServiceNumber());
		requestBody.setExtParameters(oldRequestBody.getExtParameters());
		requestInfo.setRequestBody(requestBody);
		return requestInfo;
	}

	public static String moneyFormat(String amount) {
		String moneyaccruacy = SYSConfig.getConfig().get("mcare.unit.format.moneyaccruacy");
		String moneylength = SYSConfig.getConfig().get("mcare.unit.format.moneylength");
		if (StringUtils.isEmpty(amount)) {
			return "";
		} else {
			double money = (new Double(amount).doubleValue()) / (new Double(moneyaccruacy).doubleValue());
			DecimalFormat df = new DecimalFormat(moneylength);
			return df.format(money);
			// return UnieapConstants.getMessage("mcare.unit.money", new
			// Object[] { df.format(money) });
		}
	}

	public static String dateFormat(String datetime) {
		if (StringUtils.isEmpty(datetime)) {
			return "";
		}
		String datetimeFormat = SYSConfig.getConfig().get("mcare.unit.format.datetime");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddhhmmss");
		Date strtodate;
		try {
			strtodate = sdf2.parse(datetime);
		} catch (ParseException e1) {
			return datetime;
		}
		SimpleDateFormat sdf3 = new SimpleDateFormat(datetimeFormat);
		return sdf3.format(strtodate);
	}

	public static String dataMBFormat(String data) {
		return data;
		// return UnieapConstants.getMessage("mcare.unit.datamb", new Object[] {
		// data });
	}

	public static String dataMBFormat(double data) {
		return Double.toString(data);
		// return UnieapConstants.getMessage("mcare.unit.datamb", new Object[] {
		// data });
	}

	public static String voiceFormat(String voice) {
		int amount = Integer.parseInt(voice) / 60;
		return Integer.toString(amount);
		// return UnieapConstants.getMessage("mcare.unit.voice", new Object[] {
		// amount });
	}

	public static String dataGBFormat(double data) {
		String datalength = SYSConfig.getConfig().get("mcare.unit.format.datalength");
		DecimalFormat df = new DecimalFormat(datalength);
		return df.format(data / 1024);
		// return UnieapConstants.getMessage("mcare.unit.datagb", new Object[]
		// {df.format( data / 1024)});
	}

	public static JSONObject modifyExtParameters(String extParameters, JSONObject parameters) throws Exception {
		if (StringUtils.isEmpty(extParameters)) {
			JSONObject json = new JSONObject();
			json.put("extParameters", parameters);
			return json;
		} else {
			JSONObject json = new JSONObject(extParameters);
			Iterator<?> keys = parameters.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				json.put(key, parameters.get(key));
			}
			return json;
		}
	}
}
