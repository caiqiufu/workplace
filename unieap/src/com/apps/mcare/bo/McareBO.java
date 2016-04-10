package com.apps.mcare.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.apps.esb.service.bss.BssServiceBO;
import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.vo.account.balance.QueryBalanceVO;
import com.apps.esb.service.bss.app.vo.common.address.AddressVO;
import com.apps.esb.service.bss.app.vo.customer.basicinfo.CustomerVO;
import com.apps.esb.service.bss.app.vo.subscriber.basicinfo.SubscriberVO;
import com.apps.esb.service.bss.app.vo.subscriber.cdr.CdrInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.cdr.QueryCdrVO;
import com.apps.esb.service.bss.app.vo.subscriber.freeresource.FreeResourceVO;
import com.apps.esb.service.bss.app.vo.subscriber.freeresource.QueryFreeResourceVO;
import com.apps.esb.service.bss.app.vo.subscriber.lifecycle.LifeCycleStatusVO;
import com.apps.esb.service.bss.app.vo.subscriber.lifecycle.StatusVO;
import com.apps.esb.service.bss.app.vo.subscriber.sim.SimCardVO;
import com.apps.esb.service.bss.customize.smart.app.cbs.vo.queryofferingrentcycle.QueryOfferingRentCycleVO;
import com.apps.esb.service.bss.element.RequestBody;
import com.apps.esb.service.bss.element.RequestHeader;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.vo.macre.cdr.CdrDetailVO;
import com.apps.esb.service.bss.vo.macre.customerprofile.CustomerProfileVO;
import com.unieap.BaseBO;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.encrypt.EncryptionUtils;
import com.unieap.tools.JSONUtils;

@Service("mcareBO")
public class McareBO extends BaseBO {

	private RequestInfo getRequestInfo(String bizCode, String serviceNumber) throws Exception {
		RequestInfo requestInfo = new RequestInfo();
		RequestHeader requestHeader = new RequestHeader();
		RequestBody requestBody = new RequestBody();
		requestInfo.setRequestHeader(requestHeader);
		requestInfo.setRequestBody(requestBody);
		requestHeader.setAccessPwd("356a192b7913b04c54574d18c28d46e6395428ab");
		requestHeader.setAccessUser("unieap");
		requestHeader.setBizCode(bizCode);
		requestHeader.setChannelCode("mcarePortal");
		requestHeader.setRequestTime(UnieapConstants.getCurrentTime(null, null));
		requestHeader.setSystemCode("mcarePortal");
		requestHeader.setTransactionId(requestHeader.getRequestTime());
		requestBody.setServiceNumber(serviceNumber);
		return requestInfo;
	}

	private String queryInfo(RequestInfo requestInfo) throws Exception {
		String requestInfoString = JSONUtils.convertBean2JSON(requestInfo).toString();
		BssServiceBO bssServiceBO = (BssServiceBO) ServiceUtils.getBean("bssServiceBO");
		String responsetInfoString = bssServiceBO.queryInfo(requestInfoString, null);
		return responsetInfoString;
	}

	private String bizHandle(RequestInfo requestInfo) throws Exception {
		String requestInfoString = JSONUtils.convertBean2JSON(requestInfo).toString();
		BssServiceBO bssServiceBO = (BssServiceBO) ServiceUtils.getBean("bssServiceBO");
		String responsetInfoString = bssServiceBO.bizHandle(requestInfoString, null);
		return responsetInfoString;
	}

