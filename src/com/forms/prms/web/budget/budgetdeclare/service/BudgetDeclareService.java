package com.forms.prms.web.budget.budgetdeclare.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.DateUtil;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.loadthread.ExcelImportGenernalDeal;
import com.forms.prms.tool.fileUtils.service.CommonFileUtils;
import com.forms.prms.web.budget.budgetdeclare.dao.BudgetDeclareDAO;
import com.forms.prms.web.budget.budgetdeclare.domain.BudgetDeclareBean;
import com.forms.prms.web.budget.budgetdeclare.domain.ExcelImportTaskSummaryBean;
import com.forms.prms.web.budget.budgetdeclare.domain.StockBudgetBean;

@Service
public class BudgetDeclareService {
	
	@Autowired
	public BudgetDeclareDAO dao;
	
	@Autowired
	public ExcelImportGenernalDeal importGenernalDeal;
	
	
	/**
	 * 获得BudgetService实例(使用于Excel导入的业务处理)
	 * @return
	 */
	public static BudgetDeclareService getInstance(){
		return SpringUtil.getBean(BudgetDeclareService.class);				
	}
	
	/**
	 * 新增预算申报的模板查询
	 * @param budgetDeclareBean
	 * @return
	 */
	public List<BudgetDeclareBean> queryTemp(BudgetDeclareBean budgetDeclareBean) {
		BudgetDeclareDAO pageDAO = PageUtils.getPageDao(dao);
		return pageDAO.queryTemp(budgetDeclareBean);
	}
	
