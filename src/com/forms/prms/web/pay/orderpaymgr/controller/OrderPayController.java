package com.forms.prms.web.pay.orderpaymgr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.platform.web.token.PreventDuplicateSubmit;
import com.forms.prms.web.pay.orderpaymgr.domain.OrderPayBean;
import com.forms.prms.web.pay.orderpaymgr.service.OrderPayService;
import com.forms.prms.web.util.ForwardPageUtils;


@Controller
@RequestMapping("/pay/orderpaymgr/")
public class OrderPayController {
	@Autowired
	private OrderPayService orderPayService;

	private static final String ORDERPAYPATH = "/pay/orderpaymgr/";
	
	/**
	 * 订单类付款流水查询
	 * @param orderPayBean
	 * @return
	 */
	@RequestMapping("list.do")
	@AddReturnLink(id = "list", displayValue = "订单类付款流水查询")
	public String list(OrderPayBean orderPayBean){
		List<OrderPayBean> lists = orderPayService.list(orderPayBean);
		WebUtils.setRequestAttr("lists",lists);
		WebUtils.setRequestAttr("selectInfo",orderPayBean);
		return ORDERPAYPATH + "list";
	}
	
	/**
	 * 订单类付款流水确认付款类型并选择erp的付款数据
	 * @param orderPayBean
	 * @return
	 */
	@RequestMapping("surePayType.do")
	public String surePayType(OrderPayBean orderPayBean){
		List<OrderPayBean> list = null;
		if("0".equalsIgnoreCase(orderPayBean.getFlag())){//正常
			//查询付款信息（还没有付款完成的）
			list= orderPayService.queryPayInfo(orderPayBean);
		}else if("1".equalsIgnoreCase(orderPayBean.getFlag())){//暂收结清
			//查询暂收结清信息
			list = orderPayService.querySusPInfo(orderPayBean);
		}
		WebUtils.setRequestAttr("orderPayInfo",orderPayBean);
		WebUtils.setRequestAttr("list",list);
		return ORDERPAYPATH + "selePayId";
	}
	
	/**
	 * 订单类付款流水选择确认erp的付款数据
	 * @param orderPayBean
	 * @return
	 */
	@RequestMapping("surePay.do")
	@PreventDuplicateSubmit
	public String surePay(OrderPayBean orderPayBean){
		orderPayService.surePay(orderPayBean);
		return ForwardPageUtils.getReturnUrlString("订单类付款选择确认", true,new String[]{"list"});
	}
}
