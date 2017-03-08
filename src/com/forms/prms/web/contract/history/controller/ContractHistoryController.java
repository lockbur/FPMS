package com.forms.prms.web.contract.history.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.web.WebUtils;
import com.forms.prms.web.contract.history.domain.ContractHistory;
import com.forms.prms.web.contract.history.service.ContractHistoryService;

/**
 * 合同历史记录查询
 * @author user
 */
@Controller
@RequestMapping("/contract/history/")
public class ContractHistoryController {
	@Autowired
	private ContractHistoryService service;

	private static final String FUNCTION = "contract/history/";
	
	@RequestMapping("queryList.do")
	public String ContractQuery(ContractHistory con) {
		
		WebUtils.setRequestAttr("cntList", service.queryList(con));
		WebUtils.setRequestAttr("con", con);

		return FUNCTION + "querylist";
	}
	
	@RequestMapping("cntDtl.do")
	public String ContractDetail(ContractHistory bean) {

		ContractHistory cnt = service.getDetail(bean.getCntNum(),bean.getVersionNo());
		WebUtils.setRequestAttr("cnt", cnt);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/contract/history/queryList.do?VISIT_FUNC_ID=030208");

		return FUNCTION + "cntDtl";
	}
	/**
	 * @methodName queryFeeType
	 * desc 查询受益费用页面  
	 * 
	 * @param con
	 * @return
	 */
	@RequestMapping("queryFeeTypePage.do")
	public String queryFeeType(String cntNum,String versionNo){
		WebUtils.setRequestAttr("queryFeeTypeList", service.queryFeeType(cntNum,versionNo));
		return "contract/query/queryFeeTypePage";
	}
}
