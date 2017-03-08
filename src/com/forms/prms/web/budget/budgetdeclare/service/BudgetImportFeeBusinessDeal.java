package com.forms.prms.web.budget.budgetdeclare.service;

import java.util.List;
import java.util.Map;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.excel.imports.bean.ExcelImportInfo;
import com.forms.platform.excel.imports.inter.IBusinessDeal;
import com.forms.prms.web.budget.budgetdeclare.domain.BudgetDeclareBean;
import com.forms.prms.web.budget.budgetdeclare.domain.FeeBudgetDetailBean;
import com.forms.prms.web.budget.budgetplan.domain.ExcelBean;

/**
 * 预算模板(费用类)Excel导入逻辑处理类
 * @author 	HQQ
 * @version 2015-02-05	19:36	
 */
public class BudgetImportFeeBusinessDeal implements IBusinessDeal{

	//获取预算处理Service
	BudgetDeclareService budgetService = BudgetDeclareService.getInstance();
	
	@Override
	public void start(String batchNo, String arg1, Map beans) throws Exception {
		//1.将汇总表的状态更新为【01:处理中】
		BudgetDeclareBean  budgetDeclareBean = new BudgetDeclareBean();
		budgetDeclareBean.setTmpltId((String)beans.get("tmpltId")); 
		budgetDeclareBean.setDutyCode((String)beans.get("dutyCode"));
		budgetDeclareBean.setDataFlag("01");
		budgetService.updateBudgetWriteHeader(budgetDeclareBean);
		//2.将bean放至线程，用于保存Excel导入数据
		ExcelBean<FeeBudgetDetailBean> bean = new ExcelBean<FeeBudgetDetailBean>();
		beans.put("bean", bean);
	}
	
	@Override
	public void end(String batchNo, String arg1, Map beans, ExcelImportInfo importInfo) throws Exception {
		CommonLogger.debug("HQQQ__:Into Class【BudgetTemplateFeeBusinessDeal】——[END]"+"【流水号："+batchNo+"】");
		
		//1.获取导入Excel的具体信息(bean)
		ExcelBean<FeeBudgetDetailBean> bean = (ExcelBean<FeeBudgetDetailBean>)beans.get("bean");

		//2.遍历具体的Excel内部Bean信息,并写到数据库
		List<FeeBudgetDetailBean> excelInfoList = bean.getTemplateInfo();
		
		BudgetDeclareBean budget = new BudgetDeclareBean();
		budget.setTmpltId((String)beans.get("tmpltId"));
		budget.setDutyCode((String)beans.get("dutyCode"));
		
		int i=1;
		for(FeeBudgetDetailBean dbean : excelInfoList){
			dbean.setRowSeq(""+i++);
		}
		budget.getFeeList().addAll(excelInfoList);
		
		budgetService.insertFeeBudgetDetail(budget);
		
		//3.将汇总表的状态更新为【03:处理完成】
		BudgetDeclareBean  budgetDeclareBean = new BudgetDeclareBean();
		budgetDeclareBean.setTmpltId((String)beans.get("tmpltId")); 
		budgetDeclareBean.setDutyCode((String)beans.get("dutyCode"));
		budgetDeclareBean.setDataFlag("03");
		budgetService.updateBudgetWriteHeader(budgetDeclareBean);
	}
}
