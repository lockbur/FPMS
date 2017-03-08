package com.forms.prms.tool.dbDate.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DbDateDao {
	/**
	 * 得到系统日期
	 * @param type
	 * @return
	 */
	public String getDate(@Param("type")String type);
	/**
	 * 得到数据库时间
	 * @param type
	 * @return
	 */
	public String getTime(@Param("type")String type);

}
