package com.forms.prms.tool.fms.montAprv.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.fms.montAprv.dao.MontAprvDao;

@Service
public class MontAprvService {
	public static MontAprvService getInstance() {
		return SpringUtil.getBean(MontAprvService.class);
	}

	@Autowired
	private MontAprvDao dao;
	/**
	 * 转移监控指标和审批链
	 */
	public void montAndAprvTransfer(){
		CommonLogger.info("查看是否有需要转移的数据");
		List<String> list = dao.getNoTransfer();
		if (null != list && list.size() > 0 ) {
			//有需要转移的
			for (int j = 0; j < list.size(); j++) {
				String batchNo = list.get(j);
				try {
					dao.transfer(batchNo);
				} catch (Exception e) {
					e.printStackTrace();
					CommonLogger.error("转移监控指标审批链跨年数据失败,批次号为"+batchNo);
				}		
				
			}
			
		}
	}


	/**
	 * 在合同审核，合同新增和预算的地方 要用到这个进行转移数据 type 类型
	 * type  00-所有类型（合同查询列表），01-专项报+省行统购(审批链省行维护列表),02-分行审批链维护列表 ， 11-专项包 ，12-省行统购，21-非省行统购资产 22-非专项包费用
	 * org21Code type 为01,02的时候是一级行 03的时候是二级航或机构 校验 当年
	 * 这个类型的这个机构的审批链是否在正式表里，如果不在且在FUT表里就触发转移，如果在就直接执行 结果类型 无需转移，正在转移，转移成功，没数据
	 * position 是当不为空 11 的时候是 用归口部门来查（合同复核）
	 * isReturn 当未维护的时候是继续下去还是不让继续了 1-继续 0-中断
	 */
	public Map<String, Object> transferData(String type, String org21Code,String position,String isReturn) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = true;
		String errorMsg = "";
		String returnMsg="";
//		if ("11".equals(position)) {
//			//以物料归口部门维度，目前适用于合同审核
//			String resultFutType = dao.getCountPoFut(WebHelp.getLoginUser().getDutyCode());
//			if (!Tool.CHECK.isBlank(resultFutType)) {
//				//触发转移
//				flag = false;
//				errorMsg =alertMsg("该归口的审批链正在发生转移，部分合同或者物料信息没有显示出来，请稍后再尝试") ;
//				returnMsg = "该归口的审批链正在发生转移，部分合同或者物料信息没有显示出来，请稍后再尝试";
//				goTransfer(resultFutType, org21Code);
//			}else {
//				//这个归口部门的审批链还没有制定 手动制定
//				flag = true;
//			}
//		}else{
//			//判断审批链是否是在正式表
//			String count = dao.isHaveImportData(type,org21Code);
//			
//			String resultType = dao.getCountInTable(type, org21Code);
//			if (Tool.CHECK.isBlank(resultType)) {
//				//正式表里没有
//				String resultFutType = dao.getCountInFut(type, org21Code);
//				if (Tool.CHECK.isBlank(resultFutType)) {
//					//FUT表里也没有 则 
//					if (!Tool.CHECK.isBlank(isReturn)&&"1".equals(isReturn)) {
//						flag = true;
//					}else {
//						flag = false;
//					}
//					
//					errorMsg =alertMsg("当年该类型的审批链没有制定") ;
//					returnMsg = "当年该类型的审批链没有制定";
//				}else {
//					//FUT表里存在 则触发转移
//					flag = false;
//					errorMsg =alertMsg("正在转移该类型的数据,部分数据会查询不出来，请稍后再尝试") ;
//					returnMsg = "正在转移该类型的数据,部分数据会查询不出来，请稍后再尝试";
//					goTransfer(resultFutType, org21Code);
//				}
//			}else {
//				//正式表里有
//				String resultFutType = dao.getCountInFut(type, org21Code);
//				if (Tool.CHECK.isBlank(resultFutType)) {
//					//如果FUT表里没有
//					flag = true;
//				}else {
//					//触发转移
//					flag = false;
//					errorMsg =alertMsg("正在转移数据,部分数据会查询不出来，请稍后再尝试") ;
//					returnMsg = "正在转移数据,部分数据会查询不出来，请稍后再尝试";
//					goTransfer(resultFutType, org21Code);
//				}
//			}
//		}
		
		map.put("flag", flag);
		map.put("msg", errorMsg);
		map.put("returnMsg",returnMsg );
		return map;

	}
private void goTransfer(String resultFutType,String org21Code){
	String status = dao.getStatusTransfer();
	if (Tool.CHECK.isBlank(status)) {
		//没有转移中的
		try {
			// 修改状态
			//当type=00的时候 可能返回的状态是11也可能是12有可能是21 所以就还得判断一下 
			if (!"21".equals(resultFutType)) {
				org21Code = WebHelp.getLoginUser().getOrg1Code();
			}else {
				org21Code = WebHelp.getLoginUser().getOrg2Code();
			}
			dao.updateStatus(resultFutType, org21Code, "06","11");
			ChainThread thread = new ChainThread();
			thread.chainStop = false;
			thread.setType(resultFutType);
			thread.setOrg21Code(org21Code);
			new Thread(thread).start();
		} catch (Exception e) {
			dao.updateStatus(resultFutType, org21Code, "11","13");
			e.printStackTrace();
		}
	}
}
private String alertMsg(String errorMsg){
	String string = "$( '<div>"+errorMsg+"</div>' ).dialog({";
	string+="resizable: false,";
	string+="height:200,";
	string+="width:400,";
	string+="modal: true,";
	string+="dialogClass: 'dClass',";
	string+="buttons: {";
	string+="'关闭': function() {";
	string+="$( this ).dialog( 'close' );";
	string+="},";
	string+="}";
	string+= "});";
	return string;
}
	public void chainTransferExecute() throws Exception {
		// dao.transferAprv();
//		throw new Exception("dfdfdf");
	}

	public void updateStatus(String resultFutType, String org21Code, String statusOld,String statusNew) {
		dao.updateStatus(resultFutType, org21Code, statusOld,statusNew);
		
	}
}
