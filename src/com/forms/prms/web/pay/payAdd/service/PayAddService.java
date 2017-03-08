package com.forms.prms.web.pay.payAdd.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.contract.confirm.domain.ConfirmContract;
import com.forms.prms.web.pay.payAdd.dao.PayAddDAO;
import com.forms.prms.web.pay.payAdd.domain.PayAddBean;
import com.forms.prms.web.pay.payAdd.domain.PayAddBgtBean;
import com.forms.prms.web.pay.paycommon.service.PayCommonService;
import com.forms.prms.web.sysmanagement.concurrent.domain.ConcurrentType;
import com.forms.prms.web.sysmanagement.concurrent.service.ConcurrentService;

@Service
public class PayAddService {
	@Autowired
	private PayAddDAO payAddDAO;
	
	@Autowired
	private PayCommonService payCommonService;
	@Autowired
	private ConcurrentService concurrentService;
	
	/**
	 * 合同待付款信息列表查询
	 * 
	 * @param payAddBean
	 * @return
	 */
	public List<PayAddBean> payList(PayAddBean payAddBean) {
		CommonLogger.info("合同待付款信息列表查询，PayAddService，payList");
		PayAddDAO pageDao = PageUtils.getPageDao(payAddDAO);
		payAddBean.setPayDutyCode(WebHelp.getLoginUser().getDutyCode());// 当前登录人付款所在责任中心
		return pageDao.payList(payAddBean);
	}

	/**
	 * 根据合同号查询合同信息
	 * 
	 * @param payAddBean
	 * @return
	 */
	public PayAddBean constractInfo(PayAddBean payAddBean) {
		CommonLogger.info("查询合同号"+payAddBean.getCntNum()+"的合同信息，PayAddService，constractInfo");
		return payAddDAO.constractInfo(payAddBean);
	}

	/**
	 * 根据合同号查询正常付款信息
	 * 
	 * @param payAddBean
	 * @return
	 */
	public List<PayAddBean> queryPayList(PayAddBean payAddBean) {
		CommonLogger.info("查询合同号"+payAddBean.getCntNum()+"的正常付款列表信息，PayAddService，queryPayList");
		PageUtils.setPageSize("ADD_PAY_PAGE_KEY",5);
		PayAddDAO pageDao = PageUtils.getPageDao(payAddDAO,"ADD_PAY_PAGE_KEY");
		return pageDao.queryPayList(payAddBean);
	}

	/**
	 * 根据合同号查询预付款信息
	 * 
	 * @param payAddBean
	 * @return
	 */
	public List<PayAddBean> queryPayAdvanceList(PayAddBean payAddBean) {
		CommonLogger.info("查询合同号"+payAddBean.getCntNum()+"的预付款列表信息，PayAddService，queryPayAdvanceList");
		PageUtils.setPageSize("ADD_ADVPAY_PAGE_KEY",5);
		PayAddDAO pageDao = PageUtils.getPageDao(payAddDAO,"ADD_ADVPAY_PAGE_KEY");
		return pageDao.queryPayAdvanceList(payAddBean);
	}

