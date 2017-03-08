package com.forms.prms.web.budget.budgetInspect.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
import com.forms.prms.web.budget.bgtImport.service.BudgetImportService;
import com.forms.prms.web.budget.budgetInspect.domain.BudgetManageBean;
import com.forms.prms.web.budget.budgetInspect.domain.MatrBean;
import com.forms.prms.web.budget.budgetInspect.domain.SumCnt;
import com.forms.prms.web.budget.budgetInspect.domain.SumCntDetail;
import com.forms.prms.web.budget.budgetInspect.service.BudgetInspectService;
import com.forms.prms.web.sysmanagement.montAprvBatch.export.service.ExportService;
import com.forms.prms.web.sysmanagement.montindex.domain.MontIndexBean;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/budget/budgetInspect")
public class BudgetInspectController {
	
	private static final String PREFIX = "budget/budgetInspect/";
	@Autowired
	BudgetInspectService service;
	@Autowired
	private ExportService exportService;
	@Autowired 
	private BudgetImportService importService;
	
	@RequestMapping("/org1CntList.do")
	public String org1CntList(SumCnt sumCntInfo) throws Exception{
		sumCntInfo.setOrgFlag("1");//省行
		return cntList(sumCntInfo);
	}
	
	@RequestMapping("/org2CntList.do")
	public String org2CntList(SumCnt sumCntInfo) throws Exception{
		sumCntInfo.setOrgFlag("2");//二级行
		return cntList(sumCntInfo);
	}
	
	@RequestMapping("/dutyCodeCntList.do")
	public String dutyCodeCntList(SumCnt sumCntInfo) throws Exception{
		sumCntInfo.setOrgFlag("3");//业务部门
		return cntList(sumCntInfo);
	}
	
	/**
	 * 根据合同号查询合同使用情况,也可以作为预算的使用明细查询
	 * @param sumCntInfo
	 * @param bgtId
	 * @return
	 * @throws Exception
	 */
	public String cntList(SumCnt sumCntInfo) throws Exception
	{
		sumCntInfo.setCntNum(null == sumCntInfo.getCntNum() ? 
				null : sumCntInfo.getCntNum().trim());
		if(null != sumCntInfo.getBgtId() && !"".equals(sumCntInfo.getBgtId()))
		{
			sumCntInfo.setOrgFlag("1");
			if ("1".equals(sumCntInfo.getOrgType())) {
				WebHelp.setLastPageLink("uri", "shBgtDisplayList");
			}
			else if ("2".equals(sumCntInfo.getOrgType())) {
				WebHelp.setLastPageLink("uri", "fhBgtDisplayList");
			}
			
		}
		List<SumCnt> list = service.getSumCntInfo(sumCntInfo);
		WebUtils.setRequestAttr("queryCondition", sumCntInfo);
		WebUtils.setRequestAttr("sumCntList", list);
		ReturnLinkUtils.addReturnLink("cntDisplayList", "返回列表");
		return PREFIX + "cntList";
	}
	
	
	/**
	 * 用于跳转至使用明细页面(cntList.do),绕过权限检查
	 * @param sumCntInfo
	 * @param bgtId
	 * @return
	 */
	@RequestMapping("/toCntList.do")
	public String toCntList(SumCnt sumCntInfo) throws Exception{
		return cntList(sumCntInfo);
	}
	
