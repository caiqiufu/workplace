package com.apps.esb.service.bss.handler.query;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.cbs.handler.QueryFreeUnits;
import com.apps.esb.service.bss.app.cbs.vo.queryfreeunit.FreeUnitItemVO;
import com.apps.esb.service.bss.app.cbs.vo.queryfreeunit.QueryFreeUnitVO;
import com.apps.esb.service.bss.app.vo.DataBalanceVO;
import com.apps.esb.service.bss.app.vo.MyBalanceDetailVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;

@Service("queryDataBalance")
public class QueryDataBalance extends SoapMessageHandler implements BizHandler {

	public QueryDataBalance() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo,String parameters,Map<String,Object> extParameters)
			throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		String isdebug =SYSConfig.getConfig().get("mcare.app.extaction.debug");
		if(UnieapConstants.YES.equals(isdebug)){
			requestInfo.getRequestBody().setServiceNumber("93268659");
		}
		QueryFreeUnits queryFreeUnits = (QueryFreeUnits) ServiceUtils.getBean("queryFreeUnits");
		ProcessResult processResult = queryFreeUnits.process(requestInfo,parameters,extParameters);
		if(!UnieapConstants.C0.equals(processResult.getResultCode())){
			return processResult;
		}
		QueryFreeUnitVO queryFreeUnitVO = (QueryFreeUnitVO) processResult.getVo();
		DataBalanceVO dataBalanceVO = getDataBalanceVO(queryFreeUnitVO);
		JSONObject dataBalanceJson = JSONUtils.convertBean2JSON(dataBalanceVO);
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("dataBalance", dataBalanceJson);
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
		return null;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {

		return null;
	}

	public DataBalanceVO getDataBalanceVO(QueryFreeUnitVO queryFreeUnitVO) {
		DataBalanceVO dataBalanceVO = new DataBalanceVO();
		List<FreeUnitItemVO> freeUnitItemVOList = queryFreeUnitVO.getFreeUnitItemVOList();
		if (freeUnitItemVOList != null && freeUnitItemVOList.size() > 0) {
			double totalAmount = 0, remaningAmount = 0, usageAmount = 0, dailyUsageAmount = 0, websiteAmount = 0,
					songAmount = 0, vedioAmount = 0, chatAmount = 0, readAmount = 0;
			int emailAmount = 0;
			List<MyBalanceDetailVO> balanceDetails = new ArrayList<MyBalanceDetailVO>();
			dataBalanceVO.setBalanceDetails(balanceDetails);
			//int index = 0;
			for (int i = 0; i < freeUnitItemVOList.size(); i++) {
				FreeUnitItemVO freeUnitItemVO = freeUnitItemVOList.get(i);
				if ("1108".equals(freeUnitItemVO.getMeasureUnit())) {
					if (!StringUtils.isEmpty(freeUnitItemVO.getTotalInitialAmount())) {
						totalAmount =  Double.parseDouble(freeUnitItemVO.getTotalInitialAmount());
					}
					if (!StringUtils.isEmpty(freeUnitItemVO.getTotalUnusedAmount())) {
						remaningAmount = Double.parseDouble(freeUnitItemVO.getTotalUnusedAmount());
					}
					MyBalanceDetailVO balanceDetailVO = new MyBalanceDetailVO();
					balanceDetails.add(balanceDetailVO);
					//balanceDetailVO.setIndex(index);
					//index++;
					balanceDetailVO.setOfferingCode(freeUnitItemVO.getFreeUnitType());
					balanceDetailVO.setOfferingName(freeUnitItemVO.getFreeUnitTypeName());
					balanceDetailVO.setOfferingDesc(freeUnitItemVO.getFreeUnitTypeName());
					balanceDetailVO.setTotalAmount(freeUnitItemVO.getTotalInitialAmount());
					balanceDetailVO.setRemaningAmount(freeUnitItemVO.getTotalUnusedAmount());
					double cusageAmount = Double.parseDouble(freeUnitItemVO.getTotalInitialAmount())
							- Double.parseDouble(freeUnitItemVO.getTotalUnusedAmount());
					balanceDetailVO.setUsageAmount(Double.toString(cusageAmount));
				}
			}
			dataBalanceVO.setTotalAmount(BssServiceUtils.dataMBFormat(totalAmount));
			dataBalanceVO.setRemaningAmount(BssServiceUtils.dataMBFormat(remaningAmount));
			usageAmount = totalAmount - remaningAmount;
			dataBalanceVO.setUsageAmount(BssServiceUtils.dataMBFormat(usageAmount));
			DecimalFormat df = new DecimalFormat("0.0");
			dailyUsageAmount = remaningAmount
					/ Double.parseDouble(SYSConfig.getConfig().get("mcare.display.data.dailyusate"));
			websiteAmount = remaningAmount / Double.parseDouble(SYSConfig.getConfig().get("mcare.display.data.website"));
			songAmount = remaningAmount / Double.parseDouble(SYSConfig.getConfig().get("mcare.display.data.song"));
			vedioAmount = remaningAmount / Double.parseDouble(SYSConfig.getConfig().get("mcare.display.data.vedio"));
			chatAmount = remaningAmount / Double.parseDouble(SYSConfig.getConfig().get("mcare.display.data.chat"));
			readAmount = remaningAmount / Double.parseDouble(SYSConfig.getConfig().get("mcare.display.data.read"));
			emailAmount = (int)(remaningAmount / Double.parseDouble(SYSConfig.getConfig().get("mcare.display.data.email")));
			dataBalanceVO.setDailyUsageAmount(df.format(dailyUsageAmount));
			dataBalanceVO.setWebsiteAmount(df.format(websiteAmount));
			dataBalanceVO.setSongAmount(df.format(songAmount));
			dataBalanceVO.setVedioAmount(df.format(vedioAmount));
			dataBalanceVO.setChatAmount(df.format(chatAmount));
			dataBalanceVO.setReadAmount(df.format(readAmount));
			dataBalanceVO.setEmailAmount(Integer.toString(emailAmount));
		} else {
			dataBalanceVO.setTotalAmount("0");
			dataBalanceVO.setRemaningAmount("0");
			dataBalanceVO.setUsageAmount("0");
			dataBalanceVO.setDailyUsageAmount("0");
			dataBalanceVO.setWebsiteAmount("0");
			dataBalanceVO.setSongAmount("0");
			dataBalanceVO.setVedioAmount("0");
			dataBalanceVO.setChatAmount("0");
			dataBalanceVO.setReadAmount("0");
			dataBalanceVO.setEmailAmount("0");
		}
		return dataBalanceVO;
	}
}
