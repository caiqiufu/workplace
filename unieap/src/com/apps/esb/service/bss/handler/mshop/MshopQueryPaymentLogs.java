package com.apps.esb.service.bss.handler.mshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.app.vo.account.payment.PaymentVO;
import com.apps.esb.service.bss.app.vo.account.payment.QueryPaymentLogsVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.CustomizeBizHanlder;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.vo.mshop.payment.MshopPaymentVO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;


@Service("mshopQueryPaymentLogs")
public class MshopQueryPaymentLogs implements BizHandler{

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
		QueryPaymentLogsVO queryPaymentLogsVO = (QueryPaymentLogsVO) processResult.getVo();
		List<MshopPaymentVO> payments = getPaymentLogList(queryPaymentLogsVO);
		
		if(handler!=null){
			String custHandlerName = handler.get("custHandlerName");
			if(!StringUtils.isEmpty(custHandlerName)){
				CustomizeBizHanlder custHandler = (CustomizeBizHanlder)ServiceUtils.getBean(custHandlerName);
				List<Object> originalVOs = new ArrayList<Object>();
				originalVOs.add(queryPaymentLogsVO);
				custHandler.process(requestInfo, handler, extParameters, payments,originalVOs);
			}
		}
		
		JSONObject jsonResult = new JSONObject();
		processResult.setResultCode(UnieapConstants.C0);
		processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
		jsonResult.put("paymentList", JSONUtils.collectionToJson(payments));
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
	private List<MshopPaymentVO> getPaymentLogList(QueryPaymentLogsVO queryPaymentLogsVO) {
		List<PaymentVO> paymentList = queryPaymentLogsVO.getPaymentList();
		if (paymentList != null && paymentList.size() > 0) {
			List<MshopPaymentVO> payments = new ArrayList<MshopPaymentVO>();
			for (int i = 0; i < paymentList.size(); i++) {
				PaymentVO paymentVO = paymentList.get(i);
				MshopPaymentVO mshopPaymentVO = new MshopPaymentVO();
				payments.add(mshopPaymentVO);
				mshopPaymentVO.setAmount(paymentVO.getAmount());
				mshopPaymentVO.setCurrentAmount(paymentVO.getCurrentAmount());
				mshopPaymentVO.setDate(paymentVO.getPaymentTime());
				mshopPaymentVO.setOriginalAmount(paymentVO.getOriginalAmount());
				mshopPaymentVO.setPaymentMethod(paymentVO.getPaymentMethodDesc());
				mshopPaymentVO.setTransactionId(paymentVO.getTransactionId());
				mshopPaymentVO.setEffectiveTime(paymentVO.getEffectiveTime());
				mshopPaymentVO.setExpiryTime(paymentVO.getExpiryTime());
				mshopPaymentVO.setExtAttris(paymentVO.getExtAttris());
			}
			return payments;
		}
		return null;
	}
}
