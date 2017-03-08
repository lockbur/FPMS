package com.forms.prms.web.task.model;


public abstract class AbstratTask {

    private String taskId;

    private String hostAddr;

    private int    port;

    private String uesrName;

    private String password;

    private String ftpFileName;
    private String workDir;//下载的文件在本地保存地址
    
    
    public String getWorkDir() {
		return workDir;
	}

	public void setWorkDir(String workDir) {
		this.workDir = workDir;
	}

	public String getHostAddr() {
	return hostAddr;
    }

    public void setHostAddr(String hostAddr) {
	this.hostAddr = hostAddr;
    }

    public int getPort() {
	return port;
    }

    public void setPort(int port) {
	this.port = port;
    }

    public String getUesrName() {
	return uesrName;
    }

    public void setUesrName(String uesrName) {
	this.uesrName = uesrName;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getFtpFileName() {
	return ftpFileName;
    }

    public void setFtpFileName(String ftpFileName) {
	this.ftpFileName = ftpFileName;
    }

    public String getTaskId() {
	return taskId;
    }

    public void setTaskId(String taskId) {
	this.taskId = taskId;
    }
    public abstract TaskType getTaskType();

}
