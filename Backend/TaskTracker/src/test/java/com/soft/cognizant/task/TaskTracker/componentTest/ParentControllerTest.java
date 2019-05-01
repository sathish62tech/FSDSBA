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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.soft.cognizant.task.TaskTracker.component.ParentControllerRest;
import com.soft.cognizant.task.TaskTracker.entity.ParentTask;
import com.soft.cognizant.task.TaskTracker.service.ParentService;

@RunWith(SpringRunner.class)
@WebMvcTest(value= {ParentControllerRest.class,ParentService.class,ParentTask.class})
public class ParentControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	ParentService parentService;
	
	
	ParentTask pTask = new ParentTask(1,"Parent");
	
	String pName = "Parent";
	
	String exampleJJson ="{\"parentId\":1,\"parentTaskName\":\"Parent\"}";
	
	
	@Test
	public void getParentTaskTest() throws Exception { 
		Mockito.when(parentService.getParentTask(Mockito.anyInt())).thenReturn(pTask);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/getParentTask/1")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println("getParentTaskTest >>>>>>>>" + result.getResponse());
		String expected ="{\"parentId\":1,\"parentTaskName\":\"Parent\"}";
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		
	}
	
	
	@Test
	public void createParentTest() throws Exception {
		Mockito.when(parentService.createParentTask(Mockito.any())).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.post("/addParentTask/Parent")
										.accept(MediaType.ALL);
							
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected =  pName;
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	public void retreiveParentList() throws Exception {
		Mockito.when(parentService.getParentTasks()).thenReturn(Arrays.asList(pTask));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/getParentTasks")
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
