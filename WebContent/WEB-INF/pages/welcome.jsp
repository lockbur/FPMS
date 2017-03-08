<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中国银行总行财务项目管理系统</title>
<style type="text/css">
.booklinegray {background-color:#CCCCCC;}
.fonttop{font-weight: 700;color:#4c4944;font-size:14px;}

.whiteBG{ background:#fff;padding:2px 2px 2px 4px;line-height:20px;}
.whiteBG td{padding:2px 2px 2px 4px;line-height:20px;}

.red {color: #990000;}
.noboder  tr,td{border: 0;padding: 0;margin: 0;}

td, textarea, input, span, select {color: #4c4944;font-family:Arial;font-size: 12px;}
body{margin:0;background-color:#FBFBF9;scrollbar-face-color: #dee3e7;scrollbar-highlight-color: #ffffff;scrollbar-shadow-color: #dee3e7;scrollbar-3dlight-color: #d1d7dc;
.redbj {background-color:#E5212F;}
.funPool{
	padding: 5px;
	height: 250px;
}
.redbuttonbg {background-image: url("../../common/images/middle3.jpg"); color:#fff;font-size:12px;font-weight:700;}

.desktop-menu {list-style-type: none; height: 132px;    width: 100px; text-align: center; overflow: hidden; padding: 5px; float: left; position: relative;}
.desktop-menu img.expandImg {margin: 5px 1px 1px 1px; cursor: pointer; width: 100px;}
.desktop-menu span {margin: 1px; cursor: pointer; display: inline-block;}
</style>
<script type="text/javascript">

//业务提醒 打开 关闭
function showHidden(obj,task)
{
	var divObj = document.getElementById(task);
	if(divObj.style.display=="inline")
	{
		divObj.style.display="none";
		$(obj).find("img").attr({src :"<%=request.getContextPath()%>/common/images/hp_03b.gif"});
		//obj.src="<%=request.getContextPath()%>/common/images/hp_03b.gif";
	}
	else
	{
		divObj.style.display="inline";
		$(obj).find("img").attr({src :"<%=request.getContextPath()%>/common/images/hp_03a.gif"});
		//obj.src="<%=request.getContextPath()%>/common/images/hp_03a.gif";
	}
}
//用户常用功能弹出页
function showShortCutPage()
{
	$.dialog.open(
		'<%=request.getContextPath()%>/homepage/showTreePage.do?<%=WebConsts.FUNC_ID_KEY %>=000101&operation=add',
		{
			width: "70%",
			height: "90%",
			lock: true,
		    fixed: true,
		    cancelVal:null,
		    title:"功能收藏维护",
		    id:"dialogCutPage",
			close: function(){
					getList();
				}		
		}
	);
}

//根据关闭窗口刷新常用功能列表
 function getList(){
		var url = "homepage/ajaxGetList.do?<%=WebConsts.FUNC_ID_KEY %>=000113";
		App.ajaxSubmit(
				url, 
				{
					async:false
				}, 
				function (data){
					$(".meanusUl").html("");
					var funcUrl = new Array();
					funcUrl=data.funcUrl; 
					var fUrl=null;
					var iconName = new Array();
					iconName=data.iconName; 
					var iName=null;
					var funcName = new Array();
					funcName=data.funcName; 
					var fName=null;
					var funcMemo = new Array();
					funcMemo=data.funcMemo;
					var fMemo=null;
					for(var i=0;i<funcMemo.length;i++){
						fUrl=funcUrl[i];
						iName=iconName[i];
						fName=funcName[i];
						fMemo=funcMemo[i];
						$("<li class='desktop-menu' style='width: 100px;height:110px;float: left;text-align: center;'><a href='<%=request.getContextPath()%>/"+fUrl+"'"+ "title='"+fName+"'><img class='expandImg'  style='border: 0px;padding:8%;width: 60px;height: 45px;' src='<%=request.getContextPath()%>/common/images/function/"+iName+"'"+"/></a><span style='font-family:Arial;margin: 1px;padding:0% 8% 0% 8%; cursor: pointer; display: inline-block;'>"+fMemo+"</span></li>").appendTo(".meanusUl");
					}
				},
				function (failureMsg,data){
					alert(failureMsg);	
					location.href = '<%=request.getContextPath()%>/user/gotoLogin.do';
					}
				);
}





function rollDetail(rollId){
$.dialog.open(
		'<%=request.getContextPath()%>/homepage/rollInfoDetail.do?<%=WebConsts.FUNC_ID_KEY %>=000108&rollId='+rollId,
		{
			width: "50%",
			height: "50%",
			lock: true,
		    fixed: true,
		    title:"滚动信息详情",
		    id:"dialogRollInfoDetail",
			close: function(){
			}		
		}
	)
	}
function excepDetail(excepId){
	$.dialog.open(
		'<%=request.getContextPath()%>/homepage/excepInfoDetail.do?<%=WebConsts.FUNC_ID_KEY %>=000110&excepId='+excepId,
		{
			width: "50%",
			height: "50%",
			lock: true,
		    fixed: true,
		    title:"异常信息详情",
		    id:"dialogExcepInfo",
			close: function(){
				if(art.dialog.data('excepInfoNumb')!=''&&art.dialog.data('excepInfoNumb')!=null){
					var excepInfoNumb = art.dialog.data('excepInfoNumb'); 
					$("#excepspan").html(excepInfoNumb);
					var infos = new Array();
					var ids = new Array();
					var excepId=null;
					infos=art.dialog.data('excepInfos');
					ids=art.dialog.data('excepIds');
					$(".excepTable").html("");
					for(var i=0;i<infos.length;i++){
						excepId=ids[i];
						$("<tr ><td align='center' width='40'><img  height='3'  src='<%=request.getContextPath()%>/common/images/dotred.jpg' width='3'/></td><td width='500' height=10 class='excepTd'><a class='news' onclick='excepDetail(\""+excepId+"\")'  title='测试' href='javascript:void(0);'>"+infos[i]+"<img height='14' src='<%=request.getContextPath()%>/common/images/new.gif' width=25 border=0/></a></td></tr>").appendTo(".excepTable");
					}
					info();
				}else{
				};
			}
			}
	);
}
	
function rollList(){	
	$.dialog.open(
			'<%=request.getContextPath()%>/homepage/rollInfoList.do?<%=WebConsts.FUNC_ID_KEY %>=000109',
			{
				width: "60%",
				height: "70%",
				lock: true,
			    fixed: true,
			    title:"滚动信息详情列表",
			    id:"dialogRollInfoList",
				close: function(){
				}		
			}
		)
}
	
function excepList(){
	$.dialog.open(
			'<%=request.getContextPath()%>/homepage/excepInfoList.do?<%=WebConsts.FUNC_ID_KEY %>=000111',
			{
				width: "60%",
				height: "70%",
				lock: true,
			    fixed: true,
			    title:"异常信息详情列表",
			    id:"dialogExcepInfoList",
			    dblclick_hide:false,
				close: function(){
					if(art.dialog.data('excepInfoNumb')!=''&&art.dialog.data('excepInfoNumb')!=null){
						var excepInfoNumb = art.dialog.data('excepInfoNumb'); 
						$("#excepspan").html(excepInfoNumb);
						var infos = new Array();
						var ids = new Array();
						var excepId=null;
						infos=art.dialog.data('excepInfos');
						ids=art.dialog.data('excepIds');
						$(".excepTable").html("");
						for(var i=0;i<infos.length;i++){
							excepId=ids[i];
							
							$("<tr ><td align='center' width='40'><img  height='3'  src='<%=request.getContextPath()%>/common/images/dotred.jpg' width='3'/></td><td width='500' height=10 class='excepTd'><a class='news'"+"onclick='excepDetail("+excepId+")'"+" title='测试' href='javascript:void(0);'>"+infos[i]+"<img height='14' src='<%=request.getContextPath()%>/common/images/new.gif' width=25 border=0/></a></td></tr>").appendTo(".excepTable");
						}
						info();
					}else{
					};
				}		
			}
		)
	}

$(document).ready(function() {
	info();
	});
		
//此方法用于，当信息超过td时进行的处理
function info(){
	var rollInfo=$("a.news");
	rollInfo.each(function(){
		var txt = $.trim($(this).text());
		if(getCharNumb(txt)[0]>70){
			var info = txt.substring(0,getCharNumb(txt)[1]);
			$(this).html(info+"..."+"<img height='14' src='<%=request.getContextPath()%>/common/images/new.gif' width=25 border=0 />");
		};});
}

function getCharNumb(str){
	var count=0;
	var len=0;
	var lastcount=0;
	var k=0;
	var shu=new Array();
	for(var i=0;i<str.length;i++){
		var c = str.charAt(i);
		if(/^[\u0000-\u00ff]$/.test(c)){
			count=count+1;
			len=len+1;
		}else{
			count +=2;
			len=len+1;
		}
		if(count>70&&k==0){
			lastcount=len;
			k=k+1;
		}
		}
		shu[0]=count;
		shu[1]=lastcount;
		return shu;
}
</script>
</head>
<body  >
 <table width="98%" border="0px;" style="background-color: #D2D0D0;border-collapse: separate;"      cellspacing="1" cellpadding="0" >
        <tr>
          <td  align="left" valign="top">
	          <table  border="0"  cellspacing="0" cellpadding="0" width="100%">
	            <tr>
	              <td style="width: 100%;"    valign="top" class="whiteBG">
		              <div id="funPool" class="funPool">
		              	<table width="100%" border="0" cellspacing="0" cellpadding="0">
			              <tr >
			                   <td width="80%">
			                   <table border="0"   style="margin-left: 3px;margin-bottom: 3px;width: 80px;">
			                        <tbody>
			                        <tr style="height: 25px;">
			                          <td width="5px;" style="padding:0;"><img src="<%=request.getContextPath()%>/common/images/littleleft.jpg"  height="25px;"  width="5px;"/></td>
			                          <td width="70px;" align="center" class="redbuttonbg" style="padding:0;height: 25px;background-image: url('<%=request.getContextPath()%>/common/images/middle3.jpg'); color:#fff;font-size:12px;font-weight:700;">功能收藏</td>
			                          <td width="5px;" style="padding:0;"><img src="<%=request.getContextPath()%>/common/images/littleright.jpg" width="5px;" height="25px;"/></td>
			                        </tr>
			                    </tbody>
			                  </table>
			                   
			                   
			                   
			                   </td>
			                    <td width="20%"  align="right">
			                    <a  style="cursor: pointer;"   onclick="showShortCutPage();"  href="javascript:void(0);"  >
			                    <img  style="border: 0px;" src="<%=request.getContextPath()%>/common/images/addLink.gif"   />
			                    
			                    </a>
			                    </td>
			                </tr>
			               </table>
		              
			               <table width="100%" border="0" cellspacing="0" cellpadding="0">
			                <tr >
			                   <td  class="redbj" style="padding-top:0;background-color:#E5212F;height: 1px;" ></td>
			                </tr>
			               </table>
		                    <br/>
			           		<div style="width: 100%;" id="meanus" >
				           		<ul style="list-style: none;" class="meanusUl">
					           		<c:forEach items="${userDesktopList}" var="userDesktop">
										<li  class="desktop-menu" style="width: 100px;height:110px;float: left;text-align: center;">
											<a href="<%=request.getContextPath()%>/${userDesktop.funcUrl}" title="${userDesktop.funcName}">
												<img class="expandImg"  style="border: 0px;padding:8%;width: 60px;height: 45px;" src="<%=request.getContextPath()%>/common/images/function/${userDesktop.iconName}"/>
											</a>
										    <span style="font-family:Arial;margin: 1px;padding:0% 8% 0% 8%; cursor: pointer; display: inline-block;">${userDesktop.funcMemo}</span>
										</li>
					           		</c:forEach>
								</ul>
			           		</div>
		           	   </div>
	              </td>
	            </tr>
	            <tr>
	              <td style="width: 100%;"    valign="top" class="whiteBG">
		              	<div  style="height: 30px;text-align: center;">
		           			<span  id="sp1" style="width: 20px;height: 3px;background-color: #459999;margin-left: 5px;cursor: pointer;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
		           			<span  id="sp2" style="width: 20px;height: 3px;background-color: #BB6565;margin-left: 5px;cursor: pointer;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
		           			<span  id="sp3" style="width: 20px;height: 3px;background-color: #BBB165;margin-left: 5px;cursor: pointer;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
		           		</div>
	              </td>
	            </tr>
	         </table>
          </td>
          </tr>
      </table>
<br/>
<table width="98%" border="0" cellspacing="0" cellpadding="0"  class="noboder">
	<tr>
    	<td width="350" align="left" valign="top">
        	<table style="height: 160px;" width="100%"  border="0" cellpadding="0" cellspacing="0" class="noboder">
	            <tr >
	              <td  style="height: 7px;"  colspan="3"  valign="bottom"><img src="<%=request.getContextPath()%>/common/images/bookright.png" width="100%" style="height: 7px;" /></td>
	            </tr>
	            <tr style="height: 145px;">
	              <td style="height: 115px;width: 1px;padding: 0px;"  class="booklinegray" ></td>
	              <td   valign="top"   style="width: 495px;padding: 0px;">
		              <table  style="width: 97%;" border="0" align="center" cellpadding="0" cellspacing="0" class="noboder">
		                  <tr>
		                      <td  style="height: 25px;"  colspan="2" class="fonttop">业务提醒</td>
		                  </tr>
		                  <tr>
		                      <td  style="height: 5px;" colspan="2" background="<%=request.getContextPath()%>/common/images/dot-line.jpg"></td>
		                  </tr>
		                  <tr>
		                      <td style="height: 5px;" colspan="2"></td>
		                  </tr>
		                  <tr>
		                    <td width="6%" height="20" align="left"  colspan="2">
		                       
		                        <p onclick="showHidden(this,'task1');" style="cursor: pointer;"   ><img  height=10  src="<%=request.getContextPath()%>/common/images/hp_03b.gif" width=10  />&nbsp;&nbsp;合同审批(&nbsp;<span class="red">${homePageBean.operNum01+homePageBean.operNum02+homePageBean.operNum03}</span>&nbsp;)：</p>
		                       
			                    <div id="task1" style="display:none">
				                    <table  style="width: 97%;" border="0" align="center" cellpadding="0" cellspacing="0" class="noboder">
					                     <c:if test="${homePageBean.operNum01 != 0}">
					                    <tr>
					                    	<td width="6%" height="25" align="center"><img src="<%=request.getContextPath()%>/common/images/dotred.jpg" width="3" height="3" /></td>
					                    	<td width="94%" align="left">您所在机构有&nbsp;<span class="red">${homePageBean.operNum01}</span>&nbsp;条合同物料待复核信息。<a href="<%=request.getContextPath()%>/contract/check/contractList.do?VISIT_FUNC_ID=030202" class="red">点击查看</a></td>
					                	</tr>
					                     </c:if>
					                       <c:if test="${homePageBean.operNum02 != 0}">
						                <tr>
						                    <td height="25" width="6%" align="center"><img src="<%=request.getContextPath()%>/common/images/dotred.jpg" width="3" height="3" /></td>
						                    <td align="left" width="94%">您所在机构有&nbsp;<span class="red">${homePageBean.operNum02}</span>&nbsp;条合同待确认信息。<a href="<%=request.getContextPath()%>/contract/confirm/confirmList.do?VISIT_FUNC_ID=03020103" class="red">点击查看</a></td>
						                </tr>
						                </c:if>
						                  <c:if test="${homePageBean.operNum03 != 0}">
						                <tr>
						                    <td height="25" width="6%" align="center"><img src="<%=request.getContextPath()%>/common/images/dotred.jpg" width="3" height="3" /></td>
						                    <td align="left" width="94%">您所在机构有&nbsp;<span class="red">${homePageBean.operNum03}</span>&nbsp;条合同移交待接收信息。<a href="<%=request.getContextPath()%>/contract/deliver/deliverList.do?VISIT_FUNC_ID=03020302" class="red">点击查看</a></td>
						                </tr>
						                </c:if>
				                    </table>
			              		</div>
		                    </td>
		                  </tr>
		                  
			              <tr>
			                  <td width="6%" height="20" align="left"  colspan="2">
			                  	<p onclick="showHidden(this,'task2');" style="cursor: pointer;"><img  height=10  src="<%=request.getContextPath()%>/common/images/hp_03b.gif" width=10  />&nbsp;&nbsp;付款审批(&nbsp;<span class="red">${homePageBean.operNum10+homePageBean.operNum11+homePageBean.operNum12+homePageBean.operNum13}</span>&nbsp;)：</p>
			                    <div id="task2" style="display:none">
				                    <table  style="width: 97%;" border="0" align="center" cellpadding="0" cellspacing="0" class="noboder">
				                      <c:if test="${homePageBean.operNum10 != 0}">
				                      <tr>
				                    	<td width="6%" height="25" align="center"><img src="<%=request.getContextPath()%>/common/images/dotred.jpg" width="3" height="3" /></td>
				                    	<td width="94%" align="left">您所在机构有&nbsp;<span class="red">${homePageBean.operNum10}</span>&nbsp;条付款待修改信息。
				                    	<a href="<%=request.getContextPath()%>/pay/paymodify/list.do?VISIT_FUNC_ID=030304" class="red">点击查看</a>
				                    	
				                    	</td>
				                      </tr>
				                      </c:if>
				                      <c:if test="${homePageBean.operNum11 != 0}">
				                      <tr>
				                    	<td width="6%" height="25" align="center"><img src="<%=request.getContextPath()%>/common/images/dotred.jpg" width="3" height="3" /></td>
				                    	<td width="94%" align="left">您所在机构有&nbsp;<span class="red">${homePageBean.operNum11}</span>&nbsp;条付款待复核信息。
				                    	<a href="<%=request.getContextPath()%>/pay/payexamine/list.do?VISIT_FUNC_ID=030305" class="red">点击查看</a>
				                    	
				                    	</td>
				                      </tr>
				                      </c:if>
				                      <c:if test="${homePageBean.operNum12 != 0}">
				                      <tr>
				                      	<td height="25" align="center"><img src="<%=request.getContextPath()%>/common/images/dotred.jpg" width="3" height="3" /></td>
				                      	<td align="left">您所在机构有&nbsp;<span class="red">${homePageBean.operNum12}</span>&nbsp;条发票退回信息待处理
				                      	<a href="<%=request.getContextPath()%>/pay/invoiceback/list.do?VISIT_FUNC_ID=03030902" class="red">点击查看</a>
				                      	</td>
				                      </tr>
				                      </c:if>
				                      <c:if test="${homePageBean.operNum13 != 0}">
					                  <tr>
					                    <td height="25" align="center"><img src="<%=request.getContextPath()%>/common/images/dotred.jpg" width="3" height="3" /></td>
					                    <td align="left">您所在机构有&nbsp;<span class="red">${homePageBean.operNum13}</span>&nbsp;条付款发票审核信息。
                                        <a href="<%=request.getContextPath()%>/pay/paysure/list.do?VISIT_FUNC_ID=030306" class="red">点击查看</a>
                                        </td>
					                  </tr>
				                      </c:if>
				                    </table>
			              		</div>
			                   </td>
			               </tr>
			               <c:if test="${ppRight eq 'ppRight'}">
			               <tr>
			                    <td width="6%" height="20" align="left"  colspan="2">
				                    <p onclick="showHidden(this,'task3');" style="cursor: pointer;"><img  height=10  src="<%=request.getContextPath()%>/common/images/hp_03b.gif" width=10  />&nbsp;&nbsp;预提待摊(&nbsp;<span class="red">${homePageBean.operNum21+homePageBean.operNum22+homePageBean.operNum23+homePageBean.operNum24}</span>&nbsp;)：</p>
				                    <div id="task3" style="display:none">
					                    <table  style="width: 97%;" border="0" align="center" cellpadding="0" cellspacing="0" class="noboder">
				                           <c:if test="${homePageBean.operNum21 != 0}">
					                    	<tr>
					                        	<td width="6%" height="25" align="center"><img src="<%=request.getContextPath()%>/common/images/dotred.jpg" width="3" height="3" /></td>
					                        	<td width="94%" align="left">您所在机构有&nbsp;<span class="red">${homePageBean.operNum21}</span>&nbsp;条冲销预提任务未成功。
					                        	<a href="<%=request.getContextPath()%>/amortization/fmsMgr/getCglBatchList.do?VISIT_FUNC_ID=040304" class="red">点击查看</a></td>
					                       </tr>
					                       </c:if>
					                       <c:if test="${homePageBean.operNum22 != 0}">
						                   <tr>
						                    	<td height="25" align="center"><img src="<%=request.getContextPath()%>/common/images/dotred.jpg" width="3" height="3" /></td>
						                    	<td width="94%" align="left">您所在机构有&nbsp;<span class="red">${homePageBean.operNum22}</span>&nbsp;条预提待摊任务未成功。
						                    	<a href="<%=request.getContextPath()%>/amortization/fmsMgr/getCglBatchList.do?VISIT_FUNC_ID=040304" class="red">点击查看</a>
						                    	</td>
						                  </tr>
						                  </c:if>
						                  <c:if test="${homePageBean.operNum23 != 0}">
						                  <tr>
						                    	<td height="25" align="center"><img src="<%=request.getContextPath()%>/common/images/dotred.jpg" width="3" height="3" /></td>
						                    	<td width="94%" align="left">您所在机构有&nbsp;<span class="red">${homePageBean.operNum23}</span>&nbsp;条预提待经办信息。
						                    	<a href="<%=request.getContextPath()%>/amortization/provisionMgr/provhandle.do?VISIT_FUNC_ID=040501" class="red">点击查看</a></td>
						                  </tr>
						                  </c:if>
						                  <c:if test="${homePageBean.operNum24 != 0}">
						                  <tr>
						                    	<td height="25" align="center"><img src="<%=request.getContextPath()%>/common/images/dotred.jpg" width="3" height="3" /></td>
						                    	<td width="94%" align="left">您所在机构有&nbsp;<span class="red">${homePageBean.operNum24}</span>&nbsp;条预提经办待复核信息。
						                    	<a href="<%=request.getContextPath()%>/amortization/provisionMgr/provrecheck.do?VISIT_FUNC_ID=040502" class="red">点击查看</a></td>
						                  </tr>
						                  </c:if>
				                       </table>
			              			</div>
		                    	</td>
		                  </tr>
		                  </c:if>
		                  <c:if test="${interfaceRight eq 'interfaceRight'}">
		                  <tr>
			                    <td width="6%" height="20" align="left"  colspan="2">
				                    <p onclick="showHidden(this,'task4');" style="cursor: pointer;"><img  height=10  src="<%=request.getContextPath()%>/common/images/hp_03b.gif" width=10  />&nbsp;&nbsp;接口处理(&nbsp;<span class="red">${fmsErrorBean.dwnErrorNum+fmsErrorBean.updErrorNum+fmsErrorBean.chkErrorNum}</span>&nbsp;)：</p>
				                    <div id="task4" style="display:none">
				                    	 <table  style="width: 97%;" border="0" align="center" cellpadding="0" cellspacing="0" class="noboder">
					                     <c:if test="${fmsErrorBean.dwnErrorNum != 0}">
					                    	<tr>
					                        	<td width="6%" height="25" align="center"><img src="<%=request.getContextPath()%>/common/images/dotred.jpg" width="3" height="3" /></td>
					                    		<td width="94%" align="left"><span class="red">${fmsErrorBean.dwnErrorNum}</span>&nbsp;个文件下载处理失败。<a href="<%=request.getContextPath()%>/amortization/fmsMgr/fmsDownloadList.do?VISIT_FUNC_ID=040303&dataFlag=03" class="red">点击查看</a></td>
					                       </tr>
					                       </c:if>
					                       <c:if test="${fmsErrorBean.updErrorNum != 0}">
						                   <tr>
						                    	<td height="25" align="center"><img src="<%=request.getContextPath()%>/common/images/dotred.jpg" width="3" height="3" /></td>
						                    	<td align="left"><span class="red">${fmsErrorBean.updErrorNum}</span>&nbsp;个上传文件处理失败。<a href="<%=request.getContextPath()%>/amortization/fmsMgr/fmsUploadList.do?VISIT_FUNC_ID=040302&dataFlag=09" class="red">点击查看</a></td>
						                  </tr>
						                  </c:if>
						                   <c:if test="${fmsErrorBean.chkErrorNum != 0}">
						                  <tr>
						                    	<td height="25" align="center"><img src="<%=request.getContextPath()%>/common/images/dotred.jpg" width="3" height="3" /></td>
						                    	<td align="left"><span class="red">${fmsErrorBean.chkErrorNum}</span>&nbsp;个校验文件处理失败。<a href="<%=request.getContextPath()%>/amortization/fmsMgr/fmsUploadList.do?VISIT_FUNC_ID=040302&dataFlag=05" class="red">点击查看</a></td>
						                  </tr>
						                  </c:if>
				                       </table>
			              			</div>
		                    	</td>
		                  </tr>
		                  </c:if>
		                  <tr>
		                    	<td height="20" align="center">&nbsp;</td>
		                    	<td align="left"><a href="#"></a></td>
		                  </tr>
		              </table>
				  </td>
				  <td style="height: 115px;width: 1px;padding: 0px;"  class="booklinegray" ></td>
	             
	            </tr>
	            <tr>
	              <td   style="height: 8px;" colspan="3" valign="top"><img src="<%=request.getContextPath()%>/common/images/book-down.png" width="100%" style="height: 8px;" /></td>
	            </tr>
             </table>
		  </td>
          <td style="margin-left: 2px;margin-right: 2px;" width="30" align="center" valign="middle"></td>
          <td width="381" valign="top" >
	          <table style="height: 160px;" width="100%"  border="0" cellpadding="0" cellspacing="0" class="noboder">
	            <tr >
	              <td  style="height: 7px;"  colspan="3"  valign="bottom"><img src="<%=request.getContextPath()%>/common/images/bookright.png" width="100%" style="height: 7px;" /></td>
	            </tr>
	            <tr>
	              <td style="height: 115px;width: 1px;padding: 0px;"  class="booklinegray" ></td>
	              <td   valign="top"   style="width: 540px;padding: 0px;">
		              <table  style="width: 97%;" border="0" align="center" cellpadding="0" cellspacing="0" class="noboder">
		                   <tr>
		                    <td style="height: 25px;" align="left" colspan="2" class="fonttop">滚动信息</td>
		                  </tr>
		                  <tr >
		                    <td style="height: 5px;" colspan="2"  background="<%=request.getContextPath()%>/common/images/dot-line.jpg"></td>
		                  </tr>
		                  <tr>
		                    <td style="height: 5px;" colspan="2"></td>
		                  </tr>
		                  <tr>
		                    <td width="6%" colspan="2" height="75px;" align="left">
		                    <p onclick="showHidden(this,'rollInfo');" style="cursor: pointer;"><img  height=10  src="<%=request.getContextPath()%>/common/images/hp_03b.gif" width=10  />&nbsp;&nbsp;通知类信息(&nbsp;<span class="red">${rollInfoNumb }</span>&nbsp;)：</p>
		                    <div id="rollInfo" style="display:none">
		                    	<MARQUEE onmouseover=this.stop(); onmouseout=this.start(); 
						trueSpeed scrollAmount=1 scrollDelay=40 direction=up width=100% height=125>
       <table width="100%" border=0 cellPadding=3 cellSpacing=0>
              <tbody>
             <c:if test="${!empty rollInfos}">
				<c:forEach items="${rollInfos}" var="rollInfos">
					<tr >
				  		<td align="center" width="40"><img  height="3"  src="<%=request.getContextPath()%>/common/images/dotred.jpg" width="3" /></td>
                  		<td width="500" height=10>
                 	 		<a class="news" title='${rollInfos.rollInfo}' 
						   	  href="javascript:void(0);"  onclick="rollDetail('${rollInfos.rollId}')">
    			 						${rollInfos.addTime}:&nbsp;&nbsp;${rollInfos.rollInfo}
    			  				<img height="14" src="<%=request.getContextPath()%>/common/images/new.gif" width=25 border=0 />					
                  			</a>
                 	 	</td>
                	</tr>
				</c:forEach>
               </c:if> 
                       
      </tbody>
      </table>
      
	</MARQUEE>
				<div align="right">
					<a title="更多"  href="#" onclick="rollList()">更多...</a>
				</div>
	</div>
		                    </td>
		            
		                  </tr>
		                  <tr>
		                    <td width="6%" colspan="2" height="75px;" align="left">
		                    <p onclick="showHidden(this,'exception');" style="cursor: pointer;"><img  height=10  src="<%=request.getContextPath()%>/common/images/hp_03b.gif" width=10  />&nbsp;&nbsp;系统异常信息类(&nbsp;<span class="red" id="excepspan">${excepInfoNumb }</span>&nbsp;)：</p>
		                    	<div id="exception" style="display:none">
		                    	<MARQUEE onmouseover=this.stop(); onmouseout=this.start(); 
						trueSpeed scrollAmount=1 scrollDelay=40 direction=up width=100% height=125>
    
       <table width="100%" border=0 cellPadding=3 cellSpacing=0>
              <tbody class="excepTable">
              
              	 <c:if test="${!empty excepInfos}">
				<c:forEach items="${excepInfos}" var="excepInfos">
					<tr >
				  		<td align="center" width="40"><img  height="3"  src="<%=request.getContextPath()%>/common/images/dotred.jpg" width="3" /></td>
                  		<td width="500" height=10 class="excepTd">
                 	 		<a class="news" title='${excepInfos.excepInfo}' 
						   	  href="javascript:void(0);"  onclick="excepDetail('${excepInfos.excepId}')">
    			 					${excepInfos.addTime}:&nbsp;&nbsp;${excepInfos.excepInfo}
    			  				<img height="14" src="<%=request.getContextPath()%>/common/images/new.gif" width=25 border=0 />					
                  			</a>
                 	 	</td>
                	</tr>
				</c:forEach>
               </c:if> 
      </tbody>
      </table>
	</MARQUEE>
		<div align="right">
					<a title="更多"  href="#" onclick="excepList()">更多...</a>
				</div>
	</div>
		                    </td>
		            
		                  </tr>
		                  
		                  <tr>
		                    <td height="25" align="center">&nbsp;</td>
		                    <td align="left"><a href="#"></a></td>
		                  </tr>
		              </table>
				  </td>
	              <td   style="width: 1px;padding: 0px;" class="booklinegray"></td>
	            </tr>
	            <tr>
	              <td style="height:8px;" colspan="3" valign="top"><img src="<%=request.getContextPath()%>/common/images/book-down.png" width="100%" height="8"/></td>
	            </tr>
	          </table>
          </td>
        </tr>
      </table>
	  <br/><br/>
	  
     
  
<!--  <div class="copyright"><p>Copyright 2015@中国银行股份有限公司</p></div>  -->
 <div> <p style="text-align: center">Copyright 2016@中国银行股份有限公司</p></div>
</body>
</html>