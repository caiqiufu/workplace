package com.apps.esb.service.bss.handler.mcare;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.vo.account.transfer.QueryTransferLogsVO;
import com.apps.esb.service.bss.app.vo.account.transfer.TransferLogVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.CustomizeBizHanlder;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.vo.macre.transferbalance.TransferBalanceLogVO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;

@Service("queryTransferLogs")
public class QueryTransferLogs implements BizHandler{

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler, Map<String, Object> extParameters)
			throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		BizHandler appHandler = (BizHandler) ServiceUtils.getBeanByTenantId(handler.get("appHandlerName"));
		ProcessResult processResult = appHandler.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(processResult.getResultCode())) {
			return processResult;
		}
		QueryTransferLogsVO queryTransferLogsVO = (QueryTransferLogsVO) processResult.getVo();
		List<TransferLogVO> transferLogList = queryTransferLogsVO.getTransferLogList();
		if(transferLogList!=null &&transferLogList.size()>0){
			List<TransferBalanceLogVO> logs = new ArrayList<TransferBalanceLogVO>();
			for(int i = 0 ; i < transferLogList.size() ; i++){
				TransferLogVO transferLogVO = transferLogList.get(i);
				TransferBalanceLogVO transferBalanceLogVO = new TransferBalanceLogVO();
				logs.add(transferBalanceLogVO);
				transferBalanceLogVO.setResultCode(transferLogVO.getResultCode());
				transferBalanceLogVO.setTradeTime(BssServiceUtils.dateFormat(transferLogVO.getTradeTime()));
				transferBalanceLogVO.setTransferAmount(BssServiceUtils.moneyFormat(transferLogVO.getTransferAmount()));
				transferBalanceLogVO.setTransferChannelID(UnieapConstants.getDicName("rechargeChannel", transferLogVO.getTransferChannelID()));
				transferBalanceLogVO.setTransferorNumber(transferLogVO.getServiceNumber());
				transferBalanceLogVO.setTransfereeNumber(transferLogVO.getOppositeServiceNumber());
			}
			if(handler!=null){
				String custHandlerName = handler.get("custHandlerName");
				if(!StringUtils.isEmpty(custHandlerName)){
					CustomizeBizHanlder custHandler = (CustomizeBizHanlder)ServiceUtils.getBean(custHandlerName);
					List<Object> originalVOs = new ArrayList<Object>();
					originalVOs.add(queryTransferLogsVO);
					custHandler.process(requestInfo, handler, extParameters, logs,originalVOs);
				}
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
