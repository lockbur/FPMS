package com.forms.prms.web.budget.budgetplan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.budget.budgetplan.domain.BudgetPlanBean;
import com.forms.prms.web.budget.budgetplan.domain.BudgetTempDetailBean;
import com.forms.prms.web.budget.budgetplan.domain.BudgetTmpltDutyBean;
import com.forms.prms.web.budget.budgetplan.domain.TaskLoadBean;
import com.forms.prms.web.budget.budgetplan.domain.ZiChanBudgetDetailBean;

@Repository
public interface BudgetPlanDAO {
	
	//新增预算模板
	public void addBudgetPlan(BudgetPlanBean budgetPlan);
	
	//新增预算模板与可用责任中心关系
	public void addBudgetDuty(BudgetTmpltDutyBean budgetRelDuty);
	
	//删除预算模板
	public void deleteBudgetPlan(String budgetPlanId);
	
	//删除预算模板与可用责任中心关系
	public void deleteBudgetRel(String budgetPlanId);
	
	//删除预算模板关联的Excel数据
	public void deleteBudgetDetailInfo(String budgetPlanId);
	
	//更新Excel导入任务流水的状态(TD_TASK_LOAD表中的dataFlag)
	public void updateTaskLoadStatus(TaskLoadBean taskLoad);
	
	//更新预算模板的状态
	public void updateBudgetStatus(BudgetPlanBean budgetPlan);
	
	//提交预算模板(可开始预算申报)
	public void submitBudget(BudgetPlanBean budget);
	
	//根据条件查询预算模板列表
	public List<BudgetPlanBean> list(BudgetPlanBean budget);
	
	//查看指定的预算模板
	public BudgetPlanBean view(String budgetId);
	
	//修改预算模板信息(备注等)
	public void updateBudgetPlan(BudgetPlanBean budget);
		
	//根据ID查找预算模板
	public BudgetPlanBean getBudgetById(@Param("budgetId") String budgetId);
	
	//查找指定预算模板的可用责任中心列表
	public List<BudgetTmpltDutyBean> getBudgetOrgs(@Param("budgetId") String budgetId);
	
	//【不再使用】将导入Excel(资产类预算模板)中数据保存到数据库
	public void insertZCTempDetail(ZiChanBudgetDetailBean ziChanDetail);
	
	//将导入Excel中数据保存到数据库(包括资产和费用类)
	public void insertBudgetTempDetail(BudgetTempDetailBean budgetTempDetail);
	
	//检查责任中心的年初预算数量
	public int checkYearFirstBudgetPlan(BudgetPlanBean budget);
	
	//查询预算模板导入Excel头部信息
	public List<BudgetTempDetailBean> getBudgetPlanHeaderDetail(@Param("tmpltId") String tmpltId);
	
	//查询预算模板导入Excel实体数据的详情(预算模板修改详情页面中使用，参数为：预算模板ID、物料归口部门)
	public List<BudgetTempDetailBean> getBudgetPlanBodyDetail(@Param("tmpltId") String tmpltId,@Param("matrAuditDept") String matrAuditDept);
	
	//	暂时不用的方法
	//	public void addTask(TaskLoadBean taskLoad);
	//	public void addTaskTempRel(TaskTempBean taskTemp);
	
	//查询基础模板导出中，预算监控指标+物料编码+物料名称信息
	public List<BudgetTempDetailBean> exportBasicBudgetInfo(@Param("org21Code")String org21Code , @Param("montType")String montType);
	
	//模板制作完成后，导出模板查询的信息(dutyCode=当前用户责任中心，tmpltId=查询对应的模板ID)
	public List<BudgetTempDetailBean> getBudgetTmpExcelExpInfo (@Param("dutyCode")String dutyCode , @Param("tmpltId")String tmpltId);
	
	//Ajax校验是否存在监控指标未维护(并返回未维护个数)
	public int ajaxCountDisMaintainMont(@Param("org21Code")String org21Code , @Param("montType")String montType);
	
}
