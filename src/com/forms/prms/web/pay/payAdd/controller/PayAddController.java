package com.forms.prms.web.pay.payAdd.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.platform.web.token.PreventDuplicateSubmit;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.contract.query.domain.QueryContract;
import com.forms.prms.web.monthOver.service.MonthOverService;
import com.forms.prms.web.pay.payAdd.domain.PayAddBean;
import com.forms.prms.web.pay.payAdd.service.PayAddService;
import com.forms.prms.web.pay.paymodify.domain.PayModifyBean;
import com.forms.prms.web.pay.paymodify.service.PayModifyService;
import com.forms.prms.web.pay.payquery.service.PayQueryService;
import com.forms.prms.web.sysmanagement.concurrent.domain.ConcurrentType;
import com.forms.prms.web.sysmanagement.concurrent.service.ConcurrentService;
import com.forms.prms.web.sysmanagement.provider.domain.ProviderBean;
import com.forms.prms.web.sysmanagement.provider.service.ProviderService;
import com.forms.prms.web.sysmanagement.uploadfilemanage.service.UpFileManagerService;
import com.forms.prms.web.util.ForwardPageUtils;
import com.forms.prms.web.util.LinkDealUtils;

/**
 * 业务逻辑：1.合同待付款信息列表查询（条件：状态必须是合同确认完成:20变更申请中:21物料复核:23合同移交中：25；
 * 用户只能查看自己所在责任中心下的合同信息；） 2.合同信息查询（条件：合同号） 3.正常付款信息查询（条件：合同状态 是合同录入:10；根据合同号查询）
 * 4.预付款信息查询（条件：合同状态 是合同录入:10；根据合同号查询） 5.预付款新增信息查询（会查询出合同信息和生成付款单号）
 * 6.预付款新增保存和提交（
 * 添加信息到预付款表TD_PAY_ADVANCE并更新合同的冻结金额；添加时发票状态和付款状态均为0；保存的状态为00，提交的状态为02）；
 * 7.正常付款新增信息查询（会查询出合同信息和生成付款单号；根据合同号查询出预付款核销信息；查询出合同采购设备信息）
 * 7.正常付款新增保存和提交（添加正常付款信息到TD_PAY表
 * ；更新当前合同的冻结金额；合同采购设备(正常和预付款)发票分配的金额添加到TD_PAY_DEVICE；预付款核销信息添加）
 * 8.根据预付款单号查预付款核销明细（根据预付款单号查预付款核销明细） 9.正常付款明细查询（根据合同号查合同信息；根据付款单号查付款信息；
 * 根据合同号查预付款核销信息；查询合同采购设备信息） 10.预付款明细查询（根据合同号查合同信息；根据付款单号查付款信息；）
 * 11.预付款核销处理信息查询（根据合同号查合同信息；根据付款单号查付款信息；查询合同采购设备信息）
 * 12.正常付款暂收结清处理信息查询（根据合同号查合同信息；根据付款单号查付款信息；生成已结清编号；查询已结清列表）
 * 13.正常付款暂收结清处理信息提交（添加到暂收结清处理信息表中） 14.已结清明细查询（根据结清单号查询结清明细） author : lisj <br>
 * date : 2015-01-26<br>
 * 合同付款新增Action
 */
@Controller
@RequestMapping("/pay/payAdd/")
public class PayAddController {
	@Autowired
	private MonthOverService monthOverService;
	
	@Autowired
	private PayModifyService payModifyService;
	
	private static final String PAYADDPATH = "/pay/payAdd/";
	@Autowired
	private PayAddService payAddService;
	@Autowired
	private ProviderService providerService;
	@Autowired 
	public UpFileManagerService upFileService;
	
	@Autowired
	private PayQueryService payQueryService;
	@Autowired
	private ConcurrentService concurrentService;

