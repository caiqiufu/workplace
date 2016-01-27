package com.apps.esb.service.bss.handler.mshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.app.vo.account.balance.CreditLimitVO;
import com.apps.esb.service.bss.app.vo.account.balance.OutStandingVO;
import com.apps.esb.service.bss.app.vo.account.balance.QueryBalanceVO;
import com.apps.esb.service.bss.app.vo.account.basicinfo.AccountVO;
import com.apps.esb.service.bss.app.vo.account.billmedium.BillMediumVO;
import com.apps.esb.service.bss.app.vo.subscriber.basicinfo.SubscriberVO;
import com.apps.esb.service.bss.app.vo.subscriber.offering.OfferingVO;
import com.apps.esb.service.bss.app.vo.subscriber.offering.ServiceVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.CustomizeBizHanlder;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.vo.mshop.subscriber.MshopOfferingVO;
import com.apps.esb.service.bss.vo.mshop.subscriber.MshopSubscriberVO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;

@Service("mShopQuerySubscriber")
public class MshopQuerySubscriber implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		BizHandler querySubscriber = (BizHandler) ServiceUtils.getBeanByTenantId("querySubscriber");
		ProcessResult processSubscriber = querySubscriber.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(processSubscriber.getResultCode())) {
			return processSubscriber;
		}
		
		BizHandler queryAccountBalance = (BizHandler) ServiceUtils.getBeanByTenantId("queryBalance");
		ProcessResult processAccountBalance = queryAccountBalance.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(processAccountBalance.getResultCode())) {
			return processAccountBalance;
		}
		
		
		BizHandler queryAccount = (BizHandler) ServiceUtils.getBeanByTenantId("queryAccount");
		ProcessResult processAccount = queryAccount.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(processAccount.getResultCode())) {
			return processAccount;
		}
		
		SubscriberVO subscriberVO = (SubscriberVO) processSubscriber.getVo();
		QueryBalanceVO queryBalanceVO = (QueryBalanceVO) processAccountBalance.getVo();
		AccountVO accountVO = (AccountVO) processAccount.getVo();
		MshopSubscriberVO mshopSubscriberVO = getMshopSubscriberVO(subscriberVO,queryBalanceVO,accountVO.getBillMediumList());
		mshopSubscriberVO.setBillCycle(accountVO.getBillCycle());
		mshopSubscriberVO.setBillCycleDesc(accountVO.getBillCycleDesc());
		if (handler != null) {
			String custHandlerName = handler.get("custHandlerName");
			if (!StringUtils.isEmpty(custHandlerName)) {
				CustomizeBizHanlder custHandler = (CustomizeBizHanlder) ServiceUtils.getBean(custHandlerName);
				List<Object> vos = new ArrayList<Object>();
				vos.add(processSubscriber.getVo());
				vos.add(processAccountBalance.getVo());
				vos.add(processAccount.getVo());
				custHandler.process(requestInfo, handler, extParameters, mshopSubscriberVO, vos);
			}
		}
		ProcessResult processResult = new ProcessResult();
		processResult.setResultCode(UnieapConstants.C0);
		processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
		JSONObject dataJson = JSONUtils.convertBean2JSON(mshopSubscriberVO);
		processResult.setExtParameters(dataJson.toString());
		return processResult;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	private MshopSubscriberVO getMshopSubscriberVO(SubscriberVO subscriberVO,QueryBalanceVO queryBalanceVO,List<BillMediumVO> billMediumList) {
		MshopSubscriberVO mshopSubscriberVO = new MshopSubscriberVO();
		mshopSubscriberVO.setAccountId(subscriberVO.getAccountId());
		mshopSubscriberVO.setActivationDate(subscriberVO.getActivateTime());
		mshopSubscriberVO.setBillCycle(subscriberVO.getAccountVO().getBillCycle());
		mshopSubscriberVO.setBillCycleDesc(subscriberVO.getAccountVO().getBillCycleDesc());
		mshopSubscriberVO.setBillMediumList(billMediumList);
		mshopSubscriberVO.setDeactivationDate(subscriberVO.getBarTwoWayTime());
		mshopSubscriberVO.setICCID(subscriberVO.getICCID());
		mshopSubscriberVO.setIMEI(subscriberVO.getIMEI());
		mshopSubscriberVO.setLanguage(subscriberVO.getLanguage());
		mshopSubscriberVO.setPaymentFlag(subscriberVO.getPaymentFlag());
		mshopSubscriberVO.setPaymentFlagDesc(subscriberVO.getPaymentFlagDesc());
		mshopSubscriberVO.setResignedTimestamp(subscriberVO.getResignedTimestamp());
		mshopSubscriberVO.setServiceNumber(subscriberVO.getServiceNumber());
		mshopSubscriberVO.setStatus(subscriberVO.getStatus());
		mshopSubscriberVO.setStatusDesc(subscriberVO.getStatusDesc());
		mshopSubscriberVO.setSuspesionDate(subscriberVO.getBarOneWayTime());
		mshopSubscriberVO.setStatusReason(subscriberVO.getStatusReason());
		mshopSubscriberVO.setStatusReasonDesc(subscriberVO.getStatusReasonDesc());
		mshopSubscriberVO.setSubscriberId(subscriberVO.getSubscriberId());
		setOffering(mshopSubscriberVO,subscriberVO);
		setBalance(mshopSubscriberVO,queryBalanceVO);
		setService(mshopSubscriberVO,subscriberVO);
		
		mshopSubscriberVO.setEffectiveTime(subscriberVO.getEffectiveTime());
		mshopSubscriberVO.setExpiryTime(subscriberVO.getExpiryTime());
		mshopSubscriberVO.setExtAttris(subscriberVO.getExtAttris());
		
		return mshopSubscriberVO;
	}
	private void setBalance(MshopSubscriberVO mshopSubscriberVO,QueryBalanceVO queryBalanceVO){
		mshopSubscriberVO.setTotalBalance(queryBalanceVO.getTotalBalance());
		mshopSubscriberVO.setOutstandingBill(queryBalanceVO.getTotalOutStandingAmount());
		mshopSubscriberVO.setTotalCreditLimit(queryBalanceVO.getTotalCreditLimitAmount());
		mshopSubscriberVO.setAvailableAmount(queryBalanceVO.getTotalAvailableAmount());
		List<OutStandingVO> outStandingList = queryBalanceVO.getOutStandingList();
		if(outStandingList!=null&&outStandingList.size()>0){
			OutStandingVO outStandingVO = outStandingList.get(0);
			//mshopSubscriberVO.setOutstandingBill(outStandingVO.getOutStandingAmount());
			mshopSubscriberVO.setPaymentDueDate(outStandingVO.getDueDate());
		}
		List<CreditLimitVO> creditLimitList = queryBalanceVO.getCreditLimitList();
		if(creditLimitList!=null&&creditLimitList.size()>0){
			for(int i = 0 ; i < creditLimitList.size() ; i++){
				CreditLimitVO creditLimitVO = creditLimitList.get(0);
				if("1".equals(creditLimitVO.getCreditLimitType())){
					mshopSubscriberVO.setInitinalCreditLimit(creditLimitVO.getCreditLimitType());
				}else if("2".equals(creditLimitVO.getCreditLimitType())){
					mshopSubscriberVO.setTempCreditLimit(creditLimitVO.getTotalCreditAmount());
				}
			}
		}
	}
	private void setOffering(MshopSubscriberVO mshopSubscriberVO,SubscriberVO subscriberVO){
		mshopSubscriberVO.setPrimaryOffering(exchangeOffering(subscriberVO.getPrimaryOfferingVO()));
		List<OfferingVO> supplementaryOfferingList = subscriberVO.getSupplementaryOfferingList();
		if(supplementaryOfferingList!=null&&supplementaryOfferingList.size()>0){
			List<MshopOfferingVO> supplementaryOfferings = new ArrayList<MshopOfferingVO>();
			for(int i = 0 ; i < supplementaryOfferingList.size(); i++ ){
				OfferingVO offeringVO = supplementaryOfferingList.get(i);
				supplementaryOfferings.add(exchangeOffering(offeringVO));
			}
			mshopSubscriberVO.setSupplementaryOfferingList(supplementaryOfferings);
		}
	}
	private void setService(MshopSubscriberVO mshopSubscriberVO,SubscriberVO subscriberVO){
		List<ServiceVO> serviceList = new ArrayList<ServiceVO>();
		mshopSubscriberVO.setServiceList(serviceList);
		serviceList.addAll(subscriberVO.getPrimaryOfferingVO().getServiceList());
		List<OfferingVO> supplementaryOfferingList = subscriberVO.getSupplementaryOfferingList();
		if(supplementaryOfferingList!=null&&supplementaryOfferingList.size()>0){
			for(int i = 0 ; i < supplementaryOfferingList.size(); i++ ){
				OfferingVO offeringVO = supplementaryOfferingList.get(i);
				serviceList.addAll(offeringVO.getServiceList());
			}
		}
	}
	private MshopOfferingVO exchangeOffering(OfferingVO primaryOfferingVO){
		MshopOfferingVO primaryOffering = new MshopOfferingVO();
		primaryOffering.setStartTime(primaryOfferingVO.getEffectiveTime());
		primaryOffering.setEffectiveTime(primaryOfferingVO.getEffectiveTime());
		primaryOffering.setExpiryTime(primaryOfferingVO.getExpiryTime());
		primaryOffering.setOfferingId(primaryOfferingVO.getOfferingId());
		primaryOffering.setOfferingName(primaryOfferingVO.getOfferingName());
		primaryOffering.setOfferingType(primaryOfferingVO.getOfferingType());
		primaryOffering.setOfferingTypeDesc(primaryOfferingVO.getOfferingTypeDesc());
		primaryOffering.setOneTimeFee(primaryOfferingVO.getOneTimeFee());
		primaryOffering.setRecurringFee(primaryOfferingVO.getRentFee());
		primaryOffering.setStatus(primaryOfferingVO.getStatusDesc());
		
		primaryOffering.setEffectiveTime(primaryOfferingVO.getEffectiveTime());
		primaryOffering.setExpiryTime(primaryOfferingVO.getExpiryTime());
		primaryOffering.setExtAttris(primaryOfferingVO.getExtAttris());
		
		return primaryOffering;
	}
}
