package com.forms.prms.web.projmanagement.projectMgr.controller;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.platform.web.token.PreventDuplicateSubmit;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.contract.modify.domain.ModifyContract;
import com.forms.prms.web.projmanagement.projectMgr.domain.Project;
import com.forms.prms.web.projmanagement.projectMgr.service.ProjectMgrService;
import com.forms.prms.web.sysmanagement.montAprvBatch.export.service.ExportService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/projmanagement/projectMgr")
/**
 * 
 * @author LinJia 
 * 
 * 项目新增： 
 * 项目新增中项目类型、项目名称、起始日期为必填项。项目类型可通过
 * 弹出框选择已存在的项目类型，也可以自己新增项目类型。新增时，根
 * 据安全性的选择，决定是否出现责任中心的信息录入。若安全性为责任
 * 中心，新增时提供一级行责任中心机构树供选择，可多选(至少选择一个
 * )。 起始日期，项目起始日期必须大于等于录入日期。
 * 
 * 项目查询列表：
 * 项目查询列表的权限为：前提——1.如果登陆
 * 用户为超级管理员，则显示所属一级行下的所有项目信息。2.若用户为非
 * 超级管理员，项目的安全性为'1'(责任中心)，显示责任中心关系表中存
 * 在登陆用户所属责任中心与项目的对应关系的项目信息，以及登陆用户所
 * 属责任中心为项目创建责任中心显示项目信息。3.若用户为非超级管理员，
 * 只显示用户所属一级分行下项目的安全性为'0'(全局)的项目列表，项目
 * 查询列表中只提供查看详情功能。
 * 
 * 项目修改列表：
 * 修改列表查看的权限为：前提——当前日期在项目日期范围内；如果登陆用
 * 户所属责任中心为项目创建责任中心显示项目信息。修改列表提供修改以
 * 及终止项目功能。
 * 
 * 修改功能：
 * 修改功能可对项目类型、项目名称、项目预算、安全性及项目描述进行
 * 修改。针对项目预算的修改，需要符合“项目预算金额大于合同金额”的
 * 要求，提供异步校验。安全性的选择决定是否录入责任中心与项目的关
 * 系信息，同项目新增。
 * 
 * 终止功能：
 * 项目终止要求终止日期大于等于当日日期，终止日期不能为空。将项目终
 * 止意见连操作内容添加到流水记录中。
 * 
 * 
 */
public class ProjectMgrController {
	@Autowired
	private ProjectMgrService pService;
	@Autowired
	private ExportService exportService;

	private static final String FUNCTION = "projmanagement/projectMgr/";

	/**
	 * 新增页面 --030101
	 * 
	 * @return
	 */
	@RequestMapping("preAdd.do")
	@AddReturnLink(id="preAddProj",displayValue="新增项目")
	public String preAdd() {
		String isSuper = WebHelp.getLoginUser().getIsSuperAdmin();
		String org = WebHelp.getLoginUser().getOrg2Code();
		if("1".equals(isSuper)){
			org = WebHelp.getLoginUser().getOrg1Code();
		}
		WebUtils.setRequestAttr("isSuper",isSuper);
		WebUtils.setRequestAttr("org",org);
		return FUNCTION + "add";
	}

	/**
	 * 项目新增 --03010101
	 * 
	 * @param proj
	 * @return
	 */
	@RequestMapping("add.do")
	@PreventDuplicateSubmit
	public String add(Project proj) {
		if (proj.getDutyCodeList() == null || (proj.getScope().equals("3") && proj.getDutyCodeList().length < 1)) {
			ReturnLinkUtils.setShowLink("preAddProj");
			WebUtils.getMessageManager().addInfoMessage("责任中心不能为空！");
			CommonLogger.error("项目新增失败，责任中心不能为空！,ProjectMgrController,add()");
			return ForwardPageUtils.getErrorPage();
		}
		if (pService.addProj(proj)) {
			ReturnLinkUtils.setShowLink("preAddProj");
			WebUtils.getMessageManager().addInfoMessage("添加项目成功！");
			return ForwardPageUtils.getSuccessPage();
		} else {
			ReturnLinkUtils.setShowLink("preAddProj");
			WebUtils.getMessageManager().addInfoMessage("添加项目失败！");
			CommonLogger.error("项目新增失败！,ProjectMgrController,add()");
			return ForwardPageUtils.getErrorPage();
		}
	}

