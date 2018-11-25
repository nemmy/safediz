package com.safediz.security.service;

import java.util.List;

import com.safediz.security.Configuration;
import com.safediz.security.User;

public interface ISecurityService {
	
	public static final  String NAME="SecurityService";

	void saveUser(User user);
	
	void saveUser(User user, String password);
	
	void deleteUser(User user);
	
	void saveConfiguration(Configuration configuration);
	
	Configuration readConfiguration();

	List<User> findAllUsers();

	User findUser(String username);


}