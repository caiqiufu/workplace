package com.apps.esb.service.bss.handler.mshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.app.vo.subscriber.sim.SimCardVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.CustomizeBizHanlder;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.vo.mshop.sim.MshopSimCardVO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;


@Service("mshopQuerySim")
public class MshopQuerySim implements BizHandler {

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
		SimCardVO simCardVO = (SimCardVO) processResult.getVo();
		MshopSimCardVO mshopSimCardVO = getSimCardInfo(simCardVO);

		if (handler != null) {
			String custHandlerName = handler.get("custHandlerName");
			if (!StringUtils.isEmpty(custHandlerName)) {
				CustomizeBizHanlder custHandler = (CustomizeBizHanlder) ServiceUtils.getBean(custHandlerName);
				List<Object> originalVOs = new ArrayList<Object>();
				originalVOs.add(simCardVO);
				custHandler.process(requestInfo, handler, extParameters, mshopSimCardVO, originalVOs);
			}
		}

		processResult.setResultCode(UnieapConstants.C0);
		processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
		JSONObject dataJson = JSONUtils.convertBean2JSON(mshopSimCardVO);
		processResult.setExtParameters(dataJson.toString());
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
	private MshopSimCardVO getSimCardInfo(SimCardVO simCardVO) {
		MshopSimCardVO mshopSimCardVO = new MshopSimCardVO();
		mshopSimCardVO.setIccid(simCardVO.getIccid());
		mshopSimCardVO.setImsi(simCardVO.getImsi());
		mshopSimCardVO.setPin1(simCardVO.getPin1());
		mshopSimCardVO.setPin2(simCardVO.getPin2());
		mshopSimCardVO.setPuk1(simCardVO.getPuk1());
		mshopSimCardVO.setPuk2(simCardVO.getPuk2());
		mshopSimCardVO.setServiceNumber(simCardVO.getServiceNumber());
		mshopSimCardVO.setSimCardType(simCardVO.getSimCardTypeDesc());
		
		mshopSimCardVO.setEffectiveTime(simCardVO.getEffectiveTime());
		mshopSimCardVO.setExpiryTime(simCardVO.getExpiryTime());
		mshopSimCardVO.setExtAttris(simCardVO.getExtAttris());
		
		return mshopSimCardVO;
	}

}
