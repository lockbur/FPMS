package com.forms.prms.web.tag.service;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.util.Tool;
import com.forms.prms.web.tag.dao.TagCommonDao;
import com.forms.prms.web.tag.domain.TagCommon;

/**
 * author : wuqm <br>
 * date : 2013-11-4<br>
 * 
 */
@Service
public class TagCommonService {
	@Autowired
	private TagCommonDao tagCommonDao;
	
//	@Cacheable(value="base",condition="#tagCommon.tableName == 'SYS_SELECT' or #tagCommon.tableName == 'TB_SPECIAL' or #tagCommon.tableName == 'TB_REFERENCE'")
//	public List<Map> getOptionList(TagCommon tagCommon)
//	{
//	if(!Tool.CHECK.isEmpty(tagCommon.getTableName())
//			&&!Tool.CHECK.isEmpty(tagCommon.getSelectColumn())){
//		return tagCommonDao.getOptionList(tagCommon);
//	}else{
//		return null;
//	}
//}
	public List<Map> getOptionList(TagCommon tagCommon) throws UnknownHostException
	{
		if(!Tool.CHECK.isEmpty(tagCommon.getTableName())
				&&!Tool.CHECK.isEmpty(tagCommon.getSelectColumn())){
			return tagCommonDao.getOptionList(tagCommon);
		}else {
			return null;
		}
			
	}
	
//	@CacheEvict(value="base",condition="#tagCommon.tableName == 'TB_SPECIAL' or #tagCommon.tableName == 'TB_REFERENCE'")
//	public void removeSelectList(TagCommon tagCommon)
//	{
//	CommonLogger.info("更新参考专项基础表后，清除参考专项缓存成功");
//}
	public void removeSelectList(TagCommon tagCommon)
	{
//		if (null != SystemCommonService.dataList) {
//			for (int i = 0; i < SystemCommonService.dataList.size(); i++) {
//				if (tagCommon.getTableName().equals(SystemCommonService.dataList.get(i).getTableName())) {
//					SystemCommonService.dataList.remove(i);
//				}
//			}
//		}
//		CommonLogger.info("更新参考专项基础表后，清除参考专项缓存成功");
	}

	public List<Map> getList(Map<String,String> mapTo){
		if(!Tool.CHECK.isEmpty(mapTo.get("tableName"))
				&&!Tool.CHECK.isEmpty(mapTo.get("selectColumn"))){
			return tagCommonDao.getList(mapTo);
		}else{
			return null;
		}
	}
}
