package com.forms.prms.web.contract.end.controller;

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
import com.forms.prms.web.contract.end.domain.EndForm;
import com.forms.prms.web.contract.end.service.EndService;
import com.forms.prms.web.contract.query.service.ContractQueryService;
import com.forms.prms.web.util.ForwardPageUtils;

/**
 * 合同终止
 * 
 * 查看合同列表：查询合同状态为'20'(合同确认完成)，以及付款责任中心为用户所在 责任中心的数据信息。
 * 
 * 合同取消：将合同状态更改为'30'(合同终止)，将终止操作备注存入流水表。
 * 			将占用的项目预算中未付款的部分预算释放掉。
 * 			（待定）物料部分的预算占用释放规则。
 * 
 * 合同完成(终止)：将合同状态更改为'32'(合同完成终止)，将完成操作备注存入流水表。
 * 
 * @author LinJia
 * @date 2015-01-28
 * 
 */

@Controller
@RequestMapping("/contract/end")
public class EndController {
	@Autowired
	private EndService eService;
	@Autowired
	private ContractQueryService qService;

	private static final String TO = "contract/end/";

	/**
	 * 合同完成列表 -- 03020702
	 * 
	 * @param e
	 * @return
	 */
	@RequestMapping("list.do")
	@AddReturnLink(id="EndList",displayValue="返回列表")
	public String list(EndForm e) {
		WebUtils.setRequestAttr("eList", eService.list(e));
		WebUtils.setRequestAttr("e", e);
		return TO + "list";
	}

	/**
	 * 合同取消列表 -- 03020701
	 * 
	 * @param e
	 * @return
	 *//*
	@RequestMapping("cancelList.do")
	@AddReturnLink(id="cancelList",displayValue="返回列表")
	public String cancelList(EndForm e) {
		WebUtils.setRequestAttr("eList", eService.cancelList(e));
		WebUtils.setRequestAttr("e", e);
		return TO + "cancelList";
	}*/
	
	@RequestMapping("org1List.do")
	public String org1List(EndForm con) {
		con.setOrgFlag("1");//省行
		return contractQuery(con);
	}
	
	@RequestMapping("org2List.do")
	public String org2List(EndForm con) {
		con.setOrgFlag("2");//二级行
		return contractQuery(con);
	}
	
	@RequestMapping("dutyCodeList.do")
	public String dutyCodeList(EndForm con) {
		con.setOrgFlag("3");//业务部门
		return contractQuery(con);
	}
	
	@RequestMapping("queryList.do")
	public String contractQuery(EndForm con)
	{
		ReturnLinkUtils.addReturnLink("cntList", "返回列表");
		WebUtils.setRequestAttr("eList", eService.cancelList(con));
		WebUtils.setRequestAttr("e", con);
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		return TO + "cancelList";
	}
	
	/**
	 * 合同完成详情 -- 03020704
	 * 
	 * @param e
	 * @return
	 */
	@RequestMapping("view.do")
	public String view(EndForm e) {
		WebUtils.setRequestAttr("cnt", eService.view(e.getCntNum()));
		WebUtils.setRequestAttr("nowDate", Tool.DATE.getDate());
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/contract/end/list.do?VISIT_FUNC_ID=03020702");
		return TO + "view";
	}

	/**
	 * 合同取消详情 -- 03020703
	 * 
	 * @param e
	 * @return
	 */
	@RequestMapping("cancelView.do")
	public String cancelView(EndForm e) {
		WebUtils.setRequestAttr("cnt", qService.getDetailCheck(e.getCntNum()));
		WebHelp.setLastPageLink("uri", "cntList");
		return TO + "cancelView";
	}
	
	/**
	 * @methodName checkToEnd
	 * desc  校验合同取消时退款是否完成
	 * 
	 * @param cntNum
	 * @return
	 */
	@RequestMapping("checkToEnd.do")
	@ResponseBody
	public String checkToEnd(String cntNum){
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		if(eService.getEndAmt(cntNum)){
			jsonObject.put("pass", true);
		}else{
			jsonObject.put("pass", false);
		}
		return jsonObject.writeValueAsString();
	}
	
	/**
	 * 合同取消 --0302070102
	 * 
	 * @param e
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("cancel.do")
	public String cancel(EndForm e) throws Exception {
		ReturnLinkUtils.setShowLink("EndList");
		if (eService.end(e) > 0) {
			WebUtils.getMessageManager().addInfoMessage("合同取消成功！");
			return ForwardPageUtils.getSuccessPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("合同取消失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}

	/**
	 * 完成 --0302070201
	 * 
	 * @param e
	 * @return
	 */
	@PreventDuplicateSubmit
	@RequestMapping("finish.do")
	public String finish(EndForm e) {
		ReturnLinkUtils.setShowLink("EndList");
		if (eService.finish(e) > 0) {
			WebUtils.getMessageManager().addInfoMessage("合同终止成功！");
			return ForwardPageUtils.getSuccessPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("合同终止失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}

}
