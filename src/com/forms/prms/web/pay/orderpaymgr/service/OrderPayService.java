package com.forms.prms.web.pay.orderpaymgr.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.web.pay.orderpaymgr.dao.OrderPayDAO;
import com.forms.prms.web.pay.orderpaymgr.domain.OrderPayBean;

@Service
public class OrderPayService {
	@Autowired
	private OrderPayDAO orderPayDAO;

	/**
	 * 订单类付款流水查询
	 * 
	 * @param orderPayBean
	 * @return
	 */
	public List<OrderPayBean> list(OrderPayBean orderPayBean) {
		OrderPayDAO pageDao = PageUtils.getPageDao(orderPayDAO);
		CommonLogger.info("订单类付款流水查询(ERP_PAY_ID IS NULL),OrderPayService,list");
		return pageDao.list(orderPayBean);
	}

	/**
	 * 查询正常付款信息
	 * 
	 * @param orderPayBean
	 * @return
	 */
	public List<OrderPayBean> queryPayInfo(OrderPayBean orderPayBean) {
		CommonLogger.info("查询正常付款信息（批次号："+orderPayBean.getBatchNo()+"）,OrderPayService,queryPayInfo");
		return orderPayDAO.queryPayInfo(orderPayBean);
	}

	/**
	 * 查询暂收结清信息
	 * 
	 * @param orderPayBean
	 * @return
	 */
	public List<OrderPayBean> querySusPInfo(OrderPayBean orderPayBean) {
		CommonLogger.info("查询暂收结清信息（批次号："+orderPayBean.getBatchNo()+"）,OrderPayService,querySusPInfo");
		return orderPayDAO.querySusPInfo(orderPayBean);
	}

	/**
	 * 订单类付款流水选择确认erp的付款数据
	 * @param orderPayBean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public void surePay(OrderPayBean orderPayBean) {
		if("0".equalsIgnoreCase(orderPayBean.getFlag())){//正常
			//得先判断是付款成功还是取消
			if("N".equalsIgnoreCase(orderPayBean.getPayCancelState())){//成功
				orderPayBean.setDataFlagPay("2");//付款状态-付款中
				orderPayBean.setIsFlag("0");
			}else if("Y".equalsIgnoreCase(orderPayBean.getPayCancelState())){//取消
				orderPayBean.setIsFlag("1");
				orderPayBean.setDataFlagPay("4");//付款状态-付款回冲中
				orderPayBean.setPayAmt(orderPayBean.getPayAmt().negate());
				//付款取消需要更新整个付款的状态为F2
				CommonLogger.info("订单类付款选择确认erp的付款数据：更新付款的状态（付款单："+orderPayBean.getErpPayId()+"）,OrderPayService,surePay");
				orderPayDAO.updateDataFlag(orderPayBean);
			}
			//更新累计付款金额及付款状态
			CommonLogger.info("订单类付款选择确认erp的付款数据：更新累计付款金额及付款状态（付款单："+orderPayBean.getErpPayId()+"）,OrderPayService,surePay");
			orderPayDAO.updatePayTotal(orderPayBean);
			orderPayDAO.updatePayDataFlag(orderPayBean);
			//根据付款单、累计付款金额更新付款的状态
			CommonLogger.info("订单类付款选择确认erp的付款数据：根据付款单、累计付款金额更新付款的状态（付款单："+orderPayBean.getErpPayId()+"）,OrderPayService,surePay");
			orderPayDAO.updatePayDataFlagById(orderPayBean);
			//更新整个付款状态
			//1.更新付款成功 2.更新付款回冲
			CommonLogger.info("订单类付款选择确认erp的付款数据：更新整个付款的状态（付款单："+orderPayBean.getErpPayId()+"）,OrderPayService,surePay");
			orderPayDAO.updateDataFlag2(orderPayBean);
		}else if("1".equalsIgnoreCase(orderPayBean.getFlag())){//暂收结清
			//得先判断是付款成功还是取消
			if("N".equalsIgnoreCase(orderPayBean.getPayCancelState())){//成功
				orderPayBean.setCleanAmtFms(orderPayBean.getPayAmt());
			}else if("Y".equalsIgnoreCase(orderPayBean.getPayCancelState())){//取消
				orderPayBean.setCleanAmtFms(new BigDecimal(0));
				orderPayBean.setPayAmt(orderPayBean.getPayAmt().negate());
			}
			orderPayBean.setErpPayId(orderPayBean.getNormalPayId());
			//更新累计结清金额
			CommonLogger.info("订单类付款选择确认erp的付款数据：更新累计结清金额（付款单："+orderPayBean.getNormalPayId()+"）,OrderPayService,surePay");
			orderPayDAO.updateSusTotal(orderPayBean);
			//更新暂收表的数据
			CommonLogger.info("订单类付款选择确认erp的付款数据：更新暂收表的数据（付款单："+orderPayBean.getNormalPayId()+"）,OrderPayService,surePay");
			orderPayDAO.updateCleanAmtFms(orderPayBean);
		}
		//更新TD_PAY_CLEAN_LOG表
		CommonLogger.info("订单类付款选择确认erp的付款数据：更新TD_PAY_CLEAN_LOG表（批次号："+orderPayBean.getBatchNo()+"）,OrderPayService,surePay");
		orderPayBean.setPayType(orderPayBean.getFlag());
		orderPayDAO.updateTpcl(orderPayBean);
	}

}
