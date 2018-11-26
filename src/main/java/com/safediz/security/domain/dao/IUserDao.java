package com.safediz.security.domain.dao;

import java.util.List;

import com.safediz.security.domain.User;

import rw.gov.framework.dao.IGuidDao;

public interface IUserDao extends IGuidDao<User> {

	public static final String NAME = "UserDao";

	public static class QUERY {

		public static final String findAll = "select a from User a";
		
	}

	public static class QUERY_NAME {

		public static final String findAll = "User.findAll";
	}

	public abstract List<User> findAll();

	public abstract void deleteUser(User user);

}