	private CustomerProfileVO getCustomerProfile(String responsetInfoString) throws Exception {
		JSONObject json = new JSONObject(responsetInfoString);
		if (json.has("responseBody")) {
			JSONObject responseBody = json.getJSONObject("responseBody");
			CustomerProfileVO vo = new CustomerProfileVO();
			if (responseBody.has("serviceNumber")) {
				vo.setServiceNumber(responseBody.getString("serviceNumber"));
			}
			if (responseBody.has("extParameters")) {
				JSONObject extParameters = responseBody.getJSONObject("extParameters");
				if (extParameters.has("subscriberInfo")) {
					JSONObject subscriberInfo = extParameters.getJSONObject("subscriberInfo");
					if (subscriberInfo.has("subscriberType")) {
						vo.setSubscriberType(subscriberInfo.getString("subscriberType"));
					}
					if (subscriberInfo.has("paymentFlag")) {
						vo.setPaymentFlag(subscriberInfo.getString("paymentFlag"));
						vo.setPaymentFlagDesc(subscriberInfo.getString("paymentFlagDesc"));
					}
					if (subscriberInfo.has("paymentFlagDesc")) {
						vo.setPaymentFlagDesc(subscriberInfo.getString("paymentFlagDesc"));
					}
					if (subscriberInfo.has("customerName")) {
						vo.setCustomerName(subscriberInfo.getString("customerName"));
					}
					if (subscriberInfo.has("dateOfBirth")) {
						vo.setDateOfBirth(subscriberInfo.getString("dateOfBirth"));
					}
					if (subscriberInfo.has("status")) {
						vo.setStatus(subscriberInfo.getString("status"));
					}
					if (subscriberInfo.has("activeDate")) {
						vo.setActiveDate(subscriberInfo.getString("activeDate"));
					}
					if (subscriberInfo.has("customerLevel")) {
						vo.setCustomerLevel(subscriberInfo.getString("customerLevel"));
					}
					if (subscriberInfo.has("subscriberTypeDesc")) {
						vo.setSubscriberTypeDesc(subscriberInfo.getString("subscriberTypeDesc"));
					}
					if (subscriberInfo.has("certificateType")) {
						vo.setCertificateType(subscriberInfo.getString("certificateType"));
					}
					if (subscriberInfo.has("certificateNumber")) {
						vo.setCertificateNumber(subscriberInfo.getString("certificateNumber"));
					}
					if (subscriberInfo.has("customerId")) {
						vo.setCustomerId(subscriberInfo.getString("customerId"));
					}
					if (subscriberInfo.has("email")) {
						vo.setEmail(subscriberInfo.getString("email"));
					}
					if (subscriberInfo.has("statusReason")) {
						vo.setStatusReason(subscriberInfo.getString("statusReason"));
					}
					if (subscriberInfo.has("supplement")) {
						vo.setSupplement(subscriberInfo.getString("supplement"));
					}
					if (subscriberInfo.has("contactNo")) {
						vo.setContactNo(subscriberInfo.getString("contactNo"));
					}
					if (subscriberInfo.has("primaryOfferingName")) {
						vo.setPrimaryOfferingName(subscriberInfo.getString("primaryOfferingName"));
					}
					return vo;
				}
			}
		}
		return null;
	}

