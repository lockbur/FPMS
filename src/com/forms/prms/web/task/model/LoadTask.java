package com.forms.prms.web.task.model;


public class LoadTask
  extends AbstratTask
{

  private String type;

  private String fileName;

  private boolean isRequired;

  private boolean isUnZip;

  private String loadCmd;

  public String getType()
  {
    return type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public String getFileName()
  {
    return fileName;
  }

  public void setFileName(String fileName)
  {
    this.fileName = fileName;
  }

  public boolean isRequired()
  {
    return isRequired;
  }

  public void setRequired(boolean isRequired)
  {
    this.isRequired = isRequired;
  }

  public boolean isUnZip()
  {
    return isUnZip;
  }

  public void setUnZip(boolean isUnZip)
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
  @Override
  public TaskType getTaskType()
  {
    return TaskType.valueOf(type);
  }

}
