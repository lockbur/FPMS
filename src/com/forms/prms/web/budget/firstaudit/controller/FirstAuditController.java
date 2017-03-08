package com.forms.prms.web.budget.firstaudit.controller;

import java.util.ArrayList;
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
import com.forms.prms.web.budget.firstaudit.domain.BudgetDetailBean;
import com.forms.prms.web.budget.firstaudit.domain.FirstAuditBean;
import com.forms.prms.web.budget.firstaudit.service.FirstAuditServcie;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/budget/firstaudit")
public class FirstAuditController {
	private static final String BASE_URL = "budget/firstaudit/";
	
	@Autowired
	private FirstAuditServcie service;
	
	
	/**
	 * 新增预算申报的模板查询页面
	 * @param firstAuditBean
	 * @return
	 */
	@RequestMapping("queryTemp.do")
	public String queryTemp(FirstAuditBean firstAuditBean) {
		ReturnLinkUtils.addReturnLink("budgetTempList", "返回预算模板列表");
		List<FirstAuditBean> list = new ArrayList<FirstAuditBean>();
		firstAuditBean.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		firstAuditBean.setOrg2Name(WebHelp.getLoginUser().getOrg2Name());
		firstAuditBean.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		list = service.queryTemp(firstAuditBean);
		WebUtils.setRequestAttr("list", list);
		return BASE_URL + "queryTemp";
	}
	
	/**
	 * 预算审批汇总查询
	 * @param firstAuditBean
	 * @return
	 */
	@RequestMapping("budgetlist.do")
	public String budgetlist(FirstAuditBean firstAuditBean) {
		ReturnLinkUtils.addReturnLink("budgetlist", "返回");
		List<FirstAuditBean> list = new ArrayList<FirstAuditBean>();
		firstAuditBean.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		firstAuditBean.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		
		list = service.getBudgetGeneralList(firstAuditBean);
		WebUtils.setRequestAttr("list", list);
		WebHelp.setLastPageLink("uri", "budgetTempList");
		return BASE_URL + "generalList";
	}
	
	/**
	 * 预算审批明细列表查询
	 * @param firstAuditBean
	 * @return
	 */
	@RequestMapping("detailList.do")
	public String detailList(FirstAuditBean firstAuditBean) {
		ReturnLinkUtils.addReturnLink("budgetDetailList");
		List<BudgetDetailBean> list = new ArrayList<BudgetDetailBean>();
		firstAuditBean.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		firstAuditBean.setCurrDutyCode(WebHelp.getLoginUser().getDutyCode());

		list = service.getBudgetDetailList(firstAuditBean);
		WebUtils.setRequestAttr("list", list);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/budget/firstaudit/budgetlist.do?VISIT_FUNC_ID=02030101&tmpltId="+firstAuditBean.getTmpltId());
		return BASE_URL + "detailList";
	}
	
	/**
	 * 预算审批提交
	 * @param firstAuditBean
	 * @return
	 */
	@RequestMapping("submit.do")
	public String submit(FirstAuditBean firstAuditBean) {
		firstAuditBean.setAuditOper(WebHelp.getLoginUser().getUserId());
		firstAuditBean.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		service.updateAuditAmt(firstAuditBean);
		ReturnLinkUtils.setShowLink("budgetlist");
		return ForwardPageUtils.getSuccessPage();
	}
	
	/**
	 * 预算初审详情查看
	 * @param matrCode
	 * @param jspName
	 * @return
	 */
	@RequestMapping("view.do")
	public String view(FirstAuditBean bean)
	{
		List<FirstAuditBean> list = new ArrayList<FirstAuditBean>();
		
		list=service.view(bean);
			
		WebUtils.setRequestAttr("list",list);
		String tmpltId = bean.getTmpltId();
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/budget/firstaudit/budgetlist.do?VISIT_FUNC_ID=02030101&tmpltId="+tmpltId);
		return BASE_URL + "view";
	}
	
	/**
	 * ajax提交审批金额--02030106
	 * @param matrCode
	 * @return
	 */
	@RequestMapping("submitAmt.do")
	@ResponseBody
	public String updateAmt(FirstAuditBean fab) {
		
		AbstractJsonObject json = new SuccessJsonObject();
		String msg=service.update(fab);
		
		if (msg=="ok") {
			json.put("result", true);
		} else {
			json.put("result", false);
		}
		return json.writeValueAsString();
	}
	
	
}
