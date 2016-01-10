package com.apps.esb.service.bss.handler.query;

import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.apps.esb.service.bss.vo.offerings.OfferCategoryVO;
import com.unieap.UnieapConstants;
import com.unieap.db.DBManager;
import com.unieap.db.EntityRowMapper;
import com.unieap.tools.JSONUtils;

@Service("queryOfferingCategory")
public class QueryOfferingCategory extends SoapMessageHandler implements BizHandler {

	public QueryOfferingCategory() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
		String categoryType = "ct_all";
		if (!StringUtils.isEmpty(requestInfo.getRequestBody().getExtParameters())) {
			JSONObject extParametersJson = new JSONObject(requestInfo.getRequestBody().getExtParameters());
			if (extParametersJson.has("categoryType")) {
				categoryType = extParametersJson.getString("categoryType");
			} else {
				throw new Exception("categoryType is null");
			}
		}
		ProcessResult result = new ProcessResult();
		result.setServiceNumber(requestInfo.getRequestBody().getServiceNumber());
		result.setResultCode(UnieapConstants.C0);
		result.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
		List<?> datas = getOfferingCategoryList(categoryType);
		JSONArray jsonArray = JSONUtils.getJSONArray(datas);
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("categorys", jsonArray);
		result.setExtParameters(jsonResult.toString());
		return result;
	}

	private List<?> getOfferingCategoryList(String categoryType) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT oc.id as categoryId,oc.category_type as categoryType, fa.url as pictureUrl,");
		sql.append("oc.categore_name as categoreName, oc.category_desc as categoryDesc, oc.price_desc as priceDesc ");
		sql.append("FROM unieap.app_offering_category oc,unieap.file_archive fa ");
		sql.append("where oc.id = fa.ext_key and  oc.active_flag = ? ");
		if (!categoryType.equals("ct_all") && !categoryType.equals("ct_service") && !categoryType.equals("ct_other")) {
			String types = getcategoryTypes(categoryType);
			sql.append(" and category_type in (").append(types).append(")");
		}
		sql.append(" order by categore_name ");
		List<?> datas = DBManager.getJT(null).query(sql.toString(), new Object[] { UnieapConstants.YES },
				new EntityRowMapper(OfferCategoryVO.class));
		return datas;
	}

	private String getcategoryTypes(String categoryType) {
		String[] categoryTypes = categoryType.split(",");
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < categoryTypes.length; i++) {
			s.append("'").append(categoryTypes[i]).append("',");
		}
		if (s.toString().contains(",")) {
			return s.substring(0, s.length() - 1);
		} else {
			return s.toString();
		}
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
