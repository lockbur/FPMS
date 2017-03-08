package com.forms.prms.web.pay.orderquery.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.pay.orderquery.domain.OrderQueryBean;

@Repository
public interface OrderQueryDao {
	public List<OrderQueryBean> getList(OrderQueryBean bean);// 查询补录完成订单集合

	OrderQueryBean getInfo(String orderId);// 通过订单号查找详细信息
	
	public List<OrderQueryBean> orderListByCntNum(String cntNum);//通过合同号查找对应的所有订单

	public List<OrderQueryBean> getListDetail(OrderQueryBean bean);
}