	/**
	 * 合同待付款信息列表查询
	 * 
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("payList.do")
	@AddReturnLink(id = "payList", displayValue = "合同待付款信息列表查询")
	public String payList(PayAddBean payAddBean) {
		List<PayAddBean> payList = payAddService.payList(payAddBean);
		WebUtils.setRequestAttr("payList", payList);
		WebUtils.setRequestAttr("selectInfo", payAddBean);
		WebUtils.setRequestAttr("org1Code", WebHelp.getLoginUser().getOrg1Code());
		return PAYADDPATH + "payList";
	}

	/**
	 * 合同付款新增处理（明细）
	 * 
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("deail.do")
	@AddReturnLink(id = "deail", displayValue = "合同付款信息查询")
	public String deail(PayAddBean payAddBean) {
		if (Tool.CHECK.isEmpty(payAddBean.getTabsIndex())) {
			payAddBean.setTabsIndex("0");
		}
		// 合同信息查询
		PayAddBean payAddInfo = payAddService.constractInfo(payAddBean);
		// 正常付款信息
		payAddBean.setIsCreditNote("1");//非贷项通知单
		//拿到月结的状态
		String monDataFlag = monthOverService.getMaxDataFlag();
		WebUtils.setRequestAttr("monDataFlag", monDataFlag);
		//根据合同号查询TI_TRADE_BACKWASH是否有数据
		String id = payAddService.getIdByCntNum(payAddBean.getCntNum());
		if(Tool.CHECK.isBlank(id)){
			WebUtils.setRequestAttr("flag", "1");
		}else{
			WebUtils.setRequestAttr("flag", "2");
		}
		
		if("0".equals(payAddBean.getTabsIndex()))
		{
			//正常付款信息
			List<PayAddBean> payList = payAddService.queryPayList(payAddBean);
			
			//判断预付款核销是否全部完成
			String advCancelOver = "Y";
			List<PayAddBean> payAdvanceCancelList = payAddService.queryPayAdvanceCancel(payAddBean);// 根据合同号查预付款核销信息
			for(PayAddBean pBean:payAdvanceCancelList){
				if(pBean.getPayAmt().compareTo(pBean.getCancelAmt()) == 1){
					advCancelOver = "N";
					break;
				}
			}
			
			WebUtils.setRequestAttr("payList", payList);
			WebUtils.setRequestAttr("advCancelOver", advCancelOver);
		}
		else if("1".equals(payAddBean.getTabsIndex()))
		{
			// 预付款信息
			List<PayAddBean> payAdvanceList = payAddService
					.queryPayAdvanceList(payAddBean);
			WebUtils.setRequestAttr("payAdvanceList", payAdvanceList);
		}
		

		WebUtils.setRequestAttr("payAddInfo", payAddInfo);
		WebUtils.setRequestAttr("selectInfo", payAddBean);
		
		WebUtils.setRequestAttr("selectCreditInfo", payAddBean);
		WebUtils.setRequestAttr("getBlueInvoiceList", payAddService.getBlueInvoiceList(payAddBean));		
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/pay/payAdd/payList.do?VISIT_FUNC_ID=030303");
		return PAYADDPATH + "deail";
	}

	/**
	 * 预付款新增
	 * 
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("addPayAdvance.do")
	@AddReturnLink(id = "addPayAdvance", displayValue = "预付款新增")
	public String addPayAdvance(PayAddBean payAddBean) {
		//根据合同号得到合同的影像
		QueryContract cnt = payQueryService.getCntICMSByCntNum(payAddBean.getCntNum());
		WebUtils.setRequestAttr("cnt", cnt);
		// 合同信息查询
		PayAddBean payAddInfo = payAddService.constractInfo(payAddBean);
		payAddInfo = validateProvider(payAddInfo);
		// 生成付款单号
		payAddBean.setPayFlag("0");
		payAddInfo.setPayId(payAddService.createPayId(payAddBean));
		//生成发票号
		payAddBean.setInstUser(WebHelp.getLoginUser().getUserId());//当前登录人id
		payAddBean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		payAddInfo.setInvoiceId(payAddService.createInvoiceId(payAddBean));
		//查询附件类型对应的值(从SYS_SELECT表中)
		List<PayAddBean> listAtTypes = payAddService.queryAtType();
		WebUtils.setRequestAttr("payAddInfo", payAddInfo);
		WebUtils.setRequestAttr("listAtTypes", listAtTypes);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/pay/payAdd/deail.do?VISIT_FUNC_ID=03030301&tabsIndex=1&cntNum="+payAddBean.getCntNum());
		return PAYADDPATH + "addPayAdvance";
	}

	/**
	 * 预付款新增保存
	 * 
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("addPayAdvanceSave.do")
	@PreventDuplicateSubmit
	public String addPayAdvanceSave(PayAddBean payAddBean) {
		boolean flag = payAddService.addPayAdvanceSave(payAddBean);
		WebUtils.setRequestAttr("payId", payAddBean.getPayId());
		WebUtils.setRequestAttr("isOrder", payAddBean.getIsOrder());
//		WebUtils.setRequestAttr("buttonValue","预付款封面打印");
		return ForwardPageUtils.getReturnUrlString("预付款新增保存", flag,
				new String[] { "deail", "payList"});
	}

	/**
	 * 预付款新增提交
	 * 
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("addPayAdvanceSubmit.do")
	@PreventDuplicateSubmit
	public String addPayAdvanceSubmit(PayAddBean payAddBean) {
		boolean flag = payAddService.addPayAdvanceSubmit(payAddBean);
		WebUtils.setRequestAttr("payId", payAddBean.getPayId());
		WebUtils.setRequestAttr("isOrder", payAddBean.getIsOrder());
//		WebUtils.setRequestAttr("buttonValue","预付款封面打印");
		return ForwardPageUtils.getReturnUrlString("预付款新增提交", flag, new String[] { "deail", "payList"});
	}
	
	/**
	 * 正常付款、预付款、贷项通知单在付款新增(修改同理)，检查确保供应商所在OU和登陆用户所在OU要一致
	 * @param payAddBean
	 * @return
	 */
	public PayAddBean validateProvider(PayAddBean payAddBean){
		List<ProviderBean> list = providerService.queryInfoByCode(payAddBean.getProviderCode(),payAddBean.getProviderAddrCode());
		if(!Tool.CHECK.isEmpty(list)){
			ProviderBean providerBean = list.get(0);
			if(providerBean.getOuCode().equals(WebHelp.getLoginUser().getOuCode())){
				
			}else{
				/*
				providerCode
				providerName
				providerType
				provActNo
				bankName
				provActCurr
				providerAddr
				providerAddrCode
				actName
				bankInfo
				bankCode
				bankArea
				actType
				*/
				payAddBean.setProviderCode("");
				payAddBean.setProviderName("");
				payAddBean.setProviderType("");
				payAddBean.setProvActNo("");
				payAddBean.setBankName("");
				payAddBean.setProvActCurr("");
				payAddBean.setProviderAddr("");
				payAddBean.setProviderAddrCode("");
				payAddBean.setActName("");
				payAddBean.setBankInfo("");
				payAddBean.setBankCode("");
				payAddBean.setBankArea("");
				payAddBean.setActType("");
			}
		}
		return payAddBean;
	}
	
