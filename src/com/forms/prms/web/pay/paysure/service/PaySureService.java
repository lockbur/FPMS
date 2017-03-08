package com.forms.prms.web.pay.paysure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.pay.paycommon.service.PayCommonService;
import com.forms.prms.web.pay.paymodify.dao.PayModifyDAO;
import com.forms.prms.web.pay.paysure.dao.PaySureDao;
import com.forms.prms.web.pay.paysure.domain.PaySureBean;

@Service
public class PaySureService {
	@Autowired
	private PaySureDao dao;
	@Autowired
	private PayCommonService payCommonService;
	@Autowired
	private PayModifyDAO payModifyDAO;
	/**
	 * 合同预付款和付款待确认信息列表查询
	 * 
	 * @param PaySureBean
	 * @return
	 */
	public List<PaySureBean> getList(PaySureBean bean) {
		CommonLogger.info("财务中心发票录入的正常付款和预付款列表查询,PaySureService,getList");
		PaySureDao pageDao = PageUtils.getPageDao(dao);
		String org1Code = WebHelp.getLoginUser().getOrg1Code();// 得到登录人的所在的一级分行
		bean.setOrg1Code(org1Code);
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		return pageDao.getList(bean);
	}

	/**
	 * 通过付款单号查找所有信息
	 * 
	 * @param
	 * @return
	 */
	public PaySureBean getPayByPayId(String payId) {
		CommonLogger.info("查询正常付款单号"+payId+"的明细,PaySureService,getPayByPayId");
		return dao.getPayByPayId(payId);
	}

	/**
	 * 通过预付款单号查找所有信息
	 * 
	 * @param
	 * @return
	 */
	public PaySureBean getPrePayByPayId(String payId) {
		CommonLogger.info("查询预付款单号"+payId+"的明细,PaySureService,getPrePayByPayId");
		return dao.getPrePayByPayId(payId);
	}

	/**
	 * 通过预付款单号查找对应的采购设备集合
	 * 
	 * @param payId
	 * @return
	 */
	public List<PaySureBean> getPrePayDeviceListByPayId(String payId) {
		CommonLogger.info("查询正常付款单号"+payId+"下的预付款采购设备,PaySureService,getPrePayDeviceListByPayId");
//		PaySureDao pageDao = PageUtils.getPageDao(dao);
//		return pageDao.getPrePayDeviceListByPayId(payId);
		return dao.getPrePayDeviceListByPayId(payId);
	}

	/**
	 * 通过付款单号查找对应的采购设备集合
	 * 
	 * @param payId
	 * @return
	 */
	public List<PaySureBean> getPayDeviceListByPayId(String payId) {
		CommonLogger.info("查询正常付款单号"+payId+"下的正常付款采购设备,PaySureService,getPayDeviceListByPayId");
//		PaySureDao pageDao = PageUtils.getPageDao(dao);
//		return pageDao.getPayDeviceListByPayId(payId);
		return dao.getPayDeviceListByPayId(payId);
	}

	/**
	 * 通过合同号查找对应的核销集合
	 * 
	 * @param cntNum
	 * @return
	 */
	public List<PaySureBean> getPrePayCancleListByCntNum(PaySureBean bean) {
		CommonLogger.info("查询合同号"+bean.getCntNum()+"的核销集合,PaySureService,getPrePayCancleListByCntNum");
		PaySureDao pageDao = PageUtils.getPageDao(dao);
		return pageDao.getPrePayCancleListByCntNum(bean);
	}

	// 确认预付款
	@Transactional(rollbackFor=Exception.class)
	public void agreePrePay(PaySureBean bean) {
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		bean.setPaySureUser(WebHelp.getLoginUser().getUserId());
		bean.setPayDataFlag("F0");
		CommonLogger.info("预付款确认通过（付款单号：" + bean.getPayId()+"），PaySureService，agreePrePay");
		dao.agreePrePay(bean);
		CommonLogger.info("预付款确认通过（付款单号："+bean.getPayId()+"），的预付款log信息，PaySureService，agreePrePay");
		if(!Tool.CHECK.isBlank(bean.getAuditMemo())){
			bean.setAuditMemo("预付款确认-通过:" + bean.getAuditMemo());
		}else{
			bean.setAuditMemo("预付款确认-通过");
		}
		dao.addLog(bean);
		//删除统计数据，重新统计
		CommonLogger.info("预付款确认通过（付款单号："+bean.getPayId()+"），删除统计数据重新统计，PaySureService，agreePrePay");
		payCommonService.delSysWarnPayInfo(bean.getPayId(), WebHelp.getLoginUser().getOrg1Code(), "P3");
	}

