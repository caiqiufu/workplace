package com.apps.esb.service.bss.handler.query;

import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.apps.esb.service.bss.app.crm.vo.OfferingVO;
import com.apps.esb.service.bss.app.crm.vo.ServiceVO;
import com.apps.esb.service.bss.app.crm.vo.SubscriberVO;
import com.apps.esb.service.bss.app.vo.CustomerVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.tools.JSONUtils;

@Service("querySubscriberInfo")
public class QuerySubscriberInfo extends SoapMessageHandler implements BizHandler {

	public QuerySubscriberInfo() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String serviceNumber, String extParameters, String parameters)
			throws Exception {
		if(StringUtils.isEmpty(serviceNumber)){
			throw new Exception("serviceNumber is null");
		}
		return process(this, requestInfo, serviceNumber, extParameters, parameters,"ws.crm.query.timeout");
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
		this.getCRMQuerHeader("GetSubscriberRequest", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement requestGetCustomerBodyElement = bodyElement.addChildElement("GetSubscriberBody");
		requestGetCustomerBodyElement.addChildElement("ServiceNumber").addTextNode(serviceNumber);
		return message;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		ProcessResult result = new ProcessResult();
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		SubscriberVO vo = new SubscriberVO();
		if (document.getElementsByTagName("bas:RetCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("bas:RetCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("bas:RetMsg").getLength() > 0) {

			String retMsg = document.getElementsByTagName("bas:RetMsg").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}
		////////////////////////////////////
		CustomerVO customerVo = new CustomerVO();
		if (document.getElementsByTagName("quer:ActualCustomer").getLength() > 0) {
			vo.setCustomerVO(customerVo);
			if (document.getElementsByTagName("bas:CorpNo").getLength() > 0) {
				String corpNo = document.getElementsByTagName("bas:CorpNo").item(0).getTextContent();
				customerVo.setCorpNo(corpNo);
			}
			if (document.getElementsByTagName("bas:CustName").getLength() > 0) {
				String corpName = document.getElementsByTagName("bas:CustName").item(0).getTextContent();
				customerVo.setCorpName(corpName);
			}
			StringBuffer customerName = new StringBuffer();
			if (document.getElementsByTagName("bas:FirstName").getLength() > 0) {
				String firstName = document.getElementsByTagName("bas:FirstName").item(0).getTextContent();
				customerName.append(firstName);
			}
			if (document.getElementsByTagName("bas:MiddleName").getLength() > 0) {
				String middleName = document.getElementsByTagName("bas:MiddleName").item(0).getTextContent();
				customerName.append(" ").append(middleName);
			}
			if (document.getElementsByTagName("bas:LastName").getLength() > 0) {
				String lastName = document.getElementsByTagName("bas:LastName").item(0).getTextContent();
				customerName.append(" ").append(lastName);
			}
			customerVo.setCustomerName(customerName.toString());
			StringBuffer address = new StringBuffer();
			if (document.getElementsByTagName("bas:AddressInfo").getLength() > 0) {
				if (document.getElementsByTagName("bas:Address1").getLength() > 0) {
					String address1 = document.getElementsByTagName("bas:Address1").item(0).getTextContent();
					address.append(" ").append(address1);

				}
				if (document.getElementsByTagName("bas:Address2").getLength() > 0) {
					String address2 = document.getElementsByTagName("bas:Address2").item(0).getTextContent();
					address.append(" ").append(address2);

				}
				if (document.getElementsByTagName("bas:Address3").getLength() > 0) {
					String address3 = document.getElementsByTagName("bas:Address3").item(0).getTextContent();
					address.append(" ").append(address3);

				}
				if (document.getElementsByTagName("bas:Address4").getLength() > 0) {
					String address4 = document.getElementsByTagName("bas:Address4").item(0).getTextContent();
					address.append(" ").append(address4);

				}
				if (document.getElementsByTagName("bas:Address5").getLength() > 0) {
					String address5 = document.getElementsByTagName("bas:Address5").item(0).getTextContent();
					address.append(" ").append(address5);

				}
				if (document.getElementsByTagName("bas:Address6").getLength() > 0) {
					String address6 = document.getElementsByTagName("bas:Address6").item(0).getTextContent();
					address.append(" ").append(address6);

				}
				customerVo.setAddress(address.toString());
				if (document.getElementsByTagName("bas:SmsNumber").getLength() > 0) {
					String smsNumber = document.getElementsByTagName("bas:SmsNumber").item(0).getTextContent();
					customerVo.setContactNo(smsNumber);

				}
				if (document.getElementsByTagName("bas:Email1").getLength() > 0) {
					String email = document.getElementsByTagName("bas:Email1").item(0).getTextContent();
					customerVo.setEmail(email);

				}
			}

		}
		if (document.getElementsByTagName("quer:EffectiveDate").getLength() > 0) {
			String effectiveDate = document.getElementsByTagName("quer:EffectiveDate").item(0).getTextContent();
			vo.setActiveDate(effectiveDate);
			customerVo.setActiveDate(effectiveDate);

		}
		if (document.getElementsByTagName("quer:SubscriberType").getLength() > 0) {
			String subscriberType = document.getElementsByTagName("quer:SubscriberType").item(0).getTextContent();
			vo.setSubscriberType(subscriberType);
			customerVo.setSubscriberType(subscriberType);
		}
		if (document.getElementsByTagName("quer:Status").getLength() > 0) {
			String status = document.getElementsByTagName("quer:Status").item(0).getTextContent();
			vo.setStatus(status);
			customerVo.setStatus(status);
		}
		if (document.getElementsByTagName("quer:StatusReason").getLength() > 0) {
			String statusReason = document.getElementsByTagName("quer:StatusReason").item(0).getTextContent();
			vo.setStatusReason(statusReason);
			customerVo.setStatusReason(statusReason);
		}
		if (document.getElementsByTagName("quer:PrimaryOffering").getLength() > 0) {
			OfferingVO offeringVO = new OfferingVO();
			vo.setPrimaryOffering(offeringVO);
			NodeList nodes = document.getElementsByTagName("quer:PrimaryOffering");
			if (nodes != null && nodes.getLength() > 0) {
				for (int i = 0; i < nodes.getLength(); i++) {
					Node node = nodes.item(i);
					if ("bas:OfferingId".equals(node.getNodeName())) {
						NodeList offeringNodes = node.getChildNodes();
						for (int j = 0; j < offeringNodes.getLength(); j++) {
							Node offeringNode = offeringNodes.item(j);
							if ("bas:OfferingId".equals(offeringNode.getNodeName())) {
								offeringVO.setOfferingId(offeringNode.getTextContent());
							} else if ("bas:OfferingCode".equals(offeringNode.getNodeName())) {
								offeringVO.setOfferingCode(offeringNode.getTextContent());
							} else if ("bas:OfferingCode".equals(offeringNode.getNodeName())) {
								offeringVO.setOfferingCode(offeringNode.getTextContent());
							}
						}
					} else if ("bas:OfferingName".equals(node.getNodeName())) {
						offeringVO.setOfferingName(node.getTextContent());
					} else if ("bas:Status".equals(node.getNodeName())) {
						offeringVO.setStatus(node.getTextContent());
					} else if ("bas:CreateDate".equals(node.getNodeName())) {
						offeringVO.setStatus(node.getTextContent());
					} else if ("bas:EffectiveTime".equals(node.getNodeName())) {
						offeringVO.setStatus(node.getTextContent());
					} else if ("bas:ExpiredTime".equals(node.getNodeName())) {
						offeringVO.setStatus(node.getTextContent());
					} else if ("bas:ProductList".equals(node.getNodeName())) {
						NodeList productListNodes = node.getChildNodes();
						List<ServiceVO> services = getServiceList(productListNodes);
						vo.setServices(services);
					}
				}
			}
		}
		if (document.getElementsByTagName("quer:SupplementaryOfferingList").getLength() > 0) {
			NodeList supplementaryNodes = document.getElementsByTagName("bas:GetSubOfferingInfo");
			if (supplementaryNodes != null && supplementaryNodes.getLength() > 0) {
				for (int i = 0; i < supplementaryNodes.getLength(); i++) {
					OfferingVO offeringVO = new OfferingVO();
					Node supplementaryNode = supplementaryNodes.item(i);
					if ("bas:GetSubOfferingInfo".equals(supplementaryNode.getNodeName())) {
						NodeList setSubOfferingInfoNodes = supplementaryNode.getChildNodes();
						if (setSubOfferingInfoNodes != null && setSubOfferingInfoNodes.getLength() > 0) {
							for (int j = 0; j < setSubOfferingInfoNodes.getLength(); j++) {
								Node setSubOfferingInfoNode = setSubOfferingInfoNodes.item(j);
								if ("bas:OfferingId".equals(setSubOfferingInfoNode.getNodeName())) {
									NodeList offeringNodes = setSubOfferingInfoNode.getChildNodes();
									for (int k = 0; k < offeringNodes.getLength(); k++) {
										Node offeringNode = offeringNodes.item(k);
										if ("bas:OfferingId".equals(offeringNode.getNodeName())) {
											offeringVO.setOfferingId(offeringNode.getTextContent());
										} else if ("bas:OfferingCode".equals(offeringNode.getNodeName())) {
											offeringVO.setOfferingCode(offeringNode.getTextContent());
										} else if ("bas:OfferingCode".equals(offeringNode.getNodeName())) {
											offeringVO.setOfferingCode(offeringNode.getTextContent());
										}
									}
								} else if ("bas:OfferingName".equals(setSubOfferingInfoNode.getNodeName())) {
									offeringVO.setOfferingName(setSubOfferingInfoNode.getTextContent());
								} else if ("bas:Status".equals(setSubOfferingInfoNode.getNodeName())) {
									offeringVO.setStatus(setSubOfferingInfoNode.getTextContent());
								} else if ("bas:CreateDate".equals(setSubOfferingInfoNode.getNodeName())) {
									offeringVO.setStatus(setSubOfferingInfoNode.getTextContent());
								} else if ("bas:EffectiveTime".equals(setSubOfferingInfoNode.getNodeName())) {
									offeringVO.setStatus(setSubOfferingInfoNode.getTextContent());
								} else if ("bas:ExpiredTime".equals(setSubOfferingInfoNode.getNodeName())) {
									offeringVO.setStatus(setSubOfferingInfoNode.getTextContent());
								} else if ("bas:ProductList".equals(setSubOfferingInfoNode.getNodeName())) {
									NodeList productListNodes = setSubOfferingInfoNode.getChildNodes();
									List<ServiceVO> services = getServiceList(productListNodes);
									if (vo.getServices() == null) {
										vo.setServices(services);
									} else {
										vo.getServices().addAll(services);
									}
								}
							}
						}
					}
				}
			}
		}
		String jsonCustomerInfo = JSONUtils.convertBean2JSON(vo).toString();
		result.setExtParameters(jsonCustomerInfo);
		return result;
	}

	public java.util.List<ServiceVO> getServiceList(NodeList productListNodes) {
		java.util.List<ServiceVO> services = new ArrayList<ServiceVO>();
		if (productListNodes != null && productListNodes.getLength() > 0) {
			for (int j = 0; j < productListNodes.getLength(); j++) {
				Node productListNode = productListNodes.item(j);
				if ("bas:GetSubProductInfo".equals(productListNode.getNodeName())) {
					NodeList getSubProductInfoNodes = productListNode.getChildNodes();
					if (getSubProductInfoNodes != null && getSubProductInfoNodes.getLength() > 0) {
						for (int k = 0; k < getSubProductInfoNodes.getLength(); k++) {
							Node getSubProductInfoNode = getSubProductInfoNodes.item(k);
							if ("bas:GetSubProductInfo".equals(getSubProductInfoNode.getNodeName())) {
								NodeList subProductInfoNodes = getSubProductInfoNode.getChildNodes();
								for (int l = 0; l < subProductInfoNodes.getLength(); l++) {
									Node subProductInfoNode = subProductInfoNodes.item(l);
									if ("bas:ServiceList".equals(subProductInfoNode.getNodeName())) {
										NodeList serviceListNodes = subProductInfoNode.getChildNodes();
										for (int m = 0; serviceListNodes.getLength() < m; m++) {
											Node serviceListNode = serviceListNodes.item(m);
											if ("bas:ServiceList".equals(serviceListNode.getNodeName())) {
												NodeList subServiceListNode = serviceListNode.getChildNodes();
												for (int n = 0; n < subServiceListNode.getLength(); n++) {
													Node serviceNode = subServiceListNode.item(n);
													ServiceVO serviceVO = new ServiceVO();
													if ("bas:ServiceId".equals(serviceNode.getNodeName())) {
														serviceVO.setServiceId(serviceNode.getTextContent());
													} else if ("bas:ServiceName".equals(serviceNode.getNodeName())) {
														serviceVO.setServiceName(serviceNode.getTextContent());
													} else if ("bas:ServiceType".equals(serviceNode.getNodeName())) {
														serviceVO.setServiceType(serviceNode.getTextContent());
													} else
														if ("bas:ServiceCategory".equals(serviceNode.getNodeName())) {
														serviceVO.setServiceCategory(serviceNode.getTextContent());
													} else if ("bas:IsMainService".equals(serviceNode.getNodeName())) {
														serviceVO.setIsMainService(serviceNode.getTextContent());
													} else if ("bas:ServiceStatus".equals(serviceNode.getNodeName())) {
														serviceVO.setServiceStatus(serviceNode.getTextContent());
													}
													services.add(serviceVO);
												}
											}
										}
									}
								}
							}
						}

					}
				}
			}
		}
		return services;
	}
}
