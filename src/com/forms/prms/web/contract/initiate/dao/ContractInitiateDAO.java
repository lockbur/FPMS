package com.forms.prms.web.contract.initiate.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.contract.initiate.domain.CntDevice;
import com.forms.prms.web.contract.initiate.domain.ContractBean;
import com.forms.prms.web.contract.initiate.domain.HistoryContract;
import com.forms.prms.web.contract.initiate.domain.StageInfo;
import com.forms.prms.web.contract.initiate.domain.Tenancy;

@Repository
public interface ContractInitiateDAO {
	/**
	 * 获取新的合同Id
	 * 
	 * @return
	 */
	public String getNewContractId(ContractBean bean);

	/**
	 * 新增合同
	 * @param bean
	 * @return
	 */
	public int add(ContractBean bean);

	/**
	 * 新增按进度分期付款
	 * @param map
	 */
	public int addOnSchedule(Map<String, Object> map);

	/**
	 * 新增按日期分期付款
	 * @param map
	 */
	public int addOnDate(Map<String, Object> map);

	/**
	 * 新增按条件分期付款
	 * @param map
	 */
	public int addOnTerm(StageInfo stageInfo);

	/**
	 * 新增电子审批记录
	 * @param map
	 */
	public int addDZSP(Map<String, Object> map);

	/**
	 * 新增租赁信息
	 * @param tenancy
	 * @return
	 */
	public int addTenancy(Tenancy tenancy);

	/**
	 * 新增租赁递增信息
	 * @param map
	 * @return
	 */
	public int addTenancyDz(Map<String, Object> map);

	/**
	 * 新增物料信息
	 * @param map
	 * @return
	 */
	public int addCntDevice(Map<String, Object> map);

	/**
	 * 新增费用信息
	 * @param map
	 * @return
	 */
	public int addFeeType(Map<String, Object> map);
	

	/**
	 * 获取历史记录最大版本号，如果参数operType不为空，则查询的是除operType类外最大的版本号
	 * @param bean
	 * @return
	 */
	public HistoryContract getHisMaxVersion(HistoryContract bean);
	
	/**
	 * 新增合同历史记录
	 * @param bean
	 * @return
	 */
	public int addCntHis(ContractBean bean);

	/**
	 * 新增按进度分期付款历史记录
	 * @param map
	 */
	public int addOnScheduleHis(Map<String, Object> map);

	/**
	 * 新增按日期分期付款历史记录
	 * @param map
	 */
	public int addOnDateHis(Map<String, Object> map);

	/**
	 * 新增按条件分期付款历史记录
	 * @param map
	 */
	public int addOnTermHis(StageInfo stageInfo);

	/**
	 * 新增电子审批记录历史记录
	 * @param map
	 */
	public int addDZSPHis(Map<String, Object> map);

	/**
	 * 新增租赁信息历史记录
	 * @param tenancy
	 * @return
	 */
	public int addTenancyHis(Tenancy tenancy);

	/**
	 * 新增租赁递增信息历史记录
	 * @param map
	 * @return
	 */
	public int addTenancyDzHis(Map<String, Object> map);

	/**
	 * 新增物料信息历史记录
	 * @param map
	 * @return
	 */
	public int addCntDeviceHis(Map<String, Object> map);

	/**
	 * 新增费用信息历史记录
	 * @param map
	 * @return
	 */
	public int addFeeTypeHis(Map<String, Object> map);
	
	/**
	 * @methodName hasSacned
	 * desc  查看合同是否扫描过
	 * 
	 * @param cntNum
	 * @return
	 */
	public int hasSacned(String cntNum);
	
	/**
	 * @methodName relatedCntPage
	 * desc  根据创建机构获取合同列表
	 * 
	 * @param createDept
	 * @return
	 */
	public List<ContractBean> relatedCntPage(ContractBean cnt);

	/**
	 * 更新合同表的FROZEN_YYYYMM
	 * @param cntNum
	 * @param frozenYyyymm
	 * @return
	 */
	public int updateCntFrozenYyyymm(@Param("cntNum")String cntNum,@Param("frozenYyyymm")String frozenYyyymm);
	/**
	 * 税码
	 * @param cnt
	 * @return
	 */
	public List<CntDevice> taxCodeList(CntDevice cnt);
}