	public CustomerProfileVO login(String serviceNumber, String password) throws Exception {

		RequestInfo requestInfo = getRequestInfo("C002", serviceNumber);
		if (!StringUtils.isEmpty(password)) {
			JSONObject extParametersJson = new JSONObject();
			extParametersJson.put("password", password);
			requestInfo.getRequestBody().setExtParameters(extParametersJson.toString());
		} else {
			throw new Exception("password is null");
		}
		return getCustomerProfile(queryInfo(requestInfo));
	}
    public void setCommonInfo(CustomerProfileVO vo, ModelAndView ma){
    	ma.addObject("customerName", vo.getCustomerName());
		ma.addObject("serviceNumber", vo.getServiceNumber());
    }
	public ModelAndView getMyBalance(CustomerProfileVO vo, ModelAndView ma) throws Exception {
		
		RequestInfo requestInfo1 = getRequestInfo("querySubscriber", vo.getServiceNumber());
		BizHandler querySubscriber = (BizHandler) ServiceUtils.getBeanByTenantId("querySubscriber");
		ProcessResult resultQuerySubscriber = querySubscriber.process(requestInfo1, null, null);
		SubscriberVO subscriberVO = (SubscriberVO) resultQuerySubscriber.getVo();
		
		RequestInfo requestInfo2 = getRequestInfo("queryBalance", vo.getServiceNumber());
		BizHandler queryAccountBalance = (BizHandler) ServiceUtils.getBeanByTenantId("queryBalance");
		ProcessResult processAccountBalance = queryAccountBalance.process(requestInfo2, null, null);
		QueryBalanceVO queryBalanceVO = (QueryBalanceVO) processAccountBalance.getVo();
		
		RequestInfo requestInfo3 = getRequestInfo("queryFreeReource", vo.getServiceNumber());
		BizHandler queryFreeReource = (BizHandler) ServiceUtils.getBeanByTenantId("queryFreeReource");
		ProcessResult processFreeReource = queryFreeReource.process(requestInfo3, null, null);
		QueryFreeResourceVO queryFreeResourceVO = (QueryFreeResourceVO) processFreeReource.getVo();
		
		RequestInfo requestInfo4 = getRequestInfo("queryOfferingRentCycle", vo.getServiceNumber());
		JSONObject extParametersJson = new JSONObject();
		extParametersJson.put("primaryOfferingId", subscriberVO.getPrimaryOfferingVO().getOfferingId());
		requestInfo4.getRequestBody().setExtParameters(extParametersJson.toString());
		BizHandler queryOfferingRentCycle = (BizHandler) ServiceUtils.getBeanByTenantId("queryOfferingRentCycle");
		ProcessResult processOfferingRentCycle = queryOfferingRentCycle.process(requestInfo4, null, null);
		QueryOfferingRentCycleVO queryOfferingRentCycleVO = (QueryOfferingRentCycleVO) processOfferingRentCycle.getVo();
		
		double prepaidTotalBlanceAmount = 0;
		double PostPaidTotalAvailableAmount = 0;
		
		if (!StringUtils.isEmpty( queryBalanceVO.getTotalBalanceAmount())) {
			prepaidTotalBlanceAmount = prepaidTotalBlanceAmount + Double.parseDouble(queryBalanceVO.getTotalBalanceAmount());
		}
		
		if (!StringUtils.isEmpty(queryBalanceVO.getTotalAvailableAmount())) {
			PostPaidTotalAvailableAmount = PostPaidTotalAvailableAmount + Double.parseDouble(queryBalanceVO.getTotalAvailableAmount());
		}
		if (!StringUtils.isEmpty(queryBalanceVO.getTotalCreditLimitAmount())) {
			PostPaidTotalAvailableAmount = Double.parseDouble(queryBalanceVO.getTotalCreditLimitAmount());
		}
		double bonusAccountBalance = 0;
		if(queryFreeResourceVO.getFreeResourceList()!=null&&queryFreeResourceVO.getFreeResourceList().size()>0){
			List<FreeResourceVO> freeResourceList = queryFreeResourceVO.getFreeResourceList();
			for(int i = 0 ; i< freeResourceList.size(); i++){
				FreeResourceVO freeResourceVO = freeResourceList.get(i);
				if("1".equals(freeResourceVO.getFreeResourceType())&&!StringUtils.isEmpty(freeResourceVO.getTotalUnusedAmount())){
					bonusAccountBalance = bonusAccountBalance+ Double.parseDouble(freeResourceVO.getTotalUnusedAmount());
				}
			}
		}
		
		ma.addObject("totalAvailableAmount",BssServiceUtils.moneyFormat(Double.toString(prepaidTotalBlanceAmount+PostPaidTotalAvailableAmount)));
		ma.addObject("mainAccountBalance",BssServiceUtils.moneyFormat(queryBalanceVO.getTotalBalanceAmount()));
		ma.addObject("bonusAccountBalance", BssServiceUtils.moneyFormat(Double.toString(bonusAccountBalance)));
		if(queryOfferingRentCycleVO.getOfferingRentCycleList()!=null&&queryOfferingRentCycleVO.getOfferingRentCycleList().size()>0){
			ma.addObject("nextCycle",BssServiceUtils.dateFormat(queryOfferingRentCycleVO.getOfferingRentCycleList().get(0).getEndDay()));
		}else{
			ma.addObject("nextCycle",BssServiceUtils.dateFormat(subscriberVO.getPrimaryOfferingVO().getExpiryTime()));
		}
		ma.addObject("pimaryOfferingName", vo.getPrimaryOfferingName());
		String time = UnieapConstants.getCurrentTime(null, null);
		ma.addObject("year", time.substring(0, 4));
		ma.addObject("month", time.substring(4, 6));
		return ma;
	}

