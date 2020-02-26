package com.cognomotiv.exercise.dao;

import java.util.List;

import com.cognomotiv.exercise.model.Something;

public interface SomethingDao {
	public void save(Something se) throws Exception;
	public void update(Something se) throws Exception;
	public void delete(String id) throws Exception;
	public List<Something> read() throws Exception;
	public Something read(String id) throws Exception;
}
