package com.forms.prms.web.sysmanagement.waterbook.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.waterbook.domain.WaterBook;

@Repository
public interface IWaterBookDAO {

	int insertWB(WaterBook waterBook);

	List<WaterBook> getWBList(String busNum);

	WaterBook getWBDtl(String wbNum);
}
