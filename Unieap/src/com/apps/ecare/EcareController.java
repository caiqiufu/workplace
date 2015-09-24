package com.apps.ecare;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.unieap.BaseController;
import com.unieap.base.ServiceUtils;
import com.unieap.login.bo.LoginBO;
import com.unieap.pojo.VisitLog;

@RequestMapping(value="EcareController.do")  
public class EcareController extends BaseController{
	@RequestMapping(value="EcareController.do",params="method=login",method = RequestMethod.GET)  
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/ecare/mainmenu");
		return ma;
	}
	@RequestMapping(value="LoginController.do",params="method=logout",method = RequestMethod.GET)  
	public ModelAndView logout(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("logout");
		LoginBO loginBO = (LoginBO) ServiceUtils.getBean("loginBO");
		VisitLog log = new VisitLog();
		log.setRemoteIp(request.getRemoteAddr());
		loginBO.lougoutLog(log);
		return ma;
	}
}
