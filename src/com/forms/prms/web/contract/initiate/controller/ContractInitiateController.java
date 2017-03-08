package com.forms.prms.web.contract.initiate.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.platform.web.token.PreventDuplicateSubmit;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.contract.initiate.domain.CntDevice;
import com.forms.prms.web.contract.initiate.domain.ContractBean;
import com.forms.prms.web.contract.initiate.domain.ContractInitate;
import com.forms.prms.web.contract.initiate.service.ContractInitiateService;
import com.forms.prms.web.init.SystemParamManage;
import com.forms.prms.web.monthOver.service.MonthOverService;
import com.forms.prms.web.util.ForwardPageUtils;

/**
 * 
 * @author user
 * 合同新增:新增的合同根据审批类型可分为三类，即签报立项、部内审批和电子审批。
 * 其中电子审批需要添加审批数量对应的电子审批信息列表。
 * 根据合同类型又可以分为两类，即资产类和费用类。
 * 费用类下根据费用类型又分为四类：金额固定、受益期固定，受益期固定、合同金额不固定，受益期不固定，付款时确认费用和宣传费四种。
 * 当费用类型为金额固定、受益期固定时又可根据费用子类型分为两类：普通费用类和房屋租赁类。
 * 房屋租赁类的信息涉及到甲方乙方的的详细信息以及租赁递增条件的列表信息。
 * 租赁递增条件的初始日期要与执行起始日期一致，结束日期与执行结束日期一致。
 * 而递增过程中除第一条外的开始日期要与上一条递增信息的结束日期相衔接，
 * 递增过程中除最后一条外的结束日期要符合以下两个条件中任意一个：结束日期为当月的最后一天（如3月31日或4月30日），
 * 或者结束日期与第一条起始日期之间相差整数月份（如起始日期为3月13日，对应每一条递增信息的结束日期为XX月12日）。
 * 新增合同的合同信息里，付款条件分为四种，其中第四种为分期付款。分期付款的方式又有三种：按进度，按时间和按条件。
 * 按进度与按时间分期付款都可以添加多条分期付款记录。
 * 合同信息里的采购数量是随着采购项目记录条数的变化而变化的，每一条采购信息中的物料类型都需要根据对应的合同类型、
 * 费用类型、费用子类型和是否省行统购作为条件来选择物料类型。
 * 
 */
@Controller
@RequestMapping("/contract/initiate/")
public class ContractInitiateController {
	@Autowired
	private ContractInitiateService service;
	@Autowired
	private MonthOverService monthOverService;
	
	private static final String FUNCTION = "contract/initiate/";

	@RequestMapping("preAdd.do")
	@AddReturnLink(id = "preAdd", displayValue = "继续新增")
	public String preAdd(ContractInitate bean) {
		//初始化新增页面，取得一个新的contractId
		// 一级分行机构号
		bean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		// 责任中心
		bean.setCreateDept(WebHelp.getLoginUser().getDutyCode());
		String newContractId = service.getNewContractId(bean);
		bean.setCntNum(newContractId);
		//设置初始化的新增页面的-审批类别为签报立项（2），合同类型为资产类（0），费用类型为金额固定、受益期固定（0）
		//费用子类型为普通费用类（0）,付款类型为合同签订后一次性付款（0）,付款条件为按进度（0）
		bean.setLxlx("2");
		bean.setCntType("0");
		bean.setFeeType("0");
		bean.setFeeSubType("0");
		bean.setPayTerm("0");
		bean.setStageType("0");
		WebUtils.setRequestAttr("selectInfo", bean);
		
		//费用承担部门 默认为录入人责任中心，可选范围为所属二级行(若没有为一级行)下面的责任中心
		String rootFeeDept = WebHelp.getLoginUser().getOrg1Code();
//		if(WebHelp.getLoginUser().getOrg2Code()==null || ""==WebHelp.getLoginUser().getOrg2Code()){
//			rootFeeDept = WebHelp.getLoginUser().getOrg1Code();
//		}
		//新增合同时参考、专项选择的条件
		String conditionStr=null;
		//如果为总行
		if("A0001".equals(WebHelp.getLoginUser().getOrg1Code())){
			conditionStr="SCOPE!=4";//除了停用的专项、参考
		}
		else{
			conditionStr="SCOPE!=4 AND SCOPE!=1";//除了停用、总行的参考专项
		}
		WebUtils.setRequestAttr("conditionStr", conditionStr);
		WebUtils.setRequestAttr("rootFeeDept", rootFeeDept);
		WebUtils.setRequestAttr("defaultFeeDept", WebHelp.getLoginUser().getDutyCode());
		WebUtils.setRequestAttr("dutyCodeName", WebHelp.getLoginUser().getDutyName()+"("+WebHelp.getLoginUser().getDutyCode()+")");
		return FUNCTION + "addContract";
	}
	
