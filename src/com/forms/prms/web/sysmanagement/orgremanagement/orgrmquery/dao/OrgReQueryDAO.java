package com.forms.prms.web.sysmanagement.orgremanagement.orgrmquery.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.orgremanagement.orgrmquery.domain.OrgReQueryBean;


@Repository
public interface OrgReQueryDAO {

	/**
	 * 机构撤并查询
	 * @param orgReQueryBean
	 * @return
	 */
	public List<OrgReQueryBean> list(OrgReQueryBean orgReQueryBean);
	
	/**
	 * 责任中心变动信息查询
	 * @param orgReQueryBean
	 * @return
	 */
	public List<OrgReQueryBean> orgList(OrgReQueryBean orgReQueryBean);
	/**
	 * 责任中心完结
	 * @param bean
	 * @return
	 */
	public int dutyOver(OrgReQueryBean bean);
	public int orgOver(OrgReQueryBean bean);
	/**
	 * 交叉验证表数据查询列表及导出
	 * @param orgReQueryBean
	 * @return
	 */
	public List<OrgReQueryBean> exportList(OrgReQueryBean orgReQueryBean);
	public List<OrgReQueryBean> ajaxList(OrgReQueryBean bean);
	
}
