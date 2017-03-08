package com.forms.prms.web.pay.paymodify.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.pay.payAdd.dao.PayAddDAO;
import com.forms.prms.web.pay.payAdd.domain.PayAddBean;
import com.forms.prms.web.pay.paycommon.service.PayCommonService;
import com.forms.prms.web.pay.paymodify.dao.PayModifyDAO;
import com.forms.prms.web.pay.paymodify.domain.PayModifyBean;

/**
 * author : lisj <br>
 * date : 2015-02-02<br>
 * 合同付款修改service
 */
@Service
public class PayModifyService {
	@Autowired
	private PayModifyDAO payModifyDAO;
	
	@Autowired
	private PayAddDAO payAddDAO;

	@Autowired
	private PayCommonService payCommonService;
	/**
	 * 付款信息列表查询
	 * 
	 * @param payModifyBean
	 * @return
	 */
	public List<PayModifyBean> list(PayModifyBean payModifyBean) {
		CommonLogger.info("付款信息列表查询，PayModifyService，list");
		payModifyBean.setInstDutyCode(WebHelp.getLoginUser().getDutyCode());// 当前登录人所在责任中心
		PayModifyDAO pageDao = PageUtils.getPageDao(payModifyDAO);
		return pageDao.list(payModifyBean);
	}

	/**
	 * 根据合同号查询合同信息
	 * 
	 * @param payModifyBean
	 * @return
	 */
	public PayModifyBean constractInfo(PayModifyBean payModifyBean) {
		CommonLogger.info("查询合同号"+payModifyBean.getCntNum()+"的合同信息，PayModifyService，constractInfo");
		return payModifyDAO.constractInfo(payModifyBean);
	}

	/**
	 * 查询付款信息（正常或预付款）
	 * 
	 * @param payModifyBean
	 * @return
	 */
	public PayModifyBean queryPayInfo(PayModifyBean payModifyBean) {
		CommonLogger.info("查询付款单号"+payModifyBean.getPayId()+"的付款信息，PayModifyService，queryPayInfo");
		return payModifyDAO.queryPayInfo(payModifyBean);
	}

	/**
	 * 查询合同采购设备信息
	 * 
	 * @param payModifyBean
	 * @return
	 */
	public List<PayModifyBean> queryDevicesById(PayModifyBean payModifyBean) {
		CommonLogger.info("查询付款单号"+payModifyBean.getPayId()+"的合同采购设备付款信息，PayModifyService，queryDevicesById");
		return payModifyDAO.queryDevicesById(payModifyBean);
	}

	/**
	 * 根据合同号查预付款核销信息
	 * 
	 * @param payModifyBean
	 * @return
	 */
	public List<PayModifyBean> queryPayAdvanceCancel(PayModifyBean payModifyBean) {
		CommonLogger.info("查询付款单号"+payModifyBean.getPayId()+"的预付款核销信息，PayModifyService，queryPayAdvanceCancel");
		return payModifyDAO.queryPayAdvanceCancel(payModifyBean);
	}

