package com.forms.prms.web.cleanpaydeal.cleanpaymodify.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.platform.web.token.PreventDuplicateSubmit;
import com.forms.prms.web.cleanpaydeal.cleanpay.domain.CleanPayBean;
import com.forms.prms.web.cleanpaydeal.cleanpay.service.CleanPayService;
import com.forms.prms.web.cleanpaydeal.cleanpaymodify.service.CleanModifyService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/cleanpaydeal/cleanpaymodify/")
public class CleanModifyController {

	private static final String CLEANMODIFYPATH = "/cleanpaydeal/cleanpaymodify/";
	@Autowired
	private CleanModifyService cleanModifyService;
	
	@Autowired
	private CleanPayService cleanPayService;
	
	/**
	 * 查询暂收结清信息列表
	 * 
	 * @return
	 */
	@RequestMapping("queryCleanList.do")
	@AddReturnLink(id = "queryCleanList", displayValue = "暂收结清列表")
	public String queryCleanList(CleanPayBean cleanPayBean) {
		List<CleanPayBean> cleanList = cleanModifyService
				.cleanList(cleanPayBean);
		WebUtils.setRequestAttr("cleanpayList", cleanList);
		WebUtils.setRequestAttr("selectInfo", cleanPayBean);
		return CLEANMODIFYPATH + "list";
	}
	
	/**
	 * 暂收结清信息编辑
	 * 
	 * @return
	 */
	@RequestMapping("cleanpayEdit.do")
	public String cleanpayEdit(CleanPayBean cleanPayBean){
		// 查询合同信息
		CleanPayBean constractInfo = cleanPayService.constractInfo(cleanPayBean);
		// 查询付款信息
		CleanPayBean payInfo = cleanPayService.queryPayInfo(cleanPayBean);
		//查询正在结清的总金额
		String cleanAmtIngTotal = cleanPayService.queryCleanAmtIng(cleanPayBean);
		if(!Tool.CHECK.isBlank(cleanAmtIngTotal)){
			payInfo.setCleanAmtIngTotal(new BigDecimal(cleanAmtIngTotal));
		}else{
			payInfo.setCleanAmtIngTotal(new BigDecimal(0));
		}
		//计算剩余结清金额(暂收金额-已结清暂收金额-正在暂收结清的总金额)
		BigDecimal uncleanAmt = payInfo.getSuspenseAmt().subtract(payInfo.getSusTotalAmt()).subtract(payInfo.getCleanAmtIngTotal());
		payInfo.setUncleanAmt(uncleanAmt);
		//查询暂收结清明细信息
		CleanPayBean cleanedPayDetaiInfo = cleanModifyService.queryCleanedDetail(cleanPayBean);
		// 查询结清列表
		List<CleanPayBean> payCleanedPayInfoList = cleanPayService.queryCleanedPayInfo(cleanPayBean);
		WebUtils.setRequestAttr("payCleanedPayInfoList", payCleanedPayInfoList);
		WebUtils.setRequestAttr("constractInfo", constractInfo);
		WebUtils.setRequestAttr("payInfo", payInfo);
		WebUtils.setRequestAttr("cleanPayBean", cleanPayBean);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/cleanpaydeal/cleanpaymodify/queryCleanList.do?VISIT_FUNC_ID=030502");
		WebUtils.setRequestAttr("cleanedPayDetaiInfo", cleanedPayDetaiInfo);
		return CLEANMODIFYPATH + "modify";
	}
	
	/**
	 * 暂收结清信息修改保存
	 * @param cleanPayBean
	 * @return
	 */
	@RequestMapping("cleanpayEditSave.do")
	@PreventDuplicateSubmit
	public String cleanpayEditSave(CleanPayBean cleanPayBean){
		boolean flag = cleanModifyService.cleanpayEditSave(cleanPayBean);
		return ForwardPageUtils.getReturnUrlString("暂收结清修改保存", flag,
				new String[] {"queryCleanList" });
	}
	
	/**
	 * 暂收结清信息修改提交
	 * @param cleanPayBean
	 * @return
	 */
	@RequestMapping("cleanpayEditSubmit.do")
	@PreventDuplicateSubmit
	public String cleanpayEditSubmit(CleanPayBean cleanPayBean){
		boolean flag = cleanModifyService.cleanpayEditSubmit(cleanPayBean);
		return ForwardPageUtils.getReturnUrlString("暂收结清修改提交", flag,
				new String[] {"queryCleanList" });
	}
}
