package com.forms.prms.web.budget.budgetInspect.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.exportthread.ExcelExportGenernalDeal;
import com.forms.prms.tool.fileUtils.service.CommonFileUtils;
import com.forms.prms.web.budget.bgtImport.dao.BudgetImportDao;
import com.forms.prms.web.budget.budgetInspect.dao.BudgetInspectDao;
import com.forms.prms.web.budget.budgetInspect.domain.BudgetManageBean;
import com.forms.prms.web.budget.budgetInspect.domain.MatrBean;
import com.forms.prms.web.budget.budgetInspect.domain.SumCnt;
import com.forms.prms.web.budget.budgetInspect.domain.SumCntDetail;
import com.forms.prms.web.sysmanagement.montindex.domain.MontIndexBean;

@Service
public class BudgetInspectService {
	
	@Autowired
	BudgetInspectDao dao;
	@Autowired
	private ExcelExportGenernalDeal exportDeal;				//Excel导出Service
	@Autowired
	private BudgetImportDao budgetImportDao;
	
	//获得类实例
	public static BudgetInspectService getInstance(){
		return SpringUtil.getBean(BudgetInspectService.class);
	}
	
	//第三个参数queryByPage为true时使用分页查询，false时不使用分页查询(作用于全部查询数据导出操作)
	public List<SumCnt> getSumCntInfo(SumCnt sumCnt , boolean queryByPage , Map<String , String> mapParams) throws Exception{
		String dutyCode = Tool.CHECK.isEmpty((String)mapParams.get("dutyCode")) ? WebHelp.getLoginUser().getDutyCode() : (String)mapParams.get("dutyCode");
		String org1Code = Tool.CHECK.isEmpty((String)mapParams.get("org1Code")) ? WebHelp.getLoginUser().getOrg1Code() : (String)mapParams.get("org1Code");
		String org2Code = Tool.CHECK.isEmpty((String)mapParams.get("org2Code")) ? WebHelp.getLoginUser().getOrg2Code() : (String)mapParams.get("org2Code");
		
		BudgetInspectDao pageDao = PageUtils.getPageDao(dao);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sumCnt", sumCnt);
		paramMap.put("dutyCode", dutyCode);
		paramMap.put("org1Code", org1Code);
		paramMap.put("org2Code", org2Code);
		CommonLogger.info("查询对应合同号为"+sumCnt+"的合同预算信息, BudgetInspectService, getSumCntInfo");
		if(queryByPage){
			return pageDao.getSumCntInfo(paramMap);
		}else{
			return dao.getSumCntInfo(paramMap);
		}
		
	}
	
	
	/**
	 * 得到合同预算的列表
	 * @param sumCnt
	 * @return
	 */
	public List<SumCnt> getSumCntInfo(SumCnt sumCnt) throws Exception{
		//默认使用分页查询
		return getSumCntInfo(sumCnt ,true , new HashMap<String , String>());
	}
	/**
	 * 获取合同详细信息
	 * @param cntNum
	 * @return
	 */
	public List<SumCntDetail> getSumCntDetail(String scId) throws Exception{
		CommonLogger.info("查询主键为"+scId+"的合同预算记录的详细信息, BudgetInspectService, getSumCntDetail");
		return dao.getCntDetail(scId);
	}
	
