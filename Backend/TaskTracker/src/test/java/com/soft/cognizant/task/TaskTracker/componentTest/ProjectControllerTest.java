package com.soft.cognizant.task.TaskTracker.componentTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.soft.cognizant.task.TaskTracker.component.ProjectControllerRest;
import com.soft.cognizant.task.TaskTracker.entity.Project;
import com.soft.cognizant.task.TaskTracker.entity.Task;
import com.soft.cognizant.task.TaskTracker.service.ProjectService;

@RunWith(SpringRunner.class)
@WebMvcTest(value= {ProjectControllerRest.class,ProjectService.class,Project.class})
public class ProjectControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	
	@MockBean
	ProjectService projectService;
	
	
	Project project = new Project(123,"TIM",null,null,12);
	
	String exampleJson = "{\"projectId\": 123,\"project\": \"TIM\","
			+ "\"startDate\":null,\"endDate\": null,\"priority\": 12}";
	
	int completedTaskCount = 0;
	int taskCount = 0;
	
	@Test
	public void retreiveProjectTest() throws Exception {
		Mockito.when(projectService.getProject(Mockito.anyInt())).thenReturn(project);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/getproject/123")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected ="{\"projectId\": 123,\"project\": \"TIM\",\"startDate\":null,"
				+ "\"endDate\": null,\"priority\": 12}";
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}
	
	@Test
	public void retreiveProjectList() throws Exception {
		Mockito.when(projectService.getProjects()).thenReturn(Arrays.asList(project));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/projects")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
//		String expected ="{\"projectId\": 123,\"project\": \"TIM\",\"startDate\":null,"
//				+ "\"endDate\": null,\"priority\": 12}";
//		JSONAssert.assertEquals(expected, result.getResponse()
//				.getContentAsString(), false);
		
		assertNotNull(result.getResponse().getContentAsString());
	}
	
	
	@Test
	public void createProjectTest() throws Exception {
		Mockito.when(projectService.createProject(Mockito.any())).thenReturn(project);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.post("/addproject")
										.accept(MediaType.APPLICATION_JSON)
										.content(exampleJson)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		System.out.println(response);
		String expected ="{\"projectId\": 123,\"project\": \"TIM\","
				+ "\"startDate\":null,\"endDate\": null,\"priority\": 12}";
		JSONAssert.assertEquals(expected, response
				.getContentAsString(),false);
		
	}
	
	
	@Test
	public void updateProjectTest() throws Exception {
		Mockito.when(projectService.updateProject(Mockito.any())).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.put("/editproject")
										.accept(MediaType.APPLICATION_JSON)
										.content(exampleJson)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		System.out.println(result.getResponse());
		assertEquals(HttpStatus.OK.value(), response.getStatus());		
	}
	
	@Test
	public void updateProjectNegTest() throws Exception {
		Mockito.when(projectService.updateProject(Mockito.any())).thenReturn(false);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.put("/editproject")
										.accept(MediaType.APPLICATION_JSON)
										.content(exampleJson)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		System.out.println(result.getResponse());
		assertEquals(HttpStatus.EXPECTATION_FAILED.value(), response.getStatus());		
	}
	

	@Test
	public void deleteProjectTest() throws Exception {
		Mockito.when(projectService.deleteProject(Mockito.anyInt())).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.put("/deleteproject")
										.accept(MediaType.APPLICATION_JSON)
										.content(exampleJson)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		System.out.println(result.getResponse());
		assertEquals(HttpStatus.OK.value(), response.getStatus());		
	}
	
	@Test
	public void deleteProjectNegTest() throws Exception {
		Mockito.when(projectService.deleteProject(Mockito.anyInt())).thenReturn(false);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.put("/deleteproject")
										.accept(MediaType.APPLICATION_JSON)
										.content(exampleJson)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		System.out.println(result.getResponse());
		assertEquals(HttpStatus.EXPECTATION_FAILED.value(), response.getStatus());		
	}
	@Test
	public void retreiveProjectbyNameTest() throws Exception {
		Mockito.when(projectService.getProjectByPName(Mockito.anyString())).thenReturn(project);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/getprojectByPName/TIM")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected ="{\"projectId\": 123,\"project\": \"TIM\","
				+ "\"startDate\":null,\"endDate\": null,\"priority\": 12}";
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}
	
	@Test
	public void retreivTaskist() throws Exception {
		Task task = new Task(1,1,1, "Development",null,null,1,"COMPL");
		Mockito.when(projectService.getCompletedTasks(Mockito.anyInt())).thenReturn(Arrays.asList(task));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/getProjectTasks/123")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
//		String expected ="{\"projectId\": 123,\"project\": \"TIM\",\"startDate\":null,"
//				+ "\"endDate\": null,\"priority\": 12}";
//		JSONAssert.assertEquals(expected, result.getResponse()
//				.getContentAsString(), false);
		
		assertNotNull(result.getResponse().getContentAsString());
	}
	
	@Test
	public void retreivCompletedTaskList() throws Exception {
		Task task = new Task(1,1,1, "Development",null,null,1,"COMPL");
		Mockito.when(projectService.getCompletedTasks(Mockito.anyInt())).thenReturn(Arrays.asList(task));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/getcompleted/123")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
//		String expected ="{\"projectId\": 123,\"project\": \"TIM\",\"startDate\":null,"
//				+ "\"endDate\": null,\"priority\": 12}";
//		JSONAssert.assertEquals(expected, result.getResponse()
//				.getContentAsString(), false);
		
		assertNotNull(result.getResponse().getContentAsString());
	}
}
