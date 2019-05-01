package com.soft.cognizant.task.TaskTracker.componentTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

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

import com.soft.cognizant.task.TaskTracker.component.TaskControllerRest;
import com.soft.cognizant.task.TaskTracker.entity.Task;
import com.soft.cognizant.task.TaskTracker.service.TaskTrackerService;

@RunWith(SpringRunner.class)
@WebMvcTest(value= {TaskControllerRest.class,TaskTrackerService.class,Task.class})
public class TaskControllerTest {

	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TaskTrackerService taskService;
	
	Date date = new Date();
	Calendar cal;
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	
	Task task = new Task(1,1,1, "Development",null,null,1,"NEW");
	
	String exampleJson = "{\"taskId\":1,\"parentTaskId\":1,\"projectId\":1,"
						+ "\"task\":\"Development\",\"startDate\":null,"
						+ "\"endDate\":null,\"status\":\"NEW\"}";
	
	
	@Test
	public void retrieveTaskTest() throws Exception{
		
		Mockito.when(taskService.getTask(Mockito.anyInt())).thenReturn(task);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/gettask/1").accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected =  "{\"taskId\":1,\"parentTaskId\":1,\"projectId\":1,"
				+ "\"task\":\"Development\",\"startDate\":null,"
				+ "\"endDate\":null,\"status\":\"NEW\"}";;
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}
	
	
	@Test
	public void createTaskTest() throws Exception{
		
		Mockito.when(taskService.createTask(Mockito.any())).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/addtask")
				.accept(MediaType.APPLICATION_JSON)
				.content(exampleJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		System.out.println(response);
		String expected =  "{\"taskId\":1,\"parentTaskId\":1,\"projectId\":1,"
				+ "\"task\":\"Development\",\"startDate\":null,"
				+ "\"endDate\":null,\"status\":\"NEW\"}";;
		System.out.println(expected);
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}
	
	
	@Test
	public void createTaskNegTest() throws Exception{
		
		Mockito.when(taskService.createTask(Mockito.any())).thenReturn(false);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/addtask")
				.accept(MediaType.APPLICATION_JSON)
				.content(exampleJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		System.out.println(result.getResponse());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
	}
	
	
	@Test
	public void retreiveTaskList() throws Exception {
		Mockito.when(taskService.getTasks(Mockito.anyInt())).thenReturn(Arrays.asList(task));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/gettasks/1")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		assertNotNull(result.getResponse().getContentAsString());
	}
	
	@Test
	public void editTaskTest() throws Exception{
		
		Mockito.when(taskService.updateTask(Mockito.any())).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/edittask")
				.accept(MediaType.APPLICATION_JSON)
				.content(exampleJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		System.out.println(result.getResponse());
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	public void editTaskNegTest() throws Exception{
		
		Mockito.when(taskService.updateTask(Mockito.any())).thenReturn(false);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/edittask")
				.accept(MediaType.APPLICATION_JSON)
				.content(exampleJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		System.out.println(result.getResponse());
		assertEquals(HttpStatus.EXPECTATION_FAILED.value(), response.getStatus());
	}
	
	@Test
	public void completeTaskTest() throws Exception{
		
		Mockito.when(taskService.completeTask(Mockito.anyInt())).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/completeTask/1")
				.accept(MediaType.APPLICATION_JSON)
				.content(exampleJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		System.out.println(result.getResponse());

		System.out.println(result.getResponse());
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	public void completeTaskNegTest() throws Exception{
		
		Mockito.when(taskService.completeTask(Mockito.anyInt())).thenReturn(false);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/completeTask/1")
				.accept(MediaType.APPLICATION_JSON)
				.content(exampleJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		System.out.println(result.getResponse());

		System.out.println(result.getResponse());
		assertEquals(HttpStatus.EXPECTATION_FAILED.value(), response.getStatus());
	}
	
	@Test
	public void getTaskIdbyParentNProjectTest() throws Exception{
		
		Mockito.when(taskService.getTaskIdbyParentNProject(Mockito.anyString())).thenReturn(task);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/getTaskbytask/1-1-Development").accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected =  "{\"taskId\":1,\"parentTaskId\":1,\"projectId\":1,"
				+ "\"task\":\"Development\",\"startDate\":null,"
				+ "\"endDate\":null,\"status\":\"NEW\"}";;
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}
}
