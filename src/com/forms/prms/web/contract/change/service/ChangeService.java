package com.forms.prms.web.contract.change.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.constantValues.BusTypes;
import com.forms.prms.tool.constantValues.OperateValues;
import com.forms.prms.web.contract.change.dao.ChangeDAO;
import com.forms.prms.web.contract.change.domain.ChangeForm;
import com.forms.prms.web.sysmanagement.waterbook.service.WaterBookService;

@Service
public class ChangeService {

	@Autowired
	private ChangeDAO cDao;
	@Autowired
	private WaterBookService wService;

	/**
	 * 列表
	 * 
	 * @param form
	 * @return
	 */
	public List<ChangeForm> list(ChangeForm form) {
		CommonLogger.info("查询可变更合同数据，ChangeService，list");
		form.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		form.setOrg2Code(WebHelp.getLoginUser().getOrg2Code());
		form.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		ChangeDAO pageDao = PageUtils.getPageDao(cDao);
		return pageDao.list(form);
	}

	/**
	 * 发起变更
	 * 
	 * @param cntNum
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int toChange(ChangeForm form) {
		CommonLogger.info("对合同号为"+form.getCntNum()+"进行变更发起！，ChangeService，toChange");
		form.setOperUser(WebHelp.getLoginUser().getUserId());
		int i = cDao.toChange(form);
		CommonLogger.info("合同变更：发起变更（合同编号："+form.getCntNum()+"），ChangeService，toChange");
		if (i > 0) {
			// 增加流水
			wService.insert(form.getCntNum(), BusTypes.CONTRACT, OperateValues.TOCHANGE, form.getWaterMemo(),"20","21");
		}
		return i;
	}

}
