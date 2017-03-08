<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预算申报明细页面</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
	$("#dataTypeSelect").combobox();
	$("#dataAttrSelect").combobox();
	$("#valiYearSelect").combobox();
// 	radioInit($("#isProvinceBuyDiv"),"isProvinceBuy");
}
//选择物料
function selectMatr()
{
	App.submitShowProgress();
	var url="<%=request.getContextPath()%>/sysmanagement/approvechain/selectMatr.do?<%=WebConsts.FUNC_ID_KEY %>=01060113";
	var returnValue = window.showModalDialog(url, window, "dialogHeight=400px;dialogWidth=900px;center=yes;status=no;help:no;scroll:yes;resizable:no");
	if(returnValue == null)
	{
		App.submitFinish();
		return null;
	}
	else
	{
		App.submitFinish();
		$("#matrName1").val(returnValue);
	}
}
var scrollTag="";
function scrollTable(){
	$("#scrollTab").css("overflow-X", "scroll").height(16).width($("#listDiv").width()+20);
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

$(function(){
	scrollTable();
	//初始化表格头
        var table = $("#listTab").width();
	 $("#hideDiv").width($("#listDiv")[0].clientWidth);
	$("#hideTab").width(table);

})
</script>
</head>
<body >
<script type="text/javascript">
	function heightScroll(){
		$("body").scroll(function(){
// 			console.info($("body")[0].scrollTop + "--" + $(window).height() + "--" + $("body")[0].scrollHeight);
		});
	}

	$(function(){
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

<p:authFunc funcArray="020203,02020301,02020302,02020303,02020304,01060113"/>
<form action="" method="post" id="budgetSearchForm">
<input type="hidden" id="tmpltId" value="${searchBean.tmpltId }" name="tmpltId"/>
<input type="hidden" id="dataType" value="${searchBean.dataType }" name="dataType"/>
	<div id="approveChainTableDiv">
	<table id="approveChainTable">
		<tr class="collspan-control">
			<th colspan="4" style="text-align: left">预算模板申报信息查询</th>
		</tr>
		<tr>
			<td class="tdLeft"> 物料编码</td>
			<td class="tdRight">
				<input type="text" name=matrCode value="${searchBean.matrCode }"  id="matrCode" class="base-input-text" maxlength="11"/>
			</td>
				<td class="tdLeft"> 物料名称</td>
			<td class="tdRight">
				<input type="text" name=matrName value="${searchBean.matrName }"  id="matrName" class="base-input-text" maxlength="300"/>
			</td>
		</tr>
		<tr>
			<td colspan="4">
					<p:button funcId="02020302"  value="查找" />
					<input type="button" value="返回" onclick="backToLastPage('${uri}')">
			</td>
		</tr>
	</table>
	</div>
</form>

<span id="brspan"> &nbsp;</span>
<form action="" method="post" id="budgetListForm">
	<div id="listDiv" style="width: 100%; overflow-X:scroll;overflow:hidden;  float: right">
		<table id="listTab" class="tableList">
		<tr id="listTabFirstTr" style="overflow: hidden;">	
			<c:forEach items="${headMsg }" var ="head">
				<c:set value="${ fn:split(head.rowInfo, ' |') }" var="names" />
				<c:forEach items="${ names }" var="name">
					<th nowrap="nowrap" style="width:110px; text-align: center;">${name }
				</c:forEach>
			</c:forEach>
			<th nowrap="nowrap" style="width:110px;">物料归口部门</th>
		</tr>
			
		<c:if test="${!empty listMsg}">
			<c:forEach items="${listMsg}" var="budget" varStatus="status">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');" class="tabTd" >				
					<c:set value="${ fn:split(budget.rowInfo, '|') }" var="names" />
					<c:forEach items="${ names }" var="name">
						 <td style="text-align: center ;width:110px;" class="tabTr"  >
						  ${name }
						 </td>
					</c:forEach>
					
					<c:if test="${dataType eq '0' }">
						<td style="text-align: center ;width:110px;"  class="tabTr">
							${budget.inNouseCnt }
						</td>
						<td style="text-align: center;width:110px;"  class="tabTr">
							${budget.inNeedCnt }
						</td>
					</c:if>
					<td style="text-align: center;width:110px;"  class="tabTr">
							${budget.inAmt }
					</td>
					<td style="text-align: center;width:110px;"  class="tabTr">
							${budget.inMemo }
					</td>
					<td style="text-align: center;width:110px;"  class="tabTr">
						${budget.matrAuditDeptName }
					</td>
				</tr>
			</c:forEach> 
		</c:if>
		<c:if test="${empty listMsg}">
			<tr>
				<td colspan="14" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
			</tr>
		</c:if>
	</table>
	</div>
	
</form>

<div id="scrollTab" style="position: fixed ;"></div>
<p:page/>
<div id="hideDiv" style="   overflow:hidden;display:none; position:absolute; top:127px;z-index:101" id="hideDiv">
					<table id="hideTab"  >
						<tr>
							<c:forEach items="${headMsg }" var ="head">
								<c:set value="${ fn:split(head.rowInfo, ' |') }" var="names" />
								<c:forEach items="${ 
								names }" var="name">
									<th nowrap="nowrap" style="width:110px;text-align: center;">${name }
								</c:forEach>
							</c:forEach>
							<th nowrap="nowrap" style="width:110px;">物料归口部门</th>
						</tr>
					</table>
				</div>
</body>
</html>