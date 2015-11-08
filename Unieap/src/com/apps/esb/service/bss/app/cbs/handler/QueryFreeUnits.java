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
import com.apps.esb.service.bss.app.cbs.vo.queryfreeunit.FreeUnitItemDetailVO;
import com.apps.esb.service.bss.app.cbs.vo.queryfreeunit.FreeUnitItemVO;
import com.apps.esb.service.bss.app.cbs.vo.queryfreeunit.QueryFreeUnitVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;

@Service("queryFreeUnits")
public class QueryFreeUnits extends SoapMessageHandler implements BizHandler{

	public QueryFreeUnits() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters,Map<String,Object> extParameters)
			throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("queryFreeUnits");
		return process(this, newRequestInfo, newRequestInfo.getRequestBody().getServiceNumber(), newRequestInfo.getRequestBody().getExtParameters(), parameters,"ws.cbs.query.timeout");
	}
	public SOAPMessage getRequestSOAPMessage(String serviceNumber, RequestInfo requestInfo) throws Exception {
		SOAPMessage message = messageFactory.createMessage();
		this.getCBSBbHeader("QueryFreeUnitRequestMsg", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement reqestElement = bodyElement.addChildElement("QueryFreeUnitRequest");
		SOAPElement objElement = reqestElement.addChildElement("QueryObj","bbs");
		SOAPElement subAccessCodeElement = objElement.addChildElement("SubAccessCode","bbs");
		SOAPElement primaryIdentityElement = subAccessCodeElement.addChildElement("PrimaryIdentity","bbc");
		primaryIdentityElement.addTextNode(serviceNumber);
		return message;
	}
	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		QueryFreeUnitVO queryFreeUnitVO = new QueryFreeUnitVO();
		ProcessResult result = new ProcessResult();
		result.setVo(queryFreeUnitVO);
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("cbs:ResultCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("cbs:ResultCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("cbs:ResultDesc").getLength() > 0) {

			String retMsg = document.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}
		if (document.getElementsByTagName("bbs:FreeUnitItem").getLength() > 0){
			List<FreeUnitItemVO> freeUnitItemVOs = new ArrayList<FreeUnitItemVO>();
			queryFreeUnitVO.setFreeUnitItemVOList(freeUnitItemVOs);
			NodeList freeUnitItemNodes = document.getElementsByTagName("bbs:FreeUnitItem");
			for(int i = 0 ; i < freeUnitItemNodes.getLength() ; i++){
				Node freeUnitItemNode = freeUnitItemNodes.item(i);
				FreeUnitItemVO freeUnitItemVO = new FreeUnitItemVO();
				List<FreeUnitItemDetailVO> freeUnitItemDetailVOs = new ArrayList<FreeUnitItemDetailVO>();
				freeUnitItemVOs.add(freeUnitItemVO);
				freeUnitItemVO.setFreeUnitItemDetailList(freeUnitItemDetailVOs);
				NodeList nodes = freeUnitItemNode.getChildNodes();
				for(int j = 0 ; j < nodes.getLength() ; j++){
					Node node = nodes.item(j);
					if ("bbs:FreeUnitType".equals(node.getNodeName())){
						freeUnitItemVO.setFreeUnitType(node.getTextContent());
					}else if("bbs:FreeUnitTypeName".equals(node.getNodeName())){
						freeUnitItemVO.setFreeUnitTypeName(node.getTextContent());
					}else if("bbs:FreeUnitTypeName".equals(node.getNodeName())){
						freeUnitItemVO.setFreeUnitTypeName(node.getTextContent());
					}else if("bbs:MeasureUnit".equals(node.getNodeName())){
						freeUnitItemVO.setMeasureUnit(node.getTextContent());
					}else if("bbs:MeasureUnitName".equals(node.getNodeName())){
						freeUnitItemVO.setMeasureUnitName(node.getTextContent());
					}else if("bbs:TotalInitialAmount".equals(node.getNodeName())){
						freeUnitItemVO.setTotalInitialAmount(node.getTextContent());
					}else if("bbs:TotalUnusedAmount".equals(node.getNodeName())){
						freeUnitItemVO.setTotalUnusedAmount(node.getTextContent());
					}else if("bbs:FreeUnitItemDetail".equals(node.getNodeName())){
						NodeList detailNodes = node.getChildNodes();
						FreeUnitItemDetailVO freeUnitItemDetailVO = new FreeUnitItemDetailVO();
						freeUnitItemDetailVOs.add(freeUnitItemDetailVO);
						for(int k = 0 ; k < detailNodes.getLength() ; k ++){
							Node detailNode = detailNodes.item(k);
							if("bbs:FreeUnitInstanceID".equals(detailNode.getNodeName())){
								freeUnitItemDetailVO.setFreeUnitInstanceID(detailNode.getTextContent());
							}else if("bbs:InitialAmount".equals(detailNode.getNodeName())){
								freeUnitItemDetailVO.setInitialAmount(detailNode.getTextContent());
							}else if("bbs:CurrentAmount".equals(detailNode.getNodeName())){
								freeUnitItemDetailVO.setCurrentAmount(detailNode.getTextContent());
							}else if("bbs:EffectiveTime".equals(detailNode.getNodeName())){
								freeUnitItemDetailVO.setEffectiveTime(detailNode.getTextContent());
							}else if("bbs:ExpireTime".equals(detailNode.getNodeName())){
								freeUnitItemDetailVO.setExpireTime(detailNode.getTextContent());
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
