package com.forms.prms.web.contract.deliver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.platform.web.token.PreventDuplicateSubmit;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.contract.deliver.domain.DeliverForm;
import com.forms.prms.web.contract.deliver.service.DeliverService;
import com.forms.prms.web.contract.query.service.ContractQueryService;
import com.forms.prms.web.util.ForwardPageUtils;

/**
 * 合同移交
 * 
 * 查看合同列表：查询合同状态为'20'合同确认完成以及付款责任中心为登陆用户 所在责任中心的数据信息。
 * 
 * 发起移交：付款责任中心人员发起移交，将合同状态更新为'25'（合同移交中）， 移交责任中心更新为所选责任中心代码，将发起移交备注存入流水表。
 * 
 * 查看移交列表：查询合同状态为'25'(合同移交中)且移交责任中心为用户所在责 任中心的数据信息。
 * 
 * 接受移交：将合同状态改为'20'(合同确认完成)，将移交责任中心代码赋值给付
 * 款责任中心字段PAY_DUTY_CODE，即将付款权限移交，将移交责任中心字段置空。 将接受移交操作备注保存入流水表。
 * 
 * 拒绝移交：将合同状态改为'20'(合同确认完成)，将移交责任中心字段置空。将 拒绝移交操作备注保存入流水表。
 * 
 * 查看可取消移交列表：查询合同状态为'25'(合同移交中)以及付款责任中心为登陆用户 所在责任中心的数据信息。
 * 
 * 取消移交：将合同状态改为'20'(合同确认完成)，将移交责任中心字段置空。 将取消移交操作备注保存入流水表。
 * 
 * 
 * @author LinJia
 * @date 2015-01-28
 */
@Controller
@RequestMapping("/contract/deliver")
public class DeliverController {

	@Autowired
	private DeliverService dService;
	@Autowired
	private ContractQueryService qService;

	private static final String TO = "contract/deliver/";

	/**
	 * 列表 -- 03020301
	 * 
	 * @param form
	 * @return
	 */
	@RequestMapping("list.do")
	@AddReturnLink(id="DeliverList",displayValue="返回合同列表")
	public String list(DeliverForm form) {
		WebUtils.setRequestAttr("dList", dService.list(form));
		WebUtils.setRequestAttr("d", form);
		return TO + "list";
	}

	/**
	 * 详情 -- 0302030101
	 * 
	 * @param form
	 * @return
	 */
	@RequestMapping("view.do")
	public String view(DeliverForm form) {
		WebUtils.setRequestAttr("cnt", qService.getDetailCheck(form.getCntNum()));
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/contract/deliver/list.do?VISIT_FUNC_ID=03020301");
		return TO + "view";
	}

	/**
	 * 发起移交 -- 0302030102
	 * 
	 * @param form
	 * @return
	 */
	@PreventDuplicateSubmit
	@RequestMapping("deliver.do")
	public String deliver(DeliverForm form) {
		ReturnLinkUtils.setShowLink("DeliverList");
		if (Tool.CHECK.isBlank(form.getDeliverDutyCode())) {
			WebUtils.getMessageManager().addInfoMessage("接受移交责任中心为空值,移交失败！");
			return ForwardPageUtils.getErrorPage();
		}
		if (dService.deliver(form) > 0) {
			WebUtils.getMessageManager().addInfoMessage("发起移交成功！");
			return ForwardPageUtils.getSuccessPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("发起移交失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}

	/**
	 * 接受移交列表 --03020302
	 * 
	 * @param form
	 * @return
	 */
	@RequestMapping("deliverList.do")
	@AddReturnLink(id="DeliveredList",displayValue="返回合同列表")
	public String deliverList(DeliverForm form) {
		WebUtils.setRequestAttr("deliverList", dService.deliverList(form));
		WebUtils.setRequestAttr("deliverDutyCode", WebHelp.getLoginUser().getDutyCode()); // 接收移交的机构
		WebUtils.setRequestAttr("deliver", form);
		return TO + "deliverList";
	}

	/**
	 * 接受移交详情 --0302030201
	 * 
	 * @param form
	 * @return
	 */
	@RequestMapping("deliverView.do")
	public String deliverView(DeliverForm form) {
		WebUtils.setRequestAttr("cnt", qService.getDetailCheck(form.getCntNum()));
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/contract/deliver/deliverList.do?VISIT_FUNC_ID=03020302");		
		return TO + "deliverView";
	}

	/**
	 * 接受移交 --0302030202
	 * 
	 * @param form
	 * @return
	 */
	@PreventDuplicateSubmit
	@RequestMapping("accept.do")
	public String accept(DeliverForm form) {
		ReturnLinkUtils.setShowLink("DeliveredList");
		if (Tool.CHECK.isBlank(form.getDeliverDutyCode())) {
			WebUtils.getMessageManager().addInfoMessage("接受移交责任中心为空值,移交失败！");
			return ForwardPageUtils.getErrorPage();
		}
		if (dService.accept(form) > 0) {
			WebUtils.getMessageManager().addInfoMessage("接受移交成功！");
			return ForwardPageUtils.getSuccessPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("接受移交失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}

	/**
	 * 拒绝移交 --0302030203
	 * 
	 * @param form
	 * @return
	 */
	@PreventDuplicateSubmit
	@RequestMapping("reject.do")
	public String reject(DeliverForm form) {
		ReturnLinkUtils.setShowLink("DeliveredList");
		if (dService.reject(form) > 0) {
			WebUtils.getMessageManager().addInfoMessage("拒绝移交成功！");
			return ForwardPageUtils.getSuccessPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("拒绝移交失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}

	/**
	 * 取消列表 -- 03020303
	 * 
	 * @param form
	 * @return
	 */
	@RequestMapping("cancelList.do")
	@AddReturnLink(id="cancelList",displayValue="返回合同列表")
	public String cancelList(DeliverForm form) {
		WebUtils.setRequestAttr("dList", dService.cancelList(form));
		WebUtils.setRequestAttr("d", form);
		return TO + "cancelList";
	}

	/**
	 * 取消移交详情 --0302030301
	 * 
	 * @param form
	 * @return
	 */
	@RequestMapping("cancelView.do")
	public String cancelView(DeliverForm form) {
		WebUtils.setRequestAttr("cnt", qService.getDetailCheck(form.getCntNum()));
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/contract/deliver/cancelList.do?VISIT_FUNC_ID=03020303");
		return TO + "cancelView";
	}

	/**
	 * 取消移交 --0302030302
	 * 
	 * @param form
	 * @return
	 */
	@PreventDuplicateSubmit
	@RequestMapping("cancel.do")
	public String cancel(DeliverForm form) {
		ReturnLinkUtils.setShowLink("cancelList");
		if (dService.cancel(form) > 0) {
			WebUtils.getMessageManager().addInfoMessage("取消移交成功！");
			return ForwardPageUtils.getSuccessPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("取消移交失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}

}
