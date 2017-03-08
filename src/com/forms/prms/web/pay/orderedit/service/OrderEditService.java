package com.forms.prms.web.pay.orderedit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.constantValues.BusTypes;
import com.forms.prms.tool.constantValues.OperateValues;
import com.forms.prms.web.pay.orderedit.dao.OrderEditDAO;
import com.forms.prms.web.pay.orderedit.domain.OrderEditBean;
import com.forms.prms.web.pay.orderstart.dao.OrderStartDao;
import com.forms.prms.web.pay.orderstart.domain.OrderStartBean;
import com.forms.prms.web.projmanagement.projectMgr.service.ProjectMgrService;
import com.forms.prms.web.sysmanagement.waterbook.service.WaterBookService;

@Service
public class OrderEditService {
	@Autowired
	private OrderEditDAO orderEditDAO;

	@Autowired
	private OrderStartDao orderStartDao;

	@Autowired
	private ProjectMgrService projectMgrService;
	
	@Autowired
	private WaterBookService wService;

	/**
	 * 查询可修改的数据列表
	 * 
	 * @param orderCheckBean
	 * @return
	 */
	public List<OrderEditBean> queryList(OrderEditBean orderEditBean) {
		CommonLogger.info("查询待修改的订单数据，OrderEditService，queryList");
		String dutyCode = WebHelp.getLoginUser().getDutyCode();// 得到登录所在的部门
		orderEditBean.setOrderDutyCode(dutyCode);
		OrderEditDAO pageDao = PageUtils.getPageDao(orderEditDAO);
		return pageDao.queryList(orderEditBean);
	}

	/**
	 * 查询可修改的数据明细
	 * 
	 * @param orderCheckBean
	 * @return
	 */
	public OrderEditBean orderInfo(String orderId) {
		CommonLogger.info("查看订单号为" + orderId + "的详细信息，OrderEditService，orderInfo");
		return orderEditDAO.orderInfo(orderId);
	}

	/**
	 * 修改提交
	 * 
	 * @param orderEditBean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public void editSubmit(OrderEditBean orderEditBean) {
		String instUser = WebHelp.getLoginUser().getUserId();// 登录人用户ID
		orderEditBean.setInstUser(instUser);
		CommonLogger.info("修改订单号" + orderEditBean.getOrderId() + "提交，OrderEditService，editSubmit");
		orderEditDAO.editSubmit(orderEditBean);
		// 向订单历史表中加入一条确认的记录
		orderEditBean.setDataFlag("02");// 设置确认后的状态值
		CommonLogger.info("向订单log表中加入一条订单号为" + orderEditBean.getOrderId() + "的记录，OrderStartService，sure");
		orderEditDAO.addLogOrder(orderEditBean);
		// 查找该订单对应合同号下的订单号是否存在待确认(00)的订单如果没有则将状态为02的改为04
		List<String> sureList = orderEditDAO.sureList(orderEditBean);
		if (sureList.size() == 0) {
			orderEditDAO.updateOrder(orderEditBean);
		}
	}

	/**
	 * 订单退回
	 * 
	 * @param bean
	 */
	@Transactional(rollbackFor = Exception.class)
	public void back(OrderStartBean bean) {
		String instOper = WebHelp.getLoginUser().getUserId();// 得到当前登录人的ID
		bean.setInstUser(instOper);
		// 改变合同的状态为待修改
		CommonLogger.info("改变订单号为" + bean.getOrderId() + "中的合同为待修改(40)，OrderEditService，back");
		orderEditDAO.updateCnt(bean);
		//增加合同流水
		CommonLogger.info("向合同流水表中加入一条订单号为" + bean.getOrderId() + "对应的合同流水订单退回(40)，OrderEditService，back");
		wService.insert(bean.getCntNum(), BusTypes.ORDER, OperateValues.ORDEREDITBACK, "订单修改退回", "19", "40");
		// 订单被退回
		bean.setDataFlag("03");// 设置订单状态为退回
		CommonLogger.info("修改订单号为" + bean.getOrderId() + "状态为退回(03)，OrderEditService，back");
		orderEditDAO.back(bean);
		// 更新合同设备表中的物料状态为退回
		CommonLogger.info("改变合同设备表中订单号为" + bean.getOrderId() + "对应的物料状态为退回(01)，OrderEditService，back");
		orderStartDao.backCntDev(bean);
		// 释放项目预算
		CommonLogger.info("释放订单号为" + bean.getOrderId() + "对应的项目预算(该订单对应物料执行金额占用转冻结)，OrderEditService，back");
		projectMgrService.orderBackFree(bean.getOrderId());
		// 向订单操作log表中加入一条记录
		CommonLogger.info("向订单log表中加入一条订单号为" + bean.getOrderId() + "的记录，OrderStartService，sure");
		orderStartDao.addLogOrder(bean);
		
	}

	/**
	 * 根据订单号查询对象
	 * 
	 * @param bean
	 * @return
	 */
	public List<OrderEditBean> checkOrder(OrderEditBean bean) {
		return orderEditDAO.checkOrder(bean);
	}
}
