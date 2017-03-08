<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
	.tableList td{
		text-align: center;
	}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>存量业务数据导入</title>


<script type="text/javascript">
	/*
	**	页面初始化执行加载
	*/
	function pageInit(){
		App.jqueryAutocomplete();
	}
	
	/*
	**	平台上传文件控件(Ajax上传)
	*/
	function uploadFileFun( obj ){
		// 存放附件列表对象
		var fileListObj = "#attachmentsView";
		// 配置(文件上传存储的路径为input[name='filePath']的value,上传文件的原始名称为input[name='fileRealName']的value)
			//在config中配置uploadMode:"single"控制只能上传一个附件
		var config = {filePath:"filePath",fileName:"fileRealName",uploadMode:"single",contextPath:'<%=request.getContextPath()%>',passSuffix:"xls,XLS,XLs,Xls,xlsx,XLSX,XLsx,Xlsx"};
		// 上传
		UploadUtils.uploadAttachment(fileListObj,config);
	};
	
	/*
	**	【"下载"按钮】：下载导入初始合同/付款Excel模板(参数：导入Excel的类型)		————1：合同数据、2：付款数据
	*/
	function downTempFile(tempType){
		var form = $("#downloadForm")[0];
		form.action = "<%=request.getContextPath()%>/reportmgr/dataMgr/datamigrate/downloadDMTemp.do?<%=WebConsts.FUNC_ID_KEY%>=060200&downloadFileType="+tempType;
		form.submit();
	}
	
	/*
	**	【"导入"按钮】：跳转至Excel数据上传页面
	*/
	function toUploadTempPage(){
		var form = $("#downloadForm")[0];
		form.action = "<%=request.getContextPath()%>/reportmgr/dataMgr/datamigrate/toUploadDataPage.do?<%=WebConsts.FUNC_ID_KEY%>=060202&testType=0";
		form.submit();
	}
	
	//【测试的导入使用！！】
	function toUploadTempPageForTest(){
		var form = $("#downloadForm")[0];
		form.action = "<%=request.getContextPath()%>/reportmgr/dataMgr/datamigrate/toUploadDataPage.do?<%=WebConsts.FUNC_ID_KEY%>=060202&testType=1";
		form.submit();
	}
	
	
	/*
	**	【"结果"按钮】查询导入批次(包含多个导入任务Task)的结果(进一步可查询：包含Task详情+该Task的错误校验信息)
	*/
	function queryImpTaskByBatchNo(){
		var form = $("#dataMigrateForm")[0];
		var batchId = '${udcBean.batchNo}';				//根据导入批次查询导入任务
		
		//1.添加提交请求时传递的请求参数
			//当前页面的uri(无项目根路径)；	可在Controller层添加代码：WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		$(form).append("<input type='hidden' name='upStepUrl' id='upStepUrl' value='${currentUri}'>");			//为提交跳转的form添加input框记录传递参数upStepUrl(用于查询FuncId)
			//当前页面需要的过滤参数(当无参数时为空)
		$(form).append("<input type='hidden' name='upStepParams' id='upStepParams' value=''>");					//为提交跳转的form添加input框记录传递参数upStepParams(用于当当前页面有查询过滤参数时)
		
		//2.添加查询过滤参数：根据参数做查询跳转(参数1：imOutportType=0[0：导入数据、1：导出数据] ；参数2：batchId=? [导入任务对应的导入批次号batchNo])
		$(form).append("<input type='hidden' name='imOutportType' id='imOutportType' value='0'>");
		$(form).append("<input type='hidden' name='queryByOrg1' id='queryByOrg1' value='T'>");
		$(form).append("<input type='hidden' name='batchId' id='batchId' value='"+batchId+"'>");
		//3.设置url并提交请求
		form.action = '<%=request.getContextPath()%>/reportmgr/importAndexport/query/getExportReportList.do?<%=WebConsts.FUNC_ID_KEY%>=060101';
<%-- 	form.action = '<%=request.getContextPath()%>/reportmgr/importAndexport/query/getExportReportList.do?<%=WebConsts.FUNC_ID_KEY%>=060101&imOutportType=0&batchId='+batchId+'&queryByOrg1=T'; --%>
		form.submit();
	}
	
	/*
	**	【"明细"按钮】：查看导入合同的列表,可选中具体的合同查看其详细信息(包括付款数据等)
	*/
	function importDataList(){
		var form = $("#dataMigrateForm")[0];
		$(form).append("<input type='hidden' name='batchNo' id='batchNo' value='${udcBean.batchNo}'>");			//添加过滤参数batchNo
		form.action = '<%=request.getContextPath()%>/reportmgr/dataMgr/datamigrate/dataDetail.do?<%=WebConsts.FUNC_ID_KEY %>=060207';
		form.submit();
	}
	
	/*
	**	【"删除"按钮】删除当前导入批次及相关的导入数据(正常数据+校验错误数据)	[内部逻辑为更改其dataFlag状态并新增更新时间+更新操作者]
	*/
	function dataDeleteBatchAndRel(){
		var batchNo = '${udcBean.batchNo}';
		var url = "<%=request.getContextPath()%>/reportmgr/dataMgr/datamigrate/dataDestroy.do?<%=WebConsts.FUNC_ID_KEY %>=060206&batchNo=" + batchNo;
		var form = $("#dataMigrateForm")[0];
		form.action = url;
		$( "<div>确认删除【"+batchNo+"】<br/>导入批次的相关数据吗?</div>" ).dialog({
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
	
	/*
	**	SK:数据【确认】操作前进行Ajax数据检查
	*/
	function dataVerifyCheck(){
		var varifyResult = false;		//校验结果：初始值为false
		var url = "reportmgr/dataMgr/datamigrate/dataVerifyCheckAjax.do?VISIT_FUNC_ID=060208";
		//Ajax进行数据确认操作前的校验
		App.ajaxSubmit(
				url, 
				{
					async:false
				}, 
				function(data)
				{
					var result=data.result;
					if('TRUE'==result)
					{
						//ajax返回的校验结果为通过时，varifyResult的值更改为true(用于该JS方法的返回值)
						varifyResult = true;	
					}
				}
		);
		return varifyResult;
	}
	
	/*
	**	【"确认"按钮】:数据确认功能操作
	*/
	function dataVerify()
	{
		if(!dataVerifyCheck())
		{
			App.notyError("您所在的一级分行待迁移数据中存在异常数据，需要修改后重新上传，方可进行数据迁移[确认]操作.");
			return false;
		}
		var form = $("#dataMigrateForm")[0];
		form.action = "<%=request.getContextPath()%>/reportmgr/dataMgr/datamigrate/dataVerify.do?<%=WebConsts.FUNC_ID_KEY%>=060205";
		form.submit();
	}
	
</script>
</head>
<body>
<p:authFunc funcArray="060201,060202"/>
<form id="downloadForm" method="post"></form>
<form id="dataMigrateForm" method="post"></form>
<form action="" method="post" id="dataMigrateForm">
	<p:token/>
	<br/>
	<table class="tableList">
		<tr>
			<th width="50%">数据导入模板</th>
			<th width="50%">操作</th>
		</tr>
		<tr>
			<td style="height: 36px">合同数据</td>
			<td>
				<input type="button" value="模板下载" onclick="downTempFile(1)">
			</td>
		</tr>
		<tr>
			<td style="height: 36px">付款数据</td>
			<td>
				<input type="button" value="模板下载" onclick="downTempFile(2)"> 
			</td>
		</tr>
	</table>
	
	<br/>
	<table class="tableList">
<!-- 		<tr><td colspan="5">测试校验导入表头：<input type="button" value="调至导入页面" onclick="toUploadTempPageForTest()"></td></tr> -->
		<tr>
			<th rowspan="2" width="20%">一级行名称</th>
			<th rowspan="2" width="15%">数据状态</th>
			<th colspan="2" width="30%">导入结果</th>
			<th rowspan="2" width="35%">操作</th>
		</tr>
		<tr>
			<th width="15%">表头错误数据</th>
			<th width="15%">主体错误数据</th>
		</tr>
		<tr>
			<td title="当前导入批次号：${udcBean.batchNo}" style="height: 36px">${org1Name}</td>
			<td>
				<c:choose>
					<c:when test="${udcBean.dataFlag=='09' || empty udcBean}">待导入</c:when>
					<c:when test="${udcBean.dataFlag=='00'}">导入任务进行中</c:when>
					<c:when test="${udcBean.dataFlag=='01'}">导入完成，待确认</c:when>
				</c:choose>
			</td>
			<td >
				<c:if test="${udcBean.dataFlag == '09'}"> -- </c:if>
				<c:if test="${udcBean.dataFlag != '09' && udcBean.errHeadCount == 0}"> 无</c:if>
				<c:if test="${udcBean.errHeadCount != 0}"> ${udcBean.errHeadCount} Rows  </c:if>
			</td>
			<td >
				<c:if test="${udcBean.dataFlag == '09'}"> -- </c:if>
				<c:if test="${udcBean.dataFlag != '09' && udcBean.errInfoCount == 0}"> 无</c:if>
				<c:if test="${udcBean.errInfoCount != 0}"> ${udcBean.errInfoCount} Rows  </c:if>
			</td>
			<td>
				<c:choose>
					<c:when test="${udcBean.dataFlag=='09' || empty udcBean}">
						<input type="button" value="导入" onclick="toUploadTempPage()">
						<input type="button" value="结果" disabled="disabled" onclick="queryImpTaskByBatchNo();">
						<input type="button" value="明细" disabled="disabled" onclick="importDataList();">
						<input type="button" value="删除" disabled="disabled" onclick="dataDeleteBatchAndRel();">
						<input type="button" value="确认" disabled="disabled" onclick="dataVerify();">
					</c:when>
					<c:when test="${udcBean.dataFlag=='00'}">
					    <input type="button" value="导入" disabled="disabled" onclick="toUploadTempPage()">
						<input type="button" value="结果" disabled="disabled" onclick="queryImpTaskByBatchNo();">
						<input type="button" value="明细" disabled="disabled" onclick="importDataList();">
						<input type="button" value="删除" disabled="disabled" onclick="dataDeleteBatchAndRel();">
						<input type="button" value="确认" disabled="disabled" onclick="dataVerify();">
					</c:when>
					<c:when test="${udcBean.dataFlag=='01'}">
						<input type="button" value="导入" disabled="disabled" onclick="toUploadTempPage()">
						<input type="button" value="结果" onclick="queryImpTaskByBatchNo();">
						<input type="button" value="明细" onclick="importDataList();">
						<input type="button" value="删除" onclick="dataDeleteBatchAndRel();">
						<c:if test="${(udcBean.errInfoCount + udcBean.errHeadCount) == 0}">
							<input type="button" value="确认" onclick="dataVerify();">
						</c:if>
						<c:if test="${(udcBean.errInfoCount + udcBean.errHeadCount) != 0}">
							<input type="button" value="确认" disabled="disabled"  onclick="dataVerify();" 
									title="导入批次[${udcBean.batchNo}]存在校验错误行[${udcBean.errInfoCount + udcBean.errHeadCount}行]，请检查">
						</c:if>
					</c:when>
				</c:choose>
			</td>
		</tr>
	</table>
</form>

</body>
</html>