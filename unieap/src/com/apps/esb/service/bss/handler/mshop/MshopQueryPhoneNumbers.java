package com.apps.esb.service.bss.handler.mshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.app.vo.subscriber.cdr.CdrInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.cdr.QueryCdrVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.CustomizeBizHanlder;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.vo.mshop.cdr.MshopCdrVO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;

@Service("mshopQueryPhoneNumbers")
public class MshopQueryPhoneNumbers implements BizHandler {

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

		QueryCdrVO queryCdrVO = (QueryCdrVO) processResult.getVo();
		List<MshopCdrVO> cdrs = getCdrList(queryCdrVO);
		if(handler!=null){
			String custHandlerName = handler.get("custHandlerName");
			if(!StringUtils.isEmpty(custHandlerName)){
				CustomizeBizHanlder custHandler = (CustomizeBizHanlder)ServiceUtils.getBean(custHandlerName);
				List<Object> originalVOs = new ArrayList<Object>();
				originalVOs.add(queryCdrVO);
				custHandler.process(requestInfo, handler, extParameters, cdrs,originalVOs);
			}
		}

		JSONObject jsonResult = new JSONObject();
		processResult.setResultCode(UnieapConstants.C0);
		processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
		jsonResult.put("paymentList", JSONUtils.collectionToJson(cdrs));
		processResult.setExtParameters(jsonResult.toString());
		return processResult;

	}

	@Override
	public RequestInfo getRequestInfoFromSOAPMessage(SOAPMessage request) throws Exception {
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

	private List<MshopCdrVO> getCdrList(QueryCdrVO queryCdrVO ) {
		List<CdrInfoVO> cdrList = queryCdrVO.getCdrInfoList();
		if (cdrList != null && cdrList.size() > 0) {
			List<MshopCdrVO> mshopCdrs = new ArrayList<MshopCdrVO>();
			for (int i = 0; i < cdrList.size(); i++) {
				CdrInfoVO cdrInfoVO = cdrList.get(i);
				MshopCdrVO mshopCdrVO = new MshopCdrVO();
				mshopCdrs.add(mshopCdrVO);
				mshopCdrVO.setCdrSeq(cdrInfoVO.getCdrSeq());
				mshopCdrVO.setChargeAmount(cdrInfoVO.getActualChargeAmt());
				mshopCdrVO.setDuration(cdrInfoVO.getChargeDuration());
				mshopCdrVO.setEndTime(cdrInfoVO.getEndTime());
				mshopCdrVO.setFlowType(cdrInfoVO.getFlowType());
				mshopCdrVO.setMeasureUnit(cdrInfoVO.getMeasureUnit());
				mshopCdrVO.setMeasureUnitDesc(cdrInfoVO.getMeasureUnitDesc());
				mshopCdrVO.setOfferingName(cdrInfoVO.getOfferingName());
				mshopCdrVO.setServiceNumber(cdrInfoVO.getServiceNumber());
				mshopCdrVO.setServiceType(cdrInfoVO.getServiceType());
				mshopCdrVO.setServiceTypeDesc(cdrInfoVO.getServiceTypeDesc());
				mshopCdrVO.setStartTime(cdrInfoVO.getStartTime());
				mshopCdrVO.setVolume(cdrInfoVO.getActualVolume());
				
				mshopCdrVO.setEffectiveTime(cdrInfoVO.getEffectiveTime());
				mshopCdrVO.setExpiryTime(cdrInfoVO.getExpiryTime());
				mshopCdrVO.setExtAttris(cdrInfoVO.getExtAttris());
			}
			return mshopCdrs;
		}
		return null;
	}
}
