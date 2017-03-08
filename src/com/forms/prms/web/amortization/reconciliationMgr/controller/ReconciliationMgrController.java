package com.forms.prms.web.amortization.reconciliationMgr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.web.WebUtils;
import com.forms.prms.web.amortization.reconciliationMgr.domain.AdvanceReconlieBean;
import com.forms.prms.web.amortization.reconciliationMgr.domain.ApReconlieBean;
import com.forms.prms.web.amortization.reconciliationMgr.domain.GlAccountReconciliationBean;
import com.forms.prms.web.amortization.reconciliationMgr.domain.PoOrderReconlieBean;
import com.forms.prms.web.amortization.reconciliationMgr.service.ReconciliationMgrService;

/**
 * Title:		ReconciliationMgrController
 * Description:	ERP系统与FMS系统进行交互时的对账信息管理模块，Controller层
 * Copyright: 	formssi
 * @author: 	HQQ
 * @project: 	ERP
 * @date: 		2015-05-08
 * @version: 	1.0
 */
@Controller
@RequestMapping("/amortization/reconciliationMgr")
public class ReconciliationMgrController {
	
	private final String BASE_URL = "amortization/reconciliationMgr/";
	
	@Autowired
	public ReconciliationMgrService reconService;
	
	/**
	 * @methodName apReconlie
	 * 		描述：AP发票以及付款在ERP和FMS系统中关键对账信息
	 * @param apBean
	 * @return
	 */
	@RequestMapping("/apReconlie.do")
	public String apReconlie(ApReconlieBean apBean){
		System.out.println("【HQQ--GetValue:】"+apBean.getCntNum()+"----"+apBean.getPayId());
		List<ApReconlieBean> apList = reconService.getApReconInfo(apBean);
		WebUtils.setRequestAttr("apBean", apBean);
		WebUtils.setRequestAttr("apList", apList);
		return BASE_URL + "apreconlielist";
	}
	
	/**
	 * @methodName glAccountReconlie
	 * 		描述：GL经费总账在ERP和FMS系统中关键对账信息
	 * @return
	 */
	@RequestMapping("/glAccountReconlie.do")
	public String glAccountReconlie(GlAccountReconciliationBean glBean){
		List<GlAccountReconciliationBean> glList = reconService.getglAccountReconInfo(glBean);
		WebUtils.setRequestAttr("glBean", glBean);
		WebUtils.setRequestAttr("glList", glList);
		return BASE_URL + "glaccountreconlielist";
	}
	
	/**
	 * @methodName poOrderReconlie
	 * 		描述：PO采购订单在ERP和FMS系统中关键对账信息
	 * @return
	 */
	@RequestMapping("/poOrderReconlie.do")
	public String poOrderReconlie(PoOrderReconlieBean poOrderBean){
		List<PoOrderReconlieBean> poList = reconService.getPoOrderReconInfo(poOrderBean);
		WebUtils.setRequestAttr("poOrderBean", poOrderBean);
		WebUtils.setRequestAttr("poList", poList);
		return BASE_URL + "poorderreconlielist";
	}
	
	/**
	 * @methodName advanceReconlie
	 * 		描述：预付款核销在ERP和FMS系统中关键对账信息
	 * @return
	 */
	@RequestMapping("/advanceReconlie.do")
	public String advanceReconlie(AdvanceReconlieBean advanceBean){
		List<AdvanceReconlieBean> advanceList = reconService.getAdvanceReconInfo(advanceBean);
		WebUtils.setRequestAttr("advanceBean", advanceBean);
		WebUtils.setRequestAttr("advanceList", advanceList);
		return BASE_URL + "advancereconlielist";
	}
	
}



