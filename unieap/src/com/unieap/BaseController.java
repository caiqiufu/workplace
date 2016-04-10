package com.unieap;

import java.beans.PropertyEditor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.unieap.db.DBManager;
import com.unieap.pojo.ExcLog;

public class BaseController  extends MultiActionController{
	@InitBinder
	public void initBinder(HttpServletRequest request,
	                              ServletRequestDataBinder binder) throws Exception {
	    //对于需要转换为Date类型的属性，使用DateEditor进行处理
	    binder.registerCustomEditor(Date.class, (PropertyEditor) new com.unieap.exttools.DateEditor());
	}
	/** 基于@ExceptionHandler异常处理 */  
    @ExceptionHandler  
    public String exp(HttpServletRequest request, Exception ex) {  
        request.setAttribute("ex", ex);  
        ExcLog log = new ExcLog();
        log.setId(UnieapConstants.getSequence(null,UnieapConstants.UNIEAP));
        log.setBizModule("unieap");
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
        // 根据不同错误转向不同页面  
        /*if(ex instanceof BusinessException) {  
            return "business_error";  
        }else if(ex instanceof ParameterException) {  
            return "parameter_error";  
        } else {  
        }  */
        return "error";  
    } 
}
