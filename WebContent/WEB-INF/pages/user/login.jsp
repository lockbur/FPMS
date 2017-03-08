<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/platform-tags" prefix="p"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="decorator" content="none">
<title>中国银行总行财务项目管理系统</title>
<jsp:include page="../../../common/jsp/include/common.jsp"></jsp:include>
<style type="text/css">
.message-panel {
	position: absolute;
	top: 100px;
	left: 430px;
	text-align: left;
	color: #BC314B;
}
</style>
<script type="text/javascript">
	function pageInit() {
		hideLabel('user-id-label');
		hideLabel('password-label');
		setTimeout(function() {
			if (($.trim($('#userId').val())) == '') {
				showLabel($('#userId'), 'user-id-label');
			}
			if (($.trim($('#password').val())) == '') {
				showLabel($('#password'), 'password-label');
			}
		}, 100);
	}

	function submitLoginForm() {
		if (($.trim($('#userId').val())) == '') {
			App.notyWarning("请输入用户名");
			return;
		}
		if (($.trim($('#password').val())) == '') {
			App.notyWarning("请输入密码");
			return;
		}
		//$('#loginForm').submit();
		checkInitpwd();

	}
	function checkInitpwd() {

		var userId = $("#userId").val();
		var password = $("#password").val();
		var data = {};
		data['userId'] = userId;
		data['password'] = password;
		App.ajaxSubmit("common/changePwd/checkInitPwd.do", {
			data : data,
			async : false
		}, function(data) {
			var flag = data.flag;
			var msg = data.msg;
			if(flag == "N"){
				$("#errorId").html(msg);
				return false;
			}
			if(flag == "O"){
				// 密码过期 修改密码
				showChangePwd(msg);
				return false;
				
			}
			if(flag == "I"){
				//初始密码 修改密码
				showChangePwd(msg);
				return false;
			}
			if(flag == "noRole"){
				//该用户没有角色
				$("#errorId").html(msg);
				return false;
			}
			$('#loginForm').submit();

		});
	}
	function showChangePwd(msg) {
		//修改
		$("#auditDiv").dialog({
			title : "<span style='color:red'>"+msg+"</span>",
			closeOnEscape : false,
			autoOpen : true,
			height : 'auto',
			width : 600,
			modal : true,
			zIndex : 100,
			dialogClass : 'dClass',
			resizable : false,
			open : function() {
				$("#user_id").val($("#userId").val());
			},
			buttons : {
				"确定" : function() {
					if (!App.valid("#pwdForm")) {
						return;
					}
					var upPwd = $("#upPwd").val();
					var confirmPwd = $("#confirmPwd").val();
					if(!checkNewPassword()){
						return false;
					}
					if (upPwd != confirmPwd) {
						$("#confirmPwdSpan").html("密码不一致！");
						return;
					}
					//判断是否超过设置次数
					if (checkPasswordCount()) {
						$("#confirmPwdSpan").html("此密码已达到设置次数上限!");
						return false;
					}
					
					var data = {};
					data['userId'] = $("#userId").val();
					data['confirmPwd'] = confirmPwd;
					App.ajaxSubmit("common/changePwd/changePwd.do", {
						data : data,
						async : false
					}, function(data) {
						var flag = data.flag;
						if(flag == true){
							$("#password").val(confirmPwd);
// 							$('#loginForm').submit();
						}else{
							//密码修改错误
							App.notyError("密码修改错误！");
							$(this).dialog("close");
						}

					});
					App.submitShowProgress();//锁屏
					$('#pwdForm').submit();
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			}
		});
	}
	//检验设置的新密码是否超过修改次数
	function checkPasswordCount() {
		var flag = true;
		var data = {};
		data['password'] = $("#upPwd").val();
		data['userId'] = $("#userId").val();
		App.ajaxSubmit("common/changePwd/checkPasswordCount.do", {
			data : data,
			async : false
		}, function(data) {
			flag = data.isOut;

		});
		return flag;

	}

	function hideLabel(id) {
		$('#' + id).hide();
	}

	function showLabel(elem, id) {
		if ($.trim($(elem).val()) == '') {
			$('#' + id).show();
		}
	}

	function focusInput(id) {
		$('#' + id).focus();
	}

	function reset() {
		$('#loginForm')[0].reset();
	}

	//处理密码框的回车
	function dealSubmitKeyPress(pObj, event) {
		var keyCode = event.keyCode ? event.keyCode : event.which ? event.which
				: event.charCode;
		if (keyCode == 13) {
			submitLoginForm();
		}
	}
	
	
</script>
</head>
<body>
	<div class="index">
		<div class="logo"></div>
		<div class="login-bg"></div>
		<div class="login-panel">
			<form action="<%=request.getContextPath()%>/user/login.do"
				method="post" id="loginForm">
				<p:message className="message-panel" infoCls="info-message"
					warningCls="warning-message" errorCls="error-message" />
				<span id="errorId"
					style="position: absolute; top: 20px; left: 20px; color: red; font-weight: bold;">
					${errorMsg }
				</span>
				<div class="username">
					<input type="text" class="user-id" id="userId" name="userId" value=""/>
				</div>
				<div class="pwd">
					<input type="password" class="password" id="password"  value=""
						name="password" onkeypress="dealSubmitKeyPress(this, event)" />
				</div><%-- 
				<div  style="top: 150px;position: absolute;right: 30px;">
				 <a style="cursor: pointer;"  href="<%=request.getContextPath()%>/common/changePwd/openFindPassword.do"  target="_blank" ><span>忘记密码？</span></a>
				</div> --%>
				
				
				 <input type="button" class="submit"
					onclick="submitLoginForm()" value="登  录">
				</div>
			</form>
		</div>
		<div class="loginCopyright">
			<p>Copyright 2016@中国银行股份有限公司</p>
		</div>
	</div>
	<jsp:include page="changePwd.jsp" />
</body>
</html>