package com.forms.prms.web.contract.confirm.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.contract.confirm.domain.BudgetBean;
import com.forms.prms.web.contract.confirm.domain.ConfirmContract;
import com.forms.prms.web.contract.initiate.domain.CntFee;

@Repository
public interface IContractConfirmDAO {

	/**
	 * @methodName confirmList desc 根据查詢條件获取待确认合同列表
	 * 
	 * @param map
	 *            合同确认查询条件对象con及责任中心dutyCode
	 */
	public List<ConfirmContract> confirmList(Map<String, Object> map);

	/**
	 * @methodName getDetail desc 根据合同编号获取合同详情
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public ConfirmContract getDetail(String cntNum);

	/**
	 * @methodName getCntProj desc 根据合同编号获取项目列表
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public List<ConfirmContract> getCntProj(String cntNum);

	/**
	 * @methodName confirmPass desc 根据合同编号修改合同状态（“合同确认完成”或“订单创建中”）
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public int confirmPass(@Param("cntNum") String cntNum, @Param("dataFlag") String dataFlag);

	/**
	 * @methodName confirmReturn desc 根据合同编号修改合同状态为‘合同退回’
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public int confirmReturn(String cntNum);

	/**
	 * @methodName getCntFeeSubIds desc 根据合同号获取子序列号
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public List<String> getCntFeeSubIds(@Param("cntNum") String cntNum);

	/**
	 * @methodName getSumFeeAmt desc 根据子序列号和年份获取总feeAmt
	 * 
	 * @param bean
	 *            合同费用对象
	 */
	public BigDecimal getSumFeeAmt(CntFee bean);

	/**
	 * @methodName updateCntDevicePay desc 费用类合同确认：占用当年度物料预算
	 * 
	 * @param map
	 *            费用列表集合
	 */
	// public int updateCntDevicePay(Map<String, Object> map);

	/**
	 * 费用类合同确认：占用当年度物料预算
	 * 
	 * @param map
	 * @return
	 */
	// public int updateCntDevicePayAmt(@Param("cntNum")String cntNum);

	/**
	 * @methodName getFeeStartDate desc 获取费用受益金额不为0的最小日期
	 * 
	 * @param cntNum
	 * @return
	 */
	public String getFeeStartDate(@Param("cntNum") String cntNum);

	/**
	 * @methodName updateIsOrder desc 更新合同信息：是否生成订单
	 * 
	 * @param isOrder
	 * @param cntNum
	 * @return
	 */
	public int updateIsOrder(@Param("isOrder") String isOrder, @Param("cntNum") String cntNum,
			@Param("isPrepaidProvision") String isPrepaidProvision);

	/**
	 * 通过合同号到设备表中找是否有订单退回的物料
	 * 
	 * @param cntNum
	 * @return
	 */
	public List<String> backDevCount(@Param("cntNum") String cntNum);

	/**
	 * 预提待摊类合同在合同确立时冻结预算
	 * @param cntNum
	 * @return
	 */
	public void bgtFrozen(Map<String, String> param);

	/**
	 * 
	 * 预提待摊类合同在合同确立时冻结预算
	 *	查询冻结明细
	 * @param cntNum
	 * @return
	 */
	public List<BudgetBean> queryBgtFrozenDetail(String cntNum);

	/**
	 * 删除预算临时数据
	 * @param cntNum
	 * @return
	 */
	public int deleteBgtFrozenTemp(String cntNum);

	public int isHaveMontSplit(ConfirmContract cnt);
	
	public String isInTableMont(ConfirmContract cnt);
	
	/**
	 * 比较日期
	 * @param start
	 * @param end
	 * @return
	 */
	public String compareDate(@Param("end") String end);

	public List<String> orderList(String cntNum);

	public List<String> notOrderMatrList(String cntNum);
}
