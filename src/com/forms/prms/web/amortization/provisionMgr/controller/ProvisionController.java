package com.forms.prms.web.amortization.provisionMgr.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.fms.FmsGeneral;
import com.forms.prms.web.amortization.provisionMgr.domain.ProvisionBean;
import com.forms.prms.web.amortization.provisionMgr.service.ProvisionService;
import com.forms.prms.web.amortization.tradeBackwash.service.BackwashService;
import com.forms.prms.web.monthOver.service.MonthOverService;
import com.forms.prms.web.sysmanagement.parameter.service.ParameterService;
import com.forms.prms.web.user.domain.User;
import com.forms.prms.web.util.ForwardPageUtils;

/**
 * Title:			ProvisionController
 * Description:		预提管理的Controller层
 * Copyright: 		formssi
 * @author 			HQQ
 * @project 		ERP
 * @date 			2015-05-18
 * @version 		1.0
 */
@Controller
@RequestMapping("/amortization/provisionMgr")
public class ProvisionController {

	private final String BASE_URL = "amortization/provisionMgr/";
	
	@Autowired
	private ProvisionService proService;			//引入预提管理的Service
	
	@Autowired
	private MonthOverService monthOverService;		//引入用于判断是否处于月结状态(只有月结中时，才允许预提经办和预提复核)
	
	@Autowired
	private FmsGeneral fmsGeneral;
	
	@Autowired
	private ParameterService pService;
	
	@Autowired
	private BackwashService bService;
	
	/**
	 * @methodName provisionHandleList
	 * 		预提经办列表：	列表展示出当前受益年月下待执行预提经办的预提信息，
	 * 						在当前页面中可以执行是否需要预提，并提交需要经办的预提至预提复核部分
	 * @param provision	预提Bean
	 * @return
	 */
	@RequestMapping("/provhandle.do")
	public String provisionHandleList(ProvisionBean provision){
		ReturnLinkUtils.addReturnLink("provhandlelist","进入预提经办页面");
		if(!bService.getTaskTypeStatus()){
			WebUtils.getMessageManager().addInfoMessage("当前执行任务不是预提待摊任务，不允许进行预提经办操作！");
			return ForwardPageUtils.getErrorPage();
		}
		if("0".equals(monthOverService.getMaxDataFlag())){									//月结中，允许并返回预提经办界面
//		if("1" == "1"){		//测试使用	
			
			//检查预提待摊数据生成状态
			String org1Code = WebHelp.getLoginUser().getOrg1Code();
			String yyyymm = Tool.DATE.getDateStrNO().substring(0,6);
			String ppStatus = proService.getPPStatus(org1Code,yyyymm);  
			String org2Status = proService.checkOrg2Ok(WebHelp.getLoginUser().getOrg2Code());//所在二级行或者机构下
				if( (ppStatus!=null && "04".equals(ppStatus))||org2Status == null){		//未生成数据  或存在回冲
					WebUtils.getMessageManager().addInfoMessage("提示：当月未生成预提待摊数据或存在合同回冲，不允许进行预提经办！请联系一级行管理员触发生成预提待摊数据！");
					ReturnLinkUtils.setShowLink("provQueryList");
					return ForwardPageUtils.getErrorPage();
				}
		
			//6-8修改：判断用户是否超级管理员(Y:展示其一级行下所属的全部责任中心    N:展示其二级行下属所有责任中心/其当前的机构)
			User loginUser = WebHelp.getLoginUser();
			provision = proService.judgeSuperAdmin(loginUser,provision);					//用于设置初始查询过滤条件，并设置页面机构树的根节点为:当前登录用户的一级行机构
			
			provision = this.setCreateDeptListByCreateDepts(provision);
			List<ProvisionBean> proList = proService.getProvHandleList(provision);			//1.根据主要查询条件查询数据
			//String allQueryIdList = proService.getAllProvHandleIdList(provision);			//2.获取所有(不分页)复核查询条件的数据ID集合(塞到前端，用于全数据提交)
			ProvisionBean ppSumAmt = proService.getPPSumAmt(provision);
			//String createDepts = StringUtil.arrayToString(Tool.CHECK.isEmpty(provision.getCreateDepts()) ? new String[]{} :provision.getCreateDepts());
			//WebUtils.setRequestAttr("selectedDepts", Tool.CHECK.isEmpty(provision.getCreateDepts()) ? "" : createDepts.substring(1, createDepts.length()-1));
			//WebUtils.setRequestAttr("allQueryIdList", allQueryIdList);						//3-1.设置上次查询到的所有ID集合
			WebUtils.setRequestAttr("provision", provision);								//3-2.用于处理搜索查询的条件默认值
			WebUtils.setRequestAttr("proList", proList);	
			WebUtils.setRequestAttr("provisionAmtSum",ppSumAmt.getProvisionAmtSum());
			WebUtils.setRequestAttr("prepaidAmtSum", ppSumAmt.getPrepaidAmtSum());
			WebUtils.setRequestAttr("deadlineDay", pService.getDeadlineDay());
			WebUtils.setRequestAttr("deadlineTime", pService.getDeadlineTime());
			return BASE_URL+ "provhandlelist";
		}else{																				//非月结中，不允许预提经办，返回错误信息提示页面
			WebUtils.getMessageManager().addInfoMessage("提示：非月结状态，不允许进行预提经办！");
			ReturnLinkUtils.setShowLink("provQueryList");
			return ForwardPageUtils.getErrorPage();
		}
	}
	
