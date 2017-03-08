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
<title>付款扫描</title>
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
    	 //actionFlag、MsSystemId两个参数为必填项
           str += "<actionFlag>01</actionFlag><MsSystemId>${scanConfig.systemId}</MsSystemId>";//BM

           //uploadIp、upload_port为必填项  192.168.1.197    18003
           str += "<uploadIp>${scanConfig.serverIp}</uploadIp><upload_port>${scanConfig.serverPort}</upload_port><BankCode>${scanConfig.bankCode}</BankCode>";
	    str += "<isModifyFileName>10</isModifyFileName>";
           str += "<metadataVisible>0</metadataVisible><AnnEdit>1</AnnEdit><treeMenuModel>0</treeMenuModel><ScanConfig>1</ScanConfig><LocalLanguage>CN</LocalLanguage>";

           //SaveScanPath为必填项，建议为“系统路径+ICMS+系统号”；autoMkSuDir建议值为1
           str += "<maxupsize>500</maxupsize><MaxupFileSize>25</MaxupFileSize><SaveScanPath>C:\\ICMS\\01\\</SaveScanPath><autoMkSuDir>1</autoMkSuDir>";
           str +="<AnnEditButton>1111111111111</AnnEditButton>";

           str += "<buttonVisible>11111010110001</buttonVisible><ImageEditButton>11111111111111111111</ImageEditButton><popMenu>111111111</popMenu><MainImageEditButton>11111</MainImageEditButton>";
			str += "<ifFix>0</ifFix><canChangeImageEditWindowSize>1</canChangeImageEditWindowSize><ImageEditWindowSize>1000:600</ImageEditWindowSize><ifShowFileName>1</ifShowFileName>";
           str += "<autoCreateDoc>0</autoCreateDoc><AutoCreateDocPages>1</AutoCreateDocPages><autoCreateUniq>1</autoCreateUniq>";
	    str += "<AutoMkBaseDir>1</AutoMkBaseDir><AutoMkBaseDirPages>0</AutoMkBaseDirPages><AllowManualMKBaseDir>0</AllowManualMKBaseDir>";
           str += "<fileTypes>*.bmp|*.bmp|*.jpg|*.jpg|*.tif|*.tif|*.tiff|*.tiff|*.xls|*.xls|*.doc|*.doc|*.txt|*.txt|*.*|*.*</fileTypes><FaceAndBack>0</FaceAndBack><AreaWidth>1:3:1</AreaWidth>";
           str += "<Uploadmode>0</Uploadmode><BarcodeType>1</BarcodeType><BarcodeOCR>11</BarcodeOCR><ifShowPDF>0</ifShowPDF>";
           //uploadTimeOut、FileTransWriteOneSize三个参数请各业务系统根据海外条件自行设置
           str += "<uploadTimeOut>120000</uploadTimeOut><FileTransWriteOneSize>64</FileTransWriteOneSize><FileTransReadOneSize>64</FileTransReadOneSize>";
           str += "<msProvince>${scanConfig.msProvince}</msProvince><dataProvince>${scanConfig.dataProvince}</dataProvince>";
			str += "</Info>";
           obj.SetSys(str);
       }

       function setbusiness()
       {
           var str = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>";
           str += "<batch>";
           str += "<business_info>";
           str += "<biz_metadata1>111</biz_metadata1>";              //必填项, ≤64位字母、数字或两者的组合, 不同的交易须生成新的交易号 2232
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
           str += "<item itemcode=\"99\" itemdesc=\"付款文件\" parentcode=\"100\" data_type=\"1071\"   compound=\"1\" TemplateName=\"doc2\" />";
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
		
           xml += "<BarcodeOCR><Barcode><length>18</length><metadata>biz_metadata1</metadata></Barcode>";
           xml += "</BarcodeOCR>";
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
      }

      function DisposeControl() 
      {
          obj.Dispose();
      }
      
      $(document).ready( function () { InitControl(); } );
      $(window).unload( function () { DisposeControl(); } );
      
      //节点置红
      function setRedColor(bizs){
    	  var xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
    	  xml += "<Details>";
    	  for(var i=0;i<bizs.length;i++){
    		  xml += "<AtypicalId businessId=\""+bizs[i]+"\"/>";
    	  }
    	  xml += "</Details>";
    	  var result = obj.SetBusiNoColor(xml);
      }
      
    //节点重置
      function resetRedColor(xml){
    	var bizs = $("biz_metadata1",xml).map(function(){
    		  return $(this).text();
    	}).get().join("|");
    	var result = obj.ReCoverDocColor(bizs);
      }
 </script>
 
  <script language="Javascript" event="OnSave()" for="ContentMain"> 
  App.submitShowProgress();
  
  /////////////////////上传前的检查//////////////////////
	var str1 = obj.GetIndexInfo();
	var indexInfo = str1.substring(5,str1.length);
	indexInfo = indexInfo.replace(/<\?.*\?>/g,"");
	indexInfo = "<batchs>"+indexInfo+"</batchs>";
	//alert(indexInfo);
	var xml = $.parseXML(indexInfo);
	var docSize = $(xml).find("doc_info").size();
	if(docSize==0){
		alert("请先扫描或导入文件！");
		return false;
	}
	
	
	//检查封面总数和附件总数
	var fsize = $("business_info",xml).size();
	var asize = $("page",xml).size();//-fsize;
	//alert("预期封面数/实际封面数：" + '${mainCnt}' + '/' + fsize + '\r\n' + "预期总页数/实际封面数：" + '${attachCnt}' + '/' + asize);
	/*
	if('${mainCnt}'!=0&&fsize!='${mainCnt}'){
		if(!confirm(("数目不符！\n\n预期封面总数【"+'${mainCnt}'+"】\n扫描封面总数【"+fsize+"】\n\n确认继续？"))){
			return false;
		};
	}
	
	
	if(asize!='${attachCnt}'){
		if(!confirm(("数目不符！\n\n预期总页数【"+'${attachCnt}'+"】\n扫描总页数【"+asize+"】\n\n确认继续？"))){
			return false;
		};
	}
	*/
	var batchNo = "${batchNo}";
	var payId = "";
	var icmsPkuuid = "";
	var attachCnt = "";
	/////////////////////扫描信息获取//////////////////////	
	$("business_info",xml).each(function(i){
		var biz_metadata1 =  $(this).find("biz_metadata1").text();
		var pkuuid =  $(this).find("pkuuid").text();
		var pageCnt = $(this).find("page").size();
		if(i==0){
			payId=payId+biz_metadata1;
			icmsPkuuid=icmsPkuuid+pkuuid;
			attachCnt=attachCnt+(pageCnt-1);
		}else{
			payId=payId+"&&"+biz_metadata1;
			icmsPkuuid=icmsPkuuid+"&&"+pkuuid;
			attachCnt=attachCnt+"&&"+(pageCnt-1);
		}
	});
	var data = {"batchNo":batchNo,"payIds":payId,"icmsPkuuids":icmsPkuuid,"attachCnts":attachCnt};
	var succFlag = false;
	//置红节点重置
	resetRedColor(xml);
	App.ajaxSubmit("common/pay/scan/insertAjax.do",
			{data:data,async : false},
			function(data){
				if(data.errorList&&data.errorList!=""){
					var msg = "";
					var detail;
					var bizs = new Array();
					for(var i=0;i<data.errorList.length;i++){
						detail = data.errorList[i];
						msg +=detail.payId+" - " +detail.memo + "\r\n";
						bizs.push(detail.payId);
					}
					//出错节点置红
					setRedColor(bizs);
					alert('目前无法进行影像的上传，系统校验后发现如下错误：\r\n' + msg);
				}else{
					succFlag = true;
				}
			});
	if(!succFlag){
		return false;
	}
