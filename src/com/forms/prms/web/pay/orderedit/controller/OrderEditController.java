package com.forms.prms.web.pay.orderedit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.platform.web.token.PreventDuplicateSubmit;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.pay.orderedit.domain.OrderEditBean;
import com.forms.prms.web.pay.orderedit.service.OrderEditService;
import com.forms.prms.web.pay.orderstart.domain.OrderStartBean;
import com.forms.prms.web.pay.orderstart.service.OrderStartService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/pay/orderedit/")
public class OrderEditController {

	@Autowired
	private OrderEditService orderEditService;

	@Autowired
	private OrderStartService orderStartService;

	private static final String ORDEREDITPATH = "/pay/orderedit/";

	/**
	 * 查询可修改的数据列表
	 * 
	 * @param orderEditBean
	 * @return
	 */
	@RequestMapping("queryList.do")
	@AddReturnLink(id = "queryList", displayValue = "可修改的数据列表查询")
	public String queryList(OrderEditBean orderEditBean) {
		String isBuyer = WebHelp.getLoginUser().getIsBuyer();// 得到登录用户是否采购员
		List<OrderEditBean> lists = null;
		// 如果是采购员则查本采购部门的订单
		if ("Y".equals(isBuyer)) {
			lists = orderEditService.queryList(orderEditBean);
			orderEditBean.setIsBuyer("Y");
		} else {
			orderEditBean.setIsBuyer("N");
			//非采购员跳转到错误页面
			WebUtils.getMessageManager().addInfoMessage("非采购员，不能操作订单。");
			return ForwardPageUtils.getErrorPage();
		}
		// 不是采购员集合始终为空
		WebUtils.setRequestAttr("lists", lists);
		WebUtils.setRequestAttr("selectInfo", orderEditBean);
		return ORDEREDITPATH + "list";
	}

	/**
	 * 查询可修改的数据明细
	 * 
	 * @param orderEditBean
	 * @return
	 */
	@RequestMapping("orderInfo.do")
	public String orderInfo(OrderEditBean orderEditBean) {
		OrderEditBean oeBean = orderEditService.orderInfo(orderEditBean
				.getOrderId());
		// 查询该订单号下的所有物料
		List<OrderStartBean> devList = orderStartService.devList(orderEditBean
				.getOrderId());
		WebUtils.setRequestAttr("devList", devList);
		WebUtils.setRequestAttr("orderInfo", oeBean);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
				+ "/pay/orderedit/queryList.do?VISIT_FUNC_ID=030703");
		return ORDEREDITPATH + "edit";
	}

	/**
	 * 修改提交
	 * 
	 * @param orderEditBean
	 * @return
	 */
	@RequestMapping("editSubmit.do")
	@PreventDuplicateSubmit
	public String editSubmit(OrderEditBean orderEditBean) {
		orderEditService.editSubmit(orderEditBean);
		return ForwardPageUtils.getReturnUrlString("订单信息修改", true,
				new String[] { "queryList" });
	}

	/**
	 * 订单退回
	 * 
	 * @param bean
	 */
	@RequestMapping("back.do")
	public String back(OrderStartBean bean) {
		orderEditService.back(bean);
		WebUtils.getMessageManager().addInfoMessage("订单退回成功!");
		ReturnLinkUtils.setShowLink("queryList");
		return ForwardPageUtils.getSuccessPage();
	}

	/**
	 * 订单退回时判断是否有不为订单取消或者订单失败的订单
	 * 
	 * @param bean
	 */
	@RequestMapping("checkOrder.do")
	@ResponseBody
	public String checkOrder(OrderEditBean bean) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		List<OrderEditBean> orderId = orderEditService.checkOrder(bean);
		if (orderId == null) {
			jsonObject.put("isExist", false);
		} else {
			jsonObject.put("isExist", true);
		}

		return jsonObject.writeValueAsString();
	}
}
