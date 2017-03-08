package com.forms.prms.web.pay.payquery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.contract.query.domain.QueryContract;
import com.forms.prms.web.pay.payAdd.domain.PayAddBean;
import com.forms.prms.web.pay.payAdd.service.PayAddService;
import com.forms.prms.web.pay.payquery.domain.CleanLogBean;
import com.forms.prms.web.pay.payquery.domain.PayQueryBean;
import com.forms.prms.web.pay.payquery.service.PayQueryService;
import com.forms.prms.web.sysmanagement.montAprvBatch.export.service.ExportService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/pay/payquery")
public class PayQueryController {
	private static final String PAYQUERYPATH = "/pay/payquery/";
	@Autowired
	private PayQueryService payQueryService;
	@Autowired
	private PayAddService payAddService;
	
	@Autowired
	private ExportService exportService;

	
	@RequestMapping("org1List.do")
	public String org1List(PayQueryBean bean)
	{
		bean.setOrgFlag("1");//省行
		return list(bean);
	}
	
	@RequestMapping("org2List.do")
	public String org2List(PayQueryBean bean)
	{
		bean.setOrgFlag("2");//二级行
		return list(bean);
	}
	
	@RequestMapping("dutyCodeList.do")
	public String dutyCodeList(PayQueryBean bean)
	{
		bean.setOrgFlag("3");//业务部门
		return list(bean);
	}
	
	/**
	 * 付款信息列表查询
	 * 
	 * @param PaySureBean
	 * @return
	 */
	@RequestMapping("list.do")
	public String list(PayQueryBean bean) {
		ReturnLinkUtils.addReturnLink("list", "返回列表");
		List<PayQueryBean> list = payQueryService.list(bean);// 付款集合
		String org1Code = WebHelp.getLoginUser().getOrg1Code();// 得到登录人的所在的一级分行
		WebUtils.setRequestAttr("list", list);
		WebUtils.setRequestAttr("selectInfo", bean);
		WebUtils.setRequestAttr("org1Code", org1Code);
		
		if("contract".equals(bean.getFromFlag()))
		{
			return PAYQUERYPATH + "contractlist";
		}
		return PAYQUERYPATH + "list";
	}

