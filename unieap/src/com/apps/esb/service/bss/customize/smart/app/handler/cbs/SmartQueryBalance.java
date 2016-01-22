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
import com.apps.esb.service.bss.app.vo.account.balance.BalanceDetailVO;
import com.apps.esb.service.bss.app.vo.account.balance.BalanceVO;
import com.apps.esb.service.bss.app.vo.account.balance.CreditLimitDetailVO;
import com.apps.esb.service.bss.app.vo.account.balance.CreditLimitVO;
import com.apps.esb.service.bss.app.vo.account.balance.OutStandingDetailVO;
import com.apps.esb.service.bss.app.vo.account.balance.OutStandingVO;
import com.apps.esb.service.bss.app.vo.account.balance.QueryBalanceVO;
import com.apps.esb.service.bss.customize.smart.app.handler.CustSoapMessageHandler;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.interfacecall.SoapCallUtils;


@Service("queryBalance_1")
public class SmartQueryBalance extends CustSoapMessageHandler implements BizHandler{

	@Override
	public ProcessResult process(RequestInfo requestInfo,Map<String, String> handler,Map<String,Object> extParameters)
			throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("queryBalance");
		return SoapCallUtils.process(this, newRequestInfo, newRequestInfo.getRequestBody().getServiceNumber(),  newRequestInfo.getRequestBody().getExtParameters(), handler,"ws.cbs.query.timeout");
	}
	public SOAPMessage getRequestSOAPMessage(String serviceNumber, RequestInfo requestInfo) throws Exception {
		SOAPMessage message = SoapCallUtils.getMessageFactory().createMessage();
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
		QueryBalanceVO queryBalanceVO = new QueryBalanceVO();
		ProcessResult result = new ProcessResult();
		result.setVo(queryBalanceVO);
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
			NodeList acctListNodes = document.getElementsByTagName("ars:AcctList");
			for(int i = 0 ; i < acctListNodes.getLength() ; i++){
				Node acctListNode = acctListNodes.item(i);
				List<BalanceVO> balanceList = new ArrayList<BalanceVO>();
				List<OutStandingVO> outStandingList = new ArrayList<OutStandingVO>();
				List<CreditLimitVO> creditLimitList = new ArrayList<CreditLimitVO>();
				queryBalanceVO.setBalanceList(balanceList);
				queryBalanceVO.setOutStandingList(outStandingList);
				queryBalanceVO.setCreditLimitList(creditLimitList);
				NodeList nodes = acctListNode.getChildNodes();
				for(int j = 0 ; j < nodes.getLength() ; j++){
					Node node = nodes.item(j);
					BalanceVO balanceVO = new BalanceVO();
					if ("ars:AcctKey".equals(node.getNodeName())){
						queryBalanceVO.setAccountId(node.getTextContent());
					}else if("ars:BalanceResult".equals(node.getNodeName())){
						List<BalanceDetailVO> balanceDetailList = new ArrayList<BalanceDetailVO>();
						balanceVO.setBalanceDetailList(balanceDetailList);
						balanceList.add(balanceVO);
						NodeList balanceResultNodes = node.getChildNodes();
						for(int k = 0 ; k < balanceResultNodes.getLength() ; k ++){
							Node balanceResultNode = balanceResultNodes.item(k);
							if("arc:BalanceType".equals(balanceResultNode.getNodeName())){
								balanceVO.setBalanceType(balanceResultNode.getTextContent());
							}else if("arc:BalanceTypeName".equals(balanceResultNode.getNodeName())){
								balanceVO.setBalanceTypeDesc(balanceResultNode.getTextContent());
							}else if("arc:TotalAmount".equals(balanceResultNode.getNodeName())){
								balanceVO.setTotalAmount(balanceResultNode.getTextContent());
							}else if("arc:DepositFlag".equals(balanceResultNode.getNodeName())){
								balanceVO.setDepositFlag(balanceResultNode.getTextContent());
							}else if("arc:RefundFlag".equals(balanceResultNode.getNodeName())){
								balanceVO.setRefundFlag(balanceResultNode.getTextContent());
							}else if("arc:BalanceDetail".equals(balanceResultNode.getNodeName())){
								BalanceDetailVO balanceDetailVO = new BalanceDetailVO();
								balanceDetailList.add(balanceDetailVO);
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
						List<OutStandingDetailVO> outStandingDetailList = new ArrayList<OutStandingDetailVO>();
						outStandingVO.setOutStandingDetailList(outStandingDetailList);
						outStandingList.add(outStandingVO);
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
								outStandingDetailList.add(outStandingDetailVO);
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
						CreditLimitVO creditLimitVO = new CreditLimitVO();
						creditLimitList.add(creditLimitVO);
						List<CreditLimitDetailVO> creditLimitDetailList = new ArrayList<CreditLimitDetailVO>();
						creditLimitVO.setCreditLimitDetailList(creditLimitDetailList);
						NodeList accountCreditNodes = node.getChildNodes();
						for(int k = 0 ; k< accountCreditNodes.getLength() ; k++){
							Node accountCreditNode = accountCreditNodes.item(k);
							if("ars:CreditLimitType".equals(accountCreditNode.getNodeName())){
								creditLimitVO.setCreditLimitType(accountCreditNode.getTextContent());
							}else if("ars:CreditLimitTypeName".equals(accountCreditNode.getNodeName())){
								creditLimitVO.setCreditLimitTypeDesc(accountCreditNode.getTextContent());
							}else if("ars:TotalCreditAmount".equals(accountCreditNode.getNodeName())){
								creditLimitVO.setTotalCreditAmount(accountCreditNode.getTextContent());
							}else if("ars:TotalUsageAmount".equals(accountCreditNode.getNodeName())){
								creditLimitVO.setTotalUsageAmount(accountCreditNode.getTextContent());
							}else if("ars:TotalRemainAmount".equals(accountCreditNode.getNodeName())){
								creditLimitVO.setTotalRemainAmount(accountCreditNode.getTextContent());
							}else if("ars:CreditAmountInfo".equals(accountCreditNode.getNodeName())){
								CreditLimitDetailVO creditLimitDetailVO = new CreditLimitDetailVO();
								creditLimitDetailList.add(creditLimitDetailVO);
								NodeList creditAmountInfoNodes = accountCreditNode.getChildNodes();
								for(int l = 0 ; l< creditAmountInfoNodes.getLength() ; l++){
									Node creditAmountInfoNode = creditAmountInfoNodes.item(l);
									if("ars:CreditInstID".equals(creditAmountInfoNode.getNodeName())){
										creditLimitDetailVO.setCreditInstID(creditAmountInfoNode.getTextContent());
									}else if("ars:LimitClass".equals(creditAmountInfoNode.getNodeName())){
										creditLimitDetailVO.setLimitClass(creditAmountInfoNode.getTextContent());
									}else if("ars:Amount".equals(creditAmountInfoNode.getNodeName())){
										creditLimitDetailVO.setAmount(creditAmountInfoNode.getTextContent());
									}else if("ars:EffectiveTime".equals(creditAmountInfoNode.getNodeName())){
										creditLimitDetailVO.setEffectiveTime(creditAmountInfoNode.getTextContent());
									}else if("ars:ExpireTime".equals(creditAmountInfoNode.getNodeName())){
										creditLimitDetailVO.setExpiryTime(creditAmountInfoNode.getTextContent());
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
