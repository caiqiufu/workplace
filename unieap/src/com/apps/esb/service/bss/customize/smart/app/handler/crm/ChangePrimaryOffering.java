package com.apps.esb.service.bss.customize.smart.app.handler.crm;

import java.util.Map;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;

@Service("changePrimaryOffering")
public class ChangePrimaryOffering extends SoapMessageHandler implements BizHandler{

	public ChangePrimaryOffering() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("changePrimaryOffering");
		return process(this, newRequestInfo, requestInfo.getRequestBody().getServiceNumber(), requestInfo.getRequestBody().getExtParameters(), parameters,"ws.crm.query.timeout");

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
		JSONObject json = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if(!json.has("offeringInfo")){
			throw new Exception("offeringInfo is null");
		}
		JSONObject offeringInfo = json.getJSONObject("offeringInfo");
		if(!offeringInfo.has("primaryOfferingId")){
			throw new Exception("primaryOfferingId is null");
		}
		String primaryOfferingId = offeringInfo.getString("primaryOfferingId");
		if(StringUtils.isEmpty(primaryOfferingId)){
			throw new Exception("primaryOfferingId is null");
		}
		SOAPMessage message = messageFactory.createMessage();
		this.getCRMSerHeader("SubmitOrderRequest", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement requestBodyElement = bodyElement.addChildElement("SubmitRequestBody","ser");
		SOAPElement orderElement = requestBodyElement.addChildElement("Order","ser");
		orderElement.addChildElement("OrderType","bas").addTextNode("CO024");
		SOAPElement orderItemsElement = requestBodyElement.addChildElement("OrderItems","ser");
		SOAPElement orderItemElement = orderItemsElement.addChildElement("OrderItem","bas");
		SOAPElement orderItemInfoElement = orderItemElement.addChildElement("OrderItemInfo","bas");
		orderItemInfoElement.addChildElement("OrderItemType","bas").addTextNode("CO024");
		SOAPElement subscriberElement = orderItemElement.addChildElement("Subscriber","bas");
		subscriberElement.addChildElement("ServiceNumber","bas").addTextNode(serviceNumber);
		SOAPElement primaryOfferingInfoElement = subscriberElement.addChildElement("PrimaryOfferingInfo","bas");
		/**
		 * 1 add, 2 modify,3 delete, 4 upgrade , 5 downgrade, 9 migrate , 10 continue(reserved) 
		 */
		primaryOfferingInfoElement.addChildElement("ActionType","bas").addTextNode("2");
		primaryOfferingInfoElement.addChildElement("ActiveMode","bas").addTextNode("M");
		/**
		 * 0 immediate, 1 next bill cycle, 2 absolute date
		 */
		primaryOfferingInfoElement.addChildElement("EffectMode","bas").addTextNode("0");
		/**
		 * 0 immediate, 1 next bill cycle, 2 absolute date
		 */
		primaryOfferingInfoElement.addChildElement("ExpireMode","bas").addTextNode("0");
		SOAPElement offeringIdElement = primaryOfferingInfoElement.addChildElement("OfferingId","bas");
		offeringIdElement.addChildElement("OfferingId","bas").addTextNode(primaryOfferingId.trim());
		if(offeringInfo.has("supplementaryOfferings")){
			JSONArray supplementaryOfferings = offeringInfo.getJSONArray("supplementaryOfferings");
			if(supplementaryOfferings!=null&&supplementaryOfferings.length()>0){
				SOAPElement supplementaryOfferingListElement = subscriberElement.addChildElement("SupplementaryOfferingList","bas");
				for(int i = 0 ; i < supplementaryOfferings.length() ; i++){
					JSONObject offeringJSONObject = supplementaryOfferings.getJSONObject(i);
					String offeringId = offeringJSONObject.getString("offeringId");
					if(StringUtils.isEmpty(offeringId)){
						throw new Exception("offeringId is null");
					}
					String actionType = offeringJSONObject.getString("actionType");
					if(StringUtils.isEmpty(actionType)){
						throw new Exception("actionType is null");
					}
					SOAPElement subOfferingInfoElement = supplementaryOfferingListElement.addChildElement("OfferingInfo","bas");
					subOfferingInfoElement.addChildElement("ActionType","bas").addTextNode(actionType);
					SOAPElement subOfferingIdElement = subOfferingInfoElement.addChildElement("OfferingId","bas");
					subOfferingIdElement.addChildElement("OfferingId","bas").addTextNode(offeringId.trim());
				}
			}
		}
		return message;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		ProcessResult result = new ProcessResult();
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("bas:RetCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("bas:RetCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("bas:RetMsg").getLength() > 0) {

			String retMsg = document.getElementsByTagName("bas:RetMsg").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}
		return result;
	}

}
