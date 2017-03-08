package com.forms.prms.web.sysmanagement.businessType.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.platform.web.token.PreventDuplicateSubmit;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.businessType.domain.BusinessType;
import com.forms.prms.web.sysmanagement.businessType.service.BusinessTypeService;
import com.forms.prms.web.util.ForwardPageUtils;

/**
 * author : silence <br>
 * date : 2017-02-20<br>
 * 
 */
@Controller
@RequestMapping("/sysmanagement/businessType")
public class BusinessTypeController {
	private static final String PARAMETER = "sysmanagement/businessType/";
	
	 @Autowired
	 private BusinessTypeService businessTypeService;
	 
	 /**
	  * 
	  * 进入业务类型查询列表页面
	  * 
	  * */
	 @RequestMapping("list.do")
	 public String list(BusinessType parameter) {
		 ReturnLinkUtils.addReturnLink("parameterList","返回业务类型列表");
		 
		 List<BusinessType> paramsClassList=null;
		try {
			paramsClassList = businessTypeService.queryParameter(parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 WebUtils.setRequestAttr("paramsClassList", paramsClassList);
		 WebUtils.setRequestAttr("parameter", parameter);
		 return PARAMETER + "list";
	 }
	
		/**
		 * 
		 * 进入业务类型修改页面
		 *@throws Exception 
		 * 
		 */
		@RequestMapping("preEdit.do")
		public String preEdit(BusinessType parameter) 
		{
	       
		 	BusinessType paraUpdate=null;
			try {
				paraUpdate = businessTypeService.findPara(parameter);
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
		  * 业务类型修改
		  * 
		  * */
		@RequestMapping("edit.do")
		@PreventDuplicateSubmit
		public String edit(BusinessType parameter) 
		{
			
			if(!Tool.CHECK.isEmpty(parameter.getParamName()) &&!Tool.CHECK.isEmpty(parameter.getCategoryId())){
				businessTypeService.edit(parameter);
			}else{
				return ForwardPageUtils.getErrorPage();
			}
				
		    WebUtils.getMessageManager().addInfoMessage("参数【"+parameter.getParamName()+"】信息已修改!");
		    ReturnLinkUtils.setShowLink(new String[]{"parameterList"});
		    return ForwardPageUtils.getSuccessPage();
		}
	    


		/**
		  * @methodName add
		  * desc  
		  * 新增
		  * @param rollInfoBean
		  * @return
		  */
		 @RequestMapping("add.do")
		 public String add(BusinessType parameter) {
			 WebUtils.setRequestAttr("url",WebUtils.getRequest().getContextPath()+"/sysmanagement/businessType/list.do?VISIT_FUNC_ID=0815");
			 return PARAMETER+"add";
		 }
		 
		 /**
		  * @methodName addSubmit
		  * desc  
		  * 新增提交
		  * @param rollInfoBean
		  * @return
		  */
		 @RequestMapping("addSubmit.do")
		 public String addSubmit(BusinessType parameter) {
			 parameter.setApplyUserId(WebHelp.getLoginUser().getUserId());
			 parameter.setApplyTime(Tool.DATE.getDate());
			 businessTypeService.addSubmit(parameter);
			 WebUtils.getMessageManager().addInfoMessage("新增成功");
			 ReturnLinkUtils.setShowLink(new String[]{"parameterList"});
			 return ForwardPageUtils.getSuccessPage();
			 
			 
		 }
		 
		 /**
		  * @methodName addSubmit
		  * desc  
		  * 新增提交
		  * @param rollInfoBean
		  * @return
		  */
		 @RequestMapping("updateSort.do")
		 public String updateSort(BusinessType parameter) {
			AbstractJsonObject jsonObject = new SuccessJsonObject();
			boolean isOut = false;
			jsonObject.put("isOut", isOut);
			return jsonObject.writeValueAsString();
		 }
}
