package com.forms.prms.web.cleanpaydeal.cleanpayquery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.cleanpaydeal.cleanpay.domain.CleanPayBean;
import com.forms.prms.web.cleanpaydeal.cleanpayquery.dao.CleanPayQueryDao;

@Service
public class CleanPayQueryService {
	@Autowired
	private CleanPayQueryDao dao;

	/**
	 * 暂收结清信息列表查询
	 * 
	 * @param bean
	 * @return
	 */
	public List<CleanPayBean> getList(CleanPayBean bean) {
		CommonLogger.info("暂收结清信息列表查询,CleanPayQueryService,getList");
		// List<String> instDutyCodes = dao.getinstDutyCodes(bean);//
		// 通过机构号查找对应的责任中心集合
		// bean.setInstDutyCodes(instDutyCodes);
		CleanPayQueryDao pageDao = PageUtils.getPageDao(dao);
		bean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());// 得到登录人所在的一级行
		bean.setOrg2Code(WebHelp.getLoginUser().getOrg2Code());
		bean.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		return pageDao.getList(bean);
	}
}