	/**
	 * 预付款新增保存
	 * 
	 * @param payAddBean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean addPayAdvanceSave(PayAddBean payAddBean) {
		payAddBean.setDataFlag("A0");// 状态
		payAddBean.setDataFlagInvoice("0");// 发票状态
		payAddBean.setDataFlagPay("0");// 付款状态
		payAddBean.setInstDutyCode(WebHelp.getLoginUser().getDutyCode());// 责任中心
		payAddBean.setInstUser(WebHelp.getLoginUser().getUserId());// 创建人
		CommonLogger.info("预付款新增保存添加预付款单号"+payAddBean.getPayId()+"的预付款信息，PayAddService，addPayAdvanceSave");
		int n = payAddDAO.addPayAdvanceSaveOrSubimt(payAddBean);
		
		//添加到付款log表中
		payAddBean.setOperMemo("预付款新增保存，保存为草稿");
		payAddBean.setInstOper(WebHelp.getLoginUser().getUserId());
		CommonLogger.info("预付款新增保存添加预付款单号"+payAddBean.getPayId()+"的预付款log信息，PayAddService，addPayAdvanceSave");
		payAddDAO.addPayLog(payAddBean);
		
		// 更新当前合同的冻结金额
		payAddBean.setDataFlag("20");// 合同状态
		payAddBean.setFreezeTotalAmt(payAddBean.getInvoiceAmt());
		CommonLogger.info("预付款新增保存更新合同"+payAddBean.getCntNum()+"的冻结金额，PayAddService，addPayAdvanceSave");
		payAddDAO.updateFreezeTotalAmt(payAddBean);
		
		CommonLogger.info("预付款保存添加付款单"+payAddBean.getPayId()+"的信息到统计表中，PayAddService，addPayAdvanceSave");
		payCommonService.addSysWarnPayInfo(payAddBean.getPayId(),payAddBean.getInstDutyCode(),"P0");
		return n > 0 ? true : false;
	}

	/**
	 * 预付款新增提交
	 * 
	 * @param payAddBean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean addPayAdvanceSubmit(PayAddBean payAddBean) {
		payAddBean.setDataFlag("B0");// 状态
		payAddBean.setDataFlagInvoice("0");// 发票状态
		payAddBean.setDataFlagPay("0");// 付款状态
		payAddBean.setInstDutyCode(WebHelp.getLoginUser().getDutyCode());// 责任中心
		payAddBean.setInstUser(WebHelp.getLoginUser().getUserId());// 创建人
		CommonLogger.info("预付款新增提交添加预付款单号"+payAddBean.getPayId()+"的预付款信息，PayAddService，addPayAdvanceSubmit");
		int n = payAddDAO.addPayAdvanceSaveOrSubimt(payAddBean);
		
		//添加到付款log表中
		payAddBean.setOperMemo("预付款新增提交，提交到待复核");
		payAddBean.setInstOper(WebHelp.getLoginUser().getUserId());
		CommonLogger.info("预付款新增提交添加预付款单号"+payAddBean.getPayId()+"的预付款log信息，PayAddService，addPayAdvanceSubmit");
		payAddDAO.addPayLog(payAddBean);
		
		// 更新当前合同的冻结金额
		payAddBean.setDataFlag("20");// 合同状态
		payAddBean.setFreezeTotalAmt(payAddBean.getInvoiceAmt());
		CommonLogger.info("预付款新增提交更新合同"+payAddBean.getCntNum()+"的冻结金额，PayAddService，addPayAdvanceSubmit");
		payAddDAO.updateFreezeTotalAmt(payAddBean);
		
		/*//如果是生成订单的合同则在此生成订单
		if (payAddBean.getIsOrder().equals("0")) {
			orderStartService.orderCreateList(payAddBean.getCntNum());
		}*/
		CommonLogger.info("预付款新增添加付款单"+payAddBean.getPayId()+"的信息到统计表中，PayAddService，addPayAdvanceSubmit");
		payCommonService.addSysWarnPayInfo(payAddBean.getPayId(),payAddBean.getInstDutyCode(),"P1");
		
