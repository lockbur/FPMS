package com.forms.prms.web.sysmanagement.montAprvBatch.export.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.ImportUtil;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.exportthread.ExcelExportGenernalDeal;
import com.forms.prms.web.sysmanagement.montAprvBatch.export.dao.ExportDao;
import com.forms.prms.web.sysmanagement.montAprvBatch.export.domain.ExportBean;

@Service
public class ExportService {

	@Autowired
	private ExportDao edao;
	@Autowired
	private ExcelExportGenernalDeal exportDeal;				//Excel导出Service
	
	
	/*8
	 * 导出校验
	 */
	public Map<String, Object> ajaxExport(ExportBean eb) {
		String proTypeName = "";
		if ("01".equals(eb.getProType())) {
			proTypeName = "监控指标";
		}else {
			proTypeName = "审批链";
		}
		String subTypeName = "";
		if ("11".equals(eb.getSubType())) {
			subTypeName="专项包";
		}else if ("12".equals(eb.getSubType())) {
			subTypeName = "省行统购资产";
		}else if ("21".equals(eb.getSubType())) {
			subTypeName = "非省行统购资产";
		}else if ("22".equals(eb.getSubType())) {
			subTypeName = "非专项包费用";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", "Y");
		if ("01".equals(eb.getOrgType())) {
			//省行
			eb.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
		}else {
			eb.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		}
		//看看有没有正在审核中的数据，有的话就不让导出,正在转移中也不让导出C4,E5(E5只有监控指标才有)
		int isWorking= edao.isWorking(eb);
		if (isWorking>0) {
			map.put("flag", "N");
			map.put("msg", eb.getDataYear()+subTypeName+proTypeName+"还存在有走完整个导入和审批流程的批次，请完成或者删除后再导出");
			return map;
		}
		//查看该类型是否有待勾选的审批链，如果有就不让导出
		if ("02".equals(eb.getProType())) {
			int isNoUpAprv =edao.isNoUpAprv(eb);
			if (isNoUpAprv>0) {
				map.put("flag", "N");
				map.put("msg", eb.getDataYear()+subTypeName+"的审批链还有待修改的，请在审批链页面维护的待修改页签中修改完后才能进行导出");
				return map;
			}
		}
		
		if("02".equals(eb.getProType())){
			//审批链导出 就看监控指标有没有是审核中的状态
			//如果今年的2015年的监控指标没有制定好就不让导出今年的审批链。
			eb.setDataYear(Tool.DATE.getDateStrNO().substring(0,4));
			int count1 = edao.yearMontIsOk(eb);
			if (count1<=0) {
				//没有制定好监控指标
				map.put("flag", "N");
				map.put("msg", Tool.DATE.getDateStrNO().substring(0,4)+"年的该类型的监控指标还没有制定，必须等监控指标制定后才能导出该类型的审批链");
				return map;
			}
			
		}
		return map;
	}
	
	public String download(ExportBean eb) throws Exception {
		String subTypeName = "";
		String subType=eb.getSubType();
		String proType=eb.getProType();
		String proTypeName = "";
		if (proType.equals("01")) {
			proTypeName="监控指标";	
			
		} else if(proType.equals("02")) {
			proTypeName="审批链";	
		}
		if (subType.equals("11")) {
			subTypeName="专项包";
		}else if(subType.equals("12")){
			subTypeName="省行统购资产";
		}else if(subType.equals("21")){
			subTypeName="非省行统购类资产";
		}else if(subType.equals("22")){
			subTypeName="非专项包费用类";
		}
		String sourceFileName = eb.getDataYear()+subTypeName+proTypeName+"数据导出";
		ImportUtil.createFilePath(new File(WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")));
		String destFile = WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")+"/"+sourceFileName+".xlsx";;
		//导出Excel操作
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("dataYear", eb.getDataYear());
		map.put("proType", eb.getProType());
		map.put("subType", eb.getSubType());
		if ("01".equals(eb.getOrgType())) {
			//省行
			map.put("org21Code", WebHelp.getLoginUser().getOrg1Code());
			eb.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
		}else {
			map.put("org21Code", WebHelp.getLoginUser().getOrg2Code());
			eb.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		}
		//添加一条汇总信息
//		eb.setStatus(MontAprvType.EXCEL_E0);
//		edao.insertSum(eb);
		if ("01".equals(eb.getProType())) {
			//监控指标
			if ("11".equals(eb.getSubType())) {
//				//专项包
				return exportDeal.execute(sourceFileName, "EXPORT_MONT_ZX_BATCH_EXPORT", destFile , map);
			}else {
				return exportDeal.execute(sourceFileName, "EXPORT_MONT_BATCH_EXPORT", destFile , map);
			}
			
		}else if ("02".equals(eb.getProType())) {
			if ("11".equals(eb.getSubType())) {
//				//专项包
				return exportDeal.execute(sourceFileName, "EXPORT_APRV_ZX_BATCH_EXPORT", destFile , map);
			}else {
				return exportDeal.execute(sourceFileName, "EXPORT_APRV_BATCH_EXPORT", destFile , map);
			}
		}
		else{
			return null;
		}
	}
	//获得类实例
		public static ExportService getInstance(){
			return SpringUtil.getBean(ExportService.class);
		}
	    /**
	     * 监控指标正式表中得到数据
	     * @param bean
	     * @return
	     */
		public List<ExportBean> getMontList(ExportBean bean) {
			return edao.getMontList(bean);
		}
		/**
		 * 监控指标历史表
		 * @param bean
		 * @return
		 */
		public List<ExportBean> getMontListForThisYear(ExportBean bean) {
			return edao.getMontListForThisYear(bean);
		}
		/**
		 * 历史表专项包
		 * @param bean
		 * @return
		 */
		public List<ExportBean> getAprvZxHisList(ExportBean bean) {
			return edao.getAprvZxHisList(bean);
		}
		/**
		 * 拿出正式表里所有数据
		 * @param bean
		 * @return
		 */
		public List<ExportBean> lastYearMontList(ExportBean bean) {
			return edao.lastYearMontList(bean);
		}
		/**
		 * 查询 正式表里 某一类型的监控指标 有多少数据
		 * @param bean
		 * @return
		 */
//		public String getCountMontFromMont(ExportBean bean) {
//			// TODO Auto-generated method stub
//			return edao.getCountMontFromMont(bean);
//		}

//		public String getCountMontFromMontFut(ExportBean bean) {
//			// TODO Auto-generated method stub
//			return edao.getCountMontFromMontFut(bean);
//		}

		/**
		 * 导出当年的监控指标
		 * @param bean
		 * @return
		 */
		public List<ExportBean> getAllMontList(ExportBean bean) {
			// TODO Auto-generated method stub
			return edao.getAllMontList(bean);
		}
		/**
		 * 这类型的数据是否在正式表
		 * @param bean
		 * @return
		 */
		public String isExistsFromMont(ExportBean bean) {
			return edao.isExistsFromMont(bean);
		}
		/**
		 * 转移触发，调用存储过程，
		 * @param bean
		 */
		public void transferMontData(ExportBean bean) {
			edao.transferMontData(bean.getMontType(),bean.getOrg21Code(),bean.getDataYear(),"1");
		}
		/**
		 * 是不是没有这类型的数据
		 * @param bean
		 * @return
		 */
		public int isFirst(ExportBean bean) {
			return edao.isFirst(bean);
		}
		/**
		 * 初次导入数据
		 * @param bean
		 * @return
		 */
		public List<ExportBean> selectInitData(ExportBean bean) {
			return edao.selectInitData(bean);
		}

		/**
		 * 更新导出任务状态
		 * @param taskId
		 */
		public void updateTaskDataFlag(String taskId) {
			edao.updateTaskDataFlag(taskId);
		}
		
		public String showYear(ExportBean eb) {
			if ("01".equals(eb.getOrgType())) {
				eb.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
			}else {
				eb.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
			}
			return edao.showYear(eb);
		}
		/**
		 * 
		 * @param bean
		 * @return
		 */

		public List<ExportBean> getLastMontsFromTable(ExportBean bean) {
			return edao.getLastMontsFromTable(bean);
		}
		/**
		 * 专项包当年的审批链直接从表里取
		 * @param bean
		 * @return
		 */
		public List<ExportBean> getAprvSpecTable(ExportBean bean) {
			return edao.getAprvSpecTable(bean);
		}
		/**
		 * 专项包不是当年的审批链得转换一下
		 * @param bean
		 * @return
		 */
		public List<ExportBean> getAprvSpecTableChange(ExportBean bean) {
			return edao.getAprvSpecTableChange(bean);
		}
		/**
		 * 非专项包当年的审批链直接从表里取
		 * @param bean
		 * @return
		 */
		public List<ExportBean> getAprvNoSpecTable(ExportBean bean) {
			return edao.getAprvNoSpecTable(bean);
		}
		/**
		 * 非专项包不是当年的审批链得转换一下
		 * @param bean
		 * @return
		 */
		public List<ExportBean> getAprvNoSpecTableChange(ExportBean bean) {
			return edao.getAprvNoSpecTableChange(bean);
		}
		/**
		 * 审批链初次导出时的初始化数据
		 * @param bean
		 * @return
		 */
		public List<ExportBean> selectInitAprvData(ExportBean bean) {
			return edao.selectInitAprvData(bean);
		}

		public int getCountAprvSpecTable(ExportBean bean) {
			return edao.getCountAprvSpecTable(bean);
		}


}
