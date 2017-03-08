package com.forms.prms.web.sysmanagement.parameter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.platform.web.token.PreventDuplicateSubmit;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.parameter.domain.Parameter;
import com.forms.prms.web.sysmanagement.parameter.domain.ParameterClass;
import com.forms.prms.web.sysmanagement.parameter.service.ParameterService;
import com.forms.prms.web.util.ForwardPageUtils;

/**
 * author : wuqm <br>
 * date : 2013-10-25<br>
 * 
 */
@Controller
@RequestMapping("/sysmanagement/parameter")
public class ParameterController {
	private static final String PARAMETER = "sysmanagement/parameter/";
	
	 @Autowired
	 private ParameterService parameterService;
	 
	 /**
	  * 
	  * 进入参数查询列表页面
	  * 
	  * */
	 @RequestMapping("list.do")
	 public String list(Parameter parameter) {
		 ReturnLinkUtils.addReturnLink("parameterList","返回参数列表");
		 
		 List<ParameterClass> paramsClassList=null;
		try {
			paramsClassList = parameterService.queryParameter(parameter);
		} catch (Exception e) {
//			e.printStackTrace();
		}
		 WebUtils.setRequestAttr("paramsClassList", paramsClassList);
		 return PARAMETER + "list";
	 }
	 
	 /**
	  * 
	  * 进入参数审批历史查询列表页面
	  * 
	  * */
	 @RequestMapping("applyHisList.do")
	 public String applyHisList(Parameter parameter) 
	 {
		List<Parameter> applyHisList=null;
		try {
			applyHisList = parameterService.queryApplyHisList(parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 WebUtils.setRequestAttr("applyHisList", applyHisList);
		 WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/parameter/list.do?VISIT_FUNC_ID=01050102");
		 return PARAMETER + "applyHisList";
	 }
	 
	 
	
	 /**
	  * 
	  * 进入参数修改页面
	 * @throws Exception 
	  * 
	  * */
	 @RequestMapping("preEdit.do")
	 public String preEdit(Parameter parameter) 
	 {
	       
	        Parameter paraUpdate=null;
			try {
				paraUpdate = parameterService.findPara(parameter);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
	        WebUtils.setRequestAttr("paraUpdate", paraUpdate);
	        ReturnLinkUtils.addReturnLink("preEditPara","返回修改页面");
	        WebHelp.setLastPageLink("uri", "parameterList");
	        return PARAMETER + "edit";
	    }
	    
	 
	 
	 
		 /**
		  * 
		  * 参数修改
		  * 
		  * */
	    @RequestMapping("edit.do")
	    @PreventDuplicateSubmit
	    public String edit(Parameter parameter) 
	    {
	        parameterService.edit(parameter);
	        WebUtils.getMessageManager().addInfoMessage("参数【"+parameter.getParamVarName()+"】信息已提交，等待审批!");
	        ReturnLinkUtils.setShowLink(new String[]{"parameterList","preEditPara","checkList"});
	        return ForwardPageUtils.getSuccessPage();
	    }
	    
	    
	    /**
		  * 
		  * 查询审批列表
		  * 
		  * */
	    @RequestMapping("checkList.do")
	    public String checkList(Parameter parameter)
	    {
			ReturnLinkUtils.addReturnLink("checkList","返回审批列表");
	    	List<Parameter> list=null;
			try {
				list = parameterService.checkList(parameter);
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
	    	WebUtils.setRequestAttr("list",list);
	    	return PARAMETER + "checkList";
	    }
	    
	    @RequestMapping("pass.do")
	    public String pass(Parameter para)
	    {
	    	parameterService.pass(para);
	    	WebUtils.getMessageManager().addInfoMessage("审批完成");
	        ReturnLinkUtils.setShowLink(new String[]{"checkList"});
	    	return ForwardPageUtils.getSuccessPage();
	    }
	    
	    @RequestMapping("refuse.do")
	    public String refuse(Parameter para)
	    {
	    	parameterService.refuse(para);
	    	WebUtils.getMessageManager().addInfoMessage("审批完成");
	    	ReturnLinkUtils.setShowLink(new String[]{"checkList"});
	    	return ForwardPageUtils.getSuccessPage();
	    }
	    
	    /**
	     * 校验预提待摊截止日期
	     * @param yyyymm
	     * @return
	     */
	    @RequestMapping("ajaxCheckDeadline.do")
	    @ResponseBody
	    public String ajaxCheckDeadline(String yyyymm){
	    	AbstractJsonObject jsonObject = new SuccessJsonObject();
			boolean r = false;
			try {
				r = parameterService.checkDeadline(yyyymm);
			} catch (Exception e) {
				e.printStackTrace();
				CommonLogger.error("校验预提待摊截止日期失败！");
				jsonObject.put("pass", false);
				return jsonObject.writeValueAsString();	
			}
			if (r) {
				jsonObject.put("pass", true);
				CommonLogger.info("校验预提待摊截止日期通过！,ParameterController,ajaxCheckDeadline()");
			} else {
				jsonObject.put("pass", false);
				CommonLogger.info("校验预提待摊截止日期不通过！,ParameterController,ajaxCheckDeadline()");
			}
			return jsonObject.writeValueAsString();	
	    }
	    
	    
}