	/**
	 * @methodName provRecheckList
	 * 		预提复核列表：	列表展示出当前受益年月下待执行预提复核的预提信息，
	 * 						在当前页面中可以执行预提复核(包括单个或批量的预提退回和预提通过)；
	 * 						当执行预提退回后，该预提将重新进入预提经办列表，其状态为预提退回，可以再次进行预提经办；
	 * 						当执行预提通过后，该预提将不再在预提复核列表中，只有通过预提查询模块指定查询"预提通过"的预提才可以查询到；
	 * @param provision		预提Bean
	 * @return
	 */
	@RequestMapping("/provrecheck.do")
	public String provRecheckList(ProvisionBean provision){
		ReturnLinkUtils.addReturnLink("provisionrechecklist","进入预提复核页面");
		if(!bService.getTaskTypeStatus()){
			WebUtils.getMessageManager().addInfoMessage("当前执行任务不是预提待摊任务，不允许进行预提复核操作！");
			return ForwardPageUtils.getErrorPage();
		}
		if("0".equals(monthOverService.getMaxDataFlag())){										//月结中，允许并返回预提复核界面
//		if("1" == "1"){			//测试时使用
			//6-8修改：判断用户是否超级管理员(Y:展示其一级行下所属的全部责任中心    N:展示其二级行下属所有责任中心/其当前的机构)
			User loginUser = WebHelp.getLoginUser();
			
			provision.setOrg1Code(loginUser.getOrg1Code());			//超级管理员时取一级行下属所有责中
			provision.setOrgFlag("1");
			
			provision = this.setCreateDeptListByCreateDepts(provision);
			//String queryAllProvList = proService.getAllProvRecheckIdList(provision);
			List<ProvisionBean> provrecheckList = proService.getProvRecheckList(provision);
			//String createDepts = StringUtil.arrayToString(Tool.CHECK.isEmpty(provision.getCreateDepts()) ? new String[]{} :provision.getCreateDepts());
			//WebUtils.setRequestAttr("selectedDepts", Tool.CHECK.isEmpty(provision.getCreateDepts()) ? "" : createDepts.substring(1, createDepts.length()-1));
			WebUtils.setRequestAttr("provision", provision);
			//WebUtils.setRequestAttr("queryAllProvList", queryAllProvList);	
			WebUtils.setRequestAttr("provrecheckList", provrecheckList);
			WebUtils.setRequestAttr("deadlineDay", pService.getDeadlineDay());
			WebUtils.setRequestAttr("deadlineTime", pService.getDeadlineTime());
			WebUtils.setRequestAttr("loginUser", WebHelp.getLoginUser().getUserId());
			return BASE_URL + "provrechecklist";
		}else{																					//非月结中，不允许执行预提复核操作，并进入错误提示页面
			WebUtils.getMessageManager().addInfoMessage("提示：非月结状态，不允许进行预提复核！");
			ReturnLinkUtils.setShowLink("provQueryList");
			return ForwardPageUtils.getErrorPage();
		}
	}
	
