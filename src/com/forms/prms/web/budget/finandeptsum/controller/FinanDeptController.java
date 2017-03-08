package com.forms.prms.web.budget.finandeptsum.controller;

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
import com.forms.prms.web.budget.budgetplan.domain.BudgetPlanBean;
import com.forms.prms.web.budget.finandeptsum.domain.FinanDeptSumBean;
import com.forms.prms.web.budget.finandeptsum.service.FinanDeptService;
import com.forms.prms.web.util.ForwardPageUtils;

/**
 * 预算管理-【财务部门汇总】Controller
 * 		包含本级汇总、二级行汇总、一级行汇总
 * @author HQQ
 */
@Controller
@RequestMapping("/budget/finandeptsum")
public class FinanDeptController {

	//财务部门汇总链接前缀
	private final String BASE_URL = "budget/finandeptsum/"; 
	
	@Autowired
	private FinanDeptService fiDeptService;
	
	/**==============================================================================
	 * 本级汇总查询
	 * 		根据模板ID进行当前登录用户的责任中心对应的归口部门作预算信息汇总
	 * @param finanDeptSum
	 * @return
	 */
	@RequestMapping("selList.do")
	public String selList(FinanDeptSumBean finanDeptSum){
		finanDeptSum.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		List<FinanDeptSumBean> finanSumList = new ArrayList<FinanDeptSumBean>();
		finanSumList = fiDeptService.selList(finanDeptSum,"list");	
		WebUtils.setRequestAttr("finanDeptSum", finanDeptSum);
		WebUtils.setRequestAttr("finanSumList", finanSumList);
		WebUtils.setRequestAttr("org1Code", WebHelp.getLoginUser().getOrg1Code());
		ReturnLinkUtils.setShowLink("budgetTmpSumList");
		ReturnLinkUtils.addReturnLink("selList", "返回本级汇总明细列表");
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/budget/finandeptsum/budgetTmpSumList.do?VISIT_FUNC_ID=020401");
		return BASE_URL + "selSumList";
	}
	/**==============================================================================
	 * 本级汇总明细
	 * @param bean
	 * @return
	 */
	@RequestMapping("view.do")
	public String view(FinanDeptSumBean bean){
		//查询申报明细
		List<FinanDeptSumBean> list = fiDeptService.getSbList(bean);
		ReturnLinkUtils.setShowLink("selList");
		WebUtils.setRequestAttr("list", list);
		WebUtils.setRequestAttr("bean", bean);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/budget/finandeptsum/selList.do?VISIT_FUNC_ID=02040101&tmpltId="+bean.getTmpltId());
		return BASE_URL +"selSumView";
	}
	/**==============================================================================
	 * 本级汇总退回
	 * @param bean
	 * @return
	 */
	@RequestMapping("back.do")
	public String back(FinanDeptSumBean bean){
		
		if(fiDeptService.back(bean)){
			ReturnLinkUtils.setShowLink("selList");
			WebUtils.getMessageManager().addInfoMessage("物料退回成功");
			return ForwardPageUtils.getSuccessPage();
		}else {
			ReturnLinkUtils.setShowLink("selList");
			WebUtils.getMessageManager().addInfoMessage("物料退回失败");
			return ForwardPageUtils.getErrorPage();
		}
	}
	/**==============================================================================
	 * 本级行提交
	 * @param bean
	 * @return
	 */
	@RequestMapping("submit.do")
	public String submit(FinanDeptSumBean bean){
		if(fiDeptService.submit(bean)){
			ReturnLinkUtils.setShowLink("selList");
			WebUtils.getMessageManager().addInfoMessage(" 提交成功");
			return ForwardPageUtils.getSuccessPage();
		}else {
			ReturnLinkUtils.setShowLink("selList");
			WebUtils.getMessageManager().addInfoMessage(" 提交失败");
			return ForwardPageUtils.getErrorPage();
		}
	}
	/**==============================================================================
	 * 二级行汇总列表
	 * @param finanDeptSum
	 * @return
	 */
	//secondList.do
	@RequestMapping("secondList.do")
	public String secondList(FinanDeptSumBean finanDeptSum){
		List<FinanDeptSumBean> finanSumList = fiDeptService.secondList(finanDeptSum,"list");
		WebUtils.setRequestAttr("finanDeptSum", finanDeptSum);
		WebUtils.setRequestAttr("finanSumList", finanSumList);
		WebUtils.setRequestAttr("org1Code", WebHelp.getLoginUser().getOrg1Code());
		ReturnLinkUtils.setShowLink("budgetTmpLvl2SumList");
		ReturnLinkUtils.addReturnLink("secondList", "返回二级行汇总明细列表");
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/budget/finandeptsum/budgetTmpLvl2SumList.do?VISIT_FUNC_ID=020402");
		return BASE_URL + "selSumList2";
	}
	/**==============================================================================
	 * 二级汇总明细
	 * @param bean
	 * @return
	 */
	@RequestMapping("secondView.do")
	public String secondView(FinanDeptSumBean bean){
		//查询申报明细
		List<FinanDeptSumBean> list = fiDeptService.getSbList(bean);
		WebUtils.setRequestAttr("list", list);
		WebUtils.setRequestAttr("bean", bean);
		ReturnLinkUtils.setShowLink("secondList");
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/budget/finandeptsum/secondList.do?VISIT_FUNC_ID=02040201&tmpltId="+bean.getTmpltId());
		return BASE_URL +"selSumView2";
	}
	/**==============================================================================
	 * 二级行提交
	 * @param bean
	 * @return
	 */
	@RequestMapping("secondSubmit.do")
	public String secondSubmit(FinanDeptSumBean bean){
		if(fiDeptService.secondSubmit(bean)){
			ReturnLinkUtils.setShowLink("secondList");
			WebUtils.getMessageManager().addInfoMessage(" 提交成功");
			return ForwardPageUtils.getSuccessPage();
		}else {
			ReturnLinkUtils.setShowLink("secondList");
			WebUtils.getMessageManager().addInfoMessage(" 提交失败");
			return ForwardPageUtils.getErrorPage();
		}
	}
	/**==============================================================================
	 * 二级行下载
	 * @param bean
	 * @return
	 */
	@RequestMapping("download.do")
	public String download(FinanDeptSumBean bean){
		
		return null;
	}
	/**==============================================================================
	 * 一级汇总
	 * @param finanDeptSum
	 * @return
	 */
	@RequestMapping("firstList.do")
	public String firstList(FinanDeptSumBean finanDeptSum){
		List<FinanDeptSumBean> finanSumList = fiDeptService.firList(finanDeptSum,"list");
		WebUtils.setRequestAttr("finanDeptSum", finanDeptSum);
		WebUtils.setRequestAttr("finanSumList", finanSumList);
		WebUtils.setRequestAttr("org1Code", WebHelp.getLoginUser().getOrg1Code());
		ReturnLinkUtils.setShowLink("budgetTmpLvl1SumList");
		ReturnLinkUtils.addReturnLink("firstList", "返回一级行汇总明细列表");
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/budget/finandeptsum/budgetTmpLvl1SumList.do?VISIT_FUNC_ID=020403");
		return BASE_URL + "selSumList1";
	}
	/**==============================================================================
	 * 一级汇总明细
	 * @param bean
	 * @return
	 */
	@RequestMapping("firstView.do")
	public String firstView(FinanDeptSumBean bean){
		//查询申报明细
		List<FinanDeptSumBean> list = fiDeptService.getSbList(bean);
		WebUtils.setRequestAttr("list", list);
		WebUtils.setRequestAttr("bean", bean);
		ReturnLinkUtils.setShowLink("firstList");
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/budget/finandeptsum/firstList.do?VISIT_FUNC_ID=02040301&tmpltId="+bean.getTmpltId());
		return BASE_URL +"selSumView1";
	}
	
