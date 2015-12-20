package com.apps.esb.service.bss.handler.query;

import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.bizengine.CheckOfferingRuleHandler;
import com.apps.esb.service.bss.app.crm.handler.GetAvailablePrimaryOffer;
import com.apps.esb.service.bss.app.crm.handler.GetAvailableSupplementaryOffering;
import com.apps.esb.service.bss.app.crm.vo.getavailableprimaryoffering.AvailablePrimaryOfferingVO;
import com.apps.esb.service.bss.app.crm.vo.getavailablesuboffering.AvailableSubOfferingVO;
import com.apps.esb.service.bss.app.vo.OfferCategoryVO;
import com.apps.esb.service.bss.app.vo.OfferingVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.db.DBManager;
import com.unieap.db.EntityRowMapper;
import com.unieap.tools.JSONUtils;

@Service("queryOfferings")
public class QueryOfferings extends SoapMessageHandler implements BizHandler {

	public QueryOfferings() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
		String categoryId = "";
		if (!StringUtils.isEmpty(requestInfo.getRequestBody().getExtParameters())) {
			JSONObject extParametersJson = new JSONObject(requestInfo.getRequestBody().getExtParameters());
			if (extParametersJson.has("categoryId")) {
				categoryId = extParametersJson.getString("categoryId");
			} else {
				throw new Exception("categoryId is null");
			}
		}
		ProcessResult result = new ProcessResult();
		result.setServiceNumber(requestInfo.getRequestBody().getServiceNumber());
		result.setResultCode(UnieapConstants.C0);
		result.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
		OfferCategoryVO offerCategoryVO = getOfferingsCategory(categoryId);

		if ("ct_me".equals(offerCategoryVO.getCategoryType())) {

			GetAvailablePrimaryOffer getAvailablePrimaryOffer = (GetAvailablePrimaryOffer) ServiceUtils
					.getBean("getAvailablePrimaryOffer");
			ProcessResult processResult = getAvailablePrimaryOffer.process(requestInfo, parameters, extParameters);
			AvailablePrimaryOfferingVO availablePrimaryOfferingVO = (AvailablePrimaryOfferingVO) processResult.getVo();

			CheckOfferingRuleHandler checkOfferingRuleHandler = (CheckOfferingRuleHandler) ServiceUtils
					.getBean("checkOfferingRuleHandler");
			checkOfferingRuleHandler.availableOfferings("P",availablePrimaryOfferingVO.getOfferingInfoMap(),
					offerCategoryVO);
		} else if ("ct_all".equals(offerCategoryVO.getCategoryType())) {

			GetAvailablePrimaryOffer getAvailablePrimaryOffer = (GetAvailablePrimaryOffer) ServiceUtils
					.getBean("getAvailablePrimaryOffer");
			ProcessResult processResult = getAvailablePrimaryOffer.process(requestInfo, parameters, extParameters);
			AvailablePrimaryOfferingVO availablePrimaryOfferingVO = (AvailablePrimaryOfferingVO) processResult.getVo();

			CheckOfferingRuleHandler checkOfferingRuleHandler = (CheckOfferingRuleHandler) ServiceUtils
					.getBean("checkOfferingRuleHandler");
			checkOfferingRuleHandler.availableOfferings("P",availablePrimaryOfferingVO.getOfferingInfoMap(),
					offerCategoryVO);

			GetAvailableSupplementaryOffering getAvailableSupplementaryOffering = (GetAvailableSupplementaryOffering) ServiceUtils
					.getBean("getAvailableSupplementaryOffering");
			ProcessResult processSubResult = getAvailableSupplementaryOffering.process(requestInfo, parameters,
					extParameters);
			AvailableSubOfferingVO AvailableSubOfferingVO = (AvailableSubOfferingVO) processSubResult.getVo();
			checkOfferingRuleHandler.availableOfferings("S",AvailableSubOfferingVO.getOfferingInfoMap(), offerCategoryVO);

		} else {
			GetAvailableSupplementaryOffering getAvailableSupplementaryOffering = (GetAvailableSupplementaryOffering) ServiceUtils
					.getBean("getAvailableSupplementaryOffering");
			ProcessResult processSubResult = getAvailableSupplementaryOffering.process(requestInfo, parameters,
					extParameters);
			AvailableSubOfferingVO AvailableSubOfferingVO = (AvailableSubOfferingVO) processSubResult.getVo();
			CheckOfferingRuleHandler checkOfferingRuleHandler = (CheckOfferingRuleHandler) ServiceUtils
					.getBean("checkOfferingRuleHandler");
			checkOfferingRuleHandler.availableOfferings("S",AvailableSubOfferingVO.getOfferingInfoMap(), offerCategoryVO);
		}

