package com.apps.esb.service.bss.handler.mcare;

import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.vo.account.xchange.XchangeLogVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;

@Service("xchange")
public class Xchange implements BizHandler{
	@Override
	public ProcessResult process(RequestInfo requestInfo,Map<String, String> handler, Map<String, Object> extParameters)
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
		BizHandler appHandler = (BizHandler) ServiceUtils.getBeanByTenantId(handler.get("appHandlerName"));
		ProcessResult processResult = appHandler.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(processResult.getResultCode())) {
			return processResult;
		}
		XchangeLogVO xchangeLogVO = (XchangeLogVO) processResult.getVo();
		JSONObject xChangeResultJson = new JSONObject();
		xChangeResultJson.put("applierNumber", xchangeLogVO.getApplierNumber());
		xChangeResultJson.put("receiverNumber", xchangeLogVO.getReceiverNumber());
		xChangeResultJson.put("deductAmount", xchangeLogVO.getDeductAmount());
		xChangeResultJson.put("transferFee", xchangeLogVO.getTransferFee());
		xChangeResultJson.put("bonusAmount", xchangeLogVO.getBonusAmount());
		xChangeResultJson.put("bonusExpTime", BssServiceUtils.dateFormat(xchangeLogVO.getExpiryTime()));
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
