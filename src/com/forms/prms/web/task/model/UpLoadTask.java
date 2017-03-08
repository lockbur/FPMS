package com.forms.prms.web.task.model;


public class UpLoadTask extends AbstratTask {

    private String type;

    @Override
    public TaskType getTaskType() {
	return TaskType.valueOf(type);
    }

    public void setType(String type) {
	this.type = type;
    }

}
