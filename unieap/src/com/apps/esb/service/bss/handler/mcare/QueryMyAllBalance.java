package com.apps.esb.service.bss.handler.mcare;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.vo.account.balance.BalanceDetailVO;
import com.apps.esb.service.bss.app.vo.account.balance.BalanceVO;
import com.apps.esb.service.bss.app.vo.account.balance.QueryBalanceVO;
import com.apps.esb.service.bss.app.vo.subscriber.cdr.QueryCdrVO;
import com.apps.esb.service.bss.app.vo.subscriber.freeresource.FreeResourceDetailVO;
import com.apps.esb.service.bss.app.vo.subscriber.freeresource.FreeResourceVO;
import com.apps.esb.service.bss.app.vo.subscriber.freeresource.QueryFreeResourceVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.CustomizeBizHanlder;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.vo.macre.allbalance.AllBalanceVO;
import com.apps.esb.service.bss.vo.macre.allbalance.MoneyBalanceVO;
import com.apps.esb.service.bss.vo.macre.allbalance.MyBalanceDetailVO;
import com.apps.esb.service.bss.vo.macre.allbalance.VoiceBalanceVO;
import com.apps.esb.service.bss.vo.macre.databalance.DataBalanceVO;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;

@Service("queryMyAllBalance")
public class QueryMyAllBalance implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		/*BizHandler appHandler = (BizHandler) ServiceUtils.getBean(handler.get("appHandlerName"));
		ProcessResult queryMyAllBalanceProcessResult = appHandler.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(queryMyAllBalanceProcessResult.getResultCode())) {
			return queryMyAllBalanceProcessResult;
		}*/
		
		BizHandler queryAccountBalance = (BizHandler) ServiceUtils.getBeanByTenantId("queryBalance");
		ProcessResult processAccountBalance = queryAccountBalance.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(processAccountBalance.getResultCode())) {
			return processAccountBalance;
		}
		BizHandler queryFreeReource = (BizHandler) ServiceUtils.getBeanByTenantId("queryFreeReource");
		ProcessResult processFreeReource = queryFreeReource.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(processFreeReource.getResultCode())) {
			return processFreeReource;
		}
		BizHandler queryUsage = (BizHandler) ServiceUtils.getBeanByTenantId("queryUsage");
		ProcessResult processQueryUsage = queryUsage.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(processQueryUsage.getResultCode())) {
			return processQueryUsage;
		}
		QueryBalanceVO queryBalanceVO = (QueryBalanceVO) processAccountBalance.getVo();
		QueryFreeResourceVO queryFreeResourceVO = (QueryFreeResourceVO) processFreeReource.getVo();
		QueryCdrVO queryCdrVO = (QueryCdrVO) processQueryUsage.getVo();

		AllBalanceVO allBalanceVO = getAllBalanceVO(queryFreeResourceVO, queryBalanceVO, queryCdrVO);
		if (handler != null) {
			String custHandlerName = handler.get("custHandlerName");
			if (!StringUtils.isEmpty(custHandlerName)) {
				List<Object> vos = new ArrayList<Object>();
				vos.add(processAccountBalance.getVo());
				vos.add(processFreeReource.getVo());
				vos.add(processQueryUsage.getVo());
				CustomizeBizHanlder custHandler = (CustomizeBizHanlder) ServiceUtils.getBean(custHandlerName);
				custHandler.process(requestInfo, handler, extParameters, allBalanceVO, vos);
			}
		}

		JSONObject dataBalanceJson = JSONUtils.convertBean2JSON(allBalanceVO);
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("allBalanceInfo", dataBalanceJson);
		ProcessResult processResult = new ProcessResult();
		processResult.setResultCode(UnieapConstants.C0);
		processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
		processResult.setServiceNumber(requestInfo.getRequestBody().getServiceNumber());
		processResult.setExtParameters(jsonResult.toString());
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

	public AllBalanceVO getAllBalanceVO(QueryFreeResourceVO queryFreeResourceVO, QueryBalanceVO queryBalanceVO,
			QueryCdrVO queryCdrVO) {
		AllBalanceVO allBalanceVO = new AllBalanceVO();
		VoiceBalanceVO voiceBalance = new VoiceBalanceVO();
		allBalanceVO.setVoiceBalance(voiceBalance);
		DataBalanceVO dataBalance = new DataBalanceVO();
		allBalanceVO.setDataBalance(dataBalance);
		MoneyBalanceVO moneyBalance = new MoneyBalanceVO();
		allBalanceVO.setMoneyBalance(moneyBalance);
		List<MyBalanceDetailVO> mainBalanceDetails = new ArrayList<MyBalanceDetailVO>();
		moneyBalance.setMainBalanceDetails(mainBalanceDetails);

		List<BalanceVO> balanceList = queryBalanceVO.getBalanceList();
		if (balanceList != null) {
			BalanceVO balanceVO = balanceList.get(0);
			//long bonusAmount = 0, bonusRemaningAmount = 0, bonusUsageAmount = 0;
			moneyBalance.setMainRemaningAmount(BssServiceUtils.moneyFormat(balanceVO.getTotalAmount()));
			moneyBalance.setMainExpiryTime(balanceVO.getExpiryTime());
			if(queryCdrVO.getCdrSummaryList()!=null&&queryCdrVO.getCdrSummaryList().size()>0){
				moneyBalance.setMainUsageAmount(BssServiceUtils.moneyFormat(queryCdrVO.getCdrSummaryList().get(0).getTotalChargeAmt()));
			}else{
				moneyBalance.setMainUsageAmount(BssServiceUtils.moneyFormat("0"));
			}
			List<BalanceDetailVO> balanceDetailList = balanceVO.getBalanceDetailList();
			if (balanceDetailList != null && balanceDetailList.size() > 0) {
				for (int i = 0; i < balanceDetailList.size(); i++) {
					BalanceDetailVO balanceDetailVO = balanceDetailList.get(i);
					MyBalanceDetailVO myBalanceDetailVO = new MyBalanceDetailVO();
					mainBalanceDetails.add(myBalanceDetailVO);

					myBalanceDetailVO.setOfferingName(balanceVO.getBalanceType());
					myBalanceDetailVO.setEffectiveTime(BssServiceUtils.dateFormat(balanceDetailVO.getEffectiveTime()));
					myBalanceDetailVO.setExpiryTime(BssServiceUtils.dateFormat(moneyBalance.getMainExpiryTime()));
					myBalanceDetailVO.setOfferingName(balanceVO.getBalanceTypeDesc());
					myBalanceDetailVO.setRemaningAmount(BssServiceUtils.moneyFormat(balanceDetailVO.getAmount()));
					myBalanceDetailVO.setTotalAmount(BssServiceUtils.moneyFormat(balanceDetailVO.getInitialAmount()));
					String usageAmount = getUsageAmount(balanceDetailVO.getInitialAmount(),
							balanceDetailVO.getAmount());
					if (usageAmount != null) {
						myBalanceDetailVO.setUsageAmount(usageAmount);
					}
				}
			}

			if (queryFreeResourceVO != null) {
				List<FreeResourceVO> freeResourceList = queryFreeResourceVO.getFreeResourceList();
				if (freeResourceList != null && freeResourceList.size() > 0) {
					int voiceTotalAmount = 0, voiceRemaningAmount = 0, dataTotalAmount = 0, dataRemaningAmount = 0;
					List<MyBalanceDetailVO> voiceBalanceDetails = new ArrayList<MyBalanceDetailVO>();
					voiceBalance.setBalanceDetails(voiceBalanceDetails);
					List<MyBalanceDetailVO> dataBalanceDetails = new ArrayList<MyBalanceDetailVO>();
					dataBalance.setBalanceDetails(dataBalanceDetails);
					for (int i = 0; i < freeResourceList.size(); i++) {
						FreeResourceVO freeResourceVO = freeResourceList.get(i);
						if ("2".equals(freeResourceVO.getFreeResourceType())) {
							voiceTotalAmount = voiceTotalAmount
									+ Integer.parseInt(freeResourceVO.getTotalInitialAmount());
							voiceRemaningAmount = voiceRemaningAmount
									+ Integer.parseInt(freeResourceVO.getTotalUnusedAmount());
							List<FreeResourceDetailVO> freeResourceDetailList = freeResourceVO
									.getFreeResourceDetailList();
							if (freeResourceDetailList != null && freeResourceDetailList.size() > 0) {
								for (int j = 0; j < freeResourceDetailList.size(); j++) {
									FreeResourceDetailVO freeResourceDetailVO = freeResourceDetailList.get(j);
									MyBalanceDetailVO myBalanceDetailVO = new MyBalanceDetailVO();
									voiceBalanceDetails.add(myBalanceDetailVO);
									myBalanceDetailVO.setEffectiveTime(
											BssServiceUtils.dateFormat(freeResourceDetailVO.getEffectiveTime()));
									myBalanceDetailVO.setExpiryTime(
											BssServiceUtils.dateFormat(freeResourceDetailVO.getExpiryTime()));
									if (Long.parseLong(freeResourceDetailVO.getInitialAmount()) > 999990) {
										myBalanceDetailVO
												.setTotalAmount(UnieapConstants.getMessage("mcare.unit.nolimit"));
										myBalanceDetailVO
												.setRemaningAmount(UnieapConstants.getMessage("mcare.unit.nolimit"));
										myBalanceDetailVO
												.setUsageAmount(UnieapConstants.getMessage("mcare.unit.nolimit"));
									} else {
										myBalanceDetailVO.setTotalAmount(
												BssServiceUtils.voiceFormat(freeResourceDetailVO.getInitialAmount()));
										myBalanceDetailVO.setRemaningAmount(
												BssServiceUtils.voiceFormat(freeResourceDetailVO.getCurrentAmount()));
										int usageAmount = Integer.parseInt(freeResourceDetailVO.getInitialAmount())
												- Integer.parseInt(freeResourceDetailVO.getCurrentAmount());
										myBalanceDetailVO.setUsageAmount(
												BssServiceUtils.voiceFormat(Integer.toString(usageAmount)));
									}
								}
							}
						} else if ("3".equals(freeResourceVO.getFreeResourceType())) {
							dataTotalAmount = dataTotalAmount
									+ Integer.parseInt(freeResourceVO.getTotalInitialAmount());
							dataRemaningAmount = dataRemaningAmount
									+ Integer.parseInt(freeResourceVO.getTotalUnusedAmount());
							List<FreeResourceDetailVO> freeResourceDetailList = freeResourceVO
									.getFreeResourceDetailList();
							if (freeResourceDetailList != null && freeResourceDetailList.size() > 0) {
								for (int j = 0; j < freeResourceDetailList.size(); j++) {
									FreeResourceDetailVO freeResourceDetailVO = freeResourceDetailList.get(j);
									MyBalanceDetailVO myBalanceDetailVO = new MyBalanceDetailVO();
									dataBalanceDetails.add(myBalanceDetailVO);
									myBalanceDetailVO.setEffectiveTime(
											BssServiceUtils.dateFormat(freeResourceDetailVO.getEffectiveTime()));
									myBalanceDetailVO.setExpiryTime(
											BssServiceUtils.dateFormat(freeResourceDetailVO.getExpiryTime()));
									myBalanceDetailVO.setTotalAmount(freeResourceDetailVO.getInitialAmount());
									myBalanceDetailVO.setRemaningAmount(freeResourceDetailVO.getCurrentAmount());
									int usageAmount = Integer.parseInt(freeResourceDetailVO.getInitialAmount())
											- Integer.parseInt(freeResourceDetailVO.getCurrentAmount());
									myBalanceDetailVO.setUsageAmount(Integer.toString(usageAmount));
								}
							}
						}
					}
					if (voiceTotalAmount > 999990) {
						voiceBalance.setTotalAmount(UnieapConstants.getMessage("mcare.unit.nolimit"));
						voiceBalance.setRemaningAmount(UnieapConstants.getMessage("mcare.unit.nolimit"));
						voiceBalance.setDailyUsageAmount(UnieapConstants.getMessage("mcare.unit.nolimit"));

					} else {
						voiceBalance.setTotalAmount(BssServiceUtils.voiceFormat(Integer.toString(voiceTotalAmount)));
						voiceBalance
								.setRemaningAmount(BssServiceUtils.voiceFormat(Integer.toString(voiceRemaningAmount)));
						int voiceUsageAmount = voiceTotalAmount - voiceRemaningAmount;
						voiceBalance.setUsageAmount(BssServiceUtils.voiceFormat(Integer.toString(voiceUsageAmount)));
						int voiceDailyUsageAmount = voiceRemaningAmount
								/ Integer.parseInt(SYSConfig.getConfig().get("mcare.display.daily.voice"));
						voiceBalance.setDailyUsageAmount(
								BssServiceUtils.voiceFormat(Integer.toString(voiceDailyUsageAmount)));
					}

					dataBalance.setTotalAmount(Integer.toString(dataTotalAmount));
					dataBalance.setRemaningAmount(Integer.toString(dataRemaningAmount));
					int dataUsageAmount = dataTotalAmount - dataRemaningAmount;
					dataBalance.setUsageAmount(Integer.toString(dataUsageAmount));
				}
			}

		}
		return allBalanceVO;
	}
	public String getUsageAmount(String totalAmount, String remainingAmount) {
		if (!StringUtils.isEmpty(totalAmount) && !StringUtils.isEmpty(remainingAmount)) {
			if ("0".equals(totalAmount)) {
				return null;
			} else {
				long usageAmount = Long.parseLong(totalAmount) - Long.parseLong(remainingAmount);
				return Long.toString(usageAmount);
			}
		} else {
			return null;
		}
	}
}
