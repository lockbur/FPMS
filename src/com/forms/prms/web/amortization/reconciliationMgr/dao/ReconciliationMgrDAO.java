package com.forms.prms.web.amortization.reconciliationMgr.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.amortization.reconciliationMgr.domain.AdvanceReconlieBean;
import com.forms.prms.web.amortization.reconciliationMgr.domain.ApReconlieBean;
import com.forms.prms.web.amortization.reconciliationMgr.domain.GlAccountReconciliationBean;
import com.forms.prms.web.amortization.reconciliationMgr.domain.PoOrderReconlieBean;

@Repository
public interface ReconciliationMgrDAO {

	/**
	 * AP发票及付款
	 */
	public List<ApReconlieBean> getApReconlieInfo(ApReconlieBean apBean);
	
	/**
	 * GL经费总账
	 */
	public List<GlAccountReconciliationBean> getGlAccountReconlieInfo(GlAccountReconciliationBean glBean);
	
	/**
	 * PO采购订单
	 */
	public List<PoOrderReconlieBean> getPoOrderReconlieInfo(PoOrderReconlieBean poOrderBean);
	
	/**
	 * 预付款核销
	 */
	public List<AdvanceReconlieBean> getAdvanceReconlieInfo(AdvanceReconlieBean advanceBean);
}
