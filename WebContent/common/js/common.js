function initButton() {
	$( "input[type=submit],[type=button],button").button().addClass("base-button");
}

function pageInit() {
}

function doValidate(form) {
	return true;
}

function beforeSubmit(form) {
}

$(function(){
	initButton();	
	pageInit();
});

var App = {
	ROOT : '',
	FUNC_KEY : '',
	CURR_FUNC_ID : '',
	FIRST_LEVEL_MENU : '',
		
	submit : function(form, validate) {
		if (beforeSubmit && "function" == typeof(beforeSubmit)) {
			beforeSubmit(form);
		}
		
		if (validate && doValidate && "function" == typeof(doValidate)) {
			if (!doValidate(form))
				return false;
		}
		App.submitShowProgress();
		form.submit();
	},

	submitForm : function(button, url) {
		var forms = $(button).parents('form');
		if (forms.length > 0) {
			var form = forms[0];
			form.action = url;
			App.submit(form, true);
		}
	},
	
	ajaxSubmit : function(url, options, callback, failure) {
		var tempUrl = url;
		options = options || {};
		var data = options.data || {};
		var form = options.form;
		if (typeof form === 'string') {
			form = '#' + form;
		}
		if ($(form).length > 0) {
			var formFields = $(form).serialize().split("&");
			for (var i = 0; i < formFields.length; i++) {
				var field = formFields[i].split("=");
				if(data[field[0]]){
				//如果之前有这个属性
					data[field[0]] += "," + decodeURIComponent(field[1].replace(/\+/g, ' '));
				}else{
					data[field[0]] = decodeURIComponent(field[1].replace(/\+/g, ' '));
				}
			}
		}
		data.REQUEST_TYPE = 'ajax';
		var params = {
				url		:	App.ROOT + url,
				type	:	'post',
				data	:	data,
				dataType:	'json',
				contentType: "application/x-www-form-urlencoded; charset=utf-8", 
				async	:	options.async !== false,
				success	:	function(data){
					if ('success' == data.result) {
						callback(data.data);
					} else if ('failure' == data.result) {
						var showFailureMsg = true;
						if (failure) {
							showFailureMsg = failure(data.failureMsg, data.data);
						}
						
						if (showFailureMsg) {
							if (data.data.gotoLogin) {
								$( "<div>" + data.failureMsg + "</div>" ).dialog({
									dialogClass: 'unloginDialogClass',
									resizable: false,
									width: 500,
									height:'auto',
									modal: true,
									buttons: {
										"登 录": function() {
											location.href = App.ROOT;
											$( this ).dialog('close');
										}
									}
								});
							} else {
								$( "<div>" + data.failureMsg + "</div>" ).dialog({
									dialogClass: 'errorDialogClass',
									resizable: false,
									width: 516,
									height: 296,
									modal: true,
									buttons: {
										"确 定": function() {
											$( this ).dialog('close');
										}
									}
								});
							}
						}
					}
					App.submitFinish();
				},
				error : function(event) {
					$( "<div>请求出错</div>" ).dialog({
						dialogClass: 'dClass dTipClass',
						title: '请求出错',
						resizable: false,
						height:'auto',
						modal: true,
						buttons: {
							"确定": function() {
								$( this ).dialog('close');
							}
						}
					});
					App.submitFinish();
				},
				beforeSend:	function(){
				}
			};
			if(tempUrl.indexOf("function/getSubMenu.do")!=-1){
				//donothing
			}else{
				App.submitShowProgress();
			}
			jQuery.ajax(params);
	},
	ajaxSubmitForm : function(url, form, callback, failure) {
		var tempUrl = url;
		var params = {
				url		:	App.ROOT + url,
				type	:	'post',
				data	:	$(form).serialize(),
				dataType:	'json',
				contentType: "application/x-www-form-urlencoded; charset=utf-8", 
				async	:	false,
				success	:	function(data) {
					if ('success' == data.result) {
						callback(data.data);
					} else if ('failure' == data.result) {alert("error");
						var showFailureMsg = true;
						if (failure) {
							showFailureMsg = failure(data.failureMsg, data.data);
						}
						
						if (showFailureMsg) {
							if (data.data.gotoLogin) {
								$( "<div>" + data.failureMsg + "</div>" ).dialog({
									dialogClass: 'unloginDialogClass',
									resizable: false,
									width: 500,
									height:'auto',
									modal: true,
									buttons: {
										"登 录": function() {
											location.href = App.ROOT;
											$( this ).dialog('close');
										}
									}
								});
							} else {
								$( "<div>" + data.failureMsg + "</div>" ).dialog({
									dialogClass: 'errorDialogClass',
									resizable: false,
									width: 516,
									height: 296,
									modal: true,
									buttons: {
										"确 定": function() {
											$( this ).dialog('close');
										}
									}
								});
							}
						}
					}
					App.submitFinish();
				},
				error : function(event) {
					$( "<div>请求出错</div>" ).dialog({
						dialogClass: 'dClass dTipClass',
						title: '请求出错',
						resizable: false,
						height:'auto',
						modal: true,
						buttons: {
							"确定": function() {
								$( this ).dialog('close');
							}
						}
					});
					App.submitFinish();
				},
				beforeSend:	function(){
				}
			};
			if(tempUrl.indexOf("function/getSubMenu.do")!=-1){
				//donothing
			}else{
				App.submitShowProgress();
			}
			jQuery.ajax(params);
	},
	
	jqueryAutocomplete : function(){
		$.widget( "ui.combobox", {
			_create: function() {
				var self = this,
					select = this.element.hide(),
					selected = select.children( ":selected" ),
					value = selected.text();
				var input = this.input = $( "<input>" ).blur(function(){
					var sel = $(this).parent().find("select :first");
					var selValue = sel.val();
					var selText = "";
					sel.find("option").each(function(){
						if($(this).val() == selValue){
							selText = $(this).text();
						}
					});
					$(this).val(selText);
					
				}).insertAfter( select )
					.val( value )
					.autocomplete({
						delay: 0,
						minLength: 0,
						source: function( request, response ) {
							var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
							response( select.children( "option" ).map(function() {
								var text = $( this ).text();
								
									return {
										label: text.replace(
											new RegExp(
												"(?![^&;]+;)(?!<[^<>]*)(" +
												$.ui.autocomplete.escapeRegex(request.term) +
												")(?![^<>]*>)(?![^&;]+;)", "gi"
											), "<strong>$1</strong>" ),
										value: text,
										option: this
									};
							}) );
						},
						select: function( event, ui ) {
							ui.item.option.selected = true;
							self._trigger( "selected", event, {
								item: ui.item.option
							});
							select.trigger("change");
						},
						change: function( event, ui ) {
							if ( !ui.item ) {
								var matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( $(this).val() ) + "$", "i" ),
									valid = false;
								select.children( "option" ).each(function() {
									if ( $( this ).text().match( matcher ) ) {
										this.selected = valid = true;
										return false;
									}
								});
								if ( !valid ) {
									// remove invalid value, as it didn't match anything
									$( this ).val( "" );
									select.val( "" );
									input.data( "autocomplete" ).term = "";
									return false;
								}
							}
						}
					})
					.addClass( "ui-widget" );

				input.data( "autocomplete" )._renderItem = function( ul, item ) {
					return $( "<li></li>" )
					.data( "item.autocomplete", item )
					.append( "<a>" + trim(item.label) + "</a>" )
					.appendTo( ul );
				};

				this.button = $( "<button type='button' class='ui-autocomplete-button'>&nbsp;</button>" )
					.attr( "tabIndex", -1 )
					.attr( "title", "Show All Items" )
					.insertAfter( input )
					.button({
						text: false
					})
					.click(function() {
						// close if already visible
						if ( input.autocomplete( "widget" ).is( ":visible" ) ) {
							input.autocomplete( "close" );
							return;
						}

						$( this ).blur();

						input.autocomplete( "search", "" );
						input.focus();
					});
			},

			destroy: function() {
				this.input.remove();
				this.button.remove();
				this.element.show();
				$.Widget.prototype.destroy.call( this );
			}
		});
	},
	
	radioCheck : function(labelObj,divId) {
		var labelList = $("#"+divId+" label"); 
		for(var j=0;j<labelList.length;j++)
	    {
			labelList.eq(j).removeClass("check-label"); 
	    }
		labelObj.className="check-label";
	},
	
	notyAlert : function(textStr) {
		var n = noty({
		            text        : textStr, // Can be html or string
		            type        : 'alert',
		            dismissQueue: false, // If you want to use queue feature set this true
		            layout      : 'topCenter',
		            theme       : 'defaultTheme',
		            killer      : true
		        });
		
		setTimeout(function () {
			  $.noty.close(n.options.id);
	        }, 2000);
	},
	
	notyError : function(textStr) {
		var n =noty({
			text        : textStr,
			type        : 'error',
			dismissQueue: false,
			layout      : 'topCenter',
			theme       : 'defaultTheme',
			maxVisible	: 5 // you can set max visible notification for dismissQueue true option
		});
		
		setTimeout(function () {
			  $.noty.close(n.options.id);
	        }, 5000);
	},
	
	notyWarning : function(textStr) {
		var n =noty({
			text        : textStr,
			type        : 'warning',
			dismissQueue: false,
			layout      : 'topCenter',
			theme       : 'defaultTheme',
			maxVisible	: 5 // you can set max visible notification for dismissQueue true option
		});
		
		setTimeout(function () {
			  $.noty.close(n.options.id);
	        }, 2000);
	},
	
	notySuccess : function(textStr) {
		var n = noty({
					text        : textStr,
					type        : 'success',
					dismissQueue: false,
					layout      : 'topCenter',
					theme       : 'defaultTheme',
					maxVisible	: 5 // you can set max visible notification for dismissQueue true option
				});
		
		setTimeout(function () {
			  $.noty.close(n.options.id);
	        }, 2000);
	},
	
	notyInfo : function(textStr) {
		var n = noty({
			text        : textStr,
			type        : 'information',
			dismissQueue: false,
			layout      : 'topCenter',
			theme       : 'defaultTheme',
			maxVisible	: 5 // you can set max visible notification for dismissQueue true option
		});
		
		setTimeout(function () {
			$.noty.close(n.options.id);
		}, 2000);
	},
    
	notyCustom : function(layout,theme,type,text,dismissQueueFlag,timeoutFlag,forceFlag,modalFlag,maxVisible,killerFlag,closeWith,buttonsFlag) {
		noty({
			layout      : layout, // 'top','topCenter','topLeft','topRight','center','centerLeft','centerRight','bottom','bottomCenter','bottomLeft','bottomRight'
			theme       : theme,
			type        : type, // 'alert','information','error','warning','notification','success'
			text        : text, // Can be html or string
			dismissQueue: dismissQueueFlag, // If you want to use queue feature set this true
			template	: '<div class="noty_message"><span class="noty_text"></span><div class="noty_close"></div></div>',
			animation	: {
				open:{height:'toggle'},
				close:{height:'toggle'},
				easing:'swing',
				speed:500 // opening & closing animation speed
			},
			timeout		: timeoutFlag, // delay for closing event. Set false for sticky notification
			force		: forceFlag, // adds notification to beginning of queue when set to true
			modal		: modalFlag,
			maxVisible	: maxVisible, // you can set max visible notification for dismissQueue true option
			killer		: killerFlag, // for close all notification before show
			closeWith	: ['click'], // ['click', 'button', 'hover']
			callback	: {
				onShow: function() {},
				afterShow:function() {},
				onClose:function() {},
				afterClose:function() {}
			},
			buttons		: buttonsFlag // an array of buttons
		});
	},
	valid:function(selector)
	{	
		var flag=true;
		var message="";
		$(selector+" [valid]").each(function(i,s){
			if(!App.validNullCheck(s)){flag=false;}
			if(s.tagName=="SELECT"){
				$(s).next().blur(function(){
					App.validNullCheck(s);
				})
			}else{
				$(s).blur(function(){
					App.validNullCheck(s);
				})
			}
		})
		return flag;
	},

	validNullCheck:function (s)
	{//校验表单空值
		var vvalue=$(s).val();
		if(vvalue==undefined
			||vvalue=="undefined"
			||vvalue==""
			||vvalue==$(s).find("option").eq(0).html())
		{
			if(s.tagName=="SELECT")
			{
				message="请选择.";
			}else
			{
				message="不能为空.";
			}
			if($(s).attr("errorMsg")!=undefined &&$(s).attr("errorMsg")!="")
			{
				message=$(s).attr("errorMsg");
			}
			if($(s).next('.tip').length>0){
				//$(s).next('.tip').html(message);
			}else{
				
				if(s.tagName=="SELECT"){
					//$(s).parent().css("border","1px solid red");
					$(s).next().css("border-color","red");
					$(s).next().next().css("border-color","red");
					var po = $(s).next().position();
					$(s).next().after("<div class='tip' style='position:absolute;padding-left:5px; top:"+
							parseInt(po.top+10)+"px;left:"+parseInt(po.left+250)+"px;' >"+
							"<img src='"+$publicSystemPath$+"/common/images/warning_s.gif' height='15px' width='15px' title='"+message+"' style='cursor: pointer;'/>"+
							"</div>");
				}else{
					$(s).css("border-color","red");
					var po = $(s).position();
					var pp=$(s).parent();
					//$(pp).css("position","relative").css("display","block");
					$(s).after("<div class='tip' style='position:absolute;padding-left:5px; top:"+
							parseInt(po.top+10)+"px;left:"+parseInt($(s).width()+po.left+5)+"px;' >"+
							"<img src='"+$publicSystemPath$+"/common/images/warning_s.gif' height='15px' title='"+message+"' width='15px' style='cursor: pointer;'/>"+
							"</div>");
					
				}
			}
			return false;
		}else{
			$(s).parent().children('.tip').remove();
			if(s.tagName=="SELECT"){
				$(s).next().css("border-color","#D8D4C4");
				$(s).next().next().css("border-color","#D8D4C4");
			}else{
				$(s).css("border-color","#D8D4C4");
			}
			return true;
		}
	},
	toValidNullCheck:function(formId,checkId,showId,tdId,message)
	{//根据传入的id设置校验值及提示id，针对多选框提供此方法
		var vvalue=$("#"+formId+" #"+checkId).val();
		if(vvalue==undefined
			||vvalue=="undefined"
			||vvalue=="")
		{
			if($("#"+formId+" #"+tdId).children('.tip').length>0){

			}else{
				$("#"+formId+" #"+showId).css("border","1px solid red");
				var po = $("#"+formId+" #"+showId).position();
				var pp=$("#"+formId+" #"+showId).parent();
				//$(pp).css("position","relative").css("display","block");
				$("#"+formId+" #"+showId).after("<div class='tip' style='position:absolute;padding-left:5px; top:"+
						parseInt(po.top+10)+"px;left:"+parseInt($("#"+formId+" #"+showId).width()+po.left+5)+"px;' >"+
						"<img src='"+$publicSystemPath$+"/common/images/warning_s.gif' height='15px' title='"+message+"' width='15px' style='cursor: pointer;'/>"+
						"</div>");
			}
			return false;
		}else{
			$("#"+formId+" #"+tdId).children('.tip').remove();
			$("#"+formId+" #"+showId).css("border","0px solid red");
			return true;
		}
	},
	toShowNullCheck:function(formId,showId,message)
	{//设置显示提示不可为空
		$("#"+formId+" #"+showId).css("border","1px solid red");
		var po = $("#"+formId+" #"+showId).position();
		var pp=$("#"+formId+" #"+showId).parent();
		//$(pp).css("position","relative").css("display","block");
		$("#"+formId+" #"+showId).after("<div class='tip' style='position:absolute;padding-left:5px; top:"+
				parseInt(po.top+10)+"px;left:"+parseInt($("#"+formId+" #"+showId).width()+po.left+5)+"px;' >"+
				"<img src='"+$publicSystemPath$+"/common/images/warning_s.gif' height='15px' title='"+message+"' width='15px' style='cursor: pointer;'/>"+
				"</div>");
	},
	toHiddenNullCheck:function(formId,showId,tdId)
	{//隐藏空值提示框
		$("#"+formId+" #"+tdId).children('.tip').remove();
		$("#"+formId+" #"+showId).css("border","0px solid red");
	},
	toShowCheckIdIsExist:function(formId,showId,tdId,message,flag)
	{
		if(flag){
			App.notyError(message);
			$("#"+formId+" #"+showId).css("border","1px solid red");
			var po = $("#"+formId+" #"+showId).position();
			var pp=$("#"+formId+" #"+showId).parent();
			//$(pp).css("position","relative").css("display","block");
			$("#"+formId+" #"+showId).after("<div class='tipex' style='position:absolute;padding-left:5px; top:"+
					parseInt(po.top+10)+"px;left:"+parseInt($("#"+formId+" #"+showId).width()+po.left+5)+"px;' >"+
					"<img src='"+$publicSystemPath$+"/common/images/error_s.gif' height='15px' title='"+message+"' width='15px' style='cursor: pointer;'/>"+
					"</div>");
			$("#userForm #userId").css("color","red");
		}else{
			$("#"+formId+" #"+tdId).children('.tipex').remove();
			$("#"+formId+" #"+showId).css("border","1px solid #D8D4C4 ");
			$("#userForm #userId").css("color","");
		}
	},
	//formId : 表单id   url：ajax查找机构url   inpId:存放最终机构号    inpNm：存放最终机构名
	selectDeptCommon:function(formId,url,inpId,inpNm){
		//级联机构下拉框
		$( "#"+formId+" #deptType" ).change(function(){
			//总/分行下拉框监听
			var selValue = this.value;
	    	App.setFstDeptId(formId,selValue,url,inpId,inpNm,null);
	    });
	    
	    $( "#"+formId+" #fstdeptId" ).change(function(){
	    	//一级下拉框监听
	    	var selValue = this.value;
	    	
	    	App.setSedDeptId(formId,selValue,url,inpId,inpNm,null);
	    });
	    
	    $( "#"+formId+" #seddeptId" ).change(function(){
	    	//二级下拉框监听
	    	var selValue = this.value;
			App.setThdDeptId(formId, selValue,inpId,inpNm);
	    });
	},
	cleanSelectDeptCommon:function(formId,num,inpId,inpNm){
		//清空所有下拉框
		if(num=='1'){
			$( "#"+formId+" #fstdeptIdDiv" ).css("display","none");
			$( "#"+formId+" #fstdeptId" ).empty();
			$( "#"+formId+" #fstdeptIdDiv input").val("");
			$( "#"+formId+" #fstdeptId" ).val("");
			
		}
    	$( "#"+formId+" #seddeptIdDiv" ).css("display","none");
		$( "#"+formId+" #seddeptId" ).empty();
		$( "#"+formId+" #seddeptIdDiv input").val("");
		$( "#"+formId+" #seddeptId" ).val("");
		
		$( "#"+formId+" #"+inpId ).val("");
		$( "#"+formId+" #"+inpNm ).val("");
	},
	setFstDeptId:function(formId,selValue,url,inpId,inpNm,fstDeptId){
		//设置一级下拉框
		App.cleanSelectDeptCommon(formId,'1');
    	var data = {};
    	data['deptType'] =  selValue;
    	data['deptLevel'] =  '1';
		App.ajaxSubmit(url,{data : data}, function(data) {
			var lst=data.fstDeptList;
			var fstDept='<option value="">请选择</option>';
			for(var i=0;i<lst.length;i++){
				if(fstDeptId==lst[i].deptId){
					fstDept=fstDept+'<option value="'+lst[i].deptId+'" selected="selected">'+lst[i].deptName+'</option>';
					$( "#"+formId+" #fstdeptIdDiv input").val(lst[i].deptName);
				}else {
					fstDept=fstDept+'<option value="'+lst[i].deptId+'">'+lst[i].deptName+'</option>';
				}
			}
    		$( "#"+formId+" #fstdeptId" ).append(fstDept);
    		$( "#"+formId+" #fstdeptIdDiv" ).css("display","inline");
		});
	},
	setSedDeptId:function(formId,selValue,url,inpId,inpNm,sedDeptId){
		//设置二级下拉框
		App.cleanSelectDeptCommon(formId,'2');
		$( "#"+formId+" #"+inpId ).val(selValue);
		var deptNm=$("#"+formId+" #deptType").find("option:selected").text()+"-"+$("#"+formId+" #fstdeptId").find("option:selected").text();
		$( "#"+formId+" #"+inpNm ).val(deptNm);
    	var data = {};
    	data['upDeptId'] =  selValue;
    	data['deptLevel'] =  '2';
		App.ajaxSubmit(url,{data : data}, function(data) {
			var lst=data.fstDeptList;
			if(lst.length>0){
				var fstDept='<option value="">请选择</option>';
				$( "#"+formId+" #seddeptId" ).empty();
				for(var i=0;i<lst.length;i++){
					if(sedDeptId==lst[i].deptId){
						fstDept=fstDept+'<option value="'+lst[i].deptId+'" selected="selected">'+lst[i].deptName+'</option>';
						$( "#"+formId+" #seddeptIdDiv input").val(lst[i].deptName);
					}else {
						fstDept=fstDept+'<option value="'+lst[i].deptId+'">'+lst[i].deptName+'</option>';
					}
				}
	    		$( "#"+formId+" #seddeptId" ).append(fstDept);
	    		$( "#"+formId+" #seddeptIdDiv" ).css("display","inline");
			}
		});
	},
	setThdDeptId:function(formId,selValue,inpId,inpNm){
		$( "#"+formId+" #"+inpId ).val(selValue);
		var deptNm=$("#"+formId+" #deptType").find("option:selected").text()+"-"+$("#"+formId+" #seddeptId").find("option:selected").text();
		$( "#"+formId+" #"+inpNm ).val(deptNm);
	},
	//表单提交锁屏并显示等待效果
	submitShowProgress : function(){
		var panel = $(document.body);
		var mask = $("<div id='ajaxLock'/>").css({
			position : "absolute",
			zIndex : 9999,
			background : "#fff",
			width:$(document).width()+"px",
			height:$(document).height()+"px",
			top : "0",
			left : "0",
			opacity: "0.7"
		});
		$(document.body).prepend(mask);
	    var __ajaxProgressWin = "<img id='load_time_gif' class='ajaxLoading' src='"+$publicSystemPath$+"/common/images/time_gif.gif'/>";
	    $("#ajaxLock").html(__ajaxProgressWin);
	    var newTop = 300+getScrollTop();//等待效果固定于浏览器可视区域
	    $(".ajaxLoading").css("top",newTop+"px");
	},
	//解锁屏幕
	submitFinish : function(){
		$("#ajaxLock").remove();
	}
};

