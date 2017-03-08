function viewPayScanImg(payId, icmsPkuuid){
	//alert("pay");
	var url=$publicSystemPath$+"/common/pay/scan/view.do?id=" 
 	+ payId + "&icmsPkuuid=" + icmsPkuuid;
	window.open(
			url,
			"查看付款扫描文件",
			""
	);
}
function viewCntScanImg(cntNum, icmsPkuuid){
	//alert("contract");
	var url=$publicSystemPath$+"/common/contract/scan/view.do?id=" 
	+ cntNum + "&icmsPkuuid=" + icmsPkuuid;
	window.open(
			url,
			"查看合同扫描文件",
			""
	);
}

function viewOrder(cntNum){
	var url=$publicSystemPath$+"/contract/query/queryOrder.do?"+
	 App.FUNC_KEY+"=03020605&cntNum=" + cntNum;
	$.dialog.open(
			url,
			{
				width: "75%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"订单列表",
			    id:"dialogCutPage",
				close: function(){
				}
			}
		 );
}

function queryFeeTypePage(cntNum){
	var url=$publicSystemPath$+"/contract/query/queryFeeTypePage.do?"+
	App.FUNC_KEY+"=03020603&cntNum="+cntNum;
	$.dialog.open(
			url,
			{
				width: "75%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"费用类型查看",
			    id:"dialogCutPage",
				close: function(){
				}
			}
		 );
}

function gotoCntDtl(cntNum)
{
	var url=$publicSystemPath$+"/contract/query/gotoCntDtl.do?"+App.FUNC_KEY+"=03020609&cntNum=" + cntNum;
	$.dialog.open(
		url,
		{
			width: "90%",
			height: "95%",
			lock: true,
		    fixed: true,
		    title:"合同明细",
		    id:"dialogCutPage",
			close: function(){
				
			}		
		}
	 );
	
}