	/**
	 * 预付款修改保存
	 * 
	 * @param payModifyBean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean payAdvModifySave(PayModifyBean payModifyBean) {
		if("A0".equalsIgnoreCase(payModifyBean.getDataFlag())){
			payModifyBean.setOperMemo("预付款新增录入修改保存待提交");
		}else if("AB".equalsIgnoreCase(payModifyBean.getDataFlag())){
			payModifyBean.setOperMemo("预付款复核退回修改保存待提交");
		}else if("AC".equalsIgnoreCase(payModifyBean.getDataFlag())){
			payModifyBean.setOperMemo("预付款扫描退回修改保存待提交");
		}else if(payModifyBean.getDataFlag().equalsIgnoreCase("AD")){
			payModifyBean.setOperMemo("预付款财务中心退回修改保存待提交");
		}else if("AF".equalsIgnoreCase(payModifyBean.getDataFlag())){
			payModifyBean.setOperMemo("预付款校验文件失败退回修改提交待复核");
		}else if("BC".equalsIgnoreCase(payModifyBean.getDataFlag())){
			payModifyBean.setOperMemo("预付款扫描退回修改提交待复核");
		}
		payModifyBean.setDataFlag("A0");// 状态
		payModifyBean.setDataFlagInvoice("0");// 发票状态
		payModifyBean.setDataFlagPay("0");// 付款状态
		payModifyBean.setInstDutyCode(WebHelp.getLoginUser().getDutyCode());// 责任中心
		payModifyBean.setInstOper(WebHelp.getLoginUser().getUserId());
		CommonLogger.info("预付款修改保存更新付款单号"+payModifyBean.getPayId()+"的预付款信息，PayModifyService，payAdvModifySave");
		int n = payModifyDAO.payAdvModifySaveOrSubimt(payModifyBean);
		
		//添加到付款log表中
		CommonLogger.info("预付款修改保存更新付款单号"+payModifyBean.getPayId()+"的预付款log信息，PayModifyService，payAdvModifySave");
		payModifyDAO.addPayLog(payModifyBean);
		
		// 更新当前合同的冻结金额
		payModifyBean.setDataFlag("20");// 合同状态
		//页面上的freezeTotalAm已经减去原来的了
		payModifyBean.setFreezeTotalAmt(payModifyBean.getInvoiceAmt().add(payModifyBean.getFreezeTotalAmt()));//发票+冻结=冻结总金额
		CommonLogger.info("预付款修改保存更新合同号"+payModifyBean.getCntNum()+"的合同冻结金额，PayModifyService，payAdvModifySave");
		payModifyDAO.updateFreezeTotalAmt(payModifyBean);
		
		return n > 0 ? true : false;
	}

	/**
	 * 预付款修改提交
	 * 
	 * @param payModifyBean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean payAdvModifySubmit(PayModifyBean payModifyBean) {
		if("A0".equalsIgnoreCase(payModifyBean.getDataFlag())){
			payModifyBean.setOperMemo("预付款新增录入，修改后提交至待复核");
		}else if("AB".equalsIgnoreCase(payModifyBean.getDataFlag())){
			payModifyBean.setOperMemo("预付款复核退回，修改后提交至待复核");
		}else if("AC".equalsIgnoreCase(payModifyBean.getDataFlag())){
			payModifyBean.setOperMemo("预付款扫描退回，修改后提交至待复核");
		}else if("AD".equalsIgnoreCase(payModifyBean.getDataFlag())){
			payModifyBean.setOperMemo("预付款财务中心退回，修改后提交至待复核");
		}else if("AF".equalsIgnoreCase(payModifyBean.getDataFlag())){
			payModifyBean.setOperMemo("预付款校验文件失败退回，修改后提交至待复核");
		}else if("BC".equalsIgnoreCase(payModifyBean.getDataFlag())){
			payModifyBean.setOperMemo("预付款扫描退回，修改后提交至待复核");
		}
		payModifyBean.setDataFlag("B0");// 状态
		payModifyBean.setDataFlagInvoice("0");// 发票状态
		payModifyBean.setDataFlagPay("0");// 付款状态
		payModifyBean.setInstDutyCode(WebHelp.getLoginUser().getDutyCode());// 责任中心
		payModifyBean.setInstOper(WebHelp.getLoginUser().getUserId());
		CommonLogger.info("预付款修改提交更新付款单号"+payModifyBean.getPayId()+"的预付款信息，PayModifyService，payAdvModifySubmit");
		int n = payModifyDAO.payAdvModifySaveOrSubimt(payModifyBean);
		
		//添加到付款log表中
		CommonLogger.info("预付款修改提交更新付款单号"+payModifyBean.getPayId()+"的预付款log信息，PayModifyService，payAdvModifySubmit");
		payModifyDAO.addPayLog(payModifyBean);
		
		// 更新当前合同的冻结金额
		payModifyBean.setDataFlag("20");// 合同状态
		//页面上的freezeTotalAm已经减去原来的了
		payModifyBean.setFreezeTotalAmt(payModifyBean.getInvoiceAmt().add(payModifyBean.getFreezeTotalAmt()));//发票+冻结=冻结总金额
		CommonLogger.info("预付款修改提交更新合同号"+payModifyBean.getCntNum()+"的合同冻结金额，PayModifyService，payAdvModifySubmit");
		payModifyDAO.updateFreezeTotalAmt(payModifyBean);
		
		/*//如果是生成订单的合同则在此生成订单
		if (payModifyBean.getIsOrder().equals("0")) {
			orderStartService.orderCreateList(payModifyBean.getCntNum());
		}*/
		//更新为待复核P1
		CommonLogger.info("预付款修改提交添加付款单"+payModifyBean.getPayId()+"的信息到统计表中，PayModifyService，payAdvModifySubmit");
		payCommonService.updateSysWarnPayInfo(payModifyBean.getPayId(),payModifyBean.getInstDutyCode(),"P1");
		
