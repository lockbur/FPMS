package com.forms.prms.web.pay.ordercheck.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.pay.ordercheck.dao.OrderCheckDAO;
import com.forms.prms.web.pay.ordercheck.domain.OrderCheckBean;

@Service
public class OrderCheckService {
	
	@Autowired
	private OrderCheckDAO orderCheckDAO;
	
	/**
	 * 查询待复核的数据列表
	 * @param orderCheckBean
	 * @return
	 */
	public List<OrderCheckBean> queryList(OrderCheckBean orderCheckBean) {
		CommonLogger.info("待复核的订单数据列表查询，OrderCheckService，queryList");
		orderCheckBean.setInstDutyCode(WebHelp.getLoginUser().getDutyCode());//登录用户所在责任中心
		OrderCheckDAO pageDao = PageUtils.getPageDao(orderCheckDAO);
		return pageDao.queryList(orderCheckBean);
	}

	/**
	 * 查询订单的数据明细
	 * @param orderCheckBean
	 * @return
	 */
	public OrderCheckBean orderInfo(String orderId) {
		CommonLogger.info("订单"+orderId+"明细查询，OrderCheckService，orderInfo");
		return orderCheckDAO.orderInfo(orderId);
	}

	/**
	 * 订单复核
	 * @param orderCheckBean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean orderCheck(OrderCheckBean orderCheckBean) {
		CommonLogger.info("订单"+orderCheckBean.getOrderId()+"复核，OrderCheckService，orderCheck");
		if(orderCheckBean.getIsAgree().equals("1")){
			orderCheckBean.setDataFlag("04");
		}else if(orderCheckBean.getIsAgree().equals("2")){
			orderCheckBean.setDataFlag("03");
		}
		return orderCheckDAO.orderCheck(orderCheckBean)>0?true:false;
	}

}
