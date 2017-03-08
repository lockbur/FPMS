package com.forms.prms.web.pay.paymodify.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.forms.prms.web.pay.payAdd.domain.PayAddBean;
import com.forms.prms.web.pay.payAdd.domain.PayAddBgtBean;
import com.forms.prms.web.pay.payAdd.service.PayAddService;
import com.forms.prms.web.pay.paymodify.domain.PayModifyBean;
import com.forms.prms.web.pay.paymodify.service.PayModifyService;
import com.forms.prms.web.pay.payquery.service.PayQueryService;
import com.forms.prms.web.sysmanagement.concurrent.domain.ConcurrentType;
import com.forms.prms.web.sysmanagement.concurrent.service.ConcurrentService;
import com.forms.prms.web.sysmanagement.provider.domain.ProviderBean;
import com.forms.prms.web.sysmanagement.provider.service.ProviderService;
import com.forms.prms.web.util.ForwardPageUtils;
import com.forms.prms.web.util.LinkDealUtils;

/**
 * 业务逻辑：1.付款信息列表查询（查询正常付款和预付款的信息，条件必须是状态 00 录入01 退回）
 * 			2.付款修改信息查询（根据合同号查合同信息；根据付款单号查付款信息； 根据合同号查预付款核销信息；查询合同采购设备信息）
 * 			3.预付款修改保存或提交（更新信息到预付款表TD_PAY_ADVANCE并更新合同的冻结金额；更新时发票状态和付款状态均为0；保存的状态为00，提交的状态为02）
 * 			4.正常付款修改保存或提交（更新正常付款信息到TD_PAY表；更新当前合同的冻结金额；合同采购设备(正常和预付款)发票分配的金额更新到TD_PAY_DEVICE；预付款核销信息更新）
 * 
 * author : lisj <br>
 * date : 2015-02-02<br>
 * 合同付款修改Action
 */
@Controller
@RequestMapping("/pay/paymodify/")
public class PayModifyController {
	private static final String PAYMODIFYPATH = "/pay/paymodify/";
	@Autowired
	private PayModifyService payModifyService;
	@Autowired
	private ProviderService providerService;
	@Autowired
	private PayAddService payAddService;
	@Autowired
	private PayQueryService payQueryService;
	@Autowired
	private ConcurrentService concurrentService;

	/**
	 * 付款信息列表查询
	 * 
	 * @param payModifyBean
	 * @return
	 */
	@RequestMapping("list.do")
	@AddReturnLink(id = "list", displayValue = "付款信息列表查询")
	public String list(PayModifyBean payModifyBean) {
		List<PayModifyBean> list = payModifyService.list(payModifyBean);
		WebUtils.setRequestAttr("selectInfo", payModifyBean);
		WebUtils.setRequestAttr("list", list);
		//WebUtils.setRequestAttr("dutyCode", WebHelp.getLoginUser().getDutyCode());
		return PAYMODIFYPATH + "list";
	}

