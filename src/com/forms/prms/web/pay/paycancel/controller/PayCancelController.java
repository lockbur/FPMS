package com.forms.prms.web.pay.paycancel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.platform.web.token.PreventDuplicateSubmit;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.pay.paycancel.domain.PayCancelBean;
import com.forms.prms.web.pay.paycancel.service.PayCancelService;
import com.forms.prms.web.pay.payquery.domain.PayQueryBean;
import com.forms.prms.web.util.ForwardPageUtils;


@Controller
@RequestMapping("/pay/paycancel/")
public class PayCancelController {
	private static final String PAYCANCELPATH = "/pay/paycancel/";
	@Autowired
	private PayCancelService payCancelService;
	
	/**
	 * 付款历史信息列表查询
	 * 
	 * @param PaySureBean
	 * @return
	 */
	@RequestMapping("list.do")
	@AddReturnLink(id = "list", displayValue = "付款历史信息列表查询")
	public String list(PayCancelBean payCancelBean) {
		//ReturnLinkUtils.addReturnLink("list", "返回列表");
		List<PayCancelBean> list = payCancelService.list(payCancelBean);// 付款集合
		String org1Code = WebHelp.getLoginUser().getOrg1Code();// 得到登录人的所在的一级分行
		WebUtils.setRequestAttr("list", list);
		WebUtils.setRequestAttr("selectInfo", payCancelBean);
		WebUtils.setRequestAttr("org1Code", org1Code);
		return PAYCANCELPATH + "list";
	}
	
	/**
	 * 得到付款的的详细信息
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("detail.do")
	public String payDetail(PayCancelBean payCancelBean) {
		String path = "";
		if ("Y".equals(payCancelBean.getIsPrePay())) {//预付款
			payCancelBean.setTable("TD_PAY_ADVANCE");
			PayCancelBean pcBean = payCancelService.getPayByPayId(payCancelBean);//查询合同付款信息
			WebUtils.setRequestAttr("prePayInfo", pcBean);
			path = "prePayDetail";
		}else{//正常付款
			payCancelBean.setTable("TD_PAY");
			PayCancelBean pcBean = payCancelService.getPayByPayId(payCancelBean);//查询合同付款信息
			payCancelBean.setPayType("1");
			List<PayQueryBean> payDeviceList = payCancelService.getPayDeviceListByPayId(payCancelBean);//查询付款设备信息
			payCancelBean.setPayType("0");
			List<PayQueryBean> prePayDeviceList = payCancelService.getPayDeviceListByPayId(payCancelBean);//查询预付款核销设备信息
			List<PayQueryBean> prePayCancleList = payCancelService.getPrePayCancleListByCntNum(payCancelBean);//查询预付款核销列表
			WebUtils.setRequestAttr("userIdL", WebHelp.getLoginUser().getUserId());// 当前登录人ID
			WebUtils.setRequestAttr("payInfo", pcBean);
			WebUtils.setRequestAttr("payDeviceList", payDeviceList);
			WebUtils.setRequestAttr("prePayDeviceList", prePayDeviceList);
			WebUtils.setRequestAttr("prePayCancleList", prePayCancleList);
			path = "payDetail";
		}
		WebUtils.setRequestAttr("selectInfo", payCancelBean);
		return PAYCANCELPATH + path;
	}
	
	/**
	 * 正常付款取消
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("cancelPay.do")
	@PreventDuplicateSubmit
	public String cancelPay(PayCancelBean payCancelBean){
		boolean flag=payCancelService.cancelPay(payCancelBean);
		return ForwardPageUtils.getReturnUrlString("付款取消", flag, new String[]{"list"});
	}
}
