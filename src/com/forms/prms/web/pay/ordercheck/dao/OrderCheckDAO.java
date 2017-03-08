package com.forms.prms.web.pay.ordercheck.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.pay.ordercheck.domain.OrderCheckBean;

@Repository
public interface OrderCheckDAO {
	/**
	 * 查询待复核的数据列表
	 * @param orderCheckBean
	 * @return
	 */
	public List<OrderCheckBean> queryList(OrderCheckBean orderCheckBean);

	/**
	 * 查询订单的数据明细
	 * @param orderCheckBean
	 * @return
	 */
	public OrderCheckBean orderInfo(@Param("orderId")String orderId);

	/**
	 * 订单复核
	 * @param orderCheckBean
	 * @return
	 */
	public int orderCheck(OrderCheckBean orderCheckBean);

}
