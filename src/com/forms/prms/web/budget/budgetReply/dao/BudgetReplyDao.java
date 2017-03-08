package com.forms.prms.web.budget.budgetReply.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.budget.budgetReply.domain.BudgetReplyBean;

@Repository
public interface BudgetReplyDao {
	
	public List<BudgetReplyBean> list(BudgetReplyBean budgetReplyBean);
	
	public BudgetReplyBean getOneTemp(@Param("tmpltId") String tmpltId);

	public List<BudgetReplyBean> getMont(BudgetReplyBean budgetReplyBean);

	public void mergeToDetail(BudgetReplyBean budgetReplyBean);

	public void mergeToHeader(BudgetReplyBean budgetReplyBean);
	
	public String getTmpltHaveSplit(BudgetReplyBean budgetReplyBean);
	public String getMontHaveSplit(BudgetReplyBean budgetReplyBean);

}
