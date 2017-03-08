package com.forms.prms.web.pay.payAdd.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringHelp;
import com.forms.platform.core.util.Tool;
import com.forms.prms.web.pay.payAdd.dao.PayAddDAO;
import com.forms.prms.web.pay.payAdd.domain.PayAddBean;
import com.forms.prms.web.report.domain.ThisReport;
import com.forms.prms.web.report.domain.ThisRow;
import com.forms.prms.web.report.domain.ThisTable;
import com.forms.prms.web.report.service.ReportInterface;
import com.forms.prms.web.user.domain.User;
import com.forms.prms.web.user.service.UserService;
/**
 * 
 * Title:PayPrintService
 * Description:为打印的表单设置值
 *
 * Coryright: formssi
 * @author wangtao
 * @project ERP
 * @date 2015-3-26
 * @version 1.0
 */
public class PayPrintService implements ReportInterface{
	@Autowired
	private PayAddDAO payAddDAO;
	public void prepare(ThisReport thisReport) throws Exception {
		CommonLogger.info("为正常付款封面打印做准备，PayPrintService，prepare");
		PayAddDAO payAddDAO = SpringHelp.getBean(PayAddDAO.class);
		UserService userService = SpringHelp.getBean(UserService.class);
		
		HttpServletRequest request = thisReport.getRequest();
		
		String payid = request.getParameter("payId");
		String userId = request.getParameter("userId");
		
		PayAddBean bean = payAddDAO.getPayReportData(payid);
		User user =userService.getUser(userId) ;
		
		ThisTable table = thisReport.getReportTable("pay");
	    ThisRow  thisRow = new ThisRow();
	    thisRow.setFieldValue("date_time",bean.getDateTime());
	    //当大写金额过长时，进行自动换行
//	    Integer ig=new Integer(NumberFormat.getInstance().format(bean.getPayAmt()));
	    String str=conver(String.format("%.2f", bean.getPayAmt()));
	    String str1=conver(String.format("%.2f", bean.getInvoiceAmt()));
	    StringBuffer sb1=new StringBuffer(str);
	    StringBuffer sb2=new StringBuffer(bean.getBankInfo());
	    StringBuffer sb3=new StringBuffer(str1);
//	    if(sb1.length()>=12){
//	    	sb1.insert(12,"/r/n");
//	    }
//	    if(sb2.length()>12){
//	    	sb2.insert(13,"/r/n");
//	    }
		
	    thisRow.setFieldValue("invoice_id", bean.getInvoiceId());
	    thisRow.setFieldValue("pay_id", bean.getPayId());
//	    thisRow.setFieldValue("dept_id", user.getDutyCode());
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
		
		thisRow.setFieldValue("invoice_amt", "￥"+String.format("%.2f", bean.getInvoiceAmt()));
		thisRow.setFieldValue("invoice_amt_",sb3.toString().replaceAll("/r/n", System.getProperty("line.separator")+System.getProperty("line.separator")));
		thisRow.setFieldValue("cnt_num",bean.getCntNum());
		thisRow.setFieldValue("provider_name",bean.getProviderName());
		thisRow.setFieldValue("invoice_memo",bean.getInvoiceMemo());
		//核销数据
//		String cancelData = this.getCancelData(bean.getPayId(),payAddDAO);
//		thisRow.setFieldValue("cancel_data",cancelData.toString().replaceAll("/r/n", System.getProperty("line.separator")+System.getProperty("line.separator")));
		//供应商开户行供应商账号合并为一个数据
		thisRow.setFieldValue("prov_act_no",bean.getProvActNo());
		thisRow.setFieldValue("bank_name_prov_act_no",sb2.toString().replaceAll("/r/n", System.getProperty("line.separator")+System.getProperty("line.separator"))+System.getProperty("line.separator")+System.getProperty("line.separator")+bean.getProvActNo());
		thisRow.setFieldValue("attachment_num",bean.getAttachmentNum().toString());
		String typeNameStr = this.getTypeNameStr(bean.getAttachmentTypeName());
		thisRow.setFieldValue("attachment_type_name",typeNameStr.replaceAll("/r/n", System.getProperty("line.separator")+System.getProperty("line.separator")));
		//		thisRow.setFieldValue("attachment_type_name",bean.getAttachmentTypeName().replaceAll("/r/n", System.getProperty("line.separator")+System.getProperty("line.separator")));
		
		//判断是否订单类正常付款
		if("0".equals(bean.getIsOrder()) && "1".equals(bean.getIsCreditNote())){//0是订单，1非订单
			//po单、po行号、数量
			List<PayAddBean> list = payAddDAO.getCancelData(bean.getPayId());
			ThisTable canceltable = thisReport.getReportTable("payCancel");
			for(int i=0;i<list.size();i++){
				ThisRow thisR = new ThisRow();
				thisR.setFieldValue("po_number", list.get(i).getPoNumber().toString());
				thisR.setFieldValue("po_lineno", list.get(i).getPoLineno().toString());
				thisR.setFieldValue("exec_num", list.get(i).getExecNum().toString());
				thisR.setFieldValue("exec_amt", list.get(i).getExecAmt().toString());
				thisR.setFieldValue("tax_code", list.get(i).getTaxCode().toString());
				thisR.setFieldValue("is_gdzc", list.get(i).getIsGdzc().toString());
				thisR.setFieldValue("matr_code", list.get(i).getMatrCode().toString()+"-"+list.get(i).getMatrName());
				canceltable.addRow(thisR);
			}
			//在此加入正常付款的预付款核销信息
			List<PayAddBean> payAdvCancelList=payAddDAO.getAdvCancelPayIds(bean.getPayId());
			ThisTable payAdvCancelTable = thisReport.getReportTable("payAdvCancel");
			for(int i=0;i<payAdvCancelList.size();i++){
				ThisRow thisR = new ThisRow();
				thisR.setFieldValue("advance_pay_id", payAdvCancelList.get(i).getAdvancePayId().toString());
				thisR.setFieldValue("cancel_amt",String.format("%.2f", payAdvCancelList.get(i).getCancelAmt()));
				thisR.setFieldValue("invoice_id",payAdvCancelList.get(i).getInvoiceId().toString());
				payAdvCancelTable.addRow(thisR);
			}
			
			//判断是否有核销数据（订单类）
			List<PayAddBean> isExixtList = payAddDAO.queryCancelDevices(bean.getPayId());
			if(!Tool.CHECK.isEmpty(isExixtList)){
				//预付款单号
//				String advCancelPayIdStrs =  payAddDAO.getAdvCancelPayIds(bean.getPayId());
//				thisRow.setFieldValue("adv_cancel_pay_id",advCancelPayIdStrs);
				List<PayAddBean> advList =  payAddDAO.getAdvCancelPayIds(bean.getPayId());
				ThisTable advtable = thisReport.getReportTable("advcancel");
				for(int i=0;i<advList.size();i++){
					ThisRow thisR = new ThisRow();
					thisR.setFieldValue("advance_pay_id", advList.get(i).getAdvancePayId().toString());
					thisR.setFieldValue("cancel_amt",String.format("%.2f", advList.get(i).getCancelAmt()));
					advtable.addRow(thisR);
				}
			}
		}
		//非订单类正常付款和贷项通知单
		else{
			List<PayAddBean> cglCodeAmtList=payAddDAO.getCglCodeAmt(bean.getPayId());
			ThisTable cglCodeAmtTable = thisReport.getReportTable("cglCodeAmt");
			String cglCodeAmt="";
			ThisRow thisR = new ThisRow();
			for(int i=0;i<cglCodeAmtList.size();i++){
				String cglAmtTemp="核算码:"+cglCodeAmtList.get(i).getCglCode().toString()+"    不含税金额:"+String.format("%.2f", cglCodeAmtList.get(i).getSubInvoiceAmt())+"     税额:"+String.format("%.2f", cglCodeAmtList.get(i).getAddTaxAmt());
				cglAmtTemp+=System.getProperty("line.separator")+System.getProperty("line.separator");
				cglCodeAmt=cglCodeAmt+cglAmtTemp;
				/*thisR.setFieldValue("cgl_code", cglCodeAmtList.get(i).getCglCode().toString());
				thisR.setFieldValue("sub_invoice_amt",String.format("%.2f", cglCodeAmtList.get(i).getSubInvoiceAmt()));
				thisR.setFieldValue("add_tax_amt",String.format("%.2f", cglCodeAmtList.get(i).getAddTaxAmt()));*/
				cglCodeAmtTable.addRow(thisR);
			}
			if(cglCodeAmt.length()>650){
				cglCodeAmt=cglCodeAmt.substring(0, 650)+"(内容超长.....)";
			}
			thisR.setFieldValue("cgl_code_amt", cglCodeAmt);
		}
		
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

	private String getCancelData(String payId,PayAddDAO payAddDAO) {
		List<PayAddBean> list = payAddDAO.getCancelData(payId);
		StringBuffer cancelData = new StringBuffer();
		String headstr = "PO单号/tPO行号/t数量/r/n";
		cancelData.append(headstr);
		for(int i=0;i<list.size();i++){
			PayAddBean pBean = list.get(i);
			String str = pBean.getPoNumber()+"/t"+pBean.getPoLineno()+"/t"+pBean.getExecNum();
			cancelData.append(str);
			cancelData.append("/r/n");
		}
		return cancelData.toString();
	}
	/**
	 * @methodName conver
	 * desc  将金额小写转换为大写
	 * 
	 * @param s
	 * @return
	 */
	public String conver(String s){
		final String[] str1 = {"元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿"};  
	    final String[] str2 = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};  
	    final String[] str3 = {"角", "分"};
	    //将字符串分割为小数部分与整数部分
	    String temp0 = s.substring(0,1);
	    String temp1=null;  
	    String temp2=null;
	    String temp3=null;
	    
	    if("-".equals(temp0)){
        temp1 = s.substring(1, s.indexOf("."));  
        temp2 = s.substring(s.indexOf(".")+1);
        temp3 = "负";
	    }else{
	    temp1 = s.substring(0, s.indexOf("."));  
	    temp2 = s.substring(s.indexOf(".")+1);  
	    temp3 = "";
	    }
        int k = -1;  
        int len = -1;  
        StringBuffer sb = new StringBuffer();  
//转换整数部分
        len = temp1.length()-1;
        try{
        if(temp1.length()<10){
        //如果整数部分小于等于9位则按下面方式转换
        for(int i=0; i<temp1.length(); i++)  
        {  
            k = Integer.parseInt(temp1.substring(i, i+1));  
            sb.append(str2[k]).append(str1[len-i]);
            
        }
        sb.insert(0, temp3);
        }else{
        //如果整数部分大于9位则按下面方式转换
        	String s1=temp1.substring(0,temp1.length()-8);
        	for(int i=0;i<s1.length();i++){
        		if(i==s1.length()-1){
        			k = Integer.parseInt(temp1.substring(i, i+1));  
                    sb.append(str2[k]).append("亿");
        		}else{
        		 k = Integer.parseInt(temp1.substring(i, i+1));  
                 sb.append(str2[k]).append(str1[s1.length()-1-i]);
        		}
                 
        	}
        	for(int i=s1.length(); i<temp1.length(); i++)  
            {  
                k = Integer.parseInt(temp1.substring(i, i+1));  
                sb.append(str2[k]).append(str1[temp1.length()-1-i]);
                }
        	sb.insert(0, temp3);
        }
//转换小数部分
        for(int i=0; i<2; i++)  
        {  
        	if(i==1){
        			if("".equals(temp2.substring(1))){
        			sb.append(str2[0]).append(str3[1]);
        			}else{
        				k = Integer.parseInt(temp2.substring(i, i+1));
        	            sb.append(str2[k]).append(str3[i]); }
        	}else{
            k = Integer.parseInt(temp2.substring(i, i+1));
            sb.append(str2[k]).append(str3[i]); }
        }
        }catch (Exception e) {
        	e.printStackTrace();
//        	CommonLogger.info("数据有误，大小写转换不成功");
        	return null;
		}
        return sb.toString(); 
    }
//	public static void main(String[] args) {
//		String string =new PayPrintService().conver("1000000000.11");
//		System.out.println(string);
//	}
}
