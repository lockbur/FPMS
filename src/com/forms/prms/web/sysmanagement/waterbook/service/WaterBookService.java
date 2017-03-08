package com.forms.prms.web.sysmanagement.waterbook.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.constantValues.BusTypes;
import com.forms.prms.tool.constantValues.OperateValues;
import com.forms.prms.web.sysmanagement.waterbook.dao.IWaterBookDAO;
import com.forms.prms.web.sysmanagement.waterbook.domain.WaterBook;

@Service
public class WaterBookService {

	@Autowired
	private IWaterBookDAO dao;

	/**
	 * 新增流水
	 * 
	 * @param WaterBook
	 * @throws Exception
	 */
	public int insert(WaterBook wb) {
		wb.setOperator(WebHelp.getLoginUser().getUserId());
		return dao.insertWB(wb);
	}

	
	/**
	 * 
	 * @param busNum
	 * 			业务主键，如合同号、付款单号
	 * @param busType
	 * 			业务类型，如何项目管理、合同管理
	 * @param operate
	 * 			操作类型，如新增、修改或删除
	 * @param log
	 * 			日志，如业务转发意见
	 * @param oldDataFlag
	 *          旧数据状态，如合同正常状态为'20'.新增操作时没有旧数据状态
	 * @param newDataFlag
	 *          新数据状态.删除操作时没有新数据状态
	 * @return
	 */
	public int insert(String busNum, BusTypes busType, OperateValues operate, String log, String oldDataFlag, String newDataFlag) {
		CommonLogger.info("新增合同号为"+busNum+"的交易流水信息");
		WaterBook wb = new WaterBook();
		wb.setBusNum(busNum);
		wb.setBusType(busType.toString());
		wb.setBusMenu(busType.getMenu());
		wb.setOperateType(operate.toString());
		wb.setOperateLog(log);
		wb.setOperator(WebHelp.getLoginUser().getUserId());
		wb.setOldDataFlag(oldDataFlag);
		wb.setNewDataFlag(newDataFlag);
		return dao.insertWB(wb);
	}

	/**
	 * 根据业务号获取流水列表
	 * 
	 * @param busNum
	 * @return List<WaterBook>
	 */
	public List<WaterBook> getWBList(String busNum) {
		List<WaterBook> wbList = null;
		IWaterBookDAO pageDao = PageUtils.getPageDao(dao);
		if (StringUtils.isNotBlank(busNum)) {
			wbList = pageDao.getWBList(busNum);
		}
		return wbList;
	}

	/**
	 * 根据流水号获取流水信息
	 * 
	 * @param wbNum
	 * @return WaterBook
	 */
	public WaterBook getWBDtl(String wbNum) {
		WaterBook wbInfo = dao.getWBDtl(wbNum);
		return wbInfo;
	}

}
