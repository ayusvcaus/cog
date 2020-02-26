package com.cognomotiv.exercise.service;

public interface UserService {
	public boolean exists(String username, String password) throws Exception;
}
