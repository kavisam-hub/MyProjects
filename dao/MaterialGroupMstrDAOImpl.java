package com.dtvs.dtvsonline.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dtvs.dtvsonline.model.MaterialGroupMaster;

@Repository
public class MaterialGroupMstrDAOImpl implements MaterialGroupMstrDAO {

	@Autowired
	private  SessionFactory sessionFactory;
	@SuppressWarnings("unchecked")
	public List<MaterialGroupMaster> fetchAllGroup() {		
		return sessionFactory.getCurrentSession().createQuery("from MaterialGroupMaster").list();
	}

}
