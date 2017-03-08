package com.forms.prms.web.pay.paysure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.contract.query.domain.QueryContract;
import com.forms.prms.web.monthOver.service.MonthOverService;
import com.forms.prms.web.pay.payAdd.domain.PayAddBean;
import com.forms.prms.web.pay.payAdd.service.PayAddService;
import com.forms.prms.web.pay.payquery.service.PayQueryService;
import com.forms.prms.web.pay.paysure.domain.PaySureBean;
import com.forms.prms.web.pay.paysure.service.PaySureService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/pay/paysure")
public class PaySureController {
	private static final String PAYSUREPATH = "/pay/paysure/";
	@Autowired
	private PaySureService paySureService;
	@Autowired
	private PayAddService payAddService;

	@Autowired
	private MonthOverService monthOverService;
	
	@Autowired
	private PayQueryService payQueryService;

	/**
	 * 合同待确认信息列表查询
	 * 
	 * @param PaySureBean
	 * @return
	 */
	@RequestMapping("list.do")
	public String list(PaySureBean bean) {
		ReturnLinkUtils.addReturnLink("list", "返回列表");
		List<PaySureBean> list = paySureService.getList(bean);// 预付款和付款集合
		String org1Code = WebHelp.getLoginUser().getOrg1Code();// 得到登录人的所在的一级分行
		List<PaySureBean> ouCodeList = paySureService.ouCodeList(org1Code);// 得到登录人的ouCode集合
		// 拿到月结的状态
		String monDataFlag = monthOverService.getMaxDataFlag();
		WebUtils.setRequestAttr("monDataFlag", monDataFlag);
		
		//根据合同号查询TI_TRADE_BACKWASH是否有数据
		String id = payAddService.getIdByCntNum(bean.getCntNum());
		if(Tool.CHECK.isBlank(id)){
			WebUtils.setRequestAttr("flag", "1");
		}else{
			WebUtils.setRequestAttr("flag", "2");
		}
		
		WebUtils.setRequestAttr("list", list);
		WebUtils.setRequestAttr("selectInfo", bean);
		WebUtils.setRequestAttr("org1Code", org1Code);
		WebUtils.setRequestAttr("ouCodeList", ouCodeList);
		return PAYSUREPATH + "list";
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
			
			return PAYSUREPATH + "payCreditDetail";
		}
		else{
			//根据合同号得到合同的影像
			QueryContract cnt = payQueryService.getCntICMSByCntNum(bean.getCntNum());
			WebUtils.setRequestAttr("cnt", cnt);
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
					+ "/pay/paysure/list.do?VISIT_FUNC_ID=03030601");
			WebUtils.setRequestAttr("payInfo", bean);
			WebUtils.setRequestAttr("payDeviceList", payDeviceList);
			WebUtils.setRequestAttr("prePayDeviceList", prePayDeviceList);
			WebUtils.setRequestAttr("prePayCancleList", prePayCancleList);
			return PAYSUREPATH + isPrePay;
		}
		
	}

	/**
	 * 
	 * @param approve
	 * @return
	 */
	@RequestMapping("agree.do")
	public String Agree(PaySureBean bean) {
		if ("Y".equalsIgnoreCase(bean.getIsPrePay())) {
			paySureService.agreePrePay(bean);
		} else {
			paySureService.agreePay(bean);
		}
		WebUtils.getMessageManager().addInfoMessage("付款确认完成!");
		ReturnLinkUtils.setShowLink("list");
		return ForwardPageUtils.getSuccessPage();
	}

	/**
	 * 
	 * @param approve
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("back.do")
	public String back(PaySureBean bean) throws Exception {
		if ("Y".equalsIgnoreCase(bean.getIsPrePay())) {
			paySureService.backPrePay(bean);
		} else {
			paySureService.backPay(bean);
		}
		WebUtils.getMessageManager().addInfoMessage("退回成功!");
		ReturnLinkUtils.setShowLink("list");
		return ForwardPageUtils.getSuccessPage();
	}

	/**
	 * 根据预付款单号查预付款核销明细
	 * 
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("queryPayAdvCancelDetail.do")
	public String queryPayAdvCancelDetail(PayAddBean bean) {
		PayAddBean payAdvCancelInfo = payAddService.queryPayAdvCancelDetail(bean);
		WebUtils.setRequestAttr("payAdvCancelInfo", payAdvCancelInfo);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
				+ "/pay/paysure/detail.do?VISIT_FUNC_ID=03030602&isPrePay=N&payId=" + bean.getPayId() + "&cntNum="
				+ bean.getCntNum());
		return PAYSUREPATH + "payAdvCancelDetail";
	}
}