/////////////////////上传并获取结果//////////////////////	
    if(!confirm('影像校验全部通过，现在是否确定上传影像?')){
    	return false;
    }
    
	var result = obj.Upload();	
	if(result.substr(0,4) == '0000'){
		var uploadInfo = result.substring(5,result.length);
		uploadInfo = uploadInfo.replace(/<\?.*\?>/g,"");
		var uxml = $.parseXML(uploadInfo);
		var payId = "";		
		var flag = "";		
		var errorMsg = "";
		var errorMsgVal = "";		
		var icmsPkuuid = "";
		var promInfo = "";
		
		$("business_info",uxml).each(function(i){
			errorMsgVal = $(this).attr("errorMsg");
			if(errorMsgVal == null || errorMsgVal == ''){
				errorMsgVal = 'succ';
			}

			if(i==0){
				payId = payId + $(this).attr("BusinessId");
				flag = flag + $(this).attr("flag");
				errorMsg = errorMsg +  errorMsgVal;
				icmsPkuuid = icmsPkuuid + $(this).find("item").attr("pkuuid");
			}else{
				payId = payId + "&&"+ $(this).attr("BusinessId");
				flag = flag + "&&"+ $(this).attr("flag");
				errorMsg = errorMsg + "&&"+ errorMsgVal;
				icmsPkuuid = icmsPkuuid + "&&"+$(this).find("item").attr("pkuuid");
			}
		});
		var data = {"batchNo":batchNo,"payIds":payId,"icmsPkuuids":icmsPkuuid,"flags":flag,"errorMsgs":errorMsg};
		var flag = false;
		var failureMsgVal = '';
		App.ajaxSubmit("common/pay/scan/uploadAjax.do",
				{data:data,async : false},
				function(data){
					flag = true;
					
				},
				function(failureMsg,data)
				{
					failureMsgVal = failureMsg;					
				}
		);
		App.submitFinish();
		if(flag){
		  alert('影像上传成功！');
		  art.dialog.close();
		}
		else {alert(failureMsgVal);	};
		return flag;
	}
	else{
		var errorXml = $.parseXML(result.substring(5));
		alert("影像上传失败：\r\n"+$(errorXml).find("business_info").attr('errorMsg'));
		return false;
	}
	
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
<form action="<%=request.getContextPath()%>/common/contract/scan/add.do?" method="post" id="scanForm">
<p:token/>
<input name="id" type="hidden" value="${contractNo}"/>
<input name="icmsPkuuid" type="hidden" id="pkuuid"/>
</form>

  <div id="scanPlugin" >
	 <object id="ContentMain" classid="${scanConfig.icmsClassId}" WIDTH=100% HEIGHT=100% ALIGN=center HSPACE=0 VSPACE=0 Font-Width="9px;"></object>
  </div>
</body>
</html>