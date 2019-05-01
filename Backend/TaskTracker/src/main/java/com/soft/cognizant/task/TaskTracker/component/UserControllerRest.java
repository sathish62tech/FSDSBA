package com.soft.cognizant.task.TaskTracker.component;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soft.cognizant.task.TaskTracker.Exception.TaskTrackerException;
import com.soft.cognizant.task.TaskTracker.entity.User;
import com.soft.cognizant.task.TaskTracker.service.UserService;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class UserControllerRest {

	private static final Logger logger = LogManager.getLogger(UserControllerRest.class);

	@Autowired
	UserService userService;

	// Get User List
	@GetMapping(value = "/user")
	@Produces({ "application/json" })
	@ResponseBody
	public List<User> getUsers() {
		logger.info("getUsers invoked ");
		return userService.getUsers();
	}

	@PostMapping(value = "/addUser")
	@Consumes({ "application/json" })
	@ResponseBody
	public ResponseEntity<User> createUser(@RequestBody User user) throws TaskTrackerException {
		logger.info("createUser invoked with " + user);
		boolean isCreated = false;
		try {
			isCreated = userService.createUser(user);
		} catch (Exception e) {
			logger.error(e);
			throw new TaskTrackerException("usernot created, Check if Parent task exists!", e);
		}

		if (isCreated) {
			return new ResponseEntity<User>(HttpStatus.CREATED);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header("message", "User not created, Check if User exists!").build();
		}
	}

	// Edit user
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping(value = "/edituser")
	@Consumes({ "application/json" })
	@ResponseBody
	public ResponseEntity<User> updateUser(@RequestBody User user) throws TaskTrackerException {
		logger.info("updateUser invoked with " + user);
		boolean isCreated = false;
		try {
			isCreated = userService.updateUser(user);
		} catch (Exception e) {
			logger.error(e);
			throw new TaskTrackerException("User not updated, Check if valid data is provided!", e);
		}
		if (isCreated) {
			return new ResponseEntity<User>(HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.header("message", "Task Not Updated, Check Values provided").build();
		}
	}

	// Edit user
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping(value = "/deleteUser")
	@Consumes({ "application/json" })
	@ResponseBody
	public ResponseEntity<User> deleteUser(@RequestBody User user) throws TaskTrackerException {
		boolean isdeleted = false;
		logger.info("deleteUser invoked with " + user);
		try {
			isdeleted = userService.deleteUser(user.getUserId());
		} catch (Exception e) {
			logger.error(e);
			throw new TaskTrackerException("User not deleted, Check if valid data is provided!", e);
		}
		if (isdeleted) {
			return new ResponseEntity<User>(HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.header("message", "Task Not Updated, Check Values provided").build();
		}
	}

	@GetMapping(value = "/getuser/{id}")
	@Produces({ "application/json" })
	@ResponseBody
	public User getUser(@PathVariable int id) throws TaskTrackerException {
		logger.info("getUser invoked with " + id);
		User userFetched = null;
		userFetched = userService.getUser(id);
		return userFetched;
	}

	@GetMapping(value = "/getuserbyproject/{id}")
	@Produces({ "application/json" })
	@ResponseBody
	public User getUserByProjectId(@PathVariable int id) throws TaskTrackerException {
		logger.info("getUserByProjectId invoked with " + id);
		User userFetched = null;
		userFetched = userService.getUserByProjectId(id);

		return userFetched;
	}
}
