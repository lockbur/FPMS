<%@page import="com.forms.platform.web.WebUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.forms.platform.web.consts.WebConsts"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>物料审批链列表</title>
<script type="text/javascript">
function pageInit(){
	$("#aprvType").val("${al.aprvType}");
	App.jqueryAutocomplete();
	$("#aprvType").combobox();
}

function listAppChain()
{
 	var form=$("#appFormSearch")[0];
	form.action="<%=request.getContextPath()%>/sysmanagement/approveChainMgr/appList.do?<%=WebConsts.FUNC_ID_KEY%>=08120104";
	App.submit(form);
}

function resetAll(){
	$(":text").val("");
	$("select").val("");
	$("#matrBuyDept").val("");
	$("#matrAuditDept").val("");
	$("#decomposeOrg").val("");
	$("#fincDeptS").val("");
	$("#fincDept2").val("");
	$("#fincDept1").val("");
	$("#feeCode").val("");
 	$(":selected").prop("selected",false);
// 	matrBuyDeptTree.tagReset();
// 	matrAuditDeptTree.tagReset();
// 	decomposeOrgTree.tagRest();
// 	fincDeptSTree.tagReset();  
// 	fincDept2Tree.tagReset();
// 	fincDept1Tree.tagReset();
// 	feeCodeTree.tagReset();
 	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
});
}
function showMont(){
	$.dialog.open(
			'<%=request.getContextPath()%>/sysmanagement/approveChainMgr/montCodePage.do?<%=WebConsts.FUNC_ID_KEY %>=0812010401',
			{
				width: "75%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"监控指标选择",
			    id:"dialogCutPage",
			    close:function(){
			    	var data=art.dialog.data('data');
					if(data){
						$("#montCode").val(data.montCode);  //内部供应商名称
					}
			    }
			}
	);
}
function Export(){
	if ($("#aprvType").val()==""||$("#dataYear").val()=="") {
		App.notyError("请至少选择审批链类型和填写年份！");
		return false;
	}
	var data = {};
	data['feeCode']=$("#feeCode").val();
	data['montCode']=$("#montCode").val();
	data['matrBuyDept']=$("#matrBuyDept").val();
	data['dataYear']=$("#dataYear").val();
	data['matrCode']=$("#matrCode").val();
	data['matrName']=$("#matrName").val();
	data['matrAuditDept']=$("#matrAuditDept").val();
	data['decomposeOrg']=$("#decomposeOrg").val();
	data['fincDeptS']=$("#fincDeptS").val();
	data['fincDept2']=$("#fincDept2").val();
	data['fincDept1']=$("#fincDept1").val();
	data['aprvType']=$("#aprvType").val();
	var pass=false;
	App.ajaxSubmit("sysmanagement/approveChainMgr/download.do?<%=WebConsts.FUNC_ID_KEY%>=08120105",
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
							        		$(form).append("<input type='hidden' name='upStepUrl' id='upStepUrl'>");
							        		$(form).append("<input type='hidden' name='funcId' value='08120104' ");//为提交跳转的form添加input框记录传递参数upStepUrl(用于查询FuncId)
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
					pass =  true;
				}
				else
				{
					App.notyError("添加下载失败，请检查后重试!");
					pass =  false;
				}
			});
	return pass;
}
//明细页面
function detail(aprvType,montCode,matrCode,feeCode,orgCode,dataYear){
	var url = '<%=request.getContextPath()%>/sysmanagement/approveChainMgr/selectDetail.do?<%=WebConsts.FUNC_ID_KEY %>=0812010402';
	$("#aprvTypeSkip").val(aprvType);
	$("#montCodeSkip").val(montCode);
	$("#matrCodeSkip").val(matrCode);
	$("#feeCodeSkip").val(feeCode);
	$("#orgCodeSkip").val(orgCode);
	$("#dataYearSkip").val(dataYear);
	$("#skipForm").attr("action",url);
	$("#skipForm").submit();
}
</script>
</head>
<body>
<form action="" method="post" id="skipForm">
	<input type="hidden" id="aprvTypeSkip" name="aprvType"/>
	<input type="hidden" id="montCodeSkip" name="montCode"/>
	<input type="hidden" id="matrCodeSkip" name="matrCode"/>
	<input type="hidden" id="feeCodeSkip" name="feeCode"/>
	<input type="hidden" id="orgCodeSkip" name="orgCode"/>
	<input type="hidden" id="dataYearSkip" name="dataYear"/>
