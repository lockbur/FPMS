package com.forms.prms.web.sysmanagement.matrtype.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.matrtype.domain.MatrType;

@Repository
public interface MatrTypeDAO {
	/**
	 * 列表
	 * 
	 * @param mt
	 * @return
	 */
	public List<MatrType> list(MatrType mt);
	
	/**
	 * 物料核算码列表
	 * 
	 * @param mt
	 * @return
	 */
	public List<MatrType> listCglCode(MatrType mt);

	
	public void addPrepaidCode(MatrType mt);
	public void updatePrepaidCode(MatrType mt);
	/**
	 * 明细
	 * 
	 * @param matrCode
	 * @return
	 */
	public MatrType view(String matrCode);

	/**
	 * 物料选择Option列表
	 * 
	 * @param m
	 * @return
	 */
	public List<MatrType> matrTypeOption(MatrType m);

	/**
	 * 物料新增
	 * 
	 * @param m
	 * 
	 */

	
	public void insertMatr(MatrType m);//添加物料信息至数据库

	public MatrType propertyList(String cglCode);
	
	public int checkCglCode(String cglCode);

	/**
	 * 增加一条核算码记录
	 * 
	 * @param m
	 */
	public void addCglCode(MatrType m);
	
	public void importMt(@Param("matrTypes")List<MatrType> matrTypes);

	public List cglList();	

}
