package com.forms.prms.web.export.exportPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.net.aso.l;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.platform.excel.exports.ExcelExportUtility;
import com.forms.platform.excel.exports.SimplifyBatchExcelExporter;
import com.forms.prms.web.sysmanagement.montAprvBatch.export.domain.ExportBean;
import com.forms.prms.web.sysmanagement.montAprvBatch.export.service.ExportService;
@Service
public class ExportPageToolService {
	@Autowired
	private ExportPageToolDao dao;
	
	public static ExportPageToolService getInstance(){
		return SpringUtil.getBean(ExportPageToolService.class);
	}
	
	public void execute(String sql , SimplifyBatchExcelExporter excelExporter) throws Exception{
		Map<String, Object> beansMap = new HashMap<String, Object>();
		SqlAdapter sqlAdapter = new SqlAdapter();
		String sql2 = sql;
		sql2 = "SELECT COUNT(1) FROM ("+sql+")";
		sqlAdapter.setSql(sql2);
		int count= dao.getCount(sqlAdapter);
		int pageCount =10000;
		int pageSize = count%pageCount == 0 ?count/pageCount :count/pageCount + 1;
		//需要分页
		String sql3 = "";
		int beginCount = 0;
		int endCount = 0;
		List<Map<String, Object>> list = null;
		for (int i = 1; i <= pageSize; i++) {
			list = new ArrayList<Map<String,Object>>();
			beginCount = (i-1)*pageCount;
			endCount = i*pageCount;
			sql3="";
			sql3 += "select * from                                                 ";
			sql3 += "   (select a.*,rownum row_num from                            ";
			sql3 += "      ("+sql+") a ";
			sql3 += "   ) b                                                        ";
			sql3 += "where b.row_num between "+beginCount+" and  "+endCount;
			sqlAdapter.setSql(sql3);
			list = dao.getResult(sqlAdapter);
			beansMap.put("0", list);
			ExcelExportUtility.loadExcelData(beansMap, excelExporter);
		}
	}

}
