package com.unieap.mantis;

import java.beans.PropertyEditor;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.mantis.bo.DefectBO;
import com.unieap.mantis.bo.DefectReportBO;
import com.unieap.mantis.bo.TestCaseBO;
import com.unieap.mantis.vo.MDefectVO;
import com.unieap.mantis.vo.MTestcaseVO;
import com.unieap.mantis.vo.ViewIssueVO;
import com.unieap.pojo.MAtta;
import com.unieap.pojo.MDefect;
import com.unieap.pojo.MNote;
import com.unieap.pojo.MTestcase;
@RequestMapping(value="MantisController.do")  
public class MantisController extends MultiActionController{
	@InitBinder
	protected void initBinder(HttpServletRequest request,
	                              ServletRequestDataBinder binder) throws Exception {
	    //对于需要转换为Date类型的属性，使用DateEditor进行处理
	    binder.registerCustomEditor(Date.class, (PropertyEditor) new com.unieap.exttools.DateEditor());
	}
	@RequestMapping(value="MantisController.do",params="method=main",method = RequestMethod.GET)  
	public ModelAndView main(String project,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DefectBO defectBO = (DefectBO) ServiceUtils.getBean("defectBO");
		Map attributes = defectBO.getMain(project);
		ModelAndView ma = new ModelAndView("/apps/mantis/main",attributes);
		ma.addObject("project",project);
		return ma;
	}
	@RequestMapping(value="MantisController.do",params="method=myview",method = RequestMethod.GET)  
	public ModelAndView myview(String project,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("/apps/mantis/myview");
		ma.addObject("project",project);
		return ma;
	}
	
	@RequestMapping(value="MantisController.do",params="method=reportissue",method = RequestMethod.GET)  
	public ModelAndView reportissue(String project,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("/apps/mantis/reportissue");
		ma.addObject("project",project);
		return ma;
	}
	
	@RequestMapping(value="MantisController.do",params="method=myaccount",method = RequestMethod.GET)  
	public ModelAndView myaccount(String project,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("/apps/mantis/myaccount");
		ma.addObject("project",project);
		return ma;
	}
	
