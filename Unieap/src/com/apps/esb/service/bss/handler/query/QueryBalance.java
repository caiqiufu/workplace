package com.apps.esb.service.bss.handler.query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.soap.Name;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.apps.esb.service.bss.BssErrorCode;
import com.apps.esb.service.bss.app.cbs.BssCBSServiceUtils;
import com.apps.esb.service.bss.app.cbs.vo.querybalance.AccountCreditVO;
import com.apps.esb.service.bss.app.cbs.vo.querybalance.BalanceAccountVO;
import com.apps.esb.service.bss.app.cbs.vo.querybalance.BalanceDetailVO;
import com.apps.esb.service.bss.app.cbs.vo.querybalance.BalanceResultVO;
import com.apps.esb.service.bss.app.cbs.vo.querybalance.OutstandingVO;
import com.apps.esb.service.bss.app.cbs.vo.querybalance.QueryBalanceVO;
import com.apps.esb.service.bss.app.cbs.vo.queryfreeunit.FreeUnitItemDetailVO;
import com.apps.esb.service.bss.app.cbs.vo.queryfreeunit.FreeUnitItemVO;
import com.apps.esb.service.bss.app.cbs.vo.queryfreeunit.QueryFreeUnitVO;
import com.apps.esb.service.bss.app.vo.BalanceInfoVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetHeader;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.UnieapConstants;
import com.unieap.tools.JSONUtils;

@Service("queryBalance")
public class QueryBalance extends SoapMessageHandler implements BizHandler {

