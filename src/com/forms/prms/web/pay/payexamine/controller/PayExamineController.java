package com.forms.prms.web.pay.payexamine.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.contract.query.domain.QueryContract;
import com.forms.prms.web.pay.payAdd.domain.PayAddBean;
import com.forms.prms.web.pay.payAdd.service.PayAddService;
import com.forms.prms.web.pay.payexamine.domain.PayExamineBean;
import com.forms.prms.web.pay.payexamine.service.PayExamineService;
import com.forms.prms.web.pay.payquery.service.PayQueryService;
import com.forms.prms.web.pay.scan.domain.PayScanBean;
import com.forms.prms.web.pay.scan.service.PayScanService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/pay/payexamine")
public class PayExamineController {
	private static final String PAYEXAMINEPATH = "/pay/payexamine/";
	@Autowired
	private PayExamineService payExamineService;
	@Autowired
	private PayAddService payAddService;
	@Autowired
	private PayScanService payScanSevice;
	
	@Autowired
	private PayQueryService payQueryService;

	/**
	 * 合同付款确认信息列表查询
	 * 
	 * @param PayExamineBean
	 * @return
	 */
	@RequestMapping("list.do")
	public String list(PayExamineBean bean) {
		ReturnLinkUtils.addReturnLink("list", "返回列表");
		List<PayExamineBean> list = payExamineService.getList(bean);// 预付款和付款集合
		String org2Code = WebHelp.getLoginUser().getOrg2Code();// 得到登录人的所在的二级分行
		WebUtils.setRequestAttr("list", list);
		WebUtils.setRequestAttr("selectInfo", bean);
		WebUtils.setRequestAttr("org2Code", org2Code);
		return PAYEXAMINEPATH + "list";
	}