var Tool = {
	toggleCheck : function(item, name) {
		var checked = item.checked;
		$('input[type=checkbox][name=' + name + ']').each(function(){
			this.checked = checked;
		});
	},
	selectResultsIntoTextarea:function(pareId,selId,testareaName){
		var selectResult = $("#"+pareId+" #"+selId);
		if(selectResult.val() != '')
		{
			$("#"+pareId+" textarea[name="+testareaName+"]").val(selectResult.find("option:selected").text());		
		}
	},
	windowOpen:function(url,winWidth,winHeight,name){
		var WinOP;
		var winLeft = Math.ceil((window.screen.width - winWidth)/2);
		var winTop = Math.ceil((window.screen.height - winHeight)/2);
		var features =  'width=' + winWidth + 'px,' +
						'height=' + winHeight + 'px,' +
						'left=' + winLeft + 'px,' +
						'top=' + winTop + 'px,' + 
						'fullscreen=0, toolbar=0, location=0, directories=0, status=0, menubar=0, scrollbars=1, resizable=0';
		if(WinOP != null)//确保只打开一个窗口 
		{
			WinOP.close();
			WinOP = null;
		}
		WinOP = window.open(url,name,features);
		WinOP.focus();
	}
};
var CheckMoney={
		format : function(id){
			if(!CheckMoney.checkData(id))return false;
			$("#"+id).val(CheckMoney.buildMoneyString($("#"+id).val())) ;
			return true;
		},
		deleteDot : function(value){
			while(value.indexOf(",") != -1)
			{
				value = value.replace(",", "");
			}
			return value;
		},
		deleteDotById : function(id){
			var value=$("#"+id).val();
			while(value.indexOf(",") != -1)
			{
				value = value.replace(",", "");
			}
			$("#"+id).val(value);
		},
		checkData : function(id){
			var value = $("#"+id).val();
			
			if(value<0){
				value = -1*value;
			}
			for(var i=0; i<value.length; i++)
			{
				var ascValue = value.charCodeAt(i);
				if((ascValue<48 || ascValue>57) 
						&& ascValue!=46 
						&& ascValue!=44 )
				{
					App.notyError("对不起，你输入的金额有误，可能含有非法字符！");
					$("#"+id).val("")
					$("#"+id).focus();
					return false;
				}
			}
			return true;
		},
		deleteHeadZero : function(value){
			var index = 0;
			for(var i=0; i<value.length; i++)
			{
				if((value.charAt(i) != "0" && value.charAt(i) != ",")
						|| ((value.indexOf(".") == -1 && (value.length-i) == 1) 
								|| (value.indexOf(".") != -1 && (value.length-i) == 3)))
				{
					index = i;
					break;
				}
			}
			return value.substring(index, value.length);
		},
		buildMoneyString : function(value){
			var moneyString = "";
			var tempValue = CheckMoney.deleteHeadZero(value);
			if(tempValue.indexOf(",") != -1)
			{
				moneyString =  tempValue;
			}else
			{
				var temp = tempValue;
				
				//build the string behind decimal
				if(value.indexOf(".") != -1)
				{
					if(value.indexOf(".")< value.length-3){
						moneyString += temp.substring(temp.length-3, temp.length);
						temp = temp.substring(0, temp.length-3);
					}
				}else
				{
					moneyString += ".00";
				}
				
				//build the string in front of decimal
				/*while(temp.length > 3)
				{
					moneyString = "," + temp.substring(temp.length-3, temp.length)
									  + moneyString;
					temp = temp.substring(0, temp.length-3)
				}*/
				//handle empty String 
				if(temp == "")
				{
					moneyString = "";
				}else
				{
					moneyString = temp + moneyString;
				}
				
			}
			return moneyString;
		}
};

