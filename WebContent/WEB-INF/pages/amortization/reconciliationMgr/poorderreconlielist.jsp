<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PO采购订单对账</title>
<script type="text/javascript">
/**
 * 页面初始化时执行
 */
function pageInit()
{
	App.jqueryAutocomplete();
	$("#checkResultSelect").combobox();
	$("#resultSelect").combobox();
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
		
		judgeScrollBarPosition();
	});
	
	//判断页面Table表格的内容条数是否需要使用替代的ScrollBar，还是使用table所属Div自带的ScrollBar
	function judgeScrollBarPosition(){
// 		console.info("${poList}".split(",").length);
// 		console.info("窗体高度："+$(window).height());
// 		console.info($("#listTab").height());			//主体信息表格的高度=动态变化
// 		console.info($("#listTab")[0].offsetTop);		//表格与body顶部的距离=59
// 		console.info($(".menu1")[0].offsetHeight+$(".top")[0].offsetHeight);	//html整个页面顶端与body顶端的距离=127
		
		//"${poList}".split(",").length
		if(($("#listTab").height()+$("#listTab")[0].offsetTop+$(".menu1")[0].offsetHeight+$(".top")[0].offsetHeight) > $(window).height()){
// 			console.info('数据过多，需要替换滚动条');
			$("#scrollTab").css("top",$(window).height()-16+"px");			//动态设置DIV滚动条固定位置(设置于浏览器窗体的底部)
			var obj = document.getElementById("listTab");
			var _getHeight = obj.offsetTop;
			var scrollTag2 ="";
			$(window).scroll(function(){
				var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
//	 			console.info("的方式的发送到"+scrollTop+":"+_getHeight);
				if(parseInt(scrollTop) <parseInt(_getHeight)){  
					$("#hideDiv").hide();
				}else{
					 $("#hideDiv").show();
				}
//	 			console.info("移动了"+$("#scrollTab")[0].scrollLeft);
				 $("#hideDiv")[0].scrollLeft = $("#listDiv")[0].scrollLeft;
				 
				var scTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
				if(scTop + $(window).height() > $("body")[0].scrollHeight - 18 ){
//	 				alert('滚动到底了！！');
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
		}else{
// 			if("${glList}".split(",").length == 1){
//				console.info('没有数据,将替代的横向滚动条scrollTab隐藏！');
//			}
			//数据较少，采用DIV原生DIV，并把替代的横向滚动条scrollTab隐藏！！
			$("#scrollTab").hide();
			$("#scrollTab").css("overflow-X","hidden");
// 			console.info('少数据，不需要替换滚动条，采用Table自身的滚动条');
		};
	}
	
	
</script>
<p:authFunc funcArray="040803"/>
<form action="" method="post" id="poOrderReconliationForm">
	<table id="queryConditionArea">
		<tr class="collspan-control">
			<th colspan="4" >
				查询条件
			</th>
		</tr>
		<tr>
			<td class="tdLeft">合同编号</td>
			<td class="tdRight">
					<input type="text" id="cntNum" name="cntNum" value="${poOrderBean.cntNum}" class="base-input-text" maxlength="80">
			</td>
			
			<td class="tdLeft">订单号</td>
			<td class="tdRight">
				<input type="text" id="orderId" name="orderId" value="${poOrderBean.orderId}" class="base-input-text" maxlength="20">
			</td>
		</tr>
		
		<tr>
			<td class="tdLeft">校验结果</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="checkResultSelect" name="checkResult" >
						<option value="">请选择</option>
						<option value="未返回" <c:if test="${poOrderBean.checkResult == '未返回'}">selected="selected"</c:if> >未返回</option>
						<option value="校验失败" <c:if test="${poOrderBean.checkResult == '校验失败'}">selected="selected"</c:if> >校验失败</option>
						<option value="返回一致" <c:if test="${poOrderBean.checkResult == '返回一致'}">selected="selected"</c:if> >返回一致</option>
						<option value="返回不一致" <c:if test="${poOrderBean.checkResult == '返回不一致'}">selected="selected"</c:if> >返回不一致</option>
					</select>
				</div>
			</td>
			
			<td class="tdLeft">订单结果</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="resultSelect" name="orderResult">
						<option value="">请选择</option>
						<option value="未返回" <c:if test="${poOrderBean.orderResult == '未返回'}">selected="selected"</c:if> >未返回</option>
						<option value="返回一致" <c:if test="${poOrderBean.orderResult == '返回一致'}">selected="selected"</c:if> >返回一致</option>
						<option value="返回不一致" <c:if test="${poOrderBean.orderResult == '返回不一致'}">selected="selected"</c:if> >返回不一致</option>
					</select>
				</div>
			</td>
		</tr>

		<tr>
			<td colspan="4" class="tdBottom tdWhite">
				<p:button funcId="040803" value="查询"/>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>


	<div id="listDiv" style="width:100%;overflow-X:scroll; float: right">
	<table class="tableList" id="listTab" style="table-layout: fixed;"> 
	<br/>
<%-- 	<caption>PO采购订单对账信息列表</caption> --%>
		<tr style="height: 1px;font-size: 1px;line-height: 1px;background-color: white;" class="tbHead">
			<td style="width: 60px;height: 1px;border-top-style: none;padding: 0px;" ></td>
			<td style="width: 130px;height: 1px;border-top-style: none;border-left-style: none;border-right-style: none;padding: 0px;"></td>
			<td style="width: 110px;height: 1px;border-top-style: none;border-left-style: none;border-right-style: none;padding: 0px;"></td>
			<td style="width: 110px;height: 1px;border-top-style: none;border-left-style: none;border-right-style: none;padding: 0px;"></td>
			<td style="width: 170px;height: 1px;border-top-style: none;border-left-style: none;border-right-style: none;padding: 0px;"></td>
			<td style="width: 110px;height: 1px;border-top-style: none;border-left-style: none;border-right-style: none;padding: 0px;"></td>
			<td style="width: 170px;height: 1px;border-top-style: none;border-left-style: none;border-right-style: none;padding: 0px;"></td>
			<td style="width: 110px;height: 1px;border-top-style: none;border-left-style: none;border-right-style: none;padding: 0px;"></td>
			<td style="width: 110px;height: 1px;border-top-style: none;border-left-style: none;border-right-style: none;padding: 0px;"></td>
			<td style="width: 170px;height: 1px;border-top-style: none;border-left-style: none;border-right-style: none;padding: 0px;"></td>
			<td style="width: 110px;height: 1px;border-top-style: none;border-left-style: none;border-right-style: none;padding: 0px;"></td>
			<td style="width: 110px;height: 1px;border-top-style: none;border-left-style: none;border-right-style: none;padding: 0px;"></td>
		</tr>

		<tr class="collspan-control tbHead">
			<th nowrap="nowrap" rowspan="3">序号</th>
			<th nowrap="nowrap" rowspan="3">合同编号</th>
			<th nowrap="nowrap" rowspan="3">订单号</th>
			<th nowrap="nowrap" rowspan="3">订单行号</th>
			<th nowrap="nowrap" colspan="2" rowspan="2" style="text-align: center">ERP信息</th>
			<th nowrap="nowrap" colspan="6" style="text-align: center">FMS信息</th>
		</tr>
		<tr class="tbHead">
			<th nowrap="nowrap" colspan="3" style="text-align: center">校验文件</th>
			<th nowrap="nowrap" colspan="3" style="text-align: center">采购订单信息</th>
		</tr>
		<tr class="tbHead">
			<th nowrap="nowrap" style="text-align: center">发送值</th>
			<th nowrap="nowrap" >发送时间</th>
			<th nowrap="nowrap" style="text-align: center">返回值</th>
			<th nowrap="nowrap" >返回结果</th>
			<th nowrap="nowrap" >ERP处理时间</th>
			<th nowrap="nowrap" style="text-align: center">返回值</th>
			<th nowrap="nowrap" >返回结果</th>
			<th nowrap="nowrap" >ERP处理时间</th>
		</tr>
		
		<c:forEach items="${poList}" var="poReconInfo" varStatus="vs">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');" class="trWhite">
				<td>${vs.index+1}</td>
				<td>${poReconInfo.cntNum}</td>
				<td>${poReconInfo.orderId}</td>
				<td>${poReconInfo.rowSeqNo}</td>
				<td class="splitValue">${poReconInfo.sendValue}</td>
				<td>${poReconInfo.uploadDate}</td>
				
				<c:if test="${poReconInfo.checkResult == '未返回'}"><td></td></c:if>
				<c:if test="${poReconInfo.checkResult != '未返回'}"><td class="splitValue">${poReconInfo.checkValue}</td></c:if>
				
				<td>${poReconInfo.checkResult}</td>
				<td>${poReconInfo.checkDownDate}</td>
				<td class="splitValue">${poReconInfo.orderValue}</td>
				<td>${poReconInfo.orderResult}</td>
				<td>${poReconInfo.orderDate}</td>
			</tr>
		</c:forEach>
		
		<c:if test="${empty poList}">
			<tr>
				<td colspan="12" style="text-align: center;color: red;">没有查询到相关的数据!</td>
			</tr>
		</c:if>
	</table>
	</div>
</form>

<div id="scrollTab" style="position: fixed ;"></div>

<p:page/>

<div id="hideDiv" style="overflow:hidden;display:none; position:absolute; top:127px;z-index:101">
	<table id="hideTab"  >
	
	</table>
</div>

</body>
</html>