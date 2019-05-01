package com.capsule.TaskTracker.jdbc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.annotations.Parent;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.capsule.TaskTracker.entity.ParentTask;
import com.capsule.TaskTracker.entity.Task;


@Repository
public class TaskDAO {

	EntityManager entityManager;
	
	@Autowired
	public TaskDAO(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	
	@Transactional
	public boolean insertTask(Task task) {
//		System.out.println("inserting task " + task);
		task.setStatus("NEW");
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.save(task);
//		System.out.println(task);
		return true;
	}
	
//	@Transactional
//	public ParentTask inserParentTask(Task task) {
////		System.out.println("inserting Parent...............");
//		Session currentSession = entityManager.unwrap(Session.class);
//
////		Set the parent task name -- id is auto inc
//		ParentTask pTask = new ParentTask(task.getParentId(),task.getTask());
//
////		save it
//		currentSession.save(pTask);
////		System.out.println("parent id created  " + pTask);
//		
//		return pTask;
//	}
//

	public Task getTask(int id) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Task> query = 
				currentSession.createQuery("from Task where taskId=:id",Task.class);
		query.setParameter("id", id);
		
		Task existingTask = query.getSingleResult();
		
		return existingTask;
	}


	public List<Task> getTaskList(int id) {
//		System.out.println("Hibernate");
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Task> query = 
				currentSession.createQuery("from Task where project_id=:id",Task.class);
		query.setParameter("id", id);
//		System.out.println("Query" + query);
		List<Task> taskList=query.getResultList();
		return taskList;
	}


	@Transactional
	public boolean deleteTask(int id) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		Task task = null;
		task = getTask(id);
//		System.out.println("deleting task.........." + task);
		currentSession.delete(task);
		return true;
	}

	@Transactional
	public boolean updateTask(Task task) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
//		System.out.println("updating task.........." + task);
		task.setStatus("NEW");
		currentSession.update(task);
//		System.out.println("updated!");
		return true;
	}
	

	public List<Task> getCompletedTasks(int projectId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String completedInd = "COMPL";
//		System.out.println("Session" + currentSession);
		Query<Task> query = 
				currentSession.createQuery("from Task where projectId=:id and status=:complInd",Task.class);
		query.setParameter("id", projectId);
		query.setParameter("complInd", completedInd);
		List<Task> taskList=query.getResultList();
		return taskList;
		
	}


	public List<Task> getProjectTasks(int projectId) {
		Session currentSession = entityManager.unwrap(Session.class);
//		System.out.println("Session" + currentSession);
		Query<Task> query = 
				currentSession.createQuery("from Task where projectId=:id",Task.class);
		query.setParameter("id", projectId);
		List<Task> taskList=query.getResultList();
		System.out.println(taskList);
		return taskList;
	}



	public Task getTaskIdbyParentNProject(String datatohelp) {
		Session currentSession = entityManager.unwrap(Session.class);
		System.out.println("Session" + datatohelp);
		String tasksplit[]= datatohelp.split("-");
		Query<Task> query = 
				currentSession.createQuery("from Task where projectId=:projectid "
						+ "and parentId=:parentid "
						+ "and task=:taskName",Task.class);
		query.setParameter("projectid", Integer.parseInt(tasksplit[0]));
		query.setParameter("parentid", Integer.parseInt(tasksplit[1]));
		query.setParameter("taskName", tasksplit[2]);
		Task taskres = query.getSingleResult();
		
		System.out.println(taskres);
		return taskres;
	}

	@Transactional
	public void updateProjectsdelete(int projectId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
//		System.out.println("updating task on delete for project " + projectId);
		Query query = 
				currentSession.createQuery("update Task set project_id = 0 where project_id=:id");
		query.setParameter("id", projectId);
		int result = query.executeUpdate();
	}

	@Transactional
	public boolean completeTask(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
//		System.out.println("completeing tasks...................");
		Task task = getTask(id);
		//update status 
		task.setStatus("COMPL");
		
		//update date
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		String currDat = dateFormat.format(date);
		try {
			date = dateFormat.parse(currDat);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("final date " + date);
		task.setEndDate(date);
		System.out.println("aaaaannnnnsdddd completed " + date);
		currentSession.update(task);
		return true;
	}

}
