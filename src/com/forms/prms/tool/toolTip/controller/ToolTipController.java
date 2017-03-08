package com.forms.prms.tool.toolTip.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.prms.web.init.SystemInitServlet;
import com.forms.prms.web.sysmanagement.promptinfo.domain.PromptInfoBean;

@Controller
@RequestMapping("/common/toolTip")
public class ToolTipController {
	@RequestMapping("getMsg.do")
	@ResponseBody
	public String getMsg(HttpServletRequest request){
		Map<String ,Object> map =new HashMap<String,Object>();
		//从内存中拿到所有的 提示信息
		List<PromptInfoBean> promptInfoList =  SystemInitServlet.list;
		String name = request.getParameter("name");
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		boolean flag  = false;
		if (null!=promptInfoList && promptInfoList.size()>0) {
			for(int i=0;i<promptInfoList.size();i++){
				name = name.trim();
				name.replaceAll("", " ");
				if (promptInfoList.get(i).getDispName().equals(name)) {
					flag = true;
					//匹配
					map.put("flag", true);
					map.put("value",promptInfoList.get(i).getPromptInfo());
					break;
				}
			}
		}
		if (!flag) {
			map.put("flag", false);
		}
		jsonObject.put("data", map);
		return jsonObject.writeValueAsString();
	}
}
