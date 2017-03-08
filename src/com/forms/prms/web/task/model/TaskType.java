package com.forms.prms.web.task.model;

public enum TaskType {

  /**
   * FMS 和ERP 系统信息
   */
	FMS_ERP_SYS("0", "FMS_ERP_SYS", "从FMS传过来的用户数据");

  private final String type;

  private final String name;

  private final String desc;

  private TaskType(String type, String name, String desc)
  {
    this.type = type;
    this.name = name;
    this.desc = desc;
  }

  public String getType()
  {
    return this.type;
  }

  public String getName()
  {
    return this.name;
  }

  public String getDesc()
  {
    return desc;
  }

  public boolean equals(String type)
  {
    return this.type.equals(type);
  }

  @Override
  public String toString()
  {
    return this.getName();
  }

}