	/**
	 * 正常付款新增
	 * 
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("addPay.do")
	@AddReturnLink(id = "addPay", displayValue = "正常付款新增")
	public String addPay(PayAddBean payAddBean) {
		//根据合同号得到合同的影像
		QueryContract cnt = payQueryService.getCntICMSByCntNum(payAddBean.getCntNum());
		WebUtils.setRequestAttr("cnt", cnt);
		// 合同信息查询
		PayAddBean payAddInfo = payAddService.constractInfo(payAddBean);
		payAddInfo = validateProvider(payAddInfo);
		payAddBean.setPayFlag("1");
		// 生成付款单号
		payAddInfo.setPayId(payAddService.createPayId(payAddBean));
		//生成发票号
		payAddBean.setInstUser(WebHelp.getLoginUser().getUserId());//当前登录人id
		payAddBean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		payAddInfo.setInvoiceId(payAddService.createInvoiceId(payAddBean));
		// 根据合同号查预付款核销信息
		List<PayAddBean> payAdvanceCancelList = payAddService
				.queryPayAdvanceCancel(payAddBean);
		
		//过滤掉全部核销完的预付款
		List<PayAddBean> pAdvanceCancelList = new ArrayList<PayAddBean>();
		for(PayAddBean pBean:payAdvanceCancelList){
			if(pBean.getPayAmt().compareTo(pBean.getCancelAmt()) == 1){
				pAdvanceCancelList.add(pBean);
			}
		}
		// 查询合同采购设备信息
		List<PayAddBean> devices = payAddService.queryDevicesById(payAddBean);
		//查询附件类型对应的值(从SYS_SELECT表中)
		List<PayAddBean> listAtTypes = payAddService.queryAtType();
		WebUtils.setRequestAttr("payAddInfo", payAddInfo);
		WebUtils.setRequestAttr("payAdvanceCancelList", pAdvanceCancelList);
		WebUtils.setRequestAttr("devices", devices);
		WebUtils.setRequestAttr("listAtTypes", listAtTypes);
		boolean flag = false;
		if (!Tool.CHECK.isEmpty(pAdvanceCancelList)) {
			flag = true;
		}
		WebUtils.setRequestAttr("flag", flag);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/pay/payAdd/deail.do?VISIT_FUNC_ID=03030301&cntNum="+payAddBean.getCntNum());
		return PAYADDPATH + "addPay";
	}
	/**
	 * 贷项通知单付款新增
	 * 
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("addCreditNote.do")
	@AddReturnLink(id = "addCreditNote", displayValue = "贷项通知单付款新增")
	public String addCreditNote(PayAddBean payAddBean) {
		
		// 1.原蓝字发票头信息查询
		PayAddBean invoiceBlue = payAddService.queryInvoiceBlue(payAddBean);
		
		// 2.生成红字发票预备信息
		PayAddBean invoiceRed = payAddService.constractInfo(payAddBean);
		
		// 2.1校验供应商所在OU与登录用户所在OU
		invoiceRed = validateProvider(invoiceRed);
		
		// 2.2生成贷项通知单付款单号
		payAddBean.setPayFlag("1");
		invoiceRed.setPayFlag("1");
		invoiceRed.setPayId(payAddService.createPayId(invoiceRed));
		
		// 3.查看原蓝字发票对应的物料行信息
		List<PayAddBean> invoiceBlueDevices = payAddService.queryInvoiceBlueDevice(payAddBean);
		
		// 4.查询附件类型对应的值(从SYS_SELECT表中)
		List<PayAddBean> listAtTypes = payAddService.queryAtType();
		
		WebUtils.setRequestAttr("invoiceBlue", invoiceBlue);
		WebUtils.setRequestAttr("invoiceBlueDevices", invoiceBlueDevices);
		WebUtils.setRequestAttr("invoiceRed", invoiceRed);
		WebUtils.setRequestAttr("listAtTypes", listAtTypes);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/pay/payAdd/deail.do?VISIT_FUNC_ID=03030301&tabsIndex=2&cntNum="+payAddBean.getCntNum()+"&invoiceIdBlue="+payAddBean.getInvoiceIdBlue());
		return PAYADDPATH + "addCreditNote";
	}

	/**
	 * 正常付款新增保存
	 * 
	 * @param payAddBean
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("addPaySave.do")
	@PreventDuplicateSubmit
	public String addPaySave(PayAddBean payAddBean) throws Exception {
		//先查询付款单是否存在，存在则删除该付款信息，然后调用保存的方法，预算冻结成功才更新其状态
		payAddBean.setTable("TD_PAY");
		PayAddBean paAddBean = payAddService.queryPayInfo(payAddBean);
		boolean isDeleSuc = true;
		if(!Tool.CHECK.isEmpty(paAddBean)){//存在则删除
			PayModifyBean pmbBean = new PayModifyBean();
			pmbBean.setPayId(payAddBean.getPayId());//付款单
			pmbBean.setCntNum(payAddBean.getCntNum());//合同号
			//1.更新合同冻结金额
			//2.更新设备冻结金额
			//3.根据付款单删除付款信息（TD_PAY、TD_PAY_DEVICE、TD_PAY_ADVANCE_CANCEL）
			isDeleSuc = payModifyService.payDelete(pmbBean);
		}
//		Map<String, String[]> newParameters = new HashMap<String, String[]>();
		boolean flag = false;
		if(isDeleSuc){
			flag = payAddService.addPaySave(payAddBean);
		}
		WebUtils.setRequestAttr("payId", payAddBean.getPayId());
		WebUtils.setRequestAttr("isOrder", payAddBean.getIsOrder());
//		WebUtils.setRequestAttr("buttonValue","正常付款封面打印");
//		String[] link = {};
//		if("0".equals(payAddBean.getIsCreditNote())){//是贷项通知单
//			link = new String[] {  "deail", "payList"};
//		}else{
//			link = new String[] {  "deail", "payList"};
//		}
		return ForwardPageUtils.getReturnUrlString("正常付款新增保存", flag,new String[] {  "deail", "payList"});
	}

	/**
	 * 正常付款新增提交
	 * 
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("addPaySubmit.do")
	@PreventDuplicateSubmit
	public String addPaySubmit(PayAddBean payAddBean) {
		boolean flag = false;
		try {
			flag = payAddService.addPaySubmit(payAddBean);
		} catch (Exception e) {
			WebUtils.getMessageManager().addInfoMessage(e.getCause().getMessage());
			return ForwardPageUtils.getErrorPage();
		}
		
		
//		Map<String, String[]> newParameters = new HashMap<String, String[]>();
//		//注意需要URI对应的添加功能id
//		newParameters.put("VISIT_FUNC_ID", new String[]{"03030317"});
//		newParameters.put("payId", new String[]{payAddBean.getPayId()});
//		newParameters.put("isOrder", new String[]{payAddBean.getIsOrder()});
//		payAddBean.setButtonFlag("F");
//		newParameters.put("buttonFlag", new String[]{payAddBean.getButtonFlag()});
//		newParameters.put("cntNum", new String[]{payAddBean.getCntNum()});
//		
//		LinkDealUtils.addReturnLink("printNormal", "正常付款打印", "pay/payAdd/print.do", newParameters);
		
		WebUtils.setRequestAttr("payId", payAddBean.getPayId());
		WebUtils.setRequestAttr("isOrder", payAddBean.getIsOrder());
//		WebUtils.setRequestAttr("buttonValue","正常付款封面打印");
		
//		String[] link = {};
//		if("0".equals(payAddBean.getIsCreditNote())){//是贷项通知单
//			link = new String[] { "addCreditNote", "deail", "payList"};
//		}else{
//			link = new String[] { "addPay", "deail", "payList"};
//		}
		return ForwardPageUtils.getReturnUrlString("正常付款新增提交", flag,new String[] {  "deail", "payList"});
	}

	/**
	 * 根据预付款单号查预付款核销明细
	 * 
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("queryPayAdvCancelDetail.do")
	public String queryPayAdvCancelDetail(PayAddBean payAddBean) {
		PayAddBean payAdvCancelInfo = payAddService
				.queryPayAdvCancelDetail(payAddBean);
		WebUtils.setRequestAttr("payAdvCancelInfo", payAdvCancelInfo);
		//判断返回按钮返回到哪个页面
		if(payAddBean.getFlag()!=null){
			if("1".equals(payAddBean.getFlag())){
				WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/pay/payAdd/addPay.do?VISIT_FUNC_ID=03030306&cntNum="+payAddBean.getCntNum());
			}
			else if("2".equals(payAddBean.getFlag())){
				WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/pay/payAdd/queryPayDetail.do?VISIT_FUNC_ID=03030311&payId="+payAddBean.getPayId()+"&cntNum="+payAddBean.getCntNum());
			}
			else if("3".equals(payAddBean.getFlag())){
				WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/pay/payAdd/queryPayAdvCancelDeal.do?VISIT_FUNC_ID=03030312&cntNum="+payAddBean.getCntNum());
			}
			else if("4".equals(payAddBean.getFlag())){
				WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/pay/paymodify/modify.do?VISIT_FUNC_ID=03030401&payType=Y&payId="+payAddBean.getPayId()+"&cntNum="+payAddBean.getCntNum());
			}
		}
		return PAYADDPATH + "payAdvCancelDetail";
	}

	/**
	 * 正常付款明细查询
	 * 
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("queryPayDetail.do")
	public String queryPayDetail(PayAddBean payAddBean) {
			
		if("0".equals(payAddBean.getIsCreditNote())){
			// 1.原蓝字发票头信息查询
			PayAddBean invoiceBlue = payAddService.queryInvoiceBlue(payAddBean);
			
			// 2.查询贷项通知单头信息
			payAddBean.setTable("TD_PAY");
			PayAddBean payInfo = payAddService.queryPayInfo(payAddBean);

			// 3.查询贷项通知单发票行信息
			payAddBean.setPayType("1");
			List<PayAddBean> payDevices = payAddService.queryDeviceDetailsById(payAddBean);
			
			//根据合同号得到合同的影像
			QueryContract cnt = payQueryService.getCntICMSByCntNum(payAddBean.getCntNum());
			// 合同信息
			PayAddBean constractInfo = payAddService.constractInfo(payAddBean);
						
			WebUtils.setRequestAttr("cnt", cnt);
			WebUtils.setRequestAttr("constractInfo", constractInfo);
			WebUtils.setRequestAttr("invoiceBlue", invoiceBlue);
			WebUtils.setRequestAttr("payInfo", payInfo);
			WebUtils.setRequestAttr("payDevices", payDevices);
			
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/pay/payAdd/creditList.do?<%=WebConsts.FUNC_ID_KEY %>=03030332&invoiceIdBlue="+payAddBean.getInvoiceIdBlue());
			return PAYADDPATH + "payCreditDetail";
		}
		else{
			//根据合同号得到合同的影像
			QueryContract cnt = payQueryService.getCntICMSByCntNum(payAddBean.getCntNum());
			WebUtils.setRequestAttr("cnt", cnt);
			// 合同信息
			PayAddBean constractInfo = payAddService.constractInfo(payAddBean);
			// 付款信息
			payAddBean.setTable("TD_PAY");
			PayAddBean payInfo = payAddService.queryPayInfo(payAddBean);
			// 根据合同号查预付款核销信息
			List<PayAddBean> payAdvanceCancelList = payAddService
					.queryPayAdvanceCancel(payAddBean);

			// 查询合同采购设备信息
			payAddBean.setPayType("1");
			List<PayAddBean> payDevices = payAddService
					.queryDeviceDetailsById(payAddBean);
			payAddBean.setPayType("0");
			List<PayAddBean> payAdvDevices = payAddService
					.queryDeviceDetailsById(payAddBean);
			WebUtils.setRequestAttr("constractInfo", constractInfo);
			WebUtils.setRequestAttr("payInfo", payInfo);
			WebUtils.setRequestAttr("payAdvanceCancelList", payAdvanceCancelList);
			WebUtils.setRequestAttr("payDevices", payDevices);
			WebUtils.setRequestAttr("payAdvDevices", payAdvDevices);
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/pay/payAdd/deail.do?VISIT_FUNC_ID=03030301&tabsIndex=0&cntNum="+payAddBean.getCntNum());
		
			return PAYADDPATH + "payDetail";
		}
	}

	/**
	 * 预付款明细查询
	 * 
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("queryPayAdvDetail.do")
	public String queryPayAdvDetail(PayAddBean payAddBean) {
		//根据合同号得到合同的影像
		QueryContract cnt = payQueryService.getCntICMSByCntNum(payAddBean.getCntNum());
		WebUtils.setRequestAttr("cnt", cnt);
		// 合同信息
		PayAddBean constractInfo = payAddService.constractInfo(payAddBean);
		// 付款信息
		payAddBean.setTable("TD_PAY_ADVANCE");
		PayAddBean payInfo = payAddService.queryPayInfo(payAddBean);
		WebUtils.setRequestAttr("constractInfo", constractInfo);
		WebUtils.setRequestAttr("payInfo", payInfo);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/pay/payAdd/deail.do?VISIT_FUNC_ID=03030301&tabsIndex=1&cntNum="+payAddBean.getCntNum());
		return PAYADDPATH + "payAdvDetail";
	}

	/**
	 * 预付款核销处理信息查询
	 * 
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("queryPayAdvCancelDeal.do")
	public String queryPayAdvCancelDeal(PayAddBean payAddBean) {
		// 查询合同信息
		PayAddBean payAddInfo = payAddService.constractInfo(payAddBean);
		// 生成付款单号
		payAddBean.setPayFlag("0");
		payAddInfo.setPayId(payAddService.createPayId(payAddBean));
		//生成发票号
		payAddInfo.setInstUser(WebHelp.getLoginUser().getUserId());//当前登录人id
		payAddInfo.setInvoiceId(payAddService.createInvoiceId(payAddBean));
		// 查询合同采购信息
		List<PayAddBean> devices = payAddService.queryDevicesById(payAddBean);
		//查询附件类型对应的值(从SYS_SELECT表中)
		List<PayAddBean> listAtTypes = payAddService.queryAtType();
		// 根据合同号查预付款核销信息
		List<PayAddBean> payAdvanceCancelList = payAddService
				.queryPayAdvanceCancel(payAddBean);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/pay/payAdd/deail.do?VISIT_FUNC_ID=03030301&cntNum="+payAddBean.getCntNum());
		WebUtils.setRequestAttr("payAddInfo", payAddInfo);
		WebUtils.setRequestAttr("listAtTypes", listAtTypes);
		WebUtils.setRequestAttr("devices", devices);
		WebUtils.setRequestAttr("payAdvanceCancelList", payAdvanceCancelList);
		return PAYADDPATH + "payAdvCancelDeal";
	}

	/**
	 * 正常付款暂收结清处理信息查询
	 * 
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("queryPaySuspenseDeal.do")
	public String queryPaySuspenseDeal(PayAddBean payAddBean) {
		// 查询合同信息
		PayAddBean constractInfo = payAddService.constractInfo(payAddBean);
		// 查询付款信息
		payAddBean.setTable("TD_PAY");
		PayAddBean payInfo = payAddService.queryPayInfo(payAddBean);
		payAddBean.setPayFlag("2");
		//查询正在结清的总金额
		String cleanAmtIngTotal = payAddService.queryCleanAmtIng(payAddBean);
		payInfo.setCleanAmtIngTotal(new BigDecimal(cleanAmtIngTotal));
		// 生成已结清编号
		constractInfo.setCleanPayId(payAddService.createPayId(payAddBean));
		// 查询已结清列表
		List<PayAddBean> payCleanInfoList = payAddService
				.queryPayCleanInfo(payAddBean);
		WebUtils.setRequestAttr("constractInfo", constractInfo);
		WebUtils.setRequestAttr("payInfo", payInfo);
		WebUtils.setRequestAttr("payCleanInfoList", payCleanInfoList);
		return PAYADDPATH + "paySuspenseDeal";
	}

	/**
	 * 正常付款暂收结清处理信息提交
	 * 
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("paySuspenseDealSubmit.do")
	@PreventDuplicateSubmit
	public String paySuspenseDealSubmit(PayAddBean payAddBean) {
		boolean flag = payAddService.paySuspenseDealSubmit(payAddBean);
		return ForwardPageUtils.getReturnUrlString("正常付款暂收结清处理", flag,
				new String[] { "deail", "payList" });
	}

	/**
	 * 预付款核销信息提交
	 * 
	 * @param payAddBean
	 * @return
	 */
	/*
	 * @RequestMapping("payAdvCancelDealSubmit.do")
	 * 
	 * @PreventDuplicateSubmit public String payAdvCancelDealSubmit(PayAddBean
	 * payAddBean) { boolean flag = true; return
	 * ForwardPageUtils.getReturnUrlString("预付款核销处理", flag, new String[] {
	 * "deail", "payList" }); }
	 */

