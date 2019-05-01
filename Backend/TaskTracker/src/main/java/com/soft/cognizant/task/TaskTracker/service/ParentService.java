package com.soft.cognizant.task.TaskTracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soft.cognizant.task.TaskTracker.entity.ParentTask;
import com.soft.cognizant.task.TaskTracker.jdbc.ParentDAO;

@Service
public class ParentService {
	
	@Autowired
	ParentDAO parentDAO;

	public List<ParentTask> getParentTasks(){
		return parentDAO.getParentTasks();
	}

	public ParentTask getParentTask(int id) {
		return parentDAO.getParentTask(id);
	}

	public boolean createParentTask(ParentTask parentTask) {
		return parentDAO.createParentTask(parentTask);
	}	

}
