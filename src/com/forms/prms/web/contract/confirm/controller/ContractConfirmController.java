package com.forms.prms.web.contract.confirm.controller;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.taglibs.standard.lang.jstl.NullLiteral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.platform.web.token.PreventDuplicateSubmit;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.contract.confirm.domain.BudgetBean;
import com.forms.prms.web.contract.confirm.domain.ConfirmContract;
import com.forms.prms.web.contract.confirm.service.ContractConfirmService;
import com.forms.prms.web.contract.contractcommon.service.ContractCommonService;
import com.forms.prms.web.contract.query.domain.QueryContract;
import com.forms.prms.web.sysmanagement.concurrent.domain.ConcurrentType;
import com.forms.prms.web.sysmanagement.concurrent.service.ConcurrentService;
import com.forms.prms.web.sysmanagement.homepage.service.SysWarnCountService;
import com.forms.prms.web.sysmanagement.provider.domain.ProviderBean;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/contract/confirm")
/**
 * Title:ContractConfirmController
 * Description:查询待确认合同列表及明细
 * 业务逻辑：待确认合同列表查询（条件：合同号、合同类型（ 0-资产类 1-费用类）、供应商（根据名字可模糊查询）、签订日期区间、创建机构），
 * 		     	可查询范围为：创建责任中心=责任中心 且 合同状态=合同待确认（data_flag='12'）
 * 		      合同详情：根据合同号查询相关的基础信息、物料信息及费用信息
 * 通过待确认合同
 * 业务逻辑：合同状态变为合同确认完成（data_flag='20'），根据选择是否生成订单修改是否生成订单为'0'或'1'（0-是 1-否）
 * 		     插入流水信息至流水表TL_WATER_BOOK
 * 退回待确认合同
 * 业务逻辑：合同状态变为合同退回（data_flag='11'），
 * 		     插入流水信息至流水表TL_WATER_BOOK
 * Coryright: formssi
 * @author liys
 * @project ERP
 * @date 2015-01-28
 * @version 1.0	
 */
public class ContractConfirmController {

	private static final String PREFIX = "contract/confirm/";

	@Autowired
	private ContractConfirmService service;
	@Autowired ContractCommonService ccService;
	@Autowired
	private SysWarnCountService  sysWarnCountService;
	@Autowired
	private ConcurrentService concurrentService;
	

	@RequestMapping("confirmList.do")
	public String ContractConfirm(ConfirmContract con) {
		ReturnLinkUtils.addReturnLink("confirmList", "返回待确认列表");
		WebUtils.setRequestAttr("cntList", service.confirmList(con));
		WebUtils.setRequestAttr("con", con);

		return PREFIX + "confirmlist";
	}

	@RequestMapping("cntDtl.do")
	public String ContractDetail(ConfirmContract con) {
		String cntNum = con.getCntNum();
		QueryContract cnt = service.getDetail(cntNum);
		WebUtils.setRequestAttr("cnt", cnt);
		String sysdate = Tool.DATE.getDate(); 
		String feeEndDate = cnt.getFeeEndDate();
		if(feeEndDate != null && (sysdate.compareTo(feeEndDate)>0)){
			//当前系统时间大于合同受益结束时间，则不需要录入受益金额
			WebUtils.setRequestAttr("FFFlag", "no");
		}else{
			WebUtils.setRequestAttr("FFFlag", "yes");
		}
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/contract/confirm/confirmList.do?VISIT_FUNC_ID=03020103");

		return PREFIX + "confirm";
	}
	
	/**
	 * 合同确认前录入受益金额(ajax)
	 * @param cnt
	 * @return
	 */
	@RequestMapping("confirmFeeAmt.do")
	@ResponseBody
	public String confirmFeeAmt(ConfirmContract cnt){
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		boolean flag =service.confirmFeeAmt(cnt);
		jsonObject.put("flag", flag);
		return jsonObject.writeValueAsString();
	}
	
