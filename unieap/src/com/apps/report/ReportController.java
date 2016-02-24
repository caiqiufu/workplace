package com.apps.report;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.apps.esb.bo.EsbBO;
import com.apps.esb.vo.EsblogVO;
import com.unieap.BaseController;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;

@Controller
@RequestMapping("reportController.do")
public class ReportController extends BaseController{
	@RequestMapping(params="method=esbReport")  
	public ModelAndView user(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/report/esbreport");
		return ma;
	}
	@RequestMapping(params="method=esblogGrid")  
	public @ResponseBody String userGrid(PaginationSupport page,EsblogVO vo,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		EsbBO esbBO = (EsbBO) ServiceUtils.getBean("esbBO");
		esbBO.getEsblogList(page, vo);
		return page.getJsonString();
	}
	@RequestMapping(params="method=getEsblog")  
	public @ResponseBody String getEsblog(String logId, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		EsbBO esbBO = (EsbBO) ServiceUtils.getBean("esbBO");
		return esbBO.getEsblog(logId);
	}
}
