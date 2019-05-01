package com.soft.cognizant.task.TaskTracker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskTrackerApplication extends ServletInitializer{

	public static void main(String[] args) {
		final Logger logger = LogManager.getLogger(TaskTrackerApplication.class);
		logger.info("TaskTrackerApplication starts");
		SpringApplication.run(TaskTrackerApplication.class, args);
		logger.info("TaskTrackerApplication ends");
	}

}

