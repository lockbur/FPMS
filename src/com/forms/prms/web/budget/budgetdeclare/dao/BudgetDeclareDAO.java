package com.forms.prms.web.budget.budgetdeclare.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.budget.budgetdeclare.domain.BudgetDeclareBean;
import com.forms.prms.web.budget.budgetdeclare.domain.ExcelImportTaskSummaryBean;
import com.forms.prms.web.budget.budgetdeclare.domain.StockBudgetBean;

@Repository
public interface BudgetDeclareDAO {
	
	public List<BudgetDeclareBean> queryTemp(BudgetDeclareBean budgetDeclareBean);
	
	public BudgetDeclareBean queryTempById(@Param("tempId") String tempId);
	
	public void insertBudgetWriteHeader(BudgetDeclareBean budgetDeclareBean);
	
	public void updateBudgetWriteHeader(BudgetDeclareBean budgetDeclareBean);
	
	public ExcelImportTaskSummaryBean getTaskSummary(@Param("taskId") String taskId);
	
	public void insertFeeBudgetDetail(BudgetDeclareBean budgetDeclareBean);
	
	public void insertAssetBudgetDetail(BudgetDeclareBean budgetDeclareBean);
	
	public List<StockBudgetBean> getUnsignContList(StockBudgetBean stockBudgetBean);
	
	public List<StockBudgetBean> getUnexecuteList(StockBudgetBean stockBudgetBean);
	
	public void confirmBudget(StockBudgetBean stockBudgetBean);

	public void addFirstAudit(StockBudgetBean sBudgetBean);
	

}
