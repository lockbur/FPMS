<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/platform-tags" prefix="p"%>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>科目余额查询</title>
<script type="text/javascript">
function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	$(".erp_cascade_select").combobox();
	
 	//设置时间插件
	$( "#befDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
 	
	//设置时间插件
	$( "#aftDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
	reShow($("#befDate"));
	reShow($("#aftDate"));
	
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
function reShow(dateObj){
	var date = $(dateObj).val();
	if(date != "" && date.length==8){
		date = date.substring(0,4) + '-' + date.substring(4,6) + '-' + date.substring(6,8);
		$(dateObj).val(date);
	}	
}
function subBalanceDtl(batchNo, seqNo){
	$.dialog.open(
			'<%=request.getContextPath()%>/amortization/subjectBalance/subBalanceDtl.do?<%=WebConsts.FUNC_ID_KEY%>=04090101&batchNo='+batchNo+'&seqNo='+seqNo,
			{
				width: "75%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"明细详情 ",
			    id:"dialogCutPage",
				close: function(){
					}		
			}
		 );
}
function doValidate(){
	if(!$.checkDate("befDate","aftDate")){
		return false;
	}
	return true;
}
</script>
</head>
<body>
<p:authFunc funcArray="040901" />
	<form action="" method="post" id="subjectFormSearch">
		<p:token />
		<table>
			<tr class="collspan-control">
				<th colspan="4">科目余额查询</th>
			</tr>
			<tr>
			<td class="tdLeft" width="20%">责任中心</td>
				<td class="tdRight" width="30%"><forms:OrgSelPlugin suffix="c"
						rootNodeId="${org1Code}" initValue="${selectInfo.dutyCode}"
						jsVarGetValue="dutyCode"  parentCheckFlag="false" />
			</td>
			<td class="tdLeft" width="25%">核算码</td>
			<td class="tdRight" width="25%" >
				<input style="width: 40px;" type="text" id="cglCode" name="cglCode" class="base-input-text" value="${selectInfo.cglCode}" maxlength="4"/>
			</td>
				
			</tr>
			<tr>
			<td class="tdLeft">数据日期</td>
			<td class="tdRight" colspan="3">
				<input type="text" id="befDate" name="befDate"  maxlength='10' readonly="readonly" value="${selectInfo.befDate}" class="base-input-text" style="width:135px;"/>
				至
				<input type="text" id="aftDate" name="aftDate"  maxlength='10' readonly="readonly" value="${selectInfo.aftDate}" class="base-input-text" style="width:135px;"/>
			</td>
			
			</tr>
			<tr>
				<td colspan="4" class="tdWhite">
					<p:button funcId="040901" value="查询"/> 
					<input type="button" value="重置" onclick="resetAll();">
				</td>
			</tr>
		</table>

	</form>
	<form action="" method="post" id="projForm">
		<br />
		<div id="listDiv"
			style="width: 100%; position: relative; float: right">

			<table id="listTab" class="tableList">
				<tr>
					<th width="10%">数据日期</th>
					<th width="25%">责任中心代码</th>
					<th width="25%">责任中心名称</th>
					<th width="10%">核算码</th>
					<th width="10%">借方余额</th>
					<th width="10%">贷方余额</th>
					<th width="10%">操作</th>
				</tr>
				
				<c:forEach items="${subBalanceList}" var="subBalance">
					<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
						<td style="text-align: center;">${subBalance.tradeDate }</td>
						<td style="text-align: center;">${subBalance.dutyCode }</td>
						<td style="text-align: center;">${subBalance.dutyName }</td>
						<td style="text-align: center;">${subBalance.cglCode }</td>
						<td style="text-align: center;">${subBalance.drAmt }</td>
						<td style="text-align: center;">${subBalance.crAmt }</td>
						<td>
							<div class="detail">
					    		<a href="#" onclick="subBalanceDtl('${subBalance.batchNo}','${subBalance.seqNo}');" title="详情"></a>
							</div>
						</td>
					</tr>
				</c:forEach>
				<c:if test="${empty subBalanceList}">
					<tr>
						<td colspan="7" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
					</tr>
				</c:if>
			</table>
		</div>
	</form>
<p:page/>
</body>
</html>