package com.soft.cognizant.task.TaskTracker.DAOTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.soft.cognizant.task.TaskTracker.entity.Project;
import com.soft.cognizant.task.TaskTracker.jdbc.ProjectDAO;
import com.soft.cognizant.task.TaskTracker.jdbc.TaskDAO;
import com.soft.cognizant.task.TaskTracker.service.ProjectService;
import com.soft.cognizant.task.TaskTracker.service.TaskTrackerService;



@RunWith(SpringRunner.class)
public class ProjectDaoServiceTest {

	@TestConfiguration
    static class ProjectServiceImplTestContextConfiguration {
  
        @Bean
        public ProjectService projectService() {
            return new ProjectService();
        }
    }
	
	@Autowired
	ProjectService projectService;
	
	@MockBean
    private ProjectDAO projectDao;
	
	@TestConfiguration
    static class TaskServiceImplTestContextConfiguration {
  
        @Bean
        public TaskTrackerService taskService() {
            return new TaskTrackerService();
        }
    }
	
	@Autowired
	TaskTrackerService taskService;
	
	@MockBean
    private TaskDAO taskDao;
	
	@Before
	public void setUp() {
		Project project = new Project(123,"TIM",null,null,12);
		
	 
	    Mockito.when(projectDao.getProject(project.getProjectId())).thenReturn(project);
	    Mockito.when(projectDao.createProject(project)).thenReturn(project);
	    Mockito.when(projectDao.updateProject(project)).thenReturn(true);
	    Mockito.when(projectDao.deleteProject(project.getProjectId())).thenReturn(true);
	    Mockito.when(projectDao.getProjectByPName(project.getProject())).thenReturn(project); 
	    
	}
	
	@Test
	public void whenValidName_thenProjectShouldBeFound() {
	    int id = 123;
	    Project project = projectService.getProject(id);
	  
	    assertTrue("mock and test are equal", project.getProjectId()==id);
	 }
	
//	@Test
//	public void whenProjectInserted() {
//		Project project = new Project(123,"TIM",null,null,12);
//		assertNotNull(projectService.createProject(project));
//		
//	 }
	
	@Test
	public void whenUpdateProject() {
		Project project = new Project(123,"TIM",null,null,12);
		
	    assertNotNull(projectService.updateProject(project));
	 }
	
	@Test
	public void whendeleteProject() {
	    assertNotNull(projectService.deleteProject(123));
	 }
	
	@Test
	public void whengetProjectByPName() {
		String pname="TIM";
				
		Project project = projectService.getProjectByPName(pname);
	  
	    assertTrue("See if insert is success", project.getProject()==pname);
	 }
	
}