	/**
	 * 查询"已提交04"状态的模板列表，根据具体的模板进行本级汇总
	 * @param budget
	 * @return
	 */
	@RequestMapping("budgetTmpSumList.do")
	public String budgetTmpSumList(BudgetPlanBean budget){
		ReturnLinkUtils.addReturnLink("budgetTmpSumList", "返回本级行汇总模板列表");
		List<BudgetPlanBean> budgetTmpList = fiDeptService.budgetTmpSumList(budget);
		WebUtils.setRequestAttr("budgetTmpList", budgetTmpList);
		WebUtils.setRequestAttr("budgetBean", budget);
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		return BASE_URL + "budgetTmpSumList";
	}
	
	/**
	 * 二级汇总
	 * @param budget
	 * @return
	 */
	@RequestMapping("budgetTmpLvl2SumList.do")
	public String budgetTmpLvl2SumList(BudgetPlanBean budget){
		ReturnLinkUtils.addReturnLink("budgetTmpLvl2SumList", "返回二级行汇总模板列表");
		List<BudgetPlanBean> budgetTmpList = fiDeptService.budgetTmpSumList(budget);
		WebUtils.setRequestAttr("budgetTmpList", budgetTmpList);
		WebUtils.setRequestAttr("budgetBean", budget);
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		return BASE_URL + "budgetTmpSumList2";
	}
	
	/**
	 * 一级汇总
	 * @param budget
	 * @return
	 */
	@RequestMapping("budgetTmpLvl1SumList.do")
	public String budgetTmpLvl1SumList(BudgetPlanBean budget){
		ReturnLinkUtils.addReturnLink("budgetTmpLvl1SumList", "返回一级行汇总模板列表");
		List<BudgetPlanBean> budgetTmpList = fiDeptService.budgetTempLvl1SumList(budget);
		WebUtils.setRequestAttr("budgetTmpList", budgetTmpList);
		budget.setSumLvl("1");
		WebUtils.setRequestAttr("budgetBean", budget);
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		return BASE_URL + "budgetTmpSumList1";
	}
	/**
	 * 下载==============================================================================
	 * @param budget
	 * @return
	 */
	@RequestMapping("tmpltDetailDown.do")
	@ResponseBody
	public String tmpltDetailDown(FinanDeptSumBean budget) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		//Excel导出操作
		try {
			budget.setDutyCode(WebHelp.getLoginUser().getDutyCode());
			fiDeptService.firstDown( budget);
			jsonObject.put("pass", true);
		} catch (Exception e) {
			jsonObject.put("pass", false);
			e.printStackTrace();
		}
		return jsonObject.writeValueAsString();
	}
	
}
