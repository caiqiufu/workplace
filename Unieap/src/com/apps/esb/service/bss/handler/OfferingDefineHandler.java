package com.apps.esb.service.bss.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import com.apps.esb.service.bss.app.vo.OfferCategoryVO;
import com.apps.esb.service.bss.app.vo.OfferingVO;
import com.unieap.BaseBO;
import com.unieap.CacheMgt;
import com.unieap.db.DBManager;
import com.unieap.handler.ConfigHandler;

@Service("offeringDefineHandler")
public class OfferingDefineHandler extends BaseBO implements ConfigHandler {

	@Override
	public void dealNode(Node node, ServletContext servlet, String appName) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void deal(String parameters) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ff.seq, offering_id as offeringId, offering_code as offeringCode, ");
		sql.append(" offering_name as offeringName, offering_type as offeringType, ");
		sql.append(" offering_desc as offeringDesc,effective_type as effectiveType,");
		sql.append(" fee_amount as feeAmount, category_type as categoryType, picture_url as pictureUrl, ");
		sql.append(" categore_name as categoreName,category_desc as categoryDesc, ");
		sql.append(" price_desc as priceDesc FROM app_offering ff , app_offering_category fc ");
		sql.append(" where ff.category_id = fc.id and ff.active_flag= 'Y' and fc.active_flag='Y'");
		List<Map<String, Object>> datas = DBManager.getJT(null).queryForList(sql.toString());
		Map<String, OfferingVO> offeringDefines = new HashMap<String, OfferingVO>();
		if (datas != null && datas.size() > 0) {
			for (int i = 0; i < datas.size(); i++) {
				OfferingVO offeringVO = new OfferingVO();
				OfferCategoryVO offerCategory = new OfferCategoryVO();
				offeringVO.setOfferCategory(offerCategory);
				Map<String, Object> data = (Map<String, Object>) datas.get(i);
				offeringVO.setSeq(data.get("seq").toString());
				offeringVO.setOfferingId(data.get("offeringId").toString());
				offeringVO.setOfferingCode(data.get("offeringCode") == null ? "" : data.get("offeringCode").toString());
				offeringVO.setOfferingName(data.get("offeringName") == null ? "" : data.get("offeringName").toString());
				offeringVO.setOfferingType(data.get("offeringType") == null ? "" : data.get("offeringType").toString());
				offeringVO.setOfferingDesc(data.get("offeringDesc") == null ? "" : data.get("offeringDesc").toString());
				offeringVO.setEffectiveType(
						data.get("effectiveType") == null ? "" : data.get("effectiveType").toString());
				offeringVO.setFeeAmount(data.get("feeAmount") == null ? "" : data.get("feeAmount").toString());
				offerCategory
						.setCategoryType(data.get("categoryType") == null ? "" : data.get("categoryType").toString());
				offerCategory.setPictureUrl(data.get("pictureUrl") == null ? "" : data.get("pictureUrl").toString());
				offerCategory
						.setCategoreName(data.get("categoreName") == null ? "" : data.get("categoreName").toString());
				offerCategory
						.setCategoryDesc(data.get("categoryDesc") == null ? "" : data.get("categoryDesc").toString());
				offerCategory.setPriceDesc(data.get("priceDesc") == null ? "" : data.get("priceDesc").toString());
				offeringDefines.put(offeringVO.getOfferingId(), offeringVO);
			}
		}
		CacheMgt.getCacheData().put("offeringDefine", offeringDefines);
	}

	@Override
	public boolean reload() {
		// TODO Auto-generated method stub
		return true;
	}

}
