package com.forms.prms.web.sysmanagement.approvechain.approveChainMgr.controller;

import java.util.List;
import java.util.Map;

import oracle.net.aso.b;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.consts.WebConsts;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.fms.montAprv.service.MontAprvService;
import com.forms.prms.web.sysmanagement.approvechain.approveChainMgr.domain.ApproveChainMgrBean;
import com.forms.prms.web.sysmanagement.approvechain.approveChainMgr.service.ApproveChainMgrService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/sysmanagement/approveChainMgr")
public class ApproveChainMgrController {
	private static final String PREFIX = "sysmanagement/approveChainMgr/";
	@Autowired
	private ApproveChainMgrService service;
	@Autowired
	private MontAprvService mAservice;
	/**
	 * 省行维护审批链
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/specList.do")
	public String specList(ApproveChainMgrBean bean) throws Exception {
		String year = Tool.DATE.getDate().substring(0,4);
		if (Tool.CHECK.isEmpty(bean.getDataYear())) {
			bean.setDataYear(year);
		}
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		String org1Name = WebHelp.getLoginUser().getOrg1Name();
		bean.setOrg1Code(org1Code);
		bean.setOrg1Name(org1Name);
		bean.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
		bean.setOrgType("01");
		if (bean.getDataYear().equals(year)) {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
		}else {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC_FUT");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
		}
		if (Tool.CHECK.isEmpty(bean.getTabsIndex())|| "0".equals(bean.getTabsIndex())) {
			if (Tool.CHECK.isEmpty(bean.getAprvType())) {
				bean.setAprvType("11");
			}
			ReturnLinkUtils.addReturnLink("specApproveChainList", "返回审批链列表页面");
			//查询已维护的
			List<ApproveChainMgrBean> specHaveWhList = service.specHaveWhList(bean,"specHaveWh");
			WebUtils.setRequestAttr("specHaveWhList", specHaveWhList);
			bean.setTabsIndex("0");
		}else if("1".equals(bean.getTabsIndex())) {
			ReturnLinkUtils.addReturnLink("specApproveChainList1", "返回审批链维护页面");
			bean.setTabsIndex("1");
			//01是初始选择页面,02是专项包，03是是否省行统购未维护页面
			WebUtils.setRequestAttr("noWhTag", "01");
		}else{
			ReturnLinkUtils.addReturnLink("specApproveChainList2", "返回审批链待修改页面");
			//待修改的
			List<ApproveChainMgrBean> changeList = service.getAprvChange(bean,"specChange");
			WebUtils.setRequestAttr("changeList", changeList);
			bean.setTabsIndex("2");
		}
		
		
		WebUtils.setRequestAttr("thisYear",year);
		WebUtils.setRequestAttr("selectInfo", bean);
		WebUtils.setRequestAttr("funcId", WebUtils.getParameter(WebConsts.FUNC_ID_KEY));
		return PREFIX + "specList";

	}
	/**
	 * 省行未维护跳转页面
	 * @param bean
	 * @return
	 */
	@RequestMapping("/specNoWhSkip.do")
	public String specNoWh(ApproveChainMgrBean  bean){
		String path = "";
		bean.setTabsIndex("1");
		bean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		bean.setOrg1Name(WebHelp.getLoginUser().getOrg1Name());
		if (Tool.DATE.getDate().substring(0,4).equals(bean.getDataYear())) {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
		}else {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC_FUT");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
		}
		if ("11".equals(bean.getAprvType())) {
			//专项包
			//查询未维护的
			List<Map<String, Object>>specNoWhList =service.specNoWhList(bean,"noUp");
			WebUtils.setRequestAttr("noUpdateList", specNoWhList);
			WebUtils.setRequestAttr("noWhTag", "02");
			
			path= PREFIX+"specZxNoWhDetail";
		}else if("12".equals(bean.getAprvType())) {
			//是否省行统购
			bean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
			List<ApproveChainMgrBean>specTgDutyList =service.specNoWhListTg(bean,"specNoWh");
			WebUtils.setRequestAttr("specTgDutyList", specTgDutyList);
			WebUtils.setRequestAttr("noWhTag", "03");
			
			path= PREFIX+"specList";
		}
		WebUtils.setRequestAttr("selectInfo", bean);
		return path;
		
	}
	/**
	 * 进入未维护 物料页面 省行统购
	 * @param bean
	 * @return
	 */
	@RequestMapping("/specNoWhDetail")
	public String specNoWhMatrs(ApproveChainMgrBean bean){
		if (Tool.DATE.getDate().substring(0,4).equals(bean.getDataYear())) {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
		}else {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC_FUT");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
		}
		bean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		List<Map<String, Object>>specNoWhMatrs =service.specNoWhMatrs(bean);
		WebUtils.setRequestAttr("specNoWhMatrs", specNoWhMatrs);
		WebUtils.setRequestAttr("bean", bean);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/approveChainMgr/specNoWhSkip.do?VISIT_FUNC_ID=0812010205&tabsIndex=1&aprvType=12&dataYear="+bean.getDataYear());
		return PREFIX + "specNoWhDetail";
	}
	/**
	 * 省行维护审批链新增 统购
	 * @param bean
	 * @return
	 */
	@RequestMapping("/specTgAdd")
	public String specTgAdd(ApproveChainMgrBean bean){
		if (Tool.DATE.getDate().substring(0,4).equals(bean.getDataYear())) {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
		}else {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC_FUT");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
		}
		String org1Code=WebHelp.getLoginUser().getOrg1Code();
		bean.setOrg21Code(org1Code);
		try {
			service.noSpecAdd(bean);
			ReturnLinkUtils.setShowLink(new String[]{"specApproveChainList" , "specApproveChainList1"});
			WebUtils.getMessageManager().addInfoMessage("审批链添加成功！");
			return ForwardPageUtils.getSuccessPage();
		} catch (Exception e) {
			e.printStackTrace();
			ReturnLinkUtils.setShowLink(new String[]{"specApproveChainList" , "specApproveChainList1"});
			WebUtils.getMessageManager().addInfoMessage("审批链添加失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}
	
	/**
	 * 省行维护审批链新增
	 * @param bean
	 * @return
	 */
	@RequestMapping("/specAdd")
	public String specAdd(ApproveChainMgrBean bean){
		if (Tool.DATE.getDate().substring(0,4).equals(bean.getDataYear())) {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
		}else {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC_FUT");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
		}
		try {
			service.specAdd(bean);
			ReturnLinkUtils.setShowLink(new String[]{"specApproveChainList" , "specApproveChainList1"});
			WebUtils.getMessageManager().addInfoMessage("审批链添加成功！");
			return ForwardPageUtils.getSuccessPage();
		} catch (Exception e) {
			e.printStackTrace();
			ReturnLinkUtils.setShowLink(new String[]{"specApproveChainList" , "specApproveChainList1"});
			WebUtils.getMessageManager().addInfoMessage("审批链添加失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}
	/**
	 * 省行维护审批链编辑页
	 * @param bean
	 * @return
	 */
	@RequestMapping("/specPreEdit")
	public String specPreEdit(ApproveChainMgrBean bean){
	if (Tool.DATE.getDate().substring(0,4).equals(bean.getDataYear())) {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
		}else {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC_FUT");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
		}
		ApproveChainMgrBean bean2 = service.specPreEdit(bean);
		bean2.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		WebUtils.setRequestAttr("bean", bean2);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/approveChainMgr/specList.do?VISIT_FUNC_ID=08120102&tabsIndex=0");
		return PREFIX+"specEdit";
	}
	/**
	 * 省行维护审批链 编辑
	 * @param bean
	 * @return
	 */
	@RequestMapping("/specEdit")
	public String specEdit(ApproveChainMgrBean bean){
		if (Tool.DATE.getDate().substring(0,4).equals(bean.getDataYear())) {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
		}else {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC_FUT");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
		}
		try {
			service.specEdit(bean);
			ReturnLinkUtils.setShowLink("specApproveChainList");
			WebUtils.getMessageManager().addInfoMessage("审批链编辑成功！");
			return ForwardPageUtils.getSuccessPage();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ReturnLinkUtils.setShowLink("specApproveChainList");
			WebUtils.getMessageManager().addInfoMessage("审批链编辑失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}
	/**
	 * 省行解除审批链
	 * @param bean
	 * @return
	 */
	@RequestMapping("/specDel")
	public String specDel(ApproveChainMgrBean bean){
		if (Tool.DATE.getDate().substring(0,4).equals(bean.getDataYear())) {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
		}else {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC_FUT");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
		}
		if(service.specDel(bean)){
			ReturnLinkUtils.setShowLink("specApproveChainList");
			WebUtils.getMessageManager().addInfoMessage("审批链删除成功！");
			return ForwardPageUtils.getSuccessPage();
		}else {
			ReturnLinkUtils.setShowLink("specApproveChainList");
			WebUtils.getMessageManager().addInfoMessage("审批链删除失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}
	/**
	 * 省行批量审批链
	 * @param bean
	 * @return
	 */
	@RequestMapping("/specBatchEdit.do")
	public String specBatchEdit(ApproveChainMgrBean bean){
		if (Tool.DATE.getDate().substring(0,4).equals(bean.getDataYear())) {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
		}else {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC_FUT");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
		}
		bean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		List<Map<String, Object>> list = service.getDutyByOrg1ForSpec(bean);
		WebUtils.setRequestAttr("list", list);
		WebUtils.setRequestAttr("userOrg1Code", WebHelp.getLoginUser().getOrg1Code());
		WebUtils.setRequestAttr("bean", bean);
//		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/approveChainMgr/specList.do?VISIT_FUNC_ID=08120102&tabsIndex=1");
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/approveChainMgr/specNoWhSkip.do?VISIT_FUNC_ID=0812010205&tabsIndex=1&aprvType=12&dataYear="+bean.getDataYear());
		return PREFIX+"specBatchEdit";
	}
	/**
	 * 省行批量维护
	 * @param bean
	 * @return
	 */
	@RequestMapping("/specBatchEditExecute.do")
	public String specBatchEditExecute(ApproveChainMgrBean bean){
		if (Tool.DATE.getDate().substring(0,4).equals(bean.getDataYear())) {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
		}else {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC_FUT");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
		}
		String org1Code=WebHelp.getLoginUser().getOrg1Code();
		bean.setOrg21Code(org1Code);
		if(service.noSpecBatchEditExecute(bean)){
			ReturnLinkUtils.setShowLink(new String[]{"specApproveChainList" , "specApproveChainList1"});
			WebUtils.getMessageManager().addInfoMessage("批量维护审批链成功！");
			return ForwardPageUtils.getSuccessPage();
		}else {
			ReturnLinkUtils.setShowLink(new String[]{"specApproveChainList" , "specApproveChainList1"});
			WebUtils.getMessageManager().addInfoMessage("批量维护审批链失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}
	/**
	 * 省行得到公共的物料
	 * @param bean
	 * @return
	 */
	@RequestMapping("/specSelecstPublicMatrs.do")
	@ResponseBody
	public String specSelecstPublicMatrs(ApproveChainMgrBean bean){
		
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		bean.setOrg2Code(WebHelp.getLoginUser().getOrg2Code());
		bean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		bean.setOrg2CodeOri(WebHelp.getLoginUser().getOrg2CodeOri());
		if (Tool.CHECK.isBlank(bean.getDutyArray())) {
			//如果责任中心是空的
			jsonObject.put("data", null);
		}else {
			Map map = service.selecstPublicMatrs(bean);
			jsonObject.put("data", map);
		}
		return jsonObject.writeValueAsString();
	}
	//=================================================分行============================================
	
	/*8
	 * 分行维护审批链
	 * 分行未维护的 就查询 在Nospec表中未维护 且在spec表中也未维护的
	 */
	@RequestMapping("/noSpecList.do")
	public String noSpecList(ApproveChainMgrBean bean) throws Exception {
	
		
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		String org1Name = WebHelp.getLoginUser().getOrg1Name();
		bean.setOrg1Code(org1Code);
		bean.setOrg1Name(org1Name);
		bean.setOrg2Code(WebHelp.getLoginUser().getOrg2Code());
		bean.setOrg2CodeOri(WebHelp.getLoginUser().getOrg2CodeOri());
		bean.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		String year = Tool.DATE.getDate().substring(0,4);
		bean.setOrgType("02");
		if (Tool.CHECK.isEmpty(bean.getDataYear())) {
			bean.setDataYear(year);
		}
		if (bean.getDataYear().equals(year)) {
			//同年
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
		}else {
			//同年
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
		}
		if (Tool.CHECK.isEmpty(bean.getTabsIndex())|| "0".equals(bean.getTabsIndex())) {
			if (Tool.CHECK.isEmpty(bean.getAprvType())) {
				bean.setAprvType("21");
			}
			ReturnLinkUtils.addReturnLink("noSpecApproveChainList", "返回审批链列表页面");
			//查询已维护的
			List<ApproveChainMgrBean> noSpecList = service.noSpecHaveWhList(bean,"noSpecHaveWh");
			WebUtils.setRequestAttr("noSpecList", noSpecList);
			bean.setTabsIndex("0");
		}else if ("1".equals(bean.getTabsIndex())){
			ReturnLinkUtils.addReturnLink("noSpecApproveChainList1", "返回审批链维护页面");
			//查询未维护的
			if (Tool.CHECK.isEmpty(bean.getAprvType())) {
				bean.setAprvType("21");
			}
			List<ApproveChainMgrBean>noSpecNoWhList =service.noSpecNoWhList(bean,"noSpecNoWh");
			WebUtils.setRequestAttr("noSpecNoWhList", noSpecNoWhList);
			bean.setTabsIndex("1");
		}else {
			ReturnLinkUtils.addReturnLink("noSpecApproveChainList2", "返回审批链待修改页面");
			List<ApproveChainMgrBean> changeList = service.getAprvChange(bean,"noSpecChange");
			WebUtils.setRequestAttr("changeList", changeList);
			bean.setTabsIndex("2");
		}
		WebUtils.setRequestAttr("selectInfo", bean);
		WebUtils.setRequestAttr("thisYear", year);
		WebUtils.setRequestAttr("funcId", WebUtils.getParameter(WebConsts.FUNC_ID_KEY));
		return PREFIX + "noSpecList";

	}
	/**
	 * 进入未维护 物料页面
	 * @param bean
	 * @return
	 */
	@RequestMapping("/noSpecNoWhDetail")
	public String noSpecNoWhMatrs(ApproveChainMgrBean bean){
		if (Tool.DATE.getDate().substring(0,4).equals(bean.getDataYear())) {
			//同年
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
		}else {
			//同年
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
		}
		bean.setOrg2Code(WebHelp.getLoginUser().getOrg2Code());
		List<Map<String, Object>>noSpecNoWhMatrs =service.noSpecNoWhMatrs(bean);
		bean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		WebUtils.setRequestAttr("noSpecNoWhMatrs", noSpecNoWhMatrs);
		WebUtils.setRequestAttr("bean", bean);
		WebUtils.setRequestAttr("uri",WebUtils.getRequest().getContextPath()+"/sysmanagement/approveChainMgr/noSpecList.do?VISIT_FUNC_ID=08120103&tabsIndex=1&dataYear="+bean.getDataYear());
		return PREFIX + "noSpecNoWhDetail";
	}
	/**
	 * 分行维护审批链新增
	 * @param bean
	 * @return
	 */
	@RequestMapping("/noSpecAdd")
	public String noSpecAdd(ApproveChainMgrBean bean){
		String org2Code=WebHelp.getLoginUser().getOrg2Code();
		bean.setOrg21Code(org2Code);
		if (Tool.DATE.getDate().substring(0,4).equals(bean.getDataYear())) {
			//同年
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
		}else {
			//同年
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
		}
		try {
			service.noSpecAdd(bean);
			ReturnLinkUtils.setShowLink(new String[]{"noSpecApproveChainList" , "noSpecApproveChainList1"});
			WebUtils.getMessageManager().addInfoMessage("审批链添加成功！");
			return ForwardPageUtils.getSuccessPage();
		} catch (Exception e) {
			e.printStackTrace();
			ReturnLinkUtils.setShowLink("noSpecApproveChainList");
			WebUtils.getMessageManager().addInfoMessage("审批链添加失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}
	/**
	 * 分行维护审批链编辑页
	 * @param bean
	 * @return
	 */
	@RequestMapping("/noSpecPreEdit")
	public String noSpecPreEdit(ApproveChainMgrBean bean){
		if (Tool.DATE.getDate().substring(0,4).equals(bean.getDataYear())) {
			//同年
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
		}else {
			//同年
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
		}
		ApproveChainMgrBean bean2 = service.noSpecPreEdit(bean);
		bean2.setOrg2Code(WebHelp.getLoginUser().getOrg2Code());
		bean2.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		bean2.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		WebUtils.setRequestAttr("bean", bean2);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/approveChainMgr/noSpecList.do?VISIT_FUNC_ID=08120103&tabsIndex=0");
		return PREFIX+"noSpecEdit";
	}
	/**
	 * 分省行维护审批链 编辑
	 * @param bean
	 * @return
	 */
	@RequestMapping("/noSpecEdit")
	public String noSpecEdit(ApproveChainMgrBean bean){
		if (Tool.DATE.getDate().substring(0,4).equals(bean.getDataYear())) {
			//同年
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
		}else {
			//同年
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
		}
		try {
			service.noSpecEdit(bean);
			ReturnLinkUtils.setShowLink("noSpecApproveChainList");
			WebUtils.getMessageManager().addInfoMessage("审批链编辑成功！");
			return ForwardPageUtils.getSuccessPage();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ReturnLinkUtils.setShowLink("noSpecApproveChainList");
			WebUtils.getMessageManager().addInfoMessage("审批链编辑失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}
	/**
	 * 分行解除审批链
	 * @param bean
	 * @return
	 */
	@RequestMapping("/noSpecDel")
	public String noSpecDel(ApproveChainMgrBean bean){
		if (Tool.DATE.getDate().substring(0,4).equals(bean.getDataYear())) {
			//同年
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
		}else {
			//同年
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
		}
		if(service.noSpecDel(bean)){
			ReturnLinkUtils.setShowLink("noSpecApproveChainList");
			WebUtils.getMessageManager().addInfoMessage("审批链删除成功！");
			return ForwardPageUtils.getSuccessPage();
		}else {
			ReturnLinkUtils.setShowLink("noSpecApproveChainList");
			WebUtils.getMessageManager().addInfoMessage("审批链删除失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}
	/**
	 * 得到撤并的责任中心
	 * @param bean
	 * @return
	 */
//	@RequestMapping("/changeDutyList.do")
//	public String changeDutyList(ApproveChainMgrBean bean){
//		if (Tool.DATE.getDate().substring(0,4).equals(bean.getDataYear())) {
//			//同年
//			bean.setSpecTableName("TB_APRV_CHAIN_SPEC");
//			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
//		}else {
//			//同年
//			bean.setSpecTableName("TB_APRV_CHAIN_SPEC_FUT");
//			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
//		}
//		List<ApproveChainMgrBean> list = service.getChangeDutyList(bean);
//		WebUtils.setRequestAttr("list", list);
//		WebUtils.setRequestAttr("selectInfo", bean);
//		return PREFIX+"changeDutyList";
//	}
	/**
	 * 分行批量审批链
	 * @param bean
	 * @return
	 */
	@RequestMapping("/batchEdit.do")
	public String batchEdit(ApproveChainMgrBean bean){
		if (Tool.DATE.getDate().substring(0,4).equals(bean.getDataYear())) {
			//同年
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
		}else {
			//同年
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
		}
		bean.setOrg2Code(WebHelp.getLoginUser().getOrg2Code());
		bean.setOrg2CodeOri(WebHelp.getLoginUser().getOrg2CodeOri());
		bean.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		List<ApproveChainMgrBean> dutyList = service.getDutyByOrg2(bean);
		WebUtils.setRequestAttr("selectInfo", bean);
		WebUtils.setRequestAttr("dutyList", dutyList);
		WebUtils.setRequestAttr("userOrg1Code", WebHelp.getLoginUser().getOrg1Code());
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/approveChainMgr/noSpecList.do?VISIT_FUNC_ID=08120103&tabsIndex=1");
		return PREFIX+"noSpecBatchEdit";
	}
	/**
	 * 得到公共的物料
	 * @param bean
	 * @return
	 */
	@RequestMapping("/selecstPublicMatrs.do")
	@ResponseBody
	public String selecstPublicMatrs(ApproveChainMgrBean bean){
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		bean.setOrg2Code(WebHelp.getLoginUser().getOrg2Code());
		bean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		bean.setOrg2CodeOri(WebHelp.getLoginUser().getOrg2CodeOri());
		if (Tool.CHECK.isBlank(bean.getDutyArray())) {
			//如果责任中心是空的
			jsonObject.put("data", null);
		}else {
			Map map = service.selecstPublicMatrs(bean);
			jsonObject.put("data", map);
		}
		return jsonObject.writeValueAsString();
	}
	/**
	 * 分行批量维护
	 * @param bean
	 * @return
	 */
	@RequestMapping("/noSpecBatchEditExecute.do")
	public String noSpecBatchEditExecute(ApproveChainMgrBean bean){	
		String org2Code=WebHelp.getLoginUser().getOrg2Code();
		bean.setOrg21Code(org2Code);
		if (Tool.DATE.getDate().substring(0,4).equals(bean.getDataYear())) {
			//同年
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
		}else {
			//同年
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
		}
		if(service.noSpecBatchEditExecute(bean)){
			ReturnLinkUtils.setShowLink(new String[]{"noSpecApproveChainList" , "noSpecApproveChainList1"});
			WebUtils.getMessageManager().addInfoMessage("批量维护审批链成功！");
			return ForwardPageUtils.getSuccessPage();
		}else {
			ReturnLinkUtils.setShowLink(new String[]{"noSpecApproveChainList" , "noSpecApproveChainList1"});
			ReturnLinkUtils.setShowLink("noSpecApproveChainList");
			WebUtils.getMessageManager().addInfoMessage("批量维护审批链失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}
	/**
	 * 费用承担部门批量修改
	 * @param bean
	 * @return
	 */
	@RequestMapping("/batchUpFee")
	public String batchUpFee(ApproveChainMgrBean bean){
		if (Tool.DATE.getDate().substring(0,4).equals(bean.getDataYear())) {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
		}else {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC_FUT");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
		}
		if(service.updateFee(bean)){
			if ("12".equals(bean.getAprvType())) {
				ReturnLinkUtils.setShowLink("specApproveChainList");
				WebUtils.getMessageManager().addInfoMessage("审批链费用承担部门批量修改成功！");
			}else{
				ReturnLinkUtils.setShowLink("noSpecApproveChainList");
				WebUtils.getMessageManager().addInfoMessage("审批链费用承担部门批量修改成功！");
			}
			return ForwardPageUtils.getSuccessPage();
		}else {
			if ("11".equals(bean.getAprvType()) || "12".equals(bean.getAprvType())) {
				ReturnLinkUtils.setShowLink("specApproveChainList");
				WebUtils.getMessageManager().addInfoMessage("审批链费用承担部门批量修改失败！");
			}else{
				ReturnLinkUtils.setShowLink("noSpecApproveChainList");
				WebUtils.getMessageManager().addInfoMessage("审批链费用承担部门批量修改失败！");
			}
			return ForwardPageUtils.getErrorPage();
		}
	}
	/**
	 * 采购部门批量修改
	 * @param bean
	 * @return
	 */
	@RequestMapping("/batchUpBuy")
	public String batchUpBuy(ApproveChainMgrBean bean){
		if (Tool.DATE.getDate().substring(0,4).equals(bean.getDataYear())) {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
		}else {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC_FUT");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
		}
		if(service.updateBuy(bean)){
			if ("11".equals(bean.getAprvType()) || "12".equals(bean.getAprvType())) {
				ReturnLinkUtils.setShowLink("specApproveChainList");
				WebUtils.getMessageManager().addInfoMessage("审批链采购部门批量修改成功！");
			}else{
				ReturnLinkUtils.setShowLink("noSpecApproveChainList");
				WebUtils.getMessageManager().addInfoMessage("审批链采购部门批量修改成功！");
			}
			return ForwardPageUtils.getSuccessPage();
		}else {
			if ("11".equals(bean.getAprvType()) || "12".equals(bean.getAprvType())) {
				ReturnLinkUtils.setShowLink("specApproveChainList");
				WebUtils.getMessageManager().addInfoMessage("审批链采购部门批量修改失败！");
			}else{
				ReturnLinkUtils.setShowLink("noSpecApproveChainList");
				WebUtils.getMessageManager().addInfoMessage("审批链采购部门批量修改失败！");
			}
			return ForwardPageUtils.getErrorPage();
		}
	}
	/**
	 * 物料归口部门批量修改
	 * @param bean
	 * @return
	 */
	@RequestMapping("/batchUpAudit")
	public String batchUpAudit(ApproveChainMgrBean bean){
		if (Tool.DATE.getDate().substring(0,4).equals(bean.getDataYear())) {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
		}else {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC_FUT");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
		}
		try {
			service.updateAudit(bean);
			if ("11".equals(bean.getAprvType()) || "12".equals(bean.getAprvType())) {
				ReturnLinkUtils.setShowLink("specApproveChainList");
				WebUtils.getMessageManager().addInfoMessage("审批链物料归口部门批量修改成功！");
			}else{
				ReturnLinkUtils.setShowLink("noSpecApproveChainList");
				WebUtils.getMessageManager().addInfoMessage("审批链物料归口部门批量修改成功！");
			}
			return ForwardPageUtils.getSuccessPage();
		} catch (Exception e) {
			e.printStackTrace();
			if ("11".equals(bean.getAprvType()) || "12".equals(bean.getAprvType())) {
				ReturnLinkUtils.setShowLink("specApproveChainList");
				WebUtils.getMessageManager().addInfoMessage("审批链物料归口部门批量修改失败！");
			}else{
				ReturnLinkUtils.setShowLink("noSpecApproveChainList");
				WebUtils.getMessageManager().addInfoMessage("审批物料归口部门批量修改失败！");
			}
			return ForwardPageUtils.getErrorPage();
		}
	}
	
	
	/**
	 * 物料审批链查询
	 * @param bean
	 * @return
	 */	
	@RequestMapping("/appList.do")
	public String appList(ApproveChainMgrBean bean){	
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		String org1Name = WebHelp.getLoginUser().getOrg1Name();
		bean.setOrg1Code(org1Code);
		bean.setOrg1Name(org1Name);
		bean.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		List<ApproveChainMgrBean> list=service.appList(bean);

		WebUtils.setRequestAttr("al", bean);
		WebUtils.setRequestAttr("appList", list);
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		return PREFIX+"appList";
	}
	
	/**
	 * 审批链查询弹出监控指标列表
	 * @param bean
	 * @return
	 */
	
	@RequestMapping("/montCodePage.do")
	public String montCodePage(ApproveChainMgrBean bean){
		List<ApproveChainMgrBean> montList = service.getMont(bean);
			WebUtils.setRequestAttr("ml", montList);
			return PREFIX + "montCodePage";
	}
	
	/**
	 * 审批链导出
	 * @param bean
	 * @return
	 */
	@RequestMapping("/download.do")
	@ResponseBody
	public String download(ApproveChainMgrBean bean){
		AbstractJsonObject jsonObject = new SuccessJsonObject();	
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		String org21Code= WebHelp.getLoginUser().getOrg2Code();
		bean.setOrg1Code(org1Code);
		bean.setOrg21Code(org21Code);
		//Excel导出操作
		try {if (service.download(bean)==null) {
			jsonObject.put("pass", false);
		} else {
			jsonObject.put("pass", true);
		}		

		} catch (Exception e) {
			jsonObject.put("pass", false);
			e.printStackTrace();
		}		
		return jsonObject.writeValueAsString();
	}
	/**
	 * 提交 待修改
	 * @return
	 */
	@RequestMapping("/selectSubmit.do")
	public String selectSubmit(ApproveChainMgrBean bean){
		try {
			service.selectSubmit(bean);
			if ("01".equals(bean.getOrgType())) {
				ReturnLinkUtils.setShowLink("specApproveChainList");
				WebUtils.getMessageManager().addInfoMessage("提交成功!");
			}else{
				ReturnLinkUtils.setShowLink("noSpecApproveChainList");
				WebUtils.getMessageManager().addInfoMessage("提交成功");
			}
			return ForwardPageUtils.getSuccessPage();
		} catch (Exception e) {
			e.printStackTrace();
			if ("01".equals(bean.getOrgType())) {
				ReturnLinkUtils.setShowLink("specApproveChainList");
				WebUtils.getMessageManager().addInfoMessage("提交失败!");
			}else{
				ReturnLinkUtils.setShowLink("noSpecApproveChainList");
				WebUtils.getMessageManager().addInfoMessage("提交失败");
			}
			return ForwardPageUtils.getErrorPage();
		}
	}
	@RequestMapping("/selectDetail.do")
	public String selectDetail(ApproveChainMgrBean bean){
		if (Tool.DATE.getDate().substring(0,4).equals(bean.getDataYear())) {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
		}else {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC_FUT");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
		}
		bean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		ApproveChainMgrBean bean2 = service.selectDetail(bean);
		bean2.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		WebUtils.setRequestAttr("bean", bean2);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/approveChainMgr/appList.do?VISIT_FUNC_ID=08120104");
		return PREFIX+"appDetail";
	}
	
	@RequestMapping("/forUpdate.do")
	public String forUpdate(ApproveChainMgrBean bean){
		List<ApproveChainMgrBean> list = service.forUpdate(bean);
		WebUtils.setRequestAttr("bean", bean);
		WebUtils.setRequestAttr("list", list);
		if ("01".equals(bean.getOrgType())) {
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/approveChainMgr/specList.do?VISIT_FUNC_ID=08120102&tabsIndex=2");
		}else {
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/approveChainMgr/noSpecList.do?VISIT_FUNC_ID=08120103&tabsIndex=2");
		}
		return PREFIX+"forUpdate";
	}
	
}
