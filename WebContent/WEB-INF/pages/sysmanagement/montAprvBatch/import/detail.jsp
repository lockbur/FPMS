<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
function fileDownload(batchNo){
	var data = {};
	var orgType= $("#orgType").val();
	var func_id="";
	var func_id_1="";
	data["batchNo"] = batchNo;
	if (orgType=="01") {
		func_id="0811030102";
		func_id_1="0811020108";
	} else {
		func_id="0811030202";
		func_id_1="0811020208";
	}
	//Ajax检查文件是否存在校验
	App.ajaxSubmit("sysmanagement/montAprvBatch/apprv/checkDownFileExist.do?<%=WebConsts.FUNC_ID_KEY %>="+func_id,
		{data:data,async:false}, 
		function(data) {
			var checkFlag = data.checkFlag;
			var checkMsg  = data.checkMsg;
			if(checkFlag){
				var form = $("#detailForm")[0];
				form.action = "<%=request.getContextPath()%>/sysmanagement/montAprvBatch/apprv/Export.do?<%=WebConsts.FUNC_ID_KEY%>="+func_id_1+"&batchNo="+data.batchNo;
				form.submit();
			}else{
				//如果文件不存在，则不执行下载操作，并且将"文件不存在"信息报给用户
				App.notyError(checkMsg);
			}
		}
	);
}
function downloadFile(filePath,fileName){
	var  url = '<%=request.getContextPath()%>/fileUtils/fileDownload.do?filePath='+filePath+'&fileName='+fileName;
	location.href = url;
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>详情</title>
</head>
<body>
<input type="hidden" value="${bean.orgType}" id="orgType"/>
	<table class="tableList">
		<tr>
			<th colspan="4">数据详情</th>
		</tr>
		<tr>
			<td class="tdLeft">批次号</td>
			<td class="tdRight">${bean.batchNo}</td>
			<td class="tdLeft">年份</td>
			<td class="tdRight">${bean.dataYear}</td>
		</tr>
		<tr>
			<td class="tdLeft">源文件名</td>
			<td class="tdRight">
			  ${bean.sourceFilename}
			</td>
			<td class="tdLeft">数据类型</td>
			<td class="tdRight">
				<c:if test="${bean.proType=='01'}">监控指标</c:if>
				<c:if test="${bean.proType=='02'}">审批链</c:if>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">子类型</td>
			<td class="tdRight">
				<%-- <c:if test="${bean.subType=='11'}">专项包</c:if>
				<c:if test="${bean.subType=='12'}">省行统购资产</c:if>
				<c:if test="${bean.subType=='22'}">非专项包费用类</c:if>
				<c:if test="${bean.proType=='01'&&bean.subType=='21'}">非省行统购资产</c:if>
				<c:if test="${bean.proType=='02'&&bean.subType=='21'}">非省行统购非专项包</c:if> --%>		
				${bean.subTypeName }					
			</td>
			<td class="tdLeft">状态</td>
			<td class="tdRight">
				${bean.statusName }
			</td>
		</tr>
		<tr>
			<td class="tdLeft">操作人</td>
			<td class="tdRight">${bean.instUser}</td>
			<td class="tdLeft">操作时间</td>
			<td class="tdRight">${bean.instDate} - ${bean.instTime}</td>
		</tr>
		<tr>
			<td class="tdLeft">导入类型</td>
			<td class="tdRight">${bean.instMemo}</td>
			<td class="tdLeft">备注</td>
			<td class="tdRight">${bean.memo}</td>			
		</tr>	
		<tr>
			<td class="tdLeft">导入日志文件</td>
			<td class="tdRight" colspan="3"> 
				<c:if test="${!empty logFile}">
				log文件：<a href="#" onclick="downloadFile('${logFilePath}','${logFile }')">${logFile}</a>
				</c:if>
				<c:if test="${!empty badFile}">
					<br>
					bad文件：<a href="#" onclick="downloadFile('${badFilePath}','${badFile }')">${badFile}</a>
				</c:if>
			</td>
		</tr>					
	</table>
	<br>
	<br>

	<c:if test="${bean.proType eq '01' }">
	<table class="tableList">
		<tr class="collspan-control" title="导入信息Table">
			<th width="10%">Excel行号</th>
			<th width="16%">旧监控指标</th>
			<th width="14%">物料编码</th>
			<th width="16%">新监控指标</th>
			<th width="17%">错误原因</th>
		</tr>
		<c:forEach items="${err}" var="errBean" varStatus="vs">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');" class="trWhite" >
				<td style="text-align: center" >第 ${errBean.rowNo} 行</td>
				<td style="text-align: center">${errBean.lastMontName}</td>
				<td style="text-align: center">${errBean.matrCode}</td>
				<td style="text-align: center">${errBean.montName}</td>
				<td style="text-align: center ;height:33px">
				${errBean.errDesc}
				</td>
			</tr>
		</c:forEach>
		<c:if test="${empty err}">
			<tr><td style="text-align: center;" colspan="5"><span class="red">无导入错误数据</span></td></tr>
		</c:if>
		
		</table>
		<p:page/>
	</c:if>

	<c:if test="${bean.proType eq '02' }">
		<table class="tableList">
			<tr class="collspan-control" title="导入信息Table">
			<th width="10%">Excel行号</th>
			<th width="16%">监控指标代码</th>
			<th width="14%">物料</th>
			<th width="16%">费用承担部门</th>
			<th width="16%">机构</th>
			<th width="17%">错误原因</th>
		</tr>
		<c:forEach items="${err}" var="errBean" varStatus="vs">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');" class="trWhite" >
				<td style="text-align: center" >第 ${errBean.rowNo} 行</td>
				<td style="text-align: center">${errBean.montCode}</td>
				<td style="text-align: center">${errBean.matrCode}</td>
				<td style="text-align: center">${errBean.feeCode}</td>
				<td style="text-align: center">${errBean.orgCode}</td>
				<td style="text-align: center ;height:33px">
				${errBean.errDesc}
				</td>
			</tr>
		</c:forEach>
		<c:if test="${empty err}">
			<tr><td style="text-align: center;" colspan="6"><span class="red">无导入错误数据</span></td></tr>
		</c:if>
		
		</table>
		<p:page/>
	</c:if>

	

<br><br><br>
<input type="button" value="返回" onclick="backToLastPage('${uri}');">
</body>
</html>