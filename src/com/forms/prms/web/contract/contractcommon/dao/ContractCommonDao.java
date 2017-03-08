package com.forms.prms.web.contract.contractcommon.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.contract.check.domain.ContractCheckBean;
import com.forms.prms.web.contract.contractcommon.domain.ContractCommonBean;
import com.forms.prms.web.contract.deliver.domain.DeliverForm;

@Repository
public interface ContractCommonDao {
	/**
	 * 合同新增后专向包向合同统计表中加入数据
	 * 
	 * @param bean
	 * @return
	 */
	public int addWarnCnt(ContractCommonBean bean);

	/**
	 * 合同新增后非专向包向合同统计表中加入数据
	 * 
	 * @param bean
	 * @return
	 */
	public int addWarnCntNoSpec(ContractCommonBean bean);
	
	public int addWarnCntSpecOrNoSpec(ContractCommonBean bean);

	/**
	 * 合同物料复核通过后加入一条待确认的合同数据
	 * 
	 * @param bean
	 */
	public void addWarnCntCheck(ContractCommonBean bean);

	/**
	 * 合同发起移交后要接受的合同的部门加入到合同统计表中
	 * 
	 * @param form
	 */
	public void addWarnCntDeliver(DeliverForm form);

	/**
	 * 修改合同删除之前这条待复核合同对应物料的归口部门在合同统计表中的数据
	 * 
	 * @param cntNum
	 */
	public void delWarnCntCheck(@Param("cntNum") String cntNum);

	/**
	 * 复核物料后删除这些物料对应归口部门在合同统计表中的数据
	 * 
	 * @param cntNum
	 */
	public void delWarnCntCheckOnce(ContractCheckBean contractCheckBean);

	/**
	 * 合同确认通过退回都删除这条合同在统计表中待确认的数据
	 * 
	 * @param cntNum
	 */
	public void delWarnCntConfirm(String cntNum);

	/**
	 * 合同移交前先删除这些合同所归属那个部门接受
	 * 
	 * @param form
	 */
	public void delWarnCntDeliver(DeliverForm form);

	/**
	 * 得到新增合同时添加到合同统计表中的归口部门集合
	 * 
	 * @param cntNum
	 * @return
	 */
	public List<String> getDutyCodeList(String cntNum);

	/**
	 * 得到待确认合同所归属的部门
	 * 
	 * @param cntNum
	 * @return
	 */
	public String getSureDutyCode(String cntNum);
	
	public void cancelDeliverCntInfo(DeliverForm form);

	public void cancelDeliverCount(DeliverForm form);
}
