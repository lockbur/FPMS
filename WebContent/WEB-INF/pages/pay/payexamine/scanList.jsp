<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>扫描查询</title>
<script>
function pageInit()
{   
	App.jqueryAutocomplete();
	$("#cntType").combobox();
	$( "#payDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd",
	});
}
function resetAll(){
	$(":text").val("");
	$("select").val("");
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
	});
}
function selProvider(){
	var proObj = window.showModalDialog("<%=request.getContextPath()%>/sysmanagement/provider/searchProvider.do?<%=WebConsts.FUNC_ID_KEY %>=010710", null, "dialogHeight=600px;dialogWidth=1000px;center=yes;status=no;");
	$("#providerName").val(proObj.providerName);
}
function showDetail(payId,isPrePay,cntNum){
	var form = $("#queryDetailForm");
 	$("#queryDetailForm #payId1").val(payId);
	$("#queryDetailForm #isPrePay1").val(isPrePay);
	$("#queryDetailForm #cntNum1").val(cntNum);
 	form.attr('action', '<%=request.getContextPath()%>/pay/payexamine/detail.do?<%=WebConsts.FUNC_ID_KEY%>=03030502');
	App.submit(form);
}

function scan(batchNo,mainCnt,attachCnt){
	var url = "<%=request.getContextPath()%>/common/pay/scan/preadd.do?batchNo="+batchNo+"&mainCnt="+mainCnt+"&attachCnt="+attachCnt;
	url = convertURL(url);
// 	 window.showModalDialog(url,
// 			'中行影像前端控件', "dialogHeight=600px;dialogWidth=1200px;center=yes;status=no;");
	 $.dialog.open(
				url,
				{
					padding:0,
					width: "90%",
					height: "100%",
					lock: true,
				    fixed: true,
					esc: true,
					resize:	false,
				    title:"付款扫描",
				    id:"dialogAdvCancelInfoDetail",
				    drag:false,
				    close: function(){
				    	var form = $("#tempForm");
				     	form.attr('action', '<%=request.getContextPath()%>/pay/payexamine/scanList.do?<%=WebConsts.FUNC_ID_KEY%>=03030901');
				    	App.submit(form);
					}		
				}
			)
}

function showList(batchNO){
	var form = $("#tempForm");
 	$("#batchNo").val(batchNO);
 	form.attr('action', '<%=request.getContextPath()%>/pay/payexamine/scanDetailList.do?<%=WebConsts.FUNC_ID_KEY%>=0303090103');
	App.submit(form);
}

//删除指定的扫描批次数据
function delIcmsBatchNo(icmsBatchNo){
	if(!confirm('是否确认删除此数据？')){
		return false;
	}
	var form = $("#tempForm");
 	$("#batchNo").val(icmsBatchNo);
 	form.attr('action', '<%=request.getContextPath()%>/pay/payexamine/delIcmsBatchNo.do?<%=WebConsts.FUNC_ID_KEY%>=0303090112');
	App.submit(form);
}

//扫描控件下载
function scanUpload(fileId){
	var form = $("#download")[0];
	var url='<%=request.getContextPath()%>/sysmanagement/uploadfilemanage/upFileDownload.do?<%=WebConsts.FUNC_ID_KEY%>=08090206';
	form.action = url + "&fileId="+fileId;
	form.submit();
}

//ajax检验是否可以新增
function checkCanAddScanBatch(){
	var data={};
	App.ajaxSubmit("pay/payexamine/ajaxCheckCanAddScanBatch.do?<%=WebConsts.FUNC_ID_KEY %>=0303090111",
			{data:data,async:false}, 
			function(data) {
				var result=data.flag;
				if(result == "N"){
					App.notyError("请将已有批次处理完毕后，才能新增扫描批次！"); 
				}else{
					var form = $("#tempForm")[0];
					var url='<%=request.getContextPath()%>/pay/payexamine/preScan.do?<%=WebConsts.FUNC_ID_KEY%>=0303090102';
					form.action = url;
					form.submit();
				}
			}
	);
}
</script>
</head>
<body>
<p:authFunc funcArray="03030901"/>
<form id="download" method="post" ></form>
<form  method="post" id="tempForm">
	<p:token/>
	<table id="condition">
		<tr class="collspan-control">
			<th colspan="4">
				扫描查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft">批次号</td>
			<td class="tdRight">
				<input type="text" id="batchNo" name="batchNo" value="${selectInfo.batchNo}" class="base-input-text" maxlength="50" />
			</td>
			<td class="tdLeft">付款单号</td>
			<td class="tdRight">
				<input type="text" id="payId" name="payId" value="${selectInfo.payId}" class="base-input-text" maxlength="50" />
			</td>
			
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<%-- <p:button funcId="0303050802" value="增加"/> --%>
				<input type="button" value="增加" onclick="checkCanAddScanBatch()"/>
				<!-- <input type="button" value="扫描控件下载" onclick="scanUpload('ICMS_SCAN_PLUGIN')"/> -->
				<p:button funcId="03030901" value="查询"/> 
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br/>
<div id="tabs" style="border: 0;">
	<div id="tabs-1" style="padding: 0;">
		<table class="tableList">
			<tr>    
			 		<th width="20%" rowspan="2">批次号</th>
					<th width="16%" colspan="2">预期</th>
					<th width="16%" colspan="2">实际</th>
					<th width="18%" rowspan="2">扫描时间</th>
					<th width="12%" rowspan="2">状态</th>
					<th width="18%" rowspan="2">操作</th>
			</tr>
			<tr>    
					<th width="8%">主件页数</th>
					<th width="8%">总页数</th>
					<th width="8%">主件页数</th>
					<th width="8%">总页数</th>
			</tr>
			 <c:forEach items="${list}" var="sedList">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
					<td><c:out value="${sedList.batchNo}"/></td>
					<td><c:out value="${sedList.mainCnt}"/></td>
					<td><c:out value="${sedList.attachCnt}"/></td>
					<td><c:out value="${sedList.mainCntOk}"/></td>
					<td><c:out value="${sedList.attachCntOk}"/></td>
					<td><c:out value="${sedList.instDate} ${sedList.instTime}"/></td>
					<td><c:out value="${sedList.dataFlagName}"/></td>
					<td>
					<c:if test="${sedList.dataFlag eq '00' || sedList.dataFlag eq '01'}">					
					<input type="button" onclick="scan('${sedList.batchNo}','${sedList.mainCnt}','${sedList.attachCnt}')" value="扫描"/>
					<input type="button" onclick="delIcmsBatchNo('${sedList.batchNo}')" value="删除"/>
					</c:if>
					<c:if test="${sedList.dataFlag != '00'}">
					<input type="button" onclick="showList('${sedList.batchNo}')" value="明细"/>
					</c:if>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${empty list}">
					<tr><td style="text-align: center;" colspan="10"><span class="red">没有找到相关信息</span></td></tr>
			</c:if>
		</table>
	</div>
</div>
</form>
<p:page />
</body>
</html>