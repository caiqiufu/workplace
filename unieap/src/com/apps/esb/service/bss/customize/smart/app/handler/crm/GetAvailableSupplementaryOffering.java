package com.apps.esb.service.bss.customize.smart.app.handler.crm;

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
import com.apps.esb.service.bss.app.crm.vo.getavailableprimaryoffering.OfferingInfoVO;
import com.apps.esb.service.bss.app.crm.vo.getavailablesuboffering.AvailableSubOfferingVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;

@Service("getAvailableSupplementaryOffering")
public class GetAvailableSupplementaryOffering extends SoapMessageHandler implements BizHandler {

	public GetAvailableSupplementaryOffering() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("getAvailableSupplementaryOffering");
		return process(this, newRequestInfo, requestInfo.getRequestBody().getServiceNumber(),
				requestInfo.getRequestBody().getExtParameters(), parameters, "ws.crm.query.timeout");

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
		SOAPMessage message = messageFactory.createMessage();
		this.getCRMQuerHeader("GetCustSOfferingRequest", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement getCustPOfferingBodyElement = bodyElement.addChildElement("GetCustSOfferingBody", "quer");
		getCustPOfferingBodyElement.addChildElement("ServiceNumber", "quer").addTextNode(serviceNumber);
		return message;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		AvailableSubOfferingVO availableSubOfferingVO = new AvailableSubOfferingVO();
		ProcessResult result = new ProcessResult();
		result.setVo(availableSubOfferingVO);
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
			List<OfferingInfoVO> offeringInfoList = new ArrayList<OfferingInfoVO>();
			Map<String, OfferingInfoVO> offeringInfoMap = new HashMap<String, OfferingInfoVO>();
			availableSubOfferingVO.setOfferingInfoList(offeringInfoList);
			availableSubOfferingVO.setOfferingInfoMap(offeringInfoMap);
			for (int i = 0; i < getOfferingInfoNodes.getLength(); i++) {
				Node getOfferingInfoNode = getOfferingInfoNodes.item(i);
				NodeList nodes = getOfferingInfoNode.getChildNodes();
				OfferingInfoVO offeringInfoVO = new OfferingInfoVO();
				offeringInfoList.add(offeringInfoVO);
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
						offeringInfoVO.setMonthlyFee(node.getTextContent());
					} else if ("bas:OneTimeFee".equals(node.getNodeName())) {
						offeringInfoVO.setOneTimeFee(node.getTextContent());
					} else if ("bas:EffectiveTime".equals(node.getNodeName())) {
						offeringInfoVO.setEffectiveTime(node.getTextContent());
					} else if ("bas:ExpiredTime".equals(node.getNodeName())) {
						offeringInfoVO.setExpiredTime(node.getTextContent());
					} else if ("bas:SellCatalogueId".equals(node.getNodeName())) {
						offeringInfoVO.setSellCatalogueId(node.getTextContent());
					} else if ("bas:SellCatalogueId".equals(node.getNodeName())) {
						offeringInfoVO.setSellCatalogueId(node.getTextContent());
					} else if ("bas:SellCatalogureName".equals(node.getNodeName())) {
						offeringInfoVO.setSellCatalogureName(node.getTextContent());
					}
				}
				offeringInfoMap.put(offeringInfoVO.getOfferingId(), offeringInfoVO);
			}
		}
		return result;
	}

}
