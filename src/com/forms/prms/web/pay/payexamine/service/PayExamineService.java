package com.forms.prms.web.pay.payexamine.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.icms.dao.ScanDAO;
import com.forms.prms.web.icms.domain.ScanBean;
import com.forms.prms.web.pay.paycommon.service.PayCommonService;
import com.forms.prms.web.pay.payexamine.dao.PayExamineDao;
import com.forms.prms.web.pay.payexamine.domain.PayExamineBean;
import com.forms.prms.web.pay.paymodify.dao.PayModifyDAO;
import com.forms.prms.web.pay.scan.dao.PayScanDAO;
import com.forms.prms.web.pay.scan.domain.PayScanBean;

@Service
public class PayExamineService {
	@Autowired
	private PayModifyDAO payModifyDAO;
	@Autowired
	private PayExamineDao dao;
	@Autowired
	private PayCommonService payCommonService;
	@Autowired
	private PayScanDAO payScanDAO;
	@Autowired
	private ScanDAO sanDao;
	
	/**
	 * 合同预付款和付款确认信息列表查询
	 * 
	 * @param PayExamineBean
	 * @return
	 */
	public List<PayExamineBean> getList(PayExamineBean bean) {
		CommonLogger.info("待复核的正常付款和预付款列表查询,PayExamineService,getList");
		PayExamineDao pageDao = PageUtils.getPageDao(dao);
		String dutyCode = WebHelp.getLoginUser().getDutyCode();// 得到登录人的所在责任中心
		String orgCode = WebHelp.getLoginUser().getOrgCode();
		bean.setInstDutyCode(dutyCode);
		bean.setOrgCode(orgCode);
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		return pageDao.getList(bean);
	}

	/**
	 * 通过付款单号查找所有信息
	 * 
	 * @param
	 * @return
	 * @throws Exception 
	 */
	public PayExamineBean getPayByPayId(String payId) throws Exception {
		CommonLogger.info("查询正常付款单号"+payId+"的详细信息,PayExamineService,getPayByPayId");
		PayExamineBean bean = dao.getPayByPayId(payId);
		if(bean==null){
			throw new Exception("未找到付款单号"+payId+"记录");
		}
		return bean;
	}

	/**
	 * 通过预付款单号查找所有信息
	 * 
	 * @param
	 * @return
	 * @throws Exception 
	 */
	public PayExamineBean getPrePayByPayId(String payId) throws Exception {
		CommonLogger.info("查询预付款单号"+payId+"的详细信息,PayExamineService,getPrePayByPayId");
		PayExamineBean bean = dao.getPrePayByPayId(payId);
		if(bean==null){
			throw new Exception("未找到付款单号"+payId+"记录");
		}
		return bean;
	}

	/**
	 * 通过付款单号查找对应的预付款采购设备集合
	 * 
	 * @param payId
	 * @return
	 */
	public List<PayExamineBean> getPrePayDeviceListByPayId(String payId) {
		CommonLogger.info("查询正常付款单号"+payId+"下的预付款采购设备,PayExamineService,getPrePayDeviceListByPayId");
//		PayExamineDao pageDao = PageUtils.getPageDao(dao);
//		return pageDao.getPrePayDeviceListByPayId(payId);
		return dao.getPrePayDeviceListByPayId(payId);
	}

	/**
	 * 通过付款单号查找对应的采购设备集合
	 * 
	 * @param payId
	 * @return
	 */
	public List<PayExamineBean> getPayDeviceListByPayId(String payId) {
		CommonLogger.info("查询正常付款单号"+payId+"下的付款采购设备,PayExamineService,getPayDeviceListByPayId");
//		PayExamineDao pageDao = PageUtils.getPageDao(dao);
//		return pageDao.getPayDeviceListByPayId(payId);
		return dao.getPayDeviceListByPayId(payId);
	}