	public QueryBalance() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String serviceNumber, String extParameters, String parameters)
			throws Exception {
		QueryBalanceVO queryBalanceVO = processForQueryBalance(requestInfo, serviceNumber, extParameters, parameters);
		QueryFreeUnitVO queryFreeUnitVO = processForQueryFreeUnit(requestInfo, serviceNumber, extParameters, parameters);
		BalanceInfoVO vo = new BalanceInfoVO();
		ProcessResult result = new ProcessResult();
		if(!BssErrorCode.C0.equals(queryBalanceVO.getProcessResult().getResultCode())){
			result.setResultCode(queryBalanceVO.getProcessResult().getResultCode());
			result.setResultDesc(queryBalanceVO.getProcessResult().getResultDesc());
		}else if(!BssErrorCode.C0.equals(queryFreeUnitVO.getProcessResult().getResultCode())){
			result.setResultCode(queryFreeUnitVO.getProcessResult().getResultCode());
			result.setResultDesc(queryFreeUnitVO.getProcessResult().getResultDesc());
		}else{
			result.setResultCode(BssErrorCode.C0);
			result.setResultDesc("success");
		}
		vo.setBalanceResultVOList(queryBalanceVO.getBalanceAccountList().get(0).getBalanceResultList());
		vo.setFreeUnitItemVOList(queryFreeUnitVO.getFreeUnitItemVOList());
		return getProcessResult(result,vo, serviceNumber, parameters);
	}
	/**
	 * get balance info for display
	 * @param result
	 * @param vo
	 * @param serviceNumber
	 * @param parameters
	 * @return ProcessResult
	 * @throws Exception
	 */
	public ProcessResult getProcessResult(ProcessResult result,BalanceInfoVO vo,String serviceNumber, String parameters) throws Exception{
		result.setServiceNumber(serviceNumber);
		String extParameters = JSONUtils.convertBean2JSON(vo).toString();
		result.setExtParameters(extParameters);
		return result;
	}
	public void getMainBalance(BalanceInfoVO vo, List<BalanceResultVO> balanceResultList){
		if(balanceResultList!=null&&balanceResultList.size()>0){
			BalanceResultVO balanceResultVO;
			for(int i = 0 ; i< balanceResultList.size();i++){
				balanceResultVO = balanceResultList.get(i);
				if("C_MAIN_ACCOUNT".equals(balanceResultVO.getBalanceType())){
					vo.setMainBalance(balanceResultVO.getTotalAmount());
				}
			}
		}
	}
	public void getAvailableCreditLimit(BalanceInfoVO vo, List<AccountCreditVO> accountCreditList){
		if(accountCreditList!=null&&accountCreditList.size()>0){
			AccountCreditVO accountCreditVO;
			for(int i = 0 ; i< accountCreditList.size();i++){
				accountCreditVO = accountCreditList.get(i);
				if("C_OCS_POS_CREDIT_LIMIT".equals(accountCreditVO.getCreditLimitType())){
					vo.setAvailableCreditLimit(accountCreditVO.getTotalRemainAmount());
				}
			}
		}
	}
	public void getFreeUnitSummaryBalance(BalanceInfoVO vo,List<FreeUnitItemVO> freeUnitItemVOList){
		if(freeUnitItemVOList!=null&&freeUnitItemVOList.size()>0){
			FreeUnitItemVO freeUnitItemVO;
			for(int i = 0 ; i< freeUnitItemVOList.size();i++){
				freeUnitItemVO = freeUnitItemVOList.get(i);
				if("C_VOICE_DOMESTIC_TOLL_TIMES".equals(freeUnitItemVO.getFreeUnitType())){
					vo.setVoiceBalance(freeUnitItemVO.getTotalUnusedAmount());
				}else if("C_FREE_ITEMS_FOR_NATIONAL_SMS".equals(freeUnitItemVO.getFreeUnitType())){
					vo.setSmsBalance(freeUnitItemVO.getTotalUnusedAmount());
				}else if("C_FREE_MMS_ITEMS".equals(freeUnitItemVO.getFreeUnitType())){
					vo.setMmsBalance(freeUnitItemVO.getTotalUnusedAmount());
				}else if("C_GPRS_BONUS_FLUS".equals(freeUnitItemVO.getFreeUnitType())){
					vo.setDataBalance(freeUnitItemVO.getTotalUnusedAmount());
				}
			}
		}
	}

	/**
	 * query balance
	 * 
	 * @param requestInfo
	 * @param serviceNumber
	 * @param extParameters
	 * @param parameters
	 * @return ProcessResult
	 * @throws Exception
	 */
	public QueryBalanceVO processForQueryBalance(RequestInfo requestInfo, String serviceNumber, String extParameters,
			String parameters) throws Exception {
		long beginTime = System.currentTimeMillis();
		SOAPMessage request = getQueryBalanceRequestSOAPMessage(serviceNumber, requestInfo);
		SOAPMessage response = callService(serviceNumber, requestInfo, request, beginTime, "ws.cbs.query.timeout");
		QueryBalanceVO queryBalanceVO = getResposeForQueryBalanceResult(response);
		String responseTime = UnieapConstants.getCurrentTime(null, null);
		requestInfo.getRequestHeader().setResponseTime(responseTime);
		String bizCode = requestInfo.getRequestHeader().getBizCode();
		ResponsetHeader responsetHeader = new ResponsetHeader();
		responsetHeader.setResultCode(queryBalanceVO.getProcessResult().getResultCode());
		responsetHeader.setResultDesc(queryBalanceVO.getProcessResult().getResultDesc());
		String systemName = getSystemName(bizCode);
		long endTime = System.currentTimeMillis();
		String during = "" + (endTime - beginTime);
		this.esbLog(request, response, requestInfo.getRequestHeader(),queryBalanceVO.getProcessResult(),during, systemName);
		return queryBalanceVO;
	}

	public SOAPMessage getQueryBalanceRequestSOAPMessage(String serviceNumber, RequestInfo requestInfo)
			throws Exception {
		SOAPMessage message = messageFactory.createMessage();
		BssCBSServiceUtils.getCBSARHeader("QueryBalanceRequestMsg", message);
		Name requstHeaderName = message.getSOAPPart().getEnvelope().createName("QueryBalanceRequestMsg", "ars",
				"http://www.huawei.com/bme/cbsinterface/arservices");
		Iterator childElements = message.getSOAPBody().getChildElements(requstHeaderName);
		SOAPBodyElement bodyElement = (SOAPBodyElement) childElements.next();
		SOAPElement requestGetCustomerBodyElement = bodyElement.addChildElement("QueryBalanceRequest");
		SOAPElement queryObj = requestGetCustomerBodyElement.addChildElement("QueryObj", "ars");
		SOAPElement subAccessCode = queryObj.addChildElement("SubAccessCode", "ars");
		subAccessCode.addChildElement("ServiceNumber", "ars").addTextNode(serviceNumber);
		requestGetCustomerBodyElement.addChildElement("PrimaryIdentity", "arc").addTextNode(serviceNumber);
		return message;
	}

	/**
	 * query free unit
	 * 
	 * @param requestInfo
	 * @param serviceNumber
	 * @param extParameters
	 * @param parameters
	 * @return ProcessResult
	 * @throws Exception
	 */
	public QueryFreeUnitVO processForQueryFreeUnit(RequestInfo requestInfo, String serviceNumber, String extParameters,
			String parameters) throws Exception {
		long beginTime = System.currentTimeMillis();
		SOAPMessage request = getQueryFreeUnitRequestSOAPMessage(serviceNumber, requestInfo);
		SOAPMessage response = callService(serviceNumber, requestInfo, request, beginTime, "ws.cbs.query.timeout");
		QueryFreeUnitVO queryFreeUnitVO = getResposeForQueryFreeUnitResult(response);
		String responseTime = UnieapConstants.getCurrentTime(null, null);
		requestInfo.getRequestHeader().setResponseTime(responseTime);
		String bizCode = requestInfo.getRequestHeader().getBizCode();
		String systemName = getSystemName(bizCode);
		long endTime = System.currentTimeMillis();
		String during = "" + (endTime - beginTime);
		this.esbLog(request, response, requestInfo.getRequestHeader(),queryFreeUnitVO.getProcessResult(), during, systemName);
		return queryFreeUnitVO;
	}

	public SOAPMessage getQueryFreeUnitRequestSOAPMessage(String serviceNumber, RequestInfo requestInfo)
			throws Exception {
		SOAPMessage message = messageFactory.createMessage();
		BssCBSServiceUtils.getCBSBBHeader("QueryFreeUnitRequestMsg", message);
		Name requstHeaderName = message.getSOAPPart().getEnvelope().createName("QueryFreeUnitRequestMsg", "bbs",
				"http://www.huawei.com/bme/cbsinterface/bbservices");
		Iterator childElements = message.getSOAPBody().getChildElements(requstHeaderName);
		SOAPBodyElement bodyElement = (SOAPBodyElement) childElements.next();
		SOAPElement requesBodyElement = bodyElement.addChildElement("QueryFreeUnitRequest");
		SOAPElement queryObj = requesBodyElement.addChildElement("QueryObj", "bbs");
		SOAPElement subAccessCode = queryObj.addChildElement("SubAccessCode", "bbs");
		subAccessCode.addChildElement("PrimaryIdentity", "bbs").addTextNode(serviceNumber);
		return message;
	}

	public QueryBalanceVO getResposeForQueryBalanceResult(SOAPMessage response) throws Exception {
		QueryBalanceVO vo = new QueryBalanceVO();
		ProcessResult processResult = new ProcessResult();
		vo.setProcessResult(processResult);
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("cbs:ResultCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("cbs:ResultCode").item(0).getTextContent();
			processResult.setResultCode(retCode);
		}
		if (document.getElementsByTagName("cbs:ResultDesc").getLength() > 0) {

			String retMsg = document.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent();
			processResult.setResultDesc(retMsg);
		}
		////////////////////
		if (document.getElementsByTagName("ars:AcctList").getLength() > 0) {

			NodeList acctListNodes = document.getElementsByTagName("ars:AcctList");
			List<BalanceAccountVO> balanceAccountList = new ArrayList<BalanceAccountVO>();
			Node acctNode;
			for (int i = 0; i < acctListNodes.getLength(); i++) {
				acctNode = acctListNodes.item(i);
				NodeList acctChilds = acctNode.getChildNodes();
				Node accChildNode;
				BalanceAccountVO balanceAccountVO = new BalanceAccountVO();
				for (int j = 0; j < acctChilds.getLength(); j++) {
					accChildNode = acctChilds.item(j);
					if ("ars:AcctKey".equals(accChildNode.getNodeName())) {
						balanceAccountVO.setAccountKey(accChildNode.getTextContent());
					} else if ("ars:BalanceResult".equals(accChildNode.getNodeName())) {
						List<BalanceResultVO> balanceResultList = new ArrayList<BalanceResultVO>();
						NodeList balanceResultNodes = accChildNode.getChildNodes();
						Node balanceResultNode;
						for (int k = 0; k < balanceResultNodes.getLength(); k++) {
							balanceResultNode = balanceResultNodes.item(k);
							BalanceResultVO balanceResultVO = new BalanceResultVO();
							if ("arc:BalanceType".equals(balanceResultNode.getNodeName())) {
								balanceResultVO.setBalanceType(balanceResultNode.getTextContent());
							} else if ("arc:BalanceTypeName".equals(balanceResultNode.getNodeName())) {
								balanceResultVO.setBalanceTypeName(balanceResultNode.getTextContent());
							} else if ("arc:TotalAmount".equals(balanceResultNode.getNodeName())) {
								balanceResultVO.setTotalAmount(balanceResultNode.getTextContent());
							} else if ("arc:DepositFlag".equals(balanceResultNode.getNodeName())) {
								balanceResultVO.setDepositFlag(balanceResultNode.getTextContent());
							} else if ("arc:RefundFlag".equals(balanceResultNode.getNodeName())) {
								balanceResultVO.setRefundFlag(balanceResultNode.getTextContent());
							} else if ("arc:BalanceDetail".equals(balanceResultNode.getNodeName())) {
								NodeList balanceDetailNodes = balanceResultNode.getChildNodes();
								List<BalanceDetailVO> balanceDetailList = new ArrayList<BalanceDetailVO>();
								Node balanceDetailNode;
								for (int l = 0; l < balanceDetailNodes.getLength(); l++) {
									balanceDetailNode = balanceDetailNodes.item(l);
									BalanceDetailVO balanceDetailVO = new BalanceDetailVO();
									if ("arc:BalanceInstanceID".equals(balanceDetailNode.getNodeName())) {
										balanceDetailVO.setBalanceInstanceID(balanceDetailNode.getTextContent());
									} else if ("arc:Amount".equals(balanceDetailNode.getNodeName())) {
										balanceDetailVO.setAmount(balanceDetailNode.getTextContent());
									} else if ("arc:InitialAmount".equals(balanceDetailNode.getNodeName())) {
										balanceDetailVO.setInitialAmount(balanceDetailNode.getTextContent());
									} else if ("arc:EffectiveTime".equals(balanceDetailNode.getNodeName())) {
										balanceDetailVO.setEffectiveTime(balanceDetailNode.getTextContent());
									} else if ("arc:ExpireTime".equals(balanceDetailNode.getNodeName())) {
										balanceDetailVO.setExpiryTime(balanceDetailNode.getTextContent());
									}
									balanceDetailList.add(balanceDetailVO);
								}
								balanceResultVO.setBalanceDetailList(balanceDetailList);
							}
						}
						balanceAccountVO.setBalanceResultList(balanceResultList);
					}
					if ("ars:OutStandingList".equals(accChildNode.getNodeName())) {
						List<OutstandingVO> outstandingList = new ArrayList<OutstandingVO>();
						NodeList outStandingListNodes = accChildNode.getChildNodes();
						Node outStandingNode;
						for (int m = 0; m < outStandingListNodes.getLength(); m++) {
							outStandingNode = outStandingListNodes.item(m);
							OutstandingVO outstandingVO = new OutstandingVO();
							if ("ars:BillCycleID".equals(outStandingNode.getNodeName())) {
								outstandingVO.setBillCycleID(outStandingNode.getTextContent());
							} else if ("ars:BillCycleBeginTime".equals(outStandingNode.getNodeName())) {
								outstandingVO.setBillCycleBeginTime(outStandingNode.getTextContent());
							} else if ("ars:BillCycleEndTime".equals(outStandingNode.getNodeName())) {
								outstandingVO.setBillCycleEndTime(outStandingNode.getTextContent());
							} else if ("ars:DueDate".equals(outStandingNode.getNodeName())) {
								outstandingVO.setDueDate(outStandingNode.getTextContent());
							} else if ("ars:OutStandingDetail".equals(outStandingNode.getNodeName())) {
								outstandingVO
										.setOutstandingAmount(outStandingNode.getChildNodes().item(0).getTextContent());
							}
							outstandingList.add(outstandingVO);
						}
						balanceAccountVO.setOutstandingList(outstandingList);
					}
					if ("ars:AccountCredit".equals(accChildNode.getNodeName())) {
						List<AccountCreditVO> accountCreditList = new ArrayList<AccountCreditVO>();
						NodeList accountCreditListNodes = accChildNode.getChildNodes();
						Node accountCreditNode;
						for (int n = 0; n < accountCreditListNodes.getLength(); n++) {
							AccountCreditVO accountCreditVO = new AccountCreditVO();
							accountCreditNode = accountCreditListNodes.item(n);
							if ("ars:CreditLimitType".equals(accountCreditNode.getNodeName())) {
								accountCreditVO.setCreditLimitType(accountCreditNode.getTextContent());
							} else if ("ars:CreditLimitTypeName".equals(accountCreditNode.getNodeName())) {
								accountCreditVO.setCreditLimitTypeName(accountCreditNode.getTextContent());
							} else if ("ars:TotalCreditAmount".equals(accountCreditNode.getNodeName())) {
								accountCreditVO.setTotalCreditAmount(accountCreditNode.getTextContent());
							} else if ("ars:TotalUsageAmount".equals(accountCreditNode.getNodeName())) {
								accountCreditVO.setTotalUsageAmount(accountCreditNode.getTextContent());
							} else if ("ars:TotalRemainAmount".equals(accountCreditNode.getNodeName())) {
								accountCreditVO.setTotalRemainAmount(accountCreditNode.getTextContent());
							} else if ("ars:TotalRemainAmount".equals(accountCreditNode.getNodeName())) {
								accountCreditVO.setTotalRemainAmount(accountCreditNode.getTextContent());
							}
							accountCreditList.add(accountCreditVO);
						}
						balanceAccountVO.setAccountCreditList(accountCreditList);
					}
				}
				balanceAccountList.add(balanceAccountVO);
				vo.setBalanceAccountList(balanceAccountList);
			}
		}
		return vo;
	}

	public QueryFreeUnitVO getResposeForQueryFreeUnitResult(SOAPMessage response) throws Exception {
		QueryFreeUnitVO vo = new QueryFreeUnitVO();
		ProcessResult processResult = new ProcessResult();
		vo.setProcessResult(processResult);
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("cbs:ResultCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("cbs:ResultCode").item(0).getTextContent();
			processResult.setResultCode(retCode);
		}
		if (document.getElementsByTagName("cbs:ResultDesc").getLength() > 0) {

			String retMsg = document.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent();
			processResult.setResultDesc(retMsg);
		}
		////////////////////
		if (document.getElementsByTagName("bbs:FreeUnitItem").getLength() > 0) {

			NodeList freeUnitItemNodes = document.getElementsByTagName("bbs:FreeUnitItem");
			List<FreeUnitItemVO> freeUnitItemList = new ArrayList<FreeUnitItemVO>();
			Node freeUnitItemNode;
			for (int i = 0; i < freeUnitItemNodes.getLength(); i++) {
				freeUnitItemNode = freeUnitItemNodes.item(i);
				NodeList freeUnitItems = freeUnitItemNode.getChildNodes();
				Node freeUnitNode;
				FreeUnitItemVO freeUnitItemVO = new FreeUnitItemVO();
				for (int j = 0; j < freeUnitItems.getLength(); j++) {
					freeUnitNode = freeUnitItems.item(j);
					if ("bbs:FreeUnitType".equals(freeUnitNode.getNodeName())) {
						freeUnitItemVO.setFreeUnitType(freeUnitNode.getTextContent());
					} else if ("bbs:FreeUnitTypeName".equals(freeUnitNode.getNodeName())) {
						freeUnitItemVO.setFreeUnitTypeName(freeUnitNode.getTextContent());
					} else if ("bbs:MeasureUnit".equals(freeUnitNode.getNodeName())) {
						freeUnitItemVO.setMeasureUnit(freeUnitNode.getTextContent());
					} else if ("bbs:MeasureUnitName".equals(freeUnitNode.getNodeName())) {
						freeUnitItemVO.setMeasureUnitName(freeUnitNode.getTextContent());
					} else if ("bbs:TotalInitialAmount".equals(freeUnitNode.getNodeName())) {
						freeUnitItemVO.setTotalInitialAmount(freeUnitNode.getTextContent());
					} else if ("bbs:TotalUnusedAmount".equals(freeUnitNode.getNodeName())) {
						freeUnitItemVO.setTotalUnusedAmount(freeUnitNode.getTextContent());
					} else if ("bbs:FreeUnitItemDetail".equals(freeUnitNode.getNodeName())) {
						List<FreeUnitItemDetailVO> freeUnitItemDetailList = new ArrayList<FreeUnitItemDetailVO>();
						NodeList freeUnitItemDetailNodes = freeUnitNode.getChildNodes();
						Node freeUnitItemDetailNode;
						for (int k = 0; k < freeUnitItemDetailNodes.getLength(); k++) {
							freeUnitItemDetailNode = freeUnitItemDetailNodes.item(k);
							FreeUnitItemDetailVO freeUnitItemDetailVO = new FreeUnitItemDetailVO();
							if ("bbs:FreeUnitInstanceID".equals(freeUnitItemDetailNode.getNodeName())) {
								freeUnitItemDetailVO.setFreeUnitInstanceID(freeUnitItemDetailNode.getTextContent());
							} else if ("bbs:InitialAmount".equals(freeUnitItemDetailNode.getNodeName())) {
								freeUnitItemDetailVO.setInitialAmount(freeUnitItemDetailNode.getTextContent());
							} else if ("bbs:CurrentAmount".equals(freeUnitItemDetailNode.getNodeName())) {
								freeUnitItemDetailVO.setCurrentAmount(freeUnitItemDetailNode.getTextContent());
							} else if ("bbs:EffectiveTime".equals(freeUnitItemDetailNode.getNodeName())) {
								freeUnitItemDetailVO.setEffectiveTime(freeUnitItemDetailNode.getTextContent());
							} else if ("bbs:ExpireTime".equals(freeUnitItemDetailNode.getNodeName())) {
								freeUnitItemDetailVO.setExpireTime(freeUnitItemDetailNode.getTextContent());
							}
							freeUnitItemDetailList.add(freeUnitItemDetailVO);
						}
					}
				}
				freeUnitItemList.add(freeUnitItemVO);
			}
			vo.setFreeUnitItemVOList(freeUnitItemList);
		}
		return vo;
	}

	@Override
	public RequestInfo getRequestInfoFromSOAPMessage(SOAPMessage request) throws Exception {
		return null;
	}

	@Override
	public ResponsetInfo getResponseInfoFromSOAPMessage(SOAPMessage response) throws Exception {
		return null;
	}

	@Override
	public SOAPMessage getRequestSOAPMessage(String serviceNumber, RequestInfo requestInfo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
