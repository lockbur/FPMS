package com.forms.prms.web.contract.check.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.contract.check.domain.ContractCheckBean;

/**
 * author : lisj <br>
 * date : 2015-01-23<br>
 * 合同复核DAO
 */
@Repository
public interface ContractCheckDAO {

	/**
	 * 合同复核信息列表查询
	 * 
	 * @param constract
	 * @return
	 */
	public List<ContractCheckBean> constractList(Map<String, Object> paramMap);

	/**
	 * 合同复核信息明细查询
	 * 
	 * @param constract
	 * @return
	 */
	public ContractCheckBean contractDeail(ContractCheckBean contractCheckBean);

	/**
	 * 合同物料设备信息
	 * 
	 * @param map
	 * @return
	 */
	public List<ContractCheckBean> deviceDeail(Map<String, Object> map);
	/**
	 * 去重合同物料设备信息
	 * 
	 * @param map
	 * @return
	 */
	public List<ContractCheckBean> distinctDeviceDeail(Map<String, Object> map);

	/**
	 * 改变物料的状态
	 * 
	 * @param contractCheckBean
	 * @return
	 */
	public int updateDeviceFlag(ContractCheckBean contractCheckBean);

	/**
	 * 查询某合同下物料状态不是成功-99的物料信息
	 * 
	 * @param contractCheckBean
	 * @return
	 */
	public List<ContractCheckBean> findDeviceById(ContractCheckBean contractCheckBean);

	/**
	 * 改变合同的状态
	 * 
	 * @param contractCheckBean
	 */
	public void updateContractFlag(ContractCheckBean contractCheckBean);

	/**
	 * 判断是否还有为复核的物料
	 * 
	 * @param contractCheckBean
	 * @return
	 */
	public List<ContractCheckBean> noCheckDeviceList(ContractCheckBean contractCheckBean);

	/**
	 * 复核通过时如果有退回的物料且没有要复核的物料
	 * 
	 * @param contractCheckBean
	 * @return
	 */
	public int have01NoHave00(ContractCheckBean contractCheckBean);
	
}