	/**
	 * 合同新增
	 * @param bean
	 * @return
	 */
	@RequestMapping("add.do")
	@PreventDuplicateSubmit
	public String add(ContractInitate bean){
		boolean isSuc = service.add(bean);
		ReturnLinkUtils.setShowLink("preAdd");
		if(isSuc){
			WebUtils.getMessageManager().addInfoMessage("新增合同成功！");
			return ForwardPageUtils.getSuccessPage();
		}else{
			WebUtils.getMessageManager().addInfoMessage("新增合同失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}
	/**
	 * 合同新增保存
	 * @param bean
	 * @return
	 */
	@RequestMapping("save.do")
	@PreventDuplicateSubmit
	public String save(ContractInitate bean){
		boolean isSuc = service.save(bean);
		ReturnLinkUtils.setShowLink("preAdd");
		if(isSuc){
			WebUtils.getMessageManager().addInfoMessage("保存合同成功！");
			return ForwardPageUtils.getSuccessPage();
		}else{
			WebUtils.getMessageManager().addInfoMessage("保存合同失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("feeTypePage.do")
	public String feeTypePage(ContractInitate bean) {
		
		return FUNCTION + "feeTypePage";
	}
	
	/**
	 * 校验采购项目金额是否超出项目预算
	 * @param cntNum
	 * @return
	 */
	@RequestMapping("checkProjAmt.do")
	@ResponseBody
	public String checkProjAmt(String projId, BigDecimal execAmt){
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		String projName = service.checkProjAmt(projId, execAmt);
		if(projName != null && projName.equals("")){
			jsonObject.put("projName", projName);
		}
		return jsonObject.writeValueAsString();
	}
	
	/**
	 * @methodName hasScaned
	 * desc 查看合同是否检查过  
	 * 
	 * @param cntNum
	 * @return
	 */
	@RequestMapping("hasScaned.do")
	@ResponseBody
	public String hasScaned(String cntNum){
		AbstractJsonObject succJsonObject = new SuccessJsonObject();
		boolean hasScaned = service.hasSacned(cntNum);
		succJsonObject.put("hasScaned", hasScaned);
		return succJsonObject.writeValueAsString();
	}
	
	/**
	 * @methodName getMonthOverFlag
	 * desc  获取月结状态
	 * 
	 * @return
	 */
	@RequestMapping("getMonthOverFlag.do")
	@ResponseBody
	public String getMonthOverFlag(){
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		boolean monthOverFlag = "0".equals(monthOverService.getMaxDataFlag());
		jsonObject.put("monthOverFlag", monthOverFlag);
		return jsonObject.writeValueAsString();
	}
	
	/**
	 * @methodName relatedCntPage
	 * desc  关联合同号弹出页  
	 * 
	 * @return
	 */
	@RequestMapping("relatedCntPage.do")
	public String relatedCntPage(ContractBean cnt){
		cnt.setCreateDept(WebHelp.getLoginUser().getDutyCode());
		List<ContractBean> list = service.relatedCntPage(cnt);
		WebUtils.setRequestAttr("cntList", list);
		WebUtils.setRequestAttr("selectInfo", cnt);
		return FUNCTION + "relatedCntPage";
	}
	/**
	 * 税码弹出框
	 * @param cnt
	 * @return
	 */
	@RequestMapping("taxCodeList.do")
	public String taxCodeList(CntDevice cnt){
		String taxCode = cnt.getTaxCode();
		if (!Tool.CHECK.isEmpty(taxCode)) {
			try {
				taxCode = new String(taxCode.getBytes("ISO-8859-1"), "UTF-8"); 
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		WebUtils.setRequestAttr("taxCode", taxCode);
		List<CntDevice> list = service.taxCodeList(cnt);
		WebUtils.setRequestAttr("taxCodeList", list);
		
		return FUNCTION + "taxCode";
	}
}
