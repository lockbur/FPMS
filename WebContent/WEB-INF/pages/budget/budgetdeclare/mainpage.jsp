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
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/css/erp-tabs.css">

</head>
<body>
<table width="100%" class="noborder"  id="tableScreen" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" id="top"  >
			<br/>
			<table border="0" style="border: 0px;" width="97.5%" align="center" cellpadding="0" cellspacing="0" id="pagetopid">
				<tr id="tab">
				   <td >
					   <div class="tab_gray" id="td_0" style="float: left;">
							<div class="tab_left"></div>
							<div class="tab_middle">
								 <a href="<%=request.getContextPath()%>/budget/budgetdeclare/unExecuteList.do?<%=WebConsts.FUNC_ID_KEY %>=02020101" target="module" style="text-decoration: none;"  onclick="change(this);" name="hrefName" id="href_0">已签合同未执行完毕</a>
							</div>
							<div class="tab_right"></div>
						</div>		
						<div class="tab_gray" id="td_1" style="float: left;">
							<div class="tab_left"></div>
							<div class="tab_middle">
								<a href="<%=request.getContextPath()%>/budget/budgetdeclare/unSignContList.do?<%=WebConsts.FUNC_ID_KEY %>=02020103" target="module" style="text-decoration: none;"  onclick="change(this);" name="hrefName" id="href_1">已立项未签合同</a>
							</div>
							<div class="tab_right"></div>
						</div>
					</td>
				</tr>
			</table>			
		</td>
	</tr>
    <tr>
		<td style="height: 1px;">
			<table width="97.5%" align="center"  style="border: 0px;"  cellpadding="0" cellspacing="0" class="redbj">
              <tr >
                <td  style="height: 1px;"></td>
              </tr>
       		</table>
		</td>
	</tr>
	<tr>
		<td>			
			<iframe id="module" name="module" frameborder="0"  width="100%" scrolling="no" onload="iframeOnloadHeight(this);" 
				src="<%=request.getContextPath()%>/budget/budgetdeclare/unExecuteList.do?<%=WebConsts.FUNC_ID_KEY %>=02020101"></iframe>
		</td>	
	</tr>
</table>	


<script type="text/javascript" language="javascript">
defaultclass();
function change(obj)
{
	var ob;
	var tdob;
	var hrefobj=document.getElementsByName("hrefName");
	
	for(var i=hrefobj.length-1;i>=0;i--)
	{
		ob=document.getElementById("href_"+i);
		tdob=document.getElementById("td_"+i);
		if(obj!=ob)
		{
			tdob.className="tab_gray";
		}
		else 
		{
			tdob.className="tab_red";
		}
	}
	obj.blur();
}
function defaultclass()
{
	document.getElementById("td_0").className="tab_red";
	
} 

$(window).resize(function(){
	iframeResizeHeight("module");
});


//iframe随windos变化而变化
function iframeResizeHeight(iframe)
{
	var pTar = null; 
	
	if(typeof iframe === "string")
	{
	    pTar = document.getElementById(iframe);  
	}
	else
	{
		pTar = iframe;
	} 
	
	if (pTar && !window.opera)
	{   
	   	pTar.style.display = "";  
		subWeb = pTar.Document ? pTar.Document: pTar.contentDocument; 
    	var h1 = subWeb.body.scrollHeight;   
    	var h2 = subWeb.body.offsetHeight;
    	var h3 = pTar.contentWindow.document.documentElement.scrollHeight;
		eval("pTar.height = Math.min(h1 == 0 ? number.MAX_VALUE : h1 ,h2 == 0 ? number.MAX_VALUE : h2, h3)");
	    
	}
} 


function iframeOnloadHeight(iframe)
{
	var pTar = null; 
	
	if(typeof iframe === "string")
	{
	    pTar = document.getElementById(iframe);  
	}
	else
	{
		pTar = iframe;
	} 
	
	$(pTar).css("height", "1px").css("height", $(pTar).contents().height() + "px");
} 
</script>
</body>
</html>