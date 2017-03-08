package com.forms.prms.web.contract.freeze.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.platform.web.token.PreventDuplicateSubmit;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.contract.freeze.domain.Contract;
import com.forms.prms.web.contract.freeze.service.FreezeService;
import com.forms.prms.web.contract.query.service.ContractQueryService;
import com.forms.prms.web.util.ForwardPageUtils;

/**
 * 合同冻结
 * 
 * 查看可冻结合同列表：查询合同状态为'20'(合同确认完成)，以及付款责任中心为用户所在 责任中心的数据信息。
 * 
 * 查看解冻结合同列表：查询合同状态为'35'(合同冻结)，以及付款责任中心为用户所在责任 中心的数据信息。
 * 
 * 冻结合同：将合同状态改为'35'(合同冻结)，将冻结操作及备注意见存入流水表。
 * 
 * 解冻合同：将合同状态改为'20'(合同确认完成)，将解冻操作及备注意见存入流水表。
 * 
 * @author LinJia
 * @data 2015-01-28
 */

@Controller
@RequestMapping("/contract/freeze")
public class FreezeController {

	@Autowired
	private FreezeService fService;
	@Autowired
	private ContractQueryService qService;

	private static final String FUNCTION = "contract/freeze/";

	/**
	 * 可冻结列表 --03020501
	 * 
	 * @param c
	 * @return
	 */
	@RequestMapping("freezeList.do")
	@AddReturnLink(id="freezeList",displayValue="返回可冻结列表")
	public String freezeList(Contract c) {
		c.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		WebUtils.setRequestAttr("cList", fService.freezeList(c));
		WebUtils.setRequestAttr("c", c);
		return FUNCTION + "freezeList";
	}

	/**
	 * 可冻结合同详情 --0302050101
	 * 
	 * @param c
	 * @return
	 */
	@RequestMapping("viewFreeze.do")
	public String viewFreeze(Contract c) {
		WebUtils.setRequestAttr("cnt", qService.getDetailCheck(c.getCntNum()));
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/contract/freeze/freezeList.do?VISIT_FUNC_ID=03020501");
		return FUNCTION + "viewFreeze";
	}

	/**
	 * 冻结合同 --0302050102
	 * 
	 * @param c
	 * @return
	 */
	@PreventDuplicateSubmit
	@RequestMapping("freeze.do")
	public String freeze(Contract c) {
		ReturnLinkUtils.setShowLink("freezeList");
		if (fService.freeze(c) > 0) {
			WebUtils.getMessageManager().addInfoMessage("冻结成功！");
			return ForwardPageUtils.getSuccessPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("冻结失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}

	/**
	 * 可解冻列表 --03020502
	 * 
	 * @param c
	 * @return
	 */
	@RequestMapping("unfreezeList.do")
	@AddReturnLink(id="unfreezeList",displayValue="返回可解冻列表")
	public String unfreezeList(Contract unfreeze) {
		unfreeze.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		List<Contract> unfreezeList = fService.unfreezeList(unfreeze);
		WebUtils.setRequestAttr("unfreezeList", unfreezeList);
		WebUtils.setRequestAttr("unfreeze", unfreeze);
		return FUNCTION + "unfreezeList";
	}

	/**
	 * 可解冻合同详情 --0302050201
	 * 
	 * @param c
	 * @return
	 */
	@RequestMapping("viewUnfreeze.do")
	public String viewUnfreeze(Contract c) {
		WebUtils.setRequestAttr("cnt", qService.getDetailCheck(c.getCntNum()));
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/contract/freeze/unfreezeList.do?VISIT_FUNC_ID=03020502");
		return FUNCTION + "viewUnfreeze";
	}

	/**
	 * 解冻合同 --0302050202
	 * 
	 * @param c
	 * @return
	 */
	@PreventDuplicateSubmit
	@RequestMapping("unfreeze.do")
	public String unfreeze(Contract c) {
		ReturnLinkUtils.setShowLink("unfreezeList");
		if (fService.unfreeze(c) > 0) {
			WebUtils.getMessageManager().addInfoMessage("解冻成功！");
			return ForwardPageUtils.getSuccessPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("解冻失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}

}
