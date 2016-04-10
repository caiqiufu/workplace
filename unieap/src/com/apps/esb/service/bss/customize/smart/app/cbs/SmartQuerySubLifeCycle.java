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
import com.apps.esb.service.bss.app.vo.subscriber.lifecycle.LifeCycleStatusVO;
import com.apps.esb.service.bss.app.vo.subscriber.lifecycle.StatusVO;
import com.apps.esb.service.bss.customize.smart.app.CustSoapMessageHandler;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.interfacecall.SoapCallUtils;

@Service("querySubLifeCycle_1")
public class SmartQuerySubLifeCycle extends CustSoapMessageHandler implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo,Map<String, String> handler, Map<String, Object> extParameters)
			throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("querySubLifeCycle");
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

		LifeCycleStatusVO lifeCycleStatusVO = new LifeCycleStatusVO();
		List<StatusVO> statusList = new ArrayList<StatusVO>();
		StatusVO currentStatus = new StatusVO();
		lifeCycleStatusVO.setCurrentStatus(currentStatus);
		lifeCycleStatusVO.setStatusList(statusList);
		ProcessResult result = new ProcessResult();
		result.setVo(lifeCycleStatusVO);
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
					currentStatus.setStatusIndex(node.getTextContent());
				} else if ("bcs:LifeCycleStatus".equals(node.getNodeName())) {
					StatusVO status = new StatusVO();
					statusList.add(status);
					NodeList lifeCycleStatusNodes = node.getChildNodes();
					for (int j = 0; j < lifeCycleStatusNodes.getLength(); j++) {
						Node lifeCycleStatusNode = lifeCycleStatusNodes.item(j);
						if ("bcs:StatusIndex".equals(lifeCycleStatusNode.getNodeName())) {
							status.setStatusIndex(lifeCycleStatusNode.getTextContent());
						}else if ("bcs:StatusName".equals(lifeCycleStatusNode.getNodeName())) {
							status.setStatusDesc(lifeCycleStatusNode.getTextContent());
						} else if ("bcs:StatusExpireTime".equals(lifeCycleStatusNode.getNodeName())) {
							status.setExpiryTime(lifeCycleStatusNode.getTextContent());
						}
					}
				} else if ("bcs:RBlacklistStatus".equals(node.getNodeName())) {
					lifeCycleStatusVO.setrBlacklistStatus(node.getTextContent());
				} else if ("bcs:FraudTimes".equals(node.getNodeName())) {
					lifeCycleStatusVO.setFraudTimes(node.getTextContent());
				}
			}
		}
		if(statusList.size()>0){
			for(int i = 0 ; i< statusList.size() ; i++){
				StatusVO s = statusList.get(i);
				if(currentStatus.getStatusIndex().equals(s.getStatusIndex())){
					currentStatus.setExpiryTime(s.getExpiryTime());
					currentStatus.setStatusDesc(s.getStatusDesc());
				}
			}
		}
		return result;
	}

}
