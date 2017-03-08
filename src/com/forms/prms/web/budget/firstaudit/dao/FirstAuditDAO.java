package com.forms.prms.web.budget.firstaudit.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.budget.firstaudit.domain.BudgetDetailBean;
import com.forms.prms.web.budget.firstaudit.domain.FirstAuditBean;

@Repository
public interface FirstAuditDAO {
	public List<FirstAuditBean> queryTemp(FirstAuditBean budgetDeclareBean);
	
	public List<FirstAuditBean> getBudgetGeneralList(FirstAuditBean budgetDeclareBean);

	public List<BudgetDetailBean> getBudgetDetailList(FirstAuditBean budgetDeclareBean);
	
	public void updateAuditAmt(FirstAuditBean budgetDeclareBean);
	
	public void insertAuditLog(FirstAuditBean budgetDeclareBean);

	public List<FirstAuditBean> view(FirstAuditBean viewBean);

    public void update(FirstAuditBean fab); 
}
