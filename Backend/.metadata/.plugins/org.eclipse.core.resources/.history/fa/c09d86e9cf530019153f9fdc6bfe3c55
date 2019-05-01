package com.capsule.TaskTracker.jdbc;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.capsule.TaskTracker.entity.Project;
import com.capsule.TaskTracker.entity.Task;
import com.capsule.TaskTracker.entity.User;

@Repository
public class ProjectDAO {
	
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
		Session currentSession = entityManager.unwrap(Session.class);
//		System.out.println("inserting project...........");
//		System.out.println("project model" + project);
		
		currentSession.save(project);
//		System.out.println("After insert"+ project);
		
		return project;
	}

	public Project getProject(int projectId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
//		System.out.println(projectId);
//		System.out.println("get project dta");
		
		Query<Project> query = 
				currentSession.createQuery("from Project where projectId=:id",Project.class);
		query.setParameter("id", projectId);
//		System.out.println("Query" + query);
		
		Project existingProject = query.getSingleResult();
		
//		System.out.println(existingProject);
		return existingProject;
	}

	public List<Project> getProjectList() {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Project> query = 
				currentSession.createQuery("from Project",Project.class);
		
		List<Project> projectList = query.getResultList();
//		System.out.println(projectList);
		
		return projectList;
	}
	
	@Transactional
	public boolean deleteProject(int projectId) {
		// TODO Auto-generated method stub
//		System.out.println("deleting...................................");
		
		Session currentSession = entityManager.unwrap(Session.class);
		Project existingProject = getProject(projectId);
//		System.out.println("Updating tasks...................................");
		taskDAO.updateProjectsdelete(projectId);
//		System.out.println("Updating Users...................................");
		userDAO.updateUsersdelete(projectId);
//		System.out.println("delete project...................................");
		currentSession.delete(existingProject);
		return true;
	}

	@Transactional
	public boolean updateProject(Project project) {
		Session currentSession = entityManager.unwrap(Session.class);
//		System.out.println("entring project");
		System.out.println(project);
		
//		User existingUser = getUser(user.getUserId());
//		
//		System.out.println("this is our old user" + existingUser);
//		existingUser.setFirstName(user.getFirstName());
//		existingUser.setLastName(user.getLastName());
//		existingUser.setEmployeeId(user.getEmployeeId());
//		
//		System.out.println("after editing " + existingUser);
//		
//		System.out.println("updating user.........");
//		
//		currentSession.save(existingUser);
//		currentSession.update(existingUser);
		currentSession.update(project);
//		currentSession.close();
//		currentSession.flush();
//		User diditupdtUser = getUser(user.getUserId());
//		
//		System.out.println("this is updated user" + diditupdtUser);
		return true;
	}


	public Project getProjectByPName(String projectName) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		System.out.println(projectName);
//		System.out.println("get project dta");
		
		Query<Project> query = 
				currentSession.createQuery("from Project where project=:name",Project.class);
		query.setParameter("name", projectName);
//		System.out.println("Query" + query);
		
		Project existingProject = query.getSingleResult();
		
//		System.out.println("get project by name" + existingProject);
		return existingProject;
	}


	
}
