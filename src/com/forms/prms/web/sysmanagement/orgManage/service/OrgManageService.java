package com.forms.prms.web.sysmanagement.orgManage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.orgManage.dao.OrgManageDao;
import com.forms.prms.web.sysmanagement.orgManage.domain.OrgBean;

@Service
public class OrgManageService {
	@Autowired
	private OrgManageDao dao;

	/**
	 * 列表集合
	 * 
	 * @param bean
	 * @return
	 */
	public List<OrgBean> getPageList(OrgBean bean) {
		OrgManageDao pagedao = PageUtils.getPageDao(dao);
		return pagedao.getPageList(bean);
	}

	/**
	 * 修改扫描岗
	 * 
	 * @param bean
	 * @return
	 */
	public boolean changeScanPosition(OrgBean bean) {
		int result = dao.changeScanPosition(bean);
		return result > 0;
	}

	/**
	 * 查找明细
	 * 
	 * @param bean
	 * @return
	 */
	public OrgBean getBean(OrgBean bean) {
		return dao.getBean(bean);
	}

	public List<OrgBean> getOuList(OrgBean bean) {
		return dao.getOuList(bean);
	}

	public List<OrgBean> getOrgList(OrgBean bean) {
		return dao.getOrgList(bean);
	}

	public List<OrgBean> getByOrgOuList(String orgCode) {
		return dao.getByOrgOuList(orgCode);
	}
	@Transactional(rollbackFor = Exception.class)
	public void lock(OrgBean bean) {
		// 得到鎖定的人
		bean.setUserId(WebHelp.getLoginUser().getUserId());
		dao.lock(bean);
	}
	@Transactional(rollbackFor = Exception.class)
	public void openLock(OrgBean bean) {
		// 得到解锁的人
		bean.setUserId(WebHelp.getLoginUser().getUserId());
		dao.openLock(bean);

	}

}
