/**
 * 时间累积并显示
 * @param timespan
 */
function thenceThen(timespan){
	if(nowSeconds <=0){//避免频繁和服务器交互
		var date=getServerCurrentTime();//获取服务器时间
		nowSeconds = date.getTime();
	}else{
		nowSeconds += 500;//第一次获得服务器时间后后边只要递增即可，递增毫秒数必须和调用该方法的页面上的定时器的间隔时间相同
	}
	 var date1=new Date(timespan);
	 var totalSecs=(nowSeconds-date1.getTime())/1000;
	 var days=Math.floor(totalSecs/3600/24);
	 var hours=Math.floor((totalSecs-days*24*3600)/3600);
	 var mins=Math.floor((totalSecs-days*24*3600-hours*3600)/60);
	 var secs=Math.floor((totalSecs-days*24*3600-hours*3600-mins*60));
	 $("#thenceThen").html("<span id='thenceSpan'>总耗时：</span>"+days+"天"+hours+"时"+mins+"分"+secs+"秒");
}
/**
 * 显示流程图
 * processDefinitionId:流程ID
 * procInstId：实例ID
 * funcId：方法ID （根据方法ID判断来源 REQ:需求管理，BLPR:方案管理  IMPL:实施管理）
 * busiId：业务ID
 */
function $_graphTrace(processDefinitionId, procInstId ,funcId ,busiId) {
	if(processDefinitionId!=null&&processDefinitionId!=""&&procInstId!=null&&procInstId!=""&&funcId!=null&&funcId!=""&&busiId!=null&&busiId!=""){
		//锁屏
		App.submitShowProgress();
		
		var fromPage;
		if(funcId.indexOf("07")==0){
			fromPage = "REQ";
		}else if(funcId.indexOf("06")==0){
			fromPage = "BLPR";
		}else if(funcId.indexOf("05")==0){
			fromPage = "IMPL";
		}
		
		// 获取图片资源
		var imageUrl = "/PRMS/common/activitytool/getWfPic.do?VISIT_FUNC_ID=9901&procDefId="+processDefinitionId+"&procInstId="+procInstId+"&resourceType=image";
		
		$.getJSON("/PRMS/common/activitytool/getTrace.do?VISIT_FUNC_ID=9902&procDefId="+processDefinitionId+"&procInstId="+procInstId+"&fromPage="+fromPage+"&busiId="+busiId+"&rnd="+Math.random(), function(infos) {
		
			var positionHtml = "";
	
		    // 生成图片
		    var varsArray = new Array();
		    var tipsArray = new Array();
		    $.each(infos, function(i, v) {
		        var $positionDiv = $('<div/>', {
		            'class': 'activity-attr'
		        }).css({
		            position: 'absolute',
		            left: (v.x - 1),
		            top: (v.y - 1),
		            width: (v.width + 2),
		            height: (v.height +2),
		            backgroundColor: 'black',
		            opacity: 0,
		            "z-index": 100
		        });
		        if(v.vars.resultFlag=='1'){
		        	$positionDiv.css({cursor:'pointer'});
		        }
		
		        // 节点边框
		        var $border = $('<div/>', {
		            'class': 'activity-attr-border'
		        }).css({
		            position: 'absolute',
		            left: (v.x - 1),
		            top: (v.y - 1),
		            width: (v.width - 3),
		            height: (v.height - 3),
		            "z-index": 99
		        });
		        
		        if (v.current) {
		            $border.addClass('ui-corner-all-12').css({
		                border: '3px solid red'
		            });
		        }
		        //获取元素的HTML代码，positionHtml += $positionDiv.outerHTML() + $border.outerHTML();
		        positionHtml += $($('<div></div>').html($positionDiv.clone())).html()+$($('<div></div>').html($border.clone())).html();
		        varsArray[varsArray.length] = v.vars;
		        
		    });
		    
		    // 先删除之前生成的DIV，否则无法获取图片尺寸
			$('#workflowTraceDialog').remove();
	
		    if ($('#workflowTraceDialog').length == 0) {
		        $('<div/>', {
		            id: 'workflowTraceDialog',
		            title: '查看流程',
		            html: "<div><img src='" + imageUrl + "' style='position:absolute; left:0px; top:0px;' />" +
		            "<div id='processImageBorder'>" +
		            positionHtml +
		            "</div>" +
		            "</div>"
		        }).appendTo('body');
		    } else {
		        $('#workflowTraceDialog img').attr('src', imageUrl);
		        $('#workflowTraceDialog #processImageBorder').html(positionHtml);
		    }
		    
		    // 打开对话框
		    $('#workflowTraceDialog').dialog({
		    	modal: true,
		        resizable: true,
		        dragable: false,
		        dialogClass: 'wfPicDialogClass',
		        width: 1040,
		        height: 600
		    });
		    
		    App.submitFinish();
		    
		    //生成之后循环绑定事件
		    $.each(infos, function(i, v) {
				if(v.vars.resultFlag=="1"){
					$("#processImageBorder").find(".activity-attr")[i].onclick = function(){
	   					$_graphTrace(v.vars.procDefId, v.vars.procInstId , funcId, busiId);
	   		        };
				}
		    });
		});
	}
};

