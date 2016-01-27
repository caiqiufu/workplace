package com.apps.esb.service.bss.handler.mshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.app.vo.subscriber.freeresource.FreeResourceVO;
import com.apps.esb.service.bss.app.vo.subscriber.freeresource.QueryFreeResourceVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.CustomizeBizHanlder;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.vo.mshop.freeresource.MshopFreeResourceVO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;

@Service("mshopQueryFreeResource")
public class MshopQueryFreeResource implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		BizHandler appHandler = (BizHandler) ServiceUtils.getBeanByTenantId(handler.get("appHandlerName"));
		ProcessResult processResult = appHandler.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(processResult.getResultCode())) {
			return processResult;
		}
		QueryFreeResourceVO queryFreeResourceVO = (QueryFreeResourceVO) processResult.getVo();
		List<MshopFreeResourceVO> freeResources = getFreeResourceList(queryFreeResourceVO);
		
		if(handler!=null){
			String custHandlerName = handler.get("custHandlerName");
			if(!StringUtils.isEmpty(custHandlerName)){
				CustomizeBizHanlder custHandler = (CustomizeBizHanlder)ServiceUtils.getBean(custHandlerName);
				List<Object> originalVOs = new ArrayList<Object>();
				originalVOs.add(queryFreeResourceVO);
				custHandler.process(requestInfo, handler, extParameters, freeResources,originalVOs);
			}
		}
		
		JSONObject jsonResult = new JSONObject();
		processResult.setResultCode(UnieapConstants.C0);
		processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
		jsonResult.put("freeResourceList",JSONUtils.collectionToJson(freeResources));
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

	private List<MshopFreeResourceVO> getFreeResourceList(QueryFreeResourceVO queryFreeResourceVO) {
		List<FreeResourceVO> freeResourceList = queryFreeResourceVO.getFreeResourceList();
		if (freeResourceList != null && freeResourceList.size() > 0) {
			List<MshopFreeResourceVO> freeResources = new ArrayList<MshopFreeResourceVO>();
			for (int i = 0; i < freeResourceList.size(); i++) {
				FreeResourceVO freeResourceVO = freeResourceList.get(i);
				MshopFreeResourceVO mshopFreeResourceVO = new MshopFreeResourceVO();
				freeResources.add(mshopFreeResourceVO);
				mshopFreeResourceVO.setEffectiveTime(freeResourceVO.getEffectiveTime());
				mshopFreeResourceVO.setExpiryTime(freeResourceVO.getExpiryTime());
				mshopFreeResourceVO.setFreeResourceCode(freeResourceVO.getFreeResourceCode());
				mshopFreeResourceVO.setFreeResourceID(freeResourceVO.getFreeResourceID());
				mshopFreeResourceVO.setFreeResourceName(freeResourceVO.getFreeResourceName());
				mshopFreeResourceVO.setFreeResourceType(freeResourceVO.getFreeResourceType());
				mshopFreeResourceVO.setFreeResourceTypeDesc(freeResourceVO.getFreeResourceTypeDesc());
				mshopFreeResourceVO.setMeasureUnit(freeResourceVO.getMeasureUnit());
				mshopFreeResourceVO.setMeasureUnitDesc(freeResourceVO.getMeasureUnitDesc());
				mshopFreeResourceVO.setTotalInitialAmount(freeResourceVO.getTotalInitialAmount());
				mshopFreeResourceVO.setTotalUnusedAmount(freeResourceVO.getTotalUnusedAmount());
				mshopFreeResourceVO.setTotalUsedAmount(
						getUsedAmount(freeResourceVO.getTotalInitialAmount(), freeResourceVO.getTotalUnusedAmount()));
				
				mshopFreeResourceVO.setEffectiveTime(freeResourceVO.getEffectiveTime());
				mshopFreeResourceVO.setExpiryTime(freeResourceVO.getExpiryTime());
				mshopFreeResourceVO.setExtAttris(freeResourceVO.getExtAttris());
			}
			return freeResources;
		}
		return null;
	}

	private String getUsedAmount(String totalInitialAmount, String totalUnusedAmount) {
		double usedAmount = Double.parseDouble(totalInitialAmount) - Double.parseDouble(totalUnusedAmount);
		return Double.toString(usedAmount);
	}
}
