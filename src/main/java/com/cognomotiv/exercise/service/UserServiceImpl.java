package com.cognomotiv.exercise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognomotiv.exercise.dao.UserDao;
import com.cognomotiv.exercise.service.UserService;;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Override
	@Transactional
    public boolean exists(String username, String password) throws Exception {
		if (username==null || "".equals(username) || password==null || "".equals(password)) {
			return false;
		}
    	return !userDao.read(username, password).isEmpty();
    }
}
