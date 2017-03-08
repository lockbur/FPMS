package com.forms.prms.web.sysmanagement.orgManage.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.orgManage.domain.OrgBean;
@Repository
public interface OrgManageDao {
	/**
	 * 机构列表
	 * @param bean
	 * @return
	 */
	List<OrgBean> getPageList(OrgBean bean);
	
	/**
	 * 修改扫描岗
	 * @param bean
	 * @return
	 */
	int changeScanPosition(OrgBean bean);
	/**
	 * 查找明细
	 * @param bean
	 * @return
	 */
	OrgBean getBean(OrgBean bean);

	List<OrgBean> getOuList(OrgBean bean);

	List<OrgBean> getOrgList(OrgBean bean);

	List<OrgBean> getByOrgOuList(String orgCode);

	public void lock(OrgBean bean);

	public void openLock(OrgBean bean);

}
