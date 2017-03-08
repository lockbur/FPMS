package com.forms.prms.web.pay.paymodify.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.pay.paymodify.domain.PayModifyBean;

/**
 * author : lisj <br>
 * date : 2015-02-02<br>
 * 合同付款修改DAO
 */
@Repository
public interface PayModifyDAO {

	/**
	 * 付款信息列表查询
	 * 
	 * @param payModifyBean
	 * @return
	 */
	public List<PayModifyBean> list(PayModifyBean payModifyBean);

	/**
	 * 根据合同号查询合同信息
	 * 
	 * @param payModifyBean
	 * @return
	 */
	public PayModifyBean constractInfo(PayModifyBean payModifyBean);

	/**
	 * 查询付款信息（正常或预付款）
	 * 
	 * @param payModifyBean
	 * @return
	 */
	public PayModifyBean queryPayInfo(PayModifyBean payModifyBean);

	/**
	 * 查询合同采购设备信息
	 * 
	 * @param payModifyBean
	 * @return
	 */
	public List<PayModifyBean> queryDevicesById(PayModifyBean payModifyBean);

	/**
	 * 根据合同号查预付款核销信息
	 * 
	 * @param payModifyBean
	 * @return
	 */
	public List<PayModifyBean> queryPayAdvanceCancel(PayModifyBean payModifyBean);

	/**
	 * 预付款修改保存或提交
	 * 
	 * @param payModifyBean
	 * @return
	 */
	public int payAdvModifySaveOrSubimt(PayModifyBean payModifyBean);

	/**
	 * 更新当前合同的冻结金额
	 * 
	 * @param payModifyBean
	 */
	public void updateFreezeTotalAmt(PayModifyBean payModifyBean);

	/**
	 * 更新正常付款信息到TD_PAY表
	 * 
	 * @param payModifyBean
	 * @return
	 */
	public int updatePayInfo(PayModifyBean payModifyBean);

	/**
	 * 更新设备付款信息 to TD_PAY_DEVICE
	 * 
	 * @param map
	 */
	public void updatePayDevice(PayModifyBean payModifyBean);

	/**
	 * 预付款核销信息更新
	 * 
	 * @param pbAddBean
	 */
	public void updatePayAdvCancel(PayModifyBean pbAddBean);

	/**
	 * 删除预付款信息
	 * 
	 * @param payModifyBean
	 * @return
	 */
	public int payAdvDelete(PayModifyBean payModifyBean);

	/**
	 * 删除付款信息
	 * 
	 * @param payModifyBean
	 * @return
	 */
	public int payDelete(PayModifyBean payModifyBean);

	/**
	 * 删除设备付款信息
	 * 
	 * @param map
	 */
	public void deletePayDevice(PayModifyBean payModifyBean);

	/**
	 * 预付款核销信息删除
	 * 
	 * @param pbAddBean
	 */
	public void deletePayAdvCancel(PayModifyBean payModifyBean);

	/**
	 * 更新设备冻结金额
	 * 
	 * @param map
	 */
	public void updateDevFreezeAmt(PayModifyBean payModifyBean);

	/**
	 * 添加付款log信息
	 */
	public void addPayLog(PayModifyBean payModifyBean);

	/**
	 * 删除付款信息（更新设备冻结金额）
	 * @param pmBean
	 */
	public void deleteDevFreezeAmt(PayModifyBean pmBean);

	/**
	 * 删除预算冻结失败的临时信息
	 * @param payId
	 * @return
	 */
	public int deleteBgtFrozenTemp(String payId);
	
	
	/**
	 * 订单删除释放预算
	 * @param param
	 * @return
	 */
	public String deletePayFreeBgt(Map<String, String> param);

	public int deleteCntFreezaAmt(PayModifyBean payModifyBean);
	
	/**
	 * 查询贷项通知单发票行设备列表
	 * @param payModifyBean
	 * @return
	 */
	public List<PayModifyBean> queryCreditDevicesById(PayModifyBean payModifyBean);
	
	/**
	 * 查询贷项通知单正常付款信息
	 * @param payModifyBean
	 * @return
	 */
	public List<PayModifyBean> queryCreditNDevicesById(PayModifyBean payModifyBean);
	
	/**
	 * 查询贷项通知单正常付款信息-ONE
	 * @param payModifyBean
	 * @return
	 */
	public PayModifyBean queryCrNDevOne(PayModifyBean payModifyBean);
	
	
	/**
	 * 贷项通知单，删除原信息时需要更新原蓝字发票的可冲销信息
	 * @param payModifyBean
	 */
	public void refreshBlueInvoiceAmtLeft(PayModifyBean payModifyBean);
	
	/**
	 * 贷项通知单，删除原信息时需要更新原蓝字发票物料行的可冲销信息
	 * @param payModifyBean
	 */
	public void refreshBlueDeviceLeft(PayModifyBean payModifyBean);
	
	/**
	 * 贷项通知单，删除原信息时需要更新原蓝字发票预付款核销物料行的可冲销信息
	 * @param payModifyBean
	 */
	public void refreshBlueAdvanceDeviceLeft(PayModifyBean payModifyBean);
	
	/**
	 * 贷项通知单更新原蓝字发票剩余可冲销的余额
	 * @param payModifyBean
	 */
	public void updateBlueInvoiceAmtLeft(PayModifyBean payModifyBean);
	
	/**
	 * 贷项通知单更新原蓝字发票发票行剩余可冲销的余额
	 * @param payModifyBean
	 */
	public void updateBlueDeviceLeft(PayModifyBean payModifyBean);
	
	/**
	 * 查看是否存在预付款核销
	 * @param payModifyBean
	 * @return
	 */
	public String getExists0(PayModifyBean payModifyBean);

}
