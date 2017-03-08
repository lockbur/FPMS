package com.forms.prms.web.cleanpaydeal.cleanpaysure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.prms.web.cleanpaydeal.cleanpay.domain.CleanPayBean;
import com.forms.prms.web.cleanpaydeal.cleanpaysure.dao.CleanPaySureDao;

@Service
public class CleanPaySureService {
	@Autowired
	private CleanPaySureDao dao;

	/**
	 * 暂收结清待确认信息列表查询
	 * 
	 * @param bean
	 * @return
	 */
	public List<CleanPayBean> getList(CleanPayBean bean) {
		// List<String> instDutyCodes = dao.getinstDutyCodes(bean);//
		// 通过机构号查找对应的责任中心集合
		// bean.setInstDutyCodes(instDutyCodes);
		CleanPaySureDao pageDao = PageUtils.getPageDao(dao);
		return pageDao.getList(bean);
	}

	// 暂收结清确认通过
	public void Agree(CleanPayBean bean) {
		bean.setDataFlag("06");
		dao.Agree(bean);
	}

	// 暂收结清确认退回
	public void Back(CleanPayBean bean) {
		bean.setDataFlag("03");
		dao.Back(bean);
	}
}
