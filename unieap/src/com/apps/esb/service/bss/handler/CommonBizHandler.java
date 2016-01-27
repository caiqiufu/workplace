package com.apps.esb.service.bss.handler;

import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.commons.fileupload.FileItem;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.file.bo.FileBO;
import com.unieap.pojo.FileArchive;

@Service("commonBizHandler")
public class CommonBizHandler implements BizHandler{

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		if(extParameters!=null&&extParameters.containsKey("files")){
			List<FileItem> files = (List<FileItem>) extParameters.get("files");
			return saveTempFiles(files);
		}else{
			ProcessResult processResult = new ProcessResult();
			processResult.setResultCode("2006");
			processResult.setResultDesc(UnieapConstants.getMessage("2006"));
			return processResult;
		}
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
	private ProcessResult saveTempFiles(List<FileItem> files) throws Exception{
		ProcessResult processResult = new ProcessResult();
		processResult.setResultCode(UnieapConstants.C0);
		processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
		if(files!=null && files.size() >0){
			FileBO fileBO = (FileBO) ServiceUtils.getBean("fileBO");
			Integer extKey = UnieapConstants.getSequence(null, null);
			String shareFolderPath = SYSConfig.getConfig().get("shareFolderPath");
			String url = SYSConfig.getConfig().get("url.images.complain");
			String uploadPath = shareFolderPath+"/mdm/sharefolder/tempfiles";
			List<FileArchive> fileArchiveList = fileBO.upload("tempfile", extKey.toString(), files, uploadPath, url);
			JSONObject jsonResult = new JSONObject();
			jsonResult.put("extKey",extKey.toString());
			if(fileArchiveList!=null&&fileArchiveList.size()>0){
				JSONArray jsonArr = new JSONArray();
				for(int i = 0 ; i < fileArchiveList.size() ; i++){
					JSONObject jsonFile = new JSONObject();
					jsonFile.put("fileId", fileArchiveList.get(i).getId().toString());
					jsonArr.put(jsonFile);
				}
				jsonResult.put("fileList",jsonArr);
			}
			processResult.setExtParameters(jsonResult.toString());
		}
		return processResult;
		
	}
}
