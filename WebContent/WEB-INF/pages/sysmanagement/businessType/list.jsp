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
<title>参数列表</title>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">
var $list = $("#parameterForm").find("table[name='tableList']");

$( function() {
	$( ".tableList" ).sortable({
	    items: "tr:not(#totalTr)",
	    cursor: 'move',
	    opacity: 0.8,
	    update: function(){ 
            var new_order = []; 
            $list.children(".trWhite").each(function() { 
               new_order.push(this.id); 
            }); 
            var newid = new_order.join(','); 
            $.ajax({ 
               type: "post", 
               url: "sysmanagement/businessType/updateSort.do", //服务端处理程序 
               data: { id: newid},   //id:新的排列对应的ID,order：原排列顺序 
               beforeSend: function() { 
                    $show.html("<img src='load.gif' /> 正在更新"); 
               }, 
               success: function(msg) { 
                    alert(msg); 
                    $show.html("修改排序成功"); 
               } 
            }); 
            var data = {};
    		data['id'] = newid;
    		data['categoryId'] = categoryId;
            App.ajaxSubmit("sysmanagement/businessType/preEdit.do", {
    			data : data,
    			async : false
    		}, function(data) {
    			flag = data.isOut;

    		});
        }
	});
	$( ".tableList tr" ).disableSelection();
  
} );

 /*$( function() {
	$( ".tableList" ).sortable({
	    items: "tr:not(#totalTr)",
	    cursor: 'move',
	    opacity: 0.8,
	    update: function(){ 
            var new_order = []; 
            $list.children(".trWhite").each(function() { 
               new_order.push(this.id); 
            }); 
            var newid = new_order.join(','); 
            $.ajax({ 
               type: "post", 
               url: "sysmanagement/businessType/preEdit.do", //服务端处理程序 
               data: { id: newid},   //id:新的排列对应的ID,order：原排列顺序 
               beforeSend: function() { 
                    $show.html("<img src='load.gif' /> 正在更新"); 
               }, 
               success: function(msg) { 
                    alert(msg); 
                    $show.html("修改排序成功"); 
               } 
            }); 
            var data = {};
    		data['id'] = newid;
    		data['categoryId'] = categoryId;
            App.ajaxSubmit("sysmanagement/businessType/preEdit.do", {
    			data : data,
    			async : false
    		}, function(data) {
    			flag = data.isOut;

    		});
        }
	});
	$( ".tableList tr" ).disableSelection();
  
} ); */
	
function pageInit(){
	App.jqueryAutocomplete();
 	$("#categoryId").combobox();
}

function modify(paramName,categoryId) 
{
	var form = $('#parameterForm')[0];
	$('#paramName').val(paramName);
	$('#categoryId').val(categoryId);
	form.action = '<%=request.getContextPath()%>/sysmanagement/businessType/preEdit.do?<%=WebConsts.FUNC_ID_KEY%>=081502';
	App.submit(form);
}

function add(){
	var form = $("#add")[0];
	form.action = "<%=request.getContextPath()%>/sysmanagement/businessType/add.do?<%=WebConsts.FUNC_ID_KEY%>=081503";
	form.submit();
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

function hideSub(obj,index)
{
	var hideFlag=$("#inputClass"+index).val();
	var trlist=$("#parameterForm").find("table[name='tableList']");
	if(hideFlag=='0')
	{
		for(var i = 0;i<trlist.length-1;i++){
			if(i!=index){
				$(trlist[i]).find("tr[id='trWhite']").hide();
				$(trlist[i]).find("img").attr({src :"<%=request.getContextPath()%>/common/images/plus.gif"});
				$(trlist[i]).find("input[name='showFlag']").val('0');
			}
		}
		$(obj).find("img").attr({src :"<%=request.getContextPath()%>/common/images/minus.gif"});
		$("tr[name='tr"+index+"']").show();
		$("#inputClass"+index).val('1');
		
	}
	else if(hideFlag=='1')
	{
		$(obj).find("img").attr({src :"<%=request.getContextPath()%>/common/images/plus.gif"});
		$("tr[name='tr"+index+"']").hide();
		$("#inputClass"+index).val('0');
	}
	
}
</script>
</head>

<body>
<p:authFunc funcArray="081501"/>
<form method="post" id="add" action=""></form>
<form action="<%=request.getContextPath()%>/sysmanagement/businessType/list.do" method="post" id="parameterForm">
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				业务类型查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft">参数类型</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="categoryId" name="categoryId">
						<option value="">--请选择--</option>		
						<forms:codeTable tableName="SYS_SELECT_CATEGORY" selectColumn="CATEGORY_ID,CATEGORY_NAME" 
					 		valueColumn="CATEGORY_ID" textColumn="CATEGORY_NAME" orderColumn="CATEGORY_ID" 
					 		orderType="ASC" selectedValue="${parameter.categoryId}"/> 
					</select>
				</div>
			</td>		
			<td class="tdLeft">参数名称</td>
			<td class="tdRight">
				<input type="text" id="paramName" name="paramName" value="${parameter.paramName}" class="base-input-text"/>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdBottom tdWhite">
				<p:button funcId="081501" value="查找"/>
				<input type="button" value="新增" onclick="add();">
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br/>
   <c:forEach items="${paramsClassList}" var="paramsClass"  varStatus="status">
		<table class="tableList" id="table${status.index}" name="tableList">
			<tr  onclick="hideSub(this,'${status.index}');" id="totalTr" >
				<th   colspan="4"  align="left" style="text-align: left;padding-left: 0px;">
				     <input type="hidden" id="inputClass${status.index}" name="showFlag"  value="0"/>
				<img  src="<%=request.getContextPath()%>/common/images/plus.gif" style="position: relative;top:3px">${paramsClass.categoryName}
				</th>
			</tr>
	   
	   <c:forEach items="${paramsClass.params}"  var="paramBean">
		   <tr  name="tr${status.index}" id="trWhite"  class="trWhite"  style="display:none">
				<td width="30%" >  <c:out value="${paramBean.paramName}" /><input type="hidden" name="" value=""></td>
				<td width="30%">
					<c:out value="${paramBean.paramValue}" /><input type="hidden" name="" value="">
				</td>
				<td width="30%">
					<c:out value="${paramBean.isInvalid}" />
				</td>
				<td width="10%" align="center"  style="text-align: center;">
						<div class="update"><a href="javascript:void(0);" onclick="modify('${paramBean.paramName}','${paramsClass.categoryId}');return false;" title="修改"></a></div>
				</td>
			</tr>
	   </c:forEach>
	   </table>
   </c:forEach>
		<c:if test="${empty paramsClassList}">
		<table class="tableList" >
		    <tr>
			<td colspan="3" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
			</tr>
		</table>
	   </c:if>	 
</form>
</body>
</html>