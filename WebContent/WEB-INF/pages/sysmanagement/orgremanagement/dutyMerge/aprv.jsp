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
<title>机构撤并审批页面</title>
<script type="text/javascript">
function doValidate() {
	
}
function toAudit(){
	$("#auditDiv").dialog({
		title:'撤并复核',
		autoOpen: true,
		height: 'auto',
		width: 550,
		modal: true,
		dialogClass: 'dClass',
		resizable: false,
		buttons: {
			"确定": function() {
				//判断radio是否被选中
				if($('input:radio[name="dealFlag"]:checked').val()==null){
					App.notyError("请先选择同意或者退回!");
					return false;
				}else if($('input:radio[name="dealFlag"]:checked').val()=='1'){//同意
					$("#auditMemo").removeAttr("valid");
				}else{//退回
					$("#auditMemo").attr("valid","");
				}
				//校验
				if(!App.valid("#auditForm")){return;}
				//ajax校验最后的更新时间否是一致
				$( this ).dialog( "close" );
				var data1 = {};
				data1['batchNo']=$("#batchNo").val();
				data1['lastUpdtime']=$("#lastUpdtime").val();
				App.ajaxSubmit("sysmanagement/orgremanagement/dutyMerge/aprvAjax.do?<%=WebConsts.FUNC_ID_KEY %>=0104110211",{
					data : data1,
					async : false
				}, function(data) {
					if(data.flag){
						$('#auditForm').attr('action', '<%=request.getContextPath()%>/sysmanagement/orgremanagement/dutyMerge/aprv.do?<%=WebConsts.FUNC_ID_KEY%>=0104110210');
						$('#auditForm').submit();
					}else{
						$( "<div>"+$("#batchNo").val()+"批次下的数据已被修改，请重新进入列表页面！</div>" ).dialog({
							resizable: false,
							height:'auto',
							modal: true,
							dialogClass: 'dClass',
							buttons: {
								"确定": function() {
									$( this ).dialog( "close" );
									location.href = '<%=request.getContextPath()%>/sysmanagement/orgremanagement/dutyMerge/mergeAprvList.do?VISIT_FUNC_ID=01041102';
								}
							}
						});
					}
				});
				
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}
</script>
</head>
<body>
<p:authFunc funcArray=""/>
<form action="" method="post" id="orgForm">
	<table id="table1">
		<tr>
			<th colspan="4">撤并复核</th>
		</tr>
		<tr>
			<td class="tdRight" colspan="4">
			待处理的撤并日期：${dealDate}<br><br>
			以下列表的数据日期：${listDate}<br><br>
			数据说明：${memo }<br>
			</td>
		</tr>
	</table>
	<table class="tableList">
		<tr>
			<th width="20%">撤并类型</th>
			<th width="40%">撤并前</th>
			<th width="40%">撤并后</th>
		</tr>
		<c:if test="${!empty list }">
			<c:forEach items="${list }" var="bean">
			<tr>
				<td>
					<input type="hidden" name="changeType" value="${bean.changeType}"/>
					<c:if test="${bean.changeType == '01' }">责任中心撤并</c:if>
					<c:if test="${bean.changeType == '02' }">机构撤并</c:if>
				</td>
				<td>${bean.codeBef}-${bean.nameBef}</td>
				<td>
					${bean.codeCur}-${bean.nameCur}
				</td>
			</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty list }">
			<tr>
				<td colspan="4" style="text-align: center;"><span class="red">没有相关记录</span></td>
			</tr>
		</c:if>
	</table>
	<c:if test="${!empty list }">
	<div align="center" style="margin-top: 10px;">
		 <input type="button" value="审批" onclick="toAudit();"/>
	</div>
	</c:if>
</form>
<br>
<div id="auditDiv" style="display: none;" >
	<form action="" method="post" id="auditForm">
		<p:token/>
		<input type="hidden" value="${batchNo }" id="batchNo" name="batchNo"/>
		<input type="hidden" value="${lastUpdtime }" id="lastUpdtime" name="lastUpdtime"/>
		<table width="98%" id="auditTable">
		   <tr>
				<td align="left" colspan="2">
					<input type="hidden" id="isAgree" name="isAgree" value=""/>
					<div class="base-input-radio" id="authdealFlagDiv">
						<label for="authdealFlag1" onclick="App.radioCheck(this,'authdealFlagDiv');">同意</label><input type="radio" id="authdealFlag1" name="dealFlag" value="1" >
						<label for="authdealFlag2" onclick="App.radioCheck(this,'authdealFlagDiv');">退回</label><input type="radio" id="authdealFlag2" name="dealFlag" value="0">
					</div>
				</td>
			</tr>
			<tr>
				<td align="left" colspan="2">
					<br>转发意见(<span id="authdealCommentSpan">0/500</span>)：
					<textarea class="base-textArea" onkeyup="$_showWarnWhenOverLen1(this,500,'authdealCommentSpan')" id="auditMemo" name="memo" rows="7" cols="45" valid errorMsg="请输入转发意见。"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>	
</body>
</html>