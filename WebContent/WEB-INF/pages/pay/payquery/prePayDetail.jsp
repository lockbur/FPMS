<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.WebUtils"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预付款明细</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/contract.query.common.js"></script>
<script type="text/javascript">
//查询付款审批历史记录(弹窗显示)
function queryHis(payId){
	var url = "<%=request.getContextPath()%>/pay/payAdd/queryHis.do?<%=WebConsts.FUNC_ID_KEY%>=03030321&payId="+payId;
// 	App.submitShowProgress();
// 	window.showModalDialog(url,null,"dialogHeight=500px;dialogWidth=800px;center=yes;status=no;");
// 	App.submitFinish();
	$.dialog.open(
			url,
			{
				width: "60%",
				height: "80%",
				lock: true,
			    fixed: true,
			    title:"查看流转日志",
			    id:"dialogCutPage",
				close: function(){
					}		
			}
		 );
}

function printFace(payId,cntNum,isPrePay,isOrder){
	var url = "<%=request.getContextPath()%>/pay/payquery/print.do?<%=WebConsts.FUNC_ID_KEY%>=03030708&payId="+payId+"&cntNum="+cntNum+"&isPrePay="+isPrePay+"&isOrder="+isOrder;
	$.dialog.open(
			url,
			{
				width: "70%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"打印封面",
			    id:"dialogCutPage"
			}
		 );
}
//付款明细22文件
function queryPay22Detail(invoiceId){
	var url = "<%=request.getContextPath()%>/pay/payquery/queryPay22Detail.do?<%=WebConsts.FUNC_ID_KEY%>=03030709&invoiceId="+invoiceId;
	$.dialog.open(
		url,
		{
			width: "60%",
			height: "80%",
			lock: true,
		    fixed: true,
		    title:"查看付款记录",
		    id:"queryPay22Detail",
			close: function(){
				
			}		
		}
	 );
}

//导出信息
function exportData(){
	var isPass=true;
	var data = {};
 	data['orgFlag'] =  "${payInfo.orgFlag}";
 	data['payId'] =  "${payInfo.payId}";
	App.ajaxSubmit("pay/payquery/PrePayexportData.do?<%=WebConsts.FUNC_ID_KEY %>=03030715",
			{data:data,async : false},
			function(data){
				flag = data.pass;
				if(flag){
					$("<div>Excel已成功提交后台处理<br\>是否进入下载页面？</div>").dialog({
						resizable: false,
						width: 290,
						height:'auto',
						modal: true,
						dialogClass: 'dClass',
						title: "跳转至下载页面",
						buttons:[
						         {
						        	 text:"确认",
										click:function(){
							        	 	var form = document.forms[0];
							        	 	var upStepUrl = '${currentUri}';				//当前页面的uri(无项目根路径)；	可在Controller层添加代码：WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
							        		var upStepParams = '';							//当前页面需要的过滤参数(当无参数时为空)
							        		$(form).append("<input type='hidden' name='upStepUrl' id='upStepUrl'>");			//为提交跳转的form添加input框记录传递参数upStepUrl(用于查询FuncId)
							        		$(form).append("<input type='hidden' name='upStepParams' id='upStepParams'>");	//为提交跳转的form添加input框记录传递参数upStepParams(用于当当前页面有查询过滤参数时)
							        		$("form #upStepUrl").val(upStepUrl);											//添加传递参数的值
							        		$("form #upStepParams").val(upStepParams);
											form.action='<%=request.getContextPath()%>/reportmgr/importAndexport/query/getExportReportList.do?<%=WebConsts.FUNC_ID_KEY%>=060101&funcId=010302';
											App.submit(form);
										}   
						         },
								{
									text:"取消",
									click:function(){
										$(this).dialog("close");
									}
								}
						]
					});
					isPass =  true;
				}
				else
				{
					App.notyError("添加下载失败，可能是因为表里没有该条数据，请检查后重试!");
					isPass =  false;
				}
			});
	return isPass;
}
 
</script>
</head>
<body>
<form action="" id="print" method="post"></form>
		<jsp:include page="prePayDetailCommon.jsp"></jsp:include>
		<br/>
		<div style="text-align:center;" >
			<input type="button" value="查看合同扫描文件" onclick="viewCntScanImg('${cnt.id}','${cnt.icmsPkuuid }')"/>
			<c:if test="${!empty payInfo.id}">
				<input type="button" value="查看付款扫描文件" onclick="viewPayScanImg('${payInfo.payId}','${payInfo.icmsPkuuid}')"/>
			</c:if>
			<input type="button" value="查询审批历史记录" onclick="queryHis('${payInfo.payId}');">
			<c:if test="${payInfo.dataFlag == 'C0' or  payInfo.dataFlag=='C1'}">
			<input type="button" value="打印封面" onclick="printFace('${payInfo.payId}','${payInfo.cntNum}','${payInfo.isPrePay}','${payInfo.isOrder}');">
			</c:if>
			<input type="button" value="查询付款明细" onclick="queryPay22Detail('${payInfo.invoiceId}');">
			<input type="button" value="导出付款信息" onclick="exportData();">
			<input type="button" value="返回" onclick="backToLastPage('${uri}');">
		</div>	
</body>
</html>