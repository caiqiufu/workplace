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
import com.apps.esb.service.bss.app.cbs.vo.queryaccountbalance.AccountCreditVO;
import com.apps.esb.service.bss.app.cbs.vo.queryaccountbalance.BalanceAccountVO;
import com.apps.esb.service.bss.app.cbs.vo.queryaccountbalance.BalanceDetailVO;
import com.apps.esb.service.bss.app.cbs.vo.queryaccountbalance.BalanceResultVO;
import com.apps.esb.service.bss.app.cbs.vo.queryaccountbalance.CreditAmountInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.queryaccountbalance.OutStandingDetailVO;
import com.apps.esb.service.bss.app.cbs.vo.queryaccountbalance.OutStandingVO;
import com.apps.esb.service.bss.app.cbs.vo.queryaccountbalance.QueryAccountBalanceVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;


@Service("queryAccountBalance")
public class QueryAccountBalance  extends SoapMessageHandler  implements BizHandler{

	public QueryAccountBalance() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public ProcessResult process(RequestInfo requestInfo,String parameters,Map<String,Object> extParameters)
			throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("queryAccountBalance");
		return process(this, newRequestInfo, newRequestInfo.getRequestBody().getServiceNumber(),  newRequestInfo.getRequestBody().getExtParameters(), parameters,"ws.cbs.query.timeout");
	}
	public SOAPMessage getRequestSOAPMessage(String serviceNumber, RequestInfo requestInfo) throws Exception {
		SOAPMessage message = messageFactory.createMessage();
		this.getCBSArsHeader("QueryBalanceRequestMsg", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement reqestElement = bodyElement.addChildElement("QueryBalanceRequest");
		SOAPElement objElement = reqestElement.addChildElement("QueryObj","ars");
		SOAPElement subAccessCodeElement = objElement.addChildElement("SubAccessCode","ars");
		SOAPElement primaryIdentityElement = subAccessCodeElement.addChildElement("PrimaryIdentity","arc");
		primaryIdentityElement.addTextNode(serviceNumber);
		//SOAPElement balanceTypeElement = reqestElement.addChildElement("BalanceType");
		//balanceTypeElement.addTextNode("");
		return message;
	}
	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		QueryAccountBalanceVO queryAccountBalanceVO = new QueryAccountBalanceVO();
		ProcessResult result = new ProcessResult();
		result.setVo(queryAccountBalanceVO);
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("cbs:ResultCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("cbs:ResultCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("cbs:ResultDesc").getLength() > 0) {

			String retMsg = document.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}
		if (document.getElementsByTagName("ars:AcctList").getLength() > 0){
			List<BalanceAccountVO> balanceAccountVOs = new ArrayList<BalanceAccountVO>();
			queryAccountBalanceVO.setBalanceAccountList(balanceAccountVOs);
			NodeList acctListNodes = document.getElementsByTagName("ars:AcctList");
			for(int i = 0 ; i < acctListNodes.getLength() ; i++){
				Node acctListNode = acctListNodes.item(i);
				BalanceAccountVO balanceAccountVO = new BalanceAccountVO();
				List<BalanceResultVO> balanceResultVOs = new ArrayList<BalanceResultVO>();
				List<OutStandingVO> outStandingVOs = new ArrayList<OutStandingVO>();
				List<AccountCreditVO> accountCreditVOs = new ArrayList<AccountCreditVO>();
				balanceAccountVO.setBalanceResultList(balanceResultVOs);
				balanceAccountVO.setOutStandingList(outStandingVOs);
				balanceAccountVO.setAccountCreditList(accountCreditVOs);
				balanceAccountVOs.add(balanceAccountVO);
				NodeList nodes = acctListNode.getChildNodes();
				for(int j = 0 ; j < nodes.getLength() ; j++){
					Node node = nodes.item(j);
					if ("ars:AcctKey".equals(node.getNodeName())){
						balanceAccountVO.setAccountKey(node.getTextContent());
					}else if("ars:BalanceResult".equals(node.getNodeName())){
						BalanceResultVO balanceResultVO = new BalanceResultVO();
						List<BalanceDetailVO> balanceDetailVOs = new ArrayList<BalanceDetailVO>();
						balanceResultVO.setBalanceDetailList(balanceDetailVOs);
						balanceResultVOs.add(balanceResultVO);
						NodeList balanceResultNodes = node.getChildNodes();
						for(int k = 0 ; k < balanceResultNodes.getLength() ; k ++){
							Node balanceResultNode = balanceResultNodes.item(k);
							if("arc:BalanceType".equals(balanceResultNode.getNodeName())){
								balanceResultVO.setBalanceType(balanceResultNode.getTextContent());
							}else if("arc:BalanceTypeName".equals(balanceResultNode.getNodeName())){
								balanceResultVO.setBalanceTypeName(balanceResultNode.getTextContent());
							}else if("arc:TotalAmount".equals(balanceResultNode.getNodeName())){
								balanceResultVO.setTotalAmount(balanceResultNode.getTextContent());
							}else if("arc:DepositFlag".equals(balanceResultNode.getNodeName())){
								balanceResultVO.setDepositFlag(balanceResultNode.getTextContent());
							}else if("arc:RefundFlag".equals(balanceResultNode.getNodeName())){
								balanceResultVO.setRefundFlag(balanceResultNode.getTextContent());
							}else if("arc:BalanceDetail".equals(balanceResultNode.getNodeName())){
								BalanceDetailVO balanceDetailVO = new BalanceDetailVO();
								balanceDetailVOs.add(balanceDetailVO);
								NodeList balanceDetailNodes = balanceResultNode.getChildNodes();
								for(int l = 0 ; l < balanceDetailNodes.getLength() ; l++){
									Node balanceDetailNode = balanceDetailNodes.item(l);
									if("arc:BalanceInstanceID".equals(balanceDetailNode.getNodeName())){
										balanceDetailVO.setBalanceInstanceID(balanceDetailNode.getTextContent());
									}else if("arc:Amount".equals(balanceDetailNode.getNodeName())){
										balanceDetailVO.setAmount(balanceDetailNode.getTextContent());
									}else if("arc:InitialAmount".equals(balanceDetailNode.getNodeName())){
										balanceDetailVO.setInitialAmount(balanceDetailNode.getTextContent());
									}else if("arc:EffectiveTime".equals(balanceDetailNode.getNodeName())){
										balanceDetailVO.setEffectiveTime(balanceDetailNode.getTextContent());
									}else if("arc:ExpiryTime".equals(balanceDetailNode.getNodeName())){
										balanceDetailVO.setExpiryTime(balanceDetailNode.getTextContent());
									}
								}
							}
						}
					}else if("ars:OutStandingList".equals(node.getNodeName())){
						NodeList outStandingListNodes = node.getChildNodes();
						OutStandingVO outStandingVO = new OutStandingVO();
						List<OutStandingDetailVO> outStandingDetailVOs = new ArrayList<OutStandingDetailVO>();
						outStandingVO.setOutStandingAmount(outStandingDetailVOs);
						outStandingVOs.add(outStandingVO);
						for(int k = 0 ; k < outStandingListNodes.getLength() ; k++){
							Node outStandingListNode = outStandingListNodes.item(k);
							if("ars:BillCycleID".equals(outStandingListNode.getNodeName())){
								outStandingVO.setBillCycleID(outStandingListNode.getTextContent());
							}else if("ars:BillCycleBeginTime".equals(outStandingListNode.getNodeName())){
								outStandingVO.setBillCycleBeginTime(outStandingListNode.getTextContent());
							}else if("ars:BillCycleEndTime".equals(outStandingListNode.getNodeName())){
								outStandingVO.setBillCycleEndTime(outStandingListNode.getTextContent());
							}else if("ars:DueDate".equals(outStandingListNode.getNodeName())){
								outStandingVO.setDueDate(outStandingListNode.getTextContent());
							}else if("ars:OutStandingDetail".equals(outStandingListNode.getNodeName())){
								OutStandingDetailVO outStandingDetailVO = new OutStandingDetailVO();
								outStandingDetailVOs.add(outStandingDetailVO);
								NodeList outStandingDetailNodes = outStandingListNode.getChildNodes();
								for(int l = 0 ; l < outStandingDetailNodes.getLength() ; l++){
									Node outStandingDetailNode =  outStandingDetailNodes.item(l);
									if("ars:OutStandingAmount".equals(outStandingDetailNode.getNodeName())){
										outStandingDetailVO.setOutStandingAmount(outStandingDetailNode.getTextContent());
									}
								}
								
							}
						}
					}else if("ars:AccountCredit".equals(node.getNodeName())){
						AccountCreditVO accountCreditVO = new AccountCreditVO();
						accountCreditVOs.add(accountCreditVO);
						List<CreditAmountInfoVO> creditAmountInfoVOs = new ArrayList<CreditAmountInfoVO>();
						accountCreditVO.setCreditAmountInfoList(creditAmountInfoVOs);
						NodeList accountCreditNodes = node.getChildNodes();
						for(int k = 0 ; k< accountCreditNodes.getLength() ; k++){
							Node accountCreditNode = accountCreditNodes.item(k);
							if("ars:CreditLimitType".equals(accountCreditNode.getNodeName())){
								accountCreditVO.setCreditLimitType(accountCreditNode.getTextContent());
							}else if("ars:CreditLimitTypeName".equals(accountCreditNode.getNodeName())){
								accountCreditVO.setCreditLimitTypeName(accountCreditNode.getTextContent());
							}else if("ars:TotalCreditAmount".equals(accountCreditNode.getNodeName())){
								accountCreditVO.setTotalCreditAmount(accountCreditNode.getTextContent());
							}else if("ars:TotalUsageAmount".equals(accountCreditNode.getNodeName())){
								accountCreditVO.setTotalUsageAmount(accountCreditNode.getTextContent());
							}else if("ars:TotalRemainAmount".equals(accountCreditNode.getNodeName())){
								accountCreditVO.setTotalRemainAmount(accountCreditNode.getTextContent());
							}else if("ars:CreditAmountInfo".equals(accountCreditNode.getNodeName())){
								CreditAmountInfoVO creditAmountInfoVO = new CreditAmountInfoVO();
								creditAmountInfoVOs.add(creditAmountInfoVO);
								NodeList creditAmountInfoNodes = accountCreditNode.getChildNodes();
								for(int l = 0 ; l< creditAmountInfoNodes.getLength() ; l++){
									Node creditAmountInfoNode = creditAmountInfoNodes.item(l);
									if("ars:CreditInstID".equals(creditAmountInfoNode.getNodeName())){
										creditAmountInfoVO.setCreditInstID(creditAmountInfoNode.getTextContent());
									}else if("ars:LimitClass".equals(creditAmountInfoNode.getNodeName())){
										creditAmountInfoVO.setLimitClass(creditAmountInfoNode.getTextContent());
									}else if("ars:Amount".equals(creditAmountInfoNode.getNodeName())){
										creditAmountInfoVO.setAmount(creditAmountInfoNode.getTextContent());
									}else if("ars:EffectiveTime".equals(creditAmountInfoNode.getNodeName())){
										creditAmountInfoVO.setEffectiveTime(creditAmountInfoNode.getTextContent());
									}else if("ars:ExpireTime".equals(creditAmountInfoNode.getNodeName())){
										creditAmountInfoVO.setExpiryTime(creditAmountInfoNode.getTextContent());
									}
								}
								
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
