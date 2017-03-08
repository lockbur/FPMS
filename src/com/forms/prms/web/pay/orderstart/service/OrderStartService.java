package com.forms.prms.web.pay.orderstart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.constantValues.BusTypes;
import com.forms.prms.tool.constantValues.OperateValues;
import com.forms.prms.web.pay.orderstart.dao.OrderStartDao;
import com.forms.prms.web.pay.orderstart.domain.OrderStartBean;
import com.forms.prms.web.projmanagement.projectMgr.service.ProjectMgrService;
import com.forms.prms.web.sysmanagement.waterbook.service.WaterBookService;

@Service
public class OrderStartService {
	@Autowired
	private OrderStartDao dao;

	@Autowired
	private ProjectMgrService projectMgrService;
	
	@Autowired
	private WaterBookService wService;

	/**
	 * 专向包合同号生成订单
	 * 
	 * @param bean
	 */
	@Transactional(rollbackFor = Exception.class)
	public void orderCreateList(String cntNum) {
		String insertDutyCode=WebHelp.getLoginUser().getDutyCode();//得到登录人所在责任中心
		String instUser=WebHelp.getLoginUser().getUserId();//得到登录人用户
		CommonLogger.info("批量生成合同号" + cntNum + "的订单信息，OrderStartService，orderCreateList");
		//dao.orderCreateList(cntNum,insertDutyCode,instUser);// 批量生成订单
		CommonLogger.info("批量更新合同设备表中合同号为" + cntNum + "的订单号，OrderStartService，orderCreateList");
		//dao.updateCntDev(cntNum);// 批量更新合同设备中的订单号
	}

	/**
	 * 不是专向包合同号生成订单
	 * 
	 * @param bean
	 */
	public void orderCreateList1(String cntNum) {
		String insertDutyCode=WebHelp.getLoginUser().getDutyCode();//得到登录人所在责任中心
		String instUser=WebHelp.getLoginUser().getUserId();//得到登录人用户
		CommonLogger.info("批量生成合同号" + cntNum + "的订单信息，OrderStartService，orderCreateList");
		//dao.orderCreateList1(cntNum,insertDutyCode,instUser);// 批量生成订单
		CommonLogger.info("批量更新合同设备表中合同号为" + cntNum + "的订单号，OrderStartService，orderCreateList");
		//dao.updateCntDev1(cntNum);// 批量更新合同设备中的订单号
	}

	/**
	 * 查询待补录集合
	 * 
	 * @param bean
	 * @return
	 */
	public List<OrderStartBean> getList(OrderStartBean bean) {
		CommonLogger.info("查询待确认的订单信息，OrderStartService，getList");
		String dutyCode=WebHelp.getLoginUser().getDutyCode();//得到登录所在的部门
		bean.setOrderDutyCode(dutyCode);
		OrderStartDao pageDao = PageUtils.getPageDao(dao);
		return pageDao.getList(bean);
	}

	/**
	 * 订单发起提交
	 * 
	 * @param bean
	 */
	@Transactional(rollbackFor = Exception.class)
	public void start(OrderStartBean bean) {
		String instUser=WebHelp.getLoginUser().getUserId();//得到登录人用户
		bean.setChkUser(instUser);
		CommonLogger.info("将订单号为" + bean.getOrderId() + "确认通过，OrderStartService，start");
		if(Tool.CHECK.isEmpty(bean.getOperMemo())){
			bean.setOperMemo("同意。");
		}
		dao.start(bean);
		// 向订单历史表中加入一条确认的记录
		bean.setDataFlag("02");//设置确认后的状态值
		bean.setInstUser(instUser);
		CommonLogger.info("向订单log表中加入一条订单号为" + bean.getOrderId() + "的记录，OrderStartService，start");
		dao.addLogOrder(bean);
		// 查找该订单对应合同号下的订单号是否存在待确认(00)或者退回待确认(01)的订单如果没有则将状态为02的改为04
		CommonLogger.info("查看订单表中对应订单号" + bean.getOrderId() + "是否还存在00或者01状态的，OrderStartService，start");
		List<String> sureList = dao.sureList(bean);
		if (sureList.size() == 0) {
			dao.updateOrder(bean);
		}
	}

