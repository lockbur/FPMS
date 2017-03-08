package com.forms.prms.web.budget.budgetResolve.resolve.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.web.budget.budgetResolve.resolve.domain.BudgetReplyedBean;
import com.forms.prms.web.budget.budgetResolve.resolve.domain.BudgetResolveBean;
import com.forms.prms.web.budget.budgetResolve.resolve.service.ResolveService;
import com.forms.prms.web.sysmanagement.matrtype.domain.MatrType;

@Controller
@RequestMapping("/budget/budgetResolve/resolve/")
public class ResolveController {

	private static final String BASE_URL = "budget/budgetResolve/resolve/";
	
	@Autowired
	private ResolveService service;
	
	/**
	 * 获取已通过预算批复的预算列表，该预算列表可进行预算分解操作，以预算模板ID分组，以监控指标为单位进行预算分解操作
	 * 			Service层通过【预算分解部门】(表：TB_MONT_NAME)做查询限制条件，仅列出登录人dutyCode=预算分解部门的可分解列表
	 * @param budgetReplyed	
	 * 			参数描述：(预算批复Bean,主要使用其TmpltId属性做查询过滤条件，需要其dataType、dataYear、dataAttr作条件)
	 * @return
	 */
	@RequestMapping("getResolveList.do")
	public String getResolveList( BudgetReplyedBean budgetReplyed ){
		List<BudgetReplyedBean> budgetResolveList = service.getBudgetReplyedList( budgetReplyed );
		WebUtils.setRequestAttr("budgetReplyed", budgetReplyed);
		WebUtils.setRequestAttr("budgetResolveList", budgetResolveList);
		ReturnLinkUtils.addReturnLink("budgetResolveList" , "预算分解列表");
		return BASE_URL + "resolveList";
	}
	
	/**
	 * 执行分解操作时，进入指定预算模板+监控指标下的分解详情列表，(返回页面属性：budgetReplyed=预算批复情况、resolveDetailList=指定的分解详情列表)
	 * 		通过预算批复(tmpltId+montCode)查找表：TD_BUDGET_SPLIT_DETAIL 所属的预算分解详情列表
	 * @param budgetReplyed 
	 * @return
	 */
	@RequestMapping("getResolve.do")
	public String getResolve( BudgetReplyedBean budgetReplyed ){
		budgetReplyed = service.getBudReplyByTmpAndMont( budgetReplyed );
		List<BudgetResolveBean> resolveDetailList = service.getResolveList( budgetReplyed );
		WebUtils.setRequestAttr("budgetReplyed", budgetReplyed);
		WebUtils.setRequestAttr("resolveDetailList", resolveDetailList);
//		WebUtils.setRequestAttr("url", "getResolveList.do?VISIT_FUNC_ID=020601");
		return BASE_URL + "resolve";
	}
	
	/**
	 * 【待处理】执行预算分解时的子功能，分解物料选择：目前只针对选择一个物料，以后有可能需要对应多个物料
	 * 			功能描述：根据【监控指标】查询该监控指标所关联维护的物料信息 (关联表： TB_MONT_NAME -- TB_MONT_MATR_CONTRA -- TB_MATR_TYPE )
	 * @param montCode	监控指标编码
	 * @return
	 */
	@RequestMapping("selectMatr.do")
	public String selectMatr(String montCode , String selectedMatrCodes){
		List<MatrType> matrList = service.getMatrListByMont(montCode);
		WebUtils.setRequestAttr("matrList", matrList);
		//将初始化已勾选的物料信息传到[物料选择]页面
		WebUtils.setRequestAttr("selectedMatrCodes", selectedMatrCodes);
		return BASE_URL + "matrSelectPage";
	}
	
	/**
	 * 	Ajax执行新增预算物料分解操作
	 * 	描述：将页面中的信息，插入数据库TD_BUDGET_SPLIT_DETAIL表中
	 */
	@RequestMapping("addBudgetSplit.do")
	@ResponseBody
	public String addBudgetSplit( BudgetResolveBean resolve){
		AbstractJsonObject json = new SuccessJsonObject();
		//新增预算分解
		int insertCount = service.addBudgetResolve(resolve);
		if(insertCount > 0){
			json.put("insertResult", true);
		}else{
			json.put("insertResult", false);
		}
		return json.writeValueAsString();
	}
	
	/**
	 * Ajax执行预算分解删除操作
	 * @param resolve
	 * @return
	 */
	@RequestMapping("delBudgetResolve.do")
	@ResponseBody
	public String delBudgetResolve( BudgetResolveBean resolve){
//		System.out.println("【delBudgetResolve-测试传入数据】："+resolve.getTmpltId()+"---"+resolve.getMontCode());
		AbstractJsonObject json = new SuccessJsonObject();
		//删除预算分解
		int delCount = service.deleteBudgetResolve(resolve);
		if(delCount>0){
			json.put("delResult", true);
		}else{
			json.put("delResult", false);
		}
		return json.writeValueAsString();
	}
	
//	@RequestMapping("testController.do")
//	@ResponseBody
//	public String testController(){
//		AbstractJsonObject json = new SuccessJsonObject();
//		Map<String , Object> map = service.testService();
//		json.put("jsonResult", map);
//		System.out.println("【测试Controller的结果：】"+json.writeValueAsString());
//		return json.writeValueAsString();
//	}
}