	// 确认付款
	@Transactional(rollbackFor=Exception.class)
	public void agreePay(PaySureBean bean) {
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		bean.setPaySureUser(WebHelp.getLoginUser().getUserId());
		bean.setPayDataFlag("F0");
		CommonLogger.info("付款确认通过（付款单号：" + bean.getPayId()+"），PaySureService，agreePay");
		dao.agreePay(bean);
		CommonLogger.info("付款确认通过（付款单号："+bean.getPayId()+"），的付款log信息，PaySureService，agreePay");
		if(!Tool.CHECK.isBlank(bean.getAuditMemo())){
			bean.setAuditMemo("正常付款确认-通过:" + bean.getAuditMemo());
		}else{
			bean.setAuditMemo("正常付款确认-通过");
		}
		dao.addLog(bean);
		//删除统计数据，重新统计
		CommonLogger.info("付款确认通过（付款单号："+bean.getPayId()+"），删除统计数据重新统计，PaySureService，agreePay");
		payCommonService.delSysWarnPayInfo(bean.getPayId(), WebHelp.getLoginUser().getOrg1Code(), "P3");
	}

	// 财务中心预付款退回
	@Transactional(rollbackFor=Exception.class)
	public void backPrePay(PaySureBean bean) {
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		bean.setPaySureUser(WebHelp.getLoginUser().getUserId());
		bean.setPayDataFlag("CD");
		CommonLogger.info("预付款确认退回（付款单号：" + bean.getPayId()+"），PaySureService，backPrePay");
		dao.backPrePay(bean);
		CommonLogger.info("预付款确认退回（付款单号："+bean.getPayId()+"），的预付款log信息，PaySureService，backPrePay");
		bean.setAuditMemo("预付款确认-退回：" + bean.getAuditMemo());
		dao.addLog(bean);
		//删除统计数据，重新统计
		CommonLogger.info("预付款确认退回（付款单号："+bean.getPayId()+"），删除统计数据重新统计，PaySureService，backPrePay");
//		payCommonService.delSysWarnPayInfo(bean.getPayId(), WebHelp.getLoginUser().getOrg1Code(), "P3");//删除p3的数据重新统计
		payCommonService.updateSysWarnPayInfo(bean.getPayId(),WebHelp.getLoginUser().getOrg1Code() ,"P2");//添加发票退回的数据重新统计
	}

	// 财务中心付款退回
	@Transactional(rollbackFor=Exception.class)
	public void backPay(PaySureBean bean) throws Exception {
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		bean.setPaySureUser(WebHelp.getLoginUser().getUserId());
		bean.setPayDataFlag("CD");
		CommonLogger.info("付款确认退回（付款单号：" + bean.getPayId()+"），PaySureService，backPay");
		dao.backPay(bean);
		CommonLogger.info("付款确认退回（付款单号："+bean.getPayId()+"），的付款log信息，PaySureService，backPay");
		bean.setAuditMemo("正常付款确认-退回: " + bean.getAuditMemo());
		dao.addLog(bean);
		//删除统计数据，重新统计
		CommonLogger.info("付款确认退回（付款单号："+bean.getPayId()+"），删除统计数据重新统计，PaySureService，backPay");
//		payCommonService.delSysWarnPayInfo(bean.getPayId(), WebHelp.getLoginUser().getOrg1Code(), "P3");
//		payCommonService.addSysWarnPayInfo(bean.getPayId(), payCommonService.getPayInfo(bean.getPayId()).getDutyCode(), "P0");//添加待修改的数据重新统计
		payCommonService.updateSysWarnPayInfo(bean.getPayId(),WebHelp.getLoginUser().getOrg1Code() ,"P2");//添加发票退回的数据重新统计
		//考虑到退回扫描岗后，可能需要重新扫描，所以付款退回时不做预算的释放处理
		/*//处理预算(如果付款时有冻结预算，就释放预算)
		if("0".equals(bean.getIsFrozenBgt()))
		{
			Map<String, String> param = new HashMap<String, String>();
			param.put("payid", bean.getPayId());
			param.put("memo", "02");//复核退回
			param.put("retMsg", "");
			payModifyDAO.deletePayFreeBgt(param);
			//预算处理失败，抛出异常
			if(null == param.get("retMsg") || "".contentEquals(param.get("retMsg")) || "0".contentEquals(param.get("retMsg")))
			{
				CommonLogger.error("退回付款单："+bean.getPayId()+" ，释放预算失败");
				throw new Exception("退回付款单："+bean.getPayId()+" ，释放预算失败");
			}
		}*/
	}
	/**
	 * 得到登录人的ouCode
	 * @param org1Code
	 * @return
	 */
	public List<PaySureBean>  ouCodeList(String org1Code){
		CommonLogger.info("查询一级行"+org1Code+"下的ouCode集合,PaySureService,ouCodeList");
		return dao.ouCodeList(org1Code);
	}
	
}
