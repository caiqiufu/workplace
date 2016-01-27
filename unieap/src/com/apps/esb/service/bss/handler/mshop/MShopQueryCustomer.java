package com.apps.esb.service.bss.handler.mshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.app.vo.common.address.AddressVO;
import com.apps.esb.service.bss.app.vo.customer.basicinfo.CustomerVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.CustomizeBizHanlder;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.vo.mshop.customer.MshopAddressVO;
import com.apps.esb.service.bss.vo.mshop.customer.MshopCustomerVO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;


@Service("mShopQueryCustomer")
public class MShopQueryCustomer implements BizHandler{

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		BizHandler appHandler = (BizHandler) ServiceUtils.getBeanByTenantId(handler.get("appHandlerName"));
		ProcessResult processResult = appHandler.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(processResult.getResultCode())) {
			return processResult;
		}
		CustomerVO customerVO = (CustomerVO) processResult.getVo();
		MshopCustomerVO mshopCustomerVO = getCustomerVO(customerVO);
		
		if(handler!=null){
			String custHandlerName = handler.get("custHandlerName");
			if(!StringUtils.isEmpty(custHandlerName)){
				CustomizeBizHanlder custHandler = (CustomizeBizHanlder)ServiceUtils.getBean(custHandlerName);
				List<Object> originalVOs = new ArrayList<Object>();
				originalVOs.add(customerVO);
				custHandler.process(requestInfo, handler, extParameters, mshopCustomerVO,originalVOs);
			}
		}
		processResult.setResultCode(UnieapConstants.C0);
		processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
		JSONObject dataBalanceJson = JSONUtils.convertBean2JSON(mshopCustomerVO);
		processResult.setExtParameters(dataBalanceJson.toString());
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
	
	public MshopCustomerVO getCustomerVO(CustomerVO customerVO){
		MshopCustomerVO mshopCustomerVO = new MshopCustomerVO();
		mshopCustomerVO.setCertificateNumber(customerVO.getCertificateNumber());
		mshopCustomerVO.setCertificateType(customerVO.getCertificateType());
		mshopCustomerVO.setCertificateTypeDesc(customerVO.getCertificateTypeDesc());
		mshopCustomerVO.setCustomerId(customerVO.getCustomerId());
		mshopCustomerVO.setCustomerLevel(customerVO.getCustomerLevel());
		mshopCustomerVO.setCustomerLevelDesc(customerVO.getCustomerLevelDesc());
		mshopCustomerVO.setDateOfBirth(customerVO.getDateOfBirth());
		mshopCustomerVO.setEffectiveTime(customerVO.getEffectiveTime());
		mshopCustomerVO.setEmail(customerVO.getEmail());
		mshopCustomerVO.setExpiryTime(customerVO.getExpiryTime());
		mshopCustomerVO.setFirstName(customerVO.getFirstName());
		mshopCustomerVO.setGender(customerVO.getGender());
		mshopCustomerVO.setGenderDesc(customerVO.getGenderDesc());
		mshopCustomerVO.setIssuingCountry(customerVO.getNationality());
		mshopCustomerVO.setLanguage(customerVO.getLanguage());
		mshopCustomerVO.setLastName(customerVO.getLastName());
		mshopCustomerVO.setMiddleName(customerVO.getMiddleName());
		mshopCustomerVO.setPhoneNumber("");
		mshopCustomerVO.setRemark(customerVO.getRemark());
		mshopCustomerVO.setTitle(customerVO.getTitle());
		mshopCustomerVO.setTitleDesc(customerVO.getTitleDesc());
		mshopCustomerVO.setVerifiedCustomer("");
		
		mshopCustomerVO.setEffectiveTime(customerVO.getEffectiveTime());
		mshopCustomerVO.setExpiryTime(customerVO.getExpiryTime());
		mshopCustomerVO.setExtAttris(customerVO.getExtAttris());
		
		List<MshopAddressVO> mshopAddressList = new ArrayList<MshopAddressVO>();
		mshopCustomerVO.setAddressList(mshopAddressList);
		List<AddressVO> addressList = customerVO.getAddressList();
		if(addressList!=null&&addressList.size()>0){
			MshopAddressVO mshopAddressVO = new MshopAddressVO();
			AddressVO addressVO = addressList.get(0);
			mshopAddressVO.setAddressType(addressVO.getAddressType());
			mshopAddressVO.setAddressTypeDesc(addressVO.getActionTypeDesc());
			mshopAddressVO.setBlock("");
			mshopAddressVO.setCommune("");
			mshopAddressVO.setCommuneDesc("");
			mshopAddressVO.setDistrict(addressVO.getAddress3());
			mshopAddressVO.setCommuneDesc("");
			mshopAddressVO.setHouseNumber("");
			mshopAddressVO.setNationality(addressVO.getAddress1());
			mshopAddressVO.setNationalityDesc("");
			mshopAddressVO.setPoBox("");
			mshopAddressVO.setPostcode("");
			mshopAddressVO.setProvince(addressVO.getAddress2());
			mshopAddressVO.setProvinceDesc("");
			mshopAddressVO.setRemark("");
			mshopAddressVO.setStreet("");
			mshopAddressVO.setVillage("");
			mshopAddressVO.setWardNumber("");
			
			mshopAddressVO.setEffectiveTime(addressVO.getEffectiveTime());
			mshopAddressVO.setExpiryTime(addressVO.getExpiryTime());
			mshopAddressVO.setExtAttris(addressVO.getExtAttris());
			
		}
		return mshopCustomerVO;
	}

}
