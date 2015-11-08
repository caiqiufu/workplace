package com.apps.esb.service.bss.app.cbs.handler;

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
import com.apps.esb.service.bss.app.cbs.vo.querysublifecycle.LifeCycleStatusVO;
import com.apps.esb.service.bss.app.cbs.vo.querysublifecycle.QuerySubLifeCycleVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;

@Service("querySubLifeCycle")
public class QuerySubLifeCycle extends SoapMessageHandler implements BizHandler {

	public QuerySubLifeCycle() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("querySubLifeCycle");
		return process(this, newRequestInfo, newRequestInfo.getRequestBody().getServiceNumber(),
				newRequestInfo.getRequestBody().getExtParameters(), parameters, "ws.cbs.query.timeout");
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
		this.getCBSBcHeader("QuerySubLifeCycleRequestMsg", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement reqestElement = bodyElement.addChildElement("QuerySubLifeCycleRequest");
		SOAPElement subAccessCodeElement = reqestElement.addChildElement("SubAccessCode", "bcs");
		SOAPElement primaryIdentityElement = subAccessCodeElement.addChildElement("PrimaryIdentity", "bcc");
		primaryIdentityElement.addTextNode(serviceNumber);
		return message;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {

		QuerySubLifeCycleVO querySubLifeCycleVO = new QuerySubLifeCycleVO();
		List<LifeCycleStatusVO> lifeCycleStatusList = new ArrayList<LifeCycleStatusVO>();
		querySubLifeCycleVO.setLifeCycleStatusList(lifeCycleStatusList);
		ProcessResult result = new ProcessResult();
		result.setVo(querySubLifeCycleVO);
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("cbs:ResultCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("cbs:ResultCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("cbs:ResultDesc").getLength() > 0) {

			String retMsg = document.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}
		if (document.getElementsByTagName("QuerySubLifeCycleResult").getLength() > 0) {
			NodeList nodes = document.getElementsByTagName("QuerySubLifeCycleResult").item(0).getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if ("bcs:CurrentStatusIndex".equals(node.getNodeName())) {
					querySubLifeCycleVO.setCurrentStatusIndex(node.getTextContent());
				} else if ("bcs:LifeCycleStatus".equals(node.getNodeName())) {
					LifeCycleStatusVO lifeCycleStatusVO = new LifeCycleStatusVO();
					lifeCycleStatusList.add(lifeCycleStatusVO);
					NodeList lifeCycleStatusNodes = node.getChildNodes();
					for (int j = 0; j < lifeCycleStatusNodes.getLength(); j++) {
						Node lifeCycleStatusNode = lifeCycleStatusNodes.item(j);
						if ("bcs:StatusName".equals(lifeCycleStatusNode.getNodeName())) {
							lifeCycleStatusVO.setStatusName(lifeCycleStatusNode.getTextContent());
						} else if ("bcs:StatusExpireTime".equals(lifeCycleStatusNode.getNodeName())) {
							lifeCycleStatusVO.setStatusExpireTime(lifeCycleStatusNode.getTextContent());
						} else if ("bcs:StatusIndex".equals(lifeCycleStatusNode.getNodeName())) {
							lifeCycleStatusVO.setStatusIndex(lifeCycleStatusNode.getTextContent());
						}
					}
				} else if ("bcs:RBlacklistStatus".equals(node.getNodeName())) {
					querySubLifeCycleVO.setrBlacklistStatus(node.getTextContent());
				} else if ("bcs:FraudTimes".equals(node.getNodeName())) {
					querySubLifeCycleVO.setFraudTimes(node.getTextContent());
				}
			}
		}
		return result;
	}

}
