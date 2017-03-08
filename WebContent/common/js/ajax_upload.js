/**
 * 异步上传工具类
 * @author wangzf
 * @data 2014-05-21
 */
var AjaxUploadEE = (function(){
	var AjaxUpload = function(config){
		this._init(config);
	};
	AjaxUpload.prototype = {
		resultType : "text",
		success : null , 
		error : null, 
		// 初始化
		_init : function(config){
			this._form = $(config.form || "");
			this.resultType = config.resultType || "text";
			this.success = config.success || null;
			this.error = config.error || null;
		},
		//  执行上传
		upload : function() {
			var time = new Date().getTime();
			this._iFrame = $(
					"<iframe name='ajaxupload_" + time
							+ "' style='display:none'/>").appendTo(document.body);
			this._form.attr("target", this._iFrame.attr("name"));
			var base = this;
			this._iFrame.load(function() {
				base.onload();
			});
			this._form[0].submit();
		},
		// 完成回调方法
		onload : function() {
			var data = null
			 	,text = null
			 	,dom = null;
			var iFrame = this._iFrame[0];
			if (iFrame.contentWindow) {
				text = iFrame.contentWindow.document.body ? $(
						iFrame.contentWindow.document.body).text() : null;
			} else if (iFrame.contentDocument) {
				text = iFrame.contentDocument.document.body ? $(
						iFrame.contentDocument.document.body).text() : null;
			}
			if (this.resultType == "json"){
				try{
					eval("data=" + text);
				}catch(e){
					var msg = "json data:"+data+" turn to json error:"+e;
					if(this.error && typeof this.success == "function")
						this.error(msg);
					return;
				}
			}else if (this.resultType == "text"){
				data = text;
			}
			if(this.success && typeof this.success == "function")
				this.success(data);
		}
	};
	return AjaxUpload;
})();

