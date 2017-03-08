<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@page import="com.forms.platform.core.util.Tool"%>
<%@page import="com.forms.platform.core.tree.ITree"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="json/html; charset=UTF-8">
<meta name="decorator" content="dialog">
<base target="_self">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/component/dtree/css/dhtmlxtree_std.css">
<style type="text/css">

li.desktop-menu {list-style-type: none; height: 110px;width: 80px; text-align: center; overflow: hidden; padding: 3px; float: left; position: relative;}
li.desktop-menu img.delImg {margin: 1px; cursor: pointer; position: absolute; left: 10px; top: 10px; display: "";}
li.desktop-menu img.expandImg {margin: 5px 1px 1px 1px; cursor: pointer;width: 60px;height: 55px;}
li.desktop-menu span {margin: 1px; cursor: pointer; display: inline-block;}
div.desktop-tree { height: 400px; border: 0px solid Silver; overflow: auto; text-align: left;   }
/* div.desktop-menu { height: 400px;  border: 1px solid Silver; overflow: auto; } */


.checked{ border:1px solid #f76b0c;}


.radioImg{cursor:pointer; vertical-align:middle;width: 65px;height: 60px;};

</style>

<script type="text/javascript" src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std_patch.js"></script>
<title></title>
<script type="text/javascript">

var tree;
function pageInit(){
	<%
	String jsonData = WebUtils.getRequestAttr("jsonStr");
	%>
	var treeObj = <%=Tool.CHECK.isEmpty(jsonData) ? "null" : jsonData%>;
	if (treeObj != null) {
		 tree = new dhtmlXTreeObject("treepanel","100%","100%",0);
		tree.setImagesPath('<%=request.getContextPath()%>/component/dtree/css/imgs/');
		tree.enableTreeLines(true);
		tree.enableThreeStateCheckboxes(true);
		tree.setDragBehavior('complex', true);
		tree.enableCheckBoxes(false);
		tree.makeAllDraggable();
		
		tree.loadJSONObject(treeObj);
		tree.openAllItems(1);
		tree.setOnClickHandler(menuclick);
	}
	App.jqueryAutocomplete();
	$(".xzdiv").attr("style","display:none");
	$(".gxdiv").attr("style","display:none");
}
//限制功能简称的长度
function getCharNumb(){
	var str=$.trim($("#funcMemo").val());
	var count=0;
	var a=0;
	var b=0;
	for(var i=0;i<str.length;i++){
		var c = str.charAt(i);
		if(/^[\u0000-\u00ff]$/.test(c)){
			count=count+1;
			a=a+1;
		}else{
			count +=2;
			b=b+1;
		}
	}
	if(count>24||2*a>(36-3*b)||a>18||b>13){
		return false;
	}else{
		return true;
	}
}
//添加用户常用功能
function addUserDesktop()
{
	var funcId=$("#funcId").val();
	var funcMemo=$.trim($("#funcMemo").val());
	var iconName=$("input[name=iconName]:checked").val();
	if(funcMemo==null||funcMemo==""){
		App.notyError("功能简称不能为空");
		return ;
	}else if(!getCharNumb()){
		App.notyError("功能简称过长");
		return ;
	}
	var data={};
	data['funcId'] =  funcId;
	data['funcMemo'] = funcMemo;
	data['iconName'] = iconName;
	var url = "homepage/addUserDesktopAjax.do?<%=WebConsts.FUNC_ID_KEY %>=000104";
	App.ajaxSubmit(
			url, 
			{data : data,async:false},
			function(data)
			{
				ch();
				alert(data.success);
				initUserDesktop();
			}, 
			function(failureMsg,data)
			{
				alert(failureMsg);
			}
		);	
}

//修改用户常用功能
function updateUserDesktop()
{
	var funcId=$("#funcId").val();
	var funcMemo=$.trim($("#funcMemo").val());
	var iconName=$("input[name=iconName]:checked").val();
	if(funcMemo==null||funcMemo==""){
		App.notyError("功能简称不能为空");
		return ;
	}else if(!getCharNumb()){
		App.notyError("功能简称过长");
		return ;
	}
	var data={};
	data['funcId'] =  funcId;
	data['funcMemo'] = funcMemo;
	data['iconName'] = iconName;
	var url = "homepage/updateUserDesktopAjax.do?<%=WebConsts.FUNC_ID_KEY %>=000107";
	App.ajaxSubmit(
			url, 
			{
				data:data,async:false
			}, 
// 			data,
			function(data)
			{
				alert(data.success);
				initUserDesktop();
			}, 
			function(failureMsg,data)
			{
				alert(failureMsg);
			}
		);	
}
//当新增完当前的功能时将按钮切换显示
function ch(){
	$("#gx").attr("style","height: 38px;");
	$(".gxdiv").attr("style","");
	$("#xz").attr("style","display:none");
}

//删除用户常用功能
function delUserDesktop(funcId)
{
		var url = "homepage/delUserDesktopAjax.do?<%=WebConsts.FUNC_ID_KEY %>=000106&funcId="+funcId;
		App.ajaxSubmit(
			url, 
			{
				async:false
			}, 
			function(data)
				{
					initUserDesktop();
					var func=$("#funcId").val();
					if(func==funcId){
						var radio = $("input[type='radio']");
						$("input[name='funcId']").val("");//菜单ID
						$("#fun").html("");
						$("input[name='funcName']").val("");//菜单名
						$("#funcNa").html("");
						$("input[name='funcMemo']").val("");
						$("#funcMemo").html("");
						//所有的radio都不会选中
						for(var j=0;j<radio.length;j++){
							radio[j].checked=false;
						}
						$(".memo").attr("style","display:none");
						$("#gx").attr("style","display:none");
						$("#xz").attr("style","display:none");
					}
				}, 
			function(failureMsg,data)
				{
					alert(failureMsg);
				}
			);	
}

//通过ajax刷新用户已添加的常用功能图标，并可以删除
function  initUserDesktop()
{
	var url = "homepage/initUserDesktopAjax.do?<%=WebConsts.FUNC_ID_KEY %>=000103";
	App.ajaxSubmit(
			url, 
			{
				async:false
			}, 
			function(data)
			{
				$("li.desktop-menu").remove();
				if(data.userDesktopList.length>0)
				{
					for(var i=0;i<data.userDesktopList.length;i++)
					{
						$("#menus").append(createImgLink(data.userDesktopList[i].funcId,data.userDesktopList[i].funcMemo,data.userDesktopList[i].funcName,data.userDesktopList[i].iconName));
					}
				}
			}, 
			function(failureMsg,data)
			{
				alert(failureMsg);
			}
		);	
}

function createImgLink(funcId,funcMemo,funcName,iconName)
{
	var loc_li = $("<li/>", {id : "wrap-"+"'"+funcId+"'", "class": "desktop-menu"});
	loc_li.append($("<img/>", {"class" : "delImg","onclick":"delUserDesktop("+"'"+funcId+"'"+")", src : $publicSystemPath$+"/common/images/cancel.gif"}));
	loc_li.append($("<img/>", {"class" : "expandImg", "onclick":"menuclick("+"'"+funcId+"'"+")",src : $publicSystemPath$+"/common/images/function/"+iconName }));
	loc_li.append($("<span/>").text(funcMemo));
	return loc_li;
}



$(document).ready(function()
{
	  var  iconNames=$("input[name=iconName]");
	  iconNames.bind("click",function() 
	  {
		  $(".checked").removeClass("checked");
		  $(this).next(".radioImg").addClass("checked");}
	  );
	 $("li.desktop-menu").remove();
	  initUserDesktop();
	  
});

/*
 *菜单点击事件
 */	
function menuclick(id)
{
	getMenuInfoAjax(id);
}

function getMenuInfoAjax(id)
{
	var url = "homepage/getDesktopInfoAjax.do?VISIT_FUNC_ID=000105&funcId=" + id;
	App.ajaxSubmit(
			url, 
			{
				async:false
			}, 
			function(data)
			{
				var functionInfo = data.functionInfo;
				var numb = tree.hasChildren(functionInfo.funcId);
				var userDesktopBean = data.userDesktopBean;
				var radio = $("input[type='radio']");
				if(numb == 0){
					if(userDesktopBean == null ||userDesktopBean == ""){
						$("input[name='funcId']").val(functionInfo.funcId);//菜单ID
						$("#fun").html(functionInfo.funcId);
						$("input[name='funcName']").val(functionInfo.funcName);//菜单名
						$("#funcNa").html(functionInfo.funcName);
						$(".memo").attr("style","");
						$("input[name='funcMemo']").val(functionInfo.funcName);//功能简称
						$("input[type='radio']:first")[0].checked=true;
						$("#xz").attr("style","height: 38px;");
						$(".xzdiv").attr("style","");
						$("#gx").attr("style","display:none");
					}else{
						$("input[name='funcId']").val(functionInfo.funcId);//菜单ID
						$("#fun").html(functionInfo.funcId);
						$("input[name='funcName']").val(functionInfo.funcName);//菜单名
						$("#funcNa").html(functionInfo.funcName);
						$(".memo").attr("style","");
						$("input[name='funcMemo']").val(userDesktopBean.funcMemo);//功能简称
						var iconName = userDesktopBean.iconName;
						change(radio,iconName);
					    $("#gx").attr("style","height: 38px;");
					    $(".gxdiv").attr("style","");
						$("#xz").attr("style","display:none");
					}
				}else{
					$("input[name='funcId']").val(functionInfo.funcId);//菜单ID
					$("#fun").html(functionInfo.funcId);
					$("input[name='funcName']").val(functionInfo.funcName);//菜单名
					$("#funcNa").html(functionInfo.funcName);
					//所有的radio都不会选中
					for(var j=0;j<radio.length;j++){
						radio[j].checked=false;
					}
					$(".memo").attr("style","display:none");
					$("#xz").attr("style","height: 38px;");
					$(".xzdiv").attr("style","display:none");
					$("#gx").attr("style","display:none");
				}
			}, 
			function(failureMsg,data)
			{
				alert(failureMsg);
			}
		);
}
//类型为radio的input中如果传入的对象值为aValue则将其改为选中状态
function change(radio_oj,aValue){
	for(var i=0;i<radio_oj.length;i++){
		if(radio_oj[i].value==aValue){
			radio_oj[i].checked=true;
			break;
		}
	}
}

</script>
</head>
<body>

<form action="" id="tempForm" method="post">
	<input type="hidden" id="funcId" name="funcId" maxlength="50" class="base-input-text" valid errorMsg="请输入功能ID。"/>
	<input type="hidden" id="funcName" name="funcName"  maxlength="200" class="base-input-text" valid errorMsg="请输入功能名称。"/>
<table >
<tr>
<td width="35%"  class="tdWhite">
<div id="tree"  class="desktop-tree">
   <div id="treepanel"></div>
</div>
</td>
<td width="65%" class="tdWhite"> 
    <div id="menu"  class="desktop-menu"  > 
 		<table id="subtable">
			<thead>
			<tr>
				<th colspan="2" class="thTop">
					常用功能新增
				</th>
			</tr>
		</thead>
			<tr>
				<td  style="width: 30%;" class="tdLeft"><fmt:message key="label.funcId"/></td>
				<td  style="width: 70%;" class="tdRight" id="fun">
				</td>
			</tr>
			<tr>
				<td class="tdLeft"><fmt:message key="label.funcName"/></td>
				<td class="tdRight" id="funcNa">
				</td>
				
			</tr>
			<tr>
				<td class="tdLeft"><span style="color:red;">*</span>功能简称</td>
				<td class="tdRight">
					<div class="memo" style="display:none">
						<input type="text" id="funcMemo" name="funcMemo" maxlength="200" class="base-input-text" valid errorMsg="请输入功能名称。"/>
					</div>
				</td>
				
			</tr>
			<tr>
				<td class="tdLeft"><span style="color:red;">*</span>快捷图标选择</td>
				
				<td class="tdRight"  id="icon">
				<div>
				<ul style="list-style: none;">
				<li style="padding-top:5px; ">
			
				<span><input type="radio"  name="iconName" value="01.gif"/><img  class="radioImg" style="vertical-align:middle;" src="<%=request.getContextPath()%>/common/images/function/01.gif"> </span>&nbsp;
				<span><input type="radio"  name="iconName" value="02.gif"/><img  class="radioImg" style="vertical-align:middle;" src="<%=request.getContextPath()%>/common/images/function/02.gif"> </span>&nbsp;
				<span><input type="radio"  name="iconName" value="03.gif"/><img  class="radioImg" style="vertical-align:middle;" src="<%=request.getContextPath()%>/common/images/function/03.gif"></span>
				</li>
				<li style="padding-top:5px;">
				<span>
				 <input type="radio"  name="iconName"  value="04.gif" /><img  class="radioImg" style="vertical-align:middle;" src="<%=request.getContextPath()%>/common/images/function/04.gif"> 
				</span>&nbsp;
				<span>
				<input type="radio"  name="iconName" value="06.gif"/><img  class="radioImg" style="vertical-align:middle;" src="<%=request.getContextPath()%>/common/images/function/06.gif"> 
				</span>&nbsp;
				<span>
				<input type="radio"  name="iconName" value="07.jpg"/><img  class="radioImg" style="vertical-align:middle;" src="<%=request.getContextPath()%>/common/images/function/07.jpg"> 
				</span>
				</li>
				<li style="padding-top:5px;">
				<span>
				 <input type="radio"  name="iconName" value="08.jpg"/><img  class="radioImg" style="vertical-align:middle;" src="<%=request.getContextPath()%>/common/images/function/08.jpg"> 
				</span>&nbsp;
				<span>
				<input type="radio"  name="iconName" value="09.jpg"/><img  class="radioImg" style="vertical-align:middle;" src="<%=request.getContextPath()%>/common/images/function/09.jpg"> 
				</span>&nbsp;
				<span>
				<input type="radio"  name="iconName" value="10.jpg"/><img  class="radioImg" style="vertical-align:middle;" src="<%=request.getContextPath()%>/common/images/function/10.jpg"> 
				</span>
				</li>
				</ul>
				</div>
				</td>
			</tr>
			<tr>
				<td id="xz"  colspan="2"  width="100%" height="100%" style="height: 38px;">
					<div class="xzdiv">
						<input  type="button"  value="新增"  onclick="addUserDesktop();" id = "addDesktop">
					</div>
				</td>
				<td id="gx"  colspan="2"  width="100%" height="100%" style="display: none">
					<div class="gxdiv">
						<input  type="button"  value="更新"  onclick="updateUserDesktop();" id = "updateDesktop">
					</div>
				</td>
			</tr>
	</table>
	    </div> 
   
   

</td>
</tr>
<tr>
<td colspan="2" class="tdWhite" height="50px;" align="left">

<div  class="shortcut">
<ul style="list-style: none;"   id="menus">
<!-- 
<li   class="desktop-menu"><img class="delImg" src="<%=request.getContextPath()%>/common/images/cancel.gif" style="display: '';"><img class="expandImg" src="<%=request.getContextPath()%>/common/images/function/white_11.jpg"><span>网点个金综合</span></li>
 -->

</ul>


</div>

</td>
</tr>
</table>
<div align="center">
<input type="button" value="关闭" onclick="art.dialog.close()" align="middle"/>
</div>
</form>

<br>
</body>
</html>