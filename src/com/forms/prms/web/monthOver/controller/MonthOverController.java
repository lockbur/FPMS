package com.forms.prms.web.monthOver.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.fms.FmsGeneral;
import com.forms.prms.web.amortization.fmsMgr.domain.FmsMgr;
import com.forms.prms.web.monthOver.domain.MonthOverBean;
import com.forms.prms.web.monthOver.service.MonthOverService;
import com.forms.prms.web.util.ForwardPageUtils;

/**
 * 
 * @author Luo Yunlai
 * 
 */

@Controller
@RequestMapping("/monthOver/")
public class MonthOverController {
	private static final String PATH = "monthOver/";

	@Autowired
	private MonthOverService monthOverService;
	@Autowired
	private FmsGeneral fmsGeneral;

	/**
	 * 月结列表 - 030601
	 * 
	 * @param mb
	 * @return
	 */
	@RequestMapping("list.do")
	public String list(MonthOverBean mb) {
		ReturnLinkUtils.addReturnLink("list", "返回");
		if(!monthOverService.checkOpenOrg1()){
			WebUtils.getMessageManager().addInfoMessage("系统基础参数未初始化一级行，不能执行操作！请联系管理员！");
			return ForwardPageUtils.getErrorPage();
		}
		List<MonthOverBean> mbList = monthOverService.list(mb);
		if (mbList.size()==0) {
			monthOverService.insert(mb);
			mbList=monthOverService.list(mb);
		}
		String maxDataFlag = monthOverService.getMaxDataFlag();
		WebUtils.setRequestAttr("max", maxDataFlag);
		WebUtils.setRequestAttr("list", mbList);
		return PATH + "list";
	}

	/**
	 * 月结 - 03060101
	 * 
	 * @param mb
	 * @return
	 */

	@RequestMapping("change.do")
	public String change(MonthOverBean mb) {

		if ("once".equals(mb.getType())) {
			return subChange(mb);
		} else {
			String maxDataFlag = monthOverService.getMaxDataFlag();
			boolean flag = "1".equals(maxDataFlag);//1-月结结束
			try {
				if (!flag) {
					WebUtils.getMessageManager().addInfoMessage("月结中，不能发起付款！");
					return ForwardPageUtils.getErrorPage();
				} else {
					fmsGeneral.dealPayOrder(WebHelp.getLoginUser().getUserId(),WebHelp.getLoginUser().getOrg1Code(),"mo");
					return subChange(mb);
				}
			} catch (Exception e) {
				e.printStackTrace();
				CommonLogger.error("月结前应付发票、预付款核销及po订单数据上传FMS触发失败！");
				WebUtils.getMessageManager().addInfoMessage("月结前应付发票、预付款核销及po订单数据文件上传FMS失败！");
				return ForwardPageUtils.getErrorPage();

			}

		}
	}

	/**
	 * 修改月结状态
	 * @param mb
	 * @param type
	 * @return
	 */
	private String subChange(MonthOverBean mb) {
		String msg = monthOverService.change(mb);

		if ("ok".equals(msg)) {
			WebUtils.getMessageManager().addInfoMessage("月结成功!");
			ReturnLinkUtils.setShowLink("list");
			return ForwardPageUtils.getSuccessPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("月结失败!");
			ReturnLinkUtils.setShowLink("list");
			return ForwardPageUtils.getErrorPage();
		}
	}

	/**
	 * 检查应附发票未上传的记录 --03060102
	 * 
	 * @return
	 */
	@RequestMapping("checkPayStatus.do")
	@ResponseBody
	public String checkPayStatus() {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		FmsMgr fmsMgr = monthOverService.getUnUploadRecode();
		boolean flag = fmsMgr.getPayCnt().compareTo(new BigDecimal(0)) > 0 ? true : false;
		if (flag) {
			jsonObject.put("pass", false);
			jsonObject.put("payCnt", fmsMgr.getPayCnt());
		} else {
			jsonObject.put("pass", true);
		}
		return jsonObject.writeValueAsString();

	}
	
	/**
	 * 异步校验月结状态 --03060103
	 * @return
	 */
	@RequestMapping("ajaxCheckMonthStatus.do")
	@ResponseBody
	public String ajaxCheckMonthStatus(){
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		boolean flag0 = "0".equals(monthOverService.getMaxDataFlag());
		if(!flag0){
			jsonObject.put("pass", false);
		}else{
			jsonObject.put("pass", true);
		}
		return jsonObject.writeValueAsString();
		
	}
	
    /**
     * 校验冲销任务状态 --03060104
     * @return
     */
	@RequestMapping("ajaxCheckProvision.do")
	@ResponseBody
	public String ajaxCheckProvision(){
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		boolean flag = monthOverService.ajaxCheckProvision();
		if (flag) {
			jsonObject.put("pass", true); //通过
		} else {
			jsonObject.put("pass", false);
		}
		return jsonObject.writeValueAsString();
	}
	
	/**
	 * 查看全国各一级行的月结状态 --030602
	 * @return
	 */
	@RequestMapping("getAllMonthOverFlag.do")
	public String getAllMonthOverFlag(MonthOverBean bean){
		ReturnLinkUtils.addReturnLink("getAllMonthOverFlag", "返回");
		
		// 得到系统日期
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");// 设置日期格式
		String date = df.format(new Date());
		// 得到系统日期
		if(bean.getMonth()== null || bean.getMonth() == ""){
			bean.setMonth(date);
		}
		List<MonthOverBean> mbList = monthOverService.getAllMonthOverFlag(bean);
		WebUtils.setRequestAttr("mbList", mbList);
		WebUtils.setRequestAttr("bean", bean);
		return PATH + "alllist";
	}
}
