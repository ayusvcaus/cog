package com.cognomotiv.exercise.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.cognomotiv.exercise.model.User;
import com.cognomotiv.exercise.dao.UserDao;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory=sf;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<User> read(String username, String password) throws Exception {		
		Session session=sessionFactory.getCurrentSession();
		return session.createQuery("from User where username='"+username+"' and password='"+password+"'").list();
	}
}
