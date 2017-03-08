package com.forms.prms.web.sysmanagement.concurrent.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.prms.web.sysmanagement.concurrent.domain.ConcurrentBean;
import com.forms.prms.web.sysmanagement.concurrent.service.ConcurrentService;

@Controller
@RequestMapping("/common/concurrentController")
public class ConcurrentController {
	@Autowired 
	private ConcurrentService service;
	/**
	 * 页面ajax校验
	 * @param bean
	 * @return
	 */
	@RequestMapping("concurrent.do")
	@ResponseBody
	public String aprvTransfer(ConcurrentBean bean) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
//		Map<String, Object> map = service.concurrent(bean);
//		jsonObject.put("data", map);
		return jsonObject.writeValueAsString();
	}
	
}