	/**
	 * 项目类型页面--03010102
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("projTypePage.do")
	public String projTypePage(Project param) {
		List<Project> pTypes = pService.getProjectType();
		WebUtils.setRequestAttr("pTypes", pTypes);
		return FUNCTION + "projTypePage";
	}

	/**
	 * 异步校验预算金额--03010103
	 * 
	 * @param proj
	 * @return
	 */
	@RequestMapping("ajaxCheckAmt.do")
	@ResponseBody
	public String ajaxCheckAmt(Project proj) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		Project P = pService.checkAmt(proj);
		if (!Tool.CHECK.isEmpty(P)) {
			jsonObject.put("pass", true);
			CommonLogger.info("校验预算金额，在预算范围内！,ProjectMgrController,ajaxCheckAmt()");
		} else {
			jsonObject.put("pass", false);
			CommonLogger.info("校验预算金额，超出预算范围！,ProjectMgrController,ajaxCheckAmt()");
		}
		return jsonObject.writeValueAsString();
	}

	/**
	 * 查询项目列表 --030102
	 * 
	 * @param proj
	 * @return
	 */
	@RequestMapping("list.do")
	@AddReturnLink(id="pList",displayValue="返回项目列表")
	public String list(Project proj) {
		List<Project> pList = pService.listProj(proj);
		WebUtils.setRequestAttr("pList", pList);
		WebUtils.setRequestAttr("proj", proj);
		return FUNCTION + "list";
	}

	/**
	 * 查看项目详情（修改页面）--03010201
	 * 
	 * @param projId
	 * @param jspPage
	 * @return
	 */
	@RequestMapping("view.do")
	public String view(String projId, String jspPage) {
		Project proj = pService.veiwProj(projId);
		String isSuper = WebHelp.getLoginUser().getIsSuperAdmin();
		String org = WebHelp.getLoginUser().getOrg2Code();
		if("1".equals(isSuper)){
			org = WebHelp.getLoginUser().getOrg1Code();
		}
		WebUtils.setRequestAttr("isSuper",isSuper);
		WebUtils.setRequestAttr("org",org);
		WebUtils.setRequestAttr("proj", proj);
		if(jspPage.equals("view")){
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/projmanagement/projectMgr/list.do?VISIT_FUNC_ID=021002");
		}else{
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/projmanagement/projectMgr/updList.do?VISIT_FUNC_ID=021003");
		}
		return FUNCTION + jspPage;
	}

	/**
	 * 修改项目列表 --030103
	 * 
	 * @param proj
	 * @return
	 */
	@RequestMapping("updList.do")
	@AddReturnLink(id="updList",displayValue="返回项目列表")
	public String updList(Project proj) {
		List<Project> pList = pService.listUpdProj(proj);
		WebUtils.setRequestAttr("pList", pList);
		WebUtils.setRequestAttr("proj", proj);
		return FUNCTION + "updList";
	}

	/**
	 * 获取项目OPtion 选项 -- 030104
	 * 
	 * @param proj
	 * @return
	 */
	@RequestMapping("projOptionPage.do")
	public String projOptionPage(Project proj) {
		if("0".equals(proj.getIsContract())){
			List<Project> pList = pService.getProjOption1(proj);
			WebUtils.setRequestAttr("pList", pList);
			WebUtils.setRequestAttr("proj", proj);
			return FUNCTION + "projOptionPageContract";
		}
		else{
			List<Project> pList = pService.getProjOption(proj);
			WebUtils.setRequestAttr("pList", pList);
			WebUtils.setRequestAttr("proj", proj);
			WebUtils.setRequestAttr("projId", proj.getProjId());
			return FUNCTION + "projOptionPage";
		}
		
	}

	/**
	 * 更新项目修改信息 -- 03010301
	 * 
	 * @return
	 */
	@PreventDuplicateSubmit
	@RequestMapping("update.do")
	public String updProject(Project proj) {
		if (proj.getDutyCodeList() == null || (proj.getScope().equals("3") && proj.getDutyCodeList().length < 1)) {
			ReturnLinkUtils.setShowLink("updList");
			WebUtils.getMessageManager().addInfoMessage("责任中心不能为空！");
			CommonLogger.info("修改项目信息失败，责任中心不能为空！,ProjectMgrController,updProject()");
			return ForwardPageUtils.getErrorPage();
		}
		int i = pService.updProject(proj);
		if (i > 0) {
			WebUtils.getMessageManager().addInfoMessage("修改成功！");
			CommonLogger.info("修改项目信息成功！,ProjectMgrController,updProject()");
			ReturnLinkUtils.setShowLink("updList");
			return ForwardPageUtils.getSuccessPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("修改失败！");
			CommonLogger.info("修改项目信息失败！,ProjectMgrController,updProject()");
			ReturnLinkUtils.setShowLink("updList");
			return ForwardPageUtils.getErrorPage();
		}
	}

	/**
	 * 项目终止 -- 03010302
	 * 
	 * @param proj
	 * @return
	 */
	@PreventDuplicateSubmit
	@RequestMapping("endProj.do")
	public String endProj(Project proj) {
		ReturnLinkUtils.setShowLink("updList");
		if (pService.endProj(proj)) {
			WebUtils.getMessageManager().addInfoMessage("项目终止成功");
			CommonLogger.info("项目终止成功！,ProjectMgrController,endProj()");
			ReturnLinkUtils.setShowLink("updList");
			return ForwardPageUtils.getSuccessPage();
		}
		WebUtils.getMessageManager().addInfoMessage("项目终止失败");
		CommonLogger.info("项目终止失败！,ProjectMgrController,endProj()");
		ReturnLinkUtils.setShowLink("updList");
		return ForwardPageUtils.getErrorPage();

	}
	
	/**
	 * 异步校验项目的有效时间
	 * 
	 * @param proj
	 * @return
	 */
	@RequestMapping("ajaxCheckDate.do")
	@ResponseBody
	public String ajaxCheckDate(ModifyContract modifyContract) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		Set<String> set = new HashSet<String>();
		String[] projId = modifyContract.getProjId();
		for(int i=0;i<projId.length;i++){
			Project proj = pService.veiwProj(projId[i]);
			//String nowDate = Tool.DATE.getDate();
			String signDate=modifyContract.getSignDate();
			if(this.compare_date(proj.getStartDate(),signDate) == 1 || this.compare_date(proj.getEndDate(),signDate) == -1){
				set.add("【项目ID:"+proj.getProjId()+",名称："+proj.getProjName()+"】对应的有效期区间不在合同签订日期中,请重新选择项目！");
			}
		}
		jsonObject.put("set", set);
		return jsonObject.writeValueAsString();
	}

	
	private  int compare_date(String DATE1, String DATE2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
//				System.out.println("dt1 在dt2后");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
//				System.out.println("dt1在dt2前");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
		
	public static void main(String[] args) {
		System.out.println(new ProjectMgrController().compare_date("2014-07-08","2015-07-08"));
	}
	/**
	 * 信息导出
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/exportData.do")
	@ResponseBody
	public String exportData(Project project){
		AbstractJsonObject jsonObject = new SuccessJsonObject();		
		//Excel导出操作
		String taskId = null;
		try {
			taskId = pService.exportData(project);
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