	public List<BudgetManageBean> queryBudgetmanageBeans(BudgetManageBean bmBean) throws Exception{
		CommonLogger.info("预算监控模块-预算维度查询操作,BudgetInspectService,queryBudgetmanageBeans");
		BudgetInspectDao page = PageUtils.getPageDao(dao);
		CommonLogger.info("查询用户所在一级行下满足条件的预算记录, BudgetInspectService, querBudgetmanageBeans");
		return page.queryBudgetmanageBeans(bmBean);
	}
	
	 
	/**
	 * 根据bgtId查询预算下达明细
	 * @param bgtId
	 * @return
	 */
	public List<BudgetManageBean> view(String bgtId) throws Exception{
		CommonLogger.info("根据预算id为"+bgtId+"查询预算的下达明细信息, BudgetInspectService, view");
		return dao.view(bgtId);
	}
	/**
	 * 根据唯一Id查询预算下达明细
	 * @param bgtId
	 * @return
	 */
	public BudgetManageBean sumDetail(String sdId){
		CommonLogger.info("根据预算唯一性id为"+sdId+"查询预算的下达明细信息, BudgetInspectService, sumDetail");
		return dao.sumDetail(sdId);
	}
	/**
	 * 根据用户所在行或者指标类型获取监控指标名称
	 * @param bmBaBean
	 * @return
	 */
	public List<BudgetManageBean> getMontName(BudgetManageBean bmBean) throws Exception{
		CommonLogger.info("预算监控模块-预算维度-监控指标三级级联下拉框,主键信息("+bmBean.getMontType()+"),BudgetInspectService,queryBudgetmanageBeans");
		//如果传过来为省行
		if("1".equals(bmBean.getMontCode())){
			String org1Code=WebHelp.getLoginUser().getOrg1Code();
			bmBean.setBgtOrgcode(org1Code);
		}
		//如果为分行
		else if("2".equals(bmBean.getMontCode())){
			String org2Code=WebHelp.getLoginUser().getOrg2Code();
			bmBean.setBgtOrgcode(org2Code);
		}
		CommonLogger.info("根据用户所在行或指标类型查询指标名称, BudgetInspectService, getMontName");
		return dao.getMontName(bmBean);
	}
	/**
	 *根据物料编码或者物料名称或者核算码等等获取物料信息
	 *@param mBean
	 *@return 
	 */
	public List<MatrBean> getMatrName(MatrBean mBean) throws Exception{
		CommonLogger.info("预算监控模块-预算维度-物料名称弹出框获取物料,BudgetInspectService,getMatrName");
		BudgetInspectDao page = PageUtils.getPageDao(dao); 
		return page.getMatrName(mBean);
	}
	
	
	//预算维度Total查询数据-Excel导出
	//exportType=1为total查询，exportType=2为下达明细查询，exportType=3为使用明细查询
	public String bgtTotalDataExport(BudgetManageBean bmBean) throws Exception{
		CommonLogger.info("预算监控模块-预算维度Total查询数据导出操作-START,BudgetInspectService,bgtTotalDataExport()");
		//1.创建导出路径
		String downloadPath =WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR");		//导出文件的路径
		CommonFileUtils.FileDirCreate(downloadPath);
		//2.组织传输参数
		String destFile = downloadPath+"/.xlsx";						//文件导出详细路径+文件名
		String excelDesc = "";
		String exportConf = "";
		Map<String,Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("exportType", bmBean.getExportType());
		paramsMap.put("org1Code", WebHelp.getLoginUser().getOrg1Code());
		paramsMap.put("org2Code", WebHelp.getLoginUser().getOrg2Code());
		paramsMap.put("orgType", bmBean.getOrgType());
		if("1".equals(bmBean.getExportType())){
			//执行Total查询数据导出
			excelDesc = "预算维度数据导出";
			exportConf = "BUDGETINS_BGT_TOTAL_DIMENSION";
			paramsMap.put("bgtId", bmBean.getBgtId());
			paramsMap.put("bgtYear", bmBean.getBgtYear());
			paramsMap.put("overDrawType", bmBean.getOverDrawType());
			paramsMap.put("bgtOrgcode", bmBean.getBgtOrgcode());
			paramsMap.put("matrCode", bmBean.getBgtMatrcode());
			paramsMap.put("montCode", bmBean.getMontCode());
			paramsMap.put("montType", bmBean.getMontType());
			paramsMap.put("montName", bmBean.getMontName());
		}else if("2".equals(bmBean.getExportType())){
			//执行使用明细查询数据导出
			excelDesc = "预算维度使用明细数据导出";
			exportConf = "BUDGET_INSP_BGTDIMEN_USEDTL";
			paramsMap.put("bgtId", bmBean.getBgtId());
		}
		//3.调用Excel导出组件
		//参数说明：任务描述、export-config调用配置、输出目标路径+文件名、导出所需的参数传递
		String exportTaskId = exportDeal.execute(excelDesc,exportConf,destFile,paramsMap);
		CommonLogger.info("预算监控模块-预算维度查询数据导出操作,导出任务ID["+exportTaskId+"],BudgetInspectService,bgtTotalDataExport()");
		return exportTaskId;
	}
	
	
	//合同维度的查询数据导出
	public String bgtCntDemensionDataExport(String exportType) throws Exception{
		CommonLogger.info("预算监控模块-合同维度查询数据导出操作-START,BudgetInspectService,bgtCntDemensionDataExport()");
		//1.创建导出路径
		String downloadPath =WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR");		//导出文件的路径
		CommonFileUtils.FileDirCreate(downloadPath);
		//2.组织传输参数
		String destFile = downloadPath+"/.xlsx";						//文件导出详细路径+文件名
		String excelDesc = "";
		String exportConf = "";
		Map<String,Object> paramsMap = new HashMap<String, Object>();
		
		if("1".equals(exportType)){
			excelDesc = "合同维度Total数据导出";
			exportConf = "BUDGET_INSP_BGTDIMEN_CNT";
//			paramsMap.put("", value);
		}else{
			excelDesc = "合同维度XX明细数据导出";
			exportConf = "BUDGET_INSP_BGTDIMEN_CNTDTL";
//			paramsMap.put("", value);
		}
		
		String exportTaskId = exportDeal.execute(excelDesc,exportConf,destFile,paramsMap);
		CommonLogger.info("预算监控模块-预算维度(exportType=["+exportType+"])查询数据导出操作,导出任务ID["+exportTaskId+"],BudgetInspectService,bgtTotalDataExport()");
		return exportTaskId;
	}

	public BudgetManageBean cntInspectDetail(String scdId) {
		 return dao.cntInspectDetail(scdId);
	}
	/**
	 * 监控指标列表
	 * @param montIndexBean
	 * @return
	 */
	public List<MontIndexBean> selectRole(MontIndexBean montIndexBean) {
		BudgetInspectDao pageDao = PageUtils.getPageDao(dao);
		return pageDao.selectRole(montIndexBean);
	}

