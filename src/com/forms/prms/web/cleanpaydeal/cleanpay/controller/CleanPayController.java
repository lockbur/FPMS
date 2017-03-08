package com.forms.prms.web.cleanpaydeal.cleanpay.controller;

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
import com.forms.prms.web.util.ForwardPageUtils;

/**
 * 业务逻辑：1.查询可做暂收结清的列表（条件：①付款状态是成功的12，②暂收金额需>0,③暂收金额需>已暂收金额（除回冲外））
 * 			2.暂收结清处理信息查询(查询合同信息、查询付款信息、查询正在结清的总金额、生成暂收结清编号、查询已结清列表)
 * 			3.暂收结清处理信息保存或提交
 * author : lisj <br>
 * date : 2015-03-12<br>
 * 暂收结清Action
 */
@Controller
@RequestMapping("/cleanpaydeal/cleanpay/")
public class CleanPayController {
	
	private static final String CLEANPAYPATH = "/cleanpaydeal/cleanpay/";
	
	@Autowired
	private CleanPayService cleanPayService;
	
	/**
	 * 查询可做暂收结清的列表
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	@RequestMapping("cleanpayList.do")
	@AddReturnLink(id = "cleanpayList", displayValue = "暂收结清列表")
	public String cleanpayList(CleanPayBean cleanPayBean){
		List<CleanPayBean> cleanpayList = cleanPayService.cleanpayList(cleanPayBean);
		WebUtils.setRequestAttr("cleanpayList", cleanpayList);
		WebUtils.setRequestAttr("selectInfo", cleanPayBean);
		return CLEANPAYPATH + "list";
	}
	
	/**
	 * (正常付款)暂收结清处理信息查询
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	@RequestMapping("queryCleanPayDeal.do")
	public String queryCleanPayDeal(CleanPayBean cleanPayBean) {
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
		// 生成暂收结清编号
		//cleanPayBean.setPayFlag("2");//付款标志：2--暂收结清
		//constractInfo.setCleanPayId(cleanPayService.createCleanPayId(cleanPayBean));
		// 查询已结清列表
		List<CleanPayBean> payCleanedPayInfoList = cleanPayService.queryCleanedPayInfo(cleanPayBean);
		WebUtils.setRequestAttr("constractInfo", constractInfo);
		WebUtils.setRequestAttr("payInfo", payInfo);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/cleanpaydeal/cleanpay/cleanpayList.do?VISIT_FUNC_ID=030501");
		WebUtils.setRequestAttr("payCleanedPayInfoList", payCleanedPayInfoList);
		return CLEANPAYPATH + "cleanpayDel";
	}

	
	/**
	 * (正常付款)暂收结清处理信息保存
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	@RequestMapping("payCleanDealSave.do")
	@PreventDuplicateSubmit
	public String payCleanDealSave(CleanPayBean cleanPayBean) {
		boolean flag = cleanPayService.payCleanDealSave(cleanPayBean);
		CleanPayBean  pay=cleanPayService.QuerySortId(cleanPayBean);	
		WebUtils.setRequestAttr("normalPayId", cleanPayBean.getNormalPayId());
		WebUtils.setRequestAttr("sortId", pay.getSortId());
		WebUtils.setRequestAttr("cutNum", cleanPayBean.getCntNum());
		WebUtils.setRequestAttr("buttonValue","暂收结清封面打印");
		return ForwardPageUtils.getReturnUrlString("暂收结清处理保存", flag,
				new String[] {"cleanpayList"});
	}
	
	/**
	 * (正常付款)暂收结清处理信息提交
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	@RequestMapping("payCleanDealSubmit.do")
	@PreventDuplicateSubmit
	public String payCleanDealSubmit(CleanPayBean cleanPayBean) {
		boolean flag = cleanPayService.payCleanDealSubmit(cleanPayBean);
		CleanPayBean  pay=cleanPayService.QuerySortId(cleanPayBean);
		WebUtils.setRequestAttr("normalPayId", cleanPayBean.getNormalPayId());
		WebUtils.setRequestAttr("sortId", pay.getSortId());
		WebUtils.setRequestAttr("cutNum", cleanPayBean.getCntNum());
		WebUtils.setRequestAttr("buttonValue","暂收结清封面打印");
		return ForwardPageUtils.getReturnUrlString("暂收结清处理提交", flag,
				new String[] {"cleanpayList" });
	}

	/**
	 * 已结清明细查询
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	@RequestMapping("queryCleanedDetail.do")
	public String queryCleanedDetail(CleanPayBean cleanPayBean) {
		CleanPayBean cleanedPayDetaiInfo = cleanPayService.queryCleanedDetail(cleanPayBean);
		WebUtils.setRequestAttr("cleanedPayDetaiInfo", cleanedPayDetaiInfo);
		//根据参数判断从暂收结清详情中返回到哪个页面
		if(cleanPayBean.getFlag()!=null){
			if("1".equals(cleanPayBean.getFlag())){
				WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/cleanpaydeal/cleanpay/queryCleanPayDeal.do?VISIT_FUNC_ID=03050101&normalPayId="+cleanPayBean.getNormalPayId()+"&sortId="+cleanPayBean.getSortId()+"&cntNum="+cleanPayBean.getCntNum());
			}
			else if("2".equals(cleanPayBean.getFlag())){
				WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/cleanpaydeal/cleanpaycheck/detail.do?VISIT_FUNC_ID=03050302&cleanPayId="+cleanPayBean.getCleanPayId()+"&payId="+cleanPayBean.getPayId()+"&cntNum="+cleanPayBean.getCntNum());
			}
			else if("3".equals(cleanPayBean.getFlag())){
				WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/cleanpaydeal/cleanpaymodify/cleanpayEdit.do?VISIT_FUNC_ID=03050201&normalPayId="+cleanPayBean.getNormalPayId()+"&sortId="+cleanPayBean.getSortId()+"&cntNum="+cleanPayBean.getCntNum());
			}
			else if("4".equals(cleanPayBean.getFlag())){
				WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/cleanpaydeal/cleanpayquery/detail.do?VISIT_FUNC_ID=03050502&normalPayId="+cleanPayBean.getNormalPayId()+"&sortId="+cleanPayBean.getSortId()+"&cntNum="+cleanPayBean.getCntNum());
			}
			else{
				WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/cleanpaydeal/cleanpaysure/detail.do?VISIT_FUNC_ID=03050402&normalPayId="+cleanPayBean.getNormalPayId()+"&sortId="+cleanPayBean.getSortId()+"&cntNum="+cleanPayBean.getCntNum());
			}
		}
		return CLEANPAYPATH + "cleanedpayDetail";
	}
}
