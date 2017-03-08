package com.forms.prms.web.pay.orderedit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.pay.orderedit.domain.OrderEditBean;
import com.forms.prms.web.pay.orderstart.domain.OrderStartBean;

@Repository
public interface OrderEditDAO {
	/**
	 * 查询可修改的数据列表
	 * 
	 * @param orderCheckBean
	 * @return
	 */
	public List<OrderEditBean> queryList(OrderEditBean orderEditBean);

	/**
	 * 查询可修改的数据明细
	 * 
	 * @param orderCheckBean
	 * @return
	 */
	public OrderEditBean orderInfo(@Param("orderId") String orderId);

	/**
	 * 修改提交
	 * 
	 * @param orderEditBean
	 * @return
	 */
	public void editSubmit(OrderEditBean orderEditBean);

	public void back(OrderStartBean bean);// 订单退回

	public void updateCnt(OrderStartBean bean);// 改变合同中的状态为待修改

	public List<OrderEditBean> checkOrder(OrderEditBean bean);// 根据订单号查询对象

	public List<String> sureList(OrderEditBean bean);// 查找订单号对应的合同下是否有00状态的订单

	public void updateOrder(OrderEditBean bean);// 更新订单号状态为02的为待发送
	
	public void addLogOrder(OrderEditBean bean);// 向订单操作表中加入一条记录

}
