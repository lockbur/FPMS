package com.forms.prms.web.pay.payAdd.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.pay.payAdd.domain.PayAddBean;
import com.forms.prms.web.pay.payAdd.domain.PayAddBgtBean;

/**
 * author : lisj <br>
 * date : 2015-01-26<br>
 * 合同付款新增DAO
 */
@Repository
public interface PayAddDAO {

	/**
	 * 合同待付款信息列表查询
	 * 
	 * @param payAddBean
	 * @return
	 */
	public List<PayAddBean> payList(PayAddBean payAddBean);

	/**
	 * 根据合同号查询合同信息
	 * 
	 * @param payAddBean
	 * @return
	 */
	public PayAddBean constractInfo(PayAddBean payAddBean);

	/**
	 * 根据合同号查询正常付款信息
	 * 
	 * @param payAddBean
	 * @return
	 */
	public List<PayAddBean> queryPayList(PayAddBean payAddBean);

	/**
	 * 根据合同号查询预付款信息
	 * 
	 * @param payAddBean
	 * @return
	 */
	public List<PayAddBean> queryPayAdvanceList(PayAddBean payAddBean);

	/**
	 * 预付款新增保存或提交
	 * 
	 * @param payAddBean
	 * @return
	 */
	public int addPayAdvanceSaveOrSubimt(PayAddBean payAddBean);

	/**
	 * 生成付款单号
	 * 
	 * @param payAddBean
	 * @return
	 */
	public String createPayId(PayAddBean payAddBean);

	/**
	 * 根据合同号查预付款核销信息
	 * 
	 * @param payAddBean
	 * @return
	 */
	public List<PayAddBean> queryPayAdvanceCancel(PayAddBean payAddBean);

	/**
	 * 查询合同采购设备信息
	 * 
	 * @param payAddBean
	 * @return
	 */
	public List<PayAddBean> queryDevicesById(PayAddBean payAddBean);

	/**
	 * 添加正常付款信息到TD_PAY表
	 * 
	 * @param payAddBean
	 * @return
	 */
	public int addPayInfo(PayAddBean payAddBean);

	/**
	 * 添加设备付款信息 to TD_PAY_DEVICE
	 * 
	 * @param map
	 */
	public void addPayDevice(PayAddBean pbAddBean);

	/**
	 * 预付款核销信息添加
	 * 
	 * @param map
	 */
	public void addPayAdvCancel(PayAddBean pbAddBean);

	/**
	 * 更新当前合同的冻结金额
	 * 
	 * @param payAddBean
	 */
	public void updateFreezeTotalAmt(PayAddBean payAddBean);

	/**
	 * 根据预付款单号查预付款核销明细
	 * 
	 * @param payAddBean
	 * @return
	 */
	public PayAddBean queryPayAdvCancelDetail(PayAddBean payAddBean);

	/**
	 * 查询付款信息（正常或预付款）
	 * 
	 * @param payAddBean
	 * @return
	 */
	public PayAddBean queryPayInfo(PayAddBean payAddBean);

	/**
	 * 正常付款暂收结清处理信息提交
	 * 
	 * @param payAddBean
	 * @return
	 */
	public int paySuspenseDealSubmit(PayAddBean payAddBean);

	/**
	 * 查询已结清列表
	 * 
	 * @param payAddBean
	 * @return
	 */
	public List<PayAddBean> queryPayCleanInfo(PayAddBean payAddBean);

	/**
	 * 已结清明细查询
	 * 
	 * @param payAddBean
	 * @return
	 */
	public PayAddBean querySuspenseDetail(PayAddBean payAddBean);

	/**
	 * 更新设备冻结金额
	 * 
	 * @param map
	 */
	public void updateDevFreezeAmt(PayAddBean pbAddBean);

	/**
	 * 查询预付款或正常付款合同采购设备信息（明细）
	 * 
	 * @param payAddBean
	 * @return
	 */
	public List<PayAddBean> queryDeviceDetailsById(PayAddBean payAddBean);

	/**
	 * 查询正在结清的总金额
	 * 
	 * @param payAddBean
	 * @return
	 */
	public String queryCleanAmtIng(PayAddBean payAddBean);

	/**
	 * 查询附件类型对应的值(从SYS_SELECT表中)
	 * 
	 * @return
	 */
	public List<PayAddBean> queryAtType();

	/**
	 * 正常付款打印
	 * 
	 * @param payId
	 * @return
	 */

