package com.forms.prms.web.sysmanagement.projtype.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.projtype.dao.ProjTypeDao;
import com.forms.prms.web.sysmanagement.projtype.domain.ProjTypeBean;

@Service
public class ProjTypeService {
	@Autowired
	private ProjTypeDao projTypeDao;
	/**
	 * @methodName list
	 * desc  
	 * 列表查询
	 * @param rollInfoBean
	 * @return
	 */
	public List<ProjTypeBean> list(ProjTypeBean projTypeBean){
		CommonLogger.info("首页项目列表查询,ProjTypeService,list");
		ProjTypeDao pageDao=PageUtils.getPageDao(projTypeDao);
		String org1Code=WebHelp.getLoginUser().getOrg1Code();
		projTypeBean.setOrg1Code(org1Code);
		return pageDao.list(projTypeBean);
	}
	/**
	 * @methodName addSubmit
	 * desc  
	 * 新增提交
	 * @param rollInfoBean
	 */
	public void addSubmit(ProjTypeBean projTypeBean){
		CommonLogger.info("项目类型新增名称为"+projTypeBean.getParamName()+"的信息，ProjTypeService，addSubmit");
		projTypeBean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		projTypeDao.addSubmit(projTypeBean);
	}
	
	/**
	 * @methodName addSubmit
	 * desc  
	 * 更新提交
	 * @param rollInfoBean
	 */
	public void updateSubmit(ProjTypeBean projTypeBean){
		CommonLogger.info("更新提交,ProjTypeService,updateSubmit");
		projTypeBean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		projTypeDao.updateSubmit(projTypeBean);
	}
	
	/**
	 * @methodName addSubmit
	 * desc  
	 * 删除
	 * @param rollInfoBean
	 */
	public void del(ProjTypeBean projTypeBean){
		CommonLogger.info("项目类型删除ID为"+projTypeBean.getParamValue()+"的信息，ProjTypeService，del");
		projTypeDao.del(projTypeBean);
	}
	public ProjTypeBean queryInfo(ProjTypeBean projTypeBean){
		String org1Code=WebHelp.getLoginUser().getOrg1Code();
		projTypeBean.setOrg1Code(org1Code);
		return projTypeDao.queryInfo(projTypeBean);
	}
	
}