	/**
	 * @methodName provHandleSubmit
	 * 		预提经办页面"提交"操作，提交后执行的逻辑:更改预算的状态为待复核，以及提交人Oper等信息，将该预提提交到预提复核界面
	 * @param provMgrIdList		预提经办提交的预提ID集合
	 * @param provFlag			需要预提：1 /不需要预提：0
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/provHandleSubmit.do")
	public String provHandleSubmit(String provMgrIdList,String provFlag){
		try {
			int updateCount = proService.handleSubmit(provMgrIdList , provFlag);
			CommonLogger.info("【预提经办】--成功提交预提经办"+updateCount+"笔");
			ReturnLinkUtils.setShowLink("provhandlelist");								//设置快速返回链接：预提经办列表
			return ForwardPageUtils.getSuccessPage();
		} catch (Exception e) {
			e.printStackTrace();
			CommonLogger.error("【预提经办】--预提经办失败！");
			return ForwardPageUtils.getErrorPage();
		}
		
	}
	
	/**
	 * @methodName provRecheckUpdate
	 * 		预提复核通过/退回操作：该操作中，根据用户对预提选择"通过/退回"进行预提的状态修改，并录入或更改复核人checkOper的信息
	 * 			PS:在Services层处理单条或批量的预提复核通过，处理单条的预提复核退回
	 * @param  provision
	 * @return
	 */
	@RequestMapping("/provRecheckUpdate.do")
	public String provRecheckUpdate(ProvisionBean provision) {
		try {
			int recheckCount = proService.provRecheck(provision);							//执行预提复核操作(需要复核的预提在provMgrIdList属性中)
			
			String org1Code = WebHelp.getLoginUser().getOrg1Code();
			String yyyymm = proService.getYYYYMM(org1Code);
			String user = WebHelp.getLoginUser().getUserId();
			/*************检查预提复核情况******************/
			if(proService.getNotPass(org1Code) == null){//预提复核全部通过
				//3.调用生成预提待摊的交易明细接口数据
				int flag = fmsGeneral.dealProvisionPrepaid(org1Code, yyyymm, "1", user);				
				if(flag == 2){
					CommonLogger.error("存在未复核的预提记录，自动触发预提待摊接口数据上送失败！,ProvisionController,provRecheckUpdate()");
					WebUtils.getMessageManager().addInfoMessage("存在未复核的预提记录，自动触发预提待摊接口数据上送失败！");
					return ForwardPageUtils.getErrorPage();
				}
				else if(flag == 3){
					CommonLogger.error("自动触发预提待摊接口数据上送失败，请在任务年月时间段执行任务！,ProvisionController,provRecheckUpdate()");
					WebUtils.getMessageManager().addInfoMessage("自动触发预提待摊接口数据上送失败，请在任务年月时间段执行任务！");
					return ForwardPageUtils.getErrorPage();
				}else if(flag == 5){
					CommonLogger.error("自动触发预提待摊接口数据上送失败，存在发生变化的责任中心未处理情况！,ProvisionController,provRecheckUpdate()");
					WebUtils.getMessageManager().addInfoMessage("自动触发预提待摊接口数据上送失败，存在发生变化的责任中心未处理情况！");
					return ForwardPageUtils.getErrorPage();
				}else if(flag == 6){
					CommonLogger.error("操作失败，预提待摊任务超出当月截止时间！请在次月初触发补做。,ProvisionController,provRecheckUpdate()");
					WebUtils.getMessageManager().addInfoMessage("操作失败，预提待摊任务超出当月截止时间！请在次月初触发补做。");
					return ForwardPageUtils.getErrorPage();
				}
			}		
			
			CommonLogger.info("【预提复核】--成功执行预提复核"+recheckCount+"条");
			ReturnLinkUtils.setShowLink("provisionrechecklist");							//设置快速返回链接：预提待复核列表
			return ForwardPageUtils.getSuccessPage();
		} catch (Exception e) {
			e.printStackTrace();
			CommonLogger.error("【预提复核】--预提复核失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}
	
	/**
	 * @methodName provQueryMgr
	 * 		预提查询管理，可以进行预提的精细查询(包括可以根据预提的状态、是否需要预提、合同号、收益年月、创建部门等信息进行预提信息的过滤查询)
	 * @param provision
	 * @return
	 */
	@RequestMapping("/provQueryMgr.do")
	public String provQueryMgr(ProvisionBean provision){
		ReturnLinkUtils.addReturnLink("provQueryList", "进入预提查询管理");					//添加预提查询管理的ReturnLink
		User loginUser = WebHelp.getLoginUser();
		String showFeeYyyymm = provision.getFeeYyyymm();									//显示在JSP上的受益年月样式
		provision = proService.judgeSuperAdmin(loginUser,provision);						//用于设置初始查询过滤条件，并设置页面机构树的根节点为:当前登录用户的一级行机构
		provision = this.setCreateDeptListByCreateDepts(provision);
		
		List<ProvisionBean> provQueryList = proService.provQueryList(provision);			//根据传入查询条件进行预提信息的查询
		provision.setFeeYyyymm(showFeeYyyymm);												//由于在Service层更改了受益年月的样式，这里需要重新赋值！否则二次查询时StringIndex会越界！
		
		//String createDepts = StringUtil.arrayToString(Tool.CHECK.isEmpty(provision.getCreateDepts()) ? new String[]{} :provision.getCreateDepts());
		//WebUtils.setRequestAttr("selectedDepts", Tool.CHECK.isEmpty(provision.getCreateDepts()) ? "" : createDepts.substring(1, createDepts.length()-1));
//		System.out.println("【选中的部门】："+  (Tool.CHECK.isEmpty(provision.getCreateDepts()) ? "" : createDepts.substring(1, createDepts.length()-1)));
		WebUtils.setRequestAttr("provision", provision);
		WebUtils.setRequestAttr("provPassList", provQueryList);
		return BASE_URL + "provquerymgrlist";
	}
	

	/**
	 * @methodName getProvDetail
	 * 		预提详情查看：获取预提详细信息并展示到详情页面上
	 * @param provision
	 * @return
	 */
	@RequestMapping("/getProvDetail.do")
	public String getProvDetail(ProvisionBean provision){
		provision = proService.getProvDetailService(provision);
		//将查询得到的预提对象塞到前端JSP进行信息展示
		WebUtils.setRequestAttr("provision", provision);
		return BASE_URL + "provisionDetail";
	}
	
	
	/**
	 * 【Controller-公共工具方法】
	 * @methodName setCreateDeptListByCreateDepts
	 * 		  根据createDepts属性的值，设置createDeptList属性的值(将数组Array转换出List类型)
	 * @param 	provision
	 * @return	ProvisionBean
	 */
	public ProvisionBean setCreateDeptListByCreateDepts(ProvisionBean provision){
		if(null != provision.getCreateDepts()){
			if(provision.getCreateDepts().length != 0){
				provision.setCreateDeptList(Arrays.asList(provision.getCreateDepts()));
			}
		}
		return provision;
	}
	
	public static void main(String[] args) {
		String a = "{02276}";
		System.out.println(a.substring(0, a.length()-1));
	}
	
	@RequestMapping("addDownLoad.do")
	@ResponseBody
	public String addDownload(ProvisionBean proBean) throws Exception{
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		String taskId = "";
		try{			
			taskId = proService.queryDownloadList(proBean);
			if(taskId == null || "".equals(taskId)){				
				jsonObject.put("pass", false);				
			}else{
				jsonObject.put("pass", true);
			}
		}catch(Exception e){
			jsonObject.put("pass", false);
			throw e;
		}
		
		return jsonObject.writeValueAsString();
	}
}
															