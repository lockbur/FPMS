package com.forms.prms.web.cleanpaydeal.cleanpayquery.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.cleanpaydeal.cleanpay.domain.CleanPayBean;
import com.forms.prms.web.cleanpaydeal.cleanpay.service.CleanPayService;
import com.forms.prms.web.cleanpaydeal.cleanpaymodify.service.CleanModifyService;
import com.forms.prms.web.cleanpaydeal.cleanpayquery.service.CleanPayQueryService;
import com.forms.prms.web.pay.payquery.domain.PayQueryBean;
import com.forms.prms.web.sysmanagement.uploadfilemanage.service.UpFileManagerService;

@Controller
@RequestMapping("/cleanpaydeal/cleanpayquery/")
public class CleanPayQueryController {
	private static final String CLEANQUERYPATH = "/cleanpaydeal/cleanpayquery/";
	@Autowired
	private CleanPayQueryService cleanPayQueryService;
	@Autowired
	private CleanPayService cleanPayService;
	@Autowired
	private CleanModifyService cleanModifyService;
	@Autowired 
	public UpFileManagerService upFileService;
	
	@RequestMapping("org1List.do")
	public String org1List(CleanPayBean bean)
	{
		bean.setOrgFlag("1");//省行
		return list(bean);
	}
	
	@RequestMapping("org2List.do")
	public String org2List(CleanPayBean bean)
	{
		bean.setOrgFlag("2");//二级行
		return list(bean);
	}
	
	@RequestMapping("dutyCodeList.do")
	public String dutyCodeList(CleanPayBean bean)
	{
		bean.setOrgFlag("3");//业务部门
		return list(bean);
	}
	
	/**
	 * 订单暂收结清信息列表查询
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("list.do")
	public String list(CleanPayBean bean) {
		ReturnLinkUtils.addReturnLink("list", "返回列表");
		List<CleanPayBean> list = cleanPayQueryService.getList(bean);// 得到所有结清编号的数据
		WebUtils.setRequestAttr("list", list);
		WebUtils.setRequestAttr("selectInfo", bean);
		return CLEANQUERYPATH + "list";
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
		WebHelp.setLastPageLink("uri", "list");
		WebUtils.setRequestAttr("cleanedPayDetaiInfo", cleanedPayDetaiInfo);
		return CLEANQUERYPATH + "detail";
	}
	/**
	 * 暂收结清封面打印
	 * 
	 * 
	 * @return
	 * @author 
	 */
	@RequestMapping("print.do")
	public String print(CleanPayBean cleanpaybean){
		WebUtils.setRequestAttr("sortId", cleanpaybean.getSortId()); 
		WebUtils.setRequestAttr("normalPayId", cleanpaybean.getNormalPayId()); 
		WebUtils.setRequestAttr("userId", WebHelp.getLoginUser().getUserId()); 
		return CLEANQUERYPATH+"print";
	}
	/**
	 * 暂收结清下载插件
	 * 
	 * @param upFile 
	 * @return
	 */
	@RequestMapping("printDownload.do")
	public void upFileDownload(HttpServletRequest request,HttpServletResponse response,String fileId) throws Exception{
		upFileService.upFileDownload(request ,response, fileId);

	}
	
}