	public ModelAndView getMyProfile(CustomerProfileVO vo, ModelAndView ma) throws Exception {

		RequestInfo requestInfo = getRequestInfo("querySubLifeCycle", vo.getServiceNumber());

		BizHandler querySubLifeCycle = (BizHandler) ServiceUtils.getBeanByTenantId("querySubLifeCycle");
		ProcessResult resultQuerySubLifeCycle = querySubLifeCycle.process(requestInfo, null, null);
		LifeCycleStatusVO lifeCycleStatusVO = (LifeCycleStatusVO) resultQuerySubLifeCycle.getVo();

		RequestInfo requestInfo1 = getRequestInfo("querySubscriber", vo.getServiceNumber());
		BizHandler querySubscriber = (BizHandler) ServiceUtils.getBeanByTenantId("querySubscriber");
		ProcessResult resultQuerySubscriber = querySubscriber.process(requestInfo1, null, null);
		SubscriberVO subscriberVO = (SubscriberVO) resultQuerySubscriber.getVo();
		ma.addObject("customerName", vo.getCustomerName());
		ma.addObject("serviceNumber", vo.getServiceNumber());
		ma.addObject("statusDesc", subscriberVO.getStatusDesc());
		ma.addObject("statusReasonDesc", subscriberVO.getStatusReasonDesc());
		ma.addObject("paymentFlagDesc", subscriberVO.getPaymentFlagDesc());
		List<StatusVO> statusList = lifeCycleStatusVO.getStatusList();
		if (statusList != null && statusList.size() > 2) {
			StatusVO statusVO0 = statusList.get(0);
			ma.addObject("statusIndex0", statusVO0.getStatusIndex());
			ma.addObject("statusDesc0", statusVO0.getStatusDesc());
			ma.addObject("activateTime", BssServiceUtils.dateFormat(statusVO0.getExpiryTime()));
			StatusVO statusVO1 = statusList.get(1);
			ma.addObject("statusIndex1", statusVO1.getStatusIndex());
			ma.addObject("statusDesc1", statusVO1.getStatusDesc());
			ma.addObject("suspesionTime", BssServiceUtils.dateFormat(statusVO1.getExpiryTime()));
			StatusVO statusVO2 = statusList.get(2);
			ma.addObject("statusIndex2", statusVO2.getStatusIndex());
			ma.addObject("statusDesc2", statusVO2.getStatusDesc());
			ma.addObject("deactivationTime", BssServiceUtils.dateFormat(statusVO2.getExpiryTime()));
			StatusVO statusVO3 = statusList.get(2);
			ma.addObject("statusIndex3", statusVO3.getStatusIndex());
			ma.addObject("statusDesc3", statusVO3.getStatusDesc());
			ma.addObject("expiryTime", BssServiceUtils.dateFormat(statusVO3.getExpiryTime()));
		}
		ma.addObject("language", "English(U.S)");
		CustomerVO customerVO = subscriberVO.getCustomerVO();
		List<AddressVO> addressList = customerVO.getAddressList();
		if (addressList != null && addressList.size() > 0) {
			AddressVO addressVO = addressList.get(0);
			ma.addObject("addressNationality", addressVO.getAddress1());
			ma.addObject("addressProvince", addressVO.getAddress2());
			StringBuffer address = new StringBuffer();
			if (!StringUtils.isEmpty(addressVO.getAddress3())) {
				address.append(addressVO.getAddress3()).append(" ");
			}
			if (!StringUtils.isEmpty(addressVO.getAddress4())) {
				address.append(addressVO.getAddress4()).append(" ");
			}
			if (!StringUtils.isEmpty(addressVO.getAddress5())) {
				address.append(addressVO.getAddress5()).append(" ");
			}
			if (!StringUtils.isEmpty(addressVO.getAddress6())) {
				address.append(addressVO.getAddress6()).append(" ");
			}
			if (!StringUtils.isEmpty(addressVO.getAddress7())) {
				address.append(addressVO.getAddress7()).append(" ");
			}
			if (!StringUtils.isEmpty(addressVO.getAddress8())) {
				address.append(addressVO.getAddress8()).append(" ");
			}
			if (!StringUtils.isEmpty(addressVO.getAddress9())) {
				address.append(addressVO.getAddress9()).append(" ");
			}
			if (!StringUtils.isEmpty(addressVO.getAddress10())) {
				address.append(addressVO.getAddress10()).append(" ");
			}
			ma.addObject("addressDetails", address.toString());
		}
		return ma;
	}
	
