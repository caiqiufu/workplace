package com.apps.esb.service.bss.handler.mcare;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.vo.common.address.AddressVO;
import com.apps.esb.service.bss.app.vo.customer.basicinfo.CorporateVO;
import com.apps.esb.service.bss.app.vo.customer.basicinfo.CustomerVO;
import com.apps.esb.service.bss.app.vo.subscriber.basicinfo.SubscriberVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.CustomizeBizHanlder;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.vo.macre.customerprofile.CustomerProfileVO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;

@Service("servicePasswordLogin")
public class ServicePasswordLogin implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo,Map<String, String> handler,Map<String,Object> extParameters)
			throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		JSONObject json = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if (!json.has("password")) {
			throw new Exception("password is null");
		}
		if (StringUtils.isEmpty(json.getString("password"))) {
			throw new Exception("password is null");
		}
		BizHandler appHandler = (BizHandler) ServiceUtils.getBeanByTenantId(handler.get("appHandlerName"));
		ProcessResult processResult = appHandler.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(processResult.getResultCode())) {
			return processResult;
		}
		SubscriberVO subscriberVO = (SubscriberVO) processResult.getVo();
		CustomerProfileVO customerProfileVO = getCustomerInfo(subscriberVO);
		if(handler!=null){
			String custHandlerName = handler.get("custHandlerName");
			if(!StringUtils.isEmpty(custHandlerName)){
				CustomizeBizHanlder custHandler = (CustomizeBizHanlder)ServiceUtils.getBean(custHandlerName);
				List<Object> originalVOs = new ArrayList<Object>();
				originalVOs.add(subscriberVO);
				custHandler.process(requestInfo, handler, extParameters, customerProfileVO,originalVOs);
			}
		}
		JSONObject jsonResult = new JSONObject();
		JSONObject customerJson = JSONUtils.convertBean2JSON(customerProfileVO);
		jsonResult.put("subscriberInfo", customerJson);
		processResult.setExtParameters(jsonResult.toString());
		processResult.setResultCode(UnieapConstants.C0);
		processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
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
		return null;
	}
	public CustomerProfileVO getCustomerInfo(SubscriberVO subscriberVO)
			throws Exception{
		CustomerProfileVO vo = new CustomerProfileVO();
		vo.setActiveDate(BssServiceUtils.dateFormat(subscriberVO.getEffectiveTime()));
		CustomerVO customerVO = subscriberVO.getCustomerVO();
		
		vo.setCustomerId(subscriberVO.getCustomerId());
		vo.setCertificateType(customerVO.getCertificateType());
		vo.setCertificateNumber(customerVO.getCertificateNumber());
		vo.setCustomerLevel(customerVO.getCustomerLevel());
		vo.setSupplement("");
		vo.setEmail("");
		vo.setDateOfBirth(customerVO.getDateOfBirth());
		vo.setPaymentFlag(subscriberVO.getPaymentFlag());
		vo.setPaymentFlagDesc(subscriberVO.getPaymentFlagDesc());
		StringBuffer name = new StringBuffer();
		if (StringUtils.isNotEmpty(customerVO.getFirstName())) {
			name.append(customerVO.getFirstName()).append(" ");
		}
		if (StringUtils.isNotEmpty(customerVO.getMiddleName())) {
			name.append(customerVO.getMiddleName()).append(" ");
		}
		if (StringUtils.isNotEmpty(customerVO.getLastName())) {
			name.append(customerVO.getLastName()).append(" ");
		}
		vo.setCustomerName(name.toString());
		
		
		List<AddressVO> addressList = customerVO.getAddressList();
		if (addressList != null && addressList.size() > 0) {
			AddressVO addressVO = addressList.get(0);
			StringBuffer address = new StringBuffer();
			if (StringUtils.isNotEmpty(addressVO.getAddress1())) {
				address.append(addressVO.getAddress1()).append(" ");
			}
			if (StringUtils.isNotEmpty(addressVO.getAddress2())) {
				address.append(addressVO.getAddress2()).append(" ");
			}
			if (StringUtils.isNotEmpty(addressVO.getAddress3())) {
				address.append(addressVO.getAddress3()).append(" ");
			}
			if (StringUtils.isNotEmpty(addressVO.getAddress4())) {
				address.append(addressVO.getAddress4()).append(" ");
			}
			if (StringUtils.isNotEmpty(addressVO.getAddress5())) {
				address.append(addressVO.getAddress5()).append(" ");
			}
			if (StringUtils.isNotEmpty(addressVO.getAddress6())) {
				address.append(addressVO.getAddress6()).append(" ");
			}
			if (StringUtils.isNotEmpty(addressVO.getAddress7())) {
				address.append(addressVO.getAddress7()).append(" ");
			}
			if (StringUtils.isNotEmpty(addressVO.getAddress8())) {
				address.append(addressVO.getAddress8()).append(" ");
			}
			if (StringUtils.isNotEmpty(addressVO.getAddress9())) {
				address.append(addressVO.getAddress9()).append(" ");
			}
			if (StringUtils.isNotEmpty(addressVO.getAddress10())) {
				address.append(addressVO.getAddress10()).append(" ");
			}
			vo.setAddress(address.toString());
		}
		vo.setPrimaryOfferingName(subscriberVO.getPrimaryOfferingVO().getOfferingName());
		CorporateVO corporateVO = subscriberVO.getCorporateVO();
		if (corporateVO != null) {
			vo.setCorpName(corporateVO.getCustName());
			vo.setCorpNo(corporateVO.getCorpNo());
		}
		vo.setStatus(subscriberVO.getStatusDesc());
		vo.setStatusReason(subscriberVO.getStatusReasonDesc());
		vo.setSubscriberType(subscriberVO.getSubscriberType());
		vo.setSubscriberTypeDesc(subscriberVO.getSubscriberTypeDesc());
		return vo;
	}

}
