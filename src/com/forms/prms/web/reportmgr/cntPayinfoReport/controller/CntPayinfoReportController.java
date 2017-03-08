package com.forms.prms.web.reportmgr.cntPayinfoReport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.prms.web.reportmgr.cntPayinfoReport.domain.CntPayinfoReportBean;
import com.forms.prms.web.reportmgr.cntPayinfoReport.service.CntPayinfoReportService;

@Controller
@RequestMapping("/reportmgr/cntPayinfoReport")
public class CntPayinfoReportController {
	
	@Autowired
	private CntPayinfoReportService cService;
	
	private static final String FUNCTION = "reportmgr/cntPayinfoReport/";

	/**
	 * 下载报表--060301
	 * @param con
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addDownLoad.do")
	@ResponseBody
	public String addDownload(CntPayinfoReportBean con) throws Exception{
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		String taskId = "";
		try{			
			taskId = cService.queryDownloadList(con);
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
	 * 查询列表---060302
	 * @return
	 */
	@RequestMapping("getCntPayinfoReportList.do")
	public String getCntPayinfoReportList(CntPayinfoReportBean bean){
		WebUtils.setRequestAttr("cntPayinfoReportList", cService.getCntPayinfoReport(bean));
		WebUtils.setRequestAttr("con", bean);
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		return FUNCTION + "cntPayInfoReport";
		
	}
	
	/**
	 * --060303
	 * @param con
	 * @return
	 */
	@RequestMapping("org1List.do")
	public String org1List(CntPayinfoReportBean con) {
		con.setOrgFlag("1");//省行
		return getCntPayinfoReportList(con);
	}
	
	/**
	 *  --060304
	 * @param con
	 * @return
	 */
	@RequestMapping("org2List.do")
	public String org2List(CntPayinfoReportBean con) {
		con.setOrgFlag("2");//二级行
		return getCntPayinfoReportList(con);
	}
	
	/**
	 * --060305
	 * @param con
	 * @return
	 */
	@RequestMapping("dutyCodeList.do")
	public String dutyCodeList(CntPayinfoReportBean con) {
		con.setOrgFlag("3");//业务部门
		return getCntPayinfoReportList(con);
	}
	

}
