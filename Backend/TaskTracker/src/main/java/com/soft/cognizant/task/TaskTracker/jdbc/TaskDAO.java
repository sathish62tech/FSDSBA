package com.soft.cognizant.task.TaskTracker.jdbc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.soft.cognizant.task.TaskTracker.entity.Task;


@Repository
public class TaskDAO {

	private static final Logger logger = LogManager.getLogger(TaskDAO.class);
	
	EntityManager entityManager;
	
	@Autowired
	public TaskDAO(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	@Transactional
	public boolean insertTask(Task task) {
		logger.info("insertTask <<" + task);
		task.setStatus("NEW");
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.save(task);
		logger.info("insertTask >>");
		return true;
	}
	
	public Task getTask(int id) {
		logger.info("getTask <<" + id);
		Session currentSession = entityManager.unwrap(Session.class);
		Query<Task> query = 
				currentSession.createQuery("from Task where taskId=:id",Task.class);
		query.setParameter("id", id);
		Task existingTask = query.getSingleResult();
		logger.info("getTask >>" + existingTask);
		return existingTask;
	}

	public List<Task> getTaskList(int id) {
		logger.info("getTaskList <<" + id);
		Session currentSession = entityManager.unwrap(Session.class);
		Query<Task> query = 
				currentSession.createQuery("from Task where project_id=:id",Task.class);
		query.setParameter("id", id);
		List<Task> taskList=query.getResultList();
		logger.info("getTaskList >>" + taskList);
		return taskList;
	}


	@Transactional
	public boolean deleteTask(int id) {
		logger.info("deleteTask <<" + id);
		Session currentSession = entityManager.unwrap(Session.class);
		Task task = null;
		task = getTask(id);
		currentSession.delete(task);
		logger.info("deleteTask >>");
		return true;
	}

	@Transactional
	public boolean updateTask(Task task) {
		logger.info("updateTask <<" + task);
		Session currentSession = entityManager.unwrap(Session.class);
		task.setStatus("NEW");
		currentSession.update(task);
		logger.info("updateTask >>");
		return true;
	}
	

	public List<Task> getCompletedTasks(int projectId) {
		logger.info("getCompletedTasks <<" + projectId);
		Session currentSession = entityManager.unwrap(Session.class);
		String completedInd = "COMPL";
		Query<Task> query = 
				currentSession.createQuery("from Task where projectId=:id and status=:complInd",Task.class);
		query.setParameter("id", projectId);
		query.setParameter("complInd", completedInd);
		List<Task> taskList=query.getResultList();
		logger.info("getCompletedTasks >>" + taskList);
		return taskList;
		
	}


	public List<Task> getProjectTasks(int projectId) {
		logger.info("getProjectTasks <<" + projectId);
		Session currentSession = entityManager.unwrap(Session.class);
		Query<Task> query = 
				currentSession.createQuery("from Task where projectId=:id",Task.class);
		query.setParameter("id", projectId);
		List<Task> taskList=query.getResultList();
		logger.info("getProjectTasks >>" + taskList);
		return taskList;
	}



	public Task getTaskIdbyParentNProject(String datatohelp) {
		logger.info("getTaskIdbyParentNProject <<" + datatohelp);
		Session currentSession = entityManager.unwrap(Session.class);
		String tasksplit[]= datatohelp.split("-");
		Query<Task> query = 
				currentSession.createQuery("from Task where projectId=:projectid "
						+ "and parentId=:parentid "
						+ "and task=:taskName",Task.class);
		query.setParameter("projectid", Integer.parseInt(tasksplit[0]));
		query.setParameter("parentid", Integer.parseInt(tasksplit[1]));
		query.setParameter("taskName", tasksplit[2]);
		Task taskres = query.getSingleResult();
		logger.info("getTaskIdbyParentNProject >>" + taskres);
		return taskres;
	}

	@Transactional
	public void updateProjectsdelete(int projectId) {
		logger.info("updateProjectsdelete <<" + projectId);
		Session currentSession = entityManager.unwrap(Session.class);
		Query<?> query = 
				currentSession.createQuery("update Task set project_id = 0 where project_id=:id");
		query.setParameter("id", projectId);
		query.executeUpdate();
		logger.info("updateProjectsdelete >>");
	}

	@Transactional
	public boolean completeTask(int id) {
		logger.info("completeTask <<" + id);
		Session currentSession = entityManager.unwrap(Session.class);
		Task task = getTask(id);
		task.setStatus("COMPL");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String currDat = dateFormat.format(date);
		try {
			date = dateFormat.parse(currDat);
		} catch (ParseException e) {
			logger.error(e);
		}
		task.setEndDate(date);
		currentSession.update(task);
		logger.info("completeTask >>");
		return true;
	}

}
