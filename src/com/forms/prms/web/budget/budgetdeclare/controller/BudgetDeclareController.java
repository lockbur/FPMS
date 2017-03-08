package com.forms.prms.web.budget.budgetdeclare.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.platform.web.returnlink.annotation.ShowReturnLink;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.budget.budgetdeclare.domain.BudgetDeclareBean;
import com.forms.prms.web.budget.budgetdeclare.domain.ExcelImportTaskSummaryBean;
import com.forms.prms.web.budget.budgetdeclare.domain.StockBudgetBean;
import com.forms.prms.web.budget.budgetdeclare.service.BudgetDeclareService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/budget/budgetdeclare")
public class BudgetDeclareController {
	
	private static final String BASE_URL = "budget/budgetdeclare/";
	
	@Autowired
	private BudgetDeclareService service;
	
	
	@RequestMapping("mainpage.do")
	public String mainpage() 
	{
		
		return BASE_URL + "mainpage";
	}
	
	
	/**
	 * 新增预算申报的模板查询页面
	 * 		描述：该查询仅查询列出当前登录用户的责任中心可用于申报操作的模板列表(SQL中第一个exist限定)，
	 * 				并且过滤掉已进行申报操作的年初预算(同一年中年初预算只允许存在一个，当其已申报即不应该在查询结果的待申报列表中，SQL中not exist限定)
	 * @param budgetDeclareBean
	 * @return
	 */
	@RequestMapping("queryTemp.do")
	public String queryTemp(BudgetDeclareBean budgetDeclareBean) {
		ReturnLinkUtils.addReturnLink("budgetTempList", "返回预算模板列表");
		List<BudgetDeclareBean> list = new ArrayList<BudgetDeclareBean>();
		budgetDeclareBean.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		budgetDeclareBean.setOrg2Name(WebHelp.getLoginUser().getOrg2Name());
		budgetDeclareBean.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		
		list = service.queryTemp(budgetDeclareBean);
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		WebUtils.setRequestAttr("list", list);
		return BASE_URL + "queryTemp";
	}
	
	
	/**
	 * 导出(下载)预算计划操作
	 * @param budgetDeclareBean
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("exportBudgetPlan.do")
	public String exportBudgetPlan(HttpServletRequest request, HttpServletResponse response, BudgetDeclareBean budgetDeclareBean) throws Exception {
		service.downloadTemp(response, budgetDeclareBean);
		return null;
	}
	
	/**
	 * 预算申报-跳转至预算申报，上传Excel模板页面
	 * @param budgetDeclareBean
	 * @return
	 */
	@RequestMapping("viewBudget.do")
	public String viewBudget(BudgetDeclareBean budgetDeclareBean) {
		return BASE_URL + "applyImport";
	}
	
	/**
	 * 预算申报-预算申报EXCEL文件导入上传操作
	 * @param budgetDeclareBean
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("applyImport.do")
	public String applyImport(BudgetDeclareBean budgetDeclareBean) throws Exception {
		budgetDeclareBean.setInstOper(WebHelp.getLoginUser().getUserId());
		budgetDeclareBean.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		ExcelImportTaskSummaryBean bean =  service.applyImport(budgetDeclareBean);
		
		WebUtils.getMessageManager().addInfoMessage("待处理任务总数："+bean.getTotalCnt()+"，当前导入任务排名："+bean.getTaskRank());
		ReturnLinkUtils.setShowLink("budgetTempList");
		return ForwardPageUtils.getSuccessPage();
	}
	
	/**
	 * 已立项未签订合同项目列表 -- 020201
	 * @param budgetDeclareBean
	 * @return
	 */
	@RequestMapping("unSignContList.do")
	public String unSignContList(StockBudgetBean stockBudgetBean) {
		stockBudgetBean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		stockBudgetBean.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		List<StockBudgetBean> list = service.getUnsignContList(stockBudgetBean);
		WebUtils.setRequestAttr("list", list);
		return BASE_URL + "unSignContList";
	}
	
	/**
	 * 已签订合同未执行完毕项目列表 -- 02020101
	 * @param budgetDeclareBean
	 * @return
	 */
	@RequestMapping("unExecuteList.do")
	@AddReturnLink(id = "unExecuteList", displayValue = "返回已签订合同未执行完毕项目列表")
	public String unExecuteList(StockBudgetBean stockBudgetBean) {
		stockBudgetBean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		stockBudgetBean.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		List<StockBudgetBean> list = service.getUnexecuteList(stockBudgetBean);
		WebUtils.setRequestAttr("list", list);
		return BASE_URL + "unExecuteList";
	}
	
	/**
	 * 确认年度预算操作
	 * @param stockBudgetBean
	 * @return
	 */
	@RequestMapping("confirmBudget.do")
	@ShowReturnLink(showLinks = "unExecuteList")
	public String confirmBudget(StockBudgetBean stockBudgetBean) {
		stockBudgetBean.setConfirmOper(WebHelp.getLoginUser().getUserId());
		service.confirmBudget(stockBudgetBean);
		return ForwardPageUtils.getSuccessPage();
	}

}
