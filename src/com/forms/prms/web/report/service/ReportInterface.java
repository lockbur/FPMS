package com.forms.prms.web.report.service;

import com.forms.prms.web.report.domain.ThisReport;

public interface ReportInterface {

	//准备报表数据
	public void prepare(ThisReport thisReport) throws Exception;
}
