package com.apps.esb.service.bss.handler.mcare;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.vo.subscriber.freeresource.FreeResourceVO;
import com.apps.esb.service.bss.app.vo.subscriber.freeresource.QueryFreeResourceVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.CustomizeBizHanlder;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.vo.macre.allbalance.MyBalanceDetailVO;
import com.apps.esb.service.bss.vo.macre.databalance.DataBalanceVO;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;

@Service("queryDataBalance")
public class QueryDataBalance implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		BizHandler appHandler = (BizHandler) ServiceUtils.getBeanByTenantId(handler.get("appHandlerName"));
		ProcessResult processResult = appHandler.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(processResult.getResultCode())) {
			return processResult;
		}
		QueryFreeResourceVO queryFreeResourceVO = (QueryFreeResourceVO) processResult.getVo();
		DataBalanceVO dataBalanceVO = getDataBalanceVO(queryFreeResourceVO);
		if (handler != null) {
			String custHandlerName = handler.get("custHandlerName");
			if (!StringUtils.isEmpty(custHandlerName)) {
				CustomizeBizHanlder custHandler = (CustomizeBizHanlder) ServiceUtils.getBean(custHandlerName);
				List<Object> originalVOs = new ArrayList<Object>();
				originalVOs.add(queryFreeResourceVO);
				custHandler.process(requestInfo, handler, extParameters, dataBalanceVO, originalVOs);
			}
		}
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

	public DataBalanceVO getDataBalanceVO(QueryFreeResourceVO queryFreeResourceVO) {
		DataBalanceVO dataBalanceVO = new DataBalanceVO();
		List<FreeResourceVO> freeResourceList = queryFreeResourceVO.getFreeResourceList();
		if (freeResourceList != null && freeResourceList.size() > 0) {
			double totalAmount = 0, remaningAmount = 0, usageAmount = 0, dailyUsageAmount = 0, websiteAmount = 0,
					songAmount = 0, vedioAmount = 0, chatAmount = 0, readAmount = 0;
			int emailAmount = 0;
			List<MyBalanceDetailVO> balanceDetails = new ArrayList<MyBalanceDetailVO>();
			dataBalanceVO.setBalanceDetails(balanceDetails);
			for (int i = 0; i < freeResourceList.size(); i++) {
				FreeResourceVO freeResourceVO = freeResourceList.get(i);
				if (!StringUtils.isEmpty(freeResourceVO.getTotalInitialAmount())) {
					totalAmount = totalAmount + Double.parseDouble(freeResourceVO.getTotalInitialAmount());
				}
				if (!StringUtils.isEmpty(freeResourceVO.getTotalUnusedAmount())) {
					remaningAmount = remaningAmount + Double.parseDouble(freeResourceVO.getTotalUnusedAmount());
				}
				MyBalanceDetailVO balanceDetailVO = new MyBalanceDetailVO();
				balanceDetails.add(balanceDetailVO);
				balanceDetailVO.setOfferingCode(freeResourceVO.getFreeResourceType());
				balanceDetailVO.setOfferingName(freeResourceVO.getFreeResourceTypeDesc());
				balanceDetailVO.setOfferingDesc(freeResourceVO.getFreeResourceTypeDesc());
				balanceDetailVO.setTotalAmount(freeResourceVO.getTotalInitialAmount());
				balanceDetailVO.setRemaningAmount(freeResourceVO.getTotalUnusedAmount());
				double cusageAmount = Double.parseDouble(freeResourceVO.getTotalInitialAmount())
						- Double.parseDouble(freeResourceVO.getTotalUnusedAmount());
				balanceDetailVO.setUsageAmount(Double.toString(cusageAmount));
			}
			dataBalanceVO.setTotalAmount(BssServiceUtils.dataMBFormat(totalAmount));
			dataBalanceVO.setRemaningAmount(BssServiceUtils.dataMBFormat(remaningAmount));
			usageAmount = totalAmount - remaningAmount;
			dataBalanceVO.setUsageAmount(BssServiceUtils.dataMBFormat(usageAmount));
			DecimalFormat df = new DecimalFormat("0.0");
			dailyUsageAmount = remaningAmount
					/ Double.parseDouble(SYSConfig.getConfig().get("mcare.display.data.dailyusate"));
			websiteAmount = remaningAmount
					/ Double.parseDouble(SYSConfig.getConfig().get("mcare.display.data.website"));
			songAmount = remaningAmount / Double.parseDouble(SYSConfig.getConfig().get("mcare.display.data.song"));
			vedioAmount = remaningAmount / Double.parseDouble(SYSConfig.getConfig().get("mcare.display.data.vedio"));
			chatAmount = remaningAmount / Double.parseDouble(SYSConfig.getConfig().get("mcare.display.data.chat"));
			readAmount = remaningAmount / Double.parseDouble(SYSConfig.getConfig().get("mcare.display.data.read"));
			emailAmount = (int) (remaningAmount
					/ Double.parseDouble(SYSConfig.getConfig().get("mcare.display.data.email")));
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
