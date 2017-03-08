package com.forms.prms.web.contract.check.controller;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.platform.web.token.PreventDuplicateSubmit;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.contract.check.domain.ContractCheckBean;
import com.forms.prms.web.contract.check.service.ContractCheckService;
import com.forms.prms.web.contract.modify.service.ContractModifyService;
import com.forms.prms.web.contract.query.domain.QueryContract;
import com.forms.prms.web.contract.query.service.ContractQueryService;
import com.forms.prms.web.util.ForwardPageUtils;

/**
 * 业务逻辑：1.合同复核信息列表查询（条件：状态是合同录入；费用承担部门、物料编码、是否省行统购必须在 TB_APPROVE_CHAIN表存在）
 * 			2.合同复核信息明细查询-合同基本信息（条件：合同号）和物料设备信息（条件：合同号；费用承担部门、物料编码、是否省行统购必须在 TB_APPROVE_CHAIN表存在且对应的归口部门与登录用户所在机构一致）
 * 			3.合同复核通过（改变当前物料设备信息的状态为99-成功；再判断该合同下的物料状态是否已全部成功则改变合同的状态为12-合同待确认）
 * 			4.合同复核退回（改变当前物料设备信息的状态为01-退回）
 * author : lisj <br>
 * date : 2015-01-23<br>
 * 合同复核Action
 */
@Controller
@RequestMapping("/contract/check/")
public class ContractCheckController {

	private static final String CONTRACTCHECKPATH = "/contract/check/";

	@Autowired
	private ContractCheckService contractCheckService;

	@Autowired 
	private ContractQueryService queryService;
	
	@Autowired
	private ContractModifyService contractModifyService;
	/**
	 * 合同复核信息列表查询
	 * 
	 * @param constract
	 * @return
	 */
	@RequestMapping("contractList.do")
	@AddReturnLink(id = "constractList", displayValue = "合同复核信息列表查询")
	public String constractList(ContractCheckBean contractCheckBean) 
	{
		List<ContractCheckBean> list = contractCheckService.constractList(contractCheckBean);
		WebUtils.setRequestAttr("contractList", list);
		WebUtils.setRequestAttr("selectInfo", contractCheckBean);
		return CONTRACTCHECKPATH + "list";
	}

	/**
	 * 合同复核信息明细查询
	 * 
	 * @param constract
	 * @return
	 */
	@RequestMapping("checkDeail.do")
	public String checkDeail(ContractCheckBean contractCheckBean) 
	{
		String dutyCode = WebHelp.getLoginUser().getDutyCode();
		contractCheckBean.setDutyCode(dutyCode);
		//合同基本信息
		QueryContract contractInfo = queryService.getDetailCheckByDutyCode(contractCheckBean.getCntNum());
		contractCheckBean.setIsSpec(contractInfo.getIsSpec());
		//合同物料设备信息
		List<ContractCheckBean> deviceInfoList = contractCheckService.deviceDeail(contractCheckBean);
		
		//去重的物料信息
		List<ContractCheckBean> distinctDeviceInfoList = contractCheckService.distinctDeviceDeail(contractCheckBean);
		
		// 合同类型：1-费用类 && 费用类型： 0-金额固定、受益期固定 && 费用子类型 1-房屋租赁类
		if ("1".equals(contractInfo.getCntType()) && "0".equals(contractInfo.getFeeType()) && "1".equals(contractInfo.getFeeSubType())) {
			// 租金信息
			List<Map<String, Object>> tenancyList = contractModifyService.getTcyDzCheckList(distinctDeviceInfoList,contractCheckBean.getCntNum());
			String tenancyStr = "";
			if(null!=tenancyList)
			{
				tenancyStr = JSONArray.fromObject(tenancyList).toString();
			}
			contractInfo.setTenanciesList(tenancyList);
			contractInfo.setTenanciesStr(tenancyStr);
		}
		
		WebUtils.setRequestAttr("cnt", contractInfo);
		WebUtils.setRequestAttr("deviceInfoList", deviceInfoList);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/contract/check/contractList.do?VISIT_FUNC_ID=030202");
		return CONTRACTCHECKPATH + "detail";
	}
	
	/**
	 * 合同复核通过
	 * @param contractCheckBean
	 * @return
	 */
	@RequestMapping("checkPass.do")
	@PreventDuplicateSubmit
	public String checkPass(ContractCheckBean contractCheckBean)
	{
		boolean flag = contractCheckService.checkPass(contractCheckBean);
		return ForwardPageUtils.getReturnUrlString("合同复核",flag,new String[]{"constractList"});
	}
	
	/**
	 * 合同复核退回
	 * @param contractCheckBean
	 * @return
	 */
	@RequestMapping("checkGoBack.do")
	@PreventDuplicateSubmit
	public String checkGoBack(ContractCheckBean contractCheckBean)
	{
		boolean flag = contractCheckService.checkGoBack(contractCheckBean);
		return ForwardPageUtils.getReturnUrlString("合同退回",flag,new String[]{"constractList"});
	}
	
	
}
