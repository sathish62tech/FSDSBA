package com.capsule.TaskTracker.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capsule.TaskTracker.entity.ParentTask;
import com.capsule.TaskTracker.entity.Task;
import com.capsule.TaskTracker.jdbc.ParentDao;
import com.capsule.TaskTracker.jdbc.TaskDAO;
//import com.tasktracker.jdbc.TaskDao;

@Repository
public class TaskRepository {
	

	@Autowired
	private TaskDAO taskDao;
	
	@Autowired
	private ParentDao parentDao;
	
	public List<Task> getTaskList() {
		System.out.println("TaskListRepo");
		return taskDao.getTaskList();
	}

	public boolean createTask(Task Task) {
		return taskDao.insertTask(Task);
	}

	public Task getTask(int TaskId) {
		return taskDao.getTask(TaskId);
	}

	public boolean deleteTask(int TaskId) {
		return taskDao.deleteTask(TaskId);
	}

	public boolean updateTask(Task task) {
		return taskDao.updateTask(task);
	}
	
	public boolean updateFlipTask(Task task) {
		boolean flip = true;
		return taskDao.updateFlipTask(task,flip);
	}
	/*public static void main(String[] args) {
		TaskRepository pr = new TaskRepository();
		System.out.println(pr.getTaskDb());
	}*/

	public boolean addParentTask(ParentTask parentTask) {
		// TODO Auto-generated method stub
		return parentDao.insertParent(parentTask);
	}
}
