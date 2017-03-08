package com.forms.prms.web.cleanpaydeal.cleanpay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.cleanpaydeal.cleanpay.dao.CleanPayDAO;
import com.forms.prms.web.cleanpaydeal.cleanpay.domain.CleanPayBean;

/**
 * 
 * author : lisj <br>
 * date : 2015-03-12<br>
 * 暂收结清service
 */
@Service
public class CleanPayService {
	@Autowired
	private CleanPayDAO cleanPayDAO;

	/**
	 * 查询可做暂收结清的列表
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	public List<CleanPayBean> cleanpayList(CleanPayBean cleanPayBean) {
		CommonLogger.info("查询可做暂收结清的列表，CleanPayService，cleanpayList");
		cleanPayBean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());// 得到登录人所在的一级行
		cleanPayBean.setInstDutyCode(WebHelp.getLoginUser().getDutyCode());
		CleanPayDAO pageDao = PageUtils.getPageDao(cleanPayDAO);
		return pageDao.cleanpayList(cleanPayBean);
	}
	/**
	 * 查询当前新增的子序号
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	public CleanPayBean QuerySortId(CleanPayBean cleanPayBean) {
		CommonLogger.info("查询当前新增的子序号，CleanPayService，QuerySortId");
		return cleanPayDAO.QuerySortId(cleanPayBean);
	}

	/**
	 * 查询合同信息
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	public CleanPayBean constractInfo(CleanPayBean cleanPayBean) {
		CommonLogger.info("查询合同号"+cleanPayBean.getCntNum()+"的合同信息，CleanPayService，constractInfo");
		return cleanPayDAO.constractInfo(cleanPayBean);
	}

	/**
	 * 查询付款信息
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	public CleanPayBean queryPayInfo(CleanPayBean cleanPayBean) {
		CommonLogger.info("查询付款单号"+cleanPayBean.getNormalPayId()+"的付款信息，CleanPayService，queryPayInfo");
		return cleanPayDAO.queryPayInfo(cleanPayBean);
	}

	/**
	 * 查询正在结清的总金额
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	public String queryCleanAmtIng(CleanPayBean cleanPayBean) {
		CommonLogger.info("查询付款单号"+cleanPayBean.getNormalPayId()+"的正在结清信息，CleanPayService，queryCleanAmtIng");
		return cleanPayDAO.queryCleanAmtIng(cleanPayBean);
	}

	/**
	 * 生成暂收结清编号
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	public String createCleanPayId(CleanPayBean cleanPayBean) {
		CommonLogger.info("生成暂收结清编号，CleanPayService，createCleanPayId");
		return cleanPayDAO.createCleanPayId(cleanPayBean);
	}

	/**
	 * 查询已结清列表
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	public List<CleanPayBean> queryCleanedPayInfo(CleanPayBean cleanPayBean) {
		CommonLogger.info("查询付款单号"+cleanPayBean.getNormalPayId()+"的已结清信息，CleanPayService，queryCleanedPayInfo");
		return cleanPayDAO.queryCleanedPayInfo(cleanPayBean);
	}

	/**
	 * 暂收结清处理信息保存
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	public boolean payCleanDealSave(CleanPayBean cleanPayBean) {
		CommonLogger.info("新增暂收结清信息（付款单："+cleanPayBean.getNormalPayId()+",子序号："+cleanPayBean.getSortId()+"），CleanPayService，payCleanDealSave");
		cleanPayBean.setDataFlag("00");// 录入
		/*//拿到当前付款单下的暂收信息
		List<CleanPayBean> lists = cleanPayDAO.getClenPayById(cleanPayBean.getNormalPayId());
		if(Tool.CHECK.isEmpty(lists)){
			cleanPayBean.setUncleanAmt(cleanPayBean.getSuspenseAmt());
		}else{
			CleanPayBean cp = lists.get(0);
			cleanPayBean.setUncleanAmt(cp.getUncleanAmt().subtract(cp.getCleanAmt()));
		}*/
		return cleanPayDAO.payCleanDealSaveOrSubmit(cleanPayBean) > 0 ? true : false;
	}

	/**
	 * 暂收结清处理信息提交
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	public boolean payCleanDealSubmit(CleanPayBean cleanPayBean) {
		CommonLogger.info("新增暂收结清信息（付款单："+cleanPayBean.getNormalPayId()+",子序号："+cleanPayBean.getSortId()+"），CleanPayService，payCleanDealSubmit");
		cleanPayBean.setDataFlag("02");
		//拿到当前付款单下的暂收信息
//		List<CleanPayBean> lists = cleanPayDAO.getClenPayById(cleanPayBean.getNormalPayId());
//		if(Tool.CHECK.isEmpty(lists)){
//			cleanPayBean.setUncleanAmt(cleanPayBean.getSuspenseAmt());
//		}else{
//			CleanPayBean cp = lists.get(0);
//			cleanPayBean.setUncleanAmt(cp.getUncleanAmt().subtract(cp.getCleanAmt()));
//		}
		return cleanPayDAO.payCleanDealSaveOrSubmit(cleanPayBean) > 0 ? true : false;
	}

	/**
	 * 已结清明细查询
	 * 
	 * @param cleanPayBean
	 * @return
	 */
	public CleanPayBean queryCleanedDetail(CleanPayBean cleanPayBean) {
		CommonLogger.info("查询暂收已结清明细信息（付款单："+cleanPayBean.getNormalPayId()+",子序号："+cleanPayBean.getSortId()+"），CleanPayService，queryCleanedDetail");
		return cleanPayDAO.queryCleanedDetail(cleanPayBean);
	}

}