	public PayAddBean getPayReportData(@Param(value = "payId") String payId);

	/**
	 * 预付款打印
	 * 
	 * @param payId
	 * @return
	 */
	public PayAddBean getAdvPayReportData(@Param(value = "payId") String payId);

	/**
	 * 生成发票号
	 * 
	 * @param payAddBean
	 * @return
	 */
	public String createInvoiceId(PayAddBean payAddBean);

	/**
	 * 添加到付款log表中
	 * @param payAddBean
	 */
	public void addPayLog(PayAddBean payAddBean);

	/**
	 * 查询审批历史记录
	 * @param payId
	 * @return
	 */
	public List<PayAddBean> queryHis(@Param("payId")String payId);

	/**
	 * 根据合同号查询TI_TRADE_BACKWASH是否有数据
	 * @param cntNum
	 * @return
	 */
	public String getIdByCntNum(@Param("cntNum")String cntNum);

	/**
	 * 预算冻结的ajax校验
	 * @param payId
	 * @return
	 */
	public String checkBgtFrozen(Map<String, String> param);

	/**
	 * 查询付款冻结失败的信息
	 * @param payId
	 * @return
	 */
	public List<PayAddBgtBean> queryBgtFrozenFailMsg(String payId);

	/**
	 * 更新付款单的状态
	 * @param payAddBean
	 * @return
	 */
	public int changePayStatus(PayAddBean payAddBean);

	public PayAddBean checkBgtOverdraw(PayAddBean pBean);
	
	/**
	 * 校验是否可新增扫描批次
	 * @param userId
	 * @return
	 */
	public String ajaxCheckCanAddScanBatch(@Param("userId")String userId);

	public List<PayAddBean> getCancelData(@Param("payId")String payId);

	public List<PayAddBean> queryCancelDevices(@Param("payId")String payId);

	public List<PayAddBean> getAdvCancelPayIds(@Param("payId")String payId);

	public String ajaxCheckInvoiceId(@Param("invoiceId")String invoiceId,@Param("payId")String payId, @Param("tableName")String tableName,@Param("modifyFlag")String modifyFlag);
	
	public String checkOuprovider(@Param("ouCode")String ouCode, @Param("providerCode")String providerCode,
			@Param("providerAddrCode")String providerAddrCode);
	
	/**
	 * 查看可冲销的蓝字发票列表
	 */
	public List<PayAddBean> getBlueInvoiceList(PayAddBean payAddBean);
	
	/**
	 * 查看原蓝字发票对应的物料行信息
	 * @param invoiceIdBlue
	 * @return
	 */
	public List<PayAddBean> queryInvoiceBlueDevice(@Param("invoiceIdBlue")String invoiceIdBlue);
	
	/**
	 * 查看原蓝字发票头的信息
	 * @param invoiceIdBlue
	 * @return
	 */
	public PayAddBean queryInvoiceBlue(@Param("invoiceIdBlue")String invoiceIdBlue);
	
	/**
	 * 贷项通知单更新原蓝字发票剩余可冲销的余额
	 * @param pBean
	 * @return
	 */
	public int updateBlueInvoiceAmtLeft(PayAddBean pBean);
	
	/**
	 * 贷项通知单更新原蓝字发票发票行剩余可冲销的余额
	 * @param pbAddBean
	 */
	public void updateBlueDeviceLeft (PayAddBean pbAddBean);

	/**
	 * 查看原蓝字发票下的贷项通知单列表
	 * @param invoiceIdBlue
	 * @return
	 */
	public List<PayAddBean> getCreditListByInvoiceidBlue(PayAddBean pbAddBean);
	
	/**
	 * 查看是否存在预付款核销
	 * @param pbAddBean
	 * @return
	 */
	public String getExists0(PayAddBean pbAddBean);
	
	/**
	 * 获取预付款核销冲销的金额
	 * @param pbAddBean
	 * @return
	 */
	public PayAddBean getAdvanceUpdateLeftAmt(PayAddBean pbAddBean);
	
	/**
	 * 贷项通知单--预付款核销更新原蓝字发票发票行剩余可冲销的余额
	 * @param pbAddBean
	 */
	public void updateBlueACDeviceLeft(PayAddBean pbAddBean);

	/**
	 * 获取某付款下各核算码对应的不含税金额及税额
	 * @param payId
	 * @return
	 */
	public List<PayAddBean> getCglCodeAmt(String payId);
}
