package com.forms.prms.web.contract.confirmchg.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.contract.confirmchg.domain.ConfirmChgContract;
import com.forms.prms.web.contract.modify.domain.ModifyContract;


@Repository
public interface IContractConfirmChgDAO {
	/** 
	 * @methodName confirmChgList
	 * desc  根据查詢條件获取确认变更合同列表
	 * 
	 * @param map 合同查询条件对象con及所属一级行机构号
	 */
	public List<ConfirmChgContract> confirmChgList(Map<String, Object> map);

	/** 
	 * @methodName getDetail
	 * desc  获取合同明细内容
	 * 
	 * @param cntNum 合同编号
	 */
	public ConfirmChgContract getDetail(String cntNum);

	/** 
	 * @methodName updCnt
	 * desc  修改合同主表信息
	 * 
	 * @param cnt 合同修改信息
	 */
	public int updCnt(ModifyContract cnt);
	
	/**
	 * @methodName insertCntDeviceHis
	 * desc  合同变更时不修改物料信息，插入合同物料到历史记录中
	 * 
	 * @param bean
	 * @return
	 */
	public int insertCntDeviceHis(ModifyContract bean);
	public ConfirmChgContract getInfo(String cntNum);
	
	/**
	 * 
	 * @param param
	 */
	public void calCntChange(Map<String, String> param);

	public ConfirmChgContract getTotalPayAmt(@Param("cntNum")String cntNum, @Param("subId")String subId);

	public void ruduceProjPayAmt(@Param("projId")String projId, @Param("payAmt")BigDecimal payAmt);

	public void addProjPayAmt(@Param("projId")String projId, @Param("payAmt")BigDecimal payAmt);
}