function trim(str)
{
  if(str==undefined||str==null)
  {
    return "";
  }
 
  if (str.length > 0) 
  {
   while ((str.substring(0,1) == " ") && (str.length > 0)) 
   {
    str = str.substring(1,str.length);
   }
   while (str.substring(str.length-1,str.length) == " ") 
   {
    str = str.substring(0,str.length-1);
   }
  }
  return str;
}
/**
 * Judge whether is a chinses character
 *
 *
 * @param value -- a character
 *
 * @return 
 */ 
function isChinese(value)
{	
    if(value < 127)
    {
	    return false;
    }
    return true;
}

/**
* 检查总字数，超过最大长度时弹出警告提示.
* @param obj 当前录入文字的HTML ELEMENT对象引用
* @param maxLength 所允许的最大字符长度（中文字符算2字符）
* @param warnMsgShowTargetId 即时提示显示的位置，即某HTML ELEMENT 的ID，是般是某个SPAN标签的ID。
*/
function  $_showWarnWhenOverLen1(obj, maxLength,warnMsgShowTargetId,name)
{	
	var length = 0;
	for(var i = 0; i < obj.value.length; i++)
	{
		if(isChinese(obj.value.charCodeAt(i)))
		{
			length += 2;
		} else
		{
			length += 1;
		}
	}
	
	var warnMsgShowTargetObj = document.getElementById(warnMsgShowTargetId);
	if(warnMsgShowTargetObj)
	{
		warnMsgShowTargetObj.innerText = "".concat(length).concat("/").concat(maxLength).concat("");
	}
	length = 0;
	for(var i = 0; i < obj.value.length; i++)
	{
		if(isChinese(obj.value.charCodeAt(i)))
		{
			length += 2;
		} else
		{
			length += 1;
		}
		
		if(length > maxLength)
		{	
			//if(name != undefined)
			//{
			//	alert("["+name+"]"+ "最大长度为(" + maxLength + "字符)!");
			//} else
			//{
			//	alert("最大长度为(" + maxLength + "字符)!");
			//}
			obj.value = obj.value.substring(0, i);
			
			length = 0;
			for(var i = 0; i < obj.value.length; i++)
			{
				if(isChinese(obj.value.charCodeAt(i)))
				{
					length += 2;
				} else
				{
					length += 1;
				}
			}
			//重新显示最终的字符总数
			if(warnMsgShowTargetObj)
			{
				warnMsgShowTargetObj.innerText = "".concat(length).concat("/").concat(maxLength).concat("");
			}
			return false;
		}
	}
	
	return true;
}

/**
* 功能说明：改变TR的背景颜色。<br>
* 使用方法：1、首先将该js文件包含至页面中；<br>
*         2、在tr中增加：
*            <tr onmouseover="setTrBgClass(this, 'trGrey');" onmouseout="setTrBgClass(this, 'trOnOver');">...</tr>
* wucf 2013-10-28
*/
function setTrBgClass(trObjectRef, bgClassName)
{
    if(trObjectRef )
    {
	    trObjectRef.className=bgClassName;
    }
}

