package com.forms.prms.web.task.taskManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.prms.tool.fms.parse.service.FMS2ERP;

@Service
public class TaskManageService {

	/**
	 * 本类唯一实例
	 */
	private static TaskManageService instance = null;

	
    @Autowired
    private FMS2ERP pFms2erp;
	
	public static TaskManageService getInstance() {
		return SpringUtil.getBean(TaskManageService.class);
	}
	
	 

}
