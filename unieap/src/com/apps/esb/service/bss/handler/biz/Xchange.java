package com.apps.esb.service.bss.handler.biz;

import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.cbs.vo.xchange.XchangePromotionVO;
import com.apps.esb.service.bss.customize.smart.app.handler.cbs.XchangePromotion;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;

@Service("xchange")
public class Xchange extends SoapMessageHandler implements BizHandler{

	public Xchange() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getExtParameters())) {
			throw new Exception("xchange info is null");
		}
		JSONObject json = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if (!json.has("xChangeType")) {
			throw new Exception("xChangeType is null");
		}
		if (!json.has("applierNumber")) {
			throw new Exception("applierNumber is null");
		}
		if (!json.has("receiverNumber")) {
			throw new Exception("receiverNumber is null");
		}
		if (!json.has("exchangeAmount")) {
			throw new Exception("exchangeAmount is null");
		}
		XchangePromotion xchangePromotion = (XchangePromotion) ServiceUtils.getBean("xchangePromotion");
		ProcessResult processResult = xchangePromotion.process(requestInfo, parameters, extParameters);
		if (!UnieapConstants.C0.equals(processResult.getResultCode())) {
			return processResult;
		}
		XchangePromotionVO xchangePromotionVO = (XchangePromotionVO) processResult.getVo();
		JSONObject xChangeResultJson = new JSONObject();
		xChangeResultJson.put("applierNumber", xchangePromotionVO.getApplierNumber());
		xChangeResultJson.put("receiverNumber", xchangePromotionVO.getReceiverNumber());
		xChangeResultJson.put("deductAmount", xchangePromotionVO.getDeductAmount());
		xChangeResultJson.put("transferFee", xchangePromotionVO.getTransferFee());
		xChangeResultJson.put("bonusAmount", xchangePromotionVO.getBonusAmount());
		xChangeResultJson.put("bonusExpTime", BssServiceUtils.dateFormat(xchangePromotionVO.getBonusExpTime()));
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("xChangeResult", xChangeResultJson);
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

}
