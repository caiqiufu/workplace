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
				ProcessResult processResult = new ProcessResult();
				processResult.setResultCode(BssErrorCode.C99999);
				processResult.setResultDesc(e.getLocalizedMessage());
				long endTime = System.currentTimeMillis();
				String during = "" + (endTime - beginTime);
				Esblog esblog = BssServiceUtils.getEsbLog(requestHeader, processResult, requestInfoString, "", during, UnieapConstants.ESB);
				setDatas(esblog);
				///////////////////////////////////////
				ResponsetInfo ResponsetInfo = new ResponsetInfo();
				ResponsetHeader responsetHeader =  new ResponsetHeader();
				ResponseBody responseBody = new ResponseBody();
				ResponsetInfo.setResponsetHeader(responsetHeader);
				ResponsetInfo.setResponseBody(responseBody);
				responsetHeader.setResultCode(BssErrorCode.C99999);
				responsetHeader.setResultDesc(e.getLocalizedMessage());
				String responsetInfoString = BssServiceUtils.getResposeInfoString(ResponsetInfo);
				return responsetInfoString;
			}
			requestInfo.getRequestHeader().setTransactionId(transactionId);
			String bizCode = requestInfo.getRequestHeader().getBizCode();
			Map<String, String> handlerObj = SYSConfig.getBizHandler().get(bizCode);
			if (handlerObj == null) {
				String errorDesc = SYSConfig.getErrorDesc(BssErrorCode.CRM_10001);
				ProcessResult processResult = new ProcessResult();
				processResult.setResultCode(BssErrorCode.CRM_10001);
				processResult.setResultDesc(errorDesc);
				processResult.setServiceNumber(requestInfo.getRequestBody().getServiceNumber());
				long endTime = System.currentTimeMillis();
				String during = "" + (endTime - beginTime);
				Esblog esblog = BssServiceUtils.getEsbLog(requestInfo.getRequestHeader(), processResult, requestInfoString,"", during, UnieapConstants.ESB);
				setDatas(esblog);
				//////////////////////////////////////////
				ResponsetInfo responsetInfo = new ResponsetInfo();
				ResponsetHeader responsetHeader = new ResponsetHeader();
				responsetHeader.setResultCode(BssErrorCode.CRM_10001);
				responsetHeader.setResultDesc(errorDesc);
				responsetHeader.setTransactionId(transactionId);
				ResponseBody responseBody = new ResponseBody();
				responseBody.setServiceNumber(requestInfo.getRequestBody().getServiceNumber());
				responsetInfo.setResponsetHeader(responsetHeader);
				responsetInfo.setResponseBody(responseBody);
				String responsetInfoString = BssServiceUtils.getResposeInfoString(responsetInfo);
				return responsetInfoString;
			}
			BizHandler handler = (BizHandler) ServiceUtils.getBean(handlerObj.get("className"));
			ProcessResult processResult;
			try {
				processResult = handler.process(BssServiceUtils.copyRequestInfo(requestInfo), requestInfo.getRequestBody().getServiceNumber(),
						requestInfo.getRequestBody().getExtParameters(), handlerObj.get("parameters"));
			} catch (Exception e) {
				processResult = new ProcessResult();
				processResult.setResultCode(BssErrorCode.C99999);
				processResult.setResultDesc(e.getLocalizedMessage());
				processResult.setServiceNumber(requestInfo.getRequestBody().getServiceNumber());
				long endTime = System.currentTimeMillis();
				String during = "" + (endTime - beginTime);
				Esblog esblog = BssServiceUtils.getEsbLog(requestInfo.getRequestHeader(), processResult, requestInfoString,"", during, UnieapConstants.ESB);
				setDatas(esblog);
				//////////////////////////////////////////////
				ResponsetInfo responsetInfo = new ResponsetInfo();
				ResponsetHeader responsetHeader = new ResponsetHeader();
				responsetHeader.setResultCode(BssErrorCode.C99999);
				responsetHeader.setResultDesc(e.getLocalizedMessage());
				responsetHeader.setTransactionId(transactionId);
				ResponseBody responseBody = new ResponseBody();
				responsetInfo.setResponsetHeader(responsetHeader);
				responsetInfo.setResponseBody(responseBody);
				String responsetInfoString = BssServiceUtils.getResposeInfoString(responsetInfo);
				return responsetInfoString;
			}
			ResponsetInfo responsetInfo = BssServiceUtils.getResponsetInfo(requestInfo, processResult);
			String responsetInfoString = BssServiceUtils.getResposeInfoString(responsetInfo);
			long endTime = System.currentTimeMillis();
			String during = "" + (endTime - beginTime);
			String responseTime = UnieapConstants.getCurrentTime(null, null);
			responsetInfo.getResponsetHeader().setResponseTime(responseTime);
			Esblog esblog = BssServiceUtils.getEsbLog(requestInfo.getRequestHeader(),processResult, requestInfoString, responsetInfoString, during, UnieapConstants.ESB);
			setDatas(esblog);
			return responsetInfoString;
		} catch (Exception e) {
			ProcessResult processResult = new ProcessResult();
			processResult.setResultCode(BssErrorCode.C99999);
			processResult.setResultDesc(e.getLocalizedMessage());
			//////////////////////////////////
			long endTime = System.currentTimeMillis();
			String during = "" + (endTime - beginTime);
			Esblog esblog = BssServiceUtils.getEsbLog(requestInfo.getRequestHeader(), processResult, requestInfoString,"", during, UnieapConstants.ESB);
			setDatas(esblog);
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
			return responsetInfoString;
		}
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
}
