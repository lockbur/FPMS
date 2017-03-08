<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>附件上传下载公共页面</title>
<script  type="text/javascript">
	/**
	文件上传下载相关js
	**/
	var orgId = '101';
	var fileListSelect = "";
	var busiId = ""; 
	var attachmentType = "";
	var lastVchSelectedIndex = 0;//最后一次用户点击批次下附件列表的selectedindex
	window.onload=function(){
		initPara();
	}
	function initPara(){
			App.ajaxSubmit("attachment/getFtpInfo.do?<%=WebConsts.FUNC_ID_KEY%>=020603",
					{
						data:{}
					},
					function(data){
/*						$("#deliverX").attr("HostAddr", data.ftpInfo.ftpIp);
						$("#deliverX").attr("HostPort", data.ftpInfo.ftpPort);
						$("#deliverX").attr("SysID", "PRM");//系统ID
						$("#deliverX").attr("BsnID", "RSQ");//业务品种
						$("#deliverX").attr("HostMode", "ftp:pasv");
						$("#deliverX").attr("HostUser", data.ftpInfo.uploadUser);
						$("#deliverX").attr("HostPass", data.ftpInfo.uploadPsd);
						alert("1" + $("#deliverX").attr("SysID"));*/
						
						document.all("deliverX").HostAddr = data.ftpInfo.ftpIp;//22.145.27.107
						document.all("deliverX").HostPort = data.ftpInfo.ftpPort;
						
						document.all("deliverX").SysID = 'PRM';//'R01';//系统ID
						document.all("deliverX").BsnID = 'RSQ';//'EXC';//业务品种
						document.all("deliverX").HostMode = 'ftp:pasv';
						document.all("deliverX").HostUser = data.ftpInfo.uploadUser;
						document.all("deliverX").HostPass = data.ftpInfo.uploadPsd; 
						if (orgId.length > 3)
							orgId = orgId.substring(0,3);
					}
					);
	}
	
	
	//附件上传
	function upload(fileListId, busi, type)
  	{  
		fileListSelect = fileListId;
		busiId = busi;
		attachmentType = type;
		//参数按顺序：动作代码、机构号、终端号、业务申请编号、文件过滤字符串
	   document.all("deliverX").UploadDoc('upload', orgId, 'A', 'REQU20131106', 'ALL FILE|*.*|Office文件(*.doc,*.xls,*.ppt)|*.doc;*.xls;*.ppt|PDF文件|*.pdf|ISO文件|*.iso', 1);
	   //$("#deliverX").UploadDoc('upload', orgId, 'A', 'PROB20131106', 'ALL FILE|*.*|Office文件(*.doc,*.xls,*.ppt)|*.doc;*.xls;*.ppt|PDF文件|*.pdf|ISO文件|*.iso', 1);
	}
	//附件上传
	function clickAttachmentOption(obj)
  	{  
		clickAttachment(obj.options[obj.selectedIndex].value,obj.options[obj.selectedIndex].text)
	}
	function downloadOption(obj)
	{
		download(obj.options[obj.selectedIndex].value,obj.options[obj.selectedIndex].text);
	}
	//打开
	function openOption(obj)
    {
		openDoc(obj.options[obj.selectedIndex].value,obj.options[obj.selectedIndex].text);
   	  
    }
	
	//附件上传
	function clickAttachment(fileIndex,fileName)
  	{  
		if(fileIndex!=''){
			$( "<div >请选择对附件【"+fileName+"】的操作！</div>" ).dialog({
				resizable: false,
				modal: true,
				width:350,
				dialogClass: 'dClass',
				buttons: {
					"打开": function() {
						$( this ).dialog( "close" );
						openDoc(fileIndex,fileName);
					},
					"下载": function() {
						$( this ).dialog( "close" );
						download(fileIndex,fileName);
					},
					"取消": function() {
						$( this ).dialog( "close" );
					}
				}
			});
		}else{
			App.notyWarning('无效选择！');
		}
	}
	//附件下载
	function download(fileIndex,fileName)
	{
		if(fileIndex!=''){
			document.all("deliverX").DownloadDoc('download', fileIndex, fileName, 1);	
		}else{
			App.notyWarning('无效选择！');
		}
	}

	//打开
	function openDoc(fileIndex,fileName)
    {
		if(fileIndex!=''){
			document.all("deliverX").openDoc('_blank', fileIndex, fileName);
		}else{
			App.notyWarning('无效选择！');
		}
    }
	
	function setDocIndex(fileListId)
	{
		 var docList = document.all(fileListId);
			var curSelectedIndex = docList.selectedIndex;
			if(docList.selectedIndex  == 0 || docList.selectedIndex == -1) 
			{
				docList.selectedIndex = lastVchSelectedIndex;
				return;
			}
			else 
			    lastVchSelectedIndex = docList.selectedIndex;
	}
	
	//删除附件
  function deleteUploadFile(fileListId)
  {  	
	  var docList = document.all(fileListId);
	  if (docList.length == 1)
	  {
		  App.notyWarning('对不起，没有可要删除附件！');
			  return ;
	  }
	  
      if (docList.selectedIndex ==-1)
       {
    	  App.notyWarning('请选择要删除附件！');
			return ;
	  }
	  var id = docList[docList.selectedIndex].value;
	  
	  if(id == "")
	  {
		  App.notyWarning('请选择要删除附件！');
			return ;
	  }
	  if (confirm('您这样做将会删除所选的附件,确定删除吗？'))
	  {
		  var selectedIndex = docList.selectedIndex;
		  var docLength = docList.length;
		  var fileInfo = getFileIndex(fileListId);
		  var fileLength = getFileIndex(fileListId).split(',,')[0];
		  var fileIndex = getFileIndex(fileListId).split(',,')[1];
		  App.ajaxSubmit("attachment/delFileIndex.do?<%=WebConsts.FUNC_ID_KEY%>=020602",
					{
						data: {"attachmentId" : fileIndex}
					},
					function(data){
						if(data.count>0)
						{
						   //删除列表中的附件显示
							 delFileIndex(fileListId);
					         for(var i = selectedIndex;i < docList.length;i++)
							 {
							    var temp2 = docList[i].text.split(' ')[0];
							    if (i < 10 && temp2 < 10)
							       docList[i].text = i + docList[i].text.substring(1,docList[i].text.length);
							    else if (i < 10 && temp2 >= 10)
							       docList[i].text = i + docList[i].text.substring(2,docList[i].text.length);
							    else if (i >= 10 && temp2 > 10)
							       docList[i].text = i + docList[i].text.substring(2,docList[i].text.length);
								
							 }
							 if (docList.length != 0)
							 {
							     document.all(fileListId).selectedIndex = selectedIndex;
						         lastVchSelectedIndex = selectedIndex;
							 }
							 
							 App.notySuccess('删除成功');
						}else{
							App.notyWarning("删除了0条数据，暂只支持单条数据删除！");
						}
					}
					);
		  
	  }
	     
	}
