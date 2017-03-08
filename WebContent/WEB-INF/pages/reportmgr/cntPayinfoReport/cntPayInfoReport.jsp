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
	$("#matrType").combobox();

	
 	//设置时间插件
	$( "#feeStartDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});

	if('${con.cntType}'=='1')
	{
		$(".CntCostQueryTableId").show();
	}
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

function addDownload(){	
	var orgFlag = '${con.orgFlag}';
	var org1Code = $("#org1Code").val();
	var org2Code = $("#org2Code").val();
	var feeDept  = $("#feeDept").val();
	var cntType  = $("#cntType").val();
	var feeType  = $("#feeType").val();
	var projName  = $("#projName").val();
	var matrType  = $("#matrType").val();
	var cglCode  = $("#cglCode").val();
	var feeStartDate  = $("#feeStartDate").val();


	

	var isPass = false;
	var data = {};
	data['orgFlag'] = orgFlag;
	data['org1Code'] = org1Code;
	data['org2Code'] = org2Code;
	data['feeDept'] = feeDept;
	data['cntType'] = cntType;
	data['feeType'] = feeType;
	data['projName'] = projName;
	data['matrType'] = matrType;
	data['cglCode'] = cglCode;
	data['feeStartDate'] = feeStartDate;
	App.ajaxSubmit("reportmgr/cntPayinfoReport/addDownLoad.do?VISIT_FUNC_ID=060301",
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
							        	 	var form = $("#queryForm")[0];
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

//费用类合同 ，显示合同费用信息查询
function cntTypeChange()
{
	var selectVal=$("#cntType").val();	
	if(selectVal=="1")//费用类
	{
		$(".CntCostQueryTableId").show();
	}
	else
	{
		$(".CntCostQueryTableId").hide();
	}
}
</script>
</head>

<body>
<p:authFunc funcArray="0603,060301,060302,060303,060304"/>
<form method="post" id="queryForm" action="">
	<table>
		<tr class="collspan-control-cnt">
			<th colspan="4">
				合同基本信息查询
				<input type="hidden" id="orgFlag" name="orgFlag" value="${con.orgFlag}"/>
			</th>
		</tr>
		<tr class="collspan">
		    <c:if test="${con.orgFlag=='1'}">
		    <td class="tdLeft">二级分行</td>
		    <td class="tdRight">
		      	<input type="text" id="org2Code" name="org2Code" value="${con.org2Code}" class="base-input-text" maxlength="200" />
		    </td>
		    </c:if>
		    <c:if test="${con.orgFlag=='1'or con.orgFlag=='2'}">
			<td class="tdLeft">费用承担部门</td>
		    <td class="tdRight">
		        <input type="text" id="feeDept" name="feeDept" value="${con.feeDept}" class="base-input-text" maxlength="200" />
		   	<%-- 	<forms:OrgSelPlugin   rootNodeId="${con.org1Code}" jsVarName="feeDept"  jsVarGetValue="feeDept" initValue="${con.feeDept}"  ableQuery="true"   parentCheckFlag="false"/>
		    --%> 
		    </td>
		    </c:if>
		</tr>
		<tr class="collspan">
		    <td class="tdLeft">合同类型</td>
		    <td class="tdRight">
		    	<div class="ui-widget">
				<select class="selectC" id="cntType" name="cntType"  onchange="cntTypeChange();">
					<option value="">--请选择--</option>						
					<forms:codeTable tableName="SYS_SELECT"   selectColumn="PARAM_VALUE,PARAM_NAME"
					 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG" 
					 conditionStr="CATEGORY_ID = 'CNT_TYPE'"
					 orderType="ASC" selectedValue="${con.cntType}"/>	
				</select>
			</div>
		    </td>
		    <td class="tdLeft CntCostQueryTableId" style="display: none;">费用类型</td>
		    <td class="tdRight CntCostQueryTableId" style="display: none;">
		    	<div class="ui-widget">
					<select id="feeType" name="feeType" class="selectC" >
						<option value="">--请选择--</option>						
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
						 conditionStr="CATEGORY_ID = 'CNT_FEE_TYPE'"
						 orderType="ASC" selectedValue="${con.feeType }"/>
					</select>
				</div>
		    </td>
		</tr>
		<tr class="collspan">
		    <td class="tdLeft">项目名称</td>
		    <td class="tdRight">
		    	<input type="text" id="projName" name="projName" value="${con.projName}" class="base-input-text" maxlength="200" />
		    </td>
		    <td class="tdLeft">物料类型</td>
		    <td class="tdRight">
		        <div class="ui-widget">
					<select id="matrType" name="matrType" class="selectC" >
						<option value="">--请选择--</option>						
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
						 conditionStr="CATEGORY_ID = 'MATR_TYPE'"
						 orderType="ASC" selectedValue="${con.matrType }"/>
					</select>
				</div>
		    </td>
		</tr>
		<tr class="collspan">
		    <td class="tdLeft">核算码</td>
		    <td class="tdRight">
		    	<input type="text" id="cglCode" name="cglCode" value="${con.cglCode}" class="base-input-text" maxlength="200" />
		    </td>
		    <td class="tdLeft">合同受益开始日期</td>
		    <td class="tdRight">
		    	<input type="text" id="feeStartDate" name="feeStartDate"  maxlength='10' readonly="readonly" value="${con.feeStartDate}" class="base-input-text" style="width:35%;"/>
		    </td>
		</tr>
		<tr class="collspan">
		<td colspan="4" class="tdWhite">
		<c:if test="${con.orgFlag=='1'}">
			<p:button funcId="060302" value="查找"/>
		</c:if>
		<c:if test="${con.orgFlag=='2'}">
			<p:button funcId="060303" value="查找"/>
		</c:if>
		<c:if test="${con.orgFlag=='3'}">
			<p:button funcId="060304" value="查找"/>
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
			    <th width=''>一级分行</th>
				<th width=''>二级分行</th>
				<th width=''>费用承担部门</th>
				<th width=''>合同编号</th>
				<th width=''>关联合同号</th>
				<th width=''>合同事项</th>
				<th width=''>项目名称</th>
				<th width=''>供应商</th>
				<th width=''>合同受益起始日期</th>
				<th width=''>合同受益终止日期</th>
				<th width=''>核算码</th>
				<th width=''>合同总金额</th>
				<th width=''>付款类型</th>
				<th width=''>累计（不含税+不可抵扣税）金额</th>
				<th width=''>累计已付款金额</th>
				<th width=''>累计已列账金额</th>
				<th width=''>累计尚未摊销的待摊金额</th>
				<th width=''>当年累计已付款</th>
				<th width=''>当年累计已列账</th>
				<th width=''>当年累计尚未摊销的待摊金额</th>
				<th width=''>当年受益额合计</th>
			</tr>
		<c:forEach items="${cntPayinfoReportList}" var="cntItem">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td>（${cntItem.org1Code}）${cntItem.org1Name}</td>
				<td>（${cntItem.org2Code}）${cntItem.org2Name}</td>
				<td>${cntItem.feeDept}</td>
				<td>${cntItem.cntNum}</td>
				<td>${cntItem.cntNumRelated}</td>
				<td><forms:StringTag length="20" value="${cntItem.cntName}"/></td>
				<td>${cntItem.projName}</td>
				<td>${cntItem.providerCode}</td>
				<td>${cntItem.feeStartDate}</td>
				<td>${cntItem.feeEndDate}</td>
				<td>${cntItem.cglCode}</td>
				<td><fmt:formatNumber type="number" value="${cntItem.cntAllAmt}" minFractionDigits="2"/></td>
				<td>${cntItem.payType}</td>
				<td>
				<c:choose>
					<c:when test="${cntItem.notaxAllPayAmt eq '-'}">
						${cntItem.notaxAllPayAmt}
					</c:when>
					<c:otherwise>
						<fmt:formatNumber type="number" value="${cntItem.notaxAllPayAmt}" minFractionDigits="2"/>
					</c:otherwise>
				</c:choose>
				</td>
				<td><fmt:formatNumber type="number" value="${cntItem.countAllPayAmt}" minFractionDigits="2"/></td>
				<td>
				<c:choose>
					<c:when test="${cntItem.countAllInAmt eq '-'}">
						${cntItem.countAllInAmt}
					</c:when>
					<c:otherwise>
						<fmt:formatNumber type="number" value="${cntItem.countAllInAmt}" minFractionDigits="2"/>
					</c:otherwise>
				</c:choose>
				</td>
				<td>
				<c:choose>
					<c:when test="${cntItem.countAllOutAmt eq '-'}">
						${cntItem.countAllOutAmt}
					</c:when>
					<c:otherwise>
						<fmt:formatNumber type="number" value="${cntItem.countAllOutAmt}" minFractionDigits="2"/>
					</c:otherwise>
			    </c:choose>
				</td>
				<td><fmt:formatNumber type="number" value="${cntItem.yearAllPayAmt}" minFractionDigits="2"/></td>
				<td>
				<c:choose>
					<c:when test="${cntItem.yearAllInAmt eq '-'}">
						${cntItem.yearAllInAmt}
					</c:when>
					<c:otherwise>
						<fmt:formatNumber type="number" value="${cntItem.yearAllInAmt}" minFractionDigits="2"/>
					</c:otherwise>
				</c:choose>
				</td>
				<td>
				<c:choose>
					<c:when test="${cntItem.yearAllOutAmt eq '-'}">
						${cntItem.yearAllOutAmt}
					</c:when >
					<c:otherwise>
						<fmt:formatNumber type="number" value="${cntItem.yearAllOutAmt}" minFractionDigits="2"/>
					</c:otherwise>
				</c:choose>
				</td>
				<td><fmt:formatNumber type="number" value="${cntItem.yearAllFee}" minFractionDigits="2"/></td>
			</tr>
		</c:forEach>
		
		<c:if test="${empty cntPayinfoReportList}">
			<tr>
				<td colspan="21" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
	</table>
</form>
<p:page/>
</body>
</html>