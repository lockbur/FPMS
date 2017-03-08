package com.forms.prms.web.amortization.abnormalDataMgr.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.prms.web.amortization.abnormalDataMgr.domain.CommonTidQueryBean;
import com.forms.prms.web.amortization.abnormalDataMgr.domain.TidAccountBean;
import com.forms.prms.web.amortization.abnormalDataMgr.domain.TidApInvoiceBean;
import com.forms.prms.web.amortization.abnormalDataMgr.domain.TidApPayBean;
import com.forms.prms.web.amortization.abnormalDataMgr.domain.TidCglAmtBean;
import com.forms.prms.web.amortization.abnormalDataMgr.domain.TidOrderBean;
import com.forms.prms.web.amortization.abnormalDataMgr.service.AbnorDataMgrService;

/**
 * Title:		AbnorDataMgrController
 * Description:	异常数据查询模块的Controller层
 * Coryright: 	formssi
 * @author：		HQQ
 * @project：	ERP
 * @date：		2015-04-08
 * @version：	1.0
 */
@Controller
@RequestMapping("/amortization/abnormalDataMgr")
public class AbnorDataMgrController {
	
	//当前Controller位于项目中的前置路径
	private final String BASE_URL = "amortization/abnormalDataMgr/";

	@Autowired
	private AbnorDataMgrService abnorDataService;
	
