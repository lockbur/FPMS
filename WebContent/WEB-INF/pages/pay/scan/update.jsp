<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="dialog">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>合同扫描</title>
<base target="_self">
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/check.js"></script>
<!-- ***************************扫描控件**************************** --> 
<script language="javascript">
       var obj ;
       function Init()
       {
           obj = document.getElementById("ContentMain");
           //alert(obj);
        }

       function setsys()
       {
           var str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
           str += "<Info>";
			//actionFlag控件状态
           str += "<actionFlag>03</actionFlag>";			//uploadIp上传消息服务器IP、upload_port上传消息服务器端口、download_ip下载消息服务器IP、download_port下载消息服务器端口（uploadIp、upload_port、download_ip、download_port为必填项）
           str += "<uploadIp>${scanConfig.serverIp}</uploadIp><upload_port>${scanConfig.serverPort}</upload_port><download_ip>${scanConfig.serverIp}</download_ip><download_port>${scanConfig.serverPort}</download_port>";
           //systemId系统编号、buttonVisible控件按钮的显示情况、AnnEdit是否包含批注功能
			str += "<systemId>${scanConfig.systemId}</systemId><buttonVisible>11111010110001</buttonVisible><AnnEdit>1</AnnEdit>";
           str += "<businessId>${contractNo}</businessId><DownloadContent>1</DownloadContent><DownloadAll>0</DownloadAll>";//2232 QPC21804
			//str += "<NewBusinessId>zsy</NewBusinessId>";
			str += "<Variable>1</Variable><metadataVisible>0</metadataVisible><popMenu>111111111</popMenu>";
			//maxupsize上传文件总大小上限、SaveScanPath保存路径、autoMkSuDir是否生成新目录（SaveScanPath为必填项）
           str += "<maxupsize>500</maxupsize><SaveScanPath>C:\\ICMS\\01\\</SaveScanPath><autoMkSuDir>1</autoMkSuDir>";
			//scanFilePre扫描文件名的前缀、scanFileSuffix扫描文件名的后缀
           //str += "<scanFilePre>g</scanFilePre><scanFileSuffix>W</scanFileSuffix>";            			//ImageEditButton图像编辑按钮可见状态控制、treeMenuModel文档结构树快捷菜单使用方式、popMenu文档结构树快捷菜单项控制、Variable修改操作是否可以修改元数据及内容文件
			str += "<ImageEditButton>11111111111111111111</ImageEditButton><treeMenuModel>0</treeMenuModel><MaxupFileSize>25</MaxupFileSize>";					//autoCreateUniq是否自动生成唯一索引元数据uniq_metadata、MainToolBarStyle主界面工具栏显示方式、ImageToolBarStyle图像编辑界面工具栏显示方式、fileTypes可以导入的附件类型
           str += "<autoCreateUniq>0</autoCreateUniq><MainToolBarStyle>0</MainToolBarStyle><ImageToolBarStyle>0</ImageToolBarStyle><fileTypes>*.jpg|*.jpg|*.bmp|*.bmp|*.tif|*.tif|*.tiff|*.tiff|*.xls|*.xls|*.doc|*.doc|*.txt|*.txt|*.*|*.*</fileTypes>";     		//uploadTimeOut上传单个文件超时时间、downloadTimeOutt下载单个文件超时时间、LocalLanguage语言标识、TreeVisible是否显示文档树结构
			str += "<uploadTimeOut>120000</uploadTimeOut><LocalLanguage>CN</LocalLanguage><TreeVisible>1</TreeVisible>";
			str +="<downloadTimeOut>120000</downloadTimeOut>";

			//BarcodeType条码类型、Uploadmode上传模式（0：同步1：异步）、metadataVisible元数据编辑区是否显示
			str += "<BarcodeType>0</BarcodeType><Uploadmode>0</Uploadmode>";
			
			//BarcodeOCR是否自动进行条码识别、AutoMkBaseDir是否生成交易信息目录、AutoMkBaseDirPages每自动生成一个交易信息目录的文件个数
           str += "<AutoMkBaseDir>0</AutoMkBaseDir><AutoMkBaseDirPages>1</AutoMkBaseDirPages><BarcodeOCR>00</BarcodeOCR>";

			//MsSystemId发起消息的业务系统对应的系统标识
           str += "<MsSystemId>15</MsSystemId><showViews>1*1</showViews>";
			
			//FaceAndBack正反面标示
			str += "<FaceAndBack>1</FaceAndBack>";
			
			//Version版本号、BankCode银行号、EnCode控件与后台的通讯交互是否加密
           str += "<Version>1301</Version><BankCode>000</BankCode><EnCode>    </EnCode><isModifyFileName>11</isModifyFileName><MainImageEditButton>11111</MainImageEditButton>";
           str += "<msProvince>${scanConfig.msProvince}</msProvince><dataProvince>${scanConfig.dataProvince}</dataProvince>";
           str += "<ifAddKeyFile>1</ifAddKeyFile></Info>";
           obj.SetSys(str);
       }

       
	//设置文档类型树的树型目录结构
       function setdocstypefile()
       { 
          var str = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>";
           str += "<cred_type>";
		
           str += "<item itemcode=\"100\" itemdesc=\"影像控件\" parentcode=\"\" data_type=\"${scanConfig.dataType}\"   TemplateName=\"doc1\"/>";
           str += "<item itemcode=\"99\" itemdesc=\"合同文件\" parentcode=\"100\" data_type=\"${scanConfig.dataType}\"   compound=\"1\" TemplateName=\"doc2\" pkuuid=\"${pkuuid}\"/>";
           /*
           str += "<item itemcode=\"98\" itemdesc=\"身份文件\" parentcode=\"99\" data_type=\"1070\"   compound=\"1\" TemplateName=\"doc2\"/>";
           str += "<item itemcode=\"97\" itemdesc=\"身份证文件\" parentcode=\"98\" data_type=\"1070\"   compound=\"1\" TemplateName=\"doc2\" InputFileDoc=\"c:\\1\"/>";//InputFileDoc=\"e:\\1\"
           str += "<item itemcode=\"96\" itemdesc=\"户口文件\" parentcode=\"99\" data_type=\"1070\"   compound=\"1\" TemplateName=\"doc2\"/>";
           str += "<item itemcode=\"95\" itemdesc=\"财力证明文件\" parentcode=\"100\" data_type=\"1070\"   compound=\"1\" TemplateName=\"doc2\"/>";
	
		str += "<item itemcode=\"100\" itemdesc=\"影像控件\" parentcode=\"\"  archieved=\"Y\" data_type=\"0059\"   security=\"aa\" expire_date=\"\" new_version=\"Y\" full_index=\"Y\" modi_meta=\"Y\" />";  
		str += "<item itemcode=\"99\" itemdesc=\"文件1\" parentcode=\"100\" data_type=\"0059\"   compound=\"1\" TemplateName=\"doc2\" />";
           str += "<item itemcode=\"98\" itemdesc=\"文件2\" parentcode=\"100\" data_type=\"0059\"   compound=\"1\" TemplateName=\"doc2\"/>";
*/
           str += "</cred_type>";
           obj.SetDocsType(str);  
       }


       //扫描和扫描参数两个按钮均不显示的情况下，SetScanConfig删除
       function SetScanConfig()
       {
    	   var str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><scan><paraset SYSNO=\"01\" CURRENTTEMPLET=\"0\" TEMPLETLIST=\"平板灰度,ADF彩色\"><templet CQ=\"100\" PICTYPE=\"2\" SCANMODE=\"1\" FEEDERENABLED=\"0\" DUPLEXENABLED=\"0\" PIXELTYPE=\"1\" SUPPORTEDSIZES=\"0\" ORIENTATION=\"0\" CONTRAST=\"5\" BRIGHTNESS=\"5\" RESOLUTION=\"4\" SHOWINTERFACE=\"0\">平板灰度</templet><templet CQ=\"100\" PICTYPE=\"2\" SCANMODE=\"1\" FEEDERENABLED=\"1\" DUPLEXENABLED=\"0\" PIXELTYPE=\"1\" SUPPORTEDSIZES=\"0\" ORIENTATION=\"0\" CONTRAST=\"5\" BRIGHTNESS=\"5\" RESOLUTION=\"4\" SHOWINTERFACE=\"0\">ADF彩色</templet></paraset></scan>";
           obj.InitScanConfig(str);
       }

       function OperateVerifyNo()
       {
          var str = obj.GetSecurityNO();
          //对得到的str进行3DES加密, 得到加密字符串替换'aebf4c14e1f995d2aebf4c14e1f995d2aebf4c14e1f995d2aebf4c14e1f995d27fe74291b71313a3'；
          obj.SetVerifyNO(stringToHex(a("ABCDEFGHIJKLMNOPQRSTUVWX", str)));
       }

       function BarCodeRult()
       {
           var xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		
           xml += "<BarcodeOCR><Barcode><length>0</length><metadata>biz_metadata1</metadata></Barcode>";
           xml += "<Barcode><length>3</length><metadata>biz_metadata2</metadata></Barcode></BarcodeOCR>";
           obj.SetBarcodeOCR(xml);
        }
	 
       function InitControl() 
       {
          Init();
          OperateVerifyNo();
          SetScanConfig();
          setsys();
          //setbusiness();
          //setdocsinfo();
          setdocstypefile();
    	  //BarCodeRult();
          obj.Init();      
      }

      function DisposeControl() 
      {
          obj.Dispose();
      }
      
      $(document).ready( function () { InitControl(); } );
      $(window).unload( function () { DisposeControl(); } );
 </script>

