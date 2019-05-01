package com.capsule.TaskTracker.jdbc;

import java.util.List;

import javax.sql.DataSource;

import com.capsule.TaskTracker.entity.Task;

public interface TaskDAO {

	boolean insertTask(Task task);
	Task getTask(int id);
	List<Task> getTaskList();
	boolean deleteTask(int id);
	boolean updateTask(Task task);
	boolean updateFlipTask(Task task, boolean flip);

}
