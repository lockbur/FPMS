package com.forms.prms.web.pay.icmsapply.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.contract.query.domain.QueryContract;
import com.forms.prms.web.pay.icmsapply.domain.IcmsApplyBean;
import com.forms.prms.web.pay.icmsapply.service.IcmsApplyService;
import com.forms.prms.web.pay.payAdd.domain.PayAddBean;
import com.forms.prms.web.pay.payAdd.service.PayAddService;
import com.forms.prms.web.pay.payquery.service.PayQueryService;
import com.forms.prms.web.util.ForwardPageUtils;

/**
 * 
 */
@Controller
@RequestMapping("/pay/icmsapply/")
public class IcmsApplyController {
	
	private static final String ICMSAPPLYPATH = "/pay/icmsapply/";
	@Autowired
	private IcmsApplyService icmsApplyService;
	
	@Autowired
	private PayQueryService payQueryService;
	@Autowired
	private PayAddService payAddService;
	/**
	 * 合同待付款信息列表查询
	 * 
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("list.do")
	@AddReturnLink(id = "icmsList", displayValue = "返回列表")
	public String payList(IcmsApplyBean icmsApplyBean) {
		String org1Code = WebHelp.getLoginUser().getOrg1Code();// 得到登录人的所在的一级分行
		List<IcmsApplyBean> icmsList = icmsApplyService.icmsApplyList(icmsApplyBean);
//		List<IcmsApplyBean> ouCodeList = icmsApplyService.ouCodeList(org1Code);// 得到登录人的ouCode集合
		WebUtils.setRequestAttr("icmsList", icmsList);
		WebUtils.setRequestAttr("selectInfo", icmsApplyBean);
		WebUtils.setRequestAttr("org1Code", org1Code);
//		WebUtils.setRequestAttr("ouCodeList", ouCodeList);
		return ICMSAPPLYPATH + "list";
	}

	/**
	 * 得到预付款或者付款的的详细信息
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("detail.do")
	public String detail(IcmsApplyBean bean) {
		String isPrePay = "";
		List<IcmsApplyBean> payDeviceList = null;
		List<IcmsApplyBean> prePayDeviceList = null;
		List<IcmsApplyBean> prePayCancleList = null;
		if("0".equals(bean.getIsCreditNote())){
			PayAddBean payAddBean = new PayAddBean();
			payAddBean.setInvoiceIdBlue(bean.getInvoiceIdBlue());
			
			// 1.原蓝字发票头信息查询
			PayAddBean invoiceBlue = payAddService.queryInvoiceBlue(payAddBean);
			
			// 2.查询贷项通知单头信息
			bean = icmsApplyService.getApplyByPayId(bean.getPayId());

			// 3.查询贷项通知单发票行信息
			payDeviceList = icmsApplyService.getApplyDeviceListByPayId(bean.getPayId());
			
			//根据合同号得到合同的影像
			QueryContract cnt = payQueryService.getCntICMSByCntNum(bean.getCntNum());
						
			WebUtils.setRequestAttr("cnt", cnt);
			WebUtils.setRequestAttr("invoiceBlue", invoiceBlue);
			WebUtils.setRequestAttr("payInfo", bean);
			WebUtils.setRequestAttr("payDevices", payDeviceList);
			WebHelp.setLastPageLink("uri", "list");		
			WebHelp.setLastPageLink("contracturi", "");
			
			return ICMSAPPLYPATH + "payCreditDetail";
		}
		else{
			//根据合同号得到合同的影像
			QueryContract cnt = payQueryService.getCntICMSByCntNum(bean.getCntNum());
			WebUtils.setRequestAttr("cnt", cnt);
			if ("Y".equalsIgnoreCase(bean.getIsPrePay())) {
				bean = icmsApplyService.getPreApplyByPayId(bean.getPayId());
				isPrePay = "prePayDetail";
			} else {
				bean = icmsApplyService.getApplyByPayId(bean.getPayId());
				payDeviceList = icmsApplyService.getApplyDeviceListByPayId(bean.getPayId());
				prePayDeviceList = icmsApplyService.getPreApplyDeviceListByPayId(bean.getPayId());
				prePayCancleList = icmsApplyService.getPreApplyCancleListByCntNum(bean);
				isPrePay = "payDetail";
			}
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
					+ "/pay/icmsapply/list.do?VISIT_FUNC_ID=03030609");
			WebUtils.setRequestAttr("payInfo", bean);
			WebUtils.setRequestAttr("payDeviceList", payDeviceList);
			WebUtils.setRequestAttr("prePayDeviceList", prePayDeviceList);
			WebUtils.setRequestAttr("prePayCancleList", prePayCancleList);
			
			return ICMSAPPLYPATH + isPrePay;
		}
		
	}
	
	/**
	 * 申请编辑通过或者撤销申请通过
	 * @param approve
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("agree.do")
	public String Agree(IcmsApplyBean bean) throws Exception {
		int cnt;
		if ("Y".equalsIgnoreCase(bean.getIsPrePay())) {
			cnt = icmsApplyService.agreePreApply(bean);
		} else {
			cnt = icmsApplyService.agreeApply(bean);
		}
		if (cnt != 1) {
			WebUtils.getMessageManager().addInfoMessage("付款单号"+bean.getPayId()+"已经被其他人员"+("1".equals(bean.getIcmsEdit())?"撤销申请":"申请编辑"));
			return ForwardPageUtils.getErrorPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("1".equals(bean.getIcmsEdit())?"撤销申请成功!":"申请编辑成功!");
			ReturnLinkUtils.setShowLink("icmsList");
			return ForwardPageUtils.getSuccessPage();
		}
	}

}
