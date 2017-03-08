<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FMS下载</title>
<script type="text/javascript">
function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	$("#dataFlag").combobox();
	$("#tradeType").combobox();
	
	//设置时间插件
	$( "#startDate,#endDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
}

function resetAll() {
	$("select").val("");
	$(":text").val("");
	$(":selected").prop("selected",false);
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
	});
}

//文件下载
function downloadFile(batchNo){
	var form = $("#downloadForm")[0];
	var url = '<%=request.getContextPath()%>/amortization/fmsMgr/fmsFileDownload.do?<%=WebConsts.FUNC_ID_KEY%>=04030201';
	form.action = url + "&batchNo="+batchNo+ "&fileFlag=0";
	form.submit();
}

//补做
function mendFmsDownLoad(batchNo,tradeType){
	var form = $("#downloadForm")[0];
	var url = '<%=request.getContextPath()%>/amortization/fmsMgr/mendFmsDownload.do?<%=WebConsts.FUNC_ID_KEY%>=04030301';
	form.action = url + "&batchNo="+batchNo+"&tradeType="+tradeType;
	form.submit();
}
function querLog(batchNo,tradeType){
	var url = '<%=request.getContextPath()%>/amortization/fmsMgr/querLog.do?<%=WebConsts.FUNC_ID_KEY%>=04030302';
	url += "&batchNo="+batchNo+"&tradeType="+tradeType;
	$.dialog.open(
			url,
			{
				width: "50%",
				height: "60%",
				lock: true,
			    fixed: true,
			    title:"处理失败信息",
			    id:"dialogCutPage",
				close: function(){
					
				}
			}
	);
}

function doValidate(){
	if(!$.checkDate("startDate","endDate")){
		return false;
	}
	return true;
}
function showDetail(batchNo,tradeType){
	var url = '<%=request.getContextPath()%>/amortization/fmsMgr/querDetail.do?<%=WebConsts.FUNC_ID_KEY%>=04030303';
	url += "&batchNo="+batchNo+"&tradeType="+tradeType;
	$.dialog.open(
		url,
		{
			padding:0,
			width: "60%",
			height: "60%",
			lock: true,
		    fixed: true,
			resize:	false,
			esc: true,
		    title:"明细",
		    id:"showDetail",
		    close: function(){
			}		
		}
	)
}
function fmsDownloadFile(filePath){
	var form = $("#downloadForm")[0];
	var url = '<%=request.getContextPath()%>/amortization/fmsMgr/fmsDownloadFile.do?<%=WebConsts.FUNC_ID_KEY%>=04030304&filePath='+filePath;
	form.action = url;
	form.submit();
}
</script>
</head>

<body>
<p:authFunc funcArray="040303,04030201"/>
<form id="downloadForm" method="post"></form>
<form method="post" id="queryForm" action="">
	<table>
		<tr class="collspan-control">
			<th colspan="4">fms下载查询</th>
		</tr>
		<tr>
			<td class="tdLeft">交易类型</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="tradeType" name="tradeType">
						<option value="">--请选择--</option>						
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
						 conditionStr="CATEGORY_ID = 'DOWNLOAD_TRADE_TYPE'"
						 orderType="ASC"  selectedValue="${fmsDownload.tradeType }"/>
					</select>
				</div>
			</td>
			<td class="tdLeft">交易日期</td>
			<td class="tdRight">
				<input type="text" id="startDate" name="startDate" class="base-input-text" style="width:35%;" readonly value="${startDateString}"/>&nbsp;至&nbsp;
				<input type="text" id="endDate" name="endDate" class="base-input-text" style="width:35%;" readonly value="${endDateString}"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">状态</td>
			<td class="tdRight" colspan="3">
				<div class="ui-widget">
					<select id="dataFlag" name="dataFlag">
						<option value="">--请选择--</option>						
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
						 conditionStr="CATEGORY_ID = 'DOWNLOAD_DATA_FLAG'"
						 orderType="ASC"  selectedValue="${fmsDownload.dataFlag}"/>
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="040303" value="查询"/>
				<input type="button" class="base-input-button" value="重置" onclick="resetAll()"/>
			</td>
		</tr>
	</table>
