package com.apps.esb.service.bss.handler.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.cbs.handler.QueryAccountTransferLogs;
import com.apps.esb.service.bss.app.cbs.vo.transferbalancelog.QueryTransferBalanceLogVO;
import com.apps.esb.service.bss.app.cbs.vo.transferbalancelog.TransferInfoVO;
import com.apps.esb.service.bss.app.vo.TransferBalanceLogVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;

@Service("queryTransferLogs")
public class QueryTransferLogs extends SoapMessageHandler implements BizHandler{

	public QueryTransferLogs() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		String isdebug =SYSConfig.getConfig().get("mcare.app.extaction.debug");
		if(UnieapConstants.YES.equals(isdebug)){
			requestInfo.getRequestBody().setServiceNumber("93268659");
		}
		QueryAccountTransferLogs queryAccountTransferLogs = (QueryAccountTransferLogs) ServiceUtils.getBean("queryAccountTransferLogs");
		ProcessResult processResult = queryAccountTransferLogs.process(requestInfo, parameters, extParameters);
		if(!UnieapConstants.C0.equals(processResult.getResultCode())){
			return processResult;
		}
		QueryTransferBalanceLogVO queryTransferBalanceLogVO = (QueryTransferBalanceLogVO) processResult.getVo();
		List<TransferInfoVO> transferInfoList = queryTransferBalanceLogVO.getTransferInfoList();
		if(transferInfoList!=null &&transferInfoList.size()>0){
			List<TransferBalanceLogVO> logs = new ArrayList<TransferBalanceLogVO>();
			for(int i = 0 ; i < transferInfoList.size() ; i++){
				TransferInfoVO transferInfoVO = transferInfoList.get(i);
				TransferBalanceLogVO transferBalanceLogVO = new TransferBalanceLogVO();
				logs.add(transferBalanceLogVO);
				transferBalanceLogVO.setResultCode(transferInfoVO.getResultCode());
				transferBalanceLogVO.setTradeTime(BssServiceUtils.dateFormat(transferInfoVO.getTradeTime()));
				transferBalanceLogVO.setTransferAmount(BssServiceUtils.moneyFormat(transferBalanceLogVO.getTransferAmount()));
				transferBalanceLogVO.setTransferChannelID(UnieapConstants.getDicName("rechargeChannel", transferBalanceLogVO.getTransferChannelID()));
				transferBalanceLogVO.setTransfereeNumber(transferBalanceLogVO.getTransfereeNumber());
			}
			JSONArray logsJson = JSONUtils.getJSONArray(logs);
			JSONObject jsonResult = new JSONObject();
			jsonResult.put("transferBalanceLog", logsJson);
			processResult.setExtParameters(jsonResult.toString());
		}
		return processResult;
		
	}

	@Override
	public RequestInfo getRequestInfoFromSOAPMessage(SOAPMessage request) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponsetInfo getResponseInfoFromSOAPMessage(SOAPMessage response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SOAPMessage getRequestSOAPMessage(String serviceNumber, RequestInfo requestInfo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
