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

function doValidate(form)
{
	if(!$.checkDate("beginCreateDate","endCreateDate")){
		return false;
	}
	if(!$.checkDate("befDate","aftDate")){
		return false;
	}
	if($("#cntAmtgt").val()!==''&& $("#cntAmtgt").val()!=null)
	{
		if(!$.checkMoney($("#cntAmtgt").val())){
			App.notyError("合同金额区间[1]格式有误，请输入最多含两位小数的18位正浮点数！");
			return false;
		}
	}
	if($("#cntAmtlt").val()!==''&& $("#cntAmtlt").val()!=null)
	{
		if(!$.checkMoney($("#cntAmtlt").val())){
			App.notyError("合同金额区间[2]格式有误，请输入最多含两位小数的18位正浮点数！");
			return false;
		}
	}
	if($("#lxjegt").val()!==''&& $("#lxjegt").val()!=null)
	{
		if(!$.checkMoney($("#lxjegt").val())){
			App.notyError("审批金额区间[1]格式有误，请输入最多含两位小数的18位正浮点数！");
			return false;
		}
	}
	if($("#lxjelt").val()!==''&& $("#lxjelt").val()!=null)
	{
		if(!$.checkMoney($("#lxjelt").val())){
			App.notyError("审批金额区间[2]格式有误，请输入最多含两位小数的18位正浮点数！");
			return false;
		}
	}
	
	var cntR1=$("#cntAmtlt").val();
	var cntL1=$("#cntAmtgt").val();
	var cntR=new Number(cntR1);
	var cntL=new Number(cntL1);
		

	var spR1=$("#lxjelt").val();
	var spL1=$("#lxjegt").val();
	var spR=new Number(spR1);
	var spL=new Number(spL1);
	if (!$.isBlank(cntL1) && !$.isBlank(cntR1) && (cntL>cntR)) {
		App.notyError("合同金额区间格式有误，前面不能大于后面！");
		return false;
	}
	if (!$.isBlank(spL1) && !$.isBlank(spR1) && (spL>spR)) {
		App.notyError("审批金额区间格式有误，前面不能大于后面！");
		return false;
	}
	return true;
}

function beforeSubmit(form) 
{
	var selectVal=$("#cntType").val();	
	if(selectVal!="1")
	{
		$(".CntCostQueryTableId  select").val("");
		$(".CntCostQueryTableId  :text").val("");
		$(".CntCostQueryTableId  :selected").prop("selected",false);
	}
}

