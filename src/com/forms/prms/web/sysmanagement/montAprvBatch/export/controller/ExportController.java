package com.forms.prms.web.sysmanagement.montAprvBatch.export.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.prms.tool.ImportDataType;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.domain.ImportBean;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.service.ImportService;
import com.forms.prms.web.sysmanagement.montAprvBatch.export.domain.ExportBean;
import com.forms.prms.web.sysmanagement.montAprvBatch.export.service.ExportService;

@Controller
@RequestMapping("/sysmanagement/montAprvBatch/export")
public class ExportController {
	@Autowired
    private ExportService esService;
	@Autowired
    private ImportService importService;
	private static final String basePath="sysmanagement/montAprvBatch/export/";
	
	/**
	 * 导出页面
	 */
	@RequestMapping("/shExport.do")
	public String shExport(ImportBean bean){
		CommonLogger.info("进入省行导出页面,ExportController,shExport");
//		List<Map<String, Object>> authList = importService.getAuthList(WebHelp.getLoginUser().getRoleId(), ImportDataType.shAuthList("export"));
//		List<Map<String, Object>> list = ImportDataType.getSelectList(authList,ImportDataType.shTag,"export");
//		WebUtils.setRequestAttr("selectList", list);
		bean.setOrgType("01");//省行
		WebUtils.setRequestAttr("bean", bean);
		String dataYear = Tool.DATE.getDateStrNO().substring(0,4);
		WebUtils.setRequestAttr("dataYear", dataYear);
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		return basePath+"export";
	}
	
	@RequestMapping("/fhExport.do")
	public String fhExport(ImportBean bean){
		CommonLogger.info("进入分行导出页面,ExportController,fhExport");
//		List<Map<String, Object>> authList = importService.getAuthList(WebHelp.getLoginUser().getRoleId(), ImportDataType.shAuthList("export"));
//		List<Map<String, Object>> list = ImportDataType.getSelectList(authList,ImportDataType.shTag,"export");
//		WebUtils.setRequestAttr("selectList", list);
		bean.setOrgType("02");//fen行
		WebUtils.setRequestAttr("bean", bean);
		String dataYear = Tool.DATE.getDateStrNO().substring(0,4);
		WebUtils.setRequestAttr("dataYear", dataYear);
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		return basePath+"export";
	}
	/**
	 * 导出校验
	 * 
	 * 
	 * @param eb
	 * @return
	 */
	@RequestMapping("/ajaxExport.do")
	@ResponseBody
	public String ajaxExport(ExportBean eb) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();	
		
		//Excel导出操作
		try {
			Map<String, Object> map = esService.ajaxExport(eb);
			jsonObject.put("value", map);
		} catch (Exception e) {
			// TODO: handle exceptionjsonObject.put("pass", false);
			jsonObject.put("msgInfo", "导出校验数据异常");
			e.printStackTrace();
		}
		
		return jsonObject.writeValueAsString();
	}
	/**
	 * 根据类型来显示导出的年份
	 * @param eb
	 * @return
	 */
	@RequestMapping("/showYear.do")
	@ResponseBody
	public String showYear(ExportBean eb) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();	
		
		Map<String, Object> map = new HashMap<String, Object>();
		//Excel导出操作
		try {
			String dataYear = esService.showYear(eb);
			map.put("flag", true);
			map.put("msg", dataYear);
		} catch (Exception e) {
			map.put("flag", false);
			jsonObject.put("msg", "程序出现异常");
			e.printStackTrace();
		}
		
		jsonObject.put("data", map);
		return jsonObject.writeValueAsString();
	}
	/**
	 * 导出
	 * 
	 * 
	 * @param eb
	 * @return
	 */
	@RequestMapping("/download.do")
	@ResponseBody
	public String download(ExportBean eb) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();		
		//Excel导出操作
		String taskId = null;
		try {
			taskId = esService.download(eb);
			if (Tool.CHECK.isBlank(taskId)) {
				jsonObject.put("pass", false);
			} else {
				jsonObject.put("pass", true);
			}		
		} catch (Exception e) {
			try{
				//如果  taskId已插入出现异常,则更新为失败
				if(!Tool.CHECK.isBlank(taskId)){
					esService.updateTaskDataFlag(taskId);
				}
			}catch (Exception e1) {
				e1.printStackTrace();
			}
			jsonObject.put("pass", false);
			e.printStackTrace();
		}
		return jsonObject.writeValueAsString();
	}
	
	
}
