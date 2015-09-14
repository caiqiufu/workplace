package com.apps.ecare;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.unieap.BaseController;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.MenuVO;
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
	@RequestMapping(value="LoginController.do",params="method=loginsimple",method = RequestMethod.GET)  
	public ModelAndView loginsimple(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal()==null){
			ModelAndView ma = new ModelAndView("login");
			return ma;
		}
		ModelAndView ma = new ModelAndView("apps/monitor/monitorMainDisplay");
		/*LoginBO loginBO = (LoginBO) ServiceUtils.getBean("loginBO");
		loginBO.loadUser();
		UserVO user = UnieapConstants.getUser();
		loginBO.initUserButton(this.getServletContext(),user.getUserId(),null);
		loginBO.initUserDicdata(this.getServletContext(),user.getUserId(),null);
		ma.addObject("MENU",loginBO.getUserMenu(user.getUserId(),null));*/
		return ma;
	}
	@RequestMapping(value="LoginController.do",params="method=getMenu",method = RequestMethod.GET)  
	public @ResponseBody Map<String,Object> getMenu(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("id", "001");
		model.put("text", "text");
		return model;
	}
	@RequestMapping(value="LoginController.do",params="method=pagearea",method = RequestMethod.GET)  
	public ModelAndView pagearea(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/base/desktop/pagearea",null);
		
		String locationUrl = request.getParameter("locationUrl");
		LoginBO loginBO = (LoginBO) ServiceUtils.getBean("loginBO");
		MenuVO vo = loginBO.getMenu(locationUrl);
		String url = vo.getHref();
		String windowName = vo.getMenuName();
		if (SYSConfig.isDebug) {
			logger.info("request url:" + url);
			logger.info("window name:" + windowName);
		}
		request.setAttribute(UnieapConstants.LOCATIONURL, url);
		request.setAttribute(UnieapConstants.WINDOWNAME, windowName);
		return ma;
	}
}
