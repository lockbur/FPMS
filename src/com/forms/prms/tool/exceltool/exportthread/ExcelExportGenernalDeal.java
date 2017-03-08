package com.forms.prms.tool.exceltool.exportthread;

import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.constantValues.ExcelTaskStatusValues;
import com.forms.prms.tool.exceltool.dao.ICommonExcelDealDao;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.tool.thread.ThreadUtil;

@Service
public class ExcelExportGenernalDeal {
	@Autowired
	private ICommonExcelDealDao dao;
	
	/**
	 * 汇总处理Excel信息,一个Excel对应一个处理线程
	 * @param excelDesc 	文件描述
	 * @param configId 		导出配置编号
	 * @param destFile		导出目标文件路径
	 * @param beans 导入参数，存放在数据库中
	 * 
	 * @return taskId 返回生成的任务ID
	 */
//	public String execute(String excelDesc,String configId, String destFile,Map<String,String> params)throws Exception{
//		String taskId = dao.getExportTaskId();
//		CommonExcelDealBean bean = new CommonExcelDealBean();
//		bean.setTaskDesc(excelDesc);
//		bean.setInstOper(WebHelp.getLoginUser().getUserId());
//		bean.setDataFlag(ExcelTaskStatusValues.WAITDEAL.toString());
//		bean.setTaskId(taskId);
//		bean.setConfigId(configId);
//		//将taskID添加到文件名中，保证文件名的唯一性
//		destFile = destFile.substring(0, destFile.lastIndexOf(".")) + "_" + 
//						taskId + destFile.substring(destFile.lastIndexOf("."));
//		bean.setDestFile(destFile);
//		
//		if(params != null){			
//			JSONObject json = JSONObject.fromObject(params);
//			bean.setTaskParams(String.valueOf(json));
//		}
//		
//		dao.insertExport(bean);
//		
//		//唤醒处理线程
//		ThreadUtil.notifyExcelExportDealThread();
//		
//		return taskId;
//	}
	
	/**
	 * 汇总处理Excel信息,一个Excel对应一个处理线程
	 * @param excelDesc 	文件描述
	 * @param configId 		导出配置编号
	 * @param destFile		导出目标文件路径
	 * @param beans 导入参数，存放在数据库中
	 * 
	 * @return taskId 返回生成的任务ID
	 */
	public String execute(String excelDesc,String configId, String destFile,Map<String,Object> params)throws Exception{
		String taskId = dao.getExportTaskId();
		CommonExcelDealBean bean = new CommonExcelDealBean();
		bean.setTaskDesc(excelDesc);
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		bean.setDataFlag(ExcelTaskStatusValues.WAITDEAL.toString());
		bean.setTaskId(taskId);
		bean.setConfigId(configId);
		//将taskID添加到文件名中，保证文件名的唯一性
		destFile = destFile.substring(0, destFile.lastIndexOf(".")) + 
						taskId + destFile.substring(destFile.lastIndexOf("."));
		bean.setDestFile(destFile);
		//设置excel文件所需导出方式
		bean.setExportType((String)params.get("exportType"));
		if(params != null){			
			JSONObject json = JSONObject.fromObject(params);
			bean.setTaskParams(String.valueOf(json));
		}
		
		dao.insertExport(bean);
		
		//唤醒处理线程
		ThreadUtil.notifyExcelExportDealThread();
		
		return taskId;
	}

}
