package com.forms.prms.web.budget.budgetplan.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.budget.budgetplan.domain.BudgetPlanBean;
import com.forms.prms.web.budget.budgetplan.domain.BudgetTempDetailBean;
import com.forms.prms.web.budget.budgetplan.domain.BudgetTmpltDutyBean;
import com.forms.prms.web.budget.budgetplan.service.BudgetPlanService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/budget/budgetplan")
public class BudgetPlanController {

	private static final String BASE_URL = "budget/budgetplan/";
	
	@Autowired
	private BudgetPlanService budgetService;
	
	/**
	 * 跳转至基础预算Excel模板的下载页面
	 * @return
	 */
	@RequestMapping("toBudTmpExportPage.do")
	public String toBudTmpExportPage(){
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		return BASE_URL + "budTmpExportPage";
	}
	
	/**
	 * 预算基础模板下载
	 * 【Ps:Excel导出的模板格式必须为2007版】
	 * @param dataAttr	导出模板的类型(0:资产类/1:费用类)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("budgetTmpExport.do")
	@ResponseBody
	public String budgetTmpExport(String dataAttr) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		//Excel导出操作
		try {
			String exportTaskId = budgetService.budgetBasicTmpExport(dataAttr);
			jsonObject.put("pass", true);
			jsonObject.put("exportTaskId", exportTaskId);
		} catch (Exception e) {
			jsonObject.put("pass", false);
			e.printStackTrace();
		}
		//返回导出Excel操作是否成功标识
		return jsonObject.writeValueAsString();
	}
	
	/**
	 * 进行申报前的Excel表格详情导出(导出内容包括归口部门信息、自定义标题的更改，其他列接收Excel导入的数据)
	 * 		功能ID：02010303
	 * 		【注意：Excel导出的模板格式必须为2007版】
	 * 		@throws Exception 
	 */
	@RequestMapping("exportBudgetPlanTempInfo.do")
	@ResponseBody
	public String exportBudgetPlanTempInfo(BudgetPlanBean budget) {
		budget = budgetService.view(budget.getTmpltId());
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		//Excel导出操作
		try {
			budgetService.budgetTempExport( budget);
			jsonObject.put("pass", true);
		} catch (Exception e) {
			jsonObject.put("pass", false);
			e.printStackTrace();
		}
		return jsonObject.writeValueAsString();
	}
	
	/**
	 * 跳转至预算模板-新增页面
	 * @return
	 */
	@RequestMapping("preAdd.do")
	public String preAdd(){
		//将当前用户的二级机构信息传到页面
		String currentUserOrg21Code = WebHelp.getLoginUser().getOrg2Code();
		WebUtils.setRequestAttr("currentUserOrg21Code", currentUserOrg21Code);
		ReturnLinkUtils.addReturnLink("preAdd","编制预算模板");
		return BASE_URL + "add";
	}
	
	/**
	 * 预算模板-新增操作
	 * @param budget
	 * @return
	 */
	@RequestMapping("add.do")
	public String add(BudgetPlanBean budget){
		budgetService.addBudgetPlanAndRel( budget );
		WebUtils.getMessageManager().addInfoMessage("预算模板已经新增，模板Excel导入线程进入排队！");
		ReturnLinkUtils.setShowLink(new String[]{"preAdd" , "budgetPlanList"});	//快速链接：预增加页面,budget列表
		return ForwardPageUtils.getSuccessPage();
	}
	
	/**
	 * 查询预算模板的列表
	 * @param budget
	 * @return
	 */
	@RequestMapping("list.do")
	public String list(BudgetPlanBean budget){
		List<BudgetPlanBean> budgetPlanList = budgetService.list(budget);
		WebUtils.setRequestAttr("budgetList", budgetPlanList);
		WebUtils.setRequestAttr("budget", budget);
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		ReturnLinkUtils.addReturnLink("budgetPlanList", "返回预算模板列表");
		return BASE_URL + "list";
	}
	
