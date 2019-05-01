package com.soft.cognizant.task.TaskTracker.componentTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

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

import com.soft.cognizant.task.TaskTracker.component.UserControllerRest;
import com.soft.cognizant.task.TaskTracker.entity.User;
import com.soft.cognizant.task.TaskTracker.service.UserService;


@RunWith(SpringRunner.class)
@WebMvcTest(value= {UserControllerRest.class,UserService.class,User.class})
public class UserControllerTest {

	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	
	User user = new User(2,"Rashmi","Chudamani",1123,1,1);
	User emptyUser = new User();
	
	String exampleJson = "{\"userId\": \"2\",\"firstName\": \"Rashmi\",\"lastName\": \"Chudamani\",\"employeeId\": \"1123\",\"projectId\": 1,\"taskId\": 1}";
	
	String arrayJson = "[{\"userId\": 2,\"firstName\": \"Rashmi\",\"lastName\": \"Chudamani\",\"employeeId\": 1123,\"projectId\": 1,\"taskId\": 1},{\"userId\": 3,\"firstName\": \"Ramya\",\"lastName\": \"Chudamani\",\"employeeId\": 5813,\"projectId\": 3,\"taskId\": 2}]";
	
	boolean insertexpRet = true;
	
	List<User> lister = Arrays.asList(new User(2,"Rashmi","Chudamani",1123,1,1),
									  new User(3,"Ramya","Chudamani",5813,3,2));
	
	@Test
	public void retrieveUserTest() throws Exception{
		Mockito.when(userService.getUser(Mockito.anyInt())).thenReturn(user);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/getuser/2").accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "{\"userId\": 2,\"firstName\": \"Rashmi\","
						+ "\"lastName\": \"Chudamani\",\"employeeId\": 1123,"
						+ "\"projectId\": 1,\"taskId\": 1}";
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}
	
	@Test
	public void retrieveUserExcepTest() throws Exception{
		Mockito.when(userService.getUser(Mockito.anyInt())).thenReturn(user);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/getuser/2").accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "{\"userId\": 2,\"firstName\": \"Rashmi\","
						+ "\"lastName\": \"Chudamani\",\"employeeId\": 1123,"
						+ "\"projectId\": 1,\"taskId\": 1}";
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}
	
	@Test
	public void retreiveUserList() throws Exception {
		Mockito.when(userService.getUsers()).thenReturn(Arrays.asList(user));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/user")
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
	public void retrieveUserNegTest() throws Exception{
		Mockito.when(userService.getUser(Mockito.anyInt())).thenReturn(emptyUser);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
									.get("/getuser/3")
									.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
	}
	
	@Test
	public void createUserTest() throws Exception {
		Mockito.when(userService.createUser(Mockito.any())).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.post("/addUser")
										.accept(MediaType.APPLICATION_JSON)
										.content(exampleJson)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		System.out.println(result.getResponse());
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		
	}
	
	@Test
	public void createUserNegTest() throws Exception {
		Mockito.when(userService.createUser(Mockito.any())).thenReturn(false);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.post("/addUser")
										.accept(MediaType.APPLICATION_JSON)
										.content(exampleJson)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		System.out.println(result.getResponse());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
		
	}

	@Test
	public void updateUserTest() throws Exception {
		Mockito.when(userService.updateUser(Mockito.any())).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.put("/edituser")
										.accept(MediaType.APPLICATION_JSON)
										.content(exampleJson)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		System.out.println(result.getResponse());
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}
	
	@Test
	public void updateUserNegTest() throws Exception {
		Mockito.when(userService.updateUser(Mockito.any())).thenReturn(false);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.put("/edituser")
										.accept(MediaType.APPLICATION_JSON)
										.content(exampleJson)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		System.out.println(result.getResponse());
		assertEquals(HttpStatus.EXPECTATION_FAILED.value(), response.getStatus());
		
	}
	
	@Test
	public void deleteUserTest() throws Exception {
		Mockito.when(userService.deleteUser(Mockito.anyInt())).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.put("/deleteUser")
										.accept(MediaType.APPLICATION_JSON)
										.content(exampleJson)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		System.out.println(result.getResponse());
//		assertEquals(true, response.getStatus());
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}
	
	@Test
	public void deleteUserNegTest() throws Exception {
		Mockito.when(userService.deleteUser(Mockito.anyInt())).thenReturn(false);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.put("/deleteUser")
										.accept(MediaType.APPLICATION_JSON)
										.content(exampleJson)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		System.out.println(result.getResponse());
		assertEquals(HttpStatus.EXPECTATION_FAILED.value(), response.getStatus());
		
	}
	
	@Test
	public void getUserByProjectIdTest() throws Exception{
		Mockito.when(userService.getUserByProjectId(Mockito.anyInt())).thenReturn(user);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.get("/getuserbyproject/1")
										.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "{\"userId\": 2,\"firstName\": \"Rashmi\","
						+ "\"lastName\": \"Chudamani\",\"employeeId\": 1123,"
						+ "\"projectId\": 1,\"taskId\": 1}";
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}
	
}
