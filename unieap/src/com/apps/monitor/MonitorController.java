package com.apps.monitor;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.apps.esb.bo.EsbBO;
import com.apps.monitor.bo.MonitorBO;
import com.unieap.BaseController;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.db.DBManager;
import com.unieap.pojo.ExcLog;

@Controller
@RequestMapping("monitorController.do")
public class MonitorController extends BaseController{
	
	@ExceptionHandler  
    public String exp(HttpServletRequest request, Exception ex) {  
        request.setAttribute("ex", ex);  
        ExcLog log = new ExcLog();
        log.setId(UnieapConstants.getSequence(null,UnieapConstants.UNIEAP));
        log.setBizModule("monitor");
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
	
	@RequestMapping(params="method=monitor")  
	public ModelAndView user(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/monitor/monitor");
		return ma;
	}
	@RequestMapping(params="method=serviceRequestChart")  
	public @ResponseBody String serviceRequestChart(PaginationSupport page,String bizCode,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		MonitorBO monitorBO = (MonitorBO) ServiceUtils.getBean("monitorBO");
		monitorBO.getServiceRequestChart(page, bizCode);
		return page.getJsonString();
	}
	@RequestMapping(params="method=getEsblog")  
	public @ResponseBody String getEsblog(String logId, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		EsbBO esbBO = (EsbBO) ServiceUtils.getBean("esbBO");
		return esbBO.getEsblog(logId);
	}
}
