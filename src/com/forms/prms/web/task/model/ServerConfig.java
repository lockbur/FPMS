package com.forms.prms.web.task.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServerConfig {

    private ServerConfig() {

    }

    private static DbBean	      dbBean;

    private static List<LoadTask>      loadTasks;

    private static List<UpLoadTask>    uploadTasks;

    private static Map<String, String> replacedParams;

    public static DbBean getDbBean() {
	return dbBean;
    }

    public static void setDbBean(DbBean _dbBean) {
	dbBean = _dbBean;
    }

    public static List<LoadTask> getLoadTasks(TaskType type) {
	ArrayList<LoadTask> rtn = new ArrayList<LoadTask>();
	for (LoadTask task : loadTasks) {
	    if (task.getTaskType() == type) {
		rtn.add(task);
	    }
	}
	return rtn;
    }

    public static List<UpLoadTask> getUploadTasks(TaskType type) {
	ArrayList<UpLoadTask> rtn = new ArrayList<UpLoadTask>();
	for (UpLoadTask task : uploadTasks) {
	    if (task.getTaskType() == type) {
		rtn.add(task);
	    }
	}
	return rtn;
    }

    public static void setLoadTasks(List<LoadTask> _tasks) {
	loadTasks = _tasks;
    }

    public static void setUploadTasks(List<UpLoadTask> _tasks) {
	uploadTasks = _tasks;
    }

    public static Map<String, String> getReplacedParams() {
	return replacedParams;
    }

    public static void setReplacedParams(Map<String, String> replacedParams) {
	ServerConfig.replacedParams = replacedParams;
    }
}
