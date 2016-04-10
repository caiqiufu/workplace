package com.apps.esb.service.bss.customize.smart.app.cbs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.vo.subscriber.accumulate.AccumulateUsageVO;
import com.apps.esb.service.bss.app.vo.subscriber.accumulate.QueryAccumulateUsageVO;
import com.apps.esb.service.bss.customize.smart.app.CustSoapMessageHandler;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.interfacecall.SoapCallUtils;

@Service("queryAccumulationUsage_1")
public class SmartQueryAccumulationUsage extends CustSoapMessageHandler implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("queryAccumulationUsage");
		return SoapCallUtils.process(this, newRequestInfo, newRequestInfo.getRequestBody().getServiceNumber(),
				newRequestInfo.getRequestBody().getExtParameters(), handler, "ws.cbs.query.timeout");

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
		this.getCBSBcHeader("QueryAccumulationUsageRequestMsg", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement reqestElement = bodyElement.addChildElement("QueryAccumulationUsageRequest");
		SOAPElement objElement = reqestElement.addChildElement("QueryObj", "bcs");
		SOAPElement subAccessCodeElement = objElement.addChildElement("SubAccessCode", "bcs");
		SOAPElement primaryIdentityElement = subAccessCodeElement.addChildElement("PrimaryIdentity", "bcc");
		primaryIdentityElement.addTextNode(serviceNumber);
		reqestElement.addChildElement("AccmType", "bcs").addTextNode("-1");
		return message;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		QueryAccumulateUsageVO queryAccumulateUsageVO = new QueryAccumulateUsageVO();
		List<AccumulateUsageVO> accumulateUsageList = new ArrayList<AccumulateUsageVO>();
		queryAccumulateUsageVO.setAccumulateUsageList(accumulateUsageList);
		ProcessResult result = new ProcessResult();
		result.setVo(queryAccumulateUsageVO);
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("cbs:ResultCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("cbs:ResultCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("cbs:ResultDesc").getLength() > 0) {

			String retMsg = document.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}
		if (document.getElementsByTagName("QueryAccumulationUsageResult").getLength() > 0) {
			NodeList nodes = document.getElementsByTagName("bcs:AccmUsageList");
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				NodeList accmUsageListNodes = node.getChildNodes();
				AccumulateUsageVO accumulateUsageVO = new AccumulateUsageVO();
				accumulateUsageList.add(accumulateUsageVO);
				for (int j = 0; j < accmUsageListNodes.getLength(); j++) {
					Node accmUsageListNode = accmUsageListNodes.item(j);
					if ("bcs:AccmType".equals(accmUsageListNode.getNodeName())) {
						accumulateUsageVO.setAccumulateType(accmUsageListNode.getTextContent());
					} else if ("bcs:AccmTypeName".equals(accmUsageListNode.getNodeName())) {
						accumulateUsageVO.setAccumulateTypeDesc(accmUsageListNode.getTextContent());
					} else if ("bcs:Amount".equals(accmUsageListNode.getNodeName())) {
						accumulateUsageVO.setAmount(accmUsageListNode.getTextContent());
					} /*
						 * else if
						 * ("bcs:UnitType".equals(accmUsageListNode.getNodeName(
						 * ))) {
						 * accumulateUsageVO.setMeasureUnit(accmUsageListNode.
						 * getTextContent()); }
						 */ else if ("bcs:MeasureID".equals(accmUsageListNode.getNodeName())) {
						accumulateUsageVO.setMeasureUnit(accmUsageListNode.getTextContent());
					} else if ("bcs:BeginDate".equals(accmUsageListNode.getNodeName())) {
						accumulateUsageVO.setBeginDate(accmUsageListNode.getTextContent());
					} else if ("bcs:EndDate".equals(accmUsageListNode.getNodeName())) {
						accumulateUsageVO.setEndDate(accmUsageListNode.getTextContent());
					}
				}
			}
		}
		return result;
	}
}
