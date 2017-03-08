package com.forms.prms.web.cleanpaydeal.cleanpayquery.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringHelp;
import com.forms.prms.web.cleanpaydeal.cleanpay.domain.CleanPayBean;
import com.forms.prms.web.cleanpaydeal.cleanpayquery.dao.CleanPayQueryDao;
import com.forms.prms.web.report.domain.ThisReport;
import com.forms.prms.web.report.domain.ThisRow;
import com.forms.prms.web.report.domain.ThisTable;
import com.forms.prms.web.report.service.ReportInterface;
import com.forms.prms.web.user.domain.User;
import com.forms.prms.web.user.service.UserService;
import com.forms.prms.web.pay.payAdd.dao.PayAddDAO;
import com.forms.prms.web.pay.payAdd.service.PayPrintService;
/**
 * 
 * Title:CleanPayQueryServicePrint
 * Description:暂收结清封面打印service
 *
 * Coryright: formssi
 * @author wangtao
 * @project HQERP
 * @date 2015-5-6
 * @version 1.0
 */
public class CleanPayQueryServicePrint implements ReportInterface{
	@Autowired
	private PayAddDAO payAddDAO;
	@Override
	public void prepare(ThisReport thisReport) throws Exception {
		CommonLogger.info("暂收结清封面打印信息生成，CleanPayQueryServicePrint，prepare");
		CleanPayQueryDao cleanpayquerydao = SpringHelp.getBean(CleanPayQueryDao.class);
		UserService userService = SpringHelp.getBean(UserService.class);
		
		HttpServletRequest request = thisReport.getRequest();
		
		String sortid = request.getParameter("sortId");
		String normalPayId = request.getParameter("normalPayId");
		String userId = request.getParameter("userId");
		
		CleanPayBean bean = cleanpayquerydao.getCleanPayReportData(sortid,normalPayId);
		CleanPayBean befCleanBean = cleanpayquerydao.getBeforeCleanAmt(sortid,normalPayId);
		User user =userService.getUser(userId) ;
		
		ThisTable table = thisReport.getReportTable("cleanpay");
	    ThisRow  thisRow = new ThisRow();
	    
	    PayPrintService ps=new PayPrintService();
	    thisRow.setFieldValue("date_time",bean.getDateTime());
	   //部门名称
	    thisRow.setFieldValue("dept_name", user.getDutyName());
	    //用户姓名
		thisRow.setFieldValue("oper_name", user.getUserName());
		//正常付款单号
		thisRow.setFieldValue("normal_pay_id", bean.getNormalPayId());
		//子序号
		thisRow.setFieldValue("sort_id",bean.getSortId());
		//付款单号+子序号
		thisRow.setFieldValue("pay_sort_id", bean.getNormalPayId()+bean.getSortId());
		//未结清金额小写
		thisRow.setFieldValue("unclean_Amt", "￥"+String.format("%.2f",bean.getUncleanAmt()));
		//未结清金额大写
		thisRow.setFieldValue("UNCLEAN_AMT_", "人民币"+"  "+ps.conver(String.format("%.2f", bean.getUncleanAmt())));
		//结清金额小写
		thisRow.setFieldValue("clean_Amt", "￥"+String.format("%.2f",bean.getCleanAmt()));
		//结清金额大写
		thisRow.setFieldValue("CLEAN_AMT_", "人民币"+"  "+ps.conver(String.format("%.2f", bean.getCleanAmt())));
		//剩余暂收金额小写
		Double d=befCleanBean.getBefcleanAmt().doubleValue();
		thisRow.setFieldValue("Amt","￥"+String.format("%.2f",d));
		//剩余暂收金额大写
		thisRow.setFieldValue("AMT_","人民币"+"  "+ps.conver(String.format("%.2f", d)));
		//发票号
		thisRow.setFieldValue("invoice_id",bean.getInvoiceId());
		//合同号
		thisRow.setFieldValue("cnt_num",bean.getCntNum());
		//结清原因
		thisRow.setFieldValue("clean_reason",bean.getCleanReason());
		table.addRow(thisRow);
	}

}
