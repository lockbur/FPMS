package com.forms.prms.web.cleanpaydeal.cleanpaycheck.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.prms.web.cleanpaydeal.cleanpaycheck.dao.CleanPayCheckDao;
import com.forms.prms.web.cleanpaydeal.cleanpaycheck.domain.CleanPayCheckBean;

@Service
public class CleanPayCheckService {
	@Autowired
	private CleanPayCheckDao dao;

	/**
	 * 暂收结清待复核信息列表查询
	 * 
	 * @param bean
	 * @return
	 */
	public List<CleanPayCheckBean> getList(CleanPayCheckBean bean) {
		// List<String> instDutyCodes = dao.getinstDutyCodes(bean);//
		// 通过机构号查找对应的责任中心集合
		// bean.setInstDutyCodes(instDutyCodes);
		CleanPayCheckDao pageDao = PageUtils.getPageDao(dao);
		return pageDao.getList(bean);
	}

	// 暂收结清复核通过
	public void Agree(CleanPayCheckBean bean) {
		bean.setDataFlag("04");
		dao.Agree(bean);
	}

	// 暂收结清复核退回
	public void Back(CleanPayCheckBean bean) {
		bean.setDataFlag("01");
		dao.Back(bean);
	}
}
