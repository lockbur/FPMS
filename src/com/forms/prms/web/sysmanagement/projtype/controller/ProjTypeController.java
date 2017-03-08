package com.forms.prms.web.sysmanagement.projtype.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.projtype.domain.ProjTypeBean;
import com.forms.prms.web.sysmanagement.projtype.service.ProjTypeService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/sysmanagement/projType")
public class ProjTypeController {
	private static final String PARAMETER = "sysmanagement/projtype/";
	
	 @Autowired
	 private ProjTypeService projTypeService;
	 /**
		 * @methodName list
		 * desc  
		 * 列表查询
		 * @param rollInfoBean
		 * @return
		 */
	 @RequestMapping("list.do")
	 public String list(ProjTypeBean projTypeBean) {
		 ReturnLinkUtils.addReturnLink("ProjTypeController.list", "返回列表");
		 List<ProjTypeBean> list=projTypeService.list(projTypeBean);
		 WebUtils.setRequestAttr("list",list);
		 WebUtils.setRequestAttr("projTypeBean",projTypeBean);
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
	 public String add(ProjTypeBean projTypeBean) {
		 WebUtils.setRequestAttr("userId",WebHelp.getLoginUser().getUserId());
		 WebUtils.setRequestAttr("mProvice",WebHelp.getLoginUser().getOrg1Code());
		 WebUtils.setRequestAttr("isA0001SuperAdmin","0".equals(WebHelp.getLoginUser().getIsSuperAdmin())&&"A0001".equalsIgnoreCase(WebHelp.getLoginUser().getOrg1Code())?"0":"1");		 
		 WebUtils.setRequestAttr("url",WebUtils.getRequest().getContextPath()+"/sysmanagement/projType/list.do?VISIT_FUNC_ID=0814");
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
	 public String update(ProjTypeBean projTypeBean) {
		 ProjTypeBean typeBean = projTypeService.queryInfo(projTypeBean);
		 WebUtils.setRequestAttr("typeBean",typeBean);
		 WebUtils.setRequestAttr("url",WebUtils.getRequest().getContextPath()+"/sysmanagement/projType/list.do?VISIT_FUNC_ID=0814");
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
	 public String addSubmit(ProjTypeBean projTypeBean) {
//		 projTypeBean.setRollId(projTypeService.createRollId(rollInfoBean));
		 //默认状态为1 启用
		 projTypeBean.setUseFlag("1");
		 projTypeBean.setInstUser(WebHelp.getLoginUser().getUserId());
		 projTypeBean.setInstDate(Tool.DATE.getDate());
		 projTypeBean.setInstTime(Tool.DATE.getTime());
		 projTypeService.addSubmit(projTypeBean);
		 ReturnLinkUtils.setShowLink("ProjTypeController.list");
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
	 public String updateSubmit(ProjTypeBean projTypeBean) {
		 
		 projTypeBean.setUpdUser(WebHelp.getLoginUser().getUserId());
		 projTypeBean.setUpdDate(Tool.DATE.getDate());
		 projTypeBean.setUpdTime(Tool.DATE.getTime());
		 projTypeService.updateSubmit(projTypeBean);
		 ReturnLinkUtils.setShowLink("ProjTypeController.list");
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
	 public String delete(ProjTypeBean projTypeBean) {
		 projTypeService.del(projTypeBean);
		 ReturnLinkUtils.setShowLink("ProjTypeController.list");
		 WebUtils.getMessageManager().addInfoMessage("删除成功");
		 return ForwardPageUtils.getSuccessPage();
	 }
	 
	 
	 
}
