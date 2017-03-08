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
       var cntSuccScan = '';
       
       function Init()
       {
           obj = document.getElementById("ContentMain");
           //alert(obj);
        }

       function setsys()
       {
           var str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
           str += "<Info>";

           //actionFlag、MsSystemId两个参数为必填项
           str += "<actionFlag>01</actionFlag><MsSystemId>${scanConfig.systemId}</MsSystemId>";//BM

           //uploadIp、upload_port为必填项  192.168.1.197    18003
           str += "<uploadIp>${scanConfig.serverIp}</uploadIp><upload_port>${scanConfig.serverPort}</upload_port><BankCode>${scanConfig.bankCode}</BankCode>";
    str += "<isModifyFileName>10</isModifyFileName>";
           str += "<metadataVisible>0</metadataVisible><AnnEdit>1</AnnEdit><treeMenuModel>0</treeMenuModel><ScanConfig>1</ScanConfig><LocalLanguage>CN</LocalLanguage>";

           //SaveScanPath为必填项，建议为“系统路径+ICMS+系统号”；autoMkSuDir建议值为1
           str += "<maxupsize>500</maxupsize><MaxupFileSize>25</MaxupFileSize><SaveScanPath>C:\\ICMS01\\</SaveScanPath><autoMkSuDir>1</autoMkSuDir>";
           str +="<AnnEditButton>1111111111111</AnnEditButton>";

           str += "<buttonVisible>11111010110001</buttonVisible><ImageEditButton>11111111111111111111</ImageEditButton><popMenu>111111111</popMenu><MainImageEditButton>11111</MainImageEditButton>";
		str += "<ifFix>0</ifFix><canChangeImageEditWindowSize>1</canChangeImageEditWindowSize><ImageEditWindowSize>1000:600</ImageEditWindowSize><ifShowFileName>1</ifShowFileName>";
           str += "<autoCreateDoc>0</autoCreateDoc><AutoCreateDocPages>1</AutoCreateDocPages><autoCreateUniq>1</autoCreateUniq>";
    str += "<AutoMkBaseDir>0</AutoMkBaseDir><AutoMkBaseDirPages>0</AutoMkBaseDirPages><AllowManualMKBaseDir>0</AllowManualMKBaseDir>";
           str += "<fileTypes>*.jpg|*.jpg|*.bmp|*.bmp|*.tif|*.tif|*.tiff|*.tiff|*.xls|*.xls|*.doc|*.doc|*.txt|*.txt|*.*|*.*</fileTypes><FaceAndBack>0</FaceAndBack><AreaWidth>1:3:1</AreaWidth>";
           str += "<Uploadmode>0</Uploadmode><BarcodeType>0</BarcodeType><BarcodeOCR>00</BarcodeOCR><ifShowPDF>0</ifShowPDF>";
           //uploadTimeOut、FileTransWriteOneSize三个参数请各业务系统根据海外条件自行设置
           str += "<uploadTimeOut>120000</uploadTimeOut><FileTransWriteOneSize>16</FileTransWriteOneSize><FileTransReadOneSize>16</FileTransReadOneSize>";
           str += "<msProvince>${scanConfig.msProvince}</msProvince><dataProvince>${scanConfig.dataProvince}</dataProvince>";
		str += "</Info>";
           obj.SetSys(str);
       }

       function setbusiness()
       {
           var str = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>";
           str += "<batch>";
           str += "<business_info>";
           str += "<biz_metadata1>${contractNo}</biz_metadata1>";              //必填项, ≤64位字母、数字或两者的组合, 不同的交易须生成新的交易号 2232
           str += "<biz_metadata2></biz_metadata2>";
           str += "<biz_metadata3></biz_metadata3>";
           str += "<source_system>${scanConfig.systemId}</source_system>"; //15                    //必填项, ≤4位的字母、数字或两者的组合
           str += "<create_province>aaaaa</create_province>";              //必填项, 5位的字母、数字或两者的组合, 若无创建机构填写'aaaaa'
           str += "<check_telno>aaaaaaaaaaaaaaaaaaaa</check_telno>";   //必填项, ≤20位的字母、数字或两者的组合, 若无柜员号填写'aaaaaaaaaaaaaaaaaaaa'
           str += "<achr_sec>aa</achr_sec>";                                         //必填项, 2位的字母、数字或两者的组合, 默认填写'aa'
           str += "<cust_no>aaaaaaaaaaaaaaaaa</cust_no>";                    //必填项, ≤17位的字母、数字或两者的组合, 若无客户号填写'aaaaaaaaaaaaaaaaa'
           str += "</business_info>";
           str += "</batch>";
           obj.SetBusinessInfo(str);
       }
	//设置树目录最底层目录的属性信息
       function setdocsinfo()
       {            
           var str = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>";
           str += "<docs_info>";
           //data_type填写各自业务申请的数据类型
           str += "<doc_info archieved=\"Y\" data_type=\"${scanConfig.dataType}\"   security=\"aa\" expire_date=\"2050-12-17T09:30:47Z\" modi_time=\"2050-12-17T09:30:47Z\" new_version=\"Y\" full_index=\"Y\" modi_meta=\"Y\" oper_type=\"A\">";
           //str += "<uniq_metadata>123</uniq_metadata>";//123
		str += "<index_metadata1></index_metadata1>";
           str += "<index_metadata2></index_metadata2>";
           str += "<index_metadata3></index_metadata3>";
           str += "<ext_metadata></ext_metadata>";
           str += "</doc_info>";
		
           /*
		str += "<doc_info archieved=\"Y\" data_type=\"1070\"   security=\"aa\" expire_date=\"2050-12-17T09:30:47Z\" modi_time=\"2050-12-17T09:30:47Z\" new_version=\"Y\" full_index=\"Y\" modi_meta=\"Y\" oper_type=\"A\">";
           //str += "<uniq_metadata>122</uniq_metadata>";
		str += "<index_metadata1></index_metadata1>";
           str += "<index_metadata2></index_metadata2>";
           str += "<index_metadata3></index_metadata3>";
           str += "<ext_metadata></ext_metadata>";
           str += "</doc_info>";
    		
           str += "<doc_info archieved=\"Y\" data_type=\"1070\"   security=\"aa\" expire_date=\"2050-12-17T09:30:47Z\" modi_time=\"2050-12-17T09:30:47Z\" new_version=\"Y\" full_index=\"Y\" modi_meta=\"Y\" oper_type=\"A\">";
           //str += "<uniq_metadata>111</uniq_metadata>";
		str += "<index_metadata1></index_metadata1>";
           str += "<index_metadata2></index_metadata2>";
           str += "<index_metadata3></index_metadata3>";
           str += "<ext_metadata></ext_metadata>";
           str += "</doc_info>";
		*/
           str += "</docs_info>";
           obj.SetDocsInfo(str);
       }
	//设置文档类型树的树型目录结构
       function setdocstypefile()
       { 
          var str = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>";
           str += "<cred_type>";
		
           str += "<item itemcode=\"100\" itemdesc=\"影像控件\" parentcode=\"\" data_type=\"1071\"   TemplateName=\"doc1\"/>";
           str += "<item itemcode=\"99\" itemdesc=\"合同文件\" parentcode=\"100\" data_type=\"1071\"   compound=\"1\" TemplateName=\"doc2\" />";
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
          setbusiness();
          setdocsinfo();
          setdocstypefile();
    	  BarCodeRult();
          obj.Init();      
          cntSuccScan = '${cntSuccScan}';
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
		var ret = false;
		var indexInfo = str1.substring(5,str1.length);
		var xml = $.parseXML(indexInfo);
		var docSize = $(xml).find("doc_info").size();
		art.dialog.data('cntSuccScan', false);
		if(docSize==0){
			alert("请先扫描或导入文件！");		
		}
		else{	
			var pkuuid = obj.GetValueByName('pkuuid');
			var str = obj.Upload();
			if("0000" == str.substring(0,4)){
				$("#pkuuid").val(pkuuid);
				App.ajaxSubmitForm("common/contract/scan/add.do", $("#scanForm"),  
			    		function(data){
							ret = true;
							art.dialog.data('cntSuccScan', true);
							alert("上传并保存成功!");					
						});
			}
			else{
				var errorXml = $.parseXML(str.substring(5));
				alert("影像上传失败：\r\n"+$(errorXml).find("business_info").attr('errorMsg'));
			}		
		}	
		if(ret){
			DisposeControl();
			art.dialog.close();
		}
		return ret;
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
 
   
<script type="text/javascript">
window.onbeforeunload=function() {  
	  DisposeControl();
	}; 
</script>
</head>
<body>
<p:authFunc funcArray="03020107"/>
<form action="<%=request.getContextPath()%>/common/contract/scan/add.do" method="post" id="scanForm">
<input name="id" type="hidden" value="${contractNo}"/>
<input name="icmsPkuuid" type="hidden" id="pkuuid"/>
</form>

  <div id="scanPlugin" >
	 <object id="ContentMain" classid="${scanConfig.icmsClassId}" WIDTH=100% HEIGHT=100% ALIGN=center HSPACE=0 VSPACE=0 Font-Width="9px;"></object>
  </div>
</body>
</html>