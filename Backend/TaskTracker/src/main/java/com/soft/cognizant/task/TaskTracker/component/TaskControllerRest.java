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
import com.soft.cognizant.task.TaskTracker.entity.Task;
import com.soft.cognizant.task.TaskTracker.service.TaskTrackerService;

@Controller
public class TaskControllerRest {

	private static final Logger logger = LogManager.getLogger(TaskControllerRest.class);

	@Autowired
	TaskTrackerService taskService;

	// Get Task List
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "/gettasks/{id}")
	@Produces({ "application/json" })
	@ResponseBody
	public List<Task> getTasks(@PathVariable int id) {
		logger.info("getTasks invoked with " + id);
		return taskService.getTasks(id);
	}

	// Get Task List
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "/gettask/{id}")
	@Produces({ "application/json" })
	@ResponseBody
	public Task getTask(@PathVariable int id) {
		logger.info("getTask invoked with " + id);
		return taskService.getTask(id);
	}

	// Create Tasks
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(value = "/addtask")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	@ResponseBody
	public ResponseEntity<Task> createTask(@RequestBody Task task) throws TaskTrackerException {
		logger.info("createTask invoked with " + task);
		boolean isCreated = false;
		try {
			isCreated = taskService.createTask(task);
		} catch (Exception e) {
			logger.error(e);
			throw new TaskTrackerException("Task not created, Check if Parent task exists!", e);
		}

		if (isCreated) {
			return new ResponseEntity<Task>(HttpStatus.CREATED);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header("message", "Task not created, Check if Parent task exists!").build();
		}
	}

	// Edit Task
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping(value = "/edittask")
	@Consumes({ "application/json" })
	@ResponseBody
	public ResponseEntity<Task> updateTask(@RequestBody Task task) throws TaskTrackerException {
		boolean isCreated = false;
		logger.info("updateTask invoked with " + task);
		try {
			isCreated = taskService.updateTask(task);
		} catch (Exception e) {
			logger.error(e);
			throw new TaskTrackerException("Task not updated, Check if Parent task exists and valid data is provided!",
					e);
		}
		if (isCreated) {
			return new ResponseEntity<Task>(HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.header("message", "Task Not Updated, Check Values provided").build();
		}
	}

	// completeTask
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping(value = "/completeTask/{id}")
	@Consumes({ "application/json" })
	@ResponseBody
	public ResponseEntity<Task> completeTask(@PathVariable int id) throws TaskTrackerException {
		boolean isCreated = false;
		logger.info("completeTask invoked with " + id);
		try {
			isCreated = taskService.completeTask(id);
		} catch (Exception e) {
			logger.error(e);
			throw new TaskTrackerException("Task not updated, Check if Parent task exists and valid data is provided!",
					e);
		}
		if (isCreated) {
			return new ResponseEntity<Task>(HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.header("message", "Task Not Updated, Check Values provided").build();
		}
	}

	// Get Task List
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "/getTaskbytask/{datatohelp}")
	@Produces({ "application/json" })
	@ResponseBody
	public Task getTaskIdbyParentNProject(@PathVariable String datatohelp) {
		logger.info("getTaskIdbyParentNProject invoked with " + datatohelp);
		return taskService.getTaskIdbyParentNProject(datatohelp);
	}
}
