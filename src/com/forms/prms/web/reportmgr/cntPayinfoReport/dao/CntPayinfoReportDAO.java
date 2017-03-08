package com.forms.prms.web.reportmgr.cntPayinfoReport.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.reportmgr.cntPayinfoReport.domain.CntPayinfoReportBean;

@Repository
public interface CntPayinfoReportDAO {
	
	/**
	 * 查询合同付款信息列表
	 * @return
	 */
	public List<CntPayinfoReportBean> getCntPayinfoReport(Map<String, Object> map);
}