	/**
	 * @methodName abDataQuery
	 * 		异常数据查询功能：
	 * 			用户在前端页面选择需要查询异常数据的表(1.TID_ACCOUNT、2.TID_ACCOUNT、3.TID_ACCOUNT、4.TID_ACCOUNT、5.TID_ACCOUNT)，
	 * 			再选择需要查询表数据的异常状态(2：全状态、	1：正常状态、0：异常状态)进行数据的查询，查询得到的结果将会展示在该页面下方的<table>中
	 * @param request	request请求,用于取得前端页面对应的name=queryType和name=useFlag的值，作为查询条件的控制
	 * @return
	 */
	@RequestMapping("/abnormalDataQuery.do")
	public String abDataQuery( HttpServletRequest request , CommonTidQueryBean commonQueryBean){
		System.out.println("【HQQ-0504】:异常数据查询-传过来的查询条件：");
//		System.out.println(commonQueryBean.getBefDate()+"---"+commonQueryBean.getAftDate());
//		System.out.println("总账凭证【"+commonQueryBean.getQueryType()+"^^"+commonQueryBean.getUseFlag()+"^^"+commonQueryBean.getCntNum()+"^^"+commonQueryBean.getVoucherName()+"^^"+commonQueryBean.getDebitAmt()+"】");
//		System.out.println("AP发票【"+commonQueryBean.getQueryType()+"^^"+commonQueryBean.getUseFlag()+"^^"+commonQueryBean.getCntNum()+"^^"+commonQueryBean.getPayId()+"^^"+commonQueryBean.getInvoiceNo()+"^^"+commonQueryBean.getInvoiceAmt()+"】");
//		System.out.println("AP付款【"+commonQueryBean.getQueryType()+"^^"+commonQueryBean.getUseFlag()+"^^"+commonQueryBean.getCntNum()+"^^"+commonQueryBean.getPayId()+"^^"+commonQueryBean.getPayAmt()+"】");
//		System.out.println("科目余额【"+commonQueryBean.getQueryType()+"^^"+commonQueryBean.getUseFlag()+"^^"+commonQueryBean.getOrgName()+"^^"+commonQueryBean.getCglName()+"^^"+commonQueryBean.getProductName()+"^^"+commonQueryBean.getDebitAmt()+"】");
//		System.out.println("订单接口【"+commonQueryBean.getQueryType()+"^^"+commonQueryBean.getUseFlag()+"^^"+commonQueryBean.getCntNum()+"^^"+commonQueryBean.getPoNumber()+"^^"+commonQueryBean.getStockNum()+"^^"+commonQueryBean.getOrderId()+"】");
		
		String queryType  = request.getParameter("queryType");
		String useFlag = request.getParameter("useFlag");
		CommonLogger.debug("Testing:【查询到queryType+useFlag的值为】："+queryType+"--"+useFlag);
		if("2".equals(useFlag)){
			//如果前端页面的useFlag值为2，即查询全部(包括正常和异常)状态的数据
			useFlag = "";	//当为查询全部状态时，这里将传输到SQL的该条件设为空值
			commonQueryBean.setUseFlag(useFlag);
		}
		
		//用于传输SQL(Mapper)中查询数据的条件，
		Map<String,Object> mapObj = new HashMap<String,Object>();
		mapObj.put("queryMapObj", commonQueryBean);
		
		//根据查询条件[queryType]进行指定表的异常数据查询
		if("1".equals(queryType)){
			//查询总账凭证接口信息
//			TidAccountBean queryMapObj = (TidAccountBean)mapObj.get("queryMapObj");
			
			List<TidAccountBean> tidAccountList = abnorDataService.queryTidAccount(mapObj);
			WebUtils.setRequestAttr("tidAccountList", tidAccountList);
		}else if("2".equals(queryType)){
			//查询AP发票接口信息
			List<TidApInvoiceBean> tidApInvoiceList = abnorDataService.queryTidApInvoice(mapObj);
			WebUtils.setRequestAttr("tidApInvoiceList", tidApInvoiceList);
		}else if("3".equals(queryType)){
			//查询AP付款接口信息
			List<TidApPayBean> tidApPayList = abnorDataService.queryTidApPay(mapObj);
			WebUtils.setRequestAttr("tidApPayList", tidApPayList);
		}else if("4".equals(queryType)){
			//查询科目余额信息
			List<TidCglAmtBean> tidCglAmtList = abnorDataService.queryTidCglAmt(mapObj);
			WebUtils.setRequestAttr("tidCglAmtList", tidCglAmtList);
		}else if("5".equals(queryType)){
			//查询订单接口信息
			List<TidOrderBean> tidOrderList = abnorDataService.queryTidOrder(mapObj);
			WebUtils.setRequestAttr("tidOrderList", tidOrderList);
		}
		
		if("".equals(useFlag)){
			//如果查询的异常状态为"全部"，因为前面已经将该状态值设为空，所以需要为useFlag赋值"2",并给commonQueryBean的useFlag赋值
			useFlag = "2";
			commonQueryBean.setUseFlag(useFlag);
		}
		//将[查询类别]和[查询状态]的查询值set到前端页面，页面初始化时自动勾选赋值
		WebUtils.setRequestAttr("commonQueryBean", commonQueryBean);
//		WebUtils.setRequestAttr("queryType", queryType);
		
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		return BASE_URL + "abnormalDataQuery";
	}

	
	/**
	 * @methodName queryDataExport
	 * 		  异常数据查询的导出功能：
	 * 			用户在前端页面选择需要查询异常数据的表(1.TID_ACCOUNT、2.TID_ACCOUNT、3.TID_ACCOUNT、4.TID_ACCOUNT、5.TID_ACCOUNT)，
	 * 			再选择需要导出数据的异常状态(2：全状态、	1：正常状态、	0：异常状态)进行数据的查询，
	 * 			最后通过Excel导出组件和导出配置文件将查询数据写到相应的Excel模板进行查询数据的导出
	 * @param queryType		查询类别(下拉选择值分别对应上述5个表)
	 * @param useFlag	查询状态(下拉选择值分别对应上述表中的USE_FLAG字段，代表需要导出数据的异常状态)
	 * @return
	 */
	@RequestMapping("/queryAbnorDataExport.do")
	@ResponseBody
	public String queryDataExport( String queryType , String useFlag , CommonTidQueryBean commonQueryBean){
		CommonLogger.debug("Testing:【异常数据Controller:-queryDataExport前台获得数据】："+queryType+"--"+useFlag);
		System.out.println("【HQQ-0504-】:"+commonQueryBean.getQueryType()+"---"+commonQueryBean.getUseFlag()+"---"+commonQueryBean.getCntNum()+"----"+commonQueryBean.getDebitAmt());
		
		AbstractJsonObject json = new SuccessJsonObject();
		try {
			//Service层调用Excel组件进行Excel导出功能
			abnorDataService.dataExport(queryType , useFlag ,commonQueryBean);
			json.put("pass", true);
		} catch (Exception e) {
			json.put("pass", false);
			e.printStackTrace();
		}
		//将Excel导出处理结果发送到前台
		return json.writeValueAsString();
	}
	
	
	@RequestMapping("/ajaxToResetButton.do")
	@ResponseBody
	public String ajaxToResetButton(HttpServletRequest request,HttpServletResponse response){
		AbstractJsonObject json = new SuccessJsonObject();
//		WebUtils.removeRequestAttr("commonQueryBean");
		System.out.println("【测试前端通过Ajax删除request中的属性】："+request);
		request.removeAttribute("commonQueryBean");
//		WebUtils.setRequestAttr("commonQueryBean",null);
		json.put("result", "reset Successed");
		return json.writeValueAsString();
	}
	
}
