package com.unieap.ext;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.apps.esb.service.bss.BssServiceBO;
import com.apps.reuse.bo.ReuseSMSBO;
import com.apps.reuse.pojo.ReuseCustomer;
import com.apps.reuse.pojo.ReuseSmsNotify;
import com.unieap.BaseController;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.db.DBManager;
import com.unieap.file.bo.FileBO;
import com.unieap.pojo.ExcLog;

@Controller
@RequestMapping("extAction.do")
public class ExtAction extends BaseController {

	@ExceptionHandler  
    public String exp(HttpServletRequest request, Exception ex) {  
        request.setAttribute("ex", ex);  
        ExcLog log = new ExcLog();
        log.setId(UnieapConstants.getSequence(null,UnieapConstants.UNIEAP));
        log.setBizModule("ext");
        log.setExType("system_exception");
        log.setExCode("");
        log.setExInfo(ex.getLocalizedMessage());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        log.setExTracking(sw.toString().getBytes());
        if(UnieapConstants.getUser()!=null){
        	log.setOperator(UnieapConstants.getUser().getUserCode());
        }else{
        	log.setOperator("system error");
        }
        log.setOperationDate(UnieapConstants.getDateTime(null));
        DBManager.getHT(null).save(log);
        return "error";  
    }
	
	@RequestMapping(params = "method=sendSms", method = RequestMethod.GET)
	public @ResponseBody String sendSms(ReuseSmsNotify vo, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReuseSMSBO reuseSMSBO = (ReuseSMSBO) ServiceUtils.getBean("reuseSMSBO");
		reuseSMSBO.sendSms(vo);
		return UnieapConstants.SUCCESS;
	}

	@RequestMapping(params = "method=smsVerify", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> smsVerify(ReuseSmsNotify vo, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReuseSMSBO reuseSMSBO = (ReuseSMSBO) ServiceUtils.getBean("reuseSMSBO");
		return reuseSMSBO.smsVerify(vo);
	}

	@RequestMapping(params = "method=regisCustomer", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> regisCustomer(ReuseSmsNotify smsVo, ReuseCustomer custVo,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReuseSMSBO reuseSMSBO = (ReuseSMSBO) ServiceUtils.getBean("reuseSMSBO");
		return reuseSMSBO.regisCustomer(smsVo, custVo);
	}

	@RequestMapping(params = "method=index", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView ma = new ModelAndView("apps/ecare/index");
		return ma;
	}

	@RequestMapping(params = "method=queryInfo")
	public @ResponseBody String queryInfo(String parameters, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> extParameters = new HashMap<String, Object>();
		extParameters.put("HttpServletRequest", request);
		BssServiceBO bssServiceBO = (BssServiceBO) ServiceUtils.getBean("bssServiceBO");
		String responsetInfoString = bssServiceBO.queryInfo(parameters, extParameters);
		String isdebug = SYSConfig.getConfig().get("mcare.app.extaction.debug");
		if (UnieapConstants.YES.equals(isdebug)) {
			System.out.println("parameters=" + parameters);
			System.out.println("responsetInfoString=" + responsetInfoString);
		}
		return responsetInfoString;
	}

	@RequestMapping(params = "method=bizHandle")
	public @ResponseBody String bizHandle(String parameters, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BssServiceBO bssServiceBO = (BssServiceBO) ServiceUtils.getBean("bssServiceBO");
		String responsetInfoString = bssServiceBO.bizHandle(parameters, null);
		String isdebug = SYSConfig.getConfig().get("mcare.app.extaction.debug");
		if (UnieapConstants.YES.equals(isdebug)) {
			System.out.println("parameters=" + parameters);
			System.out.println("responsetInfoString=" + responsetInfoString);
		}
		return responsetInfoString;
	}

	@RequestMapping(params = "method=bizFileHandle")
	public @ResponseBody String submitComplain(String parameters, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		FileBO fileBO = (FileBO) ServiceUtils.getBean("fileBO");
		List<FileItem> items = fileBO.getFileItems(request);
		BssServiceBO bssServiceBO = (BssServiceBO) ServiceUtils.getBean("bssServiceBO");
		Map<String, Object> extparameters = new HashMap<String, Object>();
		extparameters.put("files", items);
		String responsetInfoString = bssServiceBO.bizHandle(parameters, extparameters);
		return responsetInfoString;
	}

}
