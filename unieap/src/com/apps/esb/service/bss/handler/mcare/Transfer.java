package com.apps.esb.service.bss.handler.mcare;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.app.vo.account.transfer.TransferBalanceVO;
import com.apps.esb.service.bss.app.vo.account.transfer.TransferorVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.CustomizeBizHanlder;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.vo.macre.transferbalance.BalanceChgInfoVO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;

@Service("transfer")
public class Transfer implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getExtParameters())) {
			throw new Exception("transfer account is null");
		}
		JSONObject json = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if (!json.has("transferorNumber")) {
			throw new Exception("transferorNumber is null");
		}
		if (!json.has("transfereeNumber")) {
			throw new Exception("transfereeNumber is null");
		}
		if (!json.has("transferAmount")) {
			throw new Exception("transferAmount is null");
		}

		BizHandler appHandler = (BizHandler) ServiceUtils.getBeanByTenantId(handler.get("appHandlerName"));
		ProcessResult processResult = appHandler.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(processResult.getResultCode())) {
			return processResult;
		}
		
		TransferBalanceVO transferBalanceVO = (TransferBalanceVO) processResult.getVo();
		com.apps.esb.service.bss.vo.macre.transferbalance.TransferBalanceVO myTransferBalanceVO = new com.apps.esb.service.bss.vo.macre.transferbalance.TransferBalanceVO();
		TransferorVO transferorVO = transferBalanceVO.getTransferorVO();
		myTransferBalanceVO.setHandingChargeAmt(transferorVO.getHandlingChargeAmt());
		BalanceChgInfoVO balanceChgInfoVO = new BalanceChgInfoVO();
		myTransferBalanceVO.setBalanceChgInfoVO(balanceChgInfoVO);
		List<com.apps.esb.service.bss.app.vo.subscriber.chginfo.BalanceChgInfoVO> balanceChgInfoList = transferorVO.getBalanceChgInfoList();
		if (balanceChgInfoList != null && balanceChgInfoList.size() > 0) {
			for (int i = 0; i < balanceChgInfoList.size(); i++) {
				com.apps.esb.service.bss.app.vo.subscriber.chginfo.BalanceChgInfoVO myBalanceChgInfoVO = balanceChgInfoList.get(i);
				balanceChgInfoVO.setBalanceId(myBalanceChgInfoVO.getBalanceID());
				balanceChgInfoVO.setBalanceType(myBalanceChgInfoVO.getBalanceType());
				balanceChgInfoVO.setBalanceTypeName(myBalanceChgInfoVO.getBalanceTypeDesc());
				balanceChgInfoVO.setNewBalanceAmt(myBalanceChgInfoVO.getOldBalanceAmount());
				balanceChgInfoVO.setOldBalanceAmt(myBalanceChgInfoVO.getNewBalanceAmount());
			}
		}
		if(handler!=null){
			String custHandlerName = handler.get("custHandlerName");
			if(!StringUtils.isEmpty(custHandlerName)){
				CustomizeBizHanlder custHandler = (CustomizeBizHanlder)ServiceUtils.getBean(custHandlerName);
				List<Object> originalVOs = new ArrayList<Object>();
				originalVOs.add(transferBalanceVO);
				custHandler.process(requestInfo, handler, extParameters, balanceChgInfoVO,originalVOs);
			}
		}
		
		JSONObject balanceChgInfo = JSONUtils.convertBean2JSON(balanceChgInfoVO);
		JSONObject balanceChgInfoJson = new JSONObject();
		balanceChgInfoJson.put("balanceChgInfo", balanceChgInfo);
		JSONObject transferor = new JSONObject();
		transferor.put("transferor", balanceChgInfoJson);
		JSONObject transferResultJson = new JSONObject();
		transferResultJson.put("transferResult", transferor);
		processResult.setExtParameters(transferResultJson.toString());
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
