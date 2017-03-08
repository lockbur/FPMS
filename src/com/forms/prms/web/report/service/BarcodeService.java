package com.forms.prms.web.report.service;

import javax.servlet.http.HttpServletRequest;

import com.forms.prms.web.report.domain.ThisReport;
import com.forms.prms.web.report.domain.ThisRow;
import com.forms.prms.web.report.domain.ThisTable;

public class BarcodeService implements ReportInterface{

	@Override
	public void prepare(ThisReport thisReport) throws Exception {
		HttpServletRequest request = thisReport.getRequest();
		String msg = request.getParameter("msg");
		
		
		ThisTable table = thisReport.getReportTable("data");
	    ThisRow  thisRow = new ThisRow();
		thisRow.setFieldValue("msg", msg);
		table.addRow(thisRow);
	}

}
