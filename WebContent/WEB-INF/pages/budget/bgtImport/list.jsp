<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预算汇总(省行/分行)管理</title>
<script type="text/javascript">
//页面初始化加载
function pageInit(){
	//listTabFirstTr
	App.jqueryAutocomplete();
	$("#bgtYearSelect").combobox();
 	$("#subTypeSelect").combobox();
	$("#bgtStatusSelect").combobox();
	$("#valiYearSelect").combobox();

	
}

//重置清空所有选中信息
function resetAll(){
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

//提交确认预算
function confirmBgtSum( batchNo ,subType,org21Code){
	var form = $("#budgetPlanListForm")[0];
	var orgType = $("#orgType").val();
	var funcId='';
	if(orgType=='1'){
		funcId = "02090108";
	}else{
		funcId = "02090208";
	}
	form.action="<%=request.getContextPath()%>/budget/bgtImport/bgtSubmit.do?<%=WebConsts.FUNC_ID_KEY %>="+funcId+"&batchNo=" + batchNo+"&subType="+subType+"&org21Code="+org21Code;
	$( "<div>确认提交该批次["+batchNo+"]</br>预算汇总数据?</div>" ).dialog({
		resizable: false,
		height:180,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
				App.submit(form,true);
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	
	
}
//进入导入页面
function gotoImportPage(){
	var orgType = $("#orgType").val();
	var funcId='';
	if(orgType=='1'){
		funcId = "02090101";
	}else{
		funcId = "02090201";
	}
	var form = $("#bgtSumTotalInfo")[0];
	form.action="<%=request.getContextPath()%>/budget/bgtImport/importPage.do?<%=WebConsts.FUNC_ID_KEY %>="+funcId;
	App.submit(form);
}
//删除指定预算模板
function delBgtSumById( batchNo ){
	var orgType = $("#orgType").val();
	var funcId='';
	if(orgType=='1'){
		funcId = "02090104";
	}else{
		funcId = "02090204";
	}
	var url = "<%=request.getContextPath()%>/budget/bgtImport/del.do?<%=WebConsts.FUNC_ID_KEY %>="+funcId+"&batchNo=" + batchNo;
	var form = $("#bgtSumTotalInfo")[0];
	form.action = url;
	$( "<div>确认删除汇总数据["+batchNo+"]?</div>" ).dialog({
		resizable: false,
		height:180,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
				App.submit(form,true);
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}
//查看预算详情
function detail( batchNo ){
	var orgType = $("#orgType").val();
	var funcId="";
	if(orgType=="1"){
		funcId = "02090105";
	}else{
		funcId = "02090205";
	}
	var form = $("#bgtSumTotalInfo")[0];
	form.action="<%=request.getContextPath()%>/budget/bgtImport/detail.do?<%=WebConsts.FUNC_ID_KEY %>="+funcId+"&batchNo=" + batchNo;
	App.submit(form);
}
//导出
function  exportData(batchNo,bgtYear,bgtType,subType){
	var orgType = $("#orgType").val();
	var funcId="";
	if(orgType=="1"){
		funcId = "02090106";
	}else{
		funcId = "02090206";
	}
	var data = {};
	data['batchNo'] 	= batchNo;
	data['bgtYear'] 	= bgtYear;
	data['bgtType'] 	= bgtType;
	data['subType'] 	= subType;
	App.ajaxSubmit("budget/bgtImport/export.do?<%=WebConsts.FUNC_ID_KEY%>="+funcId,
			{data:data,async : false},
			function(data){
				flag = data.pass;					//导出操作是否 成功  或  出现异常报错
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
											//跳转至下载页面
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
				else
				{
					App.notyError("添加下载失败，请重试!");
					isPass =  false;
				}
			});
	return isPass;
}
function fileDownload(batchNo){
	var url="";
	var func_id = "";
	if("${orgType}" == '01'){
		func_id="02090109";
	}else{
		func_id="02090209";
	}
	var data = {};
	data["batchNo"] = batchNo;
	//Ajax检查文件是否存在校验
	App.ajaxSubmit("budget/bgtImport/checkDownFileExist.do?<%=WebConsts.FUNC_ID_KEY %>="+func_id,
		{data:data,async:false}, 
		function(data) {
			var checkFlag = data.checkFlag;
			var checkMsg  = data.checkMsg;
			if(checkFlag){
				var form = $("#bgtSumTotalInfo")[0];
				if("${orgType}" == '01'){
					
					form.action = "<%=request.getContextPath()%>/budget/bgtImport/sorceFileDownload.do?<%=WebConsts.FUNC_ID_KEY%>=02090110&batchNo="+data.batchNo;
				}else{
					form.action = "<%=request.getContextPath()%>/budget/bgtImport/sorceFileDownload.do?<%=WebConsts.FUNC_ID_KEY%>=02090210&batchNo="+data.batchNo;
				}
				form.submit();
			}else{
				//如果文件不存在，则不执行下载操作，并且将"文件不存在"信息报给用户
				App.notyError(checkMsg);
			}
		}
	);
}
</script>
</head>
<body>
<p:authFunc funcArray="020901,020902,02090201,02090202,02090104,02090204"/>
<form action="" method="post" id="bgtSumTotalInfo" >
<input type="hidden" id="orgType" value="${bean.orgType }" name="orgType"/>
<input type="hidden" id="orgType" value="${currentUri }" name="currentUri"/>
	<table id="approveChainTable">
		<tr class="collspan-control">
			<th colspan="4">
				预算导入数据查找
			</th>
		</tr>
		<tr>
			<td class="tdLeft">预算年份</td>	
			<td class="tdRight">
				<div class="ui-widget">
				<select id="bgtYearSelect" name="bgtYear">
					<option value="">请选择</option>
					<forms:codeTable tableName="ERP_BUDGET_SUM_TOTAL" selectColumn="DISTINCT BGT_YEAR"
						 valueColumn="BGT_YEAR" textColumn="BGT_YEAR" orderColumn="BGT_YEAR" 
						 orderType="ASC" selectedValue="${dataYear}" />
				</select>
				</div>
			</td>
			<td class="tdLeft" width="20%">预算子类型</td>
			<td class="tdRight" width="30%">
				<div class="ui-widget">
					<!-- 省行标签页  orgType=1省行 -->
					<c:if test="${'1' eq bean.orgType }">
						<select id="subTypeSelect" name="subType">
							<option value="">请选择</option>
							<option value="11" <c:if test="${'11' eq bean.subType}"> selected="selected" </c:if> >专项包</option>
							<option value="12" <c:if test="${'12' eq bean.subType  }"> selected="selected" </c:if> >省行统购资产</option>
						</select>
					</c:if>
					<!-- 分行标签页  orgType=2分行-->
					<c:if test="${'2' eq bean.orgType }">
						<select id="subTypeSelect" name="subType">
							<option value="">请选择</option>
							<option value="22" <c:if test="${'22' eq bean.subType}"> selected="selected" </c:if> >非专项包费用</option>
							<option value="21" <c:if test="${'21' eq bean.subType}"> selected="selected" </c:if> >非省行统购资产</option>
						</select>
					</c:if>
				</div>
			</td>
		</tr>
        <tr>
			<td class="tdLeft">处理状态</td>
			<td class="tdRight" colspan="3">
			<div class="ui-widget">
					<select id="bgtStatusSelect" name="status" >
					<option value="">请选择</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
						valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG" orderType="ASC"  conditionStr="CATEGORY_ID='BGT_IMPORT_FLAG'" selectedValue="${bean.status}"/>
					</select>
				</div>	
			</td>
		<tr>
		<tr>
			<td colspan="4">
				<c:if test="${bean.orgType eq '1'}">
					<p:button funcId="020901"  value="查找" />
					
				</c:if>
				<c:if test="${bean.orgType eq '2'}">
					<p:button funcId="020902"  value="查找" />
				</c:if>
				
				<input type="button" value="导入" onclick="gotoImportPage()" >
				<input type="button" value="重置"  onclick="resetAll()">
			</td>
		</tr>
	</table>
</form>
<form action="" method="post" id="budgetPlanListForm">
	<br/>	
	<table id="bgtSumTotalInfo" class="tableList">
		<tr>
			<th width="15%">预算年份</th>
			<th width="15%">预算类型</th>
			<th width="15%">预算子类型</th>
			<th width="">文件名称</th>
			<th width="15%">处理状态</th>
			<th width="30%">操作</th>
		</tr>
		<c:forEach items="${selectList}" var="bgtStBean" varStatus="vs">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td class="tdc">${bgtStBean.bgtYear}</td>
				<td class="tdc">${bgtStBean.bgtTypeName}</td>
				<td class="tdc">
					${bgtStBean.subTypeName}
				</td>
				<td class="tdc">
			 <a style="cursor:pointer;" onclick="fileDownload('${bgtStBean.batchNo}');">${bgtStBean.sourceFilename}</a> 
				</td>
				<td class="tdc">
					${bgtStBean.statusName}
				</td>
				<td class="tdc">
					<!-- 
					<c:if test="${bgtStBean.status eq '06'}">
						<input type="button" value="导出明细" onclick="exportData('${bgtStBean.batchNo}','${bgtStBean.bgtYear}','${bgtStBean.bgtType}','${bgtStBean.batchNo}');">
					</c:if>
					<c:if test="${!(bgtStBean.status eq '06')}">
						<input type="button" value="导出明细"  disabled="disabled" onclick="exportData('${bgtStBean.batchNo}','${bgtStBean.bgtYear}','${bgtStBean.bgtType}','${bgtStBean.batchNo}');">
					</c:if>
					 -->
					<c:choose>
						<c:when test="${bgtStBean.status eq '02' || bgtStBean.status eq '03' || bgtStBean.status eq '06' }">
							<input type="button" value="删除"  onclick="delBgtSumById('${bgtStBean.batchNo}');">
						</c:when>
						<c:otherwise>
							<input type="button" value="删除" disabled="disabled"  onclick="delBgtSumById('${bgtStBean.batchNo}');">
						</c:otherwise>
					</c:choose>
					
					<c:if test="${bgtStBean.status eq '01'}">
						<input type="button" value="导入详情" disabled="disabled" onclick="detail('${bgtStBean.batchNo}');">
					</c:if>
					<c:if test="${!(bgtStBean.status eq '01')}">
						<input type="button" value="导入详情" onclick="detail('${bgtStBean.batchNo}');">
					</c:if>
					
					
					
					<c:if test="${bgtStBean.status ne '03'}">
						<input type="button" value="提交" disabled="disabled" onclick="confirmBgtSum('${bgtStBean.batchNo}','${bgtStBean.subType}','${bgtStBean.org21Code}');" title="校验不通过，不允许执行数据迁移提交操作" >
					</c:if>
					<c:if test="${bgtStBean.status eq '03'}">
						<input type="button" value="提交" onclick="confirmBgtSum('${bgtStBean.batchNo}','${bgtStBean.subType}','${bgtStBean.org21Code}');">
					</c:if>
					
				</td>
			</tr>
		</c:forEach>
		
		<c:if test="${empty selectList}">
			<tr>
				<td colspan="6" style="text-align: center;"><span class="red">没有查询到符合条件的相关数据！</span></td>
			</tr>
		</c:if>
	</table>
</form>
<p:page/>

</body>
</html>