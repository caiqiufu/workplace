package com.apps.esb.service.bss.handler.mshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.app.vo.account.invoice.InvoiceVO;
import com.apps.esb.service.bss.app.vo.account.invoice.QueryInvoiceVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.CustomizeBizHanlder;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.vo.mshop.invoice.MshopInvoiceVO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;

@Service("mshopQueryInvoice")
public class MshopQueryInvoice implements BizHandler {

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
		QueryInvoiceVO queryInvoiceVO = (QueryInvoiceVO) processResult.getVo();
		List<MshopInvoiceVO> invoices = getInvoiceList(queryInvoiceVO);

		if(handler!=null){
			String custHandlerName = handler.get("custHandlerName");
			if(!StringUtils.isEmpty(custHandlerName)){
				CustomizeBizHanlder custHandler = (CustomizeBizHanlder)ServiceUtils.getBean(custHandlerName);
				List<Object> originalVOs = new ArrayList<Object>();
				originalVOs.add(queryInvoiceVO);
				custHandler.process(requestInfo, handler, extParameters, invoices,originalVOs);
			}
		}
		
		JSONObject jsonResult = new JSONObject();
		processResult.setResultCode(UnieapConstants.C0);
		processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
		jsonResult.put("invoiceList", JSONUtils.collectionToJson(invoices));
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

	private List<MshopInvoiceVO> getInvoiceList(QueryInvoiceVO queryInvoiceVO) {
		List<InvoiceVO> invoiceList = queryInvoiceVO.getInvoiceList();
		if (invoiceList != null && invoiceList.size() > 0) {
			List<MshopInvoiceVO> invoices = new ArrayList<MshopInvoiceVO>();
			for (int i = 0; i < invoiceList.size(); i++) {
				InvoiceVO invoiceVO = invoiceList.get(i);
				MshopInvoiceVO mshopInvoiceVO = new MshopInvoiceVO();
				invoices.add(mshopInvoiceVO);
				mshopInvoiceVO.setAccountId(invoiceVO.getAccountId());
				mshopInvoiceVO.setBillCycleBeginTime(invoiceVO.getBillCycleBeginTime());
				mshopInvoiceVO.setBillCycleEndTime(invoiceVO.getBillCycleEndTime());
				mshopInvoiceVO.setBillCycleId(invoiceVO.getBillCycleId());
				mshopInvoiceVO.setCloseAmount(invoiceVO.getCloseAmount());
				mshopInvoiceVO.setDisputeAmount(invoiceVO.getDisputeAmount());
				mshopInvoiceVO.setDueDate(invoiceVO.getDueDate());
				mshopInvoiceVO.setInvoiceDate(invoiceVO.getInvoiceDate());
				mshopInvoiceVO.setInvoiceId(invoiceVO.getInvoiceId());
				mshopInvoiceVO.setInvoiceNo(invoiceVO.getInvoiceNo());
				mshopInvoiceVO.setOpenAmount(invoiceVO.getOpenAmount());
				mshopInvoiceVO.setStatus(invoiceVO.getStatus());
				mshopInvoiceVO.setStatusDesc(invoiceVO.getStatusDesc());
				
				mshopInvoiceVO.setEffectiveTime(invoiceVO.getEffectiveTime());
				mshopInvoiceVO.setExpiryTime(invoiceVO.getExpiryTime());
				mshopInvoiceVO.setExtAttris(invoiceVO.getExtAttris());
			}
			return invoices;
		}
		return null;
	}
}
