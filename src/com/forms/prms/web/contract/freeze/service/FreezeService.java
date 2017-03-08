package com.forms.prms.web.contract.freeze.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.constantValues.BusTypes;
import com.forms.prms.tool.constantValues.OperateValues;
import com.forms.prms.web.contract.freeze.dao.FreezeDAO;
import com.forms.prms.web.contract.freeze.domain.Contract;
import com.forms.prms.web.sysmanagement.waterbook.service.WaterBookService;

@Service
public class FreezeService {

	@Autowired
	private FreezeDAO fDao;

	@Autowired
	private WaterBookService wService;

	/**
	 * 可冻结列表
	 * 
	 * @param c
	 * @return
	 */
	public List<Contract> freezeList(Contract c) {
		CommonLogger.info("查看可以冻结的合同列表，FreezeService，freezeList");
		FreezeDAO pageDao = PageUtils.getPageDao(fDao);
		return pageDao.freezeList(c);
	}

	/**
	 * 可解冻列表
	 * 
	 * @param c
	 * @return
	 */
	public List<Contract> unfreezeList(Contract c) {
		CommonLogger.info("查看可解冻的合同列表，FreezeService，unfreezeList");
		FreezeDAO pageDao = PageUtils.getPageDao(fDao);
		return pageDao.unfreezeList(c);
	}

	/**
	 * 冻结
	 * 
	 * @param cntNum
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int freeze(Contract c) {
		CommonLogger.info("合同冻结（合同号："+c.getCntNum()+"），FreezeService，freeze");
		int i = fDao.freeze(c.getCntNum());
		if (i > 0) {
			// 增加流水
			wService.insert(c.getCntNum(), BusTypes.CONTRACT, OperateValues.FREEZE, c.getWaterMemo(),"20","35");
		}
		return i;
	}

	/**
	 * 解冻
	 * 
	 * @param cntNum
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int unfreeze(Contract c) {
		CommonLogger.info("合同解冻（合同号："+c.getCntNum()+"），FreezeService，unfreeze");
		int i = fDao.unfreeze(c.getCntNum());
		if (i > 0) {
			// 增加流水
			wService.insert(c.getCntNum(), BusTypes.CONTRACT, OperateValues.UNFREEZE, c.getWaterMemo(),"35","20");
		}
		return i;
	}

}