	/**
	 * 通过合同号查找对应的核销集合
	 * 
	 * @param payId
	 * @return
	 */
	public List<PayExamineBean> getPrePayCancleListByCntNum(PayExamineBean bean) {
		CommonLogger.info("查询合同号"+bean.getCntNum()+"的核销集合,PayExamineService,getPrePayCancleListByCntNum");
		PayExamineDao pageDao = PageUtils.getPageDao(dao);
		return pageDao.getPrePayCancleListByCntNum(bean);
	}

	// 复核预付款通过
	@Transactional(rollbackFor=Exception.class)
	public void agreePrePay(PayExamineBean bean) {
		bean.setPayExamineUser(WebHelp.getLoginUser().getUserId());
		CommonLogger.info("预付款复核通过（付款单号：" + bean.getPayId()+"），PayExamineService，agreePrePay");
		dao.agreePrePay(bean);
		//判断是否是订单
		String isOrder=dao.isOrder(bean.getPayId());
		if("0".equals(isOrder)){
			bean.setPayDataFlag("C1");
		}
		else{
			bean.setPayDataFlag("C0");
		}
		CommonLogger.info("预付款复核通过（付款单号："+bean.getPayId()+"），的预付款log信息，PayExamineService，agreePrePay");
		bean.setAuditMemo("预付款复核-通过(支付方式："+bean.getPayModeName()+")：\r\n" + bean.getAuditMemo());
		dao.addArgeePayLog(bean);
		//TODO
		//更新为待扫描P2（责任中心不确定，因权限不确定）
		//payCommonService.updateSysWarnPayInfo(bean.getPayId(),bean.getInstDutyCode(),"P2");
		//删除SYS_WARN_PAY_INFO的对应付款数据，重新调用过程统计
		CommonLogger.info("预付款复核通过添加付款单"+bean.getPayId()+"的信息到统计表中，PayExamineService，agreePrePay");
		payCommonService.delSysWarnPayInfo(bean.getPayId(),WebHelp.getLoginUser().getDutyCode(),"P1");
	}

	// 复核付款通过
	@Transactional(rollbackFor=Exception.class)
	public void agreePay(PayExamineBean bean) {
		bean.setPayExamineUser(WebHelp.getLoginUser().getUserId());
		CommonLogger.info("正常付款复核通过（付款单号：" + bean.getPayId()+"），PayExamineService，agreePay");
		dao.agreePay(bean);
		String isOrder=dao.isOrder1(bean.getPayId());
		if("0".equals(isOrder)){
			bean.setPayDataFlag("C1");
		}
		else{
			bean.setPayDataFlag("C0");
		}
		CommonLogger.info("正常付款复核通过（付款单号："+bean.getPayId()+"），的正常付款log信息，PayExamineService，agreePay");
		bean.setAuditMemo("正常付款复核-通过(支付方式："+bean.getPayModeName()+")：\r\n" + bean.getAuditMemo());
		dao.addArgeePayLog(bean);
		//TODO
		//更新为待扫描P2（责任中心不确定，因权限不确定）
		//payCommonService.updateSysWarnPayInfo(bean.getPayId(),bean.getInstDutyCode(),"P2");
		//删除SYS_WARN_PAY_INFO的对应付款数据，重新调用过程统计
		CommonLogger.info("正常付款复核通过添加付款单"+bean.getPayId()+"的信息到统计表中，PayExamineService，agreePay");
		payCommonService.delSysWarnPayInfo(bean.getPayId(),WebHelp.getLoginUser().getDutyCode(),"P1");
	}

	// 复核预付款退回
	@Transactional(rollbackFor=Exception.class)
	public void backPrePay(PayExamineBean bean) {
		bean.setPayExamineUser(WebHelp.getLoginUser().getUserId());
		CommonLogger.info("预付款复核退回（付款单号：" + bean.getPayId()+"），PayExamineService，backPrePay");
		dao.backPrePay(bean);
		bean.setAuditMemo("预付款复核-退回(支付方式："+bean.getPayModeName()+")：\r\n" + bean.getAuditMemo());
		CommonLogger.info("预付款复核退回（付款单号："+bean.getPayId()+"），的预付款log信息，PayExamineService，backPrePay");
		dao.addBackPayLog(bean);
		//删除SYS_WARN_PAY_INFO的对应付款数据，重新调用过程统计
		CommonLogger.info("预付款复核退回到统计表中删除付款单"+bean.getPayId()+"的信息，PayExamineService，backPrePay");
		payCommonService.updateSysWarnPayInfo(bean.getPayId(),WebHelp.getLoginUser().getDutyCode(),"P0");
	}

