package com.forms.prms.web.contract.confirmchg.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.contract.confirmchg.domain.ConfirmChgContract;
import com.forms.prms.web.contract.confirmchg.service.ContractConfirmChgService;
import com.forms.prms.web.contract.modify.domain.ModifyContract;
import com.forms.prms.web.contract.modify.service.ContractModifyService;
import com.forms.prms.web.contract.query.service.ContractQueryService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/contract/confirmchg")
/**
 * Title:ContractConfirmChgController
 * Description:查询合同列表及明细
 * 业务逻辑：合同列表查询（条件：合同号、合同类型（ 0-资产类 1-费用类）、供应商（根据名字可模糊查询）、签订日期区间），
 * 		     	可查询范围为：所属一級行相同 且 合同状态为 变更申请中（data_flag='21'）
 * 		      合同详情：根据合同号查询相关的基础信息、物料信息及费用信息
 *     	      变更合同：将修改内容更新至合同主表（TD_CNT）、电子审批表（TD_CNT_DZSP）、费用表（TD_CNT_FEE）、房屋租赁表（TD_CNT_TENANCY）、 按进度分期付款表（TD_CNT_FKJD）、
 * 				按日期分期付款表（TD_CNT_FKRQ）、按条件分期付款表（TD_CNT_FKTJ）等。合同进入'合同确认'状态（data_flag='20'）
 * Coryright: formssi
 * @author liys
 * @project ERP
 * @date 2015-01-29
 * @version 1.0	
 */
public class ContractConfirmChgController {

	private static final String PREFIX = "contract/confirmchg/";

	@Autowired
	private ContractConfirmChgService service;
	
	@Autowired
	private ContractModifyService modService;
	@Autowired
	private ContractQueryService qService;
	
	@RequestMapping("confirmChgList.do")
	@AddReturnLink(id = "confirmChgList", displayValue = "返回变更列表页面")
	public String confirmChgList(ConfirmChgContract con) {
		WebUtils.setRequestAttr("cntList", service.confirmChgList(con));
		WebUtils.setRequestAttr("con", con);

		return PREFIX + "confirmchglist";
	}
	
	@RequestMapping("org1List.do")
	public String org1List(ConfirmChgContract con) {
		con.setOrgFlag("1");//省行
		return contractQuery(con);
	}
	
	@RequestMapping("org2List.do")
	public String org2List(ConfirmChgContract con) {
		con.setOrgFlag("2");//二级行
		return contractQuery(con);
	}
	
	@RequestMapping("dutyCodeList.do")
	public String dutyCodeList(ConfirmChgContract con) {
		con.setOrgFlag("3");//业务部门
		return contractQuery(con);
	}
	
	@RequestMapping("queryList.do")
	public String contractQuery(ConfirmChgContract con)
	{
		ReturnLinkUtils.addReturnLink("cntList", "返回合同列表");
		WebUtils.setRequestAttr("cntList", service.confirmChgList(con));
		WebUtils.setRequestAttr("con", con);

		return PREFIX + "confirmchglist";
	}
	
	@RequestMapping("confirmChgDtl.do")
	public String confirmChgDtl(ModifyContract con) {

		con = modService.getDetail(con.getCntNum());
		ConfirmChgContract Info = service.getInfo(con.getCntNum());
		WebUtils.setRequestAttr("cnt", con);
		WebUtils.setRequestAttr("Info", Info);
		if(con.getTenancies() != null){
			WebUtils.setRequestAttr("tenancyLength", con.getTenancies().size());
		}else{
			WebUtils.setRequestAttr("tenancyLength", 0);
		}
		if(con.getStageInfos() != null){
			WebUtils.setRequestAttr("stageInfoLength", con.getStageInfos().size());
		}else{
			WebUtils.setRequestAttr("stageInfoLength", 0);
		}
		WebHelp.setLastPageLink("uri", "cntList");
		// 费用承担部门 默认为录入人责任中心，可选范围为所属二级行(若没有为一级行)下面的责任中心
		String rootFeeDept = WebHelp.getLoginUser().getOrg1Code();
//		if (WebHelp.getLoginUser().getOrg2Code() == null || "" == WebHelp.getLoginUser().getOrg2Code()) {
//			rootFeeDept = WebHelp.getLoginUser().getOrg1Code();
//		}
		WebUtils.setRequestAttr("rootFeeDept", rootFeeDept);
		
	/*	List<?> orderSucDev = service2.getOrderSucDevices(con.getCntNum());
		if(orderSucDev.size() > 0 && orderSucDev != null){
			WebUtils.setRequestAttr("hasSucOrderDev",true);
			WebUtils.setRequestAttr("orderSucDev",orderSucDev);
		}*/
		//得到合同下的所有物料
		WebUtils.setRequestAttr("cntDev", qService.getCntProj(con.getCntNum()));
		//得到所有物料费用承担部门去重的集合
		WebUtils.setRequestAttr("feeDeptList", qService.getCntProj1(con.getCntNum()));
		return PREFIX + "confirmchgdtl";
	}

	@RequestMapping("confirmChg.do")
	public String confirmChg(ModifyContract bean) {

		boolean isSuc = service.updCnt(bean);
		ReturnLinkUtils.setShowLink("confirmChgList");
		if (isSuc) {
			WebUtils.getMessageManager().addInfoMessage("确认变更成功！");
			return ForwardPageUtils.getSuccessPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("确认变更失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}
}
