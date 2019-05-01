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

import com.soft.cognizant.task.TaskTracker.entity.User;

@Repository
public class UserDAO {

	private static final Logger logger = LogManager.getLogger(UserDAO.class);

	EntityManager entityManager;

	@Autowired
	public UserDAO(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	public boolean insertUser(User user) {
		logger.info("insertTask <<" + user);
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.save(user);
		logger.info("insertTask >>");
		return true;
	}

	public User getUser(int userId) {
		logger.info("getUser <<" + userId);
		Session currentSession = entityManager.unwrap(Session.class);
		Query<User> query = currentSession.createQuery("from User where userId=:id", User.class);
		query.setParameter("id", userId);
		User existingUser = query.getSingleResult();
		logger.info("getUser >>");
		return existingUser;
	}

	public List<User> getUserList() {
		logger.info("getUserList <<");
		Session currentSession = entityManager.unwrap(Session.class);
		Query<User> query = currentSession.createQuery("from User", User.class);
		List<User> userList = query.getResultList();
		logger.info("getUserList >>");
		return userList;
	}

	@Transactional
	public boolean deleteUser(int userId) {
		logger.info("deleteUser <<" + userId);
		Session currentSession = entityManager.unwrap(Session.class);
		User existingUser = getUser(userId);
		currentSession.delete(existingUser);
		logger.info("deleteUser >>");
		return true;
	}

	@Transactional
	public boolean updateUser(User user) {
		logger.info("updateUser <<" + user);
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.update(user);
		currentSession.close();
		logger.info("updateUser >>");
		return true;
	}

	public User getUserbyProjectId(int id) {
		logger.info("getUserbyProjectId <<" + id);
		Session currentSession = entityManager.unwrap(Session.class);
		Query<User> query = currentSession.createQuery("from User where projectId=:id", User.class);
		query.setParameter("id", id);
		User existingUser = query.getSingleResult();
		logger.info("getUserbyProjectId >>");
		return existingUser;
	}

	public void updateUsersdelete(int projectId) {
		logger.info("updateUsersdelete <<" + projectId);
		Session currentSession = entityManager.unwrap(Session.class);
		Query<?> query = currentSession.createQuery("update User set project_id = 0 where project_id=:id");
		query.setParameter("id", projectId);
		query.executeUpdate();
		logger.info("updateUsersdelete >>");
	}

}
