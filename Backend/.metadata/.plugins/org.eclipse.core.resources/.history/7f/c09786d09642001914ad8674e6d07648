package com.capsule.TaskTracker.component;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capsule.TaskTracker.service.TaskTrackerService;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.capsule.TaskTracker.Exception.TaskTrackerException;
import com.capsule.TaskTracker.entity.ParentTask;
import com.capsule.TaskTracker.entity.Task;


@Controller
public class TaskControllerRest {
	
	@Autowired
	TaskTrackerService taskService;
	
	
	// Get Task List
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value="/task")
	@Produces({"application/json"})
	@ResponseBody
	public List<Task> getTasks(){
		System.out.println("contrller");
		return taskService.getTasks();
	}
	
	// Create Tasks
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(value="/addtask")
	@Consumes({"application/json"})
	@ResponseBody
	public ResponseEntity<Task> createTask(@RequestBody Task task) throws TaskTrackerException{
		System.out.println("task passed" + task);
		boolean isCreated = false;
		try {
			isCreated = taskService.createTask(task);
		} catch (Exception e) {
			throw new TaskTrackerException("not inserted",e);
		}
		
		if(isCreated){
			return new ResponseEntity<Task>(HttpStatus.CREATED);
		} else {
//			return new ResponseEntity<Product>(HttpStatus.OK);
			
			return ResponseEntity.status(HttpStatus.OK).header("message", "not created").build();
		}
	}
	
	
	// Create Tasks
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(value="/addParentTask")
	@Consumes({"application/json"})
	@ResponseBody
	public ResponseEntity<String> createParentTask(@RequestBody ParentTask parentTask) {
		System.out.println("task passed" + parentTask);
		boolean isCreated = false;
		
		isCreated = taskService.createParentTask(parentTask);
		
		if(isCreated){
			return new ResponseEntity<String>(HttpStatus.CREATED);
		} else {
//			return new ResponseEntity<Product>(HttpStatus.OK);
			
			return ResponseEntity.status(HttpStatus.OK).header("message", "not created").build();
		}
	}
	
	
	
   // Edit Non Flipped Task
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping(value="/edittask")
	@Consumes({"application/json"})
	@ResponseBody
	public ResponseEntity<Task> updateTask(@RequestBody Task task){
		boolean isCreated = taskService.updateTask(task);
		if(isCreated){
			return new ResponseEntity<Task>(HttpStatus.OK);
		} else {
//			return new ResponseEntity<Product>(HttpStatus.OK);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).header("message", "not updated").build();
		}
	}
	
	   // Edit Flipped Task
		@CrossOrigin(origins = "http://localhost:4200")
		@PutMapping(value="/editFliptask")
		@Consumes({"application/json"})
		@ResponseBody
		public ResponseEntity<Task> updateFlipTask(@RequestBody Task task){
			boolean isCreated = taskService.updateFlipTask(task);
			if(isCreated){
				return new ResponseEntity<Task>(HttpStatus.CREATED);
			} else {
//				return new ResponseEntity<Product>(HttpStatus.OK);
				return ResponseEntity.status(HttpStatus.OK).header("message", "not updated").build();
			}
		}
}
