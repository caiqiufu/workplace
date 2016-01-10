package com.apps.esb.service.bss.handler.biz;

import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.app.cbs.vo.transferbalance.TransferBalanceResponseVO;
import com.apps.esb.service.bss.app.cbs.vo.transferbalance.TransferorVO;
import com.apps.esb.service.bss.customize.smart.app.handler.cbs.AccountTransfer;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.apps.esb.service.bss.vo.transferbalance.BalanceChgInfoVO;
import com.apps.esb.service.bss.vo.transferbalance.TransferBalanceVO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;

@Service("transfer")
public class Transfer extends SoapMessageHandler implements BizHandler {

	public Transfer() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
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

		AccountTransfer accountTransfer = (AccountTransfer) ServiceUtils.getBean("accountTransfer");
		ProcessResult processResult = accountTransfer.process(requestInfo, parameters, extParameters);
		if (!UnieapConstants.C0.equals(processResult.getResultCode())) {
			return processResult;
		}
		TransferBalanceResponseVO transferBalanceResponseVO = (TransferBalanceResponseVO) processResult.getVo();
		TransferBalanceVO transferBalanceVO = new TransferBalanceVO();
		TransferorVO transferorVO = transferBalanceResponseVO.getTransferorVO();
		transferBalanceVO.setHandingChargeAmt(transferorVO.getHandlingChargeAmt());
		BalanceChgInfoVO balanceChgInfoVO = new BalanceChgInfoVO();
		transferBalanceVO.setBalanceChgInfoVO(balanceChgInfoVO);
		List<com.apps.esb.service.bss.app.cbs.vo.recharge.BalanceChgInfoVO> balanceChgInfos = transferorVO
				.getBalanceChgInfos();
		if (balanceChgInfos != null && balanceChgInfos.size() > 0) {
			for (int i = 0; i < balanceChgInfos.size(); i++) {
				com.apps.esb.service.bss.app.cbs.vo.recharge.BalanceChgInfoVO chgInfoVO = balanceChgInfos.get(i);
				if ("C_MAIN_ACCOUNT".equals(chgInfoVO.getBalanceType())) {
					balanceChgInfoVO.setBalanceId(chgInfoVO.getBalanceID());
					balanceChgInfoVO.setBalanceType(chgInfoVO.getBalanceType());
					balanceChgInfoVO.setBalanceTypeName(chgInfoVO.getBalanceTypeName());
					balanceChgInfoVO.setNewBalanceAmt(chgInfoVO.getOldBalanceAmt());
					balanceChgInfoVO.setOldBalanceAmt(chgInfoVO.getOldBalanceAmt());
				}
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