	/**
	 * 得到某合同下的预付款和付款列表类别信息
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("cntDetail.do")
	public String cntDetail(PayQueryBean bean) {
		if (Tool.CHECK.isEmpty(bean.getTabsIndex())) {
			bean.setTabsIndex("0");
		}
		PayQueryBean cntBean = payQueryService.getCntByCntNum(bean.getCntNum());
		List<PayQueryBean> payList = payQueryService.getPayListByCntNum(bean.getCntNum());
		List<PayQueryBean> prePayList = payQueryService.getPrePayListByCntNum(bean.getCntNum());
		WebUtils.setRequestAttr("cntInfo", cntBean);
		WebUtils.setRequestAttr("selectInfo", bean);
		WebUtils.setRequestAttr("payList", payList);
		WebUtils.setRequestAttr("prePayList", prePayList);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
				+ "/pay/payquery/list.do?VISIT_FUNC_ID=03030701");
		return PAYQUERYPATH + "cntDetail";
	}

	/**
	 * 得到预付款或者付款的的详细信息
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("detail.do")
	public String payDetail(PayQueryBean bean) {
		String fromFlag = bean.getFromFlag();
		String path = "";
		List<PayQueryBean> payDeviceList = null;
		List<PayQueryBean> prePayDeviceList = null;
		List<PayQueryBean> prePayCancleList = null;
		
		if("0".equals(bean.getIsCreditNote())){
			
			PayAddBean payAddBean = new PayAddBean();
			payAddBean.setInvoiceIdBlue(bean.getInvoiceIdBlue());
			
			// 1.原蓝字发票头信息查询
			PayAddBean invoiceBlue = payAddService.queryInvoiceBlue(payAddBean);
			
			// 2.查询贷项通知单头信息
			bean = payQueryService.getPayByPayId(bean.getPayId());

			// 3.查询贷项通知单发票行信息
			payDeviceList = payQueryService.getPayDeviceListByPayId(bean.getPayId());
			
			//根据合同号得到合同的影像
			QueryContract cnt = payQueryService.getCntICMSByCntNum(bean.getCntNum());
						
			WebUtils.setRequestAttr("cnt", cnt);
			WebUtils.setRequestAttr("invoiceBlue", invoiceBlue);
			WebUtils.setRequestAttr("payInfo", bean);
			WebUtils.setRequestAttr("payDevices", payDeviceList);
			WebHelp.setLastPageLink("uri", "list");		
			WebHelp.setLastPageLink("contracturi", "");
			
			return PAYQUERYPATH + "payCreditDetail";
		}else{
		
		//根据合同号得到合同的影像
		QueryContract cnt = payQueryService.getCntICMSByCntNum(bean.getCntNum());
		WebUtils.setRequestAttr("cnt", cnt);
		if ("Y".equals(bean.getIsPrePay())) {
			bean = payQueryService.getPrePayByPayId(bean.getPayId());
			path = "prePayDetail";
			if("contract".equals(fromFlag))
			{
				path = "prePayDetailContract";
			}
		} else {
			bean = payQueryService.getPayByPayId(bean.getPayId());
			payDeviceList = payQueryService.getPayDeviceListByPayId(bean.getPayId());
			prePayDeviceList = payQueryService.getPrePayDeviceListByPayId(bean.getPayId());
			prePayCancleList = payQueryService.getPrePayCancleListByCntNum(bean);
			path = "payDetail";
			if("contract".equals(fromFlag))
			{
				path = "payDetailContract";
			}
			// 查询付款Log信息
//			List<PayQueryBean> lists = payQueryService.queryPayLog(bean.getPayId());
//			WebUtils.setRequestAttr("lists", lists);
			WebUtils.setRequestAttr("userIdL", WebHelp.getLoginUser().getUserId());// 当前登录人ID
			// 查找正常付款的数据个数
			int num1 = payQueryService.queryNormalPayNumById(bean.getPayId(), null);
			// 查询正常付款的付款Y或N的数据个数
			// bean.setPayCancelState("Y");
			int num2 = payQueryService.queryNormalPayNumById(bean.getPayId(), "Y");

			if (num1 == num2 || num2 == 0) {
				WebUtils.setRequestAttr("flag", true);
			} else {
				WebUtils.setRequestAttr("flag", false);
			}

		}
		WebHelp.setLastPageLink("uri", "list");
		WebUtils.setRequestAttr("payInfo", bean);
		WebUtils.setRequestAttr("payDeviceList", payDeviceList);
		WebUtils.setRequestAttr("prePayDeviceList", prePayDeviceList);
		WebUtils.setRequestAttr("prePayCancleList", prePayCancleList);
		WebHelp.setLastPageLink("contracturi", "");
		return PAYQUERYPATH + path;
		
		}
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
				+ "/pay/payquery/detail.do?VISIT_FUNC_ID=03030703&isPrePay=N&payId=" + bean.getPayId() + "&cntNum="
				+ bean.getCntNum());
		return PAYQUERYPATH + "payAdvCancelDetail";
	}

	/**
	 * flag=0:暂收转正常/flag=1:正常转暂收
	 * 
	 * @param cleanLogBean
	 * @return
	 */
	@RequestMapping("change.do")
	public String change(CleanLogBean cleanLogBean) {
		payQueryService.change(cleanLogBean);
		String str = "";
		if ("0".equals(cleanLogBean.getFlag())) {
			str = "暂收转正常";
		} else {
			str = "正常转暂收";
		}
		return ForwardPageUtils.getReturnUrlString(str, true, new String[] {});
	}
	
