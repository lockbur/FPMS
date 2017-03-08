package com.forms.prms.web.pay.payquery.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.platform.web.WebUtils;
import com.forms.prms.tool.ImportUtil;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.exportthread.ExcelExportGenernalDeal;
import com.forms.prms.web.contract.query.domain.QueryContract;
import com.forms.prms.web.pay.payquery.dao.PayQueryDao;
import com.forms.prms.web.pay.payquery.domain.CleanLogBean;
import com.forms.prms.web.pay.payquery.domain.PayQueryBean;

@Service
public class PayQueryService {
	@Autowired
	private PayQueryDao dao;
	@Autowired
	private ExcelExportGenernalDeal exportDeal;	

	/**
	 * 付款信息列表查询
	 * 
	 * @param PayExamineBean
	 * @return
	 */
	public List<PayQueryBean> list(PayQueryBean bean) {
		CommonLogger.info("发起预付款或者正常付款列表查询,PayQueryService,list");
		PayQueryDao pageDao = PageUtils.getPageDao(dao);
		bean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());// 得到登录人所在的一级行
		bean.setOrg2Code(WebHelp.getLoginUser().getOrg2Code());
		bean.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		return pageDao.list(bean);
	}

	/**
	 * 通过合同号单号查找预付款合同信息
	 * 
	 * @param
	 * @return
	 */
	public PayQueryBean getPreCntByCntNum(String cntNum) {
		CommonLogger.info("查找合同号（"+cntNum+"）的预付款合同信息,PayQueryService,getPreCntByCntNum");
		return dao.getPreCntByCntNum(cntNum);
	}

	/**
	 * 通过合同号单号查找付款合同信息
	 * 
	 * @param
	 * @return
	 */
	public PayQueryBean getCntByCntNum(String cntNum) {
		CommonLogger.info("查找合同号（"+cntNum+"）的付款合同信息,PayQueryService,getCntByCntNum");
		return dao.getCntByCntNum(cntNum);
	}

	/**
	 * 通过预付款合同号查找对应的预付款单号集合
	 * 
	 * @param payId
	 * @return
	 */
	public List<PayQueryBean> getPrePayListByCntNum(String cntNum) {
		CommonLogger.info("查找合同号（"+cntNum+"）下的预付款单号集合,PayQueryService,getPrePayListByCntNum");
		return dao.getPrePayListByCntNum(cntNum);
	}

	/**
	 * 通过付款合同号查找对应的付款单号集合
	 * 
	 * @param payId
	 * @return
	 */
	public List<PayQueryBean> getPayListByCntNum(String cntNum) {
		CommonLogger.info("查找合同号（"+cntNum+"）下的正常付款单号集合,PayQueryService,getPayListByCntNum");
		return dao.getPayListByCntNum(cntNum);
	}

	/**
	 * 通过预付款单号查找所有信息
	 * 
	 * @param
	 * @return
	 */
	public PayQueryBean getPrePayByPayId(String payId) {
		CommonLogger.info("查询预付款单号"+payId+"的详细信息,PayQueryService,getPrePayByPayId");
		return dao.getPrePayByPayId(payId);
	}

	/**
	 * 通过付款单号查找对应的付款采购设备集合
	 * 
	 * @param payId
	 * @return
	 */
	public List<PayQueryBean> getPayDeviceListByPayId(String payId) {
		CommonLogger.info("查询正常付款单号"+payId+"下的付款采购设备,PayQueryService,getPayDeviceListByPayId");
//		PayQueryDao pageDao = PageUtils.getPageDao(dao);
//		return pageDao.getPayDeviceListByPayId(payId);
		return dao.getPayDeviceListByPayId(payId);
	}

	/**
	 * 通过付款单号查找对应的预付款采购设备集合
	 * 
	 * @param payId
	 * @return
	 */
	public List<PayQueryBean> getPrePayDeviceListByPayId(String payId) {
		CommonLogger.info("查询正常付款单号"+payId+"下的预付款采购设备,PayQueryService,getPrePayDeviceListByPayId");
//		PayQueryDao pageDao = PageUtils.getPageDao(dao);
//		return pageDao.getPrePayDeviceListByPayId(payId);
		return dao.getPrePayDeviceListByPayId(payId);
	}

	/**
	 * 通过合同号查找对应的核销集合
	 * 
	 * @param payId
	 * @return
	 */
	public List<PayQueryBean> getPrePayCancleListByCntNum(PayQueryBean bean) {
		CommonLogger.info("查询合同号"+bean.getCntNum()+"的核销集合,PayQueryService,getPrePayCancleListByCntNum");
		PayQueryDao pageDao = PageUtils.getPageDao(dao);
		return pageDao.getPrePayCancleListByCntNum(bean);
	}

	/**
	 * 通过付款单号查找所有信息
	 * 
	 * @param
	 * @return
	 */
	public PayQueryBean getPayByPayId(String payId) {
		CommonLogger.info("查询正常付款单号"+payId+"的详细信息,PayQueryService,getPayByPayId");
		return dao.getPayByPayId(payId);
	}

	public List<PayQueryBean> getPayCleanList(String payId) {
		CommonLogger.info("查询正常付款单号"+payId+"下的暂收结清列表,PayQueryService,getPayCleanList");
		PayQueryDao pageDao = PageUtils.getPageDao(dao);
		return pageDao.getPayCleanList(payId);
	}

	public List<PayQueryBean> getPayAdvanceCancelList(String payId) {
		CommonLogger.info("查询正常付款单号"+payId+"下已核销列表,PayQueryService,getPayAdvanceCancelList");
		PayQueryDao pageDao = PageUtils.getPageDao(dao);
		return pageDao.getPayAdvanceCancelList(payId);
	}

	/**
	 * 查询付款Log信息
	 * 
	 * @param cleanLogBean
	 * @return
	 */
	public List<PayQueryBean> queryPayLog(String payId) {
		// PayAddDAO pageDao = PageUtils.getPageDao(payAddDAO);
		CommonLogger.info("查询正常付款单号"+payId+"下暂收结清log信息,PayQueryService,queryPayLog");
		WebUtils.getRequest().getContextPath();
		return dao.queryPayLog(payId);
	}

	/**
	 * flag=0:正常转暂收/flag=1:暂收转正常
	 * 
	 * @param cleanLogBean
	 * @return
	 */
	public void change(CleanLogBean cleanLogBean) {
		if ("0".equals(cleanLogBean.getFlag())) {// 正常转暂收
			CommonLogger.info("付款单号（"+cleanLogBean.getPayId()+"）正常转为暂收,PayQueryService,change");
			this.changeSuseToNormal(cleanLogBean);
		} else {// 暂收转正常
			CommonLogger.info("付款单号（"+cleanLogBean.getPayId()+"）暂收转为正常,PayQueryService,change");
			this.changeNormalToSuse(cleanLogBean);
		}
	}

	// 正常转暂收
	private void changeSuseToNormal(CleanLogBean cleanLogBean) {
		// 查询正常付款的数据有几条(如果有多条则只更新结清明细表的状态)
		int normalPayNum = this.queryNormalPayNumById(cleanLogBean.getPayId(), null);
		cleanLogBean.setPayType("1");
		cleanLogBean.setModiUser(WebHelp.getLoginUser().getUserId());
		CommonLogger.info("付款单号（"+cleanLogBean.getPayId()+"）正常转为暂收，更新结清明细表的状态,PayQueryService,changeSuseToNormal");
		dao.updateCleanLog(cleanLogBean);// 更新结清明细表的状态
		if ("N".equalsIgnoreCase(cleanLogBean.getPayCancelState())) {// 付款没有取消
			CommonLogger.info("付款单号（"+cleanLogBean.getPayId()+"）正常转为暂收，更新累计结清金额,PayQueryService,changeSuseToNormal");
			dao.updateSusTotalAmt(cleanLogBean);// 更新累计结清金额
		}
		if (normalPayNum > 1) {
			CommonLogger.info("付款单号（"+cleanLogBean.getPayId()+"）正常转为暂收，查询付款信息,PayQueryService,changeSuseToNormal");
			PayQueryBean pqbBean = dao.getPayByPayId(cleanLogBean.getPayId());
			if ("0".equals(cleanLogBean.getDataFlagPay())) {
				cleanLogBean.setDataFlag("10");
			} else if ("2".equals(pqbBean.getDataFlagInvoice())) {
				if ("2".equals(cleanLogBean.getDataFlagPay()) || "3".equals(cleanLogBean.getDataFlagPay())) {
					cleanLogBean.setDataFlag("12");
				}
			} else if ("3".equals(pqbBean.getDataFlagInvoice())) {
				if ("2".equals(cleanLogBean.getDataFlagPay()) || "3".equals(cleanLogBean.getDataFlagPay())) {
					cleanLogBean.setDataFlag("13");
				}
			}
		} else if (normalPayNum == 1) {
			cleanLogBean.setDataFlagPay("0");
			cleanLogBean.setDataFlag("10");
		}
		CommonLogger.info("付款单号（"+cleanLogBean.getPayId()+"）正常转为暂收，更新付款的状态,PayQueryService,changeSuseToNormal");
		dao.updateDataFlagPay(cleanLogBean);// 更新付款的状态
	}

	// 暂收转正常
	private void changeNormalToSuse(CleanLogBean cleanLogBean) {
		cleanLogBean.setPayType("0");
		cleanLogBean.setModiUser(WebHelp.getLoginUser().getUserId());
		CommonLogger.info("付款单号（"+cleanLogBean.getPayId()+"）暂收转为正常，更新结清明细表的状态,PayQueryService,changeNormalToSuse");
		dao.updateCleanLog(cleanLogBean);// 更新结清明细表的状态
		if ("N".equalsIgnoreCase(cleanLogBean.getPayCancelState())) {// 付款没有取消
			cleanLogBean.setPayAmt(cleanLogBean.getPayAmt().negate());// 取反（-）
			CommonLogger.info("付款单号（"+cleanLogBean.getPayId()+"）暂收转为正常，更新累计结清金额,PayQueryService,changeNormalToSuse");
			dao.updateSusTotalAmt(cleanLogBean);// 更新累计结清金额
		}
		CommonLogger.info("付款单号（"+cleanLogBean.getPayId()+"）暂收转为正常，查询付款信息,PayQueryService,changeNormalToSuse");
		PayQueryBean pqbBean = dao.getPayByPayId(cleanLogBean.getPayId());
		if ("0".equals(cleanLogBean.getDataFlagPay())) {
			cleanLogBean.setDataFlag("10");
		} else if ("2".equals(pqbBean.getDataFlagInvoice())) {
			if ("2".equals(cleanLogBean.getDataFlagPay()) || "3".equals(cleanLogBean.getDataFlagPay())) {
				cleanLogBean.setDataFlag("12");
			}
		} else if (pqbBean.getDataFlagInvoice().equals("3")) {
			if ("2".equals(cleanLogBean.getDataFlagPay()) || "3".equals(cleanLogBean.getDataFlagPay())) {
				cleanLogBean.setDataFlag("13");
			}
		}
		CommonLogger.info("更新付款单号（"+cleanLogBean.getPayId()+"）的状态,PayQueryService,changeNormalToSuse");
		dao.updateDataFlagPay(cleanLogBean);// 更新付款的状态
	}

	/**
	 * 查询付款数据的个数
	 * 
	 * @param bean
	 * @return
	 */
	public int queryNormalPayNumById(String payId, String payCancelState) {
		CommonLogger.info("查询正常付款单号"+payId+"的数据个数,PayQueryService,queryNormalPayNumById");
		return dao.queryNormalPayNumById(payId, payCancelState);
	}

	/**
	 * 付款流水明细
	 * @param payQueryBean
	 * @return
	 */
	public PayQueryBean queryPayLogDetail(PayQueryBean payQueryBean) {
		CommonLogger.info("查询付款单号"+payQueryBean.getPayId()+"的付款流水明细,PayQueryService,queryPayLogDetail");
		return dao.queryPayLogDetail(payQueryBean);
	}
	
	
	public String getOrder(PayQueryBean payQueryBean) {
		CommonLogger.info("查询合同号"+payQueryBean.getCntNum()+"是否订单,PayQueryService,getOrder");
		return dao.getOrder(payQueryBean);
	}

	/**
	 * 根据合同号得到合同的影像
	 * @param cntNum
	 * @return
	 */
	public QueryContract getCntICMSByCntNum(String cntNum) {
		return dao.getCntICMSByCntNum(cntNum);
	}

	/**
	 * 22付款明细查询
	 * @param invoiceId
	 * @return
	 */
	public List<PayQueryBean> queryPay22Detail(String invoiceId) {
		return dao.queryPay22Detail(invoiceId);
	}
	//获得类实例
	public static PayQueryService getInstance(){
		return SpringUtil.getBean(PayQueryService.class);
	}

	public List<PayQueryBean> exportExcute(PayQueryBean bean) {
		return dao.list(bean);
	}

	public String exportData(PayQueryBean bean)throws Exception {
		String sourceFileName = "付款导出";
		ImportUtil.createFilePath(new File(WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")));
		String destFile = WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")+"/"+sourceFileName+".xlsx";;
		//导出Excel操作
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("cntNum", bean.getCntNum());
		map.put("cntType",bean.getCntType());
		map.put("payId", bean.getPayId());
		map.put("invoiceId", bean.getInvoiceId());
		map.put("providerName", bean.getProviderName());
		map.put("befDate", bean.getBefDate());
		map.put("aftDate", bean.getAftDate());
		map.put("payDataFlag", bean.getPayDataFlag());
		map.put("dataFlagInvoice", bean.getDataFlagInvoice());
		map.put("dataFlagPay", bean.getDataFlagPay());
		map.put("projName", bean.getProjName());
		map.put("conCglCode", bean.getConCglCode());
		String org1Code = WebHelp.getLoginUser().getOrg1Code();// 得到登录人所在一级行
		String org2Code = WebHelp.getLoginUser().getOrg2Code();// 得到登录人所在二级行
		String instDutyCode=WebHelp.getLoginUser().getDutyCode();//得到登录人所在责任中心
		
		map.put("instDutyCode", instDutyCode);
		map.put("org1Code", org1Code);
		map.put("org2Code", org2Code);
		map.put("orgFlag", bean.getOrgFlag());
		return exportDeal.execute(sourceFileName, "PAY_EXPORT", destFile , map);
	}
	/**
	 * 预付款导出
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public String PrePayexportData(PayQueryBean bean)throws Exception {
		String sourceFileName = "预付款导出";
		ImportUtil.createFilePath(new File(WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")));
		String destFile = WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")+"/"+sourceFileName+".xlsx";;
		//导出Excel操作
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("cntNum", bean.getCntNum());
		map.put("cntAllAmt",bean.getCntType());
		map.put("zbAmt", bean.getPayId());
		map.put("cntType", bean.getInvoiceId());
		map.put("normarlTotalAmt", bean.getProviderName());
		map.put("advanceTotalAmt", bean.getBefDate());
		map.put("freezeTotalAmt", bean.getAftDate());
		map.put("suspenseTotalAmt", bean.getPayDataFlag());
		map.put("payId", bean.getDataFlagInvoice());
		map.put("invoiceId", bean.getDataFlagPay());
		map.put("attachmentNum", bean.getProjName());
		map.put("providerName", bean.getConCglCode());
		map.put("provActNo", bean.getConCglCode());
		map.put("bankName", bean.getConCglCode());
		map.put("payDate", bean.getConCglCode());
		map.put("payModeName", bean.getConCglCode());
		map.put("advancePayId", bean.getConCglCode());
		map.put("payTotalAmt", bean.getConCglCode());

		String org1Code = WebHelp.getLoginUser().getOrg1Code();// 得到登录人所在一级行
		String org2Code = WebHelp.getLoginUser().getOrg2Code();// 得到登录人所在二级行
		String instDutyCode=WebHelp.getLoginUser().getDutyCode();//得到登录人所在责任中心
		
		map.put("instDutyCode", instDutyCode);
		map.put("org1Code", org1Code);
		map.put("org2Code", org2Code);
		map.put("orgFlag", bean.getOrgFlag());
		map.put("payid", bean.getPayId());
		map.put("exportType", "bean");
		return exportDeal.execute(sourceFileName, "PERPAY_EXPORT", destFile , map);
	}
}