		return n > 0 ? true : false;
	}

	/**
	 * 正常付款修改保存
	 * 
	 * @param payModifyBean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean payModifySave(PayModifyBean payModifyBean) {
		if("A0".equalsIgnoreCase(payModifyBean.getDataFlag())){
			payModifyBean.setOperMemo("付款新增录入修改保存待提交");
		}else if("AB".equalsIgnoreCase(payModifyBean.getDataFlag())){
			payModifyBean.setOperMemo("付款复核退回修改保存待提交");
		}else if("AC".equalsIgnoreCase(payModifyBean.getDataFlag())){
			payModifyBean.setOperMemo("付款扫描退回修改待保存提交");
		}else if("AD".equalsIgnoreCase(payModifyBean.getDataFlag())){
			payModifyBean.setOperMemo("付款财务中心退回修改保存待提交");
		}else if("AF".equalsIgnoreCase(payModifyBean.getDataFlag())){
			payModifyBean.setOperMemo("付款校验文件失败退回修改提交待复核");
		}else if("BC".equalsIgnoreCase(payModifyBean.getDataFlag())){
			payModifyBean.setOperMemo("付款扫描退回修改提交待复核");
		}
		payModifyBean.setDataFlag("A0");// 状态
		payModifyBean.setDataFlagInvoice("0");// 发票状态
		payModifyBean.setDataFlagPay("0");// 付款状态
		payModifyBean.setInstDutyCode(WebHelp.getLoginUser().getDutyCode());// 责任中心
		payModifyBean.setInstOper(WebHelp.getLoginUser().getUserId());
		// 更新正常付款信息到TD_PAY表
		CommonLogger.info("正常付款修改保存更新付款单号"+payModifyBean.getPayId()+"的付款信息，PayModifyService，payModifySave");
		int n = payModifyDAO.updatePayInfo(payModifyBean);
		
		//添加到付款log表中
		CommonLogger.info("正常付款修改保存更新付款单号"+payModifyBean.getPayId()+"的付款log信息，PayModifyService，payModifySave");
		payModifyDAO.addPayLog(payModifyBean);
		
		if((payModifyBean.getPayAmt().add(payModifyBean.getSuspenseAmt())).compareTo(new BigDecimal(0))!=0){//非核销则执行
			// 更新当前合同的冻结金额
			payModifyBean.setDataFlag("20");// 合同状态
			if("1".equals(payModifyBean.getIsCreditNote())){
				//页面上的freezeTotalAm已经减去原来的了
				payModifyBean.setFreezeTotalAmt((payModifyBean.getPayAmt()
						.add(payModifyBean.getSuspenseAmt())).add(payModifyBean.getFreezeTotalAmt()));//付款+暂收+冻结=冻结总金额
				}else{
					//贷项通知单
					payModifyBean.setFreezeTotalAmt(new BigDecimal(0));
				}
			CommonLogger.info("正常付款修改保存更新合同"+payModifyBean.getCntNum()+"的冻结金额，PayModifyService，payModifySave");
			payModifyDAO.updateFreezeTotalAmt(payModifyBean);
		}
		//贷项通知单
		if("0".equals(payModifyBean.getIsCreditNote())){
			CommonLogger.info("贷项通知单需要更新原蓝字发票对应的剩余冲销金额"+payModifyBean.getInvoiceIdBlue()+"，PayAddService，addPaySave");
			payModifyDAO.updateBlueInvoiceAmtLeft(payModifyBean);
		}
		
		// 非空,合同采购设备(正常)发票分配的金额添加到TD_PAY_DEVICE
		if (!Tool.CHECK.isEmpty(payModifyBean.getPayInvAmts())) {
			List<PayModifyBean> list= this.updateDevice(payModifyBean);
			if (!Tool.CHECK.isEmpty(list)) {
				CommonLogger.info("正常付款修改保存更新设备付款信息和更新设备冻结金额（正常），PayModifyService，payModifySave");
				for(PayModifyBean pmBean :list){
					
					//贷项通知单更新原蓝字发票行的剩余金额
					if("0".equals(payModifyBean.getIsCreditNote())){
						CommonLogger.info("贷项通知单需要更新原蓝字发票行对应的剩余冲销金额"+payModifyBean.getInvoiceIdBlue()+"，PayAddService，addPaySave");
						
						//判断原蓝字发票是否存在预付款核销数据
						String existsFlag = payModifyDAO.getExists0(pmBean);
						if("".equals(existsFlag)||null == existsFlag){//不存在
							payModifyDAO.updateBlueDeviceLeft(pmBean);
						}else{//存在
							//恢复原来冲销的情况
							//1.判断不含税的发生额
							PayModifyBean one = payModifyDAO.queryCrNDevOne(pmBean);
							
							BigDecimal a = one.getSubInvoiceAmtBlue();
							BigDecimal b = one.getSubInvoiceAmt().abs();
							BigDecimal c = one.getSubInvoiceAmtLeft();
							
							BigDecimal change = new BigDecimal(0);
							BigDecimal changeAdvance = new BigDecimal(0);
							
							if(b.add(c).compareTo(a)<=0){
								change = b;
							}else{
								change = a.subtract(c);
								changeAdvance = b.add(c).subtract(a);
							}
							//2.判断税额的发生额
							BigDecimal aTax = one.getAddTaxAmtBlue();
							BigDecimal bTax = one.getAddTaxAmt().abs();
							BigDecimal cTax = one.getAddTaxAmtLeft();
							
							BigDecimal changeTax = new BigDecimal(0);
							BigDecimal changeAdvanceTax = new BigDecimal(0);
							
							if(bTax.add(cTax).compareTo(aTax)<=0){
								changeTax = bTax;
							}else{
								changeTax = aTax.subtract(cTax);
								changeAdvanceTax = bTax.add(cTax).subtract(aTax);
							}
					        
							pmBean.setChange(change);
							pmBean.setChangeAdvance(changeAdvance);
							pmBean.setChangeTax(changeTax);
							pmBean.setChangeAdvanceTax(changeAdvanceTax);
							
							//1.补回正常付款
							payModifyDAO.refreshBlueDeviceLeft(pmBean);
							//2.补回预付款核销
							payModifyDAO.refreshBlueAdvanceDeviceLeft(pmBean);
							//3.按新增的逻辑冲销
							//3.1.查找能对预付款核销的记录冲销的最大值不含税及税 min(预付款核销剩余可冲销数，本次冲销数)
							PayAddBean pbAddBean = new PayAddBean();
							pbAddBean.setPayIdBlue(pmBean.getPayIdBlue());
							pbAddBean.setSubId(pmBean.getSubId());
							pbAddBean.setSubInvoiceAmt(pmBean.getSubInvoiceAmt());
							pbAddBean.setAddTaxAmt(pmBean.getAddTaxAmt());
							
							PayAddBean aCUpdateBean = payAddDAO.getAdvanceUpdateLeftAmt(pbAddBean);
							//3.2.更新预付款核销的可冲销金额
							payAddDAO.updateBlueACDeviceLeft(aCUpdateBean);
							//3.3.更新正常付款的可冲销金额 （本次冲销额-预付款核销已冲销额）
							PayAddBean nUpdateBean = pbAddBean; //subInvoiceAmt addTaxAmt
							nUpdateBean.setSubInvoiceAmt(pbAddBean.getSubInvoiceAmt().add(aCUpdateBean.getAcUpdInvoiceAmtLeft()));
							nUpdateBean.setAddTaxAmt(pbAddBean.getAddTaxAmt().add(aCUpdateBean.getAcUpdTaxAmtLeft()));
							payAddDAO.updateBlueDeviceLeft(nUpdateBean);
						}
						
					}
					
					// 更新设备付款信息 to TD_PAY_DEVICE
					payModifyDAO.updatePayDevice(pmBean);
					//更新设备冻结金额
					payModifyDAO.updateDevFreezeAmt(pmBean);
				}
			}
		}
		
		// 预付款核销信息更新
		if (!Tool.CHECK.isEmpty(payModifyBean.getCancelAmts())) {
			List<PayModifyBean> list = this.payAdvCancelList(payModifyBean);
			if (!Tool.CHECK.isEmpty(list)) {
				CommonLogger.info("正常付款修改保存更新预付款核销信息（付款单号："+payModifyBean.getNormalPayId()+"预付款单号："+payModifyBean.getAdvancePayId()+"），PayModifyService，payModifySave");
				for (PayModifyBean pmBean : list) {
					//预付款核销
					payModifyDAO.updatePayAdvCancel(pmBean);
				}
			}
		}
		
		// 非空,合同采购设备(预付款)发票分配的金额添加到TD_PAY_DEVICE
		if (!Tool.CHECK.isEmpty(payModifyBean.getAdvancePayInvAmts())) {
			List<PayModifyBean> list = this.updateAdvDevice(payModifyBean);
			if (!Tool.CHECK.isEmpty(list)) {
				CommonLogger.info("正常付款修改保存更新设备付款信息和更新设备冻结金额（预付款），PayModifyService，payModifySave");
				for(PayModifyBean pmBean :list){
					// 更新设备付款信息 to TD_PAY_DEVICE
					payModifyDAO.updatePayDevice(pmBean);
					//更新设备冻结金额
					payModifyDAO.updateDevFreezeAmt(pmBean);
				}
			}
		}
		return n > 0 ? true : false;
	}

	/**
	 * 正常付款修改提交
	 * 
	 * @param payModifyBean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean payModifySubmit(PayModifyBean payModifyBean) {
		if("A0".equalsIgnoreCase(payModifyBean.getDataFlag())){
			payModifyBean.setOperMemo("付款新增录入修改提交待复核");
		}else if("AB".equalsIgnoreCase(payModifyBean.getDataFlag())){
			payModifyBean.setOperMemo("付款复核退回修改提交待复核");
		}else if("AC".equalsIgnoreCase(payModifyBean.getDataFlag())){
			payModifyBean.setOperMemo("付款扫描退回修改提交待复核");
		}else if("AD".equalsIgnoreCase(payModifyBean.getDataFlag())){
			payModifyBean.setOperMemo("付款财务中心退回修改提交待复核");
		}else if("AF".equalsIgnoreCase(payModifyBean.getDataFlag())){
			payModifyBean.setOperMemo("付款校验文件失败退回修改提交待复核");
		}else if("BC".equalsIgnoreCase(payModifyBean.getDataFlag())){
			payModifyBean.setOperMemo("付款扫描退回修改提交待复核");
		}
		payModifyBean.setDataFlag("B0");// 状态
		payModifyBean.setDataFlagInvoice("0");// 发票状态
		payModifyBean.setDataFlagPay("0");// 付款状态
		payModifyBean.setInstDutyCode(WebHelp.getLoginUser().getDutyCode());// 责任中心
		payModifyBean.setInstOper(WebHelp.getLoginUser().getUserId());
		// 更新正常付款信息到TD_PAY表
		CommonLogger.info("正常付款修改提交更新付款单号"+payModifyBean.getPayId()+"的付款信息，PayModifyService，payModifySubmit");
		int n = payModifyDAO.updatePayInfo(payModifyBean);
		
		//添加到付款log表中
		CommonLogger.info("正常付款修改提交更新付款单号"+payModifyBean.getPayId()+"的付款log信息，PayModifyService，payModifySubmit");
		payModifyDAO.addPayLog(payModifyBean);
		
		if((payModifyBean.getPayAmt().add(payModifyBean.getSuspenseAmt())).compareTo(new BigDecimal(0))!=0){//非核销则执行
			// 更新当前合同的冻结金额
			payModifyBean.setDataFlag("20");// 合同状态
			if("1".equals(payModifyBean.getIsCreditNote())){
				//页面上的freezeTotalAm已经减去原来的了
				payModifyBean.setFreezeTotalAmt((payModifyBean.getPayAmt()
						.add(payModifyBean.getSuspenseAmt())).add(payModifyBean.getFreezeTotalAmt()));//付款+暂收+冻结=冻结总金额
				}else{
					//贷项通知单
					payModifyBean.setFreezeTotalAmt(new BigDecimal(0));
				}
			CommonLogger.info("正常付款修改提交更新合同"+payModifyBean.getCntNum()+"的冻结金额，PayModifyService，payModifySubmit");
			payModifyDAO.updateFreezeTotalAmt(payModifyBean);
		}
		
		//贷项通知单
		if("0".equals(payModifyBean.getIsCreditNote())){
			CommonLogger.info("贷项通知单需要更新原蓝字发票对应的剩余冲销金额"+payModifyBean.getInvoiceIdBlue()+"，PayAddService，addPaySave");
			payModifyDAO.updateBlueInvoiceAmtLeft(payModifyBean);
		}
		
		// 非空,合同采购设备(正常)发票分配的金额添加到TD_PAY_DEVICE
		if (!Tool.CHECK.isEmpty(payModifyBean.getPayInvAmts())) {
			List<PayModifyBean> list= this.updateDevice(payModifyBean);
			if (!Tool.CHECK.isEmpty(list)) {
				CommonLogger.info("正常付款修改提交更新设备付款信息和更新设备冻结金额（正常），PayModifyService，payModifySubmit");
				for(PayModifyBean pmBean : list){
					
					//贷项通知单更新原蓝字发票行的剩余金额
					if("0".equals(payModifyBean.getIsCreditNote())){
						CommonLogger.info("贷项通知单需要更新原蓝字发票行对应的剩余冲销金额"+payModifyBean.getInvoiceIdBlue()+"，PayAddService，addPaySave");
						
						//判断原蓝字发票是否存在预付款核销数据
						String existsFlag = payModifyDAO.getExists0(pmBean);
						if("".equals(existsFlag)||null == existsFlag){//不存在
							payModifyDAO.updateBlueDeviceLeft(pmBean);
						}else{//存在
							//恢复原来冲销的情况
							//1.判断不含税的发生额
							PayModifyBean one = payModifyDAO.queryCrNDevOne(pmBean);
							
							BigDecimal a = one.getSubInvoiceAmtBlue();
							BigDecimal b = one.getSubInvoiceAmt().abs();
							BigDecimal c = one.getSubInvoiceAmtLeft();
							
							BigDecimal change = new BigDecimal(0);
							BigDecimal changeAdvance = new BigDecimal(0);
							
							if(b.add(c).compareTo(a)<=0){
								change = b;
							}else{
								change = a.subtract(c);
								changeAdvance = b.add(c).subtract(a);
							}
							//2.判断税额的发生额
							BigDecimal aTax = one.getAddTaxAmtBlue();
							BigDecimal bTax = one.getAddTaxAmt().abs();
							BigDecimal cTax = one.getAddTaxAmtLeft();
							
							BigDecimal changeTax = new BigDecimal(0);
							BigDecimal changeAdvanceTax = new BigDecimal(0);
							
							if(bTax.add(cTax).compareTo(aTax)<=0){
								changeTax = bTax;
							}else{
								changeTax = aTax.subtract(cTax);
								changeAdvanceTax = bTax.add(cTax).subtract(aTax);
							}
					        
							pmBean.setChange(change);
							pmBean.setChangeAdvance(changeAdvance);
							pmBean.setChangeTax(changeTax);
							pmBean.setChangeAdvanceTax(changeAdvanceTax);
							
							//1.补回正常付款
							payModifyDAO.refreshBlueDeviceLeft(pmBean);
							//2.补回预付款核销
							payModifyDAO.refreshBlueAdvanceDeviceLeft(pmBean);
							//3.按新增的逻辑冲销
							//3.1.查找能对预付款核销的记录冲销的最大值不含税及税 min(预付款核销剩余可冲销数，本次冲销数)
							PayAddBean pbAddBean = new PayAddBean();
							pbAddBean.setPayIdBlue(pmBean.getPayIdBlue());
							pbAddBean.setSubId(pmBean.getSubId());
							pbAddBean.setSubInvoiceAmt(pmBean.getSubInvoiceAmt());
							pbAddBean.setAddTaxAmt(pmBean.getAddTaxAmt());
							
							PayAddBean aCUpdateBean = payAddDAO.getAdvanceUpdateLeftAmt(pbAddBean);
							//3.2.更新预付款核销的可冲销金额
							payAddDAO.updateBlueACDeviceLeft(aCUpdateBean);
							//3.3.更新正常付款的可冲销金额 （本次冲销额-预付款核销已冲销额）
							PayAddBean nUpdateBean = pbAddBean; //subInvoiceAmt addTaxAmt
							nUpdateBean.setSubInvoiceAmt(pbAddBean.getSubInvoiceAmt().add(aCUpdateBean.getAcUpdInvoiceAmtLeft()));
							nUpdateBean.setAddTaxAmt(pbAddBean.getAddTaxAmt().add(aCUpdateBean.getAcUpdTaxAmtLeft()));
							payAddDAO.updateBlueDeviceLeft(nUpdateBean);
							
						}
						
					}
					// 更新设备付款信息 to TD_PAY_DEVICE
					payModifyDAO.updatePayDevice(pmBean);
					//更新设备冻结金额
					payModifyDAO.updateDevFreezeAmt(pmBean);
				}
			}
		}
		
		// 预付款核销信息添加
		if (!Tool.CHECK.isEmpty(payModifyBean.getCancelAmts())) {
			List<PayModifyBean> list = this.payAdvCancelList(payModifyBean);
			if (!Tool.CHECK.isEmpty(list)) {
				CommonLogger.info("正常付款修改提交更新预付款核销信息（付款单号："+payModifyBean.getNormalPayId()+"预付款单号："+payModifyBean.getAdvancePayId()+"），PayModifyService，payModifySubmit");
				for (PayModifyBean pbAddBean : list) {
					payModifyDAO.updatePayAdvCancel(pbAddBean);
				}
			}
		}
		
		// 非空,合同采购设备(预付款)发票分配的金额添加到TD_PAY_DEVICE
		if (!Tool.CHECK.isEmpty(payModifyBean.getAdvancePayInvAmts())) {
			List<PayModifyBean> list= this.updateAdvDevice(payModifyBean);
			if (!Tool.CHECK.isEmpty(list)) {
				CommonLogger.info("正常付款修改提交更新设备付款信息和更新设备冻结金额（预付款），PayModifyService，payModifySubmit");
				for(PayModifyBean pmBean : list){
					// 添加设备付款信息 to TD_PAY_DEVICE
					payModifyDAO.updatePayDevice(pmBean);
					//更新设备冻结金额
					payModifyDAO.updateDevFreezeAmt(pmBean);
				}
			}
		}
		/*//如果是生成订单的合同则在此生成订单
		if (payModifyBean.getIsOrder().equals("0")) {
			orderStartService.orderCreateList(payModifyBean.getCntNum());
		}*/
		CommonLogger.info("正常付款修改提交添加付款单"+payModifyBean.getPayId()+"的信息到统计表中，PayModifyService，payModifySubmit");
		payCommonService.updateSysWarnPayInfo(payModifyBean.getPayId(),payModifyBean.getInstDutyCode(),"P1");
		return n > 0 ? true : false;
	}

	// 预付款核销信息转List处理
	private List<PayModifyBean> payAdvCancelList(PayModifyBean payModifyBean) {
		String invoiceId = payModifyBean.getInvoiceId();// 发票号
		String normalPayId = payModifyBean.getPayId();// 正常付款单号
		String[] advancePayIds = payModifyBean.getAdvancePayIds();// 预付款单号（批次号）
		BigDecimal[] cancelAmts = payModifyBean.getCancelAmts();// 本次核销金额
		List<PayModifyBean> list = new ArrayList<PayModifyBean>();
		for (int i = 0; i < cancelAmts.length; i++) {
			if(!Tool.CHECK.isEmpty(cancelAmts[i])){
				PayModifyBean pbBean = new PayModifyBean();
				pbBean.setInvoiceId(invoiceId);
				pbBean.setNormalPayId(normalPayId);
				pbBean.setAdvancePayId(advancePayIds[i]);
				pbBean.setCancelAmt(cancelAmts[i]);
				list.add(pbBean);
			}
		}
		return list;
	}

	// 合同采购设备(预付款)转list处理
	private List<PayModifyBean> updateAdvDevice(PayModifyBean payModifyBean) {
		String invoiceId = payModifyBean.getInvoiceId();// 发票号
		String payId = payModifyBean.getPayId();// 单号
		String cntNum = payModifyBean.getCntNum();// 合同号
		BigDecimal[] advSubIds = payModifyBean.getAdvSubIds();// 子序列
		BigDecimal[] advancePayInvAmts = payModifyBean.getAdvancePayInvAmts();// 分配的金额
		BigDecimal[] advPayAddTaxAmts = payModifyBean.getAdvPayAddTaxAmts();// 增值税金额
		BigDecimal[] freezeAmtAdvBefores = payModifyBean.getFreezeAmtAdvBefores();
		BigDecimal[] freezeTaxAmtAdvBefores = payModifyBean.getFreezeTaxAmtAdvBefores();
		String[] deductAdvFlags = payModifyBean.getDeductAdvFlags();//是否可以抵扣
		String[] advancePayIvrowMemos = payModifyBean.getAdvancePayIvrowMemos();//发票行说明
		List<PayModifyBean> list = new ArrayList<PayModifyBean>();
		for (int i = 0; i < advancePayInvAmts.length; i++) {
			PayModifyBean pbBean = new PayModifyBean();
			pbBean.setInvoiceId(invoiceId);
			pbBean.setPayId(payId);
			pbBean.setPayType("0");
			pbBean.setCntNum(cntNum);
			pbBean.setSubId(advSubIds[i]);
			pbBean.setSubInvoiceAmt(advancePayInvAmts[i]);// 分配发票金额
			pbBean.setAddTaxAmt(advPayAddTaxAmts[i]);// 分配发票金额
			pbBean.setFreezeAmt(advancePayInvAmts[i].subtract(freezeAmtAdvBefores[i]));//冻结不含税金额
			pbBean.setFreezeAmtTax(advPayAddTaxAmts[i].subtract(freezeTaxAmtAdvBefores[i]));//冻结税额
			pbBean.setFreezeAmtBefore(freezeAmtAdvBefores[i]);
			pbBean.setDeductFlag(deductAdvFlags[i]);//是否可抵扣
			//pbBean.setIvrowMemo(advancePayIvrowMemos[i]);
			if(advancePayIvrowMemos.length == 0){
				pbBean.setIvrowMemo(null);
			}else{
				pbBean.setIvrowMemo(advancePayIvrowMemos[i]);
			}
			list.add(pbBean);
		}
		return list;
	}

	// 合同采购设备(正常)转map处理
	private List<PayModifyBean> updateDevice(PayModifyBean payModifyBean) {
		String invoiceId = payModifyBean.getInvoiceId();// 发票号
		String invoiceIdBlue = payModifyBean.getInvoiceIdBlue();//原蓝字发票编号
		String payIdBlue = payModifyBean.getPayIdBlue();//原蓝字付款单号
		String payId = payModifyBean.getPayId();// 单号
		String cntNum = payModifyBean.getCntNum();// 合同号
		BigDecimal[] subIds = payModifyBean.getSubIds();// 子序列
		BigDecimal[] payInvAmts = payModifyBean.getPayInvAmts();// 不含税金额
		BigDecimal[] payAddTaxAmts = payModifyBean.getPayAddTaxAmts();// 税额
		BigDecimal[] freezeAmtBefores = payModifyBean.getFreezeAmtBefores();
		BigDecimal[] freezeTaxAmtBefores = payModifyBean.getFreezeTaxAmtBefores();
		String[] deductFlags = payModifyBean.getDeductFlags();//是否可以抵扣
		String[] payIvrowMemos = payModifyBean.getPayIvrowMemos();//发票行说明
	    BigDecimal[] payInvAmtOlds = payModifyBean.getPayInvAmtOlds();//
		BigDecimal[] payAddTaxAmtOlds = payModifyBean.getPayAddTaxAmtOlds();
		List<PayModifyBean> list = new ArrayList<PayModifyBean>();
		for (int i = 0; i < payInvAmts.length; i++) {
			PayModifyBean pbBean = new PayModifyBean();
			pbBean.setInvoiceId(invoiceId);
			pbBean.setInvoiceIdBlue(invoiceIdBlue);
			pbBean.setPayIdBlue(payIdBlue);
			pbBean.setPayId(payId);
			pbBean.setPayType("1");
			pbBean.setCntNum(cntNum);
			pbBean.setSubId(subIds[i]);
			pbBean.setSubInvoiceAmt(payInvAmts[i]);//分配不含税金额
			pbBean.setAddTaxAmt(payAddTaxAmts[i]);// 分配税额
			pbBean.setFreezeAmtTax(payAddTaxAmts[i].subtract(freezeTaxAmtBefores[i]));//冻结税额
			pbBean.setFreezeAmt(payInvAmts[i].subtract(freezeAmtBefores[i]));//冻结不含税金额
			pbBean.setFreezeAmtBefore(freezeAmtBefores[i]);
			pbBean.setDeductFlag(deductFlags[i]);//是否可抵扣
			pbBean.setSubInvoiceAmtOld(payInvAmtOlds[i]);
			pbBean.setAddTaxAmtOld(payAddTaxAmtOlds[i]);
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
	 * 预付款信息删除
	 * 
	 * @param payAddBean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean payAdvDelete(PayModifyBean payModifyBean) {
		payModifyBean.setTable("TD_PAY_ADVANCE");
		PayModifyBean pModifyBean = this.queryPayInfo(payModifyBean);
		pModifyBean.setFreezeAmt(pModifyBean.getPayAmt());
		//删除预付款信息
		CommonLogger.info("删除预付款单号"+payModifyBean.getPayId()+"付款信息，PayModifyService，payAdvDelete");
		int n = payModifyDAO.payAdvDelete(payModifyBean);
		if(n>0){
			//更新合同冻结金额
			payModifyBean.setDataFlag("20");// 合同状态
			CommonLogger.info("删除预付款单号"+payModifyBean.getPayId()+"付款信息时更新合同"+payModifyBean.getCntNum()+"的冻结金额，PayModifyService，payAdvDelete");
			payModifyDAO.deleteCntFreezaAmt(pModifyBean);
			
			//删除统计数据，重新统计
			CommonLogger.info("预付款信息删除（付款单号："+payModifyBean.getPayId()+"），删除统计数据重新统计，PayModifyService，payAdvDelete");
			payCommonService.delSysWarnPayInfo(payModifyBean.getPayId(), WebHelp.getLoginUser().getDutyCode(), "P0");
		}
		return n>0?true:false;
	}

	/**
	 * 正常付款信息删除
	 * 
	 * @param payAddBean
	 * @return
	 * @throws Exception 
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean payDelete(PayModifyBean payModifyBean) throws Exception {
		payModifyBean.setTable("TD_PAY");
		PayModifyBean pModifyBean = this.queryPayInfo(payModifyBean);
		
		//正常付款
	    pModifyBean.setFreezeAmt(pModifyBean.getPayAmt().add(pModifyBean.getSuspenseAmt()));

		//删除付款信息
		CommonLogger.info("删除付款单号"+payModifyBean.getPayId()+"付款信息，PayModifyService，payDelete");
		int n = payModifyDAO.payDelete(payModifyBean);
		if(n>0){
			if("1".equals(pModifyBean.getIsCreditNote())){
			//更新合同冻结金额
			CommonLogger.info("删除付款单号"+payModifyBean.getPayId()+"付款信息时更新合同"+payModifyBean.getCntNum()+"的冻结金额，PayModifyService，payDelete");
			payModifyDAO.deleteCntFreezaAmt(pModifyBean);
			}
			
			//2.更新设备冻结金额
			CommonLogger.info("删除付款单号"+payModifyBean.getPayId()+"付款信息时更新设备冻结金额，PayModifyService，payDelete");
			payModifyDAO.deleteDevFreezeAmt(payModifyBean);
			
			
			//更新剩余可冲销金额(若为贷项通知单)
			if("0".equals(payModifyBean.getIsCreditNote())){
				CommonLogger.info("删除付款单号"+payModifyBean.getPayId()+"。原蓝字发票编号："+payModifyBean.getInvoiceIdBlue()+"付款信息时更新原蓝字发票可冲销金额，PayModifyService，payDelete");
				payModifyDAO.refreshBlueInvoiceAmtLeft(payModifyBean);
				
				payModifyBean.setPayType("1");
				List<PayModifyBean> creditDevList = payModifyDAO.queryCreditNDevicesById(payModifyBean);
				for(PayModifyBean bean:creditDevList){
					bean.setPayIdBlue(payModifyBean.getPayIdBlue());
					bean.setPayId(payModifyBean.getPayId());
					CommonLogger.info("删除付款单号"+bean.getPayId()+"。原蓝字发票编号："+bean.getInvoiceIdBlue()+"付款信息时更新原蓝字发票设备可冲销金额，PayModifyService，payDelete");
					//判断原蓝字发票是否存在预付款核销数据
					String existsFlag = payModifyDAO.getExists0(bean);
					if("".equals(existsFlag)||null == existsFlag){//不存在
						bean.setChange(bean.getSubInvoiceAmt().abs());
						bean.setChangeTax(bean.getAddTaxAmt().abs());
						//1.补回正常付款
						payModifyDAO.refreshBlueDeviceLeft(bean);
					}else{//存在
						//恢复原来冲销的情况
						//1.判断不含税的发生额
						BigDecimal a = bean.getSubInvoiceAmtBlue();
						BigDecimal b = bean.getSubInvoiceAmt().abs();
						BigDecimal c = bean.getSubInvoiceAmtLeft();
						
						BigDecimal change = new BigDecimal(0);
						BigDecimal changeAdvance = new BigDecimal(0);
						
						if(b.add(c).compareTo(a)<=0){
							change = b;
						}else{
							change = a.subtract(c);
							changeAdvance = b.add(c).subtract(a);
						}
						//2.判断税额的发生额
						BigDecimal aTax = bean.getAddTaxAmtBlue();
						BigDecimal bTax = bean.getAddTaxAmt().abs();
						BigDecimal cTax = bean.getAddTaxAmtLeft();
						
						BigDecimal changeTax = new BigDecimal(0);
						BigDecimal changeAdvanceTax = new BigDecimal(0);
						
						if(bTax.add(cTax).compareTo(aTax)<=0){
							changeTax = bTax;
						}else{
							changeTax = aTax.subtract(cTax);
							changeAdvanceTax = bTax.add(cTax).subtract(aTax);
						}
				        
						bean.setChange(change);
						bean.setChangeAdvance(changeAdvance);
						bean.setChangeTax(changeTax);
						bean.setChangeAdvanceTax(changeAdvanceTax);
						
						//1.补回正常付款
						payModifyDAO.refreshBlueDeviceLeft(bean);
						//2.补回预付款核销
						payModifyDAO.refreshBlueAdvanceDeviceLeft(bean);
				      }
			    }
			}
			//3.根据付款单删除付款信息（TD_PAY、TD_PAY_DEVICE、TD_PAY_ADVANCE_CANCEL）
			CommonLogger.info("删除付款单号"+payModifyBean.getPayId()+"付款信息时删除该付款下的设备付款信息，PayModifyService，payDelete");
			payModifyDAO.deletePayDevice(payModifyBean);
			CommonLogger.info("删除付款单号"+payModifyBean.getPayId()+"付款信息时删除该付款下的预付款核销信息，PayModifyService，payDelete");
			payModifyDAO.deletePayAdvCancel(payModifyBean);
			
			//删除统计数据，重新统计
			CommonLogger.info("正常付款信息删除（付款单号："+payModifyBean.getPayId()+"），删除统计数据重新统计，PayModifyService，payDelete");
			payCommonService.delSysWarnPayInfo(payModifyBean.getPayId(), WebHelp.getLoginUser().getDutyCode(), "P0");
		}
		return n>0?true:false;
	}

	/**
	 * 删除预算冻结失败的临时信息
	 * @param payId
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteBgtFrozenTemp(String payId) {
		CommonLogger.info("删除付款单号"+payId+"预算冻结失败的临时信息，PayModifyService，deleteBgtFrozenTemp");
		return payModifyDAO.deleteBgtFrozenTemp(payId)>0 ? true:false;
	}

	/**
	 * 查询贷项通知单发票行设备列表
	 * 
	 * @param payModifyBean
	 * @return
	 */
	public List<PayModifyBean> queryCreditDevicesById(PayModifyBean payModifyBean) {
		CommonLogger.info("查询付款单号"+payModifyBean.getPayId()+"的贷项通知单发票行设备列表，PayModifyService，queryCreditDevicesById");
		return payModifyDAO.queryCreditDevicesById(payModifyBean);
	}
}
