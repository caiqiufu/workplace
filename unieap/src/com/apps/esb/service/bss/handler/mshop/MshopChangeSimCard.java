package com.apps.esb.service.bss.handler.mshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.vo.subscriber.basicinfo.SubscriberVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.CustomizeBizHanlder;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.unieap.base.ServiceUtils;


@Service("mshopChangeSimCard")
public class MshopChangeSimCard implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		JSONObject json = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if (!json.has("ICCID")) {
			throw new Exception("ICCID is null");
		}
		if (!json.has("reasonCode")) {
			throw new Exception("reasonCode is null");
		}
		BizHandler querySubscriber = (BizHandler) ServiceUtils.getBeanByTenantId("querySubscriber");
		ProcessResult processSubscriber = querySubscriber.process(requestInfo, handler, extParameters);
		SubscriberVO subscriberVO = (SubscriberVO) processSubscriber.getVo();
		JSONObject custObj = new JSONObject("{OldICCID:"+subscriberVO.getSimCardVO().getIccid()+"}");
		JSONObject newExtParameters = BssServiceUtils
				.modifyExtParameters(requestInfo.getRequestBody().getExtParameters(), custObj);
		requestInfo.getRequestBody().setExtParameters(newExtParameters.toString());
		
		BizHandler appHandler = (BizHandler) ServiceUtils.getBeanByTenantId(handler.get("appHandlerName"));
		ProcessResult processResult = appHandler.process(requestInfo,handler,extParameters);
		if(handler!=null){
			String custHandlerName = handler.get("custHandlerName");
			if(!StringUtils.isEmpty(custHandlerName)){
				CustomizeBizHanlder custHandler = (CustomizeBizHanlder)ServiceUtils.getBean(custHandlerName);
				List<Object> originalVOs = new ArrayList<Object>();
				originalVOs.add(processResult.getVo());
				custHandler.process(requestInfo, handler, extParameters, processResult.getVo(),originalVOs);
			}
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
