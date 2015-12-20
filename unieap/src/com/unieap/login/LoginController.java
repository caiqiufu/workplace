package com.unieap.login;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.unieap.BaseController;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.vo.MenuVO;
import com.unieap.login.bo.LoginBO;
import com.unieap.pojo.VisitLog;
import com.unieap.tools.JSONUtils;

@Controller
@RequestMapping("loginController.do")
public class LoginController extends BaseController {
	@Autowired
	public LoginBO loginBO;

	@RequestMapping(params = "method=login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		loginBO.loadLoginUser();
		loginBO.initUserDicdata(this.getServletContext());
		loginBO.initUserButton(this.getServletContext());
		Map<String, List<Object>> menus = loginBO.getUserMenu(UnieapConstants.getUser().getUserId());
		/** mantis application */
		//ModelAndView ma = new ModelAndView("apps/mantis/mainmenu",menus);
		ModelAndView ma = new ModelAndView();
		if("1".equals(SYSConfig.getConfig().get("menuType"))){
			/** mdm application */
			ma.setViewName("apps/base/mdm/mainmenu");
			ma.addAllObjects(menus);
		}else{
			/** desk application */
			ma.setViewName("apps/base/desktop/desk");
			List<Object> menuList = menus.get("menus");
			if(menuList!=null &&menuList.size() >0){
				for(int i = 0 ; i< menuList.size() ; i++){
					MenuVO vo = (MenuVO)menuList.get(i);
					JSONArray dataBalanceJson = JSONUtils.collectionToJson(menuList);
					ma.addObject("MENU",dataBalanceJson.toString());
				}
			}else{
				ma.addObject("MENU","{}");
			}
		}
		VisitLog log = new VisitLog();
		log.setRemoteIp(request.getRemoteAddr());
		loginBO.louginLog(log);
		return ma;
	}
	@RequestMapping(params = "method=logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView ma = new ModelAndView("logout");
		VisitLog log = new VisitLog();
		log.setRemoteIp(request.getRemoteAddr());
		loginBO.lougoutLog(log);
		return ma;
	}

	@RequestMapping(params = "method=sessionTimeout")
	public void sessionTimeout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		VisitLog log = new VisitLog();
		log.setRemark("session timeout");
		log.setRemoteIp(request.getRemoteAddr());
		loginBO.lougoutLog(log);
		// String requestUrl = request.getRequestURI();
		if (request.getHeader("x-requested-with") != null
				&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) { // ajax超时处理
			response.setHeader("sessionstatus", "timeout");
			PrintWriter out = response.getWriter();
			out.print("{timeout:true}");
			out.flush();
			out.close();
		} else { // http 超时处理
			response.sendRedirect(request.getContextPath() + "/timeout.jsp");
		}
	}
}
