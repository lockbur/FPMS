package com.forms.prms.web.budget.budgetInput.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.budget.budgetInput.domain.BudgetInputBean;
import com.forms.prms.web.budget.budgetInput.service.BudgetInputService;
import com.forms.prms.web.budget.finandeptsum.domain.FinanDeptSumBean;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/budget/budgetInput")
public class BudgetInputController {
	private static final String PREFIX = "budget/budgetInput/";
	@Autowired
	BudgetInputService service ;
	/**
	 * 查询列表
	 * @param bean
	 * @return
	 */
	@RequestMapping("/list.do")
	public String list(BudgetInputBean bean){
		ReturnLinkUtils.addReturnLink("budgetInputList", "返回");
		bean.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		bean.setIsSuperAdmin(WebHelp.getLoginUser().getIsSuperAdmin());
		bean.setUserOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		List<BudgetInputBean> list = service.selectAllBudget(bean); 
		WebUtils.setRequestAttr("list", list);
		WebUtils.setRequestAttr("searchBean", bean);
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		return PREFIX + "list";
	}
	/**
	 * 明细页面
	 * @param bean
	 * @return
	 */
	@RequestMapping("/view.do")
	public String view(BudgetInputBean bean){
		//查询模板的头信息
		
		bean.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		List<BudgetInputBean> headMsg = service.getHeadMsg(bean);
		List<BudgetInputBean> listMsg = service.getListMsg(bean,"list");
		WebUtils.setRequestAttr("headMsg", headMsg);
		WebUtils.setRequestAttr("listMsg", listMsg);
		WebUtils.setRequestAttr("searchBean", bean);
		WebUtils.setRequestAttr("dataType", bean.getDataType());
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/budget/budgetInput/list.do?VISIT_FUNC_ID=020203");
		return PREFIX +"view";
	}
	/**
	 * 删除
	 * @param bean
	 * @return
	 */
	@RequestMapping("/del.do")
	public String del(BudgetInputBean bean){
		if(service.del(bean)){
			ReturnLinkUtils.setShowLink("budgetInputList");
			WebUtils.getMessageManager().addInfoMessage("删除预算申报数据成功！");
			return ForwardPageUtils.getSuccessPage();
		}else {
			ReturnLinkUtils.setShowLink("budgetInputList");
			WebUtils.getMessageManager().addInfoMessage("删除预算申报数据失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}
	/**
	 * 提交
	 * @param bean
	 * @return
	 */
	@RequestMapping("/input.do")
	public String input(BudgetInputBean bean){

		bean.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		if(service.input(bean)){
			ReturnLinkUtils.setShowLink("budgetInputList");
			WebUtils.getMessageManager().addInfoMessage("预算申报数据提交成功！");
			return ForwardPageUtils.getSuccessPage();
		}else {
			ReturnLinkUtils.setShowLink("budgetInputList");
			WebUtils.getMessageManager().addInfoMessage("预算申报数据提交失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}
	/**
	 * 下载
	 * @param budget
	 * @return
	 */
	@RequestMapping("/download.do")
	@ResponseBody
	public String download(FinanDeptSumBean budget) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		//Excel导出操作
		try {
			budget.setDutyCode(WebHelp.getLoginUser().getDutyCode());
			service.downLoad( budget);
			jsonObject.put("pass", true);
		} catch (Exception e) {
			jsonObject.put("pass", false);
			e.printStackTrace();
		}
		return jsonObject.writeValueAsString();
	}
	
}
