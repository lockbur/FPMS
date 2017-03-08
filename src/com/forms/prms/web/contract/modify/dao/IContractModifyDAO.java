package com.forms.prms.web.contract.modify.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.contract.initiate.domain.CntDevice;
import com.forms.prms.web.contract.initiate.domain.DzspInfo;
import com.forms.prms.web.contract.initiate.domain.StageInfo;
import com.forms.prms.web.contract.initiate.domain.Tenancy;
import com.forms.prms.web.contract.initiate.domain.TenancyDz;
import com.forms.prms.web.contract.modify.domain.ModifyContract;

@Repository
public interface IContractModifyDAO {

	/**
	 * @methodName modifyList desc 获取可修改合同列表信息
	 * 
	 * @param map
	 *            查询条件及责任中心
	 */
	public List<ModifyContract> modifyList(Map<String, Object> map);

	/**
	 * @methodName getDetail desc 获取可修改合同信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public ModifyContract getDetail(String cntNum);

	/**
	 * @methodName updCnt desc 修改合同信息
	 * 
	 * @param cnt
	 *            合同信息
	 */
	public int updCnt(ModifyContract cnt);

	/**
	 * @methodName getCntProj desc 获取物料信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public List<CntDevice> getCntProj(String cntNum);

	/**
	 * @methodName delCntDevice desc 删除物料信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public int delCntDevice(String cntNum);

	/**
	 * @methodName getDZSPProj desc 获取电子审批信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public List<DzspInfo> getDZSPProj(String cntNum);

	/**
	 * @methodName delDZSPProj desc 删除电子审批信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public int delDZSPProj(String cntNum);

	/**
	 * @methodName getTenancy desc 获取房屋租赁信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public int getTenancy(String cntNum);

	/**
	 * @methodName updTenancy desc 更新房屋租赁信息
	 * 
	 * @param tenancy
	 *            房屋租赁信息
	 */
	public int updTenancy(Tenancy tenancy);

	/**
	 * @methodName delTenancy desc 删除房屋租赁信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public int delTenancy(String cntNum);

	/**
	 * @methodName getTcyDz desc 获取租金递增信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public List<TenancyDz> getTcyDz(String cntNum);

	
	public List<TenancyDz> getMatrCode(String cntNum);
	public List<TenancyDz> getTcyDzByMatrCode(@Param("matrCodeFz") String matrCodeFz ,@Param("cntNum") String cntNum);
	/**
	 * @methodName delTcyDz desc 删除租赁递增信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public int delTcyDz(String cntNum);

	/**
	 * @methodName getOnSchedule desc 获取按进度付款信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public List<StageInfo> getOnSchedule(String cntNum);

	/**
	 * @methodName delOnSchedule desc 删除按进度分期付款信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public int delOnSchedule(String cntNum);

	/**
	 * @methodName getOnDate desc 获取按日期付款信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public List<StageInfo> getOnDate(String cntNum);

	/**
	 * @methodName delOnDate desc 删除按日期分期付款信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public int delOnDate(String cntNum);

	/**
	 * @methodName getOnTerm desc 获取按条件分期付款信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public List<StageInfo> getOnTerm(String cntNum);

	/**
	 * @methodName updOnTerm desc 更新按条件分期付款
	 * 
	 * @param stageInfo
	 *            按条件分期付款
	 */
	public int updOnTerm(StageInfo stageInfo);

	/**
	 * @methodName delOnTerm desc 删除按条件分期付款
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public int delOnTerm(String cntNum);

	/**
	 * @methodName delCntFee desc 删除费用类型信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public int delCntFee(String cntNum);

	/**
	 * @methodName getBenefit desc 获取费用类型受益信息
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public List<CntDevice> getBenefit(String cntNum);

	/**
	 * @methodName getSumExecAmt desc 查询同一项目总的执行金额
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public List<CntDevice> getSumExecAmt(String cntNum);

	/**
	 * @methodName updateFreezeAmt desc 修改冻结金额
	 * 
	 * @param freezeAmt
	 *            冻结金额 ，projId 项目 ID
	 * @param cntTrAmt 
	 */
	public int updateFreezeAmt(@Param("freezeAmt") BigDecimal freezeAmt, @Param("projId") String projId, @Param("taxNamt")BigDecimal cntTrAmt);

	/**
	 * @methodName updateProjCntAmt desc 修改冻结金额
	 * 
	 * @param cntTotalAmt
	 *            执行金额 ，projId 项目 ID
	 */
	public int updateProjCntAmt(@Param("cntTotalAmt") BigDecimal freezeAmt, @Param("projId") String projId,@Param("taxNamt")BigDecimal taxNamt);

	/**
	 * @methodName delCnt desc 删除合同
	 * 
	 * @param cntNum
	 *            合同编号
	 */
	public int delCnt(String cntNum);

	/**
	 * @methodName delOrderBackDev desc 订单退回删除合同物料
	 * 
	 * @param cntNum
	 */
	public void delOrderBackDev(String cntNum);

	/**
	 * @methodName getMaxSubIdByCntNum desc 根据合同号获取物料编号最大值
	 * 
	 * @param cntNum
	 * @return
	 */
	public int getMaxSubIdByCntNum(String cntNum);

	/**
	 * @methodName getOrderSucDevices desc 根据合同号获取状态为订单退回的合同中发送成功的订单对应的物料列表
	 * 
	 * @param cntNum
	 * @return
	 */
	public List<CntDevice> getOrderSucDevices(String cntNum);

	/**
	 * @methodName delOrderBackCntDevice desc 订单退回的合同修改操作时删除非订单成功的原物料信息
	 * 
	 * @param cntNum
	 */
	public void delOrderBackCntDevice(String cntNum);

	/**
	 * @methodName getOrderSucDevices desc 根据合同号获取状态为订单退回的合同中可修改的物料列表
	 * 
	 * @param cntNum
	 * @return
	 */
	public List<CntDevice> orderBackNewDevices(String cntNum);

	/**
	 * @methodName updateOrderInfo desc 修改订单退回的合同时修改对应的成功订单信息
	 * 
	 * @param cnt
	 * @return
	 */
	public void updateOrderInfo(ModifyContract cnt);

	public void delCntFeeLog(String cntNum);

	public List<CntDevice> checkPassDev(String cntNum);

	public List<CntDevice> canMotidyDev(String cntNum);

	public void updateFreezeTotal(String cntNum);

	/**
	 * 合同删除时删除掉合同号在扫描信息表中的记录
	 * 
	 * @param cntNum
	 */
	public void delTiIcms(String cntNum);

	/**
	 * 删除合同在审批历史记录表中的数据
	 * 
	 * @param cntNum
	 */
	public void delCntLog(String cntNum);

	/**
	 * 删除合同在合同设备历史表中的数据
	 * 
	 * @param cntNum
	 */
	public void delCntDevLog(String cntNum);
}
