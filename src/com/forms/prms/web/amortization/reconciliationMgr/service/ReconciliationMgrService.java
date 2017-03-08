package com.forms.prms.web.amortization.reconciliationMgr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.amortization.reconciliationMgr.dao.ReconciliationMgrDAO;
import com.forms.prms.web.amortization.reconciliationMgr.domain.AdvanceReconlieBean;
import com.forms.prms.web.amortization.reconciliationMgr.domain.ApReconlieBean;
import com.forms.prms.web.amortization.reconciliationMgr.domain.GlAccountReconciliationBean;
import com.forms.prms.web.amortization.reconciliationMgr.domain.PoOrderReconlieBean;

@Service
public class ReconciliationMgrService {

	@Autowired
	public ReconciliationMgrDAO reconDao;
	
	/**
	 *	查询ERP与FMS系统交互中AP发票和付款的对账信息 
	 */
	public List<ApReconlieBean> getApReconInfo(ApReconlieBean apBean){
		ReconciliationMgrDAO pageDao = PageUtils.getPageDao(reconDao);
		String org1Code = WebHelp.getLoginUser().getOrg1Code();// 得到登录人所在的一级行
		apBean.setOrg1Code(org1Code);
		return pageDao.getApReconlieInfo(apBean);
	}
	
	/**
	 *	查询ERP与FMS系统交互中GL经费总账的对账信息 
	 */
	public List<GlAccountReconciliationBean> getglAccountReconInfo(GlAccountReconciliationBean glBean){
		ReconciliationMgrDAO pageDao = PageUtils.getPageDao(reconDao);
		String org1Code = WebHelp.getLoginUser().getOrg1Code();// 得到登录人所在的一级行
		glBean.setOrg1Code(org1Code);
		return pageDao.getGlAccountReconlieInfo(glBean);
	}
	
	/**
	 *	查询ERP与FMS系统交互中PO采购订单的对账信息 
	 */
	public List<PoOrderReconlieBean> getPoOrderReconInfo(PoOrderReconlieBean poOrderBean){
		ReconciliationMgrDAO pageDao = PageUtils.getPageDao(reconDao);
		String org1Code = WebHelp.getLoginUser().getOrg1Code();// 得到登录人所在的一级行
		poOrderBean.setOrg1Code(org1Code);
		return pageDao.getPoOrderReconlieInfo(poOrderBean);
	}
	
	/**
	 *	查询ERP与FMS系统交互中预付款核销的对账信息 
	 */
	public List<AdvanceReconlieBean> getAdvanceReconInfo(AdvanceReconlieBean advanceBean){
		ReconciliationMgrDAO pageDao = PageUtils.getPageDao(reconDao);
		String org1Code = WebHelp.getLoginUser().getOrg1Code();// 得到登录人所在的一级行
		advanceBean.setOrg1Code(org1Code);
		return pageDao.getAdvanceReconlieInfo(advanceBean);
	}
}
