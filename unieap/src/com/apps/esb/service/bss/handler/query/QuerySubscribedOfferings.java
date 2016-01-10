package com.apps.esb.service.bss.handler.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.PrimaryOfferingVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.QuerySubscriberInfoVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.ServiceVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.SubProductInfoVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.SupplementaryOfferingVO;
import com.apps.esb.service.bss.customize.smart.app.handler.crm.GetSubscriptionData;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.apps.esb.service.bss.vo.offerings.OfferingVO;
import com.apps.esb.service.bss.vo.subscribedofferings.SubscribedOfferingsVO;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;

@Service("querySubscribedOfferings")
public class QuerySubscribedOfferings extends SoapMessageHandler implements BizHandler {

	public QuerySubscribedOfferings() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo,String parameters,Map<String,Object> extParameters)
			throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		GetSubscriptionData getSubscriptionData = (GetSubscriptionData) ServiceUtils.getBean("getSubscriptionData");
		ProcessResult processResult = getSubscriptionData.process(requestInfo,parameters,extParameters);
		if(!UnieapConstants.C0.equals(processResult.getResultCode())){
			return processResult;
		}
		QuerySubscriberInfoVO querySubscriberInfoVO = (QuerySubscriberInfoVO) processResult.getVo();
		SubscribedOfferingsVO subscribedOfferingsVO = getSubscribedOfferingsVO(querySubscriberInfoVO);
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

	public SubscribedOfferingsVO getSubscribedOfferingsVO(QuerySubscriberInfoVO querySubscriberInfoVO) {
		
		Map<String, OfferingVO> offeringDefines = (Map<String, OfferingVO>)CacheMgt.getCacheData().get("offeringDefine");
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

		PrimaryOfferingVO primaryOfferingVO = querySubscriberInfoVO.getPrimaryOfferingVO();
		OfferingVO offeringDefine = offeringDefines.get(primaryOfferingVO.getOfferingIdVO().getOfferingId());
		OfferingVO primaryOffering = new OfferingVO();
		normalOfferings.add(primaryOffering);
		primaryOffering.setEffectiveDate(BssServiceUtils.dateFormat(primaryOfferingVO.getEffectiveTime()));
		primaryOffering.setExpiryDate(BssServiceUtils.dateFormat(primaryOfferingVO.getExpiredTime()));
		if(offeringDefine!=null){
			primaryOffering.setEffectiveType(offeringDefine.getEffectiveType());
			//primaryOffering.setFeeAmount(BssServiceUtils.moneyFormat(offeringDefine.getFeeAmount()));
			primaryOffering.setFeeAmount(offeringDefine.getFeeAmount());
			primaryOffering.setOfferCategory(offeringDefine.getOfferCategory());
		}
		primaryOffering.setOfferingCode(primaryOfferingVO.getOfferingIdVO().getOfferingCode());
		primaryOffering.setOfferingName(primaryOfferingVO.getOfferingName());
		primaryOffering.setOfferingDesc(primaryOfferingVO.getOfferingName());
		primaryOffering.setOfferingId(primaryOfferingVO.getOfferingIdVO().getOfferingId());
		primaryOffering.setOfferingType("P");
		primaryOffering.setSubscribeDate(BssServiceUtils.dateFormat(primaryOfferingVO.getEffectiveTime()));
		List<SubProductInfoVO> primaryProductInfoList = primaryOfferingVO.getSubProductInfoList();
		if(primaryProductInfoList!=null&&primaryProductInfoList.size()>0){
			for(int i = 0 ; i < primaryProductInfoList.size() ; i++){
				SubProductInfoVO productInfoVO = primaryProductInfoList.get(i);
				OfferingVO productVo = new OfferingVO();
				products.add(productVo);
				productVo.setEffectiveDate(BssServiceUtils.dateFormat(primaryOfferingVO.getEffectiveTime()));
				productVo.setExpiryDate(BssServiceUtils.dateFormat(primaryOfferingVO.getExpiredTime()));
				productVo.setOfferingId(productInfoVO.getProductId());
				productVo.setOfferingCode("");
				productVo.setOfferingName(productInfoVO.getProductName());
				productVo.setOfferingDesc(productInfoVO.getProductName());
				productVo.setSubscribeDate(BssServiceUtils.dateFormat(primaryOfferingVO.getEffectiveTime()));
				
				List<ServiceVO> serviceList = productInfoVO.getServiceList();
				if (serviceList != null) {
					for (int k = 0; k < serviceList.size(); k++) {
						ServiceVO serviceVO = serviceList.get(k);
						OfferingVO offeringVO = new OfferingVO();
						services.add(offeringVO);
						offeringVO.setEffectiveDate(BssServiceUtils.dateFormat(primaryOfferingVO.getEffectiveTime()));
						offeringVO.setExpiryDate(BssServiceUtils.dateFormat(primaryOfferingVO.getExpiredTime()));
						offeringVO.setOfferingCode(serviceVO.getServiceId());
						offeringVO.setOfferingName(serviceVO.getServiceName());
						offeringVO.setStatus(serviceVO.getServiceStatus());
						offeringVO.setOfferingDesc(serviceVO.getServiceName());
						offeringVO.setOfferingId(serviceVO.getServiceId());
						offeringVO.setOfferingType(serviceVO.getServiceType());
						offeringVO.setSubscribeDate(BssServiceUtils.dateFormat(primaryOfferingVO.getEffectiveTime()));

					}
				}
			}
		}
		
		List<SupplementaryOfferingVO> supplementaryOfferingList = querySubscriberInfoVO.getSupplementaryOfferingList();
		if (supplementaryOfferingList != null) {
			for (int i = 0; i < supplementaryOfferingList.size(); i++) {
				SupplementaryOfferingVO supplementaryOfferingVO = supplementaryOfferingList.get(i);
				OfferingVO supplementaryOffer = new OfferingVO();
				offeringDefine = offeringDefines.get(supplementaryOfferingVO.getOfferingIdVO().getOfferingId());
				vasOfferings.add(supplementaryOffer);
				supplementaryOffer.setEffectiveDate(BssServiceUtils.dateFormat(supplementaryOfferingVO.getEffectiveTime()));
				supplementaryOffer.setExpiryDate(BssServiceUtils.dateFormat(supplementaryOfferingVO.getExpiredTime()));
				if(offeringDefine!=null){
					supplementaryOffer.setEffectiveType(offeringDefine.getEffectiveType());
					//supplementaryOffer.setFeeAmount(BssServiceUtils.moneyFormat(offeringDefine.getFeeAmount()));
					supplementaryOffer.setFeeAmount(offeringDefine.getFeeAmount());
					supplementaryOffer.setOfferCategory(offeringDefine.getOfferCategory());
				}
				supplementaryOffer.setOfferingCode(supplementaryOfferingVO.getOfferingIdVO().getOfferingCode());
				supplementaryOffer.setOfferingName(supplementaryOfferingVO.getOfferingName());
				supplementaryOffer.setOfferingDesc(supplementaryOfferingVO.getOfferingName());
				supplementaryOffer.setOfferingId(supplementaryOfferingVO.getOfferingIdVO().getOfferingId());
				supplementaryOffer.setOfferingType("S");
				supplementaryOffer.setSeq(supplementaryOfferingVO.getOfferingIdVO().getPurchaseSeq());
				supplementaryOffer.setSubscribeDate(BssServiceUtils.dateFormat(supplementaryOfferingVO.getEffectiveTime()));

				List<SubProductInfoVO> subProductInfoList = supplementaryOfferingVO.getSubProductInfoList();
				if (subProductInfoList != null) {
					for (int j = 0; j < subProductInfoList.size(); j++) {
						SubProductInfoVO subProductInfoVO = subProductInfoList.get(j);
						
						OfferingVO productVo = new OfferingVO();
						products.add(productVo);
						productVo.setEffectiveDate(BssServiceUtils.dateFormat(supplementaryOfferingVO.getEffectiveTime()));
						productVo.setExpiryDate(BssServiceUtils.dateFormat(supplementaryOfferingVO.getExpiredTime()));
						productVo.setOfferingId(subProductInfoVO.getProductId());
						productVo.setOfferingCode("");
						productVo.setOfferingName(subProductInfoVO.getProductName());
						productVo.setOfferingDesc(subProductInfoVO.getProductName());
						productVo.setSubscribeDate(BssServiceUtils.dateFormat(supplementaryOfferingVO.getEffectiveTime()));
						
						List<ServiceVO> serviceList = subProductInfoVO.getServiceList();
						if (serviceList != null) {
							for (int k = 0; k < serviceList.size(); k++) {
								ServiceVO serviceVO = serviceList.get(k);
								OfferingVO offeringVO = new OfferingVO();
								services.add(offeringVO);
								offeringVO.setEffectiveDate(BssServiceUtils.dateFormat(supplementaryOfferingVO.getEffectiveTime()));
								offeringVO.setExpiryDate(BssServiceUtils.dateFormat(supplementaryOfferingVO.getExpiredTime()));
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

			}
		}
		return subscribedOfferingsVO;
	}
}
