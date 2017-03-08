package com.forms.prms.web.sysmanagement.homePageRollInfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.homePageRollInfo.domain.RollInfoBean;
import com.forms.prms.web.sysmanagement.homePageRollInfo.service.RollInfoService;
import com.forms.prms.web.util.ForwardPageUtils;
import java.util.List;

@Controller
@RequestMapping("/sysmanagement/homePageRollInfo")
public class RollInfoController {
	private static final String PARAMETER = "sysmanagement/homePageRollInfo/";
	
	 @Autowired
	 private RollInfoService rollInfoService;
	 /**
		 * @methodName list
		 * desc  
		 * 列表查询
		 * @param rollInfoBean
		 * @return
		 */
	 @RequestMapping("list.do")
	 public String list(RollInfoBean rollInfoBean) {
		 ReturnLinkUtils.addReturnLink("RollInfoController.list", "返回列表");
		//是否是总行超级管理员 1-是 0-否
		String isA0001SuperAdmin = "1".equals(WebHelp.getLoginUser().getIsSuperAdmin())&&"A0001".equalsIgnoreCase(WebHelp.getLoginUser().getOrg1Code())?"1":"0";
		rollInfoBean.setIsA0001SuperAdmin(isA0001SuperAdmin);		
		 List<RollInfoBean> list=rollInfoService.list(rollInfoBean);
		 WebUtils.setRequestAttr("list",list);
		 WebUtils.setRequestAttr("rollInfoBean",rollInfoBean);
		return PARAMETER+"list";
	 }
	 /**
	  * @methodName add
	  * desc  
	  * 新增
	  * @param rollInfoBean
	  * @return
	  */
	 @RequestMapping("add.do")
	 public String add(RollInfoBean rollInfoBean) {
		 WebUtils.setRequestAttr("userId",WebHelp.getLoginUser().getUserId());
		 WebUtils.setRequestAttr("mProvice",WebHelp.getLoginUser().getOrg1Code());
		 //是否是总行超级管理员 1-是 0-否
		 String isA0001SuperAdmin = "1".equals(WebHelp.getLoginUser().getIsSuperAdmin())&&"A0001".equalsIgnoreCase(WebHelp.getLoginUser().getOrg1Code())?"1":"0";
		 WebUtils.setRequestAttr("isA0001SuperAdmin",isA0001SuperAdmin);		 
		 WebUtils.setRequestAttr("url",WebUtils.getRequest().getContextPath()+"/sysmanagement/homePageRollInfo/list.do?VISIT_FUNC_ID=0813");
		 return PARAMETER+"add";
	 }
	 /**
	  * @methodName update
	  * desc  
	  * 更新
	  * @param rollInfoBean
	  * @return
	  */
	 @RequestMapping("update.do")
	 public String update(RollInfoBean rollInfoBean) {
		 RollInfoBean roll = rollInfoService.queryInfo(rollInfoBean);
		 WebUtils.setRequestAttr("roll",roll);
		 WebUtils.setRequestAttr("bean",rollInfoBean);
		 WebUtils.setRequestAttr("mProvice",WebHelp.getLoginUser().getOrg1Code());
		 WebUtils.setRequestAttr("url",WebUtils.getRequest().getContextPath()+"/sysmanagement/homePageRollInfo/list.do?VISIT_FUNC_ID=0813");
		 return PARAMETER+"update";
	 }
	 /**
	  * @methodName addSubmit
	  * desc  
	  * 新增提交
	  * @param rollInfoBean
	  * @return
	  */
	 @RequestMapping("addSubmit.do")
	 public String addSubmit(RollInfoBean rollInfoBean) {
		 rollInfoBean.setRollId(rollInfoService.createRollId(rollInfoBean));
		 rollInfoService.addSubmit(rollInfoBean);
		 ReturnLinkUtils.setShowLink("RollInfoController.list");
		 WebUtils.getMessageManager().addInfoMessage("新增成功");
		 return ForwardPageUtils.getSuccessPage();
	 }
	 /**
	  * @methodName addSubmit
	  * desc  
	  * 更新提交
	  * @param rollInfoBean
	  * @return
	  */
	 @RequestMapping("updateSubmit.do")
	 public String updateSubmit(RollInfoBean rollInfoBean) {
		 rollInfoService.updateSubmit(rollInfoBean);
		 ReturnLinkUtils.setShowLink("RollInfoController.list");
		 WebUtils.getMessageManager().addInfoMessage("更新成功");
		 return ForwardPageUtils.getSuccessPage();
	 }
	 /**
	  * @methodName addSubmit
	  * desc  
	  * 删除
	  * @param rollInfoBean
	  * @return
	  */
	 @RequestMapping("del.do")
	 public String delete(RollInfoBean rollInfoBean) {
		 rollInfoService.del(rollInfoBean);
		 ReturnLinkUtils.setShowLink("RollInfoController.list");
		 WebUtils.getMessageManager().addInfoMessage("删除成功");
		 return ForwardPageUtils.getSuccessPage();
	 }
	 
	 
	 
}
