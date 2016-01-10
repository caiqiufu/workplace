package com.apps.esb.service.bss.handler.biz;

import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.crm.vo.querycustomerinfo.CustomerInfoVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.ActualCustomerVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.AddressInfoVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.CorpInfoVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.IndividualInfoVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.QuerySubscriberInfoVO;
import com.apps.esb.service.bss.customize.smart.app.handler.crm.GetCustomerData;
import com.apps.esb.service.bss.customize.smart.app.handler.crm.GetSubscriptionData;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.apps.esb.service.bss.vo.customerprofile.CustomerProfileVO;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.encrypt.EncryptionUtils;
import com.unieap.sms.bo.SmsBO;
import com.unieap.tools.JSONUtils;

@Service("verifyCodeLogin")
public class VerifyCodeLogin extends SoapMessageHandler implements BizHandler {

	public VerifyCodeLogin() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getExtParameters())) {
			throw new Exception("verifyCode is null");
		}
		JSONObject json = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if (!json.has("verifyCode")) {
			throw new Exception("verifyCode is null");
		}
		String verifyCodeEn = json.getString("verifyCode");
		String mKey =  SYSConfig.getConfig().get("mcare.encryption.key");
		String verifyCode = EncryptionUtils.decryptSun(verifyCodeEn, mKey);

		SmsBO smsBO = (SmsBO) ServiceUtils.getBean("smsBO");
		String returnCode = smsBO.checkVerifyCode("V", requestInfo.getRequestBody().getServiceNumber(), verifyCode);
		ProcessResult processResult = new ProcessResult();
		if (UnieapConstants.C0.equals(returnCode)) {
			GetSubscriptionData getSubscriptionData = (GetSubscriptionData) ServiceUtils.getBean("getSubscriptionData");
			ProcessResult processResultSubscriberData = getSubscriptionData.process(requestInfo, parameters,
					extParameters);
			QuerySubscriberInfoVO querySubscriberInfoVO = (QuerySubscriberInfoVO) processResultSubscriberData.getVo();

			JSONObject customerObj = new JSONObject("{customerId:"+querySubscriberInfoVO.getCustomerId()+"}");
			JSONObject newExtParameters = BssServiceUtils.modifyExtParameters(requestInfo.getRequestBody().getExtParameters(), customerObj);
			requestInfo.getRequestBody().setExtParameters(newExtParameters.toString());
			
			GetCustomerData getCustomerData = (GetCustomerData) ServiceUtils.getBean("getCustomerData");
			ProcessResult processResultGetCustomerData = getCustomerData.process(requestInfo, parameters,
					extParameters);
			CustomerInfoVO customerInfoVO = (CustomerInfoVO) processResultGetCustomerData.getVo();

			VerifyCodeLogin verifyCodeLogin = (VerifyCodeLogin) ServiceUtils.getBean("verifyCodeLogin");
			CustomerProfileVO customerProfileVO = verifyCodeLogin.getCustomerInfo(querySubscriberInfoVO,customerInfoVO);
			customerProfileVO.setContactNo(requestInfo.getRequestBody().getServiceNumber());
			customerProfileVO.setServiceNumber(requestInfo.getRequestBody().getServiceNumber());
			JSONObject customerJson = JSONUtils.convertBean2JSON(customerProfileVO);
			JSONObject jsonResult = new JSONObject();
			jsonResult.put("subscriberInfo", customerJson);
			processResult.setExtParameters(jsonResult.toString());
			processResult.setResultCode(UnieapConstants.C0);
			processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
			return processResult;
		} else {
			processResult.setResultCode(returnCode);
			processResult.setResultDesc(UnieapConstants.getMessage(returnCode));
			processResult.setServiceNumber(requestInfo.getRequestBody().getServiceNumber());
		}
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

	public CustomerProfileVO getCustomerInfo(QuerySubscriberInfoVO querySubscriberInfoVO, CustomerInfoVO customerInfoVO)
			throws Exception {
		CustomerProfileVO vo = new CustomerProfileVO();
		vo.setActiveDate(BssServiceUtils.dateFormat(querySubscriberInfoVO.getEffectiveDate()));
		List<AddressInfoVO> addressList = customerInfoVO.getAddressList();
		if (addressList != null && addressList.size() > 0) {
			AddressInfoVO addressInfoVO = addressList.get(0);
			StringBuffer address = new StringBuffer();
			if (StringUtils.isNotEmpty(addressInfoVO.getAddress1())) {
				address.append(addressInfoVO.getAddress1()).append(" ");
			}
			if (StringUtils.isNotEmpty(addressInfoVO.getAddress2())) {
				address.append(addressInfoVO.getAddress2()).append(" ");
			}
			if (StringUtils.isNotEmpty(addressInfoVO.getAddress3())) {
				address.append(addressInfoVO.getAddress3()).append(" ");
			}
			if (StringUtils.isNotEmpty(addressInfoVO.getAddress4())) {
				address.append(addressInfoVO.getAddress4()).append(" ");
			}
			if (StringUtils.isNotEmpty(addressInfoVO.getAddress5())) {
				address.append(addressInfoVO.getAddress5()).append(" ");
			}
			if (StringUtils.isNotEmpty(addressInfoVO.getAddress6())) {
				address.append(addressInfoVO.getAddress6()).append(" ");
			}
			if (StringUtils.isNotEmpty(addressInfoVO.getAddress7())) {
				address.append(addressInfoVO.getAddress7()).append(" ");
			}
			if (StringUtils.isNotEmpty(addressInfoVO.getAddress8())) {
				address.append(addressInfoVO.getAddress8()).append(" ");
			}
			if (StringUtils.isNotEmpty(addressInfoVO.getAddress9())) {
				address.append(addressInfoVO.getAddress9()).append(" ");
			}
			if (StringUtils.isNotEmpty(addressInfoVO.getAddress10())) {
				address.append(addressInfoVO.getAddress10()).append(" ");
			}
			vo.setAddress(address.toString());
		}
		vo.setPrimaryOfferingName(querySubscriberInfoVO.getPrimaryOfferingVO().getOfferingName());
		ActualCustomerVO actualCustomerVO = querySubscriberInfoVO.getActualCustomerVO();
		if (actualCustomerVO != null) {
			CorpInfoVO corpInfoVO = actualCustomerVO.getCorpInfoVO();
			if (corpInfoVO != null) {
				vo.setCorpName(corpInfoVO.getCustName());
				vo.setCorpNo(corpInfoVO.getCorpNo());
			}
			IndividualInfoVO individualInfoVO = new IndividualInfoVO();
			if (individualInfoVO != null) {
				StringBuffer name = new StringBuffer();
				if (StringUtils.isNotEmpty(individualInfoVO.getFirstName())) {
					name.append(individualInfoVO.getFirstName()).append(" ");
				}
				if (StringUtils.isNotEmpty(individualInfoVO.getMiddleName())) {
					name.append(individualInfoVO.getMiddleName()).append(" ");
				}
				if (StringUtils.isNotEmpty(individualInfoVO.getLastName())) {
					name.append(individualInfoVO.getLastName()).append(" ");
				}
				vo.setCustomerName(name.toString());
			}
		}
		vo.setStatus(UnieapConstants.getDicName("subscriberStatus", querySubscriberInfoVO.getStatus()));
		vo.setStatusReason(querySubscriberInfoVO.getStatusReason());
		vo.setSubscriberType(querySubscriberInfoVO.getSubscriberType());
		vo.setSubscriberTypeDesc(UnieapConstants.getDicName("paymentType", querySubscriberInfoVO.getSubscriberType()));
		StringBuffer name = new StringBuffer();
		if (StringUtils.isNotEmpty(customerInfoVO.getFirstName())) {
			name.append(customerInfoVO.getFirstName()).append(" ");
		}
		if (StringUtils.isNotEmpty(customerInfoVO.getMiddleName())) {
			name.append(customerInfoVO.getMiddleName()).append(" ");
		}
		if (StringUtils.isNotEmpty(customerInfoVO.getLastName())) {
			name.append(customerInfoVO.getLastName()).append(" ");
		}
		vo.setCustomerId(customerInfoVO.getCustomerId());
		vo.setCustomerName(name.toString());
		vo.setCertificateType(UnieapConstants.getDicName("certificateType", customerInfoVO.getCertificateType()));
		vo.setCertificateNumber(customerInfoVO.getCertificateNumber());
		vo.setCustomerLevel(UnieapConstants.getDicName("customerLevel", customerInfoVO.getCustomerLevel()));
		vo.setSupplement("");
		vo.setEmail("");
		vo.setDateOfBirth(customerInfoVO.getDateOfBirth());
		return vo;
	}
}