</form>
<br>
<table class="tableList">
	<tr>
		<th width="10%">交易日期</th>
		<th width="10%">交易类型    </th>
		<th width="10%">状态</th>
		<th width="15%">处理时间</th>
		<th width="45%">说明 </th>
		<th width="10%">操作 </th>
	</tr>
	<c:forEach items="${fmsDownloadList }" var="fmsMgr">
		<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');">
			<td>${fmsMgr.tradeDate   }</td>
 			<td>${fmsMgr.tradeTypeName}</td>
 			<td>
 				<c:choose>
 					<c:when test="${fmsMgr.dataFlag == '03'}">
 						<font color="red">${fmsMgr.dataFlagName}</font>
 					</c:when>
	 				<c:otherwise>
	 					${fmsMgr.dataFlagName}
	 				</c:otherwise>
 				</c:choose>
 			</td>
			<td>${fmsMgr.downloadDate} ${fmsMgr.downloadTime}</td>
			<td>${fmsMgr.dealLog}</td>
			<td>
				<input type="button" value="明细" onclick="showDetail('${fmsMgr.batchNo}','${fmsMgr.tradeType}');"/>
			</td>
		</tr>
	</c:forEach>
</table>
<!-- <table class="tableList"> -->
<!-- 	<tr> -->
<!-- 		<th width="15%">批次号      </th> -->
<!-- 		<th width="10%">交易类型    </th> -->
<!-- 		<th width="10%">总笔数      </th> -->
<!-- 		<th width="20%">下载文件路径</th> -->
<!-- 		<th width="10%">下载日期    </th> -->
<!-- 		<th width="10%">下载时间    </th> -->
<!-- 		<th width="10%">状态        </th> -->
<!-- 		<th width="10%">交易日期    </th> -->
<!-- 		<th width="5%">操作 </th> -->
<!-- 	</tr> -->
<%-- 	<c:forEach items="${fmsDownloadList }" var="fmsMgr"  varStatus="st"> --%>
<!-- 		<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');"> -->
<%-- 			<td>${fmsMgr.batchNo     }</td>              --%>
<%-- 			<td><forms:codeName tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" --%>
<%-- 					valueColumn="PARAM_VALUE" textColumn="PARAM_NAME"  --%>
<%-- 					conditionStr="CATEGORY_ID = 'DOWNLOAD_TRADE_TYPE'" selectedValue="${fmsMgr.tradeType}"/></td>              --%>
<%-- 			<td>${fmsMgr.allCnt      }</td>              --%>
<!-- 			<td> -->
<%-- 				<c:if test="${!empty fmsMgr.downloadPath}"> --%>
<%-- 					<a href="#" onclick="downloadFile('${fmsMgr.batchNo}')">${fmsMgr.downloadPath}</a> --%>
<%-- 				</c:if> --%>
<!-- 			</td>              -->
<%-- 			<td>${fmsMgr.downloadDate}</td>              --%>
<%-- 			<td>${fmsMgr.downloadTime}</td>              --%>
<%-- 			<td><forms:codeName tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" --%>
<%-- 					valueColumn="PARAM_VALUE" textColumn="PARAM_NAME"  --%>
<%-- 					conditionStr="CATEGORY_ID = 'DOWNLOAD_DATA_FLAG'" selectedValue="${fmsMgr.dataFlag }"/></td>              --%>
<%-- 			<td>${fmsMgr.tradeDate   }</td> --%>
<!-- 			<td> -->
<%-- 				<c:choose> --%>
<%-- 					<c:when test="${fmsMgr.dataFlag == '03'}"> --%>
<%-- 						<input type="button" class="base-input-button" value="处理" onclick="mendFmsDownLoad('${fmsMgr.batchNo}','${fmsMgr.tradeType}')"/> --%>
<%-- 					</c:when> --%>
<%-- 					<c:otherwise> --%>
<!-- 						<input type="button" class="base-input-button" disabled="disabled" value="处理"/> -->
<%-- 					</c:otherwise> --%>
<%-- 				</c:choose> --%>
<%-- 				<c:if test="${fmsMgr.dataFlag == '06'}"> --%>
<%-- 					<input type="button" class="base-input-button" value="日志" onclick="querLog('${fmsMgr.batchNo}','${fmsMgr.tradeType}')" /> --%>
<%-- 				</c:if> --%>
<!-- 			</td> -->
<!-- 		</tr> -->
<%-- 	</c:forEach> --%>
<%-- 	<c:if test="${empty fmsDownloadList}"> --%>
<!-- 			<tr> -->
<!-- 				<td colspan="9" style="text-align: center;"><span class="red">没有找到相关信息！</span></td> -->
<!-- 			</tr> -->
<%-- 	</c:if> --%>
<!-- </table> -->
<p:page/>
</body>
</html>