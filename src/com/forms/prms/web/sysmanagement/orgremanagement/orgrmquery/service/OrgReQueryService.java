package com.forms.prms.web.sysmanagement.orgremanagement.orgrmquery.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.prms.tool.ImportUtil;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.exportthread.ExcelExportGenernalDeal;
import com.forms.prms.web.sysmanagement.orgremanagement.orgrmquery.dao.OrgReQueryDAO;
import com.forms.prms.web.sysmanagement.orgremanagement.orgrmquery.domain.OrgReQueryBean;

@Service
public class OrgReQueryService {
	@Autowired
	private OrgReQueryDAO orgReQueryDAO;
	@Autowired
	private ExcelExportGenernalDeal exportDeal;	

	/**
	 * 机构撤并查询
	 * @param orgReQueryBean
	 * @return
	 */
	public List<OrgReQueryBean> list(OrgReQueryBean orgReQueryBean) {
		CommonLogger.info("机构自动撤并查询，OrgReQueryService，list");
		OrgReQueryDAO pageDao = PageUtils.getPageDao(orgReQueryDAO);
		return pageDao.list(orgReQueryBean);
	}

	public List<OrgReQueryBean> orgList(OrgReQueryBean orgReQueryBean) {
		CommonLogger.info("责任中心变动信息查询查询，OrgReQueryService，orgList");
		OrgReQueryDAO pageDao = PageUtils.getPageDao(orgReQueryDAO);
		return pageDao.orgList(orgReQueryBean);
	}
	/**
	 * 责任中心完结
	 * @param bean
	 * @return
	 */
	public boolean dutyOver(OrgReQueryBean bean) {
		return orgReQueryDAO.dutyOver(bean)>0;
	}
	/**
	 * 机构完结
	 * @param bean
	 * @return
	 */
	public boolean orgOver(OrgReQueryBean bean) {
		return orgReQueryDAO.orgOver(bean)>0;
	}
	//获得类实例
	public static OrgReQueryService getInstance(){
		return SpringUtil.getBean(OrgReQueryService.class);
	}
	public List<OrgReQueryBean> exportExcute(OrgReQueryBean orgReQueryBean) {
		CommonLogger.info("交叉验证表导出查询，OrgReQueryService，exportExcute");
		return orgReQueryDAO.exportList(orgReQueryBean);
	}
	public List<OrgReQueryBean> exportList(OrgReQueryBean orgReQueryBean) {
		CommonLogger.info("交叉验证表导出查询，OrgReQueryService，exportList");
		//得到用户是否超级管理员，一级行代码
		orgReQueryBean.setOrg1CheckCode(WebHelp.getLoginUser().getOrg1Code());
		orgReQueryBean.setIsSuperAdmin(WebHelp.getLoginUser().getIsSuperAdmin());
		orgReQueryBean.setOrg2CheckCode(WebHelp.getLoginUser().getOrg2Code());
		OrgReQueryDAO pageDao = PageUtils.getPageDao(orgReQueryDAO);
		return pageDao.exportList(orgReQueryBean);
	}
	public String exportData(OrgReQueryBean bean)throws Exception {
		String sourceFileName = "交叉验证表导出";
		ImportUtil.createFilePath(new File(WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")));
		String destFile = WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")+"/"+sourceFileName+".xlsx";;
		//导出Excel操作
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("org1Code", bean.getOrg1Code());
		map.put("org1Name",bean.getOrg1Name());
		map.put("org2Code", bean.getOrg2Code());
		map.put("org2Name", bean.getOrg2Name());
		map.put("dutyCode", bean.getDutyCode());
		map.put("dutyName", bean.getDutyName());
		map.put("isLocked", bean.getIsLocked());
		String org1CheckCode = WebHelp.getLoginUser().getOrg1Code();// 得到登录人所在一级行
		String org2CheckCode = WebHelp.getLoginUser().getOrg2Code();// 得到登录人所在二级行
		String isSuperAdmin=WebHelp.getLoginUser().getIsSuperAdmin();//得到是否超级管理员
		map.put("org1CheckCode", org1CheckCode);
		map.put("org2CheckCode", org2CheckCode);
		map.put("isSuperAdmin", isSuperAdmin);
		return exportDeal.execute(sourceFileName, "FNDWRR_EXPORT", destFile , map);
	}
}
