package com.forms.prms.web.amortization.unnormalDataMgr.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.web.amortization.unnormalDataMgr.domain.UnnorDataMgrBean;
import com.forms.prms.web.amortization.unnormalDataMgr.service.UnnorDataMgrService;
import com.forms.prms.web.util.ForwardPageUtils;

/**
 * Title:		UnnorDataMgrController
 * Description:	异常数据查询模块的Controller层
 * Coryright: 	formssi
 * @author：		
 * @project：	ERP
 * @date：		2015-07-02
 * @version：	1.0
 */
@Controller
@RequestMapping("/amortization/fmsMgr")
public class UnnorDataMgrController {
	
	//当前Controller位于项目中的前置路径
	private final String BASE_URL = "amortization/unnormalDataMgr/";

	@Autowired
	private UnnorDataMgrService unnorDataService;
	
	
	@RequestMapping("/unnormalDataQuery.do")
	public String abDataQuery( HttpServletRequest request , UnnorDataMgrBean unnorDataMgrBean){
		ReturnLinkUtils.addReturnLink("unnormalDataQuery", "返回");
		String queryType  = request.getParameter("queryType");
		
		//用于传输SQL(Mapper)中查询数据的条件，
		Map<String,Object> mapObj = new HashMap<String,Object>();
		mapObj.put("queryMapObj", unnorDataMgrBean);
		
		//根据查询条件[queryType]进行指定表的异常数据查询
		if("1".equals(queryType)){
			//查询总账凭证接口信息
//			TidAccountBean queryMapObj = (TidAccountBean)mapObj.get("queryMapObj");
			
			List<UnnorDataMgrBean> orderCancelList = unnorDataService.getOrderCancelList(mapObj);
			WebUtils.setRequestAttr("orderCancelList", orderCancelList);
		}
		
		//将[查询类别]和[查询状态]的查询值set到前端页面，页面初始化时自动勾选赋值
		WebUtils.setRequestAttr("unnorDataMgrBean", unnorDataMgrBean);
//		WebUtils.setRequestAttr("queryType", queryType);
		
		return BASE_URL + "unnormalDataQuery";
	}
	
	@RequestMapping("/unnormalDataUpdate.do")
	public String unnormalDataUpdate(UnnorDataMgrBean unnorDataMgrBean) {
		boolean returnResult = unnorDataService.unnormalDataUpdate(unnorDataMgrBean);
		ReturnLinkUtils.setShowLink("unnormalDataQuery");
		if(returnResult)
		{
			WebUtils.getMessageManager().addInfoMessage("更新成功");
			return ForwardPageUtils.getSuccessPage();
		}
		else
		{
			WebUtils.getMessageManager().addInfoMessage("更新失败");
			return ForwardPageUtils.getErrorPage();
		}
		
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
