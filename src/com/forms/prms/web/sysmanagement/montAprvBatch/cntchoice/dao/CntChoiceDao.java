package com.forms.prms.web.sysmanagement.montAprvBatch.cntchoice.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.contract.initiate.domain.CntDevice;
import com.forms.prms.web.sysmanagement.montAprvBatch.cntchoice.domain.CntChoiceBean;

@Repository
public interface CntChoiceDao {
	/**
	 * 查询省行列表
	 * 
	 * @param bean
	 * @return
	 */
	List<CntChoiceBean> getList(CntChoiceBean bean);

	/**
	 * 得到待处理和退回的合同集合
	 * 
	 * @param bean
	 * @return
	 */
	List<CntChoiceBean> waitManageBackList(CntChoiceBean bean);

	/**
	 * 得到复核通过的合同集合
	 * 
	 * @param bean
	 * @return
	 */
	List<CntChoiceBean> checkPassList(CntChoiceBean bean);

	/**
	 * 得到待复核的合同集合
	 * 
	 * @param bean
	 * @return
	 */
	List<CntChoiceBean> waitCheckList(CntChoiceBean bean);

	/**
	 * 改变勾选合同提交后合同状态
	 * 
	 * @param bean
	 */
	void updateCntData(CntChoiceBean bean);

	/**
	 * 查看是否还有待处理和退回的合同
	 * 
	 * @param bean
	 * @return
	 */
	List<CntChoiceBean> cntChoiceList(CntChoiceBean bean);

	/**
	 * 改变合同的主状态
	 * 
	 * @param bean
	 */
	void updateCntMain(String batchNo);

	/**
	 * 更新新的监控指标代码名称
	 * 
	 * @param newBean
	 */
	void updateMontCodeName(CntChoiceBean newBean);

	List<CntDevice> exportExcute(String batchNo);


}
