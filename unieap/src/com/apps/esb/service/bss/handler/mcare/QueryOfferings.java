package com.apps.esb.service.bss.handler.mcare;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.app.vo.subscriber.offering.QueryAvailableOfferingVO;
import com.apps.esb.service.bss.bizengine.CheckOfferingRuleHandler;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.CustomizeBizHanlder;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.vo.macre.offerings.OfferCategoryVO;
import com.apps.esb.service.bss.vo.macre.offerings.OfferingVO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.db.DBManager;
import com.unieap.db.EntityRowMapper;
import com.unieap.tools.JSONUtils;

@Service("queryAvailableOfferings")
public class QueryOfferings implements BizHandler {

	public QueryOfferings() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		String categoryId = "";
		if (!StringUtils.isEmpty(requestInfo.getRequestBody().getExtParameters())) {
			JSONObject extParametersJson = new JSONObject(requestInfo.getRequestBody().getExtParameters());
			if (extParametersJson.has("categoryId")) {
				categoryId = extParametersJson.getString("categoryId");
			} else {
				throw new Exception("categoryId is null");
			}
		}
		BizHandler appHandler = (BizHandler) ServiceUtils.getBean(handler.get("appHandlerName"));
		ProcessResult queryAvailableOfferingsProcessResult = appHandler.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(queryAvailableOfferingsProcessResult.getResultCode())) {
			return queryAvailableOfferingsProcessResult;
		}
		QueryAvailableOfferingVO queryAvailableOfferingVO = (QueryAvailableOfferingVO) queryAvailableOfferingsProcessResult
				.getVo();
		ProcessResult result = new ProcessResult();
		result.setServiceNumber(requestInfo.getRequestBody().getServiceNumber());
		result.setResultCode(UnieapConstants.C0);
		result.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
		OfferCategoryVO offerCategoryVO = getOfferingsCategory(categoryId);

		if ("ct_me".equals(offerCategoryVO.getCategoryType())) {
			CheckOfferingRuleHandler checkOfferingRuleHandler = (CheckOfferingRuleHandler) ServiceUtils
					.getBean("checkOfferingRuleHandler");
			checkOfferingRuleHandler.availableOfferings("P", queryAvailableOfferingVO.getOfferingMap(),
					offerCategoryVO);
		} else if ("ct_all".equals(offerCategoryVO.getCategoryType())) {
			CheckOfferingRuleHandler checkOfferingRuleHandler = (CheckOfferingRuleHandler) ServiceUtils
					.getBean("checkOfferingRuleHandler");
			checkOfferingRuleHandler.availableOfferings("S", queryAvailableOfferingVO.getOfferingMap(),
					offerCategoryVO);
		} else {
			CheckOfferingRuleHandler checkOfferingRuleHandler = (CheckOfferingRuleHandler) ServiceUtils
					.getBean("checkOfferingRuleHandler");
			checkOfferingRuleHandler.availableOfferings("S", queryAvailableOfferingVO.getOfferingMap(),
					offerCategoryVO);
		}
		if (handler != null) {
			String custHandlerName = handler.get("custHandlerName");
			if (!StringUtils.isEmpty(custHandlerName)) {
				CustomizeBizHanlder custHandler = (CustomizeBizHanlder) ServiceUtils.getBean(custHandlerName);
				List<Object> originalVOs = new ArrayList<Object>();
				originalVOs.add(offerCategoryVO);
				custHandler.process(requestInfo, handler, extParameters, offerCategoryVO, originalVOs);
			}
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
