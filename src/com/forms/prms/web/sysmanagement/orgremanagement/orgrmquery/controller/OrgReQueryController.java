package com.forms.prms.web.sysmanagement.orgremanagement.orgrmquery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.montAprvBatch.export.service.ExportService;
import com.forms.prms.web.sysmanagement.orgremanagement.orgrmquery.domain.OrgReQueryBean;
import com.forms.prms.web.sysmanagement.orgremanagement.orgrmquery.service.OrgReQueryService;


@Controller
@RequestMapping("/sysmanagement/orgremanagement/orgrequery/")
public class OrgReQueryController {
	@Autowired
	private OrgReQueryService orgReQueryService;
	@Autowired
	private ExportService exportService;
	private static final String PAYADDPATH = "/sysmanagement/orgremanagement/orgrequery/";
	
	/**
	 * 自动撤并查询
	 * @param orgReQueryBean
	 * @return
	 */
	@RequestMapping("list.do")
	public String list(OrgReQueryBean orgReQueryBean){
		List<OrgReQueryBean> lists = orgReQueryService.list(orgReQueryBean);
		WebUtils.setRequestAttr("lists", lists);
		WebUtils.setRequestAttr("selectInfo", orgReQueryBean);
		return PAYADDPATH + "list";
	}
	/**
	 * 机构自动撤并查询
	 * @param orgReQueryBean
	 * @return
	 */
	@RequestMapping("orgList.do")
	public String orgList(OrgReQueryBean orgReQueryBean){
		List<OrgReQueryBean> lists = orgReQueryService.orgList(orgReQueryBean);
		WebUtils.setRequestAttr("lists", lists);
		WebUtils.setRequestAttr("selectInfo", orgReQueryBean);
		return PAYADDPATH + "orgList";
	}
	/**
	 * 责任中心完结
	 * @param bean
	 * @return
	 */
	@RequestMapping("dutyOver.do")
	@ResponseBody
	public String dutyOver(OrgReQueryBean bean){
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		boolean flag = orgReQueryService.dutyOver(bean);
		jsonObject.put("data", flag);
		return jsonObject.writeValueAsString();
	}
	/**
	 * 机构完结
	 * @param bean
	 * @return
	 */
	@RequestMapping("orgOver.do")
	@ResponseBody
	public String orgOver(OrgReQueryBean bean){
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		boolean flag = orgReQueryService.orgOver(bean);
		jsonObject.put("data", flag);
		return jsonObject.writeValueAsString();
	}
	/**
	 * 交叉验证表导出查询
	 * @param orgReQueryBean
	 * @return
	 */
	@RequestMapping("exportList.do")
	public String exportList(OrgReQueryBean orgReQueryBean){
		if (Tool.CHECK.isEmpty(orgReQueryBean.getIsLocked())) {
			orgReQueryBean.setIsLocked("0");
		}
		OrgReQueryBean bean = new OrgReQueryBean();
		bean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		bean.setIsSuperAdmin(WebHelp.getLoginUser().getIsSuperAdmin());
		
		List<OrgReQueryBean> lists = orgReQueryService.exportList(orgReQueryBean);
		WebUtils.setRequestAttr("lists", lists);
		WebUtils.setRequestAttr("selectInfo", orgReQueryBean);
		WebUtils.setRequestAttr("selectInfoHidden", bean);
		return PAYADDPATH + "exportList";
	}
	/**
	 * 信息导出
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/exportData.do")
	@ResponseBody
	public String exportData(OrgReQueryBean bean){
		AbstractJsonObject jsonObject = new SuccessJsonObject();		
		//Excel导出操作
		String taskId = null;
		try {
			taskId = orgReQueryService.exportData(bean);
			if (Tool.CHECK.isBlank(taskId)) {
				jsonObject.put("pass", false);
			} else {
				jsonObject.put("pass", true);
			}		
		} catch (Exception e) {
			try{
				//如果  taskId已插入出现异常,则更新为失败
				if(!Tool.CHECK.isBlank(taskId)){
					exportService.updateTaskDataFlag(taskId);
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
