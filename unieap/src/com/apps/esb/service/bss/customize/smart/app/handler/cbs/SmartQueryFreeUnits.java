package com.apps.esb.service.bss.customize.smart.app.handler.cbs;

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
import com.apps.esb.service.bss.app.vo.subscriber.freeresource.FreeResourceDetailVO;
import com.apps.esb.service.bss.app.vo.subscriber.freeresource.FreeResourceVO;
import com.apps.esb.service.bss.app.vo.subscriber.freeresource.QueryFreeResourceVO;
import com.apps.esb.service.bss.customize.smart.app.handler.CustSoapMessageHandler;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.interfacecall.SoapCallUtils;

@Service("queryFreeReource_1")
public class SmartQueryFreeUnits extends CustSoapMessageHandler implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("queryFreeReource");
		return SoapCallUtils.process(this, newRequestInfo, newRequestInfo.getRequestBody().getServiceNumber(),
				newRequestInfo.getRequestBody().getExtParameters(), handler, "ws.cbs.query.timeout");
	}

	public SOAPMessage getRequestSOAPMessage(String serviceNumber, RequestInfo requestInfo) throws Exception {
		SOAPMessage message = SoapCallUtils.getMessageFactory().createMessage();
		this.getCBSBbHeader("QueryFreeUnitRequestMsg", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement reqestElement = bodyElement.addChildElement("QueryFreeUnitRequest");
		SOAPElement objElement = reqestElement.addChildElement("QueryObj", "bbs");
		SOAPElement subAccessCodeElement = objElement.addChildElement("SubAccessCode", "bbs");
		SOAPElement primaryIdentityElement = subAccessCodeElement.addChildElement("PrimaryIdentity", "bbc");
		primaryIdentityElement.addTextNode(serviceNumber);
		return message;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		QueryFreeResourceVO queryFreeResourceVO = new QueryFreeResourceVO();
		ProcessResult result = new ProcessResult();
		result.setVo(queryFreeResourceVO);
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("cbs:ResultCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("cbs:ResultCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("cbs:ResultDesc").getLength() > 0) {

			String retMsg = document.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}
		if (document.getElementsByTagName("bbs:FreeUnitItem").getLength() > 0) {
			List<FreeResourceVO> freeResourceList = new ArrayList<FreeResourceVO>();
			queryFreeResourceVO.setFreeResourceList(freeResourceList);
			NodeList freeUnitItemNodes = document.getElementsByTagName("bbs:FreeUnitItem");
			for (int i = 0; i < freeUnitItemNodes.getLength(); i++) {
				Node freeUnitItemNode = freeUnitItemNodes.item(i);
				FreeResourceVO freeResourceVO = new FreeResourceVO();
				List<FreeResourceDetailVO> freeResourceDetailList = new ArrayList<FreeResourceDetailVO>();
				freeResourceList.add(freeResourceVO);
				freeResourceVO.setFreeResourceDetailList(freeResourceDetailList);
				NodeList nodes = freeUnitItemNode.getChildNodes();
				for (int j = 0; j < nodes.getLength(); j++) {
					Node node = nodes.item(j);
					if ("bbs:FreeUnitType".equals(node.getNodeName())) {
						freeResourceVO.setFreeResourceCode(node.getTextContent());
					} else if ("bbs:FreeUnitTypeName".equals(node.getNodeName())) {
						freeResourceVO.setFreeResourceName(node.getTextContent());
					} else if ("bbs:MeasureUnit".equals(node.getNodeName())) {
						freeResourceVO.setMeasureUnit(node.getTextContent());
						if ("1003".equals(freeResourceVO.getMeasureUnit())) {
							freeResourceVO.setFreeResourceType("2");
							freeResourceVO.setFreeResourceTypeDesc("voice");
						} else if ("1106".equals(freeResourceVO.getMeasureUnit())) {
							freeResourceVO.setFreeResourceType("3");
							freeResourceVO.setFreeResourceTypeDesc("data");
						} else if ("1107".equals(freeResourceVO.getMeasureUnit())) {
							freeResourceVO.setFreeResourceType("3");
							freeResourceVO.setFreeResourceTypeDesc("data");
						} else if ("1108".equals(freeResourceVO.getMeasureUnit())) {
							freeResourceVO.setFreeResourceType("3");
							freeResourceVO.setFreeResourceTypeDesc("data");
						} else if ("1109".equals(freeResourceVO.getMeasureUnit())) {
							freeResourceVO.setFreeResourceType("3");
							freeResourceVO.setFreeResourceTypeDesc("data");
						}

					} else if ("bbs:MeasureUnitName".equals(node.getNodeName())) {
						freeResourceVO.setMeasureUnitDesc(node.getTextContent());
					} else if ("bbs:TotalInitialAmount".equals(node.getNodeName())) {
						freeResourceVO.setTotalInitialAmount(node.getTextContent());
					} else if ("bbs:TotalUnusedAmount".equals(node.getNodeName())) {
						freeResourceVO.setTotalUnusedAmount(node.getTextContent());
					} else if ("bbs:FreeUnitItemDetail".equals(node.getNodeName())) {
						NodeList detailNodes = node.getChildNodes();
						FreeResourceDetailVO freeResourceDetailVO = new FreeResourceDetailVO();
						freeResourceDetailList.add(freeResourceDetailVO);
						for (int k = 0; k < detailNodes.getLength(); k++) {
							Node detailNode = detailNodes.item(k);
							if ("bbs:FreeUnitInstanceID".equals(detailNode.getNodeName())) {
								freeResourceDetailVO.setFreeResourceInstanceID(detailNode.getTextContent());
							} else if ("bbs:InitialAmount".equals(detailNode.getNodeName())) {
								freeResourceDetailVO.setInitialAmount(detailNode.getTextContent());
							} else if ("bbs:CurrentAmount".equals(detailNode.getNodeName())) {
								freeResourceDetailVO.setCurrentAmount(detailNode.getTextContent());
							} else if ("bbs:EffectiveTime".equals(detailNode.getNodeName())) {
								freeResourceDetailVO.setEffectiveTime(detailNode.getTextContent());
							} else if ("bbs:ExpireTime".equals(detailNode.getNodeName())) {
								freeResourceDetailVO.setExpiryTime(detailNode.getTextContent());
							}
						}
					}
				}
			}
		}
		return result;
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

}
