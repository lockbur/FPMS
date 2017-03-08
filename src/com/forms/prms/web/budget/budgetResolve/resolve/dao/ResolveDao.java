package com.forms.prms.web.budget.budgetResolve.resolve.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.budget.budgetResolve.resolve.domain.BudgetReplyedBean;
import com.forms.prms.web.budget.budgetResolve.resolve.domain.BudgetResolveBean;
import com.forms.prms.web.budget.budgetResolve.resolve.domain.Resolve;
import com.forms.prms.web.sysmanagement.matrtype.domain.MatrType;

@Repository
public interface ResolveDao {

	
	/**
	 * @param resolve
	 * @return
	 */
	public List<Resolve> getResolveList( Resolve resolve );
	
	
	
	/**
	 * 查询已进行预算批复的可分解列表
	 * @param budgetReply
	 * @return
	 */
	public List<BudgetReplyedBean> getBudgetReplyedList( BudgetReplyedBean budgetReplyed );
	
	/**
	 * 查找指定模板下指定监控指标的具体信息
	 * @param budgetReplyed
	 * @return
	 */
	public BudgetReplyedBean getBudReplyByTmpAndMont(@Param("tmpltId") String tmpltId , @Param("montCode") String montCode);
	
	
	
	public List<MatrType> getMatrListByMont(@Param("montCode") String montCode);
	
	//新增预算分解操作
	public int addBudgetResolve(BudgetResolveBean resolve);
	
	public List<BudgetResolveBean> getResolveDetailList( BudgetResolveBean resolve );
	
	//删除指定的预算分解操作
	public int deleteBudgetResolve(BudgetResolveBean resolve);
	
	//更新Reply表的已分解金额splited_Amt
	public void updateSplitedAmtInReplyTb( BudgetReplyedBean budgetReplyed );
	
	//用于测试SQL的写法
	public int testUpdateSettingParams(Map mapObj);
}
