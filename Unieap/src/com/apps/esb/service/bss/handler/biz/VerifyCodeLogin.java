package com.apps.esb.service.bss.handler.biz;

import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.crm.handler.GetCustomerData;
import com.apps.esb.service.bss.app.crm.handler.GetSubscriberData;
import com.apps.esb.service.bss.app.crm.vo.querycustomerinfo.CustomerInfoVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.ActualCustomerVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.AddressInfoVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.CorpInfoVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.IndividualInfoVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.QuerySubscriberInfoVO;
import com.apps.esb.service.bss.app.vo.CustomerProfileVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
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
		String verifyCode = json.getString("verifyCode");

		SmsBO smsBO = (SmsBO) ServiceUtils.getBean("smsBO");
		String returnCode = smsBO.checkVerifyCode("V", requestInfo.getRequestBody().getServiceNumber(), verifyCode);
		ProcessResult processResult = new ProcessResult();
		if (UnieapConstants.C1.equals(returnCode)) {
			GetSubscriberData getSubscriberData = (GetSubscriberData) ServiceUtils.getBean("getSubscriberData");
			ProcessResult processResultSubscriberData = getSubscriberData.process(requestInfo, parameters,
					extParameters);
			QuerySubscriberInfoVO querySubscriberInfoVO = (QuerySubscriberInfoVO) processResultSubscriberData.getVo();

			GetCustomerData getCustomerData = (GetCustomerData) ServiceUtils.getBean("getCustomerData");
			ProcessResult processResultGetCustomerData = getCustomerData.process(requestInfo, parameters,
					extParameters);
			CustomerInfoVO customerInfoVO = (CustomerInfoVO) processResultGetCustomerData.getVo();

			CustomerProfileVO customerProfileVO = getCustomerInfo(querySubscriberInfoVO, customerInfoVO);
			customerProfileVO.setContactNo(requestInfo.getRequestBody().getServiceNumber());
			customerProfileVO.setServiceNumber(requestInfo.getRequestBody().getServiceNumber());
			JSONObject customerJson = JSONUtils.convertBean2JSON(customerProfileVO);
			JSONObject jsonResult = new JSONObject();
			jsonResult.put("subscriberInfo", customerJson);
			processResult.setExtParameters(jsonResult.toString());
			processResult.setResultCode(UnieapConstants.C1);
			processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C1));
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
		List<AddressInfoVO> addressList = querySubscriberInfoVO.getAddressInfoList();
		if (addressList != null && addressList.size() > 0) {
			AddressInfoVO addressInfoVO = addressList.get(0);
			StringBuffer address = new StringBuffer();
			if (StringUtils.isNotEmpty(addressInfoVO.getAddress4())) {
				address.append(addressInfoVO.getAddress4()).append(" ");
			}
			if (StringUtils.isNotEmpty(addressInfoVO.getAddress5())) {
				address.append(addressInfoVO.getAddress5()).append(" ");
			}
			if (StringUtils.isNotEmpty(addressInfoVO.getAddress6())) {
				address.append(addressInfoVO.getAddress6()).append(" ");
			}
			vo.setAddress(address.toString());
		}
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
		vo.setSubscriberType(UnieapConstants.getDicName("paymentType", querySubscriberInfoVO.getSubscriberType()));

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
		vo.setCustomerName(name.toString());
		vo.setCertificateType(UnieapConstants.getDicName("certificateType", customerInfoVO.getCertificateType()));
		vo.setCertificateNumber(customerInfoVO.getCertificateNumber());
		vo.setCustomerLevel(UnieapConstants.getDicName("customerLevel", customerInfoVO.getCustomerLevel()));
		vo.setSupplement("");
		vo.setEmail("");
		return vo;
	}
}
