package com.soft.cognizant.task.TaskTracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soft.cognizant.task.TaskTracker.entity.User;
import com.soft.cognizant.task.TaskTracker.jdbc.UserDAO;

@Service
public class UserService {
	
	@Autowired
	UserDAO userDAO;
	
	public List<User> getUsers(){
		return userDAO.getUserList();
	}
	public boolean createUser(User user) {
		return userDAO.insertUser(user);
	}
	public User getUser(int id) {
		return userDAO.getUser(id);
	}
	public boolean deleteUser(int id) {
		return userDAO.deleteUser(id);
	}
	public boolean updateUser(User user) {
		return userDAO.updateUser(user);
	}
	public User getUserByProjectId(int id) {
		return userDAO.getUserbyProjectId(id);
	}
}
