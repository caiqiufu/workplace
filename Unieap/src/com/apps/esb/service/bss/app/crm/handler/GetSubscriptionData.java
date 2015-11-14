package com.apps.esb.service.bss.app.crm.handler;

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
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.ActualCustomerVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.AddressInfoVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.ConsumptionLimitVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.CorpInfoVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.IndividualInfoVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.OfferingIdVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.PrimaryOfferingVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.QuerySubscriberInfoVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.ResourceInfoVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.ServiceVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.SubProductInfoVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.SupplementaryOfferingVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;

@Service("getSubscriptionData")
public class GetSubscriptionData extends SoapMessageHandler implements BizHandler {

	public GetSubscriptionData() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo,String parameters,Map<String,Object> extParameters)
			throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("getSubscriptionData");
		return process(this, newRequestInfo, requestInfo.getRequestBody().getServiceNumber(), requestInfo.getRequestBody().getExtParameters(), parameters,"ws.crm.query.timeout");
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
		SOAPElement requestGetCustomerBodyElement = bodyElement.addChildElement("GetSubscriberBody","quer");
		requestGetCustomerBodyElement.addChildElement("IncludeOfferFlag","quer").addTextNode("1");
		requestGetCustomerBodyElement.addChildElement("ServiceNumber","quer").addTextNode(serviceNumber);
		return message;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		QuerySubscriberInfoVO querySubscriberInfoVO = new QuerySubscriberInfoVO();
		ProcessResult result = new ProcessResult();
		result.setVo(querySubscriberInfoVO);
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
			ActualCustomerVO actualCustomerVO = new ActualCustomerVO();
			PrimaryOfferingVO primaryOfferingVO = new PrimaryOfferingVO();
			List<SupplementaryOfferingVO> supplementaryOfferingList = new ArrayList<SupplementaryOfferingVO>();
			List<ConsumptionLimitVO> consumptionLimitList = new ArrayList<ConsumptionLimitVO>();
			List<AddressInfoVO> addressInfoList = new ArrayList<AddressInfoVO>();
			querySubscriberInfoVO.setActualCustomerVO(actualCustomerVO);
			querySubscriberInfoVO.setPrimaryOfferingVO(primaryOfferingVO);
			querySubscriberInfoVO.setSupplementaryOfferingList(supplementaryOfferingList);
			querySubscriberInfoVO.setConsumptionLimitList(consumptionLimitList);
			querySubscriberInfoVO.setAddressInfoList(addressInfoList);
			NodeList nodes = getSubscriberBodyNode.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if ("quer:SubscriberId".equals(node.getNodeName())) {
					querySubscriberInfoVO.setSubscriberId(node.getTextContent());
				} else if ("quer:AccountId".equals(node.getNodeName())) {
					querySubscriberInfoVO.setAccountId(node.getTextContent());
				} else if ("quer:CustomerId".equals(node.getNodeName())) {
					querySubscriberInfoVO.setCustomerId(node.getTextContent());
				} else if ("quer:ServiceNumber".equals(node.getNodeName())) {
					querySubscriberInfoVO.setServiceNumber(node.getTextContent());
				} else if ("quer:SubscriberType".equals(node.getNodeName())) {
					querySubscriberInfoVO.setSubscriberType(node.getTextContent());
				} else if ("quer:NetworkType".equals(node.getNodeName())) {
					querySubscriberInfoVO.setNetworkType(node.getTextContent());
				} else if ("quer:IMEI".equals(node.getNodeName())) {
					querySubscriberInfoVO.setIMEI(node.getTextContent());
				} else if ("quer:ICCID".equals(node.getNodeName())) {
					querySubscriberInfoVO.setICCID(node.getTextContent());
				} else if ("quer:BrandId".equals(node.getNodeName())) {
					querySubscriberInfoVO.setBrandId(node.getTextContent());
				} else if ("quer:Language".equals(node.getNodeName())) {
					querySubscriberInfoVO.setLanguage(node.getTextContent());
				} else if ("quer:WrittenLanguage".equals(node.getNodeName())) {
					querySubscriberInfoVO.setWrittenLanguage(node.getTextContent());
				} else if ("quer:EffectiveDate".equals(node.getNodeName())) {
					querySubscriberInfoVO.setEffectiveDate(node.getTextContent());
				} else if ("quer:ExpireDate".equals(node.getNodeName())) {
					querySubscriberInfoVO.setExpireDate(node.getTextContent());
				} else if ("quer:DealerId".equals(node.getNodeName())) {
					querySubscriberInfoVO.setDealerId(node.getTextContent());
				} else if ("quer:Status".equals(node.getNodeName())) {
					querySubscriberInfoVO.setStatus(node.getTextContent());
				} else if ("quer:StatusReason".equals(node.getNodeName())) {
					querySubscriberInfoVO.setStatusReason(node.getTextContent());
				} else if ("quer:ResignedTimestamp".equals(node.getNodeName())) {
					querySubscriberInfoVO.setResignedTimestamp(node.getTextContent());
				} else if ("quer:ActualCustomer".equals(node.getNodeName())) {
					if (document.getElementsByTagName("bas:IndividualInfo").getLength() > 0) {
						IndividualInfoVO individualInfoVO = new IndividualInfoVO();
						actualCustomerVO.setIndividualInfoVO(individualInfoVO);
						Node individualInfo = document.getElementsByTagName("bas:IndividualInfo").item(0);
						NodeList individualInfoNodes = individualInfo.getChildNodes();
						for (int j = 0; j < individualInfoNodes.getLength(); j++) {
							Node individualInfoNode = individualInfoNodes.item(j);
							if ("bas:CertificateType".equals(individualInfoNode.getNodeName())) {
								individualInfoVO.setCertificateType(individualInfoNode.getTextContent());
							} else if ("bas:CertificateNumber".equals(individualInfoNode.getNodeName())) {
								individualInfoVO.setCertificateNumber(individualInfoNode.getTextContent());
							} else if ("bas:Title".equals(individualInfoNode.getNodeName())) {
								individualInfoVO.setTitle(individualInfoNode.getTextContent());
							} else if ("bas:FirstName".equals(individualInfoNode.getNodeName())) {
								individualInfoVO.setFirstName(individualInfoNode.getTextContent());
							} else if ("bas:MiddleName".equals(individualInfoNode.getNodeName())) {
								individualInfoVO.setMiddleName(individualInfoNode.getTextContent());
							} else if ("bas:LastName".equals(individualInfoNode.getNodeName())) {
								individualInfoVO.setLastName(individualInfoNode.getTextContent());
							} else if ("bas:Nationality".equals(individualInfoNode.getNodeName())) {
								individualInfoVO.setNationality(individualInfoNode.getTextContent());
							} else if ("bas:Gender".equals(individualInfoNode.getNodeName())) {
								individualInfoVO.setGender(individualInfoNode.getTextContent());
							} else if ("bas:DateOfBirth".equals(individualInfoNode.getNodeName())) {
								individualInfoVO.setDateOfBirth(individualInfoNode.getTextContent());
							} else if ("bas:Remark".equals(individualInfoNode.getNodeName())) {
								individualInfoVO.setRemark(individualInfoNode.getTextContent());
							}
						}
					} else if (document.getElementsByTagName("bas:CorpInfo").getLength() > 0) {
						CorpInfoVO corpInfoVO = new CorpInfoVO();
						actualCustomerVO.setCorpInfoVO(corpInfoVO);
						Node corpInfo = document.getElementsByTagName("bas:CorpInfo").item(0);
						NodeList corpInfoNodes = corpInfo.getChildNodes();
						for (int j = 0; j < corpInfoNodes.getLength(); j++) {
							Node corpInfoNode = corpInfoNodes.item(j);
							if ("bas:CorpNo".equals(corpInfoNode.getNodeName())) {
								corpInfoVO.setCorpNo(corpInfoNode.getTextContent());
							} else if ("bas:IDType".equals(corpInfoNode.getNodeName())) {
								corpInfoVO.setIDType(corpInfoNode.getTextContent());
							} else if ("bas:IDNumber".equals(corpInfoNode.getNodeName())) {
								corpInfoVO.setIDNumber(corpInfoNode.getTextContent());
							} else if ("bas:IDValidity".equals(corpInfoNode.getNodeName())) {
								corpInfoVO.setIDValidity(corpInfoNode.getTextContent());
							} else if ("bas:CustNo".equals(corpInfoNode.getNodeName())) {
								corpInfoVO.setCustNo(corpInfoNode.getTextContent());
							} else if ("bas:CustType".equals(corpInfoNode.getNodeName())) {
								corpInfoVO.setCustType(corpInfoNode.getTextContent());
							} else if ("bas:CustGrade".equals(corpInfoNode.getNodeName())) {
								corpInfoVO.setCustGrade(corpInfoNode.getTextContent());
							} else if ("bas:CustName".equals(corpInfoNode.getNodeName())) {
								corpInfoVO.setCustName(corpInfoNode.getTextContent());
							} else if ("bas:CustShortName".equals(corpInfoNode.getNodeName())) {
								corpInfoVO.setCustShortName(corpInfoNode.getTextContent());
							} else if ("bas:CustPhoneNumber".equals(corpInfoNode.getNodeName())) {
								corpInfoVO.setCustPhoneNumber(corpInfoNode.getTextContent());
							} else if ("bas:CustEmail".equals(corpInfoNode.getNodeName())) {
								corpInfoVO.setCustEmail(corpInfoNode.getTextContent());
							} else if ("bas:CustWebSite".equals(corpInfoNode.getNodeName())) {
								corpInfoVO.setCustWebSite(corpInfoNode.getTextContent());
							}
						}
					} else if (document.getElementsByTagName("bas:ActualCustomer").getLength() > 0) {
						NodeList actualCustomerNodes = document.getElementsByTagName("bas:ActualCustomer");
						List<AddressInfoVO> custAddressInfoList = new ArrayList<AddressInfoVO>();
						for (int j = 0; j < actualCustomerNodes.getLength(); j++) {
							Node actualCustomerNode = actualCustomerNodes.item(j);
							if ("bas:AddressList".equals(actualCustomerNode.getNodeName())) {
								NodeList custAddressInfoListNodes = actualCustomerNode.getChildNodes();
								for (int k = 0; k < custAddressInfoListNodes.getLength(); k++) {
									Node custAddressInfoListNode = custAddressInfoListNodes.item(k);
									AddressInfoVO custAddressInfoVO = new AddressInfoVO();
									custAddressInfoList.add(custAddressInfoVO);
									if ("bas:AddressInfo".equals(custAddressInfoListNode.getNodeName())) {
										NodeList custAddressInfoNodes = custAddressInfoListNode.getChildNodes();
										for (int l = 0; l < custAddressInfoNodes.getLength(); l++) {
											Node custAddressInfoNode = custAddressInfoNodes.item(l);
											if ("bas:AddressClass".equals(custAddressInfoNode.getNodeName())) {
												custAddressInfoVO.setAddressClass(custAddressInfoNode.getTextContent());
											} else if ("bas:ContactSeq".equals(custAddressInfoNode.getNodeName())) {
												custAddressInfoVO.setContactSeq(custAddressInfoNode.getTextContent());
											} else if ("bas:AddressType".equals(custAddressInfoNode.getNodeName())) {
												custAddressInfoVO.setAddressType(custAddressInfoNode.getTextContent());
											} else if ("bas:ActionType".equals(custAddressInfoNode.getNodeName())) {
												custAddressInfoVO.setActionType(custAddressInfoNode.getTextContent());
											} else if ("bas:LocalId".equals(custAddressInfoNode.getNodeName())) {
												custAddressInfoVO.setLocalId(custAddressInfoNode.getTextContent());
											} else if ("bas:Address1".equals(custAddressInfoNode.getNodeName())) {
												custAddressInfoVO.setAddress1(custAddressInfoNode.getTextContent());
											} else if ("bas:Address2".equals(custAddressInfoNode.getNodeName())) {
												custAddressInfoVO.setAddress2(custAddressInfoNode.getTextContent());
											} else if ("bas:Address3".equals(custAddressInfoNode.getNodeName())) {
												custAddressInfoVO.setAddress3(custAddressInfoNode.getTextContent());
											} else if ("bas:Address4".equals(custAddressInfoNode.getNodeName())) {
												custAddressInfoVO.setAddress4(custAddressInfoNode.getTextContent());
											} else if ("bas:Address5".equals(custAddressInfoNode.getNodeName())) {
												custAddressInfoVO.setAddress5(custAddressInfoNode.getTextContent());
											} else if ("bas:Address6".equals(custAddressInfoNode.getNodeName())) {
												custAddressInfoVO.setAddress6(custAddressInfoNode.getTextContent());
											} else if ("bas:Address7".equals(custAddressInfoNode.getNodeName())) {
												custAddressInfoVO.setAddress7(custAddressInfoNode.getTextContent());
											} else if ("bas:Address8".equals(custAddressInfoNode.getNodeName())) {
												custAddressInfoVO.setAddress8(custAddressInfoNode.getTextContent());
											} else if ("bas:Address9".equals(custAddressInfoNode.getNodeName())) {
												custAddressInfoVO.setAddress9(custAddressInfoNode.getTextContent());
											} else if ("bas:Address10".equals(custAddressInfoNode.getNodeName())) {
												custAddressInfoVO.setAddress10(custAddressInfoNode.getTextContent());
											} else if ("bas:Telephone1".equals(custAddressInfoNode.getNodeName())) {
												custAddressInfoVO.setTelephone1(custAddressInfoNode.getTextContent());
											} else if ("bas:Telephone2".equals(custAddressInfoNode.getNodeName())) {
												custAddressInfoVO.setTelephone2(custAddressInfoNode.getTextContent());
											} else if ("bas:Telephone3".equals(custAddressInfoNode.getNodeName())) {
												custAddressInfoVO.setTelephone3(custAddressInfoNode.getTextContent());
											} else if ("bas:Email1".equals(custAddressInfoNode.getNodeName())) {
												custAddressInfoVO.setEmail1(custAddressInfoNode.getTextContent());
											} else if ("bas:Email2".equals(custAddressInfoNode.getNodeName())) {
												custAddressInfoVO.setEmail2(custAddressInfoNode.getTextContent());
											} else if ("bas:Email3".equals(custAddressInfoNode.getNodeName())) {
												custAddressInfoVO.setEmail3(custAddressInfoNode.getTextContent());
											} else if ("bas:SmsNumber".equals(custAddressInfoNode.getNodeName())) {
												custAddressInfoVO.setSmsNumber(custAddressInfoNode.getTextContent());
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
							OfferingIdVO offeringIdVO = new OfferingIdVO();
							primaryOfferingVO.setOfferingIdVO(offeringIdVO);
							for (int k = 0; k < offeringIdNodes.getLength(); k++) {
								Node offeringIdNode = offeringIdNodes.item(k);
								if ("bas:OfferingId".equals(offeringIdNode.getNodeName())) {
									offeringIdVO.setOfferingId(offeringIdNode.getTextContent());
								} else if ("bas:OfferingCode".equals(offeringIdNode.getNodeName())) {
									offeringIdVO.setOfferingCode(offeringIdNode.getTextContent());
								} else if ("bas:PurchaseSeq".equals(offeringIdNode.getNodeName())) {
									offeringIdVO.setPurchaseSeq(offeringIdNode.getTextContent());
								}
							}
						}else if ("bas:ParentOfferingId".equals(primaryOfferingNode.getNodeName())) {
							NodeList offeringIdNodes = primaryOfferingNode.getChildNodes();
							OfferingIdVO parentOfferingIdVO = new OfferingIdVO();
							primaryOfferingVO.setParentOfferingIdVO(parentOfferingIdVO);
							for (int k = 0; k < offeringIdNodes.getLength(); k++) {
								Node offeringIdNode = offeringIdNodes.item(k);
								if ("bas:OfferingId".equals(offeringIdNode.getNodeName())) {
									parentOfferingIdVO.setOfferingId(offeringIdNode.getTextContent());
								} else if ("bas:OfferingCode".equals(offeringIdNode.getNodeName())) {
									parentOfferingIdVO.setOfferingCode(offeringIdNode.getTextContent());
								} else if ("bas:PurchaseSeq".equals(offeringIdNode.getNodeName())) {
									parentOfferingIdVO.setPurchaseSeq(offeringIdNode.getTextContent());
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
							primaryOfferingVO.setExpiredTime(primaryOfferingNode.getTextContent());
						} else if ("bas:LatestActiveDate".equals(primaryOfferingNode.getNodeName())) {
							primaryOfferingVO.setLatestActiveDate(primaryOfferingNode.getTextContent());
						} else if ("bas:ProductList".equals(primaryOfferingNode.getNodeName())) {
							List<SubProductInfoVO> subProductInfoList = new ArrayList<SubProductInfoVO>();
							primaryOfferingVO.setSubProductInfoList(subProductInfoList);
							NodeList productListNodes = primaryOfferingNode.getChildNodes();
							for (int k = 0; k < productListNodes.getLength(); k++) {
								Node productListNode = productListNodes.item(k);
								if ("bas:GetSubProductInfo".equals(productListNode.getNodeName())) {
									SubProductInfoVO subProductInfoVO = new SubProductInfoVO();
									subProductInfoList.add(subProductInfoVO);
									NodeList getSubProductInfoNodes = productListNode.getChildNodes();
									for (int l = 0; l < getSubProductInfoNodes.getLength(); l++) {
										Node getSubProductInfoNode = getSubProductInfoNodes.item(l);
										if ("bas:ProductId".equals(getSubProductInfoNode.getNodeName())) {
											subProductInfoVO.setProductId(getSubProductInfoNode.getTextContent());
										} else if ("bas:ProductName".equals(getSubProductInfoNode.getNodeName())) {
											subProductInfoVO.setProductName(getSubProductInfoNode.getTextContent());
										} else if ("bas:Status".equals(getSubProductInfoNode.getNodeName())) {
											subProductInfoVO.setStatus(getSubProductInfoNode.getTextContent());
										} else if ("bas:ResourceInfo".equals(getSubProductInfoNode.getNodeName())) {
											ResourceInfoVO resourceInfoVO = new ResourceInfoVO();
											subProductInfoVO.setResourceInfoVO(resourceInfoVO);
											NodeList resourceInfoNodes = getSubProductInfoNode.getChildNodes();
											for (int m = 0; m < resourceInfoNodes.getLength(); m++) {
												Node resourceInfoNode = resourceInfoNodes.item(m);
												if ("bas:ResourceType".equals(resourceInfoNode.getNodeName())) {
													resourceInfoVO.setResourceType(resourceInfoNode.getTextContent());
												} else if ("bas:ResourceModel".equals(resourceInfoNode.getNodeName())) {
													resourceInfoVO.setResourceModel(resourceInfoNode.getTextContent());
												} else if ("bas:ProductId".equals(resourceInfoNode.getNodeName())) {
													resourceInfoVO.setProductId(resourceInfoNode.getTextContent());
												} else if ("bas:PriceAmount".equals(resourceInfoNode.getNodeName())) {
													resourceInfoVO.setPriceAmount(resourceInfoNode.getTextContent());
												} else if ("bas:ResourceID".equals(resourceInfoNode.getNodeName())) {
													resourceInfoVO.setResourceID(resourceInfoNode.getTextContent());
												} else if ("bas:ResourceCode".equals(resourceInfoNode.getNodeName())) {
													resourceInfoVO.setResourceCode(resourceInfoNode.getTextContent());
												} else if ("bas:ShippingFlag".equals(resourceInfoNode.getNodeName())) {
													resourceInfoVO.setShippingFlag(resourceInfoNode.getTextContent());
												}
											}
										} else if ("bas:ServiceList".equals(getSubProductInfoNode.getNodeName())) {
											List<ServiceVO> serviceList = new ArrayList<ServiceVO>();
											subProductInfoVO.setServiceList(serviceList);
											NodeList serviceListNodes = getSubProductInfoNode.getChildNodes();
											for (int m = 0; m < serviceListNodes.getLength(); m++) {
												Node serviceListNode = serviceListNodes.item(m);
												ServiceVO serviceVO = new ServiceVO();
												serviceList.add(serviceVO);
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
							SupplementaryOfferingVO supplementaryOfferingVO = new SupplementaryOfferingVO();
							List<SubProductInfoVO> subProductInfoList = new ArrayList<SubProductInfoVO>();
							supplementaryOfferingVO.setSubProductInfoList(subProductInfoList);
							supplementaryOfferingList.add(supplementaryOfferingVO);
							NodeList getSubOfferingInfoNodes = supplementaryOfferingListNode.getChildNodes();
							for (int k = 0; k < getSubOfferingInfoNodes.getLength(); k++) {
								Node getSubOfferingInfoNode = getSubOfferingInfoNodes.item(k);
								if ("bas:OfferingId".equals(getSubOfferingInfoNode.getNodeName())) {
									OfferingIdVO offeringIdVO = new OfferingIdVO();
									supplementaryOfferingVO.setOfferingIdVO(offeringIdVO);
									NodeList offeringIdNodes = getSubOfferingInfoNode.getChildNodes();
									for (int l = 0; l < offeringIdNodes.getLength(); l++) {
										Node offeringIdNode = offeringIdNodes.item(l);
										if ("bas:OfferingId".equals(offeringIdNode.getNodeName())) {
											offeringIdVO.setOfferingId(offeringIdNode.getTextContent());
										} else if ("bas:OfferingCode".equals(offeringIdNode.getNodeName())) {
											offeringIdVO.setOfferingCode(offeringIdNode.getTextContent());
										} else if ("bas:PurchaseSeq".equals(offeringIdNode.getNodeName())) {
											offeringIdVO.setPurchaseSeq(offeringIdNode.getTextContent());
										}
									}
								}else if ("bas:ParentOfferingId".equals(getSubOfferingInfoNode.getNodeName())) {
									OfferingIdVO parentOfferingIdVO = new OfferingIdVO();
									supplementaryOfferingVO.setParentOfferingIdVO(parentOfferingIdVO);
									NodeList parentOfferingIdNodes = getSubOfferingInfoNode.getChildNodes();
									for (int l = 0; l < parentOfferingIdNodes.getLength(); l++) {
										Node offeringIdNode = parentOfferingIdNodes.item(l);
										if ("bas:OfferingId".equals(offeringIdNode.getNodeName())) {
											parentOfferingIdVO.setOfferingId(offeringIdNode.getTextContent());
										} else if ("bas:OfferingCode".equals(offeringIdNode.getNodeName())) {
											parentOfferingIdVO.setOfferingCode(offeringIdNode.getTextContent());
										} else if ("bas:PurchaseSeq".equals(offeringIdNode.getNodeName())) {
											parentOfferingIdVO.setPurchaseSeq(offeringIdNode.getTextContent());
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
									supplementaryOfferingVO.setExpiredTime(getSubOfferingInfoNode.getTextContent());
								} else if ("bas:LatestActiveDate".equals(getSubOfferingInfoNode.getNodeName())) {
									supplementaryOfferingVO
											.setLatestActiveDate(getSubOfferingInfoNode.getTextContent());
								} else if ("bas:ProductList".equals(getSubOfferingInfoNode.getNodeName())) {
									NodeList productListNodes = getSubOfferingInfoNode.getChildNodes();
									for (int l = 0; l < productListNodes.getLength(); l++) {
										Node productListNode = productListNodes.item(l);
										if ("bas:GetSubProductInfo".equals(productListNode.getNodeName())) {
											SubProductInfoVO subProductInfoVO = new SubProductInfoVO();
											subProductInfoList.add(subProductInfoVO);
											NodeList getSubProductInfoNodes = productListNode.getChildNodes();
											for (int m = 0; m < getSubProductInfoNodes.getLength(); m++) {
												Node getSubProductInfoNode = getSubProductInfoNodes.item(m);
												if ("bas:ProductId".equals(getSubProductInfoNode.getNodeName())) {
													subProductInfoVO
															.setProductId(getSubProductInfoNode.getTextContent());
												} else
													if ("bas:ProductName".equals(getSubProductInfoNode.getNodeName())) {
													subProductInfoVO
															.setProductName(getSubProductInfoNode.getTextContent());
												} else if ("bas:Status".equals(getSubProductInfoNode.getNodeName())) {
													subProductInfoVO.setStatus(getSubProductInfoNode.getTextContent());
												} else if ("bas:ResourceInfo"
														.equals(getSubProductInfoNode.getNodeName())) {
													ResourceInfoVO resourceInfoVO = new ResourceInfoVO();
													subProductInfoVO.setResourceInfoVO(resourceInfoVO);
													NodeList resourceInfoNodes = getSubProductInfoNode.getChildNodes();
													for (int n = 0; n < resourceInfoNodes.getLength(); n++) {
														Node resourceInfoNode = resourceInfoNodes.item(n);
														if ("bas:ResourceType".equals(resourceInfoNode.getNodeName())) {
															resourceInfoVO
																	.setResourceType(resourceInfoNode.getTextContent());
														} else if ("bas:ResourceModel"
																.equals(resourceInfoNode.getNodeName())) {
															resourceInfoVO.setResourceModel(
																	resourceInfoNode.getTextContent());
														} else if ("bas:ProductId"
																.equals(resourceInfoNode.getNodeName())) {
															resourceInfoVO
																	.setProductId(resourceInfoNode.getTextContent());
														} else if ("bas:PriceAmount"
																.equals(resourceInfoNode.getNodeName())) {
															resourceInfoVO
																	.setPriceAmount(resourceInfoNode.getTextContent());
														} else if ("bas:ResourceID"
																.equals(resourceInfoNode.getNodeName())) {
															resourceInfoVO
																	.setResourceID(resourceInfoNode.getTextContent());
														} else if ("bas:ResourceCode"
																.equals(resourceInfoNode.getNodeName())) {
															resourceInfoVO
																	.setResourceCode(resourceInfoNode.getTextContent());
														} else if ("bas:ShippingFlag"
																.equals(resourceInfoNode.getNodeName())) {
															resourceInfoVO
																	.setShippingFlag(resourceInfoNode.getTextContent());
														}
													}
												} else
													if ("bas:ServiceList".equals(getSubProductInfoNode.getNodeName())) {
													List<ServiceVO> serviceList = new ArrayList<ServiceVO>();
													subProductInfoVO.setServiceList(serviceList);
													NodeList serviceListNodes = getSubProductInfoNode.getChildNodes();
													for (int n = 0; n < serviceListNodes.getLength(); n++) {
														NodeList sserviceListNodes = serviceListNodes.item(n)
																.getChildNodes();
														ServiceVO serviceVO = new ServiceVO();
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
						AddressInfoVO addressInfoVO = new AddressInfoVO();
						addressInfoList.add(addressInfoVO);
						for (int k = 0; k < saddressListNodes.getLength(); k++) {
							Node saddressListNode = saddressListNodes.item(k);
							if ("bas:AddressClass".equals(saddressListNode.getNodeName())) {
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