		JSONObject offerCategoryJson = JSONUtils.convertBean2JSON(offerCategoryVO);
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("offerCategoryDetail", offerCategoryJson);
		result.setExtParameters(jsonResult.toString());
		return result;
	}

	private OfferCategoryVO getOfferingsCategory(String categoryId) {

		/*
		 * AppOfferingCategory appOfferingCategory =
		 * DBManager.getHT(null).get(AppOfferingCategory.class, categoryId);
		 * OfferCategoryVO offerCategoryVO = new OfferCategoryVO();
		 * offerCategoryVO.setCategoreName(appOfferingCategory.getCategoreName()
		 * );
		 * offerCategoryVO.setCategoryDesc(appOfferingCategory.getCategoryDesc()
		 * ); offerCategoryVO.setCategoryId(categoryId);
		 * offerCategoryVO.setDetailHyperlink(appOfferingCategory.
		 * getDetailHyperlink());
		 * offerCategoryVO.setDetailUrl(appOfferingCategory.getDetailUrl());
		 * offerCategoryVO.setNoteHyperlink(appOfferingCategory.getNoteHyperlink
		 * ());
		 * offerCategoryVO.setNoteUrl(appOfferingCategory.getNoteHyperlink());
		 * offerCategoryVO.setPictureUrl(appOfferingCategory.getPictureUrl());
		 * offerCategoryVO.setPlanHyperlink(appOfferingCategory.getPlanHyperlink
		 * ()); offerCategoryVO.setPlanUrl(appOfferingCategory.getPlanUrl());
		 * offerCategoryVO.setPriceDesc(appOfferingCategory.getPriceDesc());
		 * offerCategoryVO.setQuestionHyperlink(appOfferingCategory.
		 * getQuestionHyperlink());
		 * offerCategoryVO.setQuestionUrl(appOfferingCategory.getQuestionUrl());
		 */
		StringBuffer sqlCategory = new StringBuffer();
		sqlCategory.append(" select  oc.id as categoryId,category_type as categoryType,fa.url as pictureUrl,");
		sqlCategory.append("categore_name as categoreName, category_desc as categoryDesc,price_desc as priceDesc, ");
		sqlCategory.append(" oc.detail_url as detailUrl,detail_hyperlink as detailHyperlink,plan_url as planUrl,");
		sqlCategory.append(" plan_hyperlink as planHyperlink,question_url as questionUrl,");
		sqlCategory.append("question_hyperlink as questionHyperlink,");
		sqlCategory.append(" note_url as noteUrl,note_hyperlink as noteHyperlink ");
		sqlCategory.append(" from app_offering_category oc ,unieap.file_archive fa ");
		sqlCategory.append(" where oc.id = fa.ext_key and oc.id= ? ");
		List offerCategoryVOs = DBManager.getJT(null).query(sqlCategory.toString(), new Object[] { categoryId },
				new EntityRowMapper(OfferCategoryVO.class));
		OfferCategoryVO offerCategoryVO = (OfferCategoryVO) offerCategoryVOs.get(0);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT seq as seq,offering_id as offeringId,offering_code as offeringCode,");
		sql.append("offering_name as offeringName,");
		sql.append("offering_type as offeringType,offering_desc as offeringDesc,effective_type as effectiveType,");
		sql.append(" fee_amount as feeAmount FROM unieap.app_offering");
		sql.append(" where category_id = ? and active_flag = ? order by seq, offering_name");
		List datas = DBManager.getJT(null).query(sql.toString(), new Object[] { categoryId, UnieapConstants.YES },
				new EntityRowMapper(OfferingVO.class));
		offerCategoryVO.setOfferings(datas);
		return offerCategoryVO;
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

}