/**
 * 功能说明：返回到上一页
 * 使用方法：调用该方法即可
 * 
 * @param uri
 */
function backToLastPage(uri)
{	
	if(uri != null && uri != ''){
		var url=encodeURI(encodeURI(uri));
		$("body").html("<form action='"+ url +"' method='post' id='lastPageForm'></form>");
		App.submit($("#lastPageForm")[0]);
	}else{
		history.back();
	}
	
}

function backToLastPage2(uri)
{
	if(uri != null && uri != '')
	{
		parent.location.href = uri;
	}
	else
	{
		history.back();
	}
}


function shortDesc(id,id2,text){
	var length = 0;
	var showText="";
	if(text == null && "" == text){
		return ;
	}
	for(var i = 0; i < text.length; i++)
	{
		if(length > 60){
			showText=text.substring(0,i+1);
			break;
		}
		if(isChinese(text.charCodeAt(i)))
		{
			length += 2;
		} else
		{
			length += 1;
		}
	}
	if(length < 60){
		showText=text;
	}
	//.after("<div style='color : red;' onclick = 'detailDesc(\""+id+"\",\""+text+"\");'>  详细查看>>> </div>")
	$("#"+id).text(showText);
	if(length > 60){
		$("#"+id2).html("<span style='color : red;'>  详细查看>> </span>").click(
			function(){
				detailDesc(id,id2,text);
			}
		);
	}
}
function detailDesc (id,id2,text){
	if(text == null && "" == text){
		return ;
	}
	//.after("<div style='color : blue ;' onclick = 'shortDesc(\""+id+"\",\""+str+"\")'> <<<折叠</div>")
	$("#"+id).text(text);
	$("#"+id2).html("<span style='color : blue ;' > <<折叠 </span>").click(
			function() {
				shortDesc(id,id2,text);
			}
	);
}
//获取服务器时间
function getServerCurrentTime()
{
    try
    {
        var xmlhttp=null;
        if (window.XMLHttpRequest)
        {
            xmlhttp=new XMLHttpRequest();
        }
        else if (window.ActiveXObject)
        {
            xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.open("HEAD", "/PRMS", false); 
        xmlhttp.setRequestHeader("CacheControl","no-cache");  
        xmlhttp.setRequestHeader("Pragma","no-cache"); 
        xmlhttp.send(); 
        var date=new Date(xmlhttp.getResponseHeader("Date")); 
        return date;
    }
    catch(err)
    {
        return new Date();  //Ajax获取失败的话只好返回客户端的时间
    }
}
function showDescDiv(obj) {
	var tta=document.getElementById("showDescDiv");
	if(tta != null ){
		$("#showDescDiv").css("display","none");
	}
	//$(this).append('')
	//$("#showDescDiv").css("width",$(this).width()+70);
	$("#showDescDiv").css("left",$(obj).position().left);
	$("#showDescDiv").css("top",$(obj).position().top+$(obj).innerHeight());
	$("#showDescDiv #showDescText").text($(obj).attr("value"));
	$("#showDescDiv").css("display","");
	
}

function closeDiv(){
	$("#showDescDiv").css("display","none");
}


/************************影像控件用********************************/
function des (key, message, encrypt, mode, iv, padding) {
    //declaring this locally speeds things up a bit
    var spfunction1 = new Array (0x1010400,0,0x10000,0x1010404,0x1010004,0x10404,0x4,0x10000,0x400,0x1010400,0x1010404,0x400,0x1000404,0x1010004,0x1000000,0x4,0x404,0x1000400,0x1000400,0x10400,0x10400,0x1010000,0x1010000,0x1000404,0x10004,0x1000004,0x1000004,0x10004,0,0x404,0x10404,0x1000000,0x10000,0x1010404,0x4,0x1010000,0x1010400,0x1000000,0x1000000,0x400,0x1010004,0x10000,0x10400,0x1000004,0x400,0x4,0x1000404,0x10404,0x1010404,0x10004,0x1010000,0x1000404,0x1000004,0x404,0x10404,0x1010400,0x404,0x1000400,0x1000400,0,0x10004,0x10400,0,0x1010004);
    var spfunction2 = new Array (-0x7fef7fe0,-0x7fff8000,0x8000,0x108020,0x100000,0x20,-0x7fefffe0,-0x7fff7fe0,-0x7fffffe0,-0x7fef7fe0,-0x7fef8000,-0x80000000,-0x7fff8000,0x100000,0x20,-0x7fefffe0,0x108000,0x100020,-0x7fff7fe0,0,-0x80000000,0x8000,0x108020,-0x7ff00000,0x100020,-0x7fffffe0,0,0x108000,0x8020,-0x7fef8000,-0x7ff00000,0x8020,0,0x108020,-0x7fefffe0,0x100000,-0x7fff7fe0,-0x7ff00000,-0x7fef8000,0x8000,-0x7ff00000,-0x7fff8000,0x20,-0x7fef7fe0,0x108020,0x20,0x8000,-0x80000000,0x8020,-0x7fef8000,0x100000,-0x7fffffe0,0x100020,-0x7fff7fe0,-0x7fffffe0,0x100020,0x108000,0,-0x7fff8000,0x8020,-0x80000000,-0x7fefffe0,-0x7fef7fe0,0x108000);
    var spfunction3 = new Array (0x208,0x8020200,0,0x8020008,0x8000200,0,0x20208,0x8000200,0x20008,0x8000008,0x8000008,0x20000,0x8020208,0x20008,0x8020000,0x208,0x8000000,0x8,0x8020200,0x200,0x20200,0x8020000,0x8020008,0x20208,0x8000208,0x20200,0x20000,0x8000208,0x8,0x8020208,0x200,0x8000000,0x8020200,0x8000000,0x20008,0x208,0x20000,0x8020200,0x8000200,0,0x200,0x20008,0x8020208,0x8000200,0x8000008,0x200,0,0x8020008,0x8000208,0x20000,0x8000000,0x8020208,0x8,0x20208,0x20200,0x8000008,0x8020000,0x8000208,0x208,0x8020000,0x20208,0x8,0x8020008,0x20200);
    var spfunction4 = new Array (0x802001,0x2081,0x2081,0x80,0x802080,0x800081,0x800001,0x2001,0,0x802000,0x802000,0x802081,0x81,0,0x800080,0x800001,0x1,0x2000,0x800000,0x802001,0x80,0x800000,0x2001,0x2080,0x800081,0x1,0x2080,0x800080,0x2000,0x802080,0x802081,0x81,0x800080,0x800001,0x802000,0x802081,0x81,0,0,0x802000,0x2080,0x800080,0x800081,0x1,0x802001,0x2081,0x2081,0x80,0x802081,0x81,0x1,0x2000,0x800001,0x2001,0x802080,0x800081,0x2001,0x2080,0x800000,0x802001,0x80,0x800000,0x2000,0x802080);
    var spfunction5 = new Array (0x100,0x2080100,0x2080000,0x42000100,0x80000,0x100,0x40000000,0x2080000,0x40080100,0x80000,0x2000100,0x40080100,0x42000100,0x42080000,0x80100,0x40000000,0x2000000,0x40080000,0x40080000,0,0x40000100,0x42080100,0x42080100,0x2000100,0x42080000,0x40000100,0,0x42000000,0x2080100,0x2000000,0x42000000,0x80100,0x80000,0x42000100,0x100,0x2000000,0x40000000,0x2080000,0x42000100,0x40080100,0x2000100,0x40000000,0x42080000,0x2080100,0x40080100,0x100,0x2000000,0x42080000,0x42080100,0x80100,0x42000000,0x42080100,0x2080000,0,0x40080000,0x42000000,0x80100,0x2000100,0x40000100,0x80000,0,0x40080000,0x2080100,0x40000100);
    var spfunction6 = new Array (0x20000010,0x20400000,0x4000,0x20404010,0x20400000,0x10,0x20404010,0x400000,0x20004000,0x404010,0x400000,0x20000010,0x400010,0x20004000,0x20000000,0x4010,0,0x400010,0x20004010,0x4000,0x404000,0x20004010,0x10,0x20400010,0x20400010,0,0x404010,0x20404000,0x4010,0x404000,0x20404000,0x20000000,0x20004000,0x10,0x20400010,0x404000,0x20404010,0x400000,0x4010,0x20000010,0x400000,0x20004000,0x20000000,0x4010,0x20000010,0x20404010,0x404000,0x20400000,0x404010,0x20404000,0,0x20400010,0x10,0x4000,0x20400000,0x404010,0x4000,0x400010,0x20004010,0,0x20404000,0x20000000,0x400010,0x20004010);
    var spfunction7 = new Array (0x200000,0x4200002,0x4000802,0,0x800,0x4000802,0x200802,0x4200800,0x4200802,0x200000,0,0x4000002,0x2,0x4000000,0x4200002,0x802,0x4000800,0x200802,0x200002,0x4000800,0x4000002,0x4200000,0x4200800,0x200002,0x4200000,0x800,0x802,0x4200802,0x200800,0x2,0x4000000,0x200800,0x4000000,0x200800,0x200000,0x4000802,0x4000802,0x4200002,0x4200002,0x2,0x200002,0x4000000,0x4000800,0x200000,0x4200800,0x802,0x200802,0x4200800,0x802,0x4000002,0x4200802,0x4200000,0x200800,0,0x2,0x4200802,0,0x200802,0x4200000,0x800,0x4000002,0x4000800,0x800,0x200002);
    var spfunction8 = new Array (0x10001040,0x1000,0x40000,0x10041040,0x10000000,0x10001040,0x40,0x10000000,0x40040,0x10040000,0x10041040,0x41000,0x10041000,0x41040,0x1000,0x40,0x10040000,0x10000040,0x10001000,0x1040,0x41000,0x40040,0x10040040,0x10041000,0x1040,0,0,0x10040040,0x10000040,0x10001000,0x41040,0x40000,0x41040,0x40000,0x10041000,0x1000,0x40,0x10040040,0x1000,0x41040,0x10001000,0x40,0x10000040,0x10040000,0x10040040,0x10000000,0x40000,0x10001040,0,0x10041040,0x40040,0x10000040,0x10040000,0x10001000,0x10001040,0,0x10041040,0x41000,0x41000,0x1040,0x1040,0x40040,0x10000000,0x10041000);
  
    //create the 16 or 48 subkeys we will need
    var keys = des_createKeys (key);
    var m=0, i, j, temp, temp2, right1, right2, left, right, looping;
    var cbcleft, cbcleft2, cbcright, cbcright2
    var endloop, loopinc;
    var len = message.length;
    var chunk = 0;
    //set up the loops for single and triple des
    var iterations = keys.length == 32 ? 3 : 9; //single or triple des
    if (iterations == 3) {
      looping = encrypt ? new Array (0, 32, 2) : new Array (30, -2, -2);
    }
    else {
      looping = encrypt ? new Array (0, 32, 2, 62, 30, -2, 64, 96, 2) : new Array (94, 62, -2, 32, 64, 2, 30, -2, -2);
    }
  
    //pad the message depending on the padding parameter
    if (padding == 2) 
      message += "        "; //pad the message with spaces
    else if (padding == 1) {
      temp = 8-(len%8); message += String.fromCharCode (temp,temp,temp,temp,temp,temp,temp,temp); 
  	  if (temp==8) 
  	    len+=8;
    } //PKCS7 padding 这里其实只是PKCS5 padding, PKCS5规定采用8位补全，而PKCS7则不确定位数
    else if (!padding) 
      message += "\0\0\0\0\0\0\0\0"; //pad the message out with null bytes
  
    //store the result here
    result = "";
    tempresult = "";
  
    if (mode == 1) { //CBC mode
      cbcleft = (iv.charCodeAt(m++) << 24) | (iv.charCodeAt(m++) << 16) | (iv.charCodeAt(m++) << 8) | iv.charCodeAt(m++);
      cbcright = (iv.charCodeAt(m++) << 24) | (iv.charCodeAt(m++) << 16) | (iv.charCodeAt(m++) << 8) | iv.charCodeAt(m++);
      m=0;
    }
  
    //loop through each 64 bit chunk of the message
    while (m < len) {
      left = (message.charCodeAt(m++) << 24) | (message.charCodeAt(m++) << 16) | (message.charCodeAt(m++) << 8) | message.charCodeAt(m++);
      right = (message.charCodeAt(m++) << 24) | (message.charCodeAt(m++) << 16) | (message.charCodeAt(m++) << 8) | message.charCodeAt(m++);
  
      //for Cipher Block Chaining mode, xor the message with the previous result
      if (mode == 1) {if (encrypt) {left ^= cbcleft; right ^= cbcright;} else {cbcleft2 = cbcleft; cbcright2 = cbcright; cbcleft = left; cbcright = right;}}
  
      //first each 64 but chunk of the message must be permuted according to IP
      temp = ((left >>> 4) ^ right) & 0x0f0f0f0f; right ^= temp; left ^= (temp << 4);
      temp = ((left >>> 16) ^ right) & 0x0000ffff; right ^= temp; left ^= (temp << 16);
      temp = ((right >>> 2) ^ left) & 0x33333333; left ^= temp; right ^= (temp << 2);
      temp = ((right >>> 8) ^ left) & 0x00ff00ff; left ^= temp; right ^= (temp << 8);
      temp = ((left >>> 1) ^ right) & 0x55555555; right ^= temp; left ^= (temp << 1);
  
      left = ((left << 1) | (left >>> 31)); 
      right = ((right << 1) | (right >>> 31)); 
  
      //do this either 1 or 3 times for each chunk of the message
      for (j=0; j<iterations; j+=3) {
        endloop = looping[j+1];
        loopinc = looping[j+2];
        //now go through and perform the encryption or decryption  
        for (i=looping[j]; i!=endloop; i+=loopinc) { //for efficiency
          right1 = right ^ keys[i]; 
          right2 = ((right >>> 4) | (right << 28)) ^ keys[i+1];
          //the result is attained by passing these bytes through the S selection functions
          temp = left;
          left = right;
          right = temp ^ (spfunction2[(right1 >>> 24) & 0x3f] | spfunction4[(right1 >>> 16) & 0x3f]
                | spfunction6[(right1 >>>  8) & 0x3f] | spfunction8[right1 & 0x3f]
                | spfunction1[(right2 >>> 24) & 0x3f] | spfunction3[(right2 >>> 16) & 0x3f]
                | spfunction5[(right2 >>>  8) & 0x3f] | spfunction7[right2 & 0x3f]);
        }
        temp = left; left = right; right = temp; //unreverse left and right
      } //for either 1 or 3 iterations
  
      //move then each one bit to the right
      left = ((left >>> 1) | (left << 31)); 
      right = ((right >>> 1) | (right << 31)); 
  
      //now perform IP-1, which is IP in the opposite direction
      temp = ((left >>> 1) ^ right) & 0x55555555; right ^= temp; left ^= (temp << 1);
      temp = ((right >>> 8) ^ left) & 0x00ff00ff; left ^= temp; right ^= (temp << 8);
      temp = ((right >>> 2) ^ left) & 0x33333333; left ^= temp; right ^= (temp << 2);
      temp = ((left >>> 16) ^ right) & 0x0000ffff; right ^= temp; left ^= (temp << 16);
      temp = ((left >>> 4) ^ right) & 0x0f0f0f0f; right ^= temp; left ^= (temp << 4);
  
      //for Cipher Block Chaining mode, xor the message with the previous result
      if (mode == 1) {if (encrypt) {cbcleft = left; cbcright = right;} else {left ^= cbcleft2; right ^= cbcright2;}}
      tempresult += String.fromCharCode ((left>>>24), ((left>>>16) & 0xff), ((left>>>8) & 0xff), (left & 0xff), (right>>>24), ((right>>>16) & 0xff), ((right>>>8) & 0xff), (right & 0xff));
  
      chunk += 8;
      if (chunk == 512) {result += tempresult; tempresult = ""; chunk = 0;}
    } //for every 8 characters, or 64 bits in the message
  
    //return the result as an array
    return result + tempresult;
  } //end of des
  
  
  
  //des_createKeys
  //this takes as input a 64 bit key (even though only 56 bits are used)
  //as an array of 2 integers, and returns 16 48 bit keys
  function des_createKeys (key) {
    //declaring this locally speeds things up a bit
    pc2bytes0  = new Array (0,0x4,0x20000000,0x20000004,0x10000,0x10004,0x20010000,0x20010004,0x200,0x204,0x20000200,0x20000204,0x10200,0x10204,0x20010200,0x20010204);
    pc2bytes1  = new Array (0,0x1,0x100000,0x100001,0x4000000,0x4000001,0x4100000,0x4100001,0x100,0x101,0x100100,0x100101,0x4000100,0x4000101,0x4100100,0x4100101);
    pc2bytes2  = new Array (0,0x8,0x800,0x808,0x1000000,0x1000008,0x1000800,0x1000808,0,0x8,0x800,0x808,0x1000000,0x1000008,0x1000800,0x1000808);
    pc2bytes3  = new Array (0,0x200000,0x8000000,0x8200000,0x2000,0x202000,0x8002000,0x8202000,0x20000,0x220000,0x8020000,0x8220000,0x22000,0x222000,0x8022000,0x8222000);
    pc2bytes4  = new Array (0,0x40000,0x10,0x40010,0,0x40000,0x10,0x40010,0x1000,0x41000,0x1010,0x41010,0x1000,0x41000,0x1010,0x41010);
    pc2bytes5  = new Array (0,0x400,0x20,0x420,0,0x400,0x20,0x420,0x2000000,0x2000400,0x2000020,0x2000420,0x2000000,0x2000400,0x2000020,0x2000420);
    pc2bytes6  = new Array (0,0x10000000,0x80000,0x10080000,0x2,0x10000002,0x80002,0x10080002,0,0x10000000,0x80000,0x10080000,0x2,0x10000002,0x80002,0x10080002);
    pc2bytes7  = new Array (0,0x10000,0x800,0x10800,0x20000000,0x20010000,0x20000800,0x20010800,0x20000,0x30000,0x20800,0x30800,0x20020000,0x20030000,0x20020800,0x20030800);
    pc2bytes8  = new Array (0,0x40000,0,0x40000,0x2,0x40002,0x2,0x40002,0x2000000,0x2040000,0x2000000,0x2040000,0x2000002,0x2040002,0x2000002,0x2040002);
    pc2bytes9  = new Array (0,0x10000000,0x8,0x10000008,0,0x10000000,0x8,0x10000008,0x400,0x10000400,0x408,0x10000408,0x400,0x10000400,0x408,0x10000408);
    pc2bytes10 = new Array (0,0x20,0,0x20,0x100000,0x100020,0x100000,0x100020,0x2000,0x2020,0x2000,0x2020,0x102000,0x102020,0x102000,0x102020);
    pc2bytes11 = new Array (0,0x1000000,0x200,0x1000200,0x200000,0x1200000,0x200200,0x1200200,0x4000000,0x5000000,0x4000200,0x5000200,0x4200000,0x5200000,0x4200200,0x5200200);
    pc2bytes12 = new Array (0,0x1000,0x8000000,0x8001000,0x80000,0x81000,0x8080000,0x8081000,0x10,0x1010,0x8000010,0x8001010,0x80010,0x81010,0x8080010,0x8081010);
    pc2bytes13 = new Array (0,0x4,0x100,0x104,0,0x4,0x100,0x104,0x1,0x5,0x101,0x105,0x1,0x5,0x101,0x105);
  
    //how many iterations (1 for des, 3 for triple des)
    var iterations = key.length > 8 ? 3 : 1; 
    //stores the return keys
    var keys = new Array (32 * iterations);
    //now define the left shifts which need to be done
    var shifts = new Array (0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0);
    //other variables
    var lefttemp, righttemp, m=0, n=0, temp;
  
    for (var j=0; j<iterations; j++) { //either 1 or 3 iterations
      left = (key.charCodeAt(m++) << 24) | (key.charCodeAt(m++) << 16) | (key.charCodeAt(m++) << 8) | key.charCodeAt(m++);
      right = (key.charCodeAt(m++) << 24) | (key.charCodeAt(m++) << 16) | (key.charCodeAt(m++) << 8) | key.charCodeAt(m++);
  
      temp = ((left >>> 4) ^ right) & 0x0f0f0f0f; right ^= temp; left ^= (temp << 4);
      temp = ((right >>> -16) ^ left) & 0x0000ffff; left ^= temp; right ^= (temp << -16);
      temp = ((left >>> 2) ^ right) & 0x33333333; right ^= temp; left ^= (temp << 2);
      temp = ((right >>> -16) ^ left) & 0x0000ffff; left ^= temp; right ^= (temp << -16);
      temp = ((left >>> 1) ^ right) & 0x55555555; right ^= temp; left ^= (temp << 1);
      temp = ((right >>> 8) ^ left) & 0x00ff00ff; left ^= temp; right ^= (temp << 8);
      temp = ((left >>> 1) ^ right) & 0x55555555; right ^= temp; left ^= (temp << 1);
  
      //the right side needs to be shifted and to get the last four bits of the left side
      temp = (left << 8) | ((right >>> 20) & 0x000000f0);
      //left needs to be put upside down
      left = (right << 24) | ((right << 8) & 0xff0000) | ((right >>> 8) & 0xff00) | ((right >>> 24) & 0xf0);
      right = temp;
  
      //now go through and perform these shifts on the left and right keys
      for (var i=0; i < shifts.length; i++) {
        //shift the keys either one or two bits to the left
        if (shifts[i]) {left = (left << 2) | (left >>> 26); right = (right << 2) | (right >>> 26);}
        else {left = (left << 1) | (left >>> 27); right = (right << 1) | (right >>> 27);}
        left &= -0xf; right &= -0xf;
  
        //now apply PC-2, in such a way that E is easier when encrypting or decrypting
        //this conversion will look like PC-2 except only the last 6 bits of each byte are used
        //rather than 48 consecutive bits and the order of lines will be according to 
        //how the S selection functions will be applied: S2, S4, S6, S8, S1, S3, S5, S7
        lefttemp = pc2bytes0[left >>> 28] | pc2bytes1[(left >>> 24) & 0xf]
                | pc2bytes2[(left >>> 20) & 0xf] | pc2bytes3[(left >>> 16) & 0xf]
                | pc2bytes4[(left >>> 12) & 0xf] | pc2bytes5[(left >>> 8) & 0xf]
                | pc2bytes6[(left >>> 4) & 0xf];
        righttemp = pc2bytes7[right >>> 28] | pc2bytes8[(right >>> 24) & 0xf]
                  | pc2bytes9[(right >>> 20) & 0xf] | pc2bytes10[(right >>> 16) & 0xf]
                  | pc2bytes11[(right >>> 12) & 0xf] | pc2bytes12[(right >>> 8) & 0xf]
                  | pc2bytes13[(right >>> 4) & 0xf];
        temp = ((righttemp >>> 16) ^ lefttemp) & 0x0000ffff; 
        keys[n++] = lefttemp ^ temp; keys[n++] = righttemp ^ (temp << 16);
      }
    } //for each iterations
    //return the keys we've created
    return keys;
  } //end of des_createKeys
  
  
  
  ////////////////////////////// TEST //////////////////////////////
  function stringToHex (s) {
    var r = "";//这里本来有0x,我去掉了，需要的朋友自己加
    var hexes = new Array ("0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f");
    for (var i=0; i<s.length; i++) {r += hexes [s.charCodeAt(i) >> 4] + hexes [s.charCodeAt(i) & 0xf];}
    return r;
  }
  function a(key, message)
  {
  	for (var i = key.length; i<24; i++) {  
      key+="0";  
      }  
      return des(key, message, 1, 0, 0, 1);  
  }
  /************************影像控件用********************************/
//给URL增加时间戳
  function convertURL(url){
  	  var timstamp = new Date().valueOf();
  	  if (url.indexOf("?")>=0){
  	     url = url + "&t=" + timstamp; 
  	  }else {
  	     url = url + "?t=" + timstamp;
  	  };
  	  return url;
  	};