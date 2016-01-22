package com.apps.esb.service.bss.handler.mcare;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.vo.subscriber.basicinfo.SubscriberVO;
import com.apps.esb.service.bss.app.vo.subscriber.offering.ServiceVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.CustomizeBizHanlder;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.vo.macre.offerings.OfferingVO;
import com.apps.esb.service.bss.vo.macre.subscribedofferings.SubscribedOfferingsVO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;

@Service("querySubscribedOfferings")
public class QuerySubscribedOfferings implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo,Map<String, String> handler,Map<String,Object> extParameters)
			throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		BizHandler appHandler = (BizHandler) ServiceUtils.getBeanByTenantId(handler.get("appHandlerName"));
		ProcessResult processResult = appHandler.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(processResult.getResultCode())) {
			return processResult;
		}
		SubscriberVO subscriberVO = (SubscriberVO) processResult.getVo();
		SubscribedOfferingsVO subscribedOfferingsVO = getSubscribedOfferingsVO(subscriberVO);
		if(handler!=null){
			String custHandlerName = handler.get("custHandlerName");
			if(!StringUtils.isEmpty(custHandlerName)){
				CustomizeBizHanlder custHandler = (CustomizeBizHanlder)ServiceUtils.getBean(custHandlerName);
				List<Object> originalVOs = new ArrayList<Object>();
				originalVOs.add(subscriberVO);
				custHandler.process(requestInfo, handler, extParameters, subscribedOfferingsVO,originalVOs);
			}
		}
		JSONObject subscribedInfo = JSONUtils.convertBean2JSON(subscribedOfferingsVO);
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("subscribedInfo", subscribedInfo);
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

	public SubscribedOfferingsVO getSubscribedOfferingsVO(SubscriberVO subscriberVO) {
		
		//Map<String, OfferingVO> offeringDefines = (Map<String, OfferingVO>)CacheMgt.getCacheData().get("offeringDefine");
		SubscribedOfferingsVO subscribedOfferingsVO = new SubscribedOfferingsVO();
		List<OfferingVO> normalOfferings = new ArrayList<OfferingVO>();
		subscribedOfferingsVO.setNormalOfferings(normalOfferings);
		List<OfferingVO> vasOfferings = new ArrayList<OfferingVO>();
		subscribedOfferingsVO.setVasOfferings(vasOfferings);
		List<OfferingVO> products = new ArrayList<OfferingVO>();
		subscribedOfferingsVO.setProducts(products);
		List<OfferingVO> services = new ArrayList<OfferingVO>();
		//subscribedOfferingsVO.setServices(services);
		subscribedOfferingsVO.setServices(products);
		List<OfferingVO> promotionOfferings = new ArrayList<>();
		subscribedOfferingsVO.setPromotionOfferings(promotionOfferings);

		com.apps.esb.service.bss.app.vo.subscriber.offering.OfferingVO primaryOfferingVO = subscriberVO.getPrimaryOfferingVO();
		//OfferingVO offeringDefine = offeringDefines.get(primaryOfferingVO.getOfferingIdVO().getOfferingId());
		OfferingVO primaryOffering = new OfferingVO();
		normalOfferings.add(primaryOffering);
		primaryOffering.setEffectiveDate(BssServiceUtils.dateFormat(primaryOfferingVO.getEffectiveTime()));
		primaryOffering.setExpiryDate(BssServiceUtils.dateFormat(primaryOfferingVO.getExpiryTime()));
		/*if(offeringDefine!=null){
			primaryOffering.setEffectiveType(offeringDefine.getEffectiveType());
			//primaryOffering.setFeeAmount(BssServiceUtils.moneyFormat(offeringDefine.getFeeAmount()));
			primaryOffering.setFeeAmount(offeringDefine.getFeeAmount());
			primaryOffering.setOfferCategory(offeringDefine.getOfferCategory());
		}*/
		primaryOffering.setOfferingCode(primaryOfferingVO.getOfferingCode());
		primaryOffering.setOfferingName(primaryOfferingVO.getOfferingName());
		primaryOffering.setOfferingDesc(primaryOfferingVO.getOfferingName());
		primaryOffering.setOfferingId(primaryOfferingVO.getOfferingId());
		primaryOffering.setOfferingType("P");
		primaryOffering.setSubscribeDate(BssServiceUtils.dateFormat(primaryOfferingVO.getEffectiveTime()));
		//List<SubProductInfoVO> primaryProductInfoList = primaryOfferingVO.getSubProductInfoList();
		List<com.apps.esb.service.bss.app.vo.subscriber.offering.ServiceVO> serviceList = primaryOfferingVO.getServiceList();
		if (serviceList != null) {
			for (int k = 0; k < serviceList.size(); k++) {
				com.apps.esb.service.bss.app.vo.subscriber.offering.ServiceVO serviceVO = serviceList.get(k);
				OfferingVO offeringVO = new OfferingVO();
				services.add(offeringVO);
				products.add(offeringVO);
				offeringVO.setEffectiveDate(BssServiceUtils.dateFormat(primaryOfferingVO.getEffectiveTime()));
				offeringVO.setExpiryDate(BssServiceUtils.dateFormat(primaryOfferingVO.getExpiryTime()));
				offeringVO.setOfferingCode(serviceVO.getServiceId());
				offeringVO.setOfferingName(serviceVO.getServiceName());
				offeringVO.setStatus(serviceVO.getServiceStatus());
				offeringVO.setOfferingDesc(serviceVO.getServiceName());
				offeringVO.setOfferingId(serviceVO.getServiceId());
				offeringVO.setOfferingType(serviceVO.getServiceType());
				offeringVO.setSubscribeDate(BssServiceUtils.dateFormat(primaryOfferingVO.getEffectiveTime()));

			}
		}
		
		List<com.apps.esb.service.bss.app.vo.subscriber.offering.OfferingVO> supplementaryOfferingList = subscriberVO.getSupplementaryOfferingList();
		if (supplementaryOfferingList != null) {
			for (int i = 0; i < supplementaryOfferingList.size(); i++) {
				com.apps.esb.service.bss.app.vo.subscriber.offering.OfferingVO supplementaryOfferingVO = supplementaryOfferingList.get(i);
				OfferingVO supplementaryOffer = new OfferingVO();
				vasOfferings.add(supplementaryOffer);
				supplementaryOffer.setEffectiveDate(BssServiceUtils.dateFormat(supplementaryOfferingVO.getEffectiveTime()));
				supplementaryOffer.setExpiryDate(BssServiceUtils.dateFormat(supplementaryOfferingVO.getExpiryTime()));
				supplementaryOffer.setOfferingCode(supplementaryOfferingVO.getOfferingCode());
				supplementaryOffer.setOfferingName(supplementaryOfferingVO.getOfferingName());
				supplementaryOffer.setOfferingDesc(supplementaryOfferingVO.getOfferingName());
				supplementaryOffer.setOfferingId(supplementaryOfferingVO.getOfferingId());
				supplementaryOffer.setOfferingType("S");
				supplementaryOffer.setSeq(supplementaryOfferingVO.getPurchaseSeq());
				supplementaryOffer.setSubscribeDate(BssServiceUtils.dateFormat(supplementaryOfferingVO.getEffectiveTime()));

				List<ServiceVO> supplementaryOfferingServiceList = supplementaryOfferingVO.getServiceList();
				if (supplementaryOfferingServiceList != null) {
					for (int k = 0; k < supplementaryOfferingServiceList.size(); k++) {
						ServiceVO serviceVO = supplementaryOfferingServiceList.get(k);
						OfferingVO offeringVO = new OfferingVO();
						services.add(offeringVO);
						products.add(offeringVO);
						offeringVO.setEffectiveDate(BssServiceUtils.dateFormat(supplementaryOfferingVO.getEffectiveTime()));
						offeringVO.setExpiryDate(BssServiceUtils.dateFormat(supplementaryOfferingVO.getExpiryTime()));
						offeringVO.setOfferingCode(serviceVO.getServiceId());
						offeringVO.setOfferingName(serviceVO.getServiceName());
						offeringVO.setOfferingDesc(serviceVO.getServiceName());
						offeringVO.setOfferingId(serviceVO.getServiceId());
						offeringVO.setStatus(serviceVO.getServiceStatus());
						offeringVO.setSubscribeDate(BssServiceUtils.dateFormat(supplementaryOfferingVO.getEffectiveTime()));

					}
				}
			}
		}
		return subscribedOfferingsVO;
	}
}