var UploadUtils = (function(){
	var UploadUtils = function(){};
	//添加遮蒙
	UploadUtils.maskLock = function(id){
		var panel = $(document.body);
		var mask = $("<div id='" + id + "'/>").css({
			position : "fixed",
			zIndex : 9999,
			background : "#000",
			top : "0",
			left : "0"
		}).fadeTo(0, 0.2);
		panel.outerWidth()<window.screen.availWidth?mask.width(window.screen.availWidth):mask.width(panel.outerWidth());
		panel.outerHeight()<window.screen.availHeight?mask.height(window.screen.availHeight):mask.height(panel.outerHeight());
		$(document.body).prepend(mask);
		return mask;
	};
	
	//删除遮蒙
	UploadUtils.maskUnLock = function(id){
		$("#"+id).remove();
	};
	
	//上传附件
	UploadUtils.uploadAttachment = function(fileListObj,inputPostName,callback) {
		//获取上下文跟路径
		var contextPath = '/PRMS';
		if(inputPostName.contextPath){
			contextPath = inputPostName.contextPath;
		}
		
		if(inputPostName.uploadMode=='single'&&$(fileListObj).find("li").length>0){
			App.notyWarning("只能上传一个附件！");
			return false;
		}
		var mask = UploadUtils.maskLock("bodyMask");
		window.__uploadAttachmentWin = $("<div class='winModal' style='width: 450px; height: 100px; margin-left: -225px; margin-top: -110px;background: none repeat scroll 0 0 #FFFFFF;border: 1px solid #BBBBBB;font-size: 12px; left: 50%;position: fixed;top: 50%;z-index: 1000010;' >"
				+ "<div class='winModalTitle' style='background: none repeat scroll 0 0 #F0F0EE;border-bottom: 1px solid #BBBBBB; padding: 5px;'>"
				//+ "<span title='关闭 (Esc)' class='winModalClose' style='background:url(\""+$("#ajaxUploadPath").val()+"/common/images/close.jpg\") repeat scroll 0 0 transparent;cursor: pointer;float: right; height: 16px;width: 16px;'></span>上传文件"
				+ "<b>上传文件</b><span style='float: right;'><a href='#'><img class='winModalClose' src=\""+contextPath+"/common/images/close.jpg\" width='16' height='16' title='关闭 (Esc)'/></a></span>"
				+ "</div>"
				
				+ "<div class='winModalContent' style='height: 191px;'>"
				+ "<form style='vertical-align:middle;' enctype='multipart/form-data' method='post' action='"
				+ contextPath+"/fileUtils/uploadFile.do"
				+ "' name='iframeUploadForm' id='iframeUpload'>"
				+ "<div style='vertical-align:middle;text-align:center;margin:10px 0 0 0;'>"
				+ "<label style='font-size:12px;'>请选择：</label>&nbsp;"
				+ "<input type='file' id='uploadAttachment' value='' style='width:300px;' size='13' name='uploadAttachment'>&nbsp;"
				+ "</div>"
				
				+ "<div style='vertical-align:middle;text-align:center;margin:10px 0 0 0;'>"
				+ "<input type='submit' value='上传' class='uploadBt'>"
				+ "</div>"
				
				+ "<div style='vertical-align:middle;text-align:center;margin:10px 0 0 0;'>"
				+ "<label style='font-size:12px;display:none;' class='uploadingTxt' >正在上传中，请稍候...</label>&nbsp;"
				+ "</div>" + "</form>" + "</div>" + "</div>");
		var uploadForm = window.__uploadAttachmentWin.find("form");
		$(document.body).prepend(window.__uploadAttachmentWin);
		window.__uploadAttachmentWin.find(".winModalClose").click(function() {
			window.__uploadAttachmentWin.remove();
			UploadUtils.maskUnLock("bodyMask");
		});
		uploadForm.submit(function() {
			var attachment = $("#uploadAttachment").val();
			if(!attachment){
				alert("请选择上传的附件!");
				return false;
			};
			if(attachment.search("'")!=-1){
				App.notyError("文件名不能包含'号，请修改");
				return false;
			}
			if(inputPostName.passSuffix || inputPostName.unpassSuffix){
				var fileType = attachment.substr(attachment.lastIndexOf(".")+1);
				if(inputPostName.passSuffix){
					if(inputPostName.passSuffix.toUpperCase().indexOf(fileType.toUpperCase())==-1){
						alert("请上传格式为"+inputPostName.passSuffix.split(",").join("或")+"的文件");
						return false;
					}
				}
				if(inputPostName.unpassSuffix){
					if(inputPostName.unpassSuffix.toUpperCase().indexOf(fileType.toUpperCase())!=-1){
						alert("禁止上传格式为"+inputPostName.unpassSuffix.split(",").join("或")+"的文件");
						return false;
					}
				}
			}
			try {
				new AjaxUploadEE(
						{
							form : this,
							resultType : "text",
							success : function(data) {
								var dataArry = data.split(" | ");
								if (dataArry[0] == 0) {
									var attachUrl = dataArry[2] || "";
									var fileName = dataArry[1] || "";
									var li = "<li><input type='hidden' name='"+inputPostName.filePath+"' value='"+attachUrl+"'/>"
							         + "<input type='hidden' name='"+inputPostName.fileName+"' value='"+fileName+"'/>"
							         + "<a href=\"javascript:UploadUtils.downloadFile('"+attachUrl+"','"+fileName+"','"+contextPath+"')\">"+fileName+"</a>&nbsp;"
							         + "<a href='javascript:;' onclick='UploadUtils.deleteAttachment(this);'><img style='vertical-align: middle;width: 18px; height: 18px;' src='"+contextPath+"/common/images/close.jpg' title='删除(Delete)'/></a></li>";
									
									fileListObj = $(fileListObj);
									fileListObj.find("ul").append(li);
									if(callback)										
										callback(data);	
									UploadUtils.maskUnLock("bodyMask");
									if (window.__uploadAttachmentWin)
										window.__uploadAttachmentWin.remove();
								} else {
									alert("上传失败!"+dataArry[1]);
									var uploadForm = window.__uploadAttachmentWin
											.find("form");
									uploadForm.find(".uploadBt").removeAttr(
											"disabled");
									uploadForm.find(".uploadingTxt").hide();
								}
								$(document.body).find("iframe[name^='ajaxupload_']").remove();
							}
						}).upload();
			} catch (e) {
				return false;
			}
			uploadForm.find(".uploadingTxt").show();
			uploadForm.find(".uploadBt").attr("disabled", "disabled");
			return false;
		});
	};
	
	// 删除附件
	UploadUtils.deleteAttachment = function (obj){
		if(window.confirm("确认删除此附件吗?")){
			$(obj).parent().remove();
		}
	};
	
	// 点击附件打开弹出框
	UploadUtils.clickAttment = function (filePath,fileName,contextPath){
		$( "<div >请选择对附件【"+fileName+"】的操作！</div>" ).dialog({
			resizable: false,
			modal: true,
			width:350,
			dialogClass: 'dClass',
			buttons: {
//				"打开": function() {
//					$( this ).dialog( "close" );
//					UploadUtils.downloadFile(filePath,fileName,contextPath,"inlineOpen");
//				},
				"下载": function() {
					$( this ).dialog( "close" );
					UploadUtils.downloadFile(filePath,fileName,contextPath);
				},
				"取消": function() {
					$( this ).remove();
				}
			}
		});
	};
	
	// 附件下载
	UploadUtils.downloadFile = function (filePath,fileName,contextPath,openModel){
		var time = new Date().getTime();
		var id = "attachmentForm_"+time;
		var form = "<form  target='uploadFrame' method='post' id='"+id+"' name='"+id+"' action='"+contextPath+"/fileUtils/fileDownload.do'>"
		 	     + "<input type='hidden' name='filePath' value='"+filePath+"'/>"
	             + "<input type='hidden' name='fileName' value='"+fileName+"'/>";
        if(openModel){
    	  form += "<input type='hidden' name='openModel' value='"+openModel+"'/>";
        }
		form += "</form>";  
	  	$("body").append(form);
	  	$("#"+id).submit();
	  	$("#"+id).remove();
	};
	
	//附件列表初始化
	UploadUtils.initForm = function (obj,config){
		var li = "<li><input type='hidden' name='"+config.filePath+"' value='"+config.filePathValue+"&&"+config.fileId+"'/>"
         + "<input type='hidden' name='"+config.fileName+"' value='"+config.fileNameValue+"'/>"
         + "<a href=\"javascript:UploadUtils.downloadFile('"+config.filePathValue+"','"+config.fileNameValue+"','"+config.contextPath+"')\">"+config.fileNameValue+"</a>&nbsp;"
         + "<a href='javascript:;' onclick='UploadUtils.deleteAttachment(this);'><img style='vertical-align: middle;width: 18px; height: 18px;' src='"+config.contextPath+"/common/images/close.jpg' title='删除(Delete)'/></a></li>";
		obj = $(obj);
		obj.find("ul").append(li);
	}
	
	return UploadUtils;
})();