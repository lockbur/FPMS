
jQuery.extend({
// 校验开始日期和结束日期的
checkDate:function(startDateId,endDateId)
{
	// alert(startDateId+"---"+endDateId);
	var startDateString=$.trim($("#"+startDateId).val());
	var endDateString=$.trim($("#"+endDateId).val());
	/*
	 * if(startDate!="" && endDate!=""){ if(startDate>endDate){
	 * App.notyError("合同约定期限（开始日期）必须在结束时间之前!"); return false; } }
	 */
	var startDate=startDateString.replace(/-/g,'');
	var endDate=endDateString.replace(/-/g,'');
	if(!$.isBlank(startDate) && !$.isBlank(endDate) && startDate>endDate)
	{
		App.notyError("开始日期不能在结束日期之后!");
		return false;
	}else{
		return true;
	}
},
// 控制输入框只能输入最多两位小数的数值（去掉非数字）
clearNoNum : function (obj,isNegative){
	// 响应鼠标事件，允许左右方向键移动
	var event = window.event || event;
    if (event.keyCode == 37 | event.keyCode == 39 || event.keyCode == 9) {
        return;
    }
    if(isNegative){
		obj.value = obj.value.replace(/[^\d.-]/g,""); // 清除"数字"和".","-"以外的字符
		obj.value = obj.value.replace(/\-{2,}/g,"-"); // 只保留第一个. 清除多余的
		obj.value = obj.value.replace("-","$#$").replace(/\-/g,"").replace("$#$","-");
		obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
		// 确保中间不会出现"-"
		if(obj.value.substr(0,obj.value.indexOf("-")).length>0){
			obj.value = obj.value.replace(/-/g,""); 
		}
    }else{
		obj.value = obj.value.replace(/[^\d.]/g,""); // 清除"数字"和"."以外的字符
		obj.value = obj.value.replace(/\.{2,}/g,"."); // 只保留第一个. 清除多余的
		obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
    }
	obj.value = obj.value.replace(/^\./g,""); // 验证第一个字符是数字而不是点
	obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); // 只能输入两个小数
},
onBlurReplace : function (obj){
	var _value = $(obj).val();
    // 最后一位是小数点的话，移除
    // $(obj).val(_value.replace(/\.$/g, ""));
    // 如果第一位是0，且0后面不是小数点
    if(_value){
		$(obj).val(parseFloat(_value));
    }
    if(_value=="-"){
    	$(obj).val(_value.replace(/\-$/g, 0));
    }
},
/*
 * // 创建XMLHTTPRequest对象
 * @param pluginsName
 * @param activexObjectName
 * @returns
 */
createXMLHTTPRequest:function(activexObjectName){
	var xmlhttp; // 创建XMLHTTPRequest对象
	if(window.ActiveXObject){ // 判断是否支持ActiveX控件
		xmlhttp = new ActiveXObject(activexObjectName); // 通过实例化ActiveXObject的一个新实例来创建XMLHTTPRequest对象
	}
	else if(window.XMLHTTPRequest){ // 判断是否把XMLHTTPRequest实现为一个本地javascript对象
		xmlhttp = new XMLHTTPRequest(); // 创建XMLHTTPRequest的一个实例（本地javascript对象）
	}else{
		xmlhttp = null;
	}
	return xmlhttp;
},
/*
 * 用来检测是否安装指定的插件 pluginsName 插件的名称 activexObjectName 控件名称，主要针对于IE author: Jet
 * Mah website: http://www.javatang.com/archives/2006/09/13/442864.html
 */ 
checkPlugins : function (pluginsName, activexObjectName) {
	// alert(pluginsName+","+activexObjectName);
     // 通常ActiveXObject的对象名称是两个插件名称的组合
     if (activexObjectName == '') activexObjectName = pluginsName + "." + pluginsName;
	 if ($.isIE()){
//		 alert('你是使用'+window.browser.name+window.browser.version);
		 try {
			 // 将对象转化为布尔类型
			 var axobj =eval("new ActiveXObject(activexObjectName)");
			 return axobj ? true : false;
		 } catch (e) {
			 alert('系统检测到您未安装封面打印插件，请点击下面的链接下载安装！')
			 return '';
		 }
	 }else{
		 alert('你使用的是'+window.browser.name+window.browser.version+"！此控件只支持IE");
		 return '';
	 }
 },

checkMoney:function(obj)
{// 检查是否满足金额格式 number(18,2)
// var regExp =new RegExp(/^\d{1,16}[\.\d]?\d{0,2}$/);
	var regExp =new RegExp(/^(0|[1-9][0-9]{0,15})(\.[\d]{1,2})?$/);	
	var money = $.trim(obj);
	return (typeof money === "undefined" || money === null || !regExp.test(money)) ? false : true;
},
isMoney:function(obj)
{// 检查是否满足金额格式 number(18,2)
// var regExp =new RegExp(/^\d{1,16}[\.\d]?\d{0,2}$/);
	var regExp =new RegExp(/^(0|[1-9][0-9]{0,15})(\.[\d]+)?$/);	
	var money = $.trim(obj);
	return (typeof money === "undefined" || money === null || !regExp.test(money)) ? false : true;
},
isNull:function(obj)
{// 检查是否为null
	return (typeof obj === "undefined" || obj === null) ? true : false;
},

isBlank:function(obj)
{// 检查是否为空
	return (typeof obj === "undefined" || obj === null || obj.length === 0 || (/^\s+$/.test(obj) === true)) ? true : false;
},

checkRegExp:function(obj, regExp)
{// 检查是否满足正则表达式regExp
	return ((typeof obj != "undefined") && (typeof regExp != "undefined") && regExp.test(obj)) ? true : false;
},

/**
 * 数字精确度计算,参数:数值字符串string,精确度neg
 * 
 * @param string
 * @param neg
 * @return string
 */
numberFormat:function (numValue,neg)
 {
 	numValue=numValue+'';
 	var reVal=Math.round(numValue.replace(/\,/g,'')*1000000000000)/1000000000000;
 	return reVal.toFixed(neg);
 },
 
 /**
	 * 数字精确度计算,参数:数值字符串string,数值字符串string,精确度neg
	 * 
	 * @param string
	 * @param string
	 * @param neg
	 * @return string
	 */
 numberFormatAdd:function (numValue1,numValue2,neg)
 {
 	numValue1=numValue1+'';
 	numValue2=numValue2+'';
 	var reVal=(Math.round(numValue1.replace(/\,/g,'')*1000000000000)+Math.round(numValue2.replace(/\,/g,'')*1000000000000))/1000000000000;
 	return reVal.toFixed(neg);
 },
 /**
  * 金额隔三位给逗号
  */
 angelMoney:function(obj){
	 var money = $(obj).val();
	 if(/[^0-9\.]/.test(money)){
		 return "不是数字！";
	 }
	 money = money.replace(/^(\d*)$/,"$1.");
	 money = (money+"00").replace(/(\d*\.\d\d)\d*/,"$1");
	 money = money.replace(".",",");
	 var re = /(\d)(\d{3},)/;
	 while(re.test(money)){
		 money = money.replace(re,"$1,$2");
		 money = money.replace(/,(\d\d)$/,".$1");
		 money = money.replace(/^\./,"0.");
		 $(obj).val(money);
	 }
 }
});
//浏览器判断
function JudgeBroswer() { 
    if($.browser.msie) { 
        alert("this is msie!"); //IE
    } 
    else if($.browser.safari) 
    { 
        alert("this is safari!"); //Safari 
    } 
    else if($.browser.mozilla) 
    { 
        alert("this is mozilla!");  //Firefox
    } 
    else if($.browser.opera) { 
        alert("this is opera");     //Opera
    } 
}
(function($, window, document,undefined){
	  if(!window.browser){
	      
	    var userAgent = navigator.userAgent.toLowerCase(),uaMatch;
	    window.browser = {}
	      
	    /**
	     * 判断是否为ie
	     */
	    function isIE(){
	      return ("ActiveXObject" in window);
	    }
	    /**
	     * 判断是否为谷歌浏览器
	     */
	    if(!uaMatch){
	      uaMatch = userAgent.match(/chrome\/([\d.]+)/);
	      if(uaMatch!=null){
	        window.browser['name'] = 'chrome';
	        window.browser['version'] = uaMatch[1];
	      }
	    }
	    /**
	     * 判断是否为火狐浏览器
	     */
	    if(!uaMatch){
	      uaMatch = userAgent.match(/firefox\/([\d.]+)/);
	      if(uaMatch!=null){
	        window.browser['name'] = 'firefox';
	        window.browser['version'] = uaMatch[1];
	      }
	    }
	    /**
	     * 判断是否为opera浏览器
	     */
	    if(!uaMatch){
	      uaMatch = userAgent.match(/opera.([\d.]+)/);
	      if(uaMatch!=null){
	        window.browser['name'] = 'opera';
	        window.browser['version'] = uaMatch[1];
	      }
	    }
	    /**
	     * 判断是否为Safari浏览器
	     */
	    if(!uaMatch){
	      uaMatch = userAgent.match(/safari\/([\d.]+)/);
	      if(uaMatch!=null){
	        window.browser['name'] = 'safari';
	        window.browser['version'] = uaMatch[1];
	      }
	    }
	    /**
	     * 最后判断是否为IE
	     */
	    if(!uaMatch){
	      if(userAgent.match(/msie ([\d.]+)/)!=null){
	        uaMatch = userAgent.match(/msie ([\d.]+)/);
	        window.browser['name'] = 'ie';
	        window.browser['version'] = uaMatch[1];
	      }else{
	        /**
	         * IE10
	         */
	        if(isIE() && !!document.attachEvent && (function(){"use strict";return !this;}())){
	          window.browser['name'] = 'ie';
	          window.browser['version'] = '10';
	        }
	        /**
	         * IE11
	         */
	        if(isIE() && !document.attachEvent){
	          window.browser['name'] = 'ie';
	          window.browser['version'] = '11';
	        }
	      }
	    }
	  
	    /**
	     * 注册判断方法
	     */
	    if(!$.isIE){
	      $.extend({
	        isIE:function(){
	          return (window.browser.name == 'ie');
	        }
	      });
	    }
	    if(!$.isChrome){
	      $.extend({
	        isChrome:function(){
	          return (window.browser.name == 'chrome');
	        }
	      });
	    }
	    if(!$.isFirefox){
	      $.extend({
	        isFirefox:function(){
	          return (window.browser.name == 'firefox');
	        }
	      });
	    }
	    if(!$.isOpera){
	      $.extend({
	        isOpera:function(){
	          return (window.browser.name == 'opera');
	        }
	      });
	    }
	    if(!$.isSafari){
	      $.extend({
	        isSafari:function(){
	          return (window.browser.name == 'safari');
	        }
	      });
	    }
	  }
	})(jQuery, window, document);