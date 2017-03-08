package com.forms.prms.tool.dbDate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.prms.tool.dbDate.dao.DbDateDao;

@Service
public class DbDateService {
	@Autowired
	private DbDateDao dbDateDao;
	/**
	 * 得到日期
	 * @param type
	 * @return
	 */
	public String getDate(String type){
		return dbDateDao.getDate(type);
	}
	/**
	 * 得到时间
	 * @param type
	 * @return
	 */
	public String getTime(String type){
		return dbDateDao.getTime(type);
	}

}
