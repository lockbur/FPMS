package com.forms.prms.web.reportmgr.cntPayinfoReport.service;

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
import com.forms.prms.web.reportmgr.cntPayinfoReport.dao.CntPayinfoReportDAO;
import com.forms.prms.web.reportmgr.cntPayinfoReport.domain.CntPayinfoReportBean;

@Service
public class CntPayinfoReportService {
	@Autowired
	private CntPayinfoReportDAO cDao;
	@Autowired
	private ExcelExportGenernalDeal exportDeal;
	//公共Excel导出更新dao
	@Autowired
	private ICommonExcelDealDao excelDao;
	
	//获得类实例
	public static CntPayinfoReportService getInstance(){
		return SpringUtil.getBean(CntPayinfoReportService.class);
	}

	/**
	 * 查询合同付款信息列表
	 * @return
	 */
	public List<CntPayinfoReportBean> getCntPayinfoReport(CntPayinfoReportBean bean){
		String dutyCode = WebHelp.getLoginUser().getDutyCode();
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		String org2Code = WebHelp.getLoginUser().getOrg2Code();

		HashMap<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("con", bean);
		paramMap.put("dutyCode", dutyCode);
		paramMap.put("org1Code", org1Code);
		paramMap.put("org2Code", org2Code);
		
		CntPayinfoReportDAO pageDao= PageUtils.getPageDao(cDao);
		CommonLogger.info("查询合同付款信息报表,CntPayinfoReportService,getCntPayinfoReport()");
		return pageDao.getCntPayinfoReport(paramMap);
	}
	
	//导出
	public List<CntPayinfoReportBean> exportExcute(Map<String, Object> paramMap) {
		
		return cDao.getCntPayinfoReport(paramMap);
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
	public String queryDownloadList(CntPayinfoReportBean con) throws Exception{
		CommonLogger.info("得到生成的一个任务ID,CntPayinfoReportService，queryDownloadList");
		String destFile = WebHelp.getSysPara("EXCEL_UPLOAD_FILE_DIR")+"/cntPayinfoReport.xlsx";
		Map<String,Object> map = new HashMap<String, Object>();
		String dutyCode = WebHelp.getLoginUser().getDutyCode();
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		String org2Code = WebHelp.getLoginUser().getOrg2Code();
		map.put("orgFlag", con.getOrgFlag());
		map.put("dutyCode", dutyCode);
		map.put("org1Code", org1Code);
		map.put("org2Code", org2Code);
		
		map.put("cntType", con.getCntType());
		map.put("feeType", con.getFeeType());
		map.put("projName", con.getProjName());
		map.put("matrType", con.getMatrType());
		map.put("cglCode", con.getCglCode());
		map.put("feeStartDate", con.getFeeStartDate());
		
		return exportDeal.execute("合同付款信息报表", "CNTPAYINFOREPORT",destFile,map);
	}
	
	

}
