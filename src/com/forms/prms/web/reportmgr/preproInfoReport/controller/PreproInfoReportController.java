package com.forms.prms.web.reportmgr.preproInfoReport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.prms.web.reportmgr.preproInfoReport.domain.PreproInfoReportBean;
import com.forms.prms.web.reportmgr.preproInfoReport.service.PreproInfoReportService;

@Controller
@RequestMapping("/reportmgr/preproInfoReport")
public class PreproInfoReportController {
	@Autowired
	private PreproInfoReportService pService;
	
	private static final String FUNCTION = "reportmgr/preproInfoReport/";
	
	
	/**
	 * 查询列表---060401
	 * @return
	 */
	@RequestMapping("getPreproInfoReport.do")
	public String getPreproInfoReport(PreproInfoReportBean bean){
		WebUtils.setRequestAttr("preproInfoReport", pService.getPreproinfoReport(bean));
		WebUtils.setRequestAttr("con", bean);
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		return FUNCTION + "preproInfoReport";
		
	}
	
	/**
	 * 下载报表--060402
	 * @param con
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addDownLoad.do")
	@ResponseBody
	public String addDownload(PreproInfoReportBean con) throws Exception{
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		String taskId = "";
		try{			
			taskId = pService.queryDownloadList(con);
			if(taskId == null || "".equals(taskId)){				
				jsonObject.put("pass", false);				
			}else{
				jsonObject.put("pass", true);
			}
		}catch(Exception e){
			jsonObject.put("pass", false);
			throw e;
		}
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		return jsonObject.writeValueAsString();
	}
	
	/**
	 * --060403
	 * @param con
	 * @return
	 */
	@RequestMapping("org1List.do")
	public String org1List(PreproInfoReportBean p) {
		p.setOrgFlag("1");//省行
		return getPreproInfoReport(p);
	}
	
	/**
	 *  --060404
	 * @param con
	 * @return
	 */
	@RequestMapping("org2List.do")
	public String org2List(PreproInfoReportBean p) {
		p.setOrgFlag("2");//二级行
		return getPreproInfoReport(p);
	}
	
	/**
	 * --060405
	 * @param con
	 * @return
	 */
	@RequestMapping("dutyCodeList.do")
	public String dutyCodeList(PreproInfoReportBean p) {
		p.setOrgFlag("3");//业务部门
		return getPreproInfoReport(p);
	}

}
