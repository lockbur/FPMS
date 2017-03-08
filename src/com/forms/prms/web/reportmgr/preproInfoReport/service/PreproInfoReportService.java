package com.forms.prms.web.reportmgr.preproInfoReport.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.dao.ICommonExcelDealDao;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.tool.exceltool.exportthread.ExcelExportGenernalDeal;
import com.forms.prms.web.reportmgr.preproInfoReport.dao.PreproInfoReportDAO;
import com.forms.prms.web.reportmgr.preproInfoReport.domain.PreproInfoReportBean;

@Service
public class PreproInfoReportService {
	@Autowired
	private PreproInfoReportDAO pDao;
	
	@Autowired
	private ExcelExportGenernalDeal exportDeal;
	//公共Excel导出更新dao
	@Autowired
	private ICommonExcelDealDao excelDao;
	
	//获得类实例
	public static PreproInfoReportService getInstance(){
		return SpringUtil.getBean(PreproInfoReportService.class);
	}
	
	
	/**
	 * 查看预提待摊报表
	 */
	public List<PreproInfoReportBean> getPreproinfoReport(PreproInfoReportBean bean){
		String dutyCode = WebHelp.getLoginUser().getDutyCode();
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		String org2Code = WebHelp.getLoginUser().getOrg2Code();
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("con", bean);
		paramMap.put("dutyCode", dutyCode);
		paramMap.put("org1Code", org1Code);
		paramMap.put("org2Code", org2Code);
		
		PreproInfoReportDAO pageDao = PageUtils.getPageDao(pDao);
		CommonLogger.info("查看预提待摊报表,PreproInfoReportService,getPreproinfoReport()");
		return pageDao.getPreproinfoReport(paramMap);
	}

	//导出
	public List<PreproInfoReportBean> exportExcute(Map<String, Object> paramMap) {
		return pDao.getPreproinfoReport(paramMap);
	}
	/** 
	 * @methodName updateExcelResult
	 * desc   更新导出状态数据
	 * 
	 * @param bean 
	 */
	public void updateExcelResult(CommonExcelDealBean bean){
		excelDao.updateExportResult(bean);
	}
	
	/**
	 * 根据查詢條件获取合同列表
	 * 
	 * @param con
	 * @return 返回生成的任务ID
	 * @throws Exception
	 */
	public String queryDownloadList(PreproInfoReportBean pp) throws Exception{
		CommonLogger.info("得到生成的一个任务ID,PreproInfoReportService，queryDownloadList");
		String destFile = WebHelp.getSysPara("EXCEL_UPLOAD_FILE_DIR")+"/preproinfoReport.xlsx";
		Map<String,Object> map = new HashMap<String, Object>();
		String dutyCode = WebHelp.getLoginUser().getDutyCode();
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		String org2Code = WebHelp.getLoginUser().getOrg2Code();
		map.put("orgFlag", pp.getOrgFlag());
		map.put("dutyCode", dutyCode);
		map.put("org1Code", org1Code);
		map.put("org2Code", org2Code);
		
		map.put("feeDept", pp.getFeeDept());
		map.put("cglCode", pp.getCglCode());
		map.put("feeYear", pp.getFeeYear());
		
		return exportDeal.execute("待摊费用明细报表", "PREPROINFOREPORT",destFile,map);
	}

}
