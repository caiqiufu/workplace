package com.apps.esb.service.bss.handler.mcare;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.easymobile.bo.ComplainBO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.CustomizeBizHanlder;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;

@Service("queryComplains")
public class QueryComplains implements BizHandler {
	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		ComplainBO complainBO = (ComplainBO) ServiceUtils.getBean("complainBO");
		List<Map<String, Object>> datas = complainBO.queryComplains(requestInfo.getRequestBody().getServiceNumber());
		JSONArray complains = new JSONArray();
		if (datas != null && datas.size() > 0) {
			for (int i = 0; i < datas.size(); i++) {
				Map<String, Object> data = datas.get(i);
				JSONObject complain = new JSONObject();
				complain.put("index", data.get("id"));
				complain.put("text", (String) data.get("text"));
				complain.put("url", (String) data.get("url"));
				complain.put("feedback", (String) data.get("feedback"));
				complain.put("feedbackDate", data.get("modify_date"));
				SimpleDateFormat sdf = new SimpleDateFormat(UnieapConstants.TIMEFORMAT);
				String dt = sdf.format(data.get("submit_date"));
				complain.put("submitDate", dt);
				complains.put(complain);
			}
		}
		if (handler != null) {
			String custHandlerName = handler.get("custHandlerName");
			if (!StringUtils.isEmpty(custHandlerName)) {
				CustomizeBizHanlder custHandler = (CustomizeBizHanlder) ServiceUtils.getBean(custHandlerName);
				List<Object> originalVOs = new ArrayList<Object>();
				custHandler.process(requestInfo, handler, extParameters, complains, originalVOs);
			}
		}

		ProcessResult processResult = new ProcessResult();
		processResult.setResultCode(UnieapConstants.C0);
		processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("complains", complains);
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

}