	public Map<String, String> saveChangepassword(CustomerProfileVO vo,String serviceNumber, String oldPassword,String newPassword) throws Exception {
		Map<String, String> model = new HashMap<String, String>();
		RequestInfo requestInfo = getRequestInfo("changePassword", serviceNumber);
		JSONObject extParametersJson = new JSONObject();
		extParametersJson.put("oldPassword", oldPassword);
		extParametersJson.put("newPassword", newPassword);
		requestInfo.getRequestBody().setExtParameters(extParametersJson.toString());
		BizHandler changePassword = (BizHandler) ServiceUtils.getBeanByTenantId("changePassword");
		ProcessResult processResultChangePassword = changePassword.process(requestInfo,null,null);
		if(UnieapConstants.C0.equals(processResultChangePassword.getResultCode())){
			model.put(UnieapConstants.SUCCESS, UnieapConstants.SUCCESS);
			model.put(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
			model.put(UnieapConstants.ERRORCODE, UnieapConstants.C0);
			model.put(UnieapConstants.ERRORDESC, UnieapConstants.getMessage(UnieapConstants.C0));
			return model;
		}else{
			model.put(UnieapConstants.SUCCESS, UnieapConstants.SUCCESS);
			model.put(UnieapConstants.ISSUCCESS, UnieapConstants.FAILED);
			model.put(UnieapConstants.ERRORCODE, UnieapConstants.C99999);
			model.put(UnieapConstants.ERRORDESC, processResultChangePassword.getResultDesc());
			return model;
		}
	}
	public ModelAndView getMySimCard(CustomerProfileVO vo, ModelAndView ma) throws Exception {
		RequestInfo requestInfo = getRequestInfo("querySubscriber", vo.getServiceNumber());
		BizHandler querySubscriber = (BizHandler) ServiceUtils.getBeanByTenantId("querySubscriber");
		ProcessResult resultQuerySubscriber = querySubscriber.process(requestInfo, null, null);
		SubscriberVO subscriberVO = (SubscriberVO) resultQuerySubscriber.getVo();
		SimCardVO simCardVO = subscriberVO.getSimCardVO();
		if(simCardVO!=null){
			ma.addObject("iccid", simCardVO.getIccid());
			ma.addObject("simCardTypeDesc","4G SIM Card");
			String sKey = SYSConfig.getConfig().get("bss.encryption.key");
			ma.addObject("pin1",EncryptionUtils.decryptSun(simCardVO.getPin1(), sKey) );
			ma.addObject("pin2", EncryptionUtils.decryptSun(simCardVO.getPin2(), sKey));
			ma.addObject("puk1", EncryptionUtils.decryptSun(simCardVO.getPuk1(), sKey));
			ma.addObject("puk2", EncryptionUtils.decryptSun(simCardVO.getPuk2(), sKey));
		}
		return ma;
	}
	public Map<String, String> getUsageHistoryList(CustomerProfileVO vo,String serviceNumber,String page,String rowNum,String totalNum) throws Exception {
		Map<String, String> model = new HashMap<String, String>();
		RequestInfo requestInfo = getRequestInfo("queryCDR", serviceNumber);
		JSONObject extParametersJson = new JSONObject();
		extParametersJson.put("queryType", "D");
		extParametersJson.put("page", page);
		extParametersJson.put("rowNum", rowNum);
		if(StringUtils.isEmpty(totalNum)){
			totalNum = "0";
		}
		extParametersJson.put("totalNum", totalNum);
		requestInfo.getRequestBody().setExtParameters(extParametersJson.toString());
		BizHandler queryCDR = (BizHandler) ServiceUtils.getBeanByTenantId("queryCDR");
		ProcessResult processResultQueryCDR = queryCDR.process(requestInfo,null,null);
		
		if(UnieapConstants.C0.equals(processResultQueryCDR.getResultCode())){
			QueryCdrVO queryCdrVO = (QueryCdrVO)processResultQueryCDR.getVo();
			List<CdrDetailVO> cdrs = changeToCdrList(queryCdrVO);
			JSONArray cdrsJson = JSONUtils.getJSONArray(cdrs);
			JSONObject jsonResult = new JSONObject();
			jsonResult.put("rows", cdrsJson);
			jsonResult.put("total", queryCdrVO.getTotalNum());
			jsonResult.put("page", page);
			jsonResult.put("rowNum", rowNum);
			model.put(UnieapConstants.SUCCESS, UnieapConstants.SUCCESS);
			model.put(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
			model.put(UnieapConstants.ERRORCODE, UnieapConstants.C0);
			model.put(UnieapConstants.ERRORDESC, UnieapConstants.getMessage(UnieapConstants.C0));
			model.put("datas", jsonResult.toString());
			model.put("totalNum",queryCdrVO.getTotalNum());
			return model;
		}else{
			model.put(UnieapConstants.SUCCESS, UnieapConstants.SUCCESS);
			model.put(UnieapConstants.ISSUCCESS, UnieapConstants.FAILED);
			model.put(UnieapConstants.ERRORCODE, UnieapConstants.C99999);
			model.put(UnieapConstants.ERRORDESC, processResultQueryCDR.getResultDesc());
			return model;
		}
	}
	public List<CdrDetailVO> changeToCdrList(QueryCdrVO queryCdrVO){
		List<CdrInfoVO> cdrInfoList = queryCdrVO.getCdrInfoList();
		if (cdrInfoList != null && cdrInfoList.size() > 0) {
			List<CdrDetailVO> cdrs = new ArrayList<CdrDetailVO>();
			for (int i = 0; i < cdrInfoList.size(); i++) {
				CdrInfoVO cdrInfoVO = cdrInfoList.get(i);
				CdrDetailVO cdrDetailVO = new CdrDetailVO();
				cdrs.add(cdrDetailVO);
				cdrDetailVO.setActualChargeAmt(BssServiceUtils.moneyFormat(cdrInfoVO.getActualChargeAmt()));

				if (StringUtils.isEmpty(cdrInfoVO.getActualVolume())) {
					cdrDetailVO.setActualVolume("--");
				} else {
					cdrDetailVO.setActualVolume(cdrInfoVO.getActualVolume());
					/*
					 * if("1106".equals(cdrInfoVO.getMeasureUnit())){
					 * cdrDetailVO.setActualVolume(BssServiceUtils.
					 * dataBToKBFormat(cdrInfoVO.getActualVolume())+"(KB)");
					 * cdrDetailVO.setOtherNumber(requestInfo.getRequestBody().
					 * getServiceNumber()); }else
					 * if("1003".equals(cdrInfoVO.getMeasureUnit())){
					 * cdrDetailVO.setActualVolume(cdrInfoVO.getActualVolume()+
					 * "(s)");
					 * cdrDetailVO.setOtherNumber(cdrInfoVO.getOtherNumber());
					 * }else if("1101".equals(cdrInfoVO.getMeasureUnit())){
					 * cdrDetailVO.setActualVolume(cdrInfoVO.getActualVolume()+
					 * "(item)");
					 * cdrDetailVO.setOtherNumber(cdrInfoVO.getOtherNumber());
					 * }else if("1006".equals(cdrInfoVO.getMeasureUnit())){
					 * cdrDetailVO.setActualVolume(cdrInfoVO.getActualVolume()+
					 * "(times)");
					 * cdrDetailVO.setOtherNumber(requestInfo.getRequestBody().
					 * getServiceNumber()); }else{
					 * cdrDetailVO.setActualVolume(cdrInfoVO.getActualVolume());
					 * cdrDetailVO.setOtherNumber(cdrInfoVO.getOtherNumber()); }
					 */
					// cdrDetailVO.setActualVolume(cdrInfoVO.getActualVolume());
					// cdrDetailVO.setOtherNumber(cdrInfoVO.getOtherNumber());
				}
				if (StringUtils.isEmpty(cdrInfoVO.getOtherNumber())) {
					cdrDetailVO.setOtherNumber(cdrInfoVO.getServiceNumber());
				} else {
					cdrDetailVO.setOtherNumber(cdrInfoVO.getOtherNumber());
				}
				cdrDetailVO.setEndTime(BssServiceUtils.dateFormat(cdrInfoVO.getEndTime()));
				if (StringUtils.isEmpty(cdrInfoVO.getFlowType())) {
					cdrInfoVO.setFlowType("--");
				}
				cdrDetailVO.setFlowType(UnieapConstants.getDicName("cdrFlowType", cdrInfoVO.getFlowType()));
				/*
				 * if("7".equals(cdrInfoVO.getServiceCategory())){
				 * cdrDetailVO.setOtherNumber(requestInfo.getRequestBody().
				 * getServiceNumber()); }
				 */
				cdrDetailVO.setServiceTypeName(cdrInfoVO.getServiceTypeDesc());
				cdrDetailVO.setStartTime(BssServiceUtils.dateFormat(cdrInfoVO.getStartTime()));
			}
			return cdrs;
		}
		return null;
	}
}
