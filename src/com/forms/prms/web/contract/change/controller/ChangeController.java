package com.forms.prms.web.contract.change.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.contract.change.domain.ChangeForm;
import com.forms.prms.web.contract.change.service.ChangeService;
import com.forms.prms.web.contract.query.service.ContractQueryService;
import com.forms.prms.web.util.ForwardPageUtils;

/**
 * 合同更改
 * 
 * 查看合同列表：查询合同状态为'20'(合同确认完成)，以及创建中心为用户所在 责任中心的数据信息。
 * 
 * 发起变更：将合同状态改为'21'(变更申请中)，将发起变更操作备注存入流水表。
 * 
 * @author LinJia
 * @date 2015-01-28
 * 
 */
@Controller
@RequestMapping("/contract/change")
public class ChangeController {

	@Autowired
	private ChangeService cService;
	@Autowired
	private ContractQueryService qService;

	private static final String TO = "contract/change/";

	/**
	 * 变更申请列表 -- 03020401
	 * 
	 * @param form
	 * @return
	 */
	@RequestMapping("list.do")
	@AddReturnLink(id="ChangeList",displayValue="返回合同列表")
	public String list(ChangeForm form) {
		WebUtils.setRequestAttr("cList", cService.list(form));
		WebUtils.setRequestAttr("c", form);
		return TO + "list";
	}
	
	@RequestMapping("org1List.do")
	public String org1List(ChangeForm con) {
		con.setOrgFlag("1");//省行
		return contractQuery(con);
	}
	
	@RequestMapping("org2List.do")
	public String org2List(ChangeForm con) {
		con.setOrgFlag("2");//二级行
		return contractQuery(con);
	}
	
	@RequestMapping("dutyCodeList.do")
	public String dutyCodeList(ChangeForm con) {
		con.setOrgFlag("3");//业务部门
		return contractQuery(con);
	}
	
	@RequestMapping("queryList.do")
	public String contractQuery(ChangeForm form)
	{
		
		ReturnLinkUtils.addReturnLink("cntList", "返回合同列表");
		WebUtils.setRequestAttr("cList", cService.list(form));
		WebUtils.setRequestAttr("c", form);
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		return TO + "list";
	}

	/**
	 * 合同详情
	 * 
	 * @param form
	 *            --0302040101
	 * @return
	 */
	@RequestMapping("view.do")
	public String view(ChangeForm form) {
		WebUtils.setRequestAttr("cnt", qService.getDetailCheck(form.getCntNum()));
		WebHelp.setLastPageLink("uri", "cntList");
		return TO + "view";
	}

	/**
	 * 发起变更 --0302040102
	 * 
	 * @param form
	 * @return
	 */
	@RequestMapping("toChange.do")
	public String toChange(ChangeForm form) {
		ReturnLinkUtils.setShowLink("ChangeList");
		if (cService.toChange(form) > 0) {
			WebUtils.getMessageManager().addInfoMessage("发起变更成功！");
			return ForwardPageUtils.getSuccessPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("发起变更失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}

}
