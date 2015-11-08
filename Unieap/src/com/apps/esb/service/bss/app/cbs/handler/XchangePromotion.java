package com.apps.esb.service.bss.app.cbs.handler;

import java.util.Map;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;

@Service("xchangePromotion")
public class XchangePromotion extends SoapMessageHandler implements BizHandler{

	public XchangePromotion() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo,String parameters,Map<String,Object> extParameters)
			throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("xchangePromotion");
		return process(this, newRequestInfo, newRequestInfo.getRequestBody().getServiceNumber(), newRequestInfo.getRequestBody().getExtParameters(), parameters, "ws.cbs.query.timeout");
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
		String xChangeType = "";
		String applierNumber = "";
		String receiverNumber = "";
		String exchangeAmount = "";
		if (!StringUtils.isEmpty(requestInfo.getRequestBody().getExtParameters())) {
			JSONObject extParametersJson = new JSONObject(requestInfo.getRequestBody().getExtParameters());
			if (extParametersJson.has("xChangeType")) {
				xChangeType = extParametersJson.getString("xChangeType");
			} else {
				throw new Exception("applierNumber is null");
			}
			if (extParametersJson.has("receiverNumber")) {
				receiverNumber = extParametersJson.getString("receiverNumber");
			} else {
				throw new Exception("transfereeNumber is null");
			}
			if (extParametersJson.has("exchangeAmount")) {
				exchangeAmount = extParametersJson.getString("exchangeAmount");
				if (Double.parseDouble(exchangeAmount) <= 0) {
					throw new Exception("exchangeAmount is negative value");
				}
			} else {
				throw new Exception("exchangeAmount is null");
			}
		}
		
		
		SOAPMessage message = messageFactory.createMessage();
		this.getCBSArsHeader("ExchangeAcctRequestMsg", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement reqestElement = bodyElement.addChildElement("ExchangeAcctRequest");
		reqestElement.addChildElement("ExchangeAmt").addTextNode(exchangeAmount);
		SOAPElement exchangeObjElement = reqestElement.addChildElement("ExchangeObj");
		SOAPElement subAccessCodeElement = exchangeObjElement.addChildElement("SubAccessCode");
		SOAPElement primaryIdentityElement = subAccessCodeElement
				.addChildElement("PrimaryIdentity");
		primaryIdentityElement.addTextNode(transferorNumber);
		SOAPElement transfereeObjElement = reqestElement.addChildElement("TransfereeObj");
		SOAPElement transfereeSubAccessCodeElement = transfereeObjElement.addChildElement("SubAccessCode");
		SOAPElement transfereePrimaryIdentityElement = transfereeSubAccessCodeElement
				.addChildElement("PrimaryIdentity");
		transfereePrimaryIdentityElement.addTextNode(transfereeNumber);
		
		
		return null;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
