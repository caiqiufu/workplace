package com.apps.esb.service.bss.app.crm.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.crm.vo.querycustomerinfo.CustomerInfoVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.AddressInfoVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;

@Service("getCustomerData")
public class GetCustomerData extends SoapMessageHandler implements BizHandler {

	public GetCustomerData() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("getCustomerData");
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
		this.getCRMQuerHeader("GetCustomerRequest", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement requestGetCustomerBodyElement = bodyElement.addChildElement("GetCustomerBody", "quer");
		JSONObject json = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if (!json.has("customerId")) {
			throw new Exception("customerId is null");
		} else {
			String customerId = json.getString("customerId");
			requestGetCustomerBodyElement.addChildElement("CustomerId", "quer").addTextNode(customerId);
		}
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
		if (document.getElementsByTagName("quer:GetCustomerBody").getLength() > 0) {
			Node getCustomerBodyNode = document.getElementsByTagName("quer:GetCustomerBody").item(0);
			List<AddressInfoVO> addressInfoList = new ArrayList<AddressInfoVO>();
			customerInfoVO.setAddressList(addressInfoList);
			NodeList nodes = getCustomerBodyNode.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if ("quer:CustomerId".equals(node.getNodeName())) {
					customerInfoVO.setCustomerId(node.getTextContent());
				} else if ("quer:Title".equals(node.getNodeName())) {
					customerInfoVO.setTitle(node.getTextContent());
				} else if ("quer:FirstName".equals(node.getNodeName())) {
					customerInfoVO.setFirstName(node.getTextContent());
				} else if ("quer:MiddleName".equals(node.getNodeName())) {
					customerInfoVO.setMiddleName(node.getTextContent());
				} else if ("quer:LastName".equals(node.getNodeName())) {
					customerInfoVO.setLastName(node.getTextContent());
				} else if ("quer:Nationality".equals(node.getNodeName())) {
					customerInfoVO.setNationality(node.getTextContent());
				} else if ("quer:CustomerLevel".equals(node.getNodeName())) {
					customerInfoVO.setCustomerLevel(node.getTextContent());
				} else if ("quer:Gender".equals(node.getNodeName())) {
					customerInfoVO.setGender(node.getTextContent());
				} else if ("quer:CertificateType".equals(node.getNodeName())) {
					customerInfoVO.setCertificateType(node.getTextContent());
				} else if ("quer:CertificateNumber".equals(node.getNodeName())) {
					customerInfoVO.setCertificateNumber(node.getTextContent());
				} else if ("quer:CustomerSegment".equals(node.getNodeName())) {
					customerInfoVO.setCustomerSegment(node.getTextContent());
				} else if ("quer:DateOfBirth".equals(node.getNodeName())) {
					customerInfoVO.setDateOfBirth(node.getTextContent());
				} else if ("quer:Status".equals(node.getNodeName())) {
					customerInfoVO.setStatus(node.getTextContent());
				} else if ("quer:AddressList".equals(node.getNodeName())) {
					NodeList addressListNodes = node.getChildNodes();
					for (int j = 0; j < addressListNodes.getLength(); j++) {
						NodeList saddressListNodes = addressListNodes.item(j).getChildNodes();
						AddressInfoVO addressInfoVO = new AddressInfoVO();
						addressInfoList.add(addressInfoVO);
						for (int k = 0; k < saddressListNodes.getLength(); k++) {
							Node saddressListNode = saddressListNodes.item(k);
							if ("bas:AddressInfo".equals(saddressListNode.getNodeName())) {
								addressInfoVO.setAddressClass(saddressListNode.getTextContent());
							} else if ("bas:ContactSeq".equals(saddressListNode.getNodeName())) {
								addressInfoVO.setContactSeq(saddressListNode.getTextContent());
							} else if ("bas:AddressType".equals(saddressListNode.getNodeName())) {
								addressInfoVO.setAddressType(saddressListNode.getTextContent());
							} else if ("bas:ActionType".equals(saddressListNode.getNodeName())) {
								addressInfoVO.setActionType(saddressListNode.getTextContent());
							} else if ("bas:LocalId".equals(saddressListNode.getNodeName())) {
								addressInfoVO.setLocalId(saddressListNode.getTextContent());
							} else if ("bas:Address1".equals(saddressListNode.getNodeName())) {
								addressInfoVO.setAddress1(saddressListNode.getTextContent());
							} else if ("bas:Address2".equals(saddressListNode.getNodeName())) {
								addressInfoVO.setAddress2(saddressListNode.getTextContent());
							} else if ("bas:Address3".equals(saddressListNode.getNodeName())) {
								addressInfoVO.setAddress3(saddressListNode.getTextContent());
							} else if ("bas:Address4".equals(saddressListNode.getNodeName())) {
								addressInfoVO.setAddress4(saddressListNode.getTextContent());
							} else if ("bas:Address5".equals(saddressListNode.getNodeName())) {
								addressInfoVO.setAddress5(saddressListNode.getTextContent());
							} else if ("bas:Address6".equals(saddressListNode.getNodeName())) {
								addressInfoVO.setAddress6(saddressListNode.getTextContent());
							} else if ("bas:Address7".equals(saddressListNode.getNodeName())) {
								addressInfoVO.setAddress7(saddressListNode.getTextContent());
							} else if ("bas:Address8".equals(saddressListNode.getNodeName())) {
								addressInfoVO.setAddress8(saddressListNode.getTextContent());
							} else if ("bas:Address9".equals(saddressListNode.getNodeName())) {
								addressInfoVO.setAddress9(saddressListNode.getTextContent());
							} else if ("bas:Address10".equals(saddressListNode.getNodeName())) {
								addressInfoVO.setAddress10(saddressListNode.getTextContent());
							} else if ("bas:Telephone1".equals(saddressListNode.getNodeName())) {
								addressInfoVO.setTelephone1(saddressListNode.getTextContent());
							} else if ("bas:Telephone2".equals(saddressListNode.getNodeName())) {
								addressInfoVO.setTelephone2(saddressListNode.getTextContent());
							} else if ("bas:Telephone3".equals(saddressListNode.getNodeName())) {
								addressInfoVO.setTelephone3(saddressListNode.getTextContent());
							} else if ("bas:Email1".equals(saddressListNode.getNodeName())) {
								addressInfoVO.setEmail1(saddressListNode.getTextContent());
							} else if ("bas:Email2".equals(saddressListNode.getNodeName())) {
								addressInfoVO.setEmail2(saddressListNode.getTextContent());
							} else if ("bas:Email3".equals(saddressListNode.getNodeName())) {
								addressInfoVO.setEmail3(saddressListNode.getTextContent());
							} else if ("bas:SmsNumber".equals(saddressListNode.getNodeName())) {
								addressInfoVO.setSmsNumber(saddressListNode.getTextContent());
							}
						}

					}
				}
			}
		}
		return result;
	}

}
