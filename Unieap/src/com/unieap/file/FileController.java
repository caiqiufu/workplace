package com.unieap.file;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unieap.base.ServiceUtils;
import com.unieap.file.bo.FileBO;
import com.unieap.file.vo.FileUploadVO;

@Controller
@RequestMapping("fileController.do")
public class FileController {
	@Autowired
	public FileBO fileBO;

	 public @ResponseBody Map<String,String> upload(String parameters,String handlerId, FileUploadVO vo, HttpServletRequest request,HttpServletResponse response) throws Exception{  
			FileBO fileBO = (FileBO) ServiceUtils.getBean("fileBO");
			return fileBO.saveFile(parameters,handlerId,vo);
	    }
	
	@RequestMapping(params = "method=download")
	public @ResponseBody Map<String,String> download(String fileId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return fileBO.downLoad(fileId, request, response);
	}

}