	// 复核付款退回
	@Transactional(rollbackFor=Exception.class)
	public void backPay(PayExamineBean bean) throws Exception {
		bean.setPayExamineUser(WebHelp.getLoginUser().getUserId());
		CommonLogger.info("正常付款复核退回（付款单号：" + bean.getPayId()+"），PayExamineService，backPay");
		dao.backPay(bean);
		CommonLogger.info("正常付款复核退回（付款单号："+bean.getPayId()+"），的正常付款log信息，PayExamineService，backPay");
		bean.setAuditMemo("正常付款复核-退回(支付方式："+bean.getPayModeName()+")：\r\n" + bean.getAuditMemo());
		dao.addBackPayLog(bean);
		//删除SYS_WARN_PAY_INFO的对应付款数据，重新调用过程统计
		CommonLogger.info("正常付款复核退回到统计表中删除付款单"+bean.getPayId()+"的信息，PayExamineService，backPay");
		payCommonService.updateSysWarnPayInfo(bean.getPayId(),WebHelp.getLoginUser().getDutyCode(),"P0");
		
		
		//处理预算(如果付款时有冻结预算，就释放预算)
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
		}
	}

	// 扫描付款通过
	public void scanAgree(PayExamineBean bean) {
		ScanBean scanBean = null;
		PayScanBean payScanBean = new PayScanBean();
		payScanBean.setBatchNo(bean.getBatchNo());
		payScanBean.setPayId(bean.getPayId());
		
		bean.setPayDataFlag("D0");
		int updatedRow = 0;
		if ("Y".equalsIgnoreCase(bean.getIsPrePay())) {
			updatedRow = dao.scanAgreePrePay(bean);
		} else {
			updatedRow = dao.scanAgreePay(bean);
		}
		//成功更新
		if(updatedRow ==1){
			//增加审批日志
			bean.setAuditMemo("扫描复核-通过");
			CommonLogger.info("付款扫描通过（付款单号：" + bean.getPayId()+"），PayExamineService，scanAgreePay");
			bean.setTableName("TD_PAY");
			dao.addArgeePayLog(bean);
			CommonLogger.info("付款扫描通过（付款单号："+bean.getPayId()+"）的付款log信息，PayExamineService，scanAgreePay");					
			
			//将td_batch_icms_detail中的icmsPkuuid更新至ti_icms_pkuuid表
			scanBean = new ScanBean();
			scanBean.setId(bean.getPayId());
			scanBean.setInstOper(bean.getInstOper());
			scanBean.setIcmsPkuuid(bean.getIcmsPkuuid());
			sanDao.mergeUUID(scanBean);			
			
			//更新为发票待录入P3
			CommonLogger.info("扫描正常付款通过更新统计表（付款单号：" + bean.getPayId()+"），PayExamineService，scanAgreePrePay");
			payCommonService.updateSysWarnPayInfo(bean.getPayId(),WebHelp.getLoginUser().getOrg1Code(),"P3");
			
			//
			payScanBean.setDataFlag("04");			
			payScanBean.setMemo(bean.getAuditMemo());
		}else
		{
			payScanBean.setDataFlag("06");			
			payScanBean.setMemo("付款单非待扫描状态，可能已被其它人扫描。");			
		}
		payScanDAO.updateBatchDetail(payScanBean);	
		if(dao.updateScanBatchDataFlag(bean.getBatchNo()) == 1){
			CommonLogger.info("扫描批次[" + bean.getBatchNo() + "]已复核完毕！");
		}
	}

	// 扫描预付款退回
	@Transactional(rollbackFor=Exception.class)
	public void scanBack(PayExamineBean bean) throws Exception{
		int updatedRow = 0;
		PayScanBean payScanBean = new PayScanBean();
		payScanBean.setBatchNo(bean.getBatchNo());
		payScanBean.setPayId(bean.getPayId());
		
		if(!"Y".equalsIgnoreCase(bean.getIsPrePay())){
			//处理预算(如果付款时有冻结预算，就释放预算)
			if("0".equals(bean.getIsFrozenBgt()))
			{
				CommonLogger.info("退回付款单："+bean.getPayId()+" ，准备释放冻结的预算...");
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
				CommonLogger.info("退回付款单："+bean.getPayId()+" ，释放冻结的预算成功！");
			}
		}
		updatedRow = "Y".equalsIgnoreCase(bean.getIsPrePay()) ? dao.scanBackPrePay(bean):dao.scanBackPay(bean);		
		
		if(updatedRow == 1){
			CommonLogger.info("预付款扫描退回（付款单号："+bean.getPayId()+"），的预付款log信息，PayExamineService，scanBackPrePay");
			bean.setAuditMemo("扫描复核-退回：：\r\n" + bean.getAuditMemo());
			dao.addBackPayLog(bean);
			
			payScanBean.setDataFlag("05");//05-扫描复核退回
			payScanBean.setMemo(bean.getAuditMemo());
		}
		else{
			payScanBean.setDataFlag("06");			
			payScanBean.setMemo("付款单非待扫描状态，可能已被其它人扫描。");	
		}
		payScanDAO.updateBatchDetail(payScanBean);	
		if(dao.updateScanBatchDataFlag(bean.getBatchNo()) == 1){
			CommonLogger.info("扫描批次[" + bean.getBatchNo() + "]已复核完毕！");
		}		
	}

	public void addIcms(PayExamineBean bean) {
		// 设置扫描汇总的状态为待扫描
		bean.setDataFlag("00");
		// 得到扫描用户
		String instOper = WebHelp.getLoginUser().getUserId();// 得到用户ID
		String instOrg21Code = WebHelp.getLoginUser().getOrg2Code();
		bean.setInstOper(instOper);
		bean.setInstOrg21Code(instOrg21Code);
		CommonLogger.info("增加扫描批次，PayExamineService，addIcms");
		dao.addIcms(bean);
	}

	/**
	 * 查询扫描汇总
	 * 
	 * @param bean
	 * @return
	 */
	public List<PayExamineBean> scanList(PayExamineBean bean) {
		//String instOrg21Code = WebHelp.getLoginUser().getOrg2Code();
		//bean.setInstOrg21Code(instOrg21Code);
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		PayExamineDao pageDao = PageUtils.getPageDao(dao);
		CommonLogger.info("扫描汇总列表查询,PayExamineService,scanList");
		return pageDao.scanList(bean);
	}
	
	/**
	 * 查询扫描明细列表
	 * 
	 * @param bean
	 * @return
	 */
	public List<PayExamineBean> scanDetailList(PayExamineBean bean) {
		//String instOrg21Code = WebHelp.getLoginUser().getOrg2Code();
		//bean.setInstOrg21Code(instOrg21Code);
		bean.setInstOperOuCode(WebHelp.getLoginUser().getOuCode());
		PayExamineDao pageDao = PageUtils.getPageDao(dao);
		CommonLogger.info("查询批次号"+bean.getBatchNo()+"的明细,PayExamineService,scanDetailList");
		return pageDao.scanDetailList(bean);
	}
	
	/**
	 * 删除制定的扫描批次数据
	 * @param icmsBatchNo
	 */
	@Transactional(rollbackFor=Exception.class)
	public void delIcmsBatchNo(PayExamineBean bean){
		PayScanBean payScanBean = new PayScanBean();
		payScanBean.setBatchNo(bean.getBatchNo());
		payScanDAO.clearBatchDetail(payScanBean);
		payScanDAO.clearBatchHeader(payScanBean);
	}
}
