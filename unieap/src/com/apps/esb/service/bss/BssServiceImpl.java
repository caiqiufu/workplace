package com.apps.esb.service.bss;

import javax.jws.WebService;

import com.apps.esb.service.bss.element.ResponseBody;
import com.apps.esb.service.bss.element.ResponseHeader;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.unieap.UnieapConstants;

@WebService(serviceName = "BssService", endpointInterface = "com.apps.esb.service.bss.BssService")
public class BssServiceImpl extends BssServiceBO implements BssService {
	@Override
	public String queryInfo(String requestInfoString) {
		try {
			return this.queryInfo(requestInfoString,null);
		} catch (Exception e) {
			ResponsetInfo responsetInfo = new ResponsetInfo();
			ResponseHeader ResponseHeader = new ResponseHeader();
			ResponseHeader.setResultCode(UnieapConstants.C99999);
			ResponseHeader.setResultDesc(e.getLocalizedMessage());
			ResponseBody responseBody = new ResponseBody();
			responsetInfo.setResponseHeader(ResponseHeader);
			responsetInfo.setResponseBody(responseBody);
			String responsetInfoString = "";
			try {
				responsetInfoString = BssServiceUtils.getResposeInfoString(responsetInfo);
			} catch (Exception e1) {
				saveException(e1);
			}
			return responsetInfoString;
		}
	}
}
