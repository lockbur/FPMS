package com.forms.prms.web.contract.modify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.contract.confirm.service.ContractConfirmService;
import com.forms.prms.web.contract.initiate.domain.CntDevice;
import com.forms.prms.web.contract.initiate.domain.TenancyDz;
import com.forms.prms.web.contract.modify.domain.ModifyContract;
import com.forms.prms.web.contract.modify.service.ContractModifyService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/contract/modify")
/**
 * Title:ContractModifyController
 * Description:查询可修改合同列表及明细
 * 业务逻辑：合同列表查询（条件：合同号、合同类型（ 0-资产类 1-费用类）、供应商（根据名字可模糊查询）、签订日期区间），
 * 		     	可查询范围为：创建责任中心=责任中心 的合同 且 合同状态为 '合同录入'（data_flag='10'）或'合同退回'（data_flag='11'）
 * 		      合同详情：根据合同号查询相关的基础信息、物料信息及费用信息
 * 		      修改合同：将修改内容更新至合同主表（TD_CNT）、电子审批表（TD_CNT_DZSP）、费用表（TD_CNT_FEE）、房屋租赁表（TD_CNT_TENANCY）、 按进度分期付款表（TD_CNT_FKJD）、
 * 				按日期分期付款表（TD_CNT_FKRQ）、按条件分期付款表（TD_CNT_FKTJ）、物料表（TD_CNT_DEVICE）等。其中当物料表有物料未审核时（data_flag!='99'），需重新进入物料复核（data_flag='10'）；否则合同进入'合同待确认'状态（data_flag='12'）
 *
 * Coryright: formssi
 * @author liys
 * @project ERP
 * @date 2015-01-29
 * @version 1.0	
 */
public class ContractModifyController {

	private static final String PREFIX = "contract/modify/";

	@Autowired
	private ContractModifyService service;
	@Autowired
	private ContractConfirmService confirmService;

	@RequestMapping("modifyList.do")
	@AddReturnLink(id = "modifyList", displayValue = "返回修改列表页面")
	public String ModifyList(ModifyContract con) {
		WebUtils.setRequestAttr("cntList", service.modifyList(con));
		WebUtils.setRequestAttr("con", con);

		return PREFIX + "modifylist";
	}

	@RequestMapping("modifyDtl.do")
	public String ModifyDetail(ModifyContract con) {

		con = service.getDetail(con.getCntNum());
		WebUtils.setRequestAttr("cnt", con);
		if (null != con.getTenancies()) {
			WebUtils.setRequestAttr("tenancyLength", con.getTenancies().size());
		} else {
			WebUtils.setRequestAttr("tenancyLength", 0);
		}
		if (null != con.getStageInfos()) {
			WebUtils.setRequestAttr("stageInfoLength", con.getStageInfos().size());
		} else {
			WebUtils.setRequestAttr("stageInfoLength", 0);
		}
		/*
		 * if(con.getIsPrepaidProvision().equals("0")){
		 * //预提待摊类起始日期与当前月的比较，开始为true，未开始为false
		 * WebUtils.setRequestAttr("feeStartFlag",
		 * confirmService.getFeeStartFlag(con.getCntNum())); }
		 */
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
				+ "/contract/modify/modifyList.do?VISIT_FUNC_ID=03020102");

		// 费用承担部门 默认为录入人责任中心，可选范围为所属二级行(若没有为一级行)下面的责任中心
		String rootFeeDept = WebHelp.getLoginUser().getOrg1Code();
		// if (WebHelp.getLoginUser().getOrg2Code() == null || "" ==
		// WebHelp.getLoginUser().getOrg2Code()) {
		// rootFeeDept = WebHelp.getLoginUser().getOrg1Code();
		// }
		WebUtils.setRequestAttr("rootFeeDept", rootFeeDept);
		WebUtils.setRequestAttr("defaultFeeDept", WebHelp.getLoginUser().getDutyCode());
		// 查询出可以修改和不可以修改的物料
		// 包括订单成功的物料以及复核通过的物料
		List<CntDevice> checkPassDev = service.checkPassDev(con.getCntNum());
		// 查询可以修改的物料(如果合同为确认退回则复核通过的物料也可以改)
		List<CntDevice> orderSucDev = service.getOrderSucDevices(con.getCntNum());
		if (orderSucDev.size() > 0 && orderSucDev != null) {
			WebUtils.setRequestAttr("hasSucOrderDev", true);

		}
		if ("10".equals(con.getDataFlag())) {
			orderSucDev.addAll(checkPassDev);
		}
		WebUtils.setRequestAttr("orderSucDev", orderSucDev);
		if ("10".equals(con.getDataFlag())) {
			// 只能修改不是90或者99而且订单号为空的物料
			List<CntDevice> canMotidyDev = service.canMotidyDev(con.getCntNum());
			WebUtils.setRequestAttr("orderBackNewDev", canMotidyDev);
		} else {
			WebUtils.setRequestAttr("orderBackNewDev", service.orderBackNewDevices(con.getCntNum()));
		}
		// 新增合同时参考、专项选择的条件
		String conditionStr = null;
		// 如果为总行
		if ("A0001".equals(WebHelp.getLoginUser().getOrg1Code())) {
			conditionStr = "SCOPE!=4";// 除了停用的专项、参考
		} else {
			conditionStr = "SCOPE!=4 AND SCOPE!=1";// 除了停用、总行的参考专项
		}
		WebUtils.setRequestAttr("conditionStr", conditionStr);
		WebUtils.setRequestAttr("dutyCodeName", WebHelp.getLoginUser().getDutyName()+"("+WebHelp.getLoginUser().getDutyCode()+")");
		return PREFIX + "modifyDtl";
	}

	@RequestMapping("modify.do")
	public String Modify(ModifyContract bean) throws Exception {

		boolean isSuc = service.updCnt(bean);
		ReturnLinkUtils.setShowLink("modifyList");
		if (isSuc) {
			WebUtils.getMessageManager().addInfoMessage("修改合同成功！");
			return ForwardPageUtils.getSuccessPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("修改合同失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}

	@RequestMapping("delete.do")
	public String Modify(String cntNum) {

		int rs = service.delCnt(cntNum);
		ReturnLinkUtils.setShowLink("modifyList");
		if (rs == 1) {
			WebUtils.getMessageManager().addInfoMessage("删除合同成功！");
			return ForwardPageUtils.getSuccessPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("删除合同失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}

	@RequestMapping("checkProAmt.do")
	@ResponseBody
	public String checkProAmt(ModifyContract con) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		String projName = service.checkFreezeAmt(con);
		if (null != projName && !"".equals(projName)) {
			jsonObject.put("projName", projName);
		}
		return jsonObject.writeValueAsString();
	}
	
	
	@RequestMapping("getMatrDzList.do")
	@ResponseBody
	public String getMatrDzList(String matrCode,String cntNum) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		List<TenancyDz> tenancies = service.getTcyDzByMatrCode(matrCode, cntNum);
		jsonObject.put("matrDzList", tenancies);
		return jsonObject.writeValueAsString();

	}
}
