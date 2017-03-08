package com.forms.prms.web.amortization.accEntry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.web.WebUtils;
import com.forms.prms.web.amortization.accEntry.service.AccEntryService;
import com.forms.prms.web.contract.query.domain.QueryContract;
import com.forms.prms.web.contract.query.service.ContractQueryService;

@Controller
@RequestMapping("/amortization/accEntry")
public class AccEntryController {
	
	private static final String FUNCTION ="amortization/accEntry/";
	@Autowired
	private AccEntryService service;
	
	@Autowired
	private ContractQueryService cService;
	
	/**
	 * 查看合同列表 040402
	 * @param con
	 * @return
	 */
	@RequestMapping("cntList1.do")
	public String cntList1(QueryContract con){
		con.setIsPrepaidProvision("0");
		con.setNotDataFlag("y");
		con.setOrgFlag("1");//省行
		WebUtils.setRequestAttr("cntList", cService.queryList(con));
		WebUtils.setRequestAttr("con", con);
		WebUtils.setRequestAttr("type", 1);
		return FUNCTION + "cntList";
	}
	/**
	 * 查看合同列表 040403
	 * @param con
	 * @return
	 */
	@RequestMapping("cntList2.do")
	public String cntList2(QueryContract con){
		con.setIsPrepaidProvision("0");
		con.setNotDataFlag("y");
		con.setOrgFlag("2");//二级行
		WebUtils.setRequestAttr("cntList", cService.queryList(con));
		WebUtils.setRequestAttr("con", con);
		WebUtils.setRequestAttr("type", 2);
		return FUNCTION + "cntList";
	}
	/**
	 * 查看合同列表 040404
	 * @param con
	 * @return
	 */
	@RequestMapping("cntListDuty.do")
	public String cntListDuty(QueryContract con){
		con.setIsPrepaidProvision("0");
		con.setNotDataFlag("y");
		con.setOrgFlag("3");//业务部门
		WebUtils.setRequestAttr("cntList", cService.queryList(con));
		WebUtils.setRequestAttr("con", con);
		WebUtils.setRequestAttr("type", 3);
		return FUNCTION + "cntList";
	}
	
	/**
	 * 根据合同号查看会计分录 040401
	 */
	@RequestMapping("getAccEntry.do")
	public String getAccEntry(String cntNum,String startDate,String endDate,String uri){
		WebUtils.setRequestAttr("cntNum",cntNum);
		WebUtils.setRequestAttr("startDate",startDate);
		WebUtils.setRequestAttr("endDate",endDate);
		WebUtils.setRequestAttr("accList", service.getAccEntry(cntNum,startDate,endDate));
		return FUNCTION + "accEntry";
	}

	
}
