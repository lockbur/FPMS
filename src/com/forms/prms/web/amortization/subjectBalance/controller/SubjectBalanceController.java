package com.forms.prms.web.amortization.subjectBalance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.web.WebUtils;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.amortization.subjectBalance.domian.SubjectBalanceBean;
import com.forms.prms.web.amortization.subjectBalance.service.SubjectBalanceService;

@Controller
@RequestMapping("/amortization/subjectBalance")
public class SubjectBalanceController {

	private static final String FUNCTION ="amortization/subjectBalance/";
	@Autowired
	private SubjectBalanceService service;
	
	@RequestMapping("subBalanceList.do")
	public String getSubjectBalanceList(SubjectBalanceBean bean){
		if(null ==  bean){
			bean = new SubjectBalanceBean();
		}
		List<SubjectBalanceBean> list = service.getSubjectBalanceList(bean);
		WebUtils.setRequestAttr("org1Code", WebHelp.getLoginUser().getOrg1Code());
		WebUtils.setRequestAttr("selectInfo", bean);
		WebUtils.setRequestAttr("subBalanceList", list);
		return FUNCTION + "list";
	}
	
	@RequestMapping("subBalanceDtl.do")
	public String getSubjetcBalanceDetail(String batchNo, String seqNo){
		SubjectBalanceBean bean = service.getDetail(batchNo, seqNo);
		WebUtils.setRequestAttr("bean", bean);
		return FUNCTION + "subBalanceDtl";
	}
}