//获取多选的附件fileIndex以及其对应的位置,同时删除对应的选中的列表数据
	function getFileIndex(fileListId)
	{
		delIndexArray = new Array();
		var docList = document.all(fileListId);
		var j = 0;//记录选中的数量
		var ret = "";//记录选中的fileIndex值，以,来进行分割
		var lastIndex = docList.length - 1;
		var selectedIndex = docList.selectedIndex;
		for(var i = 0;i < docList.length;i++)
		{
		if (docList[i].selected == true)
		{
			if (j == 0)
			   ret = docList[i].value;
			else
			   ret = ret + ',' + docList[i].value;
			j++;
			delIndexArray[delIndexArray.length]=i;
			//docList.options[i] = null;
		}
		}
		return (j + ',,' + ret);
	}

	function delFileIndex(fileListId)
	{
		var docList = document.all(fileListId);
		var lastIndex = docList.length - 1;
		var selectedIndex = docList.selectedIndex;
		for(var i = delIndexArray.length-1;i >=0 ;i--)
		{
		 docList.options[delIndexArray[i]] = null;
		}
		delIndexArray = null;
	}
</script>
<SCRIPT for="deliverX" event="OnUpload(actionID, docIndex, docPath, docFile, docParam)">
	/*
	   每次插件上传成功一个附件后，就会触发一次此事件。参数含义如下：
	   actionID -- 动作ID，程序员可以对每一类上传的调用定义一个Action，这里的actionID就是之前调用UnloadDoc时传入的那个actionID
	   docIndex -- 由插件自动编的上传附件的索引号
	   docPath  -- 上传文件在本地的路径
	   docFile  -- 上传文件在本地的文件名，建议在数据库中保存此文件名，这样当用户下载文件时可以使用此文件名保存
	   docParam -- 保留参数     
	*/
	
	//将文件的索引号及路径显示在TextArea中
	//var id = document.getElementById("fileId").value;
	
	//alert('docIndex=' + docIndex + ' docPath=' + docPath +  ' docFile=' + docFile);
	var docList = document.all(fileListSelect);
	App.ajaxSubmit("attachment/addFileIndex.do?<%=WebConsts.FUNC_ID_KEY%>=020601",
			{
				data:{"busiId": busiId, 
					  "attachmentId": docIndex, 
					  "origalFilename": docFile,
					  "attachmentType": attachmentType}
			},
			function(data){
				if(data.count > 0){
					
					
					App.notyAlert('上传成功!');
				}else{
					App.notyError('保存失败!');
				}
				
			}
			);

	newDocOption = new Option(docList.length + ' '+ docFile, docIndex, false, false);
	docList.options[docList.length] = newDocOption;
	docList.options.selectedIndex = docList.length - 1;
	
</script>
</head>
<OBJECT id="deliverX"  name="deliverX" classid="clsid:D46EBA71-9EA5-4E1D-A822-B26D508C3969" width="550" height="60" hspace=0 vspace=0 style="display: none;"></OBJECT>
</html>