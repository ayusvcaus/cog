package com.cognomotiv.exercise.service;

import java.util.List;

import com.cognomotiv.exercise.model.Something;

public interface SomethingService {
	public List<Something> read() throws Exception;	
	public Something read(String id) throws Exception;
	public void create(final String name) throws Exception;
	public void update(final String name, final String id) throws Exception;
	public void remove(final String id) throws Exception;
}
