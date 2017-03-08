package com.forms.prms.web.pay.orderquery.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.prms.tool.ImportUtil;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.exportthread.ExcelExportGenernalDeal;
import com.forms.prms.web.pay.orderquery.dao.OrderQueryDao;
import com.forms.prms.web.pay.orderquery.domain.OrderQueryBean;

@Service
public class OrderQueryService {
	@Autowired
	private OrderQueryDao dao;
	@Autowired
	private ExcelExportGenernalDeal exportDeal;	
	
	//获得类实例
	public static OrderQueryService getInstance(){
		return SpringUtil.getBean(OrderQueryService.class);
	}

	/**
	 * 查询补录完成集合
	 * 
	 * @param bean
	 * @return
	 */
	public List<OrderQueryBean> getList(OrderQueryBean bean) {
		CommonLogger.info("查询采购部门为本省或者创建订单机构为本责任中心的所有订单，OrderQueryService，getList");
		String org1Code = WebHelp.getLoginUser().getOrg1Code();// 得到登录人所在一级行
		String org2Code = WebHelp.getLoginUser().getOrg2Code();// 得到登录人所在二级行
		String instDutyCode=WebHelp.getLoginUser().getDutyCode();//得到登录人所在责任中心
		bean.setInstDutyCode(instDutyCode);
		bean.setOrg1Code(org1Code);
		bean.setOrg2Code(org2Code);
		OrderQueryDao pageDao = PageUtils.getPageDao(dao);
		return pageDao.getList(bean);
	}

	/**
	 * 通过订单编号查找所有信息
	 * 
	 * @param orderId
	 * @return
	 */
	public OrderQueryBean getInfo(String orderId) {
		CommonLogger.info("查询订单号为" + orderId + "的详细信息，OrderQueryService，getInfo");
		return dao.getInfo(orderId);
	}

	public String exportData(OrderQueryBean orderQueryBean, String flag) throws Exception {
		String sourceFileName = "";
		if ("1".equals(flag)) {
			sourceFileName = "订单汇总数据导出";
		}else{
			sourceFileName = "订单明细数据导出";
		}
		
		ImportUtil.createFilePath(new File(WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")));
		String destFile = WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")+"/"+sourceFileName+".xlsx";;
		//导出Excel操作
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("orderId", orderQueryBean.getOrderId());
		map.put("cntName", orderQueryBean.getCntName());
		map.put("cntNum", orderQueryBean.getCntNum());
		map.put("orderDutyCode", orderQueryBean.getOrderDutyCode());
		map.put("chkUser", orderQueryBean.getChkUser());
		
		map.put("dataFlag", orderQueryBean.getDataFlag());
		map.put("matrCode", orderQueryBean.getMatrCode());
		map.put("matrName", orderQueryBean.getMatrName());
		map.put("cglCode", orderQueryBean.getCglCode());
		map.put("providerName", orderQueryBean.getProviderName());
		map.put("poNumber", orderQueryBean.getPoNumber());
		map.put("projName", orderQueryBean.getProjName());
		
		String org1Code = WebHelp.getLoginUser().getOrg1Code();// 得到登录人所在一级行
		String org2Code = WebHelp.getLoginUser().getOrg2Code();// 得到登录人所在二级行
		String instDutyCode=WebHelp.getLoginUser().getDutyCode();//得到登录人所在责任中心
		
		map.put("instDutyCode", instDutyCode);
		map.put("org1Code", org1Code);
		map.put("org2Code", org2Code);
		map.put("orgFlag", orderQueryBean.getOrgFlag());
		map.put("flag", flag);
		if ("1".equals(flag)) {
			return exportDeal.execute(sourceFileName, "ORDER_EXPORT", destFile , map);
		}else {
			return exportDeal.execute(sourceFileName, "ORDER_EXPORT_DETAIL", destFile , map);
		}
		
	}

	public List<OrderQueryBean> exportExcute(OrderQueryBean bean) {
		if ("1".equals(bean.getFlag())) {
			return dao.getList(bean);
		}else {
			return dao.getListDetail(bean);
		}
		
	}

}
