package com.apps.esb.service.bss.customize.smart.handler;

import java.util.List;
import java.util.Map;

import org.apache.axis.utils.StringUtils;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.vo.subscriber.cdr.CdrInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.cdr.QueryCdrVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.handler.CustomizeBizHanlder;
import com.apps.esb.service.bss.vo.macre.cdr.CdrDetailVO;
import com.unieap.UnieapConstants;

@Service("queryMyCdr_1")
public class QueryMyCdr implements CustomizeBizHanlder{

	@Override
	public Object process(RequestInfo requestInfo, Map<String, String> handler, Map<String, Object> extParameters,
			Object viewVO, List<Object> originalVOs) throws Exception {
		QueryCdrVO queryCdrVO = (QueryCdrVO)originalVOs.get(0);
		List<CdrDetailVO> cdrs = (List<CdrDetailVO>)viewVO;
		cdrs.clear();
		List<CdrInfoVO> cdrInfoList = queryCdrVO.getCdrInfoList();
		if (cdrInfoList != null && cdrInfoList.size() > 0){
			for (int i = 0; i < cdrInfoList.size(); i++){
				CdrInfoVO cdrInfoVO = cdrInfoList.get(i);
				CdrDetailVO cdrDetailVO = new CdrDetailVO();
				cdrs.add(cdrDetailVO);
				cdrDetailVO.setActualChargeAmt(BssServiceUtils.moneyFormat(cdrInfoVO.getActualChargeAmt()));
				
				if(StringUtils.isEmpty(cdrInfoVO.getActualVolume())){
					cdrDetailVO.setActualVolume("--");
				}else{
					if("1106".equals(cdrInfoVO.getMeasureUnit())){
						cdrDetailVO.setActualVolume(BssServiceUtils.dataBToKBFormat(cdrInfoVO.getActualVolume())+"(KB)");
						cdrDetailVO.setOtherNumber(requestInfo.getRequestBody().getServiceNumber());
					}else if("1003".equals(cdrInfoVO.getMeasureUnit())){
						cdrDetailVO.setActualVolume(cdrInfoVO.getActualVolume()+"(s)");
						cdrDetailVO.setOtherNumber(cdrInfoVO.getOtherNumber());
					}else if("1101".equals(cdrInfoVO.getMeasureUnit())){
						cdrDetailVO.setActualVolume(cdrInfoVO.getActualVolume()+"(item)");
						cdrDetailVO.setOtherNumber(cdrInfoVO.getOtherNumber());
					}else if("1006".equals(cdrInfoVO.getMeasureUnit())){
						cdrDetailVO.setActualVolume(cdrInfoVO.getActualVolume()+"(times)");
						cdrDetailVO.setOtherNumber(requestInfo.getRequestBody().getServiceNumber());
					}else{
						cdrDetailVO.setActualVolume(cdrInfoVO.getActualVolume());
						cdrDetailVO.setOtherNumber(cdrInfoVO.getOtherNumber());
					}
				}
				cdrDetailVO.setEndTime(BssServiceUtils.dateFormat(cdrInfoVO.getEndTime()));
				if(StringUtils.isEmpty(cdrInfoVO.getFlowType())){
					cdrInfoVO.setFlowType("--");
				}
				cdrDetailVO.setFlowType(UnieapConstants.getDicName("cdrFlowType", cdrInfoVO.getFlowType()));
				if("7".equals(cdrInfoVO.getServiceCategory())){
					cdrDetailVO.setOtherNumber(requestInfo.getRequestBody().getServiceNumber());
				}
				cdrDetailVO.setServiceTypeName(cdrInfoVO.getServiceTypeDesc());
				cdrDetailVO.setStartTime(BssServiceUtils.dateFormat(cdrInfoVO.getStartTime()));
			}
		}	
		return cdrs;
	}

}
