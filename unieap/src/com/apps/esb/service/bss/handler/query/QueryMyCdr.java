package com.apps.esb.service.bss.handler.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.cbs.handler.QueryCDR;
import com.apps.esb.service.bss.app.cbs.vo.queryCdr.CdrInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.queryCdr.QueryCdrVO;
import com.apps.esb.service.bss.app.cbs.vo.xchangelog.XchangeInfoVO;
import com.apps.esb.service.bss.app.vo.CdrDetailVO;
import com.apps.esb.service.bss.app.vo.XchangeLogVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;

@Service("queryMyCdr")
public class QueryMyCdr extends SoapMessageHandler implements BizHandler {

	public QueryMyCdr() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		String isdebug = SYSConfig.getConfig().get("mcare.app.extaction.debug");
		if (UnieapConstants.YES.equals(isdebug)) {
			requestInfo.getRequestBody().setServiceNumber("93268659");
		}
		JSONObject customerObj = new JSONObject("{queryType:D}");
		JSONObject newExtParameters = BssServiceUtils
				.modifyExtParameters(requestInfo.getRequestBody().getExtParameters(), customerObj);
		requestInfo.getRequestBody().setExtParameters(newExtParameters.toString());

		QueryCDR queryCDR = (QueryCDR) ServiceUtils.getBean("queryCDR");
		ProcessResult processResult = queryCDR.process(requestInfo, parameters, extParameters);
		if (!UnieapConstants.C0.equals(processResult.getResultCode())) {
			return processResult;
		}

		QueryCdrVO queryCdrVO = (QueryCdrVO) processResult.getVo();
		List<CdrInfoVO> cdrInfoList = queryCdrVO.getCdrInfoList();
		if (cdrInfoList != null && cdrInfoList.size() > 0) {
			List<CdrDetailVO> cdrs = new ArrayList<CdrDetailVO>();
			for (int i = 0; i < cdrInfoList.size(); i++) {
				CdrInfoVO cdrInfoVO = cdrInfoList.get(i);
				CdrDetailVO cdrDetailVO = new CdrDetailVO();
				cdrs.add(cdrDetailVO);
				cdrDetailVO.setActualChargeAmt(BssServiceUtils.moneyFormat(cdrInfoVO.getActualChargeAmt()));
				
				if(StringUtils.isEmpty(cdrInfoVO.getActualVolume())){
					cdrDetailVO.setActualVolume("--");
				}else{
					if("1106".equals(cdrInfoVO.getMeasureUnit())){
						cdrDetailVO.setActualVolume(BssServiceUtils.dataBToKBFormat(cdrInfoVO.getActualVolume())+"(KB)");
					}else if("1003".equals(cdrInfoVO.getMeasureUnit())){
						cdrDetailVO.setActualVolume(cdrInfoVO.getActualVolume()+"(s)");
					}else if("1101".equals(cdrInfoVO.getMeasureUnit())){
						cdrDetailVO.setActualVolume(cdrInfoVO.getActualVolume()+"(item)");
					}else if("1006".equals(cdrInfoVO.getMeasureUnit())){
						cdrDetailVO.setActualVolume(cdrInfoVO.getActualVolume()+"(times)");
					}else{
						cdrDetailVO.setActualVolume(cdrInfoVO.getActualVolume());
					}
				}
				cdrDetailVO.setEndTime(BssServiceUtils.dateFormat(cdrInfoVO.getEndTime()));
				if(StringUtils.isEmpty(cdrInfoVO.getFlowType())){
					cdrInfoVO.setFlowType("--");
				}
				cdrDetailVO.setFlowType(UnieapConstants.getDicName("cdrFlowType", cdrInfoVO.getFlowType()));
				cdrDetailVO.setOtherNumber(cdrInfoVO.getOtherNumber());
				cdrDetailVO.setServiceTypeName(cdrInfoVO.getServiceTypeName());
				cdrDetailVO.setStartTime(BssServiceUtils.dateFormat(cdrInfoVO.getStartTime()));
			}
			JSONArray cdrsJson = JSONUtils.getJSONArray(cdrs);
			JSONObject jsonResult = new JSONObject();
			jsonResult.put("cdrList", cdrsJson);
			jsonResult.put("totalCDRNum", queryCdrVO.getTotalCDRNum());
			jsonResult.put("beginRowNum", queryCdrVO.getBeginRowNum());
			jsonResult.put("fetchRowNum", queryCdrVO.getFetchRowNum());
			processResult.setExtParameters(jsonResult.toString());
		}
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

}