<script language="Javascript" event="OnSave()" for="ContentMain"> 
	var str1 = obj.GetIndexInfo();
	//alert(str1.substring(5,str1.length));
	var indexInfo = str1.substring(5,str1.length);
	var xml = $.parseXML(indexInfo);
	var docSize = $(xml).find("doc_info").size();
	if(docSize==0){
		alert("请先扫描或导入文件！");
		return false;
	}
	
	var pkuuid = obj.GetValueByName('pkuuid');
	var old_pkuuid = $("#pkuuid").val();
	if (pkuuid != old_pkuuid) {
		alert("本次扫描生成的影像编号与之前生成的不一致！");
		return false;
	}
	
	var result = obj.Upload();
	//根据result，0000表示成功，8888代表部分交易成功部分交易规败
	//$("#pkuuid").val(pkuuid);
	var flag = false;
	if(result.substr(0,4) == '0000'){
		var url = "common/pay/scan/update.do?<%=WebConsts.FUNC_ID_KEY%>=03020107";
		App.ajaxSubmitForm(url, $("#scanForm"), function(data) {
			flag = true;
			art.dialog.data('cntSuccScan', true);
			alert("上传并保存成功");
		});
	} else {
		var errorXml = $.parseXML(result.substring(5));
		alert("影像上传失败：\r\n"+ $(errorXml).find("business_info").attr('errorMsg'));
		return false;
	}
	if (flag) {
		//DisposeControl();
		art.dialog.close();
	}
	return flag;
