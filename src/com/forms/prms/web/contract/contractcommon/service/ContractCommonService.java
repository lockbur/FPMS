package com.forms.prms.web.contract.contractcommon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.Common;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.web.contract.check.domain.ContractCheckBean;
import com.forms.prms.web.contract.contractcommon.dao.ContractCommonDao;
import com.forms.prms.web.contract.contractcommon.domain.ContractCommonBean;
import com.forms.prms.web.contract.deliver.domain.DeliverForm;

@Service
public class ContractCommonService {
	@Autowired
	private ContractCommonDao dao;

	/**
	 * 合同新增后专向包向统计表中加入待复核数据
	 * 
	 * @param cntNum
	 * @return
	 */
	public int addWarnCnt(String cntNum) {
		CommonLogger.info("新增专向包合同（合同编号： " + cntNum + "）后添加该合同物料对应归口部门复核的数据到统计表中，ContractCommonService，addWarnCnt");
		ContractCommonBean bean = new ContractCommonBean();
		bean.setCntNum(cntNum);
		bean.setFuncType("C1");
		return dao.addWarnCnt(bean);

	}

	/**
	 * 合同新增后非专向包向统计表中加入待复核数据
	 * 
	 * @param cntNum
	 * @return
	 */
	public int addWarnCntNoSpec(String cntNum) {
		CommonLogger.info("新增非专向包合同（合同编号： " + cntNum + "）后添加该合同物料对应归口部门复核的数据到统计表中，ContractCommonService，addWarnCnt");
		ContractCommonBean bean = new ContractCommonBean();
		bean.setCntNum(cntNum);
		bean.setFuncType("C1");
		return dao.addWarnCntNoSpec(bean);

	}
	
	public int addWarnCntSpecOrNoSpec(String cntNum){
		CommonLogger.info("新增费用类+其他 合同（合同编号： " + cntNum + "）后添加该合同物料对应归口部门复核的数据到统计表中，ContractCommonService，addWarnCnt");
		ContractCommonBean bean = new ContractCommonBean();
		bean.setCntNum(cntNum);
		bean.setFuncType("C1");
		return dao.addWarnCntSpecOrNoSpec(bean);
	}

	/**
	 * 合同物料复核全部通过后向合同统计表中加入一条待确认的合同
	 * 
	 * @param cntNum
	 */
	
	public void addWarnCntCheck(String cntNum) {
		CommonLogger.info("复核合同（合同编号： " + cntNum + "）后添加一条待确认的数据到合同统计表中，ContractCommonService，addWarnCntCheck");
		ContractCommonBean bean = new ContractCommonBean();
		bean.setCntNum(cntNum);
		bean.setFuncType("C2");
		dao.addWarnCntCheck(bean);
	}

	/**
	 * 合同发起移交后将这些移交待接收的合同加入到合同统计表中
	 * 
	 * @param form
	 */
	public void addWarnCntDeliver(DeliverForm form) {
		CommonLogger.info("将这些移交的合同归属的新的部门加入到合同统计表中，ContractCommonService，addWarnCntDeliver");
		form.setFuncType("C3");
		dao.addWarnCntDeliver(form);
	}

	/**
	 * 删除合同修改后之前待复核合同对应的归口部门数据
	 * 
	 * @param cntNum
	 */
	public void delWarnCntCheck(String cntNum) {
		dao.delWarnCntCheck(cntNum);
	}

	/**
	 * 复核过的物料就在合同统计表中删除该条数据
	 * 
	 * @param cntNum
	 */
	public void delWarnCntCheckOnce(ContractCheckBean contractCheckBean) {
		CommonLogger.info("删除合同号为"+contractCheckBean.getCntNum()+",归口部门为"+contractCheckBean.getDutyCode()+"在合同统计表中的信息，ContractCommonService，delWarnCntCheckOnce");
		dao.delWarnCntCheckOnce(contractCheckBean);
	}

	/**
	 * 合同确认或者退回都删除掉这条待确认的合同数据
	 * 
	 * @param cntNum
	 */
	public void delWarnCntConfirm(String cntNum) {
		CommonLogger.info("删除合同号为"+cntNum+"在合同统计表中待确认的数据，ContractCommonService，delWarnCntConfirm");
		dao.delWarnCntConfirm(cntNum);
	}

	/**
	 * 合同移交前先删除这些合同本来归属哪个部门
	 * 
	 * @param cntNum
	 */
	public void delWarnCntDeliver(DeliverForm form) {
		CommonLogger.info("删除选择移交的合同之前归属哪个部门接收的数据，ContractCommonService，delWarnCntDeliver");
		dao.delWarnCntDeliver(form);
	}

	/**
	 * 得到新增合同时插入到合同统计表中的归口部门集合
	 * 
	 * @param cntNum
	 * @return
	 */
	public List<String> getDutyCodeList(String cntNum) {
		CommonLogger.info("查看合同编号（: " + cntNum + "）对应的归口部门集合，ContractCommonService，getDutyCodeList");
		return dao.getDutyCodeList(cntNum);
	}

	/**
	 * 得到待确认合同所归属的部门
	 * 
	 * @param cntNum
	 * @return
	 */
	public String getSureDutyCode(String cntNum) {
		CommonLogger.info("查看合同编号（: " + cntNum + "）在合同统计表中待确认的部门，ContractCommonService，getSureDutyCode");
		return dao.getSureDutyCode(cntNum);
	}
	
	public void cancelDeliverCntInfo(DeliverForm form){
		 dao.cancelDeliverCntInfo(form);
	}

}
