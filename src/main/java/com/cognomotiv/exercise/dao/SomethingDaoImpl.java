package com.cognomotiv.exercise.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.stereotype.Repository;

import com.cognomotiv.exercise.model.Something;

@Repository("somethingDao")
public class SomethingDaoImpl implements SomethingDao {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory=sf;
	}
	
	@Override
	public void save(Something s) throws Exception {
		Session session=sessionFactory.getCurrentSession();
		session.persist(s);
	}
	
	@Override
	public void update(Something s) throws Exception {
		Session session=sessionFactory.getCurrentSession();
		session.update(s);
	}
	
	@Override
	public void delete(String id) throws Exception {
		Session session=sessionFactory.getCurrentSession();
		Something s=(Something) session.load(Something.class, Integer.valueOf(id));
		if (null!=s){
			session.delete(s);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Something> read() throws Exception {
		Session session=sessionFactory.getCurrentSession();
		return session.createQuery("from Something").list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Something read(String id) throws Exception {
		Session session=sessionFactory.getCurrentSession();
		List<Something> l=session.createQuery("from Something where id="+id).list();
		return l.get(0);
	}
}