	/**
	 * 订单确认
	 * 
	 * @param bean
	 */
	@Transactional(rollbackFor = Exception.class)
	public void sure(OrderStartBean bean) {
		CommonLogger.info("开始处理订单号为" + bean.getOrderId() + "的退回处理...");
		String instOper = WebHelp.getLoginUser().getUserId();// 得到当前登录人的ID
		bean.setInstUser(instOper);
		// 订单被退回
		// 改变合同的状态为订单退回
		CommonLogger.info("1/5.改变订单号为" + bean.getOrderId() + "中的合同表状态为待修改40..");
		dao.updateCnt(bean);
		//增加合同流水
		CommonLogger.info("向合同流水表中加入一条订单号为" + bean.getOrderId() + "对应的合同流水订单退回(40)，OrderEditService，back");
		wService.insert(bean.getCntNum(), BusTypes.ORDER, OperateValues.ORDERSTARTBACK, "订单确认退回", "19", "40");
		bean.setDataFlag("03");// 设置订单状态为退回
		CommonLogger.info("2/5.更新订单号为" + bean.getOrderId() + "中的订单表状态为订单退回03..");
		dao.sure(bean);
		// 更新合同设备表中的物料状态为退回
		CommonLogger.info("3/5.改变订单号为" + bean.getOrderId() + "中的合同设备表中对应的物料为退回状态(01)..");
		dao.backCntDev(bean);
		CommonLogger.info("4/5.释放订单号为" + bean.getOrderId() + "中对应的项目预算(该订单对应物料执行金额占用转冻结)..");
		// 释放项目预算
		projectMgrService.orderBackFree(bean.getOrderId());
		// 向订单操作log表中加入一条记录
		CommonLogger.info("5/5.向订单log表中加入一条订单号为" + bean.getOrderId() + "的记录。");
		dao.addLogOrder(bean);
	}

	/**
	 * 查询审批历史记录
	 * 
	 * @param orderId
	 * @return
	 */
	public List<OrderStartBean> queryHis(String orderId) {
		return dao.queryHis(orderId);
	}

	/**
	 * 删除合同号被退回的订单
	 * 
	 * @param cntNum
	 * @return
	 */
	public boolean deleteOrder(String cntNum) {
		return dao.deleteOrder(cntNum);
	}

	/**
	 * 专向包合同号修改后生成订单
	 * 
	 * @param bean
	 */
	@Transactional(rollbackFor = Exception.class)
	public void orderEditCreateList(String cntNum) {
		String insertDutyCode=WebHelp.getLoginUser().getDutyCode();//得到登录人所在责任中心
		String instUser=WebHelp.getLoginUser().getUserId();//得到登录人用户
		CommonLogger.info("批量生成合同号" + cntNum + "的订单信息，OrderStartService，orderEditCreateList");
		//dao.orderEditCreateList(cntNum,insertDutyCode,instUser);// 批量生成订单
		CommonLogger.info("批量更新合同设备表中合同号为" + cntNum + "的订单号，OrderStartService，orderEditCreateList");
		//dao.updateEditCntDev(cntNum);// 批量更新合同设备中的订单号
	}

	/**
	 * 不是专向包合同号修改后生成订单
	 * 
	 * @param bean
	 */
	public void orderEditCreateList1(String cntNum) {
		String insertDutyCode=WebHelp.getLoginUser().getDutyCode();//得到登录人所在责任中心
		String instUser=WebHelp.getLoginUser().getUserId();//得到登录人用户
		CommonLogger.info("批量生成合同号" + cntNum + "的订单信息，OrderStartService，orderEditCreateList1");
		//dao.orderEditCreateList1(cntNum,insertDutyCode,instUser);// 批量生成订单
		CommonLogger.info("批量更新合同设备表中合同号为" + cntNum + "的订单号，OrderStartService，orderEditCreateList1");
		//dao.updateEditCntDev1(cntNum);// 批量更新合同设备中的订单号
	}

	/**
	 * 查询订单号下的所有物料
	 * 
	 * @param orderId
	 * @return
	 */
	public List<OrderStartBean> devList(String orderId) {
		CommonLogger.info("查询订单号为" + orderId + "的对应的物料，OrderStartService，devList");
		return dao.devList(orderId);
	}

}
