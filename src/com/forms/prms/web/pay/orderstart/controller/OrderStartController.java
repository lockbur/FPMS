package com.forms.prms.web.pay.orderstart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.pay.orderquery.domain.OrderQueryBean;
import com.forms.prms.web.pay.orderquery.service.OrderQueryService;
import com.forms.prms.web.pay.orderstart.domain.OrderStartBean;
import com.forms.prms.web.pay.orderstart.service.OrderStartService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/pay/orderstart")
public class OrderStartController {
	private static final String ORDERSTARTEPATH = "/pay/orderstart/";
	@Autowired
	private OrderStartService orderStartService;
	@Autowired
	private OrderQueryService orderQueryService;

	/**
	 * 待补录订单查询
	 * 
	 * @param PayExamineBean
	 * @return
	 */
	@RequestMapping("list.do")
	public String list(OrderStartBean bean) {
		ReturnLinkUtils.addReturnLink("list", "返回列表");
		List<OrderStartBean> list = null;
		String isBuyer = WebHelp.getLoginUser().getIsBuyer();// 得到登录用户是否采购员
		// 如果是采购员则查本采购部门的订单
		if (!"Y".equals(isBuyer)) {
			//非采购员跳转到错误页面
			WebUtils.getMessageManager().addInfoMessage("非采购员，不能确认订单。");
			return ForwardPageUtils.getErrorPage();
		}
		else{
			bean.setIsBuyer("Y");
			list = orderStartService.getList(bean);// 待补录集合
			WebUtils.setRequestAttr("list", list);
			WebUtils.setRequestAttr("selectInfo", bean);
			return ORDERSTARTEPATH + "list";
		}
	}

	/**
	 * 订单发起
	 * 
	 * @param orderId
	 */
	@RequestMapping("preStart.do")
	public String preStart(String orderId) {
		OrderQueryBean bean = orderQueryService.getInfo(orderId);
		bean.setChkDutyCode(WebHelp.getLoginUser().getDutyCode());
		// 查询该订单号下的所有物料
		List<OrderStartBean> devList = orderStartService.devList(orderId);
		WebUtils.setRequestAttr("orderId", orderId);
		WebUtils.setRequestAttr("orderBean", bean);
		WebUtils.setRequestAttr("devList", devList);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
				+ "/pay/orderstart/list.do?VISIT_FUNC_ID=030301");
		return ORDERSTARTEPATH + "preStart";
	}

	/**
	 * 订单发起提交
	 * 
	 * @param bean
	 */
	@RequestMapping("start.do")
	public String start(OrderStartBean bean) {
		orderStartService.start(bean);
		WebUtils.getMessageManager().addInfoMessage("订单发起成功!");
		ReturnLinkUtils.setShowLink("list");
		return ForwardPageUtils.getSuccessPage();
	}

	/**
	 * 订单退回
	 * 
	 * @param bean
	 */
	@RequestMapping("sure.do")
	public String sure(OrderStartBean bean) {
		orderStartService.sure(bean);
		WebUtils.getMessageManager().addInfoMessage("订单退回成功!");
		ReturnLinkUtils.setShowLink("list");
		return ForwardPageUtils.getSuccessPage();
	}

	/**
	 * 查询审批历史记录
	 * 
	 * @param payAddBean
	 * @return
	 */
	@RequestMapping("queryHis.do")
	public String queryHis(OrderStartBean bean) {
		List<OrderStartBean> hisList = orderStartService.queryHis(bean.getOrderId());
		WebUtils.setRequestAttr("hisList", hisList);
		return ORDERSTARTEPATH + "history";
	}
}
