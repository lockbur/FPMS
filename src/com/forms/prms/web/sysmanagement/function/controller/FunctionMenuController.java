package com.forms.prms.web.sysmanagement.function.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.authority.domain.Function;
import com.forms.platform.core.util.Tool;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.FailureJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.function.service.FunctionService;

@Controller
@RequestMapping("/common/function")
public class FunctionMenuController {

	@Autowired
	private FunctionService functionService;
	
	@RequestMapping("getSubMenu.do")
	@ResponseBody
	public String getSubMenu(String menuId) {
		AbstractJsonObject jsonObj = null;
		if(Tool.CHECK.isEmpty(WebHelp.getLoginUser())){
			jsonObj = new SuccessJsonObject();
			jsonObj.put("userNotFound", "userNotFound");
		}else{
			List<Function> subMenuList = functionService.getSubMenu(
					WebHelp.getLoginUser(), menuId);
			if (subMenuList != null && !subMenuList.isEmpty()) {
				jsonObj = new SuccessJsonObject();
				jsonObj.put("subMenuList", subMenuList);
			} else {
				jsonObj = new FailureJsonObject("message.submenu.not.found");
			}
		}
		return jsonObj.writeValueAsString();
	}

}
