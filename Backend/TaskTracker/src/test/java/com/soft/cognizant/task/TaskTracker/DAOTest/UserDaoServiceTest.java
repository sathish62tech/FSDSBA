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

import com.soft.cognizant.task.TaskTracker.entity.User;
import com.soft.cognizant.task.TaskTracker.jdbc.ProjectDAO;
import com.soft.cognizant.task.TaskTracker.jdbc.TaskDAO;
import com.soft.cognizant.task.TaskTracker.jdbc.UserDAO;
import com.soft.cognizant.task.TaskTracker.service.ProjectService;
import com.soft.cognizant.task.TaskTracker.service.TaskTrackerService;
import com.soft.cognizant.task.TaskTracker.service.UserService;

@RunWith(SpringRunner.class)
public class UserDaoServiceTest {
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
	
	@TestConfiguration
    static class UserServiceImplTestContextConfiguration {
  
        @Bean
        public UserService userService() {
            return new UserService();
        }
    }
	
	@Autowired
	UserService userService;
	
	@MockBean
    private UserDAO userDao;
	
	@Before
	public void setUp() {
		User user = new User(1,"abc","def",1234,1, 1);
		
	 
	    Mockito.when(userDao.getUser(user.getUserId())).thenReturn(user);
	    Mockito.when(userDao.insertUser(user)).thenReturn(true);
	    Mockito.when(userDao.updateUser(user)).thenReturn(true);
	    Mockito.when(userDao.deleteUser(user.getUserId())).thenReturn(true);
	    Mockito.when(userDao.getUserbyProjectId(user.getProjectId())).thenReturn(user);
	    
	}
	
	@Test
	public void whenValidName_thenUserShouldBeFound() {
	    int id = 1;
	    User userfind = userService.getUser(id);
	  
	    assertTrue("mock and test are equal", userfind.getUserId()==id);
	 }
	
	@Test
	public void whenUserInserted() {
		User user = new User(1,"abc","def",1234,1, 1);
		assertNotNull(userService.createUser(user));
		
	 }
	
	@Test
	public void whenUpdateProject() {
		User user = new User(1,"abc","def",1234,1, 1);
		assertNotNull(userService.updateUser(user));
	 }
	
	@Test
	public void whendeleteUser() {
		User user = new User(1,"abc","def",1234,1, 1);
		System.out.println(user);
		assertNotNull(userService.deleteUser(user.getUserId()));
	 }
	
	@Test
	public void whengetUserByPName() {
		
		User user = new User(1,"abc","def",1234,1, 1);
		User userFetched = userService.getUserByProjectId(user.getProjectId());
	  
	    assertTrue("See if insert is success", userFetched.getUserId()==user.getUserId());
	 }
}
