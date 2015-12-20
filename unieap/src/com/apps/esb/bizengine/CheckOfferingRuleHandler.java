package com.apps.esb.bizengine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import com.apps.esb.service.bss.app.crm.vo.getavailableprimaryoffering.OfferingInfoVO;
import com.apps.esb.service.bss.app.vo.OfferCategoryVO;
import com.apps.esb.service.bss.app.vo.OfferingVO;
import com.unieap.BaseBO;
import com.unieap.handler.ConfigHandler;

@Service("checkOfferingRuleHandler")
public class CheckOfferingRuleHandler extends BaseBO implements ConfigHandler {

	@Override
	public void dealNode(Node node, ServletContext servlet, String appName) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void deal(String parameters) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean reload() {
		// TODO Auto-generated method stub
		return false;
	}

	public void availableOfferings(String offeringType, Map<String, OfferingInfoVO> offeringInfoMap,
			OfferCategoryVO offerCategoryVO) {
		if (offerCategoryVO != null && offerCategoryVO.getOfferings() != null
				&& offerCategoryVO.getOfferings().size() > 0) {
			List<OfferingVO> offerings = offerCategoryVO.getOfferings();
			List<OfferingVO> newOfferings = new ArrayList<OfferingVO>();
			for (int i = 0; i < offerings.size(); i++) {
				OfferingVO offeringVO = offerings.get(i);
				if (offeringInfoMap.containsKey(offeringVO.getOfferingId())) {
					newOfferings.add(offeringVO);
				}
			}
			offerCategoryVO.setOfferings(newOfferings);
		} else {
			List<OfferingVO> newOfferings = new ArrayList<OfferingVO>();
			offerCategoryVO.setOfferings(newOfferings);
			Iterator<?> iter = offeringInfoMap.entrySet().iterator();
			int i = 0;
			while (iter.hasNext()) {
				i++;
				if(i>5){
					break;
				}
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String) entry.getKey();
				OfferingInfoVO offeringInfoVO = offeringInfoMap.get(key);
				OfferingVO offeringVO = new OfferingVO();
				newOfferings.add(offeringVO);
				offeringVO.setEffectiveDate(offeringInfoVO.getEffectiveTime());
				offeringVO.setEffectiveType("Immediate");
				offeringVO.setExpiryDate(offeringInfoVO.getExpiredTime());
				offeringVO.setFeeAmount(offeringInfoVO.getMonthlyFee());
				offeringVO.setOfferingCode(offeringInfoVO.getOfferingCode());
				offeringVO.setOfferingDesc(offeringInfoVO.getOfferingName());
				offeringVO.setOfferingId(offeringInfoVO.getOfferingId());
				offeringVO.setOfferingName(offeringInfoVO.getOfferingName());
				offeringVO.setOfferingType(offeringType);
			}
		}
	}

}
