package com.forms.prms.web.contract.query.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.contract.query.domain.QueryContract;
import com.forms.prms.web.contract.query.domain.WaterBook;
import com.forms.prms.web.contract.query.service.ContractQueryService;
import com.forms.prms.web.pay.orderquery.domain.OrderQueryBean;
import com.forms.prms.web.pay.orderquery.service.OrderQueryService;
import com.forms.prms.web.pay.orderstart.domain.OrderStartBean;
import com.forms.prms.web.pay.orderstart.service.OrderStartService;
import com.forms.prms.web.sysmanagement.montAprvBatch.export.service.ExportService;

@Controller
@RequestMapping("/contract/query")
/**
 * Title:ContractQueryController
 * Description:查询合同列表及明细
 * 业务逻辑：合同列表查询（条件：合同号、合同类型（ 0-资产类 1-费用类）、供应商（根据名字可模糊查询）、签订日期区间、创建机构），
 * 		     	可查询范围为：创建责任中心=责任中心 或者 付款责任中心=责任中心 的合同
 * 		      合同详情：根据合同号查询相关的基础信息、物料信息及费用信息
 * Coryright: formssi
 * @author liys
 * @project ERP
 * @date 2015-01-23
 * @version 1.0	
 */
public class ContractQueryController {

	private static final String PREFIX = "contract/query/";

	@Autowired
	private ContractQueryService service;

	@Autowired
	private OrderQueryService orderQueryService;
	
	@Autowired
	private OrderStartService orderStartService;
	@Autowired
	private ExportService exportService;
	
	@RequestMapping("org1List.do")
	public String org1List(QueryContract con) {
		con.setOrgFlag("1");//省行
		return contractQuery(con);
	}
	
	@RequestMapping("org2List.do")
	public String org2List(QueryContract con) {
		con.setOrgFlag("2");//二级行
		return contractQuery(con);
	}
	
	@RequestMapping("dutyCodeList.do")
	public String dutyCodeList(QueryContract con) {
		con.setOrgFlag("3");//业务部门
		return contractQuery(con);
	}
	
	@RequestMapping("queryList.do")
	public String contractQuery(QueryContract con)
	{
		ReturnLinkUtils.addReturnLink("cntList", "返回列表");
		WebUtils.setRequestAttr("cntList", service.queryList(con));
		WebUtils.setRequestAttr("con", con);
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		return PREFIX + "querylist";
	}
	
	
	@RequestMapping("mainpage.do")
	public String mainpage(String cntNum) 
	{
		QueryContract cnt = service.getDetail(cntNum);
		WebUtils.setRequestAttr("cnt", cnt);
		return PREFIX + "mainpage";
	}

	@RequestMapping("cntDtl.do")
	public String ContractDetail(String cntNum) {

		QueryContract cnt = service.getDetailCheck(cntNum);
		WebUtils.setRequestAttr("cnt", cnt);
		String isChocie=WebUtils.getRequest().getParameter("isChoice");
		String isCheck=WebUtils.getRequest().getParameter("isCheck");
		//如果为合同勾选的返回
		if(isChocie!=null&&isChocie!=""){
			if("01".equals(WebUtils.getRequest().getParameter("orgType"))){
				String batchNo = WebUtils.getRequest().getParameter("batchNo");
				WebUtils.setRequestAttr("orgType", WebUtils.getRequest().getParameter("orgType"));
				WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/montAprvBatch/cntchoice/choiceDetail.do?VISIT_FUNC_ID=0811040101&orgType=01&batchNo="+batchNo+"&isCheck="+isCheck);
			}else if("02".equals(WebUtils.getRequest().getParameter("orgType"))){
				String batchNo = WebUtils.getRequest().getParameter("batchNo");
				WebUtils.setRequestAttr("orgType", WebUtils.getRequest().getParameter("orgType"));
				WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/montAprvBatch/cntchoice/choiceDetail.do?VISIT_FUNC_ID=0811040201&orgType=02&batchNo="+batchNo+"&isCheck="+isCheck);
			}else{
				WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/contract/query/queryList.do?VISIT_FUNC_ID=030206");
			}
		}
		else{
			if("01".equals(WebUtils.getRequest().getParameter("orgType"))){
				String batchNo = WebUtils.getRequest().getParameter("batchNo");
				WebUtils.setRequestAttr("orgType", WebUtils.getRequest().getParameter("orgType"));
				WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/montAprvBatch/apprv/cntChooseList.do?VISIT_FUNC_ID=0811030107&orgType=01&batchNo="+batchNo);
			}else if("02".equals(WebUtils.getRequest().getParameter("orgType"))){
				String batchNo = WebUtils.getRequest().getParameter("batchNo");
				WebUtils.setRequestAttr("orgType", WebUtils.getRequest().getParameter("orgType"));
				WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/montAprvBatch/apprv/cntChooseList.do?VISIT_FUNC_ID=0811030207&orgType=02&batchNo="+batchNo);
			}else{
				WebHelp.setLastPageLink("uri", "cntList");
//				WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/contract/query/queryList.do?VISIT_FUNC_ID=030206");
			}
		}
		return PREFIX + "cntDtl";
	}
	
