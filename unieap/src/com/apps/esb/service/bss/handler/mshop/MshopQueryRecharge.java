package com.apps.esb.service.bss.handler.mshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.app.vo.account.recharge.QueryRechargeLogsVO;
import com.apps.esb.service.bss.app.vo.account.recharge.RechargeLogVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.LifeCycleChgInfoVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.CustomizeBizHanlder;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.vo.mshop.recharge.MshopRechargeLogVO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;

@Service("mshopQueryRecharge")
public class MshopQueryRecharge implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		BizHandler appHandler = (BizHandler) ServiceUtils.getBeanByTenantId(handler.get("appHandlerName"));
		ProcessResult processResult = appHandler.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(processResult.getResultCode())) {
			return processResult;
		}
		QueryRechargeLogsVO queryRechargeLogsVO = (QueryRechargeLogsVO) processResult.getVo();
		List<MshopRechargeLogVO> rechargeLogs = getRechargeLogList(queryRechargeLogsVO);

		if (handler != null) {
			String custHandlerName = handler.get("custHandlerName");
			if (!StringUtils.isEmpty(custHandlerName)) {
				CustomizeBizHanlder custHandler = (CustomizeBizHanlder) ServiceUtils.getBean(custHandlerName);
				List<Object> originalVOs = new ArrayList<Object>();
				originalVOs.add(queryRechargeLogsVO);
				custHandler.process(requestInfo, handler, extParameters, rechargeLogs, originalVOs);
			}
		}

		JSONObject jsonResult = new JSONObject();
		processResult.setResultCode(UnieapConstants.C0);
		processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
		jsonResult.put("rechargeLogList", JSONUtils.collectionToJson(rechargeLogs));
		processResult.setExtParameters(jsonResult.toString());
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

	private List<MshopRechargeLogVO> getRechargeLogList(QueryRechargeLogsVO queryRechargeLogsVO) {
		List<RechargeLogVO> rechargeLogList = queryRechargeLogsVO.getRechargeLogList();
		if (rechargeLogList != null && rechargeLogList.size() > 0) {
			List<MshopRechargeLogVO> rechargeLogs = new ArrayList<MshopRechargeLogVO>();
			for (int i = 0; i < rechargeLogList.size(); i++) {
				RechargeLogVO rechargeLogVO = rechargeLogList.get(i);
				MshopRechargeLogVO mshopRechargeLogVO = new MshopRechargeLogVO();
				rechargeLogs.add(mshopRechargeLogVO);
				List<LifeCycleChgInfoVO> lifeCycleChgInfoList = rechargeLogVO.getLifeCycleChgInfoList();
				mshopRechargeLogVO.setBarringExpiryTime(lifeCycleChgInfoList.get(0).getExpiryTime());
				mshopRechargeLogVO.setCardSequence(rechargeLogVO.getRechargeSerialNo());
				mshopRechargeLogVO.setCurrentBalance(rechargeLogVO.getCurrentAmount());
				mshopRechargeLogVO.setOriginalBalance(rechargeLogVO.getOriginalAmount());
				mshopRechargeLogVO.setRechargeAmount(rechargeLogVO.getRechargeAmount());
				mshopRechargeLogVO.setRechargeChannel(rechargeLogVO.getRechargeChannelName());
				mshopRechargeLogVO.setRechargeDate(rechargeLogVO.getTradeTime());
				mshopRechargeLogVO.setRechargeType(rechargeLogVO.getRechargeTypeDesc());
				mshopRechargeLogVO.setServiceNumber(rechargeLogVO.getServiceNumber());
				
				mshopRechargeLogVO.setEffectiveTime(rechargeLogVO.getEffectiveTime());
				mshopRechargeLogVO.setExpiryTime(rechargeLogVO.getExpiryTime());
				mshopRechargeLogVO.setExtAttris(rechargeLogVO.getExtAttris());
			}
			return rechargeLogs;
		}
		return null;
	}
}
