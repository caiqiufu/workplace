package com.apps.esb.service.bss.customize.smart.app.crm;

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
import com.apps.esb.service.bss.app.vo.common.address.AddressVO;
import com.apps.esb.service.bss.app.vo.customer.basicinfo.CustomerVO;
import com.apps.esb.service.bss.customize.smart.app.CustSoapMessageHandler;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.interfacecall.SoapCallUtils;
import com.unieap.base.SYSConfig;

@Service("queryCorporateGroupMember_1")
public class SmartGetCorpGroupMembers extends CustSoapMessageHandler implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler, Map<String, Object> extParameters)
			throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("queryCorporateGroupMember");
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
		JSONObject json = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if (!json.has("groupId")) {
			throw new Exception("groupId is null");
		}
		SOAPMessage message = SoapCallUtils.getMessageFactory().createMessage();
		this.getCRMQuerHeader("GetGroupMemberDataRequest", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement getGroupMemberBodyElement = bodyElement.addChildElement("GetGroupMemberBody", "quer");
		getGroupMemberBodyElement.addChildElement("GroupId", "quer").addTextNode(json.getString("groupId"));
		if (!json.has("totalNum")&&!json.has("page")) {
			int pagesize = Integer.parseInt(SYSConfig.getConfig().get("mcare.display.cdr.pagesize"));
			int startRow = (Integer.parseInt(json.getString("page"))-1)*pagesize+1;
			getGroupMemberBodyElement.addChildElement("StartRow", "quer").addTextNode(Integer.toString(startRow));
			getGroupMemberBodyElement.addChildElement("PageSize", "quer").addTextNode(Integer.toString(pagesize));
		}
		return message;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		CustomerVO customerVO = new CustomerVO();
		ProcessResult result = new ProcessResult();
		result.setVo(customerVO);
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
			List<AddressVO> addressList = new ArrayList<AddressVO>();
			customerVO.setAddressList(addressList);
			NodeList nodes = getCustomerBodyNode.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if ("quer:CustomerId".equals(node.getNodeName())) {
					customerVO.setCustomerId(node.getTextContent());
				} else if ("quer:Title".equals(node.getNodeName())) {
					customerVO.setTitle(node.getTextContent());
				} else if ("quer:FirstName".equals(node.getNodeName())) {
					customerVO.setFirstName(node.getTextContent());
				} else if ("quer:MiddleName".equals(node.getNodeName())) {
					customerVO.setMiddleName(node.getTextContent());
				} else if ("quer:LastName".equals(node.getNodeName())) {
					customerVO.setLastName(node.getTextContent());
				} else if ("quer:Nationality".equals(node.getNodeName())) {
					customerVO.setNationality(node.getTextContent());
				} else if ("quer:CustomerLevel".equals(node.getNodeName())) {
					customerVO.setCustomerLevel(node.getTextContent());
				} else if ("quer:Gender".equals(node.getNodeName())) {
					customerVO.setGender(node.getTextContent());
				} else if ("quer:CertificateType".equals(node.getNodeName())) {
					customerVO.setCertificateType(node.getTextContent());
				} else if ("quer:CertificateNumber".equals(node.getNodeName())) {
					customerVO.setCertificateNumber(node.getTextContent());
				}  else if ("quer:DateOfBirth".equals(node.getNodeName())) {
					customerVO.setDateOfBirth(node.getTextContent());
				} else if ("quer:Status".equals(node.getNodeName())) {
					customerVO.setStatus(node.getTextContent());
				} else if ("quer:AddressList".equals(node.getNodeName())) {
					NodeList addressListNodes = node.getChildNodes();
					for (int j = 0; j < addressListNodes.getLength(); j++) {
						NodeList saddressListNodes = addressListNodes.item(j).getChildNodes();
						AddressVO addressVO = new AddressVO();
						addressList.add(addressVO);
						for (int k = 0; k < saddressListNodes.getLength(); k++) {
							Node saddressListNode = saddressListNodes.item(k);
							if ("bas:AddressInfo".equals(saddressListNode.getNodeName())) {
								addressVO.setAddressClass(saddressListNode.getTextContent());
							} else if ("bas:ContactSeq".equals(saddressListNode.getNodeName())) {
								addressVO.setContactSeq(saddressListNode.getTextContent());
							} else if ("bas:AddressType".equals(saddressListNode.getNodeName())) {
								addressVO.setAddressType(saddressListNode.getTextContent());
							} else if ("bas:ActionType".equals(saddressListNode.getNodeName())) {
								addressVO.setActionType(saddressListNode.getTextContent());
							} else if ("bas:LocalId".equals(saddressListNode.getNodeName())) {
								addressVO.setLocalId(saddressListNode.getTextContent());
							} else if ("bas:Address1".equals(saddressListNode.getNodeName())) {
								addressVO.setAddress1(saddressListNode.getTextContent());
							} else if ("bas:Address2".equals(saddressListNode.getNodeName())) {
								addressVO.setAddress2(saddressListNode.getTextContent());
							} else if ("bas:Address3".equals(saddressListNode.getNodeName())) {
								addressVO.setAddress3(saddressListNode.getTextContent());
							} else if ("bas:Address4".equals(saddressListNode.getNodeName())) {
								addressVO.setAddress4(saddressListNode.getTextContent());
							} else if ("bas:Address5".equals(saddressListNode.getNodeName())) {
								addressVO.setAddress5(saddressListNode.getTextContent());
							} else if ("bas:Address6".equals(saddressListNode.getNodeName())) {
								addressVO.setAddress6(saddressListNode.getTextContent());
							} else if ("bas:Address7".equals(saddressListNode.getNodeName())) {
								addressVO.setAddress7(saddressListNode.getTextContent());
							} else if ("bas:Address8".equals(saddressListNode.getNodeName())) {
								addressVO.setAddress8(saddressListNode.getTextContent());
							} else if ("bas:Address9".equals(saddressListNode.getNodeName())) {
								addressVO.setAddress9(saddressListNode.getTextContent());
							} else if ("bas:Address10".equals(saddressListNode.getNodeName())) {
								addressVO.setAddress10(saddressListNode.getTextContent());
							} else if ("bas:Telephone1".equals(saddressListNode.getNodeName())) {
								addressVO.setTelephone1(saddressListNode.getTextContent());
							} else if ("bas:Telephone2".equals(saddressListNode.getNodeName())) {
								addressVO.setTelephone2(saddressListNode.getTextContent());
							} else if ("bas:Telephone3".equals(saddressListNode.getNodeName())) {
								addressVO.setTelephone3(saddressListNode.getTextContent());
							} else if ("bas:Email1".equals(saddressListNode.getNodeName())) {
								addressVO.setEmail1(saddressListNode.getTextContent());
							} else if ("bas:Email2".equals(saddressListNode.getNodeName())) {
								addressVO.setEmail2(saddressListNode.getTextContent());
							} else if ("bas:Email3".equals(saddressListNode.getNodeName())) {
								addressVO.setEmail3(saddressListNode.getTextContent());
							} else if ("bas:SmsNumber".equals(saddressListNode.getNodeName())) {
								addressVO.setPhoneNumber(saddressListNode.getTextContent());
							}
						}

					}
				}
			}
		}
		return result;
	}

}
