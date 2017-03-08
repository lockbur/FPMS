package com.forms.prms.web.budget.budgetResolve.resolveQuery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.web.budget.budgetResolve.resolve.domain.BudgetReplyedBean;
import com.forms.prms.web.budget.budgetResolve.resolve.domain.BudgetResolveBean;
import com.forms.prms.web.budget.budgetResolve.resolve.service.ResolveService;
import com.forms.prms.web.budget.budgetResolve.resolveQuery.service.ResolveQueryService;

@Controller
@RequestMapping("/budget/budgetResolve/resolveQuery/")
public class ResolveQueryController {

	private static final String BASE_URL = "budget/budgetResolve/resolveQuery/";
	
	@Autowired
	private ResolveQueryService queryService;
	
	@Autowired
	private ResolveService resolveService;
	
	/**
	 * 获取已进行预算批复的可分解预算列表，以预算模板分组
	 * @param resolve
	 * @return
	 */
	@RequestMapping("getResolveQueryList.do")
	public String getResolveQueryList( BudgetReplyedBean budgetReplyed )
	{
		List<BudgetReplyedBean> budgetResolveList = resolveService.getBudgetReplyedList( budgetReplyed );
		WebUtils.setRequestAttr("budgetReplyed", budgetReplyed);
		WebUtils.setRequestAttr("budgetResolveList", budgetResolveList);
		ReturnLinkUtils.addReturnLink("budgetResolveList" , "预算分解列表");
		return BASE_URL + "resolveQueryList";
	}
	
	/**
	 * 获取指定模板下指定监控指标的分解详情及分解列表
	 * @param budgetReplyed
	 * @return
	 */
	@RequestMapping("getResolveQuery.do")
	public String getResolveQuery( BudgetReplyedBean budgetReplyed )
	{
		budgetReplyed = resolveService.getBudReplyByTmpAndMont( budgetReplyed );
		List<BudgetResolveBean> resolveDetailList = resolveService.getResolveList( budgetReplyed );
		WebUtils.setRequestAttr("budgetReplyed", budgetReplyed);
		WebUtils.setRequestAttr("resolveDetailList", resolveDetailList);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/budget/budgetResolve/resolveQuery/getResolveQueryList.do?VISIT_FUNC_ID=020602");
		return BASE_URL + "resolveQuery";
	}
	
}