	/**
	 * 付款流水明细
	 * @param payQueryBean
	 * @return
	 */
	@RequestMapping("queryPayLogDetail.do")
	public String queryPayLogDetail(PayQueryBean payQueryBean){
		PayQueryBean pqbBean = payQueryService.queryPayLogDetail(payQueryBean);
		WebUtils.setRequestAttr("payLogDetailBean", pqbBean);
		return PAYQUERYPATH + "queryPayLogDetail";
	}
	/**
	 * @methodName print
	 * desc  
	 * 付款打印
	 * @param payQueryBean
	 * @return
	 */
	@RequestMapping("print.do")
	public String print(PayQueryBean payQueryBean) {
		String path = "";
		//检查是否有核销
		if("0".equals(payQueryBean.getIsOrder())){//是订单
			List<PayAddBean> list = payAddService.queryCancelDevices(payQueryBean.getPayId());
			if(!Tool.CHECK.isEmpty(list)){//不为空则存在核销数据
				WebUtils.setRequestAttr("isExitCancel","true");
			}else{
				WebUtils.setRequestAttr("isExitCancel","false");
			}
		}
		WebUtils.setRequestAttr("payId", payQueryBean.getPayId());
		WebUtils.setRequestAttr("isOrder", payQueryBean.getIsOrder());
		WebUtils.setRequestAttr("buttonFlag", payQueryBean.getButtonFlag());
		WebUtils.setRequestAttr("invoiceId", payQueryBean.getInvoiceId());
		WebUtils.setRequestAttr("userId", WebHelp.getLoginUser().getUserId());
		if ("Y".equals(payQueryBean.getIsPrePay())) {
			 path = "printAdv";
		}else{
			 path = "print";
		}
		return PAYQUERYPATH + path;
	}
	
	/**
	 * 22付款明细查询
	 * @param invoiceId
	 * @return
	 */
	@RequestMapping("queryPay22Detail.do")
	public String queryPay22Detail(String invoiceId){
		List<PayQueryBean> list = payQueryService.queryPay22Detail(invoiceId);
		WebUtils.setRequestAttr("list", list);
		return PAYQUERYPATH + "queryPay22Detail";
	}
	/**
	 * 信息导出
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/exportData.do")
	@ResponseBody
	public String exportData(PayQueryBean bean){
		AbstractJsonObject jsonObject = new SuccessJsonObject();		
		//Excel导出操作
		String taskId = null;
		try {
			taskId = payQueryService.exportData(bean);
			if (Tool.CHECK.isBlank(taskId)) {
				jsonObject.put("pass", false);
			} else {
				jsonObject.put("pass", true);
			}		
		} catch (Exception e) {
			try{
				//如果  taskId已插入出现异常,则更新为失败
				if(!Tool.CHECK.isBlank(taskId)){
					exportService.updateTaskDataFlag(taskId);
				}
			}catch (Exception e1) {
				e1.printStackTrace();
			}
			jsonObject.put("pass", false);
			e.printStackTrace();
		}
		return jsonObject.writeValueAsString();
	}
	/**
	 *预付款 信息导出
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/PrePayexportData.do")
	@ResponseBody
	public String PrePayexportData(PayQueryBean bean){
		AbstractJsonObject jsonObject = new SuccessJsonObject();		
		//Excel导出操作
		String taskId = null;
		try {
			taskId = payQueryService.PrePayexportData(bean);
			if (Tool.CHECK.isBlank(taskId)) {
				jsonObject.put("pass", false);
			} else {
				jsonObject.put("pass", true);
			}		
		} catch (Exception e) {
			try{
				//如果  taskId已插入出现异常,则更新为失败
				if(!Tool.CHECK.isBlank(taskId)){
					exportService.updateTaskDataFlag(taskId);
				}
			}catch (Exception e1) {
				e1.printStackTrace();
			}
			jsonObject.put("pass", false);
			e.printStackTrace();
		}
		return jsonObject.writeValueAsString();
	}
}
