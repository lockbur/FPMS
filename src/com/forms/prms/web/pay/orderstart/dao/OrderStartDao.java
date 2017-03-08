package com.forms.prms.web.pay.orderstart.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.pay.orderstart.domain.OrderStartBean;

@Repository
public interface OrderStartDao {
	public String getOrderId(String orderDutyCode);// 得到采购部门和日期和序列号的组合

	//public boolean orderCreateList(@Param("cntNum")String cntNum,@Param("insertDutyCode")String insertDutyCode,@Param("instUser")String instUser);// 批量的添加订单信息

	public List<OrderStartBean> getList(OrderStartBean bean);// 查询待补录集合

	public void start(OrderStartBean bean);// 订单发起提交

	//public void updateCntDev(String cntNum);// 批量更新合同设备中的订单信息

	//public boolean orderCreateList1(@Param("cntNum")String cntNum,@Param("insertDutyCode")String insertDutyCode,@Param("instUser")String instUser);// 不是专向包批量的添加订单信息

	//public void updateCntDev1(String cntNum);// 不是专向包批量更新合同设备中的订单信息

	public void sure(OrderStartBean bean);// 订单确认

	public void addLogOrder(OrderStartBean bean);// 向订单操作表中加入一条记录

	public void updateCnt(OrderStartBean bean);// 改变合同中的状态为待修改

	/**
	 * 查询审批历史记录
	 * 
	 * @param orderId
	 * @return
	 */
	public List<OrderStartBean> queryHis(@Param("orderId") String orderId);

	/**
	 * 删除合同下的被退回的订单
	 * 
	 * @param cntNum
	 * @return
	 */
	public boolean deleteOrder(@Param("cntNum") String cntNum);

	//public boolean orderEditCreateList(@Param("cntNum")String cntNum,@Param("insertDutyCode")String insertDutyCode,@Param("instUser")String instUser);// 修改合同后批量的添加订单信息

	//public void updateEditCntDev(String cntNum);// 修改合同后批量更新合同设备中的订单信息

	//public boolean orderEditCreateList1(@Param("cntNum")String cntNum,@Param("insertDutyCode")String insertDutyCode,@Param("instUser")String instUser);// 修改合同不是专向包批量的添加订单信息

	//public void updateEditCntDev1(String cntNum);// 修改合同不是专向包批量更新合同设备中的订单信息

	public void backCntDev(OrderStartBean bean);// 改变合同设备表中的物料状态

	public List<OrderStartBean> devList(String orderId);// 通过订单号查询所有物料

	public List<String> sureList(OrderStartBean bean);// 查找订单号对应的合同下是否有00或者01状态的订单

	public void updateOrder(OrderStartBean bean);// 更新订单号状态为02的为待发送

}
