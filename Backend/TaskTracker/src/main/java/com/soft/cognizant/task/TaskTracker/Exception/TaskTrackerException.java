package com.soft.cognizant.task.TaskTracker.Exception;

public class TaskTrackerException extends Exception {
		/**
	 * 
	 */
	private static final long serialVersionUID = 4497164898145540072L;

		public TaskTrackerException(String message,Exception exception) {
			super(message,exception);
		}
		
		public TaskTrackerException(String message) {
			super(message);
		}
}
