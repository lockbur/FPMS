package com.forms.prms.web.amortization.abnormalDataMgr.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.exportthread.ExcelExportGenernalDeal;
import com.forms.prms.web.amortization.abnormalDataMgr.dao.AbnorDataMgrDAO;
import com.forms.prms.web.amortization.abnormalDataMgr.domain.CommonTidQueryBean;
import com.forms.prms.web.amortization.abnormalDataMgr.domain.TidAccountBean;
import com.forms.prms.web.amortization.abnormalDataMgr.domain.TidApInvoiceBean;
import com.forms.prms.web.amortization.abnormalDataMgr.domain.TidApPayBean;
import com.forms.prms.web.amortization.abnormalDataMgr.domain.TidCglAmtBean;
import com.forms.prms.web.amortization.abnormalDataMgr.domain.TidOrderBean;
import com.forms.prms.web.budget.budgetplan.service.BudgetPlanService;

/**
 * Title:		AbnorDataMgrService
 * Description:	异常数据查询Service层
 * Copyright: 	formssi
 * @author：		HQQ
 * @project：	ERP
 * @date：		2015-04-08
 * @version： 	1.0
 */
@Service
public class AbnorDataMgrService {

	@Autowired
	private AbnorDataMgrDAO abnorDataDao;
	
	//用于创建导出文件的路径(具体路径见配置文件config.properties)
	@Autowired
	private BudgetPlanService budgetService;
	
	//Excel导出工具类处理Service
	@Autowired
	private ExcelExportGenernalDeal exportDeal;				
	
	//获取AbnorDataMgrService对象实例，用于Excel导出Deal类中
	public static AbnorDataMgrService getInstance(){
		return SpringUtil.getBean(AbnorDataMgrService.class);
	}
	
	/**
	 *	异常数据	  分页数据查询 
	 */
	//总帐凭证接口数据-分页查询
	public List<TidAccountBean> queryTidAccount(Map<String , Object> mapObj){
		AbnorDataMgrDAO dao = PageUtils.getPageDao(abnorDataDao);
		return dao.getTidAccountQueryList(mapObj);
	}
	
	//AP发票接口数据-分页查询
	public List<TidApInvoiceBean> queryTidApInvoice(Map<String , Object> mapObj){
		AbnorDataMgrDAO dao = PageUtils.getPageDao(abnorDataDao);
		return dao.getTidApInvoiceQueryList(mapObj);
	}
	
	//AP付款接口数据-分页查询
	public List<TidApPayBean> queryTidApPay(Map<String , Object> mapObj){
		AbnorDataMgrDAO dao = PageUtils.getPageDao(abnorDataDao);
		return dao.getTidApPayQueryList(mapObj);
	}
	
	//科目余额数据-分页查询
	public List<TidCglAmtBean> queryTidCglAmt(Map<String , Object> mapObj){
		AbnorDataMgrDAO dao = PageUtils.getPageDao(abnorDataDao);
		return dao.getTidCglAmtQueryList(mapObj);
	}
	
	//订单接口数据-分页查询
	public List<TidOrderBean> queryTidOrder(Map<String , Object> mapObj){
		AbnorDataMgrDAO dao = PageUtils.getPageDao(abnorDataDao);
		return dao.getTidOrderQueryList(mapObj);
	}
	
	/**
	 *	异常数据	  全数据查询 
	 */
	//总帐凭证接口数据-全查询
	public List<TidAccountBean> queryTidAccountAllData(Map<String , Object> mapObj){
		return abnorDataDao.getTidAccountQueryList(mapObj);
	}
	
	//AP发票接口数据-全查询
	public List<TidApInvoiceBean> queryTidApInvoiceAllData(Map<String , Object> mapObj){
		return abnorDataDao.getTidApInvoiceQueryList(mapObj);
	}
	
	//AP付款接口数据-全查询
	public List<TidApPayBean> queryTidApPayAllData(Map<String , Object> mapObj){
		return abnorDataDao.getTidApPayQueryList(mapObj);
	}
	
	//科目余额数据-全查询
	public List<TidCglAmtBean> queryTidCglAmtAllData(Map<String , Object> mapObj){
		return abnorDataDao.getTidCglAmtQueryList(mapObj);
	}
	
