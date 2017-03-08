package com.forms.prms.web.pay.icmsedit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.contract.query.domain.QueryContract;
import com.forms.prms.web.pay.icmsedit.domain.IcmsEditBean;
import com.forms.prms.web.pay.icmsedit.service.IcmsEditService;
import com.forms.prms.web.pay.payAdd.domain.PayAddBean;
import com.forms.prms.web.pay.payAdd.service.PayAddService;
import com.forms.prms.web.pay.payquery.service.PayQueryService;
import com.forms.prms.web.util.ForwardPageUtils;

/**
 * 
 */
@Controller
@RequestMapping("/pay/icmsedit/")
public class IcmsEditController {
	
	private static final String ICMSEDITPATH = "/pay/icmsedit/";
	@Autowired
	private IcmsEditService icmsEditService;
	
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
	@AddReturnLink(id = "icmsList", displayValue = "影像编辑申请列表查询")
	public String payList(IcmsEditBean icmsEditBean) {
		String org1Code = WebHelp.getLoginUser().getOrg1Code();// 得到登录人的所在的一级分行
		List<IcmsEditBean> icmsList = icmsEditService.icmsEditList(icmsEditBean);
		//List<IcmsEditBean> ouCodeList = icmsEditService.ouCodeList(org1Code);// 得到登录人的ouCode集合
		WebUtils.setRequestAttr("icmsList", icmsList);
		WebUtils.setRequestAttr("selectInfo", icmsEditBean);
		WebUtils.setRequestAttr("org1Code", org1Code);
		//WebUtils.setRequestAttr("ouCodeList", ouCodeList);
		return ICMSEDITPATH + "list";
	}

	/**
	 * 得到预付款或者付款的的详细信息
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("detail.do")
	public String detail(IcmsEditBean bean) {
		String isPrePay = "";
		List<IcmsEditBean> payDeviceList = null;
		List<IcmsEditBean> prePayDeviceList = null;
		List<IcmsEditBean> prePayCancleList = null;
		if("0".equals(bean.getIsCreditNote())){
			PayAddBean payAddBean = new PayAddBean();
			payAddBean.setInvoiceIdBlue(bean.getInvoiceIdBlue());
			
			// 1.原蓝字发票头信息查询
			PayAddBean invoiceBlue = payAddService.queryInvoiceBlue(payAddBean);
			
			// 2.查询贷项通知单头信息
			bean = icmsEditService.getEditByPayId(bean.getPayId());

			// 3.查询贷项通知单发票行信息
			payDeviceList = icmsEditService.getEditDeviceListByPayId(bean.getPayId());
			
			//根据合同号得到合同的影像
			QueryContract cnt = payQueryService.getCntICMSByCntNum(bean.getCntNum());
						
			WebUtils.setRequestAttr("cnt", cnt);
			WebUtils.setRequestAttr("invoiceBlue", invoiceBlue);
			WebUtils.setRequestAttr("payInfo", bean);
			WebUtils.setRequestAttr("payDevices", payDeviceList);
			WebHelp.setLastPageLink("uri", "list");		
			WebHelp.setLastPageLink("contracturi", "");
			
			return ICMSEDITPATH + "payCreditDetail";
		}
		else{
			//根据合同号得到合同的影像
			QueryContract cnt = payQueryService.getCntICMSByCntNum(bean.getCntNum());
			WebUtils.setRequestAttr("cnt", cnt);
			if ("Y".equalsIgnoreCase(bean.getIsPrePay())) {
				bean = icmsEditService.getPreEditByPayId(bean.getPayId());
				isPrePay = "prePayDetail";
			} else {
				bean = icmsEditService.getEditByPayId(bean.getPayId());
				payDeviceList = icmsEditService.getEditDeviceListByPayId(bean.getPayId());
				prePayDeviceList = icmsEditService.getPreEditDeviceListByPayId(bean.getPayId());
				prePayCancleList = icmsEditService.getPreEditCancleListByCntNum(bean);
				isPrePay = "payDetail";
			}
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
					+ "/pay/icmsedit/list.do?VISIT_FUNC_ID=03030903");
			WebUtils.setRequestAttr("payInfo", bean);
			WebUtils.setRequestAttr("payDeviceList", payDeviceList);
			WebUtils.setRequestAttr("prePayDeviceList", prePayDeviceList);
			WebUtils.setRequestAttr("prePayCancleList", prePayCancleList);
			
			return ICMSEDITPATH + isPrePay;
		}
		
	}
	
}
