package com.forms.prms.web.budget.firstaudit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.web.budget.firstaudit.dao.FirstAuditDAO;
import com.forms.prms.web.budget.firstaudit.domain.BudgetDetailBean;
import com.forms.prms.web.budget.firstaudit.domain.FirstAuditBean;

@Service
public class FirstAuditServcie {
	@Autowired
	private FirstAuditDAO dao;
	
	
	/**
	 * 新增预算申报的模板查询
	 * @param budgetDeclareBean
	 * @return
	 */
	public List<FirstAuditBean> queryTemp(FirstAuditBean budgetDeclareBean) {
		FirstAuditDAO pageDAO = PageUtils.getPageDao(dao);
		return pageDAO.queryTemp(budgetDeclareBean);
	}
	
	
	public List<FirstAuditBean> getBudgetGeneralList(FirstAuditBean budgetDeclareBean){
		FirstAuditDAO pageDAO = PageUtils.getPageDao(dao);
		return pageDAO.getBudgetGeneralList(budgetDeclareBean);
	}
	
	
	public List<BudgetDetailBean> getBudgetDetailList(FirstAuditBean budgetDeclareBean){
		FirstAuditDAO pageDAO = PageUtils.getPageDao(dao);
		return pageDAO.getBudgetDetailList(budgetDeclareBean);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void updateAuditAmt(FirstAuditBean budgetDeclareBean){
		CommonLogger.info("预算初审模块-预算初审提交操作,主键信息("+budgetDeclareBean.getTmpltId()+"),FirstAuditServcie,updateAuditAmt");
		for(int i=0;i<budgetDeclareBean.getMatrCodes().length;i++){
			budgetDeclareBean.setMatrCode(budgetDeclareBean.getMatrCodes()[i]);
			budgetDeclareBean.setAuditAmt(budgetDeclareBean.getAuditAmts()[i]);
			dao.updateAuditAmt(budgetDeclareBean);
			dao.insertAuditLog(budgetDeclareBean);
		}
		
	}
	
	public List<FirstAuditBean> view(FirstAuditBean viewBean)
	{
		FirstAuditDAO vDAO=PageUtils.getPageDao(dao);
		return vDAO.view(viewBean);
	}
	
	@SuppressWarnings("unused")
	public String update(FirstAuditBean fab){
		CommonLogger.info("预算初审模块-提交审批金额操作,主键信息("+fab.getTmpltId()+"),FirstAuditServcie,update");
		fab.setAuditAmt(fab.getAuditAmt());
		fab.setTmpltId(fab.getTmpltId());
		fab.setDutyCode(fab.getDutyCode());
		fab.setMatrCode(fab.getMatrCode());
		fab.setAuditFlag(fab.getAuditFlag());
		dao.update(fab);
		if (fab==null) {
			return "error";
		}
			return "ok";
	}
	
}
