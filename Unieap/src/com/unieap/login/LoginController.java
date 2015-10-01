package com.unieap.login;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.unieap.BaseController;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.login.bo.LoginBO;
import com.unieap.pojo.VisitLog;

@RequestMapping(value="LoginController.do")  
public class LoginController extends BaseController{
	@RequestMapping(value="LoginController.do",params="method=login",method = RequestMethod.GET)  
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		LoginBO loginBO = (LoginBO) ServiceUtils.getBean("loginBO");
		loginBO.loadLoginUser();
		loginBO.initUserDicdata(this.getServletContext());
		loginBO.initUserButton(this.getServletContext());
		Map<String, List<Object>> menus = loginBO.getUserMenu(UnieapConstants.getUser().getUserId());
		/**mantis application*/
		//ModelAndView ma = new ModelAndView("apps/mantis/mainmenu",menus);
		/**reuse application*/
		ModelAndView ma = new ModelAndView("apps/base/mdm/mainmenu",menus);
		/**desk application*/
		//ma.addObject("MENU",loginBO.getUserMenu(user.getUserId(),null));
		VisitLog log = new VisitLog();
		log.setRemoteIp(request.getRemoteAddr());
		loginBO.louginLog(log);
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
