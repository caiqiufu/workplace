package com.apps.esb.service.bss.handler.query;

import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.apps.mcare.bo.ComplainBO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;

@Service("queryComplains")
public class QueryComplains extends SoapMessageHandler implements BizHandler {

	public QueryComplains() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
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
				complain.put("index", ((Integer) data.get("id")).toString());
				complain.put("text", (String) data.get("text"));
				complain.put("url", (String) data.get("file_path"));
				complain.put("feedback", (String) data.get("feedback"));
				complains.put(complain);
			}
		}
		ProcessResult processResult = new ProcessResult();
		processResult.setResultCode(UnieapConstants.C1);
		processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C1));
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
