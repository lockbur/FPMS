package com.forms.prms.web.amortization.payErrorData.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.consts.WebConsts;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.amortization.payErrorData.domain.PayErrorDataBean;
import com.forms.prms.web.amortization.payErrorData.service.PayErrorDataService;
import com.forms.prms.web.pay.payAdd.domain.PayAddBean;
import com.forms.prms.web.pay.payAdd.service.PayAddService;
import com.forms.prms.web.util.ForwardPageUtils;
/**
 * Title:		payErrorDataController
 * Description:	付款异常数据补录模块的Controller层
 * Coryright: 	formssi
 * @author：		wangtao
 * @project：	ERP
 * @date：		2015-05-25
 * @version：	1.0
 */
@Controller
@RequestMapping("/amortization/payErrorData")
public class PayErrorDataController {
	//当前Controller位于项目中的前置路径
	private final String URL = "/amortization/payErrorData/";
	
	@Autowired
	private PayErrorDataService payErrorDataService;
	@Autowired
	private PayAddService payAddService;
	
	@RequestMapping("org1List.do")
	public String org1List(PayErrorDataBean payErrorDataBean)
	{   
		ReturnLinkUtils.addReturnLink("org1List", "返回列表");
		payErrorDataBean.setOrgFlag("1");//省行
		return list(payErrorDataBean);
	}
	
	@RequestMapping("org2List.do")
	public String org2List(PayErrorDataBean payErrorDataBean)
	{	
		ReturnLinkUtils.addReturnLink("org2List", "返回列表");
		payErrorDataBean.setOrgFlag("2");//二级行
		return list(payErrorDataBean);
	}
	
	@RequestMapping("dutyCodeList.do")
	public String dutyCodeList(PayErrorDataBean payErrorDataBean)
	{
		ReturnLinkUtils.addReturnLink("dutyCodeList", "返回列表");
		payErrorDataBean.setOrgFlag("3");//业务部门
		return list(payErrorDataBean);
	}
	/**
	 * 列表查询
	 * @param tdPayRepairBean
	 * @return
	 */
	@RequestMapping("list.do")
	public String list(PayErrorDataBean payErrorDataBean) {
		List<PayErrorDataBean> list = payErrorDataService.getList(payErrorDataBean);
		WebUtils.setRequestAttr("list", list);

		// 查看用户是否为总行超级管理员
		String isSuperAdmin = WebHelp.getLoginUser().getIsSuperAdmin();
		if("A0001".equals(WebHelp.getLoginUser().getOrg1Code()) && "1".equals(isSuperAdmin))
		{
			payErrorDataBean.setIsCenterAdmin("1");
		}
		else
		{
			payErrorDataBean.setIsCenterAdmin("0");
		}
		WebUtils.setRequestAttr("orgList", payErrorDataService.getOrgList());
		WebUtils.setRequestAttr("selectInfo", payErrorDataBean);
		return URL + "list";
	}
	/**
	 * 订单类异常数据处理
	 * @param tdPayRepairBean
	 * @return
	 */
	@RequestMapping("deal.do")
	public String deal(PayErrorDataBean payErrorDataBean){
		payErrorDataService.deal(payErrorDataBean);
		WebUtils.getMessageManager().addInfoMessage("处理成功!");
		if("1".equals(payErrorDataBean.getOrgFlag())){
			ReturnLinkUtils.setShowLink("org1List");
		}
		else if("2".equals(payErrorDataBean.getOrgFlag())){
			ReturnLinkUtils.setShowLink("org2List");
		}
		else{
			ReturnLinkUtils.setShowLink("dutyCodeList");
		}
		return ForwardPageUtils.getSuccessPage();
	}
	
	@RequestMapping("detail.do")
	public String detail(PayErrorDataBean payErrorDataBean){
		// 根据标志判断正常付款还是预付款
				if (WebUtils.getParameter("payType").equals("01")) {
					// 合同信息查询
					PayErrorDataBean constractInfo = payErrorDataService.constractInfo(payErrorDataBean);
					WebUtils.setRequestAttr("payErrorDataBean", payErrorDataBean);
					// 查询付款信息
					payErrorDataBean.setTable("TD_PAY");
					PayErrorDataBean payInfo = payErrorDataService.queryPayInfo(payErrorDataBean);
					// 根据合同号查预付款核销信息
					List<PayErrorDataBean> payAdvanceCancelList = payErrorDataService
							.queryPayAdvanceCancel(payErrorDataBean);
					// 查询合同采购设备信息
					payErrorDataBean.setPayType("1");
					List<PayErrorDataBean> payDevices = payErrorDataService
							.queryDevicesById(payErrorDataBean);
					payErrorDataBean.setPayType("0");
					List<PayErrorDataBean> payAdvDevices = payErrorDataService
							.queryDevicesById(payErrorDataBean);

					WebUtils.setRequestAttr("constractInfo", constractInfo);
					WebUtils.setRequestAttr("payInfo", payInfo);
					WebUtils.setRequestAttr("payAdvanceCancelList",
							payAdvanceCancelList);
					boolean flag = false;
					if (!Tool.CHECK.isEmpty(payAdvanceCancelList)) {
						flag = true;
					}
					//查询附件类型对应的值(从SYS_SELECT表中)
					List<PayAddBean> listAtTypes = payAddService.queryAtType();
					WebUtils.setRequestAttr("listAtTypes", listAtTypes);
					WebUtils.setRequestAttr("flag", flag);
					WebUtils.setRequestAttr("payDevices", payDevices);
					WebUtils.setRequestAttr("payAdvDevices", payAdvDevices);
					WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/amortization/payErrorData/list.do?VISIT_FUNC_ID=040702");
					return URL + "payNormalDetail";
					
				} else if (WebUtils.getParameter("payType").equals("02")) {
					// 查询合同信息
					PayErrorDataBean constractInfo = payErrorDataService
							.constractInfo(payErrorDataBean);
					// 查询预付款信息
					payErrorDataBean.setPayFlag("0");
					payErrorDataBean.setTable("TD_PAY_ADVANCE");
					PayErrorDataBean payInfo = payErrorDataService
							.queryPayInfo(payErrorDataBean);
					//查询附件类型对应的值(从SYS_SELECT表中)
					List<PayAddBean> listAtTypes = payAddService.queryAtType();
					WebUtils.setRequestAttr("listAtTypes", listAtTypes);
					WebUtils.setRequestAttr("constractInfo", constractInfo);
					WebUtils.setRequestAttr("payInfo", payInfo);
					WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/amortization/payErrorData/list.do?VISIT_FUNC_ID=040702");
					return URL + "payAdvDetail";
				}
				return null;
		
	}
}