	@RequestMapping("addDownLoad.do")
	@ResponseBody
	public String addDownload(QueryContract con) throws Exception{
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		String taskId = "";
		try{			
			taskId = service.queryDownloadList(con);
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
	
	/**
	 * @methodName queryFeeType
	 * desc 查询受益费用页面  
	 * 
	 * @param con
	 * @return
	 */
	@RequestMapping("queryFeeTypePage.do")
	public String queryFeeType(String cntNum){
		WebUtils.setRequestAttr("queryFeeTypeList", service.queryFeeType(cntNum));
		return PREFIX + "queryFeeTypePage";
	}
	
	/**
	 * @methodName book
	 * desc 查看操作日志
	 * 
	 * @param cntNum
	 * @return
	 */
	
	@RequestMapping("book.do")
	public String book(String cntNum){
		List<WaterBook> list=new ArrayList<WaterBook>();
		list=service.book(cntNum);
		WebUtils.setRequestAttr("wblist",list);
		WebHelp.setLastPageLink("uri", "cntList");
//		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/contract/query/queryList.do?VISIT_FUNC_ID=030206");
		return PREFIX+"book";
	}
	
	/**
	 * @methodName queryOrder
	 * desc  查询订单信息  
	 * 
	 * @param cntNum
	 * @return
	 */
	@RequestMapping("queryOrder.do")
	public String queryOrder(String cntNum){
//		WebUtils.setRequestAttr("contracturi", WebUtils.getRequest().getContextPath()+"/contract/query/queryList.do?VISIT_FUNC_ID=030206");
		WebHelp.setLastPageLink("contracturi", "cntList");
		WebUtils.setRequestAttr("orderList", service.queryOrderPage(cntNum)); 
		WebUtils.setRequestAttr("flag", WebUtils.getParameter("flag")); 
		return PREFIX+"queryOrderPage";
	}
	
	@RequestMapping("getOrderInfo.do")
	public String getOrderInfo(String orderId,String flag){
		OrderQueryBean order = new OrderQueryBean();
		order.setOrderId(orderId);
		order = orderQueryService.getInfo(order.getOrderId());
		WebUtils.setRequestAttr("orderInfo", order);
		//查询该订单号下的所有物料
		List<OrderStartBean> devList=orderStartService.devList(order.getOrderId());
		WebUtils.setRequestAttr("devList", devList);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/contract/query/queryOrder.do?VISIT_FUNC_ID=03020605&cntNum="+order.getCntNum()+"&flag="+flag);
		return PREFIX+"orderInfo";
	}
	
	@RequestMapping("gotoCntDtl.do")
	public String gotoCntDtl(String cntNum) {
		QueryContract cnt = service.getDetailCheck(cntNum);
		WebUtils.setRequestAttr("cnt", cnt);
		return PREFIX+"gotoCntDtl";
	}
	/**
	 * 信息导出
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/exportData.do")
	@ResponseBody
	public String exportData(QueryContract bean){
		AbstractJsonObject jsonObject = new SuccessJsonObject();		
		//Excel导出操作
		String taskId = null;
		try {
			taskId = service.exportData(bean);
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
	
		
	@RequestMapping("szCntMatrList.do")
	public String szCntMatrList(QueryContract con) {
		return szContractQuery(con);
	}
	
	@RequestMapping("szQueryList.do")
	public String szContractQuery(QueryContract con)
	{
		// 得到系统日期
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		String date = df.format(new Date());
		if (con.getAftDate() == null || con.getAftDate() == "") {
			con.setAftDate(date);
		}
		if (con.getBefDate() == null || con.getBefDate() == "") {
			con.setBefDate(date);
		}
				
		ReturnLinkUtils.addReturnLink("cntList", "返回列表");
		WebUtils.setRequestAttr("dataFlag", service.selectCntDataFlag());
		String[] temp = {"20","21","25","30","32","35","40"};
		if(con.getDataFlags()==null){
			con.setDataFlags(temp);
		}
		if("Y".equals(con.getCheckedFlag())){
			WebUtils.setRequestAttr("dataFlagChecked",  Arrays.asList(con.getDataFlags()));
		}else{
			WebUtils.setRequestAttr("dataFlagChecked", Arrays.asList(temp) );
			
			con.setDataFlags(temp);
			
		}
		WebUtils.setRequestAttr("cntList", service.szQueryList(con));
		WebUtils.setRequestAttr("con", con);
		WebUtils.setRequestAttr("sysDate", date);
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		return PREFIX + "szQuerylist";
	}
	
	/**
	 * 深圳合同数据信息导出
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/szCntMatrExport.do")
	@ResponseBody
	public String szCntMatrExport(QueryContract con) throws Exception{
		
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		String taskId = "";
		try{			
			taskId = service.szQueryDownloadList(con);
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