	/**
	 * 得到预付款或者付款的的详细信息
	 * 
	 * @param bean
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("detail.do")
	public String detail(PayExamineBean bean) throws Exception {
		String desPage = "";
		List<PayExamineBean> payDeviceList = null;
		List<PayExamineBean> prePayDeviceList = null;
		List<PayExamineBean> prePayCancleList = null;
		if("0".equals(bean.getIsCreditNote())){
			PayAddBean payAddBean = new PayAddBean();
			payAddBean.setInvoiceIdBlue(bean.getInvoiceIdBlue());
			
			// 1.原蓝字发票头信息查询
			PayAddBean invoiceBlue = payAddService.queryInvoiceBlue(payAddBean);
			
			// 2.查询贷项通知单头信息
			bean = payExamineService.getPayByPayId(bean.getPayId());

			// 3.查询贷项通知单发票行信息
			payDeviceList = payExamineService.getPayDeviceListByPayId(bean.getPayId());
			
			//根据合同号得到合同的影像
			QueryContract cnt = payQueryService.getCntICMSByCntNum(bean.getCntNum());
						
			WebUtils.setRequestAttr("cnt", cnt);
			WebUtils.setRequestAttr("invoiceBlue", invoiceBlue);
			WebUtils.setRequestAttr("payInfo", bean);
			WebUtils.setRequestAttr("payDevices", payDeviceList);
			WebHelp.setLastPageLink("uri", "list");		
			WebHelp.setLastPageLink("contracturi", "");
			
			return PAYEXAMINEPATH + "payCreditDetail";
		}
		else{
			//根据合同号得到合同的影像
			QueryContract cnt = payQueryService.getCntICMSByCntNum(bean.getCntNum());
			WebUtils.setRequestAttr("cnt", cnt);
			if ("Y".equalsIgnoreCase(bean.getIsPrePay())) {
				bean = payExamineService.getPrePayByPayId(bean.getPayId());
				desPage = "prePayDetail";
			} else {
				bean = payExamineService.getPayByPayId(bean.getPayId());
				payDeviceList = payExamineService.getPayDeviceListByPayId(bean.getPayId());
				prePayDeviceList = payExamineService.getPrePayDeviceListByPayId(bean.getPayId());
				prePayCancleList = payExamineService.getPrePayCancleListByCntNum(bean);
				desPage = "payDetail";
			}
			String path = WebUtils.getRequest().getContextPath();
			WebUtils.setRequestAttr("uri",path+"/pay/payexamine/list.do?VISIT_FUNC_ID=03030501");
			WebUtils.setRequestAttr("payInfo", bean);
			WebUtils.setRequestAttr("payDeviceList", payDeviceList);
			WebUtils.setRequestAttr("prePayDeviceList", prePayDeviceList);
			WebUtils.setRequestAttr("prePayCancleList", prePayCancleList);
			WebUtils.setRequestAttr("scanBatchNo", bean.getBatchNo());
			return PAYEXAMINEPATH + desPage;
		}
	}

	/**
	 * 
	 * @param approve
	 * @return
	 */
	@RequestMapping("agree.do")
	public String Agree(PayExamineBean bean) {
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		if ("Y".equalsIgnoreCase(bean.getIsPrePay())) {
			payExamineService.agreePrePay(bean);
		} else {
			payExamineService.agreePay(bean);
		}
		WebUtils.getMessageManager().addInfoMessage("复核成功!");
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
	public String back(PayExamineBean bean) throws Exception {
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		if ("Y".equalsIgnoreCase(bean.getIsPrePay())) {
			payExamineService.backPrePay(bean);
		} else {
			payExamineService.backPay(bean);
		}
		WebUtils.getMessageManager().addInfoMessage("退回成功!");
		ReturnLinkUtils.setShowLink("list");
		return ForwardPageUtils.getSuccessPage();
	}

	/**
	 * 批量扫描
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("addIcms.do")
	public String addIcms(PayExamineBean bean) {
		payExamineService.addIcms(bean);
		WebUtils.getMessageManager().addInfoMessage("录入成功!");
		ReturnLinkUtils.setShowLink("scanList");
		return ForwardPageUtils.getSuccessPage();
	}
	
	@RequestMapping("delIcmsBatchNo.do")
	public String delIcmsBatchNo(PayExamineBean bean) {
		payExamineService.delIcmsBatchNo(bean);
		WebUtils.getMessageManager().addInfoMessage("删除成功!");
		ReturnLinkUtils.setShowLink("scanList");
		return ForwardPageUtils.getSuccessPage();
	}

	/**
	 * 
	 * 
	 * @param PayExamineBean
	 * @return
	 */
	@RequestMapping("preScan.do")
	public String preScan(PayExamineBean bean) {
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/pay/payexamine/scanList.do?VISIT_FUNC_ID=03030901");
		return PAYEXAMINEPATH + "scan";
	}
	/**
	 * 扫描汇总查询
	 * 
	 * @param PayExamineBean
	 * @return
	 */
	@RequestMapping("scanList.do")
	public String scanList(PayExamineBean bean) {
		ReturnLinkUtils.addReturnLink("scanList", "返回列表");
		List<PayExamineBean> list = payExamineService.scanList(bean);//扫描汇总
		WebUtils.setRequestAttr("list", list);
		WebUtils.setRequestAttr("selectInfo", bean);
		return PAYEXAMINEPATH + "scanList";
	}
	
	/**
	 * 扫描付款单明细列表查询
	 * 
	 * @param PayExamineBean
	 * @return
	 */
	@RequestMapping("scanDetailList.do")
	public String scanDetailList(PayExamineBean bean) {
		ReturnLinkUtils.addReturnLink("detaillist", "返回列表");
		List<PayExamineBean> list = payExamineService.scanDetailList(bean);//扫描汇总
		String path = WebUtils.getRequest().getContextPath();
		WebUtils.setRequestAttr("uri",path+"/pay/payexamine/scanList.do?VISIT_FUNC_ID=03030901");
		WebUtils.setRequestAttr("list", list);
		WebUtils.setRequestAttr("selectInfo", bean);
		return PAYEXAMINEPATH + "scanDetailList";
	}
	
	/**
	 * 扫描复核明细页面：得到预付款或者付款的的详细信息
	 * 
	 * @param bean
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("payDetail.do")
	public String payDetail(PayExamineBean bean) throws Exception {
		String isPrePay = "";
		List<PayExamineBean> payDeviceList = null;
		List<PayExamineBean> prePayDeviceList = null;
		List<PayExamineBean> prePayCancleList = null;
		//根据倒数第7位判断是正常付款（1）还是预付款（0）
		String payId = bean.getPayId();
		char idx  = StringUtils.reverse(payId).charAt(6);
		bean.setIsPrePay(idx=='0'?"Y":"N");
		String batchNo = bean.getBatchNo();
		if ("Y".equalsIgnoreCase(bean.getIsPrePay())) {
			bean = payExamineService.getPrePayByPayId(bean.getPayId());
			isPrePay = "scanPrePayDetail";
		} else {
			//得到该付款单是否为待项通知单
			bean = payExamineService.getPayByPayId(bean.getPayId());
			if("0".equals(bean.getIsCreditNote())){
				PayAddBean payAddBean = new PayAddBean();
				payAddBean.setInvoiceIdBlue(bean.getInvoiceIdBlue());
				// 1.原蓝字发票头信息查询
				PayAddBean invoiceBlue = payAddService.queryInvoiceBlue(payAddBean);
				// 2.查询贷项通知单发票行信息
				payDeviceList = payExamineService.getPayDeviceListByPayId(bean.getPayId());
				WebUtils.setRequestAttr("invoiceBlue", invoiceBlue);
				isPrePay = "scanPayCreditDetail";
			}
			else{
				payDeviceList = payExamineService.getPayDeviceListByPayId(bean.getPayId());
				prePayDeviceList = payExamineService.getPrePayDeviceListByPayId(bean.getPayId());
				prePayCancleList = payExamineService.getPrePayCancleListByCntNum(bean);
				isPrePay = "scanPayDetail";
			}
		}
		PayScanBean payScanBean = new PayScanBean();
		payScanBean.setBatchNo(batchNo);
		payScanBean.setPayId(payId);
		List<PayScanBean> list = payScanSevice.selectBatchDetail(payScanBean);
		if(null!=list && list.size() >0){
			payScanBean = list.get(0);
		}
		String path = WebUtils.getRequest().getContextPath();
		WebUtils.setRequestAttr("uri",path+"/pay/payexamine/scanDetailList.do?VISIT_FUNC_ID=0303090103&batchNo="+batchNo);
		WebUtils.setRequestAttr("payInfo", bean);
		WebUtils.setRequestAttr("payDeviceList", payDeviceList);
		WebUtils.setRequestAttr("prePayDeviceList", prePayDeviceList);
		WebUtils.setRequestAttr("prePayCancleList", prePayCancleList);
		WebUtils.setRequestAttr("payScanBean", payScanBean);
		WebUtils.setRequestAttr("scanBatchNo", batchNo);
		return PAYEXAMINEPATH + isPrePay;
	}
	
	/**
	 * 
	 * @param approve
	 * @return
	 */
	@RequestMapping("scanAgree.do")
	public String scanAgree(PayExamineBean bean) {
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		payExamineService.scanAgree(bean);
		WebUtils.getMessageManager().addInfoMessage("复核成功!");
		ReturnLinkUtils.setShowLink("detaillist");
		return ForwardPageUtils.getSuccessPage();
	}

	/**
	 * 
	 * @param approve
	 * @return
	 */
	@RequestMapping("scanBack.do")
	public String scanBack(PayExamineBean bean) throws Exception{
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		payExamineService.scanBack(bean);		
		WebUtils.getMessageManager().addInfoMessage("退回成功!");
		ReturnLinkUtils.setShowLink("detaillist");
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
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/pay/payexamine/detail.do?VISIT_FUNC_ID=03030502&isPrePay=N&payId="+bean.getPayId()+"&cntNum="+bean.getCntNum());
		return PAYEXAMINEPATH + "payAdvCancelDetail";
	}


	
	@RequestMapping("/ajaxCheckCanAddScanBatch.do")	
	@ResponseBody
	public String ajaxCheckCanAddScanBatch(){
		AbstractJsonObject jsonObject = new SuccessJsonObject();	
		String flag = payAddService.ajaxCheckCanAddScanBatch(WebHelp.getLoginUser().getUserId());
		jsonObject.put("flag", flag);
		return jsonObject.writeValueAsString();
	}

}
