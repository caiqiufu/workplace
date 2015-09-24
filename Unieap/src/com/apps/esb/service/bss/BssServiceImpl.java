package com.apps.esb.service.bss;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import com.apps.esb.pojo.Esblog;
import com.apps.esb.service.bss.element.RequestBody;
import com.apps.esb.service.bss.element.RequestHeader;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponseBody;
import com.apps.esb.service.bss.element.ResponsetHeader;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.db.DBManager;

@WebService(serviceName = "BssService", endpointInterface = "com.apps.esb.service.bss.BssService")
public class BssServiceImpl implements BssService {
	@Override
	public String queryInfo(String requestInfoString) {
		long beginTime = System.currentTimeMillis();
		RequestInfo requestInfo = null;
		try {
			String transactionId = BssServiceUtils.generateTransactionId();
			try {
				requestInfo = BssServiceUtils.getRequestInfo(requestInfoString);
			} catch (Exception e) {
				requestInfo = new RequestInfo();
				RequestHeader requestHeader = new RequestHeader();
				requestHeader.setSystemCode(UnieapConstants.THIRDPART);
				RequestBody requestBody = new RequestBody();
				requestInfo.setRequestHeader(requestHeader);
				requestHeader.setTransactionId(transactionId);
				requestInfo.setRequestBody(requestBody);
				ResponsetInfo responsetInfo = new ResponsetInfo();
				ResponsetHeader responsetHeader = new ResponsetHeader();
				responsetHeader.setResultCode(BssErrorCode.C99999);
				responsetHeader.setResultDesc(e.getLocalizedMessage());
				responsetHeader.setTransactionId(transactionId);
				ResponseBody responseBody = new ResponseBody();
				responsetInfo.setResponsetHeader(responsetHeader);
				responsetInfo.setResponseBody(responseBody);
				String responsetInfoString = BssServiceUtils.getResposeInfoString(responsetInfo);
				long endTime = System.currentTimeMillis();
				String during = "" + (endTime - beginTime);
				esbLog(requestInfo, requestInfoString, responsetInfo, responsetInfoString, during, UnieapConstants.ESB);
				return responsetInfoString;
			}
			requestInfo.getRequestHeader().setTransactionId(transactionId);
			String bizCode = requestInfo.getRequestHeader().getBizCode();
			Map<String, String> handlerObj = SYSConfig.getBizHandler().get(bizCode);
			if (handlerObj == null) {
				String errorDesc = SYSConfig.getErrorCoreInfo(BssErrorCode.CRM_10001).get(UnieapConstants.ERRORDESC);
				ResponsetInfo responsetInfo = new ResponsetInfo();
				ResponsetHeader responsetHeader = new ResponsetHeader();
				responsetHeader.setResultCode(BssErrorCode.CRM_10001);
				responsetHeader.setResultDesc(errorDesc);
				responsetHeader.setTransactionId(transactionId);
				ResponseBody responseBody = new ResponseBody();
				responsetInfo.setResponsetHeader(responsetHeader);
				responsetInfo.setResponseBody(responseBody);
				String responsetInfoString = BssServiceUtils.getResposeInfoString(responsetInfo);
				long endTime = System.currentTimeMillis();
				String during = "" + (endTime - beginTime);
				esbLog(requestInfo, requestInfoString, responsetInfo, responsetInfoString, during, UnieapConstants.ESB);
				return responsetInfoString;
			}
			BizHandler handler = (BizHandler) ServiceUtils.getBean(handlerObj.get("className"));
			ProcessResult processResult;
			try {
				processResult = handler.process(copyRequestInfo(requestInfo), requestInfo.getRequestBody().getServiceNumber(),
						requestInfo.getRequestBody().getExtParameters(), handlerObj.get("parameters"));
			} catch (Exception e) {
				ResponsetInfo responsetInfo = new ResponsetInfo();
				ResponsetHeader responsetHeader = new ResponsetHeader();
				responsetHeader.setResultCode(BssErrorCode.C99999);
				responsetHeader.setResultDesc(e.getLocalizedMessage());
				responsetHeader.setTransactionId(transactionId);
				ResponseBody responseBody = new ResponseBody();
				responsetInfo.setResponsetHeader(responsetHeader);
				responsetInfo.setResponseBody(responseBody);
				String responsetInfoString = BssServiceUtils.getResposeInfoString(responsetInfo);
				long endTime = System.currentTimeMillis();
				String during = "" + (endTime - beginTime);
				esbLog(requestInfo, requestInfoString, responsetInfo, responsetInfoString, during, UnieapConstants.ESB);
				return responsetInfoString;
			}
			ResponsetInfo responsetInfo = getResponsetInfo(requestInfo, processResult);
			String responsetInfoString = BssServiceUtils.getResposeInfoString(responsetInfo);
			long endTime = System.currentTimeMillis();
			String during = "" + (endTime - beginTime);
			String responseTime = UnieapConstants.getCurrentTime(null, null);
			responsetInfo.getResponsetHeader().setResponseTime(responseTime);
			esbLog(requestInfo, requestInfoString, responsetInfo, responsetInfoString, during, UnieapConstants.ESB);
			return responsetInfoString;
		} catch (Exception e) {
			ResponsetInfo responsetInfo = new ResponsetInfo();
			ResponsetHeader responsetHeader = new ResponsetHeader();
			responsetHeader.setResultCode(BssErrorCode.C99999);
			responsetHeader.setResultDesc(e.getLocalizedMessage());
			ResponseBody responseBody = new ResponseBody();
			responsetInfo.setResponsetHeader(responsetHeader);
			responsetInfo.setResponseBody(responseBody);
			String responsetInfoString = "";
			try {
				responsetInfoString = BssServiceUtils.getResposeInfoString(responsetInfo);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			long endTime = System.currentTimeMillis();
			String during = "" + (endTime - beginTime);
			esbLog(requestInfo, requestInfoString, responsetInfo, responsetInfoString, during, UnieapConstants.ESB);
			return responsetInfoString;
		}
	}

	public ResponsetInfo getResponsetInfo(RequestInfo requestInfo, ProcessResult processResult) {
		ResponsetInfo responsetInfo = new ResponsetInfo();
		ResponsetHeader responsetHeader = new ResponsetHeader();
		responsetHeader.setAccessPwd(requestInfo.getRequestHeader().getAccessPwd());
		responsetHeader.setAccessUser(requestInfo.getRequestHeader().getAccessUser());
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

	public void esbLog(RequestInfo requestInfo, String requestInfoString, ResponsetInfo responsetInfo,
			String responsetInfoString, String during, String destSystem) {
		Esblog esblog;
		if (requestInfo == null) {
			esblog = new Esblog();
			esblog.setLogId(UnieapConstants.getSequence(null, UnieapConstants.ESB));
			esblog.setTransactionId(responsetInfo.getResponsetHeader().getTransactionId());
			esblog.setResponseTime(responsetInfo.getResponsetHeader().getResponseTime());
			esblog.setResultCode(responsetInfo.getResponsetHeader().getResultCode());
			esblog.setResultDesc(responsetInfo.getResponsetHeader().getResultDesc());
			esblog.setRequestInfo(requestInfoString.getBytes());
			esblog.setResponseInfo(responsetInfoString.getBytes());
			esblog.setCreateDate(UnieapConstants.getDateTime(null));
			esblog.setExecuteTime(during);
			esblog.setSourceSystem(destSystem);
			esblog.setDestSystem(destSystem);
		} else {

			esblog = BssServiceUtils.getEsbLog(requestInfo, requestInfoString, responsetInfo, responsetInfoString,
					during, destSystem);
		}
		setDatas(esblog);
	}

	public void setDatas(Esblog vo) {
		CacheMgt.addEsblog(vo);
		batchSaveMore100();
	}

	public void batchSaveMore100() {
		List<Esblog> datas = CacheMgt.getEsblogDatas();
		List<Esblog> copyDatas = new ArrayList<Esblog>();
		if (datas.size() > 100) {
			copyDatas.addAll(datas);
			datas.clear();
			DBManager.getHT(null).saveOrUpdateAll(copyDatas);

		}
	}
	public RequestInfo  copyRequestInfo(RequestInfo oldRequestInfo){
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
