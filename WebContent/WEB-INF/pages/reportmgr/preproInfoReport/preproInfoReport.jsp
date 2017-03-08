	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>合同查询</title>
<script type="text/javascript">

function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	$("#cntType").combobox();
	$("#feeType").combobox();
	
 	//设置时间插件
	$( "#feeStartDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
 	
}

function addDownload(){	
	var orgFlag = '${con.orgFlag}';
	var org1Code = $("#org1Code").val();
	var org2Code = $("#org2Code").val();
	var feeDept = $("#feeDept").val();
	var cglCode = $("#cglCode").val();
	var feeYear = $("#feeYear").val();

	var isPass = false;
	var data = {};
	data['orgFlag'] = orgFlag;
	data['org1Code'] = org1Code;
	data['org2Code'] = org2Code;
	data['feeDept'] = feeDept;
	data['cglCode'] = cglCode;
	data['feeYear'] = feeYear;

	App.ajaxSubmit("reportmgr/preproInfoReport/addDownLoad.do?VISIT_FUNC_ID=060402",
			{data:data,async : false},
			function(data){
				flag = data.pass;
				if(!flag){
					App.notyError("添加下载失败，请重试!");
					isPass =  false;
				}
				else
				{
					$("<div>Excel已成功提交后台处理<br\>是否进入下载页面？</div>").dialog({
						resizable: false,
						width: 290,
						height:'auto',
						modal: true,
						title: "跳转下载页面",
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
											form.action='<%=request.getContextPath()%>/reportmgr/importAndexport/query/getExportReportList.do?<%=WebConsts.FUNC_ID_KEY%>=060101';
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
			});
	return isPass;
}

function resetAll() {
	$("#flag").removeAttr("checked");
	$("#flag").attr("disabled","disabled");

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
</script>
</head>

<body>
<p:authFunc funcArray="060101,0604,060401,060402,060403,060404,060405"/>
<form method="post" id="queryForm" action="">
	<table>
		<tr class="collspan-control-cnt">
			<th colspan="4">
				合同基本信息查询
			</th>
		</tr>
		<tr class="collspan">
		    <c:if test="${con.orgFlag=='1'}">
		    <td class="tdLeft">二级分行</td>
		     <td class="tdRight">
		       <input type="text" id="org2Code" name="org2Code" value="${con.org2Code}" class="base-input-text" maxlength="200" />
		    </td>
		    </c:if>
		    <c:if test="${con.orgFlag=='1' or con.orgFlag=='2'}">
			<td class="tdLeft">费用承担部门</td>
		    <td class="tdRight">
		        <input type="text" id="feeDept" name="feeDept" value="${con.feeDept}" class="base-input-text" maxlength="200" />
		   		<%-- <forms:OrgSelPlugin   rootNodeId="${con.org1Code}" jsVarName="feeDept"  jsVarGetValue="feeDept" initValue="${con.feeDept}"  ableQuery="true"   parentCheckFlag="false"/> --%>
		    </td>
		  </c:if>
		</tr>
		<tr class="collspan">
		   <td class="tdLeft">核算码</td>
		    <td class="tdRight">
		    	<input type="text" id="cglCode" name="cglCode" value="${con.cglCode}" class="base-input-text" maxlength="200" />
		    </td>
		    <td class="tdLeft">受益年度</td>
		    <td class="tdRight">
		    	<input type="text" id="feeYear" name="feeYear" value="${con.feeYear}" class="base-input-text" maxlength="200" />
		    </td>
		</tr>
		<tr class="collspan">
		<td colspan="4" class="tdWhite">
		<c:if test="${con.orgFlag=='1'}">
			<p:button funcId="060403" value="查找"/>
		</c:if>
		<c:if test="${con.orgFlag=='2'}">
			<p:button funcId="060404" value="查找"/>
		</c:if>
		<c:if test="${con.orgFlag=='3'}">
			<p:button funcId="060405" value="查找"/>
		</c:if>
		<input type="button" value="重置" onclick="resetAll();">
		
		<input type="button" value="添加下载" onclick="addDownload();">
		</td>
		</tr>
	</table>	
</form>	
<form action="">
	<br>
	<br>
	<table class="tableList">
			<tr>
				<th >一级分行</th>
				<th width=''>二级分行</th>
				<th width=''>费用承担部门</th>
				<th width=''>核算码</th>
				<th width=''>物料</th>
				<th width=''>合同编号</th>
				<th width=''>合同事项</th>
				<th width=''>付款类型</th>
				<th width=''>累计（不含税+不可抵扣税）金额</th>
				<th width=''>累计已付款金额</th>
				<th width=''>累计已列账金额</th>
				<th width=''>累计尚未摊销的待摊金额</th>
				<th width=''>当年累计已付款金额</th>
				<th width=''>当年累计已列账金额</th>
				<th width=''>当年尚未摊销的待摊金额</th>
				<th width=''>当年受益金额合计数</th>
			</tr>
		<c:forEach items="${preproInfoReport}" var="cntItem">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td>（${cntItem.org1Code}）${cntItem.org1Name}</td>
				<td>（${cntItem.org2Code}）${cntItem.org2Name}</td>
				<td>${cntItem.feeDept}</td>
				<td>${cntItem.cglCode}</td>
				<td>(${cntItem.matrCode})${cntItem.matrName}</td>
				<td>${cntItem.cntNum}</td>
				<td><forms:StringTag length="20" value="${cntItem.cntName}"/></td>
				<td>${cntItem.payType}</td>
				<td><fmt:formatNumber type="number" value="${cntItem.notaxAllPayAmt}" minFractionDigits="2"/></td>
				<td><fmt:formatNumber type="number" value="${cntItem.countAllPayAmt}" minFractionDigits="2"/></td>
				<td><fmt:formatNumber type="number" value="${cntItem.countAllInAmt}" minFractionDigits="2"/></td>
				<td><fmt:formatNumber type="number" value="${cntItem.countAllOutAmt}" minFractionDigits="2"/></td>
				<td><fmt:formatNumber type="number" value="${cntItem.yearAllPayAmt}" minFractionDigits="2"/></td>
				<td><fmt:formatNumber type="number" value="${cntItem.yearAllInAmt}" minFractionDigits="2"/></td>
				<td><fmt:formatNumber type="number" value="${cntItem.yearAllOutAmt}" minFractionDigits="2"/></td>
				<td><fmt:formatNumber type="number" value="${cntItem.yearAllFee}" minFractionDigits="2"/></td>
			</tr>
		</c:forEach>
		
		<c:if test="${empty preproInfoReport}">
			<tr>
				<td colspan="16" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
	</table>
</form>
<p:page/>
</body>
</html>