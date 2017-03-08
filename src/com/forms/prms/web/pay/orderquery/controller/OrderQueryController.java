package com.forms.prms.web.pay.orderquery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.pay.orderquery.domain.OrderQueryBean;
import com.forms.prms.web.pay.orderquery.service.OrderQueryService;
import com.forms.prms.web.pay.orderstart.domain.OrderStartBean;
import com.forms.prms.web.pay.orderstart.service.OrderStartService;
import com.forms.prms.web.sysmanagement.montAprvBatch.export.service.ExportService;

@Controller
@RequestMapping("/pay/orderquery")
public class OrderQueryController {
	private static final String ORDERQUERYEPATH = "/pay/orderquery/";
	@Autowired
	private OrderQueryService orderQueryService;
	@Autowired
	private ExportService exportService;
	
	@Autowired
	private OrderStartService orderStartService;

	@RequestMapping("org1List.do")
	public String org1List(OrderQueryBean bean) {
		bean.setOrgFlag("1");//省行
		return list(bean);
	}
	
	@RequestMapping("org2List.do")
	public String org2List(OrderQueryBean bean) {
		bean.setOrgFlag("2");//二级行
		return list(bean);
	}
	
	@RequestMapping("dutyCodeList.do")
	public String dutyCodeList(OrderQueryBean bean) {
		bean.setOrgFlag("3");//业务部门
		return list(bean);
	}
	
	/**
	 * 补录完成订单查询
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("list.do")
	public String list(OrderQueryBean bean) {
		ReturnLinkUtils.addReturnLink("list", "返回列表");
		String org1Code=WebHelp.getLoginUser().getOrg1Code();//得到一级行
//		bean.setOrderDutyCode(WebHelp.getLoginUser().getDutyCode());
		List<OrderQueryBean> list = orderQueryService.getList(bean);
		WebUtils.setRequestAttr("list", list);
		WebUtils.setRequestAttr("org1Code", org1Code);
		WebUtils.setRequestAttr("selectInfo", bean);
		return ORDERQUERYEPATH + "list";
	}

	/**
	 * 得到订单号号的详细信息
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("getInfo.do")
	public String getInfo(OrderQueryBean bean) {
		bean = orderQueryService.getInfo(bean.getOrderId());
		WebUtils.setRequestAttr("orderInfo", bean);
		//查询该订单号下的所有物料
		List<OrderStartBean> devList=orderStartService.devList(bean.getOrderId());
		WebUtils.setRequestAttr("devList", devList);
		WebHelp.setLastPageLink("uri", "list");
		return ORDERQUERYEPATH + "orderInfo";
	}
	/**
	 * 信息导出
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/exportData.do")
	@ResponseBody
	public String exportData(OrderQueryBean orderQueryBean){
		AbstractJsonObject jsonObject = new SuccessJsonObject();		
		//Excel导出操作
		String taskId = null;
		try {
			taskId = orderQueryService.exportData(orderQueryBean,"1");
			if (Tool.CHECK.isBlank(taskId)) {
				jsonObject.put("pass", false);
			} else {
				jsonObject.put("pass", true);
			}		
		} catch (Exception e) {
			try{
				//如果  taskId已插入出现异常,则更新为失败
				if(!Tool.CHECK.isBlank(taskId)){
					exportService.updateTaskDataFlag(taskId);
				}
			}catch (Exception e1) {
				e1.printStackTrace();
			}
			jsonObject.put("pass", false);
			e.printStackTrace();
		}
		return jsonObject.writeValueAsString();
	}
	/**
	 * 订单明细信息导出
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/exportDataDetail.do")
	@ResponseBody
	public String exportDataDetail(OrderQueryBean orderQueryBean){
		AbstractJsonObject jsonObject = new SuccessJsonObject();		
		//Excel导出操作
		String taskId = null;
		try {
			taskId = orderQueryService.exportData(orderQueryBean,"2");
			if (Tool.CHECK.isBlank(taskId)) {
				jsonObject.put("pass", false);
			} else {
				jsonObject.put("pass", true);
			}		
		} catch (Exception e) {
			try{
				//如果  taskId已插入出现异常,则更新为失败
				if(!Tool.CHECK.isBlank(taskId)){
					exportService.updateTaskDataFlag(taskId);
				}
			}catch (Exception e1) {
				e1.printStackTrace();
			}
			jsonObject.put("pass", false);
			e.printStackTrace();
		}
		return jsonObject.writeValueAsString();
	}
}