</form>
<p:authFunc funcArray="08120104,0812010401"/>
<form action="" method="post" id="appFormSearch">
	<p:token/>
	<table id="t1">
		<tr class="collspan-control">
			<th colspan="4">审批链查询</th>
		</tr>
		
		
		<tr>
			<td class="tdLeft" width="25%">物料编码</td>
			<td class="tdRight" width="25%">
				<input type="text" id="matrCode" name="matrCode" value="${al.matrCode}" class="base-input-text" maxlength="11" />				
			</td>
			<td class="tdLeft" width="25%">物料名称</td>
			<td class="tdRight" width="25%">
				<input type="text" id="matrName" name="matrName" value="${al.matrName}" class="base-input-text" maxlength="200" />				
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">监控指标代码</td>
			<td class="tdRight" width="25%">
				<input type="text" id="montCode" name="montCode" value="${al.montCode}" class="base-input-text" maxlength="50" />				
			</td>
			<td class="tdLeft" width="25%">监控指标名称</td>
			<td class="tdRight" width="25%">		
				<input type="text" id="montName" name="montName" value="${al.montName}" class="base-input-text" />		
			</td>
			
		</tr>	
		<tr>
			<td class="tdLeft" width="25%">费用承担部门编码</td>
			<td class="tdRight" width="25%">		
				<input type="text" id="feeCode" name="feeCode" value="${al.feeCode}" class="base-input-text" />		
			</td>
			<td class="tdLeft" width="25%">费用承担机构编码</td>
			<td class="tdRight" width="25%">		
				<input type="text" id="orgCode" name="orgCode" value="${al.orgCode}" class="base-input-text" />		
			</td>
		</tr>	
		<tr>
			<td class="tdLeft" width="25%">物料采购部门编码</td>
			<td class="tdRight" width="25%">		
				<input type="text" id="matrBuyDept" name="matrBuyDept" value="${al.matrBuyDept}" class="base-input-text" />		
			</td>
			<td class="tdLeft" width="25%">物料归口部门编码</td>
			<td class="tdRight" width="25%">		
				<input type="text" id="matrAuditDept" name="matrAuditDept" value="${al.matrAuditDept}" class="base-input-text" />		
			</td>
		</tr>	
		<tr>
			<td class="tdLeft" width="25%">审批链类型</td>
			<td class="tdRight" width="25%">
				<div class="ui-widget">
					<!-- <select id="aprvType" name="aprvType" class="erp_cascade_select">
						<option value="">-请选择-</option>
						<option value="11">专项包</option>
						<option value="12">省行统购</option>
						<option value="21">非省行统购资产</option>
						<option value="22">非专项包费用</option>
					</select> --> 
					<select id="aprvType" name="aprvType"   class="erp_cascade_select">
						<option value="">-请选择-</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="ASC"  conditionStr="CATEGORY_ID='MONT_TYPE'" selectedValue="${selectInfo.aprvType}"/>
					</select>
				</div>
			</td>
			<td class="tdLeft" width="25%">年份</td>
			<td class="tdRight">
					<input type="text" id="dataYear" name="dataYear" class="base-input-text" value="${al.dataYear}">
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<input type="button" value="查找" onclick="listAppChain();"/>
				<input type="button" value="重置" onclick="resetAll();"/>	
				<!-- 
				<input type="button" value="导出" onclick="Export()">
				 -->				
			</td>
		</tr>									
	</table>
</form>
<br/>
<form action="" method="post" id="appForm">
	<table id="listTab" class="tableList">
		<tr>
			<th width="10%">审批链类型</th>
			<th width="20%">物料名称</th>
			<th width="20%">监控指标名称</th>
			<th width="10%">费用承担部门编码</th>
			<th width="10%">费用承担机构编码</th>
			<th width="10%">物料采购部门编码</th>
			<th width="10%">物料归口部门编码</th>
			<th width="10%">操作</th>
		</tr>
		<c:forEach items="${appList}" var="list" varStatus="status">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td>
					<%-- <c:if test="${list.aprvType == '11'}">
						专项包
					</c:if>
					<c:if test="${list.aprvType == '12'}">
						省行统购
					</c:if>
					<c:if test="${list.aprvType == '21'}">
						非省行统购资产
					</c:if>
					<c:if test="${list.aprvType == '22'}">
						非专项包费用
					</c:if> --%>
					<span > ${list.aprvTypeName}</span>
				</td>
				<td> <span title="${list.matrCode }">${list.matrName}</span></td>
				<td> <span title="${list.montCode }">${list.montName}</span></td>
				<td>  <span > ${list.feeCode}</span></td>
				<td>  <span > ${list.orgCode}</span></td>
				<td>  <span >${list.matrBuyDept}</span></td>
				<td>  <span >${list.matrAuditDept}</span></td>
				<td> <input type="button" value="明细" onclick="detail('${list.aprvType}','${list.montCode}','${list.matrCode }','${list.feeCode }','${list.orgCode }','${list.dataYear }')"/></td>
		</c:forEach>
		<c:if test="${empty appList}">
	   		<tr>
				<td colspan="8" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
			</tr>
	   </c:if>
	</table>
</form>
<p:page/>
</body>
</html>