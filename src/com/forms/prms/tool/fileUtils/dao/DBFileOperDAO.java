package com.forms.prms.tool.fileUtils.dao;



import org.springframework.stereotype.Repository;

import com.forms.prms.tool.fileUtils.domain.DBFileBean;


@Repository
public interface DBFileOperDAO
{
	
	public  void  addFile(DBFileBean  dbFileBean);
	
	public  DBFileBean  findFile(String  fileName);

	public  void  deleteFile(String  fileName);
	
}
