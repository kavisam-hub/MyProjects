package com.dtvs.dtvsonline.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dtvs.dtvsonline.model.User;

@Repository
public class DatabaseDAOImpl implements DatabaseDAO {

	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public User getUserById(String id) {
		// TODO Auto-generated method stub
		return (User)sessionFactory.getCurrentSession().get(User.class, id);
	}

}
