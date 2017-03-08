package com.forms.prms.web.sysmanagement.orgManage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.web.sysmanagement.orgManage.domain.OrgBean;
import com.forms.prms.web.sysmanagement.orgManage.service.OrgManageService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/sysmanagement/orgController")
public class OrgManageController {
	private static final String PREFIX = "sysmanagement/org/";
	@Autowired
	private OrgManageService service;

	@RequestMapping("/list.do")
	public String list(OrgBean bean) {
		ReturnLinkUtils.addReturnLink("orgList", "返回机构列表");
		if (bean == null)
			bean = new OrgBean();

		if (Tool.CHECK.isEmpty(bean.getIsLocked())) {
			bean.setIsLocked("0");
		}
		List<OrgBean> ouList = service.getOuList(bean);
		List<OrgBean> orgList = service.getOrgList(bean);
		WebUtils.setRequestAttr("ouList", ouList);
		WebUtils.setRequestAttr("orgList", orgList);
		List<OrgBean> list = service.getPageList(bean);
		WebUtils.setRequestAttr("list", list);
		WebUtils.setRequestAttr("searchBean", bean);

		return PREFIX + "list";
	}

	/**
	 * 修改扫描岗
	 * 
	 * @param bean
	 * @return
	 */
	// @RequestMapping("changeScanPosition.do")
	// @ResponseBody
	// public String changeScanPosition(OrgBean bean){
	// AbstractJsonObject jsonObject = new SuccessJsonObject();
	// boolean checkMont = service.changeScanPosition(bean);
	// jsonObject.put("data", checkMont);
	// return jsonObject.writeValueAsString();
	// }
	/**
	 * 明细
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("detail.do")
	public String detail(OrgBean bean) {
		OrgBean bean2 = service.getBean(bean);
		WebUtils.setRequestAttr("bean", bean2);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
				+ "/sysmanagement/orgController/list.do?VISIT_FUNC_ID=010403");
		return PREFIX + "detail";
	}

	/**
	 * 根据二级行代码查找ou以及机构集合
	 * 
	 * @param org2Code
	 * @return
	 */
	@RequestMapping("getOuList.do")
	@ResponseBody
	public String getOuList(OrgBean bean) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		List<OrgBean> ouList = service.getOuList(bean);
		List<OrgBean> orgList = service.getOrgList(bean);
		if (ouList != null) {
			jsonObject.put("ouList", ouList);
		} else {
			jsonObject.put("ouList", null);
		}
		if (orgList != null) {
			jsonObject.put("orgList", orgList);
		} else {
			jsonObject.put("orgList", null);
		}

		return jsonObject.writeValueAsString();
	}
	/**
	 * 锁定监控指标
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("lock.do")
	public String lock(OrgBean bean) {
		service.lock(bean);
		WebUtils.getMessageManager().addInfoMessage("锁定责任中心成功!");
		ReturnLinkUtils.setShowLink("orgList");
		return ForwardPageUtils.getSuccessPage();
	}
	/**
	 * 锁定监控指标
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("openLock.do")
	public String openLock(OrgBean bean) {
		service.openLock(bean);
		WebUtils.getMessageManager().addInfoMessage("解锁责任中心成功!");
		ReturnLinkUtils.setShowLink("orgList");
		return ForwardPageUtils.getSuccessPage();
	}
	
}
