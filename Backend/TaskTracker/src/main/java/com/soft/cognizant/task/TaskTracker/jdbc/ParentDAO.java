package com.soft.cognizant.task.TaskTracker.jdbc;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.soft.cognizant.task.TaskTracker.entity.ParentTask;

@Repository
public class ParentDAO {

	private static final Logger logger = LogManager.getLogger(ParentDAO.class);

	EntityManager entityManager;

	@Autowired
	public ParentDAO(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	public List<ParentTask> getParentTasks() {
		logger.info("getParentTasks <<");
		Session currentSession = entityManager.unwrap(Session.class);

		Query<ParentTask> query = currentSession.createQuery("from ParentTask", ParentTask.class);

		List<ParentTask> pList = query.getResultList();
		logger.info("getParentTasks >>" + pList);

		return pList;
	}

	public ParentTask getParentTask(int id) {
		logger.info("getParentTask <<" + id);
		Session currentSession = entityManager.unwrap(Session.class);

		Query<ParentTask> query = currentSession.createQuery("from ParentTask where parentId=:id", ParentTask.class);
		query.setParameter("id", id);
		ParentTask pTask = query.getSingleResult();
		logger.info("getParentTask >>");
		return pTask;
	}

	@Transactional
	public boolean createParentTask(ParentTask parentTask) {
		logger.info("createParentTask <<");
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.save(parentTask);
		logger.info("createParentTask >>");
		return true;
	}

}
