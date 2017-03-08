package com.forms.prms.web.sysmanagement.promptinfo.controller;

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
import com.forms.platform.web.returnlink.annotation.ShowReturnLink;
import com.forms.prms.web.sysmanagement.promptinfo.domain.PromptInfoBean;
import com.forms.prms.web.sysmanagement.promptinfo.service.PromptInfoService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/sysmanagement/promptinfo")
public class PromptInfoController {
	
	@Autowired
	private PromptInfoService service;
	
	private final static String PROMPT_URL = "sysmanagement/promptinfo/";
	
	@RequestMapping("list.do")
	@AddReturnLink(id="list", displayValue="返回列表")
	public String list(PromptInfoBean promptInfoBean) {
		List<PromptInfoBean> list = new ArrayList<PromptInfoBean>();
		list = service.query(promptInfoBean);
		WebUtils.setRequestAttr("list", list);
		WebUtils.setRequestAttr("selectBean", promptInfoBean);
		return PROMPT_URL + "list";
	}
	
	@RequestMapping("ajaxEdit.do")
	@ResponseBody
	public String ajaxEdit(PromptInfoBean promptInfoBean) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		String result = null;
		result = service.edit(promptInfoBean);
		if (result != null)
			jsonObject.put("isDone", false);
		else
			jsonObject.put("isDone", true);

		return jsonObject.writeValueAsString();
		
	}
	
	@RequestMapping("delete.do")
	@ShowReturnLink(showLinks="list")
	public String delete(PromptInfoBean promptInfoBean) {
		service.delete(promptInfoBean);
		WebUtils.getMessageManager().addInfoMessage("操作成功！");
		return ForwardPageUtils.getSuccessPage();
	}
	
	@RequestMapping("add.do")
	@ShowReturnLink(showLinks="list")
	public String add(PromptInfoBean promptInfoBean) {
		service.add(promptInfoBean);
		WebUtils.getMessageManager().addInfoMessage("操作成功！");
		return ForwardPageUtils.getSuccessPage();
	}
	
	@RequestMapping("ajaxCheck.do")
	@ResponseBody
	public String ajaxCheck(PromptInfoBean promptInfoBean) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		int result = 0;
		result = service.checkPrimaryKey(promptInfoBean);
		if (result != 0)
			jsonObject.put("isExist", true);
		else
			jsonObject.put("isExist", false);
		
		return jsonObject.writeValueAsString();
	}

}
