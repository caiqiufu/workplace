package com.apps.esb.service.bss.customize.smart.app.crm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.vo.subscriber.offering.OfferingVO;
import com.apps.esb.service.bss.app.vo.subscriber.offering.QueryAvailableOfferingVO;
import com.apps.esb.service.bss.customize.smart.app.CustSoapMessageHandler;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.interfacecall.SoapCallUtils;

@Service("queryPhoneNumbers_1")
public class SmartGetAvailablePhoneNumbers extends CustSoapMessageHandler implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo,Map<String, String> handler, Map<String, Object> extParameters)
			throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("queryPhoneNumbers");
		return SoapCallUtils.process(this, newRequestInfo, requestInfo.getRequestBody().getServiceNumber(),
				requestInfo.getRequestBody().getExtParameters(), handler, "ws.crm.query.timeout");

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
		SOAPMessage message = SoapCallUtils.getMessageFactory().createMessage();
		this.getCRMQuerHeader("GetCustPOfferingRequest", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement getCustPOfferingBodyElement = bodyElement.addChildElement("GetCustPOfferingBody", "quer");
		getCustPOfferingBodyElement.addChildElement("ServiceNumber", "quer").addTextNode(serviceNumber);
		return message;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		QueryAvailableOfferingVO queryAvailableOfferingVO = new QueryAvailableOfferingVO();
		ProcessResult result = new ProcessResult();
		result.setVo(queryAvailableOfferingVO);
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("bas:RetCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("bas:RetCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("bas:RetMsg").getLength() > 0) {

			String retMsg = document.getElementsByTagName("bas:RetMsg").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}
		if (document.getElementsByTagName("quer:GetOfferingInfo").getLength() > 0) {
			NodeList getOfferingInfoNodes = document.getElementsByTagName("quer:GetOfferingInfo");
			List<OfferingVO> primaryOfferingList = new ArrayList<OfferingVO>();
			Map<String, OfferingVO> offeringMap = new HashMap<String, OfferingVO>();
			queryAvailableOfferingVO.setPrimaryOfferingList(primaryOfferingList);
			queryAvailableOfferingVO.setOfferingMap(offeringMap);
			for (int i = 0; i < getOfferingInfoNodes.getLength(); i++) {
				Node getOfferingInfoNode = getOfferingInfoNodes.item(i);
				NodeList nodes = getOfferingInfoNode.getChildNodes();
				OfferingVO offeringInfoVO = new OfferingVO();
				primaryOfferingList.add(offeringInfoVO);
				for (int j = 0; j < nodes.getLength(); j++) {
					Node node = nodes.item(j);
					if ("bas:OfferingId".equals(node.getNodeName())) {
						offeringInfoVO.setOfferingId(node.getTextContent());
					} else if ("bas:OfferingName".equals(node.getNodeName())) {
						offeringInfoVO.setOfferingName(node.getTextContent());
					} else if ("bas:OfferingCode".equals(node.getNodeName())) {
						offeringInfoVO.setOfferingCode(node.getTextContent());
					} else if ("bas:OfferingShortName".equals(node.getNodeName())) {
						offeringInfoVO.setOfferingShortName(node.getTextContent());
					} else if ("bas:Status".equals(node.getNodeName())) {
						offeringInfoVO.setStatus(node.getTextContent());
					} else if ("bas:NetworkType".equals(node.getNodeName())) {
						offeringInfoVO.setNetworkType(node.getTextContent());
					} else if ("bas:OfferingType".equals(node.getNodeName())) {
						offeringInfoVO.setOfferingType(node.getTextContent());
					} else if ("bas:OwnerType".equals(node.getNodeName())) {
						offeringInfoVO.setOwnerType(node.getTextContent());
					} else if ("bas:MonthlyFee".equals(node.getNodeName())) {
						offeringInfoVO.setRentFee(node.getTextContent());
					} else if ("bas:OneTimeFee".equals(node.getNodeName())) {
						offeringInfoVO.setOneTimeFee(node.getTextContent());
					} else if ("bas:EffectiveTime".equals(node.getNodeName())) {
						offeringInfoVO.setEffectiveTime(node.getTextContent());
					} else if ("bas:ExpiredTime".equals(node.getNodeName())) {
						offeringInfoVO.setExpiryTime(node.getTextContent());
					} else if ("bas:SellCatalogueId".equals(node.getNodeName())) {
						offeringInfoVO.setSellCatalogueId(node.getTextContent());
					} else if ("bas:SellCatalogueId".equals(node.getNodeName())) {
						offeringInfoVO.setSellCatalogueId(node.getTextContent());
					} else if ("bas:SellCatalogureName".equals(node.getNodeName())) {
						offeringInfoVO.setSellCatalogueName(node.getTextContent());
					}
				}
				offeringMap.put(offeringInfoVO.getOfferingId(), offeringInfoVO);
			}
		}
		return result;
	}

}
