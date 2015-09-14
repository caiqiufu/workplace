package com.unieap.monitor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.mantis.bo.DefectBO;
import com.unieap.pojo.MNote;
@RequestMapping(value="MonitorController.do")  
public class MonitorController extends MultiActionController{
	@RequestMapping(value="MonitorController.do",params="method=medstat",method = RequestMethod.GET)  
	public ModelAndView myview(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("/apps/monitor/3ppfile");
		return ma;
	}
	@RequestMapping(value="MonitorController.do",params="method=generateStatic",method = RequestMethod.POST)  
	public @ResponseBody Map generateStatic(String operType,MNote vo, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DefectBO defectBO = (DefectBO) ServiceUtils.getBean("defectBO");
		Map model = defectBO.nodeDeal(operType,vo);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
}
