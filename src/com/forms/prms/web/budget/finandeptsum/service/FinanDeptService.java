package com.forms.prms.web.budget.finandeptsum.service;

import java.io.File;
import java.math.BigDecimal;
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
import com.forms.prms.web.budget.budgetplan.domain.BudgetPlanBean;
import com.forms.prms.web.budget.finandeptsum.dao.FinanDeptDAO;
import com.forms.prms.web.budget.finandeptsum.domain.FinanDeptSumBean;

@Service
public class FinanDeptService {

	@Autowired
	private FinanDeptDAO finanDAO;
	@Autowired
	private ExcelExportGenernalDeal exportDeal;				//Excel导出Service
	
	/**
	 * 获得BudgetService实例(使用于Excel导入、Excel导出时的业务处理)
	 * @return
	 */
	public static FinanDeptService getInstance(){
		return SpringUtil.getBean(FinanDeptService.class);				
	}
	/**
	 * 本级汇总
	 * @param finanSum
	 * @return
	 */
	public List<FinanDeptSumBean> selList(FinanDeptSumBean finanSum,String type){
		if ("downLoad".equals(type)) {
			return finanDAO.selList(finanSum);
		}else {
			FinanDeptDAO pageDAO = PageUtils.getPageDao(finanDAO);
			return pageDAO.selList(finanSum);
		}
		
	}
	
	/**
	 * 二级汇总
	 * @param finanSum
	 * @return
	 */
	public List<FinanDeptSumBean> secondList(FinanDeptSumBean finanSum,String type){
		if("".equalsIgnoreCase(finanSum.getDutyCode()) || null == finanSum.getDutyCode()){
			//如果网页上没有指定查询具体的本级部门，则取当前登录用户的责任中心
			finanSum.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		}
		finanSum.setOrg2Code(WebHelp.getLoginUser().getOrg2Code());
		if ("downLoad".equals(type)) {
			return finanDAO.secondList(finanSum);
		}else{
			FinanDeptDAO pageDAO = PageUtils.getPageDao(finanDAO);
			return pageDAO.secondList(finanSum);
		}
		
	}
	
	/**
	 * 一级汇总
	 * @param finanSum
	 * @return
	 */
	public List<FinanDeptSumBean> firList(FinanDeptSumBean finanSum,String type){
		if("".equalsIgnoreCase(finanSum.getDutyCode()) || null == finanSum.getDutyCode()){
			//如果网页上没有指定查询具体的本级部门，则取当前登录用户的责任中心
			finanSum.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		}
		finanSum.setOrg2Code(WebHelp.getLoginUser().getOrg2Code());
		finanSum.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		if ("downLoad".equals(type)) {
			return finanDAO.firList(finanSum);
		}else{
			FinanDeptDAO pageDAO = PageUtils.getPageDao(finanDAO);
			return pageDAO.firList(finanSum);
		}
	
	}
	
	/**
	 * 模板列表查询
	 * @param budget
	 * @return
	 */
	public List<BudgetPlanBean> budgetTmpSumList(BudgetPlanBean budget){
		FinanDeptDAO pageDAO = PageUtils.getPageDao(finanDAO);
		budget.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		return pageDAO.budgetTempSumList(budget);
	}
	
	public List<BudgetPlanBean> budgetTempLvl1SumList(BudgetPlanBean budget){
		FinanDeptDAO pageDAO = PageUtils.getPageDao(finanDAO);
		budget.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		return pageDAO.budgetTempLvl1SumList(budget);
	}
	
	//====================================================
	/**
	 * 本级汇总明细
	 * @param bean
	 * @return
	 */
	public List<FinanDeptSumBean> getSbList(FinanDeptSumBean bean) {
		FinanDeptDAO pageDAO = PageUtils.getPageDao(finanDAO);
		return pageDAO.getSbList(bean);
	}
	/**
	 * 物料退回
	 * @param bean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean back(FinanDeptSumBean bean) {
		CommonLogger.info("本级汇总退回操作-物料审批不通过退回,(模板:"+bean.getTmpltId()+"退回物料:"+bean.getMatrCode()+"),FinanDeptService,back");
		bean.setAuditLvl("1");
		//修改状态
		int affect = finanDAO.back(bean);
		//退回 时 申报前金额是审批金额 申报后金额是0
		bean.setBeforeAmt(new BigDecimal(bean.getAddAmt()));
		bean.setAfterAmt(new BigDecimal("0"));
		bean.setAuditOper(WebHelp.getLoginUser().getUserId());
		int affect2 = finanDAO.insertLog(bean);
		if (affect >0 && affect2>0) {
			return true;
		}
		return false;
	}
	/**
	 * 本级提交
	 * @param bean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean submit(FinanDeptSumBean bean) {
		CommonLogger.info("本级汇总提交操作-物料审批通过后提交到二级汇总列表,(模板:"+bean.getTmpltId()+"退回物料:"+bean.getMatrCode()+"),FinanDeptService,submit");
		//x修改状态
		int affect = finanDAO.submit(bean);
		bean.setBeforeAmt(new BigDecimal(bean.getAddAmt()));
		bean.setAfterAmt(new BigDecimal(bean.getAuditAmt()));
		bean.setAuditOper(WebHelp.getLoginUser().getUserId());
		bean.setAuditLvl("1");
		int affect2 = finanDAO.insertLog(bean);
		if (affect>0 && affect2>0) {
			return true;
		}
		return false;
	}
	/**
	 * 二级行提交
	 * @param bean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean secondSubmit(FinanDeptSumBean bean) {
		CommonLogger.info("二级行汇总提交操作-物料二级行审批通过后提交到一级汇总列表,(模板:"+bean.getTmpltId()+"退回物料:"+bean.getMatrCode()+"),FinanDeptService,secondSubmit");
		//x修改状态
		int affect = finanDAO.secondSubmit(bean);
		bean.setAuditOper(WebHelp.getLoginUser().getUserId());
		bean.setAuditLvl("2");
		int affect2 = finanDAO.insertLog(bean);
		if(affect > 0 && affect2 > 0){
			return true;
		}
		return false;
	}
	/**
	 * 一级行下载
	 * @param budget
	 * @throws Exception 
	 */
	public String firstDown(FinanDeptSumBean bean) throws Exception {
		CommonLogger.info("一级行汇总导出下载操作,(模板:"+bean.getTmpltId()+"),FinanDeptService,back");
		String sourceFileName = "tmpltAudit";
		createFilePath(new File(WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")));
		String destFile = WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")+"/"+sourceFileName+"-"+bean.getTmpltId()+".xlsx";;
		//导出Excel操作
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("tmpltId", bean.getTmpltId());
		map.put("dutyCode", bean.getDutyCode());
		map.put("type", bean.getType());
		return exportDeal.execute(sourceFileName+"【"+bean.getTmpltId()+"】", "TMPLT_REPORT_DETAIL", destFile , map);
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
}
