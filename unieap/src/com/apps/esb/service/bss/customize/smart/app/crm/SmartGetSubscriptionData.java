package com.apps.esb.service.bss.customize.smart.app.crm;

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
import com.apps.esb.service.bss.app.vo.common.address.AddressVO;
import com.apps.esb.service.bss.app.vo.customer.basicinfo.CorporateVO;
import com.apps.esb.service.bss.app.vo.customer.basicinfo.CustomerVO;
import com.apps.esb.service.bss.app.vo.subscriber.basicinfo.SubscriberVO;
import com.apps.esb.service.bss.app.vo.subscriber.limitation.ConsumptionLimitVO;
import com.apps.esb.service.bss.app.vo.subscriber.offering.OfferingVO;
import com.apps.esb.service.bss.app.vo.subscriber.offering.ServiceVO;
import com.apps.esb.service.bss.app.vo.subscriber.sim.SimCardVO;
import com.apps.esb.service.bss.customize.smart.app.CustSoapMessageHandler;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.interfacecall.SoapCallUtils;

@Service("smartQuerySubscriber")
public class SmartGetSubscriptionData extends CustSoapMessageHandler implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("getSubscriptionData");
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
		SOAPMessage message = SoapCallUtils.getMessageFactory().createMessage();
		this.getCRMQuerHeader("GetSubscriberRequest", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement requestGetCustomerBodyElement = bodyElement.addChildElement("GetSubscriberBody", "quer");
		requestGetCustomerBodyElement.addChildElement("IncludeOfferFlag", "quer").addTextNode("1");
		requestGetCustomerBodyElement.addChildElement("ServiceNumber", "quer").addTextNode(serviceNumber);
		return message;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		SubscriberVO subscriberVO = new SubscriberVO();
		ProcessResult result = new ProcessResult();
		result.setVo(subscriberVO);
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("bas:RetCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("bas:RetCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("bas:RetMsg").getLength() > 0) {

			String retMsg = document.getElementsByTagName("bas:RetMsg").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}
		if (document.getElementsByTagName("quer:GetSubscriberBody").getLength() > 0) {
			Node getSubscriberBodyNode = document.getElementsByTagName("quer:GetSubscriberBody").item(0);
			CustomerVO customerVO = new CustomerVO();
			OfferingVO primaryOfferingVO = new OfferingVO();
			List<OfferingVO> supplementaryOfferingList = new ArrayList<OfferingVO>();
			List<ConsumptionLimitVO> consumptionLimitList = new ArrayList<ConsumptionLimitVO>();
			SimCardVO simCardVO = new SimCardVO();
			// List<AddressVO> addressList = new ArrayList<AddressVO>();
			subscriberVO.setCustomerVO(customerVO);
			subscriberVO.setPrimaryOfferingVO(primaryOfferingVO);
			subscriberVO.setSupplementaryOfferingList(supplementaryOfferingList);
			subscriberVO.setConsumptionLimitList(consumptionLimitList);
			subscriberVO.setSimCardVO(simCardVO);
			// subscriberVO.setAddressList(addressList);
			NodeList nodes = getSubscriberBodyNode.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if ("quer:SubscriberId".equals(node.getNodeName())) {
					subscriberVO.setSubscriberId(node.getTextContent());
				} else if ("quer:AccountId".equals(node.getNodeName())) {
					subscriberVO.setAccountId(node.getTextContent());
				} else if ("quer:CustomerId".equals(node.getNodeName())) {
					subscriberVO.setCustomerId(node.getTextContent());
				} else if ("quer:ServiceNumber".equals(node.getNodeName())) {
					subscriberVO.setServiceNumber(node.getTextContent());
				} else if ("quer:SubscriberType".equals(node.getNodeName())) {
					subscriberVO.setSubscriberType(node.getTextContent());
					if("0".equals(node.getTextContent())){
						subscriberVO.setPaymentFlag("PPS");
					}else{
						subscriberVO.setPaymentFlag("POS");
					}
				} else if ("quer:NetworkType".equals(node.getNodeName())) {
					subscriberVO.setNetworkType(node.getTextContent());
				} else if ("quer:IMEI".equals(node.getNodeName())) {
					subscriberVO.setIMEI(node.getTextContent());
				} else if ("quer:ICCID".equals(node.getNodeName())) {
					simCardVO.setIccid(node.getTextContent());
				}else if ("quer:PIN1".equals(node.getNodeName())) {
					simCardVO.setPin1(node.getTextContent());
				}else if ("quer:PIN2".equals(node.getNodeName())) {
					simCardVO.setPin2(node.getTextContent());
				}else if ("quer:PUK1".equals(node.getNodeName())) {
					simCardVO.setPuk1(node.getTextContent());
				}else if ("quer:PUK2".equals(node.getNodeName())) {
					simCardVO.setPuk2(node.getTextContent());
				} else if ("quer:BrandId".equals(node.getNodeName())) {
					subscriberVO.setBrandId(node.getTextContent());
				} else if ("quer:Language".equals(node.getNodeName())) {
					subscriberVO.setLanguage(node.getTextContent());
				} else if ("quer:WrittenLanguage".equals(node.getNodeName())) {
					subscriberVO.setWrittenLanguage(node.getTextContent());
				} else if ("quer:EffectiveDate".equals(node.getNodeName())) {
					subscriberVO.setEffectiveTime(node.getTextContent());
				} else if ("quer:ExpireDate".equals(node.getNodeName())) {
					subscriberVO.setExpiryTime(node.getTextContent());
				} /*
					 * else if ("quer:DealerId".equals(node.getNodeName())) {
					 * subscriberVO.setDealerId(node.getTextContent()); }
					 */ else if ("quer:Status".equals(node.getNodeName())) {
					subscriberVO.setStatus(node.getTextContent());
				} else if ("quer:StatusReason".equals(node.getNodeName())) {
					subscriberVO.setStatusReason(node.getTextContent());
				} else if ("quer:ResignedTimestamp".equals(node.getNodeName())) {
					subscriberVO.setResignedTimestamp(node.getTextContent());
				} else if ("quer:ActualCustomer".equals(node.getNodeName())) {
					if (document.getElementsByTagName("bas:IndividualInfo").getLength() > 0) {
						Node individualInfo = document.getElementsByTagName("bas:IndividualInfo").item(0);
						NodeList individualInfoNodes = individualInfo.getChildNodes();
						for (int j = 0; j < individualInfoNodes.getLength(); j++) {
							Node individualInfoNode = individualInfoNodes.item(j);
							if ("bas:CertificateType".equals(individualInfoNode.getNodeName())) {
								customerVO.setCertificateType(individualInfoNode.getTextContent());
							} else if ("bas:CertificateNumber".equals(individualInfoNode.getNodeName())) {
								customerVO.setCertificateNumber(individualInfoNode.getTextContent());
							} else if ("bas:Title".equals(individualInfoNode.getNodeName())) {
								customerVO.setTitle(individualInfoNode.getTextContent());
							} else if ("bas:FirstName".equals(individualInfoNode.getNodeName())) {
								customerVO.setFirstName(individualInfoNode.getTextContent());
							} else if ("bas:MiddleName".equals(individualInfoNode.getNodeName())) {
								customerVO.setMiddleName(individualInfoNode.getTextContent());
							} else if ("bas:LastName".equals(individualInfoNode.getNodeName())) {
								customerVO.setLastName(individualInfoNode.getTextContent());
							} else if ("bas:Nationality".equals(individualInfoNode.getNodeName())) {
								customerVO.setNationality(individualInfoNode.getTextContent());
							} else if ("bas:Gender".equals(individualInfoNode.getNodeName())) {
								customerVO.setGender(individualInfoNode.getTextContent());
							} else if ("bas:DateOfBirth".equals(individualInfoNode.getNodeName())) {
								customerVO.setDateOfBirth(individualInfoNode.getTextContent());
							} else if ("bas:Remark".equals(individualInfoNode.getNodeName())) {
								customerVO.setRemark(individualInfoNode.getTextContent());
							}
						}
					} else if (document.getElementsByTagName("bas:CorpInfo").getLength() > 0) {
						CorporateVO corporateVO = new CorporateVO();
						subscriberVO.setCorporateVO(corporateVO);
						Node corpInfo = document.getElementsByTagName("bas:CorpInfo").item(0);
						NodeList corpInfoNodes = corpInfo.getChildNodes();
						for (int j = 0; j < corpInfoNodes.getLength(); j++) {
							Node corpInfoNode = corpInfoNodes.item(j);
							if ("bas:CorpNo".equals(corpInfoNode.getNodeName())) {
								corporateVO.setCorpNo(corpInfoNode.getTextContent());
							} else if ("bas:IDType".equals(corpInfoNode.getNodeName())) {
								corporateVO.setIDType(corpInfoNode.getTextContent());
							} else if ("bas:IDNumber".equals(corpInfoNode.getNodeName())) {
								corporateVO.setIDNumber(corpInfoNode.getTextContent());
							} else if ("bas:IDValidity".equals(corpInfoNode.getNodeName())) {
								corporateVO.setIDValidity(corpInfoNode.getTextContent());
							} else if ("bas:CustNo".equals(corpInfoNode.getNodeName())) {
								corporateVO.setCustNo(corpInfoNode.getTextContent());
							} else if ("bas:CustType".equals(corpInfoNode.getNodeName())) {
								corporateVO.setCustType(corpInfoNode.getTextContent());
							} else if ("bas:CustGrade".equals(corpInfoNode.getNodeName())) {
								corporateVO.setCustGrade(corpInfoNode.getTextContent());
							} else if ("bas:CustName".equals(corpInfoNode.getNodeName())) {
								corporateVO.setCustName(corpInfoNode.getTextContent());
							} else if ("bas:CustShortName".equals(corpInfoNode.getNodeName())) {
								corporateVO.setCustShortName(corpInfoNode.getTextContent());
							} else if ("bas:CustPhoneNumber".equals(corpInfoNode.getNodeName())) {
								corporateVO.setCustPhoneNumber(corpInfoNode.getTextContent());
							} else if ("bas:CustEmail".equals(corpInfoNode.getNodeName())) {
								corporateVO.setCustEmail(corpInfoNode.getTextContent());
							} else if ("bas:CustWebSite".equals(corpInfoNode.getNodeName())) {
								corporateVO.setCustWebSite(corpInfoNode.getTextContent());
							}
						}
					} else if (document.getElementsByTagName("bas:ActualCustomer").getLength() > 0) {
						NodeList actualCustomerNodes = document.getElementsByTagName("bas:ActualCustomer");
						List<AddressVO> addressList = new ArrayList<AddressVO>();
						customerVO.setAddressList(addressList);
						for (int j = 0; j < actualCustomerNodes.getLength(); j++) {
							Node actualCustomerNode = actualCustomerNodes.item(j);
							if ("bas:AddressList".equals(actualCustomerNode.getNodeName())) {
								NodeList custAddressInfoListNodes = actualCustomerNode.getChildNodes();
								for (int k = 0; k < custAddressInfoListNodes.getLength(); k++) {
									Node custAddressInfoListNode = custAddressInfoListNodes.item(k);
									AddressVO addressVO = new AddressVO();
									addressList.add(addressVO);
									if ("bas:AddressInfo".equals(custAddressInfoListNode.getNodeName())) {
										NodeList custAddressInfoNodes = custAddressInfoListNode.getChildNodes();
										for (int l = 0; l < custAddressInfoNodes.getLength(); l++) {
											Node custAddressInfoNode = custAddressInfoNodes.item(l);
											if ("bas:AddressClass".equals(custAddressInfoNode.getNodeName())) {
												addressVO.setAddressClass(custAddressInfoNode.getTextContent());
											} else if ("bas:ContactSeq".equals(custAddressInfoNode.getNodeName())) {
												addressVO.setContactSeq(custAddressInfoNode.getTextContent());
											} else if ("bas:AddressType".equals(custAddressInfoNode.getNodeName())) {
												addressVO.setAddressType(custAddressInfoNode.getTextContent());
											} else if ("bas:ActionType".equals(custAddressInfoNode.getNodeName())) {
												addressVO.setActionType(custAddressInfoNode.getTextContent());
											} else if ("bas:LocalId".equals(custAddressInfoNode.getNodeName())) {
												addressVO.setLocalId(custAddressInfoNode.getTextContent());
											} else if ("bas:Address1".equals(custAddressInfoNode.getNodeName())) {
												addressVO.setAddress1(custAddressInfoNode.getTextContent());
											} else if ("bas:Address2".equals(custAddressInfoNode.getNodeName())) {
												addressVO.setAddress2(custAddressInfoNode.getTextContent());
											} else if ("bas:Address3".equals(custAddressInfoNode.getNodeName())) {
												addressVO.setAddress3(custAddressInfoNode.getTextContent());
											} else if ("bas:Address4".equals(custAddressInfoNode.getNodeName())) {
												addressVO.setAddress4(custAddressInfoNode.getTextContent());
											} else if ("bas:Address5".equals(custAddressInfoNode.getNodeName())) {
												addressVO.setAddress5(custAddressInfoNode.getTextContent());
											} else if ("bas:Address6".equals(custAddressInfoNode.getNodeName())) {
												addressVO.setAddress6(custAddressInfoNode.getTextContent());
											} else if ("bas:Address7".equals(custAddressInfoNode.getNodeName())) {
												addressVO.setAddress7(custAddressInfoNode.getTextContent());
											} else if ("bas:Address8".equals(custAddressInfoNode.getNodeName())) {
												addressVO.setAddress8(custAddressInfoNode.getTextContent());
											} else if ("bas:Address9".equals(custAddressInfoNode.getNodeName())) {
												addressVO.setAddress9(custAddressInfoNode.getTextContent());
											} else if ("bas:Address10".equals(custAddressInfoNode.getNodeName())) {
												addressVO.setAddress10(custAddressInfoNode.getTextContent());
											} else if ("bas:Telephone1".equals(custAddressInfoNode.getNodeName())) {
												addressVO.setTelephone1(custAddressInfoNode.getTextContent());
											} else if ("bas:Telephone2".equals(custAddressInfoNode.getNodeName())) {
												addressVO.setTelephone2(custAddressInfoNode.getTextContent());
											} else if ("bas:Telephone3".equals(custAddressInfoNode.getNodeName())) {
												addressVO.setTelephone3(custAddressInfoNode.getTextContent());
											} else if ("bas:Email1".equals(custAddressInfoNode.getNodeName())) {
												addressVO.setEmail1(custAddressInfoNode.getTextContent());
											} else if ("bas:Email2".equals(custAddressInfoNode.getNodeName())) {
												addressVO.setEmail2(custAddressInfoNode.getTextContent());
											} else if ("bas:Email3".equals(custAddressInfoNode.getNodeName())) {
												addressVO.setEmail3(custAddressInfoNode.getTextContent());
											} else if ("bas:SmsNumber".equals(custAddressInfoNode.getNodeName())) {
												addressVO.setPhoneNumber(custAddressInfoNode.getTextContent());
											}
										}
									}
								}
							}
						}
					}

				} else if ("quer:PrimaryOffering".equals(node.getNodeName())) {
					NodeList primaryOfferingNodes = node.getChildNodes();
					for (int j = 0; j < primaryOfferingNodes.getLength(); j++) {
						Node primaryOfferingNode = primaryOfferingNodes.item(j);
						if ("bas:OfferingId".equals(primaryOfferingNode.getNodeName())) {
							NodeList offeringIdNodes = primaryOfferingNode.getChildNodes();
							for (int k = 0; k < offeringIdNodes.getLength(); k++) {
								Node offeringIdNode = offeringIdNodes.item(k);
								if ("bas:OfferingId".equals(offeringIdNode.getNodeName())) {
									primaryOfferingVO.setOfferingId(offeringIdNode.getTextContent());
								} else if ("bas:OfferingCode".equals(offeringIdNode.getNodeName())) {
									primaryOfferingVO.setOfferingCode(offeringIdNode.getTextContent());
								} else if ("bas:PurchaseSeq".equals(offeringIdNode.getNodeName())) {
									primaryOfferingVO.setPurchaseSeq(offeringIdNode.getTextContent());
								}
							}
						} else if ("bas:ParentOfferingId".equals(primaryOfferingNode.getNodeName())) {
							NodeList offeringIdNodes = primaryOfferingNode.getChildNodes();
							for (int k = 0; k < offeringIdNodes.getLength(); k++) {
								Node offeringIdNode = offeringIdNodes.item(k);
								if ("bas:OfferingId".equals(offeringIdNode.getNodeName())) {
									primaryOfferingVO.setParentOfferingId(offeringIdNode.getTextContent());
								} else if ("bas:OfferingCode".equals(offeringIdNode.getNodeName())) {
									primaryOfferingVO.setParentOfferingCode(offeringIdNode.getTextContent());
								} else if ("bas:PurchaseSeq".equals(offeringIdNode.getNodeName())) {
									primaryOfferingVO.setParentPurchaseSeq(offeringIdNode.getTextContent());
								}
							}
						} else if ("bas:OfferingName".equals(primaryOfferingNode.getNodeName())) {
							primaryOfferingVO.setOfferingName(primaryOfferingNode.getTextContent());
						} else if ("bas:BundleFlag".equals(primaryOfferingNode.getNodeName())) {
							primaryOfferingVO.setBundleFlag(primaryOfferingNode.getTextContent());
						} else if ("bas:TrialStartDate".equals(primaryOfferingNode.getNodeName())) {
							primaryOfferingVO.setTrialStartDate(primaryOfferingNode.getTextContent());
						} else if ("bas:TrialEndDate".equals(primaryOfferingNode.getNodeName())) {
							primaryOfferingVO.setTrialEndDate(primaryOfferingNode.getTextContent());
						} else if ("bas:Amount".equals(primaryOfferingNode.getNodeName())) {
							primaryOfferingVO.setAmount(primaryOfferingNode.getTextContent());
						} else if ("bas:Status".equals(primaryOfferingNode.getNodeName())) {
							primaryOfferingVO.setStatus(primaryOfferingNode.getTextContent());
						} else if ("bas:CreateDate".equals(primaryOfferingNode.getNodeName())) {
							primaryOfferingVO.setCreateDate(primaryOfferingNode.getTextContent());
						} else if ("bas:EffectiveTime".equals(primaryOfferingNode.getNodeName())) {
							primaryOfferingVO.setEffectiveTime(primaryOfferingNode.getTextContent());
						} else if ("bas:ExpiredTime".equals(primaryOfferingNode.getNodeName())) {
							primaryOfferingVO.setExpiryTime(primaryOfferingNode.getTextContent());
						} else if ("bas:LatestActiveDate".equals(primaryOfferingNode.getNodeName())) {
							primaryOfferingVO.setLatestActiveDate(primaryOfferingNode.getTextContent());
						} else if ("bas:ProductList".equals(primaryOfferingNode.getNodeName())) {
							NodeList productListNodes = primaryOfferingNode.getChildNodes();
							List<ServiceVO> serviceList = new ArrayList<ServiceVO>();
							primaryOfferingVO.setServiceList(serviceList);
							for (int k = 0; k < productListNodes.getLength(); k++) {
								Node productListNode = productListNodes.item(k);
								if ("bas:GetSubProductInfo".equals(productListNode.getNodeName())) {
									NodeList getSubProductInfoNodes = productListNode.getChildNodes();
									ServiceVO serviceVO = new ServiceVO();
									serviceList.add(serviceVO);
									for (int l = 0; l < getSubProductInfoNodes.getLength(); l++) {
										Node getSubProductInfoNode = getSubProductInfoNodes.item(l);
										//NodeList subProductInfoNodes =getSubProductInfoNode.getChildNodes();
										if ("bas:ProductId".equals(getSubProductInfoNode.getNodeName())) {
											serviceVO.setServiceId(getSubProductInfoNode.getTextContent());
										} else if ("bas:ProductName".equals(getSubProductInfoNode.getNodeName())) {
											serviceVO.setServiceName(getSubProductInfoNode.getTextContent());
										} else if ("bas:Status".equals(getSubProductInfoNode.getNodeName())) {
											serviceVO.setServiceStatus(getSubProductInfoNode.getTextContent());
											serviceVO.setServiceStatusDesc(getSubProductInfoNode.getTextContent());
										} else if ("bas:ServiceList".equals(getSubProductInfoNode.getNodeName())) {
											NodeList serviceListNodes = getSubProductInfoNode.getChildNodes();
											for (int o = 0; o < serviceListNodes.getLength(); o++) {
												Node serviceListNode = serviceListNodes.item(o);
												if ("bas:ServiceList".equals(serviceListNode.getNodeName())) {
													NodeList sServiceListNodes = serviceListNode.getChildNodes();
													for (int n = 0; n < sServiceListNodes.getLength(); n++) {
														Node sServiceListNode = sServiceListNodes.item(n);
														if ("bas:ServiceId".equals(sServiceListNode.getNodeName())) {
															serviceVO.setServiceId(sServiceListNode.getTextContent());
														} else if ("bas:ServiceName"
																.equals(sServiceListNode.getNodeName())) {
															serviceVO.setServiceName(sServiceListNode.getTextContent());
														} else if ("bas:ServiceType"
																.equals(sServiceListNode.getNodeName())) {
															serviceVO.setServiceType(sServiceListNode.getTextContent());
														} else if ("bas:ServiceNetworkType"
																.equals(sServiceListNode.getNodeName())) {
															serviceVO.setServiceNetworkType(
																	sServiceListNode.getTextContent());
														} else if ("bas:ServiceCategory"
																.equals(sServiceListNode.getNodeName())) {
															serviceVO.setServiceCategory(
																	sServiceListNode.getTextContent());
														} else if ("bas:IsMainService"
																.equals(sServiceListNode.getNodeName())) {
															serviceVO.setIsMainService(
																	sServiceListNode.getTextContent());
														} else if ("bas:ServiceStatus"
																.equals(sServiceListNode.getNodeName())) {
															serviceVO.setServiceStatus(
																	sServiceListNode.getTextContent());
														}
													}
												}
											}
										}
										
										
										
										
										/*
										for(int m = 0 ; m < subProductInfoNodes.getLength(); m++){
											Node productInfoNode = subProductInfoNodes.item(m);
											if ("bas:ProductId".equals(productInfoNode.getNodeName())) {
												serviceVO.setServiceId(productInfoNode.getTextContent());
											} else if ("bas:ProductName".equals(productInfoNode.getNodeName())) {
												serviceVO.setServiceName(productInfoNode.getTextContent());
											} else if ("bas:Status".equals(productInfoNode.getNodeName())) {
												serviceVO.setServiceStatus(productInfoNode.getTextContent());
												serviceVO.setServiceStatusDesc(productInfoNode.getTextContent());
											} else if ("bas:ServiceList".equals(productInfoNode.getNodeName())) {
												NodeList serviceListNodes = productInfoNode.getChildNodes();
												for (int o = 0; o < serviceListNodes.getLength(); o++) {
													Node serviceListNode = serviceListNodes.item(o);
													if ("bas:ServiceList".equals(serviceListNode.getNodeName())) {
														NodeList sServiceListNodes = serviceListNode.getChildNodes();
														for (int n = 0; n < sServiceListNodes.getLength(); n++) {
															Node sServiceListNode = sServiceListNodes.item(n);
															if ("bas:ServiceId".equals(sServiceListNode.getNodeName())) {
																serviceVO.setServiceId(sServiceListNode.getTextContent());
															} else if ("bas:ServiceName"
																	.equals(sServiceListNode.getNodeName())) {
																serviceVO.setServiceName(sServiceListNode.getTextContent());
															} else if ("bas:ServiceType"
																	.equals(sServiceListNode.getNodeName())) {
																serviceVO.setServiceType(sServiceListNode.getTextContent());
															} else if ("bas:ServiceNetworkType"
																	.equals(sServiceListNode.getNodeName())) {
																serviceVO.setServiceNetworkType(
																		sServiceListNode.getTextContent());
															} else if ("bas:ServiceCategory"
																	.equals(sServiceListNode.getNodeName())) {
																serviceVO.setServiceCategory(
																		sServiceListNode.getTextContent());
															} else if ("bas:IsMainService"
																	.equals(sServiceListNode.getNodeName())) {
																serviceVO.setIsMainService(
																		sServiceListNode.getTextContent());
															} else if ("bas:ServiceStatus"
																	.equals(sServiceListNode.getNodeName())) {
																serviceVO.setServiceStatus(
																		sServiceListNode.getTextContent());
															}
														}
													}
												}
											}
										}*/
									}
								}
							}
						}
					}
				} else if ("quer:SupplementaryOfferingList".equals(node.getNodeName())) {
					NodeList supplementaryOfferingListNodes = node.getChildNodes();
					for (int j = 0; j < supplementaryOfferingListNodes.getLength(); j++) {
						Node supplementaryOfferingListNode = supplementaryOfferingListNodes.item(j);
						if ("bas:GetSubOfferingInfo".equals(supplementaryOfferingListNode.getNodeName())) {
							OfferingVO supplementaryOfferingVO = new OfferingVO();
							supplementaryOfferingList.add(supplementaryOfferingVO);
							NodeList getSubOfferingInfoNodes = supplementaryOfferingListNode.getChildNodes();
							for (int k = 0; k < getSubOfferingInfoNodes.getLength(); k++) {
								Node getSubOfferingInfoNode = getSubOfferingInfoNodes.item(k);
								if ("bas:OfferingId".equals(getSubOfferingInfoNode.getNodeName())) {
									NodeList offeringIdNodes = getSubOfferingInfoNode.getChildNodes();
									for (int l = 0; l < offeringIdNodes.getLength(); l++) {
										Node offeringIdNode = offeringIdNodes.item(l);
										if ("bas:OfferingId".equals(offeringIdNode.getNodeName())) {
											supplementaryOfferingVO.setOfferingId(offeringIdNode.getTextContent());
										} else if ("bas:OfferingCode".equals(offeringIdNode.getNodeName())) {
											supplementaryOfferingVO.setOfferingCode(offeringIdNode.getTextContent());
										} else if ("bas:PurchaseSeq".equals(offeringIdNode.getNodeName())) {
											supplementaryOfferingVO.setPurchaseSeq(offeringIdNode.getTextContent());
										}
									}
								} else if ("bas:ParentOfferingId".equals(getSubOfferingInfoNode.getNodeName())) {
									NodeList parentOfferingIdNodes = getSubOfferingInfoNode.getChildNodes();
									for (int l = 0; l < parentOfferingIdNodes.getLength(); l++) {
										Node offeringIdNode = parentOfferingIdNodes.item(l);
										if ("bas:OfferingId".equals(offeringIdNode.getNodeName())) {
											supplementaryOfferingVO
													.setParentOfferingId(offeringIdNode.getTextContent());
										} else if ("bas:OfferingCode".equals(offeringIdNode.getNodeName())) {
											supplementaryOfferingVO
													.setParentOfferingCode(offeringIdNode.getTextContent());
										} else if ("bas:PurchaseSeq".equals(offeringIdNode.getNodeName())) {
											supplementaryOfferingVO
													.setParentPurchaseSeq(offeringIdNode.getTextContent());
										}
									}
								} else if ("bas:OfferingName".equals(getSubOfferingInfoNode.getNodeName())) {
									supplementaryOfferingVO.setOfferingName(getSubOfferingInfoNode.getTextContent());
								} else if ("bas:BundleFlag".equals(getSubOfferingInfoNode.getNodeName())) {
									supplementaryOfferingVO.setBundleFlag(getSubOfferingInfoNode.getTextContent());
								} else if ("bas:TrialStartDate".equals(getSubOfferingInfoNode.getNodeName())) {
									supplementaryOfferingVO.setTrialStartDate(getSubOfferingInfoNode.getTextContent());
								} else if ("bas:TrialEndDate".equals(getSubOfferingInfoNode.getNodeName())) {
									supplementaryOfferingVO.setTrialEndDate(getSubOfferingInfoNode.getTextContent());
								} else if ("bas:Amount".equals(getSubOfferingInfoNode.getNodeName())) {
									supplementaryOfferingVO.setAmount(getSubOfferingInfoNode.getTextContent());
								} else if ("bas:Status".equals(getSubOfferingInfoNode.getNodeName())) {
									supplementaryOfferingVO.setStatus(getSubOfferingInfoNode.getTextContent());
								} else if ("bas:CreateDate".equals(getSubOfferingInfoNode.getNodeName())) {
									supplementaryOfferingVO.setCreateDate(getSubOfferingInfoNode.getTextContent());
								} else if ("bas:EffectiveTime".equals(getSubOfferingInfoNode.getNodeName())) {
									supplementaryOfferingVO.setEffectiveTime(getSubOfferingInfoNode.getTextContent());
								} else if ("bas:ExpiredTime".equals(getSubOfferingInfoNode.getNodeName())) {
									supplementaryOfferingVO.setExpiryTime(getSubOfferingInfoNode.getTextContent());
								} else if ("bas:LatestActiveDate".equals(getSubOfferingInfoNode.getNodeName())) {
									supplementaryOfferingVO
											.setLatestActiveDate(getSubOfferingInfoNode.getTextContent());
								} else if ("bas:ProductList".equals(getSubOfferingInfoNode.getNodeName())) {
									NodeList productListNodes = getSubOfferingInfoNode.getChildNodes();
									List<ServiceVO> serviceList = new ArrayList<ServiceVO>();
									supplementaryOfferingVO.setServiceList(serviceList);
									for (int l = 0; l < productListNodes.getLength(); l++) {
										Node productListNode = productListNodes.item(l);
										if ("bas:GetSubProductInfo".equals(productListNode.getNodeName())) {
											NodeList getSubProductInfoNodes = productListNode.getChildNodes();
											ServiceVO serviceVO = new ServiceVO();
											serviceList.add(serviceVO);
											for (int m = 0; m < getSubProductInfoNodes.getLength(); m++) {
												Node getSubProductInfoNode = getSubProductInfoNodes.item(m);
												if ("bas:ProductId".equals(getSubProductInfoNode.getNodeName())) {
													serviceVO.setServiceId(getSubProductInfoNode.getTextContent());
												} else if ("bas:ProductName"
														.equals(getSubProductInfoNode.getNodeName())) {
													serviceVO.setServiceName(getSubProductInfoNode.getTextContent());
												} else if ("bas:Status".equals(getSubProductInfoNode.getNodeName())) {
													serviceVO.setServiceStatus(getSubProductInfoNode.getTextContent());
													serviceVO.setServiceStatusDesc(
															getSubProductInfoNode.getTextContent());
												} else if ("bas:ServiceList"
														.equals(getSubProductInfoNode.getNodeName())) {
													NodeList serviceListNodes = getSubProductInfoNode.getChildNodes();
													for (int n = 0; n < serviceListNodes.getLength(); n++) {
														NodeList sserviceListNodes = serviceListNodes.item(n)
																.getChildNodes();
														for (int o = 0; o < sserviceListNodes.getLength(); o++) {
															Node sserviceListNode = sserviceListNodes.item(o);
															if ("bas:ServiceId"
																	.equals(sserviceListNode.getNodeName())) {
																serviceVO.setServiceId(
																		sserviceListNode.getTextContent());
															} else if ("bas:ServiceName"
																	.equals(sserviceListNode.getNodeName())) {
																serviceVO.setServiceName(
																		sserviceListNode.getTextContent());
															} else if ("bas:ServiceType"
																	.equals(sserviceListNode.getNodeName())) {
																serviceVO.setServiceType(
																		sserviceListNode.getTextContent());
															} else if ("bas:ServiceNetworkType"
																	.equals(sserviceListNode.getNodeName())) {
																serviceVO.setServiceNetworkType(
																		sserviceListNode.getTextContent());
															} else if ("bas:ServiceCategory"
																	.equals(sserviceListNode.getNodeName())) {
																serviceVO.setServiceCategory(
																		sserviceListNode.getTextContent());
															} else if ("bas:IsMainService"
																	.equals(sserviceListNode.getNodeName())) {
																serviceVO.setIsMainService(
																		sserviceListNode.getTextContent());
															} else if ("bas:ServiceStatus"
																	.equals(sserviceListNode.getNodeName())) {
																serviceVO.setServiceStatus(
																		sserviceListNode.getTextContent());
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
				} else if ("quer:ConsumptionLimitList".equals(node.getNodeName())) {
					NodeList consumptionLimitListNodes = node.getChildNodes();
					for (int j = 0; j < consumptionLimitListNodes.getLength(); j++) {
						NodeList sconsumptionLimitListNodes = consumptionLimitListNodes.item(j).getChildNodes();
						ConsumptionLimitVO consumptionLimitVO = new ConsumptionLimitVO();
						consumptionLimitList.add(consumptionLimitVO);
						for (int k = 0; k < sconsumptionLimitListNodes.getLength(); k++) {
							Node sconsumptionLimitListNode = sconsumptionLimitListNodes.item(k);
							if ("bas:ActionType".equals(sconsumptionLimitListNode.getNodeName())) {
								consumptionLimitVO.setActionType(sconsumptionLimitListNode.getTextContent());
							} else if ("bas:LimitType".equals(sconsumptionLimitListNode.getNodeName())) {
								consumptionLimitVO.setLimitType(sconsumptionLimitListNode.getTextContent());
							} else if ("bas:NotifyType".equals(sconsumptionLimitListNode.getNodeName())) {
								consumptionLimitVO.setNotifyType(sconsumptionLimitListNode.getTextContent());
							} else if ("bas:NotifyLimit".equals(sconsumptionLimitListNode.getNodeName())) {
								consumptionLimitVO.setNotifyLimit(sconsumptionLimitListNode.getTextContent());
							} else if ("bas:LimitValue".equals(sconsumptionLimitListNode.getNodeName())) {
								consumptionLimitVO.setLimitValue(sconsumptionLimitListNode.getTextContent());
							} else if ("bas:UsedLimitValue".equals(sconsumptionLimitListNode.getNodeName())) {
								consumptionLimitVO.setUsedLimitValue(sconsumptionLimitListNode.getTextContent());
							} else if ("bas:RemainLimitValue".equals(sconsumptionLimitListNode.getNodeName())) {
								consumptionLimitVO.setRemainLimitValue(sconsumptionLimitListNode.getTextContent());
							}
						}
					}
				} else if ("quer:AddressList".equals(node.getNodeName())) {
					NodeList addressListNodes = node.getChildNodes();
					for (int j = 0; j < addressListNodes.getLength(); j++) {
						NodeList saddressListNodes = addressListNodes.item(j).getChildNodes();
						List<AddressVO> addressList = new ArrayList<AddressVO>();
						AddressVO addressVO = new AddressVO();
						addressList.add(addressVO);
						subscriberVO.setAddressList(addressList);
						for (int k = 0; k < saddressListNodes.getLength(); k++) {
							Node saddressListNode = saddressListNodes.item(k);
							if ("bas:AddressClass".equals(saddressListNode.getNodeName())) {
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
