package com.apps.esb.service.bss.handler.biz;

import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.app.cbs.handler.Recharge;
import com.apps.esb.service.bss.app.cbs.vo.recharge.BalanceChgInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.LifeCycleChgInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.LifeCycleStatusVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.RechargeResponseVO;
import com.apps.esb.service.bss.app.vo.VoucherRechargeVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;

@Service("voucherCardRecharge")
public class VoucherCardRecharge extends SoapMessageHandler implements BizHandler {

	public VoucherCardRecharge() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo,String parameters,Map<String,Object> extParameters)
			throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		Recharge recharge = (Recharge) ServiceUtils.getBean("recharge");
		ProcessResult processResult =  recharge.process(requestInfo,parameters,extParameters);
		if(!UnieapConstants.C0.equals(processResult.getResultCode())){
			return processResult;
		}
		RechargeResponseVO rechargeResponseVO = (RechargeResponseVO)processResult.getVo();
		VoucherRechargeVO voucherRechargeVO = new VoucherRechargeVO();
		List<BalanceChgInfoVO> balanceChgInfos = rechargeResponseVO.getBalanceChgInfos();
		for(int i = 0 ; i< balanceChgInfos.size() ; i++){
			BalanceChgInfoVO balanceChgInfoVO = balanceChgInfos.get(i);
			if("C_MAIN_ACCOUNT".equals(balanceChgInfoVO.getBalanceType())){
				voucherRechargeVO.setBalance(balanceChgInfoVO.getNewBalanceAmt());
			}
		}
		LifeCycleChgInfoVO lifeCycleChgInfoVO = rechargeResponseVO.getLifeCycleChgInfoVO();
		List<LifeCycleStatusVO> newLifeCycleStatus = lifeCycleChgInfoVO.getNewLifeCycleStatus();
		voucherRechargeVO.setValidity(newLifeCycleStatus.get(0).getStatusExpireTime());
		JSONObject customerJson = JSONUtils.convertBean2JSON(voucherRechargeVO);
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("subscriberInfo", customerJson);
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

}