	//订单接口数据-全查询
	public List<TidOrderBean> queryTidOrderAllData(Map<String , Object> mapObj){
		return abnorDataDao.getTidOrderQueryList(mapObj);
	}
	
	/**
	 * @methodName dataExport
	 * 		查询的异常数据导出功能：
	 * 			内部逻辑根据传输参数处理查询相关表的相关异常状态的数据，调用Excel导出组件进行查询结果数据的Excel导出操作
	 * @param queryType		查询类别(决定查询对应的表)
	 * @param useFlag	查询状态(即异常状态，对应表中字段USE_FLAG)
	 * @return				导出任务流水ID：ExportTaskID 或  null
	 * @throws Exception 
	 */
	public String dataExport(String queryType , String useFlag , CommonTidQueryBean commonQueryBean) throws Exception{
		//导出对应的Excel处理配置
		String exportConf = "";
		//导出文件的路径(前置路径)
		String downloadPath = WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR");
		//导出Excel文件的详细路径+文件名
		String destFile = "";
		//导出Excel任务的任务描述
		String excelDesc = "";
		
		//查询的异常状态的描述
		String useFlagDesc = "";
		if("0".equals(useFlag)){
			useFlagDesc = "异常状态";
		}else if("1".equals(useFlag)){
			useFlagDesc = "正常状态";
		}else{
			useFlag = "";
			commonQueryBean.setUseFlag(useFlag);
			useFlagDesc = "全部状态";
		}
		//查询数据对应表的表名
		String queryTableName = "";
		
		//Excel导出工具类调用传递参数Map(用于保存传递参数)
		Map<String,Object> paramsMap = new HashMap<String, Object>();
		
		//根据需要创建导出全路径
		budgetService.createFilePath(new File(downloadPath));		
		
		if("1".equals(queryType)){
			//总帐凭证接口数据查询
			queryTableName = "TID_ACCOUNT";
			excelDesc  = "总账凭证接口["+useFlagDesc+"]查询数据导出";
			exportConf = "ABNORDATA_EXPORT_TID_ACCOUNT";					//Excel导出配置文件(export-config.xml)中定义    【注意：此值如果超过30个字符会报错】
		}else if("2".equals(queryType)){
			//AP发票接口数据查询
			queryTableName = "TID_AP_INVOICE";
			excelDesc  = "AP发票接口["+useFlagDesc+"]查询数据导出";
			exportConf = "ABNORDATA_EXPORT_TID_AP_INVOI";
		}else if("3".equals(queryType)){
			//AP付款接口数据查询
			queryTableName = "TID_AP_PAY";
			excelDesc  = "AP付款接口["+useFlagDesc+"]查询数据导出";
			exportConf = "ABNORDATA_EXPORT_TID_AP_PAY";
		}else if("4".equals(queryType)){
			//科目余额数据查询
			queryTableName = "TID_CGL_AMT";
			excelDesc  = "科目余额["+useFlagDesc+"]查询数据导出";
			exportConf = "ABNORDATA_EXPORT_TID_CGL_AMT";
		}else if("5".equals(queryType)){
			//订单接口数据查询
			queryTableName = "TID_ORDER";
			excelDesc  = "订单接口["+useFlagDesc+"]查询数据导出";
			exportConf = "ABNORDATA_EXPORT_TID_ORDER";
		}
		
		//组合：导出Excel文件的全路径+导出文件名
		destFile = downloadPath+"/"+queryTableName+".xlsx";
		
		//放置Excel导出的传递参数
		paramsMap.put("queryType", queryType);
		paramsMap.put("queryState", useFlag);
		paramsMap.put("commonQueryBean", commonQueryBean);
		
		//判断：Excel导出描述和Excel处理配置不为空时，才执行Excel导出操作，否则返回null
		if("".equals(excelDesc)&&"".equals(exportConf)){
			CommonLogger.info("【"+this.getClass().getName()+"-"+Thread.currentThread().getStackTrace()[1].getMethodName()+"】Excel导出配置参数为空，请检查！");
			return null;
		}else{
			//调用参数说明：任务描述、export-config调用配置、输出目标路径+文件名、导出所需的参数传递
			return exportDeal.execute(excelDesc,exportConf,destFile,paramsMap);
		}
	}
	
}
