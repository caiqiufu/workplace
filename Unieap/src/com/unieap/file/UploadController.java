package com.unieap.file;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.batik.transcoder.TranscoderInput;
//import org.apache.batik.transcoder.TranscoderOutput;
//import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.file.bo.ExcelBO;
import com.unieap.file.bo.FileBO;
import com.unieap.file.vo.FileUploadVO;
import com.unieap.pojo.HandlerConfig;
@RequestMapping(value="UploadController.do")  
public class UploadController extends MultiActionController{
	@RequestMapping(value="UploadController.do",params="method=upload",method = RequestMethod.POST)  
    public @ResponseBody Map upload(String parameters,HandlerConfig handlerConfig, FileUploadVO vo, HttpServletRequest request,HttpServletResponse response) throws Exception{  
		FileBO fileBO = (FileBO) ServiceUtils.getBean("fileBO");
		Map model = fileBO.SaveFile(parameters,handlerConfig,vo);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
        return model;  
    }
	@RequestMapping(value="UploadController.do",params="method=uploadExcel",method = RequestMethod.POST)  
    public @ResponseBody Map uploadExcel(String parameters,HandlerConfig handlerConfig, FileUploadVO vo, HttpServletRequest request,HttpServletResponse response) throws Exception{  
		ExcelBO excelBO = (ExcelBO) ServiceUtils.getBean("excelBO");
		Map model = excelBO.importExcel(parameters,handlerConfig,vo);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
        return model;  
    } 
	@RequestMapping(value="UploadController.do",params="method=download",method = RequestMethod.GET)  
    public @ResponseBody Map download(String parameters,HandlerConfig handlerConfig, HttpServletRequest request,HttpServletResponse response) throws Exception {  
		 FileBO fileBO = (FileBO) ServiceUtils.getBean("fileBO");
		 Map model = null;
		 //fileBO.downLoad(parameters,handlerConfig,request,response, null);
        return model;  
    }
	@RequestMapping(value="UploadController.do",params="method=export",method = RequestMethod.GET)  
    public @ResponseBody Map export(String parameters,HandlerConfig handlerConfig, HttpServletRequest request,HttpServletResponse response) throws Exception {  
		ExcelBO excelBO = (ExcelBO) ServiceUtils.getBean("excelBO");
		return excelBO.exportExcel(parameters,handlerConfig,request,response);
    }
	@RequestMapping(value="UploadController.do",params="method=svgServer",method = RequestMethod.POST)  
    public @ResponseBody void svgServer(String svg,String type,HandlerConfig handlerConfig, HttpServletRequest request,HttpServletResponse response) throws Exception {  
		/*response.setContentType(type);
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
        }*/
    }
}