	@RequestMapping(value="MantisController.do",params="method=reportIssueDeal",method = RequestMethod.POST)  
	public @ResponseBody Map reportIssueDeal(String operType,MDefect defect, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DefectBO defectBO = (DefectBO) ServiceUtils.getBean("defectBO");
		Map model = defectBO.reportIssueDeal(operType,defect);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	
	@RequestMapping(value="MantisController.do",params="method=viewissues",method = RequestMethod.GET)  
	public ModelAndView viewissues(String project,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("/apps/mantis/viewissues");
		ma.addObject("project",project);
		return ma;
	}
	
	
	@RequestMapping(value="MantisController.do",params="method=defectModify",method = RequestMethod.GET)  
	public ModelAndView defectModify(MDefectVO vo,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DefectBO defectBO = (DefectBO) ServiceUtils.getBean("defectBO");
		Map attributes = defectBO.getDefect(vo);
		ModelAndView ma = new ModelAndView("/apps/mantis/defectmodify",attributes);
		ma.addObject("defectId", vo.getDefectId().toString());
		return ma;
	}
	@RequestMapping(value="MantisController.do",params="method=viewisGrid",method = RequestMethod.GET)  
	public @ResponseBody String viewisGrid(String operType,PaginationSupport page,ViewIssueVO vo,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DefectBO defectBO = (DefectBO) ServiceUtils.getBean("defectBO");
		defectBO.viewisGrid(operType,page,vo);
		return page.getJsonString();
	}
	@RequestMapping(value="MantisController.do",params="method=issueDetail",method = RequestMethod.GET)  
	public ModelAndView issueDetail(MDefectVO vo,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DefectBO defectBO = (DefectBO) ServiceUtils.getBean("defectBO");
		Map attributes = defectBO.getDefect(vo);
		String path = attributes.get("path").toString();
		ModelAndView ma = new ModelAndView(path,attributes);
		ma.addObject("defectId", vo.getDefectId().toString());
		return ma;
	}
	/**
	 * Remove attachment
	 * @param operType
	 * @param dicGroup
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="MantisController.do",params="method=dicGroupDeal",method = RequestMethod.POST)  
	public @ResponseBody Map dicGroupDeal(String operType,MAtta vo, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DefectBO defectBO = (DefectBO) ServiceUtils.getBean("defectBO");
		Map model = defectBO.deleteAtta(vo);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	/**
	 * node deal: add, edit, delete
	 * @param operType
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="MantisController.do",params="method=nodeDeal",method = RequestMethod.POST)  
	public @ResponseBody Map nodeDeal(String operType,MNote vo, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DefectBO defectBO = (DefectBO) ServiceUtils.getBean("defectBO");
		Map model = defectBO.nodeDeal(operType,vo);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	/**
	 * update defect
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="MantisController.do",params="method=updateDefect",method = RequestMethod.POST)  
	public @ResponseBody Map updateDefect(String operType,MDefect vo, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DefectBO defectBO = (DefectBO) ServiceUtils.getBean("defectBO");
		Map model = defectBO.updateDefect(operType,vo);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	/**
	 * attachment deal
	 * @param operType
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="MantisController.do",params="method=attDeal",method = RequestMethod.POST)  
	public @ResponseBody Map attDeal(String operType,MAtta vo, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DefectBO defectBO = (DefectBO) ServiceUtils.getBean("defectBO");
		Map model = defectBO.deleteAtta(vo);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	
	/**
	 * View Issue History
	 * @param page
	 * @param defect
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="MantisController.do",params="method=viewIssueHistory",method = RequestMethod.GET)  
	public @ResponseBody String viewIssueHistory(PaginationSupport page,MDefect defect,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DefectBO defectBO = (DefectBO) ServiceUtils.getBean("defectBO");
		defectBO.viewIssueHistory(page, defect);
		return page.getJsonString();
	}
	
	/**
	 * get reporter info
	 * @param isOptional
	 * @param groupId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="MantisController.do",params="method=getReporterList",method = RequestMethod.GET)  
	public @ResponseBody String getReporterList(String operType,String project,Integer defectId, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DefectBO defectBO = (DefectBO) ServiceUtils.getBean("defectBO");
		return defectBO.getReporterList(operType,project,defectId);
	}
	/**
	 * Batch update defect attribute
	 * @param operType
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="MantisController.do",params="method=batchUpdate",method = RequestMethod.POST)  
	public @ResponseBody Map batchUpdate(String operType,String datas,String updateValue, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DefectBO defectBO = (DefectBO) ServiceUtils.getBean("defectBO");
		Map model = defectBO.batchUpdate(operType,datas,updateValue);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	/**
	 * Summary
	 * @param project
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="MantisController.do",params="method=summary",method = RequestMethod.GET)  
	public ModelAndView summary(String project,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("/apps/mantis/summary");
		ma.addObject("project",project);
		return ma;
	}
	@RequestMapping(value="MantisController.do",params="method=getDefectReport",method = RequestMethod.GET)  
	public @ResponseBody String getDefectReport(String operType,String project,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DefectReportBO defectReportBO = (DefectReportBO) ServiceUtils.getBean("defectReportBO");
		return defectReportBO.getDefectReport(operType,project);
	}
	
	/**
	 * Testcase
	 * @param project
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="MantisController.do",params="method=testcases",method = RequestMethod.GET)  
	public ModelAndView testcases(String project,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("/apps/mantis/testcases");
		ma.addObject("project",project);
		return ma;
	}
	@RequestMapping(value="MantisController.do",params="method=testCasesGrid",method = RequestMethod.GET)  
	public @ResponseBody String testCasesGrid(String operType,PaginationSupport page,MTestcaseVO vo,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		TestCaseBO testCaseBO = (TestCaseBO) ServiceUtils.getBean("testCaseBO");
		testCaseBO.getTestCasesGrid(operType,page,vo);
		return page.getJsonString();
	}
	@RequestMapping(value="MantisController.do",params="method=importtc",method = RequestMethod.GET)  
	public ModelAndView importtc(String project,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("/apps/mantis/importtc");
		ma.addObject("project",project);
		return ma;
	}
	@RequestMapping(value="MantisController.do",params="method=tcmodify",method = RequestMethod.GET)  
	public ModelAndView tcmodify(String project,MTestcaseVO vo,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		TestCaseBO testCaseBO = (TestCaseBO) ServiceUtils.getBean("testCaseBO");
		Map attributes = testCaseBO.getTestCase(vo);
		ModelAndView ma = new ModelAndView(attributes.get("path").toString(),attributes);
		ma.addObject("tcId", vo.getTcId().toString());
		ma.addObject("project",project);
		return ma;
	}
	@RequestMapping(value="MantisController.do",params="method=updateTc",method = RequestMethod.POST)  
	public @ResponseBody Map updateTc(String operType,MTestcase vo, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		TestCaseBO testCaseBO = (TestCaseBO) ServiceUtils.getBean("testCaseBO");
		Map model = testCaseBO.updateTc(vo);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
}