	/**
	 * 合同使用明细的详细信息
	 * @param cntNum
	 * @param scId
	 * @param bgtId 暂时用不到了
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cntDetail.do")
	public String detail(String cntNum, String scId, String bgtId) throws Exception{
		List<SumCntDetail> sumCntDetailList = service.getSumCntDetail(scId);
		WebUtils.setRequestAttr("sumCntDetailList", sumCntDetailList);
		WebUtils.setRequestAttr("cntNum", cntNum);
		WebHelp.setLastPageLink("uri", "cntDisplayList");
//		if(null==bgtId||bgtId==""){
//			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/budget/budgetInspect/cntList.do?VISIT_FUNC_ID=020702");
//		}
//		else{
//			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/budget/budgetInspect/toCntList.do?VISIT_FUNC_ID=02070103&bgtId="+bgtId);
//		}
		return PREFIX + "cntDetail";
	}
	/**
	 * 用于跳转至cntDetail页面,暂时用不到了
	 * @param cntNum
	 * @param scId
	 * @param bgtId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toCntDetail.do")
	public String toCntDetail(String cntNum, String scId, String bgtId) throws Exception{
		return detail(cntNum, scId, bgtId);
	}
	@RequestMapping("/shList.do")
	public String shList(BudgetManageBean bmBean) throws Exception{
		bmBean.setOrgType("1");
		bmBean.setMenuType("1");
		ReturnLinkUtils.addReturnLink("shBgtDisplayList", "返回列表");
		return queryBudget(bmBean);
	}
		
	@RequestMapping("/fhList.do")
	public String fhList(BudgetManageBean bmBean) throws Exception{
		bmBean.setOrgType("2");
		bmBean.setMenuType("1");
		ReturnLinkUtils.addReturnLink("fhBgtDisplayList", "返回列表");
		return queryBudget(bmBean);
	}
	@RequestMapping("/shChangeList.do")
	public String shChangeList(BudgetManageBean bmBean) throws Exception{
		bmBean.setOrgType("1");
		bmBean.setMenuType("2");
		ReturnLinkUtils.addReturnLink("shBugetAdjust", "返回列表");
		return queryBudget(bmBean);
	}
		
	@RequestMapping("/fhChangeList.do")
	public String fhChangeList(BudgetManageBean bmBean) throws Exception{
		bmBean.setOrgType("2");
		bmBean.setMenuType("2");
		ReturnLinkUtils.addReturnLink("fhBugetAdjust", "返回列表");
		return queryBudget(bmBean);
	}
	/**
	 * 根据相应条件查询预算,获取相应预算信息。
	 */
	@RequestMapping("/list.do")
	public String queryBudget(BudgetManageBean bmBean) throws Exception{
		if (bmBean == null)
			bmBean = new BudgetManageBean();
		if (Tool.CHECK.isEmpty(bmBean.getOverDrawType())) {
			bmBean.setOverDrawType("0");//根据bean中的OverDrawType属性是否存在来设置器初始值为0
		}
		if (Tool.CHECK.isEmpty(bmBean.getBgtYear())) {
			bmBean.setBgtYear(Tool.DATE.getDateStrNO().substring(0,4));
		}
		bmBean.setBgtId(bmBean.getBgtIdTemp());
		WebUtils.setRequestAttr("org1Code", WebHelp.getLoginUser().getOrg1Code());
		WebUtils.setRequestAttr("queryCondition", bmBean);
		bmBean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		bmBean.setOrg2Code(WebHelp.getLoginUser().getOrg2Code());
		List<BudgetManageBean> infos = service.queryBudgetmanageBeans(bmBean);
		WebUtils.setRequestAttr("infoList", infos);
//		ReturnLinkUtils.addReturnLink("bgtDisplayList", "返回列表");
		return PREFIX + "list";
	}
	
	//预算维度-(Total+下达明细+使用明细)数据导出功能
	@RequestMapping("/exportBgtInspectQueryData.do")
	@ResponseBody
	public String exportBgtInspectQueryData(BudgetManageBean bmBean){
		AbstractJsonObject jsonObject = new SuccessJsonObject();		
		//Excel导出操作
		String taskId = null;
		try {
			taskId = service.bgtTotalDataExport(bmBean);
			if (Tool.CHECK.isBlank(taskId)) {
				jsonObject.put("pass", false);
			} else {
				jsonObject.put("pass", true);
			}		
		} catch (Exception e) {
			try{
				//如果  taskId已插入出现异常,则更新为失败
				if(!Tool.CHECK.isBlank(taskId)){
					exportService.updateTaskDataFlag(taskId);
				}
			}catch (Exception e1) {
				e1.printStackTrace();
			}
			jsonObject.put("pass", false);
			e.printStackTrace();
		}
		return jsonObject.writeValueAsString();
	}

	 
	
	/**
	 * 根据Id查询预算下达明细
	 */
	@RequestMapping("/sumList.do")
	public String queryBudgetList(String bgtId) throws Exception{
		List<BudgetManageBean> bmList = service.view(bgtId);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/budget/budgetInspect/list.do?VISIT_FUNC_ID=020701");
		WebUtils.setRequestAttr("bmList" , bmList);
		return PREFIX + "sumList";
	}
	/**
	 * 根据唯一Id查询预算下达明细详情
	 */
	@RequestMapping("/sumDetail.do")
	public String queryBudgetDetail(String sdId) {
		BudgetManageBean bm = service.sumDetail(sdId);
		String bgtId = bm.getBgtId();
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/budget/budgetInspect/sumList.do?VISIT_FUNC_ID=02070102&bgtId="+bgtId);
		WebUtils.setRequestAttr("bm" , bm);
		return PREFIX + "sumDetail";
	}
	/**
	 * 根据用户所在行或者指标类型获取监控指标名称
	 */
	@RequestMapping("/getMontName.do")
	@ResponseBody
	public String  getMontName(BudgetManageBean bmBean) throws Exception{
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		List<BudgetManageBean> bugetManageList=service.getMontName(bmBean);
		if(bugetManageList!=null){
			jsonObject.put("bugetManageList", bugetManageList);
		}
		return jsonObject.writeValueAsString();
	}
	/**
	 *根据物料编码或者物料名称等获取物料信息
	 */
	@RequestMapping("/getMatrName.do")
	public String getMatrName(MatrBean mBean) throws Exception{
		if (mBean == null)
			mBean = new MatrBean();
		
		if (Tool.CHECK.isEmpty(mBean.getMatrType())) {
			mBean.setMatrType("0");
		}
		List<MatrBean> matrBeans = service.getMatrName(mBean);
		WebUtils.setRequestAttr("matr", mBean);
		WebUtils.setRequestAttr("matrList", matrBeans);
		return PREFIX + "matrList";
	}
	/**
	 * 根据唯一Id查选合同预算详情
	 */
	@RequestMapping("/cntInspectDetail.do")
	public String cntInspectDetail(String scdId) {
		BudgetManageBean bm = service.cntInspectDetail(scdId);
		WebUtils.setRequestAttr("bm" , bm);
		return PREFIX + "cntInspectDetail";
	}
	