function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	$(".selectC").combobox();
 	//设置时间插件
	$( "#befDate,#aftDate,#beginDate,#endDate,#beginCreateDate,#endCreateDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
 	if(!$.isBlank($("#cntNum").val())){
 		$("#flag").removeAttr("disabled");
 	}
 	else{
 		$("#flag").removeAttr("checked");
 		$("#flag").attr("disabled","disabled");
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
	$(".CntCostQueryTableId").hide();
}

function addDownload(){	
	var orgFlag = '${con.orgFlag}';
	var cntNum = $("#cntNum").val();
	var cntType = $("#cntType").val();
	var providerName = $("#providerName").val();
	var befDate = $("#befDate").val();
	var aftDate = $("#aftDate").val();
	var createDept = $("#createDept").val();

	var stockNum = $("#stockNum").val();
	var psbh = $("#psbh").val();
	var lxlx = $("#lxlx").val();
	var qbh = $("#qbh").val();
	var cntAmtgt = $("#cntAmtgt").val();
	var cntAmtlt = $("#cntAmtlt").val();
	var lxjegt = $("#lxjegt").val();
	var lxjelt = $("#lxjelt").val();
	var isProvinceBuy = $("#isProvinceBuy").val();
	var projName = $("#projName").val();
	var cntMatrCode = $("#cntMatrCode").val();
	var feeType = $("#feeType").val();
	var feeSubType = $("#feeSubType").val();
	var wdjgId = $("#wdjgId").val();
	var cntDataFlag = $("#cntDataFlag").val();
	var beginCreateDate = $("#beginCreateDate").val();
	var endCreateDate = $("#endCreateDate").val();
	
	var isPass = false;
	var data = {};
	data['cntNum'] = cntNum;
	data['cntType'] = cntType;
	data['providerName'] = providerName;
	data['befDate'] = befDate;
	data['aftDate'] = aftDate;
	data['createDept'] = createDept;
	data['orgFlag'] = orgFlag;

	data['stockNum']=stockNum;
	data['psbh']=psbh;
	data['lxlx']=lxlx;
	data['qbh']=qbh;
	data['cntAmtgt']=cntAmtgt;
	data['cntAmtlt']=cntAmtlt;
	data['lxjegt']=lxjegt;
	data['lxjelt']=lxjelt;
	data['isProvinceBuy']=isProvinceBuy;
	data['projName']=projName;
	data['cntMatrCode']=cntMatrCode;
	data['feeType']=feeType;
	data['feeSubType']=feeSubType;
	data['wdjgId']=wdjgId;
	data['cntDataFlag']=cntDataFlag;
	data['beginCreateDate']=beginCreateDate;
	data['endCreateDate']=endCreateDate;
	//alert(cntAmtgt);
	
	App.ajaxSubmit("contract/query/addDownLoad.do?VISIT_FUNC_ID=03020602",
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

function gotoDtl(cntNum)
{
	var form = $("#dtlForm")[0];
	form.action='<%=request.getContextPath()%>/contract/query/mainpage.do?<%=WebConsts.FUNC_ID_KEY%>=03020606&cntNum='+cntNum;
	App.submit(form);
}

function selProvider()
{	
	$.dialog.open(
			'<%=request.getContextPath()%>/sysmanagement/provider/searchProvider.do?<%=WebConsts.FUNC_ID_KEY %>=010710',
			{
				width: "75%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"供应商选择",
			    id:"dialogCutPage",
				close: function(){
					var object = art.dialog.data('object'); 
					if(object){
						$("#providerName").val(object.providerName);
					}
					}		
			}
		 );
}

//查看操作日志
function waterBook(cntNum){
	var form = $("#dtlForm")[0];
	form.action="<%=request.getContextPath()%>/contract/query/book.do?<%=WebConsts.FUNC_ID_KEY%>=03020604&cntNum="+cntNum;
	App.submit(form);
}

$(document).ready(function ()
{
	$(".collspan-control-cnt").css("cursor","pointer").click(function(){
		
		if($("#hideshowflag").is(":hidden"))
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
		else
		{
			$(".CntCostQueryTableId").hide();
		}
		
		var siblings = $(this).siblings();
		siblings.filter(".collspan").toggle();
	
		$(this).attr("title",function (){
			if(this.title=="收缩"){
				return "展开";
			}else{
				return "收缩";
			}
		});
	}
	).attr("title","收缩");
	
	$("#cntType").trigger("change");
});

 
$(function(){
	//检查这个一级行下面的所有的审批链是否已经转移了
	var data = {};
	data['type'] = "00";
	App.ajaxSubmit("common/montAprvTransfer/aprvTransfer.do", {
		data : data,
		async : false
	}, function(data) {
		var resultMap= data.data;
		var flag = resultMap.flag;
		var msg = resultMap.msg;
		if(flag == false){
			eval(msg);
		}

	});
});

function showBox(obj){
	if($(obj).val() !==null && $(obj).val().length !=0){
		$("#flag").removeAttr("disabled");
	}else{
		$("#flag").attr("disabled","disabled");
		$("#flag").removeAttr("checked");
	}
	
}
function exportData(cntNum){
	var isPass=true;
	var data = {};
	data['cntNum'] =  cntNum;
	App.ajaxSubmit("contract/query/exportData.do?<%=WebConsts.FUNC_ID_KEY %>=03020614",
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
							        		$(form).append("<input type='hidden' name='upStepUrl' id='upStepUrl'>");			//为提交跳转的form添加input框记录传递参数upStepUrl(用于查询FuncId)
							        		$(form).append("<input type='hidden' name='upStepParams' id='upStepParams'>");	//为提交跳转的form添加input框记录传递参数upStepParams(用于当当前页面有查询过滤参数时)
							        		$("form #upStepUrl").val(upStepUrl);											//添加传递参数的值
							        		$("form #upStepParams").val(upStepParams);
											form.action='<%=request.getContextPath()%>/reportmgr/importAndexport/query/getExportReportList.do?<%=WebConsts.FUNC_ID_KEY%>=060101&funcId=010302';
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
					App.notyError("添加下载失败，可能是因为表里没有该条数据，请检查后重试!");
					isPass =  false;
				}
			});
	return isPass;
}
//项目弹出页
function projOptionPage(){
	var orgFlag="${con.orgFlag}";
	var url="<%=request.getContextPath()%>/projmanagement/projectMgr/projOptionPage.do?<%=WebConsts.FUNC_ID_KEY %>=021004&orgFlag="+orgFlag+"&isContract="+0;
	$.dialog.open(
			url,
			{
				width: "50%",
				height: "50%",
				lock: true,
			    fixed: true,
			    title:"项目选择",
			    id:"dialogCutPage",
				close: function(){
					var proObj = art.dialog.data('projectObj'); 
					if(proObj){
						$("#projName").val(proObj.projName);
					}
				}
			}
		 );
}
</script>
</head>

<body  >
<p:authFunc funcArray="030206,03020601,03020604,0302060501,03020611,03020612,03020613"/>
<form action="" method="post" id="dtlForm"></form>
<form method="post" id="queryForm" action="">
	<table>
		<tr class="collspan-control-cnt">
			<th colspan="4">
				合同基本信息查询
			</th>
		</tr>
		<tr class="collspan" id="hideshowflag">
			<td class="tdLeft">合同号</td>
			<td class="tdRight" colspan="3">
				<input type="text" id='cntNum' name="cntNum" value="${con.cntNum}" class="base-input-text" maxlength="80" onkeyup="showBox(this);"/>
				&nbsp;&nbsp;
				<input type="checkbox" disabled="disabled" value="1" name="flag" id="flag" <c:if test="${con.flag == 1}">checked="checked"</c:if>  />是否关联查询
			</td>
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
			
			<td class="tdLeft">合同状态</td>
			<td class="tdRight">
			<div class="ui-widget">
				<select class="selectC" id="cntDataFlag" name="cntDataFlag">
					<option value="">--请选择--</option>						
					<forms:codeTable tableName="SYS_SELECT"   selectColumn="PARAM_VALUE,PARAM_NAME"
					 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG" 
					 conditionStr="CATEGORY_ID = 'CNT_DATE_FLAG'"
					 orderType="ASC" selectedValue="${con.cntDataFlag}"/>	
				</select>
			</div>
			</td>
		
		</tr>
		
		<tr class="CntCostQueryTableId"  style="display: none;">
			<td class="tdLeft">费用类型</td>
			<td class="tdRight">
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
			<td class="tdLeft" id="feeSubTypeTdLeft" > 费用子类型</td>
			<td class="tdRight" id="feeSubTypeTdRight">
						<div class="ui-widget">
							<select id="feeSubType" name="feeSubType" class="selectC" >
								<option value="">--请选择--</option>						
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
								 conditionStr="CATEGORY_ID = 'CNT_FEE_SUB_TYPE'"
								 orderType="ASC" selectedValue="${con.feeSubType }"/>
							</select>
						</div>
			</td>
		</tr>
		
		<tr class="collspan">
			<td class="tdLeft">采集编号</td>
			<td class="tdRight">
				<input type="text" id="stockNum" name="stockNum" value="${con.stockNum}" class="base-input-text" maxlength="80"/>
			</td>
			<td class="tdLeft">评审编号</td>
			<td class="tdRight">
				<input type="text" name="psbh" id="psbh" value="${con.psbh}" class="base-input-text" maxlength="80"/>
			</td>
		</tr>
		
		<tr class="collspan">
			<td class="tdLeft">审批类别</td>
			<td class="tdRight">
				<div class="ui-widget">
							<select id="lxlx" name="lxlx" class="selectC" >
								<option value="">--请选择--</option>						
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
								 conditionStr="CATEGORY_ID = 'CNT_APPROVE_TYPE'"
								 orderType="DESC" selectedValue="${con.lxlx }" />
							</select>
						</div>
			</td>
			<td class="tdLeft">签报文号</td>
			<td class="tdRight">
				<input type="text" name="qbh" id="qbh" value="${con.qbh}" class="base-input-text" maxlength="80"/>
			</td>
		</tr>
		<tr class="collspan">
			<td class="tdLeft">合同金额区间</td>
			<td class="tdRight">
				<input type="text" name="cntAmtgt" id="cntAmtgt" value="${con.cntAmtgt}" class="base-input-text" maxlength="80" style="width:125px;"/>
				至
				<input type="text" name="cntAmtlt" id="cntAmtlt" value="${con.cntAmtlt}" class="base-input-text" maxlength="80" style="width:125px;"/>
			</td>
			<td class="tdLeft">审批金额区间</td>
			<td class="tdRight">
				<input type="text" name="lxjegt" id="lxjegt" value="${con.lxjegt}" class="base-input-text" maxlength="80" style="width:125px;"/>
				至
				<input type="text" name="lxjelt" id="lxjelt" value="${con.lxjelt}" class="base-input-text" maxlength="80" style="width:125px;"/>
			</td>
		</tr>
		
		
		<tr class="collspan">
			<td class="tdLeft">合同录入时间区间</td>
			<td class="tdRight">
				<input type="text" id="beginCreateDate" name="beginCreateDate" valid maxlength='10' readonly="readonly" value="${con.beginCreateDate}" class="base-input-text" style="width:125px;"/>
				至
				<input type="text" id="endCreateDate" name="endCreateDate" valid maxlength='10' readonly="readonly" value="${con.endCreateDate}" class="base-input-text" style="width:125px;"/>
			</td>
			<td class="tdLeft">签订日期区间</td>
			<td class="tdRight">
				<input type="text" id="befDate" name="befDate" valid maxlength='10' readonly="readonly" value="${con.befDate}" class="base-input-text" style="width:125px;"/>
				至
				<input type="text" id="aftDate" name="aftDate" valid maxlength='10' readonly="readonly" value="${con.aftDate}" class="base-input-text" style="width:125px;"/>
			</td>
		</tr>
		<tr class="collspan">
			<td class="tdLeft">创建机构</td>
			<td class="tdRight" >
				<%-- <input type="text" name="createDept" value="${con.createDept}" class="base-input-text"/> --%>
				<forms:OrgSelPlugin suffix="c" rootNodeId="${user.org1Code}" initValue="${con.createDept}" jsVarGetValue="createDept" parentCheckFlag="false"/>
			</td>
			<td class="tdLeft">是否省行统购</td>
			<td class="tdRight">
				<div class="ui-widget" style="display:inline">
							<select id="isProvinceBuy" name="isProvinceBuy" class="selectC" onchange=""  >
								<option value="">--请选择--</option>						
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
								 conditionStr="CATEGORY_ID = 'CNT_IS_YN'"
								 orderType="ASC" selectedValue="${con.isProvinceBuy }"/>
							</select>
				</div>
			</td>
		</tr>
		
		<tr class="collspan">
			<td class="tdLeft">合同项目名称</td>
			<td class="tdRight">
				<input type="text" id="projName" name="projName" value="${con.projName}" class="base-input-text" maxlength="200"/>
				<a onclick="projOptionPage()">
					<img border="0px;" src="<%=request.getContextPath()%>/common/images/search1.jpg" alt="查看合同项目" style="cursor:pointer;vertical-align: middle;margin-left:0px;heigth:30px;width:30px;"/>
				</a>
			</td>
			<td class="tdLeft">合同物料编码</td>
			<td class="tdRight">
				<input type="text" id="cntMatrCode" name="cntMatrCode" value="${con.cntMatrCode}" class="base-input-text" maxlength="80"/>
			</td>
		</tr>
		<tr class="collspan">
			<td class="tdLeft">核算码</td>
			<td class="tdRight">
				<input type="text" id="conCglCode" name="conCglCode" value="${con.conCglCode}" class="base-input-text" maxlength="200" />
			</td>
			<td class="tdLeft">供应商</td>
			<td class="tdRight">
				<input type="text" id="providerName" name="providerName" value="${con.providerName}" class="base-input-text" maxlength="300"/>
				<a onclick="selProvider()">
					<img border="0px;" src="<%=request.getContextPath()%>/common/images/search1.jpg" alt="查看供应商" style="cursor:pointer;vertical-align: middle;margin-left:0px;heigth:30px;width:30px;"/>
				</a>
			</td>
		</tr>
		<%-- 
		<tr class="CntCostQueryTableId"  style="display: none;">
			<td class="tdLeft">网点机构</td>
			<td class="tdRight" colspan="3">
			<forms:OrgSelPlugin suffix="b" rootNodeId="${user.org1Code}" initValue="${con.wdjgId}" jsVarGetValue="wdjgId" parentCheckFlag="false"/>
			</td>
		</tr>
		--%>
		<tr class="collspan">
			<td colspan="4" class="tdWhite">
				<c:if test="${con.orgFlag=='1'}">
					<p:button funcId="03020611" value="查找"/>
				</c:if>
				<c:if test="${con.orgFlag=='2'}">
					<p:button funcId="03020612" value="查找"/>
				</c:if>
				<c:if test="${con.orgFlag=='3'}">
					<p:button funcId="03020613" value="查找"/>
				</c:if>
				<input type="button" value="重置" onclick="resetAll();">
				<input type="button" value="添加下载" onclick="addDownload();">
			</td>
		</tr>
	</table>	
<!-- 	<table  > -->
<!-- 		<tr class="collspan-control" > -->
<!-- 			<th colspan="4"> -->
<!-- 				合同费用信息查询 -->
<!-- 			</th> -->
<!-- 		</tr> -->
		
<!-- 		<tr> -->
<!-- 			<td class="tdLeft">甲方ID</td> -->
<!-- 			<td class="tdRight"> -->
<%-- 				<input type="text" name="jfId" value="${con.jfId}" class="base-input-text" maxlength="80"/> --%>
<!-- 			</td> -->
<!-- 			<td class="tdLeft">甲方名称</td> -->
<!-- 			<td class="tdRight"> -->
<%-- 				<input type="text" name="jf" value="${con.jf}" class="base-input-text" maxlength="80"/> --%>
<!-- 			</td> -->
<!-- 		</tr> -->
<!-- 		<tr> -->
<!-- 			<td class="tdLeft">乙方ID</td> -->
<!-- 			<td class="tdRight"> -->
<%-- 				<input type="text" name="yfId" value="${con.yfId}" class="base-input-text" maxlength="80"/> --%>
<!-- 			</td> -->
<!-- 			<td class="tdLeft">乙方名称</td> -->
<!-- 			<td class="tdRight"> -->
<%-- 				<input type="text" name="yf" value="${con.yf}" class="base-input-text" maxlength="80"/> --%>
<!-- 			</td> -->
<!-- 		</tr> -->
		
<!-- 		<tr> -->
<!-- 			<td colspan="4" class="tdWhite"> -->
<%-- 				<p:button funcId="030206" value="查找"/> --%>
<!-- 				<input type="button" value="重置" onclick="resetAll();"> -->
<!-- 				<input type="button" value="添加下载" onclick="addDownload();"> -->
<!-- 			</td> -->
<!-- 		</tr> -->
<!-- 	</table> -->
	
	
	
	<br>
	<br>
	<table class="tableList">
			<tr>
				<th width='13%'>合同号 </th>
				<th width='10%'>合同事项    </th>
				<th width='10%'>合同类型    </th>
				<th width='9%'>供应商  </th>
				<th width='10%'>合同金额 （不含税）   </th>
				<th width='5%'>税额   </th>
				<th width='5%'>签订日期    </th>
				<th width='10%'>项目名称  </th>
				<th width='10%'>合同状态  </th>
				<th width='10%'>录入责任中心</th>
				<th width='12%'>录入时间    </th>
				<th width='6%'>操作</th>
			</tr>
		<c:forEach items="${cntList}" var="cntItem">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td class="tdc">${cntItem.cntNum}</td>
				<td class="tdc"><forms:StringTag length="20" value="${cntItem.cntName}"/></td>
				<td class="tdc">${cntItem.cntTypeName}<c:if test="${cntItem.isOrder == 1}">/非订单</c:if><c:if test="${cntItem.isOrder == 0}">/订单</c:if></td>
				<td>${cntItem.providerName}</td>
				<td class="tdr"><fmt:formatNumber type="number" value="${cntItem.cntAmt}" minFractionDigits="2"/></td>
				<td class="tdr"><fmt:formatNumber type="number" value="${cntItem.cntTaxAmt}" minFractionDigits="2"/></td>
				<td class="tdc">${cntItem.signDate}</td>
				<td class="tdc">${cntItem.projName}</td>
				<td>${cntItem.dataFlagName}</td>
				<td>${cntItem.createDeptName}</td>
				<td class="tdc">${cntItem.createDate}</td>
				<td>
					<input type="button" value="详情" onclick="gotoDtl('${cntItem.cntNum}');">
					<input type="button" value="日志" onclick="waterBook('${cntItem.cntNum}');"/>
					<input type="button" value="下载物料行" onclick="exportData('${cntItem.cntNum}')"/>
				</td>
			</tr>
		</c:forEach>
		
		<c:if test="${empty cntList}">
			<tr>
				<td colspan="11" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
	</table>
</form>
<p:page/>
</body>
</html>