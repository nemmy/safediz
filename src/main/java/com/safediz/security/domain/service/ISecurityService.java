package com.safediz.security.domain.service;

import java.util.List;

import com.safediz.security.domain.Configuration;
import com.safediz.security.domain.User;

public interface ISecurityService {
	
	public static final  String NAME="SecurityService";

	void saveUser(User user);
	
	void saveUser(User user, String password);
	
	void changeUserPassword(User user, String pwd);

	void deleteUser(User user);
	
	void saveConfiguration(Configuration configuration);
	
	Configuration readConfiguration();

	List<User> findAllUsers();

	User findUser(String username);


}