<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="decorator" content="none">
<title>中国银行总行财务项目管理系统</title>
<jsp:include page="../../../common/jsp/include/common.jsp"></jsp:include>
<style type="text/css">
.loc_main{background: rgb(255, 255, 255) ; width: 1000px;height: 350px;margin: 20px auto;margin-top:120px;padding-top: 30PX;position: relative;}
.loc_top{ width: 900px;height: 80px;}
.loc_mid{width: 900px;height: 350px;}

.loc_main .submit {background:#BEBEBE; text-align:center; color: #2A2A2A; font-weight: bold; width: 288px;height:43px;border-left:1px solid #BABABA; }
.loc_main .username {background: url("../images/login/username.bmp");}
 
.selectway a{ color: rgb(153, 153, 153); text-decoration: none; cursor: pointer;font-size: 16px;font-weight: 100;}
.selectway a:hover { color: rgb(51, 51, 51);} 
.selected { border-bottom-width: 2px; border-bottom-style: solid; border-bottom-color: rgb(220, 60, 0);}
.selected  a{color: red;cursor:default;}
.selected  a:hover{color: red;cursor:default;}

</style>

<base target="_self">
<script type="text/javascript"  >
function pagInit(){
// 	var va=$("input[name='questionAnswer']").val();
// 	alert(va);
}

function changeFindWay(flag)
{
	if(flag=='1') 
	{
		$("#questionLi").removeClass("selected");
		$("#emailLi").addClass("selected");
		$("#questionDivId").hide();
		$("#emailDivId").show();
	}
	else if(flag=='2')
	{
		checkExistQuestion();
		$("#emailLi").removeClass("selected");
		$("#questionLi").addClass("selected");
		$("#emailDivId").hide();
		$("#questionDivId").show();
	}
}



function toStepTwo()
{
		$(".step2").removeClass("step2").addClass("step22");
		$("#loc_mid_page1").hide();
		$("#loc_mid_page2").show();
		$("#userIdHidden").val($("#userId").val());
		queryPwdSafe();
}


function toStepThree()
{
		$(".step3").removeClass("step3").addClass("step33");
		$("#loc_mid_page2").hide();
		$("#loc_mid_page3").show();
}

//进入第二步的校验

function toStepTwoCheck()
{
	var userId=$("#userId").val();
	if(userId==''||userId==null)
	{
		$("#errorMsg span").html("请输入用户名。");
		$("#errorMsg").show();
		return false;
	}
	var data = {};
	data['userId']=userId;
	var url = "common/changePwd/getUserInfoAjax.do";
	App.ajaxSubmit(
			url, 
			{data : data,async:false},
			function(data)
			{
				//显示email地址 add by hp
				$("#email").val(data.notesId);
				if(data.notesId==""||data.notesId==null){
					$("#ems").hide();
					$("#step2ButEmail").hide();
					$("#email_msg").hide();
					$("#email_error_msg").show();					
					$("#email_error_msg").find("span").html("您没有设置邮箱密保，可能是设置了密保问题！");
					
				}else{
					$("#email_show").html(data.notesId);
				}
				toStepTwo();
			}, 
			function(failureMsg,data)
			{
				$("#errorMsg span").html(failureMsg);
				$("#errorMsg").show();
// 				$("#ems").hide();
// 				$("#email_error_msg").find("span").html(data.notesId);
				
			}
		);
	
	//输入的用户名不存在
	//输入的用户名是否已设置密保问题ajax
	
}

//根据用户名称查找用户密保问题==换个问题功能
function queryPwdSafe()
{
	$("input[name='questionAnswer']").val("");
	var userId =$("#userIdHidden").val();
	var questionSeq =$("#questionSeqHidden").val();
	var url = "common/changePwd/getPwdSafeAjax.do?userId="+userId+"&questionSeq="+questionSeq;
	App.ajaxSubmit(
			url, 
			{
				async:false
			}, 
			function(data)
			{
				$("#questionInfo").html(data.pwdSafebean.questionInfo);
				$("#questionSeqHidden").val(data.pwdSafebean.questionSeq);
				$("#questionIdHidden").val(data.pwdSafebean.questionId);
			}, 
			function(failureMsg,data)
			{
			}
		);
}

//回答下一个问题
var num=0;
function nextQuestion(){
	num++;
	var numb=num;
	$("#question").hide();
	queryPwdQuestion(numb);
	if(num>1){
		$("div#step2But input").attr("style","display:inline");
		$("input[name='questionAnswer']+input").attr("style","display:none");
	}
	
}

function checkExistQuestion(){
	var questionAnswer=$("input[name='questionAnswer']").val();
	var data={};
	var userId =$("#userId").val();
	data['userId']=userId;
	var url = "common/changePwd/checkExistQuestion.do";
	App.ajaxSubmit(
			url, 
			{
				data : data ,async:false
			}, 
			function(data)
			{
			}, 
			function(failureMsg,data)
			{
				$("#question span").html(failureMsg);
				$("#question").show();
				$("#gd").hide();
				$("#anq").hide();
			}
		);
}

//进入第三步的校验
function queryPwdQuestion(numb)
{
	var questionAnswer=$.trim($("input[name='questionAnswer']").val());
	if(questionAnswer==''||questionAnswer==null)
	{
		num--;
		$("#question span").html("密保问题答案不能为空。如果忘记了答案或者没有设置密保问题，请联系管理员！");
		$("#question").show();
		return;
	}
	var data={};
	var userId =$("#userId").val();
	var questionId=$("#questionIdHidden").val();
	data['userId']=userId;
	data['questionAnswer']=questionAnswer;
	data['questionId']=questionId;
	var url = "common/changePwd/queryPwdQuestion.do";
	App.ajaxSubmit(
			url, 
			{
				data : data ,async:false
			}, 
			function(data)
			{
				if(numb>2){
					toStepThree();
				}else if(numb<=2){
					queryPwdSafe();
				}
			}, 
			function(failureMsg,data)
			{
				num--;
				$("#question span").html("当前密保问题答案错误，如果忘记了答案或者没有设置密保问题，请联系管理员！");
				$("#question").show();
			}
		);
}

//修改密码校验与提交
function updatePwd()
{
	var password = $("input[name='password']").val();
	var surePassword = $("input[name='surePassword']").val();
	if(!checkNewPassword()){
		return;
	};
	if(surePassword==null||surePassword==""){
		$("#password1").hide();
		$("#password2 span").html("密码不能为空。");
		$("#password2").show();
		return;
	}
	if(surePassword!=password){
		$("#password1").hide();
		$("#password2 span").html("两次密码输入不一致，请重新输入。");
		$("#password2").show();
		return;
	}
	$('#loginForm').submit();
}
//校验此密码在历史密码表中是否已经到达设置次数上限
function checkPasswordCount() {
	var flag = true;
	var data = {};
	data['password'] = $("#password").val();
	data['userId'] = $("#userId").val();
	App.ajaxSubmit("common/changePwd/checkPasswordCount.do", {
		data : data,
		async : false
	}, function(data) {
		flag = data.isOut;

	});
	return flag;

}

//密码框失去焦点时进行判断，是否符合格式，设置次数是否以达到上限
function checkNewPassword(){
	var newPassword=$("input[name='password']").val();
	if(newPassword.length>6||newPassword.length==6){
		var passwordExp=/((?=.*\d)(?=.*\D)|(?=.*[a-zA-Z])(?=.*[^a-zA-Z]))^.{6,16}$/;
		if(passwordExp.test(newPassword)){
			if (checkPasswordCount()) {
				$("#password2").hide();
				$("#password1 span").html("此密码已达到设置次数上限!");
				$("#password1").show();
				return false;
			}else{
				return true;
			}
		}
		else{
			$("#password2").hide();
			$("#password1 span").html("密码必须由6-16位英文字符和数字组成。");
			$("#password1").show();
			return false;
		}
		
	}else{
		$("#password2").hide();
		$("#password1 span").html("密码必须由6-16位英文字符和数字组成。");
		$("#password1").show();
		return false;
	}
}
function pwdFocus(){
	$("#password2").hide();
	$("#password1").hide();
}

//发送重置密码邮件 add by hp
function sendResetPassEmail()
{
	var email = $("#email").val();
	if(email==null||email==""){
		$("#email_error_msg span").html("未设置邮箱地址，不能通过邮箱重置密码");
		$("#email_error_msg").show();
		return;
	}
	
	var data = {};
	data['userId']=$("#userId").val();
	data['email']=email;
	var url = "common/changePwd/sendResetPassEmail.do";
	App.ajaxSubmit(
			url, 
			{data : data,async:false},
			function(data)
			{
				$("#email_msg span").html("您的申请已提交成功，请查看您的" + $('#email').val() + "邮箱。");
				$("#email_msg").show();
				$("#step2ButEmail").hide();//隐藏发送按钮
			}, 
			function(failureMsg,data)
			{
			}
		);
}

$(document).ready (function(){
	var resetpwdreq = $("#resetpwdreq").val();
	if("RPR"===resetpwdreq){
		$(".step2").removeClass("step2").addClass("step22");
		$(".step3").removeClass("step3").addClass("step33");
		$("#loc_mid_page1").hide();
		$("#loc_mid_page3").show();
		$("#userId").val($("#userIdhide").val());
	}
});
</script>
</head>
<body  >
    <input type="hidden" name="resetpwdreq" id="resetpwdreq" value="${resetpwdreq}"/>
    <input type="hidden" name="userIdhide" id="userIdhide" value="${userId}"/>
    <div class="logo"></div>
    <form action="<%=request.getContextPath()%>/common/changePwd/updatePwd.do"  method="post" id="loginForm">
  
	<div class="loc_main" align="center">
		<div class="loc_top" >
		<div class="step1" ><span style="position: absolute;left: 35px;top: 8px;">输入待找回的用户名</span></div>
		<div class="step2" ><span style="position: absolute;left: 40px;top: 8px;">找回密码方式</span></div>
		<div class="step3"  ><span style="position: absolute;left: 40px;top: 8px;">密码找回</span></div>
		</div>
		<input type="hidden"  name="userIdHidden" id="userIdHidden" >
		<input type="hidden"  name="questionSeqHidden"  id="questionSeqHidden" >
		<input type="hidden"  name="questionIdHidden"  id="questionIdHidden" >
		
		
		
		<div class="loc_mid"  id="loc_mid_page1">
		<div  style="height: 120px;">
		<div   style="padding-top: 20px;height: 60px;" align="center">
			用户名&nbsp;&nbsp;<input type="text" style="height: 35px;width: 220px;font-size: large;" class="user-id" id="userId" name="userId"/>
		</div>
		<div id="errorMsg"  style="padding-top: 0px;display: none;height: 40px;"  align="center">
			<img  src="<%=request.getContextPath()%>/common/images/warning_s.gif">&nbsp;&nbsp;<span style="color: red;"></span>
		</div>
		</div>
		<div class="loc_button" id="step1But" style="padding-top: 40px;" >
				<input type="button"  class="submit" onclick="toStepTwoCheck();" value="下一步">
		</div>
			
		</div>
		<div class="loc_mid" id="loc_mid_page2"  style="display: none;">
		
		<div class="selectway"  style="height: 30px;" align="center"  >
		
		<ul  style="list-style: none;width: 500px; text-align: center;">
		<li class="selected" style="float: left;"  id="emailLi">
		<a    href="javascript:void(0);" onclick="changeFindWay(1);return false;">通过邮箱找回</a></li>
		<li style="float: left;margin-left: 10px;"  id="questionLi"><a  onclick="changeFindWay(2);return false;" href="javascript:void(0);">通过密保问题找回</a></li>
		</ul>
			
		</div>
		
		<div class="email" id="emailDivId" style="height: 50px;" align="center">
		<div  style="height: 80px;">
		     <div style="height: 30px;" id="ems">
		     	<input type="hidden" style="height: 30px;width: 280px;font-size: medium;" name="email" id="email"/>
				您的邮箱为：&nbsp;&nbsp;<span id="email_show" style="color: red;"></span>
			</div>
			<br/>
			<div id="email_error_msg" style="padding-top: 0px;display: none;height: 40px;" align="center">
				<img src="<%=request.getContextPath()%>/common/images/warning_s.gif">&nbsp;&nbsp;<span style="color: red;"></span>
			</div>
			<div id="email_msg" style="padding-top: 0px;display: none;height: 40px;" align="center">
				<img src="<%=request.getContextPath()%>/common/images/info_s.gif">&nbsp;&nbsp;<span style="color: green;"></span>
			</div>
		</div>	
			<div class="loc_button" id="step2ButEmail" style="padding-top: 20px;" >
				<input type="button" class="submit"  onclick="sendResetPassEmail();" value="找回密码">
		    </div>
		</div>
		
		<div class="question"  id="questionDivId"  style="height: 200px;display: none;" align="center" >
		    <div  style="height:140px;">
		     <div style="font-size: x-large;font-weight:lighter; font-family: cursive;padding-top: 10px;height: 50px;" id="gd">
		                  密保问题:  <span id="questionInfo"></span>
<!-- 		                  <a style="cursor: pointer;padding-top: 80px;"  href="javascrpt:void(0);"  onclick="queryPwdSafe();return false;"  ><span style="vertical-align:text-bottom;color: red;padding-top: 30px;font-size: small;" >换个问题</span></a> -->
		     </div>

			<div style="height: 30px;" id="anq">
			答案&nbsp;&nbsp;<input type="text" style="height: 30px;width: 280px;font-size: medium;"  name="questionAnswer"/>
			&nbsp;&nbsp;<input type="button" style="display:inline" value="回答下一个问题" onclick="nextQuestion();return false;"></input>
			</div>
			<br/>
			<div id="question"  style="padding-top: 0px;display: none;height: 40px;" align="center">
			<img  src="<%=request.getContextPath()%>/common/images/warning_s.gif">&nbsp;&nbsp;<span style="color: red;"></span>
		</div>
		</div>
		<div class="loc_button" id="step2But" style="padding-top: 10px;" >
						<input type="button" style="display: none" class="submit"  onclick="queryPwdQuestion(3);" value="下一步">
		</div>
		
		</div>
		
		</div>
			
		<div class="loc_mid" id="loc_mid_page3" style="display: none;">
			<div     style="height: 105px; position: relative;" >
					     <div style="height: 35px;padding-top: 20px;"><span style="width: 80px;position: absolute;left: 270px;top: 25px;">新密码</span><input type="password" style="height: 25px;width: 220px;position: absolute;left: 360px;" id="password" name="password" onblur="checkNewPassword()" maxlength="16" onfocus="pwdFocus()"/><span id="password1" style="height: 25px;display: none;position: absolute;left: 590px;top: 25px;"><img  src="<%=request.getContextPath()%>/common/images/warning_s.gif">&nbsp;&nbsp;<span style="color: red;"></span></span></div>
					     <div style="height: 35px;padding-top: 20px;"><span style="width: 80px;position: absolute;left: 260px;top: 77px;">新密码确认</span><input type="password" style="height: 25px;width: 220px;position: absolute;left: 360px;" name="surePassword" maxlength="16"/><span id="password2" style="display: none;height: 25px;position: absolute;left: 590px;top: 77px;"><img  src="<%=request.getContextPath()%>/common/images/warning_s.gif">&nbsp;&nbsp;<span style="color: red;"></span></span></div>
			</div>
			
			<div class="loc_button" id="step3But" style="padding-top: 80px;">
						<input type="button" class="submit"  onclick="updatePwd();" value="确认修改">
			</div>
		</div>
	</div>
	  </form>
	<div style="width: 100%;height: 150px;background: rgb(255, 255, 255); ">
		<div class="copyright" align="center">
			<p>Copyright 2016@中国银行股份有限公司</p>
		</div>
	</div>
	
</body>
</html>