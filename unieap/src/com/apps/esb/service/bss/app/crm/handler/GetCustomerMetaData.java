package com.apps.esb.service.bss.app.crm.handler;

import java.util.Map;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.crm.vo.querycustomerinfo.CustomerInfoVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;

@Service("getCustomerMetaData")
public class GetCustomerMetaData extends SoapMessageHandler implements BizHandler {

	public GetCustomerMetaData() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("getCustomerMetaData");
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
		this.getCRMQuerHeader("GetCustomerMetaDataRequest", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement requestGetCustomerMetaDataBodyElement = bodyElement.addChildElement("GetCustomerMetaDataBody",
				"quer");
		requestGetCustomerMetaDataBodyElement.addChildElement("ServiceNumber", "quer")
				.addTextNode(requestInfo.getRequestBody().getServiceNumber());
		return message;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		CustomerInfoVO customerInfoVO = new CustomerInfoVO();
		ProcessResult result = new ProcessResult();
		result.setVo(customerInfoVO);
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("bas:RetCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("bas:RetCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("bas:RetMsg").getLength() > 0) {

			String retMsg = document.getElementsByTagName("bas:RetMsg").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}
		if (document.getElementsByTagName("quer:GetCustomerMetaDataBody").getLength() > 0) {
			Node getCustomerBodyNode = document.getElementsByTagName("quer:GetCustomerMetaDataBody").item(0);
			NodeList nodes = getCustomerBodyNode.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if ("quer:CustomerId".equals(node.getNodeName())) {
					customerInfoVO.setCustomerId(node.getTextContent());
				} else if ("quer:Status".equals(node.getNodeName())) {
					customerInfoVO.setStatus(node.getTextContent());
				}
			}
		}
		return result;
	}

}
