package com.cognomotiv.exercise.dao;

import java.util.List;

import com.cognomotiv.exercise.model.User;

public interface UserDao {
	public List<User> read(String username, String password) throws Exception;
}
