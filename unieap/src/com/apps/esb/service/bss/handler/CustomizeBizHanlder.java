package com.apps.esb.service.bss.handler;

import java.util.List;
import java.util.Map;

import com.apps.esb.service.bss.element.RequestInfo;

public interface CustomizeBizHanlder {
	/**
	 * customize view VO
	 * @param requestInfo
	 * @param parameters
	 * @param extParameters
	 * @param viewVO
	 * @param originalVOs
	 * @return customized view VO
	 * @throws Exception
	 */
	public Object process(RequestInfo requestInfo,Map<String, String> handler,Map<String,Object> extParameters,Object viewVO,List<Object> originalVOs)
			throws Exception;
}
