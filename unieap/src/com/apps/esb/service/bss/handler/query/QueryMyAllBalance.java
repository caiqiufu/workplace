package com.apps.esb.service.bss.handler.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.cbs.handler.QueryAccountBalance;
import com.apps.esb.service.bss.app.cbs.handler.QueryAccumulationUsage;
import com.apps.esb.service.bss.app.cbs.handler.QueryFreeUnits;
import com.apps.esb.service.bss.app.cbs.handler.QuerySubLifeCycle;
import com.apps.esb.service.bss.app.cbs.vo.queryaccountbalance.AccountCreditVO;
import com.apps.esb.service.bss.app.cbs.vo.queryaccountbalance.BalanceAccountVO;
import com.apps.esb.service.bss.app.cbs.vo.queryaccountbalance.BalanceDetailVO;
import com.apps.esb.service.bss.app.cbs.vo.queryaccountbalance.BalanceResultVO;
import com.apps.esb.service.bss.app.cbs.vo.queryaccountbalance.CreditAmountInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.queryaccountbalance.QueryAccountBalanceVO;
import com.apps.esb.service.bss.app.cbs.vo.queryaccumulateusage.AccmUsageVO;
import com.apps.esb.service.bss.app.cbs.vo.queryaccumulateusage.AccumulationUsageVO;
import com.apps.esb.service.bss.app.cbs.vo.queryfreeunit.FreeUnitItemDetailVO;
import com.apps.esb.service.bss.app.cbs.vo.queryfreeunit.FreeUnitItemVO;
import com.apps.esb.service.bss.app.cbs.vo.queryfreeunit.QueryFreeUnitVO;
import com.apps.esb.service.bss.app.cbs.vo.querysublifecycle.LifeCycleStatusVO;
import com.apps.esb.service.bss.app.cbs.vo.querysublifecycle.QuerySubLifeCycleVO;
import com.apps.esb.service.bss.app.vo.AllBalanceVO;
import com.apps.esb.service.bss.app.vo.DataBalanceVO;
import com.apps.esb.service.bss.app.vo.MoneyBalanceVO;
import com.apps.esb.service.bss.app.vo.MyBalanceDetailVO;
import com.apps.esb.service.bss.app.vo.VoiceBalanceVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;

@Service("queryMyAllBalance")
public class QueryMyAllBalance extends SoapMessageHandler implements BizHandler {

	public QueryMyAllBalance() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		String isdebug = SYSConfig.getConfig().get("mcare.app.extaction.debug");
		if (UnieapConstants.YES.equals(isdebug)) {
			requestInfo.getRequestBody().setServiceNumber("93268659");
		}
		QueryFreeUnits queryFreeUnits = (QueryFreeUnits) ServiceUtils.getBean("queryFreeUnits");
		ProcessResult processResultFreeUnits = queryFreeUnits.process(requestInfo, parameters, extParameters);
		if (!UnieapConstants.C0.equals(processResultFreeUnits.getResultCode())) {
			return processResultFreeUnits;
		}
		QueryFreeUnitVO queryFreeUnitVO = (QueryFreeUnitVO) processResultFreeUnits.getVo();
		QueryAccountBalance queryAccountBalance = (QueryAccountBalance) ServiceUtils.getBean("queryAccountBalance");
		ProcessResult processResultBalance = queryAccountBalance.process(requestInfo, parameters, extParameters);
		if (!UnieapConstants.C0.equals(processResultBalance.getResultCode())) {
			return processResultBalance;
		}
		QueryAccountBalanceVO queryAccountBalanceVO = (QueryAccountBalanceVO) processResultBalance.getVo();
		QueryAccumulationUsage queryAccumulationUsage = (QueryAccumulationUsage) ServiceUtils
				.getBean("queryAccumulationUsage");
		ProcessResult processResultAccumulationUsage = queryAccumulationUsage.process(requestInfo, parameters,
				extParameters);
		if (!UnieapConstants.C0.equals(processResultAccumulationUsage.getResultCode())) {
			return processResultAccumulationUsage;
		}
		AccumulationUsageVO accumulationUsageVO = (AccumulationUsageVO) processResultAccumulationUsage.getVo();
		QuerySubLifeCycle querySubLifeCycle = (QuerySubLifeCycle) ServiceUtils.getBean("querySubLifeCycle");
		ProcessResult processResultLifeCycle = querySubLifeCycle.process(requestInfo, parameters, extParameters);
		if (!UnieapConstants.C0.equals(processResultLifeCycle.getResultCode())) {
			return processResultLifeCycle;
		}
		QuerySubLifeCycleVO querySubLifeCycleVO = (QuerySubLifeCycleVO) processResultLifeCycle.getVo();
		AllBalanceVO allBalanceVO = getAllBalanceVO(queryFreeUnitVO, queryAccountBalanceVO, accumulationUsageVO,
				querySubLifeCycleVO);
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