	/**
	 * 已结清明细查询
	 * 
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("querySuspenseDetail.do")
	public String querySuspenseDetail(PayAddBean payAddBean) {
		PayAddBean suspenseDetaiInfo = payAddService
				.querySuspenseDetail(payAddBean);
		WebUtils.setRequestAttr("suspenseDetaiInfo", suspenseDetaiInfo);
		return PAYADDPATH + "suspenseDetail";
	}
	
	
	/**
	 * 正常付款封面打印
	 * 
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("print.do")
	public String print(PayAddBean payAddBean) {
		//检查是否有核销
		if("0".equals(payAddBean.getIsOrder())){//是订单
			List<PayAddBean> list = payAddService.queryCancelDevices(payAddBean.getPayId());
			if(!Tool.CHECK.isEmpty(list)){//不为空则存在核销数据
				WebUtils.setRequestAttr("isExitCancel","true");
			}else{
				WebUtils.setRequestAttr("isExitCancel","false");
			}
		}
		WebUtils.setRequestAttr("payId", payAddBean.getPayId());
		WebUtils.setRequestAttr("isOrder", payAddBean.getIsOrder());
		WebUtils.setRequestAttr("userId", WebHelp.getLoginUser().getUserId());
		return PAYADDPATH + "print";
	}
	/**
	 * 预付款封面打印
	 * 
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("printAdv.do")
	public String printAdv(PayAddBean payAddBean) {
		WebUtils.setRequestAttr("payId", payAddBean.getPayId()); 
		WebUtils.setRequestAttr("isOrder", payAddBean.getIsOrder());
		WebUtils.setRequestAttr("userId", WebHelp.getLoginUser().getUserId());
		return PAYADDPATH + "printAdv";
	}
	/**
	 * 下载插件
	 * 
	 * @param upFile
	 * @return
	 */
	@RequestMapping("printDownload.do")
	public void upFileDownload(HttpServletRequest request,HttpServletResponse response,String fileId) throws Exception{
		upFileService.upFileDownload(request ,response, fileId);
	}
	
	
//	@RequestMapping("checkDownFileExist.do")
//	@ResponseBody
//	public String checkDownFileExist(String fileId){
//		AbstractJsonObject json = new SuccessJsonObject();
//		boolean checkFlag = false;				//校验结果标识
//		String checkMsg = "";					//校验结果MSG(当校验结果为false时，该对象才会有值)
//		try {
//			File downFile = new File(service.getUpFileById(fileId).getSourceFPath());
//			if((!downFile.exists())||!(downFile.isFile())){
//				CommonLogger.debug("【文件存在性检测】：根本不存在  "+service.getUpFileById(fileId).getSourceFPath());
//				checkMsg = "需要下载的文件不存在当前Server，请检查！";
//			}else{
//				checkFlag = true;
//				CommonLogger.debug("【文件存在性检测】：存在   "+service.getUpFileById(fileId).getSourceFPath());
//			}
//		} catch (Exception e) {
//			checkMsg = "需要下载的文件不存在当前Server，请检查！";
//		}
//		
//		json.put("fileId", fileId);			//用于Ajax检测后的下载操作中参数传输
//		json.put("checkFlag", checkFlag);
//		json.put("checkMsg", checkMsg);
//		return json.writeValueAsString();
//	}
	
