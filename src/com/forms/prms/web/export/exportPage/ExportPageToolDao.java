package com.forms.prms.web.export.exportPage;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface ExportPageToolDao {
	public int getCount(SqlAdapter sql);

	public List<Map<String, Object>> getResult(SqlAdapter sqlAdapter);

}