	public AllBalanceVO getAllBalanceVO(QueryFreeUnitVO queryFreeUnitVO, QueryAccountBalanceVO queryAccountBalanceVO,
			AccumulationUsageVO accumulationUsageVO, QuerySubLifeCycleVO querySubLifeCycleVO) {
		List<LifeCycleStatusVO> lifeCycleStatusList = querySubLifeCycleVO.getLifeCycleStatusList();
		AllBalanceVO allBalanceVO = new AllBalanceVO();
		VoiceBalanceVO voiceBalance = new VoiceBalanceVO();
		allBalanceVO.setVoiceBalance(voiceBalance);
		DataBalanceVO dataBalance = new DataBalanceVO();
		allBalanceVO.setDataBalance(dataBalance);
		MoneyBalanceVO moneyBalance = new MoneyBalanceVO();
		allBalanceVO.setMoneyBalance(moneyBalance);
		List<BalanceAccountVO> balanceAccountList = queryAccountBalanceVO.getBalanceAccountList();
		if (balanceAccountList != null && balanceAccountList.size() > 0) {
			BalanceAccountVO balanceAccountVO = balanceAccountList.get(0);
			long bonusAmount = 0, bonusRemaningAmount = 0, bonusUsageAmount = 0;
			List<BalanceResultVO> balanceResultList = balanceAccountVO.getBalanceResultList();
			List<MyBalanceDetailVO> mainBalanceDetails = new ArrayList<MyBalanceDetailVO>();
			moneyBalance.setMainBalanceDetails(mainBalanceDetails);
			if (balanceResultList != null && balanceResultList.size() > 0) {
				List<MyBalanceDetailVO> bonusBalanceDetails = new ArrayList<MyBalanceDetailVO>();
				for (int j = 0; j < balanceResultList.size(); j++) {
					BalanceResultVO balanceResultVO = balanceResultList.get(j);
					/**
					 * prepaid main account
					 */
					if ("C_MAIN_ACCOUNT".equals(balanceResultVO.getBalanceType())) {
						moneyBalance
								.setMainRemaningAmount(BssServiceUtils.moneyFormat(balanceResultVO.getTotalAmount()));
						String mainBalanceAccumulation = getMainBalaneAccumulation(accumulationUsageVO);
						if (mainBalanceAccumulation != null) {
							moneyBalance.setMainUsageAmount(BssServiceUtils.moneyFormat(mainBalanceAccumulation));
						}
						if (lifeCycleStatusList != null && lifeCycleStatusList.size() > 0) {
							for (int m = 0; m < lifeCycleStatusList.size(); m++) {
								LifeCycleStatusVO lifeCycleStatusVO = lifeCycleStatusList.get(m);
								if (lifeCycleStatusVO.getStatusIndex()
										.equals(querySubLifeCycleVO.getCurrentStatusIndex())) {
									moneyBalance.setMainExpiryTime(
											BssServiceUtils.dateFormat(lifeCycleStatusVO.getStatusExpireTime()));
								}
							}
						}
						List<BalanceDetailVO> balanceDetailList = balanceResultVO.getBalanceDetailList();
						if (balanceDetailList != null && balanceDetailList.size() > 0) {
							MyBalanceDetailVO myBalanceDetailVO = new MyBalanceDetailVO();
							mainBalanceDetails.add(myBalanceDetailVO);
							BalanceDetailVO balanceDetailVO = balanceDetailList.get(0);
							myBalanceDetailVO.setOfferingName(balanceResultVO.getBalanceTypeName());
							myBalanceDetailVO
									.setEffectiveTime(BssServiceUtils.dateFormat(balanceDetailVO.getEffectiveTime()));

							myBalanceDetailVO
									.setExpiryTime(BssServiceUtils.dateFormat(moneyBalance.getMainExpiryTime()));
							myBalanceDetailVO.setOfferingName(balanceResultVO.getBalanceTypeName());
							myBalanceDetailVO
									.setRemaningAmount(BssServiceUtils.moneyFormat(balanceDetailVO.getAmount()));
							myBalanceDetailVO
									.setTotalAmount(BssServiceUtils.moneyFormat(balanceDetailVO.getInitialAmount()));
							String usageAmount = getUsageAmount(balanceDetailVO.getInitialAmount(),
									balanceDetailVO.getAmount());
							if (usageAmount != null) {
								myBalanceDetailVO.setUsageAmount(BssServiceUtils.moneyFormat(usageAmount));
							}

						}

					} else if ("C_MAIN_BILLING_ACCOUNT".equals(balanceResultVO.getBalanceType())) {
						MyBalanceDetailVO myBalanceDetailVO = new MyBalanceDetailVO();
						mainBalanceDetails.add(myBalanceDetailVO);
						List<BalanceDetailVO> balanceDetailList = balanceResultVO.getBalanceDetailList();
						if (balanceDetailList != null && balanceDetailList.size() > 0) {
							BalanceDetailVO balanceDetailVO = balanceDetailList.get(0);
							myBalanceDetailVO.setOfferingName(balanceResultVO.getBalanceTypeName());
							myBalanceDetailVO
									.setEffectiveTime(BssServiceUtils.dateFormat(balanceDetailVO.getEffectiveTime()));
							myBalanceDetailVO.setOfferingName(balanceResultVO.getBalanceTypeName());
							myBalanceDetailVO
									.setRemaningAmount(BssServiceUtils.moneyFormat(balanceDetailVO.getAmount()));
							myBalanceDetailVO
									.setTotalAmount(BssServiceUtils.moneyFormat(balanceDetailVO.getInitialAmount()));
						}
					} else {
						/**
						 * bonus account
						 */
						bonusAmount = bonusAmount + Long.parseLong(balanceResultVO.getTotalAmount());
						List<BalanceDetailVO> balanceDetailList = balanceResultVO.getBalanceDetailList();
						if (balanceDetailList != null && balanceDetailList.size() > 0) {
							moneyBalance.setBonusBalanceDetails(bonusBalanceDetails);
							for (int k = 0; k < balanceDetailList.size(); k++) {
								MyBalanceDetailVO myBalanceDetailVO = new MyBalanceDetailVO();
								bonusBalanceDetails.add(myBalanceDetailVO);
								BalanceDetailVO balanceDetailVO = balanceDetailList.get(k);
								myBalanceDetailVO.setEffectiveTime(
										BssServiceUtils.dateFormat(balanceDetailVO.getEffectiveTime()));
								myBalanceDetailVO
										.setExpiryTime(BssServiceUtils.dateFormat(balanceDetailVO.getExpiryTime()));
								myBalanceDetailVO.setOfferingName(balanceResultVO.getBalanceTypeName());
								myBalanceDetailVO
										.setRemaningAmount(BssServiceUtils.moneyFormat(balanceDetailVO.getAmount()));
								myBalanceDetailVO.setTotalAmount(
										BssServiceUtils.moneyFormat(balanceDetailVO.getInitialAmount()));
								String usageAmount = getUsageAmount(balanceDetailVO.getInitialAmount(),
										balanceDetailVO.getAmount());
								if (usageAmount != null) {
									bonusUsageAmount = bonusUsageAmount + Long.parseLong(usageAmount);
									myBalanceDetailVO.setUsageAmount(BssServiceUtils.moneyFormat(usageAmount));
								}
							}
						}

					}
				}
				if (bonusAmount > 0) {
					moneyBalance.setBonusAmount(BssServiceUtils.moneyFormat(Long.toString(bonusAmount)));
					moneyBalance
							.setBonusRemaningAmount(BssServiceUtils.moneyFormat(Long.toString(bonusRemaningAmount)));
					bonusRemaningAmount = bonusAmount - bonusUsageAmount;
					moneyBalance.setBonusUsageAmount(BssServiceUtils.moneyFormat(Long.toString(bonusRemaningAmount)));
				}

			}
			List<AccountCreditVO> accountCreditList = balanceAccountVO.getAccountCreditList();
			if (accountCreditList != null && accountCreditList.size() > 0) {
				AccountCreditVO accountCreditVO = accountCreditList.get(0);
				if (accountCreditVO.getCreditAmountInfoList() != null
						&& accountCreditVO.getCreditAmountInfoList().size() > 0) {

					moneyBalance
							.setCreditLimitAmount(BssServiceUtils.moneyFormat(accountCreditVO.getTotalCreditAmount()));
					moneyBalance
							.setMainRemaningAmount(BssServiceUtils.moneyFormat(accountCreditVO.getTotalRemainAmount()));
					moneyBalance.setMainUsageAmount(BssServiceUtils.moneyFormat(accountCreditVO.getTotalUsageAmount()));
					List<CreditAmountInfoVO> creditAmountInfoList = accountCreditVO.getCreditAmountInfoList();
					if (creditAmountInfoList != null && creditAmountInfoList.size() > 0) {
						for (int j = 0; j < creditAmountInfoList.size(); j++) {
							MyBalanceDetailVO myBalanceDetailVO = new MyBalanceDetailVO();
							mainBalanceDetails.add(myBalanceDetailVO);
							CreditAmountInfoVO creditAmountInfoVO = creditAmountInfoList.get(j);
							myBalanceDetailVO.setEffectiveTime(
									BssServiceUtils.dateFormat(creditAmountInfoVO.getEffectiveTime()));
							myBalanceDetailVO
									.setExpiryTime(BssServiceUtils.dateFormat(creditAmountInfoVO.getExpiryTime()));
							myBalanceDetailVO.setIndex(creditAmountInfoVO.getCreditInstID());
							myBalanceDetailVO.setOfferingName(accountCreditVO.getCreditLimitTypeName());
							myBalanceDetailVO
									.setRemaningAmount(BssServiceUtils.moneyFormat(creditAmountInfoVO.getAmount()));
						}
					}
				}

			}

		}

		List<FreeUnitItemVO> freeUnitItemVOList = queryFreeUnitVO.getFreeUnitItemVOList();
		if (freeUnitItemVOList != null && freeUnitItemVOList.size() > 0) {
			int voiceTotalAmount = 0, voiceRemaningAmount = 0, dataTotalAmount = 0, dataRemaningAmount = 0;
			List<MyBalanceDetailVO> voiceBalanceDetails = new ArrayList<MyBalanceDetailVO>();
			voiceBalance.setBalanceDetails(voiceBalanceDetails);
			List<MyBalanceDetailVO> dataBalanceDetails = new ArrayList<MyBalanceDetailVO>();
			dataBalance.setBalanceDetails(dataBalanceDetails);
			for (int i = 0; i < freeUnitItemVOList.size(); i++) {
				FreeUnitItemVO freeUnitItemVO = freeUnitItemVOList.get(i);
				if ("1003".equals(freeUnitItemVO.getMeasureUnit())) {
					voiceTotalAmount = voiceTotalAmount + Integer.parseInt(freeUnitItemVO.getTotalInitialAmount());
					voiceRemaningAmount = voiceRemaningAmount + Integer.parseInt(freeUnitItemVO.getTotalUnusedAmount());
					List<FreeUnitItemDetailVO> freeUnitItemDetailList = freeUnitItemVO.getFreeUnitItemDetailList();
					if (freeUnitItemDetailList != null && freeUnitItemDetailList.size() > 0) {
						for (int j = 0; j < freeUnitItemDetailList.size(); j++) {
							FreeUnitItemDetailVO freeUnitItemDetailVO = freeUnitItemDetailList.get(j);
							MyBalanceDetailVO myBalanceDetailVO = new MyBalanceDetailVO();
							voiceBalanceDetails.add(myBalanceDetailVO);
							myBalanceDetailVO.setEffectiveTime(
									BssServiceUtils.dateFormat(freeUnitItemDetailVO.getEffectiveTime()));
							myBalanceDetailVO
									.setExpiryTime(BssServiceUtils.dateFormat(freeUnitItemDetailVO.getExpireTime()));
							if (Long.parseLong(freeUnitItemDetailVO.getInitialAmount()) > 999990) {
								myBalanceDetailVO.setTotalAmount(UnieapConstants.getMessage("mcare.unit.nolimit"));
								myBalanceDetailVO.setRemaningAmount(UnieapConstants.getMessage("mcare.unit.nolimit"));
								myBalanceDetailVO.setUsageAmount(UnieapConstants.getMessage("mcare.unit.nolimit"));
							} else {
								myBalanceDetailVO.setTotalAmount(
										BssServiceUtils.voiceFormat(freeUnitItemDetailVO.getInitialAmount()));
								myBalanceDetailVO.setRemaningAmount(
										BssServiceUtils.voiceFormat(freeUnitItemDetailVO.getCurrentAmount()));
								int usageAmount = Integer.parseInt(freeUnitItemDetailVO.getInitialAmount())
										- Integer.parseInt(freeUnitItemDetailVO.getCurrentAmount());
								myBalanceDetailVO
										.setUsageAmount(BssServiceUtils.voiceFormat(Integer.toString(usageAmount)));
							}
						}
					}
				} else if ("1108".equals(freeUnitItemVO.getMeasureUnit())) {
					dataTotalAmount = dataTotalAmount + Integer.parseInt(freeUnitItemVO.getTotalInitialAmount());
					dataRemaningAmount = dataRemaningAmount + Integer.parseInt(freeUnitItemVO.getTotalUnusedAmount());
					List<FreeUnitItemDetailVO> freeUnitItemDetailList = freeUnitItemVO.getFreeUnitItemDetailList();
					if (freeUnitItemDetailList != null && freeUnitItemDetailList.size() > 0) {
						for (int j = 0; j < freeUnitItemDetailList.size(); j++) {
							FreeUnitItemDetailVO freeUnitItemDetailVO = freeUnitItemDetailList.get(j);
							MyBalanceDetailVO myBalanceDetailVO = new MyBalanceDetailVO();
							dataBalanceDetails.add(myBalanceDetailVO);
							myBalanceDetailVO.setEffectiveTime(
									BssServiceUtils.dateFormat(freeUnitItemDetailVO.getEffectiveTime()));
							myBalanceDetailVO
									.setExpiryTime(BssServiceUtils.dateFormat(freeUnitItemDetailVO.getExpireTime()));
							myBalanceDetailVO.setTotalAmount(freeUnitItemDetailVO.getInitialAmount());
							myBalanceDetailVO.setRemaningAmount(freeUnitItemDetailVO.getCurrentAmount());
							int usageAmount = Integer.parseInt(freeUnitItemDetailVO.getInitialAmount())
									- Integer.parseInt(freeUnitItemDetailVO.getCurrentAmount());
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
				voiceBalance.setRemaningAmount(BssServiceUtils.voiceFormat(Integer.toString(voiceRemaningAmount)));
				int voiceUsageAmount = voiceTotalAmount - voiceRemaningAmount;
				voiceBalance.setUsageAmount(BssServiceUtils.voiceFormat(Integer.toString(voiceUsageAmount)));
				int voiceDailyUsageAmount = voiceRemaningAmount
						/ Integer.parseInt(SYSConfig.getConfig().get("mcare.display.voice"));
				voiceBalance.setDailyUsageAmount(BssServiceUtils.voiceFormat(Integer.toString(voiceDailyUsageAmount)));
			}

			dataBalance.setTotalAmount(Integer.toString(dataTotalAmount));
			dataBalance.setRemaningAmount(Integer.toString(dataRemaningAmount));
			int dataUsageAmount = dataTotalAmount - dataRemaningAmount;
			dataBalance.setUsageAmount(Integer.toString(dataUsageAmount));

		}
		return allBalanceVO;
	}

	public String getMainBalaneAccumulation(AccumulationUsageVO accumulationUsageVO) {
		List<AccmUsageVO> accmUsageList = accumulationUsageVO.getAccmUsageList();
		if (accmUsageList != null && accmUsageList.size() > 0) {
			for (int i = 0; i < accmUsageList.size(); i++) {
				AccmUsageVO accmUsageVO = accmUsageList.get(i);
				if (SYSConfig.getConfig().get("cbs.accumulation.mainbalance").equals(accmUsageVO.getAccmType())) {
					return accmUsageVO.getAmount();
				}
			}
		}
		return null;
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
