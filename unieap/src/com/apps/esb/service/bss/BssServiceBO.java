package com.apps.esb.service.bss;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.apps.esb.pojo.Esblog;
import com.apps.esb.pojo.EsblogDevice;
import com.apps.esb.service.FlowController;
import com.apps.esb.service.bss.element.RequestBody;
import com.apps.esb.service.bss.element.RequestHeader;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponseBody;
import com.apps.esb.service.bss.element.ResponseHeader;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.unieap.BaseBO;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.db.DBManager;
import com.unieap.pojo.ExcLog;

@Service("bssServiceBO")
public class BssServiceBO extends BaseBO {
	public String queryInfo(String requestInfoString, Map<String, Object> extParameters) throws Exception {
		FlowController.addQueryRequest();
		if (FlowController.isExpired()) {
			return expiredResonse();
		} else if (FlowController.isQueryOverFlow()) {
			FlowController.deductQueryRequest();
			return overFlowResonse();
		} else {
			FlowController.deductQueryRequest();
			return process(requestInfoString, extParameters);
		}
	}

	public boolean authenticationCheck(String accessUser, String accessPwd) {
		/*
		 * com.unieap.pojo.User user = CacheMgt.getUser(accessUser);
		 * if(user==null){ return false; }
		 * if(user.getPassword().equals(accessPwd)){ return true; }else{ return
		 * true; }
		 */
		return true;
	}

	public String bizHandle(String requestInfoString, Map<String, Object> extParameters) throws Exception {
		FlowController.addBizhandleRequest();
		if (FlowController.isExpired()) {
			return expiredResonse();
		} else if (FlowController.isBizhandleFlow()) {
			FlowController.deductBizhandleRequest();
			return overFlowResonse();
		} else {
			FlowController.deductBizhandleRequest();
			return process(requestInfoString, extParameters);
		}
	}

	public void setDatas(Esblog vo) {
		CacheMgt.addEsblog(vo);
		batchSaveMore100();
	}

