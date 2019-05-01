package com.soft.cognizant.task.TaskTracker.component;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soft.cognizant.task.TaskTracker.entity.ParentTask;
import com.soft.cognizant.task.TaskTracker.service.ParentService;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class ParentControllerRest {

	private static final Logger logger = LogManager.getLogger(ParentControllerRest.class);

	@Autowired
	ParentService parentService;

	// Get Parent Task List
	@GetMapping(value = "/getParentTasks")
	@Produces({ "application/json" })
	@ResponseBody
	public List<ParentTask> getParentTasks() {
		logger.info("getParentTasks invoked");
		return parentService.getParentTasks();
	}

	// Get Parent task by parent task id
	@GetMapping(value = "/getParentTask/{id}")
	@Produces({ "application/json" })
	@ResponseBody
	public ParentTask getParentTask(@PathVariable int id) {
		logger.info("getParentTask invoked with " + id);
		return parentService.getParentTask(id);
	}

	// Create ParentTask
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(value = "/addParentTask/{pname}")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	@ResponseBody
	public String createParentTask(@PathVariable String pname) {
		logger.info("createParentTask invoked with " + pname);
		ParentTask parentTask = new ParentTask(pname);
		parentService.createParentTask(parentTask);
		return pname;
	}

}
