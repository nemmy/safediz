package com.safediz.security.domain.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.openjpa.util.ObjectNotFoundException;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.j_spaces.core.client.SQLQuery;
import com.safediz.security.domain.Configuration;
import com.safediz.security.domain.User;
import com.safediz.security.domain.dao.IUserDao;

@Service(ISecurityService.NAME)
public class SecurityService implements ISecurityService {

	@Autowired
	private GigaSpace dataSpace;

	@Autowired
	private IUserDao userDao;

	@Autowired
	private DefaultPasswordService passwordService;

	@Override
	public void saveUser(User user) {
		if (user.isNew()) {
			userDao.create(user);
		} else {
			userDao.update(user);
		}

		dataSpace.write(user);
	}

	@Override
	public void saveUser(User user, String password) {
		String encryPwd = passwordService.encryptPassword(password);
		user.setPassword(encryPwd);
		saveUser(user);
	}

	@Override
	public void deleteUser(User user) {
		userDao.deleteUser(user);
		dataSpace.clear(user);
	}

	@Override
	public void saveConfiguration(Configuration configuration) {
		dataSpace.write(configuration);

	}

	@Override
	public Configuration readConfiguration() {
		SQLQuery<Configuration> query = new SQLQuery<>(Configuration.class, "");
		Configuration configuration = dataSpace.read(query);
		if (configuration == null) {
			configuration = new Configuration();
			configuration.setId(UUID.randomUUID());
			dataSpace.write(configuration);
		}

		return configuration;
	}

	@Override
	public List<User> findAllUsers() {
		SQLQuery<User> query = new SQLQuery<>(User.class, " order by firstname");

		User[] records = dataSpace.readMultiple(query);

		return new ArrayList<>(Arrays.asList(records));
	}

	@Override
	public User findUser(String username) {
		SQLQuery<User> query = new SQLQuery<>(User.class, "username = ?");
		query.setParameters(username);
		User user = dataSpace.read(query);
		if (user == null) {
			throw new ObjectNotFoundException(username);
		}

		return user;
	}

}
