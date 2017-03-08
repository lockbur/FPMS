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
<title>FMS上传</title>
<script type="text/javascript">
function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	$("#dataFlag").combobox();
	$("#tradeType").combobox();
	$("#ouCode").combobox();
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
function downloadFile(batchNo,fileFlag){
	var form = $("#downloadForm")[0];
	var url = '<%=request.getContextPath()%>/amortization/fmsMgr/fmsFileDownload.do?<%=WebConsts.FUNC_ID_KEY%>=04030201';
	form.action = url + "&batchNo="+batchNo+ "&fileFlag="+fileFlag;
	form.submit();
}

//补做
function mendFmsUpLoad(batchNo,tradeType,dataFlag){
	App.submitShowProgress();
	var form = $("#downloadForm")[0];
	var url = '';
	if(dataFlag == 05){
		//校验文件补做
		url = '<%=request.getContextPath()%>/amortization/fmsMgr/mendFmsDownload.do?<%=WebConsts.FUNC_ID_KEY%>=04030301';
	}else {
		//上传文件补做
		url = '<%=request.getContextPath()%>/amortization/fmsMgr/mendFmsUpload.do?<%=WebConsts.FUNC_ID_KEY%>=04030202';
	}
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
	var url = '<%=request.getContextPath()%>/amortization/fmsMgr/querUpdateDetail.do?<%=WebConsts.FUNC_ID_KEY%>=04030203';
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
</script>
</head>

<body>
<p:authFunc funcArray="040302,04030201,04030301"/>
<form id="downloadForm" method="post"></form>
<form method="post" id="queryForm" action="">
	<table>
		<tr class="collspan-control">
			<th colspan="4">fms上传查询</th>
		</tr>
		<tr>
			<td class="tdLeft">交易类型</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="tradeType" name="tradeType">
						<option value="">--请选择--</option>						
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
						 conditionStr="CATEGORY_ID = 'UPLOAD_TRADE_TYPE'"
						 orderType="ASC"  selectedValue="${fmsUpload.tradeType }"/>
					</select>
				</div>
			</td>
			<td class="tdLeft">状态</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="dataFlag" name="dataFlag">
						<option value="">--请选择--</option>						
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
						 conditionStr="CATEGORY_ID = 'UPLOAD_DATA_FLAG'"
						 orderType="ASC"  selectedValue="${fmsUpload.dataFlag}"/>
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">交易日期</td>
			<td class="tdRight">
				<input type="text" id="startDate" name="startDate" class="base-input-text" style="width:35%;" readonly value="${startDateString}"/>&nbsp;至&nbsp;
				<input type="text" id="endDate" name="endDate" class="base-input-text" style="width:35%;" readonly value="${endDateString}"/>
			</td>
			<td class="tdLeft">OU</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select name="ouCode" id="ouCode">
						<option value="">--请选择--</option>
						<c:forEach items="${ouList}" var="ouinfo">
							<option value="${ouinfo.ouCode }" ${ouinfo.ouCode == fmsUpload.ouCode ?  "selected='selected'" : ""} >${ouinfo.ouName }</option>
						</c:forEach> 
					</select>
				</div>
			</td>
		</tr>
			<c:if  test="${role=='ADMIN'}">
		<tr>
			<td class="tdLeft">OU代码</td>
			<td class="tdRight">
				<input type="text" id="likeOuCode" name="likeOuCode" class="base-input-text"  maxlength="80"  value="${fmsUpload.likeOuCode}"/>
			</td>
			<td class="tdLeft">OU名称</td>
			<td class="tdRight">
				<div class="ui-widget">
					 <input type="text" id="likeOuName" name="likeOuName"   class="base-input-text"  maxlength="80" value="${fmsUpload.likeOuName}"/>
				</div>
			</td>
		</tr>
		</c:if>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="040302" value="查询"/>
				<input type="button" class="base-input-button" value="重置" onclick="resetAll()"/>
			</td>
		</tr>
	</table>
</form>
<br/>
<table class="tableList">
	<tr>
		<th rowspan="2" width="8%">交易类型</th>
		<th colspan="3" width="35%">请求文件</th>
		<th colspan="4" width="45%">校验文件</th>
		<th rowspan="2" width="6%">状态</th>
		<th rowspan="2" width="6%">操作</th>
	</tr>
	<tr> 
		<th width="15%">文件名 </th>   
		<th width="7%">总笔数</th>
		<th width="14%">上传时间</th>
		<th width="14%">文件名</th>
		<th width="14%">下载时间</th>
		<th width="8%">成功笔数</th>
		<th width="8%">失败笔数</th>
		
	</tr>
	<c:forEach items="${fmsUploadList }" var="fmsMgr">
		<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');">
			<td><forms:codeName tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
				valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" 
				conditionStr="CATEGORY_ID = 'UPLOAD_TRADE_TYPE'" selectedValue="${fmsMgr.tradeType }"/></td>
			<td>
				<c:if test="${!empty fmsMgr.uploadPath}">
					<a href="#" onclick="downloadFile('${fmsMgr.batchNo }','2')">${fmsMgr.uploadPath  }</a>
				</c:if>
			</td>
			<td>${fmsMgr.allCnt}</td>
			<td>${fmsMgr.uploadDate  }&nbsp;${fmsMgr.uploadTime  }</td>
			<td>
				<c:if test="${!empty fmsMgr.downloadPath}">
					<a href="#" onclick="downloadFile('${fmsMgr.batchNo }','1')">${fmsMgr.downloadPath}</a>
				</c:if>
			</td>
			<td>${fmsMgr.downloadDate}&nbsp;${fmsMgr.downloadTime}</td>
			<td><c:if test="${!empty fmsMgr.downloadPath}">${fmsMgr.chkSuccCnt}</c:if></td>
			<td><c:if test="${!empty fmsMgr.downloadPath}">${fmsMgr.allCnt-fmsMgr.chkSuccCnt}</c:if></td>
			<td><forms:codeName tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
				valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" 
				conditionStr="CATEGORY_ID = 'UPLOAD_DATA_FLAG'" selectedValue="${fmsMgr.dataFlag }"/></td>
			<td>
				<c:choose>
					<c:when test="${fmsMgr.dataFlag == '05' || fmsMgr.dataFlag == '09'|| fmsMgr.dataFlag == '10'}">
						<input type="button" class="base-input-button" value="处理" onclick="mendFmsUpLoad('${fmsMgr.batchNo}','${fmsMgr.tradeType}','${fmsMgr.dataFlag}')" />
					</c:when>
					<c:otherwise>
						<input type="button" class="base-input-button" disabled="disabled" value="处理"/>
					</c:otherwise>
				</c:choose>
				<c:if test="${fmsMgr.dataFlag == '06'}">
					<input type="button" class="base-input-button" value="日志" onclick="querLog('${fmsMgr.batchNo}','${fmsMgr.tradeType}')" />
				</c:if>
				<input type="button" value="明细" onclick="showDetail('${fmsMgr.batchNo}','${fmsMgr.tradeType}');"/>
			</td>
		</tr>
	</c:forEach>
	<c:if test="${empty fmsUploadList}">
			<tr>
				<td colspan="10" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
	</c:if>
</table>
<p:page/>
</body>
</html>