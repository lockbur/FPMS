package com.forms.prms.web.sysmanagement.approvechain.approveChainMgr.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.approvechain.approveChainMgr.domain.ApproveChainMgrBean;

@Repository
public interface ApproveChainMgrDao {
	/**
	 * 省行维护 已维护审批链
	 * @param bean
	 * @return
	 */
	List<ApproveChainMgrBean> specHaveWhList(ApproveChainMgrBean bean);
	/**
	 * 省行维护  未维护审批链
	 * @param bean
	 * @return
	 */
	List<ApproveChainMgrBean> specNoWhList(ApproveChainMgrBean bean);
	/**
	 * 省行维护 新增
	 * @param bean
	 * @return
	 */
	int specAdd(ApproveChainMgrBean bean);
	/**
	 * 省行维护审批链 编辑页
	 * @param bean
	 * @return
	 */
	ApproveChainMgrBean specPreEdit(ApproveChainMgrBean bean);
	/**
	 * 省行审批链维护
	 * @param bean
	 * @return
	 */
	void specEdit(ApproveChainMgrBean bean);
	/**
	 * 解除审批链
	 * @param bean
	 * @return
	 */
	int specDel(ApproveChainMgrBean bean);
	//=================================================分行===========================================
	/**
	 * 分行物料审批链已维护
	 * @param bean
	 * @return
	 */
	List<ApproveChainMgrBean> noSpecHaveWhList(ApproveChainMgrBean bean);
	/**
	 * 分行物料审批链未维护
	 * @param bean
	 * @return
	 */
	List<ApproveChainMgrBean> noSpecNoWhList(ApproveChainMgrBean bean);
	/**
	 * 分行未维护 物料明细页面
	 * @param bean
	 * @return
	 */
	List<ApproveChainMgrBean> noSpecNoWhMatrs(ApproveChainMgrBean bean);
	/**
	 * 分行审批链新增
	 * @param bean
	 * @return
	 */
	int noSpecAdd(ApproveChainMgrBean bean);
	/**
	 * 分行进入审批链维护
	 * @param bean
	 * @return
	 */
	ApproveChainMgrBean noSpecPreEdit(ApproveChainMgrBean bean);
	/**
	 * 分行审批链编辑
	 * @param bean
	 * @return
	 */
	void noSpecEdit(ApproveChainMgrBean bean);
	/**
	 * 分行审批链解除
	 * @param bean
	 * @return
	 */
	int noSpecDel(ApproveChainMgrBean bean);
	/**
	 * 分行责任中心撤并引起的审批链变化
	 * @param bean
	 * @return
	 */
	List<ApproveChainMgrBean> getNoSpecChange(ApproveChainMgrBean bean);
	/**
	 * 查询所有责任中心
	 * @param bean
	 * @return
	 */
	List<ApproveChainMgrBean> getDutyByOrg2(ApproveChainMgrBean bean);
	/**
	 * 得到多个责任中心共同可维护的物料
	 * @param bean
	 * @return
	 */
	List<ApproveChainMgrBean> selecstPublicMatrs(ApproveChainMgrBean bean);
	/**
	 * s省行未维护责任中心
	 * @param bean
	 * @return
	 */
	List<ApproveChainMgrBean> specNoWhDutyList(ApproveChainMgrBean bean);
	/**
	 * 省行未维护明细页面物料
	 * @param bean
	 * @return
	 */
	List<ApproveChainMgrBean> specNoWhMatrs(ApproveChainMgrBean bean);
	/**
	 * 省行待修改
	 * @param bean
	 * @return
	 */
	List<ApproveChainMgrBean> getAprvChange(ApproveChainMgrBean bean);
	/**
	 * 分行批量维护审批链
	 * @param chain
	 * @return
	 */
	int noSpecBatchEditExecute(ApproveChainMgrBean chain);
	/**
	 * 省行批量维护责任中心
	 * @param bean
	 * @return
	 */
	List<ApproveChainMgrBean> getDutyByOrg1ForSpec(ApproveChainMgrBean bean);
	/**
	 * 费用承担部门修改
	 * @param bean
	 * @return
	 */
	boolean updateFee(ApproveChainMgrBean bean);
	/**
	 * 采购部门
	 * @param bean
	 * @return
	 */
	boolean updateBuy(ApproveChainMgrBean bean);
	/**
	 * 物料归口部门
	 * @param bean
	 * @return
	 */
	void updateAudit(ApproveChainMgrBean bean);
	/**
	 * 分行所有待修改的责任中心
	 * @param bean
	 * @return
	 */
	List<ApproveChainMgrBean> getChangeDutyListForNospec(
			ApproveChainMgrBean bean);
	/**
	 * 省行所有待修改的责任中心
	 * @param bean
	 * @return
	 */
	List<ApproveChainMgrBean> getChangeDutyListForSpec(ApproveChainMgrBean bean);
	
	
	List<ApproveChainMgrBean> appList(ApproveChainMgrBean bean);
	
	List<ApproveChainMgrBean> HisAppList(ApproveChainMgrBean bean);
	
	

	List<ApproveChainMgrBean> getMont(ApproveChainMgrBean bean);
	List<ApproveChainMgrBean> futAppList(ApproveChainMgrBean bean);
	int getCountFromAprv(ApproveChainMgrBean bean);
	void insertDataFromUpSpec(ApproveChainMgrBean bean);
	void insertDataFromUpNoSpec(ApproveChainMgrBean bean);
	void deleteDataUp(ApproveChainMgrBean bean);
	ApproveChainMgrBean selectDetail(ApproveChainMgrBean bean);
	List<ApproveChainMgrBean> forUpdate(ApproveChainMgrBean bean);
	ApproveChainMgrBean getBean(ApproveChainMgrBean bean);
	void updateSysWarnC1(ApproveChainMgrBean bean);


	

}
