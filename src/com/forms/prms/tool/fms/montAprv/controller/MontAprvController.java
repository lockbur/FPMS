package com.forms.prms.tool.fms.montAprv.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.fms.montAprv.bean.MontAprvBean;
import com.forms.prms.tool.fms.montAprv.service.MontAprvService;

@Controller
@RequestMapping("/common/montAprvTransfer")
public class MontAprvController {
	@Autowired
	private MontAprvService service;
	/**
	 * 页面ajax校验
	 * @param bean
	 * @return
	 */
	@RequestMapping("aprvTransfer.do")
	@ResponseBody
	public String aprvTransfer(MontAprvBean bean) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		if ("00".equals(bean.getType())) {
			//所有类型
			
			bean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
			bean.setOrg2Code(WebHelp.getLoginUser().getOrg2Code());
		}else if ("01".equals(bean.getType())) {
			bean.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
		}else if ("11".equals(bean.getType()) || "12".equals(bean.getType())) {
			bean.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
		}else if ("21".equals(bean.getType())) {
			bean.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		}
		Map<String, Object> map = service.transferData(bean.getType(),bean.getOrg21Code(),bean.getPosition(),bean.getIsReturn());
		jsonObject.put("data", map);
		return jsonObject.writeValueAsString();
	}
	
}
