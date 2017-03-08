package com.forms.prms.web.budget.budgetInput.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.budget.budgetInput.domain.BudgetInputBean;

@Repository
public interface BudgetInputDao {
	/**
	 * 提交列表
	 * @param bean
	 * @return
	 */
	List<BudgetInputBean> selectAllBudget(BudgetInputBean bean);
	/**
	 * 得到头信息
	 * @param bean
	 * @return
	 */
	List<BudgetInputBean> getHeadMsg(BudgetInputBean bean);
	/**
	 * 得到主体信息
	 * @param tmpltId
	 * @return
	 */
	List<BudgetInputBean> getListMsg(BudgetInputBean bean);
	/**
	 * 删除申报头信息
	 * @param bean
	 * @return
	 */
	int delWriteHead(BudgetInputBean bean);
	/**
	 * 删除申报明细
	 * @param bean
	 * @return
	 */
	int delWriteDetail(BudgetInputBean bean);
	/**
	 * 提交
	 * @param bean
	 * @return
	 */
	int input(BudgetInputBean bean);
	/**
	 * 增加审核表
	 * @param bean
	 * @return
	 */
	int insertAudit(BudgetInputBean bean);

}