	/**
	 * 删除预算模板及其相关的关联表
	 * @param budget
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@RequestMapping("delete.do")
	public String delete(BudgetPlanBean budget){
		budgetService.deleteBudgetPlanAndRel(budget.getTmpltId());
		WebUtils.getMessageManager().addInfoMessage("删除预算模板成功！");
		ReturnLinkUtils.setShowLink("budgetPlanList");
		return ForwardPageUtils.getSuccessPage();
	}
	
	
	/**
	 * 查看明细-- 020105
	 * @param budgetBean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("view.do")
	public String view(BudgetPlanBean budgetBean , String jspName)
	{
		Map<String,Object> objMap = budgetService.mapView(budgetBean.getTmpltId());
		BudgetPlanBean 				budget 				= (BudgetPlanBean) objMap.get("budgetPlan");
		List<BudgetTempDetailBean> 	budgetHeaderDetail	= (List<BudgetTempDetailBean>) objMap.get("budgetHeaderDetail");
		List<BudgetTempDetailBean> 	budgetBodyDetail 	= (List<BudgetTempDetailBean>) objMap.get("budgetBodyDetail");
		
		List<BudgetTmpltDutyBean> orgsList = budgetService.getBudgetOrgs(budgetBean.getTmpltId());
		budget.setAvailableOrgList(objMap.get("orgIds").toString());
		budget.setAvailableOrgNameList(objMap.get("orgNames").toString());
		
		WebUtils.setRequestAttr("budgetInfo", budget);
		WebUtils.setRequestAttr("orgsList", orgsList);
		if(budgetHeaderDetail.size()>0){
			WebUtils.setRequestAttr("budgetHeaderDetail", budgetHeaderDetail.get(0));
		}
		WebUtils.setRequestAttr("budgetBodyDetail", budgetBodyDetail);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/budget/budgetplan/list.do?VISIT_FUNC_ID=020103");
		return BASE_URL + jspName;
	}
	
	/**
	 * 
	 * @param budget
	 * @return
	 */
	@RequestMapping("update.do")
	public String update(BudgetPlanBean budgetBean){
		String toJspPage = "update";
		budgetService.updateBudgetPlanAndRel(budgetBean);
		budgetService.updateBudgetPlan(budgetBean);
		WebUtils.getMessageManager().addInfoMessage("更新模板信息成功");
		this.view(budgetBean , toJspPage);
		return BASE_URL + toJspPage;
	}
	
	/**
	 * 提交预算模板
	 * @param budgetId
	 * @return
	 */
	@RequestMapping("submit.do")
	public String submit(String budgetId){
		budgetService.submitBudget(budgetId);
		WebUtils.getMessageManager().addInfoMessage("已提交，该模板已可用于预算申报");
		ReturnLinkUtils.setShowLink("budgetPlanList");
		return ForwardPageUtils.getSuccessPage();
	}
	
	
	/**
	 * 校验统计年初预算
	 * 		描述：每年的年初预算只允许有一个，在新增时做Ajax校验，当年初预算已存在时，提示用户，不执行后续请求
	 * @param budget
	 * @return
	 */
	@RequestMapping("ajaxCheckYearFirstBudgetCount.do")
	@ResponseBody
	public String getYearFirstBudgetCount(BudgetPlanBean budgetBean){
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		int countResult = budgetService.checkYearFirstBudgetPlan(budgetBean.getDataYear());
		jsonObject.put("countResult", countResult);
		return jsonObject.writeValueAsString();
	}
	
	/**
	 * Ajax校验导出Excel基础模板中是否存在监控指标信息未进行维护状态(统计Mont-Matr对应表中，Mont为Null的个数)
	 * @param budget
	 * @return
	 */
	@RequestMapping("ajaxValidateExcelInfos.do")
	@ResponseBody
	public String ajaxValidateExcelInfos(String dataAttr){
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		int countResult = budgetService.validateMaintainByMatrMont(dataAttr);
		jsonObject.put("countResult", countResult);
		return jsonObject.writeValueAsString();
	}
	
	
}