var Utils = {
		//初始化级联选择
		//selectId 最终保存到数据库的字段名称
		//className 同组别样式名称
		initDeptCascadeSelect : function(selectId,className){
			//封装搜索条件
			var _getParams = function(selectVal,tempVal){
				var data = {};
				var tempArr = tempVal.split("_");
				var upDeptId = selectVal==tempVal?selectVal:tempArr[0];
				data['upDeptId'] = upDeptId;
				if(tempArr.length>1){
					data['deptType'] = tempArr[1];
				}
				return data;
			}
			
			//根据父级选中加载子级并子级选中
			var _cascadeSelected = function(prevObj,value){
				var data =  _getParams($("#"+selectId).val(),prevObj.val());
				var node = prevObj.parent().next();
				var selectObj = node.children().eq(0);
				var inputObj = node.children().eq(1);
				node.css("display","inline");
				selectObj.find("option").remove();
				inputObj.val("");
				App.ajaxSubmit("sysmanagement/dept/getCommonDeptList.do?VISIT_FUNC_ID=010408",{data:data,async:false},function(data){
					var rst = data.deptList;
					for(var index in rst){
						var deptTo = rst[index];
						var html = "<option value='"+deptTo['deptId']+"'>"+deptTo['deptName']+"</option>";
						selectObj.append(html);
					}
					if(value){
						var selectOpinion = selectObj.find("option[value='"+value+"']");
						selectOpinion.attr("selected","selected");
						var text = selectOpinion.html();//父级选中
						inputObj.val(text);
						selectObj.val(value);
						$("#"+selectId).val(value);
					}
				});
			};
			
			//清空所有子级搜索框
			var _clearSelect = function(obj){
				obj.parent().nextAll().each(function(){
					var node = $(this);
					var selectObj = node.children().eq(0);
					var inputObj = node.children().eq(1);
					node.css("display","none");
					selectObj.find("option").remove();
					inputObj.val("");
				});
			}
			
			//初始化选中
			if($("#"+selectId).val()){
				var data = {};
				data['deptId'] = $("#"+selectId).val();
				App.ajaxSubmit("sysmanagement/dept/getCommonParentStr.do?VISIT_FUNC_ID=010409", {data:data,async:false}, function(data){
					var deptArr = data.deptStr.split(",");
					var tempDeptStr = data.tempDeptStr;
					deptArr.reverse();
					for(var i=0;i<deptArr.length;i++){
						var val = deptArr[i];
						$("#"+selectId).val(val);
						val = i==0?tempDeptStr:val;
						var selectOpinion = $("."+className+":eq("+i+")").find("option[value='"+val+"']");
						selectOpinion.attr("selected","selected");
						$("."+className+":eq("+i+")").val(val);
						var text = selectOpinion.html();//父级选中
						$("."+className+":eq("+i+")~input").val(text);
						_cascadeSelected($("."+className+":eq("+i+")"),deptArr[i+1]);
					}
				});
			}
			
			//下拉框绑定事件
			$("."+className).change(function(){
				var obj = $(this);
				obj.find("option[value='"+$(this).val()+"']").attr("selected","selected");
				_clearSelect(obj);
				var tempArr = obj.val().split("_");
				$("#"+selectId).val(tempArr[0]);
				var data = _getParams(tempArr[0],obj.val());
				var node = obj.parent().next();
				var selectObj = node.children().eq(0);
				var inputObj = node.children().eq(1);
				node.css("display","inline");
				App.ajaxSubmit("sysmanagement/dept/getCommonDeptList.do?VISIT_FUNC_ID=010408", {data:data}, function(data){
					var rst = data.deptList;
					for(var index in rst){
						var deptTo = rst[index];
						var html = "<option value='"+deptTo['deptId']+"'>"+deptTo['deptName']+"</option>";
						selectObj.append(html);
					}
				});
			});
		},
		
		//提交表单时候如果1级部门没选，则清空部门ID
		//selectId 最终保存到数据库的字段名称
		//className 同组别样式名称
		checkDeptCascadeSelect : function(selectId,className){
			var secondDeptId = $("."+className+":eq(1)").find("option[selected='selected']");
			var firstObj = $("."+className+":eq(0)");
			if(!secondDeptId.length){
				firstObj.val("");
				firstObj.next().val("");
				$("#"+selectId).val("");
				$("."+className+":eq(1)").parent().css("display","none");
			}
		},
		
		//需求登记基本信息弹出框
		showBasePopupWindow:function(url){
			var width  = 800;
			var height = 600;
			var top  = (screen.height - height)/2;
			var left = (screen.width -width)/2;
			var features = "height="+height+",width="+width+",left="+left+",top=50,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no,status=no"; 	
			window.open(url, "popup", features);
		}
};

//保留两位小数
changeTwoDecimal=function (floatvar)
{
	var f_x = parseFloat(floatvar);
	if (isNaN(f_x))
	{
		alert('function:changeTwoDecimal->parameter error');
		return false;
	}
	var f_x = Math.round(f_x*100)/100;
	var s_x = f_x.toString();
	var pos_decimal = s_x.indexOf('.');
	if (pos_decimal < 0)
	{
		pos_decimal = s_x.length;
		s_x += '.';
	}
	while (s_x.length <= pos_decimal + 2)
	{
		s_x += '0';
	}
	return s_x;
} ;



