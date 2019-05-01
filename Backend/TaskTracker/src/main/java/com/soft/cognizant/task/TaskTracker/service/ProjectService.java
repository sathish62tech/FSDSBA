package com.soft.cognizant.task.TaskTracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soft.cognizant.task.TaskTracker.entity.Project;
import com.soft.cognizant.task.TaskTracker.entity.Task;
import com.soft.cognizant.task.TaskTracker.jdbc.ProjectDAO;
import com.soft.cognizant.task.TaskTracker.jdbc.TaskDAO;

@Service
public class ProjectService {
	
	
	@Autowired
	ProjectDAO projectDAO;
	
	@Autowired
	TaskDAO taskDAO;
	
	public List<Project> getProjects(){
		return projectDAO.getProjectList();
	}
	// create User
	public Project createProject(Project project) {
		return projectDAO.createProject(project);
	}
	
	public Project getProject(int id) {
		return projectDAO.getProject(id);
	}
	
	public boolean deleteProject(int id) {
		return projectDAO.deleteProject(id);
	}
	
	public boolean updateProject(Project project) {
		return projectDAO.updateProject(project);
	}
	public List<Task> getCompletedTasks(int projectId) {
		return taskDAO.getCompletedTasks(projectId);
	}
	public List<Task> getProjectTasks(int projectId) {
		return taskDAO.getProjectTasks(projectId);
	}
	public Project getProjectByPName(String projectName) {
		return projectDAO.getProjectByPName(projectName);
	}
	

}