	public void setDeviceDatas(EsblogDevice esblogDevice) {
		CacheMgt.addEsblogDeviceData(esblogDevice);
		batchEsblogDeviceSaveMore100();
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

	public void batchEsblogDeviceSaveMore100() {
		List<EsblogDevice> datas = CacheMgt.getEsblogDeviceDatas();
		List<EsblogDevice> copyDatas = new ArrayList<EsblogDevice>();
		if (datas.size() > 100) {
			copyDatas.addAll(datas);
			datas.clear();
			DBManager.getHT(null).saveOrUpdateAll(copyDatas);

		}
	}

	public String process(String requestInfoString, Map<String, Object> extParameters) {
		long beginTime = System.currentTimeMillis();
		RequestInfo requestInfo = null;
		try {
			String transactionId = BssServiceUtils.generateTransactionId();
			try {
				requestInfo = BssServiceUtils.getRequestInfo(requestInfoString);
				if (!authenticationCheck(requestInfo.getRequestHeader().getAccessUser(),
						requestInfo.getRequestHeader().getAccessPwd())) {
					String errorDesc = UnieapConstants.getMessage("10004");
					ProcessResult processResult = new ProcessResult();
					processResult.setResultCode("10004");
					processResult.setResultDesc(errorDesc);
					processResult.setServiceNumber(requestInfo.getRequestBody().getServiceNumber());
					long endTime = System.currentTimeMillis();
					String during = "" + (endTime - beginTime);
					Esblog esblog = BssServiceUtils.getEsbLog(requestInfo.getRequestHeader(), processResult,
							requestInfoString, "", during, "esb");
					setDatas(esblog);
					//////////////////////////////////////////
					ResponsetInfo responsetInfo = new ResponsetInfo();
					ResponseHeader ResponseHeader = new ResponseHeader();
					ResponseHeader.setResultCode("10004");
					ResponseHeader.setResultDesc(errorDesc);
					ResponseHeader.setTransactionId(transactionId);
					ResponseBody responseBody = new ResponseBody();
					responseBody.setServiceNumber(requestInfo.getRequestBody().getServiceNumber());
					responsetInfo.setResponseHeader(ResponseHeader);
					responsetInfo.setResponseBody(responseBody);
					String responsetInfoString = BssServiceUtils.getResposeInfoString(responsetInfo);
					return responsetInfoString;
				}
			} catch (Exception e) {
				requestInfo = new RequestInfo();
				RequestHeader requestHeader = new RequestHeader();
				requestHeader.setSystemCode(UnieapConstants.THIRDPART);
				RequestBody requestBody = new RequestBody();
				requestInfo.setRequestHeader(requestHeader);
				requestHeader.setTransactionId(transactionId);
				requestInfo.setRequestBody(requestBody);
				ProcessResult processResult = new ProcessResult();
				processResult.setResultCode(UnieapConstants.C99999);
				processResult.setResultDesc(e.getLocalizedMessage());
				long endTime = System.currentTimeMillis();
				String during = "" + (endTime - beginTime);
				Esblog esblog = BssServiceUtils.getEsbLog(requestHeader, processResult, requestInfoString, "", during,
						"esb");
				setDatas(esblog);
				///////////////////////////////////////
				ResponsetInfo ResponsetInfo = new ResponsetInfo();
				ResponseHeader ResponseHeader = new ResponseHeader();
				ResponseBody responseBody = new ResponseBody();
				ResponsetInfo.setResponseHeader(ResponseHeader);
				ResponsetInfo.setResponseBody(responseBody);
				ResponseHeader.setResultCode(UnieapConstants.C99999);
				ResponseHeader.setResultDesc(e.getLocalizedMessage());
				String responsetInfoString = BssServiceUtils.getResposeInfoString(ResponsetInfo);
				return responsetInfoString;
			}
			requestInfo.getRequestHeader().setTransactionId(transactionId);
			String bizCode = requestInfo.getRequestHeader().getBizCode();
			Map<String, String> handlerObj = SYSConfig.getBizHandler().get(bizCode);
			if (handlerObj == null) {
				String errorDesc = UnieapConstants.getMessage("10001");
				ProcessResult processResult = new ProcessResult();
				processResult.setResultCode("10001");
				processResult.setResultDesc(errorDesc);
				processResult.setServiceNumber(requestInfo.getRequestBody().getServiceNumber());
				long endTime = System.currentTimeMillis();
				String during = "" + (endTime - beginTime);
				Esblog esblog = BssServiceUtils.getEsbLog(requestInfo.getRequestHeader(), processResult,
						requestInfoString, "", during, "esb");
				setDatas(esblog);
				//////////////////////////////////////////
				ResponsetInfo responsetInfo = new ResponsetInfo();
				ResponseHeader ResponseHeader = new ResponseHeader();
				ResponseHeader.setResultCode("10001");
				ResponseHeader.setResultDesc(errorDesc);
				ResponseHeader.setTransactionId(transactionId);
				ResponseBody responseBody = new ResponseBody();
				responseBody.setServiceNumber(requestInfo.getRequestBody().getServiceNumber());
				responsetInfo.setResponseHeader(ResponseHeader);
				responsetInfo.setResponseBody(responseBody);
				String responsetInfoString = BssServiceUtils.getResposeInfoString(responsetInfo);
				return responsetInfoString;
			}
			BizHandler handler = (BizHandler) ServiceUtils.getBean(handlerObj.get("className"));
			ProcessResult processResult;
			try {
				processResult = handler.process(BssServiceUtils.copyRequestInfo(requestInfo),
						handlerObj, extParameters);
				processResult.setServiceNumber(requestInfo.getRequestBody().getServiceNumber());
			} catch (Exception e) {
				processResult = new ProcessResult();
				processResult.setResultCode(UnieapConstants.C99999);
				processResult.setResultDesc(e.getLocalizedMessage());
				processResult.setServiceNumber(requestInfo.getRequestBody().getServiceNumber());
				//////////////////////////////////////////////
				ResponsetInfo responsetInfo = new ResponsetInfo();
				ResponseHeader ResponseHeader = new ResponseHeader();
				ResponseHeader.setResultCode(UnieapConstants.C99999);
				ResponseHeader.setResultDesc(e.getLocalizedMessage());
				ResponseHeader.setTransactionId(transactionId);
				ResponseHeader.setBizCode(bizCode);
				ResponseHeader.setChannelCode(requestInfo.getRequestHeader().getChannelCode());
				ResponseHeader.setExtTransactionId(requestInfo.getRequestHeader().getExtTransactionId());
				ResponseHeader.setRequestTime(requestInfo.getRequestHeader().getRequestTime());
				ResponseHeader.setResponseTime(UnieapConstants.getCurrentTime(null, null));
				ResponseHeader.setTransactionId(requestInfo.getRequestHeader().getTransactionId());
				ResponseBody responseBody = new ResponseBody();
				responseBody.setServiceNumber(requestInfo.getRequestBody().getServiceNumber());
				responseBody.setExtParameters(requestInfo.getRequestBody().getExtParameters());
				responsetInfo.setResponseHeader(ResponseHeader);
				responsetInfo.setResponseBody(responseBody);
				String responsetInfoString = BssServiceUtils.getResposeInfoString(responsetInfo);
				long endTime = System.currentTimeMillis();
				String during = "" + (endTime - beginTime);
				Esblog esblog = BssServiceUtils.getEsbLog(requestInfo.getRequestHeader(), processResult,
						requestInfoString, "", during, "esb");
				setDatas(esblog);
				return responsetInfoString;
			}
			ResponsetInfo responsetInfo = BssServiceUtils.getResponsetInfo(requestInfo, processResult);
			String responsetInfoString = BssServiceUtils.getResposeInfoString(responsetInfo);
			long endTime = System.currentTimeMillis();
			String during = "" + (endTime - beginTime);
			String responseTime = UnieapConstants.getCurrentTime(null, null);
			responsetInfo.getResponseHeader().setResponseTime(responseTime);
			requestInfo.getRequestHeader().setResponseTime(responseTime);
			Esblog esblog = BssServiceUtils.getEsbLog(requestInfo.getRequestHeader(), processResult, requestInfoString,
					responsetInfoString, during, "esb");
			setDatas(esblog);
			EsblogDevice esblogDevice = BssServiceUtils.getEsbLogDevice(requestInfo.getRequestHeader(), esblog);
			if(esblogDevice!=null){
				setDeviceDatas(esblogDevice);
			}
			return responsetInfoString;
		} catch (Exception e) {
			ProcessResult processResult = new ProcessResult();
			processResult.setResultCode(UnieapConstants.C99999);
			processResult.setResultDesc(e.getLocalizedMessage());
			//////////////////////////////////
			long endTime = System.currentTimeMillis();
			String during = "" + (endTime - beginTime);
			Esblog esblog = BssServiceUtils.getEsbLog(requestInfo.getRequestHeader(), processResult, requestInfoString,
					"", during, "esb");
			setDatas(esblog);
			ResponsetInfo responsetInfo = new ResponsetInfo();
			ResponseHeader ResponseHeader = new ResponseHeader();
			ResponseHeader.setResultCode(UnieapConstants.C99999);
			ResponseHeader.setResultDesc(e.getLocalizedMessage());
			ResponseBody responseBody = new ResponseBody();
			responsetInfo.setResponseHeader(ResponseHeader);
			responsetInfo.setResponseBody(responseBody);
			String responsetInfoString = "";
			try {
				responsetInfoString = BssServiceUtils.getResposeInfoString(responsetInfo);
			} catch (Exception e1) {
				saveException(e1);
			}
			return responsetInfoString;
		}
	}

	public String overFlowResonse() throws Exception {
		ResponsetInfo ResponsetInfo = new ResponsetInfo();
		ResponseHeader ResponseHeader = new ResponseHeader();
		ResponseBody responseBody = new ResponseBody();
		ResponsetInfo.setResponseHeader(ResponseHeader);
		ResponsetInfo.setResponseBody(responseBody);
		ResponseHeader.setResultCode("10002");
		ResponseHeader.setResultDesc(UnieapConstants.getMessage("10002"));
		String responsetInfoString = BssServiceUtils.getResposeInfoString(ResponsetInfo);
		return responsetInfoString;
	}

	public String expiredResonse() throws Exception {
		ResponsetInfo ResponsetInfo = new ResponsetInfo();
		ResponseHeader ResponseHeader = new ResponseHeader();
		ResponseBody responseBody = new ResponseBody();
		ResponsetInfo.setResponseHeader(ResponseHeader);
		ResponsetInfo.setResponseBody(responseBody);
		ResponseHeader.setResultCode("10003");
		ResponseHeader.setResultDesc(UnieapConstants.getMessage("10003"));
		String responsetInfoString = BssServiceUtils.getResposeInfoString(ResponsetInfo);
		return responsetInfoString;
	}

	public void saveException(Exception ex) {
		ExcLog log = new ExcLog();
		log.setId(UnieapConstants.getSequence(null, "unieap"));
		log.setBizModule("unieap");
		log.setExType("system_exception");
		log.setExCode("");
		log.setExInfo(ex.getLocalizedMessage());
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		log.setExTracking(sw.toString().getBytes());
		if (UnieapConstants.getUser() != null) {
			log.setOperator(UnieapConstants.getUser().getUserCode());
		} else {
			log.setOperator("system error");
		}
		log.setOperationDate(UnieapConstants.getDateTime(null));
		DBManager.getHT(null).save(log);
	}
}
