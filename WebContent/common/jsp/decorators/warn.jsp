<%@page import="com.forms.platform.core.util.Tool"%>
<%@page import="com.forms.prms.tool.WebHelp"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript">
$(function(){
	var warn = "${warnType}";
	if(warn=="open" || warn ==""){
		showDiv();	
	}
	if(warn=="close"){
		closeDiv();	
	}
	if(warn=="hide"){
		hideDiv();	
	}
	
})
function showDiv(){
    if(document.getElementById('PcPoPmarket')==null)return;
		document.getElementById('PcPoPmarket').style.width = 350+'px';
		document.getElementById('PcPoPmarket').style.height = 238+'px';
		document.getElementById('showvod').style.display = 'none';
		document.getElementById('hidevod').style.display = 'block';
		openOrClose("open");
}

function hideDiv(){
    if(document.getElementById('PcPoPmarket')==null)return;
	
	document.getElementById('PcPoPmarket').style.width = 226 + 'px';
	document.getElementById('PcPoPmarket').style.height = 37 + 'px';
	document.getElementById('hidevod').style.display = 'none';
	document.getElementById('showvod').style.display = 'block';
	openOrClose("hide");
}

function closeDiv(){
    if(document.getElementById('PcPoPmarket')==null)return;
	document.getElementById('PcPoPmarket').style.display = 'none';
	openOrClose("close");
}
function openOrClose(type){
	var data = {};
	data['type'] = type;
	App.ajaxSubmit("common/systemMsg/openOrClose.do",{data : data,async:false}, function(data) {
	});
}
</script>
<style>
 
.clear{clear:both;}
#lmt {width:344px; height:232px; padding:3px; overflow:hidden; background: url(<%=request.getContextPath()%>/common/images/bg_open.png) no-repeat;}
#lmt .lmt_top {width:350px; height:36px; line-height:36px;overflow:hidden; text-indent:10px; }
#lmt .lmt_top span{ font-weight:bold; color:#c00; font-size:14px;}
#lmt .lmt_top a:hover span,#lmt .lmt_top a:hover{color:#f60; text-decoration:underline}
#lmt .body_r { text-align:left;width:335px;height:175px;overflow:hidden; float: left; margin: 0 auto;}
#lmt .body_r  h2{color: #d00;padding:5px 0 5px 0px; }
#lmt .body_r  h2 a{color:#d00;font-size:16px; font-family:"Microsoft Yahei"}
#lmt .body_r em, #lmt .body_r em a{color:#777}
</style>
<style>
.popBtn{width:18px; height:18px; cursor:pointer; float:right;margin-left:1px; margin-top:12px; display:inline; background:url(<%=request.getContextPath()%>/common/images/buttons.png) no-repeat;}
.popClose{margin-right:10px; width:15px; background-position:-39px 0}
.popClose:hover{background-position:-39px -20px}
.popShow{background-position:0px 0}
.popShow:hover{background-position:0px -20px}
.popHide{background-position:-19px 0}
.popHide:hover{background-position:-19px -20px}
</style>
</head>
<body>
<span id="tmpAreaLmtDiv">
<div style="z-index: 1000; right: 0px; bottom: 0px; overflow-x: hidden; overflow-y: hidden; position: fixed; width: 226px; height: 37px; " id="PcPoPmarket">
<div id="popTop" style="z-index:1000; POSITION: absolute; right:0; height:30px; overflow:hidden;">
<span class="popBtn popClose" onclick="closeDiv()"></span>
<span class="popBtn popShow" onclick="showDiv()" id="showvod" style="display: bolck; "></span>
<span class="popBtn popHide" onclick="hideDiv()" id="hidevod" style="display: none; ">
</span></div>
<div id="ivy_div" style="display: none;"></div>
<div id="lmt">
  <div class="lmt_top" style="text-align: left;">
	   <span  style="text-align: left;"> 系统消息
	   </span>    
   </div>
  <div class="body_r">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" >
      <tr>
        <td>
        	<li>您的密码有效时间截止到
        	<%
        		String pwdOutDate  = Tool.DATE.getDaystr(WebHelp.getLoginUser().getPwdChangeDate(), Integer.parseInt(WebHelp.getSysPara("PWD_VALIDITY_DATE")));
        		out.print(pwdOutDate);
        	%>，请尽快修改。
        	</li>
        </td>
      </tr>
    </table>
  </div>
</div>
</div></span>
</body>
</html>