	/**
	 * 正常付款、预付款、贷项通知单在付款新增(修改同理)，检查确保供应商所在OU和登陆用户所在OU要一致
	 * @param payAddBean
	 * @return
	 */
	public PayModifyBean validateProvider(PayModifyBean payModifyBean){
		List<ProviderBean> list = providerService.queryInfoByCode(payModifyBean.getProviderCode(),payModifyBean.getProviderAddrCode());
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
				payModifyBean.setProviderCode("");
				payModifyBean.setProviderName("");
				payModifyBean.setProviderType("");
				payModifyBean.setProvActNo("");
				payModifyBean.setBankName("");
				payModifyBean.setProvActCurr("");
				payModifyBean.setProviderAddr("");
				payModifyBean.setProviderAddrCode("");
				payModifyBean.setActName("");
				payModifyBean.setBankInfo("");
				payModifyBean.setBankCode("");
				payModifyBean.setBankArea("");
				payModifyBean.setActType("");
			}
		}
		return payModifyBean;
	}
	/**
	 * 付款修改信息查询
	 * 
	 * @param payModifyBean
	 * @return
	 */
	@RequestMapping("modify.do")
	public String modify(PayModifyBean payModifyBean) {
		//根据合同号得到合同的影像
		QueryContract cnt = payQueryService.getCntICMSByCntNum(payModifyBean.getCntNum());
		WebUtils.setRequestAttr("cnt", cnt);
		// 根据标志判断正常付款还是预付款
		if ("Y".equalsIgnoreCase(payModifyBean.getPayType())) {
			//贷项通知单
			if("0".equals(payModifyBean.getIsCreditNote())){
				// 1.原蓝字发票头信息查询
				PayAddBean payAddBean = new PayAddBean();
				payAddBean.setInvoiceIdBlue(payModifyBean.getInvoiceIdBlue());
				PayAddBean invoiceBlue = payAddService.queryInvoiceBlue(payAddBean);
				
				// 2.查询贷项通知单头信息
				payModifyBean.setPayFlag("1");
				payModifyBean.setTable("TD_PAY");
				PayModifyBean payInfo = payModifyService.queryPayInfo(payModifyBean);
				payInfo = validateProvider(payInfo);
				
				// 3.查询贷项通知单发票行信息
				payModifyBean.setPayType("1");
				List<PayModifyBean> payDevices = payModifyService.queryCreditDevicesById(payModifyBean);
				
				// 4.查询附件类型对应的值(从SYS_SELECT表中)
				List<PayAddBean> listAtTypes = payAddService.queryAtType();
				
				// 5.合同信息查询
				PayModifyBean constractInfo = payModifyService.constractInfo(payModifyBean);
				
				WebUtils.setRequestAttr("invoiceBlue", invoiceBlue);
				WebUtils.setRequestAttr("payInfo", payInfo);
				WebUtils.setRequestAttr("payDevices", payDevices);
				WebUtils.setRequestAttr("constractInfo", constractInfo);
				WebUtils.setRequestAttr("listAtTypes", listAtTypes);
				WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/pay/paymodify/list.do?VISIT_FUNC_ID=030304");
				
				return PAYMODIFYPATH + "creditNoteModify";
				
		    }else{
			// 合同信息查询
			PayModifyBean constractInfo = payModifyService
					.constractInfo(payModifyBean);
			
			payModifyBean.setPayFlag("1");
			// 查询付款信息
			payModifyBean.setTable("TD_PAY");
			PayModifyBean payInfo = payModifyService
					.queryPayInfo(payModifyBean);
			payInfo = validateProvider(payInfo);
			// 查询合同采购设备信息（正常）
			payModifyBean.setPayType("1");
			List<PayModifyBean> payDevices = payModifyService
					.queryDevicesById(payModifyBean);
			WebUtils.setRequestAttr("constractInfo", constractInfo);
			WebUtils.setRequestAttr("payInfo", payInfo);
			WebUtils.setRequestAttr("payDevices", payDevices);
			//查询附件类型对应的值(从SYS_SELECT表中)
			List<PayAddBean> listAtTypes = payAddService.queryAtType();
			WebUtils.setRequestAttr("listAtTypes", listAtTypes);
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/pay/paymodify/list.do?VISIT_FUNC_ID=030304");
			//非贷项通知单
				// 根据合同号查预付款核销信息
				List<PayModifyBean> payAdvanceCancelList = payModifyService
						.queryPayAdvanceCancel(payModifyBean);
				//过滤掉全部核销完的预付款
				List<PayModifyBean> pAdvanceCancelList = new ArrayList<PayModifyBean>();
				for(PayModifyBean pBean:payAdvanceCancelList){
					if(pBean.getPayAmt().compareTo(pBean.getCancelAmtTotal().subtract(pBean.getCancelAmt())) == 1){
						pAdvanceCancelList.add(pBean);
					}
				}
				
				// 查询合同采购设备信息（预付款核销）
				payModifyBean.setPayType("0");
				List<PayModifyBean> payAdvDevices = payModifyService
						.queryDevicesById(payModifyBean);

				WebUtils.setRequestAttr("payAdvanceCancelList",
						pAdvanceCancelList);
				boolean flag = false;
				if (!Tool.CHECK.isEmpty(payAdvanceCancelList)) {
					flag = true;
				}
				WebUtils.setRequestAttr("flag", flag);
				WebUtils.setRequestAttr("payAdvDevices", payAdvDevices);
				return PAYMODIFYPATH + "payModify";
		    }
		} else if ("N".equalsIgnoreCase(payModifyBean.getPayType())) {
			// 查询合同信息
			PayModifyBean constractInfo = payModifyService
					.constractInfo(payModifyBean);
			// 查询预付款信息
			payModifyBean.setPayFlag("0");
			payModifyBean.setTable("TD_PAY_ADVANCE");
			PayModifyBean payInfo = payModifyService
					.queryPayInfo(payModifyBean);
			payInfo = validateProvider(payInfo);
			//查询附件类型对应的值(从SYS_SELECT表中)
			List<PayAddBean> listAtTypes = payAddService.queryAtType();
			WebUtils.setRequestAttr("listAtTypes", listAtTypes);
			WebUtils.setRequestAttr("constractInfo", constractInfo);
			WebUtils.setRequestAttr("payInfo", payInfo);
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/pay/paymodify/list.do?VISIT_FUNC_ID=030304");
			return PAYMODIFYPATH + "payAdvModify";
		}
		return null;
	}

	/**
	 * 预付款修改保存
	 * 
	 * @param payModifyBean
	 * @return
	 */
	@RequestMapping("payAdvModifySave.do")
	@PreventDuplicateSubmit
	public String payAdvModifySave(PayModifyBean payModifyBean) {
		boolean flag = payModifyService.payAdvModifySave(payModifyBean);
		return ForwardPageUtils.getReturnUrlString("预付款修改保存", flag,
				new String[] { "list" });
	}

	/**
	 * 预付款修改提交
	 * 
	 * @param payModifyBean
	 * @return
	 */
	@RequestMapping("payAdvModifySubmit.do")
	@PreventDuplicateSubmit
	public String payAdvModifySubmit(PayModifyBean payModifyBean) {
		boolean flag = payModifyService.payAdvModifySubmit(payModifyBean);
		
		String[] link = new String[] { "list"};
		if(flag && "0".equals(payModifyBean.getIsOrder())){
			link = returnOrderLink(link,payModifyBean.getCntNum());
		}
		return ForwardPageUtils.getReturnUrlString("预付款修改提交", flag,link);
	}
	
	/**
	 * 正常付款修改保存
	 * 
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("payModifySave.do")
	@PreventDuplicateSubmit
	public String payModifySave(PayModifyBean payModifyBean) {
		boolean flag = payModifyService.payModifySave(payModifyBean);
		return ForwardPageUtils.getReturnUrlString("正常付款修改保存", flag,
				new String[] {"list"});
	}
	
	/**
	 * 正常付款修改提交
	 * 
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("payModifySubmit.do")
	@PreventDuplicateSubmit
	public String payModifySubmit(PayModifyBean payModifyBean) {
		boolean flag = payModifyService.payModifySubmit(payModifyBean);
		String[] link = new String[] { "list"};
		if(flag && "0".equals(payModifyBean.getIsOrder())){
			link = returnOrderLink(link,payModifyBean.getCntNum());
		}
		return ForwardPageUtils.getReturnUrlString("正常付款修改提交", flag,link);
	}
	
	/**
	 * 更新付款单的状态
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("changePayStatus.do")
	public String changePayStatus(PayAddBean payAddBean){
		boolean flag = payAddService.changePayStatus(payAddBean);
		return ForwardPageUtils.getReturnUrlString("正常付款修改提交", flag,new String[] {"list"});
	}
	/**
	 * 预付款信息删除
	 * 
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("payAdvDelete.do")
	@PreventDuplicateSubmit
	public String payAdvDelete(PayModifyBean payModifyBean){
		boolean flag = payModifyService.payAdvDelete(payModifyBean);
		return ForwardPageUtils.getReturnUrlString("预付款信息删除", flag,
				new String[] {"list"});
	}
	
	/**
	 * 正常付款信息删除
	 * 
	 * @param payAddBean
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("payDelete.do")
	@PreventDuplicateSubmit
	public String payDelete(PayModifyBean payModifyBean) throws Exception{
		boolean flag = payModifyService.payDelete(payModifyBean);
		return ForwardPageUtils.getReturnUrlString("正常付款信息删除", flag,
				new String[] {"list"});
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
	public String checkBgtFrozen(PayModifyBean payModifyBean) throws Exception{
		
		AbstractJsonObject json = new SuccessJsonObject();
		Map<String, String> param = new HashMap<String, String>();
		
		int returnFlag = 0;
		boolean lockFlag = true;
		//校验互斥锁 //预提待摊的才需要在合同确立的时候占用预算，所以才需要这个时候来加互斥锁
		String memo = "付款单号为"+payModifyBean.getPayId()+"付款修改提交待复核时增加锁";
		try {
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
			param.put("payId", payModifyBean.getPayId());
			param.put("flag", "");
			payAddService.checkBgtFrozen(param);
			String flag = param.get("flag");
			if("0".equals(flag)){//0表示预算冻结失败,查询付款冻结失败的信息
				String msgInfo = param.get("msgInfo");
//				List<PayAddBgtBean> bgtList = payAddService.queryBgtFrozenFailMsg(payId);
//				String bgtMsg = "";
//				for(PayAddBgtBean bgtBean : bgtList){
//					bgtMsg += "物料（"+bgtBean.getMatrName()+"）"+bgtBean.getMemo()+";";
//				}
				json.put("bgtMsg", msgInfo);
				json.put("payId", payModifyBean.getPayId());
			}
			json.put("flag", flag);
		}
		
		//删除锁
		if (returnFlag == 2) {
			try {
				concurrentService.delConcurrentLock(ConcurrentType.Concurrent_A, ConcurrentType.A2,"");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				concurrentService.delConcurrentLock(ConcurrentType.Concurrent_A, ConcurrentType.A2,"");
			}
			
		}
		return json.writeValueAsString();
	}
	
	/**
	 * 查询付款冻结失败的信息
	 * @return
	 */
	@RequestMapping("queryBgtFrozenFailMsg.do")
	public String  queryBgtFrozenFailMsg(String payId){
		List<PayAddBgtBean> bgtList = payAddService.queryBgtFrozenFailMsg(payId);
		WebUtils.setRequestAttr("bgtList",bgtList);
		return PAYMODIFYPATH + "bgtFrozenFailMsg";
	}
	
	/**
	 * ajax删除预算冻结失败的临时信息
	 * @param payId
	 * @return
	 */
	@RequestMapping("deleteBgtFrozenTemp.do")
	@ResponseBody
	public String deleteBgtFrozenTemp(String payId){
		AbstractJsonObject json = new SuccessJsonObject();
		boolean flag = payModifyService.deleteBgtFrozenTemp(payId);
		json.put("flag", flag);
		return json.writeValueAsString();
	}
}
