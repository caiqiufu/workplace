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
import com.apps.esb.service.bss.app.cbs.handler.QueryRechargeLogs;
import com.apps.esb.service.bss.app.cbs.vo.rechargelog.QueryRechargeLogVO;
import com.apps.esb.service.bss.app.cbs.vo.rechargelog.RechargeInfoVO;
import com.apps.esb.service.bss.app.vo.RechargeLogVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;

@Service("queryMyRechargeLogs")
public class QueryRechargeLog extends SoapMessageHandler implements BizHandler {

	public QueryRechargeLog() throws Exception {
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
		QueryRechargeLogs queryRechargeLogs = (QueryRechargeLogs) ServiceUtils.getBean("queryRechargeLogs");
		ProcessResult processResult = queryRechargeLogs.process(requestInfo, parameters, extParameters);
		if(!UnieapConstants.C1.equals(processResult.getResultCode())){
			return processResult;
		}
		QueryRechargeLogVO queryRechargeLogVO = (QueryRechargeLogVO) processResult.getVo();
		List<RechargeInfoVO> rechargeInfoList = queryRechargeLogVO.getRechargeInfoList();
		if (rechargeInfoList != null && rechargeInfoList.size() > 0) {
			List<RechargeLogVO> logs = new ArrayList<RechargeLogVO>();
			RechargeInfoVO rechargeInfoVO;
			for (int i = 0; i < rechargeInfoList.size(); i++) {
				rechargeInfoVO = rechargeInfoList.get(i);
				RechargeLogVO rechargeLogVO = new RechargeLogVO();
				logs.add(rechargeLogVO);
				rechargeLogVO.setAmount(BssServiceUtils.moneyFormat(rechargeInfoVO.getRechargeAmount()));
				rechargeLogVO.setChannel(
						UnieapConstants.getDicName("rechargeChannel", rechargeInfoVO.getRechargeChannelID()));
				rechargeLogVO.setDatetime(BssServiceUtils.dateFormat(rechargeInfoVO.getTradeTime()));
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
