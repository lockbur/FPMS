package com.forms.prms.web.cleanpaydeal.cleanpaycheck.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.web.cleanpaydeal.cleanpay.domain.CleanPayBean;
import com.forms.prms.web.cleanpaydeal.cleanpay.service.CleanPayService;
import com.forms.prms.web.cleanpaydeal.cleanpaycheck.domain.CleanPayCheckBean;
import com.forms.prms.web.cleanpaydeal.cleanpaycheck.service.CleanPayCheckService;
import com.forms.prms.web.cleanpaydeal.cleanpaymodify.service.CleanModifyService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/cleanpaydeal/cleanpaycheck/")
public class CleanPayCheckController {
	private static final String CLEANCHECKPATH = "/cleanpaydeal/cleanpaycheck/";
	@Autowired
	private CleanPayCheckService cleanPayCheckService;
	@Autowired
	private CleanPayService cleanPayService;
	@Autowired
	private CleanModifyService cleanModifyService;

	/**
	 * 订单暂收结清待复核信息列表查询
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("list.do")
	public String list(CleanPayCheckBean bean) {
		ReturnLinkUtils.addReturnLink("list", "返回列表");
		List<CleanPayCheckBean> list = cleanPayCheckService.getList(bean);// 得到待复核集合
		WebUtils.setRequestAttr("list", list);
		WebUtils.setRequestAttr("selectInfo", bean);
		return CLEANCHECKPATH + "list";
	}

	/**
	 * 暂收结清信息查询
	 * 
	 * @return
	 */
	@RequestMapping("detail.do")
	public String detail(CleanPayBean bean) {
		// 查询合同信息
		CleanPayBean constractInfo = cleanPayService.constractInfo(bean);
		// 查询付款信息
		CleanPayBean payInfo = cleanPayService.queryPayInfo(bean);
		// 查询正在结清的总金额
		String cleanAmtIngTotal = cleanPayService.queryCleanAmtIng(bean);
		if(!Tool.CHECK.isBlank(cleanAmtIngTotal)){
			payInfo.setCleanAmtIngTotal(new BigDecimal(cleanAmtIngTotal));
		}else{
			payInfo.setCleanAmtIngTotal(new BigDecimal(0));
		}
		// 查询暂收结清明细信息
		CleanPayBean cleanedPayDetaiInfo = cleanModifyService.queryCleanedDetail(bean);
		// 查询已结清列表
		List<CleanPayBean> payCleanedPayInfoList = cleanPayService.queryCleanedPayInfo(bean);
		WebUtils.setRequestAttr("payCleanedPayInfoList", payCleanedPayInfoList);
		WebUtils.setRequestAttr("constractInfo", constractInfo);
		WebUtils.setRequestAttr("payInfo", payInfo);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/cleanpaydeal/cleanpaycheck/list.do?VISIT_FUNC_ID=03050301");
		WebUtils.setRequestAttr("cleanedPayDetaiInfo", cleanedPayDetaiInfo);
		return CLEANCHECKPATH + "detail";
	}

	/**
	 * 
	 * @param approve
	 * @return
	 */
	@RequestMapping("agree.do")
	public String Agree(CleanPayCheckBean bean) {
		cleanPayCheckService.Agree(bean);
		WebUtils.getMessageManager().addInfoMessage("复核通过!");
		ReturnLinkUtils.setShowLink("list");
		return ForwardPageUtils.getSuccessPage();
	}

	/**
	 * 
	 * @param approve
	 * @return
	 */
	@RequestMapping("back.do")
	public String Back(CleanPayCheckBean bean) {
		cleanPayCheckService.Back(bean);
		WebUtils.getMessageManager().addInfoMessage("复核退回!");
		ReturnLinkUtils.setShowLink("list");
		return ForwardPageUtils.getSuccessPage();
	}
}
