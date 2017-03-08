package com.forms.prms.tool.fms.receive.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.EncryptUtil;
import com.forms.prms.tool.InitPwd;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.constantValues.FMSTradeType;
import com.forms.prms.tool.constantValues.FmsValues;
import com.forms.prms.tool.fms.parse.service.FMSService;
import com.forms.prms.tool.fms.receive.domain.ReceiveDao;
import com.forms.prms.web.sysmanagement.org.domain.BaseBean;
import com.forms.prms.web.sysmanagement.org.service.OrgService;
import com.forms.prms.web.user.domain.User;

@Service
public class ReceiveService {

	@Autowired
	private ReceiveDao dao;
	@Autowired
	private FMSService fmsService;
	@Autowired
	private OrgService orgService;

	/**
	 * fms文件回复并解析完成后反向更新
	 * 
	 * 2(结果文件)、21-AP发票信息 22-AP付款信息 23-GL待摊预提信息 25-采购订单信息 26-科目余额 3(校验文件)、31-应付发票
	 * 32-预付款核销 33-预提待摊 34-采购订单
	 * 
	 * @param tradeType
	 * @param batchNo
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void dealFmsFile(String tradeType, String batchNo) throws Exception {
		CommonLogger.info("fms文件回复并解析完成后反向更新!,ReceiveService,dealFmsFile()【tradeType:"+tradeType+";batchNo:"+batchNo+";】");
		if (FMSTradeType.TRADE_TYPE_11.equals(tradeType)) {
			dao.dealFms11(batchNo);
			fmsService.updateStatus(batchNo,FmsValues.FMS_DOWNLOAD_SUCC);
			/*//查询出密码为空的用户名 然后循环update
			List<User> list = dao.selectInitUser();
			if (null !=list && list.size()>0) {
				for (User user : list) {
					String userId = user.getUserId();
					String password = new EncryptUtil(userId).encryptNumStr(InitPwd.pwd);
					dao.updateUserPwd(userId,password);
				}
			}*/
		}else if (FMSTradeType.TRADE_TYPE_12.equals(tradeType)) {
			dao.dealFms12(batchNo);
			fmsService.updateStatus(batchNo,FmsValues.FMS_DOWNLOAD_SUCC);
			//清除机构树缓存
			orgService.removeCachedZtreeTag(new BaseBean());
		}else if (FMSTradeType.TRADE_TYPE_13.equals(tradeType)) {
			dao.dealFms13(batchNo);
			fmsService.updateStatus(batchNo,FmsValues.FMS_DOWNLOAD_SUCC);
		}else if (FMSTradeType.TRADE_TYPE_21.equals(tradeType)) {
			dao.dealFms21(batchNo);
			fmsService.updateStatus(batchNo, FmsValues.FMS_DOWNLOAD_SUCC);
		} else if (FMSTradeType.TRADE_TYPE_22.equals(tradeType)) {
			dao.dealFms22(batchNo);
			fmsService.updateStatus(batchNo, FmsValues.FMS_DOWNLOAD_SUCC);
		} else if (FMSTradeType.TRADE_TYPE_23.equals(tradeType)) {
			fmsService.updateStatus(batchNo, FmsValues.FMS_DOWNLOAD_SUCC);
			dao.dealFms23(batchNo);
		} else if (FMSTradeType.TRADE_TYPE_25.equals(tradeType)) {
			fmsService.updateStatus(batchNo, FmsValues.FMS_DOWNLOAD_SUCC);
			String instOper=WebHelp.getLoginUser().getUserId();
			dao.dealFms25(batchNo,instOper);
		} else if (FMSTradeType.TRADE_TYPE_26.equals(tradeType)) {
			fmsService.updateStatus(batchNo, FmsValues.FMS_DOWNLOAD_SUCC);
		} else if (FMSTradeType.TRADE_TYPE_31.equals(tradeType)) {
			dao.dealFms31(batchNo);
		} else if (FMSTradeType.TRADE_TYPE_32.equals(tradeType)) {
			dao.dealFms32(batchNo);
		} else if (FMSTradeType.TRADE_TYPE_33.equals(tradeType)) {
			dao.dealFms33(batchNo);
		} else if (FMSTradeType.TRADE_TYPE_34.equals(tradeType)) {
			String instOper=WebHelp.getLoginUser().getUserId();
			dao.dealFms34(batchNo,instOper);

		} else {
			CommonLogger.info("交易类型有误,没有该交易类型"+tradeType+"ReceiveService,dealFmsFile");
			throw new Exception("交易类型有误" + tradeType);
		}
	}
}