		return n > 0 ? true : false;
	}

	/**
	 * 生成付款单号
	 * 
	 * @param payAddBean
	 * @return
	 */
	public String createPayId(PayAddBean payAddBean) {
		CommonLogger.info("生成付款单号，PayAddService，createPayId");
		return payAddDAO.createPayId(payAddBean);
	}

	/**
	 * 根据合同号查预付款核销信息
	 * 
	 * @param payAddBean
	 * @return
	 */
	public List<PayAddBean> queryPayAdvanceCancel(PayAddBean payAddBean) {
		CommonLogger.info("查询合同号"+payAddBean.getCntNum()+"的预付款核销信息，PayAddService，queryPayAdvanceCancel");
		return payAddDAO.queryPayAdvanceCancel(payAddBean);
	}

	/**
	 * 查询合同采购设备信息
	 * 
	 * @param payAddBean
	 * @return
	 */
	public List<PayAddBean> queryDevicesById(PayAddBean payAddBean) {
		CommonLogger.info("查询合同号"+payAddBean.getCntNum()+"的采购设备信息，PayAddService，queryDevicesById");
		return payAddDAO.queryDevicesById(payAddBean);
	}

	/**
	 * 正常付款新增保存
	 * 
	 * @param payAddBean
	 * @return
	 * @throws Exception 
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean addPaySave(PayAddBean payAddBean) throws Exception {
		payAddBean.setDataFlag("A0");// 状态
		payAddBean.setDataFlagInvoice("0");// 发票状态
		payAddBean.setDataFlagPay("0");// 付款状态
		payAddBean.setInstDutyCode(WebHelp.getLoginUser().getDutyCode());// 责任中心
		payAddBean.setInstUser(WebHelp.getLoginUser().getUserId());// 创建人
		// 添加正常付款信息到TD_PAY表
		CommonLogger.info("正常付款新增保存添加付款单号"+payAddBean.getPayId()+"的付款信息，PayAddService，addPaySave");
		int n = payAddDAO.addPayInfo(payAddBean);
		
		//贷项通知单需要更新原蓝字发票对应的剩余冲销金额
		if("0".equals(payAddBean.getIsCreditNote())){
			CommonLogger.info("贷项通知单需要更新原蓝字发票对应的剩余冲销金额"+payAddBean.getInvoiceIdBlue()+"，PayAddService，addPaySave");
			payAddDAO.updateBlueInvoiceAmtLeft(payAddBean);
		}
		
		//添加到付款log表中
		payAddBean.setOperMemo("正常付款新增保存，保存为草稿");
		payAddBean.setInstOper(WebHelp.getLoginUser().getUserId());
		CommonLogger.info("正常付款新增保存添加付款单号"+payAddBean.getPayId()+"的付款log信息，PayAddService，addPaySave");
		payAddDAO.addPayLog(payAddBean);
		
		if((payAddBean.getPayAmt().add(payAddBean.getSuspenseAmt())).compareTo(new BigDecimal(0))!=0){//非核销则执行
			// 更新当前合同的冻结金额
			payAddBean.setDataFlag("20");// 合同状态
			
			if("1".equals(payAddBean.getIsCreditNote())){
		    //正常付款
			payAddBean.setFreezeTotalAmt(payAddBean.getPayAmt().add(
					payAddBean.getSuspenseAmt()));
			}else{
				//贷项通知单
				payAddBean.setFreezeTotalAmt(new BigDecimal(0));
			}
			
			CommonLogger.info("正常付款新增保存更新合同"+payAddBean.getCntNum()+"的冻结金额，PayAddService，addPaySave");
			payAddDAO.updateFreezeTotalAmt(payAddBean);
		}
		
		// 非空,合同采购设备(正常)发票分配的金额添加到TD_PAY_DEVICE
		if (!Tool.CHECK.isEmpty(payAddBean.getPayInvAmts())) {
			List<PayAddBean> list = this.updateAddDevice(payAddBean);
			if (!Tool.CHECK.isEmpty(list)) {
				// 添加设备付款信息 to TD_PAY_DEVICE
				CommonLogger.info("正常付款新增保存添加设备付款信息（正常），PayAddService，addPaySave");
				for(PayAddBean pbAddBean : list){
					payAddDAO.addPayDevice(pbAddBean);
					if("0".equals(payAddBean.getIsCreditNote())){
						//贷项通知单需要更新原蓝字发票设备表中的剩余冲销金额
						CommonLogger.info("贷项通知单需要更新原蓝字发票设备表中的剩余冲销金额（付款单号："+payAddBean.getInvoiceIdBlue()+"），PayAddService，addPaySave");
						
						//1.判断是否存在预付款核销
						String existsFlag = payAddDAO.getExists0(pbAddBean);
						if("".equals(existsFlag)||null == existsFlag){//不存在
							//更新正常付款的可冲销金额
							payAddDAO.updateBlueDeviceLeft(pbAddBean);
						}else{//存在
							//1.查找能对预付款核销的记录冲销的最大值不含税及税 min(预付款核销剩余可冲销数，本次冲销数)
							PayAddBean aCUpdateBean = payAddDAO.getAdvanceUpdateLeftAmt(pbAddBean);
							//2.更新预付款核销的可冲销金额
							payAddDAO.updateBlueACDeviceLeft(aCUpdateBean);
							//3.更新正常付款的可冲销金额 （本次冲销额-预付款核销已冲销额）
							PayAddBean nUpdateBean = pbAddBean; //subInvoiceAmt addTaxAmt
							nUpdateBean.setSubInvoiceAmt(pbAddBean.getSubInvoiceAmt().add(aCUpdateBean.getAcUpdInvoiceAmtLeft()));
							nUpdateBean.setAddTaxAmt(pbAddBean.getAddTaxAmt().add(aCUpdateBean.getAcUpdTaxAmtLeft()));
							payAddDAO.updateBlueDeviceLeft(nUpdateBean);
						}
					}
				}
			}
			// 更新设备冻结金额
			if (!Tool.CHECK.isEmpty(list)) {
				CommonLogger.info("正常付款新增保存更新设备冻结金额（正常），PayAddService，addPaySave");
				for (PayAddBean pbAddBean : list) {
					payAddDAO.updateDevFreezeAmt(pbAddBean);
				}
			}
		}
		
		// 预付款核销信息添加
		if (!Tool.CHECK.isEmpty(payAddBean.getCancelAmts())) {
			List<PayAddBean> list = this.payAdvCancelList(payAddBean);
			if (!Tool.CHECK.isEmpty(list)) {
				CommonLogger.info("正常付款新增保存添加预付款核销信息（付款单号："+payAddBean.getNormalPayId()+"预付款单号："+payAddBean.getAdvancePayId()+"），PayAddService，addPaySave");
				for (PayAddBean pbAddBean : list) {
					payAddDAO.addPayAdvCancel(pbAddBean);
				}
			}
		}
		
		// 非空,合同采购设备(预付款)发票分配的金额添加到TD_PAY_DEVICE
		if (!Tool.CHECK.isEmpty(payAddBean.getAdvancePayInvAmts())) {
			List<PayAddBean> list = this.updateAddADvDevice(payAddBean);
			if (!Tool.CHECK.isEmpty(list)) {
				// 添加设备付款信息 to TD_PAY_DEVICE
				CommonLogger.info("正常付款新增保存添加设备付款信息（预付款），PayAddService，addPaySave");
				for(PayAddBean pbAddBean : list){
					payAddDAO.addPayDevice(pbAddBean);
					
				}

			}
			// 更新设备冻结金额
			if (!Tool.CHECK.isEmpty(list)) {
				CommonLogger.info("正常付款新增保存更新设备冻结金额（预付款），PayAddService，addPaySave");
				for (PayAddBean pbAddBean : list) {
					// 更新设备冻结金额
					payAddDAO.updateDevFreezeAmt(pbAddBean);
				}
			}
		}
		CommonLogger.info("正常付款保存添加付款单"+payAddBean.getPayId()+"的信息到统计表中，PayAddService，addPaySave");
		payCommonService.addSysWarnPayInfo(payAddBean.getPayId(),payAddBean.getInstDutyCode(),"P0");
		return n > 0 ? true : false;
	}

	// 预付款核销信息转List处理
	private List<PayAddBean> payAdvCancelList(PayAddBean payAddBean) {
		String invoiceId = payAddBean.getInvoiceId();// 发票号
		String normalPayId = payAddBean.getPayId();// 正常付款单号
		String[] advancePayIds = payAddBean.getAdvancePayIds();// 预付款单号（批次号）
		BigDecimal[] cancelAmts = payAddBean.getCancelAmts();// 本次核销金额
		List<PayAddBean> list = new ArrayList<PayAddBean>();
		for (int i = 0; i < cancelAmts.length; i++) {
			if (!Tool.CHECK.isEmpty(cancelAmts[i])) {
				PayAddBean pbBean = new PayAddBean();
				pbBean.setInvoiceId(invoiceId);
				pbBean.setNormalPayId(normalPayId);
				pbBean.setAdvancePayId(advancePayIds[i]);
				pbBean.setCancelAmt(cancelAmts[i]);
				list.add(pbBean);
			}
		}
		return list;
	}

	// 合同采购设备(预付款)转map处理
	private List<PayAddBean> updateAddADvDevice(PayAddBean payAddBean) {
		String invoiceId = payAddBean.getInvoiceId();// 发票号
		String payId = payAddBean.getPayId();// 单号
		String cntNum = payAddBean.getCntNum();// 合同号
		BigDecimal[] advSubIds = payAddBean.getAdvSubIds();// 子序列
		BigDecimal[] advancePayInvAmts = payAddBean.getAdvancePayInvAmts();// 不含税金额
		BigDecimal[] advPayAddTaxAmts = payAddBean.getAdvPayAddTaxAmts();// 税额
		String[] advancePayIvrowMemos = payAddBean.getAdvancePayIvrowMemos();// 发票行说明
		List<PayAddBean> list = new ArrayList<PayAddBean>();
		for (int i = 0; i < advancePayInvAmts.length; i++) {
			PayAddBean pbBean = new PayAddBean();
			pbBean.setInvoiceId(invoiceId);
			pbBean.setPayId(payId);
			pbBean.setPayFlag("0");
			pbBean.setCntNum(cntNum);
			pbBean.setSubId(advSubIds[i]);
			pbBean.setSubInvoiceAmt(advancePayInvAmts[i]);//不含税金额
			pbBean.setAddTaxAmt(advPayAddTaxAmts[i]);//税额
			pbBean.setFreezeAmt(advancePayInvAmts[i]);//冻结不含税金额
			pbBean.setFreezeAmtTax(advPayAddTaxAmts[i]);
			if (advancePayIvrowMemos.length == 0) {
				pbBean.setIvrowMemo(null);
			} else {
				pbBean.setIvrowMemo(advancePayIvrowMemos[i]);
			}
			list.add(pbBean);
		}
		return list;
	}

	// 合同采购设备(正常)转map处理
	private List<PayAddBean> updateAddDevice(PayAddBean payAddBean) {
		String invoiceId = payAddBean.getInvoiceId();// 发票号
		String invoiceIdBlue = payAddBean.getInvoiceIdBlue();//原蓝字发票编号
		String payIdBlue = payAddBean.getPayIdBlue();//原蓝字付款单号
		String payId = payAddBean.getPayId();// 单号
		String cntNum = payAddBean.getCntNum();// 合同号
		BigDecimal[] subIds = payAddBean.getSubIds();// 子序列
		BigDecimal[] payInvAmts = payAddBean.getPayInvAmts();// 不含税金额
		BigDecimal[] payAddTaxAmts = payAddBean.getPayAddTaxAmts();// 税额
		String[] payIvrowMemos = payAddBean.getPayIvrowMemos();// 发票行说明		
		List<PayAddBean> list = new ArrayList<PayAddBean>();
		for (int i = 0; i < payInvAmts.length; i++) {
			PayAddBean pbBean = new PayAddBean();
			pbBean.setInvoiceId(invoiceId);
			pbBean.setInvoiceIdBlue(invoiceIdBlue);
			pbBean.setPayIdBlue(payIdBlue);
			pbBean.setPayId(payId);
			pbBean.setPayFlag("1");
			pbBean.setCntNum(cntNum);
			pbBean.setSubId(subIds[i]);
			pbBean.setSubInvoiceAmt(payInvAmts[i]);//不含税金额
			pbBean.setAddTaxAmt(payAddTaxAmts[i]);// 税额
			pbBean.setFreezeAmt(payInvAmts[i]); //冻结不含税金额
			pbBean.setFreezeAmtTax(payAddTaxAmts[i]);//冻结税额
			if (payIvrowMemos.length == 0) {
				pbBean.setIvrowMemo(null);
			}else{
				pbBean.setIvrowMemo(payIvrowMemos[i]);
			}
			list.add(pbBean);
			
		}
		return list;
	}

	/**
	 * 正常付款新增提交
	 * 
	 * @param payAddBean
	 * @return
	 * @throws Exception 
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean addPaySubmit(PayAddBean payAddBean) throws Exception {
		payAddBean.setDataFlag("B0");// 状态
		payAddBean.setDataFlagInvoice("0");// 发票状态
		payAddBean.setDataFlagPay("0");// 付款状态
		payAddBean.setInstDutyCode(WebHelp.getLoginUser().getDutyCode());// 责任中心
		payAddBean.setInstUser(WebHelp.getLoginUser().getUserId());// 创建人
		// 添加正常付款信息到TD_PAY表
		CommonLogger.info("正常付款新增提交添加付款单号"+payAddBean.getPayId()+"付款信息，PayAddService，addPaySubmit");
		int n = payAddDAO.addPayInfo(payAddBean);
		
		//贷项通知单需要更新原蓝字发票对应的剩余冲销金额
				if("0".equals(payAddBean.getIsCreditNote())){
					CommonLogger.info("贷项通知单需要更新原蓝字发票对应的剩余冲销金额"+payAddBean.getInvoiceIdBlue()+"，PayAddService，addPaySave");
					payAddDAO.updateBlueInvoiceAmtLeft(payAddBean);
				}
				
		//添加到付款log表中
		payAddBean.setOperMemo("正常付款新增提交，提交到待复核");
		payAddBean.setInstOper(WebHelp.getLoginUser().getUserId());
		CommonLogger.info("正常付款新增提交添加付款单号"+payAddBean.getPayId()+"付款log信息，PayAddService，addPaySubmit");
		payAddDAO.addPayLog(payAddBean);
		
		if((payAddBean.getPayAmt().add(payAddBean.getSuspenseAmt())).compareTo(new BigDecimal(0))!=0){//非核销则执行
			// 更新当前合同的冻结金额
			payAddBean.setDataFlag("20");// 合同状态
			if("1".equals(payAddBean.getIsCreditNote())){
			    //正常付款
				payAddBean.setFreezeTotalAmt(payAddBean.getPayAmt().add(
						payAddBean.getSuspenseAmt()));
				}else{
					//贷项通知单
					payAddBean.setFreezeTotalAmt(new BigDecimal(0));
				}
				
			CommonLogger.info("正常付款新增提交更新合同"+payAddBean.getCntNum()+"的冻结金额，PayAddService，addPaySubmit");
			payAddDAO.updateFreezeTotalAmt(payAddBean);
		}

		// 非空,合同采购设备(正常)发票分配的金额添加到TD_PAY_DEVICE
		if (!Tool.CHECK.isEmpty(payAddBean.getPayInvAmts())) {
			List<PayAddBean> list = this.updateAddDevice(payAddBean);
			if (!Tool.CHECK.isEmpty(list)) {
				// 添加设备付款信息 to TD_PAY_DEVICE
				CommonLogger.info("正常付款新增提交添加设备付款信息（正常），PayAddService，addPaySave");
				for(PayAddBean pbAddBean : list){
					payAddDAO.addPayDevice(pbAddBean);
					if("0".equals(payAddBean.getIsCreditNote())){
						//贷项通知单需要更新原蓝字发票设备表中的剩余冲销金额
						CommonLogger.info("贷项通知单需要更新原蓝字发票设备表中的剩余冲销金额（付款单号："+payAddBean.getInvoiceIdBlue()+"），PayAddService，addPaySave");
						
						//1.判断是否存在预付款核销
						String existsFlag = payAddDAO.getExists0(pbAddBean);
						if("".equals(existsFlag)||null == existsFlag){//不存在
							//更新正常付款的可冲销金额
							payAddDAO.updateBlueDeviceLeft(pbAddBean);
						}else{//存在
							//1.查找能对预付款核销的记录冲销的最大值不含税及税 min(预付款核销剩余可冲销数，本次冲销数)
							PayAddBean aCUpdateBean = payAddDAO.getAdvanceUpdateLeftAmt(pbAddBean);
							//2.更新预付款核销的可冲销金额
							payAddDAO.updateBlueACDeviceLeft(aCUpdateBean);
							//3.更新正常付款的可冲销金额 （本次冲销额-预付款核销已冲销额）
							PayAddBean nUpdateBean = pbAddBean; //subInvoiceAmt addTaxAmt
							nUpdateBean.setSubInvoiceAmt(pbAddBean.getSubInvoiceAmt().add(aCUpdateBean.getAcUpdInvoiceAmtLeft()));
							nUpdateBean.setAddTaxAmt(pbAddBean.getAddTaxAmt().add(aCUpdateBean.getAcUpdTaxAmtLeft()));
							payAddDAO.updateBlueDeviceLeft(nUpdateBean);
						}
					}
				}
			}
			// 更新设备冻结金额
			if (!Tool.CHECK.isEmpty(list)) {
				CommonLogger.info("正常付款新增提交更新设备冻结金额（正常），PayAddService，addPaySave");
				for (PayAddBean pbAddBean : list) {
					payAddDAO.updateDevFreezeAmt(pbAddBean);
				}
			}
		}
		
		// 预付款核销信息添加
		if (!Tool.CHECK.isEmpty(payAddBean.getCancelAmts())) {

			List<PayAddBean> list = this.payAdvCancelList(payAddBean);
			if (!Tool.CHECK.isEmpty(list)) {
				CommonLogger.info("正常付款新增提交添加预付款核销信息（付款单号："+payAddBean.getNormalPayId()+"预付款单号："+payAddBean.getAdvancePayId()+"），PayAddService，addPaySave");
				for (PayAddBean pbAddBean : list) {
					payAddDAO.addPayAdvCancel(pbAddBean);
				}
			}
		}
		
		// 非空,合同采购设备(预付款)发票分配的金额添加到TD_PAY_DEVICE
		if (!Tool.CHECK.isEmpty(payAddBean.getAdvancePayInvAmts())) {
			List<PayAddBean> list = this.updateAddADvDevice(payAddBean);
			if (!Tool.CHECK.isEmpty(list)) {
				// 添加设备付款信息 to TD_PAY_DEVICE
				CommonLogger.info("正常付款新增提交添加设备付款信息（预付款），PayAddService，addPaySave");
				for (PayAddBean pbAddBean : list) {
					payAddDAO.addPayDevice(pbAddBean);
					
				}

			}
			// 更新设备冻结金额
			if (!Tool.CHECK.isEmpty(list)) {
				CommonLogger.info("正常付款新增提交更新设备冻结金额（预付款），PayAddService，addPaySave");
				for (PayAddBean pbAddBean : list) {
					// 更新设备冻结金额
					payAddDAO.updateDevFreezeAmt(pbAddBean);
				}
			}
		}
		
		/*//如果是生成订单的合同则在此生成订单
		if (payAddBean.getIsOrder().equals("0")) {
			orderStartService.orderCreateList(payAddBean.getCntNum());
		}*/
		CommonLogger.info("正常付款新增添加付款单"+payAddBean.getPayId()+"的信息到统计表中，PayAddService，addPayAdvanceSubmit");
		payCommonService.addSysWarnPayInfo(payAddBean.getPayId(),payAddBean.getInstDutyCode(),"P1");
		
		return n > 0 ? true : false;
	}

	/**
	 * 根据预付款单号查预付款核销明细
	 * 
	 * @param payAddBean
	 * @return
	 */
	public PayAddBean queryPayAdvCancelDetail(PayAddBean payAddBean) {
		CommonLogger.info("查询预付款单号"+payAddBean.getAdvancePayId()+"的明细信息，PayAddService，queryPayAdvCancelDetail");
		return payAddDAO.queryPayAdvCancelDetail(payAddBean);
	}

	/**
	 * 查询付款信息（正常或预付款）
	 * 
	 * @param payAddBean
	 * @return
	 */
	public PayAddBean queryPayInfo(PayAddBean payAddBean) {
		CommonLogger.info("查询付款单号"+payAddBean.getPayId()+"的付款信息，PayAddService，queryPayInfo");
		return payAddDAO.queryPayInfo(payAddBean);
	}

	/**
	 * 正常付款暂收结清处理信息提交
	 * 
	 * @param payAddBean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean paySuspenseDealSubmit(PayAddBean payAddBean) {
		CommonLogger.info("正常付款暂收结清处理信息提交，PayAddService，paySuspenseDealSubmit");
		payAddBean.setDataFlag("00");
		return payAddDAO.paySuspenseDealSubmit(payAddBean) > 0 ? true : false;
	}

	/**
	 * 查询已结清列表
	 * 
	 * @param payAddBean
	 * @return
	 */
	public List<PayAddBean> queryPayCleanInfo(PayAddBean payAddBean) {
		CommonLogger.info("查询已结清列表，PayAddService，queryPayCleanInfo");
		return payAddDAO.queryPayCleanInfo(payAddBean);
	}

	/**
	 * 已结清明细查询
	 * 
	 * @param payAddBean
	 * @return
	 */
	public PayAddBean querySuspenseDetail(PayAddBean payAddBean) {
		CommonLogger.info("已结清明细查询，PayAddService，querySuspenseDetail");
		return payAddDAO.querySuspenseDetail(payAddBean);
	}

	/**
	 * 查询预付款或正常付款合同采购设备信息（明细）
	 * 
	 * @param payAddBean
	 * @return
	 */
	public List<PayAddBean> queryDeviceDetailsById(PayAddBean payAddBean) {
		CommonLogger.info("查询付款单号"+payAddBean.getPayId()+"的合同采购设备信息，PayAddService，queryDeviceDetailsById");
		return payAddDAO.queryDeviceDetailsById(payAddBean);
	}

	/**
	 * 查询正在结清的总金额
	 * 
	 * @param payAddBean
	 * @return
	 */
	public String queryCleanAmtIng(PayAddBean payAddBean) {
		CommonLogger.info("查询正在结清的总金额，PayAddService，queryCleanAmtIng");
		return payAddDAO.queryCleanAmtIng(payAddBean);
	}

	/**
	 * 查询附件类型对应的值(从SYS_SELECT表中)
	 * 
	 * @return
	 */
	public List<PayAddBean> queryAtType() {
		CommonLogger.info("查询附件类型的列表值，PayAddService，queryAtType");
		return payAddDAO.queryAtType();
	}

	/**
	 * 生成发票号
	 * 
	 * @param payAddBean
	 * @return
	 */
	public String createInvoiceId(PayAddBean payAddBean) {
		CommonLogger.info("生成发票号，PayAddService，createInvoiceId");
		return payAddDAO.createInvoiceId(payAddBean);
	}

	/**
	 * 查询审批历史记录
	 * @param payId
	 * @return
	 */
	public List<PayAddBean> queryHis(String payId) {
		CommonLogger.info("查询付款单号"+payId+"审批历史记录，PayAddService，queryHis");
		return payAddDAO.queryHis(payId);
	}

	/**
	 * 根据合同号查询TI_TRADE_BACKWASH是否有数据
	 * @param cntNum
	 * @return
	 */
	public String getIdByCntNum(String cntNum) {
		CommonLogger.info("根据合同号查询TI_TRADE_BACKWASH是否有数据(合同号："+cntNum+")，PayAddService，getIdByCntNum");
		return payAddDAO.getIdByCntNum(cntNum);
	}

	/**
	 * 预算冻结的ajax校验
	 * @param payId
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public String checkBgtFrozen(Map<String, String> param) {
		CommonLogger.info("预算冻结的ajax校验，PayAddService，checkBgtFrozen");
		return payAddDAO.checkBgtFrozen(param);
	}

	/**
	 * 查询付款冻结失败的信息
	 * @param payId
	 * @return
	 */
	public List<PayAddBgtBean> queryBgtFrozenFailMsg(String payId) {
		CommonLogger.info("查询付款单号"+payId+"冻结失败的信息，PayAddService，queryBgtFrozenFailMsg");
		return payAddDAO.queryBgtFrozenFailMsg(payId);
	}

	/**
	 * 更新付款单的状态
	 * @param payAddBean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean changePayStatus(PayAddBean payAddBean) {
		CommonLogger.info("更新付款单"+payAddBean.getPayId()+"的状态，PayAddService，changePayStatus");
		int n = payAddDAO.changePayStatus(payAddBean);
		if(n>0){
			CommonLogger.info("付款提交添加付款单"+payAddBean.getPayId()+"的信息到统计表中，PayAddService，changePayStatus");
			payCommonService.updateSysWarnPayInfo(payAddBean.getPayId(),WebHelp.getLoginUser().getDutyCode(),"P1");
		}
		return n>0?true:false;
	}

	/**
	 * 校验预算是否透支的ajax校验
	 * @param payId
	 * @return
	 * @throws Exception 
	 */
	@Transactional(rollbackFor = Exception.class)
	public String checkBgtOverdraw(PayAddBean payAddBean) {
		CommonLogger.info("正常付款新增提交校验预算是否透支"+payAddBean.getPayId()+"，PayAddService，checkBgtOverdraw");
		List<PayAddBean> list = this.updateAddDevice(payAddBean);
		StringBuffer bgtInfo = new StringBuffer("");
		for(int i=0;i<list.size();i++){
			PayAddBean pBean = list.get(i);
			if(pBean.getSubInvoiceAmt().compareTo(new BigDecimal(0))>0){//发票行金额大于0的去校验
				PayAddBean paBean = payAddDAO.checkBgtOverdraw(pBean);
				if(Tool.CHECK.isEmpty(paBean)){
					bgtInfo.append("第"+pBean.getSubId()+"条设备物料的预算未冻结，不能付款！") ;
					bgtInfo.append("&");
				}
				else if(!Tool.CHECK.isEmpty(paBean) && paBean.getBgtOverdraw().compareTo(new BigDecimal(0)) < 0)
				{
					bgtInfo.append("第"+pBean.getSubId()+"条设备物料的预算已透支，不能付款！");
					bgtInfo.append("&");
				}
			}
		}
		return bgtInfo.toString();
	}

	public String ajaxCheckCanAddScanBatch(String userId) {
		// TODO Auto-generated method stub
		CommonLogger.info("校验是否可以新增扫描批次的状态，PayAddService，changePayStatus");
		return "0".equals(payAddDAO.ajaxCheckCanAddScanBatch(userId))? "Y":"N";
	}

	public List<PayAddBean> getCancelData(String payId) {
		CommonLogger.info("获取订单类正常付款核销封面打印中的核销信息（PO单号、PO行号、数量），PayAddService，getCancelData");
		return payAddDAO.getCancelData(payId);
	}

	public List<PayAddBean> queryCancelDevices(String payId) {
		CommonLogger.info("查询预付款核销付款设备信息），PayAddService，queryCancelDevices");
		return payAddDAO.queryCancelDevices(payId);
	}

	public boolean ajaxCheckInvoiceId(String invoiceId,String payId ,String tableName,String modifyFlag) {
		return Tool.CHECK.isBlank(payAddDAO.ajaxCheckInvoiceId(invoiceId,payId,tableName,modifyFlag))?true:false;
	}
	
	public String checkOuprovider(String ouCode, String providerCode,
			String providerAddrCode) {
		CommonLogger.info("PayAddService，checkOuprovider");
		return payAddDAO.checkOuprovider(ouCode,providerCode,providerAddrCode);
	}

	/**
	 * 查看可冲销的蓝字发票列表
	 */
	public List<PayAddBean> getBlueInvoiceList(PayAddBean payAddBean){
		CommonLogger.info("查询可冲销的原蓝字发票列表，PayAddService，getBlueInvoiceList");
		PayAddDAO pageDao = PageUtils.getPageDao(payAddDAO,"blueinvoicepage");
		return pageDao.getBlueInvoiceList(payAddBean);
	}
	
	/**
	 * 查看原蓝字发票对应的物料行信息
	 * @param invoiceIdBlue
	 * @return
	 */
	public List<PayAddBean> queryInvoiceBlueDevice(PayAddBean payAddBean){
		CommonLogger.info("查询原蓝字发票"+payAddBean.getInvoiceIdBlue()+"的采购设备信息，PayAddService，queryInvoiceBlueDevice");
		return payAddDAO.queryInvoiceBlueDevice(payAddBean.getInvoiceIdBlue());
	}
	
	/**
	 * 查看原蓝字发票头的信息
	 * @param invoiceIdBlue
	 * @return
	 */
	public PayAddBean queryInvoiceBlue(PayAddBean payAddBean){
		CommonLogger.info("查询原蓝字发票"+payAddBean.getInvoiceIdBlue()+"的发票头信息，PayAddService，queryInvoiceBlue");
		return payAddDAO.queryInvoiceBlue(payAddBean.getInvoiceIdBlue());
	
	}
	
	/**
	 * 查看原蓝字发票下的贷项通知单列表
	 * @param invoiceIdBlue
	 * @return
	 */
	public List<PayAddBean> getCreditListByInvoiceidBlue(PayAddBean payAddBean){
		CommonLogger.info("查看原蓝字发票下的贷项通知单列表，PayAddService，getCreditListByInvoiceidBlue");
		PayAddDAO pageDao = PageUtils.getPageDao(payAddDAO);
		return pageDao.getCreditListByInvoiceidBlue(payAddBean);
	}
	
	
	
	
}
