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
import com.apps.esb.service.bss.app.cbs.handler.QueryXchangePromotionLogs;
import com.apps.esb.service.bss.app.cbs.vo.xchangelog.QueryXchangeLogVO;
import com.apps.esb.service.bss.app.cbs.vo.xchangelog.XchangeInfoVO;
import com.apps.esb.service.bss.app.vo.XchangeLogVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;

@Service("queryXchangeLogs")
public class QueryXchangeLogs extends SoapMessageHandler implements BizHandler{

	public QueryXchangeLogs() throws Exception {
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
		QueryXchangePromotionLogs queryXchangePromotionLogs = (QueryXchangePromotionLogs) ServiceUtils.getBean("queryXchangePromotionLogs");
		ProcessResult processResult = queryXchangePromotionLogs.process(requestInfo, parameters, extParameters);
		if(!UnieapConstants.C0.equals(processResult.getResultCode())){
			return processResult;
		}
		QueryXchangeLogVO queryXchangeLogVO = (QueryXchangeLogVO) processResult.getVo();
		List<XchangeInfoVO> xchangeInfos = queryXchangeLogVO.getXchangeInfos();
		if(xchangeInfos!=null &&xchangeInfos.size()>0){
			List<XchangeLogVO> logs = new ArrayList<XchangeLogVO>();
			for(int i = 0 ; i < xchangeInfos.size() ; i++){
				XchangeInfoVO xchangeInfoVO = xchangeInfos.get(i);
				XchangeLogVO xchangeLogVO = new XchangeLogVO();
				logs.add(xchangeLogVO);
				xchangeLogVO.setApplierNumber(xchangeInfoVO.getApplierNumber());
				xchangeLogVO.setBonusAmount(BssServiceUtils.moneyFormat(xchangeInfoVO.getBonusAmount()));
				xchangeLogVO.setBonusExpTime(BssServiceUtils.dateFormat(xchangeInfoVO.getBonusExpTime()));
				xchangeLogVO.setDeductAmount(BssServiceUtils.moneyFormat(xchangeInfoVO.getDeductAmount()));
				xchangeLogVO.setReceiverNumber(xchangeInfoVO.getReceiverNumber());
				xchangeLogVO.setTradeTime(BssServiceUtils.dateFormat(xchangeInfoVO.getTradeTime()));
				xchangeLogVO.setTransferFee(BssServiceUtils.moneyFormat(xchangeInfoVO.getTransferFee()));
			}
			JSONArray logsJson = JSONUtils.getJSONArray(logs);
			JSONObject jsonResult = new JSONObject();
			jsonResult.put("xChangeLog", logsJson);
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
