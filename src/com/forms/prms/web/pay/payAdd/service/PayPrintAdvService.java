package com.forms.prms.web.pay.payAdd.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringHelp;
import com.forms.platform.web.WebUtils;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.pay.payAdd.dao.PayAddDAO;
import com.forms.prms.web.pay.payAdd.domain.PayAddBean;
import com.forms.prms.web.report.domain.ThisReport;
import com.forms.prms.web.report.domain.ThisRow;
import com.forms.prms.web.report.domain.ThisTable;
import com.forms.prms.web.report.service.ReportInterface;
import com.forms.prms.web.user.domain.User;
import com.forms.prms.web.user.service.UserService;
import com.forms.prms.web.pay.payAdd.service.PayPrintService;
@Service
public class PayPrintAdvService implements ReportInterface{
	public void prepare(ThisReport thisReport) throws Exception {
		CommonLogger.info("为预付款封面打印做准备，PayPrintAdvService，prepare");
		PayAddDAO payAddDAO = SpringHelp.getBean(PayAddDAO.class);
		UserService userService = SpringHelp.getBean(UserService.class);
		
		HttpServletRequest request = thisReport.getRequest();
		
		String payid = request.getParameter("payId");
		String userId = request.getParameter("userId");
		
		PayAddBean bean = payAddDAO.getAdvPayReportData(payid);
		User user =userService.getUser(userId) ;
		
		ThisTable table = thisReport.getReportTable("payAdv");
	    ThisRow  thisRow = new ThisRow();
		thisRow.setFieldValue("date_time",bean.getDateTime());
	    //当大写金额过长时，进行自动换行
	    PayPrintService ps=new PayPrintService();
	    String str=ps.conver(String.format("%.2f", bean.getPayAmt()));
	    StringBuffer sb1=new StringBuffer(str);
	    StringBuffer sb2=new StringBuffer(bean.getBankInfo());
//	    if(sb1.length()>=12){
//	    	sb1.insert(12,"/r/n");
//	    }
//	    if(sb2.length()>12){
//	    	sb2.insert(13,"/r/n");
//	    }
		
	    thisRow.setFieldValue("invoice_id", bean.getInvoiceId());
	    thisRow.setFieldValue("pay_id", bean.getPayId());
	    thisRow.setFieldValue("dept_id", user.getDutyCode());
	    //部门id和部门名称合并为一个数据
	    thisRow.setFieldValue("dept_id", user.getDutyCode());
	    thisRow.setFieldValue("dept_name", user.getDutyName());
	    thisRow.setFieldValue("dept_id_name", user.getDutyCode()+" "+"-"+" "+user.getDutyName());
	  //经办人ID及姓名合并为一个数据
		thisRow.setFieldValue("oper_id", user.getUserId());
		thisRow.setFieldValue("oper_name", user.getUserName());
		thisRow.setFieldValue("oper_id_name", user.getUserId()+" "+"-"+" "+user.getUserName());
		
		thisRow.setFieldValue("pay_amt", "￥"+String.format("%.2f", bean.getPayAmt()));
		thisRow.setFieldValue("pay_amt_",sb1.toString().replaceAll("/r/n", System.getProperty("line.separator")+System.getProperty("line.separator")));
		thisRow.setFieldValue("cnt_num",bean.getCntNum());
		thisRow.setFieldValue("provider_name",bean.getProviderName());
		//供应商开户行供应商账号合并为一个数据
		thisRow.setFieldValue("prov_act_no",bean.getProvActNo());
		thisRow.setFieldValue("bank_name_prov_act_no",sb2.toString().replaceAll("/r/n", System.getProperty("line.separator")+System.getProperty("line.separator"))+System.getProperty("line.separator")+System.getProperty("line.separator")+bean.getProvActNo());
		thisRow.setFieldValue("attachment_num",bean.getAttachmentNum().toString());
		String typeNameStr = this.getTypeNameStr(bean.getAttachmentTypeName());
		thisRow.setFieldValue("attachment_type_name",typeNameStr.replaceAll("/r/n", System.getProperty("line.separator")+System.getProperty("line.separator")));
//		thisRow.setFieldValue("attachment_type_name",bean.getAttachmentTypeName().replaceAll("/r/n", System.getProperty("line.separator")+System.getProperty("line.separator")));
		thisRow.setFieldValue("invoice_memo",bean.getInvoiceMemo());
		//得到数据库时间
		
		table.addRow(thisRow);
		
	}
	private String getTypeNameStr(String attachmentTypeName) {
		String[] str = attachmentTypeName.split("/r/n");
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<str.length;i++){
			sb.append(str[i]);
			if(i!=0 && i!=str.length-1 && (i+1)%2==0){
				sb.append("/r/n");
			}else if(i!=str.length-1){
				sb.append("/t");
			}
		}
		return sb.toString().replaceAll("/t","、");
	}
}
