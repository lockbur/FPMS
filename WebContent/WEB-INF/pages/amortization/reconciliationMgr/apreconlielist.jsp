<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>AP发票付款对账</title>
<script type="text/javascript">
/**
 * 页面初始化时执行
 */
function pageInit()
{
	App.jqueryAutocomplete();
	$("#checkResultSelect").combobox();
	$("#resultSelect").combobox();
	
	//表格合并参数描述：表格ID、合并开始行、合并结束行、合并开始列、合并结束列、null、null、null、null
// 	var tc = new TableCombineObj("listTab", 3, null, 1, 2, null, null, null, null);
// 	tc.rowCombine();
}

/**
 * 重置清空所有选中信息
 */
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

var scrollTag="";
function scrollTable(){
	$("#scrollTab").css("overflow-X", "scroll").height(16).width($("#listDiv").width());			//表格右边框无问题，但滚动条样式有问题
// 	$("#scrollTab").css("overflow-X", "scroll").height(16).width($("#listDiv").width()+20);			//滚动条样式无问题，但表格右边框线消失
	$("#scrollTab").html($("#listDiv").html()); //并给DIV2层填充内容
	
    $("#scrollTab").scroll(function () {
        $("#listDiv")[0].scrollLeft = $("#scrollTab")[0].scrollLeft; //拉动DIV2层滚动条，DIV1层滚动条同步被改变
        $("#hideDiv")[0].scrollLeft = $("#scrollTab")[0].scrollLeft;
//         var x = $("#listDiv").offset().right;
//         var table = $("#listTab").width();
//          $("#listTabFirstTr").css("right",parseInt($("#scrollTab")[0].scrollLeft));
//         console.info("div的宽度是"+$("#listDiv")[0].clientWidth+"表格宽度是"+table+"以前的位置"+x+"滑动了"+ $("#scrollTab")[0].scrollLeft);
        scrollTag = this.scrollTop;
         $("#hideDiv")[0].scrollLeft = $("#listDiv")[0].scrollLeft;
        if(scrollTag == 0){
        	//说明是横向滚动
        	$("#hideDiv").css({"position":"absolute"});
        	$("#hideDiv").css({"top":127});
        	$("#hideDiv").css({"position":"fixed"});
        }else{
        	//说明是纵向滚动
        	$("#hideDiv").css({"position":"fixed"});
        }
        $("#hideDiv")[0].scrollLeft = $("#scrollTab")[0].scrollLeft;
    });
    $("#listDiv").scroll(function () {
    	 $("#hideDiv")[0].scrollLeft = $("#listDiv")[0].scrollLeft;
    });
}

/**
 * 	分割发送和接收的值
 * 		方法描述：将TD中字符串根据'$$'进行分割并添加换行标签，最终jQuery方式写入到TD中
 *		方法参数：TD对象、TD的html内容
 */
function splitSendAndReceivedValue(obj,strValue){
	var valueArray = strValue.split('$$');
	//组合TD中的最终字符串(包括内容+html标签<br/>)
	var resultStr = "";
	for(var i=0;i<valueArray.length;i++){
		resultStr += valueArray[i]+'<br/>';
	}
	//console.info(resultStr);
	//往TD中写入最终字符串
	$(obj).html(resultStr);
}

$(function(){
	scrollTable();
	//初始化表格头
    var tableWidth = $("#listTab").width();
	$("#hideDiv").width($("#listDiv")[0].clientWidth);
	$("#hideTab").width(tableWidth);
	
	//对每个需要进行内容分割的TD(样式为class='splitValue')，调用splitSendAndReceivedValue进行分割并写入内容
	$(".splitValue").each(function(){
		splitSendAndReceivedValue(this,$(this).html());		
	});
})

</script>
</head>

<body>

