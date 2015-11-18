package com.unieap.mdm.bo;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.unieap.BaseBO;
import com.unieap.UnieapConstants;
import com.unieap.base.LoadSystemData;
import com.unieap.base.ServiceUtils;

@Service("refreshCacheBO")
public class RefreshCacheBO extends BaseBO{
	public Map<String, String> refreshDeal(String id) throws Exception {
		if (StringUtils.equals(id,"1")) {
			reloadSystemHandler();
			return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
		} else if (StringUtils.equals(id,"2")){
			loadSystemConfigure();
			return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
		}else {
			throw new Exception(UnieapConstants.getMessage("comm.operation.error", new Object[] { id }));
		}
	} 
	
	public void reloadSystemHandler() throws Exception{
		LoadSystemData loadSystemData = (LoadSystemData) ServiceUtils.getBean("loadSystemData");
		loadSystemData.reloadSystemHandlerDeal();
	}
	public void loadSystemConfigure() throws Exception{
		LoadSystemData loadSystemData = (LoadSystemData) ServiceUtils.getBean("loadSystemData");
		loadSystemData.loadSystemConfigure();
		loadSystemData.loadBizHandlerData();
		loadSystemData.loadEsbInfoData();
	}
}