	/**
	 * 查询审批历史记录
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("queryHis.do")
	public String queryHis(PayAddBean payAddBean){
		List<PayAddBean> hisList = payAddService.queryHis(payAddBean.getPayId());
		WebUtils.setRequestAttr("hisList", hisList);
		return PAYADDPATH + "history";
	}
	
	/**
	 * 得到订单跳转的链接
	 * @param link
	 * @param cntNum
	 * @return
	 */
	private String[] returnOrderLink(String[] link,String cntNum){
		Map<String, String[]> adParameter = new HashMap<String, String[]>();
		//注意需要URI对应的添加功能id
		adParameter.put("VISIT_FUNC_ID", new String[]{"030301"});
		adParameter.put("cntNum", new String[]{cntNum});
		LinkDealUtils.addReturnLink("addOrderInfo", "订单补录", "pay/orderstart/list.do", adParameter);
		link = Arrays.copyOf(link,link.length+1);
		link[link.length-1] = "addOrderInfo";
		return link;
	}
	
	/**
	 * 预算冻结的ajax校验
	 * @param payId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("checkBgtFrozen.do")
	@ResponseBody
	public String checkBgtFrozen(PayAddBean payAddBean) throws Exception{
		AbstractJsonObject json = new SuccessJsonObject();
		Map<String, String> param = new HashMap<String, String>();
		//校验互斥锁 //预提待摊的才需要在合同确立的时候占用预算，所以才需要这个时候来加互斥锁
		int returnFlag = 0;
		boolean lockFlag = true;
		try {
			String memo = "合同号为"+payAddBean.getCntNum()+"付款单号为"+payAddBean.getPayId()+"付款新增提交待复核时增加锁";
			returnFlag = concurrentService.checkAndAddLock(ConcurrentType.Concurrent_A,
					ConcurrentType.A2,WebHelp.getLoginUser().getOrg1Code(),WebHelp.getLoginUser().getUserId(),memo);
			lockFlag = true;
		} catch (Exception e) {
			lockFlag = false;
			e.printStackTrace();
			json.put("flag", "2");
			json.put("errorMsg", e.getCause().getMessage());
		}
		
		
		if (lockFlag) {
			//先查询付款单是否存在，存在则删除该付款信息，然后调用保存的方法，预算冻结成功才更新其状态
			payAddBean.setTable("TD_PAY");
			PayAddBean paAddBean = payAddService.queryPayInfo(payAddBean);
			boolean isDeleSuc = true;
			if(!Tool.CHECK.isEmpty(paAddBean)){//存在则删除
				PayModifyBean pmbBean = new PayModifyBean();
				pmbBean.setPayId(payAddBean.getPayId());//付款单
				pmbBean.setNormalPayId(payAddBean.getPayId());//付款单
				pmbBean.setCntNum(payAddBean.getCntNum());//合同号
				pmbBean.setPayAmt(payAddBean.getPayAmt());//付款金额
				pmbBean.setSuspenseAmt(payAddBean.getSuspenseAmt());//暂收金额
				pmbBean.setFreezeTotalAmt(payAddBean.getFreezeTotalAmt());//合同冻结金额
				//1.更新合同冻结金额
				//2.更新设备冻结金额
				//3.根据付款单删除付款信息（TD_PAY、TD_PAY_DEVICE、TD_PAY_ADVANCE_CANCEL）
				isDeleSuc = payModifyService.payDelete(pmbBean);
			}
			if(isDeleSuc){
				boolean isSuc = payAddService.addPaySave(payAddBean);
				if(isSuc){
					
					param.put("payId", payAddBean.getPayId());
					param.put("flag", "");
					payAddService.checkBgtFrozen(param);
					String flag = param.get("flag");
					if("0".equals(flag)){//0表示预算冻结失败,查询付款冻结失败的信息
						String msgInfo = param.get("msgInfo");
//						List<PayAddBgtBean> bgtList = payAddService.queryBgtFrozenFailMsg(payAddBean.getPayId());
//						String bgtMsg = "";
//						for(PayAddBgtBean bgtBean : bgtList){
//							bgtMsg += "物料（"+bgtBean.getMatrName()+"）"+bgtBean.getMemo()+";";
//						}
						json.put("bgtMsg", msgInfo);
						json.put("payId", payAddBean.getPayId());
					}
					json.put("flag", flag);
				}
			}
		}
		
		//删除锁
		if (returnFlag == 2) {
			try {
				concurrentService.delConcurrentLock(ConcurrentType.Concurrent_A, ConcurrentType.A2,"");
			} catch (Exception e) {
				e.printStackTrace();
				concurrentService.delConcurrentLock(ConcurrentType.Concurrent_A, ConcurrentType.A2,"");
			}
			
		}
		return json.writeValueAsString();
	}
	
	/**
	 * 更新付款单的状态
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("changePayStatus.do")
	public String changePayStatus(PayAddBean payAddBean){
		boolean flag = payAddService.changePayStatus(payAddBean);
		Map<String, String[]> newParameters = new HashMap<String, String[]>();
		//注意需要URI对应的添加功能id
		newParameters.put("VISIT_FUNC_ID", new String[]{"03030317"});
		newParameters.put("payId", new String[]{payAddBean.getPayId()});
		newParameters.put("isOrder", new String[]{payAddBean.getIsOrder()});
		payAddBean.setButtonFlag("F");
		newParameters.put("buttonFlag", new String[]{payAddBean.getButtonFlag()});
		newParameters.put("cntNum", new String[]{payAddBean.getCntNum()});
		
//		LinkDealUtils.addReturnLink("printNormal", "正常付款打印", "pay/payAdd/print.do", newParameters);
		
		String[] link = new String[] {"deail", "payList"};
//		if(flag && "0".equals(payAddBean.getIsOrder())){
//			link = returnOrderLink(link,payAddBean.getCntNum());
//		}
		
		return ForwardPageUtils.getReturnUrlString("正常付款新增提交", flag,link);
	}
	
	
	/**
	 * 校验预算是否透支的ajax校验
	 * @param payId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("checkBgtOverdraw.do")
	@ResponseBody
	public String checkBgtOverdraw(PayAddBean payAddBean){
		AbstractJsonObject json = new SuccessJsonObject();
		String bgtMsg = payAddService.checkBgtOverdraw(payAddBean);
		json.put("bgtMsg", bgtMsg);
		return json.writeValueAsString();
	}
	
	@RequestMapping("checkOuprovider.do")
	@ResponseBody
	public String checkOuprovider(String providerCode, String providerAddrCode) {
		String ouCode = WebHelp.getLoginUser().getOuCode();
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		String result = payAddService.checkOuprovider(ouCode,providerCode,providerAddrCode);
		if (null != result && "Y".equals(result)) {
			jsonObject.put("isExist", true);
		} else {
			jsonObject.put("isExist", false);
		}

		return jsonObject.writeValueAsString();
	}
	
	/**
	 * 发票号的ajax校验
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("ajaxCheckInvoiceId.do")
	@ResponseBody
	public String ajaxCheckInvoiceId(String invoiceId,String payId,String flag,String modifyFlag){
		AbstractJsonObject json = new SuccessJsonObject();
		String tableName="";
		if("0".equals(flag)){//正常付款
			tableName = "td_pay";
		}else{
			tableName = "td_pay_advance";
		}
		boolean isTrue = payAddService.ajaxCheckInvoiceId(invoiceId,payId,tableName,modifyFlag);
		json.put("isTrue", isTrue);
		return json.writeValueAsString();
	}
	
	/**
	 * 查看原蓝字发票下的贷项通知单列表 03030332
	 * @param invoiceIdBlue
	 * @return
	 */
	@RequestMapping("creditList.do")
	public String getCreditListByInvoiceidBlue(PayAddBean payAddBean){
		List<PayAddBean> creditList = payAddService.getCreditListByInvoiceidBlue(payAddBean);
		WebUtils.setRequestAttr("creditList", creditList);
		WebUtils.setRequestAttr("searchBean", payAddBean);
		return PAYADDPATH + "creditList";
	
	}
}
