package com.forms.prms.web.budget.finandeptsum.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.budget.budgetplan.domain.BudgetPlanBean;
import com.forms.prms.web.budget.finandeptsum.domain.FinanDeptSumBean;

@Repository
public interface FinanDeptDAO {
	/**
	 * 年初预算本级汇总
	 * @param finanDeptSum
	 * @return
	 */
	public List<FinanDeptSumBean> selList(FinanDeptSumBean finanDeptSum);
	/**
	 * 追加预算本级汇总
	 * @param finanDeptSum
	 * @return
	 */
	public List<FinanDeptSumBean> selAddList(FinanDeptSumBean finanDeptSum);
	
	public List<FinanDeptSumBean> secondList(FinanDeptSumBean finanDeptSum);
	
	public List<FinanDeptSumBean> firList(FinanDeptSumBean finanDeptSum);
	
	public List<BudgetPlanBean> budgetTempSumList(BudgetPlanBean budget);
	
	public List<BudgetPlanBean> budgetTempLvl1SumList(BudgetPlanBean budget);
	
	public List<FinanDeptSumBean> selectDept(@Param("dutyCode") String dutyCode);
	//================================
	/**
	 * 本级汇总明细
	 * @param bean
	 * @return
	 */
	public List<FinanDeptSumBean> getSbList(FinanDeptSumBean bean);
	/**
	 * 修改审核状态
	 * @param bean
	 * @return
	 */
	public int back(FinanDeptSumBean bean);
	/**
	 * 日志信息
	 * @param bean
	 * @return
	 */
	public int insertLog(FinanDeptSumBean bean);
	/**
	 * 本级提交
	 * @param bean
	 * @return
	 */
	public int submit(FinanDeptSumBean bean);
	/**
	 * 二级提交
	 * @param bean
	 * @return
	 */
	public int secondSubmit(FinanDeptSumBean bean);

	
}
