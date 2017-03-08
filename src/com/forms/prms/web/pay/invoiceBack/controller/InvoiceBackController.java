package com.forms.prms.web.pay.invoiceBack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.contract.query.domain.QueryContract;
import com.forms.prms.web.pay.invoiceBack.service.InvoiceBackService;
import com.forms.prms.web.pay.payAdd.domain.PayAddBean;
import com.forms.prms.web.pay.payAdd.service.PayAddService;
import com.forms.prms.web.pay.payquery.domain.PayQueryBean;
import com.forms.prms.web.pay.payquery.service.PayQueryService;
import com.forms.prms.web.pay.paysure.domain.PaySureBean;
import com.forms.prms.web.pay.paysure.service.PaySureService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/pay/invoiceback")
public class InvoiceBackController {
	
	private static final String BACKPATH = "pay/invoiceback/";
	@Autowired
	private InvoiceBackService invoiceBackService;
	
	@Autowired
	private PaySureService paySureService;
	@Autowired
	private PayAddService payAddService;
	@Autowired
	private PayQueryService payQueryService;
	/**
	 * 查询发票退回的信息
	 */
	@RequestMapping("list.do")
	@AddReturnLink(id = "list", displayValue = "发票退回的信息列表查询")
	public String list(PayQueryBean payQueryBean){
		WebUtils.setRequestAttr("list", invoiceBackService.list(payQueryBean));
		WebUtils.setRequestAttr("selectInfo", payQueryBean);
		WebUtils.setRequestAttr("org1Code", WebHelp.getLoginUser().getOrg1Code());
		return BACKPATH + "list";
	}
	
	/**
	 * 得到预付款或者付款的的详细信息
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("detail.do")
	public String detail(PaySureBean bean) {
		String isPrePay = "";
		List<PaySureBean> payDeviceList = null;
		List<PaySureBean> prePayDeviceList = null;
		List<PaySureBean> prePayCancleList = null;
		if("0".equals(bean.getIsCreditNote())){
			PayAddBean payAddBean = new PayAddBean();
			payAddBean.setInvoiceIdBlue(bean.getInvoiceIdBlue());
			
			// 1.原蓝字发票头信息查询
			PayAddBean invoiceBlue = payAddService.queryInvoiceBlue(payAddBean);
			
			// 2.查询贷项通知单头信息
			bean = paySureService.getPayByPayId(bean.getPayId());

			// 3.查询贷项通知单发票行信息
			payDeviceList = paySureService.getPayDeviceListByPayId(bean.getPayId());
			
			//根据合同号得到合同的影像
			QueryContract cnt = payQueryService.getCntICMSByCntNum(bean.getCntNum());
						
			WebUtils.setRequestAttr("cnt", cnt);
			WebUtils.setRequestAttr("invoiceBlue", invoiceBlue);
			WebUtils.setRequestAttr("payInfo", bean);
			WebUtils.setRequestAttr("payDevices", payDeviceList);
			WebHelp.setLastPageLink("uri", "list");		
			WebHelp.setLastPageLink("contracturi", "");
			
			return BACKPATH + "payCreditDetail";
		}
		else{
			if ("Y".equalsIgnoreCase(bean.getIsPrePay())) {
				bean = paySureService.getPrePayByPayId(bean.getPayId());
				isPrePay = "prePayDetail";
			} else {
				bean = paySureService.getPayByPayId(bean.getPayId());
				payDeviceList = paySureService.getPayDeviceListByPayId(bean.getPayId());
				prePayDeviceList = paySureService.getPrePayDeviceListByPayId(bean.getPayId());
				prePayCancleList = paySureService.getPrePayCancleListByCntNum(bean);
				isPrePay = "payDetail";
			}
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
					+ "/pay/invoiceback/list.do?VISIT_FUNC_ID=03030902");
			WebUtils.setRequestAttr("payInfo", bean);
			WebUtils.setRequestAttr("payDeviceList", payDeviceList);
			WebUtils.setRequestAttr("prePayDeviceList", prePayDeviceList);
			WebUtils.setRequestAttr("prePayCancleList", prePayCancleList);
			return BACKPATH + isPrePay;
		}
		
	}
	
	/**
	 * 退回处理
	 * @return
	 */
	@RequestMapping("backDeal.do")
	public String backDeal(PaySureBean bean) throws Exception{
		if("1".equals(bean.getDealFlag())){
			boolean flag = invoiceBackService.backToModify(bean);
			return ForwardPageUtils.getReturnUrlString("退回经办", flag,
					new String[] {"list"});
			
		}else{
			boolean flag = invoiceBackService.backToScan(bean);
			return ForwardPageUtils.getReturnUrlString("退回重新扫描", flag,
					new String[] {"list"});
		}
	}
}
