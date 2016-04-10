package com.unieap.file;

import java.io.StringReader;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.file.bo.ExcelBO;
import com.unieap.file.bo.FileBO;
import com.unieap.file.vo.FileUploadVO;
import com.unieap.pojo.HandlerConfig;

@Controller
@RequestMapping("fileController.do")
public class FileController {
	@Autowired
	public FileBO fileBO;
	@RequestMapping(params="method=upload")
	public @ResponseBody Map<String, String> upload(String parameters, String handlerId, FileUploadVO vo,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return fileBO.saveFile(parameters, handlerId, vo);
	}

	@RequestMapping(params = "method=download")
	public @ResponseBody Map<String, String> download(String fileId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return fileBO.downLoad(fileId, request, response);
	}
	@RequestMapping(params="method=uploadExcel",method = RequestMethod.POST)  
    public @ResponseBody Map<String,String> uploadExcel(String parameters,HandlerConfig handlerConfig, FileUploadVO vo, HttpServletRequest request,HttpServletResponse response) throws Exception{  
		ExcelBO excelBO = (ExcelBO) ServiceUtils.getBean("excelBO");
		Map<String,String> model = excelBO.importExcel(parameters,handlerConfig,vo);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
        return model;  
    } 
	
	@RequestMapping(params="method=export",method = RequestMethod.GET)  
    public @ResponseBody Map<String,String> export(String parameters,HandlerConfig handlerConfig, HttpServletRequest request,HttpServletResponse response) throws Exception {  
		ExcelBO excelBO = (ExcelBO) ServiceUtils.getBean("excelBO");
		return excelBO.exportExcel(parameters,handlerConfig,request,response);
    }
	@RequestMapping(params="method=svgServer",method = RequestMethod.POST)  
    public @ResponseBody void svgServer(String svg,String type,HandlerConfig handlerConfig, HttpServletRequest request,HttpServletResponse response) throws Exception {  
		response.setContentType(type);
		String filename = new Date().toLocaleString().replace(" ","_")+"."+type.substring(6);
		response.setHeader("Content-disposition","attachment;filename=" + filename);
		JPEGTranscoder t = new JPEGTranscoder();
        t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY,new Float(.8));
        TranscoderInput input = new TranscoderInput(new StringReader(svg));
        try {
            TranscoderOutput output = new TranscoderOutput(response.getOutputStream());
            t.transcode(input, output);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }catch (Exception e){
            response.getOutputStream().close();
            e.printStackTrace();
        }
    }
}