<script type="text/javascript">

	$(function(){
		//自动将Table表格头克隆$($(this).context).clone()复制后添加到替换表格头(#hideTab)的DIV    （此操作免去重复写表格头的代码）
		$("#listTab").find(".tbHead").each(function(){
			$("#hideTab").append($($(this).context).clone());
		});
		
		$("#scrollTab").css("top",$(window).height()-16+"px");			//动态设置DIV滚动条固定位置
		var obj = document.getElementById("listTab");
		var _getHeight = obj.offsetTop;
		var scrollTag2 ="";
		$(window).scroll(function(){
			var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
// 			console.info("的方式的发送到"+scrollTop+":"+_getHeight);
			if(parseInt(scrollTop) <parseInt(_getHeight)){  
				$("#hideDiv").hide();
			}else{
				 $("#hideDiv").show();
			}
// 			console.info("移动了"+$("#scrollTab")[0].scrollLeft);
			 $("#hideDiv")[0].scrollLeft = $("#listDiv")[0].scrollLeft;
			 
			var scTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
			if(scTop + $(window).height() > $("body")[0].scrollHeight - 18 ){
// 				alert('滚动到底了！！');
				$("#scrollTab").css("overflow-X","hidden").height(0);
				$("#listDiv").css("overflow-X","scroll");
			}else{
				$("#scrollTab").css("overflow-X","scroll").height(16);
				$("#listDiv").css("overflow-X","hidden");
			}
  			 scrollTag2 = this.scrollTop;
			 if(scrollTag2 == 0){ 
		        	//说明是横向滚动
	        	$("#hideDiv").css({"position":"absolute"});
	        	$("#hideDiv").css({"position":"fixed"});
	        }else{
	        	//说明是纵向滚动
	        	$("#hideDiv").css({"position":"fixed"});
	        }
		});
	})
</script>

<p:authFunc funcArray="040801"/>
<form action="" method="post" id="apReconliationForm">
	<table id="queryConditionArea">
		<tr class="collspan-control">
			<th colspan="4" >
				查询条件
			</th>
		</tr>
		<tr>
			<td class="tdLeft">合同编号</td>
			<td class="tdRight">
					<input type="text" id="cntNum" name="cntNum" value="${apBean.cntNum}" class="base-input-text" maxlength="80">
			</td>
			
			<td class="tdLeft">付款单号</td>
			<td class="tdRight">
				<input type="text" id="payId" name="payId" value="${apBean.payId}" class="base-input-text" maxlength="20">
			</td>
		</tr>
		
		<tr>
			<td class="tdLeft">校验结果</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="checkResultSelect" name="checkResult" >
						<option value="">请选择</option>
						<option value="未返回" <c:if test="${apBean.checkResult == '未返回'}">selected="selected"</c:if> >未返回</option>
						<option value="校验失败" <c:if test="${apBean.checkResult == '校验失败'}">selected="selected"</c:if> >校验失败</option>
						<option value="返回一致" <c:if test="${apBean.checkResult == '返回一致'}">selected="selected"</c:if> >返回一致</option>
						<option value="返回不一致" <c:if test="${apBean.checkResult == '返回不一致'}">selected="selected"</c:if> >返回不一致</option>
					</select>
				</div>
			</td>
			
			<td class="tdLeft">发票结果</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="resultSelect" name="apResult">
						<option value="">请选择</option>
						<option value="未返回" <c:if test="${apBean.apResult == '未返回'}">selected="selected"</c:if> >未返回</option>
						<option value="返回一致" <c:if test="${apBean.apResult == '返回一致'}">selected="selected"</c:if> >返回一致</option>
						<option value="返回不一致" <c:if test="${apBean.apResult == '返回不一致'}">selected="selected"</c:if> >返回不一致</option>
					</select>
				</div>
			</td>
		</tr>

		<tr>
			<td colspan="4" class="tdBottom tdWhite">
				<p:button funcId="040801" value="查询"/>
<!-- 				<input type="button" value="查询" onclick="getReconlieInfo();"> -->
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br/>

	<div id="listDiv" style="width:100%;overflow-X:scroll;float: right">
	<table class="tableList" id="listTab" style="table-layout: fixed;">