	@PreventDuplicateSubmit
	@RequestMapping("confirmPass.do")
	public String confirmPass(ConfirmContract cnt) throws UnknownHostException {
		ReturnLinkUtils.setShowLink("confirmList");
		int returnFlag = 0;
		try {
			if ("0".equals(cnt.getFeeType()) || "1".equals(cnt.getFeeType())) {
				String memo = "合同号为"+cnt.getCntNum()+"的合同确立时增加锁";
				 returnFlag = concurrentService.checkAndAddLock(ConcurrentType.Concurrent_A,
						ConcurrentType.A1,WebHelp.getLoginUser().getOrg1Code(),WebHelp.getLoginUser().getUserId(),memo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebUtils.getMessageManager().addInfoMessage(e.getCause().getMessage());
			return ForwardPageUtils.getErrorPage();
		}finally{
			// 删除锁
			if (returnFlag == 2) {
				concurrentService.delConcurrentLock(ConcurrentType.Concurrent_A, ConcurrentType.A1,"");
			}
		}
		try {
			Map<String, String> param = service.confirmPass(cnt);//确认
			if ("1".equals(param.get("flag"))) {
				WebUtils.getMessageManager().addInfoMessage(param.get("msgInfo"));
				return ForwardPageUtils.getSuccessPage();
			} else {
				WebUtils.getMessageManager().addInfoMessage(param.get("msgInfo"));
				return ForwardPageUtils.getErrorPage();
			}
		} catch (Exception e) {
			WebUtils.getMessageManager().addInfoMessage((null == e.getCause())||Tool.CHECK.isEmpty(e.getCause())?e.getMessage():e.getCause().getMessage());
			return ForwardPageUtils.getErrorPage();
		}
		
	}
	@PreventDuplicateSubmit
	@RequestMapping("confirmReturn.do")
	public String confirmReturn(ConfirmContract cnt) {
		ReturnLinkUtils.setShowLink("confirmList");
		if (service.confirmReturn(cnt) > 0) {
			ccService.delWarnCntConfirm(cnt.getCntNum());//删除统计表中待确认的一条合同数据
			//重新统计确认机构的合同数据
			String dutyCode = WebHelp.getLoginUser().getDutyCode();
			sysWarnCountService.dealSysWarn(dutyCode, "C");
			WebUtils.getMessageManager().addInfoMessage("退回成功！");
			return ForwardPageUtils.getSuccessPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("退回失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}

	/**
	 * 校验采购项目金额是否超出项目预算
	 * 
	 * @param cntNum
	 * @return
	 */
	@RequestMapping("checkProjAmt.do")
	@ResponseBody
	public String checkProjAmt(String cntNum) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		String projName = service.checkProjAmt(cntNum);
		if (projName != null && "" != projName) {
			jsonObject.put("projName", projName);
		}
		return jsonObject.writeValueAsString();
	}
	
	@RequestMapping("feeTypePage.do")
	public String feeTypePage(String cntNum){
		String sysDate = Tool.DATE.getDate();
		WebUtils.setRequestAttr("sysDate", sysDate);
		return PREFIX + "feeTypePage";
	}
	
	/**
	 * 预提待摊类合同在合同确立时冻结预算
	 * @param cntNum
	 * @return
	 */
	@RequestMapping("bgtFrozen.do")
	@ResponseBody
	public String bgtFrozen(String cntNum){
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		Map<String, String> param = new HashMap<String, String>();
		param.put("syyyymm", "");//开始日期
		param.put("eyyyymm", "");//结束日期
		param.put("cntNum", cntNum);
		service.bgtFrozen(param);
		String flag = param.get("flag");
		if("0".equals(flag)){//0表示预算冻结失败,查询付款冻结失败的信息
			jsonObject.put("bgtMsg", param.get("msgInfo"));
		}else{//1表示预算冻结成功,查询付款冻结透支的信息
			List<BudgetBean> lists = service.queryBgtFrozenDetail(cntNum);
			if(!Tool.CHECK.isEmpty(lists)){
				jsonObject.put("bgtMsg", "预提待摊类合同存在预算透支，将不能做付款！");
				jsonObject.put("lists", lists);
			}
		}
		jsonObject.put("flag", flag);
		return jsonObject.writeValueAsString();
	}
	
	/**
	 * 预提待摊类合同冻结预算查询明细
	 * @param cntNum
	 * @return
	 */
	@RequestMapping("queryBgtFrozenDetail.do")
	public String queryBgtFrozenDetail(String cntNum){
		List<BudgetBean> lists = service.queryBgtFrozenDetail(cntNum);
		WebUtils.setRequestAttr("lists", lists);
		return PREFIX+"bgtFrozenDetail";
	}
	
	/**
	 * 删除预算临时数据
	 * @param cntNum
	 * @return
	 */
	@RequestMapping("deleteBgtFrozenTemp.do")
	@ResponseBody
	public String deleteBgtFrozenTemp(String cntNum){
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		boolean flag = service.deleteBgtFrozenTemp(cntNum);
		jsonObject.put("flag", flag);
		return jsonObject.writeValueAsString();
	}
	/**
	 * 检查是否有上送FMS的订单
	 * 
	 * @param cntNum
	 * @return
	 */
	@RequestMapping("checkSendOrder.do")
	@ResponseBody
	public String checkSendOrder(String cntNum) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		List<String> orderList = service.orderList(cntNum);
		if (orderList.size() == 0) {
			jsonObject.put("isExist", false);
		} else {
			jsonObject.put("isExist", true);
		}

		return jsonObject.writeValueAsString();
	}
	/**
	 * 检查合同下的物料是否都是订单类物料
	 * 
	 * @param cntNum
	 * @return
	 */
	@RequestMapping("checkNotOrderMatr.do")
	@ResponseBody
	public String checkNotOrderMatr(String cntNum) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		List<String> notOrderMatrList = service.notOrderMatrList(cntNum);
		if (notOrderMatrList.size() == 0) {
			jsonObject.put("isExist", false);
		} else {
			jsonObject.put("isExist", true);
		}

		return jsonObject.writeValueAsString();
	}
}
