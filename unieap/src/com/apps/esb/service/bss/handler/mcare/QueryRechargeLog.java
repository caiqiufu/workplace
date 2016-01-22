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
import com.apps.esb.service.bss.app.vo.account.recharge.QueryRechargeLogsVO;
import com.apps.esb.service.bss.app.vo.account.recharge.RechargeLogVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.CustomizeBizHanlder;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;

@Service("queryMyRechargeLogs")
public class QueryRechargeLog implements BizHandler {

	public QueryRechargeLog() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

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
		QueryRechargeLogsVO queryRechargeLogsVO = (QueryRechargeLogsVO) processResult.getVo();
		List<RechargeLogVO> rechargeLogList = queryRechargeLogsVO.getRechargeLogList();
		if (rechargeLogList != null && rechargeLogList.size() > 0) {
			List<com.apps.esb.service.bss.vo.macre.recharge.RechargeLogVO> logs = new ArrayList<com.apps.esb.service.bss.vo.macre.recharge.RechargeLogVO>();
			for (int i = 0; i < rechargeLogList.size(); i++) {
				RechargeLogVO rechargeLogVO = rechargeLogList.get(i);
				com.apps.esb.service.bss.vo.macre.recharge.RechargeLogVO log = new com.apps.esb.service.bss.vo.macre.recharge.RechargeLogVO();
				logs.add(log);
				log.setAmount(BssServiceUtils.moneyFormat(rechargeLogVO.getRechargeAmount()));
				log.setChannel(
						UnieapConstants.getDicName("rechargeChannel", rechargeLogVO.getRechargeChannelID()));
				log.setDatetime(BssServiceUtils.dateFormat(rechargeLogVO.getTradeTime()));
			}
			if(handler!=null){
				String custHandlerName = handler.get("custHandlerName");
				if(!StringUtils.isEmpty(custHandlerName)){
					CustomizeBizHanlder custHandler = (CustomizeBizHanlder)ServiceUtils.getBean(custHandlerName);
					List<Object> originalVOs = new ArrayList<Object>();
					originalVOs.add(queryRechargeLogsVO);
					custHandler.process(requestInfo, handler, extParameters, logs,originalVOs);
				}
			}
			JSONArray logsJson = JSONUtils.getJSONArray(logs);
			JSONObject jsonResult = new JSONObject();
			jsonResult.put("rechargeLog", logsJson);
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
		return null;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		return null;
	}

}