<%-- 	<caption>AP发票付款对账信息列表</caption> --%>
		<!-- 第一个TR是虚假的，用于处理IE浏览器中不识别TD的width设置(并且控制表格头的固定宽度)！！05-13 -->
		<tr style="height: 1px;font-size: 1px;line-height: 1px;background-color: white;" class="tbHead">
			<td style="width: 60px;height: 1px;border-top-style: none;padding: 0px;" ></td>
			<td style="width: 110px;height: 1px;border-top-style: none;border-left-style: none;border-right-style: none;padding: 0px;"></td>
			<td style="width: 110px;height: 1px;border-top-style: none;border-left-style: none;border-right-style: none;padding: 0px;"></td>
			<td style="width: 250px;height: 1px;border-top-style: none;border-left-style: none;border-right-style: none;padding: 0px;"></td>
			<td style="width: 110px;height: 1px;border-top-style: none;border-left-style: none;padding: 0px;"></td>
			<td style="width: 250px;height: 1px;border-top-style: none;border-left-style: none;padding: 0px;"></td>
			<td style="width: 110px;height: 1px;border-top-style: none;border-left-style: none;border-right-style: none;padding: 0px;"></td>
			<td style="width: 110px;height: 1px;border-top-style: none;border-left-style: none;border-right-style: none;padding: 0px;"></td>
			<td style="width: 150px;height: 1px;border-top-style: none;border-left-style: none;border-right-style: none;padding: 0px;"></td>
			<td style="width: 110px;height: 1px;border-top-style: none;border-left-style: none;border-right-style: none;padding: 0px;"></td>
			<td style="width: 110px;height: 1px;border-top-style: none;border-left-style: none;padding: 0px;"></td>
			<td style="width: 140px;height: 1px;border-top-style: none;border-left-style: none;padding: 0px;"></td>
		</tr>
		<tr style="overflow:hidden;" class="tbHead collspan-control"  >
			<th nowrap="nowrap" rowspan="3" style="text-align: center;">序号</th>
			<th nowrap="nowrap" rowspan="3" style="text-align: center;">合同编号</th>
			<th nowrap="nowrap" rowspan="3" style="text-align: center;">付款单号</th>
			<th nowrap="nowrap" colspan="2" rowspan="2" style="text-align: center">ERP信息</th>
			<th nowrap="nowrap" colspan="7" style="text-align: center">FMS信息</th>
		</tr>
		<tr style="overflow: hidden;"class="tbHead">
			<th nowrap="nowrap" colspan="3" style="text-align: center">校验文件</th>
			<th nowrap="nowrap" colspan="4" style="text-align: center">AP发票信息</th>
		</tr>
		<tr style="overflow: hidden;"class="tbHead">
			<th nowrap="nowrap" style="text-align: center;">
				发送值
			</th>
			<th nowrap="nowrap" style="text-align: center;">发送时间</th>
			<th nowrap="nowrap" style="text-align: center;">
				返回值
			</th>
			<th nowrap="nowrap" style="text-align: center;">返回结果</th>
			<th nowrap="nowrap" style="text-align: center;">ERP处理时间</th>
			<th nowrap="nowrap" style="text-align: center;">
				返回值
			</th>
			<th nowrap="nowrap" style="text-align: center;">返回结果</th>
			<th nowrap="nowrap" >返回时间</th>
			<th nowrap="nowrap" >发票创建结果</th>
		</tr>
		
		<c:forEach items="${apList}" var="apReconInfo" varStatus="vs">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');" class="trWhite">
				<td>${vs.index+1}</td>
				<td>${apReconInfo.cntNum}</td>
				<td>${apReconInfo.payId}</td>
				<td class="splitValue">${apReconInfo.sendValue}</td>
				<td>${apReconInfo.uploadDate}</td>
				<td class="splitValue">${apReconInfo.checkValue}</td>
				<td>${apReconInfo.checkResult}</td>
				<td>${apReconInfo.checkDownDate}</td>
				<td class="splitValue">${apReconInfo.invoiceValue}</td>
				<td>${apReconInfo.invoiceResult}</td>
				<td>${apReconInfo.invDate}</td>
				<td>${apReconInfo.invoiceState}</td>
			</tr>
		</c:forEach>
		
		<c:if test="${empty apList}">
			<tr>
				<td colspan="14" style="text-align: center;color: red;">没有查询到相关的数据!</td>
			</tr>
		</c:if>
	</table>
	</div>
</form>

<div id="scrollTab" style="position: fixed ;"></div>

<p:page/>


<div id="hideDiv" style="   overflow:hidden;display:none; position:absolute; top:127px;z-index:101">
	<table id="hideTab"  style="table-layout: fixed;" class="tableList">
	</table>
</div>
</body>
</html>