package com.unieap.demo;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.MenuVO;
import com.unieap.login.bo.LoginBO;

@RequestMapping(value="DemoController.do")  
public class DemoController extends MultiActionController{
	@RequestMapping(value="DemoController.do",params="method=begin",method = RequestMethod.GET)  
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		Map model = new HashMap();
		ModelAndView ma = new ModelAndView("/apps/base/demo/desk/index",model);
		return ma;
	}
	@RequestMapping(value="DemoController.do",params="method=grid",method = RequestMethod.GET)  
	public ModelAndView grid(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		Map model = new HashMap();
		ModelAndView ma = new ModelAndView("/apps/base/demo/grid/grid1",model);
		return ma;
	}
	@RequestMapping(value="LoginController.do",params="method=getMenu",method = RequestMethod.GET)  
	public @ResponseBody Map getMenu(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		Map model = new HashMap();
		model.put("id", "001");
		model.put("text", "text");
		return model;
	}
	@RequestMapping(value="LoginController.do",params="method=getRoles",method = RequestMethod.GET)  
	public @ResponseBody Map getRoles(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		Map model = new HashMap();
		model.put("id", "001");
		model.put("text", "text");
		return model;
	}
	@RequestMapping(value="LoginController.do",params="method=pagearea",method = RequestMethod.GET)  
	public ModelAndView pagearea(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("/apps/base/desktop/pagearea",null);
		
		String locationUrl = request.getParameter("locationUrl");
		LoginBO loginBO = (LoginBO) ServiceUtils.getBean("loginBO");
		MenuVO vo = loginBO.getMenu(locationUrl);
		String url = vo.getHref();
		String windowName = vo.getMenuName();
		if (SYSConfig.isDebug) {
			logger.info("菜单请求路径:" + url);
			logger.info("业务窗口名称:" + windowName);
		}
		return ma;
	}
}
