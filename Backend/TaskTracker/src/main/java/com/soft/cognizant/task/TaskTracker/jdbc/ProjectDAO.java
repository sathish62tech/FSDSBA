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

import com.soft.cognizant.task.TaskTracker.entity.Project;

@Repository
public class ProjectDAO {

	private static final Logger logger = LogManager.getLogger(ProjectDAO.class);

	EntityManager entityManager;

	@Autowired
	public ProjectDAO(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Autowired
	TaskDAO taskDAO;

	@Autowired
	UserDAO userDAO;

	public Project createProject(Project project) {
		logger.info("createProject <<" + project);
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.save(project);
		logger.info("createProject <<");
		return project;
	}

	public Project getProject(int projectId) {
		logger.info("getProject <<" + projectId);
		Session currentSession = entityManager.unwrap(Session.class);
		Query<Project> query = currentSession.createQuery("from Project where projectId=:id", Project.class);
		query.setParameter("id", projectId);
		Project existingProject = query.getSingleResult();
		logger.info("getProject <<" + existingProject);
		return existingProject;
	}

	public List<Project> getProjectList() {
		logger.info("getProjectList <<");
		Session currentSession = entityManager.unwrap(Session.class);
		Query<Project> query = currentSession.createQuery("from Project", Project.class);
		List<Project> projectList = query.getResultList();
		logger.info("getProjectList >>" + projectList);
		return projectList;
	}

	@Transactional
	public boolean deleteProject(int projectId) {
		logger.info("deleteProject <<" + projectId);
		Session currentSession = entityManager.unwrap(Session.class);
		Project existingProject = getProject(projectId);
		taskDAO.updateProjectsdelete(projectId);
		userDAO.updateUsersdelete(projectId);
		currentSession.delete(existingProject);
		logger.info("deleteProject >>");
		return true;
	}

	@Transactional
	public boolean updateProject(Project project) {
		logger.info("updateProject <<" + project);
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.update(project);
		logger.info("updateProject >>");
		return true;
	}

	public Project getProjectByPName(String projectName) {
		logger.info("getProjectByPName <<" + projectName);
		Session currentSession = entityManager.unwrap(Session.class);
		Query<Project> query = currentSession.createQuery("from Project where project=:name", Project.class);
		query.setParameter("name", projectName);
		Project existingProject = query.getSingleResult();
		logger.info("getProjectByPName >>" + existingProject);
		return existingProject;
	}
}
