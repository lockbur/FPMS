package com.forms.prms.web.reportmgr.preproInfoReport.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.reportmgr.preproInfoReport.domain.PreproInfoReportBean;

@Repository
public interface PreproInfoReportDAO {
	
	/**
	 * 查看预提待摊报表
	 */
	public List<PreproInfoReportBean> getPreproinfoReport(Map<String, Object> map);

}
