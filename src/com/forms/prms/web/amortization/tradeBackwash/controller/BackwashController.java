package com.forms.prms.web.amortization.tradeBackwash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.amortization.tradeBackwash.domain.BackwashBean;
import com.forms.prms.web.amortization.tradeBackwash.service.BackwashService;
import com.forms.prms.web.monthOver.service.MonthOverService;
import com.forms.prms.web.sysmanagement.parameter.service.ParameterService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/amortization/tradeBackwash")
public class BackwashController {
	private static final String FUNCTION ="amortization/tradeBackwash/";
	@Autowired
	private BackwashService bService;
	@Autowired
	private MonthOverService monthOverService;
	@Autowired
	private ParameterService pService;
	/**
	 * 获取可回冲的合同列表 -- 040601
	 * @return
	 */
	@RequestMapping("getBackWashList.do")
	@AddReturnLink(id="getBackWashList",displayValue="返回回冲列表")
	public String getBackWashList(BackwashBean bean){
		boolean flag3 = bService.getTaskTypeStatus();//当前正在执行的任务是否为预提待摊任务
		boolean flag4 = bService.isSysYYYYMM();//且任务时间是否为系统当前年月
		boolean flag0 = "0".equals(monthOverService.getMaxDataFlag());
		boolean flag1 = bService.getProPreStatus();//检查预提待摊状态（是否存在处理失败或者未发生预提待摊数据）
		boolean flag2 = bService.check33Upload();
		if(flag0 && flag1 && flag2 && flag3 & flag4){
		WebUtils.setRequestAttr("cntList", bService.getBackWashList(bean));
		WebUtils.setRequestAttr("bean", bean);
		WebUtils.setRequestAttr("deadlineDay", pService.getDeadlineDay());
		WebUtils.setRequestAttr("deadlineTime", pService.getDeadlineTime());
		return FUNCTION + "getBackWashList";
		}else if(!flag3){
			WebUtils.getMessageManager().addInfoMessage("当前执行任务不是预提待摊任务，不允许进行回冲操作！");
			return ForwardPageUtils.getErrorPage();
		}else if(!flag4){
			WebUtils.getMessageManager().addInfoMessage("预提待摊任务时间不是系统当前年月，不允许进行回冲操作！");
			return ForwardPageUtils.getErrorPage();
		}else if(!flag0){
			WebUtils.getMessageManager().addInfoMessage("不在月结状态！不允许进行预提待摊回冲。");
			return ForwardPageUtils.getErrorPage();
		}
		else if(!flag1){
			//校验了当月预提待摊任务是否开始。避免了当月初回冲上月预提待摊的情况
			WebUtils.getMessageManager().addInfoMessage("未开始当月预提待摊任务或存在校验失败的预提待摊记录需要处理！不允许进行预提待摊回冲。");
			return ForwardPageUtils.getErrorPage();
		}else if(!flag2){
			WebUtils.getMessageManager().addInfoMessage("存在预提待摊上送未回盘文件！不允许进行预提待摊回冲。");
			return ForwardPageUtils.getErrorPage();
		}else{
			return ForwardPageUtils.getErrorPage();
		}
		
	}
	
	/**
	 * 查询是否存在停留在预提复核状态的数据 -- 040602
	 * @param bean
	 * @return
	 */
	@RequestMapping("getTradeStatus.do")
	@ResponseBody
	public String getTradeStatus(BackwashBean bean){
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		String flag = bService.getTradeStatus(bean);
		if(flag!=null && "YES".equals(flag)){
			jsonObject.put("pass", false);
			jsonObject.put("info", "合同正在预提复核中，是否继续回冲操作？");
			CommonLogger.info("查询合同正在预提复核中！,BackwashController,getTradeStatus()");
		}
		else{
			jsonObject.put("pass", true);
			CommonLogger.info("查询合同不存在预提复核中的记录！,BackwashController,getTradeStatus()");
		}
		return jsonObject.writeValueAsString();
	}
	
	/**
	 * 回冲 -- 040603
	 * @param cntNumList
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("washback.do")
	public String washback(String cntNumList) throws Exception{
		String userId = WebHelp.getLoginUser().getUserId();
		bService.backWash(cntNumList,WebHelp.getLoginUser().getOrg1Code(),userId);
		CommonLogger.info("执行预提待摊回冲！,BackwashController,washback()");
		WebUtils.getMessageManager().addInfoMessage("预提待摊回冲完成！");
		return ForwardPageUtils.getSuccessPage();
	}
	
	/**
	 * 获取回冲历史列表 --040604
	 * @param bean
	 * @return
	 */
	@RequestMapping("washbackHistory.do")
	public String getBackWashHistory(BackwashBean bean){
		WebUtils.setRequestAttr("backWashBean",bean);
		WebUtils.setRequestAttr("bwHistory",bService.getBackWashHistory(bean));
		CommonLogger.info("获取预提待摊回冲历史列表！,BackwashController,getBackWashHistory()");
		return FUNCTION + "backWashHistory";
	}
	

}
