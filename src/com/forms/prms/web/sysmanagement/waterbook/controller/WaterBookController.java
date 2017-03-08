package com.forms.prms.web.sysmanagement.waterbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.web.WebUtils;
import com.forms.prms.web.sysmanagement.waterbook.domain.WaterBook;
import com.forms.prms.web.sysmanagement.waterbook.service.WaterBookService;

@Controller
@RequestMapping("/sysmanagement/waterbook")
/**
 * 插入信息至流水表TL_WATER_BOOK
 * 业务逻辑：将业务操作信息插入流水表
 * 
 * 查询同一业务的流水列表
 * 业务逻辑：根据业务号查询流水列表
 * 
 * 查询某一流水的详细信息
 * 业务逻辑：根据流水号查询相关信息
 * 
 * author : liys <br>
 * date : 2015-01-23<br>
 * 
 */
public class WaterBookController {

	private static final String PREFIX = "sysmanagement/waterbook/";

	@Autowired
	private WaterBookService service;

	@RequestMapping("insertWaterBook.do")
	public void insertWaterBook(WaterBook wb) {
		service.insert(wb);
	}

	@RequestMapping("getWBList.do")
	public String getWBList(String busNum) {
		WebUtils.setRequestAttr("wbList", service.getWBList(busNum));

		return PREFIX + "wblist";
	}

	@RequestMapping("getWBDtl.do")
	public String getWBDtl(String wbNum) {
		WebUtils.setRequestAttr("wbInfo", service.getWBDtl(wbNum));
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/waterbook/getWBList.do?VISIT_FUNC_ID=0305");

		return PREFIX + "detail";
	}
}
