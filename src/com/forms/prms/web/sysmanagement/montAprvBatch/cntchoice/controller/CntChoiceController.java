package com.forms.prms.web.sysmanagement.montAprvBatch.cntchoice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.tool.FuncOrgType;
import com.forms.prms.tool.ImportDataType;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.montAprvBatch.apprv.domain.ApprvBean;
import com.forms.prms.web.sysmanagement.montAprvBatch.cntchoice.domain.CntChoiceBean;
import com.forms.prms.web.sysmanagement.montAprvBatch.cntchoice.service.CntChoiceService;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.service.ImportService;
import com.forms.prms.web.sysmanagement.montAprvBatch.export.service.ExportService;
import com.forms.prms.web.sysmanagement.user.domain.UserInfo;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/sysmanagement/montAprvBatch/cntchoice")
public class CntChoiceController {
	@Autowired
	private static final String PREFIX = "sysmanagement/montAprvBatch/cntchoice/";
	@Autowired
	private CntChoiceService cntChoiceService;
	@Autowired
	private ImportService importService;
	@Autowired
	private ExportService exportService;

	/**
	 * @methodName shList desc 省行列表查询
	 * @param apprvBean
	 * @return
	 */
	@RequestMapping("/shList.do")
	public String shList(CntChoiceBean bean) {
		bean.setOrgType("01");
		ReturnLinkUtils.addReturnLink("shList", FuncOrgType.ORG1_TYPE+"数据列表");
//		List<Map<String, Object>> authList = importService.getAuthList(WebHelp.getLoginUser().getRoleId(),
//				ImportDataType.shAuthList("aprv"));
//		List<Map<String, Object>> proTypeList = ImportDataType.getSelectList(authList, ImportDataType.shTag, "aprv");
//		WebUtils.setRequestAttr("selectList", proTypeList);
		List<CntChoiceBean> list = cntChoiceService.getList(bean);
		WebUtils.setRequestAttr("list", list);
		WebUtils.setRequestAttr("searchInfo", bean);
		return PREFIX + "list";
	}
	
	/**
	 * @methodName shList desc 分行列表查询
	 * @param apprvBean
	 * @return
	 */
	@RequestMapping("/fhList.do")
	public String fhList(CntChoiceBean bean) {
		bean.setOrgType("02");
		ReturnLinkUtils.addReturnLink("fhList", FuncOrgType.ORG2_TYPE+"数据列表");
//		List<Map<String, Object>> authList = importService.getAuthList(WebHelp.getLoginUser().getRoleId(),
//				ImportDataType.shAuthList("aprv"));
//		List<Map<String, Object>> proTypeList = ImportDataType.getSelectList(authList, ImportDataType.shTag, "aprv");
//		WebUtils.setRequestAttr("selectList", proTypeList);
		List<CntChoiceBean> list = cntChoiceService.getList(bean);
		WebUtils.setRequestAttr("list", list);
		WebUtils.setRequestAttr("searchInfo", bean);
		return PREFIX + "list";
	}


	/**
	 * 得到某批次号下的详情合同详细数据 
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/choiceDetail.do")
	public String choiceDetail(CntChoiceBean bean) {
		ReturnLinkUtils.addReturnLink("cntChoice", "返回勾选");
		// 得到待处理和合同退回的合同集合
		List<CntChoiceBean> waitManageBackList = cntChoiceService.waitManageBackList(bean);
		// 得到待复核的合同集合
		List<CntChoiceBean> waitCheckList = cntChoiceService.waitCheckList(bean);
		// 得到复核通过的合同集合
		List<CntChoiceBean> checkPassList = cntChoiceService.checkPassList(bean);
		WebUtils.setRequestAttr("waitManageBackList", waitManageBackList);
		WebUtils.setRequestAttr("waitCheckList", waitCheckList);
		WebUtils.setRequestAttr("checkPassList", checkPassList);
		WebUtils.setRequestAttr("bean", bean);
		//省行
		if("01".equals(bean.getOrgType())){
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
					+ "/sysmanagement/montAprvBatch/cntchoice/shList.do?VISIT_FUNC_ID=08110401");
		}
		//分行
		if("02".equals(bean.getOrgType())){
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
					+ "/sysmanagement/montAprvBatch/cntchoice/fhList.do?VISIT_FUNC_ID=08110402");
		}
		//如果是待复核的则查看详情
		if("waitCheck".equals(bean.getIsCheck())){
			return PREFIX+"checkDetail";
		}
		else{
			return PREFIX + "choiceDetail";
		}
		
	}

	/**
	 * 勾选合同
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/choiceCnt.do")
	public String choiceCnt(CntChoiceBean bean) {
		cntChoiceService.choiceCnt(bean);
		WebUtils.getMessageManager().addInfoMessage("勾选合同成功！");
		//省行
		if("01".equals(bean.getOrgType())){
			ReturnLinkUtils.setShowLink(new String[] { "cntChoice", "shList" });
		}
		//分行
		if("02".equals(bean.getOrgType())){
			ReturnLinkUtils.setShowLink(new String[] { "cntChoice", "fhList" });
		}
		return ForwardPageUtils.getSuccessPage();
		
	}
	/**
	 * 用户信息导出
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/exportData.do")
	@ResponseBody
	public String exportData(CntChoiceBean bean){
		AbstractJsonObject jsonObject = new SuccessJsonObject();		
		//Excel导出操作
		String taskId = null;
		try {
			taskId = cntChoiceService.userExport(bean);
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