	/**
	 * 预算计划导出下载操作
	 * @param response
	 * @param budgetDeclareBean
	 * @throws Exception
	 */
	public void downloadTemp(HttpServletResponse response, BudgetDeclareBean budgetDeclareBean) throws Exception{
		CommonLogger.info("预算申报模块-导出预算计划操作,主键信息("+budgetDeclareBean.getTmpltId()+"),BudgetDeclareService,downloadTemp");
		BudgetDeclareBean temp = dao.queryTempById(budgetDeclareBean.getTmpltId());
		if(temp!=null){
			File file = new File(temp.getServerFile());
			if(!file.exists()){
				throw new Exception("模板文件【"+temp.getServerFile()+"】不存在");
			}
			
			response.setContentType("application/x-msdownload;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(temp.getSourceFilename().getBytes("GB2312"), "ISO-8859-1"));
			
			InputStream in = null;
			OutputStream out = null;
			try{
				in = new FileInputStream(file);
				out = response.getOutputStream();
				IOUtils.copy(in, out);
			}catch (Exception e) {
				throw e;
			}finally{
				IOUtils.closeQuietly(in);
				IOUtils.closeQuietly(out);
			}
			
		}
		
	}
	
	/**
	 * 预算申报Excel文件上传保存操作
	 * @param budgetDeclareBean
	 * @return
	 * @throws Exception
	 */
	public ExcelImportTaskSummaryBean applyImport(BudgetDeclareBean budgetDeclareBean) throws Exception{
		MultipartFile  file = budgetDeclareBean.getImpFile();
		budgetDeclareBean.setSourceFilename(file.getOriginalFilename());
		//1.写本地文件
		CommonFileUtils.FileDirCreate(WebHelp.getSysPara("EXCEL_UPLOAD_FILE_DIR"));
		String serverFile = WebHelp.getSysPara("EXCEL_UPLOAD_FILE_DIR")+"/"+DateUtil.getDateTimeStrNo()+file.getOriginalFilename();
		OutputStream out = null;
		InputStream in = null;
		
		try {
			out = new FileOutputStream(serverFile);
			in = file.getInputStream();
			IOUtils.copy(in, out);
		} catch (IOException e) {
			throw e; 
		}finally{
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
		budgetDeclareBean.setServerFile(serverFile);
		//2.写表
		dao.insertBudgetWriteHeader(budgetDeclareBean);
		//3.调用导入组件
		
		String taskId = importExcel(budgetDeclareBean);
		
		CommonLogger.info("预算申报模块-上传预算计划Excel文件操作,(操作模板ID:"+budgetDeclareBean.getTmpltId()+",导入Excel任务ID:"+taskId+"),BudgetDeclareService,applyImport");
		//4.查询导入任务队列情况
		return dao.getTaskSummary(taskId);
		
	}
	
	/**
	 * 调用导入Excel组件操作
	 * @param 	budget	导入Excel相关的预算模板
	 * @return			Excel导入操作是否已执行(与执行结果无关)
	 * @throws 	Exception
	 */
	public String importExcel(BudgetDeclareBean budget) throws Exception{
		
		//1.Excel路径处理
			//取得Excel导入到服务器的原始路径
		String excelUrl = budget.getServerFile();
			//根据原始路径信息获取导入Excel所需的文件文件夹路径、文件名+后缀
		String filePath = excelUrl.substring(0,excelUrl.lastIndexOf("/")+1);
		String fileName = excelUrl.substring(excelUrl.lastIndexOf("/")+1,excelUrl.length());
		//2.Excel导入参数处理
		String tmpltId = budget.getTmpltId();
		String excelDesc = "";
		if("0".equals(budget.getDataAttr())){
			//资产类
			budget.setConfigId("BUDGET_IMPORT_ASSET");
			excelDesc = "Import Excel "+",预算模板ID:【"+tmpltId+"】";
		}else if("1".equals(budget.getDataAttr())){
			//费用类
			budget.setConfigId("BUDGET_IMPORT_FEE");
			excelDesc = "Import Excel "+",预算模板ID:【"+tmpltId+"】";
		}
		Map<String,String> beans = new HashMap<String,String>();
		beans.put("tmpltId", tmpltId);
		beans.put("dutyCode", budget.getDutyCode());
		String taksId = importGenernalDeal.execute(filePath, fileName, excelDesc, new String[]{budget.getConfigId()}, beans);	//模板ID/任务流水号、Excel文件路径、Excel文件名、Excel描述、ConfigId(数组)
		return taksId;
	}
	
	
	/**
	 * 已立项未签订合同项目列表                                                          
	 * @param stockBudgetBean
	 * @return
	 */
	public List<StockBudgetBean> getUnsignContList(StockBudgetBean stockBudgetBean) {
		BudgetDeclareDAO pageDao = PageUtils.getPageDao(dao);
		return pageDao.getUnsignContList(stockBudgetBean);
	}

	/**
	 * 已签合同未执行完毕项目列表
	 * @param stockBudgetBean
	 * @return
	 */
	public List<StockBudgetBean> getUnexecuteList(StockBudgetBean stockBudgetBean) {
		BudgetDeclareDAO pageDao = PageUtils.getPageDao(dao);
		return pageDao.getUnexecuteList(stockBudgetBean);
	}
	
	/**
	 * 确认年度需确认费用
	 * @param stockBudgetBean
	 * @return
	 */
	public String confirmBudget(StockBudgetBean stockBudgetBean) {
		CommonLogger.info("预算申报模块-确认年度预算操作,BudgetDeclareService,confirmBudget");
		if(!Tool.CHECK.isEmpty(stockBudgetBean.getUnionPrimaryKey())) {
			String[] keys = stockBudgetBean.getUnionPrimaryKey().split(",");
			String[] amts = stockBudgetBean.getNeedConfirmAmt().split(",");
			if(keys.length != amts.length) {
				return "确认费用含有空值，失败";
			}
			for(int i=0; i<keys.length; i++) {
				StockBudgetBean sBudgetBean = new StockBudgetBean();
				sBudgetBean.setConfirmOper(stockBudgetBean.getConfirmOper());
				sBudgetBean.setNeedConfirmAmt(amts[i]);
				sBudgetBean.setProjId(keys[i].split("~~")[0]);
				sBudgetBean.setCntNum(keys[i].split("~~")[1]);
				sBudgetBean.setDutyCode(keys[i].split("~~")[2]);
				sBudgetBean.setMatrCode(keys[i].split("~~")[3]);
				dao.confirmBudget(sBudgetBean);
				dao.addFirstAudit(sBudgetBean);
			}
		}
		return null;
	}
	
	
	public void insertFeeBudgetDetail(BudgetDeclareBean budgetDeclareBean){
		dao.insertFeeBudgetDetail(budgetDeclareBean);
	}
	
	public void insertAssetBudgetDetail(BudgetDeclareBean budgetDeclareBean){
		dao.insertAssetBudgetDetail(budgetDeclareBean);
	}
	
	public void updateBudgetWriteHeader(BudgetDeclareBean budgetDeclareBean){
		dao.updateBudgetWriteHeader(budgetDeclareBean);
	}
}
