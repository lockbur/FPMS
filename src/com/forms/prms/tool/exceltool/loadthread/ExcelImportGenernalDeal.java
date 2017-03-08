package com.forms.prms.tool.exceltool.loadthread;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.constantValues.ExcelTaskStatusValues;
import com.forms.prms.tool.exceltool.dao.ICommonExcelDealDao;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.tool.thread.ThreadUtil;

@Service
public class ExcelImportGenernalDeal {
	@Autowired
	public ICommonExcelDealDao dao;
	/**
	 * 汇总处理Excel信息
	 * @param excelPath 导入文件路径
	 * @param excelName 导入文件名
	 * @param excelDesc 文件描述
	 * @param configIds Excel中sheet对应的configId数组
	 * @param beans 导入参数，存放在数据库中
	 * 
	 * @return taskId 返回生成的任务ID
	 */
	public String execute(String excelPath,String excelName,String excelDesc,String[] configIds,Map<String,String> beans)throws Exception{
		String taskId = dao.getLoadTaskId();
		CommonLogger.info("Excel导入处理控件-新增导入任务：导入文件名["+excelName+"],文件路径["+excelPath+"],导入描述["+excelDesc+"]," +
									"导入Excel的配置ID为["+StringUtil.arrayToString(configIds)+"],ExcelImportGenernalDeal,execute()");
		CommonExcelDealBean bean = new CommonExcelDealBean();
		bean.setSourceFpath(excelPath);
		bean.setTaskDesc(excelDesc);
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		bean.setDataFlag(ExcelTaskStatusValues.WAITDEAL.toString());
		bean.setTaskId(taskId);
		bean.setConfigId(StringUtils.join(configIds, ","));
		bean.setSourceFname(excelName);
		if(!Tool.CHECK.isEmpty(beans.get("impBatch"))){
			bean.setTaskBatchNo(beans.get("impBatch"));			//插入导出批次信息
		}
		if (!Tool.CHECK.isEmpty(beans.get("loadType")) && "01".equals(beans.get("loadType"))) {
			bean.setLoadType("01");								//插入导入类型信息
		}
		if(beans != null){			
			JSONObject json = JSONObject.fromObject(beans);
			bean.setTaskParams(String.valueOf(json));
		}
		try {
			dao.insertLoad(bean);
		} catch (Exception e) {
			CommonLogger.error("调用Excel导入处理工具进行Task信息录入时发生异常	ExcelImportGenernalDeal	execute");
			CommonLogger.error("ErrorMsg为："+e.getMessage());
		}
		
		//唤醒处理线程
		ThreadUtil.notifyExcelImportDealThread();
		
		return taskId;
	}
}
