package com.forms.prms.web.task.model;

public class TaskBean
{

  private String taskId;

  private String hostAddr;

  private int port;

  private String uesrName;

  private String password;

  private String ftpFileName;

  private String fileName;

  private boolean isRequired;

  private boolean isUnZip;

  private String loadCmd;

  private String tableName;

  public String getTaskId()
  {
    return taskId;
  }

  public void setTaskId(String taskId)
  {
    this.taskId = taskId;
  }

  public String getHostAddr()
  {
    return hostAddr;
  }

  public void setHostAddr(String hostAddr)
  {
    this.hostAddr = hostAddr;
  }

  public String getUesrName()
  {
    return uesrName;
  }

  public void setUesrName(String uesrName)
  {
    this.uesrName = uesrName;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public String getFtpFileName()
  {
    return ftpFileName;
  }

  public void setFtpFileName(String ftpFileName)
  {
    this.ftpFileName = ftpFileName;
  }

  public boolean isRequired()
  {
    return isRequired;
  }

  public void setRequired(boolean isRequired)
  {
    this.isRequired = isRequired;
  }

  public boolean getIsUnZip()
  {
    return isUnZip;
  }

  public void setIsUnZip(boolean isUnZip)
  {
    this.isUnZip = isUnZip;
  }

  public String getLoadCmd()
  {
    return loadCmd;
  }

  public void setLoadCmd(String loadCmd)
  {
    this.loadCmd = loadCmd;
  }

  public int getPort()
  {
    return port;
  }

  public void setPort(int port)
  {
    this.port = port;
  }

  public String getTableName()
  {
    return tableName;
  }

  public void setTableName(String tableName)
  {
    this.tableName = tableName;
  }

  public String getFileName()
  {
    return fileName;
  }

  public void setFileName(String fileName)
  {
    this.fileName = fileName;
  }

}