	@RequestMapping("selectMont.do")
	public String selectMont(MontIndexBean montIndexBean) {
		montIndexBean.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
		List<MontIndexBean> montList = service.selectRole(montIndexBean);
		WebUtils.setRequestAttr("montList", montList);
		WebUtils.setRequestAttr("selectInfo", montIndexBean);
		WebUtils.setRequestAttr("funcId", WebUtils.getParameter(WebConsts.FUNC_ID_KEY));
		return PREFIX + "montList";
	}
	@RequestMapping("bugetAdjustLog.do")
	public String bugetAdjustLog(BudgetManageBean bean) {
		List<BudgetManageBean> bugetAdjustList = service.bugetAdjustLog(bean);
		WebUtils.setRequestAttr("bugetAdjustList", bugetAdjustList);
		WebUtils.setRequestAttr("searchInfo", bean);
		return PREFIX + "bugetAdjustLog";
	}
	@RequestMapping("getMontNameList.do")
	public String getMontNameList(BudgetManageBean bean) {
		List<BudgetManageBean> montList = service.getMontNameList(bean);
		WebUtils.setRequestAttr("montList", montList);
		WebUtils.setRequestAttr("searchInfo", bean);
		return PREFIX + "montList";
	}
	/**
	 * 跳转到预算调整页面
	 */
	@RequestMapping("/toAdjust.do")
	public String toAdjust(BudgetManageBean bmBean) throws Exception{
		String orgType = bmBean.getOrgType();
		WebUtils.setRequestAttr("orgType", orgType);
		bmBean=service.getBugetInfo(bmBean);
		WebUtils.setRequestAttr("bmBean", bmBean);
		if ("1".equals(orgType)) {
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
					+ "/budget/budgetInspect/shChangeList.do?VISIT_FUNC_ID=02090321");
		}else if ("2".equals(orgType)) {
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
					+ "/budget/budgetInspect/fhChangeList.do?VISIT_FUNC_ID=02090322");
		}
		return PREFIX + "bugetAdjust";
	}
	@RequestMapping("checkAdjust.do")
	@ResponseBody
	public String checkAdjust(BudgetManageBean bmBean) {
		AbstractJsonObject json = new SuccessJsonObject();
		json.put("flag", "Y");
		String checkMsg = ""; // 校验结果MSG
		//如果调整金额为正数
		if(!"-".equals(bmBean.getTzjy().substring(0,1))){
			//检查增加的是否超过了总可用预算
			String check=importService.isMore(bmBean);
			double endCheck=Double.valueOf(check);
			if(endCheck>0){
				checkMsg="增加的总占用预算超过了总可用预算！";
				json.put("flag", "N");
			}
		}
		//调整方式为减少
		else if("-".equals(bmBean.getTzjy().substring(0,1))){
			//检查减少的是否超过了总占用预算
			String check1=importService.isMore1(bmBean);
			double endCheck1=Double.valueOf(check1);
			if(endCheck1<0){
				checkMsg="减少的总占用预算超过了原有的总占用预算！";
				json.put("flag", "N");
			}
		} 
		json.put("msg", checkMsg);
		return json.writeValueAsString();

	}
	@RequestMapping("checkValidBgt.do")
	@ResponseBody
	public String checkValidBgt(BudgetManageBean bmBean) {
		AbstractJsonObject json = new SuccessJsonObject();
		json.put("flag", "Y");
		if(!"-".equals(bmBean.getTzjy().substring(0,1))){
			//调整的是整数
			//看看是否有透支。如果有透支必须补齐透支
			BudgetManageBean budgetManageBean = service.getBean(bmBean.getBgtId());
			if (null!=budgetManageBean) {
				BigDecimal tzjy = new BigDecimal(bmBean.getTzjy());
				BigDecimal bgtOverdraw = new BigDecimal(budgetManageBean.getBgtOverdraw());
				if (bgtOverdraw.compareTo(tzjy)>0) {
					//存在透支 且不能补足透支
					json.put("flag", "N");
					json.put("msg", "该预算透支了"+bgtOverdraw+"必须补齐透支");
				}
				
			}
		}
		else if("-".equals(bmBean.getTzjy().substring(0,1))){
			BudgetManageBean budgetManageBean = service.getBean(bmBean.getBgtId());
			if (null!=budgetManageBean) {
				BigDecimal tzjy = new BigDecimal(bmBean.getTzjy().substring(1));
				BigDecimal bgtSumValid = new BigDecimal(budgetManageBean.getBgtSumValid());
				if (tzjy.compareTo(bgtSumValid)>0) {
					//存在透支 且不能补足透支
					json.put("flag", "N");
					json.put("msg", "该预算可用金额只有"+bgtSumValid+"不能将它改为负数");
				}
				
			}
		}
		return json.writeValueAsString();
	}
	/**
	 * 调整提交
	 * @param bean
	 * @return
	 */
	@RequestMapping("/adjust.do")
	public String adjust(BudgetManageBean bean){
		if(service.adjustBgt(bean)){
			if ("1".equals(bean.getOrgType())) {
				ReturnLinkUtils.setShowLink("shBugetAdjust");
			}else {
				ReturnLinkUtils.setShowLink("fhBugetAdjust");
			}
			if("2".equals(bean.getType())){
				WebUtils.getMessageManager().addInfoMessage("占用预算调整成功！");
			}
			else{
				WebUtils.getMessageManager().addInfoMessage("可用预算调整成功！");
			}
			return ForwardPageUtils.getSuccessPage();
		}else {
			if ("1".equals(bean.getOrgType())) {
				ReturnLinkUtils.setShowLink("shBugetAdjust");
			}else {
				ReturnLinkUtils.setShowLink("fhBugetAdjust");
			}
			if("2".equals(bean.getType())){
				WebUtils.getMessageManager().addInfoMessage("占用预算调整失败！");
			}
			else{
				WebUtils.getMessageManager().addInfoMessage("可用预算调整失败！");
			}
			return ForwardPageUtils.getErrorPage();
		}
	}
	/**
	 * 跳转到可用预算调整页面
	 */
	@RequestMapping("/toValidBgt.do")
	public String toValidBgt(BudgetManageBean bmBean) throws Exception{
		bmBean=service.getBugetInfo(bmBean);
		WebUtils.setRequestAttr("bmBean", bmBean);
		if ("1".equals(bmBean.getOrgType())) {
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
					+ "/budget/budgetInspect/shChangeList.do?VISIT_FUNC_ID=02090321");
		}else if ("2".equals(bmBean.getOrgType())) {
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
					+ "/budget/budgetInspect/fhChangeList.do?VISIT_FUNC_ID=02090322");
		}
		return PREFIX + "validBgt";
	}
	@RequestMapping("checkDelBgt.do")
	@ResponseBody
	public String checkDelBgt(BudgetManageBean bmBean) {
		AbstractJsonObject json = new SuccessJsonObject();
		String flag = "Y"; // 校验结果MSG
		BudgetManageBean bean2 = service.getBean(bmBean.getBgtId());
		if (!Tool.CHECK.isEmpty(bean2)) {
			BigDecimal bgtFrozen  = bean2.getBgtFrozen();
			BigDecimal bgtUsed = new BigDecimal(bean2.getBgtUsed());
			if (bgtFrozen.compareTo(new BigDecimal(0)) !=0 ||  bgtUsed.compareTo(new BigDecimal(0)) !=0 ) {
				json.put("flag", "N");
				json.put("msg", "该预算冻结了"+bean2.getBgtFrozen()+"，占用了"+bean2.getBgtUsed()+",不能删除");
				return json.writeValueAsString();
			}
		}
		int isUsed = importService.isUsed(bmBean);
		if (isUsed>0) {
			//被使用过了 要提示信息
			json.put("flag", "W");
		}
		json.put("flag", flag);
		return json.writeValueAsString();

	}
	@RequestMapping("/delBgt.do")
	public String delBgt(BudgetManageBean bmBean) throws Exception{
		Map<String, Object> map = service.delBgt(bmBean);
		if ("Y".equals(map.get("flag"))) {
			//删除成功
			if ("1".equals(bmBean.getOrgType())) {
				ReturnLinkUtils.setShowLink("shBugetAdjust");
			}else {
				ReturnLinkUtils.setShowLink("fhBugetAdjust");
			}
			
			WebUtils.getMessageManager().addInfoMessage("删除预算成功！");
			return ForwardPageUtils.getSuccessPage();
		}else {
			if ("1".equals(bmBean.getOrgType())) {
				ReturnLinkUtils.setShowLink("shBugetAdjust");
			}else {
				ReturnLinkUtils.setShowLink("fhBugetAdjust");
			}
			WebUtils.getMessageManager().addInfoMessage("删除预算失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}
}
