package com.soft.cognizant.task.TaskTracker.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="parent_task")
public class ParentTask {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int parentId;
	
	@Column(name="parent_task")
	@JsonProperty("parentTaskName")
	private String parentTask;
	

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getParentTask() {
		return parentTask;
	}

	public void setParentTask(String parentTask) {
		this.parentTask = parentTask;
	}

	public ParentTask(String parentTask) {
		super();
		this.parentTask = parentTask;
	}
	public ParentTask(int parentId,String parentTask) {
		super();
		this.parentId = parentId;
		this.parentTask = parentTask;
	}
    
	
	public ParentTask() {
		super();
	}

	@Override
	public String toString() {
		return "ParentTask [parentId=" + parentId + ", parentTask=" + parentTask +"]";
	}
	

}
