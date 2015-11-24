package com.apps.esb.service.bss.handler.query;

import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.db.DBManager;

@Service("queryUpgardeInfo")
public class QueryUpgardeInfo extends SoapMessageHandler implements BizHandler {

	public QueryUpgardeInfo() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
		String apkVersion = requestInfo.getRequestHeader().getDeviceInfo().getAPKVersion();
		StringBuffer sql = new StringBuffer("SELECT au.version,au.change_desc,fa.url ");
		sql.append("FROM app_upgrade au, file_archive fa  where au.file_id = fa.id and ");
		sql.append("au.active_flag = ? and au.tenant_id = ? ");
		List<Map<String, Object>> datas = DBManager.getJT(null).queryForList(sql.toString(),
				new Object[] { UnieapConstants.YES,SYSConfig.getConfig().get("tenantId") });
		JSONObject upgradeInfo = new JSONObject();
		if(datas!= null && datas.size() >0){
			Map<String, Object> data = datas.get(0);
			if(apkVersion.equals((String) data.get("version"))){
				upgradeInfo.put("isUpgrade", UnieapConstants.NO);
			}else{
				upgradeInfo.put("isUpgrade", UnieapConstants.YES);
				JSONObject upgradeInfoDetails = new JSONObject();
				upgradeInfoDetails.put("url", (String) data.get("url"));
				upgradeInfoDetails.put("upgradeDesc", (String) data.get("change_desc"));
				upgradeInfo.put("upgradeInfo", upgradeInfoDetails);
			}
		}else{
			upgradeInfo.put("isUpgrade", UnieapConstants.NO);
		}
		ProcessResult processResult = new ProcessResult();
		processResult.setResultCode(UnieapConstants.C0);
		processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("upgradeInfo",upgradeInfo);
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