	public List<BudgetManageBean> exportDataExcute(BudgetManageBean bmBean) {
		if ("1".equals(bmBean.getExportType())) {
			return dao.queryBudgetmanageBeans(bmBean);
		}else if ("2".equals(bmBean.getExportType())) {
			return  dao.exportByBgtId(bmBean);
		}
		return null;
		
	}

	public List<BudgetManageBean> bugetAdjustLog(BudgetManageBean bean) {
		BudgetInspectDao pageDao = PageUtils.getPageDao(dao);
		return pageDao.bugetAdjustLog(bean);
	}

	public BudgetManageBean getBugetInfo(BudgetManageBean bmBean) {
		return dao.getBugetInfo(bmBean);
	}

	public List<BudgetManageBean> getMontNameList(BudgetManageBean bean) {
		BudgetInspectDao pageDao = PageUtils.getPageDao(dao);
		String org1Code=WebHelp.getLoginUser().getOrg1Code();
		bean.setBgtOrgcode(org1Code);
		CommonLogger.info("根据用户所在行或指标类型查询指标名称, BudgetInspectService, getMontName");
		return pageDao.getMontNameList(bean);
	}

	public BudgetManageBean getBean(String bgtId) {
		return dao.getBean(bgtId);
	}
	@Transactional(rollbackFor = Exception.class)
	public boolean adjustBgt(BudgetManageBean bean) {
		if ("2".equals(bean.getType())) {
			return adjust(bean);
		}else if ("1".equals(bean.getType())) {
			//可用调整
			BudgetManageBean bean2 = dao.getBean(bean.getBgtId());
			int affect = dao.adjustValidBgt(bean);
			if (affect == 0 ) {
				//修改失败
				return false;
			}
			BudgetManageBean bean3 = dao.getBean(bean.getBgtId());
			String operMemo="本次调整可用预算的值为"+bean.getTzjy()+",调整前的预算（总预算:"+bean2.getBgtSum()+",可用:"+bean2.getBgtSumValid()+",冻结:"+bean2.getBgtFrozen()+",占用:"+bean2.getBgtUsed()+",透支："+bean2.getBgtOverdraw()+"）,调整后的预算（总预算："+bean3.getBgtSum()+",可用："+bean3.getBgtSumValid()+"，冻结："+bean3.getBgtFrozen()+"，占用："+bean3.getBgtUsed()+"，透支："+bean3.getBgtOverdraw();
			bean.setOperMemo(operMemo);
			bean.setInstOper(WebHelp.getLoginUser().getUserId());
			int insert=budgetImportDao.insertLog(bean);
			if(insert>0){
				return true;
			}else {
				return false;
			}
		}
		return false;
	}
	@Transactional(rollbackFor = Exception.class)
	public boolean adjust(BudgetManageBean bean) {
		boolean isSuccess=false;
		boolean isInsert=false;
		int affect=0;
		//记录调整前的总占用预算
		BudgetManageBean bean2 = dao.getBean(bean.getBgtId());
		affect=budgetImportDao.adjust(bean);
		if(affect>0){
			isSuccess=true;
		}
		else{
			isSuccess=false;
		}
		//向调整记录表中加入一条记录
		//得到调整后的总占用预算
		BudgetManageBean bean3=dao.getBean(bean.getBgtId());
		String operMemo="本次调整占用预算的值为"+bean.getTzjy()+",调整前的预算（总预算:"+bean2.getBgtSum()+",可用:"+bean2.getBgtSumValid()+",冻结:"+bean2.getBgtFrozen()+",占用:"+bean2.getBgtUsed()+",透支："+bean2.getBgtOverdraw()+"）,调整后的预算（总预算："+bean3.getBgtSum()+",可用："+bean3.getBgtSumValid()+"，冻结："+bean3.getBgtFrozen()+"，占用："+bean3.getBgtUsed()+"，透支："+bean3.getBgtOverdraw();
		bean.setOperMemo(operMemo);
		String operUser=WebHelp.getLoginUser().getUserId();
		bean.setInstOper(operUser);
		int insert=budgetImportDao.insertLog(bean);
		if(insert>0){
			isInsert=true;
		}
		return (isSuccess&&isInsert);
	}
	/**
	 * 删除预算
	 * @param bmBean
	 * @return
	 */
	public Map<String, Object> delBgt(BudgetManageBean bmBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", "N");
		BudgetManageBean bean2 = dao.getBean(bmBean.getBgtId());
		if (Tool.CHECK.isEmpty(bean2)) {
			map.put("flag","Y");
			map.put("msg","预算已经删除");
		}else {
			dao.delBgt(bmBean.getBgtId());
			BudgetManageBean bean3 = dao.getBean(bmBean.getBgtId());
			if (!Tool.CHECK.isEmpty(bean3)) {
				//删除失败
				map.put("flag", "N");
			}else {
				map.put("flag", "Y");
			}
		}
		return map;
	}

}
