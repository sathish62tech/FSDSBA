package com.soft.cognizant.task.TaskTracker.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="task")
public class Task {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="task_id")
	private int taskId;
	
	
	@Column(name="parent_id")
	@JsonProperty("parentTaskId")
	private int parentId; 
	
	@Column(name="project_id")
	@JsonProperty("projectId")
	private int projectId; 
	
	@Column(name="task")
	@JsonProperty("task")
	private String task;
	
	@Column(name="start_date")
	@JsonProperty("startDate")
	private Date startDate;
	
	@Column(name="end_date")
	@JsonProperty("endDate")
	private Date endDate;
	
	@Column(name="priority")
	@JsonProperty("priority")
	private int priority;

	@Column(name="status")
	@JsonProperty("status")
	private String status;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Task(int taskId, int parentId, int projectId, String task, Date startDate, Date endDate, int priority,
			String status) {
		super();
		this.taskId = taskId;
		this.parentId = parentId;
		this.projectId = projectId;
		this.task = task;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priority = priority;
		this.status = status;
	}

	public Task() {
		super();
	}

	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", parentId=" + parentId + ", projectId=" + projectId + ", task=" + task
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", priority=" + priority + ", status=" + status
				+ "]";
	}
}
