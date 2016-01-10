package com.apps.esb.service.bss.customize.smart.app.handler.cbs;

import java.util.HashMap;
import java.util.Map;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.cbs.vo.queryofferingrentcycle.OfferingRentCycleVO;
import com.apps.esb.service.bss.app.cbs.vo.queryofferingrentcycle.QueryOfferingRentCycleVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;

@Service("queryOfferingRentCycle")
public class QueryOfferingRentCycle extends SoapMessageHandler implements BizHandler{

	public QueryOfferingRentCycle() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("queryOfferingRentCycle");
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
		SOAPMessage message = messageFactory.createMessage();
		this.getCBSBcHeader("QueryOfferingRentCycleRequestMsg", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement reqestElement = bodyElement.addChildElement("QueryOfferingRentCycleRequest");
		SOAPElement objElement = reqestElement.addChildElement("OfferingInst","bcs");
		SOAPElement subAccessCodeElement = objElement.addChildElement("SubAccessCode","bcs");
		SOAPElement primaryIdentityElement = subAccessCodeElement.addChildElement("PrimaryIdentity","bcc");
		primaryIdentityElement.addTextNode(serviceNumber);
		
		SOAPElement offeringKeyElement = objElement.addChildElement("OfferingKey","bcs");
		SOAPElement offeringIDElement = offeringKeyElement.addChildElement("OfferingID","bcc");
		offeringIDElement.addTextNode("");
		return message;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		ProcessResult result = new ProcessResult();
		QueryOfferingRentCycleVO queryOfferingRentCycleVO = new QueryOfferingRentCycleVO();
		result.setVo(queryOfferingRentCycleVO);
		Map<String,OfferingRentCycleVO> offeringRentCycleList = new HashMap<String,OfferingRentCycleVO>();
		queryOfferingRentCycleVO.setOfferingRentCycleList(offeringRentCycleList);
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("cbs:ResultCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("cbs:ResultCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("cbs:ResultDesc").getLength() > 0) {

			String retMsg = document.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}
		if (document.getElementsByTagName("bcs:OfferingRentCycle").getLength() > 0){
			NodeList offeringRentCycleNodes = document.getElementsByTagName("bcs:OfferingRentCycle");
			for (int i = 0; i < offeringRentCycleNodes.getLength(); i++){
				OfferingRentCycleVO offeringRentCycleVO = new OfferingRentCycleVO();
				Node rechargeInfoNode = offeringRentCycleNodes.item(i);
				NodeList nodes = rechargeInfoNode.getChildNodes();
				for (int j = 0; j < nodes.getLength(); j++){
					Node node = nodes.item(j);
					if ("bcs:OpenDay".equals(node.getNodeName())) {
						offeringRentCycleVO.setOpenDay(node.getTextContent());
					} else if ("bcs:EndDay".equals(node.getNodeName())) {
						offeringRentCycleVO.setEndDay(node.getTextContent());
					}else if ("bcs:RentAmount".equals(node.getNodeName())) {
						offeringRentCycleVO.setRentAmount(node.getTextContent());
					}
				}
			}
			
		}
		return result;
	}

}