</script>

<script language="Javascript" event="OnLoadOver()" for="ContentMain"> 
       
	   alert("112");
	   //alert(str.substring(0,4));
	   //根据str，0000表示成功，8888代表部分交易成功部分交易规败
    </script>
    <script language="Javascript" event="OnScanOver()" for="ContentMain"> 
       
	   alert("112");
	   //alert(str.substring(0,4));
	   //根据str，0000表示成功，8888代表部分交易成功部分交易规败
    </script>
   
    
    <script language="Javascript" event="OnExit()" for="ContentMain"> 
        if (confirm("确定退出?")){
		DisposeControl();
		art.dialog.close();
		}
    </script>
 


</head>
<body>
<p:authFunc funcArray="03020107"/>
<form action="" method="post" id="scanForm">
<p:token/>
<input name="isPrePay" type="hidden" value="${isPrePay}"/>
<input name="dataFlag" type="hidden" value="${dataFlag}"/>
<input name="payId" type="hidden" value="${contractNo}"/>
<input name="icmsPkuuid" type="hidden" id="pkuuid" value="${pkuuid}"/>
</form>

  <div id="scanPlugin" >
	 <object id="ContentMain" classid="${scanConfig.icmsClassId}" WIDTH=100% HEIGHT=100% ALIGN=center HSPACE=0 VSPACE=0 Font-Width="9px;"></object>
  </div>
</body>
</html>