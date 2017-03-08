package com.forms.prms.web.pay.ordercheck.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.platform.web.token.PreventDuplicateSubmit;
import com.forms.prms.web.pay.ordercheck.domain.OrderCheckBean;
import com.forms.prms.web.pay.ordercheck.service.OrderCheckService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/pay/ordercheck/")
public class OrderCheckController {

	@Autowired
	private OrderCheckService orderCheckService;
	
	private static final String ORDERCHECKPATH = "/pay/ordercheck/";
	
	/**
	 * 查询待复核的数据列表
	 * @param orderCheckBean
	 * @return
	 */
	@RequestMapping("queryList.do")
	@AddReturnLink(id = "queryList", displayValue = "待复核的数据列表查询")
	public String queryList(OrderCheckBean orderCheckBean){
		List<OrderCheckBean> lists = orderCheckService.queryList(orderCheckBean);
		WebUtils.setRequestAttr("lists",lists);
		WebUtils.setRequestAttr("selectInfo",orderCheckBean);
		return ORDERCHECKPATH + "list";
	}
	
	/**
	 * 查询订单的数据明细
	 * @param orderCheckBean
	 * @return
	 */
	@RequestMapping("orderInfo.do")
	public String orderInfo(OrderCheckBean orderCheckBean){
		OrderCheckBean ocBean = orderCheckService.orderInfo(orderCheckBean.getOrderId());
		WebUtils.setRequestAttr("orderInfo",ocBean);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/pay/ordercheck/queryList.do?VISIT_FUNC_ID=030704");
		return ORDERCHECKPATH + "orderInfo";
	}
	
	/**
	 * 订单复核
	 * @param orderCheckBean
	 * @return
	 */
	@RequestMapping("orderCheck.do")
	@PreventDuplicateSubmit
	public String orderCheck(OrderCheckBean orderCheckBean){
		boolean flag = orderCheckService.orderCheck(orderCheckBean);
		return ForwardPageUtils.getReturnUrlString("订单复核", flag,
				new String[] { "queryList"});
	}
	
	
}
