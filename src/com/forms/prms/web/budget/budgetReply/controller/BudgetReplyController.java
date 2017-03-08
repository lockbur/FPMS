package com.forms.prms.web.budget.budgetReply.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.budget.budgetReply.domain.BudgetReplyBean;
import com.forms.prms.web.budget.budgetReply.service.BudgetReplyService;

@Controller
@RequestMapping("/budget/budgetReply")
public class BudgetReplyController {
	
	public static final String BASE_URL = "budget/budgetReply/";
	
	@Autowired
	private BudgetReplyService service;
	
	/**
	 * 获取需要批复列表
	 * @param budgetReplyBean
	 * @return
	 */
	@RequestMapping("list.do")
	@AddReturnLink(id = "list", displayValue = "返回列表")
	public String list(BudgetReplyBean budgetReplyBean) {
		List<BudgetReplyBean> list = new ArrayList<BudgetReplyBean>();
		//登录用户所在二级网点为空则取一级网点
//		if(Tool.CHECK.isEmpty(WebHelp.getLoginUser().getOrg2Code())) {
//			budgetReplyBean.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
//		} else {
//			budgetReplyBean.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
//		}
		budgetReplyBean.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		// 判断 登陆用户的org2_code_ori 是空的就用org 如果不是 就用 本身 那么在存储过
		budgetReplyBean.setOrg2Name(WebHelp.getLoginUser().getOrg2Name());
		budgetReplyBean.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		list = service.list(budgetReplyBean);
		WebUtils.setRequestAttr("list", list);
		return BASE_URL + "list";
	}
	
	/**
	 * 进入批复详情页面
	 * @param budgetReplyBean
	 * @return
	 */
	@RequestMapping("replyPage.do")
	@AddReturnLink(id="reply", displayValue="重新批复")
	public String replyPage(BudgetReplyBean budgetReplyBean) {
		BudgetReplyBean reply = service.getOneTemp(budgetReplyBean.getTmpltId());
		WebUtils.setRequestAttr("reply", reply);
		if ("0".equals(reply.getDataAttrType())) {
			//资产
			reply.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
		}else {
			//费用
			reply.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		}
		List<BudgetReplyBean> montList = service.getMont(reply);
		WebUtils.setRequestAttr("montList", montList);
		return BASE_URL + "replyPage";
	}
	
	/**
	 * 预算批复提交
	 * @param budgetReplyBean
	 * @return
	 */
	@RequestMapping("reply.do")
	@ResponseBody
	public String reply(BudgetReplyBean budgetReplyBean) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		String result = null;
		budgetReplyBean.setReplyOper(WebHelp.getLoginUser().getUserId());
		result = service.reply(budgetReplyBean);
		if(result != null) {
			jsonObject.put("result", result);
		}else {
			jsonObject.put("result", true);
		}
		return jsonObject.writeValueAsString();
	}
	/**
	 * 检查模板预算修改不能小于已经分解的预算
	 * @param budgetReplyBean
	 * @return
	 */
	@RequestMapping("upTmpltCheck.do")
	@ResponseBody
	public String upTmpltCheck(BudgetReplyBean budgetReplyBean) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		String result = null;
		//查询模板已经分解的金额
		result = service.getTmpltHaveSplit(budgetReplyBean);
		jsonObject.put("result", result);
		return jsonObject.writeValueAsString();
	}
	/**
	 * 检查指标预算修改不能小于已经分解的预算
	 * @param budgetReplyBean
	 * @return
	 */
	@RequestMapping("upMontCheck.do")
	@ResponseBody
	public String upMontCheck(BudgetReplyBean budgetReplyBean) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		String result = null;
		//查询模板已经分解的金额
		result = service.getMontHaveSplit(budgetReplyBean);
		jsonObject.put("result", result);
		return jsonObject.writeValueAsString();
	}

}
