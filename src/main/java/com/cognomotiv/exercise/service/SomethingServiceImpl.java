package com.cognomotiv.exercise.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognomotiv.exercise.model.Something;
import com.cognomotiv.exercise.dao.SomethingDao;

@Service
public class SomethingServiceImpl implements SomethingService {
	
	@Autowired
	private SomethingDao somethingDao;
	
	@Override
	@Transactional
	public List<Something> read() throws Exception {		
		List<Something> list=somethingDao.read();
		return list;
	}
	
	@Override
	@Transactional
	public Something read(String id) throws Exception {		
		return somethingDao.read(id);
	}	
	
	
	@Override
	@Transactional
	public void create(final String name) throws Exception {
		Something se=new Something();
		se.setName(name);
		somethingDao.save(se);
	}
	
	@Override
	@Transactional
	public void remove(final String id) throws Exception {
		somethingDao.delete(id);
	}
		
	@Override
	@Transactional
	public void update(final String name, final String id) throws Exception {
		Something se=new Something();
		se.setName(name);
		se.setId(Integer.valueOf(id));
		somethingDao.update(se);			
	}
}
