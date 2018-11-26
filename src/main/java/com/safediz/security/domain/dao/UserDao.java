package com.safediz.security.domain.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.safediz.security.domain.User;

import rw.gov.framework.dao.GuidDao;

@Repository(IUserDao.NAME)
@Transactional
public class UserDao extends GuidDao<User> implements IUserDao {

	@Override
	public List<User> findAll() {
		return executeNamedQueryMultiple(IUserDao.QUERY_NAME.findAll);
	}

	@Override
	public void deleteUser(User user) {
		super.remove(user);		
	}

}
