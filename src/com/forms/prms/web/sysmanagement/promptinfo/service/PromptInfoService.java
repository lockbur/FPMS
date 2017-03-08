/**
 * 
 */
package com.forms.prms.web.sysmanagement.promptinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.prms.web.sysmanagement.promptinfo.dao.IPromptInfoDao;
import com.forms.prms.web.sysmanagement.promptinfo.domain.PromptInfoBean;

/**
 * @author ZhengCuixian
 *
 */
@Service
public class PromptInfoService {
	
	@Autowired
	private IPromptInfoDao dao;
	/**
	 * 获得PromptInfoService实例 
	 * @return
	 */
	public static PromptInfoService getInstance(){
		return SpringUtil.getBean(PromptInfoService.class);				
	}
	/**
	 * 查询所有提示信息
	 * @return
	 */
	public List<PromptInfoBean> promptInfoList(){
		CommonLogger.info("查询所有提示信息,PromptInfoService,promptInfoList");
		return dao.promptInfoList();
	}

	public List<PromptInfoBean> query(PromptInfoBean promptInfoBean) {
		IPromptInfoDao pageDao = PageUtils.getPageDao(dao);
		return pageDao.query(promptInfoBean);
	}

	public String edit(PromptInfoBean promptInfoBean) {
		CommonLogger.info("修改提示信息,PromptInfoService,edit");
		dao.edit(promptInfoBean);
		return null;
	}

	public String delete(PromptInfoBean promptInfoBean) {
		CommonLogger.info("删除提示信息,PromptInfoService,delete");
		dao.delete(promptInfoBean);
		return null;
	}

	public String add(PromptInfoBean promptInfoBean) {
		CommonLogger.info("新增提示信息,PromptInfoService,add");
		dao.add(promptInfoBean);
		return null;
	}

	public int checkPrimaryKey(PromptInfoBean promptInfoBean) {
		CommonLogger.info("检查提示信息名称是否存在,PromptInfoService,checkPrimaryKey");
		return dao.checkPrimaryKey(promptInfoBean);
	}

}
