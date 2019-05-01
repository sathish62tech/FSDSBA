package com.soft.cognizant.task.TaskTracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soft.cognizant.task.TaskTracker.entity.Task;
import com.soft.cognizant.task.TaskTracker.jdbc.TaskDAO;

@Service
public class TaskTrackerService {
		
	@Autowired
	TaskDAO taskDAO;

	public List<Task> getTasks(int id){
		return taskDAO.getTaskList(id);
	}
	public boolean createTask(Task Task) {
		return taskDAO.insertTask(Task);
	}
	public Task getTask(int id) {
		return taskDAO.getTask(id);
	}
	public boolean deleteTask(int id) {
		return taskDAO.deleteTask(id);
	}
	public boolean updateTask(Task task) {
		return taskDAO.updateTask(task);
	}
	public Task getTaskIdbyParentNProject(String datatohelp) {
		return taskDAO.getTaskIdbyParentNProject(datatohelp);
	}
	public boolean completeTask(int id) {
		return taskDAO.completeTask(id);
	}
}
