package com.forms.prms.web.budget.budgetInput.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.exportthread.ExcelExportGenernalDeal;
import com.forms.prms.web.budget.budgetInput.dao.BudgetInputDao;
import com.forms.prms.web.budget.budgetInput.domain.BudgetInputBean;
import com.forms.prms.web.budget.finandeptsum.domain.FinanDeptSumBean;

@Service
public class BudgetInputService {
	@Autowired
	BudgetInputDao dao;
	@Autowired
	private ExcelExportGenernalDeal exportDeal;				//Excel导出Service
	
	/**
	 * 获得BudgetService实例(使用于Excel导入、Excel导出时的业务处理)
	 * @return
	 */
	public static BudgetInputService getInstance(){
		return SpringUtil.getBean(BudgetInputService.class);				
	}
	/**
	 * 提交列表
	 * @param bean
	 * @return
	 */
	public List<BudgetInputBean> selectAllBudget(BudgetInputBean bean) {
		BudgetInputDao pagedao = PageUtils.getPageDao(dao);
		return pagedao.selectAllBudget(bean);
	}
	/**
	 * 得到头信息
	 * @param bean
	 * @return
	 */
	public List<BudgetInputBean> getHeadMsg(BudgetInputBean bean) {
		return dao.getHeadMsg(bean);
	}
	/**
	 * 得到主题信息
	 * @param flag 
	 * @param tmpltId
	 * @return
	 */
	public List<BudgetInputBean> getListMsg(BudgetInputBean bean, String flag) {
		if ("download".equals(flag)) {
			return dao.getListMsg(bean);
		}else {
			BudgetInputDao pagedao = PageUtils.getPageDao(dao);
			return pagedao.getListMsg(bean);
		}
	}
	/**
	 * 删除
	 * @param bean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean del(BudgetInputBean bean) {
		CommonLogger.info("预算申报模块-删除预算申报数据操作,主键信息("+bean.getTmpltId()+"),BudgetInputService,del");
		int affect1 =dao.delWriteHead(bean);
		int affect2 =dao.delWriteDetail(bean);
		if (affect1 >0 && affect2 >0) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 提交
	 * @param bean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean input(BudgetInputBean bean) {
		CommonLogger.info("预算申报模块-预算申报数据提交操作,主键信息("+bean.getTmpltId()+"),BudgetInputService,input");
		int affect = dao.input(bean);
		int affect2 = dao.insertAudit(bean);
		if (affect>0 && affect2 > 0) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 下载导出
	 * @param bean
	 * @return
	 */
	public String downLoad(FinanDeptSumBean budget) throws Exception {
		CommonLogger.info("预算申报模块-预算申报数据下载导出操作,主键信息("+budget.getTmpltId()+"),BudgetInputService,downLoad");
		String sourceFileName = "tmpltReport";
		createFilePath(new File(WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")));
		String destFile = WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")+"/"+sourceFileName+"-"+budget.getTmpltId()+".xlsx";;
		//导出Excel操作
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("tmpltId", budget.getTmpltId());
		map.put("tmpltType", budget.getTmpltType());
		map.put("dutyCode", budget.getDutyCode());
		if ("0".equals(budget.getTmpltType())) {
			//资产
			return exportDeal.execute(sourceFileName+"【"+budget.getTmpltId()+"】", "ZC_BUDGET_REPORT_INPUT", destFile , map);
		}else {
			return exportDeal.execute(sourceFileName+"【"+budget.getTmpltId()+"】", "FY_BUDGET_REPORT_INPUT", destFile , map);
		}
	}
	   
    /**
     * 如果文件路径不存在，则创建文件全部路径
     * @param filePath
     */
    public void createFilePath(File filePath){
    	if(!judgePlateFlag(filePath)){
    		filePath.mkdirs();
    	}
    }
	
	//【工具方法】判断字符串是否能转换为数值类型
	public static boolean judgeStrToNum(String str){
		try {
			Integer.valueOf(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
     * 判断文件路径或客户端系统盘符是否存在
     */
    public static boolean judgePlateFlag(File panFu){
    	if(panFu.exists()){
    		return true;
    	}else{
    		return false;
    	}
    }
 
	 /**
     * 解析数据并插入daoexcel里面
     * @param budgetInputBean
     */
	public void insertDataToZCExcel(BudgetInputBean budgetInputBean) {
		String[] infoStr = budgetInputBean.getRowInfo().split("\\|");
		budgetInputBean.setMontName(infoStr[0]);
		budgetInputBean.setPropertyType(infoStr[1]);
		budgetInputBean.setAcCode(infoStr[2]);
		budgetInputBean.setColumnOne(infoStr[3]);
		budgetInputBean.setColumnTwo(infoStr[4]);
		budgetInputBean.setMatrCode(infoStr[5]);
		budgetInputBean.setMatrName(infoStr[6]);
		budgetInputBean.setReferPrice(Integer.parseInt(infoStr[7]));
		budgetInputBean.setReferType(infoStr[8]);
	}
	 /**
     * 解析数据并插入daoexcel里面
     * @param budgetInputBean
     */
	public void insertDataToFYExcel(BudgetInputBean budgetInputBean) {
		String[] infoStr = budgetInputBean.getRowInfo().split("\\|");
		budgetInputBean.setMontName(infoStr[0]);
		budgetInputBean.setPropertyType(infoStr[1]);
		budgetInputBean.setAcCode(infoStr[2]);
		budgetInputBean.setColumnOne(infoStr[3]);
		budgetInputBean.setColumnTwo(infoStr[4]);
		budgetInputBean.setMatrCode(infoStr[5]);
		budgetInputBean.setMatrName(infoStr[6]);
	}
}
