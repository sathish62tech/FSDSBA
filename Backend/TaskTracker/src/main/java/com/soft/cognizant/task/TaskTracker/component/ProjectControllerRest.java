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
import com.soft.cognizant.task.TaskTracker.entity.Project;
import com.soft.cognizant.task.TaskTracker.entity.Task;
import com.soft.cognizant.task.TaskTracker.service.ProjectService;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectControllerRest {

	private static final Logger logger = LogManager.getLogger(ProjectControllerRest.class);

	@Autowired
	ProjectService projectService;

	// Get project List
	@GetMapping(value = "/projects")
	@Produces({ "application/json" })
	@ResponseBody
	public List<Project> getProjects() {
		logger.info("getProjects invoked");
		return projectService.getProjects();
	}

	@PostMapping(value = "/addproject")
	@Consumes({ "application/json" })
	@ResponseBody
	public Project createProject(@RequestBody Project project) throws TaskTrackerException {
		logger.info("createProject invoked with " + project);
		Project createdProject = null;
		try {
			createdProject = projectService.createProject(project);
		} catch (Exception e) {
			logger.error(e);
			throw new TaskTrackerException("projectnot created, Check if Parent task exists!", e);
		}
		return createdProject;
	}

	// Edit project
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping(value = "/editproject")
	@Consumes({ "application/json" })
	@ResponseBody
	public ResponseEntity<Project> updateProject(@RequestBody Project project) throws TaskTrackerException {
		logger.info("updateProject invoked with " + project);
		boolean isCreated = false;
		try {
			isCreated = projectService.updateProject(project);
		} catch (Exception e) {
			logger.error(e);
			throw new TaskTrackerException("Project not updated, Check if valid data is provided!", e);
		}
		if (isCreated) {
			return new ResponseEntity<Project>(HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.header("message", "Task Not Updated, Check Values provided").build();
		}
	}

	// Edit project
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping(value = "/deleteproject")
	@Consumes({ "application/json" })
	@ResponseBody
	public ResponseEntity<Project> deleteProject(@RequestBody Project project) throws TaskTrackerException {
		logger.info("deleteProject invoked with " + project);
		boolean isdeleted = false;
		try {
			isdeleted = projectService.deleteProject(project.getProjectId());
		} catch (Exception e) {
			logger.error(e);
			throw new TaskTrackerException("project not deleted, Check if valid data is provided!", e);
		}
		if (isdeleted) {
			return new ResponseEntity<Project>(HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.header("message", "Project Not Updated, Check Values provided").build();
		}
	}

	@GetMapping(value = "/getproject/{id}")
	@Produces({ "application/json" })
	@ResponseBody
	public Project getProject(@PathVariable int id) throws TaskTrackerException {
		logger.info("getProject invoked with " + id);
		Project projectFetched = null;
		projectFetched = projectService.getProject(id);
		return projectFetched;
	}

	// get 1 project
	@GetMapping(value = "/getprojectByPName/{projectName}")
	@Produces({ "application/json" })
	@ResponseBody
	public Project getProjectByPName(@PathVariable String projectName) throws TaskTrackerException {
		logger.info("getProjectByPName invoked with " + projectName);
		Project projectFetched = null;
		projectFetched = projectService.getProjectByPName(projectName);
		return projectFetched;
	}

	@GetMapping(value = "/getcompleted/{id}")
	@Produces({ "application/json" })
	@ResponseBody
	public int getCompletedTasks(@PathVariable int id) {
		logger.info("getCompletedTasks invoked with " + id);
		List<Task> completedTasks = projectService.getCompletedTasks(id);
		return completedTasks.size();
	}

	@GetMapping(value = "/getProjectTasks/{id}")
	@Produces({ "application/json" })
	@ResponseBody
	public int getProjectTasks(@PathVariable int id) {
		logger.info("getProjectTasks invoked with " + id);
		List<Task> projectTasks = projectService.getProjectTasks(id);
		return projectTasks.size();
	}
}
