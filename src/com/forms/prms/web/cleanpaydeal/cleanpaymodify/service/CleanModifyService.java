package com.forms.prms.web.cleanpaydeal.cleanpaymodify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.cleanpaydeal.cleanpay.domain.CleanPayBean;
import com.forms.prms.web.cleanpaydeal.cleanpaymodify.dao.CleanModifyDAO;

@Service
public class CleanModifyService {

	@Autowired
	private CleanModifyDAO cleanModifyDAO;

	/**
	 * 查询暂收结清信息列表
	 * 
	 * @return
	 */
	public List<CleanPayBean> cleanList(CleanPayBean cleanPayBean) {
		CommonLogger.info("查询暂收结清信息列表，CleanModifyService，cleanList");
		cleanPayBean.setInstDutyCode(WebHelp.getLoginUser().getDutyCode());
		CleanModifyDAO pageDao = PageUtils.getPageDao(cleanModifyDAO);
		return pageDao.cleanList(cleanPayBean);
	}

	/**
	 * 暂收结清信息修改保存
	 * @param cleanPayBean
	 * @return
	 */
	public boolean cleanpayEditSave(CleanPayBean cleanPayBean) {
		CommonLogger.info("暂收结清信息修改保存（付款单："+cleanPayBean.getNormalPayId()+",子序号："+cleanPayBean.getSortId()+"），CleanModifyService，cleanpayEditSave");
		cleanPayBean.setDataFlag("00");//录入
		return cleanModifyDAO.cleanpayEditSaveOrSubmit(cleanPayBean)>0?true:false;
	}

	/**
	 * 暂收结清信息修改提交
	 * @param cleanPayBean
	 * @return
	 */
	public boolean cleanpayEditSubmit(CleanPayBean cleanPayBean) {
		CommonLogger.info("暂收结清信息修改提交（付款单："+cleanPayBean.getNormalPayId()+",子序号："+cleanPayBean.getSortId()+"），CleanModifyService，cleanpayEditSubmit");
		cleanPayBean.setDataFlag("02");//待复核
		return cleanModifyDAO.cleanpayEditSaveOrSubmit(cleanPayBean)>0?true:false;
	}

	/**
	 * 查询暂收结清明细信息
	 * @param cleanPayBean
	 * @return
	 */
	public CleanPayBean queryCleanedDetail(CleanPayBean cleanPayBean) {
		CommonLogger.info("查询暂收结清明细信息（付款单："+cleanPayBean.getNormalPayId()+",子序号："+cleanPayBean.getSortId()+"），CleanModifyService，queryCleanedDetail");
		return cleanModifyDAO.queryCleanedDetail(cleanPayBean);
	}
}
